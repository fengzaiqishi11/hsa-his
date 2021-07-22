package cn.hsa.base.bdc.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bdc.bo.BaseDailyfirstCalcBO;
import cn.hsa.module.base.bdc.dao.BaseDailyfirstCalcDAO;
import cn.hsa.module.base.bdc.dto.BaseDailyfirstCalcDTO;
import cn.hsa.module.base.bspc.bo.BaseSpecialCalcBO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;


/**
 * @Package_name: cn.hsa.base.syncdailyfirst.bo.impl
 * @Class_name:: BaseDailyfirstCalcBOImpl
 * @Description: 每日首次计费
 * @Author: ljh
 * @Date: 2020/8/26 19:30
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class BaseDailyfirstCalcBOImpl extends HsafBO implements BaseDailyfirstCalcBO {
    /**
     * 每日首次计费dao
     */
    @Resource
    private BaseDailyfirstCalcDAO baseDailyfirstCalcDAO;

    @Resource
    private BaseSpecialCalcBO baseSpecialCalcBO;

    @Resource
    private RedisUtils redisUtils;

    /**
     * @Package_name: cn.hsa.base.syncdailyfirst.bo.impl
     * @Class_name:: BaseDailyfirstCalcBOImpl
     * @Description: 查询
     * @Author: ljh
     * @Date: 2020/8/26 19:31
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    @Override
    public List<BaseDailyfirstCalcDTO> queryAll(BaseDailyfirstCalcDTO baseDailyfirstCalcDTO) {
        baseDailyfirstCalcDTO.setIsValid("1");
        return baseDailyfirstCalcDAO.queryAll(baseDailyfirstCalcDTO);
    }

    /**
     * @Package_name: cn.hsa.base.syncdailyfirst.bo.impl
     * @Class_name:: BaseDailyfirstCalcBOImpl
     * @Description: 新增
     * @Author: ljh
     * @Date: 2020/8/26 19:31
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    @Override
    public boolean insert(List<BaseDailyfirstCalcDTO> baseDailyfirstCalcDTO) {
        List<BaseDailyfirstCalcDTO> baseDailyfirstCalcinserts = new ArrayList<>();
        List<BaseDailyfirstCalcDTO> baseDailyfirstCalcupdates = new ArrayList<>();
        for (int i = 0; i < baseDailyfirstCalcDTO.size(); i++) {
            if (StringUtils.isEmpty(baseDailyfirstCalcDTO.get(i).getId())) {
                baseDailyfirstCalcDTO.get(i).setId(SnowflakeUtils.getId());
                baseDailyfirstCalcDTO.get(i).setCrteTime(DateUtils.getNow());
                baseDailyfirstCalcDTO.get(i).setIsValid("1");
                BaseDailyfirstCalcDTO baseDailyfirst = new BaseDailyfirstCalcDTO();
                baseDailyfirst = baseDailyfirstCalcDTO.get(i);
                baseDailyfirstCalcinserts.add(baseDailyfirst);
            } else {
                BaseDailyfirstCalcDTO baseDailyfirst = new BaseDailyfirstCalcDTO();
                baseDailyfirst = baseDailyfirstCalcDTO.get(i);
                baseDailyfirstCalcupdates.add(baseDailyfirst);
            }
        }
        if (!ListUtils.isEmpty(baseDailyfirstCalcinserts)) {
            baseDailyfirstCalcDAO.insertList(baseDailyfirstCalcinserts);
            //缓存操作
//            cacheOperate(null,baseDailyfirstCalcinserts,true);
        }
        if (!ListUtils.isEmpty(baseDailyfirstCalcupdates)) {
            baseDailyfirstCalcDAO.updateBatch(baseDailyfirstCalcupdates);
            //缓存操作
//            cacheOperate(null,baseDailyfirstCalcinserts,true);
        }
        return true;

    }


    /**
     * @Package_name: cn.hsa.base.syncdailyfirst.bo.impl
     * @Class_name:: BaseDailyfirstCalcBOImpl
     * @Description: 删除
     * @Author: ljh
     * @Date: 2020/8/26 19:34
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    @Override
    public int deleteBylist(BaseDailyfirstCalcDTO baseDailyfirstCalcDTO) {
        List<String> ids = baseDailyfirstCalcDTO.getIds();
        String hospCode= baseDailyfirstCalcDTO.getHospCode();
        List<BaseDailyfirstCalcDTO> baseDailyfirstCalcs = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            BaseDailyfirstCalcDTO baseDailyfirstCalc = new BaseDailyfirstCalcDTO();
            baseDailyfirstCalc.setId(ids.get(i));
            baseDailyfirstCalc.setHospCode(hospCode);
            baseDailyfirstCalc.setIsValid("0");
            baseDailyfirstCalcs.add(baseDailyfirstCalc);
        }
        int i = baseDailyfirstCalcDAO.deleteBylist(baseDailyfirstCalcs);
        //缓存操作
//        cacheOperate(baseDailyfirstCalcDTO,null,false);
        return i;
    }

    /**
     * @Method queryPage()
     * @Description 分页查询
     * @Param
     * @Author ljh
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<PageDTO>
     **/
    @Override
    public PageDTO queryPage(BaseDailyfirstCalcDTO baseDailyfirstCalcDTO) {
        if (!StringUtils.isEmpty(baseDailyfirstCalcDTO.getDeptCode())) {
          Map map = new HashMap();
          map.put("hospCode",baseDailyfirstCalcDTO.getHospCode());
            String chidldrenIds = TreeUtils.getChidldrenIds(baseSpecialCalcBO.getDeptTree(map),
                    baseDailyfirstCalcDTO.getDeptCode());
            String[] split = chidldrenIds.split(",");
            List<String> list = new ArrayList<>(Arrays.asList(split));
            baseDailyfirstCalcDTO.setIds(list);
        }
        baseDailyfirstCalcDTO.setIsValid("1");
        PageHelper.startPage(baseDailyfirstCalcDTO.getPageNo(), baseDailyfirstCalcDTO.getPageSize());

        // 查询所有
        List<BaseDailyfirstCalcDTO> baseBedDTOList = baseDailyfirstCalcDAO.queryAll(baseDailyfirstCalcDTO);

        // 返回分页结果
        return PageDTO.of(baseBedDTOList);
    }

    /**
     * @Method: queryDaily
     * @Description: 获取每日首次计费信息
     * @Param: [dailyfirstCalcDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/24 11:44
     * @Return: cn.hsa.module.base.bdc.dto.BaseDailyfirstCalcDTO
     **/
    @Override
    public List<BaseDailyfirstCalcDTO> queryDaily(BaseDailyfirstCalcDTO dailyfirstCalcDTO) {
        if (dailyfirstCalcDTO == null) {
            throw new AppException("参数为空");
        }
        return baseDailyfirstCalcDAO.queryDaily(dailyfirstCalcDTO);
    }

    public void cacheOperate(BaseDailyfirstCalcDTO baseDailyfirstCalcDTO, List<BaseDailyfirstCalcDTO> baseDailyfirstCalcDTOS, Boolean operation) {
        if (baseDailyfirstCalcDTO != null) {
            if (!ListUtils.isEmpty(baseDailyfirstCalcDTO.getIds())) {
                for (String id : baseDailyfirstCalcDTO.getIds()) {
                    String key = StringUtils.createKey("dailyFirstCalc", baseDailyfirstCalcDTO.getHospCode(), id);
                    if (redisUtils.hasKey(key)) {
                        redisUtils.del(key);
                    }
                    if (operation) {
                        redisUtils.set(key, baseDailyfirstCalcDTO);
                    }
                }
            }
        }
        if (!ListUtils.isEmpty(baseDailyfirstCalcDTOS)) {
            for (BaseDailyfirstCalcDTO dailyFirstCalc : baseDailyfirstCalcDTOS) {
                String key = StringUtils.createKey("dailyFirstCalc", dailyFirstCalc.getHospCode(), dailyFirstCalc.getId());
                if (redisUtils.hasKey(key)) {
                    redisUtils.del(key);
                }
                if (operation) {
                    redisUtils.set(key, dailyFirstCalc);
                }
            }
        }
    }
}
