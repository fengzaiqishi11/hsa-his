package cn.hsa.insure.module.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.bo.InsureGetInfoBO;
import cn.hsa.module.insure.module.dto.PayInfoDTO;
import cn.hsa.module.insure.module.service.InsureGetInfoService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@HsafRestPath("/service/insure/insureGetInfo")
@Service("insureGetInfoService_provider")
public class InsureGetInfoServiceImpl extends HsafService implements InsureGetInfoService {
    // 采集信息上传
    @Resource
    private InsureGetInfoBO insureGetInfoBO;

    /**
     * @Method getSettleInfo
     * @Desrciption
     * @Param
     * [map]
     * @Author zhangxuan
     * @Date   2021-04-10 11:34
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.insure.module.dto.InsureSettleInfoDTO>>
     **/
    @Override
    public WrapperResponse<Map> insertSettleInfo(Map map) {
        return WrapperResponse.success(insureGetInfoBO.insertSettleInfo(MapUtils.get(map,"insureSettleInfoDTO")));
    }


    /**
     * @Method getSettleInfo
     * @Desrciption
     * @Param
     * [map]
     * @Author yuelong.chen
     * @Date   2021-08-19 11:34
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.insure.module.dto.InsureSettleInfoDTO>>
     **/
    @Override
    public WrapperResponse<Map> getSettleInfo(Map map) {
        return WrapperResponse.success(insureGetInfoBO.getSettleInfo(MapUtils.get(map,"insureSettleInfoDTO")));
    }


    /**
     * @Method getInsureCost
     * @Desrciption
     * @Param
     * [map]
     * @Author zhangxuan
     * @Date   2021-04-11 22:52
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
     **/
    @Override
    public WrapperResponse<Boolean> insertInsureCost(Map map) {
        return WrapperResponse.success(insureGetInfoBO.insertInsureCost(MapUtils.get(map,"insureSettleInfoDTO")));
    }

    /**
     * @Method queryCost
     * @Desrciption
     * @Param
     * [map]
     * @Author zhangxuan
     * @Date   2021-04-13 17:21
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryCost(Map map){
        return WrapperResponse.success(insureGetInfoBO.queryCost(MapUtils.get(map,"insureSettleInfoDTO")));
    }

    /**
     * @Method queryUploadCost
     * @Desrciption
     * @Param
     * [map]
     * @Author zhangxuan
     * @Date   2021-04-13 17:21
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryUploadCost(Map map){
        return WrapperResponse.success(insureGetInfoBO.queryUploadCost(MapUtils.get(map,"insureSettleInfoDTO")));
    }

    /**
     * @Method queryVisit
     * @Desrciption
     * @Param
     * [map]
     * @Author zhangxuan
     * @Date   2021-04-13 19:06
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryVisit(Map map){
        return WrapperResponse.success(insureGetInfoBO.queryVisit(MapUtils.get(map,"insureSettleInfoDTO")));
    }

    /**
     * @Method queryInsureSettle
     * @Desrciption
     * @Param
     * [map]
     * @Author zhangxuan
     * @Date   2021-04-13 19:06
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryInsureSettle(Map map){
        return WrapperResponse.success(insureGetInfoBO.queryInsureSettle(MapUtils.get(map,"insureSettleInfoDTO")));
    }
    /**
     * @Method queryInsure
     * @Desrciption
     * @Param
     * [map]
     * @Author zhangxuan
     * @Date   2021-04-13 19:06
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryInsure(Map map){
        return WrapperResponse.success(insureGetInfoBO.queryInsure(MapUtils.get(map,"insureSettleInfoDTO")));
    }

    /**
     * @Method queryPage()
     * @Desrciption  分页查询医保住院费用传输数据
     * @Param insureIndividualCostDTO数据传输对象
     *
     * @Author fuhui
     * @Date   2020/11/5 10:58
     * @Return insureIndividualCostDTO分页数据传输对象
     **/
    @Override
    public WrapperResponse<PageDTO> queryPage(Map<String, Object> param) {
        return WrapperResponse.success(insureGetInfoBO.queryPage(MapUtils.get(param,"insureSettleInfoDTO")));
    }

    /**
     * @Method queryUnMatchPage
     * @Desrciption  查询没有匹配的费用数据集合
     * @Param
     *
     * @Author fuhui
     * @Date   2021/6/20 9:55
     * @Return
     **/
    @Override
    public WrapperResponse<PageDTO> queryUnMatchPage(Map<String, Object> param) {
        return WrapperResponse.success(insureGetInfoBO.queryUnMatchPage(MapUtils.get(param,"insureSettleInfoDTO")));
    }

}
