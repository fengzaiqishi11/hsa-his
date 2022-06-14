package cn.hsa.insure.module.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.insure.util.Transpond;
import cn.hsa.insure.xiangtan.drg.DrgFunction;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;

import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.drg.bo.InsureAdviceEntryBO;
import cn.hsa.module.insure.drg.dao.InsureAdviceEntryDAO;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;

import cn.hsa.module.insure.module.dto.DoctorAdviceDTO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;

import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;

import cn.hsa.module.sys.parameter.service.SysParameterService;

import cn.hsa.util.*;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.module.bo.impl
 * @class_name: InsureAdviceEntryBOImpl
 * @project_name: hsa-his
 * @Description: 医嘱录入上传业务实现层
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date:2020/12/10 15:22
 * @Company: 创智和宇
 **/
@Component
@Slf4j
public class InsureAdviceEntryBOImpl extends HsafBO implements InsureAdviceEntryBO {
    @Resource
    private Transpond transpond;

    @Resource
    private DrgFunction drgFunction;

    @Resource
    private InsureAdviceEntryDAO insureAdviceEntryDAO;

    @Resource
    private SysParameterService sysParameterService_consumer;

    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * @Method: adviceEntry()
     * @Descrition: 录入在院人员医嘱信息
     *          1.录入在院人员医嘱信息之前，查看该病人是否进行了医保登记
     *          2.查看病人是否已经做了结算操作
     *          3.查看医嘱信息是否已经录入到医保前台
     *          4.只上传住院费用传输到医保前台的医嘱信息（开医嘱/核收/执行/费用传输/出院/结算/录入上传）
     *          5.把医嘱录入上传的日志信息保存到医保日志表里面
     * @Pramas: inptVisitDTO住院
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/10
     * @Retrun: boolean
     */
    @Override
    public boolean saveAdviceEntry(InsureIndividualVisitDTO insureIndividualVisitDTO) {
        InsureIndividualVisitDTO visitDTO = insureAdviceEntryDAO.queryInsurePatientInfo(insureIndividualVisitDTO);
        if ( visitDTO == null ) {
            throw new AppException("该病人没有进行医保登记");
        }
        visitDTO.setCrteId(insureIndividualVisitDTO.getCrteId());
        visitDTO.setCrteName(insureIndividualVisitDTO.getCrteName());

        String hospCode = visitDTO.getHospCode();
        String orgCode = visitDTO.getInsureRegCode();

        String insureRegCode = visitDTO.getInsureRegCode();
        // 根据医保机构编码查询医保配置信息
        InsureConfigurationDTO configDTO = new InsureConfigurationDTO();
        configDTO.setHospCode(hospCode); //医院编码
        configDTO.setRegCode(insureRegCode); // 医保注册编码
        configDTO.setIsValid(Constants.SF.S); // 是否有效
        List<InsureConfigurationDTO> configurationDTOList = insureConfigurationDAO.findByCondition(configDTO);
        if (ListUtils.isEmpty(configurationDTOList)) {
            throw new RuntimeException("未找到医保机构，请先配置医保信息！");
        }
        InsureConfigurationDTO insureConfigurationDTO = configurationDTOList.get(0);
        // 获取该医保配置是否走统一支付平台，1走，0/null不走
        String isUnifiedPay = insureConfigurationDTO.getIsUnifiedPay();

        insureIndividualVisitDTO.setIsAdviceEntry("0");
        List<InptAdviceDTO> adviceDTOList = this.queryMatchAdvice(insureIndividualVisitDTO);
        if (ListUtils.isEmpty(adviceDTOList)) {
            throw new AppException("没有需要上传的数据!");
        }
        insureAdviceEntryDAO.updateInsureUploadById(adviceDTOList);

        /**
         * 先操作日志表，后调用医保接口
         */
        this.operateInsureLog(visitDTO);
        Map<String, Object> adviceParamMap =null;
        List<Map<String,Object>> paramMapList = new ArrayList<>();

       /* Map<String,Object> isInsureUnifiedMap = new HashMap<>();
        isInsureUnifiedMap.put("hospCode", hospCode);
        isInsureUnifiedMap.put("code", "UNIFIED_PAY");
        // 根据系统判断 是否走医保统一支付平台调用接口
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(isInsureUnifiedMap).getData();
        if(sysParameterDTO!=null && Constants.SF.S.equals(sysParameterDTO.getValue())){*/
        if(StringUtils.isNotEmpty(isUnifiedPay) && "1".equals(isUnifiedPay)){
            paramMapList = handlerParamter(visitDTO,adviceDTOList);
            Map<String,Object> paramDataMap = new HashMap<>();
            paramDataMap.put("data",paramMapList);
            commonInsureUnified(hospCode, insureRegCode, Constant.UnifiedPay.INPT.UP_4402, paramDataMap);
        }
        /*else{
            List<Map<String, Object>> prescribeMapList = new ArrayList<>();
            List<Map<String, Object>> adviceMapList = new ArrayList<>();
            List<Map<String, Object>> diseaseMapList = new ArrayList<>();
            Map<String, Object> httpResult = new HashMap<>();
            Map<String, Object> prescribeParamMap = new HashMap<>(); // 保存处方的信息
            adviceParamMap = new HashMap<>();  // 保存医嘱的信息
            Map<String, Object> diseaseParamMap = new HashMap<>();  // 保存疾病的信息
            *//**
             * 必填参数信息
             *//*
            httpResult.put("function_id", Constant.Xiangtan.ADVICE.BIZC320001);    //功能号
            httpResult.put("akb020", visitDTO.getMedicineOrgCode());    //医疗机构编码
            httpResult.put("aaa027", visitDTO.getAaa027());    //分级统筹中心编码
            httpResult.put("aaz217", visitDTO.getMedicalRegNo()); //医保登记号
            httpResult.put("aac001 ",visitDTO.getAac001());    //个人电脑号
            httpResult.put("aae011", visitDTO.getCrteName());   // 录入人
            httpResult.put("akc190", visitDTO.getVisitNo());    //住院号
            httpResult.put("audit_flag", "0"); // 校验标识 0：只保存不校验；1：提供医审校验

            *//**
             *  传递给call方法调用的参数
             *//*
            httpResult.put("hospCode", visitDTO.getHospCode()); // 医院编码
            httpResult.put("medicineOrgCode", visitDTO.getMedicineOrgCode()); // 医疗机构编码

            *//**
             * 根据就诊id查询 处方信息
             *//*
            List<OutptPrescribeDetailsDTO> prescribeDTOList = insureAdviceEntryDAO.queryPrescribe(insureIndividualVisitDTO);
            if (!ListUtils.isEmpty(prescribeDTOList)) {
                for (int i = 0; i < prescribeDTOList.size(); i++) {
                    prescribeParamMap.put("bkr035", "");  //处方编号
                    prescribeParamMap.put("akb020", "");   // 医疗机构编码
                    prescribeParamMap.put("aaz217", "");   // 业务序列号
                    prescribeParamMap.put("akc190", "");  // 住院号
                    prescribeParamMap.put("bkr001", prescribeDTOList.get(i).getOrderNo());     // 处方号
                    prescribeParamMap.put("bkr002", "");    // 医嘱组号
                    prescribeParamMap.put("bkr003", "");    // 医嘱组内编号
                    prescribeParamMap.put("bkr004", prescribeDTOList.get(i).getTypeCode());   // 处方类型
                    prescribeParamMap.put("bkr005", "");   // 医嘱来源
                    prescribeParamMap.put("bkr008", prescribeDTOList.get(i).getCrteTime());   // 开方时间
                    prescribeParamMap.put("bkr009", prescribeDTOList.get(i).getCode());  // 项目编码
                    prescribeParamMap.put("bkr010", prescribeDTOList.get(i).getItemName()); // 项目名称
                    prescribeParamMap.put("bkr011", prescribeDTOList.get(i).getPrepCode());   // 剂型代码
                    prescribeParamMap.put("bkr012", prescribeDTOList.get(i).getPrepCodeName());  // 剂型名称
                    prescribeParamMap.put("bkr013", prescribeDTOList.get(i).getSpec());  // 药品规格
                    prescribeParamMap.put("bkr014", prescribeDTOList.get(i).getUseCode());  // 药品用法
                    prescribeParamMap.put("bkr015", prescribeDTOList.get(i).getNum());  // 发药数量
                    prescribeParamMap.put("bkr016", prescribeDTOList.get(i).getNumUnitCode());  //发药数量单位
                    prescribeParamMap.put("bkr017", prescribeDTOList.get(i).getItemCode().equals("1") ?prescribeDTOList.get(i).getRateName():"");  // 用药频次
                    prescribeParamMap.put("bkr018", prescribeDTOList.get(i).getDosage()); // 每次使用剂量
                    prescribeParamMap.put("bkr019", prescribeDTOList.get(i).getDosageUnitCode());        //每次使用剂量单位
                    prescribeParamMap.put("bkr020", prescribeDTOList.get(i).getNum());        //每次使用数量
                    prescribeParamMap.put("bkr021", prescribeDTOList.get(i).getNumUnitCode());    // 每次使用数量单位
                    prescribeParamMap.put("bkr022", "");   // 用药途径代码
                    prescribeParamMap.put("bkr023", "");  // 用药途径名称
                    prescribeParamMap.put("bkr024", prescribeDTOList.get(i).getUseDays());   // 用药天数
                    prescribeParamMap.put("bkr025", "");   // 调配药师工号
                    prescribeParamMap.put("bkr026", "");  // 调配药师姓名
                    prescribeParamMap.put("bkr027", "");  //核对药师工号
                    prescribeParamMap.put("bkr028", "");   // 核对药师姓名
                    prescribeParamMap.put("bkr029", ""); // 发药药师工号
                    prescribeParamMap.put("bkr030", "");  // 发药药师姓名
                    prescribeParamMap.put("bkr031", prescribeDTOList.get(i).getIsSkin());    // 皮试判别
                    prescribeParamMap.put("bkr032", prescribeDTOList.get(i).getIsPositive());    // 皮试判别结果，0阴性 1阳性(码表)
                    prescribeParamMap.put("bkr033", ""); // 检查部位，影像检查医嘱，说明被检查的部位
                    prescribeParamMap.put("bkr034", "");  // 备注
                    prescribeMapList.add(prescribeParamMap);
                }
            }
            if (!ListUtils.isEmpty(adviceDTOList)) {
                for (int i = 0; i < adviceDTOList.size(); i++) {
                    adviceParamMap.put("bkr091", ""); // 医嘱编号
                    adviceParamMap.put("akb020", visitDTO.getMedicineOrgCode()); // 医疗机构编码
                    adviceParamMap.put("aaz217", adviceDTOList.get(i).getMedicalRegNo()); // 业务序列号
                    adviceParamMap.put("akc190", adviceDTOList.get(i).getInNo()); // 住院号
                    adviceParamMap.put("bkr051", adviceDTOList.get(i).getOrderNo()); // 医嘱号
                    adviceParamMap.put("bkr002", adviceDTOList.get(i).getGroupNo()); // 医嘱组号
                    adviceParamMap.put("bkr003", adviceDTOList.get(i).getGroupSeqNo()); // 医嘱组内编号
                    adviceParamMap.put("bkr052", ""); // 撤消标志
                    adviceParamMap.put("bkr053", adviceDTOList.get(i).getDeptCode()); // 下达科室编码  开嘱科室
                    adviceParamMap.put("bkr054", adviceDTOList.get(i).getDeptName()); // 下达科室名称
                    adviceParamMap.put("bkr055", adviceDTOList.get(i).getCrteId()); // 医嘱下达人工号
                    adviceParamMap.put("bkr056", adviceDTOList.get(i).getCrteName()); // 医嘱下达人姓名
                    adviceParamMap.put("bkr057", adviceDTOList.get(i).getCrteTime()); // 医嘱下达时间
                    adviceParamMap.put("bkr058", adviceDTOList.get(i).getCheckId()); // 医嘱核对人工号
                    adviceParamMap.put("bkr059", adviceDTOList.get(i).getCheckName()); // 医嘱核对人姓名
                    adviceParamMap.put("bkr060", adviceDTOList.get(i).getCheckTime()); // 医嘱核对时间
                    adviceParamMap.put("bkr061", adviceDTOList.get(i).getExecDeptId()); // 执行科室编码
                    adviceParamMap.put("bkr062", adviceDTOList.get(i).getAdviceExecDeptName()); // 执行科室名称
                    adviceParamMap.put("bkr063", adviceDTOList.get(i).getExecTime()); // 医嘱执行时间
                    adviceParamMap.put("bkr064", adviceDTOList.get(i).getStopTime()); // 医嘱终止时间
                    adviceParamMap.put("bkr065", adviceDTOList.get(i).getContent()); // 医嘱说明
                    adviceParamMap.put("bkr066", ""); // 医嘱类别
                    adviceParamMap.put("bkr067", adviceDTOList.get(i).getTypeCode()); // 医嘱来源
                    adviceParamMap.put("bkr068", adviceDTOList.get(i).getItemCode()); // 医嘱明细编码
                    adviceParamMap.put("bkr069", adviceDTOList.get(i).getItemName()); // 医嘱明细名称(医院药品项目名称)
                    adviceParamMap.put("bkr070", adviceDTOList.get(i).getStopDoctorId()); // 停嘱医生工号
                    adviceParamMap.put("bkr071", adviceDTOList.get(i).getStopDoctorName()); // 停嘱医生姓名
                    adviceParamMap.put("bkr072", adviceDTOList.get(i).getStopCheckId()); // 停嘱核对人工号
                    adviceParamMap.put("bkr073", adviceDTOList.get(i).getStopCheckName()); // 停嘱核对人姓名
                    adviceParamMap.put("bkr074", adviceDTOList.get(i).getStopCheckTime()); // 停嘱核对时间
                    adviceParamMap.put("bkr075", adviceDTOList.get(i).getSpec()); // 药品规格
                    adviceParamMap.put("bkr076", adviceDTOList.get(i).getUsageCode()); // 药品用法
                    adviceParamMap.put("bkr077", adviceDTOList.get(i).getRateName()); // 用药频度
                    adviceParamMap.put("bkr078", adviceDTOList.get(i).getDosage()); // 每次使用剂量
                    adviceParamMap.put("bkr079", adviceDTOList.get(i).getDosageUnitCode()); // 每次使用剂量单位
                    adviceParamMap.put("bkr080", adviceDTOList.get(i).getNum()); // 每次使用数量
                    adviceParamMap.put("bkr081", adviceDTOList.get(i).getUnitCode()); // 每次使用数量单位
                    adviceParamMap.put("bkr082", ""); // 用药途径代码
                    adviceParamMap.put("bkr083", ""); // 用药途径名称
                    adviceParamMap.put("bkr084", adviceDTOList.get(i).getIsSkin()); // 皮试判别
                    adviceParamMap.put("bkr085", adviceDTOList.get(i).getIsPositive()); // 皮试判别结果
                    if("4".equals(adviceDTOList.get(i).getUseCode())){
                        adviceParamMap.put("bkr086", adviceDTOList.get(i).getNum()); // 用药天数，出院带药用
                        adviceParamMap.put("bkr087", adviceDTOList.get(i).getUnitCode()); // 发药数量，出院带药用
                        adviceParamMap.put("bkr088", "");  // 发药数量单位，出院带药用
                    }
                    else{
                        adviceParamMap.put("bkr087", ""); // 发药数量，出院带药用
                        adviceParamMap.put("bkr088", "");  // 发药数量单位，出院带药用
                    }
                    adviceParamMap.put("bkr089", ""); // 检查部位，影像检查医嘱，说明被检查的部位
                    adviceParamMap.put("bkr090", "");  // 特殊的说明
                    adviceParamMap.put("bkr092", "");  // 药品本位码
                    adviceMapList.add(prescribeParamMap);
                }
            }
            List<InptVisitDTO> diseaseDTOList = insureAdviceEntryDAO.queryInptVisitDisease(insureIndividualVisitDTO);
            *//**
             * 疾病参数
             *//*
            if (!ListUtils.isEmpty(diseaseDTOList)) {
                for (int i = 0; i < diseaseDTOList.size(); i++) {
                    diseaseParamMap.put("bkr112", diseaseDTOList.get(i).getDiseaseId()); // 诊断编号
                    diseaseParamMap.put("akb020", diseaseDTOList.get(i).getMedicineOrgCode()); // 医疗机构编码
                    diseaseParamMap.put("aaz217", diseaseDTOList.get(i).getMedicalRegNo());  // 就医登记号
                    diseaseParamMap.put("akc190", diseaseDTOList.get(i).getInNo()); // 住院号
                    diseaseParamMap.put("bkr100", ""); // 诊断流水号
                    diseaseParamMap.put("bkr101", ""); // 诊断类型区分, 编码。
                    diseaseParamMap.put("bkr103", ""); // 诊断类别代码
                    diseaseParamMap.put("bkr104", diseaseDTOList.get(i).getDiagnoseCrteTime()); // 诊断时间
                    diseaseParamMap.put("bkr105", diseaseDTOList.get(i).getDiseaseCode());  // 诊断编码
                    if("1".equals(diseaseDTOList.get(i).getTypeCode()) ||"2".equals(diseaseDTOList.get(i).getTypeCode()) ){
                        diseaseParamMap.put("bkr106", "01"); // 诊断编码类型
                    }
                    else{
                        diseaseParamMap.put("bkr106", "02"); // 诊断编码类型
                    }
                    diseaseParamMap.put("bkr107", diseaseDTOList.get(i).getDiseaseName()); // 诊断说明
                    diseaseParamMap.put("bkr108", diseaseDTOList.get(i).getIsMain());   // 主要诊断标志编码,
                    diseaseParamMap.put("bkr109", "");  // 疑似诊断标志
                    diseaseParamMap.put("bkr110", diseaseDTOList.get(i).getInSituationCode());  // 入院病情
                    diseaseParamMap.put("bkr111", diseaseDTOList.get(i).getOutSituationCode());   // 出院情况编码
                    diseaseMapList.add(prescribeParamMap);
                }
            }
            httpResult.put("doctorrecipe", prescribeParamMap);
            httpResult.put("doctoradvice", adviceParamMap);
            httpResult.put("disease", diseaseParamMap);
            transpond.to(visitDTO.getHospCode(), visitDTO.getMedicineOrgCode(), Constant.FUNCTION.BIZC320001, httpResult);
        }*/
        return true;
    }
    /**
     * @Method  commonInsureUnified
     * @Desrciption 调用统一支付平台公共接口方法
     * @Param  hospCode:医院编码
     *         orgCode:医保机构编码
     *         functionCode：功能号
     *         paramMap:入参
     *
     * @Author fuhui
     * @Date   2021/4/28 19:51
     * @Return
     **/
    private Map<String,Object> commonInsureUnified(String hospCode,String orgCode, String functionCode, Map<String, Object> paramMap) {
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(orgCode);
        insureConfigurationDTO.setIsValid(Constants.SF.S);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        if (insureConfigurationDTO == null) {
            throw new AppException("未查询到医保机构配置信息【"+ orgCode +"】");
        }
        Map httpParam = new HashMap();
        httpParam.put("infno", functionCode);  //交易编号
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        httpParam.put("input",paramMap);
        String json = JSONObject.toJSONString(httpParam);
        logger.info("调用功能号【"+functionCode+"】的入参为"+json);
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), json);
        if(StringUtils.isEmpty(resultJson)){
            throw new AppException("无法访问统一支付平台");
        }
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson,Map.class);
        if ("999".equals(MapUtils.get(resultMap,"code"))) {
            throw new AppException((String) resultMap.get("msg"));
        }
        if (!MapUtils.get(resultMap,"infcode").equals("0")) {
            throw new AppException((String) resultMap.get("err_msg"));
        }
        logger.info("调用功能号【"+functionCode+"】的反参为"+resultJson);
        return resultMap;
    }
    /**
     * @Method handlerParamter
     * @Desrciption  处理医保统一支付平台：住院医嘱记录上传参数
     * @Param
     *
     * @Author fuhui
     * @Date   2021/5/13 10:02
     * @Return
    **/
    private List<Map<String, Object>> handlerParamter(InsureIndividualVisitDTO visitDTO, List<InptAdviceDTO> adviceDTOList) {
        List<Map<String,Object>> mapList = new ArrayList<>();
        String mdtrtSn = visitDTO.getMedicalRegNo();
        String msgId = visitDTO.getOmsgid();
        String psnNo = visitDTO.getAac001();
        String bed = visitDTO.getVisitBerth();
        Map<String,Object> adviceMap  = null;
        if(!ListUtils.isEmpty(adviceDTOList)){
            for(InptAdviceDTO inptAdviceDTO : adviceDTOList){
                adviceMap = new HashMap<>();
                adviceMap.put("mdtrt_sn",msgId);  // 就医流水号
                adviceMap.put("mdtrt_id",mdtrtSn);  // 就诊ID
                adviceMap.put("psn_no",psnNo) ; // 人员编号
                adviceMap.put("ipt_bedno",bed); // 住院床号
                adviceMap.put("drord_no",inptAdviceDTO.getOrderNo()); // 医嘱号
                adviceMap.put("isu_dept_code",inptAdviceDTO.getDeptCode()); // 下达科室代码
                adviceMap.put("drord_isu_no",inptAdviceDTO.getCrteTime()); // 医嘱下达时间
                adviceMap.put("exe_dept_code",inptAdviceDTO.getExecDeptId()); // 执行科室代码
                adviceMap.put("exedept_name",inptAdviceDTO.getAdviceExecDeptName()); // 执行科室名称
                adviceMap.put("drord_chker_name",inptAdviceDTO.getCheckName()); // 医嘱审核人姓名
                adviceMap.put("drord_ptr_name",inptAdviceDTO.getExecName()); // 医嘱执行人姓名
                adviceMap.put("drord_grpno",inptAdviceDTO.getGroupNo()); // 医嘱组号
                adviceMap.put("drord_type",inptAdviceDTO.getTypeCode()); // 医嘱类别
                adviceMap.put("drord_item_type",null); // 医嘱项目分类代码
                adviceMap.put("drord_item_name",null); // 医嘱项目分类名称
                adviceMap.put("drord_detl_code",null); // 医嘱明细代码
                adviceMap.put("drord_detl_name",null); //医嘱明细名称
                adviceMap.put("medn_type_code",inptAdviceDTO.getItemCode()); //药物类型代码
                adviceMap.put("medn_type_name",inptAdviceDTO.getItemName()); // 药物类型名称
                adviceMap.put("drug_dosform",inptAdviceDTO.getPrepCode()); // 药品剂型
                adviceMap.put("drug_dosform_name",inptAdviceDTO.getPrepCodeName()); // 药品剂型名称
                adviceMap.put("drug_spec",inptAdviceDTO.getSpec()); // 药品规格
                adviceMap.put("dismed_cnt",inptAdviceDTO.getTotalNum()); // 发药数量
                adviceMap.put("dismed_cnt_unt",inptAdviceDTO.getTotalNumUnitCode()); // 发药数量单位
                adviceMap.put("medn_use_frqu",inptAdviceDTO.getRateName()); // 药物使用-频率
                adviceMap.put("medn_used_dosunt",inptAdviceDTO.getDosageUnitCode()); // 药物使用-剂量单位
                adviceMap.put("drug_used_sdose",inptAdviceDTO.getDosage()); // 药物使用-次剂量
                adviceMap.put("drug_used_idose",null); // 药物使用-总剂量
                adviceMap.put("drug_used_way_code",null); // 药物使用-途径代码
                adviceMap.put("drug_used_way",null); // 药物使用-途径
                adviceMap.put("medc_days",null); // 用药天数
                adviceMap.put("medc_begntime",null); // 用药开始时间
                adviceMap.put("medc_endtime",null); // 用药停止时间
                adviceMap.put("skintst_dicm",inptAdviceDTO.getIsSkin()); // 皮试判别
                adviceMap.put("tcmherb_foote",inptAdviceDTO.getHerbNoteCode()); // 草药脚注
                adviceMap.put("drord_endtime",inptAdviceDTO.getStopTime()); // 医嘱结束时间
                adviceMap.put("ipt_dept_code",visitDTO.getVisitDrptName()); // 住院科室代码
                adviceMap.put("medins_orgcode",inptAdviceDTO.getMedicineOrgCode()); // 医疗机构组织机构代码
                adviceMap.put("unif_purc_drug_flag",null); // 统一采购药品标志
                adviceMap.put("drug_mgt_plaf_code",null); // 药品管理平台代码
                adviceMap.put("drug_purc_code",null); // 药品采购代码
                adviceMap.put("bas_medn_flag",null); // 基本药物标志
                adviceMap.put("vali_flag",Constants.SF.S); // 有效标志
                adviceMap.put("medcas_drord_detl_id",inptAdviceDTO.getId()); // 病案医嘱明细id
                mapList.add(adviceMap);
            }
        }
        return mapList;
    }

    /**
     * @Method: operateInsureLog()
     * @Descrition: 操作医保医嘱录入上传日志表
     * @Pramas: visitDTO医保就诊表数据传输对象
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/17
     * @Retrun: boolean
     */
    private void operateInsureLog(InsureIndividualVisitDTO visitDTO) {
        visitDTO.setId(SnowflakeUtils.getId());
        visitDTO.setCrteId(visitDTO.getCrteId());
        visitDTO.setCrteName(visitDTO.getCrteName());
        visitDTO.setCrteTime(DateUtils.getNow());
        visitDTO.setInptVisitNo(visitDTO.getVisitNo());
        visitDTO.setIsMrisEntry("0");
        visitDTO.setIsAdviceEntry("1");
        insureAdviceEntryDAO.insertInsureAdviceLog(visitDTO);
    }

    /**
     * @Method: drawoadAdvicePatient()
     * @Decription: 提取待录入医嘱人员信息
     * @Pramas: type:1 住院号  2:参保人电脑号 3:参保人的姓名 4: 参保人的公民身份号码 5:参保人的 IC 卡号
     * insureIndividualVisitDTO:医保就诊病人传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/12/10
     * @Retrun: map
     */
