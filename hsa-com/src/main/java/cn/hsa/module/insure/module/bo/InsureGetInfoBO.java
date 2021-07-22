package cn.hsa.module.insure.module.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.insure.module.dto.InsureSettleInfoDTO;
import cn.hsa.module.insure.module.dto.PayInfoDTO;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.module.bo
 * @Class_name:: InsureGetInfoBO
 * @Description: 信息采集上传
 * @Author: zhangxuan
 * @Date: 2021-04-09 20:04
 * @Company: 创智和宇信息技术股份有限公司
 */
public interface InsureGetInfoBO {

    /**
     * @Method getSettleInfo
     * @Desrciption 医疗保障基金结算清单信息上传
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-10 11:35
     * @Return java.util.List<cn.hsa.module.insure.module.dto.InsureSettleInfoDTO>
     **/
    Map insertSettleInfo(InsureSettleInfoDTO insureSettleInfoDTO);

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

    /**
     * @Method queryCost
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-13 17:22
     * @Return cn.hsa.base.PageDTO
     **/
    PageDTO queryCost(InsureSettleInfoDTO insureSettleInfoDTO);

    /**
     * @Method queryUploadCost
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-13 17:22
     * @Return cn.hsa.base.PageDTO
     **/
    PageDTO queryUploadCost(InsureSettleInfoDTO insureSettleInfoDTO);

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
     * @Method queryInsureSettle
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-13 19:06
     * @Return cn.hsa.base.PageDTO
     **/
    PageDTO queryInsureSettle(InsureSettleInfoDTO insureSettleInfoDTO);

    /**
     * @Method queryInsure
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-13 19:06
     * @Return cn.hsa.base.PageDTO
     **/
    PageDTO queryInsure(InsureSettleInfoDTO insureSettleInfoDTO);

}
