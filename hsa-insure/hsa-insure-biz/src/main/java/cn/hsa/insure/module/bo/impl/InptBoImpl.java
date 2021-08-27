package cn.hsa.insure.module.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.insure.util.Transpond;
import cn.hsa.insure.xiangtan.inpt.InptFunction;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.inpt.bo.InptBo;
import cn.hsa.module.insure.inpt.service.InsureUnifiedPayInptService;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualBasicDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualCostDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualVisitDAO;
import cn.hsa.module.insure.module.dto.*;
import cn.hsa.module.insure.module.service.InsureIndividualCostService;
import cn.hsa.module.insure.module.service.InsureIndividualVisitService;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.Constants;
import cn.hsa.util.ListUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.insure.module.bo.impl
 * @Class_name: InptBoImpl
 * @Describe(描述): 住院医保开放统一接口 Bo实现层
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/11/09 8:58
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
public class InptBoImpl extends HsafBO implements InptBo {

    @Resource
    private InptFunction inptFunction;

    @Resource
    private Transpond transpond;

    @Resource
    private InsureIndividualVisitDAO insureIndividualVisitDAO;

    @Resource
    private InsureIndividualCostDAO insureIndividualCostDAO;

    @Resource
    private InsureIndividualBasicDAO insureIndividualBasicDAO;

    @Resource
    private InsureUnifiedPayInptService insureUnifiedPayInptService;

    @Resource
    private InsureIndividualVisitService insureIndividualVisitService;

    @Resource
    private SysParameterService sysParameterService_consumer;

    @Resource
    private InsureIndividualCostService insureIndividualCostService_consumer;

    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;

