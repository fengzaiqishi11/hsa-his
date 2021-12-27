package cn.hsa.module.insure.module.dao;

import cn.hsa.module.outpt.fees.entity.OutptInsurePayDO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.module.dao
 * @class_name: InsureUnifiedUniversityDAO
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/12/15 11:43
 * @Company: 创智和宇
 **/
@Component
public interface InsureUnifiedUniversityDAO {

    /**
     * @Method selectOutptUniversityFee
     * @Desrciption  查询已经结算的医保费用数据
     * @Param
     *
     * @Author fuhui
     * @Date   2021/12/15 16:47
     * @Return
    **/
    List<Map<String, Object>> selectOutptUniversityFee(Map<String, Object> insureCostParam);

    /**
     * @Method handlerOutptInsurePay
     * @Desrciption  门诊医保结算完成以后更新门诊医保支付明细表
     * @Param
     *
     * @Author fuhui
     * @Date   2021/12/15 14:29
     * @Return
     **/
    void insertOutptInsurePay(OutptInsurePayDO outptInsurePayDO);

    /**
     * @Method selectUnMatchFee
     * @Desrciption  查询出未匹配的费用项目
     * @Param
     *
     * @Author fuhui
     * @Date   2021/12/15 16:55
     * @Return
    **/
    List<String> selectUnMatchFee(Map<String, Object> insureCostParam);

    /**
     * @Method selectSumPrice
     * @Desrciption  查询门诊本次结算总费用
     * @Param
     *
     * @Author fuhui
     * @Date   2021/12/15 17:31
     * @Return
    **/
    BigDecimal selectSumPrice(Map<String, Object> map);

    /**
     * @Method deleteInsureCostFee
     * @Desrciption  删除医保费用信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/12/21 15:48
     * @Return
    **/
    void deleteInsureCostFee(Map<String, Object> map);
}
