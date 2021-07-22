package cn.hsa.outpt.outptregisterquery.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.outpt.outptquery.bo.OutptRegisterQueryBO;
import cn.hsa.module.outpt.outptquery.dao.OutptRegisterQueryDAO;
import cn.hsa.module.outpt.outptquery.dto.OutptRegisterDTO;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Package_name: cn.hsa.outpt.outptregisterquery
 * @class_name: OutptRegisterQueryBOImpl
 * @Description: 门诊挂号查询业务逻辑实现层
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/8/10 14:58
 * @Company: 创智和宇
 **/
@Component
@Slf4j
public class OutptRegisterQueryBOImpl extends HsafBO implements OutptRegisterQueryBO {
    /**
     * 门诊挂号数据访问层
     */
    @Resource
    private OutptRegisterQueryDAO outptRegisterQueryDAO;

    /**
     * @Method: queryPage()
     * @Description: 门诊挂号分页查询
     * @Param: outptRegisterDTO门诊挂号数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/10 14:38
     * @Return: outptRegisterDTO门诊挂号数据传输对象集合
     */
    @Override
    public PageDTO queryPage(OutptRegisterDTO outptRegisterDTO) {
        PageHelper.startPage(outptRegisterDTO.getPageNo(), outptRegisterDTO.getPageSize());
        List<OutptRegisterDTO> outptList = outptRegisterQueryDAO.queryPage(outptRegisterDTO);
        // 查询门诊挂号总金额 liuliyun 20210710
        LinkedHashMap<String, Object> sumMap = new LinkedHashMap<>();
        sumMap = outptRegisterQueryDAO.queryOutptRegisterSum(outptRegisterDTO);
        if (sumMap == null){
            sumMap = new LinkedHashMap<>();
            sumMap.put("realityPriceSum",0.00);
        }
        return PageDTO.of(outptList,sumMap);
    }
}
