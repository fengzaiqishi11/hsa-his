package cn.hsa.insure.unifiedpay.bo.impl;

import cn.hsa.enums.HsaSrvEnum;
import cn.hsa.exception.BizExcCodes;
import cn.hsa.exception.BizRtException;
import cn.hsa.exception.InsureExecCodesEnum;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.enums.FunctionEnum;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.BaseReqUtilFactory;
import cn.hsa.insure.util.Constant;
import cn.hsa.insure.util.InsureUnifiedCommonUtil;
import cn.hsa.module.base.bi.dto.BaseItemDTO;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.doctor.entity.InptCostDO;
import cn.hsa.module.inpt.doctor.service.DoctorAdviceService;
import cn.hsa.module.inpt.inptnursethird.dto.InptNurseThirdDTO;
import cn.hsa.module.insure.inpt.bo.InsureUnifiedPayInptBO;
import cn.hsa.module.insure.inpt.service.InsureUnifiedBaseService;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualBasicDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualCostDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualVisitDAO;
import cn.hsa.module.insure.module.dto.*;
import cn.hsa.module.insure.module.entity.InsureIndividualCostDO;
import cn.hsa.module.insure.module.service.InsureUnifiedLogService;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Package_name: cn.hsa.insure.unifiedpay.bo.impl
 * @class_name: InsureUnifiedPayInptBOImpl
 * @Description: 医保统一支付平台：住院业务模块业务层实现类创建
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/2/10 9:10
 * @Company: 创智和宇
 **/
@Slf4j
@Component
public class InsureUnifiedPayInptBOImpl extends HsafBO implements InsureUnifiedPayInptBO {

    @Resource
    private InsureIndividualVisitDAO insureIndividualVisitDAO;

    @Resource
    private InsureIndividualCostDAO insureIndividualCostDAO;

    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;

    @Resource
    private InsureIndividualBasicDAO insureIndividualBasicDAO;
    @Resource
    private SysParameterService sysParameterService_consumer;

    @Resource
    private DoctorAdviceService doctorAdviceService_consumer;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private InsureUnifiedBaseService insureUnifiedBaseService;

    @Resource
    private InsureItfBOImpl insureItfBO;

    @Resource
    private BaseReqUtilFactory baseReqUtilFactory;

    @Resource
    private InsureUnifiedCommonUtil insureUnifiedCommonUtil;

