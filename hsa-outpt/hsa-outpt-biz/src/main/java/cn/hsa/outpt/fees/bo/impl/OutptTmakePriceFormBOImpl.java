package cn.hsa.outpt.fees.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.enums.TrigScen;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.base.bpft.service.BasePreferentialService;
import cn.hsa.module.base.deptDrug.dto.BaseDeptDrugStoreDTO;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import cn.hsa.module.center.syshospital.service.SysHospitalService;
import cn.hsa.module.dzpz.hainan.CondList;
import cn.hsa.module.dzpz.hainan.ExtData;
import cn.hsa.module.dzpz.hainan.SeltSucCallbackDTO;
import cn.hsa.module.dzpz.hainan.UploadFee;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.emd.service.OutptElectronicBillService;
import cn.hsa.module.insure.module.dao.InsureIndividualBasicDAO;
import cn.hsa.module.insure.module.dto.*;
import cn.hsa.module.insure.module.entity.InsureIndividualSettleDO;
import cn.hsa.module.insure.module.entity.InsureIndividualVisitDO;
import cn.hsa.module.insure.module.service.*;
import cn.hsa.module.insure.outpt.service.InsureUnifiedPayOutptService;
import cn.hsa.module.insure.outpt.service.OutptService;
import cn.hsa.module.interf.outpt.dao.OutptPrescribeDAO;
import cn.hsa.module.outpt.card.dao.BaseCardRechargeChangeDAO;
import cn.hsa.module.outpt.card.dto.BaseCardRechargeChangeDTO;
import cn.hsa.module.outpt.card.service.BaseCardRechargeChangeService;
import cn.hsa.module.outpt.fees.bo.OutptTmakePriceFormBO;
import cn.hsa.module.outpt.fees.dao.*;
import cn.hsa.module.outpt.fees.dto.*;
import cn.hsa.module.outpt.fees.entity.*;
import cn.hsa.module.outpt.outinInvoice.dao.OutinInvoiceDAO;
import cn.hsa.module.outpt.outinInvoice.dto.OutinInvoiceDTO;
import cn.hsa.module.outpt.outinInvoice.entity.OutinInvoiceDO;
import cn.hsa.module.outpt.outinInvoice.entity.OutinInvoiceDetailDO;
import cn.hsa.module.outpt.outinInvoice.service.OutinInvoiceService;
import cn.hsa.module.outpt.prescribe.dao.OutptDoctorPrescribeDAO;
import cn.hsa.module.outpt.prescribe.dto.OutptDiagnoseDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsDTO;
import cn.hsa.module.outpt.register.dao.OutptRegisterDAO;
import cn.hsa.module.outpt.register.dto.OutptRegisterDTO;
import cn.hsa.module.outpt.visit.dao.OutptVisitDAO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.outpt.visit.service.OutptVisitService;
import cn.hsa.module.payment.dto.PaymentSettleDTO;
import cn.hsa.module.payment.entity.PaymentOrderDO;
import cn.hsa.module.payment.entity.PaymentSettleDO;
import cn.hsa.module.payment.service.OutptPaymentService;
import cn.hsa.module.payment.service.PaymentOrderService;
import cn.hsa.module.payment.service.PaymentSettleService;
import cn.hsa.module.phar.pharoutreceive.entity.PharOutReceiveDO;
import cn.hsa.module.phar.pharoutreceive.entity.PharOutReceiveDetailDO;
import cn.hsa.module.phar.pharoutreceive.service.PharOutReceiveDetailService;
import cn.hsa.module.phar.pharoutreceive.service.PharOutReceiveService;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.entity.SysParameterDO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.ResourceTransactionManager;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.outpt.fees.bo.impl
 * @Class_name: OutptTmakePriceFormBOImpl
 * @Describe(描述):门诊划价收费BOImpl层
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/08/25 10:38
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
public class OutptTmakePriceFormBOImpl implements OutptTmakePriceFormBO {

    private static final String RET_CODE00 = "00";
    private static final String RET_CODE02 = "02";

    // 自费病人编号
    private static final String ZIFEI_PATAIENT = "0";

    private static final String ZIFEI = "00";
    private static final String YIBAO = "01";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OutptCostDAO outptCostDAO;

    @Resource
    private BaseOrderRuleService baseOrderRuleService_consumer;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private SysParameterService sysParameterService_consumer;

    @Resource
    private OutptSettleDAO outptSettleDAO;

    @Resource
    private OutinInvoiceDAO outinInvoiceDAO;

    @Resource
    private OutinInvoiceService outinInvoiceService;

    @Resource
    private OutptPayDAO outptPayDAO;

    @Resource
    private OutptSettleInvoiceDAO outptSettleInvoiceDAO;

    @Resource
    private OutptSettleInvoiceContentDAO outptSettleInvoiceContentDAO;

    @Resource
    private PharOutReceiveDetailService pharOutReceiveDetailService_consumer;

    @Resource
    private PharOutReceiveService pharOutReceiveService_consumer;

    @Resource
    private OutptDoctorPrescribeDAO outptDoctorPrescribeDAO;

    @Resource
    private BasePreferentialService basePreferentialService;

    @Resource
    private InsureIndividualVisitService insureIndividualVisitService_consumer;

    @Resource
    private OutptService outptService_consumer;

    @Resource
    private InsureIndividualSettleService insureIndividualSettleService;

    @Resource
    private InsureConfigurationService insureConfigurationService;

    @Resource
    private OutptInsurePayDAO outptInsurePayDAO;

    @Resource
    private OutptRegisterDAO outptRegisterDAO;

    @Resource
    private InsureUnifiedPayOutptService insureUnifiedPayOutptService_consumer;

    @Resource
    private OutptElectronicBillService outptElectronicBillService;


    @Autowired
    private ResourceTransactionManager transactionManager;

    @Resource
    private InsureIndividualCostService insureIndividualCostService_consumer;

    @Resource
    private OutptVisitDAO outptVisitDAO;

    @Resource
    private LisData lisData;

    @Resource
    SysParameterService sysParameterService;

    @Resource
    private OutptVisitService outptVisitService;

    @Resource
    private BaseCardRechargeChangeDAO baseCardRechargeChangeDAO;
    @Resource
    private BaseCardRechargeChangeService baseCardRechargeChangeService;

    @Resource
    private SysParameterService getSysParameterService_consumer;

    @Resource
    private InsureItemMatchService itemMatchService;

    @Resource
    private PayOnlineInfoDAO payOnlineInfoDAO;

    @Resource
    private InsureIndividualBasicService insureIndividualBasicService;

    @Resource
    private InsureDetailAuditService insureDetailAuditService;

    @Resource
    private SysHospitalService sysHospitalService_consummer;

    @Resource
    private OutptPaymentService outptPaymentService_consummer;

    @Resource
    private PaymentSettleService paymentSettleService_consummer;

    @Resource
    private PaymentOrderService paymentOrderService_consummer;


    /**
     * @param outptVisitDTO 请求参数
     * @Menthod queryOutptVisitPage
     * @Desrciption 查询门诊就诊患者信息
     * @Author Ou·Mr
     * @Date 2020/8/25 10:46
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 请求结果
     */
    @Override
    public WrapperResponse queryOutptVisitPage(OutptVisitDTO outptVisitDTO) {
        PageHelper.startPage(outptVisitDTO.getPageNo(), outptVisitDTO.getPageSize());
        List<OutptVisitDTO> outptVisitDTOList = outptCostDAO.queryOutptVisitList(outptVisitDTO);
        return WrapperResponse.success(PageDTO.of(outptVisitDTOList));
    }


    /**
     * @param outinInvoiceDTO 请求参数
     * @Menthod queryOutinInvoice
     * @Desrciption 获取当前发票号
     * @Author Ou·Mr
     * @Date 2020/8/25 10:47
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 请求结果
     */
    @Override
    public WrapperResponse queryOutinInvoice(OutinInvoiceDTO outinInvoiceDTO) {
        //校验是否有在用状态的发票段，有就返回在用的发票信息
        outinInvoiceDTO.setStatusCode(Constants.PJSYZT.ZY);//使用状态 = 在用状态
        List<OutinInvoiceDTO> outinInvoiceDTOS = outinInvoiceDAO.getInvoiceList(outinInvoiceDTO);
        if (outinInvoiceDTOS != null && outinInvoiceDTOS.size() == 1) {
            //返回在用的发票信息
            return WrapperResponse.success("成功。", outinInvoiceDTOS.get(0));
        }
        //没有在用的发票段，查询待用状态的发票段，供前端选择
        outinInvoiceDTO.setStatusCode(Constants.PJSYZT.DY);//使用状态 = 待用状态
        outinInvoiceDTOS = outinInvoiceDAO.getInvoiceList(outinInvoiceDTO);
        if (outinInvoiceDTOS != null && outinInvoiceDTOS.size() > 0) {
            return WrapperResponse.success("成功。", outinInvoiceDTOS);
        }
        //没有在用发票段、没有待用发票段，提示用户去领取发票段
        return WrapperResponse.success("没有发票段，要领取发票段吗？", null);
    }

    /**
     * @Menthod queryOutptCostList
     * @Desrciption 根据处方id查询门诊费用
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/8/25 10:48
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 请求结果
     */
    @Override
    public WrapperResponse queryOutptCostList(Map param) {
        String hospCode = (String) param.get("hospCode");//医院编码
        String visitId = (String) param.get("visitId");//患者id
        String isPhys = (String) param.get("isPhys") == null ? "" : (String) param.get("isPhys");//是否为体检信息
        String preferentialTypeId = (String) param.get("preferentialTypeId");//优惠类型id
        // 获取系统参数 是否开启分处方结算
        SysParameterDTO mergeParameterDTO =null;
        Map<String, Object> isMergeParam = new HashMap<>();
        isMergeParam.put("hospCode", hospCode);
        isMergeParam.put("code", "SF_DIVIDED_PRRESCIBE");
        mergeParameterDTO = sysParameterService_consumer.getParameterByCode(isMergeParam).getData();
        //《========处方id========》
        List<String> opIds = new ArrayList<>();// 处方id集合  liuliyun 20210903
        if(mergeParameterDTO !=null && "1".equals(mergeParameterDTO.getValue())) {
            if (param.containsKey("opIds") && StringUtils.isNotEmpty((String) param.get("opIds"))) {
                String ids = (String) param.get("opIds");
                String opIdList[] = ids.split(",");
                if (opIdList != null && opIdList.length > 0) {
                    for (int a = 0; a < opIdList.length; a++) {
                        opIds.add(opIdList[a]);
                    }
                }
            }
        }
        //《========处方id========》
        //获取费用数据
        //为了区分查询体检和其他收费数据,添加个前端传递参数isPhys
        param.put("sourceCode", isPhys.equals("1") ? Constants.FYLYFS.QTFY : Constants.FYLYFS.ZJHJSF);//划价收费费用
        List<OutptCostDTO> outptCostDTOList = outptCostDAO.queryDrugMaterialByVisitId(param);
        OutptVisitDTO outptVisitDTO = new OutptVisitDTO();
        outptVisitDTO.setHospCode(hospCode);
        outptVisitDTO.setId(visitId);
        outptVisitDTO.setPreferentialTypeId(preferentialTypeId);
        outptVisitDTO.setOutptCostDTOList(outptCostDTOList);
        // 是否分处方结算
        if(mergeParameterDTO !=null && "1".equals(mergeParameterDTO.getValue())) {
            if (opIds != null && opIds.size() > 0) {
                outptVisitDTO.setOpIds(opIds);  // 处方id集合 liuliyun 20210907
            }
        }
        // 官红强修改，2021年1月26日19:55:07  查询患者费用信息时，不计算优惠金额，
        // outptCostDTOList = verifyCouponPrice(outptVisitDTO,0);
        outptCostDTOList = customVerifyCouponPrice(outptVisitDTO, 0);
        //获取诊断信息
        OutptPrescribeDTO outptPrescribeDTO = new OutptPrescribeDTO();
        outptPrescribeDTO.setHospCode(hospCode);//医院编码
        outptPrescribeDTO.setVisitId(visitId);//就诊id
        List<OutptDiagnoseDTO> outptDiagnoseDTOList = outptDoctorPrescribeDAO.getOutptDiagnose(outptPrescribeDTO);
        // 循环费用信息中的费用来源，只要有来源为处方的就认定为处方病人
        boolean isCFpatient = false; // 默认为直接划价病人
        for (OutptCostDTO dto : outptCostDTOList) {
            if (dto != null && dto.getSourceCode().equals(Constants.FYLYFS.CF)) {
                isCFpatient = true;
                break;
            }
        }

        // 根据就诊id查询患者 病人类型与优惠类别
        OutptVisitDTO dto = outptCostDAO.getOutptVisitById(param);

        JSONObject obj = new JSONObject();
        obj.put("outptCost", outptCostDTOList);
        obj.put("outptDiagnose", outptDiagnoseDTOList);
        obj.put("isCFpatient", isCFpatient);
        if (dto != null && dto.getPreferentialTypeId() != null && !dto.getPreferentialTypeId().equals("")) {
            obj.put("preferentialTypeId", dto.getPreferentialTypeId());
        }
        return WrapperResponse.success(obj);
    }

    /**
     * @Description: 查询患者费用，用于体检
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/11/22 20:24
     * @Return
     */
    @Override
    public WrapperResponse queryOutptCostListTJ(Map param) {
        String hospCode = (String) param.get("hospCode");//医院编码
        String visitId = (String) param.get("visitId");//患者id
        String isPhys = (String) param.get("isPhys") == null ? "" : (String) param.get("isPhys");//是否为体检信息
        List<OutptCostDTO> outptCostDTOList = outptCostDAO.queryCostVisitIdTJ(param);

        JSONObject obj = new JSONObject();
        obj.put("outptCost", outptCostDTOList);
        obj.put("isCFpatient", false);
        return WrapperResponse.success(obj);
    }

    /**
     * @Menthod queryBaseByPage
     * @Desrciption  获取医院 项目、材料、药品 信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/8/25 10:54
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 请求结果
     */
    @Override
    public WrapperResponse queryBaseByPage(Map param) {
        /* 设置分页参数 */
        PageHelper.startPage(Integer.parseInt((String) param.get("pageNo")), Integer.parseInt((String) param.get("pageSize")));
        return WrapperResponse.success(PageDTO.of(outptCostDAO.queryDrugMaterialList(param)));
    }

    /**
     * @Menthod saveOutptCostAndVisit
     * @Desrciption  暂存门诊划价收费 患者信息、费用信息。
     * @param outptVisitDTO 请求参数
     * @Author Ou·Mr
     * @Date 2020/8/25 10:56
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 请求结果
     */
    @Override
    public WrapperResponse saveOutptCostAndVisit(OutptVisitDTO outptVisitDTO) {
        return null;
    }

    /**
     * @Menthod saveOutptSettleMoney
     * @Desrciption 门诊划价收费试算、计算支付金额
     * @param outptVisitDTO 请求参数
     * @Author Ou·Mr
     * @Date 2020/8/25 10:58
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 请求结果
     */
    @Override
    @Async
    public WrapperResponse saveOutptSettleMoney(OutptVisitDTO outptVisitDTO) {
        //生成redis结算Key，医院编码 + 证件号码 + 划价收费KEY
        String key = new StringBuilder(outptVisitDTO.getHospCode()).append(StringUtils.isEmpty(outptVisitDTO.getId()) ? outptVisitDTO.getCertNo() : outptVisitDTO.getId())
                .append(Constants.OUTPT_FEES_REDIS_KEY).toString();
        if (StringUtils.isNotEmpty(redisUtils.get(key))) {
            return WrapperResponse.fail("划价收费提示：该患者正在别处结算；请稍后再试", null);
        }
        Map<String, Object> result = new HashMap<String, Object>();//返回结果
        try {
            // 使用redis锁病人，并设置自动过期时间600秒，防止异常情况没有结算成功且redis不会自动清除的问题
            redisUtils.set(key, outptVisitDTO.getId(), 600);
            //删除结算脏数据
            Map<String, String> settleParam = new HashMap<String, String>();
            settleParam.put("hospCode", outptVisitDTO.getHospCode()); //医院编码
            settleParam.put("visitId", outptVisitDTO.getId());//就诊id
            settleParam.put("isSettle", Constants.SF.F);//是否结算 = 否
            settleParam.put("statusCode", Constants.ZTBZ.ZC);//状态标志 = 正常
            outptSettleDAO.delOutptSettleByParam(settleParam);

            // 1、定义当前病人是门诊处方病人 还是 直接划价收费病人
            boolean patientIsCF = true;  // 处方病人：true   直接划价收费病人： false

            // 官红强 2021年1月26日20:50:53 试算时不再计算优惠金额，直接取费用明细（处方病人直接取费用，直接划价收费病人还是需要计算优惠）
            List<OutptCostDTO> outptCostDTOList = this.customVerifyCouponPrice(outptVisitDTO, 0);

            // =======================2021年7月21日15:53:52  官红强 如果前端删除了非处方费用，此时需要删除非处方费用（未结算的）    start ===============
            List<String> deleteCost = new ArrayList<>();  // 需要删除的非处方费用
            List<OutptCostDTO> tempoutptCostDTOList = new ArrayList<>();
            tempoutptCostDTOList.addAll(outptCostDTOList);  // 复制处方费用
            List<OutptCostDTO> temp = outptVisitDTO.getOutptCostDTOList(); // 结算页面传递的需要收费的费用
            // 得到需要删除的费用
            for (int i = tempoutptCostDTOList.size()-1; i>=0; i--) {
                if ("1".equals(tempoutptCostDTOList.get(i).getSourceCode())) {
                    tempoutptCostDTOList.remove(i);
                    continue;
                }
                for (int j = 0; j < temp.size(); j++) {
                    if (tempoutptCostDTOList.get(i).getId().equals(temp.get(j).getId())) {
                        tempoutptCostDTOList.remove(i);
                        break;
                    }
                }
            }
            // 获取需要删除费用的id集合
            if (!ListUtils.isEmpty(tempoutptCostDTOList)) {
                for (OutptCostDTO dec : tempoutptCostDTOList) {
                    deleteCost.add(dec.getId());
                }
                String[] ids = deleteCost.toArray(new String[deleteCost.size()]);
                outptCostDAO.deleteFCFCostByIds(ids, outptVisitDTO.getHospCode());
                outptCostDTOList.removeAll(tempoutptCostDTOList);
            }
            // ====================2021年7月21日15:53:52  官红强 如果前端删除了非处方费用，此时需要删除非处方费用（未结算的）    end ===============

            // 如果患者为体检患者，则不需要校验费用条数
            if (outptVisitDTO.getIsPhys() == null || "".equals(outptVisitDTO.getIsPhys())) {
                if (outptCostDTOList.size() != outptVisitDTO.getOutptCostDTOList().size()) {
                    return WrapperResponse.fail("划价收费提示：该患者费用数量不一致；请刷新浏览器再试，（如果是退款重收时，当前患者存在未结算费用，请先结算再退费或医生删除处方未结算处方后再退费）", null);
                }
            }

            // 2判断人员信息是选择还是手动录入；
            String id = null; //就诊id  其中门诊过来的病人已经有就诊id，直接取病人id，如果是直接划价收费的，需要生成就诊id
            Boolean isDel = true;//判断是否需要删除费用数据（只有在患者为直接划价收费，且第二次或以上结算时才需要删除原直接划价收费费用信息）

            //诊断
            OutptPrescribeDTO outptPrescribeDTO = new OutptPrescribeDTO();
            List<OutptDiagnoseDTO> outptDiagnoseDTOList = new ArrayList<>();
            String[] tempIds = outptVisitDTO.getDiagnose();
            StringBuilder str = new StringBuilder();
            if(tempIds!=null && tempIds.length>0) {
                for (int i = 0; i < tempIds.length; i++) {
                    str.append(tempIds[i]);
                    if (i < tempIds.length - 1) {
                        str.append(",");
                    }
                }
            }
            boolean isChange = false;
            if (tempIds != null && tempIds.length > 0) {
                isChange = true;
                outptPrescribeDTO.setHospCode(outptVisitDTO.getHospCode());
                outptPrescribeDTO.setVisitId(outptVisitDTO.getId());
                outptPrescribeDTO.setDiagnoseIds(str.toString());
                outptPrescribeDTO.setCrteId(outptVisitDTO.getCrteId());
                outptPrescribeDTO.setCrteName(outptVisitDTO.getCrteName());
                outptDiagnoseDTOList = this.buildOutptDiagnose(outptPrescribeDTO);
            }
            // 根据就诊id查询挂号信息
            OutptRegisterDTO outptRegisterDTO = outptVisitDAO.getOutptRegister(outptVisitDTO);
            // 2.1 如果病人没有挂号开处方，直接划价买药，需要保存患者基本信息、诊断信息、费用信息的新增
            if (StringUtils.isEmpty(outptVisitDTO.getId())) {
                id = this.addPatientInfo(outptVisitDTO);
                outptVisitDTO.setId(id);
                if (isChange) {
                    //诊断信息保存
                    if(!ListUtils.isEmpty(outptDiagnoseDTOList)){
                        //保存处方诊断
                        outptDoctorPrescribeDAO.insertDiagnose(outptDiagnoseDTOList);
                    }
                }
                isDel = false;
            } else {
                id = outptVisitDTO.getId();//赋值就诊id
                if (outptRegisterDTO == null) {  // 如果挂号信息为空，则说明患者为直接划价患者 可以修改患者信息
                    //人员信息修改操作
                    outptCostDAO.editOutptVisitByKey(outptVisitDTO);//编辑操作
                    // 更新诊断信息
                    if (isChange && tempIds != null && tempIds.length > 0) {
                        OutptDiagnoseDTO outptDiagnoseDTO = new OutptDiagnoseDTO();
                        outptDiagnoseDTO.setHospCode(outptVisitDTO.getHospCode());
                        outptDiagnoseDTO.setVisitId(outptVisitDTO.getId());
                        outptDiagnoseDTO.setDiseaseIds(str.toString());
                        // 根据就诊id删除历史诊断
                        outptVisitDAO.deleteDiagnoseByVisitId(outptVisitDTO);
                        //诊断信息保存
                        if(!ListUtils.isEmpty(outptDiagnoseDTOList)){
                            //保存处方诊断
                            outptDoctorPrescribeDAO.insertDiagnose(outptDiagnoseDTOList);
                        }
                    }
                }
            }

            // 3、判断病人类型，门诊直接划价收费 or 处方病人
            if (StringUtils.isEmpty(outptVisitDTO.getId())) {
                patientIsCF = false;
            } else {
                // 根据就诊id查询门诊费用明细，费用来源有处方，也有直接划价收费的为处方病人； 费用来源只有直接划价收费的表示是直接划价收费病人
                patientIsCF = this.checkPatientIsMZ(outptVisitDTO);
            }
            // 3、1 直接划价收费病人重新计算优惠
//            if (!patientIsCF) {
//                outptCostDTOList = this.verifyCouponPrice(outptVisitDTO, 0);
//            }
            if (StringUtils.isEmpty(outptVisitDTO.getId())) {
                outptCostDTOList = this.verifyCouponPrice(outptVisitDTO, 0);
            }

            // 4 处理费用信息，校验药品库存信息（如果患者为直接划价收费病人，需要将上次一次费用信息全部删除再新增）
            String settleId = SnowflakeUtils.getId();//结算id
            // 4.2 取第一次结算的结算id
            String oneSettleId = settleId;
            if (!outptCostDTOList.isEmpty() && outptCostDTOList.get(0).getOneSettleId() != null && !"".equals(outptCostDTOList.get(0).getOneSettleId())) {
                oneSettleId = outptCostDTOList.get(0).getOneSettleId();
            }
            // 4.1 处理病人费用信息，处方病人：更新费用状态； 直接划价收费病人：删除原费用信息，新增当前费用信息
            this.saveOutptCost(outptCostDTOList, outptVisitDTO, settleId, oneSettleId, isDel, id);

            // 5. 计算划价费用
            BigDecimal realityPrice = new BigDecimal(0);//优惠后总费用
            BigDecimal totalPrice = new BigDecimal(0);//项目总费用
            outptVisitDTO.setRealityPrice(realityPrice);
            for (OutptCostDTO outptCostDTO : outptCostDTOList) {
                // i + 优惠后金额 = 优惠后总费用
                realityPrice = BigDecimalUtils.add(realityPrice, outptCostDTO.getRealityPrice());
                // i + 单价 = 项目总费用
                totalPrice = BigDecimalUtils.add(totalPrice, outptCostDTO.getTotalPrice());
            }

            // 6. 判断病人类型，如果是医保病人；做医保试算操作。
            Map<String, String> payinfo = new HashMap<String, String>();
            BigDecimal miPrice = new BigDecimal(0); //统筹支付金额
            // 2022年1月5日11:01:11 退费重收时默认 将优惠后金额保留到2位小数，
            if (outptVisitDTO.getTfcsMark() != null && "tfcs".equals(outptVisitDTO.getTfcsMark())) {
                realityPrice = realityPrice.setScale(2, BigDecimal.ROUND_HALF_DOWN); // 将计算后的优惠后总金额自动保留两位小数， 不四舍五入,直接丢掉
            }
            BigDecimal selfPrice = realityPrice; //个人需支付金额
            Integer patientValueCode = Integer.parseInt(outptVisitDTO.getPatientCode());
            if (patientValueCode > 0) {
                Map<String, Object> ybMap = null;

                // 查询医院医保配置（直接走医保还是走统一支付平台）
                Map<String, Object> map = new HashMap<>();
                map.put("hospCode", outptVisitDTO.getHospCode());
                map.put("code", "UNIFIED_PAY");
                SysParameterDTO sys = sysParameterService_consumer.getParameterByCode(map).getData();
                JSONObject insureParam = JSONObject.parseObject(outptVisitDTO.getInsure());//获取医保信息
                if (insureParam.containsKey("electronicBillParam") && !StringUtils.isEmpty(insureParam.get("electronicBillParam").toString())){
                    //电子凭证
                    JSONObject insure = JSONObject.parseObject(outptVisitDTO.getInsure());
                    Map<String,String> info = (Map<String, String>)insure.get("info");
                    Map<String,Object> outptElectronicParam = new HashMap<String,Object>();
                    outptElectronicParam.put("hospCode",outptVisitDTO.getHospCode());//医院编码
                    outptElectronicParam.put("insureRegCode",info.get("regCode"));//医保编码
                    outptElectronicParam.put("outptCostDTOList",outptCostDTOList);//费用信息
                    outptElectronicParam.put("outptVisitDTO",outptVisitDTO);//个人信息
                    Map<String,Object> httpResult = (Map<String, Object>) outptElectronicBillService.addPatientCost(outptElectronicParam).getData();
                    InsureIndividualVisitDTO insureIndividualVisitDTO = (InsureIndividualVisitDTO) httpResult.get("insureIndividualVisitDTO");
                    //修改表信息
                    JSONObject payToken = JSONObject.parseObject(insureIndividualVisitDTO.getPayToken());
                    payToken.put("payToken",httpResult.get("payToken"));
                    InsureIndividualVisitDO insureIndividualVisitDO = new InsureIndividualVisitDO();
                    insureIndividualVisitDO.setVisitId(outptVisitDTO.getId());
                    insureIndividualVisitDO.setHospCode(outptVisitDTO.getHospCode());
                    insureIndividualVisitDO.setPayOrdId((String) httpResult.get("payOrdId"));
                    insureIndividualVisitDO.setPayToken(JSONObject.toJSONString(payToken));
                    Map<String,Object> insureVisitMap = new HashMap<String,Object>();
                    insureVisitMap.put("hospCode",outptVisitDTO.getHospCode());
                    insureVisitMap.put("insureIndividualVisitDO",insureIndividualVisitDO);
                    insureIndividualVisitService_consumer.editInsureIndividualVisit(insureVisitMap);
                } else if (sys != null && sys.getValue().equals("1")) {  // 调用统一支付平台
                    // 调用医保试算
                    ybMap = this.saveOutptSettleMoney_YB(outptVisitDTO, id, outptCostDTOList, realityPrice, totalPrice, settleId);
                } else {
                    // 调用医保试算
                    ybMap = this.saveOutptSettleMoney_YB(outptVisitDTO, id, outptCostDTOList, realityPrice, totalPrice, settleId);
                }
                if (ybMap != null) {
                    miPrice = (BigDecimal) ybMap.get("miPrice");
                    selfPrice = (BigDecimal) ybMap.get("selfPrice");
                    payinfo = (Map<String, String>) ybMap.get("payinfo");
                }
            }

            // 7、生成结算数据，保存门诊结算表（试算状态）
            OutptSettleDO outptSettleDO = this.saveOutptSettle(outptVisitDTO, settleId, id, totalPrice, realityPrice, selfPrice, miPrice, oneSettleId);

            // 8、将试算结果返回给前端
            outptVisitDTO.setOutptCostDTOList(outptCostDTOList);
            result.put("outptVisit", outptVisitDTO);//返回个人信息
            result.put("outptSettle", outptSettleDO);//返回结算信息
            result.put("payinfo", payinfo);//医保报销信息
        } catch (Exception e) {
            throw e;
        } finally {
            redisUtils.del(key);//删除结算key
        }
        return WrapperResponse.success("成功。", result);
    }

    /**
     * @Menthod buildOutptDiagnose
     * @Desrciption  诊断表赋值
     * @param outptPrescribeDTO
     * @Author zengfeng
     * @Date   2020/9/14 10:24
     * @Return OutptPrescribeDTO
     **/
    public List<OutptDiagnoseDTO> buildOutptDiagnose(OutptPrescribeDTO outptPrescribeDTO){
        //是否主诊断
        String isMain = "0";
        int i = 0;
        List<OutptDiagnoseDTO> outptDiagnoseDTOList = new ArrayList<>();
        if(StringUtils.isEmpty(outptPrescribeDTO.getDiagnoseIds())){
            return outptDiagnoseDTOList;
        }
        //获取诊断信息
        List<OutptDiagnoseDTO> yjzOutptDiagnoseDTOList = outptDoctorPrescribeDAO.getOutptDiagnose(outptPrescribeDTO);
        if(!ListUtils.isEmpty(yjzOutptDiagnoseDTOList)){
            //判断是否有主诊断
            List<OutptDiagnoseDTO> isMainList = yjzOutptDiagnoseDTOList.stream().filter(s->s.getIsMain().equals(Constants.SF.S)).collect(Collectors.toList());
            if(!ListUtils.isEmpty(isMainList)){
                isMain = "1";
            }
        }
        //获取诊断ID
        String diagnoseIds = outptPrescribeDTO.getDiagnoseIds();
        //拆分ID串
        String[] diagnoseIdArray = diagnoseIds.split(",");
        for(String diagnoseId : diagnoseIdArray){
            if(StringUtils.isEmpty(diagnoseId)){
                continue;
            }
            List<OutptDiagnoseDTO> isMainList = yjzOutptDiagnoseDTOList.stream().filter(s->diagnoseId.equals(s.getDiseaseId())).collect(Collectors.toList());
            //存在该诊断
            if(!ListUtils.isEmpty(isMainList)){
                continue;
            }
            OutptDiagnoseDTO outptDiagnoseDTO = new OutptDiagnoseDTO();
            //主键
            outptDiagnoseDTO.setId(SnowflakeUtils.getId());
            //医院编码
            outptDiagnoseDTO.setHospCode(outptPrescribeDTO.getHospCode());
            //就诊ID
            outptDiagnoseDTO.setVisitId(outptPrescribeDTO.getVisitId());
            //疾病ID
            outptDiagnoseDTO.setDiseaseId(diagnoseId);
            //第一条数据，默认为主诊断(没有主诊断情况下)
            if(i == 0 && Constants.SF.F.equals(isMain) ){
                outptDiagnoseDTO.setTypeCode(Constants.ZDLX.MZZZD);
                outptDiagnoseDTO.setIsMain(Constants.SF.S);
            }else{
                outptDiagnoseDTO.setTypeCode(Constants.ZDLX.MZCZD);
                outptDiagnoseDTO.setIsMain(Constants.SF.F);
            }
            i++;
            //创建人ID
            outptDiagnoseDTO.setCrteId(outptPrescribeDTO.getCrteId());
            //创建人
            outptDiagnoseDTO.setCrteName(outptPrescribeDTO.getCrteName());
            outptDiagnoseDTOList.add(outptDiagnoseDTO);
        }
        return outptDiagnoseDTOList;
    }

    /**
     * @Description: (试算) 保存门诊结算数据
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/2/5 10:02
     * @Return
     */
    private OutptSettleDO saveOutptSettle(OutptVisitDTO outptVisitDTO, String settleId, String id,
                                          BigDecimal totalPrice, BigDecimal realityPrice, BigDecimal selfPrice, BigDecimal miPrice, String oneSettleId) {
        SysParameterDO sysParameterDO = getSysParameter(outptVisitDTO.getHospCode(), Constants.HOSPCODE_DISCOUNTS_KEY);//获取当前医院优惠配置
        BigDecimal roundingCost = new BigDecimal(0);
        if (outptVisitDTO.getTfcsMark() != null && "tfcs".equals(outptVisitDTO.getTfcsMark())) {
            roundingCost = outptVisitDTO.getTruncPrice();
        } else {
            roundingCost = BigDecimalUtils.rounding(sysParameterDO.getValue(), realityPrice); //舍入费用
        }
        // 生成结算数据，保存门诊结算表
        OutptSettleDO outptSettleDO = new OutptSettleDO();
        outptSettleDO.setId(settleId);//id
        outptSettleDO.setHospCode(outptVisitDTO.getHospCode());//医院编码
        outptSettleDO.setVisitId(id);//就诊id
        outptSettleDO.setSettleNo(getOrderNo(outptVisitDTO.getHospCode(), Constants.ORDERRULE.JZ));//结算单号
        outptSettleDO.setPatientCode(outptVisitDTO.getPatientCode());//病人类型
        outptSettleDO.setSettleTime(new Date());//结算时间
        outptSettleDO.setTotalPrice(totalPrice);//总金额
        outptSettleDO.setRealityPrice(realityPrice);//优惠后总金额
        outptSettleDO.setTruncPrice(roundingCost);//舍入金额（存在正负金额）
        outptSettleDO.setActualPrice(null);//实收金额
        outptSettleDO.setSelfPrice(BigDecimalUtils.subtract(selfPrice, roundingCost));// 个人自付金额减去舍人金额
        outptSettleDO.setMiPrice(miPrice);//统筹支付金额
        outptSettleDO.setIsSettle(Constants.SF.F);//是否结算（SF）
        outptSettleDO.setDailySettleId(null);//日结缴款ID
        outptSettleDO.setStatusCode(Constants.ZTBZ.ZC);//状态标志代码（ZTBZ）;正常
        outptSettleDO.setRedId(null);//冲红ID
        outptSettleDO.setIsPrint(Constants.SF.F);//是否打印（SF）;否
        outptSettleDO.setOldSettleId(null);//原结算ID
        outptSettleDO.setIsPrintList(Constants.SF.F);//是否打印清单（SF）
        outptSettleDO.setPrintListTime(null);//清单打印时间
        outptSettleDO.setSourcePayCode(null);//支付来源代码（ZFLY，第三方对接）
        outptSettleDO.setOrderNo(null);//支付订单号（第三方订单号）
        outptSettleDO.setCrteId(outptVisitDTO.getCrteId());//创建人id
        outptSettleDO.setCrteName(outptVisitDTO.getCrteName());//创建人名称
        outptSettleDO.setCrteTime(new Date());//创建时间
        outptSettleDO.setOneSettleId(oneSettleId); // 记录下第一次结算id
        // 保存门诊结算（试算）费用信息
        outptSettleDAO.insertSelective(outptSettleDO);
        // 将费用按照医院配置的舍入规则舍入总金额与合计金额
        outptSettleDO.setTotalPrice(BigDecimalUtils.subtract(outptSettleDO.getTotalPrice(), roundingCost));//总金额
        outptSettleDO.setRealityPrice(BigDecimalUtils.subtract(outptSettleDO.getRealityPrice(), roundingCost));//优惠后总金额
        return outptSettleDO;
    }

    /**
     * @Description: （试算）处理病人费用信息，处方病人：更新费用状态； 直接划价收费病人：删除原费用信息，新增当前费用信息
     * @Param: settleId: 结算id; id:门诊就诊id; isDel: 是否删除费用信息
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/2/2 11:00
     * @Return
     */
    private void saveOutptCost(List<OutptCostDTO> outptCostDTOList, OutptVisitDTO outptVisitDTO, String settleId, String OneSettleId, boolean isDel, String id) {
        List<OutptCostDTO> prescribeList = new ArrayList<OutptCostDTO>();//处方费用
        List<OutptCostDTO> costList = new ArrayList<OutptCostDTO>(); //划价费用
        outptCostDTOList.stream().forEach(outptCostDTO -> {
            outptCostDTO.setHospCode(outptVisitDTO.getHospCode());//医院编码
            outptCostDTO.setSettleCode(Constants.JSZT.YUJS);//结算状态代码；设置预结算状态
            outptCostDTO.setStatusCode(Constants.ZTBZ.ZC);//状态标志 = 正常
            outptCostDTO.setSettleId(settleId);//结算id
            if (outptCostDTO.getOneSettleId() == null || "".equals(outptCostDTO.getOneSettleId())) {
                outptCostDTO.setOneSettleId(settleId);
            }
            if (Constants.FYLYFS.ZJHJSF.equals(outptCostDTO.getSourceCode())) {
                //划价费用信息
                outptCostDTO.setId(SnowflakeUtils.getId());//ID
                outptCostDTO.setVisitId(id);//门诊就诊id
                outptCostDTO.setSourceCode(Constants.FYLYFS.ZJHJSF);//费用来源方式代码
                if (null != outptCostDTO.getDistributeAllDetailId() && "tfcs".equals(outptVisitDTO.getTfcsMark())){
                    outptCostDTO.setIsDist(Constants.SF.S);//是否已发药 = 是
                }else{
                    outptCostDTO.setIsDist(Constants.SF.F);//是否已发药 = 否
                }
                outptCostDTO.setSourceDeptId(outptVisitDTO.getDeptId());//来源科室ID
                outptCostDTO.setDoctorId(outptVisitDTO.getDoctorId());//开方医生id
                outptCostDTO.setDoctorName(outptVisitDTO.getDoctorName());//开方医生名称
                outptCostDTO.setDeptId(outptVisitDTO.getDeptId());//开方科室id
                //outptCostDTO.setExecDeptId(outptVisitDTO.getDeptId());//执行科室id
                outptCostDTO.setCrteId(outptVisitDTO.getCrteId());//创建人id
                outptCostDTO.setCrteName(outptVisitDTO.getCrteName());//创建人姓名
                outptCostDTO.setCrteTime(new Date());//创建时间
                outptCostDTO.setOneSettleId(OneSettleId);
//                outptCostDTO.setTotalPrice(outptCostDTO.getTotalPrice().setScale(2, BigDecimal.ROUND_HALF_UP));
//                outptCostDTO.setRealityPrice(outptCostDTO.getRealityPrice().setScale(2, BigDecimal.ROUND_HALF_UP));
                // 总金额 = 总数量 * 单价    再保留两位小数
                outptCostDTO.setTotalPrice(BigDecimalUtils.multiply(outptCostDTO.getTotalNum(), outptCostDTO.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP)));
               //  优惠后金额 = 总数量 * 单价 - 优惠金额   再保留两位小数
                outptCostDTO.setRealityPrice(BigDecimalUtils.subtract(BigDecimalUtils.multiply(outptCostDTO.getTotalNum(), outptCostDTO.getPrice()), outptCostDTO.getPreferentialPrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
                costList.add(outptCostDTO);
            } else {
                //处方费用信息
                prescribeList.add(outptCostDTO);
            }
            //药品材料检查库存
            if (Constants.XMLB.YP.equals(outptCostDTO.getItemCode()) || Constants.XMLB.CL.equals(outptCostDTO.getItemCode())) {
                // 获取页面上修改后的用药性质
                boolean isChange = false;
                if (Constants.FYLYFS.ZJHJSF.equals(outptCostDTO.getSourceCode())) {
                    if (Constants.YYXZ.CG.equals(outptCostDTO.getUseCode()) || Constants.YYXZ.CYDY.equals(outptCostDTO.getUseCode())) {
                        isChange = true;
                    }
                } else {
                    if (Constants.YYXZ.CG.equals(outptCostDTO.getUseCode()) || Constants.YYXZ.CYDY.equals(outptCostDTO.getUseCode())) {
                        isChange = true;
                    }
                }
                //常规和出院带药
                if (isChange) {
                    //校验库存
                    Map<String, String> mapParameter = this.getParameterValue(outptCostDTO.getHospCode(), new String[]{"MZYS_CF_WJSFYKC"});
                    String wjsykc = MapUtils.getVS(mapParameter, "MZYS_CF_WJSFYKC", "24");
                    OutptPrescribeDetailsDTO outptPrescribeDetailsDTO = new OutptPrescribeDetailsDTO();
                    outptPrescribeDetailsDTO.setHospCode(outptCostDTO.getHospCode());
                    outptPrescribeDetailsDTO.setItemId(outptCostDTO.getItemId());
                    outptPrescribeDetailsDTO.setPharId(outptCostDTO.getPharId());
                    outptPrescribeDetailsDTO.setTotalNum(outptCostDTO.getTotalNum());
                    outptPrescribeDetailsDTO.setNumUnitCode(outptCostDTO.getNumUnitCode());
                    outptPrescribeDetailsDTO.setWjsykc(wjsykc);
                    outptPrescribeDetailsDTO.setCostId(outptCostDTO.getId());

                    List<Map<String, Object>> prescribeMap = outptDoctorPrescribeDAO.checkStock(outptPrescribeDetailsDTO);
                    //判断库存
                    if (ListUtils.isEmpty(prescribeMap)) {
                        throw new AppException(outptCostDTO.getItemName() + ":未指定药房或指定药房库存不足");
                    }
                    // 直接划价收费的药品或材料需要校验库存  2021年5月26日10:50:09
                    if (Constants.FYLYFS.ZJHJSF.equals(outptCostDTO.getSourceCode())) {
                        BigDecimal num = MapUtils.get(prescribeMap.get(0), "num");  // 总库存
                        String unitCode = MapUtils.get(prescribeMap.get(0), "unit_code"); // 单位代码
                        BigDecimal splitNum = MapUtils.get(prescribeMap.get(0), "split_num");// 拆零数量
                        BigDecimal splitRatio = MapUtils.get(prescribeMap.get(0), "split_ratio");// 拆分比
                        BigDecimal stockOccupy = MapUtils.get(prescribeMap.get(0), "stock_occupy");  // 占用库存
                        if (stockOccupy == null) {
                            stockOccupy = BigDecimalUtils.nullToZero(stockOccupy);
                        }
                        if (StringUtils.isNotEmpty(unitCode) && unitCode.equals(outptCostDTO.getNumUnitCode())) {// 如果单位和库存单位相同
                            BigDecimal divide = BigDecimalUtils.divide(stockOccupy, splitRatio);
                            BigDecimal sykc = BigDecimalUtils.subtract(num, divide);  // 剩余库存
                            if (BigDecimalUtils.greater(outptCostDTO.getTotalNum(), sykc)) {
                                throw new AppException(outptCostDTO.getItemName() + ":指定药房库存不足或者存在医生开处方的数量未失效");
                            }
                        } else {// 如果不相同 就根据拆零数量进行比较
                            BigDecimal sykc = BigDecimalUtils.subtract(splitNum, stockOccupy);  // 剩余库存
                            if (BigDecimalUtils.greater(outptCostDTO.getTotalNum(), sykc)) {
                                throw new AppException(outptCostDTO.getItemName() + ":指定药房库存不足或者存在医生开处方的数量未失效");
                            }
                        }
                    }
                }
            }
        });
        //删除非处方费用
        if (isDel) {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("hospCode", outptVisitDTO.getHospCode());//医院编码
            param.put("visitId", id);//门诊就诊id
            param.put("sourceCode", Constants.FYLYFS.ZJHJSF);//费用来源方式：直接划价收费
            param.put("settleCodes", new String[]{Constants.JSZT.WJS, Constants.JSZT.YUJS});//结算状态代码：未结算
            outptCostDAO.delOutptCostByVisitId(param);
        }
        //保存非处方费用
        if (!costList.isEmpty()) {
            outptCostDAO.batchInsert(costList);
        }
        //修改处方费用信息（根据优惠类型可能优惠金额发生改变；仅修改：项目总金额、项目优惠金额、项目优惠后总金额、结算状态、状态标志）
        if (!prescribeList.isEmpty()) {
            outptCostDAO.batchEditCostPrice(prescribeList);
        }
    }

    /**
     * @Description: 判断患者是门诊处方患者 还是 直接划价收费患者
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/2/2 10:04
     * @Return
     */
    private boolean checkPatientIsMZ(OutptVisitDTO outptVisitDTO) {
        boolean isChange = false;
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", outptVisitDTO.getHospCode());
        map.put("visitId", outptVisitDTO.getId());
        List<OutptCostDTO> list = outptCostDAO.selectByVisitId(map);
        if (list == null) {
            throw new AppException("当前病人没有费用信息，无法判断当前病人是否为直接划价收费病人");
        }
        Map<String, String> tempMap = new HashMap<>();
        for (OutptCostDTO dto : list) {
            if (!tempMap.containsKey(dto.getSourceCode())) {
                tempMap.put(dto.getSourceCode(), dto.getSourceCode());
            }
        }
        // 所有费用明细里面的费用来源只要有来源于处方的费用就代表是处方病人
        if (tempMap.containsKey(Constants.FYLYFS.CF)) {
            isChange = true;
            outptVisitDAO.updateOutptVisitCodeByid(outptVisitDTO);
        } else {
            isChange = false;
        }
        return isChange;
    }

    /**
     * @Description: 门诊划价收费时(试算)，直接划价收费时需要保存患者基础信息以及诊断信息
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/2/2 9:17
     * @Return
     */
    private String addPatientInfo(OutptVisitDTO outptVisitDTO) {
        Date now = new Date();
        //人员信息保存操作
        String id = SnowflakeUtils.getId();//赋值就诊id
        outptVisitDTO.setId(id);//id
        outptVisitDTO.setVisitNo(getOrderNo(outptVisitDTO.getHospCode(), Constants.ORDERRULE.JZ));//就诊号（根据单据规则生成）
        outptVisitDTO.setVisitCode(Constants.JZLB.MZ);//就诊类别代码；设置门诊
        outptVisitDTO.setIsVisit(Constants.SF.S);//是否就诊 = 是
        outptVisitDTO.setVisitTime(now);//就诊时间
        outptVisitDTO.setPym(PinYinUtils.toFirstPY(outptVisitDTO.getName()));//拼音码
        outptVisitDTO.setWbm(WuBiUtils.getWBCode(outptVisitDTO.getName()));//五笔码
        outptVisitDTO.setCrteTime(now);//创建时间
        //保存操作
        outptCostDAO.addOutptVisit(outptVisitDTO);
        //保存诊断信息
        if (outptVisitDTO.getDiagnose() != null && outptVisitDTO.getDiagnose().length > 0) {
            //封装诊断参数
            List<OutptDiagnoseDTO> outptDiagnoseDTOList = new ArrayList<OutptDiagnoseDTO>();
            String[] diagnose = outptVisitDTO.getDiagnose();
            for (int i = 0; i < diagnose.length; i++) {
                OutptDiagnoseDTO outptDiagnoseDTO = new OutptDiagnoseDTO();
                outptDiagnoseDTO.setId(SnowflakeUtils.getId());//id
                outptDiagnoseDTO.setHospCode(outptVisitDTO.getHospCode());//医院编码
                outptDiagnoseDTO.setDiseaseId(diagnose[i]);//诊断id
                outptDiagnoseDTO.setVisitId(id);//就诊id
                outptDiagnoseDTO.setTypeCode(i == 0 ? Constants.ZDLX.MZZZD : Constants.ZDLX.MZCZD);//类型标志
                outptDiagnoseDTO.setIsMain(i == 0 ? Constants.SF.S : Constants.SF.F);//是否主诊断
                outptDiagnoseDTO.setCrteId(outptVisitDTO.getCrteId());//创建人id
                outptDiagnoseDTO.setCrteName(outptVisitDTO.getCrteName());//创建人姓名
                outptDiagnoseDTO.setCrteTime(new Date());//创建时间
                outptDiagnoseDTOList.add(outptDiagnoseDTO);
            }
            outptDoctorPrescribeDAO.insertDiagnose(outptDiagnoseDTOList);
        }
        return id;
    }

    /**
     * @Description: 门诊试算时，医保病人试算
     * id ： 门诊就诊id
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/2/1 16:00
     * @Return
     */
    private Map<String, Object> saveOutptSettleMoney_YB(OutptVisitDTO outptVisitDTO, String id, List<OutptCostDTO> outptCostDTOList,
                                                        BigDecimal realityPrice, BigDecimal totalPrice, String settleId) {
        Map<String, Object> resultMap = new HashMap<>();
        HashMap visitMap = new HashMap();
        //根据医院编码、医保注册编码查询医保配置信息
        String hospCode = outptVisitDTO.getHospCode();
        visitMap.put("id",id);
        visitMap.put("hospCode",hospCode);
        InsureIndividualVisitDTO insureIndividualVisitById = insureIndividualVisitService_consumer.getInsureIndividualVisitById(visitMap);

        JSONObject insure = JSONObject.parseObject(outptVisitDTO.getInsure());
        Map<String, String> info = (Map<String, String>) insure.get("info");
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);//医院编码
        insureConfigurationDTO.setRegCode(insureIndividualVisitById.getInsureRegCode());//医保注册编码
        insureConfigurationDTO.setIsValid(Constants.SF.S);//是否有效
        Map conditionMap = new HashMap();
        conditionMap.put("hospCode", hospCode);
        conditionMap.put("insureConfigurationDTO", insureConfigurationDTO);
        List<InsureConfigurationDTO> insureConfigurationDTOList = insureConfigurationService.findByCondition(conditionMap);
        if (insureConfigurationDTOList == null || insureConfigurationDTOList.isEmpty()) {
            throw new NullPointerException("未找到医保机构，请重新获取人员信息。");
        }
        insureConfigurationDTO = insureConfigurationDTOList.get(0);
        // 查询医保登记信息
        InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
        insureIndividualVisitDTO.setHospCode(hospCode);
        insureIndividualVisitDTO.setVisitId(id);
        visitMap.put("hospCode", hospCode);
        visitMap.put("insureIndividualVisitDTO", insureIndividualVisitDTO);
        if (insureIndividualVisitService_consumer.findByCondition(visitMap) == null) {
            throw new AppException("请先进行医保登记");
        }

        String isUnifiedPay = insureConfigurationDTO.getIsUnifiedPay();

        //医保试算
        Map<String, Object> outptCostUploadAndTrialParam = new HashMap<String, Object>();
        outptCostUploadAndTrialParam.put("hospCode", outptVisitDTO.getHospCode());//医院编码
        outptCostUploadAndTrialParam.put("hospName", outptVisitDTO.getHospName());//医院名称
        outptCostUploadAndTrialParam.put("insureRegCode", insureIndividualVisitById.getInsureOrgCode());//医保机构编码
        outptCostUploadAndTrialParam.put("visitId", id);//门诊就诊id
        outptCostUploadAndTrialParam.put("fees", outptCostDTOList);

        /**** add by liaojiguang on 2021-07-06 -start ***/
        outptCostUploadAndTrialParam.put("code",outptVisitDTO.getCode()); // 操作员编码
        outptCostUploadAndTrialParam.put("crteId",outptVisitDTO.getCrteId());// 操作员ID
        outptCostUploadAndTrialParam.put("crteName",outptVisitDTO.getCrteName());// 操作员姓名
        outptCostUploadAndTrialParam.put("settleId",settleId); // 结算ID
        outptCostUploadAndTrialParam.put("phone",outptVisitDTO.getPhone()); // 电话号码
        /**** add by liaojiguang on 2021-07-06 -end ***/

        Map<String, Object> trialMap = null;

        /**
         * 查询医院医保配置（直接走原来的医保还是走统一支付平台）
         * 进行 医保试算之前，先调用医保的费用传输 然后调用试算接口
         * 如果调用医保的费用传输成功，试算失败时，捕获异常。调用费用传输取消接口
         *
         */
       /* Map<String, Object> map = new HashMap<>();
        map.put("hospCode", outptVisitDTO.getHospCode());
        map.put("code", "UNIFIED_PAY");
        SysParameterDTO sys = sysParameterService_consumer.getParameterByCode(map).getData();*/
//        if (sys != null && sys.getValue().equals("1")) {  // 调用统一支付平台
        Map<String, Object> unifiedPayMap = new HashMap<>();
        if (StringUtils.isNotEmpty(isUnifiedPay) && "1".equals(isUnifiedPay)){
            // 直接切换统一支付平台
           // Map<String, Object> unifiedPayMap = new HashMap<>();
            unifiedPayMap.put("outptVisitDTO", outptVisitDTO);
            unifiedPayMap.put("outptCostDTOList", outptCostDTOList);
            unifiedPayMap.put("settleId", settleId);
            unifiedPayMap.put("totalPrice", totalPrice);
            unifiedPayMap.put("visitId", outptVisitDTO.getId());
            unifiedPayMap.put("realityPrice", realityPrice);
            unifiedPayMap.put("hospCode", outptVisitDTO.getHospCode());
            unifiedPayMap.put("crteId", outptVisitDTO.getCrteId());
            unifiedPayMap.put("code", outptVisitDTO.getCode());
            unifiedPayMap.put("crteName", outptVisitDTO.getCrteName());

            Map<String, Object> stringObjectMap = updateFeeSubmit(unifiedPayMap);
            try {
                unifiedPayMap.put("batchNo",MapUtils.get(stringObjectMap,"batchNo"));
                List<Map<String, Object>> costDOList = MapUtils.get(stringObjectMap, "insureCostList");
                unifiedPayMap.put("costList", costDOList);
                trialMap = insureUnifiedPayOutptService_consumer.UP_2206(unifiedPayMap);
            } catch (Exception e) {
                unifiedPayMap.put("isError","1"); // 用来区分是异常取消结算 还是手动操作
                updateCancelFeeSubmit(unifiedPayMap);
                throw new RuntimeException(e.getMessage());
            }
        }else{
            outptCostUploadAndTrialParam.put("saveFlag","0");
            trialMap = outptService_consumer.setOutptCostUploadAndTrial(outptCostUploadAndTrialParam);
        }
        Map<String, String> payinfo = (Map<String, String>) trialMap.get("payinfo");
        String akb020 = payinfo.get("akb020");//医院编码
        String aaz217 = payinfo.get("aaz217");//就医登记号
        BigDecimal akc264 = BigDecimalUtils.convert(payinfo.get("akc264"));//医疗总费用
        BigDecimal bka831 = BigDecimalUtils.convert(payinfo.get("bka831"));//个人自付
        BigDecimal bka832 = BigDecimalUtils.convert(payinfo.get("bka832"));//医保支付
        BigDecimal bka825 = BigDecimalUtils.convert(payinfo.get("bka825"));//全自费费用
        BigDecimal bka826 = BigDecimalUtils.convert(payinfo.get("bka826"));//部分自费费用
        BigDecimal aka151 = BigDecimalUtils.convert(payinfo.get("aka151"));//起付线费用
        BigDecimal akb067 = BigDecimalUtils.convert(payinfo.get("akb067"));//个人现金支付
        BigDecimal akb066 = BigDecimalUtils.convert(payinfo.get("akb066"));//个人账户支付

        /**
         * 试算的时候如果现金支付 >= 医疗总费用 则不允许走医保
         * 增加参数控制  零费用报销是否让走医保结算
         */

            if(BigDecimalUtils.equals(akb067,akc264)){
                resultMap.put("hospCode",hospCode);
                resultMap.put("code","HOSP_APPR_FLAG");
                String cashPayValue = "";
                SysParameterDTO parameterDTO = sysParameterService_consumer.getParameterByCode(resultMap).getData();
                if(parameterDTO !=null){
                    String value = parameterDTO.getValue();
                    if(StringUtils.isNotEmpty(value)){
                        Map<String, Object> stringObjectMap = JSON.parseObject(value, Map.class);
                        for (String key : stringObjectMap.keySet()) {
                            if ("cashPay".equals(key)) {
                                cashPayValue = MapUtils.get(stringObjectMap,key);
                                break;
                            }
                        }
                        if(!"1".equals(cashPayValue)){
                            throw new AppException("零费用报销,不能走医保报销流程,请走自费结算流程。");
                        }
                    }else{
                        throw new AppException("零费用报销,不能走医保报销流程,请走自费结算流程。");
                    }
                }else{
                    throw new AppException("零费用报销,不能走医保报销流程,请走自费结算流程。");
                }
            }

            BigDecimal bka839 = BigDecimalUtils.convert(payinfo.get("bka839"));//其他支付
            BigDecimal ake039 = BigDecimalUtils.convert(payinfo.get("ake039"));//医疗保险统筹基金支付
            BigDecimal ake035 = BigDecimalUtils.convert(payinfo.get("ake035"));//公务员医疗补助基金支付
            BigDecimal ake026 = BigDecimalUtils.convert(payinfo.get("ake026"));//企业补充医疗保险基金支付
            BigDecimal ake029 = BigDecimalUtils.convert(payinfo.get("ake029"));//大额医疗费用补助基金支付
            BigDecimal acctInjPay = BigDecimalUtils.convert(payinfo.get("acctInjPay"));//职工意外伤害基金
            BigDecimal retAcctInjPay = BigDecimalUtils.convert(payinfo.get("retAcctInjPay"));//居民意外伤害基金
            BigDecimal governmentPay = BigDecimalUtils.convert(payinfo.get("governmentPay"));//政府兜底
            BigDecimal thbPay = BigDecimalUtils.convert(payinfo.get("thbPay"));//特惠保
            BigDecimal hospPrice = BigDecimalUtils.convert(payinfo.get("hospPrice"));//医院垫付
            BigDecimal carePay = BigDecimalUtils.convert(payinfo.get("carePay"));//优抚对象医疗补助基金
            BigDecimal lowInPay = BigDecimalUtils.convert(payinfo.get("lowInPay"));//农村低收入人口医疗补充保险
            BigDecimal othPay = BigDecimalUtils.convert(payinfo.get("othPay"));//其他基金支付 - 基金单项
            BigDecimal mafPay = BigDecimalUtils.convert(payinfo.get("mafPay"));//民政救助金支付
            BigDecimal hospExemAmount = BigDecimalUtils.convert(payinfo.get("hospExemAmount"));//医院减免
            BigDecimal retiredPay = BigDecimalUtils.convert(payinfo.get("retiredPay"));// 离休基金
            BigDecimal fertilityPay = BigDecimalUtils.convert(payinfo.get("fertilityPay"));// 生育基金
            BigDecimal preselfpayAmt = BigDecimalUtils.convert(payinfo.get("preselfpayAmt"));// 先行自付金额
            BigDecimal inscpScpAmt = BigDecimalUtils.convert(payinfo.get("inscpScpAmt"));// 符合政策范围金额
            BigDecimal poolPropSelfpay = BigDecimalUtils.convert(payinfo.get("poolPropSelfpay"));// 基本医疗保险统筹基金支付比例
            BigDecimal acctMulaidPay = BigDecimalUtils.convert(payinfo.get("acctMulaidPay"));// 个人账户共计支付金额
            BigDecimal soldierPay = BigDecimalUtils.convert(payinfo.get("soldierPay"));// 一至六级残疾军人医疗补助基金
            BigDecimal retiredOutptPay = BigDecimalUtils.convert(payinfo.get("soldierPay"));// 离休老工人门慢保障基金
            BigDecimal injuryPay = BigDecimalUtils.convert(payinfo.get("injuryPay"));// 工伤保险基金
            BigDecimal hallPay = BigDecimalUtils.convert(payinfo.get("hallPay"));// 厅级干部补助基金
            BigDecimal soldierToPay = BigDecimalUtils.convert(payinfo.get("soldierToPay"));// 军转干部医疗补助基金
            BigDecimal welfarePay = BigDecimalUtils.convert(payinfo.get("welfarePay"));// 公益补充保险基金
            BigDecimal COVIDPay = BigDecimalUtils.convert(payinfo.get("COVIDPay"));// 新冠肺炎核酸检测财政补助
            BigDecimal familyPay = BigDecimalUtils.convert(payinfo.get("familyPay"));// 居民家庭账户金
            BigDecimal behalfPay = BigDecimalUtils.convert(payinfo.get("behalfPay"));// 代缴基金（破产改制）

            //TODO 计算医保支付金额 = 医保支付
            BigDecimal miPrice = bka832;
            //TODO 计算本次还需支付金额 = 优惠后总金额 - 医保支付金额
            BigDecimal selfPrice = BigDecimalUtils.subtract(realityPrice, miPrice);

            //获取医保个人信息
            Map<String, String> personinfo = (Map<String, String>) insure.get("personinfo");
            BigDecimal bacu18 = BigDecimalUtils.convert(personinfo.get("bacu18"));//账户余额

            Map<String, String> delIndividualSettleParam = new HashMap<String, String>();
            delIndividualSettleParam.put("hospCode", outptVisitDTO.getHospCode());//医院编码
            delIndividualSettleParam.put("visitId", outptVisitDTO.getId());//就诊id
            delIndividualSettleParam.put("settleState", Constants.YBJSZT.SS);//结算标志 = 试算
            insureIndividualSettleService.delInsureIndividualSettleByVisitId(delIndividualSettleParam);
            //医保结算表 insure_individual_settle
            InsureIndividualSettleDO insureIndividualSettleDO = new InsureIndividualSettleDO();
            insureIndividualSettleDO.setId(SnowflakeUtils.getId());//主键
            insureIndividualSettleDO.setHospCode(outptVisitDTO.getHospCode());//医院编码
            insureIndividualSettleDO.setVisitId(outptVisitDTO.getId());//就诊id
            insureIndividualSettleDO.setSettleId(settleId);//结算id
            insureIndividualSettleDO.setIsHospital(Constants.SF.F);//是否住院（SF）
            insureIndividualSettleDO.setVisitNo(outptVisitDTO.getVisitNo());//就诊登记号
            insureIndividualSettleDO.setDischargeDnCode(null);//出院疾病诊断编码
            insureIndividualSettleDO.setInsureOrgCode(insureConfigurationDTO.getCode());//医保机构编码
            insureIndividualSettleDO.setInsureRegCode(insureConfigurationDTO.getRegCode());//医保注册编码
            insureIndividualSettleDO.setMedicineOrgCode(insureConfigurationDTO.getOrgCode());//医疗机构编码
            insureIndividualSettleDO.setDischargeDnName(null);//出院疾病诊断名称
            insureIndividualSettleDO.setDischargedDate(null);//出院日期
            insureIndividualSettleDO.setDischargedCase(null);//出院情况
            insureIndividualSettleDO.setSettleway(Constants.JSFS.PTJS);//结算方式,01 普通结算,02 包干结算
            insureIndividualSettleDO.setBeforeSettle(bacu18);//结算前账户余额
            insureIndividualSettleDO.setLastSettle(BigDecimalUtils.isZero(bacu18) ? bacu18 : BigDecimalUtils.greater(bka831, bacu18) ? new BigDecimal(0) : BigDecimalUtils.subtract(bacu18, akb066));//结算后账户余额
            insureIndividualSettleDO.setState(Constants.ZTBZ.ZC);//状态标志,0正常，2冲红，1，被冲红
            insureIndividualSettleDO.setSettleState(Constants.YBJSZT.SS);//医保结算状态;0试算，1结算
            insureIndividualSettleDO.setCostbatch(null);//费用批次
            insureIndividualSettleDO.setAka130(insureIndividualVisitById.getAka130());//业务类型
            insureIndividualSettleDO.setBka006(insureIndividualVisitById.getBka006());//待遇类型
            insureIndividualSettleDO.setInjuryBorthSn(null);//业务申请号,门诊特病，工伤，生育
            insureIndividualSettleDO.setIsAccount(BigDecimalUtils.isZero(akb066) ? Constants.SF.F : Constants.SF.S);//当前结算是否使用个人账户;0是，1否
            insureIndividualSettleDO.setRemark(null);//备注
            insureIndividualSettleDO.setCrteId(outptVisitDTO.getCrteId());//创建人ID
            insureIndividualSettleDO.setCrteName(outptVisitDTO.getCrteName());//创建人姓名
            insureIndividualSettleDO.setCrteTime(new Date());//创建时间

            // 处理金额
            insureIndividualSettleDO.setTotalPrice(akc264);// 本次医疗总费用
            insureIndividualSettleDO.setStartingPrice(aka151);//起付线金额
            insureIndividualSettleDO.setAllPortionPrice(bka825);//全自费金额
            insureIndividualSettleDO.setPortionPrice(bka826);//部分自付金额 - 超限价
            insureIndividualSettleDO.setInsurePrice(miPrice);//医保支付
            insureIndividualSettleDO.setPlanPrice(ake039);//统筹基金支付
            insureIndividualSettleDO.setPlanAccountPrice(poolPropSelfpay);//统筹段自负金额***
            insureIndividualSettleDO.setPreselfpayAmt(preselfpayAmt);// 先行自付金额
            insureIndividualSettleDO.setSeriousPrice(ake029);//大病互助支付
            insureIndividualSettleDO.setCivilPrice(ake035);//公务员补助支付
            insureIndividualSettleDO.setRetirePrice(retiredPay);// 离休人员医疗保证基金
            insureIndividualSettleDO.setMafPay(mafPay); // 医疗救助基金
            insureIndividualSettleDO.setHospExemAmount(hospExemAmount); // 医院减免
            insureIndividualSettleDO.setPersonalPrice(akb066);//个人账户支付
            insureIndividualSettleDO.setPersonPrice(akb067);//个人支付
            insureIndividualSettleDO.setRestsPrice(bka839);//其他支付
            insureIndividualSettleDO.setFertilityPay(fertilityPay);// 生育基金
            insureIndividualSettleDO.setComPay(ake026);// 企业补充医疗保险基金
            insureIndividualSettleDO.setHospPrice("990101".equals(insureIndividualVisitById.getAka130())?othPay:hospPrice);//医院垫付
            insureIndividualSettleDO.setAcctInjPay(acctInjPay);
            insureIndividualSettleDO.setRetAcctInjPay(retAcctInjPay);
            insureIndividualSettleDO.setGovernmentPay(governmentPay);
            insureIndividualSettleDO.setThbPay(thbPay);
            insureIndividualSettleDO.setCarePay(carePay);
            insureIndividualSettleDO.setLowInPay(lowInPay);
            insureIndividualSettleDO.setOthPay(othPay);
            insureIndividualSettleDO.setInscpScpAmt(inscpScpAmt);
            insureIndividualSettleDO.setPoolPropSelfpay(poolPropSelfpay);
            insureIndividualSettleDO.setAcctMulaidPay(acctMulaidPay);
            insureIndividualSettleDO.setSoldierPay(soldierPay);
            insureIndividualSettleDO.setRetiredOutptPay(retiredOutptPay);
            insureIndividualSettleDO.setInjuryPay(injuryPay);
            insureIndividualSettleDO.setHallPay(hallPay);
            insureIndividualSettleDO.setSoldierToPay(soldierToPay);
            insureIndividualSettleDO.setWelfarePay(welfarePay);
            insureIndividualSettleDO.setCOVIDPay(COVIDPay);
            insureIndividualSettleDO.setFamilyPay(familyPay);
            insureIndividualSettleDO.setBehalfPay(behalfPay);
            Map<String, Object> insureSettleParam = new HashMap<String, Object>();
            insureSettleParam.put("hospCode", outptVisitDTO.getHospCode());//医院编码
            insureSettleParam.put("insureIndividualSettleDO", insureIndividualSettleDO);
            insureIndividualSettleService.insertSelective(insureSettleParam);
            resultMap.put("payinfo", payinfo);
            resultMap.put("miPrice", miPrice);
            resultMap.put("selfPrice", selfPrice);
        return resultMap;
    }

    /**
     * @Menthod saveOutptSettle
     * @Desrciption  门诊划价收费支付结算
     * @param outptVisitDTO 个人信息
     * @param outptSettleDTO 结算信息
     * @Author Ou·Mr
     * @Date 2020/8/25 11:00
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 请求结果
     */
    @Override
    public WrapperResponse saveOutptSettle(OutptVisitDTO outptVisitDTO, OutptSettleDTO outptSettleDTO, List<OutptPayDO> outptPayDOList) {
        String hospCode = outptVisitDTO.getHospCode(); //医院编码
        String visitId = outptVisitDTO.getId(); //就诊id
        String userId = outptVisitDTO.getCrteId(); //当前登录用户id
        String userName = outptVisitDTO.getCrteName(); //当前登录用户姓名
        String depId = outptVisitDTO.getDeptId(); //执行科室
        String settleId = outptSettleDTO.getId();//结算id
        String code = outptVisitDTO.getCode(); // 操作人编码
        List<Map<String, Object>> outinInvoiceList = null;//返回发票打印的费用统计信息

        String cardNo = outptVisitDTO.getCardNo(); // 一卡通卡号
        BigDecimal cardPrice = outptVisitDTO.getCardPrice();  //一卡通支付金额
        String profileId = outptVisitDTO.getProfileId();  // 患者档案id

        //生成redis结算Key，医院编码 + 证件号码 + 划价收费KEY
        String key = new StringBuilder(hospCode).append(StringUtils.isEmpty(visitId) ? outptVisitDTO.getCertNo() : visitId)
                .append(Constants.OUTPT_FEES_REDIS_KEY).toString();
        //校验该用户是否在结算
        if (StringUtils.isNotEmpty(redisUtils.get(key))) {
            throw new AppException("划价收费提示：该患者正在别处结算；请稍后再试。");
        }
        OutinInvoiceDTO outinInvoiceDTO = new OutinInvoiceDTO();//发票段信息
        String settleNo = "";
        try {
            redisUtils.set(key, key, 600);
            // 1、 校验是否使用发票，是否存在发票段（没有返回错误信息，页面给出选择发票段信息）
            if (outptSettleDTO.getIsInvoice()) {
                outinInvoiceDTO.setHospCode(hospCode);//医院编码
                outinInvoiceDTO.setUseId(userId);//发票使用人id
                List<String> typeCode = new ArrayList<String>();//票据类型（0、全院通用，1、门诊发票，2、挂号发票，3、门诊通用，4、住院）
                Collections.addAll(typeCode, Constants.PJLX.TY, Constants.PJLX.MZ, Constants.PJLX.MZTY);
                outinInvoiceDTO.setTypeCodeList(typeCode);//0、全院通用；1、门诊发票；3、门诊通用
                //校验是否有在用状态的发票段，有就返回在用的发票信息
                outinInvoiceDTO.setStatusCode(Constants.PJSYZT.ZY);//使用状态 = 在用状态
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("hospCode", hospCode);
                map.put("outinInvoiceDTO", outinInvoiceDTO);
                List<OutinInvoiceDTO> outinInvoiceDTOS = outinInvoiceService.updateForOutinInvoiceQuery(map).getData();
                if (outinInvoiceDTOS == null || outinInvoiceDTOS.size() != 1) {
                    //没有发票信息
                    //return WrapperResponse.info(-2, "请选择发票段", outinInvoiceDTOS);
                    throw new AppException("当前没有可用发票，请领取发票或取消使用发票再结算");
                }
                outinInvoiceDTO = outinInvoiceDTOS.get(0);
            }

            // 2、 校验费用信息是否正确（根据当前结算id，查询费用表）
            Map<String, Object> param = new HashMap<String, Object>();
            //hospCode（医院编码）、statusCode（状态标志）、settleCode（结算状态）、settleId（结算id）
            param.put("hospCode", hospCode);//hospCode（医院编码）
            param.put("statusCode", Constants.ZTBZ.ZC);//statusCode（状态标志 = 正常）
            param.put("settleCode", Constants.JSZT.YUJS);//settleCode（结算状态 = 预结算）
            param.put("settleId", settleId);//settleId（结算id）
            List<OutptCostDTO> outptCostDTOList = outptCostDAO.queryBySettleId(param);

            if (outptCostDTOList == null && outptCostDTOList.isEmpty()) {
                throw new AppException("支付失败，未找到本次结算费用信息，请确认是否已经结算或者刷新后重试。");
            }
            // 如果患者是体检患者，则不需要校验费用条数
            if (outptVisitDTO.getIsPhys() == null || "".equals(outptVisitDTO.getIsPhys())) {
                if (outptCostDTOList.size() != outptVisitDTO.getOutptCostDTOList().size()) {
                    throw new AppException("费用数量不正确，请刷新浏览器再试");
                }
            }

            // 更新医技申请单状态
            if (!ListUtils.isEmpty(outptCostDTOList)) {
                outptCostDAO.updateMedicApply(visitId, hospCode, "02", outptCostDTOList);
            }

            /* 生成领药申请单，校验库存、领药申请单明细 */
            Map<String, Object> queryParam = new HashMap<String, Object>();
            queryParam.put("hospCode", outptVisitDTO.getHospCode());//医院编码
            queryParam.put("pfTypeCode", outptVisitDTO.getPreferentialTypeId());//优惠类型
            queryParam.put("items", outptCostDTOList);//当前用户的费用信息
            List<OutptCostDTO> outptCostDTOS = outptCostDAO.queryDrugMaterialListByIds(queryParam);

            //统计优惠总金额
            BigDecimal realityPrice = new BigDecimal(0);//优惠后总费用
            List<String> ids = new ArrayList<String>();//修改费用状态费用id
            List<PharOutReceiveDetailDO> pharOutReceiveDetailDOList = new ArrayList<PharOutReceiveDetailDO>();//领药申请明细参数接收
            Map<String, Map<String, Object>> pharOutReceiveMap = new HashMap<String, Map<String, Object>>();
            List<String> outptPrescribeIds = new ArrayList<String>();//存储处方id

            // 3、 校验药品、材料库存，并生成领药申请单明细
            Map<String, Object> tempMap = this.checkStockAndCreatePharOutReceiveDetail(outptCostDTOList, outptCostDTOS, hospCode);
            realityPrice = (BigDecimal) tempMap.get("realityPrice");
            ids = (List<String>) tempMap.get("ids");
            pharOutReceiveDetailDOList = (List<PharOutReceiveDetailDO>) tempMap.get("pharOutReceiveDetailDOList");
            pharOutReceiveMap = (Map<String, Map<String, Object>>) tempMap.get("pharOutReceiveMap");
            outptPrescribeIds = (List<String>) tempMap.get("outptPrescribeIds");

            OutptSettleDTO outptSettleDTO1 = outptSettleDAO.selectByPrimaryKey(settleId);//获取本次结算费用信息
            // 4、 校验个人支付金额，是否与本次结算的费用一致，不一致表示费用出现了问题
            // 2022年1月5日11:01:11 退费重收时默认 将优惠后金额保留到2位小数，
            if (outptVisitDTO.getTfcsMark() != null && "tfcs".equals(outptVisitDTO.getTfcsMark())) {
                realityPrice = realityPrice.setScale(2, BigDecimal.ROUND_HALF_DOWN); // 将计算后的优惠后总金额自动保留两位小数， 不四舍五入
            }
            if (!BigDecimalUtils.equals(realityPrice, outptSettleDTO1.getRealityPrice())) {
                 throw new AppException("支付失败，该患者费用信息错误，请刷新后重试。");
            }

            // 使用一卡通  start ========================
            // 校验是否使用一卡通，校验一卡通余额，档案id，卡状态
            if (cardPrice == null) {
                cardPrice = new BigDecimal(0);
            }
            BigDecimal tempCardPrice = new BigDecimal(0);
            if (cardNo != null) { // 门诊划价收费时，页面传入的一卡通账号为空，没有使用一卡通
                Map<String, Object> map = new HashMap<>();
                map.put("code", "SF_YKTCZ");
                map.put("hospCode", hospCode);
                WrapperResponse<SysParameterDTO> wr = sysParameterService.getParameterByCode(map);
                if (wr.getData().getValue() != null && ("2".equals(wr.getData().getValue()) || "4".equals(wr.getData().getValue()) || "6".equals(wr.getData().getValue()) || "8".equals(wr.getData().getValue()))) {
                    // 1、查询一卡通的余额是否足够，且状态是否为正常
                    BaseCardRechargeChangeDTO dto = new BaseCardRechargeChangeDTO();
                    dto.setHospCode(hospCode);
                    dto.setCardNo(cardNo);
                    BaseCardRechargeChangeDTO baseCardRechargeChangeDTO = null;
                    if (BigDecimalUtils.greaterZero(cardPrice) || (cardNo != null && !cardNo.equals(""))) {
                        baseCardRechargeChangeDTO = baseCardRechargeChangeDAO.getBaseCardRechargeChangeDTO(dto);
                    }

                    // 2、需要校验当前挂号的档案id与卡的档案id一致，
                    if (baseCardRechargeChangeDTO == null && BigDecimalUtils.greaterZero(cardPrice)) {
                        throw new AppException("结算支付时查询就诊卡出错，请联系管理员");
                    }
                    if (baseCardRechargeChangeDTO != null && BigDecimalUtils.greaterZero(cardPrice)) {
                        if (baseCardRechargeChangeDTO.getProfileId() == null || !baseCardRechargeChangeDTO.getProfileId().equals(profileId)) {
                            throw new AppException("结算支付时查询就诊卡档案id与当前患者档案id不一致，请联系管理员");
                        }
                        if (baseCardRechargeChangeDTO.getCardStatusCode() == null || !"0".equals(baseCardRechargeChangeDTO.getCardStatusCode())) {
                            throw new AppException("结算支付时查询就诊卡状态非正常状态，不能使用该卡");
                        }
                        if (baseCardRechargeChangeDTO.getAccountBalance() == null || BigDecimalUtils.less(baseCardRechargeChangeDTO.getAccountBalance(), cardPrice)){
                            throw new AppException("结算支付时查询就诊卡余额小于需要支付的金额，不能支付");
                        }
                        if (!BigDecimalUtils.equals(baseCardRechargeChangeDTO.getAccountBalance(), baseCardRechargeChangeDTO.getEndBalance())) {
                            throw new AppException("结算支付时查询就诊卡余额与上一次异动后金额不一致，不能支付");
                        }
                        // 更新一卡通充值、消费异动表，更新一卡通主表余额
                        baseCardRechargeChangeDTO.setId(SnowflakeUtils.getId());
                        baseCardRechargeChangeDTO.setStatusCode("8"); // 卡异动状态 8： 消费
                        baseCardRechargeChangeDTO.setPayCode(null);  // 支付方式
                        baseCardRechargeChangeDTO.setPrice(BigDecimalUtils.negate(cardPrice));
                        baseCardRechargeChangeDTO.setStartBalance(baseCardRechargeChangeDTO.getEndBalance());
                        baseCardRechargeChangeDTO.setStartBalanceEncryption(baseCardRechargeChangeDTO.getStartBalanceEncryption());
                        baseCardRechargeChangeDTO.setEndBalance(BigDecimalUtils.subtract(baseCardRechargeChangeDTO.getStartBalance(), cardPrice));
                        baseCardRechargeChangeDTO.setEndBalanceEncryption(null);
                        baseCardRechargeChangeDTO.setSettleType("03");
                        baseCardRechargeChangeDTO.setSettleId(settleId);
                        baseCardRechargeChangeDTO.setCrteId(userId);
                        baseCardRechargeChangeDTO.setCrteName(userName);
                        baseCardRechargeChangeDTO.setCrteTime(new Date());
                        // 新增一卡通消费异动
                        int temp1 = baseCardRechargeChangeDAO.insertBaseCardRechargeChange(baseCardRechargeChangeDTO);
                        Map<String, Object> baseCardMap = new HashMap<>();
                        baseCardMap.put("hospCode", hospCode);
                        baseCardMap.put("profileId", profileId);
                        baseCardMap.put("cardNo", cardNo);
                        baseCardMap.put("accountBalance", baseCardRechargeChangeDTO.getEndBalance());
                        // 更新一卡通主表余额
                        int temp2 = baseCardRechargeChangeDAO.updateCardAccountBalance(baseCardMap);
                        if (temp1 <= 0 || temp2 <= 0) {
                            throw new AppException("保存一卡通异动异常，未写入数据，请联系管理员");
                        }
                        tempCardPrice = cardPrice;
                    }
                }
            }
            // 使用一卡通  end ========================

            // 5、 保存结算信息（支付方式与各方式金额）
            boolean isChange = this.saveOutptPays(outptPayDOList, hospCode, settleId, visitId, outptSettleDTO1, tempCardPrice);
            if (isChange) {
                throw new AppException("支付失败；本次账户支付金额小于当前按医院配置的舍入规则计算后的应付金额！");
            }

            // 6、 根据费用信息修改本次结算的费用状态
            Map<String, Object> costParam = new HashMap<String, Object>();
            costParam.put("settleCode", Constants.JSZT.YIJS);//费用状态 = 已结算
            costParam.put("ids", ids);//费用id
            outptCostDAO.editCostSettleCodeByIDS(costParam);

            // 7、 修改门诊结算表此次结算信息状态
            OutptSettleDO outptSettleDO = new OutptSettleDO();//修改参数
            outptSettleDO.setId(settleId);//结算id
            SysParameterDO sysParameterDO = getSysParameter(hospCode, Constants.HOSPCODE_DISCOUNTS_KEY);//获取当前医院优惠配置
            BigDecimal ssje = new BigDecimal(0);
            if (outptVisitDTO.getTfcsMark() != null && "tfcs".equals(outptVisitDTO.getTfcsMark())) {
                ssje = BigDecimalUtils.subtract(realityPrice, outptVisitDTO.getTruncPrice());
            } else {
                ssje = BigDecimalUtils.subtract(realityPrice, BigDecimalUtils.rounding(sysParameterDO.getValue(), realityPrice));
            }
            outptSettleDO.setCardPrice(cardPrice); // 一卡通支付金额
            outptSettleDO.setActualPrice(BigDecimalUtils.subtract(ssje, cardPrice));//实收金额
            outptSettleDO.setIsSettle(Constants.SF.S);//是否结算 = 是
            outptSettleDO.setSourcePayCode("0");  // 0:HIS 1:微信  2：支付宝   3：自助机
            outptSettleDO.setOnlinePay(Constants.SF.F); // 是否在线支付：否
            // 2021年11月25日20:13:53  添加挂账金额，挂账支付方式必须为8
            for (OutptPayDO dto : outptPayDOList) {
                if ( "8".equals(dto.getPayCode())) {
                    outptSettleDO.setCreditPrice(dto.getPrice());
                }
            }
            outptSettleDAO.updateByPrimaryKeySelective(outptSettleDO);//修改结算状态
            // 7.1 结算后需要将结算单号返回给前端
            Map<String, Object> settleMap = new HashMap<>();
            settleMap.put("id", settleId);
            settleMap.put("hospCode", hospCode);
            OutptSettleDTO dto = outptSettleDAO.getById(settleMap);
            settleNo = dto.getSettleNo();

            // 8、 修改处方表结算信息
            if (!outptPrescribeIds.isEmpty()) {
                Map<String, Object> outptPrescribeParam = new HashMap<String, Object>();
                outptPrescribeParam.put("hospCode", hospCode);//医院编码
                outptPrescribeParam.put("ids", outptPrescribeIds);//处方ids
                outptPrescribeParam.put("settleId", settleId);//结算id
                outptPrescribeParam.put("isSettle", Constants.SF.S);//是否结算 = 是
                outptCostDAO.updateOutptPrescribeByIds(outptPrescribeParam);
            }

            // 9、判断是否需要生成发票信息
            if (outptSettleDTO.getIsInvoice()) {
                outinInvoiceDTO.setVisitId(visitId);
                outinInvoiceDTO.setSettleId(settleId);
            }

            // 10、 取最佳领药窗口，生成领药申请单（主单），保存领药申请单与领药申请单详单
            this.savePharOutReceive(hospCode, visitId, depId, userId, userName, settleId,
                    pharOutReceiveMap, outptCostDTOList, pharOutReceiveDetailDOList);
            // 10、1 更新领药明细表中原费用id 官红强 2021年4月27日11:31:47
            // 10、1、1 根据当前结算id查询old_cost_id不为空的数据（为空表示第一次领药，不为空表示已经收费了，部分退费后再次收费）
            boolean isMark = false;
            for (OutptCostDTO costDTO : outptCostDTOList) {
                if (costDTO.getOldCostId() != null && !"".equals(costDTO.getOldCostId())) {
                    isMark = true;
                }
            }
            if (isMark) {
//                outptCostDAO.updatePharOutDistributeDetailsOldCostId(outptCostDTOList);
            }

            // 11、 获取个人、费用信息给前端做票据打印
            Map<String, Object> outInvoiceParam = new HashMap<String, Object>();
            outInvoiceParam.put("hospCode", hospCode);
            OutinInvoiceDTO outinInvoiceDTO1 = new OutinInvoiceDTO();
            outinInvoiceDTO1.setHospCode(hospCode);//医院编码
            outinInvoiceDTO1.setInvoiceType(Constants.PJLX.MZ);//发票类型 = 门诊
            outinInvoiceDTO1.setSettleId(settleId);//结算id
            outInvoiceParam.put("outinInvoiceDTO", outinInvoiceDTO1);
            // 12、 如果是医保病人，做医保结算操作。
            Integer patientValueCode = Integer.parseInt(outptVisitDTO.getPatientCode());
            if (patientValueCode > 0) {
                // 查询当前医院医保是配置的统一支付平台还是医保服务
                this.saveOutptSettle_YB(outptVisitDTO, settleId, visitId, userId, userName, outptCostDTOList, hospCode, code, outptSettleDTO1);
            }
            // 13、 将优惠发票总金额返回给前端（优惠后总金额）
            outptVisitDTO.setRealityPrice(realityPrice);
            outinInvoiceList = outinInvoiceService.queryItemInfoByParams(outInvoiceParam).getData();

            outptVisitDTO.setReceiveName(outinInvoiceDTO.getReceiveName());
            outptVisitDTO.setPrefix(outinInvoiceDTO.getPrefix());
            // 发票不分单返回发票号
            outptVisitDTO.setInvoiceNo(outinInvoiceDTO.getCurrNo());
        } catch (Exception e) {
            throw e;
        } finally {
            redisUtils.del(key);//删除结算key
        }

        SysParameterDO sys = getSysParameter(hospCode, "MZFP_FDFS");
        String SFFD = "1";
        if (sys != null && !"".equals(sys.getValue())) {
            SFFD = sys.getValue();
        }

        JSONObject result = new JSONObject();
        result.put("outptVisit", outptVisitDTO);//个人信息
        result.put("outinInvoice", outinInvoiceList);//费用统计信息
        result.put("outinInvoiceDTO", outinInvoiceDTO);//费用统计信息
        result.put("settleNo", settleNo);
        result.put("SFFD", SFFD);
        return WrapperResponse.success("支付成功。", result);
    }

    /**
     * @Description: 取最佳领药窗口，生成领药申请单（主单），保存领药申请单与领药申请单详单
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/2/8 11:32
     * @Return
     */
    private void savePharOutReceive(String hospCode, String visitId, String depId, String userId, String userName, String settleId,
                                    Map<String, Map<String, Object>> pharOutReceiveMap, List<OutptCostDTO> outptCostDTOList, List<PharOutReceiveDetailDO> pharOutReceiveDetailDOList) {
        Map<String, String> bwParam = new HashMap<String, String>();
        bwParam.put("hospCode", hospCode);//医院编码
        bwParam.put("isValid", Constants.SF.S);//是否有效
        List<PharOutReceiveDO> pharOutReceiveDOList = new ArrayList<>();

        for (String pharId : pharOutReceiveMap.keySet()) {
            //获取最佳领药窗口
            bwParam.put("pharId", pharId);//药房id
            List<Map<String, Object>> windosList = outptCostDAO.queryShortcutWindows(bwParam);
            if (windosList == null || windosList.isEmpty()) {
                String deptName = outptCostDAO.queryBaseDeptById(bwParam);
                throw new AppException("【" + deptName + "】未找到发药窗口");
            }
            //生成领药申请单
            PharOutReceiveDO pharOutReceiveDO = new PharOutReceiveDO();
            pharOutReceiveDO.setId((String) pharOutReceiveMap.get(pharId).get("id"));//id
            pharOutReceiveDO.setHospCode(hospCode);//医院编码
            pharOutReceiveDO.setVisitId(visitId);//就诊id
            pharOutReceiveDO.setSettleId(settleId);//结算id
            pharOutReceiveDO.setPharId(pharId);//发药药房ID
            pharOutReceiveDO.setWindowId((String) windosList.get(0).get("id"));//发药窗口
            pharOutReceiveDO.setTotalPrice((BigDecimal) pharOutReceiveMap.get(pharId).get("totalPrice"));//总金额
            Boolean isDist = Constants.SF.F.equals(outptCostDTOList.get(0).getIsDist());//判断是否已发药
            pharOutReceiveDO.setStatusCode(Constants.LYZT.QL);//发药状态
            pharOutReceiveDO.setDeptId((String) pharOutReceiveMap.get(pharId).get("sourceDeptId"));//申请科室
            pharOutReceiveDO.setCrteId(userId);//创建人
            pharOutReceiveDO.setCrteName(userName);//创建人姓名
            pharOutReceiveDO.setCrteTime(new Date());//创建时间
            pharOutReceiveDOList.add(pharOutReceiveDO);
        }

        if (!pharOutReceiveDOList.isEmpty() || !pharOutReceiveDetailDOList.isEmpty()) {
            //生成领药申请单
//            Map<String, Object> pharOutReceiveParam = new HashMap<String, Object>();
//            pharOutReceiveParam.put("hospCode", hospCode);//医院编码
//            pharOutReceiveParam.put("pharOutReceiveDOList", pharOutReceiveDOList);
//            pharOutReceiveService_consumer.batchInsert(pharOutReceiveParam);
//            pharOutReceiveParam.put("pharOutReceiveDetailDOList", pharOutReceiveDetailDOList);
//            pharOutReceiveDetailService_consumer.batchInsert(pharOutReceiveParam);
            if (ObjectUtil.isNotEmpty(pharOutReceiveDOList)) {
                outptCostDAO.batchPharOutReceiveInsert(pharOutReceiveDOList);
            }
            if (ObjectUtil.isNotEmpty(pharOutReceiveDetailDOList)) {
                outptCostDAO.batchPharOutReceiveDetailInsert(pharOutReceiveDetailDOList);
            }
        }
    }

    /**
     * @Description: 保存支付方式（结算）
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/2/8 11:19
     * @Return
     */
    private boolean saveOutptPays(List<OutptPayDO> outptPayDOList, String hospCode, String settleId, String visitId, OutptSettleDTO outptSettleDTO1, BigDecimal cardPrice) {
        boolean isChange = false;
        List<OutptPayDO> outptPayDOS = new ArrayList<OutptPayDO>();
        BigDecimal actualPrice = new BigDecimal(0);
        if (outptPayDOList != null && !outptPayDOList.isEmpty()) {
            for (OutptPayDO outptPayDO : outptPayDOList) {
                if (StringUtils.isNotEmpty(outptPayDO.getPayCode()) && outptPayDO.getPrice() != null) {
                    outptPayDO.setId(SnowflakeUtils.getId());//id
                    outptPayDO.setHospCode(hospCode);//医院编码
                    outptPayDO.setSettleId(settleId);//结算id
                    outptPayDO.setVisitId(visitId);//就诊id
                    outptPayDO.setServicePrice(outptPayDO.getServicePrice() != null ? outptPayDO.getServicePrice() : new BigDecimal(0));//手续费
                    actualPrice = BigDecimalUtils.add(actualPrice, outptPayDO.getPrice());
                    outptPayDOS.add(outptPayDO);
                }
            }
        }

        actualPrice = BigDecimalUtils.add(actualPrice, cardPrice); // 实际支付金额加上一卡通扣除金额
        //BigDecimal ssje = BigDecimalUtils.subtract(outptSettleDTO1.getSelfPrice(), outptSettleDTO1.getTruncPrice()); // 实收金额= 个人自付金额-舍人金额
        //判断 实收金额 >= 需支付金额
        int greater = BigDecimalUtils.compareTo(outptSettleDTO1.getSelfPrice(), actualPrice);
        //int greater = BigDecimalUtils.compareTo(ssje, actualPrice);
        if (greater > 0) {
            isChange = true;
        }
        //有退款（找零）费用
        if (greater < 0) {
            //相差值(实收金额-应收金额)
            BigDecimal dif = BigDecimalUtils.subtract(outptSettleDTO1.getSelfPrice(), actualPrice);
            // ============================官红强修改  2021年1月8日15:25:42=====
            // 有退款，不再存储负数，直接将现金支付金额减去退款金额保存，例如：应收180，现金付100，支付宝付100，那么保存时直接在现金上减去20 ，保存现金支付金额为80=================
            for (int i = 0; i < outptPayDOS.size(); i++) {
                // 将现金支付金额减去退款金额
                if (outptPayDOS.get(i).getPayCode().equals(Constants.ZFFS.XJ)) {
                    outptPayDOS.get(i).setPrice(BigDecimalUtils.add(outptPayDOS.get(i).getPrice(), dif));
                    break;
                }
            }
            // ============================官红强修改  2021年1月8日15:25:42===========================================
        }
        //批量保存结算信息
        if (!ListUtils.isEmpty(outptPayDOS)) {
            outptPayDAO.batchInsertOutptPay(outptPayDOS);
        }
        return isChange;
    }

    /**
     * @Description: 校验费用明细对应的药品或材料是否有库存，有库存的情况下生成领药申请详单
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/2/8 10:36
     * @Return
     */
    private Map<String, Object> checkStockAndCreatePharOutReceiveDetail(List<OutptCostDTO> outptCostDTOList, List<OutptCostDTO> outptCostDTOS, String hospCode) {
        List<String> outptPrescribeIds = new ArrayList<String>();//存储处方id
        Map<String, Map<String, Object>> pharOutReceiveMap = new HashMap<String, Map<String, Object>>();
        List<PharOutReceiveDetailDO> pharOutReceiveDetailDOList = new ArrayList<PharOutReceiveDetailDO>();//领药申请明细参数接收
        BigDecimal realityPrice = new BigDecimal(0);//优惠后总费用
        List<String> ids = new ArrayList<String>();//修改费用状态费用id

        for (OutptCostDTO outptCostDTO : outptCostDTOList) {
            //判断是否是处方费用，记录处方费用
            if (StringUtils.isNotEmpty(outptCostDTO.getOpId()) && !outptPrescribeIds.contains(outptCostDTO.getOpId())) {
                outptPrescribeIds.add(outptCostDTO.getOpId());
            }
            // 未发药才生成发药单
            if (StringUtils.isNotEmpty(outptCostDTO.getIsDist()) && "0".equals(outptCostDTO.getIsDist())) {
                //药品材料检查库存
                if (Constants.XMLB.YP.equals(outptCostDTO.getItemCode()) || Constants.XMLB.CL.equals(outptCostDTO.getItemCode())) {
                    //常规和出院带药
                    if (Constants.YYXZ.CG.equals(outptCostDTO.getUseCode()) || Constants.YYXZ.CYDY.equals(outptCostDTO.getUseCode())) {
                        //校验库存数量
                        Map<String, String> mapParameter = this.getParameterValue(outptCostDTO.getHospCode(), new String[]{"MZYS_CF_WJSFYKC"});
                        String wjsykc = MapUtils.getVS(mapParameter, "MZYS_CF_WJSFYKC", "24");
                        OutptPrescribeDetailsDTO outptPrescribeDetailsDTO = new OutptPrescribeDetailsDTO();
                        outptPrescribeDetailsDTO.setHospCode(outptCostDTO.getHospCode());
                        outptPrescribeDetailsDTO.setItemId(outptCostDTO.getItemId());
                        outptPrescribeDetailsDTO.setPharId(outptCostDTO.getPharId());
                        outptPrescribeDetailsDTO.setTotalNum(outptCostDTO.getTotalNum());
                        outptPrescribeDetailsDTO.setNumUnitCode(outptCostDTO.getNumUnitCode());
                        outptPrescribeDetailsDTO.setWjsykc(wjsykc);
                        outptPrescribeDetailsDTO.setCostId(outptCostDTO.getId());
                        //判断库存
                        if (ListUtils.isEmpty(outptDoctorPrescribeDAO.checkStock(outptPrescribeDetailsDTO))) {
                            throw new AppException(outptCostDTO.getItemName() + ":未指定药房或指定药房库存不足");
                        }

                        //根据药房生成领药申请数据
                        if (!pharOutReceiveMap.containsKey(outptCostDTO.getPharId())) {
                            Map<String, Object> receiveMap = new HashMap<String, Object>();
                            receiveMap.put("id", SnowflakeUtils.getId());//领药申请单id
                            receiveMap.put("totalPrice", outptCostDTO.getRealityPrice());//总金额
                            receiveMap.put("sourceDeptId", outptCostDTO.getSourceDeptId());//申请科室ID
                            pharOutReceiveMap.put(outptCostDTO.getPharId(), receiveMap);
                        } else {
                            BigDecimal price = (BigDecimal) pharOutReceiveMap.get(outptCostDTO.getPharId()).get("totalPrice");
                            pharOutReceiveMap.get(outptCostDTO.getPharId()).put("totalPrice", BigDecimalUtils.add(price, outptCostDTO.getRealityPrice()));
                        }

                        //生成领药申请单明细信息
                        outptCostDTOS.stream().forEach(outptCostDTO1 -> {
                            if (outptCostDTO1.getItemId().equals(outptCostDTO.getItemId())) {
                                PharOutReceiveDetailDO pharOutReceiveDetailDO = new PharOutReceiveDetailDO();
                                pharOutReceiveDetailDO.setId(SnowflakeUtils.getId());//id
                                pharOutReceiveDetailDO.setHospCode(hospCode);//医院编码
                                pharOutReceiveDetailDO.setOrId((String) pharOutReceiveMap.get(outptCostDTO.getPharId()).get("id"));//领药申请id
                                pharOutReceiveDetailDO.setOpId(outptCostDTO.getOpId());//处方id
                                pharOutReceiveDetailDO.setOpdId(outptCostDTO.getOpdId());//处方明细id
                                pharOutReceiveDetailDO.setCostId(outptCostDTO.getId());//费用id
                                pharOutReceiveDetailDO.setItemCode(outptCostDTO.getItemCode());//项目类型
                                pharOutReceiveDetailDO.setItemId(outptCostDTO.getItemId());//项目id
                                pharOutReceiveDetailDO.setItemName(outptCostDTO.getItemName());//项目名称
                                pharOutReceiveDetailDO.setSpec(outptCostDTO.getSpec());//规格
                                pharOutReceiveDetailDO.setDosage(outptCostDTO.getDosage());//剂量
                                pharOutReceiveDetailDO.setDosageUnitCode(outptCostDTO.getDosageUnitCode());//剂量单位


                                pharOutReceiveDetailDO.setUnitCode(outptCostDTO1.getUnitCode());//单位代码
                                pharOutReceiveDetailDO.setPrice(outptCostDTO1.getPrimaryPrice());//单价
                                pharOutReceiveDetailDO.setSplitRatio(outptCostDTO1.getSplitRatio());//拆分比
                                pharOutReceiveDetailDO.setSplitUnitCode(outptCostDTO1.getSplitUnitCode());//拆零单位代码（DW）
                                pharOutReceiveDetailDO.setSplitPrice(outptCostDTO1.getSplitPrice());//拆零单价
                                if (outptCostDTO1.getUnitCode().equals(outptCostDTO.getNumUnitCode())) {//原单位
                                    pharOutReceiveDetailDO.setNum(outptCostDTO.getTotalNum());//数量
                                    pharOutReceiveDetailDO.setSplitNum(BigDecimalUtils.multiply(outptCostDTO.getTotalNum(), outptCostDTO1.getSplitRatio()));//拆零数量
                                } else if (outptCostDTO1.getSplitUnitCode().equals(outptCostDTO.getNumUnitCode())) {//拆零单位
                                    pharOutReceiveDetailDO.setNum(BigDecimalUtils.divide(outptCostDTO.getTotalNum(), outptCostDTO1.getSplitRatio()));//数量
                                    pharOutReceiveDetailDO.setSplitNum(outptCostDTO.getTotalNum());//拆零数量
                                }
                                pharOutReceiveDetailDO.setTotalPrice(outptCostDTO.getTotalPrice());//总金额
                                pharOutReceiveDetailDO.setChineseDrugNum(outptCostDTO.getHerbNum());//中药付数
                                pharOutReceiveDetailDO.setUsageCode(outptCostDTO.getUsageCode());//用法代码（YF）
                                pharOutReceiveDetailDO.setUseCode(outptCostDTO.getUseCode());//用药性质代码（YYXZ）
                                //开单单位
                                pharOutReceiveDetailDO.setCurrUnitCode(outptCostDTO.getNumUnitCode());
                                pharOutReceiveDetailDOList.add(pharOutReceiveDetailDO);
                            }
                        });
                    }
                }
            }
            // i + 优惠后金额 = 本次结算总费用
            realityPrice = BigDecimalUtils.add(realityPrice, outptCostDTO.getRealityPrice());
            ids.add(outptCostDTO.getId());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("outptPrescribeIds", outptPrescribeIds);
        map.put("ids", ids);
        map.put("realityPrice", realityPrice);
        map.put("pharOutReceiveMap", pharOutReceiveMap);
        map.put("pharOutReceiveDetailDOList", pharOutReceiveDetailDOList);
        return map;
    }

    /**
     * @Description: 保存使用的发票信息(结算时，如果使用发票，则需要记录发票信息)
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/2/8 10:03
     * @Return
     */
    private void saveInvoiceDetail(OutinInvoiceDTO outinInvoiceDTO, String settleId, String hospCode, String visitId, String depId, BigDecimal realityPrice) {
        Map<String, Object> map = new HashMap<String, Object>();
        outinInvoiceDTO.setSettleId(settleId);//结算id
        map.put("hospCode", hospCode);
        map.put("outinInvoiceDTO", outinInvoiceDTO);
        OutinInvoiceDetailDO outinInvoiceDetailDO = outinInvoiceService.updateInvoiceStatus(map).getData();

        SysParameterDO sysParameterDO = getSysParameter(MapUtils.get(map, "hospCode"), Constants.HOSPCODE_DISCOUNTS_KEY);//获取当前医院优惠配置
        BigDecimal truncPrice = BigDecimalUtils.rounding(sysParameterDO.getValue(), realityPrice); //舌入金额

        //保存门诊结算发票表
        OutptSettleInvoiceDO outptSettleInvoiceDO = new OutptSettleInvoiceDO();
        outptSettleInvoiceDO.setId(SnowflakeUtils.getId());//id
        outptSettleInvoiceDO.setHospCode(hospCode);//医院编码
        outptSettleInvoiceDO.setSettleId(settleId);//结算id
        outptSettleInvoiceDO.setVisitId(visitId);//就诊id
        outptSettleInvoiceDO.setInvoiceId(outinInvoiceDTO.getId());//发票id
        outptSettleInvoiceDO.setInvoiceDetailId(outinInvoiceDetailDO.getId());//发票明细id
        outptSettleInvoiceDO.setInvoiceNo(String.valueOf(outinInvoiceDetailDO.getInvoiceNo()));//发票号码
        outptSettleInvoiceDO.setTotalPrice(BigDecimalUtils.subtract(realityPrice, truncPrice));//发票总金额
        outptSettleInvoiceDO.setIsMain(Constants.SF.S);//是否主单 = 是
        outptSettleInvoiceDO.setStatusCode(Constants.ZTBZ.ZC);//状态标志 = 正常
        outptSettleInvoiceDO.setExecDeptId(depId);//执行科室id
        outptSettleInvoiceDAO.insert(outptSettleInvoiceDO);
        //保存门诊结算发票内容表
    }

    /**
     * @Description: 保存医保结算信息（结算、支付）
     * settleId: 结算id；  visitID： 就诊id； userId: 创建人id； userName： 创建人姓名；  outptCostDTOList：费用集合；hospCode: 医院编码
     * code:操作人编码
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/2/7 15:44
     * @Return
     */
    private void saveOutptSettle_YB(OutptVisitDTO outptVisitDTO, String settleId, String visitId, String userId, String userName,
                                    List<OutptCostDTO> outptCostDTOList, String hospCode, String code, OutptSettleDTO outptSettleDTO1) {
        Map<String, String> info = (Map<String, String>) JSONObject.parseObject(outptVisitDTO.getInsure()).get("info");
        //获取医保结算信息
        Map<String, Object> individualSettleParam = new HashMap<String, Object>();
        individualSettleParam.put("hospCode", outptVisitDTO.getHospCode());
        InsureIndividualSettleDTO insureIndividualSettleDTO = new InsureIndividualSettleDTO();
        insureIndividualSettleDTO.setHospCode(outptVisitDTO.getHospCode());//医院编码
        insureIndividualSettleDTO.setVisitId(outptVisitDTO.getId());//就诊id
        insureIndividualSettleDTO.setSettleId(settleId);//结算id
        individualSettleParam.put("insureIndividualSettleDTO", insureIndividualSettleDTO);
        insureIndividualSettleDTO = insureIndividualSettleService.findByCondition(individualSettleParam);
        if (insureIndividualSettleDTO == null) {
            throw new AppException("未获取到医保结算信息，请联系管理员。");
        }
        //修改医保结算表 insure_individual_settle；结算状态 = 结算
        InsureIndividualSettleDO insureIndividualSettleDO = new InsureIndividualSettleDO();
        insureIndividualSettleDO.setId(insureIndividualSettleDTO.getId());//id
        insureIndividualSettleDO.setSettleState(Constants.YBJSZT.JS);//结算状态 = 结算
        individualSettleParam.put("insureIndividualSettleDO", insureIndividualSettleDO);
        insureIndividualSettleService.updateByPrimaryKeySelective(individualSettleParam);
        InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
        insureIndividualVisitDTO.setVisitId(visitId);
        insureIndividualVisitDTO.setHospCode(hospCode);
        Map<String,Object> visitMap = new HashMap<>();
        visitMap.put("hospCode",hospCode);
        visitMap.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
        insureIndividualVisitDTO =  insureIndividualVisitService_consumer.selectInsureInfo(visitMap).getData();
        if(insureIndividualVisitDTO == null){
            throw new AppException("根据就诊未查询到医保就诊登记信息");
        }
        //查询医保机构信息
        Map<String, Object> configurationParam = new HashMap<String, Object>();
        configurationParam.put("hospCode", outptVisitDTO.getHospCode());//医院编码
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();

        insureConfigurationDTO.setHospCode(outptVisitDTO.getHospCode());
        insureConfigurationDTO.setOrgCode(insureIndividualVisitDTO.getMedicineOrgCode()); //医疗机构编码
        configurationParam.put("insureConfigurationDTO", insureConfigurationDTO);
        List<InsureConfigurationDTO> insureConfigurationDTOS = insureConfigurationService.queryAll(configurationParam).getData();
        if (insureConfigurationDTOS == null || insureConfigurationDTOS.isEmpty()) {
            throw new AppException("未找到医保配置信息。");
        }
        insureConfigurationDTO = insureConfigurationDTOS.get(0);
        //门诊医保明细 outpt_insure_pay
        OutptInsurePayDO outptInsurePayDO = new OutptInsurePayDO();
        outptInsurePayDO.setId(SnowflakeUtils.getId());//id
        outptInsurePayDO.setHospCode(outptVisitDTO.getHospCode());//医院编码
        outptInsurePayDO.setSettleId(settleId);//结算id
        outptInsurePayDO.setVisitId(outptVisitDTO.getId());//就诊id
        outptInsurePayDO.setTypeCode(null);//合同单位明细代码
        outptInsurePayDO.setOrgNo(insureConfigurationDTO.getOrgCode());//医保机构编码
        outptInsurePayDO.setOrgName(insureConfigurationDTO.getName());//医保机构名称
        outptInsurePayDO.setTotalPrice(insureIndividualSettleDTO.getInsurePrice());//医保报销总金额
        outptInsurePayDAO.insertSelective(outptInsurePayDO);

        //调用医保结算
        Map<String, Object> outptCostUploadAndTrialParam = new HashMap<String, Object>();
        outptCostUploadAndTrialParam.put("hospCode", outptVisitDTO.getHospCode());//医院编码
        outptCostUploadAndTrialParam.put("hospName", outptVisitDTO.getHospName());//医院名称
        outptCostUploadAndTrialParam.put("insureRegCode", info.get("regCode"));//医保机构编码
        outptCostUploadAndTrialParam.put("visitId", visitId);//门诊就诊id
        outptCostUploadAndTrialParam.put("settleId", settleId);//门诊结算id
        outptCostUploadAndTrialParam.put("outptVisitDTO",outptVisitDTO);
        outptCostUploadAndTrialParam.put("crteId", userId);//创建人id
        outptCostUploadAndTrialParam.put("code", outptVisitDTO.getCode());//创建人id
        outptCostUploadAndTrialParam.put("crteName", userName);//创建人姓名
        outptCostUploadAndTrialParam.put("fees", outptCostDTOList);
        outptCostUploadAndTrialParam.put("action", "settle");//操作 = 结算操作

        // add 读卡支付新增入参
        outptCostUploadAndTrialParam.put("isReadCardPay",outptVisitDTO.getIsReadCardPay());
        outptCostUploadAndTrialParam.put("bka895",outptVisitDTO.getBka895());
        outptCostUploadAndTrialParam.put("bka896",outptVisitDTO.getBka896());
        /**
         * 医保结算
         *
         */
        String isUnifiedPay = insureConfigurationDTO.getIsUnifiedPay();
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", outptVisitDTO.getHospCode());
//        map.put("code", "UNIFIED_PAY");
        map.put("visitId",visitId);
        map.put("id",visitId);
        String insureSettleId ="";
//        SysParameterDTO sys = sysParameterService_consumer.getParameterByCode(map).getData();
//        if (sys != null && Constants.SF.S.equals(sys.getValue())) {  // 调用统一支付平台
        if (StringUtils.isNotEmpty(isUnifiedPay) && "1".equals(isUnifiedPay)) {
            try{
                Map<String, Object> insureOutptResult = insureUnifiedPayOutptService_consumer.UP_2207(outptCostUploadAndTrialParam);
                Map<String, String> payinfo = (Map<String, String>) insureOutptResult.get("payinfo");
                List<Map<String, Object>> setldetailList = MapUtils.get(payinfo, "setldetailList");
                insureSettleId = MapUtils.get(payinfo,"setl_id"); // 结算id
                String balanceValue = MapUtils.get(payinfo,"INSURE_ACCT_PAY_PARAM");  // 海南地区开启个账参数判断
                String acctUsedFlag= MapUtils.get(payinfo,"acct_used_flag"); // 是否使用个人账户标志
                BigDecimal personalPrice =
                        BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(payinfo,"akb066")));
                if (!ListUtils.isEmpty(setldetailList)) {
                    InsureIndividualFundDTO insureIndividualFundDTO = null;
                    List<InsureIndividualFundDTO> fundDTOList = new ArrayList<>();
                    for (Map<String, Object> item : setldetailList) {
                        insureIndividualFundDTO = new InsureIndividualFundDTO();
                        insureIndividualFundDTO.setId(SnowflakeUtils.getId());
                        insureIndividualFundDTO.setHospCode(hospCode);
                        insureIndividualFundDTO.setVisitId(visitId);
                        insureIndividualFundDTO.setInsureSettleId(insureSettleId);
                        insureIndividualFundDTO.setCrteName(userName);
                        insureIndividualFundDTO.setCrteId(userId);
                        insureIndividualFundDTO.setCrteTime(DateUtils.getNow());
                        insureIndividualFundDTO.setMibId(null);
                        insureIndividualFundDTO.setFundName(null);
                        insureIndividualFundDTO.setIndiFreezeStatus(null);
                        // 基金支付类型
                        insureIndividualFundDTO.setFundPayType(MapUtils.get(item, "fund_pay_type"));

                        // 符合政策范围金额
                        String inscpScpAmt = DataTypeUtils.dataToNumString(MapUtils.get(item, "inscp_scp_amt"));
                        insureIndividualFundDTO.setInscpScpAmt(BigDecimalUtils.convert(inscpScpAmt));

                        // 本次可支付限额金额
                        String crtPaybLmtAmt = DataTypeUtils.dataToNumString(MapUtils.get(item, "crt_payb_lmt_amt"));
                        insureIndividualFundDTO.setCrtPaybLmtAmt(BigDecimalUtils.convert(crtPaybLmtAmt));

                        // 基金支付金额
                        String fundPayamt = DataTypeUtils.dataToNumString(MapUtils.get(item, "fund_payamt"));
                        insureIndividualFundDTO.setFundPayamt(BigDecimalUtils.convert(fundPayamt));

                        // 基金支付类型名称
                        insureIndividualFundDTO.setFundPayTypeName(MapUtils.get(item, "fund_pay_type_name"));
                        //结算过程信息
                        insureIndividualFundDTO.setSetlProcInfo(MapUtils.get(item, "setl_proc_info"));
                        fundDTOList.add(insureIndividualFundDTO);
                    }
                    map.put("fundDTOList",fundDTOList);
                    insureIndividualSettleService.insertBatchFund(map).getData();
                }
                /**
                 * 如果是结算操作 需要获取结算反参的：结算id 用来做结算取消操作
                 */
                String omsgid  =  MapUtils.get(payinfo,"omsgid"); // 交易信息(原交易)中的msgid,发送方报文ID
                String oinfno  =  MapUtils.get(payinfo,"oinfno"); // 交易信息(原交易)中的infno
                String medicalRegNo =  MapUtils.get(payinfo,"mdtrt_id"); // 医保返回的就诊id
                String clrOptins =  MapUtils.get(payinfo, "clr_optins");
                String clrWay =  MapUtils.get(payinfo, "clr_way");
                String clrType =  MapUtils.get(payinfo, "clr_type");

                Object object =  MapUtils.get(payinfo,"acct_pay");
                InsureIndividualSettleDO individualSettleDO  = new InsureIndividualSettleDO();
                Object balcObject = MapUtils.get(payinfo, "balc");// 结算后个人账户余额
                BigDecimal balcPrice = new BigDecimal(0.00);
                if(balcObject == null || (balcObject instanceof String && StringUtils.isEmpty(balcObject.toString()))){
                    individualSettleDO.setLastSettle(balcPrice);
                }else{
                    individualSettleDO.setLastSettle(BigDecimalUtils.convert((balcObject.toString())));
                }
                // 更新门诊结算表的个人账户支付
                OutptSettleDO outptSettleDO = new OutptSettleDO();
                outptSettleDO.setHospCode(hospCode);
                outptSettleDO.setId(settleId);
                if(object == null){
                    outptSettleDO.setAcctPay(new BigDecimal(0.00));
                }
                if(object instanceof String){
                    outptSettleDO.setAcctPay(BigDecimalUtils.convert(MapUtils.get(payinfo,"acct_pay")));
                }else{
                    outptSettleDO.setAcctPay(MapUtils.get(payinfo,"acct_pay"));
                }
                outptSettleDAO.updateByPrimaryKeySelective(outptSettleDO);
                // 结算前个人账户余额 =  个人账户支出+结算后个人账户余额
                individualSettleDO.setBeforeSettle(BigDecimalUtils.add(outptSettleDO.getAcctPay(),individualSettleDO.getLastSettle()));
                individualSettleDO.setInsureSettleId(insureSettleId);
                individualSettleDO.setMedicalRegNo(medicalRegNo);
                individualSettleDO.setHospCode(hospCode);
                individualSettleDO.setVisitId(visitId);
                individualSettleDO.setOinfno(oinfno);
                individualSettleDO.setOmsgid(omsgid);
                individualSettleDO.setClrWay(clrWay);
                individualSettleDO.setClrType(clrType);
                individualSettleDO.setClrOptins(clrOptins);
                if(Constants.SF.S.equals(balanceValue) && Constants.SF.S.equals(acctUsedFlag) ){
                    individualSettleDO.setPersonalPrice(personalPrice);
                }
                //  更新医保结算表的结算信息
                Map<String,Object> updateMap  = new HashMap<>();
                updateMap.put("hospCode",hospCode);
                updateMap.put("insureIndividualSettleDO",individualSettleDO);
                insureIndividualSettleService.updateByPrimaryKeySelective(updateMap);
                // 更新医保费用表的结算id
                InsureIndividualCostDTO insureIndividualCostDTO = new InsureIndividualCostDTO();
                insureIndividualCostDTO.setHospCode(hospCode);
                insureIndividualCostDTO.setVisitId(visitId);
                insureIndividualCostDTO.setInsureSettleId(insureSettleId);
                map.put("insureIndividualCostDTO",insureIndividualCostDTO);
                insureIndividualCostService_consumer.updateInsureSettleCost(map).getData();

                map.put("medicalRegNo",medicalRegNo);
                map.put("insureSettleId",insureSettleId);
                outptVisitDAO.updateInsuresumPatient(map);

            }catch (Exception e){
                if(StringUtils.isNotEmpty(insureSettleId)){
                    map.put("insureSettleId",insureSettleId);
                    List<Map<String, Object>> mapList = querySettleCost(map);
                    map.put("insureCostList",mapList);
                    insureUnifiedPayOutptService_consumer.UP_2208(map).getData();
                    updateCancelFeeSubmit(map);
                }
                //修改医保结算表 insure_individual_settle；结算状态 = 试算 回滚
                InsureIndividualSettleDO insureIndividualSettleDO1 = new InsureIndividualSettleDO();
                insureIndividualSettleDO.setId(insureIndividualSettleDTO.getId());//id
                insureIndividualSettleDO.setSettleState(Constants.YBJSZT.SS);//结算状态 = 试算
                individualSettleParam.put("insureIndividualSettleDO", insureIndividualSettleDO);
                insureIndividualSettleService.updateByPrimaryKeySelective(individualSettleParam);
                throw new RuntimeException(e.getMessage());
            }
        }
        else{
            /**
             * 调用长沙医保所需要用到的参数
             *
             */
            outptCostUploadAndTrialParam.put("phone",outptVisitDTO.getPhone());
            outptCostUploadAndTrialParam.put("code",code); // 操作员编码
            outptCostUploadAndTrialParam.put("outptSettleDTO1",outptSettleDTO1); // 结算信息保存单据号
            outptCostUploadAndTrialParam.put("saveFlag",'1'); // 结算标志
            outptService_consumer.setOutptCostUploadAndSettlement(outptCostUploadAndTrialParam);
        }
    }

    private List<Map<String, Object>> querySettleCost(Map<String,Object> map) {
        String hospCode = MapUtils.get(map,"hospCode");
        String visitId =  MapUtils.get(map,"visitId");
        String insureRegCode =  MapUtils.get(map,"visitId");
        //判断是否有传输费用信息
        Map<String,Object> insureCostParam = new HashMap<String,Object>();
        insureCostParam.put("hospCode",hospCode);//医院编码
        insureCostParam.put("statusCode",Constants.ZTBZ.ZC);//状态标志 = 正常
        insureCostParam.put("visitId",visitId);//就诊id
        insureCostParam.put("isMatch",Constants.SF.S);//是否匹配 = 是
        insureCostParam.put("transmitCode",Constants.SF.S);//传输标志 = 未传输
        insureCostParam.put("insureRegCode",insureRegCode);// 医保机构编码
        insureCostParam.put("isHospital",Constants.SF.F); // 区分门诊还是住院
        List<Map<String,Object>> insureCostList = insureIndividualCostService_consumer.queryOutptInsureCostByVisit(insureCostParam);
        if(ListUtils.isEmpty(insureCostList)){
            return new ArrayList<>();
        }
        return insureCostList;
    }
    /**
     * @Menthod getBasePreferentialType
     * @Desrciption  获取医院优惠列表
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/27 10:10
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse getBasePreferentialType(Map param) {
        return WrapperResponse.success(outptCostDAO.getBasePreferentialTypeList(param));
    }

    /**
     * @Menthod verifyCoupon
     * @Desrciption   计算优惠金额（切换优惠类别时，会重新计算优惠金额，并且将重新计算的优惠金额保存到数据库）
     * @param outptVisitDTO 患者费用参数
     * @Author Ou·Mr
     * @Date 2020/8/27 13:49
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse updateVerifyCoupon(OutptVisitDTO outptVisitDTO) {
        // 官红强 2021年1月26日20:47:19  切换优惠类别后需要重新计算优惠金额，此时需要存储数据库
        List<OutptCostDTO> outptCostDTOList = this.verifyCouponPrice(outptVisitDTO, 0);
        // 更新患者类型与优惠类型
        outptCostDAO.updateOutptVisitPreferentialType(outptVisitDTO);

        // 将计算的优惠金额存储起来
        outptCostDAO.updateOutptCosts(outptCostDTOList);
        return WrapperResponse.success(outptCostDTOList);
    }

    /**
     * @param outptCostDTOS
     * @Menthod updateList
     * @Desrciption 批量修改费用信息
     * @Author xingyu.xie
     * @Date 2020/8/29 10:53
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     **/

    @Override
    public WrapperResponse updateList(List<OutptCostDTO> outptCostDTOS) {
        return WrapperResponse.success(outptCostDAO.updateList(outptCostDTOS) > 0);
    }

    /**
     * @Menthod editOutinInvoice
     * @Desrciption 修改使用的发票状态
     * @param outinInvoiceDO 请求参数
     * @Author Ou·Mr
     * @Date 2020/8/31 17:33
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse editOutinInvoiceIsStatusCode(OutinInvoiceDO outinInvoiceDO) {
        return outinInvoiceDAO.updateOutinInvoice(outinInvoiceDO) > 0 ?
                WrapperResponse.success("操作成功。", null) : WrapperResponse.fail("操作失败。", null);
    }

    /**
     * @Menthod editOutinInvoice
     * @Desrciption 修改发票信息
     * @param outinInvoiceDTO 发票信息参数
     * @Author Ou·Mr
     * @Date 2020/8/31 19:17
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse editOutinInvoice(OutinInvoiceDTO outinInvoiceDTO) {
        OutinInvoiceDO outinInvoiceDO = outinInvoiceDAO.getOutinInvoiceById(outinInvoiceDTO);
        //校验发票段是否已用完
        if (Constants.PJSYZT.YW.equals(outinInvoiceDO.getTypeCode())) {
            return WrapperResponse.success("该发票段已用完，请选择新发票段。", -1);
        }
        //校验参数有效性
        int currNo = Integer.parseInt(outinInvoiceDTO.getCurrNo()); //调整后的发票号码
        int newCurrNo = Integer.parseInt(outinInvoiceDO.getCurrNo());//当前号码
        if (newCurrNo > currNo) {
            return WrapperResponse.success("发票信息发生变更，已更新发票信息，请根据需要再次调整。", outinInvoiceDO);
        }
        int endNo = Integer.parseInt(outinInvoiceDO.getEndNo());//终止号码
        if (currNo > endNo) {
            return WrapperResponse.success("调整后开始发票号不能大于该发票段终止号码。", -2);
        }
        //比如 调整号码 = 199；当前号码 = 193；199-193 = 6；6条明细
        int count = currNo - newCurrNo;
        if (count > 0) {
            List<OutinInvoiceDetailDO> outinInvoiceDetailDOList = new ArrayList<OutinInvoiceDetailDO>();
            for (int i = 0; i < count; i++) {
                OutinInvoiceDetailDO outinInvoiceDetailDO = new OutinInvoiceDetailDO();
                outinInvoiceDetailDO.setId(SnowflakeUtils.getId());//id
                outinInvoiceDetailDO.setHospCode(outinInvoiceDO.getHospCode());//医院编码
                outinInvoiceDetailDO.setInvoiceId(outinInvoiceDO.getId());//发票id
                outinInvoiceDetailDO.setInvoiceNo(String.valueOf(newCurrNo++));//发票号码
                outinInvoiceDetailDO.setStatusCode(Constants.PJMXSYZT.ZF);//使用状态代码 = 作废
                outinInvoiceDetailDO.setUseTime(new Date());//使用时间
                outinInvoiceDetailDO.setSettleId(null);//结算id，作废明细不需要结算id
                outinInvoiceDetailDOList.add(outinInvoiceDetailDO);
            }
            outinInvoiceDAO.insertOutinInvoiceDetailBatch(outinInvoiceDetailDOList);
        }
        outinInvoiceDO.setCurrNo(outinInvoiceDTO.getCurrNo());//设置当前号码
        outinInvoiceDO.setNum(endNo - currNo);//发票剩余数量
        return outinInvoiceDAO.updateOutinInvoice(outinInvoiceDO) == 1 ?
                WrapperResponse.success("操作成功。", 0) : WrapperResponse.fail("操作失败。", null);
    }

    /**
     * @Menthod: queryCharge()
     * @Desrciption: 收费查询
     * @Param: OutptSettleDTO--门诊结算DTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/9/2 14:19
     * @Return: cn.hsa.sys.PageDTO
     **/
    @Override
    public PageDTO queryCharge(OutptSettleDTO outptSettleDTO) {
        //校验时间格式
        String startTime = outptSettleDTO.getStartTime();
        String endTime = outptSettleDTO.getEndTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            sdf.parse(startTime);
        } catch (Exception e) {
            outptSettleDTO.setStartTime("");
        }
        try {
            sdf.parse(endTime);
        } catch (Exception e) {
            outptSettleDTO.setEndTime("");
        }
        //分页查询
        PageHelper.startPage(outptSettleDTO.getPageNo(), outptSettleDTO.getPageSize());
        List<OutptSettleDTO> outptSettleDTOS = outptSettleDAO.queryCharge(outptSettleDTO);
        return PageDTO.of(outptSettleDTOS);
    }

    /**
     * @Menthod queryBaseDept
     * @Desrciption  获取选择科室指定的药房科室id
     * @param baseDeptDrugStoreDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/9/18 15:31
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse queryBaseDept(BaseDeptDrugStoreDTO baseDeptDrugStoreDTO) {
        baseDeptDrugStoreDTO.setIsValid(Constants.SF.S);
        return WrapperResponse.success(outptCostDAO.queryBaseDept(baseDeptDrugStoreDTO));
    }

    /**
     * @Menthod queryIsSettleCost
     * @Desrciption 获取费用信息
     * @param outptCostDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/10/11 15:15
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse queryIsSettleCost(OutptCostDTO outptCostDTO) {
        List<OutptCostDTO> outptCostDTOList = outptCostDAO.findByCondition(outptCostDTO);
        //获取诊断信息
        OutptPrescribeDTO outptPrescribeDTO = new OutptPrescribeDTO();
        outptPrescribeDTO.setHospCode(outptCostDTO.getHospCode());//医院编码
        outptPrescribeDTO.setVisitId(outptCostDTOList.get(0).getVisitId());//就诊id
        List<OutptDiagnoseDTO> outptDiagnoseDTOList = outptDoctorPrescribeDAO.getOutptDiagnose(outptPrescribeDTO);
        JSONObject obj = new JSONObject();
        obj.put("outptCost", outptCostDTOList);
        obj.put("outptDiagnose", outptDiagnoseDTOList);
        return WrapperResponse.success(obj);
    }

    /**
     * @Menthod queryOutptSettleVisitPage
     * @Desrciption  查询已结算患者信息
     * @param outptVisitDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/12/7 22:34
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse queryOutptSettleVisitPage(OutptVisitDTO outptVisitDTO) {
        PageHelper.startPage(outptVisitDTO.getPageNo(), outptVisitDTO.getPageSize());
        List<OutptVisitDTO> outptVisitDTOList = outptCostDAO.queryOutptSettleVisitList(outptVisitDTO);
        return WrapperResponse.success(PageDTO.of(outptVisitDTOList));
    }

    /**
     * @Description: 根据页面设置的自定义优惠类型计算优惠价格
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/1/26 9:20
     * @Return
     */
    @Override
    public OutptVisitDTO customMoney(Map<String, Object> map) {
        OutptVisitDTO outptVisitDTO = MapUtils.get(map, "outptVisitDTO");
        outptVisitDTO.setHospCode(MapUtils.get(map, "hospCode"));
        Map<String, Object> param = new HashMap<>();
        param.put("hospCode", MapUtils.get(map, "hospCode"));
        param.put("visitId", outptVisitDTO.getId());
        param.put("preferentialTypeId", outptVisitDTO.getPreferentialTypeId());
        // 从数据库查询当前病人所有收费项目
        JSONObject obj = (JSONObject) this.queryOutptCostList(param).getData();
        Map<String, Object> outptCostMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : obj.entrySet()) {
            outptCostMap.put(entry.getKey(), entry.getValue());
        }

        // 1.从JSON对象中取出当前患者的所有门诊费用信息
        List<OutptCostDTO> outptCostDTOList = (List<OutptCostDTO>) outptCostMap.get("outptCost");
        // 每次计算前先算一次基础优惠
        // List<OutptCostDTO> outptCostDTOList = this.verifyCouponPrice(outptVisitDTO,0);
        // 需要返回给前端的数据,复制一份查询的数据
        List<OutptCostDTO> qtOutptCostDTOList = new ArrayList<>();
        // 2.需要自定义价格的患者费用信息
        List<OutptCostDTO> customOutptCostDTOList = outptVisitDTO.getOutptCostDTOList();
        // 3.从数据库查询的患者门诊费用信息中找出需要自定义价格的费用信息，不采用页面传输的数据，防止页面数据出错导致影响
        Map<String, String> tichuMap = new HashMap<>();
        for (int j = 0; j < customOutptCostDTOList.size(); j++) {
            tichuMap.put(customOutptCostDTOList.get(j).getId(), customOutptCostDTOList.get(j).getId());
        }
        for (int i = outptCostDTOList.size() - 1; i >= 0; i--) {
            if (!outptCostDTOList.get(i).getId().equals(tichuMap.get(outptCostDTOList.get(i).getId()))) {
                qtOutptCostDTOList.add(outptCostDTOList.get(i));
                outptCostDTOList.remove(i);
            }
        }
        // 按比例计算金额
        if (outptVisitDTO.getCustomYhlx().equals("1")) {
            // 如果不按原价计算
            if (outptVisitDTO.getRecoverOriginalCostRemark().equals(Constants.SF.F)) {
                for (int i = 0; i < outptCostDTOList.size(); i++) {
                    // 自定义优惠后总金额= 优惠后总金额X优惠比例
                    BigDecimal realityPrice = BigDecimalUtils.multiply(outptCostDTOList.get(i).getRealityPrice(), outptVisitDTO.getCustom());
                    // 四舍五入保留两位小数
                    realityPrice = realityPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
                    // 自定义优惠总金额=优惠总金额-自定义优惠总金额
                    BigDecimal preferentialPrice = BigDecimalUtils.subtract(outptCostDTOList.get(i).getRealityPrice(), realityPrice);
                    outptCostDTOList.get(i).setPreferentialPrice(preferentialPrice);
                    outptCostDTOList.get(i).setRealityPrice(realityPrice);
                }
            } else { // 按原金额的比例计算
                for (int i = 0; i < outptCostDTOList.size(); i++) {
                    // 自定义优惠后总金额= 优惠后总金额X优惠比例
                    BigDecimal realityPrice = BigDecimalUtils.multiply(outptCostDTOList.get(i).getTotalPrice(), outptVisitDTO.getCustom());
                    // 四舍五入保留两位小数
                    realityPrice = realityPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
                    // 自定义优惠总金额=优惠总金额-自定义优惠总金额
                    BigDecimal preferentialPrice = BigDecimalUtils.subtract(outptCostDTOList.get(i).getTotalPrice(), realityPrice);
                    outptCostDTOList.get(i).setLastRealityPrice(outptCostDTOList.get(i).getTotalPrice());
                    outptCostDTOList.get(i).setPreferentialPrice(preferentialPrice);
                    outptCostDTOList.get(i).setRealityPrice(realityPrice);
                }
            }

        } else { // 按总金额计算金额  需要校验费用不能有误差
            // 需要自定义价格的项目总价
            BigDecimal customTotalPrice = new BigDecimal(0);
            BigDecimal chajiaPrice = new BigDecimal(0);
            // 不按原价计算
            if (outptVisitDTO.getRecoverOriginalCostRemark().equals(Constants.SF.F)) {
                for (int i = 0; i < outptCostDTOList.size(); i++) {
                    customTotalPrice = BigDecimalUtils.add(customTotalPrice, outptCostDTOList.get(i).getRealityPrice());
                }
                // 将少收的钱按比例均摊到各个项目中所需要的比例
                BigDecimal bili = BigDecimalUtils.divide(outptVisitDTO.getCustom(), customTotalPrice);
                for (int i = 0; i < outptCostDTOList.size(); i++) {
                    // 自定义优惠后总金额= 优惠后总金额X优惠比例
                    BigDecimal realityPrice = BigDecimalUtils.multiply(outptCostDTOList.get(i).getRealityPrice(), bili);
                    // 四舍五入保留两位小数
                    realityPrice = realityPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
                    chajiaPrice = BigDecimalUtils.add(chajiaPrice, realityPrice);

                    // 自定义优惠总金额=优惠总金额-自定义优惠总金额
                    BigDecimal preferentialPrice = BigDecimalUtils.subtract(outptCostDTOList.get(i).getRealityPrice(), realityPrice);
                    outptCostDTOList.get(i).setPreferentialPrice(preferentialPrice);
                    outptCostDTOList.get(i).setRealityPrice(realityPrice);
                }
            } else {  // 按原价计算
                for (int i = 0; i < outptCostDTOList.size(); i++) {
                    customTotalPrice = BigDecimalUtils.add(customTotalPrice, outptCostDTOList.get(i).getTotalPrice());
                }
                // 将少收的钱按比例均摊到各个项目中所需要的比例
                BigDecimal bili = BigDecimalUtils.divide(outptVisitDTO.getCustom(), customTotalPrice);
                for (int i = 0; i < outptCostDTOList.size(); i++) {
                    // 自定义优惠后总金额= 优惠后总金额X优惠比例
                    BigDecimal realityPrice = BigDecimalUtils.multiply(outptCostDTOList.get(i).getTotalPrice(), bili);
                    // 四舍五入保留两位小数
                    realityPrice = realityPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
                    chajiaPrice = BigDecimalUtils.add(chajiaPrice, realityPrice);

                    // 自定义优惠总金额=优惠总金额-自定义优惠总金额
                    BigDecimal preferentialPrice = BigDecimalUtils.subtract(outptCostDTOList.get(i).getTotalPrice(), realityPrice);
                    outptCostDTOList.get(i).setLastRealityPrice(outptCostDTOList.get(i).getTotalPrice());
                    outptCostDTOList.get(i).setPreferentialPrice(preferentialPrice);
                    outptCostDTOList.get(i).setRealityPrice(realityPrice);
                }
            }

            // 处理差价问题，目前取的那一条数据加上差价
            chajiaPrice = BigDecimalUtils.subtract(outptVisitDTO.getCustom(), chajiaPrice);
            outptCostDTOList.get(0).setPreferentialPrice(BigDecimalUtils.subtract(outptCostDTOList.get(0).getPreferentialPrice(), chajiaPrice));
            outptCostDTOList.get(0).setRealityPrice(BigDecimalUtils.add(outptCostDTOList.get(0).getRealityPrice(), chajiaPrice));
        }
        qtOutptCostDTOList.addAll(outptCostDTOList);
        outptVisitDTO.setOutptCostDTOList(qtOutptCostDTOList);
        return outptVisitDTO;
    }

    /**
     * @Description: 保存自定义优惠价格，
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/1/26 15:00
     * @Return
     */
    @Override
    public boolean saveCustomMoney(Map<String, Object> map) {
        OutptVisitDTO outptVisitDTO = MapUtils.get(map, "outptVisitDTO");
        String hospCode = MapUtils.get(map, "hospCode"); //医院编码
        String visitId = outptVisitDTO.getId(); //就诊id

        //生成redis结算Key，医院编码 + 证件号码 + 划价收费KEY
        String key = new StringBuilder(hospCode).append(StringUtils.isEmpty(visitId) ? outptVisitDTO.getCertNo() : visitId)
                .append(Constants.OUTPT_FEES_REDIS_KEY).toString();
        //校验该用户是否在结算
        if (StringUtils.isNotEmpty(redisUtils.get(key))) {
            throw new AppException("该患者正在结算，不能自定义优惠价格");
        }
        try {
            redisUtils.set(key, outptVisitDTO.getId());
            // 1.查询当前病人是否已经结算，已结算不能修改
            OutptSettleDTO dto = outptSettleDAO.selectByPrimaryKey(outptVisitDTO.getId());
            // 2.重新计算一次自定义优惠
            OutptVisitDTO customOutptVisitDTO = null;
            if (dto == null || (dto != null && dto.getIsSettle() != null && Constants.SF.F.equals(dto.getIsSettle()))) {
                customOutptVisitDTO = this.customMoney(map);
            }
            // 3.更新项目优惠价格
            if (customOutptVisitDTO != null && customOutptVisitDTO.getOutptCostDTOList().size() > 0) {
                outptCostDAO.updateOutptCosts(customOutptVisitDTO.getOutptCostDTOList());
            }

        } catch (Exception e) {
            throw new AppException("保存自定义优惠价格出错，请联系管理员");
        } finally {
            redisUtils.del(key);//删除结算key
        }

        return true;
    }

    /**
     * @Method selectCheckInfo
     * @Desrciption  读取审批信息
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/2/1 19:31
     * @Return map
     **/
    @Override
    public Map<String, Object> selectCheckInfo(Map<String, Object> map) {
        return outptService_consumer.selectCheckInfo(map);
    }

    /**
     * @Desrciption  uploadCheckInfo 医院审批信息上报
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/2/1 19:28
     * @Return boolean
     **/
    @Override
    public Boolean uploadCheckInfo(Map<String, Object> map) {
        return outptService_consumer.uploadCheckInfo(map);
    }

    /**
     * @param map
     * @Desrciption uploadCheckInfo 门特病人取消结算以后 取消登记
     * @Param map
     * @Author fuhui
     * @Date 2021/2/1 19:28
     * @Return boolean
     */
    @Override
    public Boolean cancelRegister(Map<String, Object> map) {
        return outptService_consumer.cancelRegister(map);
    }

    /**
     * @Menthod verifyCouponPrice
     * @Desrciption  校验门诊划价费用优惠金额
     * @param outptVisitDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/27 21:19
     * @Return java.util.List<cn.hsa.module.outpt.fees.dto.OutptCostDTO>
     */
    private <T extends Object> T verifyCouponPrice(OutptVisitDTO outptVisitDTO, int type) {
        List<OutptCostDTO> costData = outptVisitDTO.getOutptCostDTOList();
        String hospCode = outptVisitDTO.getHospCode(); //医院编码
        String id = outptVisitDTO.getId(); //患者id
        String preferentialTypeId = outptVisitDTO.getPreferentialTypeId();//优惠类型id
        //查询当前患者处方数据
        Map<String, Object> opParam = new HashMap<String, Object>();
        opParam.put("hospCode", hospCode);
        String[] settleCodes = {Constants.JSZT.WJS, Constants.JSZT.YUJS};
        opParam.put("settleCodes", settleCodes);
        opParam.put("statusCode", Constants.ZTBZ.ZC);
        opParam.put("visitId", id);
        //查询处方费用
        List<OutptCostDTO> opCostList = outptCostDAO.queryOutptCostSourceidNotNUll(opParam);
        //查询其他费用
        List<OutptCostDTO> restCostList = outptCostDAO.queryOutptCostRest(opParam);
        if (restCostList != null && !restCostList.isEmpty()) {
            opCostList.addAll(restCostList);
        }
        if (opCostList != null && !opCostList.isEmpty()) {
            List<Map<String, Object>> opCostMapList = new ArrayList<Map<String, Object>>();
            opCostList.stream().forEach(outptCostDTO -> {
                Map<String, Object> item = new HashMap<String, Object>();
                item.put("id", outptCostDTO.getId());
                item.put("hospCode", hospCode);
                item.put("preferentialTypeId", preferentialTypeId);
                item.put("itemId", outptCostDTO.getItemId());
                item.put("itemCode", outptCostDTO.getItemCode());
                item.put("bfcId", outptCostDTO.getBfcId());
                item.put("type", Constants.PREFERENTIALTYPE.MZ);
                item.put("totalPrice", outptCostDTO.getPrice());
                opCostMapList.add(item);
            });
            Map<String, Object> basePreferentialParam = new HashMap<String, Object>();
            basePreferentialParam.put("hospCode", hospCode);
            basePreferentialParam.put("costList", opCostMapList);
            List<Map<String, Object>> resultCostLust = basePreferentialService.calculatPreferential(basePreferentialParam).getData();
            //根据优惠类型赋值优惠金额、优惠后金额
            for (int i = 0; i < opCostList.size(); i++) {
                String costId = opCostList.get(i).getId();//费用id
                for (Map<String, Object> item : resultCostLust) {
                    if (costId.equals(item.get("id"))) {
                        BigDecimal totalNum = opCostList.get(i).getTotalNum();//总数量
                        BigDecimal price = opCostList.get(i).getPrice(); //单价
                        BigDecimal preferentialPrice = (BigDecimal) item.get("preferentialPrice");//优惠金额
                        BigDecimal realityPrice = (BigDecimal) item.get("realityPrice");//优惠后金额
                        opCostList.get(i).setTotalPrice(BigDecimalUtils.multiply(totalNum, price));
                        opCostList.get(i).setPreferentialPrice(BigDecimalUtils.multiply(totalNum, preferentialPrice));//计算优惠金额
                        opCostList.get(i).setRealityPrice(BigDecimalUtils.multiply(totalNum, realityPrice));//计算优惠后金额
                        break;
                    }
                }
            }
        }

        //获取划价收费数据
        List<OutptCostDTO> outptCostDTOList = new ArrayList<OutptCostDTO>();
        if (costData != null && !costData.isEmpty()) {
            outptCostDTOList = costData.stream().filter(outptCostDTO ->
                    StringUtils.isEmpty(outptCostDTO.getSourceId()) && Constants.FYLYFS.ZJHJSF.equals(outptCostDTO.getSourceCode())
            ).collect(Collectors.toList());
            if (outptCostDTOList != null && !outptCostDTOList.isEmpty()) {
                Map<String, Object> outptCostParam = new HashMap<String, Object>();
                outptCostParam.put("hospCode", hospCode);//医院编码
                outptCostParam.put("items", outptCostDTOList);//当前用户的费用信息
                List<OutptCostDTO> outptCostList = outptCostDAO.queryDrugMaterialListByIds(outptCostParam);
                List<Map<String, Object>> costMapList = new ArrayList<Map<String, Object>>();
                for (int i = 0; i < outptCostDTOList.size(); i++) {
                    OutptCostDTO outptCostDTO = outptCostDTOList.get(i);
                    String itemId = outptCostDTO.getItemId(); //项目id
                    String itemCode = outptCostDTO.getItemCode(); //项目编码
                    String numUnitCode = outptCostDTO.getNumUnitCode(); //项目数量单位
                    BigDecimal totalNum = outptCostDTO.getTotalNum(); //项目总数量
                    for (OutptCostDTO outptCostDTO1 : outptCostList) {
                        // 官红强修改 2021年1月25日17:16:42   当项目与材料或药品id一致时会导致单价取错，除了匹配id，还需要匹配项目类别编码
                        if (outptCostDTO1.getItemId().equals(itemId) && outptCostDTO1.getItemCode().equals(itemCode)) {
                            if (Constants.XMLB.XM.equals(itemCode) || outptCostDTO1.getUnitCode().equals(numUnitCode)) {
                                //原单位
                                outptCostDTOList.get(i).setPrice(outptCostDTO1.getPrimaryPrice());//单价
                                outptCostDTOList.get(i).setTotalPrice(BigDecimalUtils.multiply(totalNum, outptCostDTO1.getPrimaryPrice()));//项目总金额
                            } else if (StringUtils.isNotEmpty(outptCostDTO1.getSplitUnitCode()) && outptCostDTO1.getSplitUnitCode().equals(outptCostDTO.getNumUnitCode())) {
                                //拆零单位
                                outptCostDTOList.get(i).setPrice(outptCostDTO1.getSplitPrice());//拆零单价
                                outptCostDTOList.get(i).setTotalPrice(BigDecimalUtils.multiply(totalNum, outptCostDTO1.getSplitPrice()));//项目总金额
                            } else {
                                //防止没有单位，没有单位就按原价格
                                outptCostDTOList.get(i).setPrice(outptCostDTO1.getPrimaryPrice());//单价
                                outptCostDTOList.get(i).setTotalPrice(BigDecimalUtils.multiply(totalNum, outptCostDTO1.getPrimaryPrice()));//项目总金额
                            }
                            String outptCostId = SnowflakeUtils.getId();
                            outptCostDTOList.get(i).setId(outptCostId);
                            outptCostDTOList.get(i).setCode(outptCostDTO1.getCode());
                            Map<String, Object> item = new HashMap<String, Object>();
                            item.put("id", outptCostId);
                            item.put("hospCode", hospCode);
                            item.put("preferentialTypeId", preferentialTypeId);
                            item.put("itemId", outptCostDTO.getItemId());
                            item.put("itemCode", outptCostDTO1.getItemCode());
                            item.put("bfcId", outptCostDTO1.getBfcId());
                            item.put("type", Constants.PREFERENTIALTYPE.MZ);
                            item.put("totalPrice", outptCostDTOList.get(i).getPrice());
                            costMapList.add(item);
                            break;
                        }
                    }
                }
                //计算优惠金额
                Map<String, Object> basePreferentialParam = new HashMap<String, Object>();
                basePreferentialParam.put("hospCode", hospCode);
                basePreferentialParam.put("costList", costMapList);
                List<Map<String, Object>> resultCostLust = basePreferentialService.calculatPreferential(basePreferentialParam).getData();
                //根据优惠类型赋值优惠金额、优惠后金额
                for (int i = 0; i < outptCostDTOList.size(); i++) {
                    String costId = outptCostDTOList.get(i).getId();//费用id
                    for (Map<String, Object> item : resultCostLust) {
                        if (costId.equals(item.get("id"))) {
                            BigDecimal totalNum = outptCostDTOList.get(i).getTotalNum();//总数量
                            BigDecimal preferentialPrice = (BigDecimal) item.get("preferentialPrice");//优惠金额
                            BigDecimal realityPrice = (BigDecimal) item.get("realityPrice");//优惠后金额
                            outptCostDTOList.get(i).setPreferentialPrice(BigDecimalUtils.multiply(totalNum, preferentialPrice));//计算优惠金额
                            outptCostDTOList.get(i).setRealityPrice(BigDecimalUtils.multiply(totalNum, realityPrice));//计算优惠后金额
                            break;
                        }
                    }
                }
            }
        }
        if (type == 0) {
            //合并返回
            if (opCostList == null) {
                opCostList = new ArrayList<OutptCostDTO>();
            }
            opCostList.addAll(outptCostDTOList);
            return (T) opCostList;
        } else {
            //分开返回
            Map<String, Object> result = new HashMap<>();
            result.put("prescriptionCostList", opCostList);//处方费用集合
            result.put("chargingCostList", outptCostDTOList);//划价费用集合
            return (T) result;
        }
    }

    /**
     * @Description: 直接取项目费用，不再根据优惠类型计算优惠
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/1/26 19:49
     * @Return
     */
    private <T extends Object> T customVerifyCouponPrice(OutptVisitDTO outptVisitDTO, int type) {
        List<OutptCostDTO> costData = outptVisitDTO.getOutptCostDTOList();
        String hospCode = outptVisitDTO.getHospCode(); //医院编码
        String id = outptVisitDTO.getId(); //患者id
        String preferentialTypeId = outptVisitDTO.getPreferentialTypeId();//优惠类型id
        //查询当前患者处方数据
        Map<String, Object> opParam = new HashMap<String, Object>();
        opParam.put("hospCode", hospCode);
        String[] settleCodes = {Constants.JSZT.WJS, Constants.JSZT.YUJS};
        opParam.put("settleCodes", settleCodes);
        opParam.put("statusCode", Constants.ZTBZ.ZC);
        opParam.put("visitId", id);
        // 获取系统参数 是否开启分处方结算 0: 不开启分处方结算;  1: 开启分处方结算
        Map<String, Object> isMergeParam = new HashMap<>();
        isMergeParam.put("hospCode", hospCode);
        isMergeParam.put("code", "SF_DIVIDED_PRRESCIBE");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(isMergeParam).getData();
        if(sysParameterDTO !=null && "1".equals(sysParameterDTO.getValue())) {
            List<String> opIds = outptVisitDTO.getOpIds(); // 处方id集合
            if (opIds != null && opIds.size() > 0) {
                opParam.put("opIds", opIds);
            }
        }
        //查询处方费用
        List<OutptCostDTO> opCostList = outptCostDAO.queryOutptCostSourceidNotNUll(opParam);
        //查询其他费用
        List<OutptCostDTO> restCostList = outptCostDAO.queryOutptCostRest(opParam);
        if (restCostList != null && !restCostList.isEmpty()) {
            opCostList.addAll(restCostList);
        }

        //获取划价收费数据
        List<OutptCostDTO> outptCostDTOList = new ArrayList<OutptCostDTO>();
        if (costData != null && !costData.isEmpty()) {
            outptCostDTOList = costData.stream().filter(outptCostDTO ->
                    StringUtils.isEmpty(outptCostDTO.getSourceId()) && Constants.FYLYFS.ZJHJSF.equals(outptCostDTO.getSourceCode())
            ).collect(Collectors.toList());
        }
        if (type == 0) {
            //合并返回
            if (opCostList == null) {
                opCostList = new ArrayList<OutptCostDTO>();
            }
            opCostList.addAll(outptCostDTOList);
            return (T) opCostList;
        } else {
            //分开返回
            Map<String, Object> result = new HashMap<>();
            result.put("prescriptionCostList", opCostList);//处方费用集合
            result.put("chargingCostList", outptCostDTOList);//划价费用集合
            return (T) result;
        }
    }

    /**
     * @Menthod getSysParameter
     * @Desrciption  查询sys_parameter配置表
     * @param hospCode 医院编码
     * @param code 参数代码
     * @Author Ou·Mr
     * @Date 2020/8/28 15:22
     * @Return cn.hsa.module.sys.parameter.entity.SysParameterDO
     */
    private SysParameterDO getSysParameter(String hospCode, String code) {
        SysParameterDTO sysParameterDTO = new SysParameterDTO();//查询条件
        sysParameterDTO.setHospCode(hospCode);//医院编码
        sysParameterDTO.setIsValid(Constants.SF.S);//是否有效；设置：是
        sysParameterDTO.setCode(code);//字典code
        Map param = new HashMap();
        param.put("hospCode", hospCode);
        param.put("sysParameterDTO", sysParameterDTO);
        List<SysParameterDTO> sysParameterDTOS = sysParameterService_consumer.queryAll(param).getData();
        if (sysParameterDTOS == null || sysParameterDTOS.isEmpty()) {
            return new SysParameterDO();
        }
        return sysParameterDTOS.get(0);
    }

    /**
     * @Menthod getOrderNo
     * @Desrciption  生成规则单据号
     * @param hospCode 医院编码
     * @param typeCode 规则标志Code
     * @Author Ou·Mr
     * @Date 2020/8/28 17:26
     * @Return java.lang.String 单据号
     */
    private String getOrderNo(String hospCode, String typeCode) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("hospCode", hospCode);
        param.put("typeCode", typeCode);
        WrapperResponse<String> orderNo = baseOrderRuleService_consumer.getOrderNo(param);
        return orderNo.getData();
    }

    /**
     * 获取系统参数
     * @param hospCode
     * @param code
     * @return
     */
    public Map<String, String> getParameterValue(String hospCode, String[] code) {
        List<SysParameterDTO> list = outptDoctorPrescribeDAO.getParameterValue(hospCode, code);
        Map<String, String> retMap = new HashMap<>();
        if (!MapUtils.isEmpty(list)) {
            for (SysParameterDTO hit : list) {
                retMap.put(hit.getCode(), hit.getValue());
            }
        }
        return retMap;
    }

    /**
     * @param outptVisitDTO
     * @Menthod saveOutptInsureVisit
     * @Desrciption 进行医保登记，保存医保个人就诊信息
     * @Author xingyu.xie
     * @Date 2021/2/22 10:04
     * @Return java.lang.Boolean
     **/
    @Override
    @Async
    public Boolean saveOutptInsureVisit(OutptVisitDTO outptVisitDTO) {
        Map<String, Object> insureIndividualVisitParam = new HashMap<String, Object>();
        insureIndividualVisitParam.put("hospCode", outptVisitDTO.getHospCode());
        insureIndividualVisitParam.put("param", outptVisitDTO);
        insureIndividualVisitService_consumer.addInsureIndividualVisit(insureIndividualVisitParam);
        return true;
    }

    @Override
    @Async
    public WrapperResponse saveOutptSettleMoneyDz(OutptVisitDTO outptVisitDTO) {

        // 查询医保登记信息
        InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
        insureIndividualVisitDTO.setHospCode(outptVisitDTO.getHospCode());
        insureIndividualVisitDTO.setVisitId(outptVisitDTO.getId());
        HashMap visitMap = new HashMap();
        visitMap.put("hospCode", outptVisitDTO.getHospCode());
        visitMap.put("insureIndividualVisitDTO", insureIndividualVisitDTO);
        if (insureIndividualVisitService_consumer.findByCondition(visitMap) == null) {
            throw new AppException("请先进行医保登记");
        }

        //生成redis结算Key，医院编码 + 证件号码 + 划价收费KEY
        String key = new StringBuilder(outptVisitDTO.getHospCode()).append(StringUtils.isEmpty(outptVisitDTO.getId()) ? outptVisitDTO.getCertNo() : outptVisitDTO.getId())
                .append(Constants.OUTPT_FEES_REDIS_KEY).toString();
        //redisUtils.del(key);
        if (StringUtils.isNotEmpty(redisUtils.get(key))) {
            return WrapperResponse.fail("划价收费提示：该患者正在别处结算；请稍后再试", null);
        }
        Map<String, Object> result = new HashMap<String, Object>();//返回结果
        try {
            redisUtils.set(key, outptVisitDTO.getId());
            //删除结算脏数据
            Map<String, String> settleParam = new HashMap<String, String>();
            settleParam.put("hospCode", outptVisitDTO.getHospCode()); //医院编码
            settleParam.put("visitId", outptVisitDTO.getId());//就诊id
            settleParam.put("isSettle", Constants.SF.F);//是否结算 = 否
            settleParam.put("statusCode", Constants.ZTBZ.ZC);//状态标志 = 正常
            outptSettleDAO.delOutptSettleByParam(settleParam);
            // List<OutptCostDTO> outptCostDTOList = this.verifyCouponPrice(outptVisitDTO,0);
            List<OutptCostDTO> outptCostDTOList = this.customVerifyCouponPrice(outptVisitDTO, 0);
            if (outptCostDTOList.size() != outptVisitDTO.getOutptCostDTOList().size()) {
                return WrapperResponse.fail("划价收费提示：该患者费用数量不一致；请刷新浏览器再试", null);
            }
            if (outptCostDTOList != null && !outptCostDTOList.isEmpty()) {
                Map<String, Object> outptCostParam = new HashMap<String, Object>();
                outptCostParam.put("hospCode", outptVisitDTO.getHospCode());//医院编码
                outptCostParam.put("items", outptCostDTOList);//当前用户的费用信息
                List<OutptCostDTO> outptCostList = outptCostDAO.queryDrugMaterialListByIds(outptCostParam);
                for (int i = 0; i < outptCostDTOList.size(); i++) {
                    OutptCostDTO outptCostDTO = outptCostDTOList.get(i);
                    String itemId = outptCostDTO.getItemId(); //项目id
                    for (OutptCostDTO outptCostDTO1 : outptCostList) {
                        if (outptCostDTO1.getItemId().equals(itemId)) {
                            outptCostDTOList.get(i).setCode(outptCostDTO1.getCode());
                            break;
                        }
                    }
                }
            }
            //判断人员信息是选择还是手动录入；处理患者费用
            String id = outptVisitDTO.getId(); //就诊id
            //人员信息修改操作
            outptCostDAO.editOutptVisitByKey(outptVisitDTO);//编辑操作
            //筛选处方费用，处方费用前端不能修改信息，所以这边只对新增费用做处理
            String settleId = SnowflakeUtils.getId();//结算id
            String finalId = id;
            List<OutptCostDTO> prescribeList = new ArrayList<OutptCostDTO>();//处方费用
            List<OutptCostDTO> costList = new ArrayList<OutptCostDTO>(); //划价费用
            outptCostDTOList.stream().forEach(outptCostDTO -> {
                outptCostDTO.setHospCode(outptVisitDTO.getHospCode());//医院编码
                outptCostDTO.setSettleCode(Constants.JSZT.YUJS);//结算状态代码；设置预结算状态
                outptCostDTO.setStatusCode(Constants.ZTBZ.ZC);//状态标志 = 正常
                outptCostDTO.setSettleId(settleId);//结算id
                //处方费用信息
                prescribeList.add(outptCostDTO);
                //药品材料检查库存
                if (Constants.XMLB.YP.equals(outptCostDTO.getItemCode()) || Constants.XMLB.CL.equals(outptCostDTO.getItemCode())) {
                    //常规和出院带药
                    if (Constants.YYXZ.CG.equals(outptCostDTO.getUseCode()) || Constants.YYXZ.CYDY.equals(outptCostDTO.getUseCode())) {
                        Map<String, String> mapParameter = this.getParameterValue(outptCostDTO.getHospCode(), new String[]{"MZYS_CF_WJSFYKC"});
                        String wjsykc = MapUtils.getVS(mapParameter, "MZYS_CF_WJSFYKC", "24");
                        OutptPrescribeDetailsDTO outptPrescribeDetailsDTO = new OutptPrescribeDetailsDTO();
                        outptPrescribeDetailsDTO.setHospCode(outptCostDTO.getHospCode());
                        outptPrescribeDetailsDTO.setItemId(outptCostDTO.getItemId());
                        outptPrescribeDetailsDTO.setPharId(outptCostDTO.getPharId());
                        outptPrescribeDetailsDTO.setTotalNum(outptCostDTO.getTotalNum());
                        outptPrescribeDetailsDTO.setNumUnitCode(outptCostDTO.getNumUnitCode());
                        outptPrescribeDetailsDTO.setWjsykc(wjsykc);
                        outptPrescribeDetailsDTO.setCostId(outptCostDTO.getId());

                        //判断库存
                        if (ListUtils.isEmpty(outptDoctorPrescribeDAO.checkStock(outptPrescribeDetailsDTO))) {
                            throw new AppException(outptCostDTO.getItemName() + ":未指定药房或指定药房库存不足");
                        }
                    }
                }
            });
            //修改处方费用信息（根据优惠类型可能优惠金额发生改变；仅修改：项目总金额、项目优惠金额、项目优惠后总金额、结算状态、状态标志）
            if (!prescribeList.isEmpty()) {
                outptCostDAO.batchEditCostPrice(prescribeList);
            }
            //计算划价费用
            BigDecimal realityPrice = new BigDecimal(0);//优惠后总费用
            BigDecimal totalPrice = new BigDecimal(0);//项目总费用
            outptVisitDTO.setRealityPrice(realityPrice);
            for (OutptCostDTO outptCostDTO : outptCostDTOList) {
                // i + 优惠后金额 = 优惠后总费用
                realityPrice = BigDecimalUtils.add(realityPrice, outptCostDTO.getRealityPrice());
                // i + 单价 = 项目总费用
                totalPrice = BigDecimalUtils.add(totalPrice, outptCostDTO.getTotalPrice());
            }

            //判断病人类型，如果是医保病人；做医保试算操作。
            Map<String, Object> payinfo = new HashMap<String, Object>();
            BigDecimal miPrice = new BigDecimal(0); //统筹支付金额
            BigDecimal selfPrice = realityPrice; //个人需支付金额

            //根据医院配置舍入金额
            SysParameterDO sysParameterDO = getSysParameter(outptVisitDTO.getHospCode(), Constants.HOSPCODE_DISCOUNTS_KEY);//获取当前医院优惠配置
            BigDecimal roundingCost = BigDecimalUtils.rounding(sysParameterDO.getValue(), realityPrice); //舍入费用
            //生成结算数据，保存门诊结算表
            OutptSettleDO outptSettleDO = new OutptSettleDO();
            outptSettleDO.setId(settleId);//id

            outptSettleDO.setHospCode(outptVisitDTO.getHospCode());//医院编码
            outptSettleDO.setVisitId(id);//就诊id
            outptSettleDO.setSettleNo(getOrderNo(outptVisitDTO.getHospCode(), Constants.ORDERRULE.JZ));//结算单号
            outptSettleDO.setPatientCode(outptVisitDTO.getPatientCode());//病人类型
            outptSettleDO.setSettleTime(new Date());//结算时间
            outptSettleDO.setTotalPrice(totalPrice);//总金额
            outptSettleDO.setRealityPrice(realityPrice);//优惠后总金额
            outptSettleDO.setTruncPrice(roundingCost);//舍入金额（存在正负金额）
            outptSettleDO.setActualPrice(null);//实收金额
            outptSettleDO.setSelfPrice(BigDecimalUtils.subtract(selfPrice, roundingCost));//个人支付金额
            outptSettleDO.setMiPrice(miPrice);//统筹支付金额
            outptSettleDO.setIsSettle(Constants.SF.F);//是否结算（SF）
            outptSettleDO.setDailySettleId(null);//日结缴款ID
            outptSettleDO.setStatusCode(Constants.ZTBZ.ZC);//状态标志代码（ZTBZ）;正常
            outptSettleDO.setRedId(null);//冲红ID
            outptSettleDO.setIsPrint(Constants.SF.F);//是否打印（SF）;否
            outptSettleDO.setOldSettleId(null);//原结算ID
            outptSettleDO.setIsPrintList(Constants.SF.F);//是否打印清单（SF）
            outptSettleDO.setPrintListTime(null);//清单打印时间
            outptSettleDO.setSourcePayCode(null);//支付来源代码（ZFLY，第三方对接）
            outptSettleDO.setOrderNo(null);//支付订单号（第三方订单号）
            outptSettleDO.setCrteId(outptVisitDTO.getCrteId());//创建人id
            outptSettleDO.setCrteName(outptVisitDTO.getCrteName());//创建人名称
            outptSettleDO.setCrteTime(new Date());//创建时间
            //保存结算费用信息
            outptSettleDAO.insertSelective(outptSettleDO);
            outptVisitDTO.setOutptCostDTOList(outptCostDTOList);
            result.put("outptVisit", outptVisitDTO);//返回个人信息
            result.put("outptSettle", outptSettleDO);//返回结算信息
            //
            Map<String,Object> resultMap = this.getUpFee(outptVisitDTO, outptSettleDO);
            Map<String,Object> data = (Map<String, Object>) resultMap.get("data");
            payinfo = data;
            result.put("payinfo", payinfo);//医保报销信息
        } catch (Exception e) {
            throw e;
        } finally {
            redisUtils.del(key);//删除结算key
        }
        return WrapperResponse.success("成功。", result);
    }

    /**
     * 获取电子凭证数据
     *
     * @param outptVisitDTO 就诊信息
     * @param outptSettleDO 结算信息
     * @return
     */
    public Map<String,Object> getUpFee(OutptVisitDTO outptVisitDTO, OutptSettleDO outptSettleDO) {
        //撤销费用
        this.saveRevokeOrder(outptVisitDTO);
        String uploadFeeData = null;
        UploadFee fee = new UploadFee();
        fee.setId(outptSettleDO.getId());
        fee.setHospCode(outptVisitDTO.getHospCode());
        fee.setVisitId(outptVisitDTO.getId());
        UploadFee feeOrg = outptSettleDAO.getDzpzOrgId(fee);
        if (feeOrg == null) {
            return null;
        }
        fee.setOrgId(feeOrg.getOrgId());
        fee.setOrgCodg(feeOrg.getOrgCodg());
        fee.setAppId(feeOrg.getAppId());
        fee.setEcToken(outptVisitDTO.getEcToken());
        UploadFee uploadFee = outptSettleDAO.getInsureFee(fee);
        uploadFee.setRxList(outptSettleDAO.getInsureFeeRxList(fee));
        uploadFee.setDiseList(outptSettleDAO.getInsureFeeDiseList(fee));
        List<CondList> condList = new ArrayList<>();
        CondList cond = new CondList();
        cond.setCondCodg("-");
        cond.setCondName("-");
        condList.add(cond);
        ExtData extData = new ExtData();
        extData.setOpter(uploadFee.getOpter());
        extData.setDocno(uploadFee.getDocno());
        extData.setPsnNum(uploadFee.getPsnNum());
        extData.setSystemNo(uploadFee.getSystemNo());
        extData.setList(outptSettleDAO.getInsureFeeExtDataList(fee));
        uploadFee.setCondList(condList);
        uploadFee.setExtData(extData);
        uploadFee.setAppId("37B0389095E640F89DEE9F5C8D763E17");
        JSONObject request = new JSONObject();
        request.put("url", "http://10.103.161.181:8082/pmc/api");
        request.put("data", JSONArray.toJSON(uploadFee));
        request.put("transType", "hos.upload.fee");
        request.put("orgId", uploadFee.getOrgId());
        //费用上传
        //JSONObject jsonObject = this.callNeusoft(request.toJSONString());
        Map<String,Object> outptElectronicParam = new HashMap<String,Object>();
        outptElectronicParam.put("hospCode",outptVisitDTO.getHospCode());//医院编码
        outptElectronicParam.put("insureRegCode",uploadFee.getOrgId());//医保编码
        outptElectronicParam.put("outptCostDTOList",outptVisitDTO.getOutptCostDTOList());//费用信息
        outptElectronicParam.put("outptVisitDTO",outptVisitDTO);//个人信息
        outptElectronicParam.put("medOrgOrd", outptSettleDO.getId()); // 结算id
        Map<String,Object> httpResult = (Map<String, Object>) outptElectronicBillService.addPatientCost(outptElectronicParam).getData();
        JSONObject jsonObject = JSONObject.parseObject(String.valueOf(httpResult));
        //保存费用
        this.getReturnValue(feeOrg, jsonObject, outptVisitDTO);
        return httpResult;
    }


    public boolean getReturnValue(UploadFee feeOrg, JSONObject jsonObject, OutptVisitDTO outptVisitDTO) {
//        JSONObject jsonObject  = JSONObject.parseObject(returnValue);
//        JSONObject jsonFee = (JSONObject) jsonObject.get("data");
        JSONObject extData = (JSONObject) jsonObject.get("extData");
        JSONArray jsonArray = extData.getJSONArray("result");
//        String payOrdId = jsonFee.getString("payOrdId");
//        String payToken = jsonFee.getString("payToken");
        String payOrdId = (String) jsonObject.get("payOrdId");
        String payToken = (String) jsonObject.get("payToken");
        Map map = new HashMap();
        map.put("payToken", payToken);
        map.put("payOrdId", payOrdId);
        map.put("hospCode", feeOrg.getHospCode());
        map.put("visitId", feeOrg.getVisitId());
        outptSettleDAO.updateIndividualVisitToken(map);
        List<InsureIndividualCostDTO> insureIndividualCostDTOList = outptSettleDAO.getInsureIndividualCost(feeOrg);
        for (InsureIndividualCostDTO insureIndividualCostDTO : insureIndividualCostDTOList) {
            insureIndividualCostDTO.setId(SnowflakeUtils.getId());
//            for (int i = 0; i < jsonArray.size(); i++) {
//                JSONObject object = jsonArray.getJSONObject(i);
//                if (insureIndividualCostDTO.getCostId().equals(object.get("hosRxDetlNo").toString())) {
//                    insureIndividualCostDTO.setRxSn(object.get("rxSn").toString());
//                    insureIndividualCostDTO.setOverlmtSelfpayAmt(object.get("overlmtSelfpayAmt").toString());
//                    insureIndividualCostDTO.setChrgItemLv(object.get("chrgItemLv").toString());
//                    insureIndividualCostDTO.setFulamtOwnpayFlag(object.get("fulamtOwnpayFlag").toString());
//                    insureIndividualCostDTO.setOwnpayRea(object.get("ownpayRea").toString());
//                    insureIndividualCostDTO.setHiLmtpric(object.get("hiLmtpric").toString());
//                    insureIndividualCostDTO.setGuestRatio(object.get("selfpayProp").toString());
//                    insureIndividualCostDTO.setApplyLastPrice(new BigDecimal(object.get("pfoAmt").toString()));
//                    break;
//                }
//            }
            insureIndividualCostDTO.setIsHospital("0");  // 是否住院费用
            insureIndividualCostDTO.setTransmitCode("1"); // 传输标志，1：已传输
            insureIndividualCostDTO.setCrteId(outptVisitDTO.getCrteId());
            insureIndividualCostDTO.setCrteName(outptVisitDTO.getCrteName());
            insureIndividualCostDTO.setCrteTime(outptVisitDTO.getCrteTime() == null ? DateUtils.getNow() : outptVisitDTO.getCrteTime());
            insureIndividualCostDTO.setInsureIsTransmit("1");
        }
        outptSettleDAO.insertInsureCost(insureIndividualCostDTOList);
        return true;
    }

    @Override
    public boolean saveRevokeOrder(OutptVisitDTO outptVisitDTO) {
        UploadFee fee = new UploadFee();
        fee.setHospCode(outptVisitDTO.getHospCode());
        fee.setVisitId(outptVisitDTO.getId());
        UploadFee feeOrg = outptSettleDAO.getDzpzOrgId(fee);
        if (feeOrg == null) {
          throw new AppException("未查找到参保人医保就诊信息！");
        }
        if (StringUtils.isEmpty(feeOrg.getPayToken()) || StringUtils.isEmpty(feeOrg.getPayOrdId())) {
          throw new AppException("请先上传费用！");
        }
        Map<String,Object> jsonObject = new HashMap<>();
        jsonObject.put("hospCode", outptVisitDTO.getHospCode());
        jsonObject.put("insureRegCode", feeOrg.getOrgId());
        //获取个人信息
        Map<String, Object> insureVisitParam = new HashMap<>();
        insureVisitParam.put("id",outptVisitDTO.getId());
        insureVisitParam.put("hospCode",outptVisitDTO.getHospCode());
        InsureIndividualVisitDTO insureIndividualVisitDTO = insureIndividualVisitService_consumer.getInsureIndividualVisitById(insureVisitParam);
        jsonObject.put("insureIndividualVisitDTO", insureIndividualVisitDTO);
        Map<String,Object> httpResult = (Map<String, Object>) outptElectronicBillService.deletePatientCost(jsonObject).getData();
        String success = (String) httpResult.get("data");
        logger.info("deletePatientCost ==========>"+httpResult );
        if ("false".equals(success)) {
          throw new AppException("电子凭证撤销费用失败，失败原因："+success);
        }
        Map map = new HashMap();
        map.put("payToken", "");
        map.put("payOrdId", "");
        map.put("hospCode", feeOrg.getHospCode());
        map.put("visitId", feeOrg.getVisitId());
        outptSettleDAO.deleteInsureCost(map); // 删除his的医保费用表数据
        return outptSettleDAO.updateIndividualVisitToken(map) > 0;
    }

    @Override
    public boolean saveSeltSucCallback(Map map) {
        String hospCode = map.get("hospCode").toString();
        Map<String, Object> tempMap = (Map<String, Object>) map.get("data");
        // 结算id
        tempMap.put("id", map.get("medOrgOrd").toString());
        tempMap.put("hospCode", hospCode);
        InsureIndividualSettleDO insureIndividualSettleDO = outptSettleDAO.getInsureInsureIndividualSettle(tempMap);
        insureIndividualSettleDO.setSettleId(map.get("medOrgOrd").toString());
        insureIndividualSettleDO.setId(SnowflakeUtils.getId());
        insureIndividualSettleDO.setTotalPrice(new BigDecimal(map.get("feeSumamt").toString()));
        insureIndividualSettleDO.setPersonalPrice(new BigDecimal(map.get("ownpayAmt").toString()));
        insureIndividualSettleDO.setPersonPrice(new BigDecimal(map.get("psnAcctPay").toString()));
        insureIndividualSettleDO.setRetirePrice(new BigDecimal(map.get("fundPay").toString()));
        insureIndividualSettleDO.setState("0");
        insureIndividualSettleDO.setSettleState("1");
        logger.info("回调医保结算信息============>{}", FastJsonUtils.toJson(insureIndividualSettleDO));

        // 1、更新医保结算表
        // outptSettleDAO.insertInsureIndividualSettle(insureIndividualSettleDO);
        Map<String, Object> insureSettleParam = new HashMap<String, Object>();
        //医院编码
        insureSettleParam.put("hospCode", hospCode);
        insureSettleParam.put("insureIndividualSettleDO", insureIndividualSettleDO);
        insureIndividualSettleService.insertSelective(insureSettleParam);
        // 3、生成领药申请单，医技确费等信息，更新个人支付，医保支付金额
        this.saveMzPharHaiNanDZPZ(hospCode, insureIndividualSettleDO);
        // 4、再次调用医保，告知结算成功
        // outptElectronicBillService.addPatientCost(outptElectronicParam)
        return true;
    }

    /**
     * @Description: 海南电子凭证移动支付完成回调时，需要生成领药申请
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/11/16 14:44
     * @Return
     */
    private void saveMzPharHaiNanDZPZ(String hospCode, InsureIndividualSettleDO insureIndividualSettleDO){
        String settleId = insureIndividualSettleDO.getSettleId();
        String visitId = insureIndividualSettleDO.getVisitId();
        String crteId = insureIndividualSettleDO.getCrteId();
        String crteName = insureIndividualSettleDO.getCrteName();
        Map<String, Object> visitMap = new HashMap<String, Object>();
        visitMap.put("hospCode", hospCode);
        visitMap.put("visitId", visitId);
        OutptVisitDTO outptVisitDTO = outptVisitDAO.getVisitByParams(visitMap);

        // 2、 校验费用信息是否正确（根据当前结算id，查询费用表）
        Map<String, Object> param = new HashMap<String, Object>();
        //hospCode（医院编码）、statusCode（状态标志）、settleCode（结算状态）、settleId（结算id）
        param.put("hospCode", hospCode);//hospCode（医院编码）
        param.put("statusCode", Constants.ZTBZ.ZC);//statusCode（状态标志 = 正常）
        param.put("settleCode", Constants.JSZT.YUJS);//settleCode（结算状态 = 预结算）
        param.put("settleId", settleId);//settleId（结算id）
        List<OutptCostDTO> outptCostDTOList = outptCostDAO.queryBySettleId(param);

        // 更新医技申请单状态
        if (!ListUtils.isEmpty(outptCostDTOList)) {
            outptCostDAO.updateMedicApply(visitId, hospCode, "02", outptCostDTOList);
        }

        /* 生成领药申请单，校验库存、领药申请单明细 */
        Map<String, Object> queryParam = new HashMap<String, Object>();
        queryParam.put("hospCode", hospCode);//医院编码
        queryParam.put("pfTypeCode", outptVisitDTO.getPreferentialTypeId());//优惠类型
        queryParam.put("items", outptCostDTOList);//当前用户的费用信息
        List<OutptCostDTO> outptCostDTOS = outptCostDAO.queryDrugMaterialListByIds(queryParam);

        //统计优惠总金额
        BigDecimal realityPrice = new BigDecimal(0);//优惠后总费用
        List<String> ids = new ArrayList<String>();//修改费用状态费用id
        List<PharOutReceiveDetailDO> pharOutReceiveDetailDOList = new ArrayList<PharOutReceiveDetailDO>();//领药申请明细参数接收
        Map<String, Map<String, Object>> pharOutReceiveMap = new HashMap<String, Map<String, Object>>();
        List<String> outptPrescribeIds = new ArrayList<String>();//存储处方id

        // 3、 校验药品、材料库存，并生成领药申请单明细
        Map<String, Object> tempMap = this.checkStockAndCreatePharOutReceiveDetail(outptCostDTOList, outptCostDTOS, hospCode);
        realityPrice = (BigDecimal) tempMap.get("realityPrice");
        ids = (List<String>) tempMap.get("ids");
        pharOutReceiveDetailDOList = (List<PharOutReceiveDetailDO>) tempMap.get("pharOutReceiveDetailDOList");
        pharOutReceiveMap = (Map<String, Map<String, Object>>) tempMap.get("pharOutReceiveMap");
        outptPrescribeIds = (List<String>) tempMap.get("outptPrescribeIds");

        // 6、 根据费用信息修改本次结算的费用状态
        Map<String, Object> costParam = new HashMap<String, Object>();
        costParam.put("settleCode", Constants.JSZT.YIJS);//费用状态 = 已结算
        costParam.put("ids", ids);//费用id
        outptCostDAO.editCostSettleCodeByIDS(costParam);

        // 7、 修改门诊结算表此次结算信息状态
        OutptSettleDO outptSettleDO = new OutptSettleDO();//修改参数
        outptSettleDO.setId(settleId);//结算id
        // SysParameterDO sysParameterDO = getSysParameter(hospCode, Constants.HOSPCODE_DISCOUNTS_KEY);//获取当前医院优惠配置
        // BigDecimal ssje = BigDecimalUtils.subtract(realityPrice, BigDecimalUtils.rounding(sysParameterDO.getValue(), realityPrice));
        outptSettleDO.setActualPrice(realityPrice);//实收金额
        outptSettleDO.setIsSettle(Constants.SF.S);//是否结算 = 是
        outptSettleDO.setSourcePayCode("4");  // 0:HIS 1:微信  2：支付宝   3：自助机 4：电子凭证支付
        outptSettleDO.setSelfPrice(insureIndividualSettleDO.getPersonalPrice()); // 个人现金支付
        outptSettleDO.setMiPrice(insureIndividualSettleDO.getRetirePrice()); // 基金支付
        //outptSettleDAO.updateByPrimaryKeySelective(outptSettleDO);//修改结算状态
        outptSettleDAO.updateByPrimaryKeySelective(outptSettleDO);

        // 8、 修改处方表结算信息
        if (!outptPrescribeIds.isEmpty()) {
            Map<String, Object> outptPrescribeParam = new HashMap<String, Object>();
            outptPrescribeParam.put("hospCode", hospCode);//医院编码
            outptPrescribeParam.put("ids", outptPrescribeIds);//处方ids
            outptPrescribeParam.put("settleId", settleId);//结算id
            outptPrescribeParam.put("isSettle", Constants.SF.S);//是否结算 = 是
            outptCostDAO.updateOutptPrescribeByIds(outptPrescribeParam);
        }
        // 10、 取最佳领药窗口，生成领药申请单（主单），保存领药申请单与领药申请单详单
        this.savePharOutReceive(hospCode, visitId, outptVisitDTO.getDeptId(), crteId, crteName, settleId,
                pharOutReceiveMap, outptCostDTOList, pharOutReceiveDetailDOList);
    }


    /**
     * @Description: 根据费用id、优惠类别、默认数量为1来计算费用优惠金额(直接划价收费时，添加一条费用项目，立马计算当前项目优惠金额)
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/2/4 14:05
     * @Return
     */
    @Override
    public OutptCostDTO getOutptCostDTOForPreferential(Map<String, Object> params) {
        String hospCode = MapUtils.get(params, "hospCode");
        OutptCostDTO outptCostDTO = MapUtils.get(params, "outptCost");
        outptCostDTO.setHospCode(hospCode);
        List<Map<String, Object>> opCostMapList = new ArrayList<Map<String, Object>>();
        String outptCostId = SnowflakeUtils.getId();
        outptCostDTO.setId(outptCostId);
        Map<String, Object> item = new HashMap<String, Object>();
        item.put("id", outptCostId);
        item.put("hospCode", hospCode);
        item.put("preferentialTypeId", outptCostDTO.getPreferentialTypeId());
        item.put("itemId", outptCostDTO.getItemId());
        item.put("itemCode", outptCostDTO.getItemCode());
        item.put("bfcId", outptCostDTO.getBfcId());
        item.put("type", Constants.PREFERENTIALTYPE.MZ);
        item.put("totalPrice", outptCostDTO.getPrice());
        opCostMapList.add(item);
        Map<String, Object> basePreferentialParam = new HashMap<String, Object>();
        basePreferentialParam.put("hospCode", hospCode);
        basePreferentialParam.put("costList", opCostMapList);
        List<Map<String, Object>> resultCostLust = basePreferentialService.calculatPreferential(basePreferentialParam).getData();

        if (resultCostLust != null) {
            for (Map<String, Object> map : resultCostLust) {
                BigDecimal totalNum = outptCostDTO.getTotalNum();//总数量
                BigDecimal price = outptCostDTO.getPrice(); //单价
                BigDecimal preferentialPrice = (BigDecimal) map.get("preferentialPrice");//优惠金额
                BigDecimal realityPrice = (BigDecimal) map.get("realityPrice");//优惠后金额
                outptCostDTO.setTotalPrice(BigDecimalUtils.multiply(totalNum, price).setScale(2, BigDecimal.ROUND_HALF_UP));
                outptCostDTO.setPreferentialPrice(BigDecimalUtils.multiply(totalNum, preferentialPrice).setScale(2, BigDecimal.ROUND_HALF_UP));//计算优惠金额
                outptCostDTO.setRealityPrice(BigDecimalUtils.multiply(totalNum, realityPrice).setScale(2, BigDecimal.ROUND_HALF_UP));//计算优惠后金额
            }
        }

        // 查询项目默认科室
        if (Constants.XMLB.XM.equals(outptCostDTO.getItemCode())) {
            String execDeptId = outptCostDAO.selectXMZXKS(outptCostDTO);
            outptCostDTO.setExecDeptId(execDeptId);
        }
        return outptCostDTO;
    }

    /**
     * @param param 请求参数
     * @Menthod callNeusoft
     * @Desrciption 调用东软医保工具类
     * @Author Ou·Mr
     * @Date 2021/1/21 19:17
     * @Return java.lang.String
     */
    public JSONObject callNeusoft(String param) {
        try {
            Map<String, Object> httpParam = new HashMap<String, Object>();
            httpParam.put("url", "http://rdwjux.natappfree.cc/NationEcc");
            httpParam.put("param", param);
            String result = HttpConnectUtil.doPost(httpParam);
            if (StringUtils.isEmpty(result)) {
                throw new AppException("调用动态库未返回信息，请联系管理员。");
            }
            JSONObject resultObj = JSON.parseObject(result);
            String code = resultObj.get("code").toString();
            if (!"0".equals(code)) {
                throw new AppException((String) resultObj.get("message"));
            }
            return resultObj;
        } catch (Exception e) {
            throw new AppException("调用医保提示【" + e.getMessage() + "】");
        }
    }

    /**
     * @param jzid
     * @param yybm
     * @param jsid
     * @Description 1.根据当前病人类型判断是否需要打印发票，未配置则默认打印发票 通过参数MZFP_HJSF_ZDDY判断
     * 2.通过MZFP_FDFS判断是否需要进行分单。
     * 3.根据根据就诊ID 进行一级分单 --
     * 4.补记帐部分(即处方ID为空的)通过MZFP_HJSF_FDFS进行二级分单。
     * 5.处方部分(即存在处方ID的) 分为西成药，中药处方分单模式(即MZFP_YPCF_FDFS)和LIS/PACS/处置部分（即MZFP_XMCF_FDFS） 进行同二级分单。
     * 6.更新和插入fplb和pj表数据插入并校验。
     * @author: zibo.yuan
     * @date: 2021/3/03
     * @return: void
     **/
    @Override
    public Map<String, Object> disposeFpfd(String jzid, String jsid, String yybm) {
        UtilFunc.isEmptyErr(new Object[]{jzid, yybm, jsid}, new String[]{"就诊ID为空！", "医院编码为空！", "结算ID为空！"});


        // 获取分单方式的参数
        Map<String, String> sysparamt = getSysparamt(yybm);

        String isFd = sysparamt.get("MZFP_FDFS");

        // 非处方费用分单规则
        String isHjfd = sysparamt.get("MZFP_HJSF_FDFS");

        // 药品处方分单设置 只针对通过门诊医生站开出的西药处方和中草药处方有效
        String iscffd = sysparamt.get("MZFP_YPCF_FDFS");

        // 只对处置处方、检验单，检查单，材料有效
        String isXmfd = sysparamt.get("MZFP_XMCF_FDFS");

        //  查询费用明细数据
        Map<String, String> param = new HashMap<>();
        param.put("settle_id", jsid);
        param.put("visit_id", jzid);
        param.put("hospCode", yybm);
        List<OutptCostDTO> outotCost = outptCostDAO.getOutotCost(param);
        if (outotCost == null || outotCost.isEmpty()) {
            throw new AppException("划价收费-分单:根据结算ID,就诊ID 无法获取费用信息!");
        }

        // 暂存所有处方ID
        Set<String> cfParamList = new HashSet<>();
        for (OutptCostDTO outptCostDTO : outotCost) {
            if (!StringUtils.isEmpty(outptCostDTO.getOpId())) {
                cfParamList.add(outptCostDTO.getOpId());
            }
        }

        // 查询发票分单方式 门诊发票分单方式 1不分单；2按规则分单
        Map<String, Object> paramMap = new HashMap<>();
        // 获取js02表中所有处方.
        paramMap.put("list", cfParamList);
        paramMap.put("hosp_code", yybm);
        List<OutptPrescribeDTO> cfList = new ArrayList<>();
        if (!cfParamList.isEmpty()) {
            cfList = outptCostDAO.queryPrescribeList(paramMap);
        }

        // 对处方进行数据结构转换
        Map<String, OutptPrescribeDTO> cfMap = new HashMap<>();
        if (!cfList.isEmpty()) {
            for (OutptPrescribeDTO outptPrescribeDTO : cfList) {
                cfMap.put(outptPrescribeDTO.getId(), outptPrescribeDTO);
            }
        }

        // 门诊结算票据表
        List<OutptSettleInvoiceDTO> pjList = new ArrayList<>();

        //门诊结算票据内容表
        List<OutptSettleInvoiceContentDTO> pjnrList = new ArrayList<>();

        // 主单ID
        String main_id = "";

        for (OutptCostDTO outptCostDTO : outotCost) {
            // 分单备注
            StringBuffer fdbz = new StringBuffer();

            fdbz.append(jzid + "(就诊ID)");

            // 优惠后金额
            BigDecimal realityPrice = outptCostDTO.getRealityPrice();

            // 不分单流程
            if ("1".equals(isFd)) {
                fdbz.append(",1");
            } else if ("2".equals(isFd)) {
                // 处理分单具体规则。
                disposeFdMx(isHjfd, iscffd, isXmfd, fdbz, cfMap, outptCostDTO);
            }

            // 根据fdbz 分单备注分组
            Map<String, OutptSettleInvoiceDTO> pj03Map = pjList.stream()
                    .collect(Collectors.toMap(OutptSettleInvoiceDTO::getDivideRemark, Function.identity(), (entity1, entity2) -> entity1));

            // 分单备注
            String fdbzStr = fdbz.toString();

            // 处理分票分单 ==》组装发票票据表数据
            OutptSettleInvoiceDO outptSettleInvoiceDO = disposeFpfdPj(main_id, pjList, outptCostDTO, realityPrice, pj03Map, fdbzStr);
            String pjid = outptSettleInvoiceDO.getId();

            // 获取主单ID
            if (StringUtils.isEmpty(main_id)) {
                main_id = pjid;
            }
            // 处理发票分单 ==》组装发票类别表数据
            diposeFpfdFplb(yybm, pjnrList, outptCostDTO, pjid, realityPrice);
        }

        // 返回值定义
        Map<String, Object> retMap = new HashMap<>();

        //pengbo   20211203 结算更新费用结算发票ID
        outptCostDAO.batchUpdateSettleInvoiceId(outotCost);

        if (!UtilFunc.isEmpty(pjList)) {
            retMap.put("jspjJsonList", pjList);
        }
        if (!UtilFunc.isEmpty(pjnrList)) {
            retMap.put("fplbJsonList", pjnrList);
        }
        return retMap;
    }

    /**
     * @param isHjfd    非处方费用分单规则
     * @param iscffd    药品处方分单设置 只针对通过门诊医生站开出的西药处方和中草药处方有效
     * @param isXmfd    只对处置处方、检验单，检查单，材料有效
     * @param fdbz      分单标志
     * @param cfMap     所有处方信息
     * @param outCosDto 费用明细信息
     * @Description 处理具体分单规则
     * @author: zibo.yuan
     * @date: 2020/3/18
     * @return: void
     **/
    private void disposeFdMx(String isHjfd, String iscffd, String isXmfd, StringBuffer fdbz,
                             Map<String, OutptPrescribeDTO> cfMap, OutptCostDTO outCosDto) {
        // 计费类别
        String jflb = outCosDto.getCwflBm();
        // 项目类别标志
        String xmlbbz = outCosDto.getItemCode();
        // 开方科室ID
        String kfksid = outCosDto.getDeptId();
        // 执行科室ID
        String zxksid = outCosDto.getExecDeptId();
        // 领药药房ID
        String fyyfid = outCosDto.getPharId();
        // 处方ID
        String cfid = outCosDto.getOpId();

        fdbz.append(",");
        // 二级分单开始 ==》处方ID为空 则走正常的非处方费用分单规则
        if (org.apache.commons.lang3.StringUtils.isEmpty(cfid)) {
            switch (isHjfd) {
                // 1就诊科室分单
                case "1":
                    fdbz.append(kfksid);
                    fdbz.append("(开方科室)");
                    break;
                // 2就诊科室与执行科室分单
                case "2":
                    fdbz.append(kfksid);
                    fdbz.append("(开方科室)");
                    fdbz.append(",");
                    fdbz.append(zxksid);
                    fdbz.append("(执行科室)");
                    break;
                // 3 就诊科室与药品项目类型（药品、项目、材 料）分单
                case "3":
                    fdbz.append(kfksid);
                    fdbz.append("(开方科室)");
                    fdbz.append(",");
                    fdbz.append(xmlbbz);
                    fdbz.append("(项目类别)");
                    break;
                // 4就诊科室与执行科室与药品项目类型（药品、项目、材 料）分单
                case "4":
                    fdbz.append(kfksid);
                    fdbz.append("(开方科室)");
                    fdbz.append(",");
                    fdbz.append(zxksid);
                    fdbz.append("(执行科室)");
                    fdbz.append(",");
                    fdbz.append(xmlbbz);
                    fdbz.append("(项目类别)");
                    break;
                // 5就诊科室、执行 科室、药品项目类型（药品、项目、材 料）与计费类别分单
                case "5":
                    fdbz.append(kfksid);
                    fdbz.append("(开方科室)");
                    fdbz.append(",");
                    fdbz.append(zxksid);
                    fdbz.append("(执行科室)");
                    fdbz.append(",");
                    fdbz.append(xmlbbz);
                    fdbz.append("(项目类别)");
                    fdbz.append(",");
                    fdbz.append(jflb);
                    fdbz.append("(计费类别)");
                    break;
                // 增加6:药品，材料按领药药房分 其他的都按6处理
                default:
                    if ("1".equals(xmlbbz) || "5".equals(xmlbbz)) {
                        fdbz.append(fyyfid);
                        fdbz.append("(发药药房)");
                    } else {
                        fdbz.append(zxksid);
                        fdbz.append("(执行科室)");
                    }
                    break;
            }
            // 处方分单
        } else {
            if (!cfMap.containsKey(cfid)) {
                throw new AppException("划价收费-结算发票分单：无法根据当前处方ID找到对应的处方信息！" + cfid);
            }

            // 处方类别
            String cflb = cfMap.get(cfid).getTypeCode();

            if (StringUtils.isEmpty(cflb)) {
                throw new AppException("划价收费-结算发票分单：当前就诊病人存在为设置处方类别的处方！" + cfid);
            }

            // 西成药，中药处方
            if ("1".equals(cflb) || "3".equals(cflb)) {

                // 西成药，中药处方 增加分单方式
                switch (iscffd) {
                    // 1 按处方分单
                    case "1":
                        fdbz.append(kfksid);
                        fdbz.append("(开方科室)");
                        fdbz.append(",");
                        fdbz.append(cfid);
                        fdbz.append("(处方ID)");
                        break;
                    // 2按处方与药房分单
                    case "2":
                        fdbz.append(kfksid);
                        fdbz.append("(开方科室)");
                        fdbz.append(",");
                        fdbz.append(fyyfid);
                        fdbz.append("(发药药房)");
                        fdbz.append(",");
                        fdbz.append(cfid);
                        fdbz.append("(处方ID)");
                        break;
                    // 3按处方、药房与执行科室分单；
                    case "3":
                        fdbz.append(kfksid);
                        fdbz.append("(开方科室)");
                        fdbz.append(",");
                        fdbz.append(zxksid);
                        fdbz.append("(执行科室)");
                        fdbz.append(",");
                        fdbz.append(fyyfid);
                        fdbz.append("(发药药房)");
                        fdbz.append(",");
                        fdbz.append(cfid);
                        fdbz.append("(处方ID)");
                        break;
                    // 4按处方、药房与 药品项目类型（药品、项目、材料）分单；
                    case "4":
                        fdbz.append(kfksid);
                        fdbz.append("(开方科室)");
                        fdbz.append(",");
                        fdbz.append(fyyfid);
                        fdbz.append("(发药药房)");
                        fdbz.append(",");
                        fdbz.append(cfid);
                        fdbz.append("(处方ID)");
                        fdbz.append(",");
                        fdbz.append(xmlbbz);
                        fdbz.append("(项目类别)");
                        break;
                    // 5按处方、药房，药品项目类
                    default:
                        fdbz.append(kfksid);
                        fdbz.append("(开方科室)");
                        fdbz.append(",");
                        fdbz.append(fyyfid);
                        fdbz.append("(发药药房)");
                        fdbz.append(",");
                        fdbz.append(cfid);
                        fdbz.append("(处方ID)");
                        fdbz.append(",");
                        fdbz.append(xmlbbz);
                        fdbz.append("(项目类别)");
                        fdbz.append(",");
                        fdbz.append(jflb);
                        fdbz.append("(计费类别)");
                        break;
                }

            } else {
                // 西成药，中药处方 增加分单方式
                switch (isXmfd) {
                    // 1 按处方分单
                    case "1":
                        fdbz.append(kfksid);
                        fdbz.append("(开方科室)");
                        fdbz.append(",");
                        fdbz.append(cfid);
                        fdbz.append("(处方ID)");
                        break;
                    // 2按处方与执行科室分单；
                    case "2":
                        fdbz.append(kfksid);
                        fdbz.append("(开方科室)");
                        fdbz.append(",");
                        fdbz.append(zxksid);
                        fdbz.append("(执行科室)");
                        fdbz.append(",");
                        fdbz.append(cfid);
                        fdbz.append("(处方ID)");
                        break;
                    // 3按处方、执行科室、计费类别分单
                    default:
                        fdbz.append(kfksid);
                        fdbz.append("(开方科室)");
                        fdbz.append(",");
                        fdbz.append(zxksid);
                        fdbz.append("(执行科室)");
                        fdbz.append(",");
                        fdbz.append(cfid);
                        fdbz.append("(处方ID)");
                        fdbz.append(",");
                        fdbz.append(jflb);
                        fdbz.append("(计费类别)");
                        break;
                }

            }
        }
    }


    /**
     * @param pjList       票据List
     * @param outptCostDTO his门诊费用
     * @param yhhje        优惠后金额
     * @param pj03Map      票据ID
     * @param fdbzStr      发票分单备注。
     * @Description 处理分票分单 ==》组装发票票据表数据
     * @author: zibo.yuan
     * @date: 2021/3/05
     * @return: java.lang.String
     **/
    private OutptSettleInvoiceDO disposeFpfdPj(String mainId, List<OutptSettleInvoiceDTO> pjList, OutptCostDTO outptCostDTO,
                                               BigDecimal yhhje, Map<String, OutptSettleInvoiceDTO> pj03Map, String fdbzStr) {

        OutptSettleInvoiceDTO outptSettleInvoiceDO = null;


        // 如果存在当前分单，则执行发票金额累加
        if (pj03Map.containsKey(fdbzStr)) {

            // 获取当前已经分单的票据信息
            outptSettleInvoiceDO = pj03Map.get(fdbzStr);
            BigDecimal fpje = outptSettleInvoiceDO.getTotalPrice();
            fpje = fpje == null ? new BigDecimal(0) : fpje;
            outptSettleInvoiceDO.setTotalPrice(BigDecimalUtils.add(fpje, yhhje));

        } else {
            outptSettleInvoiceDO = new OutptSettleInvoiceDTO();
            // 票据ID
            String fpid = SnowflakeUtils.getId();
            outptSettleInvoiceDO.setPrintNum(0);

            // 分单备注
            outptSettleInvoiceDO.setDivideRemark(fdbzStr);
            outptSettleInvoiceDO.setId(fpid);

            // 发票金额；
            outptSettleInvoiceDO.setTotalPrice(yhhje);
            outptSettleInvoiceDO.setVisitId(outptCostDTO.getVisitId());
            outptSettleInvoiceDO.setSettleId(outptCostDTO.getSettleId());
            outptSettleInvoiceDO.setHospCode(outptCostDTO.getHospCode());
            outptSettleInvoiceDO.setExecDeptId(outptCostDTO.getExecDeptId());

            // 是否主单 1是 0否
            String is_main = "1";
            if (!pj03Map.isEmpty()) {
                is_main = "0";
                outptSettleInvoiceDO.setMainId(mainId);
            }
            outptSettleInvoiceDO.setIsMain(is_main);
            // 状态标志 0 正常
            outptSettleInvoiceDO.setStatusCode("0");

            pjList.add(outptSettleInvoiceDO);
        }
        /**
         * 设置发票结算ID到费用表
         */
        outptCostDTO.setSettleInvoiceId(outptSettleInvoiceDO.getId());
        return outptSettleInvoiceDO;
    }

    /**
     * @param yybm         医院编码
     * @param fplbList     发票类别暂存List
     * @param outptCostDTO 门诊费用
     * @param pjid         发票ID
     * @Description 处理发票分单 ==》组装发票类别表数据 组装门诊费用数据修改发票信息
     * @author: zibo.yuan
     * @date: 2020/3/18
     * @return: void
     **/
    private void diposeFpfdFplb(String yybm, List<OutptSettleInvoiceContentDTO> fplbList, OutptCostDTO outptCostDTO, String pjid, BigDecimal yhhje) {

        // 发票类别编码
        String fplbid = outptCostDTO.getCwflBm();
        // 发票内容
        String fpnr = outptCostDTO.getMzFpnr();

        OutptSettleInvoiceContentDTO outptSettleInvoiceContentDTO = null;
        UtilFunc.isEmptyErr(new Object[]{fplbid, fpnr},
                new String[]{"划价收费-发票分单：当前财务分类未设置对应的发票类别，请联系管理员联系!",
                        "划价收费-发票分单：当前财务分类未设置对应的发票内容，请联系管理员联系!"});

        // 根据fdbz 分单备注分组
        Map<String, OutptSettleInvoiceContentDTO> fplbMap = fplbList.stream()
                .collect(Collectors.toMap(OutptSettleInvoiceContentDTO::getFpkey, Function.identity(), (entity1, entity2) -> entity1));

        // 发票类别Key；
        String fplbKey = pjid + fpnr;
        if (fplbMap.containsKey(fplbKey)) {
            outptSettleInvoiceContentDTO = fplbMap.get(fplbKey);

            // 优惠金额合计
            BigDecimal yhje = outptSettleInvoiceContentDTO.getRealityPrice();
            yhje = yhje == null ? new BigDecimal(0) : yhje;
            outptSettleInvoiceContentDTO.setRealityPrice(BigDecimalUtils.add(yhje, yhhje));
        } else {
            outptSettleInvoiceContentDTO = new OutptSettleInvoiceContentDTO();
            outptSettleInvoiceContentDTO.setId(SnowflakeUtils.getId());
            outptSettleInvoiceContentDTO.setHospCode(yybm);
            outptSettleInvoiceContentDTO.setSettleInvoiceId(pjid);
            outptSettleInvoiceContentDTO.setOutCode(fplbid);
            outptSettleInvoiceContentDTO.setOutName(fpnr);
            outptSettleInvoiceContentDTO.setRealityPrice(yhhje);
            fplbList.add(outptSettleInvoiceContentDTO);
        }
    }

    /**
     * @param
     * @Description 门诊发票分单发票打印
     * @author: zibo.yuan
     * @date: 2021/3/5
     * @return: java.lang.String
     **/
    @Override
    public void saveMzfpDy(OutinInvoiceDTO outinInvoiceDTO, List<OutptSettleInvoiceDTO> pjList, List<OutptSettleInvoiceContentDTO> pjnrList) {

        if (StringUtils.isEmpty(outinInvoiceDTO.getId())) {
            throw new AppException("门诊结算-发票打印:发票号段ID不能为空");
        }

        if (StringUtils.isEmpty(outinInvoiceDTO.getHospCode())) {
            throw new AppException("门诊结算-发票打印:医院编码丢失，请联系管理员");
        }

        if (StringUtils.isEmpty(outinInvoiceDTO.getCurrNo())) {
            throw new AppException("门诊结算-发票打印:发票当前使用号码不能为空");
        }

        // 传入的当前号码
        String dqCurrNo = outinInvoiceDTO.getCurrNo();
        //  获取当前发票信息
        OutinInvoiceDO selectInvoice = outinInvoiceDAO.getOutinInvoiceById(outinInvoiceDTO);
        //  当前号码
        String currNo = selectInvoice.getCurrNo();

        //  终止号码
        String endNo = selectInvoice.getEndNo();

        if (!dqCurrNo.equals(currNo)) {
            throw new AppException("门诊结算-发票打印:发票打印失败,当前传入的发票号码与当前可用的发票号码不一致[" + dqCurrNo + "]");
        }


        // 获取需要打印多少张票据
        int pjSize = pjList.size();
        // 当前号码
        Integer currNoInt = Integer.valueOf(currNo);
        // 结束号码
        Integer endNoInt = Integer.valueOf(endNo);

        if (pjSize + currNoInt > endNoInt + 1) {
            throw new AppException("门诊结算-发票打印:发票打印失败,当前需要打印[" + pjSize + "],目前发票票据数量不足,请进行重打补打操作!");
        }

        List<OutptSettleInvoiceDTO> invoiceDTOList = new ArrayList<>();

        //  循环使用票据
        for (OutptSettleInvoiceDTO outptSettleInvoiceDTO : pjList) {
            Map<String, Object> map = new HashMap<String, Object>();
            outinInvoiceDTO.setCurrNo(String.valueOf(currNoInt));
            outinInvoiceDTO.setDqCurrNo(dqCurrNo);
            map.put("hospCode", outinInvoiceDTO.getHospCode());
            map.put("outinInvoiceDTO", outinInvoiceDTO);
            // 执行使用发票
            OutinInvoiceDetailDO outinInvoiceDetailDO = outinInvoiceService.updateInvoiceStatus(map).getData();
            outptSettleInvoiceDTO.setInvoiceId(outinInvoiceDetailDO.getInvoiceId());
            outptSettleInvoiceDTO.setInvoiceDetailId(outinInvoiceDetailDO.getId());//发票明细id
            outptSettleInvoiceDTO.setInvoiceNo(String.valueOf(outinInvoiceDetailDO.getInvoiceNo()));//发票号码
            outptSettleInvoiceDTO.setPrintNum(1);
            outptSettleInvoiceDTO.setPrintTime(new Date());
            outptSettleInvoiceDTO.setHospCode(outinInvoiceDetailDO.getHospCode());
            // 发票使用后修改票据表信息
            invoiceDTOList.add(outptSettleInvoiceDTO);
            currNoInt++;
        }

        // 回写票据表数据.
        if (!invoiceDTOList.isEmpty()) {
            outptSettleInvoiceDAO.updateOutptSettleInvoicesBypj(invoiceDTOList);
        }
    }

    /***
     * @Description 门诊结算BO
     * @param param
     * @author: zibo.yuan
     * @date: 2021/3/9
     * @return: void
     **/
    @Override
    public WrapperResponse saveOutptSettleInvoice(Map<String, Object> param) {

        OutptVisitDTO outptVisitDTO = MapUtils.get(param, "outptVisitDTO");//获取个人信息
        OutptSettleDTO outptSettleDTO = MapUtils.get(param, "outptSettleDTO");//获取结算信息
        List<OutptPayDO> outptPayDOList = MapUtils.get(param, "outptPayDOList");//支付方式信息

        // 手动控制事物
        TransactionStatus status = null;
        // 全局返回对象
        WrapperResponse wrapperResponse = null;

        // 门诊结算票据表
        List<OutptSettleInvoiceDTO> pjList = null;

        //门诊结算票据内容表
        List<OutptSettleInvoiceContentDTO> pjnrList = null;
        try {


            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            // 事物隔离级别，开启新事务
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            // 获得事务状态
            status = transactionManager.getTransaction(def);


            // 先进行发票分单
            Map<String, Object> pjMap = this.disposeFpfd(outptSettleDTO.getVisitId(), outptSettleDTO.getId(), outptSettleDTO.getHospCode());

            // 门诊结算票据表
            pjList = MapUtils.get(pjMap, "jspjJsonList");

            //门诊结算票据内容表
            pjnrList = MapUtils.get(pjMap, "fplbJsonList");

            // 处理结算数据
            wrapperResponse = this.saveOutptSettle(outptVisitDTO, outptSettleDTO, outptPayDOList);

            saveOutptSettleInvoice(pjList, pjnrList);

            transactionManager.commit(status);

        } catch (Exception e) {
            if (status != null) {
                transactionManager.rollback(status);
            }
            throw e;
        }

        // 发票信息处理
        if (wrapperResponse.getCode() == 0 && wrapperResponse.getData() != null && outptSettleDTO.getIsInvoice()) {
            // 需要打印发票
            JSONObject data = (JSONObject) wrapperResponse.getData();
            OutinInvoiceDTO outinInvoiceDTO = (OutinInvoiceDTO) data.get("outinInvoiceDTO");
            saveMzfpDy(outinInvoiceDTO, pjList, pjnrList);
        }
        // 体检回调
        if (outptVisitDTO!=null &&"1".equals(outptVisitDTO.getIsPhys())) {
            phyIsCallBack(outptVisitDTO);
        }
        return wrapperResponse;
    }

    private void saveOutptSettleInvoice(List<OutptSettleInvoiceDTO> pjList, List<OutptSettleInvoiceContentDTO> pjnrList) {
        if (pjList == null || pjList.isEmpty() || pjnrList == null || pjnrList.isEmpty()) {
            throw new AppException("门诊结算-保存票据: 分单信息为空,请检查!");
        }
        // 执行批量插入票据表
        outptSettleInvoiceDAO.insertOutptSettleInvoices(pjList);
        // 执行批量插入票据内容表
        outptSettleInvoiceContentDAO.bachInsertContent(pjnrList);
    }

    /***
     * @Description 根据医院编码查询分单条件
     * @param yybm
     * @author: zibo.yuan
     * @date: 2021/3/10
     * @return: java.util.Map<java.lang.String, java.lang.String>
     **/
    private Map<String, String> getSysparamt(String yybm) {
        Map queryMap = new HashMap();
        queryMap.put("hospCode", yybm);
        queryMap.put("codeList", new String[]{"MZFP_FDFS", "MZFP_HJSF_FDFS", "MZFP_YPCF_FDFS", "MZFP_XMCF_FDFS"});
        WrapperResponse<Map<String, SysParameterDTO>> parameterByCodeList = sysParameterService_consumer.getParameterByCodeList(queryMap);
        //  获取当前查询结果
        Map<String, SysParameterDTO> data = parameterByCodeList.getData();

        if (data == null || data.isEmpty()) {
            throw new AppException("门诊结算-发票分单:当前存在未配置的分单信息!");
        }
        Map<String, String> result = new HashMap<>();
        String mzfp_fdfs = data.containsKey("MZFP_FDFS") ? data.get("MZFP_FDFS").getValue() : "1";
        String mzfp_hjsf_fdfs = data.containsKey("MZFP_HJSF_FDFS") ? data.get("MZFP_HJSF_FDFS").getValue() : "1";
        String mzfp_ypcf_fdfs = data.containsKey("MZFP_YPCF_FDFS") ? data.get("MZFP_YPCF_FDFS").getValue() : "1";
        String mzfp_xmcf_fdfs = data.containsKey("MZFP_XMCF_FDFS") ? data.get("MZFP_XMCF_FDFS").getValue() : "1";
        result.put("MZFP_FDFS", mzfp_fdfs);
        result.put("MZFP_HJSF_FDFS", mzfp_hjsf_fdfs);
        result.put("MZFP_YPCF_FDFS", mzfp_ypcf_fdfs);
        result.put("MZFP_XMCF_FDFS", mzfp_xmcf_fdfs);
        return result;

    }
    /**
     * @param map
     * @Method updateCancelFeeSubmit
     * @Desrciption 门诊医保病人取消费用上传
     * 	1.需要判断是前台界面手动触发费用上传撤销功能
     * 	2.还是调用门诊医保费用上传时try catch捕获异常时触发的
     * @Param
     * @Author fuhui
     * @Date 2021/3/3 16:44
     * @Return
     */
    @Override
    public Boolean updateCancelFeeSubmit(Map<String, Object> map) {
        String visitId = map.get("visitId").toString();
        String hospCode = map.get("hospCode").toString();
        Map<String,Object> insureVisitParam =new HashMap<String,Object>();
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map,"insureIndividualVisitDTO");
        if(insureIndividualVisitDTO ==null){
            //获取个人信息
            insureVisitParam.put("id",visitId);
            insureVisitParam.put("hospCode",hospCode);
            insureIndividualVisitDTO = insureIndividualVisitService_consumer.getInsureIndividualVisitById(insureVisitParam);
            if (insureIndividualVisitDTO == null || StringUtils.isEmpty(insureIndividualVisitDTO.getId())) {
                throw new AppException("未查找到医保个人信息，请做医保登记。");
            }
        }

        insureVisitParam.put("id",visitId);
        insureVisitParam.put("hospCode",hospCode);
        insureVisitParam.put("insureSettleId","1");  //作为sql条件判断 删除医保结算id不为空的数据
        if(!"1".equals(MapUtils.get(map,"isError"))){
            Boolean aBoolean = insureUnifiedPayOutptService_consumer.UP_2205(map).getData();
            if(true == aBoolean){
                insureIndividualCostService_consumer.deleteOutptInsureCost(insureVisitParam);
            }
        }else{
            insureIndividualCostService_consumer.deleteOutptInsureCost(insureVisitParam);
        }
        return true;
    }

    /**
     * @param outptVisitDTO
     * @Method checkSettleMoney
     * 费用做判断验证之前，需要判断是否已经做了医保登记，是否已经医保结算，一个医保登记号，只能有一笔正常的结算信息
     * @Desrciption 门诊预结算之前，需要判断his产生的费用，是否和上传到医保的费用相等。
     *  feeMap：包含医保匹配的费用集数据，以及包含匹配费用的总和
     *  sumRealityPrice：表示本次匹配费用且未传输的总金额
     *  根据就诊id， 医院编码 ，是否结算，状态标志来查询处方费用数据（outpt_cost）
     *  outptCostList:门诊费用表的数据集合
     *  outptTotalFee:门诊总费用 = 匹配未上传的费用(sumRealityPrice) + 匹配已上传的费用(uploadRealityPrice)
     *  unMatchList：未匹配的数据集合
     *  unMatchStr:未匹配的项目名称（逗号分隔）:前台提示返回
     *  outptCostDTOList:费用数据（处方费用数据+直接划价费用数据）
     *
     * @Param params
     * @Author fuhui
     * @Date 2021/3/12 11:25
     * @Return Map<String, Object>
     */
    @Override
    public Map<String, Object> checkSettleMoney(OutptVisitDTO outptVisitDTO) {
        String visitId = outptVisitDTO.getId();
        String hospCode = outptVisitDTO.getHospCode();
        Map<String,Object> map = new HashMap<>(2);
        map.put("visitId",visitId);
        map.put("id",visitId);
        map.put("hospCode", hospCode);
        map.put("insureSettleId","1");
        /**
         * 费用验证之前，删除脏数据
         */
        insureIndividualCostService_consumer.deleteOutptInsureCost(map);

        List<OutptCostDTO> outptCostDTOList = outptVisitDTO.getOutptCostDTOList();
        if(ListUtils.isEmpty(outptCostDTOList)){
            throw new RuntimeException("该患者没有产生费用信息");
        }
        List<String> opIds = outptCostDTOList.stream().map(outptCostDTO -> outptCostDTO.getOpId()).collect(Collectors.toList());
        // 获取系统参数 是否开启分处方结算 0: 不开启分处方结算;  1: 开启分处方结算
        map.put("code", "SF_DIVIDED_PRRESCIBE");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(map).getData();
        if(sysParameterDTO !=null && "1".equals(sysParameterDTO.getValue())) {
            if (!ListUtils.isEmpty(opIds)) {
                map.put("opIds", opIds);
            }
        }
        Map<String, Object> feeMap = commonInsureCost(map);
        List<Map<String,Object>> insureCostList = MapUtils.get(feeMap,"insureCostList");
//        else{
//            // 直接化解的费用信息
//            List<OutptCostDTO> collect = outptCostDTOList.stream().filter(outptCostDTO ->
//                    !StringUtils.isEmpty(outptCostDTO.getOpId())).collect(Collectors.toList());
////            insertNotOutPrescibleFee(collect,outptVisitDTO);
//        }
        BigDecimal sumRealityPrice = new BigDecimal(0.00);
        if(!ListUtils.isEmpty(insureCostList)){
            for(Map<String,Object> item :insureCostList){
                sumRealityPrice = BigDecimalUtils.add(sumRealityPrice,MapUtils.get(item,"realityPrice"));
            }
        }
        map.put("statusCode",Constants.SF.F);  // 正常
        map.put("settleCode",Constants.JSZT.YIJS); // 未结算，预结算
        map.put("isHospital",Constants.SF.F);
        List<OutptCostDTO> outptCostList = outptCostDAO.queryOutptCost(map);
//        List<OutptCostDTO> outptCostList =  outptCostDAO.queryIsSubmitOutptCost(map);
        BigDecimal outptTotalFee = new BigDecimal(0.00);

        map.put("sumRealityPrice",sumRealityPrice);
        Map<String,Object> paramMap = new HashMap<>();
        StringBuilder stringBuilder = new StringBuilder();
        String unMatchStr = "";
        insureCostList.forEach(listMap ->{
            paramMap.put(MapUtils.get(listMap,"itemId"),MapUtils.get(listMap,"itemName"));
        } );
        if(!ListUtils.isEmpty(outptCostList)){
            for(OutptCostDTO outptCostDTO : outptCostList){
                if(!paramMap.containsKey(outptCostDTO.getItemId())){
                    stringBuilder.append(outptCostDTO.getItemName()).append(",");
                }
                outptTotalFee=BigDecimalUtils.add(outptTotalFee,outptCostDTO.getRealityPrice());
            }
        }
        unMatchStr = stringBuilder.toString();
        map.put("unMatchStr",unMatchStr);
        map.put("outptTotalFee",outptTotalFee);
        return map;
    }

    /**
     * @param map
     * @Method updateVisitTime
     * @Desrciption 更新患者的就诊时间
     * @Param
     * @Author fuhui
     * @Date 2021/6/24 0:47
     * @Return
     */
    @Override
    public Boolean updateVisitTime(Map<String, Object> map) {
        if(StringUtils.isEmpty(MapUtils.get(map,"visitTime"))){
            throw  new AppException("修改的就诊时间不能为空");
        }
        return outptVisitDAO.updateVisitTime(map);
    }

    /**
     * @Description: lis数据下发
     * @Param: [map]
     * @return: java.lang.Boolean
     * @Author: zhangxuan
     * @Date: 2021-06-29
     */
    @Override
    public Boolean lisData(Map map){

        String hospCode = MapUtils.get(map, "hospCode");
        OutptVisitDTO outptVisitDTO = MapUtils.get(map,"outptVisitDTO");
        // 获取医院kafka 的IP与端口
        Map<String, Object> sysMap = new HashMap<>();
        sysMap.put("hospCode", hospCode);
        sysMap.put("code", "KAFKA_IP");
        SysParameterDTO sys = sysParameterService_consumer.getParameterByCode(sysMap).getData();
        if (sys == null || sys.getValue() == null) {  // 调用统一支付平台
            throw new AppException("请在系统参数中配置医院医保中间件所在主机IP与端口 【KAFKA_IP】");
        }
        String kafkaIp = sys.getValue();
        String url = "";
        List<Map> medicalApplyDTOList = new ArrayList<>();
        if(outptVisitDTO.getSourceTjCode().equals("1")){
            medicalApplyDTOList = outptPayDAO.queryMedicalApplyList(outptVisitDTO);
        } else if (outptVisitDTO.getSourceTjCode().equals("2")){
            medicalApplyDTOList = outptPayDAO.queryInptMedicalApplyList(outptVisitDTO);
        } else if (outptVisitDTO.getSourceTjCode().equals("3")){
        }
        if(medicalApplyDTOList.size()>0){
            Map<String, Object> objectMap = new HashMap<>();
            objectMap.put("hospCode",hospCode); // 医院编码
            objectMap.put("mark", "LIS"); // 体检标识
            objectMap.put("KAFKA_IP", kafkaIp); // 卡夫卡ip
            objectMap.put("settleDTOList", medicalApplyDTOList);
            lisData.saveNHPatientToLocal(hospCode, kafkaIp, url, objectMap);
        } else {
            throw new AppException("没有要下发的数据");
        }
        return true;
    }

    /**
     * @Method insertNotOutPrescibleFee
     * @Desrciption  医保统一支付平台：支持划价收费直接走医保。把划价收费的数据保存到费用表中
     * @Param collect：划价收费的费用集合
     *        outptVisitDTO: 门诊病人就诊信息
     * @Author fuhui
     * @Date   2021/6/3 17:26
     * @Return
     **/
    private void insertNotOutPrescibleFee(List<OutptCostDTO> collect, OutptVisitDTO outptVisitDTO) {
        String hospCode = outptVisitDTO.getHospCode();   // 医院编码
        String visitId = outptVisitDTO.getId(); // 就诊id
        String doctorId = outptVisitDTO.getDoctorId(); //开方医生id
        String doctorName = outptVisitDTO.getDoctorName(); //开方医生名称
        String crteId = outptVisitDTO.getCrteId() ; // 创建人Id
        String crteName = outptVisitDTO.getCrteName() ; //创建人姓名
        String deptId = outptVisitDTO.getDeptId(); //
        collect.stream().forEach(outptCostDTO -> {
            outptCostDTO.setId(SnowflakeUtils.getId());
            outptCostDTO.setHospCode(hospCode);
            outptCostDTO.setVisitId(visitId); // 就诊ID
            outptCostDTO.setOpId(null); // 处方ID
            outptCostDTO.setOpdId(null); // 处方明细id
            outptCostDTO.setOpeId(null); // 处方执行签名ID
            outptCostDTO.setSourceCode(Constants.FYLYFS.ZJHJSF);//费用来源方式代码
            outptCostDTO.setIsDist(Constants.SF.F);//是否已发药 = 否
            outptCostDTO.setSourceDeptId(deptId);//来源科室ID
            outptCostDTO.setDoctorId(doctorId);//开方医生id
            outptCostDTO.setDoctorName(doctorName);//开方医生名称
            outptCostDTO.setDeptId(deptId);//开方科室id
            outptCostDTO.setExecDeptId(deptId);//执行科室id
            outptCostDTO.setCrteId(crteId);//创建人id
            outptCostDTO.setCrteName(crteName);//创建人姓名
            outptCostDTO.setCrteTime(new Date());//创建时间
            outptCostDTO.setTotalPrice(outptCostDTO.getTotalPrice().setScale(2, BigDecimal.ROUND_HALF_UP));
            outptCostDTO.setRealityPrice(outptCostDTO.getRealityPrice().setScale(2, BigDecimal.ROUND_HALF_UP));
        });
        outptCostDAO.batchInsert(collect);
    }

    /**
     * @Method commonInsureCost
     * @Desrciption  查询医保匹配的数据集
     * @Param
     *
     * @Author fuhui
     * @Date   2021/3/12 14:05
     * @Return
     **/
    private Map<String,Object> commonInsureCost(Map<String, Object> map){
        String visitId = MapUtils.get(map,"visitId");
        String hospCode = MapUtils.get(map,"hospCode");
        //获取个人信息
        Map<String,Object> insureVisitParam = new HashMap<String,Object>();
        insureVisitParam.put("id",visitId);
        insureVisitParam.put("hospCode",hospCode);
        InsureIndividualVisitDTO insureIndividualVisitDTO = insureIndividualVisitService_consumer.getInsureIndividualVisitById(insureVisitParam);
        if (insureIndividualVisitDTO == null || StringUtils.isEmpty(insureIndividualVisitDTO.getId())) {
            throw new AppException("未查找到医保个人信息，请做医保登记。");
        }
        String insureRegCode = insureIndividualVisitDTO.getInsureRegCode();
        //判断是否有传输费用信息
        Map<String,Object> insureCostParam = new HashMap<String,Object>();
        insureCostParam.put("hospCode",hospCode);//医院编码
        insureCostParam.put("statusCode",Constants.ZTBZ.ZC);//状态标志 = 正常
        insureCostParam.put("visitId",visitId);//就诊id
        insureCostParam.put("isMatch",Constants.SF.S);//是否匹配 = 是
        insureCostParam.put("transmitCode",Constants.SF.F);//传输标志 = 未传输
        insureCostParam.put("insureRegCode",insureRegCode);// 医保机构编码
        insureCostParam.put("isHospital",Constants.SF.F); // 区分门诊还是住院
        List<String> opIds = MapUtils.get(map, "opIds");
        if(!ListUtils.isEmpty(opIds)){
            insureCostParam.put("opIds",opIds); // 处方id
        }
        //        List<Map<String,Object>> insureCostList = outptCostDAO.queryOutptInsureCostByVisit(insureCostParam);
        List<Map<String,Object>> insureCostList = insureIndividualCostService_consumer.queryOutptInsureCostByVisit(insureCostParam);
        if(ListUtils.isEmpty(insureCostList)){
            throw new AppException("该病人没有可以上传的费用,请先做好对应费用项目匹配工作");
        }
        map.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
        map.put("insureCostList",insureCostList);
        map.put("insureRegCode",insureRegCode);
        map.put("insureVisitParam",insureVisitParam);
        return map;
    }

    /**
     * @param map
     * @Method updateFeeSubmit()
     * @Desrciption 1.在进行试算的时候进行门诊医保病人费用传输
     * 				2. 如果本次没有可以传输的费用,也就是没门诊费用表里面的数据和医保费用表里面的数据条数相等。
     * 				如果是上面的情况，则不调用费用传输接口 直接返回true
     *
     *
     * @Param
     *  insureCostList:医保匹配的费用数据集合
     *  insureIndividualVisitDTO：医保患者就诊信息
     * @Author fuhui
     * @Date 2021/3/3 16:38
     * @Return
     */
    @Override
    public Map<String,Object> updateFeeSubmit(Map<String, Object> map) {
        String hospCode = map.get("hospCode").toString();

        List<OutptCostDTO> outptCostDTOList = MapUtils.get(map,"outptCostDTOList");
        List<String> opIds = outptCostDTOList.stream().map(outptCostDTO -> outptCostDTO.getOpId()).collect(Collectors.toList());
        // 获取系统参数 是否开启分处方结算 0: 不开启分处方结算;  1: 开启分处方结算
        map.put("code", "SF_DIVIDED_PRRESCIBE");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(map).getData();
        if(sysParameterDTO !=null && "1".equals(sysParameterDTO.getValue())) {
            if (!ListUtils.isEmpty(opIds)) {
                map.put("opIds", opIds);
            }
        }
        Map<String, Object> feeMap = commonInsureCost(map);
        List<Map<String, Object>> insureCostList = MapUtils.get(feeMap, "insureCostList");
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(feeMap, "insureIndividualVisitDTO");
        // 获取最新的费用批次号
        String feeBatch = insureIndividualCostService_consumer.selectLastCostInfo(map).getData();
        Integer batchNo = 0;
        if(StringUtils.isNotEmpty(feeBatch)){
            batchNo = Integer.valueOf(feeBatch)+1;
        }
        Map<String,Object> outptCostMap =new HashMap<>();
        outptCostMap.put("hospCode",hospCode);
        map.put("insureCostList",insureCostList);
        map.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
        map.put("batchNo",batchNo);
        Boolean aBoolean = insureUnifiedPayOutptService_consumer.UP_2204(map).getData();
        //==============================事前明细审核=================================
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("hospCode",hospCode);
        paramMap.put("code","DETAILAUDIT_SWITCH");
        WrapperResponse<SysParameterDTO> response = sysParameterService_consumer.getParameterByCode(paramMap);
        if (WrapperResponse.SUCCESS != response.getCode()) {
            throw new AppException(response.getMessage());
        }
        if (ObjectUtil.isNotEmpty(response.getData())&&"1".equals(response.getData().getValue())) {
            if (ObjectUtil.isEmpty(map.get("outptVisitDTO"))) {
               throw new AppException("事前明细审核未获取到门诊就诊信息！");
            }
            OutptVisitDTO outptVisitDTO = (OutptVisitDTO) map.get("outptVisitDTO");
            AnalysisDTO analysisDTO = this.initAnalysisDTO(outptVisitDTO,insureIndividualVisitDTO,insureCostList);
            Map<String, Object> inMap = new HashMap<>();
            inMap.put("hospCode",hospCode);
            inMap.put("analysisDTO",analysisDTO);
            inMap.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
            AnaResJudgeDTO anaResJudgeDTO = insureDetailAuditService.upldBeforeAnalysisDTO(inMap);
        }
        //========================================================================
        return map;
    }


    /**
     * @Method queryPatientPrescribeNoSettle
     * @Desrciption  查询病人已提交未结算的处方单号
     * @Param  outptPrescribeDO
     * @Author liuliyun
     * @Date  2021/09/03 10:50
     * @Return PageDTO
     **/
    @Override
    public PageDTO queryPatientPrescribeNoSettle(Map map) {
        OutptPrescribeDO outptPrescribeDO =MapUtils.get(map,"outptPrescribeDO");
        PageHelper.startPage(outptPrescribeDO.getPageNo(),outptPrescribeDO.getPageSize());
        List<OutptPrescribeDO> prescribeDOS = outptCostDAO.queryPatientPrescribeNoSettle(outptPrescribeDO);
        return PageDTO.of(prescribeDOS);
    }

    /**
     * @Method queryOutptPrescribeCostList
     * @Desrciption  根据处方id查询处方费用
     * @Param  outptPrescribeDO
     * @Author liuliyun
     * @Date  2021/09/06 09:07
     * @Return List<OutptCostDTO>
     **/
    @Override
    public List<OutptCostDTO> queryOutptPrescribeCostList(Map map) {
        map.put("statusCode",Constants.ZTBZ.ZC);//状态标志 = 正常
        map.put("settleCodes", new String[]{Constants.JSZT.WJS, Constants.JSZT.YUJS});//结算状态代码：未结算,预结算
        return outptCostDAO.queryOutptPrescribeCostList(map);
    }

    // 支付成功后，调用体检接口
    public void phyIsCallBack(OutptVisitDTO outptVisitDTO){
        Map<String, Object> stringObjectMap = new HashMap<>();
        Map queryMap = new HashMap();
        queryMap.put("hospCode", outptVisitDTO.getHospCode());
        queryMap.put("code", "TJ_URL");
        SysParameterDTO sysParameterDTO= sysParameterService_consumer.getParameterByCode(queryMap).getData();
        String url ="";
        if(sysParameterDTO!=null){
            url = sysParameterDTO.getValue()+"/updateCost";
        }else {
            url ="http://172.26.62.219:8899/hsa-phys/web/phys/interface/his/updateCost";
        }
        stringObjectMap.put("url",url);
        List<Map> maps =new ArrayList<>();
        if(outptVisitDTO.getOutptCostDTOList()!=null && outptVisitDTO.getOutptCostDTOList().size()>0) {
            for (OutptCostDTO outptCostDTO:outptVisitDTO.getOutptCostDTOList()) {
                Map<String, Object> item =new HashMap<>();
                item.put("HospCode", outptVisitDTO.getHospCode());
                item.put("VisitId", outptCostDTO.getVisitId());
                item.put("ItemId", outptCostDTO.getItemId());
                item.put("actualPrice", outptCostDTO.getRealityPrice());
                item.put("SettleId",outptCostDTO.getSettleId());
                maps.add(item);
            }
        }
        String json = JSONObject.toJSONString(maps);
        stringObjectMap.put("param",json);
        logger.info("体检收费入参:" + json);
        String resultStr = HttpConnectUtil.doPost(stringObjectMap);
        if (StringUtils.isEmpty(resultStr)) {
            throw new AppException("体检退费反参信息为空，请联系管理员。");
        }
        logger.info("体检收费反参:" + resultStr);
        JSONObject resultObj = JSON.parseObject(resultStr);
        String code = resultObj.get("code").toString();
        if (!"0".equals(code)) {
            throw new AppException((String) resultObj.get("message"));
        }
    }

    /**
     * @Menthod: queryCreditCharge()
     * @Desrciption: 挂账查询
     * @Param: OutptSettleDTO--门诊结算DTO
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/2/28 11:37
     * @Return: cn.hsa.sys.PageDTO
     **/
    @Override
    public PageDTO queryCreditCharge(OutptSettleDTO outptSettleDTO) {
        //校验时间格式
        String startTime = outptSettleDTO.getStartTime();
        String endTime = outptSettleDTO.getEndTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            sdf.parse(startTime);
        } catch (Exception e) {
            outptSettleDTO.setStartTime("");
        }
        try {
            sdf.parse(endTime);
        } catch (Exception e) {
            outptSettleDTO.setEndTime("");
        }
        //分页查询
        PageHelper.startPage(outptSettleDTO.getPageNo(), outptSettleDTO.getPageSize());
        List<OutptSettleDTO> outptSettleDTOS = outptSettleDAO.queryCreditCharge(outptSettleDTO);
        return PageDTO.of(outptSettleDTOS);
    }

    /**
     * @Menthod: updateCreditStatus()
     * @Desrciption: 更新补缴状态
     * @Param: OutptSettleDTO--门诊结算DTO
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/3/1 11:15
     * @Return: int
     **/
    @Override
    public Boolean updateCreditStatus(OutptSettleDTO outptSettleDTO){
        outptSettleDAO.updateRegisterCreditStatus(outptSettleDTO);
       int count = outptSettleDAO.updateCreditStatus(outptSettleDTO);
       return count>0;
    }

    /**
     * @param
     * @Description 挂账发票打印
     * @author: liuliyun
     * @date: 2022/3/2
     * @return: java.lang.String
     **/
    @Override
    public Boolean updateCreditQueryInovicePrint(OutinInvoiceDTO outinInvoiceDTO) {
        OutptSettleDTO outptSettleDTO =new OutptSettleDTO();
        outptSettleDTO.setHospCode(outinInvoiceDTO.getHospCode());
        outptSettleDTO.setId(outinInvoiceDTO.getSettleId());
        outptSettleDTO.setVisitId(outinInvoiceDTO.getVisitId());
        List<OutptSettleInvoiceDTO> pjList = outptSettleInvoiceDAO.getSetteleInvoiceByParams(outptSettleDTO);
        if (pjList!=null&&pjList.size()>0){
            for (OutptSettleInvoiceDTO outptSettleInvoiceDTO: pjList){
                if (StringUtils.isNotEmpty(outptSettleInvoiceDTO.getInvoiceId())){
                    throw new AppException("该病人发票已打，如需补打请去补打重打页面");
                }
            }
        }
        OutinInvoiceDO selectEntity = outinInvoiceDAO.getOutinInvoiceById(outinInvoiceDTO);
        // 获取当前登录人的最新的发票号段
        int num = 0;
        if (selectEntity == null || selectEntity.getNum() == 0) {
            // 查询是否有新的在用发票
            OutinInvoiceDTO selectDto = new OutinInvoiceDTO();
            selectDto.setHospCode(outinInvoiceDTO.getHospCode());
            selectDto.setUseId(outinInvoiceDTO.getUseId());
            selectDto.setReceiveId(outinInvoiceDTO.getReceiveId());
            selectDto.setStatusCode(Constants.PJSYZT.ZY);

            List<String> strList = new ArrayList();
            strList.add(Constants.PJLX.TY);
            strList.add(Constants.PJLX.MZ);
            strList.add(Constants.PJLX.MZTY);
            selectDto.setTypeCodeList(strList);

            Map<String, Object> map = new HashMap<>();
            map.put("code", "FPHB_FS");
            map.put("hospCode", outinInvoiceDTO.getHospCode());
            WrapperResponse<SysParameterDTO> wr = sysParameterService.getParameterByCode(map);

            selectDto.setType(wr.getData().getValue());
            List list = outinInvoiceDAO.getInvoiceList(selectDto);
            if (ListUtils.isEmpty(list)) {
                throw new AppException("当前类型发票已用完，请重新领用发票");
            }

            if (list.size() > 1) {
                throw new AppException("出现多个在用号段的发票，请联系管理员");
            }
            selectEntity = (OutinInvoiceDO) list.get(0);

            if (selectEntity.getNum() == 0) {
                throw new AppException("出现数量为0的在用发票，请联系管理员");
            }
        }
        //  当前号码
        String currNo = selectEntity.getCurrNo();

        //  终止号码
        String endNo = selectEntity.getEndNo();
        outinInvoiceDTO.setId(selectEntity.getId());
        // 获取需要打印多少张票据
        int pjSize = pjList.size();
        // 当前号码
        Integer currNoInt = Integer.valueOf(currNo);
        // 结束号码
        Integer endNoInt = Integer.valueOf(endNo);

        if (pjSize + currNoInt > endNoInt + 1) {
            throw new AppException("门诊结算-发票打印:发票打印失败,当前需要打印[" + pjSize + "],目前发票票据数量不足,请进行重打补打操作!");
        }

        List<OutptSettleInvoiceDTO> invoiceDTOList = new ArrayList<>();

        //  循环使用票据
        for (OutptSettleInvoiceDTO outptSettleInvoiceDTO : pjList) {
            Map<String, Object> map = new HashMap<String, Object>();
            outinInvoiceDTO.setCurrNo(String.valueOf(currNoInt));
            outinInvoiceDTO.setDqCurrNo(currNo);
            map.put("hospCode", outinInvoiceDTO.getHospCode());
            map.put("outinInvoiceDTO", outinInvoiceDTO);
            // 执行使用发票
            OutinInvoiceDetailDO outinInvoiceDetailDO = outinInvoiceService.updateInvoiceStatus(map).getData();
            outptSettleInvoiceDTO.setInvoiceId(outinInvoiceDetailDO.getInvoiceId());
            outptSettleInvoiceDTO.setInvoiceDetailId(outinInvoiceDetailDO.getId());//发票明细id
            outptSettleInvoiceDTO.setInvoiceNo(String.valueOf(outinInvoiceDetailDO.getInvoiceNo()));//发票号码
            outptSettleInvoiceDTO.setPrintNum(1);
            outptSettleInvoiceDTO.setPrintTime(new Date());
            outptSettleInvoiceDTO.setHospCode(outinInvoiceDetailDO.getHospCode());
            // 发票使用后修改票据表信息
            invoiceDTOList.add(outptSettleInvoiceDTO);
            currNoInt++;
        }

        // 回写票据表数据.
        if (!invoiceDTOList.isEmpty()) {
            outptSettleInvoiceDAO.updateOutptSettleInvoicesBypj(invoiceDTOList);
        }
        return  true;
    }

    /**
     * 【6201】费用明细上传
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-04-25 16:06
     * @return java.lang.Boolean
     */
    @Override
    public Boolean uploadOnlineFeeDetail(Map map) {
      OnlinePayFeeDTO onlinePayFeeDTO = MapUtils.get(map, "onlinePayFeeDTO");
      String hospCode = map.get("hospCode").toString();
      InsureIndividualVisitDTO insureIndividualVisitDTO = null;
      //判断入参是否传入
      if (ObjectUtil.isEmpty(onlinePayFeeDTO.getId())) {
        throw new AppException("请传入就诊ID!");
      }
      //就诊id
      String visitId = onlinePayFeeDTO.getId();
      Map<String, Object> insureVisitParam = new HashMap<String, Object>();
      insureVisitParam.put("id", visitId);
      insureVisitParam.put("hospCode", hospCode);
      insureIndividualVisitDTO = insureIndividualVisitService_consumer.getInsureIndividualVisitById(insureVisitParam);
      if (insureIndividualVisitDTO == null || StringUtils.isEmpty(insureIndividualVisitDTO.getId())) {
        throw new AppException("未查找到医保就诊信息，请做医保登记。");
      }
      map.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
      // 调用 统一支付平台-【6201】费用明细上传
      insureUnifiedPayOutptService_consumer.UP6201(map).getData();
      return true;
    }

    /**
     * 6301-医保订单结算结果查询
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-05-09 15:17
     * @return cn.hsa.module.dzpz.hainan.SeltSucCallbackDTO
     */
    @Override
    public WrapperResponse queryInsureSetlResult(Map map) {
      logger.info("UP_6301-页面组合入参map-{}",  JSON.toJSONString(map));
      String hospCode = map.get("hospCode").toString();
      OutptVisitDTO outptVisitDTO = MapUtils.get(map, "outptVisitDTO");

      if (ObjectUtil.isEmpty(outptVisitDTO.getVisitId())) {
        throw new AppException("请传入就诊ID!");
      }
      //就诊id
      String visitId = outptVisitDTO.getVisitId();
      Map<String, Object> insureVisitParam = new HashMap<String, Object>();
      insureVisitParam.put("id", visitId);
      insureVisitParam.put("hospCode", hospCode);
      //医保就医信息
      InsureIndividualVisitDTO insureIndividualVisitDTO =
          insureIndividualVisitService_consumer.getInsureIndividualVisitById(insureVisitParam);
      if (insureIndividualVisitDTO == null || StringUtils.isEmpty(insureIndividualVisitDTO.getId())) {
        throw new AppException("未查找到医保就诊信息，请做医保登记。");
      }
      map.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
      map.put("outptVisitDTO",outptVisitDTO);
      // 调用 【6301】医保订单结算结果查询
      Map<String, Object> resultMap = (Map<String, Object>) insureUnifiedPayOutptService_consumer.UP6301(map).getData();
      Map<String, Object> dataMap = MapUtils.get(resultMap, "data");
      SeltSucCallbackDTO seltSucCallbackDTO = FastJsonUtils.fromJson(FastJsonUtils.toJson(dataMap),
          SeltSucCallbackDTO.class);
      //判断是否结算完成，修改结算信息数据
      Map<String, Object> result =  updateSettleInfo(map,seltSucCallbackDTO);
      //成功
      if ("6".equals(MapUtils.get(result, "resultCode"))) {
        return WrapperResponse.success("支付成功", result);
      }else{
        return WrapperResponse.success("支付失败", MapUtils.get(result, "resultCode"));
      }

    }

    /**
     * 0	已保存
     * 1	预结算完成
     * 2	结算中
     * 3	自费完成
     * 4	医保支付完成
     * 5	院内结算完成
     * 6	结算完成
     * 7	已退款
     * 8	已医保全部退款
     * 9	仅自费全部退款
     * 10	仅自费部分退款
     * 11	医保全部退自费部分退款
     * 12	已撤销
     * 13	医保已撤销
     * 14	异常
     * 15	结算失败
     * 16	医保结算失败自费冲正失败
     * 判断是否结算完成，修改结算信息数据
     * @param map
     * @param seltSucCallbackDTO
     * @Author 医保开发二部-湛康
     * @Date 2022-05-12 9:08
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    private Map<String, Object> updateSettleInfo(Map map,SeltSucCallbackDTO seltSucCallbackDTO){
      //订单状态为6 结算成功才修改信息
      if (ObjectUtil.isNotEmpty(seltSucCallbackDTO.getOrdStas())&&"6".equals(seltSucCallbackDTO.getOrdStas())){
        //获取个人信息
        OutptVisitDTO outptVisitDTO = MapUtils.get(map, "outptVisitDTO");
        //获取结算信息
        OutptSettleDTO outptSettleDTO = MapUtils.get(map, "outptSettleDTO");

        //参数获取
        String hospCode = outptVisitDTO.getHospCode(); //医院编码
        String visitId = outptVisitDTO.getId(); //就诊id
        String userId = outptVisitDTO.getCrteId(); //当前登录用户id
        String userName = outptVisitDTO.getCrteName(); //当前登录用户姓名
        String depId = outptVisitDTO.getDeptId(); //执行科室
        String code = outptVisitDTO.getCode(); // 操作人编码
        List<Map<String, Object>> outinInvoiceList = null;//返回发票打印的费用统计信息
        String settleId = SnowflakeUtils.getId();//结算id

        //先判断是否已经调用过接口，并返回成功，则不再更新表操作
        OutptSettleDTO outptSettleDTO1 = new OutptSettleDTO();
        outptSettleDTO1.setHospCode(outptVisitDTO.getHospCode());
        outptSettleDTO1.setVisitId(visitId);
        Map<String, Object> settleMap = new HashMap<>();
        settleMap.put("id", settleId);
        settleMap.put("hospCode", outptVisitDTO.getHospCode());
        List<OutptSettleDTO> list = outptSettleDAO.findByCondition(outptSettleDTO1);
        if (CollectionUtil.isNotEmpty(list)){
          JSONObject result = new JSONObject();
          result.put("outptVisit", outptVisitDTO);//个人信息
          result.put("settleNo", list.get(0).getSettleNo());
          result.put("resultCode","6");
          return result;
          /*if ("1".equals(dto.getIsSettle())){
            JSONObject result = new JSONObject();
            result.put("outptVisit", outptVisitDTO);//个人信息
            result.put("settleNo", dto.getSettleNo());
            result.put("resultCode","6");
            return result;
          }*/
        }
        //发票制单
        saveInvoiceInfo(outptSettleDTO);

        // 1、校验是否使用发票，是否存在发票段（没有返回错误信息，页面给出选择发票段信息）
        OutinInvoiceDTO outinInvoiceDTO = checkUseInvoice(outptSettleDTO.getIsInvoice(),outptVisitDTO);

        // 2、 根据当前结算id，查询费用表,更新医技申请单状态
        List<OutptCostDTO> costDTOList = updateMedicApply(outptVisitDTO,settleId);

        //保存支付方式（结算）
        saveOnlineOutptPays( outptVisitDTO, settleId,seltSucCallbackDTO);

        //更新费用表结算ID  settleId
        updateCostSettleId(costDTOList,settleId);

        // 生成领药申请单，校验库存、领药申请单明细
        Map<String, Object> queryParam = new HashMap<String, Object>();
        queryParam.put("hospCode", outptVisitDTO.getHospCode());//医院编码
        queryParam.put("pfTypeCode", outptVisitDTO.getPreferentialTypeId());//优惠类型
        queryParam.put("items", costDTOList);//当前用户的费用信息
        List<OutptCostDTO> outptCostDTOS = outptCostDAO.queryDrugMaterialListByIds(queryParam);

        // 3、 校验药品、材料库存，并生成领药申请单明细
        Map<String, Object> tempMap = this.checkStockAndCreatePharOutReceiveDetail(costDTOList, outptCostDTOS,  outptVisitDTO.getHospCode());

        // 6、 根据费用信息修改本次结算的费用状态
        List<String> ids = (List<String>) tempMap.get("ids");
        Map<String, Object> costParam = new HashMap<String, Object>();
        costParam.put("settleCode", Constants.JSZT.YIJS);//费用状态 = 已结算
        costParam.put("ids", ids);//费用id
        outptCostDAO.editCostSettleCodeByIDS(costParam);

        // 7、 修改门诊结算表此次结算信息状态 结算后需要将结算单号返回给前端
        String settleNo = updateSettleStatus(settleId,outptVisitDTO,seltSucCallbackDTO);

        // 8、 修改处方表结算信息
        List<String> outptPrescribeIds = (List<String>) tempMap.get("outptPrescribeIds");
        if (!outptPrescribeIds.isEmpty()) {
          Map<String, Object> outptPrescribeParam = new HashMap<String, Object>();
          outptPrescribeParam.put("hospCode", outptVisitDTO.getHospCode());//医院编码
          outptPrescribeParam.put("ids", outptPrescribeIds);//处方ids
          outptPrescribeParam.put("settleId", settleId );//结算id
          outptPrescribeParam.put("isSettle", Constants.SF.S);//是否结算 = 是
          outptCostDAO.updateOutptPrescribeByIds(outptPrescribeParam);
        }

        // 9、判断是否需要生成发票信息
        if (outptSettleDTO.getIsInvoice()) {
          outinInvoiceDTO.setVisitId(visitId);
          outinInvoiceDTO.setSettleId(settleId);
        }

        // 10、 取最佳领药窗口，生成领药申请单（主单），保存领药申请单与领药申请单详单
        List<PharOutReceiveDetailDO> pharOutReceiveDetailDOList = (List<PharOutReceiveDetailDO>) tempMap.get("pharOutReceiveDetailDOList");
        Map<String, Map<String, Object>> pharOutReceiveMap = (Map<String, Map<String, Object>>) tempMap.get("pharOutReceiveMap");
        this.savePharOutReceive(hospCode, visitId, depId, userId, userName, settleId,
            pharOutReceiveMap, costDTOList, pharOutReceiveDetailDOList);

        // 11.修改医保结算表， 插入门诊医保明细
        updateInsureSettle(settleId,outptVisitDTO,seltSucCallbackDTO);

        //12
        Map<String, Object> outInvoiceParam = new HashMap<String, Object>();
        outInvoiceParam.put("hospCode", hospCode);
        OutinInvoiceDTO outinInvoiceDTO1 = new OutinInvoiceDTO();
        outinInvoiceDTO1.setHospCode(hospCode);//医院编码
        outinInvoiceDTO1.setInvoiceType(Constants.PJLX.MZ);//发票类型 = 门诊
        outinInvoiceDTO1.setSettleId(settleId);//结算id
        outInvoiceParam.put("outinInvoiceDTO", outinInvoiceDTO1);
        outinInvoiceList = outinInvoiceService.queryItemInfoByParams(outInvoiceParam).getData();
        outptVisitDTO.setReceiveName(outinInvoiceDTO.getReceiveName());
        outptVisitDTO.setPrefix(outinInvoiceDTO.getPrefix());
        // 发票不分单返回发票号
        outptVisitDTO.setInvoiceNo(outinInvoiceDTO.getCurrNo());

        JSONObject result = new JSONObject();
        result.put("outptVisit", outptVisitDTO);//个人信息
        result.put("outinInvoice", outinInvoiceList);//费用统计信息
        result.put("outinInvoiceDTO", outinInvoiceDTO);//费用统计信息
        result.put("settleNo", settleNo);
        result.put("resultCode","6");
        return result;
      }else{
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("resultCode",seltSucCallbackDTO.getOrdStas());
        return result;
      }
    }

    /**
     *  更新费用表结算ID  settleId
     * @Author 医保开发二部-湛康
     * @Date 2022-05-24 10:01
     * @return void
     */
    private void updateCostSettleId(List<OutptCostDTO> costDTOList,String settleId){
      outptCostDAO.updateCostSettleId(settleId,  costDTOList);
    }

    /**
     * 修改医保结算表 insure_individual_settle；结算状态 = 结算
     * @Author 医保开发二部-湛康
     * @Date 2022-05-16 10:04
     * @return void
     */
    private void updateInsureSettle(String settleId, OutptVisitDTO outptVisitDTO,SeltSucCallbackDTO seltSucCallbackDTO){
      //获取医保结算信息
      /*Map<String, Object> individualSettleParam = new HashMap<String, Object>();
      individualSettleParam.put("hospCode", outptVisitDTO.getHospCode());
      InsureIndividualSettleDTO insureIndividualSettleDTO = new InsureIndividualSettleDTO();
      insureIndividualSettleDTO.setHospCode(outptVisitDTO.getHospCode());//医院编码
      insureIndividualSettleDTO.setVisitId(outptVisitDTO.getId());//就诊id
      insureIndividualSettleDTO.setSettleId(settleId);//结算id
      individualSettleParam.put("insureIndividualSettleDTO", insureIndividualSettleDTO);
      insureIndividualSettleDTO = insureIndividualSettleService.findByCondition(individualSettleParam);
      if (insureIndividualSettleDTO == null) {
        throw new AppException("未获取到医保结算信息，请联系管理员。");
      }*/

      InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
      insureIndividualVisitDTO.setVisitId(outptVisitDTO.getVisitId());
      insureIndividualVisitDTO.setHospCode(outptVisitDTO.getHospCode());
      Map<String,Object> visitMap = new HashMap<>();
      visitMap.put("hospCode",outptVisitDTO.getHospCode());
      visitMap.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
      insureIndividualVisitDTO =  insureIndividualVisitService_consumer.selectInsureInfo(visitMap).getData();
      if(insureIndividualVisitDTO == null){
        throw new AppException("根据就诊未查询到医保就诊登记信息");
      }
      //查询医保机构信息
      Map<String, Object> configurationParam = new HashMap<String, Object>();
      configurationParam.put("hospCode", outptVisitDTO.getHospCode());//医院编码
      InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();

      insureConfigurationDTO.setHospCode(outptVisitDTO.getHospCode());
      insureConfigurationDTO.setOrgCode(insureIndividualVisitDTO.getMedicineOrgCode()); //医疗机构编码
      configurationParam.put("insureConfigurationDTO", insureConfigurationDTO);
      List<InsureConfigurationDTO> insureConfigurationDTOS = insureConfigurationService.queryAll(configurationParam).getData();
      if (insureConfigurationDTOS == null || insureConfigurationDTOS.isEmpty()) {
        throw new AppException("未找到医保配置信息。");
      }
      insureConfigurationDTO = insureConfigurationDTOS.get(0);
      //门诊医保明细 outpt_insure_pay
      OutptInsurePayDO outptInsurePayDO = new OutptInsurePayDO();
      outptInsurePayDO.setId(SnowflakeUtils.getId());//id
      outptInsurePayDO.setHospCode(outptVisitDTO.getHospCode());//医院编码
      outptInsurePayDO.setSettleId(settleId);//结算id
      outptInsurePayDO.setVisitId(outptVisitDTO.getId());//就诊id
      outptInsurePayDO.setTypeCode(null);//合同单位明细代码
      outptInsurePayDO.setOrgNo(insureConfigurationDTO.getOrgCode());//医保机构编码
      outptInsurePayDO.setOrgName(insureConfigurationDTO.getName());//医保机构名称
      outptInsurePayDO.setTotalPrice(seltSucCallbackDTO.getFundPay());//医保报销总金额
      outptInsurePayDAO.insertSelective(outptInsurePayDO);

      // 更新门诊结算表的个人账户支付
      OutptSettleDO outptSettleDO = new OutptSettleDO();
      outptSettleDO.setHospCode(outptVisitDTO.getHospCode());
      outptSettleDO.setId(settleId);
      outptSettleDO.setAcctPay(seltSucCallbackDTO.getPsnAcctPay());
      outptSettleDAO.updateByPrimaryKeySelective(outptSettleDO);

      //插入医保结算表 insure_individual_settle
      InsureIndividualSettleDO insureIndividualSettleDO = new InsureIndividualSettleDO();
      insureIndividualSettleDO.setId(SnowflakeUtils.getId());//主键
      insureIndividualSettleDO.setHospCode(outptVisitDTO.getHospCode());//医院编码
      insureIndividualSettleDO.setVisitId(outptVisitDTO.getId());//就诊id
      insureIndividualSettleDO.setSettleId(settleId);//结算id
      insureIndividualSettleDO.setIsHospital(Constants.SF.F);//是否住院（SF）
      insureIndividualSettleDO.setVisitNo(outptVisitDTO.getVisitNo());//就诊登记号
      insureIndividualSettleDO.setDischargeDnCode(null);//出院疾病诊断编码
      insureIndividualSettleDO.setInsureOrgCode(insureConfigurationDTO.getCode());//医保机构编码
      insureIndividualSettleDO.setInsureRegCode(insureConfigurationDTO.getRegCode());//医保注册编码
      insureIndividualSettleDO.setMedicineOrgCode(insureConfigurationDTO.getOrgCode());//医疗机构编码
      insureIndividualSettleDO.setSettleway(Constants.JSFS.PTJS);//结算方式,01 普通结算,02 包干结算
      insureIndividualSettleDO.setBeforeSettle(null);//结算前账户余额 todo
      insureIndividualSettleDO.setLastSettle(null);//结算后账户余额
      insureIndividualSettleDO.setState(Constants.ZTBZ.ZC);//状态标志,0正常，2冲红，1，被冲红
      insureIndividualSettleDO.setSettleState(Constants.YBJSZT.JS);//医保结算状态;0试算，1结算
      insureIndividualSettleDO.setAka130(insureIndividualVisitDTO.getAka130());//业务类型
      insureIndividualSettleDO.setBka006(insureIndividualVisitDTO.getBka006());//待遇类型
      insureIndividualSettleDO.setIsAccount(BigDecimalUtils.isZero(seltSucCallbackDTO.getPsnAcctPay()) ? Constants.SF.F :
          Constants.SF.S);//当前结算是否使用个人账户;0是，1否
      insureIndividualSettleDO.setRemark(seltSucCallbackDTO.getRevsToken());//备注
      insureIndividualSettleDO.setCrteId(outptVisitDTO.getCrteId());//创建人ID
      insureIndividualSettleDO.setCrteName(outptVisitDTO.getCrteName());//创建人姓名
      insureIndividualSettleDO.setCrteTime(new Date());//创建时间
      // 处理金额
      insureIndividualSettleDO.setTotalPrice(seltSucCallbackDTO.getFeeSumamt());// 本次医疗总费用
      insureIndividualSettleDO.setInsurePrice(seltSucCallbackDTO.getFundPay());//医保支付
      insureIndividualSettleDO.setPlanPrice(seltSucCallbackDTO.getFundPay());//统筹基金支付  todo
      insureIndividualSettleDO.setPersonalPrice(seltSucCallbackDTO.getPsnAcctPay());//个人账户支付
      Map<String, Object> insureSettleParam = new HashMap<String, Object>();
      insureSettleParam.put("hospCode", outptVisitDTO.getHospCode());//医院编码
      insureSettleParam.put("insureIndividualSettleDO", insureIndividualSettleDO);
      insureIndividualSettleService.insertSelective(insureSettleParam);

    }

    /**
     * 修改门诊结算表此次结算信息状态
     * @Author 医保开发二部-湛康
     * @Date 2022-05-13 10:39
     * @return void
     */
    private String updateSettleStatus(String settleId, OutptVisitDTO outptVisitDTO,SeltSucCallbackDTO seltSucCallbackDTO){
      // 生成结算数据，保存门诊结算表
      OutptSettleDO outptSettleDO = new OutptSettleDO();
      outptSettleDO.setId(settleId);//id
      outptSettleDO.setHospCode(outptVisitDTO.getHospCode());//医院编码
      outptSettleDO.setVisitId(outptVisitDTO.getId());//就诊id
      outptSettleDO.setSettleNo(getOrderNo(outptVisitDTO.getHospCode(), Constants.ORDERRULE.JZ));//结算单号
      outptSettleDO.setPatientCode(outptVisitDTO.getPatientCode());//病人类型
      outptSettleDO.setSettleTime(new Date());//结算时间
      outptSettleDO.setTotalPrice(seltSucCallbackDTO.getFeeSumamt());//总金额
      outptSettleDO.setRealityPrice(seltSucCallbackDTO.getFeeSumamt());//优惠后总金额
      outptSettleDO.setTruncPrice(seltSucCallbackDTO.getFeeSumamt());//舍入金额（存在正负金额）
      outptSettleDO.setActualPrice(null);//实收金额
      outptSettleDO.setSelfPrice(new BigDecimal(0));// 个人自付金额减去舍人金额
      outptSettleDO.setMiPrice(seltSucCallbackDTO.getFundPay());//统筹支付金额 医保基金支付
      outptSettleDO.setIsSettle(Constants.SF.S);//是否结算（SF）
      outptSettleDO.setDailySettleId(null);//日结缴款ID
      outptSettleDO.setStatusCode(Constants.ZTBZ.ZC);//状态标志代码（ZTBZ）;正常
      outptSettleDO.setRedId(null);//冲红ID
      outptSettleDO.setIsPrint(Constants.SF.F);//是否打印（SF）;否
      outptSettleDO.setOldSettleId(null);//原结算ID
      outptSettleDO.setIsPrintList(Constants.SF.F);//是否打印清单（SF）
      outptSettleDO.setPrintListTime(null);//清单打印时间
      outptSettleDO.setSourcePayCode("4");//支付来源代码（ZFLY，第三方对接） //4 移动支付
      outptSettleDO.setOrderNo(seltSucCallbackDTO.getPayOrdId());//支付订单号（第三方订单号）
      outptSettleDO.setCrteId(outptVisitDTO.getCrteId());//创建人id
      outptSettleDO.setCrteName(outptVisitDTO.getCrteName());//创建人名称
      outptSettleDO.setCrteTime(new Date());//创建时间
      outptSettleDO.setOneSettleId(settleId); // 记录下第一次结算id
      // 保存门诊结算（试算）费用信息
      outptSettleDAO.insertSelective(outptSettleDO);
      outptSettleDO.setId(settleId);//结算id
      SysParameterDO sysParameterDO = getSysParameter(outptVisitDTO.getHospCode(), Constants.HOSPCODE_DISCOUNTS_KEY);

      return outptSettleDO.getSettleNo();
    }

    /**
     * 保存支付方式（结算）
     * @Author 医保开发二部-湛康
     * @Date 2022-05-12 20:58
     * @return void
     */
    private void saveOnlineOutptPays(OutptVisitDTO outptVisitDTO, String settleId,SeltSucCallbackDTO seltSucCallbackDTO){
      List<OutptPayDO> outptPayDOS = new ArrayList<OutptPayDO>();

      OutptPayDO outptPayDO = new OutptPayDO();
      outptPayDO.setId(SnowflakeUtils.getId());//id
      outptPayDO.setHospCode(outptVisitDTO.getHospCode());//医院编码
      outptPayDO.setSettleId(settleId);//结算id
      outptPayDO.setVisitId(outptVisitDTO.getVisitId());//就诊id
      outptPayDO.setPayCode("9");   //9：移动支付
      outptPayDO.setServicePrice( new BigDecimal(0));//手续费
      outptPayDO.setOrderNo(seltSucCallbackDTO.getPayOrdId());
      outptPayDOS.add(outptPayDO);
      //批量保存结算信息
      if (!ListUtils.isEmpty(outptPayDOS)) {
        outptPayDAO.batchInsertOutptPay(outptPayDOS);
      }
    }

    /**
     *  根据当前结算id，查询费用表,更新医技申请单状态
     * @Author 医保开发二部-湛康
     * @Date 2022-05-12 19:28
     * @return void
     */
    private List<OutptCostDTO> updateMedicApply(OutptVisitDTO outptVisitDTO,String settleId){
      List<OutptCostDTO> outptCostDTOList = outptVisitDTO.getOutptCostDTOList();
      if (!ListUtils.isEmpty(outptCostDTOList)) {
        outptCostDAO.updateMedicApply(outptVisitDTO.getId(), outptVisitDTO.getHospCode(), "02", outptCostDTOList);
      }
      return outptCostDTOList;
    }

    /**
     * 1、校验是否使用发票，是否存在发票段（没有返回错误信息，页面给出选择发票段信息）
     * @Author 医保开发二部-湛康
     * @Date 2022-05-12 14:46
     * @return void
     */
    private OutinInvoiceDTO checkUseInvoice(Boolean isInvoice,OutptVisitDTO outptVisitDTO){
      OutinInvoiceDTO outinInvoiceDTO = new OutinInvoiceDTO();//发票段信息
      if (isInvoice) {
        outinInvoiceDTO.setHospCode(outptVisitDTO.getHospCode());//医院编码
        outinInvoiceDTO.setUseId(outptVisitDTO.getCrteId());//发票使用人id
        List<String> typeCode = new ArrayList<String>();//票据类型（0、全院通用，1、门诊发票，2、挂号发票，3、门诊通用，4、住院）
        Collections.addAll(typeCode, Constants.PJLX.TY, Constants.PJLX.MZ, Constants.PJLX.MZTY);
        outinInvoiceDTO.setTypeCodeList(typeCode);//0、全院通用；1、门诊发票；3、门诊通用
        //校验是否有在用状态的发票段，有就返回在用的发票信息
        outinInvoiceDTO.setStatusCode(Constants.PJSYZT.ZY);//使用状态 = 在用状态
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hospCode", outptVisitDTO.getHospCode());
        map.put("outinInvoiceDTO", outinInvoiceDTO);
        List<OutinInvoiceDTO> outinInvoiceDTOS = outinInvoiceService.updateForOutinInvoiceQuery(map).getData();
        if (outinInvoiceDTOS == null || outinInvoiceDTOS.size() != 1) {
          //没有发票信息
          //return WrapperResponse.info(-2, "请选择发票段", outinInvoiceDTOS);
          throw new AppException("当前没有可用发票，请领取发票或取消使用发票再结算");
        }
        outinInvoiceDTO = outinInvoiceDTOS.get(0);
      }
      return outinInvoiceDTO;
    }


    /**
     * 发票制单
     * @param outptSettleDTO
     * @Author 医保开发二部-湛康
     * @Date 2022-05-12 14:36
     * @return
     */
    private void saveInvoiceInfo(OutptSettleDTO outptSettleDTO){
      // 门诊结算票据表
      List<OutptSettleInvoiceDTO> pjList = null;
      //门诊结算票据内容表
      List<OutptSettleInvoiceContentDTO> pjnrList = null;
      // 先进行发票分单
      Map<String, Object> pjMap = this.disposeFpfd(outptSettleDTO.getVisitId(), outptSettleDTO.getId(), outptSettleDTO.getHospCode());
      // 门诊结算票据表
      pjList = MapUtils.get(pjMap, "jspjJsonList");
      //门诊结算票据内容表
      pjnrList = MapUtils.get(pjMap, "fplbJsonList");
      saveOutptSettleInvoice(pjList, pjnrList);
   }

    /**
     * 6401-费用明细上传撤销
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-05-10 13:54
     * @return java.lang.Boolean
     */
    @Override
    public Boolean insureFeeRevoke(Map map) {
      String hospCode = map.get("hospCode").toString();
      //判断入参是否传入
      SetlResultQueryDTO setlResultQueryDTO = MapUtils.get(map, "setlResultQueryDTO");
      if (ObjectUtil.isEmpty(setlResultQueryDTO.getVisitId())) {
        throw new AppException("请传入就诊ID!");
      }
      //就诊id
      String visitId = setlResultQueryDTO.getVisitId();
      Map<String, Object> insureVisitParam = new HashMap<String, Object>();
      insureVisitParam.put("id", visitId);
      insureVisitParam.put("hospCode", hospCode);
      //医保就医信息
      InsureIndividualVisitDTO insureIndividualVisitDTO =
          insureIndividualVisitService_consumer.getInsureIndividualVisitById(insureVisitParam);
      if (insureIndividualVisitDTO == null || StringUtils.isEmpty(insureIndividualVisitDTO.getId())) {
        throw new AppException("未查找到医保就诊信息，请做医保登记。");
      }
      map.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
      map.put("setlResultQueryDTO",setlResultQueryDTO);
      Map<String, Object> resultMap = (Map<String, Object>) insureUnifiedPayOutptService_consumer.UP6401(map).getData();
      Map<String, Object> outptMap = MapUtils.get(resultMap, "output");
      String success = MapUtils.get(outptMap, "data");
      //撤销成功
      if ("true".equals(success)){
        //删除医保费用表
        insureIndividualCostService_consumer.deleteOutptInsureCost(insureVisitParam);
        //医保就诊表payOrdId
        Map map1 = new HashMap();
        map1.put("payToken", "");
        map1.put("payOrdId", "");
        map1.put("hospCode", hospCode);
        outptSettleDAO.updateIndividualVisitToken(map1);
        return true;
      }else{
        return false;
      }
    }

    /**
     * 6203-医保退费
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-05-16 16:00
     * @return java.lang.Boolean
     */
    @Override
    public Boolean insureRefund(Map map) {
      logger.info("UP_6203-页面入参map-{}",  JSON.toJSONString(map));
      //医院编码
      String hospCode = map.get("hospCode").toString();
      //个人基本信息
      OutptSettleDTO outptSettleDTO = MapUtils.get(map, "outptSettleDTO");
      if (ObjectUtil.isEmpty(outptSettleDTO.getVisitId())) {
        throw new AppException("请传入就诊ID!");
      }
      if (ObjectUtil.isEmpty(outptSettleDTO.getId())) {
        throw new AppException("请传入结算编号!");
      }
      //查询医保就诊信息
      Map<String, Object> insureVisitParam = new HashMap<String, Object>();
      insureVisitParam.put("id", outptSettleDTO.getVisitId());
      insureVisitParam.put("hospCode", hospCode);
      //医保就医信息
      InsureIndividualVisitDTO insureIndividualVisitDTO =
          insureIndividualVisitService_consumer.getInsureIndividualVisitById(insureVisitParam);
      if (insureIndividualVisitDTO == null || StringUtils.isEmpty(insureIndividualVisitDTO.getId())) {
        throw new AppException("未查找到医保就诊信息，请做医保登记！");
      }
      if (StringUtils.isEmpty(insureIndividualVisitDTO.getPayToken()) || StringUtils.isEmpty(insureIndividualVisitDTO.getPayOrdId())) {
        throw new AppException("未找到支付订单号，请先上传费用！");
      }
      InsureIndividualSettleDTO settleDTO = new InsureIndividualSettleDTO();
      settleDTO.setVisitId(outptSettleDTO.getVisitId());
      settleDTO.setHospCode(hospCode);
      settleDTO.setSettleId(outptSettleDTO.getId());
      settleDTO.setState("0");
      Map<String, Object> dataMap = new HashMap<>();
      dataMap.put("hospCode", hospCode);
      dataMap.put("insureIndividualSettleDTO", settleDTO);
      settleDTO = insureIndividualSettleService.findByCondition(dataMap);
      //判断医保结算信息
      if (ObjectUtil.isEmpty(settleDTO)){
        throw new AppException("未查找到医保结算信息，请做医保结算！");
      }
      map.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
      map.put("insureIndividualSettleDTO",settleDTO);
      Map<String, Object> resultMap = (Map<String, Object>) insureUnifiedPayOutptService_consumer.UP6203(map).getData();
      logger.info("UP_6203-医保接口出参map-{}",  JSON.toJSONString(resultMap));
      Map<String, Object> data = MapUtils.get(resultMap, "code");
      //判断退费成功，删除本地表数据 0：成功
      if ("SUCC".equals(MapUtils.get(data, "refStatus"))){

        Map map1 = new HashMap();
        map1.put("payToken", "");
        map1.put("payOrdId", "");
        map1.put("hospCode", hospCode);
        map1.put("visitId", outptSettleDTO.getVisitId());
        // 删除his的医保费用表数据
        outptSettleDAO.deleteInsureCost(map);
        outptSettleDAO.updateIndividualVisitToken(map);
        //todo 结算表状态 删除结算表数据
        outptSettleDAO.deleteById(outptSettleDTO.getId());
        //医保结算表删除数据
        Map<String, String> delIndividualSettleParam = new HashMap<String, String>();
        delIndividualSettleParam.put("hospCode", hospCode);//医院编码
        delIndividualSettleParam.put("visitId", outptSettleDTO.getVisitId());//就诊id
        delIndividualSettleParam.put("settleState", Constants.YBJSZT.JS);//结算标志 = 结算
        insureIndividualSettleService.delInsureIndividualSettleByVisitId(delIndividualSettleParam);
        return true;
      }else{
        throw new AppException("医保退费失败！");
      }
    }

  /**
   * 线上医保移动支付完成的结算订单，可通过此接口进行退款
   * @param map
   * @return
   */
    @Override
    public Map<String, Object> ampRefund(Map map) {
      //医院编码
      String hospCode = map.get("hospCode").toString();
      SetlRefundQueryDTO setlRefundQueryDTO = MapUtils.get(map, "setlRefundQueryDTO");
      if (ObjectUtil.isEmpty(setlRefundQueryDTO.getVisitId())) {
        throw new AppException("请传入就诊ID!");
      }
      if (ObjectUtil.isEmpty(setlRefundQueryDTO.getSettleId())) {
        throw new AppException("请传入结算ID!");
      }
      //查询医保就诊信息
      Map<String, Object> insureVisitParam = new HashMap<String, Object>();
      insureVisitParam.put("id", setlRefundQueryDTO.getVisitId());
      insureVisitParam.put("hospCode", hospCode);
      //医保就医信息
      InsureIndividualVisitDTO insureIndividualVisitDTO =
          insureIndividualVisitService_consumer.getInsureIndividualVisitById(insureVisitParam);
      if (insureIndividualVisitDTO == null || StringUtils.isEmpty(insureIndividualVisitDTO.getId())) {
        throw new AppException("未查找到医保就诊信息，请做医保登记！");
      }
      /*if (StringUtils.isEmpty(insureIndividualVisitDTO.getPayToken()) || StringUtils.isEmpty(insureIndividualVisitDTO.getPayOrdId())) {
        throw new AppException("未找到支付订单号，请先上传费用！");
      }*/
      //判断是否已医保结算
      InsureIndividualSettleDTO settleDTO = new InsureIndividualSettleDTO();
      settleDTO.setVisitId(setlRefundQueryDTO.getVisitId());
      settleDTO.setHospCode(hospCode);
      settleDTO.setSettleId(setlRefundQueryDTO.getSettleId());
      settleDTO.setState("0");
      Map<String, Object> dataMap = new HashMap<>();
      dataMap.put("hospCode", hospCode);
      dataMap.put("insureIndividualSettleDTO", settleDTO);
      settleDTO = insureIndividualSettleService.findByCondition(dataMap);
      //判断医保结算信息
      if (ObjectUtil.isEmpty(settleDTO)){
        throw new AppException("未查找到医保结算信息，请先做医保结算！");
      }
      //查看移动支付表参数据
      //接口调用
      //payOnlineInfoDAO
      map.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
      map.put("insureIndividualSettleDTO",settleDTO);
      Map<String, Object> resultMap = (Map<String, Object>) insureUnifiedPayOutptService_consumer.AmpRefund(map).getData();
      return resultMap;
    }

    /**
     * 查询结算结果
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-06-16 14:48
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public Map<String, Object> querySettleResult(Map map) {
      Map<String, Object> responseMap = new HashMap<>();
      if (ObjectUtil.isEmpty(MapUtils.get(map, "mdtrt_cert_type"))||ObjectUtil.isEmpty(MapUtils.get(map, "mdtrt_cert_no"))){
        responseMap.put("ret_code",RET_CODE02);
        responseMap.put("ret_msg","必传参数未传!");
        responseMap.put("result","fail");
        return responseMap;
      }
      Map<String, Object> param = new HashMap<>();
      param.put("hospCode",MapUtils.get(map, "hospCode"));
      param.put("mdtrtCertType",MapUtils.get(map, "mdtrt_cert_type"));
      param.put("mdtrtCertNo",MapUtils.get(map, "mdtrt_cert_no"));
      //医保就医信息
      InsureIndividualVisitDTO insureIndividualVisitDTO =
          insureIndividualVisitService_consumer.getInsureIndividualVisitByMdtrtCertNo(param);
      if (ObjectUtil.isEmpty(insureIndividualVisitDTO)){
        responseMap.put("ret_code",RET_CODE02);
        responseMap.put("ret_msg","未查询到就医信息!");
        responseMap.put("result","fail");
        return responseMap;
      }
      PayOnlineInfoDO payOnlineInfoDO = new PayOnlineInfoDO();
      payOnlineInfoDO.setVisitId(insureIndividualVisitDTO.getVisitId());
      PayOnlineInfoDO resultDO = payOnlineInfoDAO.selectByVisitId(payOnlineInfoDO);
        //根据就诊ID获取最新一条结算信息
      param.put("visitId",insureIndividualVisitDTO.getVisitId());
      InsureIndividualSettleDTO insureIndividualSettleDTO =
          insureIndividualSettleService.getInsureSettleByVisitId(param);
      if (ObjectUtil.isEmpty(insureIndividualSettleDTO)){
        responseMap.put("ret_code",RET_CODE00);
        responseMap.put("ret_msg","查询成功,未查到院内结算信息!");
        responseMap.put("result","fail");
        return responseMap;
      }else{
        responseMap.put("ret_code",RET_CODE00);
        responseMap.put("ret_msg","查询成功!");
        responseMap.put("call_sn", resultDO.getCallSn());
        responseMap.put("balance", null);
        responseMap.put("common_tip_list", null);
        responseMap.put("druginfo", null);
        responseMap.put("result","succ");
        return responseMap;
      }
    }

    /**
     * @param map
     * @return java.lang.Boolean
     * @method AMP_HOS_001
     * @author wang'qiao
     * @date 2022/6/15 13:54
     * @description 医疗消息推送
     **/
    @Override
    public WrapperResponse savePayOnlineInfoDO(Map map) {
        //医院编码
        String hospCode = map.get("hospCode").toString();
        SetlRefundQueryDTO setlRefundQueryDTO = MapUtils.get(map, "setlRefundQueryDTO");
        if (ObjectUtil.isEmpty(setlRefundQueryDTO.getVisitId())) {
            throw new AppException("请传入就诊ID!");
        }
        //查询医保就诊信息
        Map<String, Object> insureVisitParam = new HashMap<>();
        insureVisitParam.put("id", setlRefundQueryDTO.getVisitId());
        insureVisitParam.put("hospCode", hospCode);
        //医保就医信息
        InsureIndividualVisitDTO insureIndividualVisitDTO =
                insureIndividualVisitService_consumer.getInsureIndividualVisitById(insureVisitParam);
        if (insureIndividualVisitDTO == null || StringUtils.isEmpty(insureIndividualVisitDTO.getId())) {
            throw new AppException("未查找到医保就诊信息，请做医保登记！");
        }
        //查询门诊费用
        WrapperResponse wrapperResponse = queryOutptCostList(map);
        JSONObject obj = (JSONObject) wrapperResponse.getData();
        //处方费用信息
        List outptCostDTOList = (List) obj.get("outptCost");
        //诊断信息
        List outptDiagnoseDTOList = (List) obj.get("outptDiagnose");

        //接口调用
        map.put("insureIndividualVisitDTO", insureIndividualVisitDTO);
        map.put("outptCostDTOList", outptCostDTOList);
        map.put("outptDiagnoseDTOList", outptDiagnoseDTOList);
        WrapperResponse response = insureUnifiedPayOutptService_consumer.AMP_HOS_001(map);
        Map<String, Object> data = (Map) response.getData();
        boolean requestSuccess = (boolean) data.get("requestSuccess");
        PayOnlineInfoDO payOnlineInfoDO = (PayOnlineInfoDO) data.get("payOnlineInfoDO");
        payOnlineInfoDO.setId(SnowflakeUtils.getId());
        // 如果消息推送调用成功则
        if(requestSuccess){
            payOnlineInfoDAO.insertPayOnlineInfo(payOnlineInfoDO);
        }
        return response;
    }

    /**
     * @param map
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map < java.lang.String, java.lang.Object>>
     * @method refundInquiry
     * @author wang'qiao
     * @date 2022/6/20 14:45
     * @description 查询退款结果（AMP_HOS_003）
     **/
    @Override
    public WrapperResponse<Map<String, Object>> refundInquiry(Map<String, Object> map) {
        //医院编码
        String hospCode = map.get("hospCode").toString();
        SetlRefundQueryDTO setlRefundQueryDTO = MapUtils.get(map, "setlRefundQueryDTO");
        if (ObjectUtil.isEmpty(setlRefundQueryDTO.getVisitId())) {
            throw new AppException("请传入就诊ID!");
        }
        if (ObjectUtil.isEmpty(setlRefundQueryDTO.getSettleId())) {
            throw new AppException("请传入结算ID!");
        }
        //查询医保就诊信息
        Map<String, Object> insureVisitParam = new HashMap<String, Object>();
        insureVisitParam.put("id", setlRefundQueryDTO.getVisitId());
        insureVisitParam.put("hospCode", hospCode);
        //医保就医信息
        InsureIndividualVisitDTO insureIndividualVisitDTO =
                insureIndividualVisitService_consumer.getInsureIndividualVisitById(insureVisitParam);
        if (insureIndividualVisitDTO == null || StringUtils.isEmpty(insureIndividualVisitDTO.getId())) {
            throw new AppException("未查找到医保就诊信息，请做医保登记！");
        }
       /* if (StringUtils.isEmpty(insureIndividualVisitDTO.getPayToken()) || StringUtils.isEmpty(insureIndividualVisitDTO.getPayOrdId())) {
            throw  new AppException("未找到支付订单号，请先上传费用！");
        }*/
        //判断是否已医保结算
        InsureIndividualSettleDTO settleDTO = new InsureIndividualSettleDTO();
        settleDTO.setVisitId(setlRefundQueryDTO.getVisitId());
        settleDTO.setHospCode(hospCode);
        settleDTO.setSettleId(setlRefundQueryDTO.getSettleId());
        settleDTO.setState("0");
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("hospCode", hospCode);
        dataMap.put("insureIndividualSettleDTO", settleDTO);
        settleDTO = insureIndividualSettleService.findByCondition(dataMap);
        //判断医保结算信息
        if (ObjectUtil.isEmpty(settleDTO)) {
            throw new AppException("未查找到医保结算信息，请先做医保结算！");
        }
        //接口调用
        map.put("insureIndividualVisitDTO", insureIndividualVisitDTO);
        map.put("insureIndividualSettleDTO", settleDTO);
        Map<String, Object> resultMap = insureUnifiedPayOutptService_consumer.refundInquiry(map).getData();
        return WrapperResponse.success(resultMap);
    }

    /**
     * @param map
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map < java.lang.String, java.lang.Object>>
     * @method reconciliationDocument
     * @author wang'qiao
     * @date 2022/6/20 19:48
     * @description 对账文件获取  下载后定点医疗机构可自行解析此对账文件并与定点机构的对账文件和医保核心的对账文件进行三方账目的对账
     **/
    @Override
    public WrapperResponse<Map<String, Object>> reconciliationDocument(Map<String, Object> map) {
        return insureUnifiedPayOutptService_consumer.reconciliationDocument(map);
    }

    /**
     * @param map
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map < java.lang.String, java.lang.Object>>
     * @method queryUnsettleList
     * @author wang'qiao
     * @date 2022/6/21 10:37
     * @description 拉取待结算费用信息 org_trace_no充当结算ID，方便寻找这一批费用信息
     **/
    @Override
    public Map<String, Object> updateUnsettleList(Map map) {
        Map<String, Object> responseMap = new HashMap<>();
        if (ObjectUtil.isEmpty(MapUtils.get(map, "mdtrt_cert_type")) || ObjectUtil.isEmpty(MapUtils.get(map, "mdtrt_cert_no")) || ObjectUtil.isEmpty(MapUtils.get(map, "org_code"))) {
            responseMap.put("ret_code", RET_CODE02);
            responseMap.put("ret_msg", "必传参数未传!");
            responseMap.put("result", "fail");
            return responseMap;
        }
        Map<String, Object> param = new HashMap<>();
        param.put("hospCode", MapUtils.get(map, "hospCode"));
        param.put("mdtrtCertType", MapUtils.get(map, "mdtrt_cert_type"));
        param.put("mdtrtCertNo", MapUtils.get(map, "mdtrt_cert_no"));
        // 收费批次号
        String chrgBchno = SnowflakeUtils.getId();
        OutptVisitDTO outptVisitDTO = null;
        //医保就医信息
        InsureIndividualVisitDTO insureIndividualVisitDTO =
                insureIndividualVisitService_consumer.getInsureIndividualVisitByMdtrtCertNo(param);
        Map params = new HashMap();
        //如果医保就诊信息不为空则直接用visit_id 查即可
        if(ObjectUtil.isNotEmpty(insureIndividualVisitDTO)){
            //门诊病人信息查询
            params.put("id", insureIndividualVisitDTO.getVisitId());
            params.put("hospCode", MapUtils.get(map, "hospCode"));
            outptVisitDTO = outptVisitService.queryByVisitID(params);
        }else {
            //如果医保就诊信息为空则 使用certNo查询门诊病人信息
            OutptVisitDTO outptVisit = new OutptVisitDTO();
            outptVisit.setCertNo(MapUtils.get(map, "mdtrt_cert_no"));
            outptVisit.setCertCode(MapUtils.get(map, "mdtrt_cert_type"));
            param.put("outptVisitDTO", outptVisit);
            outptVisitDTO = outptVisitService.selectOutptVisitByCertNo(param).getData();
        }
        if(ObjectUtil.isEmpty(outptVisitDTO)){
            responseMap.put("ret_code", RET_CODE02);
            responseMap.put("ret_msg", "未查询到就医信息!");
            responseMap.put("result", "fail");
            return responseMap;
        }
        //根据就诊ID获取最新一条结算信息
        param.put("visitId", insureIndividualVisitDTO.getVisitId());
        param.put("hospCode", MapUtils.get(map, "hospCode"));
        //查询门诊费用 todo 部分参数没有
        WrapperResponse wrapperResponse = queryOutptCostList(param);
        JSONObject obj = (JSONObject) wrapperResponse.getData();
        BigDecimal medfeeSumamt = new BigDecimal(0);
        //诊断明细
        List<JSONObject> diaglist = new ArrayList<>();
        //处方明细
        List<JSONObject> rxlist = new ArrayList<>();
        //处方费用信息
        List<OutptCostDTO> outptCostDTOList = (List) obj.get("outptCost");
        List<OutptDiagnoseDTO> outptDiagnoseDTOList = (List) obj.get("outptDiagnose");
        // 组装处方明细
        String[] opIds  = new String[50];
        int i = 0;
        for (OutptCostDTO outptCostDTO : outptCostDTOList) {
            BigDecimal finalMedfeeSumamt = outptCostDTO.getLastRealityPrice();
            if(finalMedfeeSumamt != null)
                medfeeSumamt = medfeeSumamt.add(finalMedfeeSumamt);
            InsureItemMatchDTO insureItemMatchDTO = new InsureItemMatchDTO();
            insureItemMatchDTO.setHospCode(MapUtils.get(map, "hospCode"));
            insureItemMatchDTO.setHospItemId(outptCostDTO.getItemId());
            param.put("insureItemMatchDTO", insureItemMatchDTO);
            List<InsureItemMatchDTO> insureItemMatchDTOS = itemMatchService.queryByHospItemId(param);
            opIds[i] = outptCostDTO.getId();
            i++;
            JSONObject outptJsonObject = new JSONObject() {{
                put("chrg_bchno", chrgBchno); //“收费批次号”
                put("rx_no", outptCostDTO.getOpdId()); //“处方号”
                put("rx_circ_flag", null); //“外购处方标志”
                put("feedetl_sn", outptCostDTO.getId()); //“费用明细流水号”
                put("drord_no", null); //“医嘱号”
                put("med_list_codg", outptCostDTO.getInsureItemCode()); //“医疗目录编码”
                put("medins_list_codg", outptCostDTO.getHospItemCode()); //“医药机构目录编码”
                put("pric", outptCostDTO.getPrice()); //“项目单价”
                put("cnt", outptCostDTO.getTotalNum()); //“项目数量”
                put("det_item_fee_sumamt", outptCostDTO.getRealityPrice()); //“明细项目费用总额”
                put("used_frqu_dscr", outptCostDTO.getRateId()); //“使用频次描述”
                put("sin_dos_dscr", outptCostDTO.getDosage()); //“单次剂量描述”
                put("prd_days", outptCostDTO.getUseDays()); //“周期天数”
                put("medc_way_dscr", outptCostDTO.getUsageCode()); //“用药途径描述”
                put("fee_ocur_time", outptCostDTO.getCrteTime()); //“费用发生时间”
                put("bilg_dr_name", outptCostDTO.getDoctorName()); //“开单医师姓名”
                put("bilg_dr_codg", outptCostDTO.getDoctorId()); //“开单医生编码”
                put("orders_dr_code", null); //“受单医生编码”
                put("orders_dr_name", null); //“受单医生姓名”
                put("bilg_dept_codg", outptCostDTO.getDeptId()); //“开单科室编码”
                put("bilg_dept_name", outptCostDTO.getDeptName()); //开单科室名称
                put("acord_dept_codg", null); //受单科室编码
                put("acord_dept_name", null); //受单科室名称
                put("prod_barc", null); //商品条形编码
                put("hosp_appr_flag", null); //医院审批标志
                put("tcmdrug_used_way", outptCostDTO.getUseCode()); //中药使用方式
                put("etip_flag", null); //外检标志
                put("etip_hosp_code", null); //外检医院编码
                put("dscg_tkdrug_flag",null); //出院带药标志
                put("matn_fee_flag",null); //生育费用标志
                put("item_name", outptCostDTO.getItemName()); //项目名称
                put("invo_item_no", SnowflakeUtils.getId()); //发票项目编码
                put("hi_item", insureItemMatchDTOS.get(0).getIsMatch()); //是否医保项目
                put("item_emp", insureItemMatchDTOS.get(0).getInsureItemUnitCode()); //项目单位
                put("item_spec", insureItemMatchDTOS.get(0).getInsureItemSpec()); //项目规格
                put("dos_form", insureItemMatchDTOS.get(0).getInsureItemPrepCode()); //剂型
                put("emp_medc_emp", insureItemMatchDTOS.get(0).getInsureItemSpec()); //单位用药单位
                put("drug_to_int", outptCostDTO.getTotalNum()); //取药总量
                put("drug_to_int_emp", insureItemMatchDTOS.get(0).getInsureItemUnitCode()); //取药总量单位
                put("drug_cnt_days", outptCostDTO.getUseDays()); //药量天数
                put("drug_medc_way_code", null); //给药途径编码
            }};
            rxlist.add(outptJsonObject);
        }
        // 组装诊断明细
        for (OutptDiagnoseDTO outptDiagnoseDTO : outptDiagnoseDTOList) {
            JSONObject outptJsonObject = new JSONObject(){{
               put("diag_type", outptDiagnoseDTO.getTypeCode());   //诊断类别
               put("diag_srt_no",null);  //诊断排序号
               put("diag_code", outptDiagnoseDTO.getDiseaseCode());  //诊断代码
               put("diag_name", outptDiagnoseDTO.getDiseaseName());  //诊断名称
               put("diag_dept", outptDiagnoseDTO.getDeptName());  //诊断科室
               put("diag_dept_name", outptDiagnoseDTO.getDeptName());  //诊断科室名称
               put("dise_dor_no", outptDiagnoseDTO.getDoctorId());  //诊断医生编码
               put("dise_dor_name", outptDiagnoseDTO.getDoctorName());  //诊断医生姓名
               if(outptDiagnoseDTO.getCrteTime() != null)
                    put("diag_time", DateUtils.format(outptDiagnoseDTO.getCrteTime(),DateUtils.Y_M_DH_M_S)); //诊断时间（yyyy-MM-dd HH:mm:ss）
               put("vali_flag", outptDiagnoseDTO.getIsValid());  //有效标志
               put("maindiag_flag", outptDiagnoseDTO.getIsMain());  //主诊断标志
            }};
            diaglist.add(outptJsonObject);
        }
        //待结算列表
        List<JSONObject> unsettlelist = new ArrayList<>();
        BigDecimal finalMedfeeSumamt = medfeeSumamt;
        OutptVisitDTO finalOutptVisitDTO = outptVisitDTO;
        String orgTraceNo = SnowflakeUtils.getId();
        String docTraceNo = SnowflakeUtils.getId();
        JSONObject unsettle = new JSONObject(){{
            put("org_trace_no", orgTraceNo);  //机构跟踪号
            put("doc_trace_no", docTraceNo);  //单据流水号
            put("ipt_otp_no",null);  //院内门诊号/住院号
            put("ipt_no", null);  //住院号
            // 如果医保就诊信息为空则 患者是自费病人
            if (finalOutptVisitDTO.getPatientCode() != null && finalOutptVisitDTO.getPatientCode().equals(ZIFEI_PATAIENT)) {
                responseMap.put("patient_type", "00"); //“患者费别” 自费 00  医保 01
            } else {
                responseMap.put("patient_type", "01"); //“患者费别” 自费 00  医保 01
            }
            put("mdtrt_cert_type", MapUtils.get(map, "mdtrt_cert_type"));  //就诊凭证类型
            put("mdtrt_cert_no", MapUtils.get(map, "mdtrt_cert_no"));  //就诊凭证编号
            put("cert_no", finalOutptVisitDTO.getCertNo());  //证件号
            put("psn_name", finalOutptVisitDTO.getName());  //姓名
            put("gend", finalOutptVisitDTO.getGenderCode());  //性别
            put("age", finalOutptVisitDTO.getAge());  //年龄
            put("mdtrt_id", insureIndividualVisitDTO.getVisitId());  //门诊/住院流水号(医保门诊挂号/入院办理返回的就诊id)
            put("dise_codg", null);  //特殊病种的编码
            put("dise_name", null);  //特殊病种名称
            put("dept_name", finalOutptVisitDTO.getDeptName());  //科室名称
            put("dept_code", finalOutptVisitDTO.getDeptId());  //科室编码
            put("caty", finalOutptVisitDTO.getDeptName());  //科别
            put("atddr_no", finalOutptVisitDTO.getDoctorId());  //医生编码
            put("dr_name", finalOutptVisitDTO.getDoctorName());  //医生姓名
            put("chfpdr_no", finalOutptVisitDTO.getDoctorId());  //主治医生编码
            put("chfpdr_name", finalOutptVisitDTO.getDoctorName());  //主诊医师姓名
            put("adm_diag_dscr",null);  //入院诊断描述（住院不可空）
            put("adm_dept_codg",null);  //入院科室编码（住院不可空）
            put("adm_dept_name",null);  //入院科室名称（住院不可空）
            put("adm_bed",null);  //入院床位（住院不可空）
            put("dscg_maindiag_code",null); //住院主诊断代码
            put("dscg_maindiag_name",null); //住院主诊断名称
            put("oprn_oprt_code",null); //手术操作代码
            put("oprn_oprt_name",null); //手术操作名称
            put("medfee_sumamt", finalMedfeeSumamt.toString()); //医疗费总额
            put("psn_setlway",null); //个人结算方式
            put("acct_used_flag",null); //个人账户使用标志
            put("invono",null); //发票号
            put("fulamt_ownpay_amt",null); //全自费金额
            put("overlmt_selfpay",null); //超限价金额
            put("preselfpay_amt",null); //先行自付金额
            put("inscp_scp_amt",null); //符合政策范围金额
            put("mid_setl_flag",null); //中途结算标志
            put("med_type",insureIndividualVisitDTO.getAka130()); //医疗类别
            put("fpsc_no",null); //计划生育服务证号
            put("birctrl_type",null); //计划生育手术类别
            put("matn_type",null); //生育类别
            put("fetus_cnt",null); //胎儿数
            put("birctrl_matn_date",null); //计划生育手术或生育日期
            put("latechb_flag",null); //晚育标志
            put("geso_val",null); //孕周数
            put("fetts",null); //胎次
            put("pret_flag",null); //早产标志
            put("dise_type_code",null); //病种类型
            put("begn_time",null); //就诊时间(yyyyMMddHHmmss)
            put("dscg_way",null); //离院方式
            put("cop_flag",null); //伴有并发症标志
            put("dscg_dept_codg",null); //出院科室编码
            put("dscg_dept_name",null); //出院科室名称
            put("dscg_bed",null); //出院床位
            put("die_date",null); //死亡日期
            put("main_cond_dscr",null); //主要病情描述
            put("insu_code",null); //参保人所在统筹区编码
            put("chrg_bchno", chrgBchno); //“收费批次号”
            put("psn_no",null); //人员编码
            put("insu_type",null); //险种类型
            put("medrcdno",null); //病历号

            put("diaglist", diaglist); //诊断明细
            put("rxlist", rxlist); //处方明细

        }};
        unsettlelist.add(unsettle);
        responseMap.put("ret_code", RET_CODE00);
        responseMap.put("ret_msg", "查询成功");
        responseMap.put("unsettlelist", unsettlelist);

        // 写表操作 todo 写表oupt_cost org_trace_no 就是 settleId;
        if(ObjectUtil.isNotEmpty(outptCostDTOList)){
            outptCostDAO.updateCostSettleIdByids(orgTraceNo,outptCostDTOList);
        }
        // todo  pay_online_info org_trace_no
        PayOnlineInfoDO payOnlineInfoDO = new PayOnlineInfoDO();
        payOnlineInfoDO.setVisitId(outptVisitDTO.getVisitId());
        payOnlineInfoDO.setOrgTraceNo(orgTraceNo);
        payOnlineInfoDO.setDocTraceNo(docTraceNo);
        payOnlineInfoDAO.updateByVisitId(payOnlineInfoDO);
        return responseMap;
    }

    /**
     * @param param
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map < java.lang.String, java.lang.Object>>
     * @method queryAccount
     * @author wang'qiao
     * @date 2022/6/22 15:10
     * @description 查询用户在院内的账户信息，如果用户是住院患者需要返回住院所需要的住院病人信息字段
     **/
    @Override
    public Map<String, Object> queryAccount(Map param) {
        String mdtrtCertType = MapUtils.get(param, "mdtrt_cert_type");
        String mdtrtCertNo = MapUtils.get(param, "mdtrt_cert_no");
        String orgCode = MapUtils.get(param, "org_code");
        String certNo = MapUtils.get(param, "cert_no");
        String accountType = MapUtils.get(param, "account_type");
        Map<String, Object> responseMap = new HashMap<>();
        //  必传参数检测
        if (ObjectUtil.isEmpty(mdtrtCertType) || ObjectUtil.isEmpty(mdtrtCertNo)
                || ObjectUtil.isEmpty(orgCode) || ObjectUtil.isEmpty(accountType)
                || ObjectUtil.isEmpty(certNo)) {
            responseMap.put("ret_code", RET_CODE02);
            responseMap.put("ret_msg", "必传参数未传!");
            responseMap.put("result", "fail");
            return responseMap;
        }

        OutptVisitDTO outptVisitDTO = null;
        Map params = new HashMap();

        param.put("hospCode", MapUtils.get(param, "hospCode"));
        param.put("mdtrtCertType", mdtrtCertType );
        param.put("mdtrtCertNo", mdtrtCertNo );
        //医保就医信息
        InsureIndividualVisitDTO insureIndividualVisitDTO =
                insureIndividualVisitService_consumer.getInsureIndividualVisitByMdtrtCertNo(param);

        InsureIndividualBasicDTO insureIndividualBasicDTO = new InsureIndividualBasicDTO();
        insureIndividualBasicDTO.setAka130(mdtrtCertType);
        insureIndividualBasicDTO.setInsureRegCode(insureIndividualVisitDTO.getInsureRegCode());
        insureIndividualBasicDTO.setInsuplc_admdvs(insureIndividualVisitDTO.getInsuplcAdmdvs());
        insureIndividualBasicDTO.setBka895(insureIndividualVisitDTO.getMdtrtCertType());
        insureIndividualBasicDTO.setAac002(insureIndividualVisitDTO.getMdtrtCertNo());
        params.put("insureIndividualBasicDTO", insureIndividualBasicDTO);
        //如果医保就诊信息不为空则直接用visit_id 查即可
        if (ObjectUtil.isNotEmpty(insureIndividualVisitDTO)) {
            //门诊病人信息查询
            params.put("id", insureIndividualVisitDTO.getVisitId());
            params.put("hospCode", MapUtils.get(param, "hospCode"));
            outptVisitDTO = outptVisitService.queryByVisitID(params);
        } else {
            //如果医保就诊信息为空则 使用certNo查询门诊病人信息
            OutptVisitDTO outptVisit = new OutptVisitDTO();
            outptVisit.setCertNo(MapUtils.get(param, "mdtrt_cert_no"));
            outptVisit.setCertCode(MapUtils.get(param, "mdtrt_cert_type"));
            params.put("hospCode", MapUtils.get(param, "hospCode"));
            params.put("outptVisitDTO", outptVisit);
            outptVisitDTO = (OutptVisitDTO) outptVisitService.selectOutptVisitByCertNo(params).getData();
        }

        //人员信息获取：1101接口
        params.put("mdtrt_cert_type", mdtrtCertType);
        params.put("mdtrt_cert_no", mdtrtCertNo);
        params.put("regCode", insureIndividualVisitDTO.getInsureRegCode());
//        params.put("orgCode", insureIndividualVisitDTO.getInsureOrgCode());
        Map<String, Object> resultMap = (Map<String, Object>) outptService_consumer.getOutptVisitInfo(params).getData();
        List<Map<String, Object>> personInfo = (List) resultMap.get("personinfo");
        Map<String, Object> tempMap = (Map<String, Object>) resultMap.get("tempMap");
        List ybxzList = (List) resultMap.get("ybxzList");
        Map<String, Object> baseinfo = (Map<String, Object>) tempMap.get("baseinfo");
//        Map<String, Object> insuinfo = (Map<String, Object>) tempMap.get("insuinfo");
        //响应参数整理：
        responseMap.put("ret_code", RET_CODE00);
        responseMap.put("ret_msg", "查询成功");
        responseMap.put("psn_no", MapUtils.getVS(personInfo.get(0), "aac001")); //人员编号
        responseMap.put("mdtrt_cert_type", mdtrtCertType); //就诊凭证类型
        responseMap.put("mdtrt_cert_no", mdtrtCertNo); //就诊凭证编号
        // 如果这个病人的BRLX是 0 也就是自费类型则：
        responseMap.put("patient_type", "01"); //“患者费别” 自费 00  医保 01

        responseMap.put("his_cust_id", insureIndividualVisitDTO.getVisitId() ); //持卡人院内默认ID
        responseMap.put("balance",MapUtils.get(personInfo.get(0), "balc").toString()); //余额(元)
        responseMap.put("psn_cert_type", MapUtils.get(personInfo.get(0), "psn_cert_type").toString()); //证件类型
        responseMap.put("cert_no", MapUtils.getVS(personInfo.get(0), "aac002")); //证件号
        responseMap.put("psn_name", MapUtils.getVS(personInfo.get(0), "aac003")); //人员姓名
        responseMap.put("gend", MapUtils.getVS(personInfo.get(0), "aac004")); //性别
        responseMap.put("account_type",null); //账户类型
        responseMap.put("phone", null); //用户手机号
        responseMap.put("disease_area", MapUtils.getVS(personInfo.get(0), "bka008")); //住院所在病区
        responseMap.put("adm_bed", null); //住院床位号
        responseMap.put("inpatient_total_fee", null); //住院总费用
        responseMap.put("inpatient_start_date",null); //住院计费开始日期
        responseMap.put("inpatient_end_date", null); //住院计费结束日期
        responseMap.put("brdy", MapUtils.getVS(personInfo.get(0), "aac006")); //出生日期（yyyy-MM-dd）
        responseMap.put("naty", MapUtils.getVS(baseinfo,"naty")); //民族
        responseMap.put("age", MapUtils.get(baseinfo, "age").toString()); //年龄
        responseMap.put("ipt_no", null); //住院号.
        responseMap.put("insu_type", MapUtils.getVS(personInfo.get(0), "aae140")); //险种类型
        responseMap.put("his_cust_list", null); //院内卡列表
        return responseMap;
    }

    /**
     * @param param
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map < java.lang.String, java.lang.Object>>
     * @method rechargeSettle
     * @author wang'qiao
     * @date 2022/6/23 15:27
     * @description 用户在平台的收银台上完成结算后，平台会将结算的“结果明细”回写给机构，机构进行内部的充值结算流程
     **/
    @Override
    public Map<String, Object> updateRechargeSettle(Map param) {
        // 返回结果集
        Map<String, Object> responseMap = new HashMap<>();
        if(mustPassCheck(param)){
            responseMap.put("ret_code", RET_CODE02);
            responseMap.put("ret_msg", "必传参数为空");
            responseMap.put("result", "fail");
            return responseMap;
        }
        String hospCode = MapUtils.get(param, "hospCode");
        String callSn = SnowflakeUtils.getId();
        //根据org_trace_no（充当结算ID）获取费用列表信息
        Map costMap = new HashMap();
        //hospCode（医院编码）、statusCode（状态标志）、settleCode（结算状态）、settleId（结算id）
        costMap.put("hospCode", hospCode);
        costMap.put("statusCode", Constants.ZTBZ.ZC);//statusCode（状态标志 = 正常）
        costMap.put("settleCode", Constants.JSZT.WJS);//settleCode（结算状态 = 未结算）
        costMap.put("settleId", MapUtils.get(param, "org_trace_no"));//settleId（结算id）
        List<OutptCostDTO> outptCostDTOList = outptCostDAO.queryBySettleId(costMap); //可以获取本次就诊ID visitId
        if(ObjectUtil.isEmpty(outptCostDTOList)){
            responseMap.put("ret_code", RET_CODE02);
            responseMap.put("ret_msg", "没有该病人");
            responseMap.put("result", "fail");
            return responseMap;
        }
        //获取本次就诊ID
        String visitId = outptCostDTOList.get(0).getVisitId();
        param.put("visitId", visitId );

        //获取医保就诊信息
        InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();

        //获取医保个人信息 insure_individual_basic
        InsureIndividualBasicDTO insureIndividualBasicDTO = new InsureIndividualBasicDTO();
        //如果该病人信息是医保病人才需要查询医保相关信息
        if (MapUtils.get(param, "used_patient_type").equals(YIBAO)) {
            insureIndividualVisitDTO.setVisitId(visitId);
            insureIndividualVisitDTO.setHospCode(hospCode);
            Map<String, Object> visitMap = new HashMap<>();
            visitMap.put("hospCode", hospCode);
            visitMap.put("insureIndividualVisitDTO", insureIndividualVisitDTO);
            insureIndividualVisitDTO = insureIndividualVisitService_consumer.selectInsureInfo(visitMap).getData();


            insureIndividualBasicDTO.setHospCode(hospCode);//医院编码
            insureIndividualBasicDTO.setId(insureIndividualVisitDTO.getMibId());//id
            Map<String, Object> insureBasicParam = new HashMap<String, Object>();
            insureBasicParam.put("hospCode", hospCode);//医院编码
            insureBasicParam.put("basicDTO", insureIndividualBasicDTO);
            insureIndividualBasicDTO = insureIndividualBasicService.getById(insureBasicParam).getData();
            if (insureIndividualBasicDTO == null) {
                throw new AppException("未获取到医保个人信息。");
            }
        }
        BigDecimal bacu18 = new BigDecimal(0);//账户余额
        if(insureIndividualVisitDTO != null){
            bacu18 = insureIndividualBasicDTO.getAkc252();//账户余额
        }


        //获取该门诊病人信息
        Map<String,String> params = new HashMap<>();
        params.put("id", visitId);
        params.put("hospCode", hospCode);
        OutptVisitDTO outptVisitDTO = outptVisitService.queryByVisitID(params);
        if(ObjectUtil.isEmpty(outptVisitDTO)){
            responseMap.put("ret_code", RET_CODE02);
            responseMap.put("ret_msg", "未在系统中查找到该病人信息");
            responseMap.put("result", "fail");
            return responseMap;
        }

        // 生成领药申请单，校验库存、领药申请单明细
        Map<String, Object> queryParam = new HashMap<String, Object>();
        queryParam.put("hospCode", hospCode);//医院编码
        queryParam.put("pfTypeCode", outptVisitDTO.getPreferentialTypeId());//优惠类型
        queryParam.put("items", outptCostDTOList);//当前用户的费用信息
        List<OutptCostDTO> outptCostDTOS = outptCostDAO.queryDrugMaterialListByIds(queryParam);

        Map<String, Object> tempMap = this.checkStockAndCreatePharOutReceiveDetail(outptCostDTOList, outptCostDTOS, outptVisitDTO.getHospCode());

        //1.根据当前结算id，查询费用表,更新医技申请单状态
        outptCostDAO.updateMedicApply(visitId, hospCode, "02", outptCostDTOList);

        //2.(医保病人专属)保存支付方式（结算）插入医保结算表， 插入门诊医保明细（也许没有） //6.如果是医保病人，修改医保结算表， 插入门诊医保明细 updateInsureSettle
        InsureSettleInfoDTO insureSettleInfoDTO = null;
        if(ObjectUtil.isNotEmpty(insureIndividualVisitDTO)){
            insureSettleInfoDTO = insertIntoInsureSettle(param, outptVisitDTO,insureIndividualVisitDTO);
        }

        //3.根据费用信息修改本次结算的费用状态
        List<String> ids = (List<String>) tempMap.get("ids");
        Map<String, Object> costParam = new HashMap<String, Object>();
        costParam.put("settleCode", Constants.JSZT.YIJS);//费用状态 = 已结算
        costParam.put("ids", ids);//费用id
        outptCostDAO.editCostSettleCodeByIDS(costParam);

        //4.插入门诊结算表
        OutptSettleDTO outptSettleDTO = insertIntoOuptVisitSettle(param);

        //5.修改处方表结算信息
        List<String> outptPrescribeIds = (List<String>) tempMap.get("outptPrescribeIds");
        if (!outptPrescribeIds.isEmpty()) {
            Map<String, Object> outptPrescribeParam = new HashMap<String, Object>();
            outptPrescribeParam.put("hospCode", outptVisitDTO.getHospCode());//医院编码
            outptPrescribeParam.put("ids", outptPrescribeIds);//处方ids
            outptPrescribeParam.put("settleId", MapUtils.get(param, "org_trace_no"));//结算id就是机构系统跟踪号 （拉取待结算时候返回的org_trace_no对应）
            outptPrescribeParam.put("isSettle", Constants.SF.S);//是否结算 = 是
            outptCostDAO.updateOutptPrescribeByIds(outptPrescribeParam);
        }

        /*//获取领药申请单明细信息
        MapUtils.get(tempMap,"pharOutReceiveDetailDOList");
        //领药申请数据
        MapUtils.get(tempMap,"pharOutReceiveMap");*/
        // 保存callSn进 payOnlineInfo表
        PayOnlineInfoDO payOnlineInfoDO = new PayOnlineInfoDO();
        payOnlineInfoDO.setVisitId(visitId);
        payOnlineInfoDO.setCallSn(callSn);
        payOnlineInfoDAO.updateByVisitId(payOnlineInfoDO);

        responseMap.put("ret_code", RET_CODE00);
        responseMap.put("ret_msg", "已确认结算");
        responseMap.put("result", "succ");
        responseMap.put("call_sn", callSn);
        responseMap.put("balance", bacu18); //余额（元）
        responseMap.put("druginfo", null); //取药提示
        responseMap.put("common_tip_list", null); //通用提醒

        return responseMap;
    }

    /**
      * @method insertIntoInsureSettle
      * @author wang'qiao
      * @date 2022/6/24 10:54
      *	@description 	插入医保结算表
      * @param  param
      * @return java.util.Map<java.lang.String,java.lang.Object>
      *
     **/
    public InsureSettleInfoDTO insertIntoInsureSettle(Map param, OutptVisitDTO outptVisitDTO,InsureIndividualVisitDTO insureIndividualVisitDTO) {
        String hospCode = MapUtils.get(param, "hospCode"); // 医院编码
        String visitId = MapUtils.get(param, "visitId");  // 就诊id
        String settleId = MapUtils.get(param, "org_trace_no"); // 结算id
        InsureSettleInfoDTO insureSettleInfoDTO = new InsureSettleInfoDTO();
        //查询医保机构信息
        Map<String, Object> configurationParam = new HashMap<String, Object>();
        configurationParam.put("hospCode", hospCode);//医院编码
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();

        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setOrgCode(MapUtils.get(param, "org_code")); //医疗机构编码
        configurationParam.put("insureConfigurationDTO", insureConfigurationDTO);
        List<InsureConfigurationDTO> insureConfigurationDTOS = insureConfigurationService.queryAll(configurationParam).getData();

        if (insureConfigurationDTOS == null || insureConfigurationDTOS.isEmpty()) {
            throw new AppException("未找到医保配置信息。");
        }
        insureConfigurationDTO = insureConfigurationDTOS.get(0);
        //门诊医保明细 outpt_insure_pay
        OutptInsurePayDO outptInsurePayDO = new OutptInsurePayDO();
        outptInsurePayDO.setId(SnowflakeUtils.getId());//id
        outptInsurePayDO.setHospCode(hospCode);//医院编码
        outptInsurePayDO.setSettleId(settleId);//结算id
        outptInsurePayDO.setVisitId(visitId);//就诊id
        outptInsurePayDO.setTypeCode(null);//合同单位明细代码
        outptInsurePayDO.setOrgNo(insureConfigurationDTO.getOrgCode());//医保机构编码
        outptInsurePayDO.setOrgName(insureConfigurationDTO.getName());//医保机构名称
        outptInsurePayDO.setTotalPrice(stringToBigDecimal(MapUtils.getVS(param, "fund_pay_sumamt","0")));//医保报销总金额
        outptInsurePayDAO.insertSelective(outptInsurePayDO);

        // 更新门诊结算表的个人账户支付
        OutptSettleDO outptSettleDO = new OutptSettleDO();
        outptSettleDO.setHospCode(hospCode);
        outptSettleDO.setId(settleId);
        outptSettleDO.setAcctPay(stringToBigDecimal((MapUtils.getVS(param, "psn_part_amt","0")))); //个人负担总金额
        outptSettleDAO.updateByPrimaryKeySelective(outptSettleDO);

        //插入医保结算表 insure_individual_settle
        InsureIndividualSettleDO insureIndividualSettleDO = new InsureIndividualSettleDO();
        insureIndividualSettleDO.setId(SnowflakeUtils.getId());//主键
        insureIndividualSettleDO.setHospCode(hospCode);//医院编码
        insureIndividualSettleDO.setVisitId(visitId);//就诊id
        insureIndividualSettleDO.setSettleId(settleId);//结算id
        insureIndividualSettleDO.setIsHospital(Constants.SF.F);//是否住院（SF）
        insureIndividualSettleDO.setVisitNo(outptVisitDTO.getVisitNo());//就诊登记号
        insureIndividualSettleDO.setDischargeDnCode(null);//出院疾病诊断编码
        insureIndividualSettleDO.setInsureOrgCode(insureConfigurationDTO.getCode());//医保机构编码
        insureIndividualSettleDO.setInsureRegCode(insureConfigurationDTO.getRegCode());//医保注册编码
        insureIndividualSettleDO.setMedicineOrgCode(insureConfigurationDTO.getOrgCode());//医疗机构编码
        insureIndividualSettleDO.setSettleway(Constants.JSFS.PTJS);//结算方式,01 普通结算,02 包干结算
        insureIndividualSettleDO.setBeforeSettle(null);//结算前账户余额 todo
        insureIndividualSettleDO.setLastSettle(null);//结算后账户余额
        insureIndividualSettleDO.setState(Constants.ZTBZ.ZC);//状态标志,0正常，2冲红，1，被冲红
        insureIndividualSettleDO.setSettleState(Constants.YBJSZT.JS);//医保结算状态;0试算，1结算
        insureIndividualSettleDO.setAka130(insureIndividualVisitDTO.getAka130());//业务类型
        insureIndividualSettleDO.setBka006(insureIndividualVisitDTO.getBka006());//待遇类型
        insureIndividualSettleDO.setIsAccount(BigDecimalUtils.isZero(stringToBigDecimal(MapUtils.getVS(param, "psn_cash_pay", "0"))) ? Constants.SF.F :
                Constants.SF.S);//当前结算是否使用个人账户;0是，1否
        //  MapUtils.get(param, "operator_name")
        insureIndividualSettleDO.setCrteId(MapUtils.get(param, "operator_id"));//创建人ID
        insureIndividualSettleDO.setCrteName(MapUtils.get(param, "operator_name"));//创建人姓名
        insureIndividualSettleDO.setCrteTime(new Date());//创建时间
        // 处理金额
        insureIndividualSettleDO.setInsureSettleId(MapUtils.get(param, "setl_id")); //医保结算返回的结算id
        insureIndividualSettleDO.setMedicalRegNo(MapUtils.get(param, "mdtrt_id"));  //入院登记唯一返回的就诊登记号
        insureIndividualSettleDO.setInsurePrice(stringToBigDecimal(MapUtils.getVS(param, "fund_pay_sumamt", "0")));//医保支付(基金支付总额
        insureIndividualSettleDO.setPersonalPrice(stringToBigDecimal(MapUtils.getVS(param, "psn_cash_pay", "0")));//个人账户支付
        insureIndividualSettleDO.setPersonPrice(stringToBigDecimal(MapUtils.getVS(param, "psn_cash_pay", "0")));  //个人现金支出
        insureIndividualSettleDO.setHospPrice(stringToBigDecimal(MapUtils.getVS(param, "hosp_part_amt", "0")));  //医院负担金额
        insureIndividualSettleDO.setPsnPartAmt(stringToBigDecimal(MapUtils.getVS(param, "psn_part_amt", "0")));  //个人负担总金额
        insureIndividualSettleDO.setCivilPrice(stringToBigDecimal(MapUtils.getVS(param, "cvlserv_pay", "0")));  //公务员补助支付
        insureIndividualSettleDO.setRetAcctInjPay(stringToBigDecimal(MapUtils.getVS(param, "hifmi_pay", "0")));  //居民大病保险资金支出
        insureIndividualSettleDO.setAcctInjPay(stringToBigDecimal(MapUtils.getVS(param, "hifob_pay", "0")));  //职工大额医疗费用补助基金支出(职工意外伤害基金)
        insureIndividualSettleDO.setTotalPrice(stringToBigDecimal(MapUtils.getVS(param, "medfee_sumamt", "0")));// 本次医疗总费用
        insureIndividualSettleDO.setAllPortionPrice(stringToBigDecimal(MapUtils.getVS(param, "fulamt_ownpay_amt", "0")));// 全自费金额
        insureIndividualSettleDO.setOverSelfPrice(stringToBigDecimal(MapUtils.getVS(param, "overlmt_selfpay", "0")));// 超限价自费费用
        insureIndividualSettleDO.setPreselfpayAmt(stringToBigDecimal(MapUtils.getVS(param, "preselfpay_amt", "0")));// 先行自付金额
        insureIndividualSettleDO.setInscpScpAmt(stringToBigDecimal(MapUtils.getVS(param, "inscp_scp_amt", "0")));// 符合政策范围金额
        insureIndividualSettleDO.setStartingPrice(stringToBigDecimal(MapUtils.getVS(param, "act_pay_dedc", "0")));// 实际支付起付线
        String setl_time = MapUtils.get(param, "setl_time");
        if(ObjectUtil.isNotEmpty(setl_time)){
            Long dates = Long.parseLong(setl_time);
            Date date = new Date(dates);
            insureIndividualSettleDO.setCrteTime(date);//创建时间
        }
        insureIndividualSettleDO.setAcctMulaidPay(stringToBigDecimal(MapUtils.getVS(param, "acct_mulaid_pay", "0")));// 个人账户共济支付金额
        insureIndividualSettleDO.setPlanPrice(stringToBigDecimal(MapUtils.getVS(param, "hifp_pay", "0")));// 基本医疗保险统筹基金支出
        insureIndividualSettleDO.setPoolPropSelfpay(stringToBigDecimal(MapUtils.getVS(param, "pool_prop_selfpay", "0")));// 基本医疗保险统筹基金支付比例
        insureIndividualSettleDO.setMafPay(stringToBigDecimal(MapUtils.getVS(param, "maf_pay", "0")));// 医疗救助基金支出
        insureIndividualSettleDO.setMafPay(stringToBigDecimal(MapUtils.getVS(param, "oth_pay", "0")));// 其他支出
        insureIndividualSettleDO.setComPay(stringToBigDecimal(MapUtils.getVS(param, "oth_pay", "0")));// 企业补充
        insureIndividualSettleDO.setClrOptins(MapUtils.get(param, "clr_optins"));// 清算经办机构
        insureIndividualSettleDO.setClrWay(MapUtils.get(param, "clr_way"));// 清算方式
        insureIndividualSettleDO.setClrType(MapUtils.get(param, "clr_type"));// 清算类别


        Map<String, Object> insureSettleParam = new HashMap<String, Object>();
        insureSettleParam.put("hospCode", hospCode);//医院编码
        insureSettleParam.put("insureIndividualSettleDO", insureIndividualSettleDO);
        insureIndividualSettleService.insertSelective(insureSettleParam);
        return insureSettleInfoDTO;
    }

    public BigDecimal stringToBigDecimal(String str){

        return BigDecimal.valueOf(Double.parseDouble(str));
    }

    /**
      * @method mustPassCheck
      * @author wang'qiao
      * @date 2022/6/29 11:32
      *	@description 	ORG_003 必传参数检查
      * @param  map
      * @return boolean
      *
     **/
    public boolean mustPassCheck(Map map){
        return ObjectUtil.isEmpty(MapUtils.get(map, "mdtrt_cert_no")) || ObjectUtil.isEmpty(MapUtils.get(map, "org_trace_no")) ||
                ObjectUtil.isEmpty(MapUtils.get(map, "amp_trace_id")) || ObjectUtil.isEmpty(MapUtils.get(map, "doc_trace_no")) ||
                ObjectUtil.isEmpty(MapUtils.get(map, "feesum_amt")) || ObjectUtil.isEmpty(MapUtils.get(map, "org_code")) ||
                ObjectUtil.isEmpty(MapUtils.get(map, "mdtrt_cert_type"));
    }
    /**
      * @method insertIntoOuptVisitSettle
      * @author wang'qiao
      * @date 2022/6/24 10:54
      *	@description   插入门诊结算表
      * @param  param
      * @return java.util.Map<java.lang.String,java.lang.Object>
      *
     **/
    public OutptSettleDTO insertIntoOuptVisitSettle(Map param) {
        OutptSettleDTO outptSettleDTO = new OutptSettleDTO();
        String hospCode = MapUtils.get(param, "hospCode");
        // 生成结算数据，保存门诊结算表
        OutptSettleDO outptSettleDO = new OutptSettleDO();
        outptSettleDO.setId(MapUtils.get(param, "org_trace_no"));//id
        outptSettleDO.setHospCode(hospCode);//医院编码
        outptSettleDO.setVisitId(MapUtils.get(param, "visitId"));//就诊id
        outptSettleDO.setSettleNo(getOrderNo(hospCode, Constants.ORDERRULE.JZ));//结算单号
        outptSettleDO.setPatientCode(MapUtils.get(param, "used_patient_type"));//病人类型
        String setl_time = MapUtils.get(param, "setl_time");
        if (ObjectUtil.isNotEmpty(setl_time)) {
            Long dates = Long.parseLong(setl_time);
            Date date = new Date(dates);
            outptSettleDO.setCrteTime(date);//创建时间
        }
        outptSettleDO.setTotalPrice(stringToBigDecimal(MapUtils.getVS(param, "feesum_amt","0")));//总金额
        outptSettleDO.setRealityPrice(stringToBigDecimal(MapUtils.getVS(param, "feesum_amt", "0")));//优惠后总金额
        outptSettleDO.setTruncPrice(null);//舍入金额（存在正负金额）
        outptSettleDO.setActualPrice(stringToBigDecimal(MapUtils.getVS(param, "deposit_amount", "0")));//实收金额
        outptSettleDO.setSelfPrice(new BigDecimal(0));// 个人自付金额减去舍人金额
        outptSettleDO.setMiPrice(stringToBigDecimal(MapUtils.getVS(param, "hosp_part_amt", "0")));//统筹支付金额 医保基金支付
        outptSettleDO.setIsSettle(Constants.SF.S);//是否结算（SF）
        outptSettleDO.setDailySettleId(null);//日结缴款ID
        outptSettleDO.setStatusCode(Constants.ZTBZ.ZC);//状态标志代码（ZTBZ）;正常
        outptSettleDO.setRedId(null);//冲红ID
        outptSettleDO.setIsPrint(Constants.SF.F);//是否打印（SF）;否
        outptSettleDO.setOldSettleId(null);//原结算ID
        outptSettleDO.setIsPrintList(Constants.SF.F);//是否打印清单（SF）
        outptSettleDO.setPrintListTime(null);//清单打印时间
        outptSettleDO.setSourcePayCode("4");//支付来源代码（ZFLY，第三方对接） //4 移动支付
        outptSettleDO.setOrderNo(null);//支付订单号（第三方订单号）
        outptSettleDO.setCrteId(MapUtils.get(param, "operator_id"));//创建人id
        outptSettleDO.setCrteName(MapUtils.get(param, "operator_name"));//创建人名称
        outptSettleDO.setCrteTime(new Date());//创建时间
        outptSettleDO.setOneSettleId(null); // 记录下第一次结算id
        // 保存门诊结算（试算）费用信息
        outptSettleDAO.insertSelective(outptSettleDO);
        outptSettleDO.setId(MapUtils.get(param, "org_trace_no"));//结算id
        //SysParameterDO sysParameterDO = getSysParameter(hospCode, Constants.HOSPCODE_DISCOUNTS_KEY);

        return outptSettleDTO;
    }

    /**
     * @Description 拼装明细审核入参
     * @Author 产品三部-郭来
     * @Date 2022-05-09 15:37
     * @return cn.hsa.module.insure.module.dto.AnalysisDTO
     */
    public AnalysisDTO initAnalysisDTO(OutptVisitDTO outptVisitDTO, InsureIndividualVisitDTO insureVisitDTO,List<Map<String, Object>> insureCostList){
        if (ObjectUtil.isEmpty(outptVisitDTO)) {
            throw new AppException("入参不能为空！");
        }

        List<AnaOrderDTO> anaOrderDTOS = new ArrayList<>();
        for (Map<String, Object> map : insureCostList) {
            AnaOrderDTO anaOrderDTO = new AnaOrderDTO();
            //*处方(医嘱)标识
            anaOrderDTO.setRxId(ObjectUtil.isNotEmpty(MapUtil.getStr(map,"opId"))?MapUtil.getStr(map,"opId"):MapUtil.getStr(map,"id"));
            //*处方号
            anaOrderDTO.setRxno(ObjectUtil.isNotEmpty(MapUtil.getStr(map,"rxNo"))?MapUtil.getStr(map,"rxNo"):MapUtil.getStr(map,"id"));
            //组编号
            anaOrderDTO.setGrpno(MapUtil.getStr(map,"iatdGroupNo"));
            //*是否为长期医嘱  1:是   0：否
            anaOrderDTO.setLongDrordFlag("0".equals(MapUtil.getStr(map,"isLong"))?"1":"0");
            //*目录类别
            anaOrderDTO.setHilistType(ObjectUtil.isEmpty(MapUtil.getStr(map,"insureItemType"))?"101":MapUtil.getStr(map,"insureItemType"));
            //*收费类别
            anaOrderDTO.setChrgType(ObjectUtil.isEmpty(MapUtil.getStr(map,"chrgType"))?"1":MapUtil.getStr(map,"chrgType"));
            //*医嘱行为
            anaOrderDTO.setDrordBhvr("-");
            //*医保目录代码
            anaOrderDTO.setHilistCode(MapUtil.getStr(map,"insureItemCode"));
            //*医保目录名称
            anaOrderDTO.setHilistName(MapUtil.getStr(map,"insureItemName"));
            //医保目录(药品)剂型
            anaOrderDTO.setHilistDosform(MapUtil.getStr(map,"insureItemPrepCode"));
            //*医保目录等级
            anaOrderDTO.setHilistLv(ObjectUtil.isEmpty(MapUtil.getStr(map,"hilistLv"))?"1":MapUtil.getStr(map,"hilistLv"));
            //*医保目录价格

            anaOrderDTO.setHilistPric(ObjectUtil.isEmpty(MapUtil.getStr(map,"insureItemPrice"))?BigDecimal.ZERO:new BigDecimal(MapUtil.getStr(map,"insureItemPrice")).setScale(2, BigDecimal.ROUND_HALF_UP));
            //医保目录备注
            anaOrderDTO.setHilistMemo("");
            //*医院目录代码
            anaOrderDTO.setHosplistCode(MapUtil.getStr(map,"hospItemCode"));
            //*医院目录名称
            anaOrderDTO.setHosplistName(MapUtil.getStr(map,"hospItemName"));
            //医院目录(药品)剂型
            anaOrderDTO.setHosplistDosform(MapUtil.getStr(map,"hospItemPrepCode"));
            //*数量
            anaOrderDTO.setCnt(BigDecimalUtils.convert(MapUtil.getStr(map,"num")).setScale(2, BigDecimal.ROUND_HALF_UP));
            //*单价
            anaOrderDTO.setPric(BigDecimalUtils.convert(MapUtil.getStr(map,"price")).setScale(2, BigDecimal.ROUND_HALF_UP));
            //*总费用
            anaOrderDTO.setSumamt(BigDecimalUtils.convert(MapUtil.getStr(map,"totalPrice")).setScale(2, BigDecimal.ROUND_HALF_UP));
            //*自费金额
            anaOrderDTO.setOwnpayAmt(BigDecimalUtils.convert(MapUtil.getStr(map,"fulamtOwnpayAmt")).setScale(2, BigDecimal.ROUND_HALF_UP));
            //*自付金额
            anaOrderDTO.setSelfpayAmt(BigDecimalUtils.convert(MapUtil.getStr(map,"preselfpayAmt")).setScale(2, BigDecimal.ROUND_HALF_UP));
            //*规格
            anaOrderDTO.setSpec(ObjectUtil.isEmpty(MapUtil.getStr(map,"spec"))?"-":MapUtil.getStr(map,"spec"));
            //*数量单位
            anaOrderDTO.setSpecUnt(ObjectUtil.isEmpty(MapUtil.getStr(map,"numUnitCode"))?"-":MapUtil.getStr(map,"numUnitCode"));
            //*医嘱开始日期
            anaOrderDTO.setDrordBegnDate(MapUtil.getDate(map,"crteTime"));
            //*下达医嘱的科室标识
            anaOrderDTO.setDrordDeptCodg(ObjectUtil.isEmpty(MapUtil.getStr(map,"execDeptId"))?"-":MapUtil.getStr(map,"execDeptId"));
            //*下达医嘱科室名称
            anaOrderDTO.setDrordDeptName(ObjectUtil.isEmpty(MapUtil.getStr(map,"execDeptName"))?"-":MapUtil.getStr(map,"execDeptName"));
            //*开处方(医嘱)医生标识
            anaOrderDTO.setDrordDrCodg(ObjectUtil.isEmpty(MapUtil.getStr(map,"crteId"))?"-":MapUtil.getStr(map,"crteId"));
            //*开处方(医嘱)医生姓名
            anaOrderDTO.setDrordDrName(ObjectUtil.isEmpty(MapUtil.getStr(map,"crteName"))?"-":MapUtil.getStr(map,"crteName"));
            //*开处方(医嘱)医职称
            anaOrderDTO.setDrordDrProfttl("-");
            //*是否当前处方(医嘱)  1=是,0=否
            anaOrderDTO.setCurrDrordFlag("1");
            anaOrderDTOS.add(anaOrderDTO);
        }
        //诊断信息DTO
        List<AnaDiagnoseDTO> anaDiagnoseDTOS = new ArrayList<>();
        OutptPrescribeDTO inPut = new OutptPrescribeDTO();
        inPut.setVisitId(outptVisitDTO.getId());
        inPut.setHospCode(outptVisitDTO.getHospCode());
        List<OutptDiagnoseDTO> outptDiagnose = outptDoctorPrescribeDAO.getOutptDiagnose(inPut);
        if (ObjectUtil.isEmpty(outptDiagnose)) {
            throw new AppException("未查询到诊断信息！");
        }
        for (int i = 0; i < outptDiagnose.size(); i++) {
            AnaDiagnoseDTO diagnoseDTO = new AnaDiagnoseDTO();
            //*诊断标识
            diagnoseDTO.setDiseId(outptDiagnose.get(i).getId());
            //*出入诊断类别
            diagnoseDTO.setInoutDiseType("1");
            //*主诊断标志
            diagnoseDTO.setMaindiseFlag(outptDiagnose.get(i).getIsMain());
            //*诊断排序号
            diagnoseDTO.setDiasSrtNo(BigDecimal.valueOf(i));
            //*诊断(疾病)编码
            diagnoseDTO.setDiseCodg(outptDiagnose.get(i).getDiseaseCode());
            //*诊断(疾病)名称
            diagnoseDTO.setDiseName(outptDiagnose.get(i).getDiseaseName());
            //*诊断日期
            diagnoseDTO.setDiseDate(outptDiagnose.get(i).getCrteTime());
            //*诊断类目  --海南必传
            diagnoseDTO.setDiseCgy("1");
            anaDiagnoseDTOS.add(diagnoseDTO);
        }
        //就诊信息
        HashMap<String, Object> map = new HashMap<>();
        map.put("hospCode",outptVisitDTO.getHospCode());
        map.put("visitId",outptVisitDTO.getId());
        OutptVisitDTO visitDTO = outptVisitDAO.selectOutptVisitById(map);
        if (ObjectUtil.isEmpty(visitDTO)) {
            throw new AppException("明细审核未查询到就诊信息，请检查！");
        }
        InsureIndividualBasicDTO basicInParm = new InsureIndividualBasicDTO();
        basicInParm.setVisitId(outptVisitDTO.getId());
        basicInParm.setHospCode(outptVisitDTO.getHospCode());
        basicInParm.setMedicalRegNo(insureVisitDTO.getMedicalRegNo());
        InsureIndividualBasicDTO basicDTO = outptVisitDAO.getByVisitId(basicInParm);
        if (ObjectUtil.isEmpty(basicDTO)) {
            throw new AppException("明细审核查询人员医保基本信息为空，请检查！");
        }
        //
        CenterHospitalDTO input = new CenterHospitalDTO();
        input.setCode(outptVisitDTO.getHospCode());
        HashMap<String, Object> inMap = new HashMap<>();
        inMap.put("centerHospitalDTO",input);
        WrapperResponse<CenterHospitalDTO> response = sysHospitalService_consummer.getByHospCode(inMap);
        if (WrapperResponse.SUCCESS != response.getCode()) {
            throw new AppException("查询医院信息失败！"+response.getMessage());
        }
        CenterHospitalDTO hospitalDTO = response.getData();
        AnaMdtrtDTO anaMdtrtDTO = new AnaMdtrtDTO();
        //*就诊标识
        anaMdtrtDTO.setMdtrtId(visitDTO.getId());
        //*医疗服务机构标识
        anaMdtrtDTO.setMedinsId(visitDTO.getHospCode());
        //*医疗机构名称
        anaMdtrtDTO.setMedinsName(ObjectUtil.isNotEmpty(hospitalDTO.getName())?hospitalDTO.getName():"-");
        //*医疗机构行政区划编码
        anaMdtrtDTO.setMedinsAdmdvs(insureVisitDTO.getMdtrtareaAdmvs());
        //*医疗服务机构类型
        anaMdtrtDTO.setMedinsType("1");
        //*医疗机构等级
        anaMdtrtDTO.setMedinsLv(ObjectUtil.isNotEmpty(hospitalDTO.getLevelCode())?hospitalDTO.getLevelCode():"02");
        //病区标识
        anaMdtrtDTO.setWardareaCodg("");
        //病房号
        anaMdtrtDTO.setWardno("");
        //病床号
        anaMdtrtDTO.setBedno("");
        //*入院日期
        anaMdtrtDTO.setAdmDate(visitDTO.getVisitTime());
        //*出院日期
        anaMdtrtDTO.setDscgDate(visitDTO.getVisitTime());
        //*主诊断编码
        anaMdtrtDTO.setDscgMainDiseCodg(insureVisitDTO.getVisitIcdCode());
        //*主诊断名称
        anaMdtrtDTO.setDscgMainDiseName(insureVisitDTO.getVisitIcdName());
        //*医师标识
        anaMdtrtDTO.setDrCodg(visitDTO.getDoctorId());
        //*入院科室标识
        anaMdtrtDTO.setAdmDeptCodg(visitDTO.getDeptId());
        //*入院科室名称
        anaMdtrtDTO.setAdmDeptName(visitDTO.getDeptName());
        //*出院科室标识
        anaMdtrtDTO.setDscgDeptCodg(visitDTO.getDeptId());
        //*出院科室名称
        anaMdtrtDTO.setDscgDeptName(visitDTO.getDeptName());
        //*就诊类型
        anaMdtrtDTO.setMedMdtrtType(insureVisitDTO.getAka130()+"0");
        //*医疗类别
        anaMdtrtDTO.setMedType(insureVisitDTO.getAka130());
        //*生育状态
        anaMdtrtDTO.setMatnStas("0");
        //*总费用
        anaMdtrtDTO.setMedfeeSumamt(new BigDecimal("0"));
        //*自费金额
        anaMdtrtDTO.setOwnpayAmt(new BigDecimal("0"));
        //*自付金额
        anaMdtrtDTO.setSelfpayAmt(new BigDecimal("0"));
        //个人账户支付金额
        anaMdtrtDTO.setMaAmt(new BigDecimal("0"));
        //救助金支付金额
        anaMdtrtDTO.setAcctPayamt(new BigDecimal("0"));
        //统筹金支付金额
        anaMdtrtDTO.setHifpPayamt(new BigDecimal("0"));
        //*结算总次数
        anaMdtrtDTO.setSetlTotlnum(new BigDecimal("1"));
        //*险种
        anaMdtrtDTO.setInsutype(basicDTO.getAae140());
        //*报销标志
        anaMdtrtDTO.setReimFlag("0");
        //*异地结算标志
        anaMdtrtDTO.setOutSetlFlag("0");
        //处方(医嘱)信息
        anaMdtrtDTO.setFsiOrderDtos(anaOrderDTOS);
        //诊断信息DTO
        anaMdtrtDTO.setFsiDiagnoseDtos(anaDiagnoseDTOS);
        //公务员标志  --海南必传
        anaMdtrtDTO.setCvlservFlag(ObjectUtil.isNotEmpty(basicDTO.getBac001())?basicDTO.getBac001():"0");
        anaMdtrtDTO.setOrderDtos(anaOrderDTOS);
        anaMdtrtDTO.setDiagnoseDtos(anaDiagnoseDTOS);

        //参保信息
        AnaInsuDTO anaInsuDTO = new AnaInsuDTO();
        //*参保人标识
        anaInsuDTO.setPatnId(basicDTO.getAac001());
        //*姓名
        anaInsuDTO.setPatnName(basicDTO.getAac003());
        //*性别
        anaInsuDTO.setGend(basicDTO.getAac004());
        //*出生日期
        anaInsuDTO.setBrdy(DateUtils.parse(basicDTO.getAac006(), DatePattern.NORM_DATE_PATTERN));
        //*统筹区编码
        anaInsuDTO.setPoolarea(insureVisitDTO.getInsuplcAdmdvs());
        //*当前就诊标识
        anaInsuDTO.setCurrMdtrtId(outptVisitDTO.getId());
        //*就诊信息集合
        anaInsuDTO.setFsiEncounterDtos(anaMdtrtDTO);
        //*就诊信息集合  --海南
        anaInsuDTO.setEncounterDtos(anaMdtrtDTO);

        //入参DTO
        AnalysisDTO analysisDTO = new AnalysisDTO();
        //*系统编码
        analysisDTO.setSyscode("YYDS");
        //任务ID
        analysisDTO.setTaskId("");
        //触发场景
        analysisDTO.setTrigScen(TrigScen.TRIG_SCEN_2.getCode());
        //规则标识集合
        analysisDTO.setRuleIds("");
        //*参保人信息
        analysisDTO.setPatientDtos(anaInsuDTO);

        return analysisDTO;
    }

    /***@Menthod saveOutptPaymentSettleInvoice
     * @Description 诊间支付结算接口
     * @param param
     * @author: liuliyun
     * @email liyun.liu@powersi.com
     * @date: 2022/09/02 11:56
     * @return: WrapperResponse
     **/
    @Override
    public WrapperResponse saveOutptPaymentSettleInvoice(Map<String, Object> param) {

        OutptVisitDTO outptVisitDTO = MapUtils.get(param, "outptVisitDTO");//获取个人信息
        OutptSettleDTO outptSettleDTO = MapUtils.get(param, "outptSettleDTO");//获取结算信息
        List<OutptPayDO> outptPayDOList = MapUtils.get(param, "outptPayDOList");//支付方式信息

        // 手动控制事物
        TransactionStatus status = null;
        // 全局返回对象
        WrapperResponse wrapperResponse = null;

        // 门诊结算票据表
        List<OutptSettleInvoiceDTO> pjList = null;

        //门诊结算票据内容表
        List<OutptSettleInvoiceContentDTO> pjnrList = null;
        try {

            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            // 事物隔离级别，开启新事务
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            // 获得事务状态
            status = transactionManager.getTransaction(def);

            // 先进行发票分单
            Map<String, Object> pjMap = this.disposeFpfd(outptSettleDTO.getVisitId(), outptSettleDTO.getId(), outptSettleDTO.getHospCode());

            // 门诊结算票据表
            pjList = MapUtils.get(pjMap, "jspjJsonList");

            //门诊结算票据内容表
            pjnrList = MapUtils.get(pjMap, "fplbJsonList");

            // 处理结算数据
            wrapperResponse = this.saveOutptPaymentSettle(outptVisitDTO, outptSettleDTO, outptPayDOList);

            saveOutptSettleInvoice(pjList, pjnrList);

            transactionManager.commit(status);

        } catch (Exception e) {
            if (status != null) {
                transactionManager.rollback(status);
            }
            throw e;
        }
        // 发票信息处理
        if (wrapperResponse.getCode() == 0 && wrapperResponse.getData() != null && outptSettleDTO.getIsInvoice()) {
            // 需要打印发票
            JSONObject data = (JSONObject) wrapperResponse.getData();
            OutinInvoiceDTO outinInvoiceDTO = (OutinInvoiceDTO) data.get("outinInvoiceDTO");
            saveMzfpDy(outinInvoiceDTO, pjList, pjnrList);
        }
        return wrapperResponse;
    }

    /**
     * @Menthod saveOutptPaymentSettle
     * @Desrciption  诊间支付结算接口
     * @param outptVisitDTO 个人信息
     * @param outptSettleDTO 结算信息
     * @Author liuliyun
     * @Date 2022/09/02 13:47
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 请求结果
     */
    public WrapperResponse saveOutptPaymentSettle(OutptVisitDTO outptVisitDTO, OutptSettleDTO outptSettleDTO, List<OutptPayDO> outptPayDOList) {
        String hospCode = outptVisitDTO.getHospCode(); //医院编码
        String visitId = outptVisitDTO.getId(); //就诊id
        String userId = outptVisitDTO.getCrteId(); //当前登录用户id
        String userName = outptVisitDTO.getCrteName(); //当前登录用户姓名
        String depId = outptVisitDTO.getDeptId(); //执行科室
        String settleId = outptSettleDTO.getId();//结算id
        String code = outptVisitDTO.getCode(); // 操作人编码
        List<Map<String, Object>> outinInvoiceList = null;//返回发票打印的费用统计信息

        String cardNo = outptVisitDTO.getCardNo(); // 一卡通卡号
        BigDecimal cardPrice = outptVisitDTO.getCardPrice();  //一卡通支付金额
        String profileId = outptVisitDTO.getProfileId();  // 患者档案id

        //生成redis结算Key，医院编码 + 证件号码 + 划价收费KEY
        String key = new StringBuilder(hospCode).append(StringUtils.isEmpty(visitId) ? outptVisitDTO.getCertNo() : visitId)
                .append(Constants.OUTPT_FEES_REDIS_KEY).toString();
        //校验该用户是否在结算
        if (StringUtils.isNotEmpty(redisUtils.get(key))) {
            throw new AppException("划价收费提示：该患者正在别处结算；请稍后再试。");
        }
        OutinInvoiceDTO outinInvoiceDTO = new OutinInvoiceDTO();//发票段信息
        String settleNo = "";
        try {
            redisUtils.set(key, key, 600);
            // 1、 校验是否使用发票，是否存在发票段（没有返回错误信息，页面给出选择发票段信息）
            if (outptSettleDTO.getIsInvoice()) {
                outinInvoiceDTO.setHospCode(hospCode);//医院编码
                outinInvoiceDTO.setUseId(userId);//发票使用人id
                List<String> typeCode = new ArrayList<String>();//票据类型（0、全院通用，1、门诊发票，2、挂号发票，3、门诊通用，4、住院）
                Collections.addAll(typeCode, Constants.PJLX.TY, Constants.PJLX.MZ, Constants.PJLX.MZTY);
                outinInvoiceDTO.setTypeCodeList(typeCode);//0、全院通用；1、门诊发票；3、门诊通用
                //校验是否有在用状态的发票段，有就返回在用的发票信息
                outinInvoiceDTO.setStatusCode(Constants.PJSYZT.ZY);//使用状态 = 在用状态
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("hospCode", hospCode);
                map.put("outinInvoiceDTO", outinInvoiceDTO);
                List<OutinInvoiceDTO> outinInvoiceDTOS = outinInvoiceService.updateForOutinInvoiceQuery(map).getData();
                if (outinInvoiceDTOS == null || outinInvoiceDTOS.size() != 1) {
                    //没有发票信息
                    //return WrapperResponse.info(-2, "请选择发票段", outinInvoiceDTOS);
                    throw new AppException("当前没有可用发票，请领取发票或取消使用发票再结算");
                }
                outinInvoiceDTO = outinInvoiceDTOS.get(0);
            }

            // 2、 校验费用信息是否正确（根据当前结算id，查询费用表）
            Map<String, Object> param = new HashMap<String, Object>();
            //hospCode（医院编码）、statusCode（状态标志）、settleCode（结算状态）、settleId（结算id）
            param.put("hospCode", hospCode);//hospCode（医院编码）
            param.put("statusCode", Constants.ZTBZ.ZC);//statusCode（状态标志 = 正常）
            param.put("settleCode", Constants.JSZT.YUJS);//settleCode（结算状态 = 预结算）
            param.put("settleId", settleId);//settleId（结算id）
            List<OutptCostDTO> outptCostDTOList = outptCostDAO.queryBySettleId(param);

            if (outptCostDTOList == null && outptCostDTOList.isEmpty()) {
                throw new AppException("支付失败，未找到本次结算费用信息，请确认是否已经结算或者刷新后重试。");
            }
            // 如果患者是体检患者，则不需要校验费用条数
            if (outptVisitDTO.getIsPhys() == null || "".equals(outptVisitDTO.getIsPhys())) {
                if (outptCostDTOList.size() != outptVisitDTO.getOutptCostDTOList().size()) {
                    throw new AppException("费用数量不正确，请刷新浏览器再试");
                }
            }

            // 更新医技申请单状态
            if (!ListUtils.isEmpty(outptCostDTOList)) {
                outptCostDAO.updateMedicApply(visitId, hospCode, "02", outptCostDTOList);
            }

            /* 生成领药申请单，校验库存、领药申请单明细 */
            Map<String, Object> queryParam = new HashMap<String, Object>();
            queryParam.put("hospCode", outptVisitDTO.getHospCode());//医院编码
            queryParam.put("pfTypeCode", outptVisitDTO.getPreferentialTypeId());//优惠类型
            queryParam.put("items", outptCostDTOList);//当前用户的费用信息
            List<OutptCostDTO> outptCostDTOS = outptCostDAO.queryDrugMaterialListByIds(queryParam);

            //统计优惠总金额
            BigDecimal realityPrice = new BigDecimal(0);//优惠后总费用
            List<String> ids = new ArrayList<String>();//修改费用状态费用id
            List<PharOutReceiveDetailDO> pharOutReceiveDetailDOList = new ArrayList<PharOutReceiveDetailDO>();//领药申请明细参数接收
            Map<String, Map<String, Object>> pharOutReceiveMap = new HashMap<String, Map<String, Object>>();
            List<String> outptPrescribeIds = new ArrayList<String>();//存储处方id

            // 3、 校验药品、材料库存，并生成领药申请单明细
            Map<String, Object> tempMap = this.checkStockAndCreatePharOutReceiveDetail(outptCostDTOList, outptCostDTOS, hospCode);
            realityPrice = (BigDecimal) tempMap.get("realityPrice");
            ids = (List<String>) tempMap.get("ids");
            pharOutReceiveDetailDOList = (List<PharOutReceiveDetailDO>) tempMap.get("pharOutReceiveDetailDOList");
            pharOutReceiveMap = (Map<String, Map<String, Object>>) tempMap.get("pharOutReceiveMap");
            outptPrescribeIds = (List<String>) tempMap.get("outptPrescribeIds");

            OutptSettleDTO outptSettleDTO1 = outptSettleDAO.selectByPrimaryKey(settleId);//获取本次结算费用信息
            // 4、 校验个人支付金额，是否与本次结算的费用一致，不一致表示费用出现了问题
            // 2022年1月5日11:01:11 退费重收时默认 将优惠后金额保留到2位小数，
            if (outptVisitDTO.getTfcsMark() != null && "tfcs".equals(outptVisitDTO.getTfcsMark())) {
                realityPrice = realityPrice.setScale(2, BigDecimal.ROUND_HALF_DOWN); // 将计算后的优惠后总金额自动保留两位小数， 不四舍五入
            }
            if (!BigDecimalUtils.equals(realityPrice, outptSettleDTO1.getRealityPrice())) {
                throw new AppException("支付失败，该患者费用信息错误，请刷新后重试。");
            }

            // 使用一卡通  start ========================
            // 校验是否使用一卡通，校验一卡通余额，档案id，卡状态
            if (cardPrice == null) {
                cardPrice = new BigDecimal(0);
            }
            BigDecimal tempCardPrice = new BigDecimal(0);
            if (cardNo != null) { // 门诊划价收费时，页面传入的一卡通账号为空，没有使用一卡通
                Map<String, Object> map = new HashMap<>();
                map.put("code", "SF_YKTCZ");
                map.put("hospCode", hospCode);
                WrapperResponse<SysParameterDTO> wr = sysParameterService.getParameterByCode(map);
                if (wr.getData().getValue() != null && ("2".equals(wr.getData().getValue()) || "4".equals(wr.getData().getValue()) || "6".equals(wr.getData().getValue()) || "8".equals(wr.getData().getValue()))) {
                    // 1、查询一卡通的余额是否足够，且状态是否为正常
                    BaseCardRechargeChangeDTO dto = new BaseCardRechargeChangeDTO();
                    dto.setHospCode(hospCode);
                    dto.setCardNo(cardNo);
                    BaseCardRechargeChangeDTO baseCardRechargeChangeDTO = null;
                    if (BigDecimalUtils.greaterZero(cardPrice) || (cardNo != null && !cardNo.equals(""))) {
                        baseCardRechargeChangeDTO = baseCardRechargeChangeDAO.getBaseCardRechargeChangeDTO(dto);
                    }

                    // 2、需要校验当前挂号的档案id与卡的档案id一致，
                    if (baseCardRechargeChangeDTO == null && BigDecimalUtils.greaterZero(cardPrice)) {
                        throw new AppException("结算支付时查询就诊卡出错，请联系管理员");
                    }
                    if (baseCardRechargeChangeDTO != null && BigDecimalUtils.greaterZero(cardPrice)) {
                        if (baseCardRechargeChangeDTO.getProfileId() == null || !baseCardRechargeChangeDTO.getProfileId().equals(profileId)) {
                            throw new AppException("结算支付时查询就诊卡档案id与当前患者档案id不一致，请联系管理员");
                        }
                        if (baseCardRechargeChangeDTO.getCardStatusCode() == null || !"0".equals(baseCardRechargeChangeDTO.getCardStatusCode())) {
                            throw new AppException("结算支付时查询就诊卡状态非正常状态，不能使用该卡");
                        }
                        if (baseCardRechargeChangeDTO.getAccountBalance() == null || BigDecimalUtils.less(baseCardRechargeChangeDTO.getAccountBalance(), cardPrice)){
                            throw new AppException("结算支付时查询就诊卡余额小于需要支付的金额，不能支付");
                        }
                        if (!BigDecimalUtils.equals(baseCardRechargeChangeDTO.getAccountBalance(), baseCardRechargeChangeDTO.getEndBalance())) {
                            throw new AppException("结算支付时查询就诊卡余额与上一次异动后金额不一致，不能支付");
                        }
                        // 更新一卡通充值、消费异动表，更新一卡通主表余额
                        baseCardRechargeChangeDTO.setId(SnowflakeUtils.getId());
                        baseCardRechargeChangeDTO.setStatusCode("8"); // 卡异动状态 8： 消费
                        baseCardRechargeChangeDTO.setPayCode(null);  // 支付方式
                        baseCardRechargeChangeDTO.setPrice(BigDecimalUtils.negate(cardPrice));
                        baseCardRechargeChangeDTO.setStartBalance(baseCardRechargeChangeDTO.getEndBalance());
                        baseCardRechargeChangeDTO.setStartBalanceEncryption(baseCardRechargeChangeDTO.getStartBalanceEncryption());
                        baseCardRechargeChangeDTO.setEndBalance(BigDecimalUtils.subtract(baseCardRechargeChangeDTO.getStartBalance(), cardPrice));
                        baseCardRechargeChangeDTO.setEndBalanceEncryption(null);
                        baseCardRechargeChangeDTO.setSettleType("03");
                        baseCardRechargeChangeDTO.setSettleId(settleId);
                        baseCardRechargeChangeDTO.setCrteId(userId);
                        baseCardRechargeChangeDTO.setCrteName(userName);
                        baseCardRechargeChangeDTO.setCrteTime(new Date());
                        // 新增一卡通消费异动
                        int temp1 = baseCardRechargeChangeDAO.insertBaseCardRechargeChange(baseCardRechargeChangeDTO);
                        Map<String, Object> baseCardMap = new HashMap<>();
                        baseCardMap.put("hospCode", hospCode);
                        baseCardMap.put("profileId", profileId);
                        baseCardMap.put("cardNo", cardNo);
                        baseCardMap.put("accountBalance", baseCardRechargeChangeDTO.getEndBalance());
                        // 更新一卡通主表余额
                        int temp2 = baseCardRechargeChangeDAO.updateCardAccountBalance(baseCardMap);
                        if (temp1 <= 0 || temp2 <= 0) {
                            throw new AppException("保存一卡通异动异常，未写入数据，请联系管理员");
                        }
                        tempCardPrice = cardPrice;
                    }
                }
            }
            // 使用一卡通  end ========================

            // 5、 保存结算信息（支付方式与各方式金额）
            boolean isChange = this.saveOutptPays(outptPayDOList, hospCode, settleId, visitId, outptSettleDTO1, tempCardPrice);
            if (isChange) {
                throw new AppException("支付失败；本次账户支付金额小于当前按医院配置的舍入规则计算后的应付金额！");
            }

            // 6、 根据费用信息修改本次结算的费用状态
            Map<String, Object> costParam = new HashMap<String, Object>();
            costParam.put("settleCode", Constants.JSZT.YIJS);//费用状态 = 已结算
            costParam.put("ids", ids);//费用id
            outptCostDAO.editCostSettleCodeByIDS(costParam);

            // 7、 修改门诊结算表此次结算信息状态
            OutptSettleDO outptSettleDO = new OutptSettleDO();//修改参数
            outptSettleDO.setId(settleId);//结算id
            SysParameterDO sysParameterDO = getSysParameter(hospCode, Constants.HOSPCODE_DISCOUNTS_KEY);//获取当前医院优惠配置
            BigDecimal ssje = new BigDecimal(0);
            if (outptVisitDTO.getTfcsMark() != null && "tfcs".equals(outptVisitDTO.getTfcsMark())) {
                ssje = BigDecimalUtils.subtract(realityPrice, outptVisitDTO.getTruncPrice());
            } else {
                ssje = BigDecimalUtils.subtract(realityPrice, BigDecimalUtils.rounding(sysParameterDO.getValue(), realityPrice));
            }
            outptSettleDO.setCardPrice(cardPrice); // 一卡通支付金额
            outptSettleDO.setActualPrice(BigDecimalUtils.subtract(ssje, cardPrice));//实收金额
            outptSettleDO.setIsSettle(Constants.SF.S);//是否结算 = 是
            outptSettleDO.setSourcePayCode("1");  // 0:HIS 1:微信  2：支付宝   3：自助机
            outptSettleDO.setOnlinePay(Constants.SF.S); // 是否在线支付：是
            // 2021年11月25日20:13:53  添加挂账金额，挂账支付方式必须为8
            for (OutptPayDO dto : outptPayDOList) {
                if ( "8".equals(dto.getPayCode())) {
                    outptSettleDO.setCreditPrice(dto.getPrice());
                }
            }
            outptSettleDAO.updateByPrimaryKeySelective(outptSettleDO);//修改结算状态
            // 7.1 结算后需要将结算单号返回给前端
            Map<String, Object> settleMap = new HashMap<>();
            settleMap.put("id", settleId);
            settleMap.put("hospCode", hospCode);
            OutptSettleDTO dto = outptSettleDAO.getById(settleMap);
            settleNo = dto.getSettleNo();

            // 8、 修改处方表结算信息
            if (!outptPrescribeIds.isEmpty()) {
                Map<String, Object> outptPrescribeParam = new HashMap<String, Object>();
                outptPrescribeParam.put("hospCode", hospCode);//医院编码
                outptPrescribeParam.put("ids", outptPrescribeIds);//处方ids
                outptPrescribeParam.put("settleId", settleId);//结算id
                outptPrescribeParam.put("isSettle", Constants.SF.S);//是否结算 = 是
                outptCostDAO.updateOutptPrescribeByIds(outptPrescribeParam);
            }

            // 9、判断是否需要生成发票信息
            if (outptSettleDTO.getIsInvoice()) {
                outinInvoiceDTO.setVisitId(visitId);
                outinInvoiceDTO.setSettleId(settleId);
            }

            // 10、 取最佳领药窗口，生成领药申请单（主单），保存领药申请单与领药申请单详单
            this.savePharOutReceive(hospCode, visitId, depId, userId, userName, settleId,
                    pharOutReceiveMap, outptCostDTOList, pharOutReceiveDetailDOList);
            // 10、1 更新领药明细表中原费用id 官红强 2021年4月27日11:31:47
            // 11、 获取个人、费用信息给前端做票据打印
            Map<String, Object> outInvoiceParam = new HashMap<String, Object>();
            outInvoiceParam.put("hospCode", hospCode);
            OutinInvoiceDTO outinInvoiceDTO1 = new OutinInvoiceDTO();
            outinInvoiceDTO1.setHospCode(hospCode);//医院编码
            outinInvoiceDTO1.setInvoiceType(Constants.PJLX.MZ);//发票类型 = 门诊
            outinInvoiceDTO1.setSettleId(settleId);//结算id
            outInvoiceParam.put("outinInvoiceDTO", outinInvoiceDTO1);
            // 诊间支付 调用支付平台接口
            this.saveOutptPaymentInfo(outptVisitDTO, settleId, visitId, userId, userName, outptCostDTOList, hospCode, code, outptSettleDTO1);
            outptVisitDTO.setRealityPrice(realityPrice);
            outinInvoiceList = outinInvoiceService.queryItemInfoByParams(outInvoiceParam).getData();

            outptVisitDTO.setReceiveName(outinInvoiceDTO.getReceiveName());
            outptVisitDTO.setPrefix(outinInvoiceDTO.getPrefix());
            // 发票不分单返回发票号
            outptVisitDTO.setInvoiceNo(outinInvoiceDTO.getCurrNo());
        } catch (Exception e) {
            throw e;
        } finally {
            redisUtils.del(key);//删除结算key
        }

        SysParameterDO sys = getSysParameter(hospCode, "MZFP_FDFS");
        String SFFD = "1";
        if (sys != null && !"".equals(sys.getValue())) {
            SFFD = sys.getValue();
        }

        JSONObject result = new JSONObject();
        result.put("outptVisit", outptVisitDTO);//个人信息
        result.put("outinInvoice", outinInvoiceList);//费用统计信息
        result.put("outinInvoiceDTO", outinInvoiceDTO);//费用统计信息
        result.put("settleNo", settleNo);
        result.put("SFFD", SFFD);
        return WrapperResponse.success("支付成功。", result);
    }

    /**
     * @Description: 保存诊间支付结算信息（结算、支付）
     * @Param:settleId: 结算id；visitID： 就诊id； userId: 创建人id； userName： 创建人姓名；outptCostDTOList：费用集合；hospCode: 医院编码 * code:操作人编码
     * @Author: liuliyun
     * @Email: liuliyun@powersi.com
     * @Date 2022/09/05 13:38
     * @Return Map<String,Object>
     */
    private Map<String,Object> saveOutptPaymentInfo(OutptVisitDTO outptVisitDTO, String settleId, String visitId, String userId, String userName,
                                    List<OutptCostDTO> outptCostDTOList, String hospCode, String code, OutptSettleDTO outptSettleDTO1) {
        //获取诊间支付结算信息
        Map<String, Object> paymentParam = new HashMap<String, Object>();
        paymentParam.put("hospCode", outptVisitDTO.getHospCode());
        PaymentSettleDTO paymentSettleDTO = new PaymentSettleDTO();
        paymentSettleDTO.setHospCode(hospCode);//医院编码
        paymentSettleDTO.setVisitId(visitId);//就诊id
        paymentSettleDTO.setSettleId(settleId);//结算id
        paymentParam.put("paymentSettleDTO", paymentSettleDTO);
        paymentSettleDTO = paymentSettleService_consummer.quyeryPaymentInfoByCondition(paymentParam);
        if (paymentSettleDTO == null) {
            throw new AppException("未获取到诊间支付结算信息，请联系管理员。");
        }
        paymentSettleDTO.setCrteName(userName);
        paymentSettleDTO.setCrteId(userId);
        // * 调用诊间支付需要的入参
        Map<String,Object> paymentSettleParam =new HashMap<>();
        paymentSettleParam.put("paymentSettleDTO",paymentSettleDTO);
        paymentSettleParam.put("hospCode",hospCode);
        Map<String, Object> result = new HashMap<>();
        try {
            /*todo 调用支付平台支付*/
            result = outptPaymentService_consummer.updatePaymentSettle(paymentSettleParam);
            if (result != null) {
                String orderStatus = MapUtils.get(result, "orderStatus"); // 支付平台支付状态
                if (StringUtils.isNotEmpty(orderStatus) && Constants.ZJ_PAY_ZFZT.ZFCG.equals(orderStatus)) {   // 支付成功
                    this.updatePaymentSettleStatus(result, paymentParam, paymentSettleDTO); // 更新结算表状态
                } else if (StringUtils.isNotEmpty(orderStatus) && Constants.ZJ_PAY_ZFZT.ZFSB.equals(orderStatus)) { // 支付失败
                    String errorMessage = MapUtils.get(result, "failCause"); // 支付失败返回的错误信息
                    throw new AppException("返回错误信息：" + errorMessage);
                } else if (StringUtils.isNotEmpty(orderStatus) && Constants.ZJ_PAY_ZFZT.ZFZ.equals(orderStatus)) { // 支付中
                    /*todo 这里调用支付查询接口  需要轮询 异步还是其他方式有待讨论*/
                    this.updateChargeQuery(paymentSettleParam, paymentParam, paymentSettleDTO);
                }
            } else {
                throw new AppException("支付平台支付失败!");
            }
        }catch (Exception e){
            // 调用撤销接口
            this.revokePaymentSettle(paymentParam);
            throw new AppException("支付平台支付失败! 返回错误信息：" + e.getMessage());
        }
        return result;
    }

    public void updatePaymentSettleStatus(Map<String,Object> queryResult,Map<String,Object> paymentParam,PaymentSettleDTO pPaymentSettleDTO){
        String orderId = MapUtils.get(queryResult,"orderId"); // 支付平台订单号
        String payType = MapUtils.get(queryResult,"payType"); // 支付平台支付类型
        String payCode ="";
        if (Constants.ZJ_PAY_TYPE.WX.equals(payType)){
            payCode = Constants.ZFFS.WX;
        }else if (Constants.ZJ_PAY_TYPE.ZFB.equals(payType)){
            payCode = Constants.ZFFS.ZFB;
        }
        //修改诊间支付结算表 payment_settle；结算状态 = 结算
        PaymentSettleDO paymentSettleDO = new PaymentSettleDO();
        paymentSettleDO.setId(pPaymentSettleDTO.getId());//id
        paymentSettleDO.setSettleId(pPaymentSettleDTO.getSettleId());
        paymentSettleDO.setVisitId(pPaymentSettleDTO.getVisitId()); // 就诊id
        paymentSettleDO.setPaymentSettleId(orderId); // 支付平台结算id
        paymentSettleDO.setSettleCode(Constants.SETTLECODE.YJS);//结算状态 = 结算
        paymentSettleDO.setIsSettle(Constants.SF.S); // 是否结算 = 是
        paymentSettleDO.setPayCode(payCode); // 支付方式: 微信
        paymentSettleDO.setSettleTime(DateUtils.getNow());
        paymentParam.put("paymentSettleDO", paymentSettleDO);
        paymentSettleService_consummer.updatePaymentSettleInfo(paymentParam);
        //修改诊间支付订单表 payment_order；结算状态 = 结算
        PaymentOrderDO paymentOrderDO = new PaymentOrderDO();
        paymentOrderDO.setId(pPaymentSettleDTO.getId());//id
        paymentOrderDO.setSettleId(pPaymentSettleDTO.getSettleId());
        paymentOrderDO.setVisitId(pPaymentSettleDTO.getVisitId()); // 就诊id
        paymentOrderDO.setSettleCode(Constants.SETTLECODE.YJS);//结算状态 = 结算
        paymentOrderDO.setPayCode(payCode); // 支付方式: 微信
        paymentOrderDO.setPaymentSettleId(orderId); // 支付平台结算id
        paymentOrderDO.setUpdateTime(DateUtils.getNow());
        paymentOrderDO.setUpdateId(pPaymentSettleDTO.getCrteId());
        paymentOrderDO.setUpdateName(pPaymentSettleDTO.getCrteName());
        paymentParam.put("paymentOrderDO", paymentOrderDO);
        paymentOrderService_consummer.updatePaymentOrder(paymentParam);
    }

    // 支付平台 支付查询
    public void updateChargeQuery(Map<String,Object> paymentSettleParam,Map<String,Object> paymentParam,PaymentSettleDTO paymentSettleDTO){
        Map<String,Object> queryResult = outptPaymentService_consummer.updatePaymentSettleQuery(paymentSettleParam);
        String payStatus = MapUtils.get(queryResult,"orderStatus"); // 支付平台支付状态
        if (StringUtils.isNotEmpty(payStatus)&&Constants.ZJ_PAY_ZFZT.ZFCG.equals(payStatus)) {   // 支付成功
            this.updatePaymentSettleStatus(queryResult,paymentParam,paymentSettleDTO); // 更新结算表状态
        }else if (StringUtils.isNotEmpty(payStatus)&&Constants.ZJ_PAY_ZFZT.ZFZ.equals(payStatus)){ // 支付中
            this.updateChargeQuery(paymentSettleParam,paymentParam,paymentSettleDTO);  // 继续轮询
        }else if (StringUtils.isNotEmpty(payStatus)&&Constants.ZJ_PAY_ZFZT.ZFZ.equals(payStatus)){ // 支付失败
            // 调用撤销接口
            this.revokePaymentSettle(paymentSettleParam);
        }else if (StringUtils.isNotEmpty(payStatus)&&Constants.ZJ_PAY_ZFZT.DDGB.equals(payStatus)){ // 订单已关闭
             /*todo 交易关闭 his本地结算处理*/

        }
    }

    // 支付平台 支付撤销
    public void revokePaymentSettle(Map<String,Object> paymentSettleParam){
        Map<String,Object> revokeResult = outptPaymentService_consummer.updatePaymentRevoke(paymentSettleParam);
        if (ObjectUtil.isNotEmpty(revokeResult)) {
            String resultCode = MapUtils.get(revokeResult, "resultCode"); // 支付平台支付状态
            if (StringUtils.isNotEmpty(resultCode) && "FAIL".equals(resultCode)) {
                String recall = MapUtils.get(revokeResult, "recall"); // 是否需要重试
                if (StringUtils.isNotEmpty(recall) && "Y".equals(recall)) { // 需要重试
                    this.revokePaymentSettle(paymentSettleParam);
                }
            }else if (StringUtils.isNotEmpty(resultCode) && "SUCCESS".equals(resultCode)){
                /*todo 撤销成功 his本地结算处理*/

            }
        }
    }
}
