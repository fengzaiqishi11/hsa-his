package cn.hsa.module.insure.module.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.PayInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.module.service
 * @Class_name:: InsureGetInfoService
 * @Description: 信息采集上传
 * @Author: zhangxuan
 * @Date: 2021-04-09 19:51 
 * @Company: 创智和宇信息技术股份有限公司
 */
@FeignClient(value = "hsa-insure")
public interface InsureGetInfoService {

    /**
     * @Method getSettleInfo
     * @Desrciption 医疗保障基金结算清单信息获取
     * @Param
     * [map]
     * @Author zhangxuan
     * @Date   2021-04-10 10:06
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.insure.module.dto.InsureSettleInfoDTO>
     **/
    @PostMapping("/service/insure/insureSettleInfo/insertSettleInfo")
    WrapperResponse<Map> insertSettleInfo(Map map);

    /**
     * @Method getInsureCost
     * @Desrciption
     * @Param
     * [map]
     * @Author zhangxuan
     * @Date   2021-04-11 22:52
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
     **/
    @PostMapping("/service/insure/insureSettleInfo/getInsureCost")
    WrapperResponse<Boolean> insertInsureCost(Map map);

    /**
     * @Method queryCost
     * @Desrciption
     * @Param
     * [map]
     * @Author zhangxuan
     * @Date   2021-04-13 17:20
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @PostMapping("/service/insure/insureSettleInfo/queryCost")
    WrapperResponse<PageDTO> queryCost(Map map);

    /**
     * @Method queryUploadCost
     * @Desrciption
     * @Param
     * [map]
     * @Author zhangxuan
     * @Date   2021-04-13 17:20
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @PostMapping("/service/insure/insureSettleInfo/queryUploadCost")
    WrapperResponse<PageDTO> queryUploadCost(Map map);

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
     * @Method queryInsureSettle
     * @Desrciption
     * @Param
     * [map]
     * @Author zhangxuan
     * @Date   2021-04-13 19:05
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @PostMapping("/service/insure/insureSettleInfo/queryInsureSettle")
    WrapperResponse<PageDTO> queryInsureSettle(Map map);

    /**
     * @Method queryInsure
     * @Desrciption
     * @Param
     * [map]
     * @Author zhangxuan
     * @Date   2021-04-13 19:05
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @PostMapping("/service/insure/insureSettleInfo/queryInsure")
    WrapperResponse<PageDTO> queryInsure(Map map);

}