    /**
     * @param map
     * @Method saveInptFeeTransmit
     * @Desrciption 医保统一支付平台：住院业务模块--住院费用传输 --入参
     *      1.如果医生开的项目全退：则不上传到医保，同时：把退费数据插入到insure_cost
     *      2.如果已经开的费用数据已经上传到医保：再次进行退费的话。则需要把退费的数量和正常的数据传入到医保
     *      3.如果本次的费用数据没有正常的状态，则不需调用医保的费用传输接口
     * @Param map
     * @Author fuhui
     * @Date 2021/2/10 9:52
     * @Return boolean
     */
    @Override
    public Map<String,Object> UP_2301(Map<String, Object> map) {
        InptVisitDTO inptVisitDTO = MapUtils.get(map,"inptVisitDTO");
        String code = MapUtils.get(map,"code");
        String crteName = MapUtils.get(map,"crteName");
        String hospCode =  MapUtils.get(map,"hospCode");
        String isHalfSettle = MapUtils.get(map,"isHalfSettle");
        String feeStartDate = MapUtils.get(map,"feeStartDate");
        String feeEndDate = MapUtils.get(map,"feeEndDate");
        Integer count = MapUtils.get(map,"count");
        InsureIndividualVisitDTO insureIndividualVisitDTO  = MapUtils.get(map,"insureIndividualVisitDTO");
        String insureRegCode = insureIndividualVisitDTO.getInsureOrgCode();
        String visitId = insureIndividualVisitDTO.getVisitId();
        map.put("visitId",visitId);
        map.put("insureIndividualVisitDTO",insureIndividualVisitDTO);

        // 查询是否有可传输费用
        Map<String, String> insureCostParam = new HashMap<String, String>();
        insureCostParam.put("hospCode", hospCode);//医院编码
        insureCostParam.put("statusCode", Constants.ZTBZ.ZC);//状态标志 = 正常
        insureCostParam.put("visitId", visitId);//就诊id
        insureCostParam.put("isMatch", Constants.SF.S);//是否匹配 = 是
        insureCostParam.put("transmitCode", Constants.SF.F);//传输标志 = 未传输
        insureCostParam.put("insureRegCode", insureRegCode);// 医保机构编码
        insureCostParam.put("queryBaby", MapUtils.get(map,"queryBaby"));// 医保机构编码
        insureCostParam.put("isHalfSettle", isHalfSettle);// 是否中途结算
        insureCostParam.put("feeStartDate", feeStartDate);
        insureCostParam.put("feeEndDate", feeEndDate);// 是否中途结算
        List<Map<String,Object>> insureCostList =  insureIndividualCostDAO.queryInsureCostByVisit(insureCostParam);
        if(ListUtils.isEmpty(insureCostList)) {
            throw new BizRtException(InsureExecCodesEnum.IN_HOSP_FEE_DATA_EMPTY,new Object[]{HsaSrvEnum.HSA_INSURE.getDesc(),visitId});
        }

        // 查询有退费且未上传的的费数据
        List<InptCostDTO> inptCostDTOList = insureIndividualCostDAO.queryBackInptFee(insureIndividualVisitDTO);

        // 查询已经上传的费用数据
        List<Map<String, Object>> individualCostDTOList = insureIndividualCostDAO.queryInsureInptCost(insureIndividualVisitDTO);

        // 本次上传如果没有正常的费用数据,则不上传到医保，直接把退费的数据插入到医保费用表即可
        int num = 0;
        if (!ListUtils.isEmpty(insureCostList)) {
            for(Map<String,Object> item : insureCostList) {
                String statusCode = MapUtils.get(item,"statusCode");
                if(Constants.ZTBZ.ZC.equals(statusCode)) {
                    num ++;
                }
            }
        }

        // 直接把全开，全退的费用保存到费用表 但是不进行调用医保的操作
        if (num == 0) {
            this.insertNotUpLoadFee(insureCostList,inptVisitDTO);
            return map;
        }
        List<Map<String,Object>> list1 = new ArrayList<>();
        List<Map<String,Object>> list2 = new ArrayList<>();
        List<Map<String,Object>> list3 = new ArrayList<>(); // 处理正负直接相抵的集合
        /**
         * 如果存在退费
         * 1.开5个  已经上传医保  退了3  下次需要上传  -5  +3
         * 2.开5个  没有上传医保  退了3  下次需要上传  +2
         */

        if(!ListUtils.isEmpty(inptCostDTOList)){
            if(!ListUtils.isEmpty(individualCostDTOList)){
                list2.addAll(insureCostList);
            }else{
                Map<String, InptCostDTO> collect = inptCostDTOList.stream().collect(Collectors.toMap(InptCostDTO::getOldCostId, Function.identity()));
                // 传正常的数据    假如最原始已经上传 10条  退4条     第二次传输 则  传-10  正6
                for(Map<String,Object> item : insureCostList){
                    if(!MapUtils.isEmpty(collect) && collect.containsKey(MapUtils.get(item,"id"))){
                        list3.add(item);
                        continue;
                    }
                    else if(!MapUtils.isEmpty(collect) && collect.containsKey(MapUtils.get(item,"oldCostId")) &&
                            BigDecimalUtils.less(MapUtils.get(item,"totalNum"),new BigDecimal(0.00))){
                        list3.add(item);
                        continue;
                    }
                    else {
                        list1.add(item);
                    }
                }
            }

            if(!ListUtils.isEmpty(list3)){
                insertNotUpLoadFee(list3,inptVisitDTO);
            }
            list2.addAll(list1);
        }
        // 说明没有退费数据
        else{
            list2.addAll(insureCostList);
        }
        List<InsureIndividualCostDTO> individualCostDTOS = insureIndividualCostDAO.queryInptFeeCost(map);
        Map<String, InsureIndividualCostDTO> inptCostDOMap = individualCostDTOS.stream().collect(Collectors.toMap(InsureIndividualCostDTO::getCostId,
                Function.identity(), (k1, k2) -> k1));

        Boolean isCompound = false;
        for(Map<String, Object> item : list2){
            if(BigDecimalUtils.lessZero((BigDecimal)item.get("totalNum")) &&
                    inptCostDOMap.containsKey(MapUtils.get(item,"oldCostId"))){
                item.put("initFeedetlSn",inptCostDOMap.get(MapUtils.get(item,"oldCostId")).getFeedetlSn());
            }

            if ("103".equals(MapUtils.get(item,"insureItemType")) && "1".equals(MapUtils.get(item,"tcmdrugUsedWay"))) {
                isCompound = true;
            }
        }

        String bka006 = insureIndividualVisitDTO.getBka006(); //待遇类型
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setOrgCode(insureIndividualVisitDTO.getMedicineOrgCode());  // 医疗机构编码
        insureConfigurationDTO.setCode(insureIndividualVisitDTO.getInsureRegCode()); // 医保机构编码
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        if (ObjectUtil.isEmpty(insureConfigurationDTO)) {
            throw new AppException("未获取到医保配置信息,请检查！hospCode:"+hospCode+",orgCode:"+insureIndividualVisitDTO.getMedicineOrgCode()+",code:"+insureIndividualVisitDTO.getInsureRegCode());
        }
        /**
         * 公共入参
         */
        Map param = new HashMap();
        param.put("infno", "2301");  //交易编号
        param.put("msgid",StringUtils.createMsgId(insureIndividualVisitDTO.getMedicineOrgCode()));
        param.put("insuplc_admdvs", insureIndividualVisitDTO.getInsuplcAdmdvs()); //参保地医保区划分
        param.put("medins_code", insureIndividualVisitDTO.getMedicineOrgCode()); //定点医药机构编号
        param.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        param.put("mdtrtarea_admvs",insureConfigurationDTO.getMdtrtareaAdmvs());// 就医地医保区划
        /**
         * 第一部分入参
         */
        Map<String,String> dataMap = new HashMap<>();
        String insureRegisterNo = insureIndividualVisitDTO.getMedicalRegNo();
        map.put("medicalRegNo",insureRegisterNo);
        dataMap.put("medins_code",insureIndividualVisitDTO.getMedicineOrgCode()); // 医疗机构编码
        dataMap.put("psn_no",insureIndividualVisitDTO.getAac001()); //个人电脑号
        dataMap.put("med_type",insureIndividualVisitDTO.getAka130()); //业务类型
        dataMap.put("serial_no",insureRegisterNo); // 就医登记号
        dataMap.put("opter",code); // 录入人工号
        dataMap.put("opter_name",crteName); // 录入人姓名
        dataMap.put("rxno ",""); //处方号
        dataMap.put("bilg_dr_code",""); // 处方医生编号
        dataMap.put("bilg_dr_name",""); // 处方医生姓名
        map.put("code","HOSP_APPR_FLAG");
        map.put("insureRegisterNo",insureRegisterNo);
        String hospApprFlag ="";
        String zhSpecial ="";
        String hnFeedetlSn ="";
        String huNanSpecial = "";
        String guangZhouSpecial = "";
        Map<String,Object> codeMap = new HashMap<>();
        codeMap.put("code","HOSP_APPR_FLAG");
        codeMap.put("hospCode",hospCode);
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(codeMap).getData();
        if(sysParameterDTO !=null && !StringUtils.isEmpty(sysParameterDTO.getValue())){
            String value = sysParameterDTO.getValue();
            Map<String,Object> stringObjectMap = JSON.parseObject(value,Map.class);
            if(stringObjectMap.containsKey(insureConfigurationDTO.getRegCode())){
                for(String key : stringObjectMap.keySet()){
                    if(key.equals(insureConfigurationDTO.getRegCode())){ // 审批标识
                        hospApprFlag = MapUtils.get(stringObjectMap,key);
                    }
                    if("zhSpecial".equals(key)){  // 珠海临时控制自费药的处理办法
                        zhSpecial = MapUtils.get(stringObjectMap,key);
                    }
                    if("hnFeedetlSn".equals(key)){ // 湖南省特有的费流水号标识
                        hnFeedetlSn = MapUtils.get(stringObjectMap,key);
                    }
                    if ("huNanSpecial".equals(key)) {
                        huNanSpecial = MapUtils.get(stringObjectMap,key);
                    }
                    if ("guangZhouSpecial".equals(key)) {
                        guangZhouSpecial = MapUtils.get(stringObjectMap,key);
                    }
                }
            }
        }
        Integer k =1;
        /**
         * 第二部分入参
         */
        List<Map<String,Object>> mapList = new ArrayList<>();
        BigDecimal sumBigDecimal = new BigDecimal(0.00);
        String feeNum =  insureIndividualCostDAO.selectLastFeedSn(map); // 判读是否是第一次传输
        for (Map<String, Object> item : list2) {
            Map<String, Object> objectMap = new HashMap<>();
            String pracCertiNo = MapUtils.get(item,"pracCertiNo");
            String doctorName = MapUtils.get(item,"doctorName");
            if(StringUtils.isEmpty(pracCertiNo)){
                throw new AppException("该" +doctorName+"医生的医师国家码没有维护,请去用户管理里面维护");
            }
            count++;
            String feedetlSn = MapUtils.get(item,"id");
            // 湖南省医保费用流水只能传15位  且费用明细流水号必须为非0的数字
            if("1".equals(hnFeedetlSn)){
                if(StringUtils.isEmpty(feeNum)){
                    objectMap.put("feedetl_sn",k) ; // 费用明细流水号
                }
                else{
                    k = Integer.parseInt(feeNum);
                    k+=2;
                    feeNum = k.toString();
                    objectMap.put("feedetl_sn",k) ; // 费用明细流水号
                }
                if(BigDecimalUtils.lessZero((BigDecimal)item.get("totalNum"))){
                    if(MapUtils.isEmpty(item,"initFeedetlSn")){
                        objectMap.put("init_feedetl_sn","") ;// 原费用流水号
                    }else {
                        objectMap.put("init_feedetl_sn",Integer.valueOf(MapUtils.get(item,"initFeedetlSn"))) ;// 原费用流水号
                    }
                }
                else{
                    objectMap.put("init_feedetl_sn","") ;// 原费用流水号
                }
            }
            else{
                feedetlSn = feedetlSn.substring(1,19);
                objectMap.put("feedetl_sn",feedetlSn) ; // 费用明细流水号
                if(BigDecimalUtils.lessZero((BigDecimal)item.get("totalNum"))){
                    objectMap.put("init_feedetl_sn",MapUtils.get(item,"initFeedetlSn")) ;// 原费用流水号
                }
                else{
                    objectMap.put("init_feedetl_sn","") ;// 原费用流水号
                }
            }
            k=k+2;
            objectMap.put("mdtrt_id",insureIndividualVisitDTO.getMedicalRegNo());  // 就诊ID
            objectMap.put("drord_no",MapUtils.get(item,"iatId"));  // 医嘱号
            objectMap.put("psn_no",insureIndividualVisitDTO.getAac001()); // 人员编号
            objectMap.put("med_type",insureIndividualVisitDTO.getAka130()); // 医疗类别 - - 业务类别
            objectMap.put("fee_ocur_time",DateUtils.format((Date) item.get("costTime"),DateUtils.Y_M_DH_M_S));// 费用发生时间
            objectMap.put("med_list_codg",item.get("insureItemCode") ==null?"":item.get("insureItemCode").toString());// 医疗目录编码
            objectMap.put("medins_list_codg",item.get("hospItemCode")==null?"":item.get("hospItemCode").toString()); // 医药机构目录编码
            objectMap.put("med_list_name", MapUtils.get(item,"insureItemName"));
            BigDecimal cnt = BigDecimalUtils.scale((BigDecimal) item.get("totalNum"), 4);
            objectMap.put("cnt",cnt);//  数量
            BigDecimal price = BigDecimalUtils.scale((BigDecimal) item.get("price"), 4);
            objectMap.put("pric",price);// 单价
            DecimalFormat df1 = new DecimalFormat("0.00");
            String realityPrice = df1.format(BigDecimalUtils.convert(item.get("realityPrice").toString()));
            BigDecimal convertPrice = BigDecimalUtils.convert(realityPrice);
            sumBigDecimal=BigDecimalUtils.add(sumBigDecimal,convertPrice);
            objectMap.put("det_item_fee_sumamt",convertPrice); // 明细项目费用总额
            objectMap.put("bilg_dept_codg",MapUtils.get(item,"deptId")); // 开单科室编码
            objectMap.put("bilg_dept_name",MapUtils.get(item,"deptName")); // 开单科室名称

            if(StringUtils.isEmpty(MapUtils.get(item,"pracCertiNo"))){
                objectMap.put("bilg_dr_codg",pracCertiNo); // 开单医生编码
            }else{
                objectMap.put("bilg_dr_codg",MapUtils.get(item,"pracCertiNo")); // 开单医生编码
            }
            if(StringUtils.isEmpty(MapUtils.get(item,"doctorName"))){
                objectMap.put("bilg_dr_name",doctorName); // 开单医师姓名
            }else {
                objectMap.put("bilg_dr_name",MapUtils.get(item,"doctorName")); // 开单医师姓名
            }
            objectMap.put("acord_dept_codg",""); // 受单科室编码
            objectMap.put("acord_dept_name",""); // 受单科室名称
            objectMap.put("orders_dr_code",MapUtil.getStr(objectMap,"bilg_dr_codg")); // 受单医生编码
            objectMap.put("orders_dr_name",MapUtil.getStr(objectMap,"bilg_dr_name")); // 受单医生姓名
            String isReimburse = MapUtils.get(item,"isReimburse");

            /**
             * zhSpecial : '1' 珠海
             * huNanSpecial : '1' 湖南
             */
            String insureItemType = MapUtils.get(item,"insureItemType");
            String lmtUserFlag = MapUtils.get(item,"lmtUserFlag");// 是否限制级用药
            //如果当前项目为限制用药，医院审批标志取isReimburse的值，否则取配置的hospApprFlag值
            if ("1".equals(lmtUserFlag)) {
                if (ObjectUtil.isEmpty(isReimburse)) {
                    throw new RuntimeException("【"+MapUtil.getStr(item,"hospItemName")+"】为限制用药，是否报销标志【isReimburse】不能为空，请检查！费用id为："+feedetlSn);
                }
                objectMap.put("hosp_appr_flag", isReimburse); // 医院审批标志
            }else {
                objectMap.put("hosp_appr_flag", hospApprFlag); // 医院审批标志
            }
            // 珠海 + （药品和材料） + 限制级用药标志为 0 ，hosp_appr_flag则使用 0
            if("1".equals(zhSpecial) && "0".equals(isReimburse)) {
                objectMap.put("hosp_appr_flag", "0");
            }

            // 湖南省医保中药饮片中出现了复方药物，则中药饮片全部报销,单方为不报销（103-中药饮片）
            if (isCompound && "1".equals(huNanSpecial) && Constant.UnifiedPay.DOWNLOADTYPE.ZYYP.equals(insureItemType)) {
                objectMap.put("hosp_appr_flag", "1");
                objectMap.put("tcmdrug_used_way","1");
            } else if (!isCompound && "1".equals(huNanSpecial) && Constant.UnifiedPay.DOWNLOADTYPE.ZYYP.equals(insureItemType)) {
                objectMap.put("hosp_appr_flag", "0");
                objectMap.put("tcmdrug_used_way","2");
            } else if("1".equals(huNanSpecial) && "0".equals(isReimburse)) {
                objectMap.put("hosp_appr_flag", "0"); //
            } else if ("1".equals(guangZhouSpecial) && isCompound && Constant.UnifiedPay.DOWNLOADTYPE.ZCY.equals(insureItemType)) {
                // 广州的102是中药饮片
                objectMap.put("hosp_appr_flag", "1");
                objectMap.put("tcmdrug_used_way","1");
            }

            // 湖南省 hosp_appr_flag 用法接口新加 西药中成药 + 湖南 + 限制级
            // 当药品本身是限制用药时，医院审批标志传0走住院自付比例，传1时走门诊自付比例
            if (Constants.SF.S.equals(huNanSpecial) && Constants.SF.S.equals(lmtUserFlag) &&
                    (Constant.UnifiedPay.DOWNLOADTYPE.XY.equals(insureItemType) || Constant.UnifiedPay.DOWNLOADTYPE.ZCY.equals(insureItemType))) {
                if (ObjectUtil.isEmpty(isReimburse)) {
                    throw new RuntimeException("【"+MapUtil.getStr(item,"hospItemName")+"】为限制使用项目，其是否报销标志【isReimburse】不能为空，请检查！费用id为："+feedetlSn);
                }
                switch (isReimburse) {
                    case Constants.SF.S:
                        objectMap.put("hosp_appr_flag", "0");
                        break;
                    case Constants.SF.F:
                        objectMap.put("hosp_appr_flag", "2");
                        break;
                    default:
                        break;
                }
            }
            //广东省接口医院审批标志只认1和2，【1：审批通过，2审批不通过】，这里做一下转换
            if ("440".startsWith(insureConfigurationDTO.getRegCode())) {
                if ("0".equals(MapUtil.getStr(objectMap, "hosp_appr_flag"))) {
                    objectMap.put("hosp_appr_flag","2");
                }
            }

            objectMap.put("etip_flag",Constants.SF.F); // 外检标志
            objectMap.put("etip_hosp_code",""); // 外检医院编码
            // 生育住院 521  128 -生育平产(居民) 129生育剖宫产(居民)
            if("128".equals(bka006) || "129".equals(bka006) || "521".equals(bka006)){
                objectMap.put("matn_fee_flag",Constants.SF.S); // 生育费用标志
            }else{
                objectMap.put("matn_fee_flag",Constants.SF.F); // 生育费用标志
            }
            objectMap.put("memo",""); // 备注
            objectMap.put("lmtUserFlag",MapUtils.get(item,"lmtUserFlag")); // 项目药品类型
            objectMap.put("list_type",MapUtils.get(item,"insureItemType")); // 项目药品类型
            objectMap.put("medins_list_code",item.get("hospItemCode")==null?"":item.get("hospItemCode").toString()); // 医院药品项目编码
            objectMap.put("medins_list_name",item.get("hospItemName")==null?"":item.get("hospItemName").toString()); // 医院药品项目名称
            objectMap.put("drug_stand_code",MapUtils.get(item,"standardCode"));// 药品本位码
            objectMap.put("drug_dosform",MapUtils.get(item,"prepCode"));  // 剂型
            objectMap.put("prdr_name",""); // 厂家
            objectMap.put("spec",MapUtils.get(item,"spec")); // 规格
            objectMap.put("sin_dos_dscr",MapUtils.get(item,"dosageUnitCode"));// 计量单位
            if(Constants.YYXZ.CYDY.equals(MapUtils.get(item,"useCode"))){
                objectMap.put("dscg_tkdrug_flag",Constants.SF.S); // 出院带药标志
                objectMap.put("dscg_tkdrug_days",MapUtils.get(item,"useDays"));//出院带药天数
            }else{
                objectMap.put("dscg_tkdrug_flag",Constants.SF.F); // 出院带药标志
                objectMap.put("dscg_tkdrug_days","");//出院带药天数
            }
            objectMap.put("used_flag","");//用药标志
            objectMap.put("opp_serial_fee",count + "");// 对应费用序列号
            objectMap.put("opter",code);// 录入人工号
            objectMap.put("opter_name",crteName);// 录入人姓名
            objectMap.put("rxno",""); // 处方号
            objectMap.put("feedetl_id",MapUtils.get(item,"id")); // 人员医疗费用明细ID
            objectMap.put("chrgitm_lv",""); // 收费项目等级
            objectMap.put("selt_date",""); //医患最终结算日期
            objectMap.put("med_list_code",item.get("insureItemCode") ==null?"":item.get("insureItemCode").toString()); // 中心药品项目编码
            objectMap.put("med_list_name",item.get("insureItemName")==null?"": item.get("insureItemName").toString());// 中心药品项目名称
            objectMap.put("org_drug_code","");// 药监局药品编码
            objectMap.put("fee_bchno","");// 费用批次
            objectMap.put("refd_amt","");// 退费金额
            objectMap.put("opt_time",DateUtils.format((Date) item.get("crteTime"),DateUtils.Y_M_DH_M_S));// 录入时间
            objectMap.put("fund_stas","");// 费用冻结标志，用来表识参保人所在单位的基本医疗保险被冻结期间录入的费用。0：未冻结；1：已冻结；2：冻结已处理
            objectMap.put("upload_time",DateUtils.format(DateUtils.getNow(),DateUtils.Y_M_DH_M_S));; // 费用上传时间
            objectMap.put("city_selfpay_prop",""); //  城职对应待遇值（自付比例支付类型）
            objectMap.put("work_stats","");// 是否在岗医师标识：0，非在岗；1，在岗
            objectMap.put("out_time_flag","");// 超时标志，0未超时，1超时上传未申诉，2超时上传正在申诉，3超时上传申诉审核同意，4超时上传申诉审核不同意
            objectMap.put("frqu_dscr","");//用药频次描述
            objectMap.put("prd_days",""); // 用药周期天数
            objectMap.put("medc_way_dscr",""); // 用药途径描述
            objectMap.put("sin_dos","");//单次剂量
            objectMap.put("sin_dosunt","");//单次剂量单位
            objectMap.put("used_days",""); // 使用天数
            objectMap.put("dismed_amt","");// 发药总量
            objectMap.put("dismed_unt","");// 发药总量单位
            objectMap.put("unchk_flag","");// 不进行审核标志
            objectMap.put("unchk_memo",""); // 不进行审核说明
            objectMap.put("visitId",visitId);
            objectMap.put("hospCode",hospCode);
            objectMap.put("id",MapUtils.get(item,"id")) ; //
            mapList.add(objectMap);
        }
        System.out.println("费用明细总金额为:"+ sumBigDecimal);
        System.out.println("费用传输的总数量为:" + mapList.size());
        map.put("mapList",mapList);
        String resultStr ="";
        String url= insureConfigurationDTO.getUrl();
        Map<String,Object> inputMap = new HashMap<>();
        List<Map<String,Object>> listAllMap = new ArrayList<>();
        if(mapList.size() > 100){
            int toIndex =100;
            for (int i = 0; i < mapList.size(); i += 100) {
                if (i + 100 > mapList.size()) {
                    toIndex = mapList.size() - i;
                }
                List newList = mapList.subList(i, i + toIndex);
                inputMap.put("data", dataMap);
                inputMap.put("feedetail",newList);
                param.put("input", inputMap);
                String json = JSONObject.toJSONString(param);
                logger.info("统一支付平台住院费用传输入参:" + json);
                resultStr = HttpConnectUtil.unifiedPayPostUtil(url, json);
                if(StringUtils.isEmpty(resultStr)){
                    throw new AppException("无法访问统一支付平台");
                }
                Map<String, Object> resultMap = JSONObject.parseObject(resultStr,Map.class);
                if ("999".equals(MapUtils.get(resultMap,"code"))) {
                    throw new AppException((String) resultMap.get("msg"));
                }
                if (!MapUtils.get(resultMap,"infcode").equals("0")) {
                    throw new AppException((String) resultMap.get("err_msg"));
                }
                logger.info("统一支付平台住院费用传输回参:" + resultStr);
                Map<String,Object> outptMap = MapUtils.get(resultMap,"output");
                List<Map<String,Object>> resultDataMap =  MapUtils.get(outptMap,"result");
                listAllMap.addAll(resultDataMap);
                insertInsureCost(resultDataMap,map, sumBigDecimal);
            }
        }
        else{
            inputMap.put("data", dataMap);
            inputMap.put("feedetail",mapList);
            param.put("input", inputMap);
            String json = JSONObject.toJSONString(param);
            logger.info("统一支付平台住院费用传输入参:" + json);
            resultStr = HttpConnectUtil.unifiedPayPostUtil(url, json);
            if(StringUtils.isEmpty(resultStr)){
                throw new AppException("无法访问统一支付平台");
            }
            Map<String, Object> resultMap = JSONObject.parseObject(resultStr,Map.class);
            if ("999".equals(MapUtils.get(resultMap,"code"))) {
                throw new AppException((String) resultMap.get("msg"));
            }
            if (!MapUtils.get(resultMap,"infcode").equals("0")) {
                throw new AppException((String) resultMap.get("err_msg"));
            }
            logger.info("统一支付平台住院费用传输回参:" + resultStr);
            Map<String,Object> outptMap = MapUtils.get(resultMap,"output");
            List<Map<String,Object>> resultDataMap =  MapUtils.get(outptMap,"result");
            listAllMap.addAll(resultDataMap);
            insertInsureCost(resultDataMap,map, sumBigDecimal);
        }
        map.put("sumBigDecimal",sumBigDecimal);
        map.put("listAllMap",listAllMap);
        return map;


    }
    /**
     * @Method updateInsureCost
     * @Desrciption  费用传输以后：更新医保的反参数据
     * @Param
     *
     * @Author fuhui
     * @Date   2021/5/21 8:35
     * @Return
     **/
    private void insertInsureCost(List<Map<String, Object>> resultDataMap, Map<String, Object> unifiedPayMap, BigDecimal sumBigDecimal) {

        String hospCode  = MapUtils.get(unifiedPayMap,"hospCode");
        String visitId = MapUtils.get(unifiedPayMap,"visitId");
        String isHalfSettle = MapUtils.get(unifiedPayMap,"isHalfSettle");
        String insureRegisterNo = MapUtils.get(unifiedPayMap,"insureRegisterNo");
        String startDate = MapUtils.get(unifiedPayMap,"feeStartDate");
        String endDate = MapUtils.get(unifiedPayMap,"feeEndDate");
        String crteName = MapUtils.get(unifiedPayMap,"crteName");
        String crteId  = MapUtils.get(unifiedPayMap,"crteId");
        InsureIndividualCostDTO insureIndividualCostDTO = null;
        List<InsureIndividualCostDTO> individualCostDTOList = new ArrayList<>();
        /**
         *   获取最新的的顺序号
         */
        int count ;
        InptVisitDTO inptVisitDTO = new InptVisitDTO();
        inptVisitDTO.setHospCode(hospCode);
        inptVisitDTO.setId(visitId);
        Integer settleCount = 0; // 保存唯一患者的中途结算次数
        if("1".equals(isHalfSettle)){
            String settleCountKey = new StringBuffer().append(hospCode).append(":").append(visitId).append(":").
                    append(insureRegisterNo).toString();
            long incr = redisUtils.incr(settleCountKey, 1);
            settleCount = (int) incr;
        }
        String orderNo = insureIndividualCostDAO.queryLastestOrderNo(inptVisitDTO);
        if (StringUtils.isEmpty(orderNo)) {
            count = 0;
        } else {
            count = Integer.parseInt(orderNo);
        }
        List<Map<String,Object>> mapList = MapUtils.get(unifiedPayMap,"mapList");
        if(!ListUtils.isEmpty(mapList)){
            Map<Object, Map<String, Object>> feedetlSnMap = mapList.stream().collect(Collectors.toMap(Map -> Map.get("feedetl_sn"), Map -> Map, (k1, k2) -> k1));
            Map<Object, Map<String, Object>> newFeedetlSnMap = new HashMap<>();
            if(!MapUtils.isEmpty(feedetlSnMap)){
                for(Map.Entry entry : feedetlSnMap.entrySet()){
                    newFeedetlSnMap.put(entry.getKey().toString(), (Map<String, Object>) entry.getValue());
                }
                if(!ListUtils.isEmpty(resultDataMap)){
                    for(Map<String, Object> item : resultDataMap){
                        DecimalFormat df1 = new DecimalFormat("0.00");
                        String feedetlSn = MapUtils.get(item,"feedetl_sn");
                        Map<String, Object> feedetlSnObjectMap = newFeedetlSnMap.get(feedetlSn);
                        insureIndividualCostDTO = new InsureIndividualCostDTO();
                        insureIndividualCostDTO.setId(SnowflakeUtils.getId());
                        insureIndividualCostDTO.setHospCode(hospCode);
                        insureIndividualCostDTO.setVisitId(visitId);//就诊id
                        insureIndividualCostDTO.setSettleId(null);
                        insureIndividualCostDTO.setIsHospital("1");
                        insureIndividualCostDTO.setItemType(MapUtils.get(feedetlSnObjectMap,"list_type"));
                        insureIndividualCostDTO.setItemCode(MapUtils.get(feedetlSnObjectMap,"med_list_codg"));
                        insureIndividualCostDTO.setItemName(MapUtils.get(feedetlSnObjectMap,"med_list_name"));
                        insureIndividualCostDTO.setCostId(MapUtils.get(feedetlSnObjectMap,"id"));//费用id
                        insureIndividualCostDTO.setFeedetlSn(MapUtils.get(item,"feedetl_sn").toString()); // 费用明细流水号(上传到医保)
                        insureIndividualCostDTO.setGuestRatio(MapUtils.get(item, "selfpay_prop").toString()); // 自付比例
                        insureIndividualCostDTO.setPrimaryPrice(BigDecimalUtils.convert(newFeedetlSnMap.get(MapUtils.get(item,"feedetl_sn")).get("det_item_fee_sumamt").toString())); // 上传到医保的费用
                        insureIndividualCostDTO.setApplyLastPrice(null);
                        insureIndividualCostDTO.setOrderNo(count +""); // 顺序号
                        insureIndividualCostDTO.setTransmitCode("1"); // 是否传输标志
                        insureIndividualCostDTO.setCrteId(crteId);
                        insureIndividualCostDTO.setRxSn(null);
                        insureIndividualCostDTO.setInsureSettleId(null);
                        insureIndividualCostDTO.setCrteName(crteName);
                        insureIndividualCostDTO.setCrteTime(DateUtils.getNow());
                        insureIndividualCostDTO.setHospCode(hospCode);  // 医院编码
                        insureIndividualCostDTO.setInsureRegisterNo(insureRegisterNo); // 就医登记号
                        insureIndividualCostDTO.setIsHalfSettle(isHalfSettle); // 是否中途结算
                        insureIndividualCostDTO.setInsureIsTransmit("1");
                        insureIndividualCostDTO.setBatchNo(null);
                        insureIndividualCostDTO.setPricUplmtAmt(null);
                        insureIndividualCostDTO.setHiLmtpric(null);
                        insureIndividualCostDTO.setOverlmtSelfpay(null);
                        insureIndividualCostDTO.setInscpScpAmt(BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "inscp_scp_amt").toString())))); // 符合政策范围金额
                        insureIndividualCostDTO.setFulamtOwnpayAmt(BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "overlmt_selfpay")==null ?"": MapUtils.get(item, "overlmt_selfpay").toString()))));
                        insureIndividualCostDTO.setOverlmtAmt(BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "overlmt_amt") == null ? "":MapUtils.get(item, "overlmt_amt").toString()))));
                        insureIndividualCostDTO.setPreselfpayAmt(BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "preselfpay_amt") ==null ? "" :MapUtils.get(item, "preselfpay_amt").toString())))); // 先行自付金额
                        insureIndividualCostDTO.setInscpScpAmt(BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "inscp_scp_amt") ==null ? "":MapUtils.get(item, "inscp_scp_amt").toString()))));
                        insureIndividualCostDTO.setFulamtOwnpayAmt(BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "fulamt_ownpay_amt") ==null ? "":MapUtils.get(item, "fulamt_ownpay_amt") .toString())))); // 全自费金额
                        insureIndividualCostDTO.setDetItemFeeSumamt(BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "det_item_fee_sumamt") ==null ? "":MapUtils.get(item, "det_item_fee_sumamt") .toString())))); // 总金额
                        insureIndividualCostDTO.setSumBigDecimalFee(sumBigDecimal);
                        insureIndividualCostDTO.setMedChrgitmType(MapUtils.get(item,"med_chrgitm_type")); //医疗收费项目类别
                        insureIndividualCostDTO.setLmtUsedFlag(MapUtils.get(item,"lmt_used_flag"));
                        insureIndividualCostDTO.setChrgItemLv(MapUtils.get(item,"chrgitm_lv"));
                        insureIndividualCostDTO.setSumFee(sumBigDecimal); // 本次上传到医保的费用总金额
                        insureIndividualCostDTO.setIsHalfSettle(isHalfSettle); // 是否中途结算
                        if(StringUtils.isNotEmpty(startDate)){
                            insureIndividualCostDTO.setFeeStartTime(DateUtils.parse(startDate,DateUtils.Y_M_D));
                        }else{
                            insureIndividualCostDTO.setFeeStartTime(null);
                        }
                        if(StringUtils.isNotEmpty(endDate)){
                            insureIndividualCostDTO.setFeeEndTime(DateUtils.parse(endDate,DateUtils.Y_M_D));
                        }else {
                            insureIndividualCostDTO.setFeeEndTime(null);
                        }
                        if(settleCount<=0){
                            insureIndividualCostDTO.setSettleCount(1);
                        }else{
                            insureIndividualCostDTO.setSettleCount(settleCount);
                        }
                        individualCostDTOList.add(insureIndividualCostDTO);
                    }
                }
            }
        }
        if(!ListUtils.isEmpty(individualCostDTOList)){
            insureIndividualCostDAO.batchInsertCost(individualCostDTOList);
        }
        // 如果在费用传输的时候 选择了中途结算，则更新医保就诊表里面的结算次数,同时更新是否结算标志
        if("1".equals(isHalfSettle)){
            inptVisitDTO.setMedicalRegNo(insureRegisterNo);
            inptVisitDTO.setIsHalfSettle(isHalfSettle);
            inptVisitDTO.setSettleCount(settleCount);
            insureIndividualVisitDAO.updateInsureInidivdual(inptVisitDTO);
        }
    }

    /**
     * @Method
     * @Desrciption  保存未上传的费用数据
     * @Param  insureCostList：未上传的费用集合
     *          inptVisitDTO ：患者基本信息
     *
     * @Author fuhui
     * @Date   2021/8/13 9:16
     * @Return
    **/
    private void insertNotUpLoadFee(List<Map<String, Object>> insureCostList, InptVisitDTO inptVisitDTO) {
        /**
         *   获取最新的的顺序号
         */
        int count;
        String orderNo = insureIndividualCostDAO.queryLastestOrderNo(inptVisitDTO);
        if (StringUtils.isEmpty(orderNo)) {
            count = 0;
        } else {
            count = Integer.parseInt(orderNo);
        }
        List<InsureIndividualCostDO> insureIndividualCostDOList = new ArrayList<InsureIndividualCostDO>();
        String hospCode = inptVisitDTO.getHospCode();
        String visitId = inptVisitDTO.getId();
        String crteId = inptVisitDTO.getCrteId();
        String crteName = inptVisitDTO.getCrteName();
        for (Map<String,Object> item : insureCostList){
            count++;
            InsureIndividualCostDO insureIndividualCostDO = new InsureIndividualCostDO();
            insureIndividualCostDO.setId(SnowflakeUtils.getId());//id
            insureIndividualCostDO.setHospCode(hospCode);//医院编码
            insureIndividualCostDO.setVisitId(visitId);//患者id
            insureIndividualCostDO.setCostId((String) item.get("id"));//费用id
            insureIndividualCostDO.setSettleId(null);//结算id
            insureIndividualCostDO.setIsHospital(Constants.SF.S);//是否住院 = 是
            insureIndividualCostDO.setItemType((String) item.get("insureItemType"));//医保项目类别
            insureIndividualCostDO.setItemCode((String) item.get("insureItemCode"));//医保项目编码
            insureIndividualCostDO.setItemName((String) item.get("insureItemName"));//医保项目名称
            insureIndividualCostDO.setGuestRatio((String)item.get("deductible"));//自付比例
            insureIndividualCostDO.setPrimaryPrice((BigDecimal)item.get("realityPrice"));//原费用
            insureIndividualCostDO.setApplyLastPrice(null);//报销后费用
            insureIndividualCostDO.setOrderNo(count+"");//顺序号
            insureIndividualCostDO.setInsureIsTransmit(Constants.SF.F);
            insureIndividualCostDO.setTransmitCode(Constants.SF.S);//传输标志 = 已传输
            insureIndividualCostDO.setCrteId(crteId);//创建id
            insureIndividualCostDO.setCrteName(crteName);//创建人姓名
            insureIndividualCostDO.setCrteTime(new Date());//创建时间
            insureIndividualCostDOList.add(insureIndividualCostDO);
        }
        insureIndividualCostDAO.insertInsureCost(insureIndividualCostDOList);
    }

    /**
     * @Method inpt_2302
     * @Desrciption 医保统一支付平台：住院业务模块--住院费用传输明细撤销
     * @Param
     * @Author fuhui
     * @Date 2021/2/10 11:43
     * @Return
     **/
    @Override
    public boolean UP_2302(Map<String, Object> param) {

        InsureIndividualVisitDTO insureIndividualVisitDTO  = (InsureIndividualVisitDTO) param.get("insureIndividualVisitDTO");

        // 调用统一支付平台接口
        Map<String, Object> paramMap = new HashMap<>();
        // 参保地医保区划
        paramMap.put("insuplcAdmdvs",insureIndividualVisitDTO.getInsuplcAdmdvs());
        paramMap.put("insureIndividualVisitDTO", insureIndividualVisitDTO);
        paramMap.put("orgCode", insureIndividualVisitDTO.getMedicineOrgCode());
        paramMap.put("configCode", insureIndividualVisitDTO.getInsureRegCode());
        paramMap.put("configRegCode", insureIndividualVisitDTO.getInsureRegCode());
        paramMap.put("hospCode",MapUtils.get(param,"hospCode"));

        //参数校验,规则校验和请求初始化
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.INPATIENT_FEE_UPLOAD_BACK.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(param.get("hospCode").toString());
        interfaceParamDTO.setIsHospital(Constants.SF.F);
        interfaceParamDTO.setVisitId(insureIndividualVisitDTO.getVisitId());

        insureItfBO.executeInsur(FunctionEnum.INPATIENT_FEE_UPLOAD_BACK, interfaceParamDTO);
        return true;
    }

    /**
     * @Method commonGetVisitInfo
     * @Desrciption  医保统一支付：住院结算，统一患者就诊信息查询接口
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/2/14 10:57
     * @Return InsureIndividualVisitDTO
     **/
    public  InsureIndividualVisitDTO commonGetVisitInfo(Map<String, Object> map){
        InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
        String hospCode = (String) map.get("hospCode");//医院编码
        String visitId = (String) map.get("visitId");//就诊id
        Map<String, Object> insureVisitParam = new HashMap<String, Object>();
        insureVisitParam.put("id", visitId);
        insureVisitParam.put("medicalRegNo",MapUtils.get(map,"medicalRegNo"));
        insureVisitParam.put("hospCode", hospCode);
        insureIndividualVisitDTO = insureIndividualVisitDAO.getInsureIndividualVisitById(insureVisitParam);
        if (insureIndividualVisitDTO == null || StringUtils.isEmpty(insureIndividualVisitDTO.getId())) {
            throw new AppException("未查找到医保就诊信息，请做医保登记。");
        }
        return insureIndividualVisitDTO;
    }

    /**
     * @Method inpt_2303
     * @Desrciption  医保统一支付：住院结算，预结算
     * @Param
     *
     * @Author fuhui
     * @Date   2021/2/14 11:28
     * @Return
     **/
    @Override
    public Map<String,String> UP_2303(Map<String, Object> map){
        String hospCode = map.get("hospCode").toString();

        InptVisitDTO inptVisitDTO = (InptVisitDTO) map.get("inptVisit");
        InsureIndividualVisitDTO insureIndividualVisitDTO = this.commonGetVisitInfo(map);
        //读卡原始信息赋值
        InsureIndividualVisitDTO visitDTO = MapUtil.get(map, "insureIndividualVisitDTO",InsureIndividualVisitDTO.class);
        if (ObjectUtil.isNotEmpty(visitDTO)) {
            insureIndividualVisitDTO.setHcardChkinfo(visitDTO.getHcardChkinfo());
            insureIndividualVisitDTO.setHcardBasinfo(visitDTO.getHcardBasinfo());
        }
        String psnNo = insureIndividualVisitDTO.getAac001();
        String visitId = insureIndividualVisitDTO.getVisitId();

        //判断医保费用是否上传
        inptVisitDTO.setHospCode(hospCode);
        InsureConfigurationDTO insureConfigurationDTO1 = new InsureConfigurationDTO();
        insureConfigurationDTO1 = insureConfigurationDAO.queryInsurePrimaryPrice(inptVisitDTO);
        if(insureConfigurationDTO1.getPrimaryPrice() == null || ("0").equals(insureConfigurationDTO1.getPrimaryPrice())){
            throw new AppException("该病人医保费用未上传，请上传医保费用。");
        }
        /**
         * 获取预结算费用信息
         */
        map.put("isHalfSettle",insureIndividualVisitDTO.getIsHalfSettle());
        map.put("medicalRegNo",insureIndividualVisitDTO.getMedicalRegNo());
        Map<String, Object> costMap = getInptTrialCostInfo(map);

        Map<String,Object> paramMap = new HashMap<String,Object>();
        // 参保地医保区划
        paramMap.put("insuplcAdmdvs",insureIndividualVisitDTO.getInsuplcAdmdvs());
        paramMap.put("insureIndividualVisitDTO", insureIndividualVisitDTO);
        paramMap.put("insureAccoutFlag", inptVisitDTO.getIsUseAccount());
        paramMap.put("costStr", costMap.get("costStr").toString());
        paramMap.put("inptVisitDTO", inptVisitDTO);
        paramMap.put("hospCode",MapUtils.get(map,"hospCode"));

        paramMap.put("orgCode", insureIndividualVisitDTO.getMedicineOrgCode());
        paramMap.put("configCode", insureIndividualVisitDTO.getInsureRegCode());
        paramMap.put("configRegCode", insureIndividualVisitDTO.getInsureRegCode());
        //参数校验,规则校验和请求初始化
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.INPATIENT_PRE_SETTLE.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(hospCode);
        interfaceParamDTO.setIsHospital(Constants.SF.F);
        interfaceParamDTO.setVisitId(insureIndividualVisitDTO.getVisitId());


        String redisKey = new StringBuilder().append(hospCode).append("-").append(2303).append("-")
                .append(visitId).append("-").append(psnNo).toString();
        if(redisUtils.hasKey(redisKey)){
                throw new AppException("该患者正在办理医保住院试算业务");
        }else{
            try {
                Map<String,Object> resultMap = insureItfBO.executeInsur(FunctionEnum.INPATIENT_PRE_SETTLE, interfaceParamDTO);
                Map<String,Object> outMap = (Map<String, Object>) resultMap.get("output");
                Map <String,Object> settleDataMap = (Map<String, Object>) outMap.get("setlinfo");
                Map <String,Object> setlinfoDataMap = (Map<String, Object>) outMap.get("setlinfo");
                Map<String,Object> acctPayMap = null;

                settleDataMap.put("aaz217",insureIndividualVisitDTO.getMedicalRegNo());
                settleDataMap.put("hospCode",hospCode);
                settleDataMap.put("visitId",inptVisitDTO.getId());
                settleDataMap.put("resultStr",JSON.toJSONString(resultMap));
                setlinfoDataMap.put("age",setlinfoDataMap.get("age").toString());
                settleDataMap.put("setlinfo",setlinfoDataMap);
                settleDataMap.put("crteId",inptVisitDTO.getCrteId());
                settleDataMap.put("crteName",inptVisitDTO.getCrteName());
                return  updateInptTrialSettleInfo(settleDataMap,hospCode,insureIndividualVisitDTO.getInsureRegCode(),acctPayMap);
            }finally {
                redisUtils.del(redisKey);
            }
        }
    }

    /**
     * @Method inpt_2304
     * @Desrciption  医保统一支付：住院结算，结算
     * @Param
     *
     * @Author fuhui
     * @Date   2021/2/14 11:28
     * @Return
     **/
    @Override
    public Map<String,String> UP_2304(Map<String, Object> map){
        String hospCode = map.get("hospCode").toString();

        InptVisitDTO inptVisitDTO = (InptVisitDTO) map.get("inptVisit");
        InsureIndividualVisitDTO insureIndividualVisitDTO = this.commonGetVisitInfo(map);
        String psnNo = insureIndividualVisitDTO.getAac001();
        String visitId = insureIndividualVisitDTO.getVisitId();
        String isReadCard = MapUtils.get(map, "isReadCard");  // 是否读卡
        String bka895  = MapUtils.get(map, "bka895");     // 就诊凭证类型
        String bka896 = MapUtils.get(map, "bka896");     //  就诊凭证编号
        //读卡原始信息赋值
        InsureIndividualVisitDTO visitDTO = MapUtil.get(map, "insureIndividualVisitDTO",InsureIndividualVisitDTO.class);
        if (ObjectUtil.isNotEmpty(visitDTO)) {
            insureIndividualVisitDTO.setHcardChkinfo(visitDTO.getHcardChkinfo());
            insureIndividualVisitDTO.setHcardBasinfo(visitDTO.getHcardBasinfo());
        }

        //判断医保费用是否上传
        inptVisitDTO.setHospCode(hospCode);
        InsureConfigurationDTO insureConfigurationDTO1 = new InsureConfigurationDTO();
        insureConfigurationDTO1 = insureConfigurationDAO.queryInsurePrimaryPrice(inptVisitDTO);
        if(insureConfigurationDTO1.getPrimaryPrice() == null || ("0").equals(insureConfigurationDTO1.getPrimaryPrice())){
            throw new AppException("该病人医保费用未上传，请上传医保费用。");
        }


        /**
         * 获取预结算费用信息
         */
        map.put("isHalfSettle",insureIndividualVisitDTO.getIsHalfSettle());
        map.put("medicalRegNo",insureIndividualVisitDTO.getMedicalRegNo());
        Map<String, Object> costMap = getInptTrialCostInfo(map);
        String medisCode = insureIndividualVisitDTO.getMedicineOrgCode();
        String omsgid = StringUtils.createMsgId(medisCode);
        // 调用统一支付平台接口
        Map<String,Object> paramMap = new HashMap<String,Object>();
        // 参保地医保区划
        paramMap.put("insuplcAdmdvs",insureIndividualVisitDTO.getInsuplcAdmdvs());
        paramMap.put("insureIndividualVisitDTO", insureIndividualVisitDTO);
        paramMap.put("insureAccoutFlag", inptVisitDTO.getIsUseAccount());
        paramMap.put("costStr", costMap.get("costStr").toString());
        paramMap.put("inptVisitDTO", inptVisitDTO);
        paramMap.put("hospCode",MapUtils.get(map,"hospCode"));

        paramMap.put("orgCode", insureIndividualVisitDTO.getMedicineOrgCode());
        paramMap.put("configCode", insureIndividualVisitDTO.getInsureRegCode());
        paramMap.put("configRegCode", insureIndividualVisitDTO.getInsureRegCode());
        //  读卡支付把读卡内容更新到接口
        if(Constants.SF.S.equals(isReadCard) && StringUtils.isNotEmpty(bka895) && StringUtils.isNotEmpty(bka896)){
            paramMap.put("bka895", bka895);
            paramMap.put("bka896", bka896);
        }
        //参数校验,规则校验和请求初始化
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.INPATIENT_SETTLE.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(hospCode);
        interfaceParamDTO.setIsHospital(Constants.SF.F);
        interfaceParamDTO.setVisitId(insureIndividualVisitDTO.getVisitId());
        interfaceParamDTO.setMsgid(omsgid);

        String redisKey = new StringBuilder().append(hospCode).append("-").append(2304).append("-")
                .append(visitId).append("-").append(psnNo).toString();

        if(redisUtils.hasKey(redisKey)){
                throw new AppException("该患者正在办理医保住院结算业务");
        }else{
            try {
                Map<String,Object> resultMap = insureItfBO.executeInsur(FunctionEnum.INPATIENT_SETTLE, interfaceParamDTO);
                Map<String,Object> outMap = (Map<String, Object>) resultMap.get("output");
                Map <String,Object> settleDataMap = (Map<String, Object>) outMap.get("setlinfo");
                Map <String,Object> setlinfoDataMap = (Map<String, Object>) outMap.get("setlinfo");
                Map<String,Object> acctPayMap = null;

                map.put("code","HN_INSURE_ACCT_PAY");
                try{
                    Map<String,Object> paramAcctMap = new HashMap<>();
                    paramAcctMap.put("code","HN_INSURE_ACCT_PAY"); // 此参数不需要配置
                    paramAcctMap.put("hospCode",hospCode);
                    SysParameterDTO data = sysParameterService_consumer.getParameterByCode(paramAcctMap).getData();

                    if(data !=null && Constants.SF.S.equals(data.getValue())){
                        settleDataMap.put("visitId",insureIndividualVisitDTO.getVisitId());
                        settleDataMap.put("medicalRegNo",insureIndividualVisitDTO.getMedicalRegNo());
                        settleDataMap.put("hospCode",hospCode);
                        settleDataMap.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
                        settleDataMap.put("INSURE_ACCT_PAY_PARAM",data.getValue());

                        settleDataMap.put("acct_used_flag",inptVisitDTO.getIsUseAccount());
                        settleDataMap.put("regCode",insureIndividualVisitDTO.getInsureRegCode());
                        acctPayMap = handlerAcctPayBalance(settleDataMap);
                    }
                }catch (Exception e){
                    logger.info("个账扣款失败，{}",e);
                }

                settleDataMap.put("action","settle");
                settleDataMap.put("omsgid",omsgid);
                settleDataMap.put("oinfno",FunctionEnum.INPATIENT_SETTLE.getCode());
                List<Map<String,Object>> setldetailList = MapUtils.get(outMap,"setldetail");
                settleDataMap.put("setldetailList",setldetailList);

                settleDataMap.put("aaz217",insureIndividualVisitDTO.getMedicalRegNo());
                settleDataMap.put("hospCode",hospCode);
                settleDataMap.put("visitId",inptVisitDTO.getId());
                settleDataMap.put("resultStr",JSON.toJSONString(resultMap));
                setlinfoDataMap.put("age",setlinfoDataMap.get("age").toString());
                settleDataMap.put("setlinfo",setlinfoDataMap);
                settleDataMap.put("crteId",inptVisitDTO.getCrteId());
                settleDataMap.put("crteName",inptVisitDTO.getCrteName());
                return  updateInptTrialSettleInfo(settleDataMap,hospCode,insureIndividualVisitDTO.getInsureRegCode(),acctPayMap);
            }finally {
                redisUtils.del(redisKey);
            }
        }
    }
    /**
     * @Menthod: UP_2305
     * @Desrciption: 统一支付平台-住院结算撤销
     * @Param: 节点标识-flag-节点标识：data
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-02-10 11:00
     * @Return:
     *
     * @return*/
    @Override
    public Map<String,Object> UP_2305(Map<String,Object> insureUnifiedMap) {

        String hospCode =  insureUnifiedMap.get("hospCode").toString();
        /**
         * 公共入参
         */
        InsureIndividualVisitDTO insureIndividualVisitDTO = this.commonGetVisitInfo(insureUnifiedMap);
        String psnNo = insureIndividualVisitDTO.getAac001();

        String visitId = insureIndividualVisitDTO.getVisitId();
        String infno = "2305";
        String msgId = StringUtils.createMsgId(insureIndividualVisitDTO.getMedicineOrgCode());


        Map<String,Object> paramMap = new HashMap<String,Object>();
        // 参保地医保区划
        paramMap.putAll(insureUnifiedMap);
        paramMap.put("hospCode",MapUtils.get(insureUnifiedMap,"hospCode"));
        paramMap.put("orgCode", insureIndividualVisitDTO.getMedicineOrgCode());
        paramMap.put("configCode", insureIndividualVisitDTO.getInsureRegCode());
        paramMap.put("configRegCode", insureIndividualVisitDTO.getInsureRegCode());
        paramMap.put("insuplcAdmdvs",insureIndividualVisitDTO.getInsuplcAdmdvs());

        //参数校验,规则校验和请求初始化
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.INPATIENT_SETTLE_BACK.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(hospCode);
        interfaceParamDTO.setIsHospital(Constants.SF.F);
        interfaceParamDTO.setVisitId(insureIndividualVisitDTO.getVisitId());
        interfaceParamDTO.setMsgid(msgId);

        String redisKey = new StringBuilder().append(hospCode).append("-").append(2305).append("-")
                .append(visitId).append("-").append(psnNo).toString();
        if(redisUtils.hasKey(redisKey)){
            throw new AppException("该患者正在办理医保住院取消结算业务");
        }else{
            try {
                Map<String,Object> resultMap = insureItfBO.executeInsur(FunctionEnum.INPATIENT_SETTLE_BACK, interfaceParamDTO);
                resultMap.put("msgId",msgId);
                resultMap.put("infno",infno);
                return resultMap;
            }finally {
                redisUtils.del(redisKey);
            }
        }
    }

    /**
     * @Method handlerAcctPayBalance
     * @Desrciption  处理海南地区：余额扣减问题
     *          1.先根据结算反参的证件类型和证件号码查询是否还有余额
     *          2.当余额大于0.00时 调用余额扣减接口
     * @Param
     *
     * @Author fuhui
     * @Date   2022/3/21 13:51
     * @Return
     **/
    private Map<String, Object> handlerAcctPayBalance(Map<String, Object> settleDataMap) {
        Map<String, Object> balanceMap = insureUnifiedBaseService.queryBalanceCount(settleDataMap).getData();
        Map<String,Object> resultMap  =  MapUtils.get(balanceMap,"result");
        Map<String,Object> balanceDataMap = null;
        Object balance = MapUtils.get(resultMap, "balance");
        String string = DataTypeUtils.dataToNumString(balance);
        BigDecimal decimal = BigDecimalUtils.convert(string);
        if(BigDecimalUtils.greaterZero(decimal)){
            settleDataMap.put("insureSettleId",MapUtils.get(settleDataMap,"setl_id"));
            Map<String, Object> dataMap = insureUnifiedBaseService.updateBalanceCountDecrease(settleDataMap).getData();
            balanceDataMap = MapUtils.get(dataMap,"result");
        }
        return balanceDataMap;
    }

    /**
     * @Method getInptTrialCostInfo
     * @Desrciption  医保统一支付：住院结算.预结算费用参数
     * @Param
     *
     * @Author fuhui
     * @Date   2021/2/15 22:30
     * @Return
     **/
    public Map<String,Object> getInptTrialCostInfo(Map<String, Object> map){
        //获取患者所有费用信息
        Map<String,Object> costParam = new HashMap<String,Object>();
        costParam.put("hospCode",map.get("hospCode").toString());//医院编码
        costParam.put("visitId",map.get("visitId").toString());//就诊id
        String costStr = insureIndividualCostDAO.queryInptCostList(costParam);
        if(StringUtils.isEmpty(costStr)){
            throw new AppException("该患者没有产生费用信息,请先进行费用传输");
        }
        costParam.put("costStr",costStr);
        return costParam;
    }

    /**
     * @Method getOutInptTrialSettleInfo
     * @Desrciption  医保统一支付：住院结算.预结算回参
     * @Param
     *
     * @Author fuhui
     * @Date   2021/2/15 21:08
     * @Return
     **/
    public Map<String,String> updateInptTrialSettleInfo(Map<String, Object> outDataMap, String hospCode,
                                                        String regCode, Map<String, Object> acctPayMap){

        String balanceValue = MapUtils.get(outDataMap,"INSURE_ACCT_PAY_PARAM");
        String acctUsedFlag= MapUtils.get(outDataMap,"acct_used_flag");
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("balanceValue",balanceValue);
        paramMap.put("acctUsedFlag",acctUsedFlag);
        Map sysParamMap = new HashMap<>();
        sysParamMap.put("hospCode", hospCode);
        sysParamMap.put("code", regCode);
        WrapperResponse<SysParameterDTO> wr = sysParameterService_consumer.getParameterByCode(sysParamMap);
        if (wr == null || wr.getData() == null || wr.getData().getValue() == null) {
            throw new AppException("请配置系统参数(" + regCode + ")中的['calculation']！" );
        }
        String  calculation  =  JSONObject.parseObject(wr.getData().getValue()).getString("calculation");
        paramMap.put("akc264",outDataMap.get("medfee_sumamt").toString()); // 医疗总费用
        paramMap.put("bka825",outDataMap.get("fulamt_ownpay_amt").toString());// 全自费费用
        paramMap.put("bka826",outDataMap.get("overlmt_selfpay").toString());// 超限价自费费用
        paramMap.put("preselfpayAmt",outDataMap.get("preselfpay_amt").toString());// 先行自付金额
        paramMap.put("inscpScpAmt",outDataMap.get("inscp_scp_amt").toString()); // 符合政策范围金额
        paramMap.put("aka151",outDataMap.get("act_pay_dedc").toString());// 实际支付起付线
        paramMap.put("ake039",outDataMap.get("hifp_pay").toString());// 基本医疗保险统筹基金支出
        paramMap.put("poolPropSelfpay",outDataMap.get("pool_prop_selfpay").toString());// 基本医疗保险统筹基金支付比例
        paramMap.put("ake035",outDataMap.get("cvlserv_pay").toString());// 公务员医疗补助资金支出
        paramMap.put("ake026",outDataMap.get("hifes_pay").toString());// 企业补充医疗保险基金支出
        paramMap.put("hifmiPay",outDataMap.get("hifmi_pay").toString());// 居民大病保险资金支出
        paramMap.put("hifobPay",outDataMap.get("hifob_pay").toString()); // 职工大额医疗费用补助基金支出
        paramMap.put("mafPay",outDataMap.get("maf_pay").toString());// 医疗救助基金支出
        paramMap.put("bka839",outDataMap.get("oth_pay").toString());//其他支付
        paramMap.put("psnPartAmt",outDataMap.get("psn_part_amt") ==null ?"":outDataMap.get("psn_part_amt").toString());// 个人负担总金额
        paramMap.put("akb067",outDataMap.get("psn_cash_pay").toString()); // 个人现金支出
        paramMap.put("hospPrice",outDataMap.get("hosp_part_amt").toString());// 医院负担金额
        paramMap.put("balc",outDataMap.get("balc").toString());// 余额账户
        paramMap.put("acctMulaidPay",outDataMap.get("acct_mulaid_pay").toString());// 个人账户共济支付金额
        String hifmiPay = BigDecimalUtils.convert(outDataMap.get("hifmi_pay").toString()).toString();
        String hifobPay = BigDecimalUtils.convert(outDataMap.get("hifob_pay").toString()).toString();
        paramMap.put("ake029",BigDecimalUtils.add(hifmiPay,hifobPay).toString()); // 大额医疗费用支出

        String bka832 = outDataMap.get("fund_pay_sumamt").toString(); // 基金支付总额
        // 湖南省的 fundPaySumamt 字段不包括个人账户支付部分，珠海医保fundPaySumamt 字段包括，所以需区分！

        String acctPay ="";
        if(Constants.SF.S.equals(balanceValue) && Constants.SF.S.equals(acctUsedFlag)){
            if(!MapUtils.isEmpty(acctPayMap)){
                acctPay  = DataTypeUtils.dataToNumString(MapUtils.get(acctPayMap,"enttAcctPay"));
            }
        }else{
            acctPay = outDataMap.get("acct_pay").toString(); // 个人账户
        }
        paramMap.put("akb066",acctPay);// 个人账户支出

        if (Constant.UnifiedPay.calculation.HN.equals(calculation)) {
            bka832 = BigDecimalUtils.add(acctPay,bka832).toString();
        }
        paramMap.put("bka832",bka832);

        // 个人现金付款金额 = 医疗总费用 - 基金支付总额
        paramMap.put("bka831",BigDecimalUtils.subtract(outDataMap.get("medfee_sumamt").toString(),bka832).toString());
        if(outDataMap.get("setlinfo") != null){
            paramMap.put("setlinfo",outDataMap.get("setlinfo").toString());
        }
        paramMap.put("resultStr",outDataMap.get("resultStr").toString());
        if("settle".equals(outDataMap.get("action") == null ? "" :outDataMap.get("action").toString())) {
            paramMap.put("action","settle");
            paramMap.put("setl_id",outDataMap.get("setl_id").toString());
            paramMap.put("mdtrt_id",outDataMap.get("mdtrt_id").toString());
            paramMap.put("oinfno",outDataMap.get("oinfno").toString());
            paramMap.put("omsgid",outDataMap.get("omsgid").toString());
            paramMap.put("clr_optins",MapUtils.get(outDataMap,"clr_optins"));
            paramMap.put("clr_way",MapUtils.get(outDataMap,"clr_way"));
            paramMap.put("clr_type",MapUtils.get(outDataMap,"clr_type"));
            paramMap.put("acct_pay",MapUtils.get(outDataMap,"acct_pay"));
            paramMap.put("setldetailList",MapUtils.get(outDataMap,"setldetailList"));

        }

        // 处理基金信息
        Map<String,String> setDetailMap = this.doResultSetdetailList(outDataMap);

        // 合并
        Map<String, String> combineResultMap = new HashMap<>();
        combineResultMap.putAll(paramMap);
        combineResultMap.putAll(setDetailMap);
        return combineResultMap;
    }

    // 保存试算基金信息
    private Map<String, String> doResultSetdetailList(Map<String, Object> outDataMap) {
        Map <String,String> resultMap = new HashMap<>();
        List<Map<String,Object>> setldetailList = MapUtils.get(outDataMap,"setldetailList");
        if (!ListUtils.isEmpty(setldetailList)) {
            BigDecimal othPay = BigDecimal.ZERO;
            for (Map<String,Object> map : setldetailList) {
                String fundPayType = MapUtils.get(map,"fund_pay_type");
                String fundPayamt = MapUtils.get(map,"fund_payamt").toString();
                String setlProcInfo = MapUtils.get(map, "setl_proc_info");
                switch (fundPayType) {
                    case "630100": // 医院减免金额
                        resultMap.put("hospExemAmount",fundPayamt);
                        break;
                    case "610100": // 医疗救助基金
                        resultMap.put("mafPay",fundPayamt);
                        break;
                    case "330200": // 职工意外伤害基金
                        resultMap.put("acctInjPay",fundPayamt);
                        break;
                    case "390400": // 居民意外伤害基金
                        resultMap.put("retAcctInjPay",fundPayamt);
                        break;
                    case "640100": // 政府兜底基金
                        resultMap.put("governmentPay",fundPayamt);
                        break;
                    case "620100": // 特惠保补偿金
                        resultMap.put("thbPay",fundPayamt);
                        break;
                    case "999996": // 医院垫付基金
                        resultMap.put("hospPrice",fundPayamt);
                        break;
                    case "610200": // 优抚对象医疗补助基金
                        resultMap.put("carePay",fundPayamt);
                        break;
                    case "999109": // 农村低收入人口医疗补充保险
                        resultMap.put("lowInPay",fundPayamt);
                        break;
                    case "999997": // 其他基金
                        othPay = BigDecimalUtils.add(othPay.toString(),fundPayamt);
                        resultMap.put("othPay",othPay.toString());
                        if ("640101".equals(setlProcInfo)) {
                            resultMap.put("governmentPay",fundPayamt);
                        }
                        if ("630101".equals(setlProcInfo)) {
                            resultMap.put("hospExemAmount",fundPayamt);
                        }
                        if ("620101".equals(setlProcInfo)) {
                            resultMap.put("thbPay",fundPayamt);
                        }
                        if ("610101".equals(setlProcInfo)) {
                            resultMap.put("mafPay",fundPayamt);
                        }
                        break;
                    case "610101":
                        resultMap.put("mafPay",fundPayamt);
                        break;
                    case "620101":
                        resultMap.put("thbPay",fundPayamt);
                        break;
                    case "630101" :
                        resultMap.put("hospExemAmount",fundPayamt);
                        break;
                    case "640101":
                        resultMap.put("governmentPay",fundPayamt);
                        break;
                    case "510100": // 生育基金
                        resultMap.put("fertilityPay",fundPayamt);
                        break;
                    case "340100": // 离休人员医疗保障基金
                        resultMap.put("retiredPay",fundPayamt);
                        break;
                    case "350100": // 一至六级残疾军人医疗补助基金
                        resultMap.put("soldierPay",fundPayamt);
                        break;
                    case "340200": // 离休老工人门慢保障基金
                        resultMap.put("retiredOutptPay",fundPayamt);
                        break;
                    case "410100": // 工伤保险基金
                        resultMap.put("injuryPay",fundPayamt);
                        break;
                    case "320200": //  厅级干部补助基金
                        resultMap.put("hallPay",fundPayamt);
                        break;
                    case "310400": //  军转干部医疗补助基金
                        resultMap.put("soldierToPay",fundPayamt);
                        break;
                    case "370200": //  公益补充保险基金
                        resultMap.put("welfarePay",fundPayamt);
                        break;
                    case "99999707": //  新冠肺炎核酸检测财政补助
                        resultMap.put("COVIDPay",fundPayamt);
                        break;
                    case "390500": //  居民家庭账户金
                        resultMap.put("familyPay",fundPayamt);
                        break;
                    case "310500": //  代缴基金（破产改制）
                        resultMap.put("behalfPay",fundPayamt);
                        break;
                    default:
                        break;
                }
            }
        }
        return resultMap;
    }



    /**
     * @param map
     * @return
     * @Author pengbo
     * @Description：住院管理-入院办理
     * @Date 17:05 2021-02-20
     * @Param
     */
    @Override
    public Map<String, Object> UP_2401(Map<String, Object> map) {
        String hospCode = MapUtils.get(map,"hospCode");
        InsureInptRegisterDTO insureInptRegisterDTO = (InsureInptRegisterDTO) map.get("insureInptRegisterDTO");
        if(insureInptRegisterDTO == null){
            throw new BizRtException(InsureExecCodesEnum.INSURE_REQUEST_DATA_EMPTY, new Object[]{HsaSrvEnum.HSA_INSURE.getDesc(),"住院登记信息"});
        }
        InptVisitDTO  inptVisitDTO = (InptVisitDTO) map.get("inptVisitDTO");
        if(inptVisitDTO == null){
            throw new BizRtException(InsureExecCodesEnum.INSURE_REQUEST_DATA_EMPTY, new Object[]{HsaSrvEnum.HSA_INSURE.getDesc(),"住院就诊信息"});
        }
        InsureIndividualBasicDTO insureIndividualBasicDTO = inptVisitDTO.getInsureIndividualBasicDTO();
        String regCode = null;
        if (insureIndividualBasicDTO != null) {
            regCode = insureIndividualBasicDTO.getInsureRegCode();
        } else {
            regCode = inptVisitDTO.getHospCode();
        }
        if (StringUtils.isEmpty(regCode)) {
            throw new BizRtException(InsureExecCodesEnum.INSURE_REQUEST_DATA_EMPTY, new Object[]{HsaSrvEnum.HSA_INSURE.getDesc(),"医保机构注册编码"});
        }

        if(StringUtils.isEmpty(inptVisitDTO.getZzDoctorId()) || StringUtils.isEmpty(inptVisitDTO.getZzDoctorName())){
            throw new BizRtException(InsureExecCodesEnum.ATDDR_NO_EMPTY, new Object[]{HsaSrvEnum.HSA_INSURE.getDesc(),inptVisitDTO.getId()});
        }
        List<String> diagnoseList = Stream.of("101","102","201","202","203").collect(Collectors.toList());
        inptVisitDTO.setDiagnoseList(diagnoseList);
        inptVisitDTO.setVisitId(inptVisitDTO.getId());
        List<InptDiagnoseDTO> data = doctorAdviceService_consumer.getInptDiagnose(map).getData();
        if(ListUtils.isEmpty(data)) {
            throw new BizRtException(InsureExecCodesEnum.IN_HOSP_DIAG_EMPTY, new Object[]{HsaSrvEnum.HSA_INSURE.getDesc(),inptVisitDTO.getId()});
        }

        inptVisitDTO.setInsureRegCode(insureIndividualBasicDTO.getInsureRegCode());
        List<InptDiagnoseDTO> inptDiagnoseDTOList = doctorAdviceService_consumer.queryInptDiagnose(map).getData();
        commonHandlerDisease(inptDiagnoseDTOList,data,"2401");

        // 调用统一支付平台接口
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.putAll(map);
        paramMap.put("inptDiagnoseDTOList",inptDiagnoseDTOList);
        // 参保地医保区划
        paramMap.put("insuplcAdmdvs",insureIndividualBasicDTO.getInsuplc_admdvs());
        paramMap.put("orgCode", insureIndividualBasicDTO.getMedicineOrgCode());
        paramMap.put("configCode", insureIndividualBasicDTO.getInsureRegCode());
        paramMap.put("configRegCode", insureIndividualBasicDTO.getInsureRegCode());
        String omsgId = StringUtils.createMsgId(insureIndividualBasicDTO.getMedicineOrgCode());
        paramMap.put("hospCode",MapUtils.get(map,"hospCode"));
        //参数校验,规则校验和请求初始化
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.INPATIENT_IN.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(hospCode);
        interfaceParamDTO.setIsHospital(Constants.SF.F);
        interfaceParamDTO.setVisitId(inptVisitDTO.getId());
        interfaceParamDTO.setMsgid(omsgId);

        String redisKey = new StringBuilder().append(hospCode).append("-").append("2401").append("-").
                append(inptVisitDTO.getId()).append("-").append(insureInptRegisterDTO.getAac001()).toString();
        logger.info("医保入院登记------------------"+redisKey+"---------" + redisUtils.hasKey(redisKey));
        try {
            if(redisUtils.hasKey(redisKey)){
                throw new AppException("该患者正在办理医保入院登记操作,请稍后再试");
            }
            redisUtils.set(redisKey,redisKey,30);

            Map<String, Object> resultMap = insureItfBO.executeInsur(FunctionEnum.INPATIENT_IN, interfaceParamDTO);
            Map<String, Object> outptMap = (Map<String, Object>) resultMap.get("output");
            Map<String, Object> resultDataMap = (Map<String, Object>) outptMap.get("result");
            resultMap.put("aaz217",resultDataMap.get("mdtrt_id"));
            resultMap.put("omsgid",omsgId);
            resultMap.put("oinfno",FunctionEnum.INPATIENT_IN.getCode());
            return resultMap;
        } finally {
            redisUtils.del(redisKey);
        }
    }

    /**
     * @Method commonHandlerDisease
     * @Desrciption  医保入院登记和出院办理时，验证诊断是否匹配
     * @Param   inptDiagnoseDTOList：未关联医保疾病匹配表
     *          data:关联疾病匹配表
     *          type:业务类型  2401  医保入院登记  2402  医保出院办理
     * @Author fuhui
     * @Date   2021/9/2 9:01
     * @Return
     **/
    private void commonHandlerDisease(List<InptDiagnoseDTO> inptDiagnoseDTOList,List<InptDiagnoseDTO> data,String type) {

        List<InptDiagnoseDTO> list = data.stream().filter(inptDiagnoseDTO ->
                Constants.SF.S.equals(inptDiagnoseDTO.getIsMain())).collect(Collectors.toList());
        int size = list.size();
        if("2401".equals(type)){
            if(size == 0){
                throw new AppException("没有开入院主诊断");
            }
            if(size >1){
                throw new AppException("入院主诊断数量大于1");
            }
        }else{
            if(size == 0){
                throw new AppException("没有开出院主诊断");
            }
            if(size >1){
                throw new AppException("出院院主诊断数量大于1");
            }
        }
        if(inptDiagnoseDTOList.size() != data.size()){
            List<String> dataCollect = data.stream().map(InptDiagnoseDTO::getDiseaseName).distinct().collect(Collectors.toList());
            List<String> inptDataCollect = inptDiagnoseDTOList.stream().map(InptDiagnoseDTO::getDiseaseName).distinct().collect(Collectors.toList());
            List<String> collect = dataCollect.stream().filter(item -> !inptDataCollect.contains(item)).collect(Collectors.toList());
            StringBuilder stringBuilder = new StringBuilder();
            if(!ListUtils.isEmpty(collect)) {
                for (String s : collect) {
                    stringBuilder.append(s).append(",");
                }
                throw new AppException("该患者开的"+stringBuilder+"还没有进行疾病匹配,请先做好匹配工作");
            }
        }
    }
    /**
     * @param map
     * @return
     * @Author pengbo
     * @Description：住院管理-出院办理
     * @Date 2021-02-20 17:11
     * @Param
     */
    @Override
    public Map<String, Object> UP_2402(Map<String, Object> map) {
        String hospCode = MapUtils.get(map,"hospCode");
        String visitId = MapUtils.get(map,"id");
        String medicalRegNo = MapUtils.get(map,"medicalRegNo");
        if(StringUtils.isEmpty(medicalRegNo)){
            medicalRegNo = insureIndividualVisitDAO.getMedicalRegNo(map);
        }
        map.put("medicalRegNo",medicalRegNo);
        InsureIndividualVisitDTO insureIndividualVisitDTO =commonGetVisitInfo(map);
        map.put("insureIndividualVisitDTO",insureIndividualVisitDTO);

        //入院诊断信息参数diseinfoList
        InptVisitDTO inptVisitDTO = new InptVisitDTO();
        inptVisitDTO.setHospCode(hospCode);
        inptVisitDTO.setId(visitId);
        inptVisitDTO.setVisitId(visitId);
        List<String> diagnoseList = Stream.of("303","204").collect(Collectors.toList());
        inptVisitDTO.setDiagnoseList(diagnoseList);
        map.put("inptVisitDTO",inptVisitDTO);
        inptVisitDTO.setVisitId(inptVisitDTO.getId());
        List<InptDiagnoseDTO> data = doctorAdviceService_consumer.getInptDiagnose(map).getData();
        if(ListUtils.isEmpty(data)) {
//            throw new AppException("该患者没有开诊断信息");
            throw new BizRtException(InsureExecCodesEnum.IN_HOSP_DIAG_EMPTY, new Object[]{HsaSrvEnum.HSA_INSURE.getDesc(),inptVisitDTO.getId()});
        }
        inptVisitDTO.setInsureRegCode(insureIndividualVisitDTO.getInsureRegCode());
        List<InptDiagnoseDTO> inptDiagnoseDTOList = doctorAdviceService_consumer.queryInptDiagnose(map).getData();
        commonHandlerDisease(inptDiagnoseDTOList,data,"2402");
        map.put("inptDiagnoseDTOList",inptDiagnoseDTOList);

        // 调用统一支付平台接口
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.putAll(map);
        paramMap.put("inptDiagnoseDTOList",inptDiagnoseDTOList);
        // 参保地医保区划
        paramMap.put("insuplcAdmdvs",insureIndividualVisitDTO.getInsuplcAdmdvs());
        paramMap.put("orgCode", insureIndividualVisitDTO.getMedicineOrgCode());
        paramMap.put("configCode", insureIndividualVisitDTO.getInsureRegCode());
        paramMap.put("configRegCode", insureIndividualVisitDTO.getInsureRegCode());
        paramMap.put("hospCode",MapUtils.get(map,"hospCode"));
        //参数校验,规则校验和请求初始化
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.INPATIENT_OUT.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(hospCode);
        interfaceParamDTO.setIsHospital(Constants.SF.F);
        interfaceParamDTO.setVisitId(inptVisitDTO.getId());


        insureItfBO.executeInsur(FunctionEnum.INPATIENT_OUT, interfaceParamDTO);
        return null;
    }

    /**
     * @param map
     * @return
     * @Author pengbo
     * @Description：住院管理-住院信息变更
     * @Date 2021-02-20 17:11
     * @Param
     */
    @Override
    public Boolean UP_2403(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String visitId =  MapUtils.get(map,"id");
        Map<String, Object> paramMap = new HashMap<>();

        map.put("visitId",visitId);
        String insureRegCode = MapUtils.get(map, "insureRegCode");

        InsureIndividualVisitDTO insureIndividualVisitDTO = commonGetVisitInfo(map);
        paramMap.put("insureIndividualVisitDTO",insureIndividualVisitDTO);

        //住院变更诊断信息参数diseinfoList
        InptVisitDTO inptVisitDTO = new InptVisitDTO();
        inptVisitDTO.setHospCode(hospCode);
        inptVisitDTO.setId(MapUtils.get(map,"id"));
        inptVisitDTO.setInsureRegCode(insureRegCode);
        map.put("inptVisitDTO",inptVisitDTO);
        List<InptDiagnoseDTO> inptDiagnoseDTOList = doctorAdviceService_consumer.queryInptDiagnose(map).getData();
        if(ListUtils.isEmpty(inptDiagnoseDTOList)){
            throw new BizRtException(InsureExecCodesEnum.IN_HOSP_DIAG_EMPTY, new Object[]{HsaSrvEnum.HSA_INSURE.getDesc(),inptVisitDTO.getId()});
        }
        paramMap.put("inptDiagnoseDTOList",inptDiagnoseDTOList);

        // 调用统一支付平台接口

        paramMap.putAll(map);
        // 参保地医保区划
        paramMap.put("insuplcAdmdvs",insureIndividualVisitDTO.getInsuplcAdmdvs());
        paramMap.put("orgCode", insureIndividualVisitDTO.getMedicineOrgCode());
        paramMap.put("configCode", insureIndividualVisitDTO.getInsureRegCode());
        paramMap.put("configRegCode", insureIndividualVisitDTO.getInsureRegCode());
        paramMap.put("hospCode",MapUtils.get(map,"hospCode"));
        //参数校验,规则校验和请求初始化
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.INPATIENT_UPDATE.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(hospCode);
        interfaceParamDTO.setIsHospital(Constants.SF.F);
        interfaceParamDTO.setVisitId(inptVisitDTO.getId());

        insureItfBO.executeInsur(FunctionEnum.INPATIENT_UPDATE, interfaceParamDTO);

        return true;
    }

    /**
     * @param map
     * @return
     * @Author pengbo
     * @Description：住院管理-入院办理撤销
     * @Date 2021-02-20 17:11
     * @Param
     */    @Override
    public Map<String, Object> UP_2404(Map<String, Object> map) {
        String hospCode = MapUtils.get(map,"hospCode");
        InsureInptOutFeeDTO insureInptOutFeeDTO = (InsureInptOutFeeDTO) map.get("insureInptOutFeeDTO");
        if (insureInptOutFeeDTO == null){
            throw new RuntimeException("未获取到入院相关信息!");
        }
        InptVisitDTO inptVisitDTO = MapUtils.get(map,"inptVisitDTO");
        String visitId = inptVisitDTO.getId();
        map.put("visitId",visitId);
        map.put("crteName",inptVisitDTO.getCrteName());
        map.put("crteId",inptVisitDTO.getCrteId());
        map.put("medicalRegNo",inptVisitDTO.getMedicalRegNo());
        InsureIndividualVisitDTO insureIndividualVisitDTO = commonGetVisitInfo(map);

        String psnNo = insureInptOutFeeDTO.getAac001();

        // 调用统一支付平台接口
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("hospCode",MapUtils.get(map,"hospCode"));
        paramMap.put("insureInptOutFeeDTO",insureInptOutFeeDTO);
        paramMap.put("orgCode", insureIndividualVisitDTO.getMedicineOrgCode());
        paramMap.put("configCode", insureIndividualVisitDTO.getInsureOrgCode());
        paramMap.put("configRegCode", insureIndividualVisitDTO.getInsureRegCode());
        paramMap.put("insuplcAdmdvs",insureIndividualVisitDTO.getInsuplcAdmdvs());

        //参数校验,规则校验和请求初始化
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.INPATIENT_IN_BACK.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(hospCode);
        interfaceParamDTO.setIsHospital(Constants.SF.F);
        interfaceParamDTO.setVisitId(inptVisitDTO.getId());

        String redisKey = new StringBuilder().append(hospCode).append("-").append("2404").append("-").append(visitId).append("-").append(psnNo).toString();
        logger.info("入院撤销------------------"+redisKey+"---------" + redisUtils.hasKey(redisKey));
        if(redisUtils.hasKey(redisKey)){
            throw new AppException("该患者正在办理入院撤销业务,请勿重复操作");
        }else{
            try {
                redisUtils.set(redisKey,redisKey,20);
                insureItfBO.executeInsur(FunctionEnum.INPATIENT_IN_BACK, interfaceParamDTO);
            }finally {
                redisUtils.del(redisKey);
            }
        }

        return null;
    }

    /**
     * @param map
     * @return
     * @Author pengbo
     * @Description：住院管理-出院办理撤销
     * @Date 2021-02-20 17:11
     * @Param
     */
    @Override
    public Map<String, Object> UP_2405(Map<String, Object> map) {
        String hospCode =  map.get("hospCode").toString();
        String medicalRegNo = MapUtils.get(map,"medicalRegNo");
        if(StringUtils.isEmpty(medicalRegNo)){
            medicalRegNo = insureIndividualVisitDAO.getMedicalRegNo(map);
        }
        /**
         * 公共入参
         */
        map.put("medicalRegNo",medicalRegNo);
        InsureIndividualVisitDTO insureIndividualVisitDTO = this.commonGetVisitInfo(map);
        String visitId = insureIndividualVisitDTO.getVisitId();
        String psnNo= insureIndividualVisitDTO.getAac001() ;

        // 设置redis的键
        String redisKey = new StringBuilder().append(hospCode).append("-").append("2405").append("-").append(visitId).append("-").append(psnNo).toString();
        logger.info("出院办理撤销------------------"+redisKey+"---------" + redisUtils.hasKey(redisKey));

        Map<String, Object> paramMap = new HashMap<>();
        // 调用统一支付平台接口
        paramMap.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
        // 参保地医保区划
        paramMap.put("insuplcAdmdvs",insureIndividualVisitDTO.getInsuplcAdmdvs());
        paramMap.put("orgCode", insureIndividualVisitDTO.getMedicineOrgCode());
        paramMap.put("configCode", insureIndividualVisitDTO.getInsureRegCode());
        paramMap.put("configRegCode", insureIndividualVisitDTO.getInsureRegCode());
        paramMap.put("hospCode",MapUtils.get(map,"hospCode"));
        //参数校验,规则校验和请求初始化
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.INPATIENT_OUT_BACK.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(hospCode);
        interfaceParamDTO.setIsHospital(Constants.SF.F);
        interfaceParamDTO.setVisitId(visitId);

        // 判断键是否存在
        if(redisUtils.hasKey(redisKey)){
            throw new AppException("该患者正在别处出院办理撤销,请稍后");
        }else{
            try {
                // 设置键
                redisUtils.set(redisKey,medicalRegNo,60);
                insureItfBO.executeInsur(FunctionEnum.INPATIENT_OUT_BACK, interfaceParamDTO);
            }finally {
                redisUtils.del(redisKey);
            }
        }
        return null;
    }

    /**
     * @Menthod: UP_4602
     * @Desrciption: 统一支付平台-护理操作生命体征测量记录【4602】
     * @Param: visitId-就诊id
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-08-21 16:22
     * @Return:
     **/
    @Override
    public Map<String, Object> UP4602(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        List<InptNurseThirdDTO> smtzList = MapUtils.get(map, "smtzList");

        //根据医院编码、医保机构编码查询医保配置信息
        InsureConfigurationDTO configDTO = new InsureConfigurationDTO();
        configDTO.setHospCode(hospCode);
        configDTO.setRegCode(insureRegCode);
        InsureConfigurationDTO insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(configDTO);
        if (insureConfigurationDTO == null) throw new RuntimeException("未发现【" + insureRegCode + "】相关医保配置信息");

        // 封装护理生命特征记录
        List<Map<String, Object>> data = this.handeleSmtzData(smtzList);

        Map inputMap = new HashMap();
        inputMap.put("data", data); // 护理生命特征记录list

        // 调用统一支付平台接口
        Map httpMap = new HashMap();
        httpMap.put("infno", Constant.UnifiedPay.INPT.UP_4602); // 交易编号
        httpMap.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode())); // 发送方报文
        httpMap.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); // 参保地医保区划
        httpMap.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs()); // 就医地医保区划
        httpMap.put("medins_code", insureConfigurationDTO.getOrgCode()); // 定点医药机构编号
        httpMap.put("insur_code", insureConfigurationDTO.getRegCode()); // 医保中心编码
        httpMap.put("input", inputMap); // 交易输入
        String dataJson = JSONObject.toJSONString(httpMap);
        logger.debug("统一支付平台-护理操作生命体征测量记录【4602】入参：" + dataJson);
        String resultStr = HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), dataJson);
        logger.debug("统一支付平台-护理操作生命体征测量记录【4602】返参：" + resultStr);

        // 解析返参
        if (StringUtils.isEmpty(resultStr)) {
            throw new AppException("无法访问统一支付平台");
        }
        Map<String, Object> resultMap = JSONObject.parseObject(resultStr, Map.class);
        if ("999".equals(MapUtils.get(resultMap, "code"))) {
            String msg = MapUtils.get(resultMap, "msg");
            throw new AppException(msg);
        }
        if (!"0".equals(MapUtils.get(resultMap, "infcode"))) {
            String err_msg = MapUtils.get(resultMap, "err_msg");
            throw new AppException(err_msg);
        }
        return resultMap;
    }

    /**
     * 【2001】人员待遇享受检查
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-07-13 9:05
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public Map<String, Object> UP_2001(Map<String, Object> map) {
      InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");
      InsureConfigurationDTO insureConfigurationDTO = MapUtils.get(map, "insureConfigurationDTO");
      InsureInptRegisterDTO insureInptRegisterDTO = MapUtils.get(map, "insureInptRegisterDTO");
      InsureIndividualBasicDTO insureIndividualBasicDTO = MapUtils.get(map, "insureIndividualBasicDTO");
      String  hospCode = MapUtils.get(map, "hospCode");
      //封装入参
      Map<String, Object> dataMap = new HashMap<>();
      //人员编号
      dataMap.put("psn_no", insureInptRegisterDTO.getAac001());
      //就诊发生日期 yyyyMMdd
      dataMap.put("begntime", insureInptRegisterDTO.getAae030());
      //险种类型
      dataMap.put("insutype", insureIndividualBasicDTO.getAae140());
      //医疗机构编码
      dataMap.put("fixmedins_code", insureConfigurationDTO.getOrgCode());
      //业务类型
      dataMap.put("med_type", inptVisitDTO.getInsureBizCode());
      dataMap.put("endtime", null);
      dataMap.put("dise_codg", "");
      dataMap.put("dise_name", "");
      dataMap.put("oprn_oprt_code", null);
      dataMap.put("oprn_oprt_name", null);
      dataMap.put("matn_type", null);
      dataMap.put("birctrl_type", null);
      Map<String, Object> inputMap = new HashMap<>();
      inputMap.put("data", dataMap);
      map.put("msgName","人员待遇享受检查");
      map.put("isHospital","");
      map.put("visitId",inptVisitDTO.getId());
      Map<String, Object> resultMap = insureUnifiedCommonUtil.commonInsureUnified(hospCode, insureConfigurationDTO.getRegCode(), Constant.UnifiedPay.REGISTER.UP_2001, dataMap,map);
      Map<String, Object> outputMap = (Map<String, Object>) resultMap.get("output");
      List<Map<String, Object>> resultDataMap = MapUtils.get(outputMap, "trtinfo");
      if (!"0".equals(MapUtils.get(resultMap, "infcode"))) {
        throw new AppException((String) resultMap.get("err_msg"));
      }
      Map<String, Object> resultMap1 = new HashMap<>();
      resultMap1.put("resultDataMap", resultDataMap);
      return resultMap1;
    }

  // 封装护理生命特征记录
    private List<Map<String, Object>> handeleSmtzData(List<InptNurseThirdDTO> smtzList) {
        List<Map<String, Object>> data = new ArrayList<>();
        if (!ListUtils.isEmpty(smtzList)) {
            InptNurseThirdDTO nurseThirdDTO = smtzList.get(0);
            // todo 目前只支持医保病人上传
            if (StringUtils.isNotEmpty(nurseThirdDTO.getPatientCode()) && !Constants.BRLX.PTBR.equals(nurseThirdDTO.getPatientCode())) {
                smtzList.forEach(dto -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("mdtrt_sn", dto.getVisitId()); // 就医流水号
                    if (!Constants.BRLX.PTBR.equals(dto.getPatientCode())) {
                        if (StringUtils.isNotEmpty(dto.getAac001()) && StringUtils.isNotEmpty(dto.getMedicalRegNo())) {
                            map.put("mdtrt_id", dto.getMedicalRegNo()); // 就诊ID
                            map.put("psn_no", dto.getAac001()); // 人员编号
                        } else {
                            throw new RuntimeException("【" + dto.getName() + "】为医保病人，请先进行医保登记");
                        }
                    }
                    map.put("dept_code", dto.getInDeptCode()); // 科室代码
                    map.put("dept_name", dto.getInDeptName()); // 科室名称
                    map.put("wardarea_name", dto.getInWardName()); // 病区名称
                    map.put("bedno", dto.getBedName()); // 病床号
                    map.put("diag_code", dto.getInDiseaseCode()); // 诊断代码
                    map.put("adm_time", dto.getInTimeBatch()); // 入院时间
                    map.put("act_ipt_days", dto.getInDays()); // 实际住院天数
                    map.put("afpn_days", dto.getOperationDays()); // 手术后天数
                    map.put("rcd_time", dto.getRecordTime()); // 记录日期时间
                    map.put("vent_frqu", dto.getBreath()); // 呼吸频率（次/min）
                    map.put("use_vent_flag", dto.getIsVentilator()); // 使用呼吸机标志
                    map.put("pule", dto.getPulse()); // 脉率（次/min）
                    map.put("pat_heart_rate", dto.getHeartRate()); // 起搏器心率（次/min）
                    map.put("tprt", BigDecimalUtils.scale(dto.getTemperature(), 1)); // 体温（℃）
                    String amBp = dto.getAmBp();
                    String[] split = amBp.split("/");
                    map.put("systolic_pre", StringUtils.isNotEmpty(split[0]) ? split[0] : ""); // 收缩压（mmHg）
                    if (split.length <= 1) {
                        map.put("dstl_pre", ""); // 舒张压（mmHg）
                    } else {
                        map.put("dstl_pre", StringUtils.isNotEmpty(split[1]) ? split[1] : ""); // 舒张压（mmHg）
                    }
                    map.put("wt", dto.getWeight()); // 体重（kg）
                    map.put("abde", dto.getGirth()); // 腹围（cm）
                    map.put("nurscare_obsv_item_name", ""); // 护理观察项目名称
                    map.put("nurscare_obsv_rslt", ""); // 护理观察结果
                    map.put("nurs_name", dto.getCrteName()); // 护士姓名
                    map.put("sign_time", dto.getCrteTime()); // 签字时间
                    map.put("vali_flag", Constants.SF.S); // 有效标志
                    data.add(map);
                });
            }
        }
        return data;
    }

}
