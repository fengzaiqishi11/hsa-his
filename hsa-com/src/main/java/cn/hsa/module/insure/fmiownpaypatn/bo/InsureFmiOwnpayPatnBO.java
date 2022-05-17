package cn.hsa.module.insure.fmiownpaypatn.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.insure.module.dto.InsureSettleInfoDTO;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.module.bo
 * @Class_name:: InsureOwnExpenseBO
 * @Description: 信息采集上传
 * @Author: qiang.fan
 * @Date: 2022-04-06 20:04
 * @Company: 创智和宇信息技术股份有限公司
 */
public interface InsureFmiOwnpayPatnBO {

    /**
     * @Method queryVisit
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-13 19:06
     * @Return cn.hsa.base.PageDTO
     **/
    PageDTO queryVisit(InsureSettleInfoDTO insureSettleInfoDTO);


    /**
     * @Method queryPage()
     * @Desrciption  分页查询医保住院费用传输数据
     * @Param insureIndividualCostDTO数据传输对象
     *
     * @Author fuhui
     * @Date   2020/11/5 10:58
     * @Return insureIndividualCostDTO分页数据传输对象
     **/
    PageDTO queryMatchFeePage(InsureSettleInfoDTO insureSettleInfoDTO);


    /**
     * @Method queryUnMatchPage
     * @Desrciption  查询没有匹配的费用数据集合
     * @Param
     *
     * @Author fuhui
     * @Date   2021/6/20 9:55
     * @Return
     **/
    PageDTO queryUnMatchPage(InsureSettleInfoDTO insureSettleInfoDTO);


    Boolean insertInsureFmiOwnPayPatnCost(InsureSettleInfoDTO insureSettleInfoDTO);

    Map<String, Object> queryFmiOwnPayPatnReconciliation(InsureSettleInfoDTO insureSettleInfoDTO);

    Map<String, Object> queryFmiOwnPayPatnReconciliationInfo(InsureSettleInfoDTO insureSettleInfoDTO);

    Boolean insertInsureMdtrtAndDiag(InsureSettleInfoDTO insureSettleInfoDTO);

    Boolean insertInsureFinish(InsureSettleInfoDTO insureSettleInfoDTO);

    /**
     * @Method queryInsureCost
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-11 22:53
     * @Return java.util.Map
     **/
    Boolean insertInsureCost(InsureSettleInfoDTO insureSettleInfoDTO);

}
