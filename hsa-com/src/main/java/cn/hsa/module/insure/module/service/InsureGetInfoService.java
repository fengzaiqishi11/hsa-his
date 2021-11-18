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
     * @Method getSettleInfo
     * @Desrciption 医疗保障基金结算清单信息获取
     * @Param
     * [map]
     * @Author yuelong.chen
     * @Date   2021-08-19 10:06
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.insure.module.dto.InsureSettleInfoDTO>
     **/
    @PostMapping("/service/insure/insureSettleInfo/getSettleInfo")
    WrapperResponse<Map<String,Object>> insertSetlInfo(Map map);

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

    /**
     * @Method queryPage()
     * @Desrciption  分页查询医保住院费用传输数据
     * @Param insureIndividualCostDTO数据传输对象
     *
     * @Author fuhui
     * @Date   2020/11/5 10:58
     * @Return insureIndividualCostDTO分页数据传输对象
     **/
    WrapperResponse<PageDTO> queryPage(Map<String, Object> param);


    /**
     * @Method queryUnMatchPage
     * @Desrciption  查询没有匹配的费用数据集合
     * @Param
     *
     * @Author fuhui
     * @Date   2021/6/20 9:55
     * @Return
     **/
    WrapperResponse<PageDTO> queryUnMatchPage(Map<String, Object> param);

    /**
     * @Method saveInsureSettleInfo
     * @Desrciption  医疗保障基金结算清单信息： 保存
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/3 14:54
     * @Return
     **/
    WrapperResponse<Boolean> saveInsureSettleInfo(Map<String, Object> map);

    /**
     * @Method deleteSettleInfo
     * @Desrciption  重置医疗保障结算清单信息
     *           1.如果已经上传则不允许 清空重置
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/4 13:58
     * @Return
     **/
    WrapperResponse<Boolean> deleteSettleInfo(Map<String, Object> map);

    /**
     * @Method selectLoadingSettleInfo
     * @Desrciption  加载保存到数据库的数据信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/6 10:21
     * @Return
     **/
    WrapperResponse<Map<String, Object>> selectLoadingSettleInfo(Map<String, Object> map);

    /**
     * @Method queryPage
     * @Desrciption  查询结算清单左侧人员类别信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/9 15:29
     * @Return
     **/

    WrapperResponse<PageDTO> querySetlePage(Map<String, Object> map);
}
