package cn.hsa.inpt.fees.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.inpt.advancepay.dao.InptAdvancePayDAO;
import cn.hsa.module.inpt.advancepay.dto.InptAdvancePayDTO;
import cn.hsa.module.inpt.doctor.dao.InptCostDAO;
import cn.hsa.module.inpt.doctor.dao.InptVisitDAO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.doctor.entity.InptCostDO;
import cn.hsa.module.inpt.fees.bo.InptCancelSettlementBO;
import cn.hsa.module.inpt.fees.dao.*;
import cn.hsa.module.inpt.fees.dto.InptSettleDTO;
import cn.hsa.module.inpt.fees.dto.InptSettleInvoiceDTO;
import cn.hsa.module.inpt.fees.entity.InptCostSettleDO;
import cn.hsa.module.inpt.fees.entity.InptPayDO;
import cn.hsa.module.inpt.fees.entity.InptSettleDO;
import cn.hsa.module.inpt.fees.entity.InptSettleInvoiceContentDO;
import cn.hsa.module.insure.inpt.service.InptService;
import cn.hsa.module.insure.inpt.service.InsureUnifiedPayInptService;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualCostDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualSettleDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.service.InsureConfigurationService;
import cn.hsa.module.insure.module.service.InsureIndividualCostService;
import cn.hsa.module.insure.module.service.InsureIndividualVisitService;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Package_name: cn.hsa.inpt.fees.bo.impl
 * @Class_name: InptCancelSettlementBOImpl
 * @Describe(描述): 住院取消结算BOImpl
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/09/25 9:26
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
public class InptCancelSettlementBOImpl extends HsafBO implements InptCancelSettlementBO {

    @Resource
    private InptSettleDAO inptSettleDAO;

    @Resource
    private InptCostDAO inptCostDAO;

    @Resource
    private InptAdvancePayDAO inptAdvancePayDAO;

    @Resource
    private InptSettleInvoiceDAO inptSettleInvoiceDAO;

    @Resource
    private InptSettleInvoiceContentDAO inptSettleInvoiceContentDAO;

    @Resource
    private InptVisitDAO inptVisitDAO;

    @Resource
    private InptService InptService_consumer;

    @Resource
    private InptPayDAO inptPayDAO;

    @Resource
    InsureUnifiedPayInptService insureUnifiedPayInptService_consumer;

    @Resource
    InsureIndividualCostService insureIndividualCostService_consumer;

    @Resource
    private SysParameterService sysParameterService_consumer;

    @Resource
    private InptCostSettleDAO inptCostSettleDAO;

    @Resource
    private InsureIndividualVisitService insureIndividualVisitService;

    @Resource
    private InsureConfigurationService insureConfigurationService_consumer;

    @Resource
    private RedisUtils  redisUtils;

