package cn.hsa.insure.module.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.inpt.bo.InptBO;
import cn.hsa.module.insure.inpt.service.InptService;
import cn.hsa.util.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.module.service.impl
 * @Class_name: InptServiceImpl
 * @Describe(描述): 住院医保开放统一接口 service实现层
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/11/09 9:09
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/insure/inpt")
@Service("inptService_provider")
public class InptServiceImpl extends HsafService implements InptService {
    @Resource
    private InptBO inptBo;

    /**
     * @Method udapteCanleInptSettle
     * @Desrciption 医保取消出院结算
     * @param param
     * @Author 廖继广
     * @Date   2020/10/29 09:56
     * @Return java.util.Map
     **/
    @Override
    public Boolean udapteCanleInptSettle(Map<String,Object> param) {
        return inptBo.udapteCanleInptSettle(param);
    }

    /**
     * @Method udapteCanleInptSettle
     * @Desrciption 医保取消异地出院结算
     * @param param
     * @Author 廖继广
     * @Date   2020/10/29 09:56
     * @Return java.util.Map
     **/
    @Override
    public Boolean udapteCanleRemoteInptSettle(Map<String,Object> param) {
        return inptBo.udapteCanleRemoteInptSettle(param);
    }

    @Override
    public Map<String, Object> insertInptRegister(Map param) {
        return inptBo.insertInptRegister(MapUtils.get(param,"insureInptRegisterDTO"));
    }

    /**
     * @Method updateInptRegister
     * @Desrciption 修改入院登记
     * @params param
     * @Author 廖继广
     * @Date 2020/10/29 11:41
     * @Return Boolean
     **/
    @Override
    public Boolean updateInptRegister(Map param) {
        return inptBo.updateInptRegister(MapUtils.get(param,"insureInptRegisterDTO"));
    }

    /**
     * @Method deteleInptRegister
     * @Desrciption 删除医保入院登记
     * @param param
     * @Author 廖继广
     * @Date   2020/10/29 09:56
     * @Return java.util.Map
     **/
    @Override
    public Boolean deteleInptRegister(Map param) {
        return inptBo.deteleInptRegister(MapUtils.get(param,"insureInptOutFeeDTO"));
    }

    /**
     * @Method insertRemoteInptRegister
     * @Desrciption 异地医保入院登记
     * @param param
     * @Author 廖继广
     * @Date   2020/10/30 09:56
     * @Return java.util.Map
     **/
    @Override
    public Map<String,Object> insertRemoteInptRegister(Map param) {
        return inptBo.insertRemoteInptRegister(MapUtils.get(param,"insureRemoteInptRegisterDTO"));
    }

    /**
     * @Method deteleRemoteInptRegister
     * @Desrciption 取消异地医保入院登记
     * @param param
     * @Author 廖继广
     * @Date   2020/10/30 09:56
     * @Return Boolean
     **/
    @Override
    public Boolean deteleRemoteInptRegister(Map param) {
        return inptBo.deteleRemoteInptRegister(MapUtils.get(param,"insureRemoteInptRegisterDTO"));
    }

    /**
     * @Menthod verifyAndCalculateCost
     * @Desrciption 校验并计算费用【住院】
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/30 15:30
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public Map<String,String> verifyAndCalculateCost(Map<String,Object> param){
        return inptBo.verifyAndCalculateCost(param);
    }

    /**
     * @Menthod inptSettlement
     * @Desrciption 住院结算
     * @param param 请求参数 必传值：hospCode:医院编码、visitId:就诊id、insureRegCode:医保编码
     * @Author Ou·Mr
     * @Date 2020/12/3 16:32
     * @Return java.util.Map<java.lang.String,java.lang.String>
     */
    @Override
    public Map<String, String> inptSettlement(Map<String, Object> param) {
        return inptBo.inptSettlement(param);
    }

    /**
     * @Menthod delInptCostTransmit
     * @Desrciption 删除医保住院已传输费用信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/12/22 16:36
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse delInptCostTransmit(Map<String, Object> param) {
        return inptBo.delInptCostTransmit(param);
    }

    /**
     * @Menthod getRemoteDiseaseInfo
     * @Desrciption 获取异地疾病信息
     * @param param 请求参数 必传值：hospCode:医院编码、insureRegCode:医保编码、
     * @Author Ljg
     * @Date 2021/4/13 15:32
     * @Return java.util.Map<java.lang.String,java.lang.String>
     */
    @Override
    public List<Map<String, Object>> getRemoteDiseaseInfo(Map<String, Object> param) {
        return inptBo.getRemoteDiseaseInfo(param);
    }
}
