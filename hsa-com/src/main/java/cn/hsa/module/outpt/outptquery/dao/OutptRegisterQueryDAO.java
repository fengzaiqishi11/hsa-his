package cn.hsa.module.outpt.outptquery.dao;

import cn.hsa.module.outpt.outptquery.dto.OutptRegisterDTO;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

;

/**
 * @Package_name: cn.hsa.module.outpt.outptquery.service
 * @class_name: OutptRegisterQueryService
 * @Description: 门诊挂号查询数据访问层
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/30 21:09
 * @Company: 创智和宇
 **/
public interface OutptRegisterQueryDAO {

    /**
     * @Method: queryPage()
     * @Description: 门诊挂号分页查询
     * @Param: outptRegisterDTO门诊挂号数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/10 14:38
     * @Return: outptRegisterDTO门诊挂号数据传输对象集合
     */
    List<OutptRegisterDTO> queryPage(OutptRegisterDTO outptRegisterDTO);

    /**
     * @Method: queryPage()
     * @Description: 门诊挂号总金额查询
     * @Param: outptRegisterDTO门诊挂号数据传输对象
     * @Author: liuliyun
     * @Date: 2021/7/10 14:38
     * @Return: LinkedHashMap<String, Object>
     */
    LinkedHashMap<String, Object> queryOutptRegisterSum(OutptRegisterDTO outptRegisterDTO);

}