//    @Override
//    public Map<String, Object> drawLoadAdvicePatient(InsureIndividualVisitDTO insureIndividualVisitDTO) {
//        Map<String, Object> httpParam = new HashMap<>();
//        InsureIndividualVisitDTO insureIndividual = insureAdviceEntryDAO.queryInsurePatientInfo(insureIndividualVisitDTO);
//        switch (insureIndividualVisitDTO.getType()) {
//            case "1":    // 住院号
//                httpParam.put("akc190", insureIndividual.getVisitNo()); // 住院号
//                httpParam.put("akb020", insureIndividual.getMedicineOrgCode()); // 医疗机构编码
//                httpParam.put("aka130", insureIndividual.getAka130()); // 业务类型 "12"：住院"42"：工伤住院"44"：工伤家庭 病床"45"：工伤辅助
//                break;
//            case "2":
//                httpParam.put("aac001", insureIndividual.getAac001()); // 个人电脑号
//                httpParam.put("akb020", insureIndividual.getMedicineOrgCode()); // 医疗机构编码
//                httpParam.put("aka130", insureIndividual.getAka130());
//                break;
//            case "3":
//                httpParam.put("aac003", insureIndividual.getAac003()); // 姓名
//                httpParam.put("akb020", insureIndividual.getMedicineOrgCode()); // 医疗机构编码
//                httpParam.put("aka130", insureIndividual.getAka130());
//                break;
//            case "4":
//                httpParam.put("aac002", insureIndividualVisitDTO.getAac002()); // 身份证
//                httpParam.put("akb020", insureIndividualVisitDTO.getMedicineOrgCode()); // 医疗机构编码
//                httpParam.put("aka130", insureIndividualVisitDTO.getAka130());
//                break;
//            case "5":
//                httpParam.put("aaz500", insureIndividualVisitDTO.getAaz500()); // IC卡号
//                httpParam.put("akb020", insureIndividualVisitDTO.getMedicineOrgCode()); // 医疗机构编码
//                httpParam.put("aka130", insureIndividualVisitDTO.getAka130());
//                break;
//        }
//        /*调用医保统一访问接口*/
//        Map<String, Object> resultMap = transpond.to(insureIndividualVisitDTO.getHospCode(), "RC0044", Constant.FUNCTION.BIZC131271, httpParam);
//        return null;
//    }

    @Override
    public Boolean deleteAdvice(InsureIndividualVisitDTO insureIndividualVisitDTO) {
        Map<String, Object> httpParamMap = new HashMap<>();
        this.insureAdviceEntryDAO.deleteLog(insureIndividualVisitDTO);
        InsureIndividualVisitDTO visitDTO = this.insureAdviceEntryDAO.queryInsurePatientInfo(insureIndividualVisitDTO);

        // 必填参数信息
        httpParamMap.put("akb020" , visitDTO.getMedicineOrgCode());    //医疗机构编码
        httpParamMap.put("aaa027", visitDTO.getMedicineOrgCode());    //医疗机构编码
        httpParamMap.put("aaz217", visitDTO.getMedicalRegNo()); //医保登记号
        httpParamMap.put("aac001", visitDTO.getAac001());    //个人电脑号
        httpParamMap.put("advice_id", null);    //医嘱号（空为全删除）
        httpParamMap.put("hospCode", visitDTO.getHospCode());    //医院编码
        httpParamMap.put("insureRegCode", visitDTO.getMedicineOrgCode());
        Map<String, Object> resultMap = transpond.to(visitDTO.getHospCode(), visitDTO.getMedicineOrgCode(), Constant.Xiangtan.ADVICE.BIZC320003, httpParamMap);
        Integer returnCode = Integer.valueOf( resultMap.get("return_code").toString());
        if (returnCode < 0) {
            throw new AppException("医嘱删除失败,远程调用号（" +  Constant.Xiangtan.ADVICE.BIZC320003 + "）:【 " + resultMap.get("return_code_message") + "】");
        }
        return true;
    }

    /**
     * @Method: queryAdvice()
     * @Descrition: 根据就诊id 查询医嘱信息
     * @Pramas: insureIndividualVisitDTO医保就诊病人数据传输对象
     *   1.根据就诊id查询医保就诊费用表nsure_individual_cost里面的cost_id
     *   根据cost_id 关联inpt_cost查询对应的医嘱id
     *
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/16
     * @Retrun: 医嘱分页信息
     */
    @Override
    public PageDTO queryAdvicePage(InsureIndividualVisitDTO insureIndividualVisitDTO) {
        PageHelper.startPage(insureIndividualVisitDTO.getPageNo(),insureIndividualVisitDTO.getPageSize());
        insureIndividualVisitDTO.setIsAdviceEntry("All");
        List<InptAdviceDTO> adviceDTOList = queryMatchAdvice(insureIndividualVisitDTO);
        return PageDTO.of(adviceDTOList);
    }

    @Override
    public WrapperResponse<Boolean> BIZC300001(InsureIndividualVisitDTO insureIndividualVisitDTO) {
        InsureIndividualVisitDTO visitDTO = insureAdviceEntryDAO.queryInsurePatientInfo(insureIndividualVisitDTO);
        if (visitDTO == null) {
            throw new AppException("该病人没有进行医保登记");
        }
        // aka130 = 42 时该住院病人为工伤住院
        String medType = "42";
        //判断是不是工伤医保病人
        if (!medType.equals(visitDTO.getAka130())) {
            throw new AppException("该病人不属于工伤住院病人");
        }
        visitDTO.setCrteId(insureIndividualVisitDTO.getCrteId());
        visitDTO.setCrteName(insureIndividualVisitDTO.getCrteName());

        insureIndividualVisitDTO.setIsAdviceEntry("0");
        List<InptAdviceDTO> adviceDTOList = this.queryMatchAdvice(insureIndividualVisitDTO);
        if (ListUtils.isEmpty(adviceDTOList)) {
            throw new AppException("没有需要上传的数据!");
        }
        //入参
        Map<String, Object> httpParamMap = new HashMap<>();

        // 必填参数信息
        httpParamMap.put("hospital_id", visitDTO.getMedicineOrgCode());    //医疗机构编码
        httpParamMap.put("patient_id", insureIndividualVisitDTO.getInNo());    //住院号
        httpParamMap.put("serial_no", visitDTO.getMedicalRegNo()); //医保登记号
        httpParamMap.put("indi_id", visitDTO.getAac001());    //个人电脑号
        httpParamMap.put("input_man", visitDTO.getCrteName());    //录入人
        //医嘱明细信息
        List<DoctorAdviceDTO> doctoradvice = new ArrayList<>();
        for (int i = 0; i < adviceDTOList.size(); i++) {
            DoctorAdviceDTO dto = new DoctorAdviceDTO();
            InptAdviceDTO inptAdviceDTO = adviceDTOList.get(i);
            dto.setBegin_date(inptAdviceDTO.getLongStartTime());//医嘱开始时间
            dto.setEnd_date(inptAdviceDTO.getLongStartTime());//停止用药时间
            dto.setHis_item_name(inptAdviceDTO.getItemName());//医院药品名称
            dto.setSpecs(inptAdviceDTO.getSpec());//规格
            dto.setUsage(inptAdviceDTO.getUsageCode());//用法
            dto.setDosage(inptAdviceDTO.getDosage());//剂量
            dto.setDoctor(inptAdviceDTO.getCrteName());//下嘱医生
            dto.setNurse(inptAdviceDTO.getSubmitName());//转抄护士
            dto.setChecker(inptAdviceDTO.getCheckName());//核对护士
            dto.setAdvice_type(inptAdviceDTO.getTypeCode());//医嘱类型

            doctoradvice.add(dto);

        }
        httpParamMap.put("doctoradvice", doctoradvice); //医嘱明细信息
        logger.info("远程调用（" + Constant.HuNanSheng.ADVICE.BIZC300001 + "）的入参为：" + JSONObject.toJSONString(httpParamMap));
        Map<String, Object> resultMap = transpond.to(visitDTO.getHospCode(), visitDTO.getInsureRegCode(), Constant.HuNanSheng.ADVICE.BIZC300001, httpParamMap);
        Integer returnCode = Integer.valueOf(resultMap.get("return_code").toString());
        if (returnCode < 0) {
            throw new AppException("医嘱录入失败,远程调用号（" + Constant.HuNanSheng.ADVICE.BIZC300001 + "）:【 " + resultMap.get("return_code_message") + "】");
        }
        //医嘱上传成功后 本地表中的 是否已上传 字段改为 已上传
        insureAdviceEntryDAO.updateInsureUploadById(adviceDTOList);
        return WrapperResponse.success(true);
    }

    @Override
    public WrapperResponse<Boolean> deleteInjuryAdvice(InsureIndividualVisitDTO insureIndividualVisitDTO) {
        InsureIndividualVisitDTO visitDTO = insureAdviceEntryDAO.queryInsurePatientInfo(insureIndividualVisitDTO);
        if (visitDTO == null) {
            throw new AppException("该病人没有进行医保登记");
        }
        // aka130 = 42 时该住院病人为工伤住院
        String medType = "42";
        //判断是不是工伤医保病人
        if (!medType.equals(visitDTO.getAka130())) {
            throw new AppException("该病人不属于工伤住院病人");
        }
        Map<String, Object> resultMap = null;
        //拼接入参 两种删除所有医嘱信息的方法 如果住院号为空就用就医登记号
        if (StringUtils.isNotEmpty(insureIndividualVisitDTO.getInNo())) {
            //入参
            Map<String, Object> httpParamMap = new HashMap<>();

            httpParamMap.put("type", "P");
            httpParamMap.put("hospital_id", visitDTO.getMedicineOrgCode());//定点医疗机构编码
            httpParamMap.put("patient_id", insureIndividualVisitDTO.getInptVisitNo());//住院号
            logger.info("远程调用（" + Constant.HuNanSheng.ADVICE.BIZC300001 + "）的入参为：" + JSONObject.toJSONString(httpParamMap));
            resultMap = transpond.to(visitDTO.getHospCode(), visitDTO.getInsureRegCode(), Constant.HuNanSheng.ADVICE.BIZC300001, httpParamMap);
        } else {
            //入参
            Map<String, Object> httpParamMap = new HashMap<>();
            httpParamMap.put("type", "S");
            httpParamMap.put("hospital_id", visitDTO.getMedicineOrgCode());//定点医疗机构编码
            httpParamMap.put("serial_no", visitDTO.getMedicalRegNo()); //就医登记号
            logger.info("远程调用（" + Constant.HuNanSheng.ADVICE.BIZC300001 + "）的入参为：" + JSONObject.toJSONString(httpParamMap));
            resultMap = transpond.to(visitDTO.getHospCode(), visitDTO.getInsureRegCode(), Constant.HuNanSheng.ADVICE.BIZC300001, httpParamMap);
        }
        Integer returnCode = Integer.valueOf(resultMap.get("return_code").toString());
        //医嘱上传成功后 本地表中的 是否已上传 字段改为 未上传
        insureAdviceEntryDAO.updateInsureUploadByVisitId(insureIndividualVisitDTO.getVisitId());
        if (returnCode < 0) {
            throw new AppException("医嘱录入失败,远程调用号（" + Constant.HuNanSheng.ADVICE.BIZC300001 + "）:【 " + resultMap.get("return_code_message") + "】");
        }
        return WrapperResponse.success(true);
    }

    /**
     * @Method: queryMatchAdvice()
     * @Descrition: 查询已匹配上传的医嘱目录信息
     * @Pramas: insureIndividualVisitDTO：vsisitId:就诊Id
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/17
     * @Retrun: 医嘱数据传输对象
     */
    private List<InptAdviceDTO> queryMatchAdvice(InsureIndividualVisitDTO insureIndividualVisitDTO) {
        Map map =new HashMap<String,Object>();
        map.put("visitId",insureIndividualVisitDTO.getVisitId());
        map.put("hospCode",insureIndividualVisitDTO.getHospCode());
        map.put("isInsureUpload",insureIndividualVisitDTO.getIsAdviceEntry());
        List<InptAdviceDTO> adviceDTOList = insureAdviceEntryDAO.queryDoctorAdvice(map);
        return adviceDTOList;
    }


    /**
     * @Method: queryPage()
     * @Descrition: 分页根据查询医保病人信息
     * @Pramas: insureIndividualVisitDTO：vsisitId:就诊Id
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/12
     * @Retrun:
     */
    @Override
    public PageDTO queryPage(InsureIndividualVisitDTO insureIndividualVisitDTO) {
        PageHelper.startPage(insureIndividualVisitDTO.getPageNo(), insureIndividualVisitDTO.getPageSize());
        List<InptVisitDTO> visitDTOList = insureAdviceEntryDAO.queryPage(insureIndividualVisitDTO);
        return PageDTO.of(visitDTOList);
    }


}