    /**
     * @Method udapteCanleInptSettle
     * @Desrciption 医保取消出院结算
     * @param param
     * @Author 廖继广
     * @Date   2020/10/29 09:56
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean udapteCanleInptSettle(Map<String,Object> param) {
        String hospCode = (String) param.get("hospCode");
        String insureRegCode = (String) param.get("insureOrgCode");
        return transpond.to(hospCode,insureRegCode,Constant.FUNCTION.BIZH120107,param);
    }

    /**
     * @Method udapteCanleRemoteInptSettle
     * @Desrciption 医保取消异地出院结算
     * @param param
     * @Author 廖继广
     * @Date   2020/10/29 09:56
     * @Return java.util.Map
     **/
    @Override
    public Boolean udapteCanleRemoteInptSettle(Map<String,Object> param) {
        String hospCode = (String) param.get("hospCode");//医院编码
        String insureRegCode = (String) param.get("insureOrgCode");//医保机构编码
        return transpond.to(hospCode,insureRegCode,Constant.FUNCTION.REMOTE_BIZC131259,param);
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
    public Map<String, String> verifyAndCalculateCost(Map<String, Object> param) {
        String hospCode = (String) param.get("hospCode");
        String insureRegCode = (String) param.get("insureRegCode");
        String isRemote = (String) param.get("isRemote");
        if (Constants.BRLX.SNYDBR.equals(isRemote) || Constants.BRLX.SWYDBR.equals(isRemote)){
            //异地住院试算
            return transpond.to(hospCode,insureRegCode, Constant.FUNCTION.REMOTE_BIZC131255,param);
        }else{
            //本地住院试算
            return transpond.to(hospCode,insureRegCode, Constant.FUNCTION.FC_3003,param);
        }
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
        String hospCode = (String) param.get("hospCode");
        String insureRegCode = (String) param.get("insureRegCode");
        String isRemote = (String) param.get("isRemote");
        if (Constants.BRLX.SWYDBR.equals(isRemote) || Constants.BRLX.SNYDBR.equals(isRemote)){
            //异地医保
            return transpond.to(hospCode,insureRegCode,Constant.FUNCTION.FC_3020,param);
        }else{
            //本地医保
            return transpond.to(hospCode,insureRegCode,Constant.FUNCTION.FC_3011,param);
        }
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
        String hospCode = (String) param.get("hospCode");//医院编码
        String visitId = (String) param.get("visitId");//就诊id
        String userName = (String) param.get("userName"); // 经办人
        String code = (String) param.get("code"); // 操作人编码
        String medicalRegNo = MapUtils.get(param,"medicalRegNo");
        InptVisitDTO inptVisitDTO = (InptVisitDTO) param.get("inptVisitDTO");
        Map<String, Object> isInsureUnifiedMap = new HashMap<>();
        isInsureUnifiedMap.put("hospCode", hospCode);
        isInsureUnifiedMap.put("code", "UNIFIED_PAY");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(isInsureUnifiedMap).getData();
        //获取医保就诊信息
        InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
        insureIndividualVisitDTO.setHospCode(hospCode);//医院编码
        insureIndividualVisitDTO.setVisitId(visitId);//就诊id
        insureIndividualVisitDTO.setMedicalRegNo(medicalRegNo);
        insureIndividualVisitDTO.setCode(code);
        InsureIndividualBasicDTO insureIndividualBasicDTO = new InsureIndividualBasicDTO();//查询条件
        List<InsureIndividualCostDTO> individualCostDTOList = new ArrayList<>();
        Map<String, Object> insureVisitParam = new HashMap<String, Object>();
        if(sysParameterDTO ==null || !"1".equals(sysParameterDTO.getValue())){
            List<InsureIndividualVisitDTO> insureIndividualVisitDTOS = insureIndividualVisitDAO.findByCondition(insureIndividualVisitDTO);
            if (insureIndividualVisitDTOS == null || insureIndividualVisitDTOS.size() > 1 || insureIndividualVisitDTOS.size() == 0) {
                throw new AppException("未获取到医保就诊信息。");
            }
            insureIndividualVisitDTO = insureIndividualVisitDTOS.get(0);
            //获取医保个人基本信息
            insureIndividualBasicDTO.setId(insureIndividualVisitDTO.getMibId());//个人基本信息
            insureIndividualBasicDTO.setHospCode(hospCode);//医院编码
            List<InsureIndividualBasicDTO>  insureIndividualBasicDTOList = insureIndividualBasicDAO.queryAll(insureIndividualBasicDTO);
            if (insureIndividualBasicDTOList == null || insureIndividualBasicDTOList.isEmpty()){
                throw new AppException("未获取到医保个人基本信息。");
            }
            insureIndividualBasicDTO = insureIndividualBasicDTOList.get(0);
        } else{
            insureVisitParam.put("hospCode", hospCode);//医院编码
            insureVisitParam.put("insureIndividualVisitDTO", insureIndividualVisitDTO);
            insureIndividualVisitDTO = insureIndividualVisitService.selectInsureInfo(insureVisitParam).getData();
            insureIndividualVisitDTO.setInsureIsTransmit("1"); //删除 只删除已经上传到医保的数据
            individualCostDTOList = insureIndividualCostService_consumer.selectInsureIndividualCost(insureVisitParam).getData();
        }

        // 根据医保机构编码查询医保配置信息
        InsureConfigurationDTO configDTO = new InsureConfigurationDTO();
        configDTO.setHospCode(hospCode); //医院编码
        configDTO.setRegCode(insureIndividualVisitDTO.getInsureRegCode()); // 医保注册编码
        configDTO.setIsValid(Constants.SF.S); // 是否有效
        List<InsureConfigurationDTO> configurationDTOList = insureConfigurationDAO.findByCondition(configDTO);
        if (ListUtils.isEmpty(configurationDTOList)) {
            throw new RuntimeException("未找到医保机构，请先配置医保信息！");
        }
        InsureConfigurationDTO insureConfigurationDTO = configurationDTOList.get(0);
        // 获取该医保配置是否走统一支付平台，1走，0/null不走
        String isUnifiedPay = insureConfigurationDTO.getIsUnifiedPay();
        /**
         * 通过获取系统参数来判断 是走医保统一支付平台 还是调用自己的的医保接口
         * 1.如果没有上传到医保的费用数据则不调用医保的费用撤销接口
         */
//        if(sysParameterDTO !=null && Constants.SF.S.equals(sysParameterDTO.getValue())) {
        if(StringUtils.isNotEmpty(isUnifiedPay) && "1".equals(isUnifiedPay)) {
               Map<String, Object> insureUnifiedMap = new HashMap<>();
               insureUnifiedMap.put("individualCostDTOList",individualCostDTOList);
               insureUnifiedMap.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
               insureUnifiedMap.put("hospCode",hospCode);
               boolean data = insureUnifiedPayInptService.UP_2302(insureUnifiedMap).getData();
               if(data){
                   //删除本次患者取消医保费用信息
                   insureIndividualCostService_consumer.delInsureCost(insureVisitParam).getData();
               }

        } else{
            String insureRegCode = insureIndividualVisitDTO.getInsureRegCode();//医保注册编码
            insureIndividualVisitDTO.setCrteName(inptVisitDTO.getCrteName());
            //封装请求参数
            Map<String,Object> httpParam = new HashMap<String,Object>();
            httpParam.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
            httpParam.put("insureIndividualBasicDTO",insureIndividualBasicDTO);
            httpParam.put("inptVisitDTO",inptVisitDTO);
            /**
             * 长沙医保需要用到的参数信息
             */
            Map<String, Object> map = StringUtils.getCommonParam(Constant.ChangSha.OUTPT.CS_2320,hospCode,code);
            String commonRequestStr = map.get("commonParams").toString();  // 公共入参
            httpParam.put("commonRequestStr",commonRequestStr); // 请求公共入参方法
            httpParam.put("visitNo",insureIndividualVisitDTO.getVisitNo()); // 住院流水号(门诊流水号)
            httpParam.put("transmitNo",""); // 被撤销交易流水号 如果只撤销一部分，则此值不为空
            httpParam.put("crteName",userName);  // 经办人
            httpParam.put("code",code);  // 经办人
            if (Constants.BRLX.SWYDBR.equals(inptVisitDTO.getPatientCode()) || Constants.BRLX.SNYDBR.equals(inptVisitDTO.getPatientCode())){
                //异地
                transpond.to(hospCode,insureRegCode,Constant.FUNCTION.REMOTE_BIZC131274,httpParam);
            }else{
                //本地
                transpond.to(hospCode,insureRegCode,Constant.FUNCTION.FC_3010,httpParam);
            }
            //删除本次患者取消医保费用信息
            insureIndividualCostDAO.delInsureCost(insureIndividualVisitDTO);
        }
        return WrapperResponse.success("删除成功。",null);
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
        return transpond.to(MapUtils.get(param,"hospCode"),MapUtils.get(param,"insureRegCode"),Constant.HuNanSheng.INPT.Remote_BIZC200017,param);
    }

    /**
     * @Method insertInptRegister
     * @Desrciption 入院登记
     * @params [insureInptRegisterDTO]
     * @Author 廖继广
     * @Date 2020/10/29 11:41
     * @Return java.util.Map
     **/
    @Override
    public Map<String, Object> insertInptRegister(InsureInptRegisterDTO insureInptRegisterDTO) {
        return transpond.to(insureInptRegisterDTO.getHospCode(),insureInptRegisterDTO.getInsureOrgCode(),Constant.FUNCTION.BIZH120103,insureInptRegisterDTO);
    }

    /**
     * @Method deteleInptRegister
     * @Desrciption 修改入院登记
     * @params [insureInptOutFeeDTO]
     * @Author 廖继广
     * @Date 2020/10/29 11:41
     * @Return Boolean
     **/
    @Override
    public Boolean updateInptRegister(InsureInptRegisterDTO insureInptRegisterDTO) {
        return transpond.to(insureInptRegisterDTO.getHospCode(),insureInptRegisterDTO.getInsureOrgCode(),Constant.FUNCTION.BIZH120104,insureInptRegisterDTO);
    }

    /**
     * @Method deteleInptRegister
     * @Desrciption 取消入院登记
     * @params [insureInptOutFeeDTO]
     * @Author 廖继广
     * @Date 2020/10/29 11:41
     * @Return java.util.Map
     **/
    @Override
    public Boolean deteleInptRegister(InsureInptOutFeeDTO insureInptOutFeeDTO) {
        return transpond.to(insureInptOutFeeDTO.getHospCode(),insureInptOutFeeDTO.getInsureOrgCode(),Constant.FUNCTION.BIZH120109,insureInptOutFeeDTO);
    }

    /**
     * @Method insertRemoteInptRegister
     * @Desrciption 异地医保入院登记
     * @params [insureRemoteInptRegisterDTO]
     * @Author 廖继广
     * @Date 2020/10/30 11:41
     * @Return java.util.Map
     **/
    @Override
    public Map<String,Object> insertRemoteInptRegister(InsureRemoteInptRegisterDTO insureRemoteInptRegisterDTO) {
        return transpond.to(insureRemoteInptRegisterDTO.getHospCode(),insureRemoteInptRegisterDTO.getInsureOrgCode(),Constant.HuNanSheng.INPT.REMOTE_BIZC131204,insureRemoteInptRegisterDTO);
    }

    /**
     * @Method deteleRemoteInptRegister
     * @Desrciption 取消异地医保入院登记
     * @params [insureRemoteInptRegisterDTO]
     * @Author 廖继广
     * @Date 2020/10/30 14:46
     * @Return Boolean
     **/
    @Override
    public Boolean deteleRemoteInptRegister(InsureRemoteInptRegisterDTO insureRemoteInptRegisterDTO) {
        return transpond.to(insureRemoteInptRegisterDTO.getHospCode(),insureRemoteInptRegisterDTO.getInsureOrgCode(),Constant.HuNanSheng.INPT.REMOTE_BIZC131206,insureRemoteInptRegisterDTO);
    }
}
