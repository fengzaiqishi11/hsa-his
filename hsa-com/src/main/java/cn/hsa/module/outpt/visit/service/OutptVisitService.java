package cn.hsa.module.outpt.visit.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.OutptCostDTO;
import cn.hsa.module.outpt.fees.dto.OutptSettleDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.visit.service
 * @Class_name: OutptVisitService
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/9/3 16:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-outpt")
public interface OutptVisitService {

    /**
     * @Method queryVistRecords
     * @Desrciption 通过档案id分页查询就诊记录
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/9/3 16:15
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @PostMapping("/service/outpt/visit/queryVisitRecords")
    WrapperResponse<PageDTO> queryVisitRecords(Map map);

    /**
     * @Method updateTranInCode
     * @Desrciption 更新转入院代码
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/9/25 9:03
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    WrapperResponse<Boolean> updateTranInCode(Map map);

    /**
     * @Menthod queryByVisitID
     * @Desrciption 根据ID查询门诊患者信息
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/12/17 21:54
     * @Return cn.hsa.module.outpt.visit.dto.OutptVisitDTO
     */
    @GetMapping("/service/outpt/visit/queryByVisitID")
    OutptVisitDTO queryByVisitID(Map<String,String> param);

    /**
     * @Method updateOutptVisit
     * @Desrciption  走统一支付平台时，登记挂号成功以后，修改病人类型
     * @Param outptVisitMap
     *
     * @Author fuhui
     * @Date   2021/3/8 15:25
     * @Return
     **/
    WrapperResponse<Boolean> updateOutptVisit(Map<String, Object> outptVisitMap);

    /**
     * @Method selectOutptVisitById
     * @Desrciption  根据就诊id查询门诊患者
     * @Param
     *
     * @Author fuhui
     * @Date   2021/12/13 16:27
     * @Return
    **/
    WrapperResponse<OutptVisitDTO> selectOutptVisitById(Map<String, Object> map);

    /**
     * @Method selectOutptSettleById
     * @Desrciption   根据就诊id查询门诊结算信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/12/13 16:34
     * @Return
    **/
    WrapperResponse<OutptSettleDTO> selectOutptSettleById(Map<String, Object> map);

    /**
     * @Method updateOutptAcctPay
     * @Desrciption  修改
     * @Param
     *
     * @Author fuhui
     * @Date   2021/12/15 11:16
     * @Return
    **/
    WrapperResponse<Boolean> updateOutptAcctPay(Map<String,Object> map);


    List<OutptVisitDTO> queryOutptVisitSelfFeePatient(Map<String, String> param);

    List<OutptCostDTO> queryOutptCostByvisitIds(Map<String, Object> reqMap);

    WrapperResponse<Boolean> updateUplod(Map<String, Object> map);


    WrapperResponse<Boolean>  updateOutptVisitUploadFlag(Map<String, Object> map);

    /**
      * @method selectOutptVisitByCertNo
      * @author wang'qiao
      * @date 2022/6/21 21:29
      *	@description  根据证件类型和证件号码 查询信息
      * @param  map
      * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.outpt.visit.dto.OutptVisitDTO>
      *
     **/
    WrapperResponse<OutptVisitDTO> selectOutptVisitByCertNo(Map<String, Object> map);
}
