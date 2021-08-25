package cn.hsa.module.interf.outpt.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.inpt.advancepay.dto.InptAdvancePayDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.interf.outpt.bo
 * @Class_name: OutptPrescribeBO
 * @Describe: 门诊处方接口逻辑实现层接口
 * @Author: zengfeng
 * @Date: 2021/5/10 16:00
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface OutptPrescribeBO {

    /**
     * 用户
     * @param map
     * @return
     */
    List<Map<String, Object>> updateHisInferface(Map map) throws Exception;

}
