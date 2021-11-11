package cn.hsa.insure.unifiedpay.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.doctor.entity.InptCostDO;
import cn.hsa.module.inpt.doctor.service.DoctorAdviceService;
import cn.hsa.module.inpt.inptnursethird.dto.InptNurseThirdDTO;
import cn.hsa.module.insure.inpt.bo.InsureUnifiedPayInptBO;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualBasicDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualCostDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualVisitDAO;
import cn.hsa.module.insure.module.dto.*;
import cn.hsa.module.insure.module.entity.InsureIndividualCostDO;
import cn.hsa.module.insure.module.service.InsureUnifiedLogService;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
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
    private InsureUnifiedLogService insureUnifiedLogService_consumer;

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
        String code = map.get("code").toString();
        InptVisitDTO inptVisitDTO = MapUtils.get(map,"inptVisitDTO");
        String crteName = map.get("crteName").toString();
        String hospCode =  map.get("hospCode").toString();
        String isHalfSettle = MapUtils.get(map,"isHalfSettle");
        String feeStartDate = MapUtils.get(map,"feeStartDate");
        String feeEndDate = MapUtils.get(map,"feeEndDate");
        Integer count = (Integer) map.get("count");
        InsureIndividualVisitDTO insureIndividualVisitDTO  = (InsureIndividualVisitDTO) map.get("insureIndividualVisitDTO");
        String insureRegCode = insureIndividualVisitDTO.getInsureOrgCode();
        String visitId = insureIndividualVisitDTO.getVisitId();
        map.put("visitId",visitId);
        map.put("insureIndividualVisitDTO",insureIndividualVisitDTO);


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
        if(ListUtils.isEmpty(insureCostList)){
            throw new AppException("没有可以上传的的医保匹配费用数据");
        }
        // 查询有退费的数据集合,且未上传的的数据集合
        List<InptCostDTO> inptCostDTOList = insureIndividualCostDAO.queryBackInptFee(insureIndividualVisitDTO);
        // 查询已经上传的费用数据
        List<Map<String, Object>> individualCostDTOList = insureIndividualCostDAO.queryInsureInptCost(insureIndividualVisitDTO);
        int num = 0;
        /**
         * 本次上传如果没有正常的费用数据,则不上传到医保，直接把退费的数据插入到医保费用表
         */
        if(!ListUtils.isEmpty(insureCostList)){
            for(Map<String,Object> item : insureCostList){
                if("0".equals(MapUtils.get(item,"statusCode"))){
                    num++;
                }else{
                    continue;
                }
            }
        }
        if(num ==0){
            // 直接把全开，全退的费用保存到费用表 但是不进行调用医保的操作
            insertNotUpLoadFee(insureCostList,inptVisitDTO);
            return map;
        }
        else{
            // 说明有正常的数据 需要调用医保接口
            List<Map<String,Object>> list2 = new ArrayList<>();  // 处理
            List<Map<String,Object>> list1 = new ArrayList<>();
            List<Map<String,Object>> list3 = new ArrayList<>(); // 处理正负直接相抵的集合
            if(!ListUtils.isEmpty(inptCostDTOList)){
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
                // 传退费对应的数据
                if(!ListUtils.isEmpty(individualCostDTOList)){
                    for(Map<String,Object> item : individualCostDTOList){
                        if(collect.containsKey(MapUtils.get(item,"costId"))){
                            list2.add(item);
                        }
                    }
                }

                if(!ListUtils.isEmpty(list3)){
                    insertNotUpLoadFee(list3,inptVisitDTO);
                }
                list2.addAll(list1);
            }else{
                list2.addAll(insureCostList);
            }
            List<InptCostDO> inptCostDOList = insureIndividualCostDAO.queryInptFeeCost(map);
            Map<String, InptCostDO> inptCostDOMap = inptCostDOList.stream().collect(Collectors.toMap(InptCostDO::getId,
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
                        objectMap.put("init_feedetl_sn",Integer.valueOf(MapUtils.get(item,"initFeedetlSn"))) ;// 原费用流水号
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
                objectMap.put("orders_dr_code",""); // 受单医生编码
                objectMap.put("orders_dr_name",""); // 受单医生姓名
                String isReimburse = MapUtils.get(item,"isReimburse");

                /**
                 * zhSpecial : '1' 珠海
                 * huNanSpecial : '1' 湖南
                 */
                objectMap.put("hosp_appr_flag", hospApprFlag); // 医院审批标志
                // 珠海 + （药品和材料） + 限制级用药标志为 0 ，hosp_appr_flag则使用 0
                if("1".equals(zhSpecial) && "0".equals(isReimburse)) {
                    objectMap.put("hosp_appr_flag", "0");
                }

                // 湖南省医保中药饮片中出现了复方药物，则中药饮片全部报销,单方为不报销
                if (isCompound && "1".equals(huNanSpecial) && "103".equals(MapUtils.get(item,"insureItemType"))) {
                    objectMap.put("hosp_appr_flag", "1");
                    objectMap.put("tcmdrug_used_way","1");
                } else if (!isCompound && "1".equals(huNanSpecial) && "103".equals(MapUtils.get(item,"insureItemType"))) {
                    objectMap.put("hosp_appr_flag", "0");
                    objectMap.put("tcmdrug_used_way","2");
                }else if("1".equals(huNanSpecial) && "0".equals(isReimburse)){
                    objectMap.put("hosp_appr_flag", "0");
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
        Date startDate = MapUtils.get(unifiedPayMap,"startDate");
        Date endDate = MapUtils.get(unifiedPayMap,"endDate");
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
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(unifiedPayMap,"insureIndividualVisitDTO");
        Integer settleCount = insureIndividualCostDAO.queryLasterCounter(insureIndividualVisitDTO);
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
                        insureIndividualCostDTO.setItemCode(MapUtils.get(feedetlSnObjectMap,"medins_list_code"));
                        insureIndividualCostDTO.setItemName(MapUtils.get(feedetlSnMap,"medins_list_name"));
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
                        insureIndividualCostDTO.setFeeStartTime(startDate);
                        insureIndividualCostDTO.setFeeEndTime(endDate);
                        if("0".equals(isHalfSettle)){
                            insureIndividualCostDTO.setSettleCount(0); // 中途结算次数
                        }else{
                            if(settleCount == null){
                                insureIndividualCostDTO.setSettleCount(1); // 中途结算次数
                            }else{
                                insureIndividualCostDTO.setSettleCount(++settleCount); // 中途结算次数
                            }
                        }
                        individualCostDTOList.add(insureIndividualCostDTO);
                    }
                }
            }
        }
        if(!ListUtils.isEmpty(individualCostDTOList)){
            insureIndividualCostDAO.batchInsertCost(individualCostDTOList);
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
    public boolean UP_2302(Map<String, Object> param) {
        String hospCode = param.get("hospCode").toString();
        InsureIndividualVisitDTO insureIndividualVisitDTO  = (InsureIndividualVisitDTO) param.get("insureIndividualVisitDTO");
        List<InsureIndividualCostDTO> individualCostDTOList = (List<InsureIndividualCostDTO>) param.get("individualCostDTOList");
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setOrgCode(insureIndividualVisitDTO.getMedicineOrgCode());  // 医疗机构编码
        insureConfigurationDTO.setCode(insureIndividualVisitDTO.getInsureRegCode()); // 医保机构编码
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        /**
         * 公共入参
         */
        Map httpParam = new HashMap();
        String infno = Constant.UnifiedPay.INPT.UP_2302;
        httpParam.put("infno",infno);  //交易编号
        String msgId = StringUtils.createMsgId(insureIndividualVisitDTO.getMedicineOrgCode());
        httpParam.put("msgid",StringUtils.createMsgId(insureIndividualVisitDTO.getMedicineOrgCode()));
        httpParam.put("insuplc_admdvs",insureIndividualVisitDTO.getInsuplcAdmdvs()); //参保地医保区划分
        httpParam.put("medins_code",insureIndividualVisitDTO.getMedicineOrgCode()); //定点医药机构编号
        httpParam.put("insur_code",insureConfigurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs",insureConfigurationDTO.getMdtrtareaAdmvs());

        List<Map<String,Object>> mapList = new ArrayList<>();
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("feedetl_sn","0000");//费用明细流水号
        objectMap.put("mdtrt_id",insureIndividualVisitDTO.getMedicalRegNo()); // 就诊ID
        objectMap.put("psn_no",insureIndividualVisitDTO.getAac001());// 个人编号
        String medisCode = insureIndividualVisitDTO.getMedicineOrgCode();
        objectMap.put("medins_code",medisCode);//定点医药机构编号

        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("data",objectMap);
        httpParam.put("input",dataMap);
        String json = JSONObject.toJSONString(httpParam);
        logger.info("统一支付平台住院费用撤销入参:" + json);
        String url= insureConfigurationDTO.getUrl();
        String resultStr = HttpConnectUtil.unifiedPayPostUtil(url, json);

        String visitId = insureIndividualVisitDTO.getVisitId();
        param.put("medisCode",medisCode);
        param.put("visitId",visitId);
        param.put("msgId",msgId);
        param.put("msgInfo",infno);
        param.put("msgName","住院费用明细撤销");
        param.put("isHospital",Constants.SF.S) ;
        param.put("paramMapJson",json);
        param.put("resultStr",resultStr);
        insureUnifiedLogService_consumer.insertInsureFunctionLog(param);

        logger.info("统一支付平台住院费用撤销回参:" + resultStr);
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
    public Map<String,String> UP_2303(Map<String, Object> map){
        String hospCode = map.get("hospCode").toString();
        String userName = MapUtils.get(map,"userName");
        String code = MapUtils.get(map,"code");
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        InptVisitDTO inptVisitDTO = (InptVisitDTO) map.get("inptVisit");
        InsureIndividualVisitDTO insureIndividualVisitDTO = this.commonGetVisitInfo(map);
        String psnNo = insureIndividualVisitDTO.getAac001();
        String visitId = insureIndividualVisitDTO.getVisitId();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setOrgCode(insureIndividualVisitDTO.getMedicineOrgCode());  // 医疗机构编码
        insureConfigurationDTO.setCode(insureIndividualVisitDTO.getInsureRegCode()); // 医保机构编码
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);

        //判断医保费用是否上传
        inptVisitDTO.setHospCode(hospCode);
        InsureConfigurationDTO insureConfigurationDTO1 = new InsureConfigurationDTO();
        insureConfigurationDTO1 = insureConfigurationDAO.queryInsurePrimaryPrice(inptVisitDTO);
        if(insureConfigurationDTO1.getPrimaryPrice() == null || ("0").equals(insureConfigurationDTO1.getPrimaryPrice())){
            throw new AppException("该病人医保费用未上传，请上传医保费用。");
        }

        String  insureAccoutFlag  =  inptVisitDTO.getIsUseAccount();
        Map<String,Object> paramMap = new HashMap<String,Object>();
        String omsgid = "" ;
        String functionCode = "";
        if("settle".equals(map.get("action") == null ? "" :map.get("action").toString())) {
            /**
             * 公共入参
             */
            omsgid = StringUtils.createMsgId(insureIndividualVisitDTO.getMedicineOrgCode());
            functionCode ="2304";
            paramMap.put("infno",functionCode);  // 交易编号
            paramMap.put("msgid",omsgid);

        }
        else{
            /**
             * 公共入参
             */
            functionCode ="2303";
            omsgid = StringUtils.createMsgId(insureIndividualVisitDTO.getMedicineOrgCode());
            paramMap.put("infno",functionCode);  // 交易编号
            paramMap.put("msgid",omsgid);

        }
        paramMap.put("insuplc_admdvs",insureIndividualVisitDTO.getInsuplcAdmdvs()); //参保地医保区划分
        String medisCode = insureIndividualVisitDTO.getMedicineOrgCode();
        paramMap.put("medins_code",medisCode); //定点医药机构编号
        paramMap.put("insur_code",insureConfigurationDTO.getRegCode()); //医保中心编码
        paramMap.put("mdtrtarea_admvs",insureConfigurationDTO.getMdtrtareaAdmvs()); // 就医地医保区划
        paramMap.put("opter",code);
        paramMap.put("opter_name",userName);
        /**
         * 获取预结算费用信息
         */
        map.put("isHalfSettle",insureIndividualVisitDTO.getIsHalfSettle());
        map.put("medicalRegNo",insureIndividualVisitDTO.getMedicalRegNo());
        Map<String, Object> costMap = getInptTrialCostInfo(map);

        /**
         * 预结算参数信息
         */
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("psn_no",insureIndividualVisitDTO.getAac001());// 人员编号
        dataMap.put("mdtrt_cert_type",insureIndividualVisitDTO.getMdtrtCertType());// 就诊凭证类型
        dataMap.put("mdtrt_cert_no",insureIndividualVisitDTO.getMdtrtCertNo()); // 就诊凭证编号
        DecimalFormat df1 = new DecimalFormat("0.00");
        String realityPrice = df1.format(BigDecimalUtils.convert(costMap.get("costStr").toString()));
        dataMap.put("medfee_sumamt",BigDecimalUtils.convert(realityPrice));// 医疗费总额
        dataMap.put("psn_setlway",Constants.JSFS.PTJS); // 个人结算方式
        dataMap.put("mdtrt_id",insureIndividualVisitDTO.getMedicalRegNo());// 就诊ID
        dataMap.put("acct_used_flag",insureAccoutFlag);// 个人账户使用标志
        dataMap.put("insutype",insureIndividualVisitDTO.getAae140());//险种类型
        dataMap.put("invono",""); // 发票号
        dataMap.put("med_type",insureIndividualVisitDTO.getAka130());

        /**
         * 是否开启中途结算
         */
        if("1".equals(insureIndividualVisitDTO.getIsHalfSettle())){
            dataMap.put("mid_setl_flag",Constants.SF.S);// 中途结算标志
        }else{
            dataMap.put("mid_setl_flag",Constants.SF.F);// 中途结算标志
        }
        dataMap.put("fulamt_ownpay_amt",0.00);// 全自费金额
        dataMap.put("overlmt_selfpay",0.00);// 超限价金额
        dataMap.put("preselfpay_amt",0.00);// 先行自付金额
        dataMap.put("inscp_scp_amt",0.00);// 符合政策范围金额
        dataMap.put("save_flag",Constants.SF.S);// 保存标志
        dataMap.put("medins_code",insureIndividualVisitDTO.getMedicineOrgCode());// 医疗机构编码
        dataMap.put("serial_no",insureIndividualVisitDTO.getMedicalRegNo()); //就医登记号
        dataMap.put("balc",0.00);// 本次业务个人帐户可用金额
        dataMap.put("dscg_diag",inptVisitDTO.getOutDiseaseIcd10());// 出院疾病
        dataMap.put("dscg_diag_name",inptVisitDTO.getOutDiseaseName()); // 出院诊断名称
        dataMap.put("endtime",DateUtils.format(DateUtils.getNow(),DateUtils.Y_M_DH_M_S));//出院日期
        dataMap.put("first_asist_diag","");// 第一副诊断
        dataMap.put("secd_asist_diag","");// 第二副诊断
        dataMap.put("dscg_cond","");// 出院详情
        dataMap.put("opter",MapUtils.get(map,"code"));// 操作员工号
        dataMap.put("opter_name",MapUtils.get(map,"userName"));// 操作员姓名
        dataMap.put("med_mdtrt_type",insureIndividualVisitDTO.getBka006()); // 待遇类别
        dataMap.put("ill_no","");// 单据号
        dataMap.put("matn_mdtrt_type","");// 生育就诊类型
        dataMap.put("matn_dise_type",""); // 生育疾病类型
        dataMap.put("serial_apply",insureIndividualVisitDTO.getInjuryBorthSn());// 特殊情况对应的申请序号
        dataMap.put("cash_pay",BigDecimalUtils.scale(new BigDecimal(0.00),2));// 刷卡金额
        dataMap.put("bilg_dr_code","");// 处方医生编号
        dataMap.put("bilg_dr_name","");// 处方医生姓名
        dataMap.put("medins_diag_code","");// 医疗机构疾病诊断
        dataMap.put("medins_first_asist_diag","");// 医疗机构第一副诊断
        dataMap.put("medins_secd_asist_diag","");// 医疗机构第二副诊断
        dataMap.put("card_sn","");//卡识别码
        dataMap.put("order_no","");// 医疗机构订单号或医疗机构就医序列号
        dataMap.put("mdtrt_mode","");// 就诊方式
        dataMap.put("hcard_basinfo","");// 持卡就诊基本信息
        dataMap.put("hcard_chkinfo","");// 持卡就诊校验信息
        /**
         * 说明是结算
         */
        if("settle".equals(map.get("action") == null ? "" :map.get("action").toString())){
            dataMap.put("elect_cert_token",""); // 医保电子凭证Token
            dataMap.put("certno",""); // 证件类型
            dataMap.put("elect_cert_token",""); // 身份证号
            dataMap.put("elect_certno",""); // 扫描医保电子凭证返回的值
            dataMap.put("is_elect_cert",""); // 是否医保电子凭证刷卡
            dataMap.put("geso_val",""); // 妊娠周期
            dataMap.put("oprn_way",""); // 手术方式
            dataMap.put("birth_cert_no",""); // 出生证号
            dataMap.put("fetus_num","") ; // 胎儿数
            dataMap.put("fetus_cnt",""); // 胎次
            dataMap.put("oper_date","") ; // 手术日期
            dataMap.put("matn_type",""); // 生育类别
            dataMap.put("acct_pay",""); // 使用个帐金
            dataMap.put("dscg_stas",""); // 出院转归情况
            dataMap.put("begntime",""); // 业务开始时间
            dataMap.put("endtime",""); // 业务结束时间
        }

        String redisKey = new StringBuilder().append(hospCode).append("-").append(functionCode).append("-")
                .append(visitId).append("-").append(psnNo).toString();
        if(redisUtils.hasKey(redisKey)){
            if("2303".equals(functionCode)){
                throw new AppException("该患者正在办理医保住院试算业务");
            }else{
                throw new AppException("该患者正在办理医保住院结算业务");
            }
        }else{
            try {
                Map<String,Object> objectMap = new HashMap<>();
                objectMap.put("data",dataMap);
                paramMap.put("input",objectMap);  // 交易输入
                String json = JSONObject.toJSONString(paramMap);
                logger.info("统一支付平台住院试算或结算入参:" + json);
                String url= insureConfigurationDTO.getUrl();
                String resultStr = HttpConnectUtil.unifiedPayPostUtil(url, json);
                if("settle".equals(map.get("action") == null ? "" :map.get("action").toString())) {
                    map.put("msgName","医保住院结算");
                }else{
                    map.put("msgName","医保住院试算");
                }
                map.put("medisCode",medisCode);
                map.put("visitId",visitId);
                map.put("msgId",omsgid);
                map.put("msgInfo",functionCode);
                map.put("isHospital",Constants.SF.S) ;
                map.put("paramMapJson",json);
                map.put("resultStr",resultStr);
                insureUnifiedLogService_consumer.insertInsureFunctionLog(map);
                logger.info("统一支付平台住院试算或结算回参:" + resultStr);
                if(StringUtils.isEmpty(resultStr)){
                    throw new AppException("无法访问统一支付平台");
                }
                Map<String, Object> resultMap = JSONObject.parseObject(resultStr,Map.class);
                if ("999".equals(MapUtils.get(resultMap,"code"))) {
                    throw new AppException((String) resultMap.get("msg"));
                }
                if (!MapUtils.get(resultMap,"infcode").equals("0")) {
                    Object resultMsg = resultMap.get("err_msg");
                    if (resultMsg == null) {
                        resultMsg = "统一支付平台回参为空：infcode(" + MapUtils.get(resultMap,"infcode") + ")";
                    }
                    throw new AppException(resultMsg.toString());
                }
                Map<String,Object> outMap = (Map<String, Object>) resultMap.get("output");
                Map <String,Object> settleDataMap = (Map<String, Object>) outMap.get("setlinfo");
                Map <String,Object> setlinfoDataMap = (Map<String, Object>) outMap.get("setlinfo");
                if("settle".equals(map.get("action") == null ? "" :map.get("action").toString())) {
                    settleDataMap.put("action","settle");
                    settleDataMap.put("omsgid",omsgid);
                    settleDataMap.put("oinfno",functionCode);
                    List<Map<String,Object>> setldetailList = MapUtils.get(outMap,"setldetail");
                    settleDataMap.put("setldetailList",setldetailList);
                }
                settleDataMap.put("aaz217",insureIndividualVisitDTO.getMedicalRegNo());
                settleDataMap.put("hospCode",hospCode);
                settleDataMap.put("visitId",inptVisitDTO.getId());
                settleDataMap.put("resultStr",resultStr);
                setlinfoDataMap.put("age",setlinfoDataMap.get("age").toString());
                settleDataMap.put("setlinfo",setlinfoDataMap);
                settleDataMap.put("crteId",inptVisitDTO.getCrteId());
                settleDataMap.put("crteName",inptVisitDTO.getCrteName());
                return  updateInptTrialSettleInfo(settleDataMap,hospCode,insureConfigurationDTO.getRegCode());
            }finally {
                redisUtils.del(redisKey);
            }
        }
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
    public Map<String,String> updateInptTrialSettleInfo(Map<String,Object> outDataMap,String hospCode,String regCode){
        Map<String,String> paramMap = new HashMap<String,String>();
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
        paramMap.put("psnPartAmt",outDataMap.get("psn_part_amt").toString());// 个人负担总金额
        paramMap.put("akb066",outDataMap.get("acct_pay").toString());// 个人账户支出
        paramMap.put("akb067",outDataMap.get("psn_cash_pay").toString()); // 个人现金支出
        paramMap.put("hospPrice",outDataMap.get("hosp_part_amt").toString());// 医院负担金额
        paramMap.put("balc",outDataMap.get("balc").toString());// 余额账户
        paramMap.put("acctMulaidPay",outDataMap.get("acct_mulaid_pay").toString());// 个人账户共济支付金额
        String hifmiPay = outDataMap.get("hifmi_pay").toString();
        String hifobPay = outDataMap.get("hifob_pay").toString();
        paramMap.put("ake029",BigDecimalUtils.add(hifmiPay,hifobPay).toString()); // 大额医疗费用支出

        String bka832 = outDataMap.get("fund_pay_sumamt").toString(); // 基金支付总额
        // 湖南省的 fundPaySumamt 字段不包括个人账户支付部分，珠海医保fundPaySumamt 字段包括，所以需区分！
        if (Constant.UnifiedPay.calculation.HN.equals(calculation)) {
            bka832 = BigDecimalUtils.add(outDataMap.get("acct_pay").toString(),bka832).toString();
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
            for (Map<String,Object> map : setldetailList) {
                String fundPayType = MapUtils.get(map,"fund_pay_type");
                String fundPayamt = MapUtils.get(map,"fund_payamt").toString();
                switch (fundPayType) {
                    case "630100": // 医院减免金额
                        resultMap.put("hospExemAmount",fundPayamt);
                        break;
                    /*case "610100": // 医疗救助基金
                        resultMap.put("mafPay",fundPayamt);
                        break;*/
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
                        resultMap.put("othPay",fundPayamt);
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
     * @Method inpt_2304
     * @Desrciption  医保统一支付：住院结算，结算
     * @Param
     *
     * @Author fuhui
     * @Date   2021/2/14 11:28
     * @Return
     **/
    public Map<String,String> UP_2304(Map<String, Object> map){
        map.put("action","settle");
        Map<String, String> objectMap = UP_2303(map);
        return objectMap;
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
    public Map<String,Object> editCancelInptSettle(Map<String,Object> insureUnifiedMap) {
        Map<String, Object> inptMap = new HashMap<>();
        String hospCode =  insureUnifiedMap.get("hospCode").toString();
        /**
         * 公共入参
         */
        InsureIndividualVisitDTO insureIndividualVisitDTO = this.commonGetVisitInfo(insureUnifiedMap);
        String psnNo = insureIndividualVisitDTO.getAac001();
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setOrgCode(insureIndividualVisitDTO.getMedicineOrgCode());  // 医疗机构编码
        insureConfigurationDTO.setCode(insureIndividualVisitDTO.getInsureRegCode()); // 医保机构编码
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        String visitId = insureIndividualVisitDTO.getVisitId();
        String infno = "2305";
        String msgId = StringUtils.createMsgId(insureIndividualVisitDTO.getMedicineOrgCode());
        inptMap.put("infno",infno);  // 交易编号
        inptMap.put("msgid",msgId);
        inptMap.put("insuplc_admdvs",insureIndividualVisitDTO.getInsuplcAdmdvs()); //参保地医保区划分
        String medisCode = insureIndividualVisitDTO.getMedicineOrgCode();
        inptMap.put("medins_code",medisCode); //定点医药机构编号
        inptMap.put("insur_code",insureConfigurationDTO.getRegCode()); //医保中心编码
        inptMap.put("mdtrtarea_admvs",insureConfigurationDTO.getMdtrtareaAdmvs()); // 就医地医保区划

        Map<String,Object> dataMap = new HashMap<>();
        Iterator<String> iterator = insureUnifiedMap.keySet().iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            if("insuplc_admdvs".equals(key)){
                iterator.remove();

            }
            if("inptVisitDTO".equals(key)){
                iterator.remove();
            }
            if("insureIndividualVisitDTO".equals(key)){
                iterator.remove();
            }

        }
        dataMap.put("data",insureUnifiedMap);
        inptMap.put("input", dataMap);//交易输入
        String dataJson = JSONObject.toJSONString(inptMap);
        logger.info("统一支付平台去取消结算入参:" + dataJson);
        String url= insureConfigurationDTO.getUrl();
        Map<String, Object> resultMap = null;
        String redisKey = new StringBuilder().append(hospCode).append("-").append(infno).append("-")
                .append(visitId).append("-").append(psnNo).toString();
        if(redisUtils.hasKey(redisKey)){
            throw new AppException("该患者正在办理医保住院取消结算业务");
        }else{
            try {
                String resultStr = HttpConnectUtil.unifiedPayPostUtil(url, dataJson);
                logger.info("统一支付平台去取消结算回参:" + resultStr);
                insureUnifiedMap.put("medisCode",medisCode);
                insureUnifiedMap.put("visitId",visitId);
                insureUnifiedMap.put("msgId",msgId);
                insureUnifiedMap.put("msgInfo",infno);
                insureUnifiedMap.put("msgName","医保住院取消结算");
                insureUnifiedMap.put("isHospital",Constants.SF.S) ;
                insureUnifiedMap.put("paramMapJson",dataJson);
                insureUnifiedMap.put("resultStr",resultStr);
                insureUnifiedLogService_consumer.insertInsureFunctionLog(insureUnifiedMap);
                if(StringUtils.isEmpty(resultStr)){
                    throw new AppException("无法访问统一支付平台");
                }
                resultMap = JSONObject.parseObject(resultStr,Map.class);
                if ("999".equals(MapUtils.get(resultMap,"code"))) {
                    throw new AppException((String) resultMap.get("msg"));
                }
                if (!MapUtils.get(resultMap,"infcode").equals("0")) {
                    throw new AppException((String) resultMap.get("err_msg"));
                }
                resultMap.put("msgId",msgId);
                resultMap.put("infno",infno);
                return resultMap;
            }finally {
                redisUtils.del(redisKey);
            }
        }
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
            throw new RuntimeException("调用统一支付平台失败,未获取到住院登记信息!");
        }
        InptVisitDTO inptVisitDTO = (InptVisitDTO) map.get("inptVisitDTO");
        map.put("crteId",inptVisitDTO.getCrteId());
        map.put("crteName",inptVisitDTO.getCrteName());
        String visitId = inptVisitDTO.getId();
        if(insureInptRegisterDTO == null){
            throw new RuntimeException("调用统一支付平台失败,未获取到住院就诊信息!");
        }
        String aac001 =  insureInptRegisterDTO.getAac001();
        InsureConfigurationDTO dto = new InsureConfigurationDTO();
        dto.setHospCode(inptVisitDTO.getHospCode());
        InsureIndividualBasicDTO insureIndividualBasicDTO = inptVisitDTO.getInsureIndividualBasicDTO();
        String regCode = null;
        if (insureIndividualBasicDTO != null) {
            regCode = insureIndividualBasicDTO.getInsureRegCode();
        } else {
            regCode = inptVisitDTO.getHospCode();
        }
        if (StringUtils.isEmpty(regCode)) {
            throw new AppException("获取患者医保信息时没有拿到指定医保机构注册编码，请联系管理员");
        }
        dto.setRegCode(regCode);
        InsureConfigurationDTO insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(dto);

        if (insureConfigurationDTO == null) {
            throw new AppException("未查询到就诊地医保区划");
        }

        if(StringUtils.isEmpty(inptVisitDTO.getZzDoctorId()) || StringUtils.isEmpty(inptVisitDTO.getZzDoctorName())){
            throw  new AppException("该患者没有主治医生,请先安床");
        }

        //入院诊断信息参数diseinfoList
        List<Map<String, Object>> diseinfoList = new ArrayList<Map<String, Object>>();
        String dscg_maindiag_code = null;
        String dscg_maindiag_name = null;
        String orgCode =insureConfigurationDTO.getOrgCode();
        String insureOrgCode = insureConfigurationDTO.getRegCode();
        inptVisitDTO.setInsureRegCode(insureOrgCode);
        List<String> diagnoseList = Stream.of("101","102","201","202","203").collect(Collectors.toList());
        inptVisitDTO.setVisitId(visitId);
        inptVisitDTO.setDiagnoseList(diagnoseList);
        map.put("inptVisitDTO",inptVisitDTO);
        List<InptDiagnoseDTO> data = doctorAdviceService_consumer.getInptDiagnose(map).getData();
        if(ListUtils.isEmpty(data)) {
            throw new AppException("该患者没有开诊断信息");
        }
        //入院诊断信息参数diseinfoMap
        Map<String, Object> diseinfoMap =null;
        /**
         * 做医保入院登记办理的时候
         * 1.需要判断是否开了诊断
         * 2.开了的诊断是否已经匹配
         */
        List<InptDiagnoseDTO> inptDiagnoseDTOList = doctorAdviceService_consumer.queryInptDiagnose(map).getData();
            commonHandlerDisease(inptDiagnoseDTOList,data,"2401");
            for(int i=0;i<inptDiagnoseDTOList.size();i++){
                diseinfoMap = new HashMap<>();
                diseinfoMap.put("psn_no", insureInptRegisterDTO.getAac001());//	人员编号
                diseinfoMap.put("diag_type", "1");//	诊断类别
                diseinfoMap.put("maindiag_flag", inptDiagnoseDTOList.get(i).getIsMain());//	主诊断标志
                if("1".equals(inptDiagnoseDTOList.get(i).getIsMain())){
                    dscg_maindiag_code = inptDiagnoseDTOList.get(i).getInsureInllnessCode();
                    dscg_maindiag_name = inptDiagnoseDTOList.get(i).getInsureInllnessName();
                }
                diseinfoMap.put("diag_srt_no", i);//	诊断排序号
                diseinfoMap.put("diag_code", inptDiagnoseDTOList.get(i).getInsureInllnessCode());//	诊断代码
                diseinfoMap.put("diag_name", inptDiagnoseDTOList.get(i).getInsureInllnessName());//	诊断名称
                diseinfoMap.put("adm_cond", null);//	入院病情
                diseinfoMap.put("diag_dept", inptDiagnoseDTOList.get(i).getInDeptName());//	诊断科室
                diseinfoMap.put("dise_dor_no",  inptDiagnoseDTOList.get(i).getPracCertiNo());//	诊断医生编码
                diseinfoMap.put("dise_dor_name",  inptDiagnoseDTOList.get(i).getZzDoctorName());//	诊断医生姓名
                diseinfoMap.put("diag_time",  DateUtils.format(inptDiagnoseDTOList.get(i).getCrteTime(),DateUtils.Y_M_DH_M_S));//	诊断时间
                diseinfoMap.put("medins_diag_code",orgCode);//	医疗机构诊断编码
                diseinfoList.add(diseinfoMap);

            }
        //就诊信息参数mdtrtinfo
        Map<String, Object> mdtrtinfoMap = new HashMap<>();
        mdtrtinfoMap.put("ipt_otp_no", insureInptRegisterDTO.getInNo());//	住院/门诊号
        mdtrtinfoMap.put("psn_no", aac001);//	人员编号
        mdtrtinfoMap.put("insutype", inptVisitDTO.getInsureIndividualBasicDTO().getAae140()); // // TODO	险种类型
        mdtrtinfoMap.put("coner_name", insureInptRegisterDTO.getAae004());//	联系人姓名
        mdtrtinfoMap.put("tel", insureInptRegisterDTO.getAae005());//	联系电话
        InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
        insureIndividualVisitDTO.setHospCode(hospCode);
        insureIndividualVisitDTO.setVisitId(visitId);
         insureIndividualVisitDTO = insureIndividualVisitDAO.selectIsHalfSettleInfo(insureIndividualVisitDTO);
        // 如果是中途结算，且中途结算次数大于0 则入院开始时间取中途结算的结算时间
        if(insureIndividualVisitDTO !=null && "1".equals(insureIndividualVisitDTO.getIsHalfSettle()) && insureIndividualVisitDTO.getSettleCount() > 0){
           insureIndividualVisitDTO.setInsureSettleId("1");
            InsureIndividualCostDTO insureIndividualCostDTO = insureIndividualCostDAO.selectHalfTransmitFee(insureIndividualVisitDTO);
            if(insureIndividualCostDTO !=null && insureIndividualCostDTO.getFeeEndTime() !=null){
                Date feeEndTime =  insureIndividualCostDTO.getFeeEndTime();
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(feeEndTime);
                calendar.add(Calendar.DATE,1);
                feeEndTime =calendar.getTime();
                mdtrtinfoMap.put("begntime", DateUtils.format(feeEndTime,DateUtils.Y_M_DH_M_S));//	开始时间
            }else{
                mdtrtinfoMap.put("begntime", DateUtils.format(inptVisitDTO.getInTime(),DateUtils.Y_M_DH_M_S));//	开始时间
            }
        }
        else{
            mdtrtinfoMap.put("begntime", DateUtils.format(inptVisitDTO.getInTime(),DateUtils.Y_M_DH_M_S));//	开始时间
        }
        String mdtrtCertType = inptVisitDTO.getInsureIndividualBasicDTO().getBka895();
        if("06".equals(mdtrtCertType) || "03".equals(mdtrtCertType)){
            mdtrtinfoMap.put("mdtrt_cert_type", "03");//inptVisitDTO.getCertCode());//	就诊凭证类型
        }else{
            mdtrtinfoMap.put("mdtrt_cert_type", mdtrtCertType);//inptVisitDTO.getCertCode());//	就诊凭证类型
        }
        if(!StringUtils.isEmpty(inptVisitDTO.getInsureIndividualBasicDTO().getAac002())){
            mdtrtinfoMap.put("mdtrt_cert_no", inptVisitDTO.getInsureIndividualBasicDTO().getAac002());// inptVisitDTO.getCertNo());//	就诊凭证编号
        }
        else{
            mdtrtinfoMap.put("mdtrt_cert_no", inptVisitDTO.getInsureIndividualBasicDTO().getBka896());// inptVisitDTO.getCertNo());//	就诊凭证编号
        }
        mdtrtinfoMap.put("med_type", inptVisitDTO.getInsureBizCode());//	医疗类别
        mdtrtinfoMap.put("ipt_no", inptVisitDTO.getInNo());//	住院号
        mdtrtinfoMap.put("medrcdno", null);//	病历号
        if(StringUtils.isEmpty(inptVisitDTO.getPracCertiNo())){
            throw  new AppException("该【"+inptVisitDTO.getZzDoctorName()+"】医生的医师编码没有维护,请先去用户管理里面维护");
        }
        mdtrtinfoMap.put("atddr_no", inptVisitDTO.getPracCertiNo());//inptVisitDTO.getZzDoctorId());//	主治医生编码
        mdtrtinfoMap.put("chfpdr_name",inptVisitDTO.getZzDoctorName());//inptVisitDTO.getZzDoctorName());//	主治医师姓名
        mdtrtinfoMap.put("adm_diag_dscr", dscg_maindiag_name);//	入院诊断描述
        mdtrtinfoMap.put("adm_dept_codg", inptVisitDTO.getInDeptId());//	入院科室编码
        mdtrtinfoMap.put("adm_dept_name", inptVisitDTO.getInDeptName());//	入院科室名称
        mdtrtinfoMap.put("adm_bed", inptVisitDTO.getBedName());//	入院床位
        mdtrtinfoMap.put("dscg_maindiag_code", dscg_maindiag_code);//	住院主诊断代码
        mdtrtinfoMap.put("dscg_maindiag_name", dscg_maindiag_name);//	住院主诊断名称
        mdtrtinfoMap.put("main_cond_dscr", dscg_maindiag_name);//	主要病情描述
        if(!StringUtils.isEmpty(inptVisitDTO.getInsureIndividualBasicDTO().getBka006())){
            mdtrtinfoMap.put("dise_code", inptVisitDTO.getInsureIndividualBasicDTO().getBka006());//	病种编码
            mdtrtinfoMap.put("dise_name", inptVisitDTO.getInsureIndividualBasicDTO().getBka006Name());//	病种名称
        }else{
            mdtrtinfoMap.put("dise_code", dscg_maindiag_code);//	病种编码
            mdtrtinfoMap.put("dise_name", dscg_maindiag_name);//	病种名称
        }
        mdtrtinfoMap.put("oprn_oprt_code", null);//	手术操作代码
        mdtrtinfoMap.put("oprn_oprt_name", null);//	手术操作名称
        mdtrtinfoMap.put("fpsc_no", null);//	计划生育服务证号
        if("52".equals(insureInptRegisterDTO.getAka130())){
            mdtrtinfoMap.put("matn_type", "1");//	生育类别
            if(inptVisitDTO.getAge().compareTo(23) ==1){
                mdtrtinfoMap.put("latechb_flag", "1");//	晚育标志
            }else{
                mdtrtinfoMap.put("latechb_flag", "0");//	晚育标志
            }
            mdtrtinfoMap.put("pret_flag", "0");//	早产标志
        }
        else{
            mdtrtinfoMap.put("matn_type", null);//	生育类别
            mdtrtinfoMap.put("latechb_flag", null);//	晚育标志
            mdtrtinfoMap.put("pret_flag", null);//	早产标志
        }
        mdtrtinfoMap.put("birctrl_type", null);//	计划生育手术类别
        mdtrtinfoMap.put("geso_val", null);//	孕周数
        mdtrtinfoMap.put("fetts", null);//	胎次
        mdtrtinfoMap.put("fetus_cnt", null);//	胎儿数
        mdtrtinfoMap.put("birctrl_matn_date", null);//	计划生育手术或生育日期
        mdtrtinfoMap.put("dise_type_code", null);//	病种类型
        mdtrtinfoMap.put("insuplc_admdvs", inptVisitDTO.getInsureIndividualBasicDTO().getInsuplc_admdvs());//	医保中心编码
        mdtrtinfoMap.put("medins_code", orgCode);//	医疗机构编码
        mdtrtinfoMap.put("psn_type", null);//	人员类别
        mdtrtinfoMap.put("med_type", inptVisitDTO.getInsureBizCode());//	业务类型
        mdtrtinfoMap.put("med_mdtrr_type", inptVisitDTO.getInsureTreatCode());//	待遇类型
        mdtrtinfoMap.put("opt_time",DateUtils.format(inptVisitDTO.getInTime(),DateUtils.Y_M_DH_M_S));//	入院登记时间
        mdtrtinfoMap.put("opter", insureInptRegisterDTO.getBka015());//	登记人员工号
        mdtrtinfoMap.put("opter_name", insureInptRegisterDTO.getAae011());    //登记人姓名
        mdtrtinfoMap.put("adm_way", inptVisitDTO.getInModeCode());//	入院方式
        mdtrtinfoMap.put("serial_apply", null);//	业务申请序列号
        mdtrtinfoMap.put("relt_medins_code", null);//	关联医疗机构编码
        mdtrtinfoMap.put("relt_serial_n", null);//	关联就医登记号
        mdtrtinfoMap.put("adm_sumtms", inptVisitDTO.getTotalIn());//	本年住院次数
        mdtrtinfoMap.put("wardarea_no", inptVisitDTO.getInWardId());//	入院病区编号
        mdtrtinfoMap.put("wardarea_name", null);//	入院病区名称
        mdtrtinfoMap.put("bed_type", null);// 	床位类型
        mdtrtinfoMap.put("prep_pay", null);//	预付款总额
        mdtrtinfoMap.put("hosp_adm_diag", null);//	入院诊断(医疗机构)
        mdtrtinfoMap.put("area_no", null);//	区/县
        mdtrtinfoMap.put("emp_no", null);//	单位电脑号
        mdtrtinfoMap.put("emp_code", null);//	单位管理码
        mdtrtinfoMap.put("emp_type", null);//	单位类型
        mdtrtinfoMap.put("name", inptVisitDTO.getName());//	姓名
        mdtrtinfoMap.put("gend", inptVisitDTO.getGenderCode());//	性别
        mdtrtinfoMap.put("brdy", DateUtils.format(inptVisitDTO.getBirthday(),DateUtils.Y_M_DH_M_S));//	出生日期
        mdtrtinfoMap.put("aim_type", null);//	补助类型
        mdtrtinfoMap.put("coner_adr", inptVisitDTO.getContactAddress());//	联系地址
        mdtrtinfoMap.put("admdv", null);//	人员所属中心
        mdtrtinfoMap.put("cvlserv_lv", insureInptRegisterDTO.getBac001());//	公务员级别
        mdtrtinfoMap.put("emp_name", null);//	单位名称
        mdtrtinfoMap.put("dise_cond_sev", inptVisitDTO.getCriticalValueCode());//	病情严重程度
        mdtrtinfoMap.put("clnc_flag", null);//	临床试验标志
        mdtrtinfoMap.put("er_flag", null);//	急诊标志
        mdtrtinfoMap.put("advpay", null);//	预付款
        mdtrtinfoMap.put("repeat_ipt_flag", null);//	重复住院标志
        mdtrtinfoMap.put("ttp_resp", null);//	是否第三方责任标志
        mdtrtinfoMap.put("merg_setl_flag", null);//	合并结算标志
        mdtrtinfoMap.put("card_sn", insureInptRegisterDTO.getCardIden());//	卡识别码（跨省异地必传）
        mdtrtinfoMap.put("cert_type", "1");//	证件类型（跨省异地必传）
        mdtrtinfoMap.put("certno", insureInptRegisterDTO.getAac002());//	证件号码（跨省异地必传）


        //代办人信息参数agnterinfo
        Map<String, Object> agnterinfoMap = new HashMap<>();

        agnterinfoMap.put("agnter_name", null);//	代办人姓名
        agnterinfoMap.put("agnter_rlts", null);//	代办人关系
        agnterinfoMap.put("agnter_cert_type", null);//	代办人证件类型
        agnterinfoMap.put("agnter_certno", null);//	代办人证件号码
        agnterinfoMap.put("agnter_tel", null);//	代办人联系电话
        agnterinfoMap.put("agnter_addr", null);//	代办人联系地址
        agnterinfoMap.put("agnter_photo", null);//	代办人照片


        //input信息参数inputMap
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("mdtrtinfo", mdtrtinfoMap);
        inputMap.put("agnterinfo", agnterinfoMap);
        inputMap.put("diseinfo", diseinfoList);

        String omsgId = StringUtils.createMsgId(insureConfigurationDTO.getOrgCode());
        String functionCode = Constant.UnifiedPay.REGISTER.UP_2401;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("input", inputMap);
        paramMap.put("insuplc_admdvs",inptVisitDTO.getInsureIndividualBasicDTO().getInsuplc_admdvs());//参保地医保区划
        String medisCode = insureConfigurationDTO.getOrgCode();
        paramMap.put("medins_code", medisCode);//定点医药机构编号
        paramMap.put("infno",functionCode);//交易编号(功能号)
        paramMap.put("msgid",omsgId);
        paramMap.put("mdtrtarea_admvs",inptVisitDTO.getInsureOrgCode());// 就医地医保区划
        paramMap.put("insur_code",inptVisitDTO.getInsureOrgCode()); // 医保中心编码
        String redisKey = new StringBuilder().append(hospCode).append("-").append("2401").append("-").
                append(visitId).append("-").append(aac001).toString();
        System.out.println("医保入院登记------------------"+redisKey+"---------" + redisUtils.hasKey(redisKey));
        System.out.println();
        try {
            if(redisUtils.hasKey(redisKey)){
                throw new AppException("该患者正在办理医保入院登记操作,请稍后再试");
            }
            redisUtils.set(redisKey,redisKey,30);
            String paramMapJson = JSON.toJSONString(paramMap) ;
            logger.info("医保统一支付平台入院办理入参:" + paramMapJson);
            String resultStr =  HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), paramMapJson);

            map.put("medisCode",medisCode);
            map.put("visitId",visitId);
            map.put("msgId",omsgId);
            map.put("msgInfo",functionCode);
            map.put("msgName","医保入院登记");
            map.put("isHospital",Constants.SF.S) ;
            map.put("paramMapJson",paramMapJson);
            map.put("resultStr",resultStr);
            insureUnifiedLogService_consumer.insertInsureFunctionLog(map);

            logger.info("医保统一支付平台入院办理回参:" + resultStr);
            if (StringUtils.isEmpty(resultStr)){
                throw new RuntimeException("调用统一支付平台无响应!");
            }

            Map<String, Object> m = JSONObject.parseObject(resultStr, Map.class);
            String resultCode = MapUtils.get(m,"infcode","");
            if (StringUtils.isEmpty(resultCode)){
                throw new RuntimeException("调用统一支付平台无响应!");
            }
            if (!"0".equals(resultCode)){
                throw new RuntimeException("调用统一支付平台错误,原因："+MapUtils.get(m,"err_msg",""));
            }
            Map<String, Object> outptMap = (Map<String, Object>) m.get("output");
            Map<String, Object> resultMap = (Map<String, Object>) outptMap.get("result");
            resultMap.put("aaz217",resultMap.get("mdtrt_id"));
            resultMap.put("omsgid",omsgId);
            resultMap.put("oinfno",functionCode);
            return resultMap;
        }
        finally {
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
        //入院诊断信息参数diseinfoList
        List<Map<String, Object>> diseinfoList = new ArrayList<Map<String, Object>>();
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        String medicineOrgCode =insureIndividualVisitDTO.getMedicineOrgCode();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setOrgCode(medicineOrgCode);  // 医疗机构编码
        insureConfigurationDTO.setCode(insureIndividualVisitDTO.getInsureRegCode()); // 医保机构编码
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        //院诊断信息参数diseinfoMap
        Map<String, Object> diseinfoMap =null;
        InptVisitDTO inptVisitDTO = new InptVisitDTO();
        inptVisitDTO.setHospCode(hospCode);
        inptVisitDTO.setId(visitId);
        inptVisitDTO.setVisitId(visitId);
        String insureOrgCode = insureConfigurationDTO.getRegCode();
        inptVisitDTO.setInsureRegCode(insureOrgCode);

        List<String> diagnoseList = Stream.of("303","204").collect(Collectors.toList());
        inptVisitDTO.setDiagnoseList(diagnoseList);
        map.put("inptVisitDTO",inptVisitDTO);

        List<InptDiagnoseDTO> data = doctorAdviceService_consumer.getInptDiagnose(map).getData();
        if(ListUtils.isEmpty(data)) {
            throw new AppException("该患者没有开诊断信息");
        }
        List<InptDiagnoseDTO> inptDiagnoseDTOList = doctorAdviceService_consumer.queryInptDiagnose(map).getData();
            commonHandlerDisease(inptDiagnoseDTOList,data,"2402");
            for(int i=0;i<inptDiagnoseDTOList.size();i++) {
                if("1".equals(insureIndividualVisitDTO.getIsHalfSettle())){
                    if("101".equals(inptDiagnoseDTOList.get(i).getTypeCode()) ||
                            "102".equals(inptDiagnoseDTOList.get(i).getTypeCode())||
                            "201".equals(inptDiagnoseDTOList.get(i).getTypeCode())||
                            "202".equals(inptDiagnoseDTOList.get(i).getTypeCode())||
                            "203".equals(inptDiagnoseDTOList.get(i).getTypeCode())){
                        diseinfoMap = new HashMap<>();
                        diseinfoMap.put("mdtrt_id", insureIndividualVisitDTO.getMedicalRegNo());//	mdtrt_id
                        diseinfoMap.put("psn_no", insureIndividualVisitDTO.getAac001());//	人员编号
                        diseinfoMap.put("diag_type", "2");//	诊断类别
                        diseinfoMap.put("maindiag_flag", inptDiagnoseDTOList.get(i).getIsMain());//	主诊断标志
                        diseinfoMap.put("diag_srt_no", i);//	诊断排序号
                        diseinfoMap.put("diag_code", inptDiagnoseDTOList.get(i).getInsureInllnessCode());//	诊断代码
                        diseinfoMap.put("diag_name", inptDiagnoseDTOList.get(i).getInsureInllnessName());//	诊断名称
                        diseinfoMap.put("diag_dept", inptDiagnoseDTOList.get(i).getInDeptName());//	诊断科室
                        diseinfoMap.put("dise_dor_no", inptDiagnoseDTOList.get(i).getPracCertiNo());//	诊断医生编码
                        diseinfoMap.put("dise_dor_name", inptDiagnoseDTOList.get(i).getZzDoctorName());//	诊断医生姓名
                        diseinfoMap.put("diag_time", DateUtils.format(inptDiagnoseDTOList.get(i).getCrteTime(), DateUtils.Y_M_DH_M_S));//	诊断时间
                        diseinfoList.add(diseinfoMap);
                    }
                }
                else{
                    if ("204".equals(inptDiagnoseDTOList.get(i).getTypeCode()) ||
                            "303".equals(inptDiagnoseDTOList.get(i).getTypeCode())) {
                        diseinfoMap = new HashMap<>();
                        diseinfoMap.put("mdtrt_id", insureIndividualVisitDTO.getMedicalRegNo());//	mdtrt_id
                        diseinfoMap.put("psn_no", insureIndividualVisitDTO.getAac001());//	人员编号
                        diseinfoMap.put("diag_type", "2");//	诊断类别
                        diseinfoMap.put("maindiag_flag", inptDiagnoseDTOList.get(i).getIsMain());//	主诊断标志
                        diseinfoMap.put("diag_srt_no", i);//	诊断排序号
                        diseinfoMap.put("diag_code", inptDiagnoseDTOList.get(i).getInsureInllnessCode());//	诊断代码
                        diseinfoMap.put("diag_name", inptDiagnoseDTOList.get(i).getInsureInllnessName());//	诊断名称
                        diseinfoMap.put("diag_dept", inptDiagnoseDTOList.get(i).getInDeptName());//	诊断科室
                        diseinfoMap.put("dise_dor_no", inptDiagnoseDTOList.get(i).getZzDoctorId());//	诊断医生编码
                        diseinfoMap.put("dise_dor_name", inptDiagnoseDTOList.get(i).getZzDoctorName());//	诊断医生姓名
                        diseinfoMap.put("diag_time", DateUtils.format(inptDiagnoseDTOList.get(i).getCrteTime(), DateUtils.Y_M_DH_M_S));//	诊断时间
                        diseinfoList.add(diseinfoMap);
                    }
                }
            }
        InsureIndividualBasicDTO insureIndividualBasicDTO = new InsureIndividualBasicDTO();
        insureIndividualBasicDTO.setVisitId(visitId);
        insureIndividualBasicDTO.setHospCode(hospCode);
        insureIndividualBasicDTO.setMedicalRegNo(medicalRegNo);
        insureIndividualBasicDTO = insureIndividualBasicDAO.getByVisitId(insureIndividualBasicDTO);

        //出院信息参数dscginfo
        Map<String, Object> dscginfoMap = new HashMap<>();
        dscginfoMap.put("mdtrt_id",insureIndividualVisitDTO.getMedicalRegNo());//	就诊ID
        dscginfoMap.put("psn_no", insureIndividualVisitDTO.getAac001());//	人员编号
        dscginfoMap.put("insutype", insureIndividualBasicDTO.getAae140());//	险种类型

        /**
         * 判断是否是中途结算，如果是中途结算的话  医保出院日期就取（住院费用传输的选择费用区间的结束日期）
         */
        if("1".equals(insureIndividualVisitDTO.getIsHalfSettle()) && insureIndividualVisitDTO.getSettleCount() >0){
            insureIndividualVisitDTO.setInsureSettleId("0");
                InsureIndividualCostDTO  individualCostDTO = insureIndividualCostDAO.selectHalfTransmitFee(insureIndividualVisitDTO);
                if(individualCostDTO != null){
                    dscginfoMap.put("endtime", DateUtils.format(individualCostDTO.getFeeEndTime(),DateUtils.Y_M_DH_M_S));//	结束时间
                }

        }else{
            dscginfoMap.put("endtime", DateUtils.format(insureIndividualVisitDTO.getOutTime(),DateUtils.Y_M_DH_M_S));//	结束时间
        }
        dscginfoMap.put("dise_code", insureIndividualVisitDTO.getBka006());//	病种编码
        dscginfoMap.put("dise_name", insureIndividualVisitDTO.getBka006Name());//	病种名称
        dscginfoMap.put("oprn_oprt_code", null);//	手术操作代码
         dscginfoMap.put("fpsc_no", insureIndividualVisitDTO.getFpscNo());//	计划生育服务证号
        dscginfoMap.put("matn_type", insureIndividualVisitDTO.getMatnType());//	生育类别
        dscginfoMap.put("birctrl_type", insureIndividualVisitDTO.getBirctrlType());//	计划生育手术类别
        dscginfoMap.put("latechb_flag", insureIndividualVisitDTO.getLatechbFlag());//	晚育标志
        dscginfoMap.put("geso_val", insureIndividualVisitDTO.getGesoVal());//	孕周数
        dscginfoMap.put("fetts", insureIndividualVisitDTO.getFetts());//	胎次
        dscginfoMap.put("fetus_cnt", insureIndividualVisitDTO.getFetusCnt());//	胎儿数
        dscginfoMap.put("pret_flag", insureIndividualVisitDTO.getPretFlag());//	早产标志
        dscginfoMap.put("birctrl_matn_date", insureIndividualVisitDTO.getBirctrlMatnDate());//	计划生育手术或生育日期
        dscginfoMap.put("cop_flag", null);//	伴有并发症标志
        dscginfoMap.put("dscg_dept_code", insureIndividualVisitDTO.getOutDeptId());//	出院科室编码
        dscginfoMap.put("dscg_dept_name", insureIndividualVisitDTO.getOutDeptName());//	出院科室名称
        dscginfoMap.put("dscg_bed", null);//	出院床位
        dscginfoMap.put("dscg_way", insureIndividualVisitDTO.getOutModeCode());//	离院方式
        dscginfoMap.put("die_date", null);//	死亡日期
        dscginfoMap.put("expi_gestation_nub_of_m", null);//	终止妊娠月数

        //input信息参数inputMap
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("dscginfo", dscginfoMap);
        inputMap.put("diseinfo", diseinfoList);
        Map<String, Object> paramMap = new HashMap<>();
        String functionCode = "2402";
        String omsgId = StringUtils.createMsgId(insureConfigurationDTO.getOrgCode());
        paramMap.put("input", inputMap);
        paramMap.put("msgid",omsgId);
        paramMap.put("insuplc_admdvs",insureIndividualVisitDTO.getInsuplcAdmdvs());//参保地医保区划
        String medisCode = insureConfigurationDTO.getOrgCode();
        paramMap.put("medins_code", medisCode);//定点医药机构编号
        paramMap.put("infno",functionCode);//交易编号(功能号)
        paramMap.put("mdtrtarea_admvs",insureConfigurationDTO.getMdtrtareaAdmvs());// 就医地医保区划
        paramMap.put("insur_code",insureConfigurationDTO.getRegCode()); // 医保中心编码

        String paramMapJson = JSON.toJSONString(paramMap) ;
        logger.info("医保统一支付平台出院办理入参" + paramMapJson);

        String resultStr =  HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), paramMapJson);
        logger.info("医保统一支付平台出院办办理回参" + resultStr);
        map.put("medisCode",medisCode);
        map.put("visitId",visitId);
        map.put("msgId",omsgId);
        map.put("msgInfo",functionCode);
        map.put("msgName","医保出院办理");
        map.put("isHospital",Constants.SF.S) ;
        map.put("paramMapJson",paramMapJson);
        map.put("resultStr",resultStr);
        insureUnifiedLogService_consumer.insertInsureFunctionLog(map).getData();
        if (StringUtils.isEmpty(resultStr)){
            throw new RuntimeException("调用统一支付平台无响应!");
        }

        Map<String, Object> m = JSONObject.parseObject(resultStr, Map.class);
        String resultCode = MapUtils.get(m,"infcode","");

        if (StringUtils.isEmpty(resultCode)){
            throw new RuntimeException("调用统一支付平台无响应!");
        }
        if (!"0".equals(resultCode)){
            throw new RuntimeException("调用统一支付平台错误,原因："+MapUtils.get(m,"err_msg",""));
        }
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
        map.put("visitId",visitId);
        InsureIndividualVisitDTO insureIndividualVisitDTO = commonGetVisitInfo(map);
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(insureRegCode); // 医保机构编码
        insureConfigurationDTO.setOrgCode(insureIndividualVisitDTO.getMedicineOrgCode()); // 医疗机构编码
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        String orgCode = insureConfigurationDTO.getOrgCode();
        Map httpParam = new HashMap();
        httpParam.put("infno", Constant.UnifiedPay.REGISTER.UP_2403);  //交易编号
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", orgCode); //定点医药机构编号
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid",StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        String medicalRegNo = insureIndividualVisitDTO.getMedicalRegNo();
        String psnNo= insureIndividualVisitDTO.getAac001();
        //入院登记信息参数dscginfo
        Map<String, Object> adminfoMap = new HashMap<String, Object>();
        adminfoMap.put("mdtrt_id",medicalRegNo);//	就诊ID
        adminfoMap.put("psn_no", psnNo);//	人员编号
        adminfoMap.put("coner_name", null);//	联系人姓名
        adminfoMap.put("tel", null);//	联系电话
        adminfoMap.put("begntime", DateUtils.format(insureIndividualVisitDTO.getCrteTime(),DateUtils.Y_M_DH_M_S));//	开始时间
        adminfoMap.put("endtime", null);//	结束时间
        adminfoMap.put("mdtrt_cert_type", insureIndividualVisitDTO.getMdtrtCertType());//	就诊凭证类型
        adminfoMap.put("med_type", insureIndividualVisitDTO.getAka130());//	医疗类别
        adminfoMap.put("ipt_otp_no", insureIndividualVisitDTO.getVisitNo());//	住院/门诊号
        adminfoMap.put("medrcdno", null);//	病历号
        adminfoMap.put("atddr_no",insureIndividualVisitDTO.getPracCertiNo());//	主治医生编码
        adminfoMap.put("chfpdr_name", insureIndividualVisitDTO.getZzDoctorName());//	主诊医师姓名
        adminfoMap.put("adm_diag_dscr", null);//	入院诊断描述
        adminfoMap.put("adm_dept_codg", insureIndividualVisitDTO.getVisitDrptId());//	入院科室编码
        adminfoMap.put("adm_dept_name", insureIndividualVisitDTO.getVisitDrptName());//	入院科室名称
        adminfoMap.put("adm_bed", insureIndividualVisitDTO.getVisitBerth());//	入院床位
        adminfoMap.put("dscg_maindiag_code", insureIndividualVisitDTO.getVisitIcdCode());//	住院主诊断代码
        adminfoMap.put("dscg_maindiag_name", insureIndividualVisitDTO.getVisitDrptName());//	住院主诊断名称
        adminfoMap.put("main_cond_dscr", null);//	主要病情描述
        adminfoMap.put("dise_code", insureIndividualVisitDTO.getBka006());//	病种编码
        adminfoMap.put("dise_name", insureIndividualVisitDTO.getBka006Name());//	病种名称
        adminfoMap.put("oprn_oprt_code", null);//	手术操作代码
        adminfoMap.put("oprn_oprt_name", null);//	手术操作名称
        adminfoMap.put("fpsc_no",insureIndividualVisitDTO.getFpscNo());//	计划生育服务证号
        adminfoMap.put("matn_type", insureIndividualVisitDTO.getMatnType());//	生育类别
        adminfoMap.put("birctrl_type", insureIndividualVisitDTO.getBirctrlType());//	计划生育手术类别
        adminfoMap.put("latechb_flag", insureIndividualVisitDTO.getLatechbFlag());//	晚育标志
        adminfoMap.put("geso_val", insureIndividualVisitDTO.getGesoVal());//	孕周数
        adminfoMap.put("fetts", insureIndividualVisitDTO.getFetts());//	胎次
        adminfoMap.put("fetus_cnt", insureIndividualVisitDTO.getFetusCnt());//	胎儿数
        adminfoMap.put("pret_flag", insureIndividualVisitDTO.getPretFlag());//	早产标志
        adminfoMap.put("birctrl_matn_date", insureIndividualVisitDTO.getBirctrlMatnDate());//	计划生育手术或生育日期
        adminfoMap.put("dise_type_code", null);//	病种编号
        adminfoMap.put("medins_code", null);//	医疗机构编码
        adminfoMap.put("serial_no", null);//	就医登记号
        adminfoMap.put("opter", null);//	操作员工号
        adminfoMap.put("opter_name", null);//	操作员姓名
        adminfoMap.put("wardarea_no", null);//	入院病区编号
        adminfoMap.put("wardarea_no", null);//	入院病区名称
        adminfoMap.put("bed_type", null);// 	床位类型
        adminfoMap.put("old_ipt_no", null);//	原住院号
        adminfoMap.put("med_mdtrt_type", null);//	待遇类别
        adminfoMap.put("memo", null);//	备注
        adminfoMap.put("coner_adr", null);//	联系地址
        adminfoMap.put("adm_time", null);//	入院时间
        adminfoMap.put("opt_time", null);//	经办时间
        adminfoMap.put("clnc_flag", null);//	临床试验标志
        adminfoMap.put("advpay", null);//	预付款

        //代办人信息参数agnterinfo
        Map<String, Object> agnterinfoMap = new HashMap<>();

        agnterinfoMap.put("agnter_name", null);//	代办人姓名
        agnterinfoMap.put("agnter_rlts", null);//	代办人关系
        agnterinfoMap.put("agnter_cert_type", null);//	代办人证件类型
        agnterinfoMap.put("agnter_certno", null);//	代办人证件号码
        agnterinfoMap.put("agnter_tel", null);//	代办人联系电话
        agnterinfoMap.put("agnter_addr", null);//	代办人联系地址
        agnterinfoMap.put("agnter_photo", null);//	代办人照片


        //住院变更诊断信息参数diseinfoList
        InptVisitDTO inptVisitDTO = new InptVisitDTO();
        inptVisitDTO.setHospCode(hospCode);
        inptVisitDTO.setId(MapUtils.get(map,"id"));
        inptVisitDTO.setInsureRegCode(insureConfigurationDTO.getRegCode());
        map.put("inptVisitDTO",inptVisitDTO);
        List<InptDiagnoseDTO> inptDiagnoseDTOList = doctorAdviceService_consumer.queryInptDiagnose(map).getData();
        if(ListUtils.isEmpty(inptDiagnoseDTOList)){
            throw new AppException("转院信息变更的诊断不能为空");
        }
        //住院变更诊断信息参数diseinfoMap
        Map<String, Object> diseinfoMap = null;
        //入院诊断信息参数diseinfoList
        List<Map<String, Object>> diseinfoList = new ArrayList<Map<String, Object>>();
        for(int i=0;i<inptDiagnoseDTOList.size();i++){
            diseinfoMap = new HashMap<>();
            diseinfoMap.put("psn_no", psnNo);//	人员编号
            if("101".equals(inptDiagnoseDTOList.get(i).getTypeCode()) ||
                    "102".equals(inptDiagnoseDTOList.get(i).getTypeCode())||
                    "201".equals(inptDiagnoseDTOList.get(i).getTypeCode())||
                    "202".equals(inptDiagnoseDTOList.get(i).getTypeCode())||
                    "203".equals(inptDiagnoseDTOList.get(i).getTypeCode())){
                diseinfoMap.put("mdtrt_id", insureIndividualVisitDTO.getMedicalRegNo());//	诊断类别
                diseinfoMap.put("diag_type", "1");//	诊断类别
                diseinfoMap.put("maindiag_flag", inptDiagnoseDTOList.get(i).getIsMain());//	主诊断标志
                diseinfoMap.put("diag_srt_no", i);//	诊断排序号
                diseinfoMap.put("diag_code", inptDiagnoseDTOList.get(i).getInsureInllnessCode());//	诊断代码
                diseinfoMap.put("diag_name", inptDiagnoseDTOList.get(i).getInsureInllnessName());//	诊断名称
                diseinfoMap.put("adm_cond", null);//	入院病情
                diseinfoMap.put("diag_dept", inptDiagnoseDTOList.get(i).getInDeptName());//	诊断科室
                diseinfoMap.put("dise_dor_no",  inptDiagnoseDTOList.get(i).getPracCertiNo());//	诊断医生编码
                diseinfoMap.put("dise_dor_name",  inptDiagnoseDTOList.get(i).getZzDoctorName());//	诊断医生姓名
                diseinfoMap.put("diag_time",  DateUtils.format(inptDiagnoseDTOList.get(i).getCrteTime(),DateUtils.Y_M_DH_M_S));//	诊断时间
                diseinfoMap.put("medins_diag_code",orgCode);//	医疗机构诊断编码
                diseinfoList.add(diseinfoMap);
            }

        }

        //input信息参数inputMap
        Map<String, Object> inputMap = new HashMap<String, Object>();
        inputMap.put("adminfo", adminfoMap);
        inputMap.put("agnterinfo", agnterinfoMap);
        inputMap.put("diseinfo", diseinfoList);
        httpParam.put("input",inputMap);
        String json = JSONObject.toJSONString(httpParam);
        logger.info("住院信息变更入参:" + json);
        String url= insureConfigurationDTO.getUrl();
        String resultStr = HttpConnectUtil.unifiedPayPostUtil(url, json);
        if (StringUtils.isEmpty(resultStr)){
            throw new AppException("调用统一支付平台无响应!");
        }
        logger.info("住院信息变更回参:" + resultStr);
        Map<String,Object> resultMap = JSONObject.parseObject(resultStr,Map.class);
        if(!"0".equals(resultMap.get("infcode").toString())){
            throw new AppException((String)resultMap.get("err_msg"));
        }
        if("999".equals(resultMap.get("infcode").toString())){
            throw new AppException((String)resultMap.get("msg"));
        }
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
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(insureInptOutFeeDTO.getHospCode());
        insureConfigurationDTO.setRegCode((String) insureInptOutFeeDTO.getInsureOrgCode());
        insureConfigurationDTO.setOrgCode(insureIndividualVisitDTO.getMedicineOrgCode());
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);

        //出院信息参数dscginfo
        Map<String, Object> dataMap = new HashMap<>();
        String medicalRegNo = insureInptOutFeeDTO.getAaz217();
        String psnNo = insureInptOutFeeDTO.getAac001();
        dataMap.put("mdtrt_id",medicalRegNo);//	就诊ID
        dataMap.put("psn_no",psnNo);//	人员编号
        dataMap.put("medins_code",insureConfigurationDTO.getOrgCode());//	医疗机构编码
        dataMap.put("serial_no",insureInptOutFeeDTO.getAaz217());//	就医登记号

        //input信息参数inputMap
        Map<String, Object> inputMap = new HashMap<String, Object>();
        inputMap.put("data", dataMap);

        Map<String, Object> paramMap = new HashMap<>();
        String omsgId = StringUtils.createMsgId(insureConfigurationDTO.getOrgCode());
        String functionCode = "2404";
        paramMap.put("insuplc_admdvs",insureIndividualVisitDTO.getInsuplcAdmdvs());//参保地医保区划
        String medisCode = insureConfigurationDTO.getOrgCode();
        paramMap.put("medins_code",medisCode);//定点医药机构编号
        paramMap.put("infno",functionCode);//交易编号
        paramMap.put("input", inputMap);
        paramMap.put("msgid",omsgId);
        paramMap.put("mdtrtarea_admvs",insureConfigurationDTO.getMdtrtareaAdmvs());//参保地医保区划
        paramMap.put("insur_code",insureConfigurationDTO.getRegCode());//中心编码
        String redisKey = new StringBuilder().append(hospCode).append("-").append("2404").append("-").append(visitId).append("-").append(psnNo).toString();
        System.out.println("入院撤销------------------"+redisKey+"---------" + redisUtils.hasKey(redisKey));
        if(redisUtils.hasKey(redisKey)){
            throw new AppException("该患者正在办理入院撤销业务,请勿重复操作");
        }else{
            try {
                redisUtils.set(redisKey,redisKey,20);
                String paramJson = JSON.toJSONString(paramMap);
                logger.info("统一支付平台取消入院登记入参:" + paramJson);
                String resultStr = HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), paramJson);
                logger.info("统一支付平台取消入院登记回参:" + resultStr);
                map.put("medisCode",medisCode);
                map.put("visitId",visitId);
                map.put("msgId",omsgId);
                map.put("msgInfo",functionCode);
                map.put("msgName","取消医保入院登记");
                map.put("isHospital",Constants.SF.S) ;
                map.put("paramMapJson",paramJson);
                map.put("resultStr",resultStr);
                insureUnifiedLogService_consumer.insertInsureFunctionLog(map).getData();
                if (StringUtils.isEmpty(resultStr)){
                    throw new RuntimeException("调用统一支付平台无响应!");
                }
                Map<String,Object> m = JSONObject.parseObject(resultStr,Map.class);
                String resultCode = MapUtils.get(m,"infcode","");
                if (StringUtils.isEmpty(resultCode)){
                    throw new RuntimeException("调用统一支付平台无响应!");
                }
                if (!"0".equals(resultCode)){
                    throw new RuntimeException("调用统一支付平台错误,原因："+MapUtils.get(m,"err_msg",""));
                }
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
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setOrgCode(insureIndividualVisitDTO.getMedicineOrgCode());  // 医疗机构编码
        insureConfigurationDTO.setCode(insureIndividualVisitDTO.getInsureRegCode()); // 医保机构编码
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        medicalRegNo  = insureIndividualVisitDTO.getMedicalRegNo();
        String psnNo= insureIndividualVisitDTO.getAac001() ;
        map.put("mdtrt_id",medicalRegNo);
        map.put("psn_no",psnNo);
        //出院信息参数dscginfo
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("mdtrt_id", medicalRegNo);//	就诊ID
        dataMap.put("psn_no", psnNo);//	人员编号

        //input信息参数inputMap
        Map<String, Object> inputMap = new HashMap<String, Object>();
        inputMap.put("data", dataMap);

        Map<String, Object> paramMap = new HashMap<>();
        String omsgId = StringUtils.createMsgId(insureIndividualVisitDTO.getMedicineOrgCode());
        String functionCode = "2405";
        paramMap.put("infno",functionCode);//交易编号
        paramMap.put("insuplc_admdvs", insureIndividualVisitDTO.getInsuplcAdmdvs());//参保地医保区划
        paramMap.put("msgid",omsgId);
        paramMap.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs()); // 就医地医保区划
        String medisCode = insureIndividualVisitDTO.getMedicineOrgCode();
        paramMap.put("medins_code", medisCode);//定点医药机构编号
        paramMap.put("insur_code", insureConfigurationDTO.getRegCode()); // 医保中心编码
        paramMap.put("input", inputMap);
        String paramJson = JSON.toJSONString(paramMap);
        logger.info("医保统一支付平台出院办理撤销入参" + paramJson);
        // 设置redis的键
        String redisKey = new StringBuilder().append(hospCode).append("-").append("2405").append("-").append(visitId).append("-").append(psnNo).toString();
        System.out.println("出院办理撤销------------------"+redisKey+"---------" + redisUtils.hasKey(redisKey));
        // 判断键是否存在
        if(redisUtils.hasKey(redisKey)){
            throw new AppException("该患者正在别处出院办理撤销,请稍后");
        }
        else{
            try {
                // 设置键
                redisUtils.set(redisKey,medicalRegNo,60);
                String resultStr = HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), paramJson);
                logger.info("医保统一支付平台出院办理撤销回参" + resultStr);
                map.put("medisCode",medisCode);
                map.put("visitId",visitId);
                map.put("msgId",omsgId);
                map.put("msgInfo",functionCode);
                map.put("msgName","医保出院办理撤销");
                map.put("isHospital",Constants.SF.S) ;
                map.put("paramMapJson",paramJson);
                map.put("resultStr",resultStr);
                insureUnifiedLogService_consumer.insertInsureFunctionLog(map);
                if (StringUtils.isEmpty(resultStr)){
                    throw new AppException("调用统一支付平台无响应!");
                }
               Map<String, Object> m = JSONObject.parseObject(resultStr, Map.class);
               String resultCode = MapUtils.get(m,"infcode","");
                if (StringUtils.isEmpty(resultCode)){
                    throw new AppException("调用统一支付平台无响应!");
                }
                if (!"0".equals(resultCode)){
                    throw new AppException("调用统一支付平台错误,原因："+MapUtils.get(m,"err_msg",""));
                }
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
