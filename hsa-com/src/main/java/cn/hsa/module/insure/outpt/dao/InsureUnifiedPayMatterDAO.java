package cn.hsa.module.insure.outpt.dao;

import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.unifiedpay.bo.impl
 * @Class_name: InsureUnifiedPayMatterBOImpl
 * @Describe: 统一支付平台---事前事中分析
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-02-24 15:07
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface InsureUnifiedPayMatterDAO {
    /**
     * @Menthod: getDiagnoseListByVisitId
     * @Desrciption: 查询就诊人的诊断列表
     * @Param: inptVistDTO[visitId、hospCode]
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-02-24 20:44
     * @Return:
     **/
    List<Map<String, Object>> getDiagnoseListByVisitId(InptVisitDTO inptVistDTO);

    /**
     * @Menthod: getOrderListByVisitId
     * @Desrciption: 查询就诊人的处方列表
     * @Param:  inptVistDTO[visitId、hospCode]
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-02-24 20:44
     * @Return:
     **/
    List<Map<String, Object>> getOrderListByVisitId(InptVisitDTO inptVistDTO);

    InptVisitDTO getInptVisitById(InptVisitDTO inptVistDTO);
}
