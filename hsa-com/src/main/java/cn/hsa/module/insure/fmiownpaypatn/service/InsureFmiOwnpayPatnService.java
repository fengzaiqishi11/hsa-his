package cn.hsa.module.insure.fmiownpaypatn.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.InsureSettleInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.module.service
 * @Class_name:: InsureOwnExpenseService
 * @Description: 自费病人信息采集上传
 * @Author: qiang.fan
 * @Date: 2022-04-06 19:51
 * @Company: 创智和宇信息技术股份有限公司
 */
@FeignClient(value = "hsa-insure")
public interface InsureFmiOwnpayPatnService {

    /**
     * @Method queryVisit
     * @Desrciption
     * @Param
     * [map]
     * @Author zhangxuan
     * @Date   2021-04-13 19:05
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @PostMapping("/service/insure/insureSettleInfo/queryVisit")
    WrapperResponse<PageDTO> queryVisit(Map map);

    /**
     * @Method queryPage()
     * @Desrciption  分页查询医保住院费用传输数据
     * @Param insureIndividualCostDTO数据传输对象
     *
     * @Author fuhui
     * @Date   2020/11/5 10:58
     * @Return insureIndividualCostDTO分页数据传输对象
     **/
    @PostMapping("/service/insure/insureSettleInfo/queryMatchFeePage")
    WrapperResponse<PageDTO> queryMatchFeePage(Map<String, Object> param);

    /**
     * @Method queryUnMatchPage
     * @Desrciption  查询没有匹配的费用数据集合
     * @Param
     *
     * @Author fuhui
     * @Date   2021/6/20 9:55
     * @Return
     **/
    @PostMapping("/service/insure/insureSettleInfo/queryUnMatchPage")
    WrapperResponse<PageDTO> queryUnMatchPage(Map<String, Object> param);

    @PostMapping("/service/insure/insureSettleInfo/insertInsureFmiOwnPayPatnCost")
    WrapperResponse<Boolean> insertInsureFmiOwnPayPatnCost(Map map);

    @PostMapping("/service/insure/insureSettleInfo/queryFmiOwnPayPatnReconciliation")
    WrapperResponse queryFmiOwnPayPatnReconciliation(Map map);

    @PostMapping("/service/insure/insureSettleInfo/queryFmiOwnPayPatnReconciliationInfo")
    WrapperResponse queryFmiOwnPayPatnReconciliationInfo(Map map);

    @GetMapping("/service/insure/insureSettleInfo/queryInsureOutptMedicTreatMent")
    WrapperResponse<PageDTO> queryInsureOutptMedicTreatMent(Map<String, Object> param);
    @PostMapping("/service/insure/insureSettleInfo/queryFmiOwnPayInfoDetail")
    WrapperResponse queryFmiOwnPayInfoDetail(Map map);

    @PostMapping("/service/insure/insureSettleInfo/queryFmiOwnPayPatnFeeListDetail")
    WrapperResponse queryFmiOwnPayPatnFeeListDetail(Map map);

    @PostMapping("/service/insure/insureSettleInfo/queryFmiOwnPayDiseListDetail")
    WrapperResponse queryFmiOwnPayDiseListDetail(Map map);
    @PostMapping("/service/insure/insureSettleInfo/insertInsureMdtrtAndDiag")
    WrapperResponse insertInsureMdtrtAndDiag(Map map);

    @PostMapping("/service/insure/insureSettleInfo/insertOutptMedicTreatMent")
    WrapperResponse insertOutptMedicTreatMent(Map<String, Object> param);
    @PostMapping("/service/insure/insureSettleInfo/deleteOutptMedicTreatMent")
    WrapperResponse deleteOutptMedicTreatMent(Map<String, Object> param);
    @PostMapping("/service/insure/insureSettleInfo/insertInsureFinish")
    WrapperResponse insertInsureFinish(Map map);


    WrapperResponse<Boolean> deleteInsureFmiOwnPayPatnCost(Map map);

    WrapperResponse<PageDTO> queryFeeInfoDetailPage(Map<String, Object> param);
}
