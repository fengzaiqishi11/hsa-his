package cn.hsa.sys.parameter.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.center.datasource.service.CenterDatasourceService;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import cn.hsa.module.sys.parameter.bo.SysParameterBO;
import cn.hsa.module.sys.parameter.dao.SysParameterDAO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterUpdateDTO;
import cn.hsa.module.sys.user.dao.SysUserDAO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Package_name: cn.hsa.sys.data.bo.impl
 * @Class_name: sysSupplierBOImpl
 * @Describe: 参数业务逻辑实现层
 * @Author: zhangxuan
 * @Email: zhangxuan@powersi.com
 * @Date: 2020/7/28 16:56
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class SysParameterBOImpl extends HsafBO implements SysParameterBO {

    /**
     * 参数数据库访问接口
     */
    @Resource
    private SysParameterDAO sysParameterDao;

    /**
     * 单据号的生成规则服务层接口
     */
    @Resource
    private BaseOrderRuleService baseOrderRuleService;

    @Resource
    private SysUserDAO sysUserDAO;

    @Resource
    RedisUtils redisUtils;
    @Resource
    CenterDatasourceService centerDatasourceService;

    /**
     * @Menthod queryPage()
     * @Desrciption 根据条件查询参数信息
     * @Param 1. SysParameterDTO  参数数据传输对象
     * @Author zhangxuan
     * @Date 2020/7/28 17:02
     * @Return cn.hsa.sys.PageDTO
     **/
    @Override
    public PageDTO queryPage(SysParameterDTO sysParameterDTO) {
        // 设置分页信息
        PageHelper.startPage(sysParameterDTO.getPageNo(), sysParameterDTO.getPageSize());
        // 根据条件查询所
        List<SysParameterDTO> sysParameterDTOS = sysParameterDao.queryPage(sysParameterDTO);
        return PageDTO.of(sysParameterDTOS);
    }

    /**
     * @Menthod queryAll()
     * @Desrciption 查询所有参数信息
     * @Param [SysParameterDTO]
     * @Author zhangxuan
     * @Date 2020/7/28 15:31
     * @Return java.util.List<cn.hsa.module.sys.parameter.dto.SysParameterDTO>
     **/
    @Override
    public List<SysParameterDTO> queryAll(SysParameterDTO sysParameterDTO) {
        List<SysParameterDTO> sysParameterDTOS = sysParameterDao.queryAll(sysParameterDTO);
        return sysParameterDTOS;
    }


    /**
     * @Menthod insert()
     * @Desrciption 新增参数
     * @Param 1. SysParameterDTO  参数数据传输对象
     * @Author zhangxuan
     * @Date 2020/7/28 15:53
     * @Return int
     **/
    @Override
    public boolean insert(SysParameterDTO sysParameterDTO) {
        return save(sysParameterDTO);
    }

    /**
     * @Menthod delete()
     * @Desrciption 删除参数
     * @Param 1. map
     * @Author zhangxuan
     * @Date 2020/7/28 15:57
     * @Return int
     **/
    @Override
    public boolean delete(SysParameterDTO sysParameterDTO) {
        List<SysParameterUpdateDTO> sysParameterUpdateDTOS = sysParameterDao.querySysParameterByIds(sysParameterDTO);
        int delete = sysParameterDao.delete(sysParameterDTO);
        if (ListUtils.isEmpty(sysParameterUpdateDTOS)) {
            throw new AppException("删除失败，未查找到数据");
        }
        for (SysParameterUpdateDTO sysParameterUpdateDTO : sysParameterUpdateDTOS) {
            // 主键id
            sysParameterUpdateDTO.setId(SnowflakeUtils.getId());
            sysParameterUpdateDTO.setAfterValue(null);
            sysParameterUpdateDTO.setAfterCode(null);
            // 删除置为无效
            sysParameterUpdateDTO.setIsValid("0");
            // 删除时间
            sysParameterUpdateDTO.setCrteTime(sysParameterDTO.getCrteTime());
            // 删除人
            sysParameterUpdateDTO.setCrteId(sysParameterDTO.getCrteId());
            // 删除人姓名
            sysParameterUpdateDTO.setCrteName(sysParameterDTO.getCrteName());
        }
        sysParameterDao.insertParameterUpdate(sysParameterUpdateDTOS);
        return delete > 0;
    }

    /**
     * @Menthod update()
     * @Desrciption 修改参数信息
     * @Param 1. SysParameterDTO  参数数据传输对象
     * @Author zhangxuan
     * @Date 2020/7/28 15:58
     * @Return int
     **/
    @Override
    public boolean update(SysParameterDTO sysParameterDTO) {
        return save(sysParameterDTO);
    }

    /**
     * @Method: save()
     * @Description: 该方法主要用来保存参数信息维护修改和新增的信息
     * @Param: sysParameterDTO数据传输对象
     * @Author: zhangxuan
     * @Date: 2020/8/13 14:17
     * @Return: int
     */
    private boolean save(SysParameterDTO sysParameterDTO) {
        //判断编码(名称)是否重复   （编码可以重复）【ID1003299】系统参数维护，判断重复值不能以名称来判断，建议用编码来判断唯一性
        if (sysParameterDao.queryCodeIsExist(sysParameterDTO) > 0) {
            throw new AppException("该参数编码已存在");
        }
        /*if (sysParameterDao.queryNameIsExist(sysParameterDTO) > 0) {
            throw new AppException("该参数名称已存在");
        }*/
        //生成拼音码五笔码
        String namePym = PinYinUtils.toFirstPY(sysParameterDTO.getName());
        String nameWbm = WuBiUtils.getWBCode(sysParameterDTO.getName());
        sysParameterDTO.setPym(namePym);
        sysParameterDTO.setWbm(nameWbm);
        if (StringUtils.isEmpty(sysParameterDTO.getId())) {
            sysParameterDTO.setId(SnowflakeUtils.getId());
            //不重复执行插入
            int insert = sysParameterDao.insert(sysParameterDTO);
            return insert > 0;
        } else {
            // 修改前数据
            SysParameterDTO beforeItem = sysParameterDao.getById(sysParameterDTO);
            int update = sysParameterDao.update(sysParameterDTO);
            // 修改后数据
            SysParameterDTO afterItem = sysParameterDao.getById(sysParameterDTO);
            if (afterItem == null || beforeItem == null) {
                throw new AppException("修改失败，未查到该数据");
            }
            SysParameterUpdateDTO sysParameterUpdateDTO = new SysParameterUpdateDTO();
            // 主键
            sysParameterUpdateDTO.setId(SnowflakeUtils.getId());
            // 系统参数主键
            sysParameterUpdateDTO.setSysParamterId(afterItem.getId());
            // 修改前编码
            sysParameterUpdateDTO.setAfterCode(afterItem.getCode());
            // 修改后编码
            sysParameterUpdateDTO.setBeforeCode(beforeItem.getCode());
            // 修改前参数值
            sysParameterUpdateDTO.setBeforeValue(beforeItem.getValue());
            // 修改后参数值
            sysParameterUpdateDTO.setAfterValue(afterItem.getValue());
            sysParameterUpdateDTO.setName(afterItem.getName());
            sysParameterUpdateDTO.setHospCode(afterItem.getHospCode());
            sysParameterUpdateDTO.setRemark(afterItem.getRemark());
            sysParameterUpdateDTO.setPym(afterItem.getPym());
            sysParameterUpdateDTO.setWbm(afterItem.getWbm());
            sysParameterUpdateDTO.setIsValid(afterItem.getIsValid());
            sysParameterUpdateDTO.setIsShow(afterItem.getIsShow());
            sysParameterUpdateDTO.setCrteId(sysParameterDTO.getCrteId());
            sysParameterUpdateDTO.setCrteName(sysParameterDTO.getCrteName());
            sysParameterUpdateDTO.setCrteTime(sysParameterDTO.getCrteTime());
            sysParameterUpdateDTO.setIsNeedPwd(sysParameterDTO.getIsNeedPwd());
            List<SysParameterUpdateDTO> sysParameterUpdateDTOS = new ArrayList<>();
            sysParameterUpdateDTOS.add(sysParameterUpdateDTO);
            sysParameterDao.insertParameterUpdate(sysParameterUpdateDTOS);
            return update > 0;
        }
    }

    /**
     * @Method: getParameterByCode
     * @Description: 根据编码获取参数信息
     * @Param: [code]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/15 16:15
     * @Return: cn.hsa.module.sys.parameter.dto.SysParameterDTO
     **/
    @Override
    public SysParameterDTO getParameterByCode(String hospCode, String code) {
        if (StringUtils.isEmpty(code)) {
            throw new AppException("编码为空");
        }
        return sysParameterDao.getParameterByCode(hospCode, code);
    }

    @Override
    public Map<String, SysParameterDTO> getParameterByCodeList(String hospCode, String[] codeList) {
        if (codeList == null || codeList.length == 0) {
            throw new AppException("编码为空");
        }
        return sysParameterDao.getParameterByCodeList(hospCode, codeList);
    }


    public void cacheOperate(SysParameterDTO sysParameterDTO, Boolean operation) {
        if (!ListUtils.isEmpty(sysParameterDTO.getCodes())) {
            for (String code : sysParameterDTO.getCodes()) {
                String key = StringUtils.createKey("parameter", sysParameterDTO.getHospCode(), code);
                if (redisUtils.hasKey(key)) {
                    redisUtils.del(key);
                }
                if (operation) {
                    redisUtils.set(key, sysParameterDTO);
                }
            }
        } else {
            String key = StringUtils.createKey("parameter", sysParameterDTO.getHospCode(), sysParameterDTO.getCode());
            if (redisUtils.hasKey(key)) {
                redisUtils.del(key);
            }
            if (operation) {
                redisUtils.set(key, sysParameterDTO);
            }
        }
    }


    /**
     * @Menthod getIsReallyPwd
     * @Desrciption
     * @Param [map]
     * @Author jiahong.yang
     * @Date 2021/12/20 14:18
     * @Return java.util.Map
     **/
    @Override
    public Map getIsReallyPwd(Map map) {
        Boolean flag = true;
        Map message = new HashMap();
        SysUserDTO sysUserDTO = MapUtils.get(map, "sysUserDTO");
        SysParameterDTO sysParameterDTO = MapUtils.get(map, "sysParameterDTO");
        SysUserDTO sysUser = new SysUserDTO();
        sysUser.setHospCode(sysUserDTO.getHospCode());
        sysUser.setId(sysUserDTO.getId());
        SysUserDTO byId = sysUserDAO.getById(sysUser);
        // 密码错误
        if (!MD5Utils.verifySha(sysParameterDTO.getMdPwd(), byId.getPassword())) {
            flag = false;
            throw new AppException("密码错误！");
        }
        message.put("flag", flag);
        return message;
    }

    /**
     * @Menthod getIsReallyPwd
     * @Desrciption 请求登录人员与机构信息信息
     * @Param [sysParameterDTO, req, res]
     * @Author yuelong.chen
     * @Date 2022/5/10 10:05
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
     **/
    @Override
    public Map getLoginInfo(Map map) {
        SysUserDTO sysUserDTO = MapUtils.get(map, "sysUserDTO");
        String hospCode = sysUserDTO.getHospCode();
        SysParameterDTO sysParameterDTO = sysParameterDao.getParameterByCode(hospCode, "HOSP_INSURE_NATION_INFO");
        Map<String, Object> resultMap = new HashMap();
        resultMap.put("pracCertiNo", sysUserDTO.getPracCertiNo());
        resultMap.put("sysParameterDTO", sysParameterDTO);
        return resultMap;
    }

    /**
     * @Author gory
     * @Description 过期时间
     * @Date 2022/7/26 9:56
     * @Param [centerHospitalDTO]
     **/
    @Override
    public Map<String, Object> getHospServiceStatsByCode(CenterHospitalDTO centerHospitalDTO) {
        // 判断是否为那些用户需要配置，获取过期天数
        String[] codeList = {"WHOISNOTIFY", "OVERDUENOTIFYDAYS"};
        Map<String, SysParameterDTO> parameterMap = sysParameterDao.getParameterByCodeList(centerHospitalDTO.getCode(),
                codeList);
        SysParameterDTO notifyUsersDTO = MapUtils.get(parameterMap, "WHOISNOTIFY");
        String notifyUsers = notifyUsersDTO == null ? "1" : notifyUsersDTO.getValue();
        if (!notifyUsers.contains(centerHospitalDTO.getWorkTypeCode())) {
            return new HashMap<>();
        } else {
            // 成功之后直接调
            SysParameterDTO overdueNotifyDaysDTO = MapUtils.get(parameterMap, "OVERDUENOTIFYDAYS");
            Integer overdueNotifyDays = overdueNotifyDaysDTO == null ? 15 :
                    Integer.parseInt(overdueNotifyDaysDTO.getValue());
            centerHospitalDTO.setMillsOfDays(overdueNotifyDays * 24 * 3600 * 1000l);
            WrapperResponse<Map<String, Object>> hospServiceStatsByCode =
                    centerDatasourceService.getHospServiceStatsByCode(centerHospitalDTO);
            if (null == hospServiceStatsByCode) {
                throw new AppException("未找到医院编码为：" + centerHospitalDTO.getCode() + "的医院信息");
            }
            return hospServiceStatsByCode.getData();
        }

    }
}
