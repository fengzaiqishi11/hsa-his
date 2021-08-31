package cn.hsa.sys.parameter.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.sys.parameter.bo.SysParameterBO;
import cn.hsa.module.sys.parameter.dao.SysParameterDAO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
    RedisUtils redisUtils;

    /**
     * @Menthod queryPage()
     * @Desrciption 根据条件查询参数信息
     * @Param
     * 1. SysParameterDTO  参数数据传输对象
     * @Author zhangxuan
     * @Date   2020/7/28 17:02
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
     * @Desrciption  查询所有参数信息
     *
     * @Param
     * [SysParameterDTO]
     * @Author zhangxuan
     * @Date   2020/7/28 15:31
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
     * @Param
     * 1. SysParameterDTO  参数数据传输对象
     * @Author zhangxuan
     * @Date   2020/7/28 15:53
     * @Return int
     **/
    @Override
    public boolean insert(SysParameterDTO sysParameterDTO) {
            return save(sysParameterDTO);
    }

    /**
     * @Menthod delete()
     * @Desrciption 删除参数
     * @Param
     * 1. map
     * @Author zhangxuan
     * @Date   2020/7/28 15:57
     * @Return int
     **/
    @Override
    public boolean delete(SysParameterDTO sysParameterDTO) {
        int delete = sysParameterDao.delete(sysParameterDTO);
//        cacheOperate(sysParameterDTO,false);
        return delete> 0;
    }

    /**
     * @Menthod update()
     * @Desrciption 修改参数信息
     * @Param
     * 1. SysParameterDTO  参数数据传输对象
     * @Author zhangxuan
     * @Date   2020/7/28 15:58
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
        //判断编码(名称)是否重复
        if (sysParameterDao.queryNameIsExist(sysParameterDTO) > 0) {
            throw new AppException("该参数名称已存在");
        }
        //生成拼音码五笔码
        String namePym = PinYinUtils.toFirstPY(sysParameterDTO.getName());
        String nameWbm = WuBiUtils.getWBCode(sysParameterDTO.getName());
        sysParameterDTO.setPym(namePym);
        sysParameterDTO.setWbm(nameWbm);
        if (StringUtils.isEmpty(sysParameterDTO.getId())) {
            sysParameterDTO.setId(SnowflakeUtils.getId());
            //不重复执行插入
            int insert = sysParameterDao.insert(sysParameterDTO);
            //缓存
//            cacheOperate(sysParameterDTO,true);
            return insert> 0;
        } else {
            int update = sysParameterDao.update(sysParameterDTO);
            //缓存
            // 缓存操作 -- 只有有效的时候才进行操作
            if(Constants.SF.S.equals(sysParameterDTO.getIsValid())){
//                cacheOperate(sysParameterDTO,true);
            }
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

    public void cacheOperate(SysParameterDTO sysParameterDTO, Boolean operation){
        if(!ListUtils.isEmpty(sysParameterDTO.getCodes())){
            for(String code : sysParameterDTO.getCodes()){
                String key = StringUtils.createKey("parameter", sysParameterDTO.getHospCode(), code);
                if(redisUtils.hasKey(key)){
                    redisUtils.del(key);
                }
                if(operation){
                    redisUtils.set(key,sysParameterDTO);
                }
            }
        } else {
            String key = StringUtils.createKey("parameter", sysParameterDTO.getHospCode(), sysParameterDTO.getCode());
            if(redisUtils.hasKey(key)){
                redisUtils.del(key);
            }
            if(operation){
                redisUtils.set(key,sysParameterDTO);
            }
        }
    }
}