    /**
     * @Menthod querySettleVisitPage
     * @Desrciption 获取取消结算用户信息
     * @param inptSettleDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/10/12 10:03
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse querySettleVisitPage(InptSettleDTO inptSettleDTO) {
        PageHelper.startPage(inptSettleDTO.getPageNo(),inptSettleDTO.getPageSize());
        return WrapperResponse.success(PageDTO.of(inptSettleDAO.queryInptSettleIsVisit(inptSettleDTO)));
    }

    /**
     * @Menthod queryCostOrPayList
     * @Desrciption 获取患者费用信息、预交金信息
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/10/12 10:03
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse queryCostOrPayList(Map param) {
        //根据就诊id、结算id查询费用信息
        List<Map<String,Object>> inptCostList = new ArrayList<>();
        if("1".equals(MapUtils.get(param,"flag"))){
          inptCostList = inptCostDAO.queryNewInptCostByBfc(param);
        } else {
          inptCostList = inptCostDAO.queryInptCostByBfc(param);
        }
        //根据就诊id查询预交金信息
        InptAdvancePayDTO inptAdvancePayDTO = new InptAdvancePayDTO();
        inptAdvancePayDTO.setHospCode((String) param.get("hospCode"));//医院编码
        inptAdvancePayDTO.setVisitId((String) param.get("id"));//就诊id
        List<InptAdvancePayDTO> inptAdvancePayDTOList = inptAdvancePayDAO.queryAll(inptAdvancePayDTO);
        //返回结果
        JSONObject result = new JSONObject();
        result.put("inptCostList",inptCostList);
        result.put("inptAdvancePayDTOList",inptAdvancePayDTOList);
        return WrapperResponse.success(result);
    }

    /**
     * @Menthod editCancelSettlement
     * @Desrciption 取消结算
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/10/12 10:05
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse editCancelSettlement(Map param) {
        String hospCode = (String) param.get("hospCode"); //医院编码
        String visitId = (String) param.get("visitId"); //就诊id
        String settleId = (String) param.get("settleId"); //结算id
        String userId = (String) param.get("userId");//当前用户id
        String userName = (String) param.get("userName");//当前用户姓名
        String userCode =  (String) param.get("userCode");//当前用户工号
        try{
            //处理人员信息
            InptVisitDTO inptVisitDTO = new InptVisitDTO();
            inptVisitDTO.setHospCode(hospCode);//医院编码
            inptVisitDTO.setId(visitId);//就诊id
            inptVisitDTO = inptVisitDAO.getInptVisitById(inptVisitDTO);
            if (inptVisitDTO == null){throw new AppException("未找到患者信息。");}
            String isHalfSettle = "0"; // 是否中途结算 1:中途结算 0：出院结算
            String settleCode = "0"; // 结算类型
            //查询结算信息
            InptSettleDO inptSettleDO = inptSettleDAO.selectByPrimaryKey(settleId);
            settleCode = inptSettleDO.getTypeCode();
            // ==============取消结算，医保患者需要取消中途结算，必须从最近一次中途结算开始取消   start===============================
            if (Integer.valueOf(inptVisitDTO.getPatientCode()) > 0) { // 病人类型大于0表示患者为医保患者
                Map<String, Object> selectMap = new HashMap<>();
                selectMap.put("hospCode", hospCode);
                selectMap.put("settleId", settleId);
                selectMap.put("visitId", visitId);
                Map<String, Object> insureCost = inptCostSettleDAO.selectMidWaySettleMaxTimes(selectMap);
                if (insureCost != null ) {
                    isHalfSettle = MapUtils.get(insureCost, "isHalfSettle");
                    int settleCount = MapUtils.get(insureCost, "settleCount");
                    String isLast = MapUtils.get(insureCost, "isLast");
                    if ("0".equals(isLast)) {
                        throw new AppException("当前退费为第【" + settleCount + "】次中途结算，请从最后一次结算开始退费");
                    }
                }
            }
            // ==============取消结算，医保患者需要取消中途结算，必须从最近一次中途结算开始取消   start===============================
            if (inptSettleDO == null){return WrapperResponse.fail("未找到结算信息。",null);}
            //生成结算冲红信息
            String settleRedId = SnowflakeUtils.getId(); //结算冲红id
            inptSettleDO.setId(settleRedId);//id = 冲红id
            inptSettleDO.setStatusCode(Constants.ZTBZ.CH);//状态标志 = 冲红
            inptSettleDO.setTotalPrice(BigDecimalUtils.negate(inptSettleDO.getTotalPrice()));//总金额 = -总金额
            inptSettleDO.setRealityPrice(BigDecimalUtils.negate(inptSettleDO.getRealityPrice()));//优惠后金额 = -优惠后金额
            inptSettleDO.setTruncPrice(BigDecimalUtils.negate(inptSettleDO.getTruncPrice()));//舍入金额 = -舍入金额
            inptSettleDO.setActualPrice(BigDecimalUtils.negate(inptSettleDO.getActualPrice()));//实收金额 = -实收金额
            inptSettleDO.setSelfPrice(BigDecimalUtils.negate(inptSettleDO.getSelfPrice()));//个人支付金额 = -个人支付金额
            inptSettleDO.setMiPrice(BigDecimalUtils.negate(inptSettleDO.getMiPrice()));//统筹支付金额 = -统筹支付金额
            inptSettleDO.setApTotalPrice(BigDecimalUtils.negate(inptSettleDO.getApTotalPrice()));//预交金合计 = -预交金合计
            inptSettleDO.setApOffsetPrice(BigDecimalUtils.negate(inptSettleDO.getApOffsetPrice()));//预交金冲抵 = -预交金冲抵
            inptSettleDO.setSettleTakePrice(BigDecimalUtils.negate(inptSettleDO.getSettleTakePrice()));//结算补收 = -结算补收
            inptSettleDO.setSettleBackPrice(BigDecimalUtils.negate(inptSettleDO.getSettleBackPrice()));//结算退款 = -结算退款
            inptSettleDO.setHospDfPrice(BigDecimalUtils.negate(inptSettleDO.getHospDfPrice()));//医院垫付金额 = -医院垫付金额
            inptSettleDO.setHospJmPrice(BigDecimalUtils.negate(inptSettleDO.getHospJmPrice()));//医院减免金额 = -医院减免金额
            inptSettleDO.setAcctPay(BigDecimalUtils.negate(inptSettleDO.getAcctPay()));//个人账户金额 = -个人账户金额
            inptSettleDO.setCreditPrice(BigDecimalUtils.negate(inptSettleDO.getCreditPrice()));
            inptSettleDO.setCrteId(userId);//创建人id
            inptSettleDO.setCrteName(userName);//创建人姓名
            inptSettleDO.setCrteTime(new Date());//创建时间
            inptSettleDO.setSettleTime(inptSettleDO.getCrteTime());
            inptSettleDAO.insertSelective(inptSettleDO);
            //修改结算被冲红信息
            InptSettleDO inptSettleDO1 = new InptSettleDO();
            inptSettleDO1.setId(settleId);//id
            inptSettleDO1.setStatusCode(Constants.ZTBZ.BCH);//状态标志 = 被冲红
            inptSettleDO1.setRedId(settleRedId);//冲红id
            inptSettleDAO.updateByPrimaryKeySelective(inptSettleDO1);

            //生成冲红支付信息
            List<InptPayDO> inptpayList = inptPayDAO.queryInptPayBySettleId(settleId);
            if (inptpayList != null && !inptpayList.isEmpty()){
                for (int i = 0; i < inptpayList.size(); i++){
                    inptpayList.get(i).setId(SnowflakeUtils.getId());//id
                    inptpayList.get(i).setSettleId(settleRedId);//冲红id
                    inptpayList.get(i).setPrice(BigDecimalUtils.negate(inptpayList.get(i).getPrice()));//-支付金额
                    inptpayList.get(i).setServicePrice(BigDecimalUtils.negate(inptpayList.get(i).getServicePrice()));//-手续费
                }
                inptPayDAO.batchInsert(inptpayList);
            }

            //查询当前住院发票信息
            InptSettleInvoiceDTO inptSettleInvoiceDTO = new InptSettleInvoiceDTO();
            inptSettleInvoiceDTO.setHospCode(hospCode);//医院编码
            inptSettleInvoiceDTO.setVisitId(visitId);//就诊id
            inptSettleInvoiceDTO.setSettleId(settleId);//结算id
            inptSettleInvoiceDTO.setStatusCode(Constants.ZTBZ.ZC);//状态标志 = 正常
            List<InptSettleInvoiceDTO> inptSettleInvoiceDTOS = inptSettleInvoiceDAO.findByCondition(inptSettleInvoiceDTO);
            if (inptSettleInvoiceDTOS != null && !inptSettleInvoiceDTOS.isEmpty()){
                //生成冲红信息
                List<InptSettleInvoiceDTO> redInptSettleInvoiceDTOS = DeepCopy.deepCopy(inptSettleInvoiceDTOS);//深度复制
                List<InptSettleInvoiceContentDO> inptSettleInvoiceContentDOList = new ArrayList<InptSettleInvoiceContentDO>();
                for (int i = 0; i < inptSettleInvoiceDTOS.size();i++){
                    String id = inptSettleInvoiceDTOS.get(i).getId();
                    //冲红
                    String redId = SnowflakeUtils.getId();
                    redInptSettleInvoiceDTOS.get(i).setId(redId);//id
                    redInptSettleInvoiceDTOS.get(i).setStatusCode(Constants.ZTBZ.CH);//状态标志 = 冲红
                    redInptSettleInvoiceDTOS.get(i).setTotalPrice(BigDecimalUtils.negate(inptSettleInvoiceDTOS.get(i).getTotalPrice()));//发票总金额 = -发票总金额
                    redInptSettleInvoiceDTOS.get(i).setSettleId(settleRedId);//结算id = 结算表冲红数据的id
                    //被冲红
                    inptSettleInvoiceDTOS.get(i).setStatusCode(Constants.ZTBZ.BCH);//状态标志 = 被冲红
                    inptSettleInvoiceDTOS.get(i).setRedId(redId);//冲红id
                    //住院发票情况表
                    InptSettleInvoiceContentDO inptSettleInvoiceContentDO = inptSettleInvoiceContentDAO.queryInvoiceContentBySettleInvoiceId(id);
                    if (inptSettleInvoiceContentDO != null){
                        inptSettleInvoiceContentDO.setId(SnowflakeUtils.getId());//id
                        inptSettleInvoiceContentDO.setSettleInvoiceId(redId);//冲红id
                        inptSettleInvoiceContentDO.setRealityPrice(BigDecimalUtils.negate(inptSettleInvoiceContentDO.getRealityPrice()));// -优惠后总金额
                        inptSettleInvoiceContentDOList.add(inptSettleInvoiceContentDO);
                    }
                }
                //批量保存
                inptSettleInvoiceDAO.batchInsert(redInptSettleInvoiceDTOS);
                //批量修改
                inptSettleInvoiceDAO.batchUpdate(inptSettleInvoiceDTOS);
                //批量增加发票情况信息
                if (!inptSettleInvoiceContentDOList.isEmpty()){
                    inptSettleInvoiceContentDAO.batchInsert(inptSettleInvoiceContentDOList);
                }
            }

            //处理预交金费用
            InptAdvancePayDTO inptAdvancePayDTO = new InptAdvancePayDTO();
            inptAdvancePayDTO.setHospCode(hospCode); //医院编码
            inptAdvancePayDTO.setVisitId(visitId);//就诊id
            inptAdvancePayDTO.setSettleId(settleId);//结算id
            inptAdvancePayDTO.setIsSettle(Constants.SF.S);//是否结算
            inptAdvancePayDTO.setStatusCode(Constants.ZTBZ.ZC);//状态标志 = 正常
            List<InptAdvancePayDTO> inptAdvancePayDTOList = inptAdvancePayDAO.queryAll(inptAdvancePayDTO);
            if (inptAdvancePayDTOList != null && !inptAdvancePayDTOList.isEmpty()){
                List<String> ids = new ArrayList<String>();
                inptAdvancePayDTOList.stream().forEach(inptAdvancePayDTO1 -> {
                    ids.add(inptAdvancePayDTO1.getId());
                });
                Map<String,Object> payParam = new HashMap<String,Object>();
                payParam.put("hospCode",hospCode);//医院编码
                payParam.put("ids",ids);//ids
                payParam.put("isSettle",Constants.SF.F);//是否结算 = 否
                payParam.put("settleId","");//结算id = ""
                inptAdvancePayDAO.editAdvancePayByIds(payParam);
            }
            //处理结算费用信息
            Map<String,Object> costQueryParam = new HashMap<String,Object>();
            costQueryParam.put("hospCode",hospCode);//医院编码
            costQueryParam.put("visitId",visitId);//就诊id
            // costQueryParam.put("statusCode",Constants.ZTBZ.ZC);//状态标志 = 正常
            costQueryParam.put("settleId",settleId);//结算id
            costQueryParam.put("settleCode",Constants.JSZT.YIJS);//结算状态 = 已结算
            List<InptCostDO> inptCostDOList = inptCostDAO.queryNewInptCostList(costQueryParam);
            if (inptCostDOList != null && !inptCostDOList.isEmpty()){
                List<String> ids = new ArrayList<String>(); //ids
                inptCostDOList.stream().forEach(inptCostDO -> {
                    ids.add(inptCostDO.getId());
                });
                Map<String,Object> costParam = new HashMap<String,Object>();
                costParam.put("hospCode",hospCode);//医院编码
                costParam.put("ids",ids);//ids
                costParam.put("settleId",""); //结算id = ""
                costParam.put("settleCode",Constants.JSZT.WJS); //结算标志 = 未结算
                inptCostDAO.editInptCostByIds(costParam);
            }

            // ========================住院退费也需要写费用备份表  start     2021年4月16日10:21:08=========
            List<InptCostSettleDO> inptCostSettleDOList = new ArrayList<InptCostSettleDO>();
            int costIndex = 0;
            for (InptCostDO inptCostDO : inptCostDOList) {
                if(inptCostDO.getRealityPrice() != null){
                    InptCostSettleDO inptCostSettleDO = new InptCostSettleDO();
                    inptCostSettleDO.setId(SnowflakeUtils.getId());//id
                    inptCostSettleDO.setHospCode(hospCode);//医院编码
                    inptCostSettleDO.setVisitId(visitId);//就诊id
                    inptCostSettleDO.setBabyId(inptCostDO.getBabyId());//babyID
                    inptCostSettleDO.setCostId(inptCostDO.getId());//费用id
                    inptCostSettleDO.setSettleId(settleId);//结算id
                    inptCostSettleDO.setRealityPrice(BigDecimalUtils.negate(inptCostDO.getRealityPrice())); // 优惠后金额
                    inptCostSettleDOList.add(inptCostSettleDO);
                }
            }
            //保存结算费用关联信息
            if (!inptCostSettleDOList.isEmpty()){
                inptCostSettleDAO.batchInsert(inptCostSettleDOList);
            }
            // ========================住院退费写备份表 end  2021年4月16日10:21:31=============

            // ==============取消结算，医保患者需要取消中途结算，病人状态不更新  ghq start=======2021年7月29日16:36:54========================
            if (isHalfSettle == null ) {
                throw new AppException("无法区分取消出院结算还是取消中途结算，请联系管理员查看insure_individual_cost表中【是否中途结算】是否有值");
            }
            if ("0".equals(isHalfSettle) && !"3".equals(settleCode)) {
                //判断患者是否是出院状态，如果是出院状态则修改为 预出院状态
                if (Constants.BRZT.CY.equals(inptVisitDTO.getStatusCode())){
                    //修改参数
                    InptVisitDTO inptVisitDTO1 = new InptVisitDTO();
                    inptVisitDTO1.setHospCode(hospCode);//医院编码
                    inptVisitDTO1.setId(visitId);//就诊id
                    inptVisitDTO1.setStatusCode(Constants.BRZT.YCY);//病人状态 = 预出院状态
                    inptVisitDAO.updateInptVisit(inptVisitDTO1);
                }
            }
            // ==============取消结算，医保患者需要取消中途结算，病人状态不更新  ghq end========2021年7月29日16:36:59======================

            //TODO 判断是否是医保病人（如果是医保病人，走医保取消结算接口 并且 生成医保结算冲红记录保存insure_individual_settle）
            // add by 廖继广 on 2020/11/04
            Integer patientCodeInt = Integer.valueOf(inptVisitDTO.getPatientCode());
            if (patientCodeInt > 0) {

                InsureIndividualSettleDTO insureIndividualSettleDTO = new InsureIndividualSettleDTO();
                insureIndividualSettleDTO.setHospCode(inptVisitDTO.getHospCode());
                insureIndividualSettleDTO.setVisitId(visitId);
                insureIndividualSettleDTO.setSettleId(settleId);
                insureIndividualSettleDTO.setIsHospital(Constants.SF.S);
                InsureIndividualSettleDTO selectEntity = inptVisitDAO.getInsureIndividualSettleInfo(insureIndividualSettleDTO);
                if(selectEntity !=null){
                    inptVisitDTO.setMedicalRegNo(selectEntity.getMedicalRegNo());
                }else{
                    throw new AppException("根据就诊id和His结算id查询医保结信息为空");
                }
                InsureIndividualVisitDTO insureIndividualVisitDTO = inptVisitDAO.getInsureIndividualVisitInfo(inptVisitDTO);
                if (insureIndividualVisitDTO == null) {
                    throw new AppException("医保取消结算失败：未查询到医保就医登记信息");
                }

                // 根据医院编码、医保注册编码查询医保配置信息
                InsureConfigurationDTO configDTO = new InsureConfigurationDTO();
                configDTO.setHospCode(hospCode); //医院编码
                configDTO.setRegCode(insureIndividualVisitDTO.getInsureRegCode()); // 医保注册编码
                configDTO.setIsValid(Constants.SF.S); // 是否有效
                Map configMap = new LinkedHashMap();
                configMap.put("hospCode", hospCode);
                configMap.put("insureConfigurationDTO", configDTO);
                List<InsureConfigurationDTO> configurationDTOList = insureConfigurationService_consumer.findByCondition(configMap);
                if (ListUtils.isEmpty(configurationDTOList)) {
                    throw new RuntimeException("未找到医保机构，请重新获取人员信息。");
                }
                InsureConfigurationDTO insureConfigurationDTO = configurationDTOList.get(0);
                // 获取该医保配置是否走统一支付平台，1走，0/null不走
                String isUnifiedPay = insureConfigurationDTO.getIsUnifiedPay();

                // 冲红医保的相关表（insure_individual_settle）
                insureIndividualVisitDTO.setInptVisitNo(inptVisitDTO.getInNo());
                // 原记录被冲红和冲红处理
                String insureSettleId = SnowflakeUtils.getId();
                if (selectEntity != null) { // 原记录被冲红和冲红处理
                    this.insureIndividualSettleChangrRed(selectEntity,insureSettleId);
                }

               /* Map<String,Object> isInsureUnifiedMap = new HashMap<>();
                isInsureUnifiedMap.put("hospCode",hospCode);
                isInsureUnifiedMap.put("code","UNIFIED_PAY");
                SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(isInsureUnifiedMap).getData();*/
                /**
                 * 通过获取系统参数来判断 是走医保统一支付平台 还是调用自己的的医保接口
                 */
//                if(sysParameterDTO !=null && Constants.SF.S.equals(sysParameterDTO.getValue())){
                if(StringUtils.isNotEmpty(isUnifiedPay) && "1".equals(isUnifiedPay)){
                    Map<String,Object> insureUnifiedMap = new HashMap<>();
                    insureUnifiedMap.put("setl_id",selectEntity.getInsureSettleId()); // 结算ID
                    insureUnifiedMap.put("mdtrt_id",insureIndividualVisitDTO.getMedicalRegNo()); // 就诊ID
                    insureUnifiedMap.put("medicalRegNo",insureIndividualVisitDTO.getMedicalRegNo()); // 就诊ID
                    insureUnifiedMap.put("hospCode",hospCode); // 医院编码
                    insureUnifiedMap.put("psn_no",insureIndividualVisitDTO.getAac001()); // 人员编号
                    insureUnifiedMap.put("medins_code",hospCode); // 医疗机构编码
                    insureUnifiedMap.put("serial_no",insureIndividualVisitDTO.getMedicineOrgCode()); //就医登记号
                    insureUnifiedMap.put("insuplc_admdvs",insureIndividualVisitDTO.getInsuplcAdmdvs()); // 参保地医保区划
                    insureUnifiedMap.put("insureIndividualVisitDTO", insureIndividualVisitDTO);
                    insureUnifiedMap.put("visitId", inptVisitDTO.getId());
                    insureUnifiedMap.put("inptVisitDTO", inptVisitDTO);
                    // 取消结算结算以后 更新费用表的结算id(把对应的费用表的结算id给设置为null)
                    Map<String,Object> settleMap = new HashMap<>();
                    InsureIndividualCostDTO insureIndividualCostDTO = new InsureIndividualCostDTO();
                    insureIndividualCostDTO.setHospCode(hospCode);
                    insureIndividualCostDTO.setInsureSettleId(null);
                    insureIndividualCostDTO.setVisitId(inptVisitDTO.getId());
                    settleMap.put("visitId",inptVisitDTO.getId());
                    settleMap.put("insureSettleId",null);
                    settleMap.put("medicalRegNo",insureIndividualVisitDTO.getMedicalRegNo());
                    settleMap.put("insureIndividualCostDTO",insureIndividualCostDTO);
                    settleMap.put("hospCode",hospCode);
                    // 出院结算取消
                   Map<String,Object> reMap =  insureUnifiedPayInptService_consumer.UP_2305(insureUnifiedMap);
                   Map<String,Object> outptMap = MapUtils.get(reMap,"output");
                   Map<String,Object> setlInfoMap = MapUtils.get(outptMap,"setlinfo");
                   if(!MapUtils.isEmpty(setlInfoMap)){
                        insureIndividualVisitService.updateInsureSettleId(settleMap).getData(); //取消结算，更新医保就诊表的结算id
                        inptCostSettleDAO.updateInsureSettleCost(settleMap); // TR35H9  取消结算，更新医保费用表的结算id
                    }
                    InsureIndividualSettleDTO individualSettleDTO = new InsureIndividualSettleDTO();
                    individualSettleDTO.setId(insureSettleId);
                    individualSettleDTO.setHospCode(hospCode);
                    individualSettleDTO.setInsureSettleId(MapUtils.get(setlInfoMap,"setl_id"));
                    individualSettleDTO.setMedicalRegNo(MapUtils.get(setlInfoMap,"mdtrt_id"));
                    individualSettleDTO.setOmsgid(MapUtils.get(reMap,"msgId"));
                    individualSettleDTO.setOinfno(MapUtils.get(reMap,"infno"));
                    if(StringUtils.isNotEmpty(MapUtils.get(setlInfoMap,"clr_optins"))){
                        individualSettleDTO.setClrOptins(MapUtils.get(setlInfoMap,"clr_optins"));
                    }else{
                        individualSettleDTO.setClrOptins(selectEntity.getClrOptins());
                    }
                    if(StringUtils.isNotEmpty(MapUtils.get(setlInfoMap,"clr_way"))){
                        individualSettleDTO.setClrWay(MapUtils.get(setlInfoMap,"clr_way"));
                    }else {
                        individualSettleDTO.setClrWay(selectEntity.getClrWay());
                    }
                    if(StringUtils.isNotEmpty(MapUtils.get(setlInfoMap,"clr_type"))){
                        individualSettleDTO.setClrType(MapUtils.get(setlInfoMap,"clr_type"));
                    }else{
                        individualSettleDTO.setClrType(selectEntity.getClrType());
                    }
                    inptVisitDAO.updateInsureSettleById(individualSettleDTO);
                }
                else{
                    Map<String,Object> insureParam = new HashMap<String,Object>();
                    insureParam.put("hospCode",hospCode);//医院编码
                    insureParam.put("insureOrgCode",insureIndividualVisitDTO.getInsureOrgCode());//医保机构编码
                    insureParam.put("insureIndividualVisit",insureIndividualVisitDTO);
                    insureParam.put("userName",userName);//登录用户姓名
                    insureParam.put("userCode",userCode);//登录用户工号
                    insureParam.put("settleNo",inptSettleDO.getSettleNo());// 结算单号

                    if (Constants.BRLX.SNYDBR.equals(inptVisitDTO.getPatientCode()) || Constants.BRLX.SWYDBR.equals(inptVisitDTO.getPatientCode())){
                        //异地医保
                        InptService_consumer.udapteCanleRemoteInptSettle(insureParam);
                    } else {
                        //本地医保
                        InptService_consumer.udapteCanleInptSettle(insureParam);
                    }
                }
            }

            /**
             *   当取消结算的时候 出现医保和his单边账
             *   1.医保已经取消  而his没有取消结算 这个时候就需要调用单边账（取消结算单边功能）
             *   2.作废医保结算记录 置空医保结算id  把病人类型改完自费病人。his结算表变为自费病人
             *   3.再把患者变成自费病人结算，结算完成以后（需要判断医保是否有作废的数据）  再修改病人类型
             *   4.病人类型的值存在redis里面中（取消结算单边功能存入）
             */
            inptVisitDTO.setSettleId(settleId);
            List<InsureIndividualSettleDTO> settleDTOList =  inptSettleDAO.queryInptInsure(inptVisitDTO);
            String redisKey = new StringBuilder().append(hospCode).append("^").append(visitId).
                    append("2305").append("^").toString();
            if(ListUtils.isEmpty(settleDTOList)){
                String redisValue = redisUtils.get(redisKey);
                if(redisUtils.hasKey(redisKey) && StringUtils.isNotEmpty(redisValue)){
                    InptVisitDTO visitDTO = new InptVisitDTO();
                    visitDTO.setHospCode(hospCode);//医院编码
                    visitDTO.setId(visitId);//就诊id
                    visitDTO.setPatientCode(redisValue);
                    inptVisitDAO.updateInptVisit(visitDTO);
                    redisUtils.del(redisKey);
                }
            }
            return WrapperResponse.success("取消结算成功。",null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("取消结算失败:" + e.getMessage());
        }
    }

    /**
     * add by 廖继广 on 2020/11/04
     * 医保结算信息表数据冲红处理
     * @param selectEntity
     */
    private void insureIndividualSettleChangrRed(InsureIndividualSettleDTO selectEntity,String insureSettleId ) {
        // 原数据被冲红
        selectEntity.setState(Constants.ZTBZ.BCH);
        inptVisitDAO.updateInsureIndividualSettle(selectEntity);
        //清空医保费用结算id
        String settleId = selectEntity.getSettleId();//结算id
        inptVisitDAO.updateInsureIndividualCostBySettleId(settleId);
        // 冲红
        selectEntity.setState(Constants.ZTBZ.CH);
        selectEntity.setId(insureSettleId);
        selectEntity.setTotalPrice(BigDecimalUtils.negate(selectEntity.getTotalPrice()));
        selectEntity.setInsurePrice(BigDecimalUtils.negate(selectEntity.getInsurePrice()));
        selectEntity.setPlanPrice(BigDecimalUtils.negate(selectEntity.getPlanPrice()));
        selectEntity.setSeriousPrice(BigDecimalUtils.negate(selectEntity.getSeriousPrice()));
        selectEntity.setCivilPrice(BigDecimalUtils.negate(selectEntity.getCivilPrice()));
        selectEntity.setRetirePrice(BigDecimalUtils.negate(selectEntity.getRetirePrice()));
        selectEntity.setPersonalPrice(BigDecimalUtils.negate(selectEntity.getPersonalPrice()));
        selectEntity.setPersonPrice(BigDecimalUtils.negate(selectEntity.getPersonPrice()));
        selectEntity.setHospPrice(BigDecimalUtils.negate(selectEntity.getHospPrice()));
        selectEntity.setPlanAccountPrice(BigDecimalUtils.negate(selectEntity.getPlanAccountPrice()));
        selectEntity.setPortionPrice(BigDecimalUtils.negate(selectEntity.getPortionPrice()));
        selectEntity.setPsnPartAmt(BigDecimalUtils.negate(selectEntity.getPsnPartAmt()));// 个人负担总金额
        selectEntity.setLastSettle(BigDecimalUtils.negate(selectEntity.getLastSettle()));// 结算后余额
        selectEntity.setBeforeSettle(BigDecimalUtils.negate(selectEntity.getBeforeSettle()));// 结算后余额

        // 生育基金
        selectEntity.setFertilityPay(BigDecimalUtils.negate(selectEntity.getFertilityPay()));
        // 代缴基金（破产改制）
        selectEntity.setBehalfPay(BigDecimalUtils.negate(selectEntity.getBehalfPay()));
        // 居民家庭账户金
        selectEntity.setFamilyPay(BigDecimalUtils.negate(selectEntity.getFamilyPay()));
        // 新冠肺炎核酸检测财政补助
        selectEntity.setCOVIDPay(BigDecimalUtils.negate(selectEntity.getCOVIDPay()));
        // 公益补充保险基金
        selectEntity.setWelfarePay(BigDecimalUtils.negate(selectEntity.getWelfarePay()));
        // 军转干部医疗补助基金
        selectEntity.setSoldierToPay(BigDecimalUtils.negate(selectEntity.getSoldierToPay()));
        // 厅级干部补助基金
        selectEntity.setHallPay(BigDecimalUtils.negate(selectEntity.getHallPay()));
        //工伤保险基金
        selectEntity.setInjuryPay(BigDecimalUtils.negate(selectEntity.getInjuryPay()));
        // 离休老工人门慢保障基金
        selectEntity.setRetiredOutptPay(BigDecimalUtils.negate(selectEntity.getRetiredOutptPay()));
        // 一至六级残疾军人医疗补助基金
        selectEntity.setSoldierPay(BigDecimalUtils.negate(selectEntity.getSoldierPay()));
        // 离休人员医疗保障基金
        selectEntity.setRetiredPay(BigDecimalUtils.negate(selectEntity.getSoldierPay()));
        // 其他基金
        selectEntity.setOthPay(BigDecimalUtils.negate(selectEntity.getOthPay()));
        // 农村低收入人口医疗补充保险
        selectEntity.setLowInPay(BigDecimalUtils.negate(selectEntity.getLowInPay()));
        // 优抚对象医疗补助基金
        selectEntity.setCarePay(BigDecimalUtils.negate(selectEntity.getCarePay()));
        // 特惠保补偿金
        selectEntity.setThbPay(BigDecimalUtils.negate(selectEntity.getThbPay()));
        // 政府兜底基金
        selectEntity.setGovernmentPay(BigDecimalUtils.negate(selectEntity.getGovernmentPay()));
        // 居民意外伤害基金
        selectEntity.setRetAcctInjPay(BigDecimalUtils.negate(selectEntity.getRetAcctInjPay()));
        // 职工意外伤害基金
        selectEntity.setAcctInjPay(BigDecimalUtils.negate(selectEntity.getAcctInjPay()));
        // 个人账户共计支付金额
        selectEntity.setAcctMulaidPay(BigDecimalUtils.negate(selectEntity.getAcctMulaidPay()));
        // 符合政策范围金额
        selectEntity.setInscpScpAmt(BigDecimalUtils.negate(selectEntity.getInscpScpAmt()));
        // 先行自付金额
        selectEntity.setPreselfpayAmt(BigDecimalUtils.negate(selectEntity.getPreselfpayAmt()));
        // 超限价
        selectEntity.setOverSelfPrice(BigDecimalUtils.negate(selectEntity.getOverSelfPrice()));
        // 全自费金额
        selectEntity.setAllPortionPrice(BigDecimalUtils.negate(selectEntity.getAllPortionPrice()));
        // 企业支付
        selectEntity.setComPay(BigDecimalUtils.negate(selectEntity.getComPay()));
        // 医院减免金额
        selectEntity.setHospExemAmount(BigDecimalUtils.negate(selectEntity.getHospExemAmount()));
        //医疗救助金额
        selectEntity.setMafPay(BigDecimalUtils.negate(selectEntity.getMafPay()));
        // 伤残人员医疗保障基金
        selectEntity.setHifdmPay(BigDecimalUtils.negate(selectEntity.getHifdmPay()));
        inptVisitDAO.insertInsureIndividualSettle(selectEntity);
    }
}
