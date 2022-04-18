package cn.hsa.insure.fmiownpaypatn.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.fmiownpaypatn.bo.InsureFmiOwnpayPatnBO;
import cn.hsa.module.insure.fmiownpaypatn.service.InsureFmiOwnpayPatnService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@HsafRestPath("/service/insure/insureFmiOwnpayPatn")
@Service("insureFmiOwnpayPatnService_provider")
public class InsureFmiOwnpayPatnServiceImpl extends HsafService implements InsureFmiOwnpayPatnService {
    // 采集信息上传
    @Resource
    private InsureFmiOwnpayPatnBO insureFmiOwnpayPatnBO;


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
        return WrapperResponse.success(insureFmiOwnpayPatnBO.queryVisit(MapUtils.get(map,"insureSettleInfoDTO")));
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
    public WrapperResponse<PageDTO> queryMatchFeePage(Map<String, Object> param) {
        return WrapperResponse.success(insureFmiOwnpayPatnBO.queryMatchFeePage(MapUtils.get(param,"insureSettleInfoDTO")));
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
        return WrapperResponse.success(insureFmiOwnpayPatnBO.queryUnMatchPage(MapUtils.get(param,"insureSettleInfoDTO")));
    }

    @Override
    public WrapperResponse<Boolean> insertInsureFmiOwnPayPatnCost(Map map) {
        return WrapperResponse.success(insureFmiOwnpayPatnBO.insertInsureFmiOwnPayPatnCost(MapUtils.get(map,"insureSettleInfoDTO")));
    }

    @Override
    public WrapperResponse queryFmiOwnPayPatnReconciliation(Map map) {
        return WrapperResponse.success(insureFmiOwnpayPatnBO.queryFmiOwnPayPatnReconciliation(MapUtils.get(map,"insureSettleInfoDTO")));
    }

    @Override
    public WrapperResponse queryFmiOwnPayPatnReconciliationInfo(Map map) {
        return WrapperResponse.success(insureFmiOwnpayPatnBO.queryFmiOwnPayPatnReconciliationInfo(MapUtils.get(map,"insureSettleInfoDTO")));
    }


}
