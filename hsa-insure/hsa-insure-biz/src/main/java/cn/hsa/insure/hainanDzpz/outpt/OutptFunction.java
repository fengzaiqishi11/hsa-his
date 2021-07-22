package cn.hsa.insure.hainanDzpz.outpt;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.insure.util.RequestInsure;
import cn.hsa.module.insure.module.dao.InsureIndividualBusinessDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualVisitDAO;
import cn.hsa.module.insure.module.dao.InsureItemMatchDAO;
import cn.hsa.module.insure.module.dto.InsureIndividualBusinessDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.dto.InsureItemMatchDTO;
import cn.hsa.module.insure.module.dto.InsureOutptOutFeeDTO;
import cn.hsa.module.insure.module.entity.InsureIndividualCostDO;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.util.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Package_name: cn.hsa.insure.hainanDzpz.outpt
 * @Class_name: OutptFunction
 * @Describe(描述): 门诊调用海南电子凭证服务
 * @Author: zengfeng
 * @Eamil: zengfeng@powersi.com.cn
 * @Date: 2020/11/04 11:46
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */

@Component("hainanDzpz-outpt")
public class OutptFunction {

    @Resource
    private RequestInsure requestInsure;

    @Resource
    private InsureIndividualVisitDAO insureIndividualVisitDAO;

    @Resource
    private InsureIndividualBusinessDAO insureIndividualBusinessDAO;

    @Resource
    private InsureItemMatchDAO insureItemMatchDAO;

    /**
     * @Menthod bizh110001
     * @Desrciption 门诊取人员信息
     * @param param 请求医保参数
     * @Author Ou·Mr
     * @Date 2020/11/4 11:49 
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String,Object> bizh110001(LinkedHashMap<String,Object> param){
        String regCode = (String) param.get("regCode");
        param.put("function_id", Constant.Xiangtan.OUTPT.BIZH110001);//功能号
        param.put("akb020",regCode);//医疗机构编码
        param.put("aaa027",regCode);//医保中心编码
        Map<String,Object> httpResult = requestInsure.call((String)param.get("hospCode"),regCode,param);
        return httpResult;
    }

    /**
     * @Menthod bizh110104_ss
     * @Desrciption 门诊费用上传并试算
     * 必填项: 医院编码(hospCode)、医院名称（hospName）、就诊id(visitId)、费用数据(fees)、
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/5 9:19
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String,Object> bizh110104_ss(HashMap<String,Object> param){
        String hospCode = (String) param.get("hospCode");//医院编码
        String hospName = (String) param.get("hospName");//医院名称
        String visitId = (String) param.get("visitId");//就诊id
        String settleId = (String) param.get("settleId");//结算id
        String crteId = (String) param.get("crteId");//创建人id
        String crteName = (String) param.get("crteName");//创建人姓名
        InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
        insureIndividualVisitDTO.setHospCode(hospCode);//医院编码
        insureIndividualVisitDTO.setVisitId(visitId);//就诊id
        List<InsureIndividualVisitDTO> insureIndividualVisitDTOList = insureIndividualVisitDAO.findByCondition(insureIndividualVisitDTO);
        if (insureIndividualVisitDTOList == null || insureIndividualVisitDTOList.isEmpty()){
            throw new AppException("未找到医保个人就诊信息。");
        }
        insureIndividualVisitDTO = insureIndividualVisitDTOList.get(0);
        InsureIndividualBusinessDTO insureIndividualBusinessDTO = new InsureIndividualBusinessDTO();
        insureIndividualBusinessDTO.setHospCode(hospCode);//医院编码
        insureIndividualBusinessDTO.setVisitId(visitId);//就诊id
        List<InsureIndividualBusinessDTO> insureIndividualBusinessDTOList = insureIndividualBusinessDAO.findByCondition(insureIndividualBusinessDTO);
        if (insureIndividualBusinessDTOList != null && !insureIndividualBusinessDTOList.isEmpty()){
            insureIndividualBusinessDTO = insureIndividualBusinessDTOList.get(0);
        }
        Map<String,Object> httpParam = new HashMap<String,Object>();
        httpParam.put("function_id",Constant.Xiangtan.OUTPT.BIZH110104);
        httpParam.put("aaa027",insureIndividualVisitDTO.getInsureOrgCode());//医保中心编码
        httpParam.put("akb020",insureIndividualVisitDTO.getMedicineOrgCode());//医疗机构编码
        httpParam.put("akb021",hospName);//医疗机构名称
        httpParam.put("aac001",insureIndividualVisitDTO.getAac001());//个人电脑号
        httpParam.put("aka130",insureIndividualVisitDTO.getAka130());//业务类型
        httpParam.put("bka006",insureIndividualVisitDTO.getBka006());//待遇类型
        httpParam.put("aae030",DateUtils.format(insureIndividualVisitDTO.getVisitTime(),DateUtils.YMD));//就诊发生日期
        httpParam.put("aae011",insureIndividualVisitDTO.getCrteId());//工号
        httpParam.put("bka015",insureIndividualVisitDTO.getCrteName());//工号姓名
        httpParam.put("bka021",insureIndividualVisitDTO.getVisitAreaId());//病区编码
        httpParam.put("bka022",insureIndividualVisitDTO.getVisitAreaName());//病区名称
        httpParam.put("akf001",insureIndividualVisitDTO.getVisitDrptId());//就诊科室
        httpParam.put("bka020",insureIndividualVisitDTO.getVisitDrptName());//就诊科室名称

        //TODO 诊断
        httpParam.put("akc193",insureIndividualVisitDTO.getVisitIcdCode());//诊断编码
        //TODO 诊断名称
        httpParam.put("bkz101",insureIndividualVisitDTO.getVisitIcdName());//诊断名称

        httpParam.put("aaz267",insureIndividualBusinessDTO.getAaz267());//门慢申请序号门诊选点号
        httpParam.put("bka042",insureIndividualBusinessDTO.getBearNo());//工伤生育凭证号
        httpParam.put("bka893",param.get("bka893") == null ? Constant.Xiangtan.DICT.BCBZ_SS : param.get("bka893"));//保存标志
        httpParam.put("bka894",param.get("bka894") == null ? Constant.Xiangtan.DICT.JSBZ_SF : param.get("bka894"));//结算标识
        httpParam.put("aaz217",insureIndividualVisitDTO.getMedicalRegNo());//就医登记号
        httpParam.put("bkc290",null);//照片base64编码
        List<InsureIndividualCostDO> insureIndividualCostDOS = null;
        Boolean isSettle = Constant.Xiangtan.DICT.JSBZ_SF.equals(param.get("bka894"));//是否结算
        // 自定义，1 表示退费数据上传
        if ("1".equals(param.get("type"))) {
            httpParam.put("bka893",param.get("bka893"));
            httpParam.put("bka894",Constant.Xiangtan.DICT.JSBZ_TF); // 退费
            httpParam.put("aaz217",param.get("aaz217"));

            List<Map<String,Object>> feesList = (List<Map<String,Object>>) param.get("fees");
            if (!ListUtils.isEmpty(feesList)) {
                for (Map<String,Object> fee : feesList) {
                    // 总费用
                    BigDecimal money = new BigDecimal(String.valueOf(fee.get("aae019")));
                    // 总用量
                    BigDecimal num = new BigDecimal(String.valueOf(fee.get("akc226")));
                    fee.put("aae019", BigDecimalUtils.negate(money));
                    fee.put("akc226",BigDecimalUtils.negate(num));
                }
                httpParam.put("feeinfo",feesList);
            }
        } else {
            int len = 1;
            List<OutptCostDTO> feesList = (List<OutptCostDTO>) param.get("fees");
            List<Map<String,Object>> feeinfoList = new ArrayList<Map<String,Object>>();
            if (feesList != null && !feesList.isEmpty()){
                List<String> itemIds = new ArrayList<String>();
                feesList.stream().forEach(outptCostDTO -> {itemIds.add(outptCostDTO.getItemId());});
                Map<String,Object> itemMatchParam = new HashMap<String,Object>();
                itemMatchParam.put("hospCode",hospCode);
                itemMatchParam.put("isValid",Constants.SF.S);
                itemMatchParam.put("isMatch",Constants.SF.S);
                itemMatchParam.put("itemIds",itemIds);
                List<InsureItemMatchDTO> insureItemMatchDTOList = insureItemMatchDAO.queryByHospItemId(itemMatchParam);
                if (insureItemMatchDTOList != null && !insureItemMatchDTOList.isEmpty()){
                    insureIndividualCostDOS = new ArrayList<InsureIndividualCostDO>();
                    for (OutptCostDTO item : feesList){
                        for (InsureItemMatchDTO insureItemMatchDTO : insureItemMatchDTOList){
                            if (StringUtils.isNotEmpty(item.getItemId()) && item.getItemId().equals(insureItemMatchDTO.getHospItemId())
                                    && StringUtils.isNotEmpty(item.getItemCode()) && item.getItemCode().equals(insureItemMatchDTO.getHospItemType())){
                                Map<String,Object> fee = new HashMap<String,Object>();
                                /* 费用信息（Feeinfo） */
                                fee.put("ake005",insureItemMatchDTO.getHospItemCode());//医院药品项目编码
                                fee.put("ake006",insureItemMatchDTO.getHospItemName());//医院药品项目名称
                                fee.put("aka070",insureItemMatchDTO.getHospItemPrepCode());//剂型
                                fee.put("bka073",insureItemMatchDTO.getManufacturer());//厂家
                                fee.put("aka074",insureItemMatchDTO.getHospItemSpec());//规格
                                fee.put("ake007",DateUtils.format(item.getCrteTime(),DateUtils.YMD));//费用发生日期
                                fee.put("aka067",item.getDosage());//计量单位
                                fee.put("bka040",item.getPrice());//单价
                                fee.put("akc226",item.getTotalNum());//用量
                                fee.put("aae019",item.getRealityPrice());//金额
                                fee.put("aaz213",len++);//费用序号
                                fee.put("bka070",item.getOpId());//处方号
                                fee.put("bka074",item.getDoctorId());//处方医生编号
                                fee.put("bka075",item.getDoctorName());//处方医生姓名
                                fee.put("bka071",item.getId());//医院费用的唯一标识
                                feeinfoList.add(fee);
                                if (isSettle){
                                    InsureIndividualCostDO insureIndividualCostDO = new InsureIndividualCostDO();
                                    insureIndividualCostDO.setId(SnowflakeUtils.getId());//id
                                    insureIndividualCostDO.setHospCode(hospCode);//医院编码
                                    insureIndividualCostDO.setVisitId(visitId);//就诊id
                                    insureIndividualCostDO.setSettleId(settleId);//结算id
                                    insureIndividualCostDO.setIsHospital(Constants.SF.F);//是否住院
                                    insureIndividualCostDO.setItemType(insureItemMatchDTO.getInsureItemType());//对应医保项目类别
                                    insureIndividualCostDO.setItemCode(insureItemMatchDTO.getInsureItemCode());//对应医保项目编码
                                    insureIndividualCostDO.setItemName(insureItemMatchDTO.getInsureItemName());//对应医保项目名称
                                    insureIndividualCostDO.setGuestRatio(null);//自付比例
                                    insureIndividualCostDO.setPrimaryPrice(null);//原费用
                                    insureIndividualCostDO.setApplyLastPrice(null);//报销后费用
                                    insureIndividualCostDO.setOrderNo(null);//顺序号
                                    insureIndividualCostDO.setTransmitCode(Constants.SF.S);//传输标志
                                    insureIndividualCostDO.setCrteId(crteId);//创建人id
                                    insureIndividualCostDO.setCrteName(crteName);//创建姓名
                                    insureIndividualCostDO.setCrteTime(new Date());//创建时间
                                    insureIndividualCostDOS.add(insureIndividualCostDO);
                                }
                            }
                        }
                    }
                }
            }
            httpParam.put("feeinfo",feeinfoList);
            if (len == 1){
                throw new AppException("费用在医保中心未审核或者未匹配。");
            }
        }
        Map<String,Object> httpResult = requestInsure.call((String)param.get("hospCode"),(String) param.get("insureRegCode"),httpParam);
        if (httpResult.containsKey("payinfo")){
            List<Map<String,String>> payinfoList = (List<Map<String, String>>) httpResult.get("payinfo");
            httpResult.put("payinfo",payinfoList.get(0));
        }
        if (isSettle){httpResult.put("insureIndividualCostDOList",insureIndividualCostDOS);}
        return httpResult;
    }

    /**
     * @Menthod bizh110104_js
     * @Desrciption 门诊费用上传并结算
     * @param param 请求参数
     * 必填项: 医院编码(hospCode)、医院名称（hospName）、就诊id(visitId)、费用数据(fees)
     * @Author Ou·Mr
     * @Date 2020/11/20 14:21
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String,Object> bizh110104_js(HashMap<String,Object> param){
        param.put("bka893",Constant.Xiangtan.DICT.BCBZ_JS);
        param.put("bka894",Constant.Xiangtan.DICT.JSBZ_SF);
        return bizh110104_ss(param);
    }

    /**
     * @Menthod BIZH110103
     * @Desrciption 退费时提取门诊业务信息
     * @param insureOutptOutFeeDTO 请求医保参数
     * @Author 廖继广
     * @Date 2020/11/5 09：18
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String,Object> bizh110003 (InsureOutptOutFeeDTO insureOutptOutFeeDTO) {
        if (StringUtils.isEmpty(insureOutptOutFeeDTO.getMedicalRegNo())) {
            throw new AppException("就医登记号不能为空");
        }
        /* 封装入参 */
        Map<String, Object> httpParam = new HashMap<>();
        httpParam.put("function_id", Constant.Xiangtan.OUTPT.BIZH110103);
        httpParam.put("aka130", insureOutptOutFeeDTO.getAka130());
        httpParam.put("akb020", insureOutptOutFeeDTO.getAkb020());
        httpParam.put("bka001", insureOutptOutFeeDTO.getBka001());
        httpParam.put("bka006", insureOutptOutFeeDTO.getBka006());
        httpParam.put("bka895", "aaz217");
        httpParam.put("bka896", insureOutptOutFeeDTO.getMedicalRegNo());

        // httpParam.put("aaz217", "RC004420201130000010");
        // httpParam.put("aaz217", insureOutptOutFeeDTO.getAaz217());

        /* 医保接口请求 */
        Map<String,Object> resultMap =  requestInsure.call(insureOutptOutFeeDTO.getHospCode(),insureOutptOutFeeDTO.getInsureRegCode(),httpParam);

        /* 返回结果集处理 */
        Integer returnCode = Integer.valueOf(resultMap.get("return_code").toString());
        if (returnCode < 0) {
            throw new AppException("医保退费时提取门诊业务信息失败,远程调用号（" + Constant.Xiangtan.OUTPT.BIZH110103 + "）:【 " + resultMap.get("msg") + "】");
        }
        return this.doResultMap(resultMap,insureOutptOutFeeDTO);
    }

    /**
     * 医保处理回参方法 by 廖继广 on 2020/11/5 10:32
     * * @return
     */
    private Map<String, Object> doResultMap(Object object,InsureOutptOutFeeDTO insureOutptOutFeeDTO) {
        HashMap<String,Object> repMap = new HashMap();
        if (object == null) {
            return null;
        }

        Map map = (Map)object;
        List<Map<String,Object>> bizList = (List)map.get("bizinfo");
        if (ListUtils.isEmpty(bizList)) {
            throw new AppException("获取医保退费业务信息失败，请联系管理人员排查");
        }

        // 形式一处理方式(通过就医登记号作为参数重新调用本function_id)
        if (bizList.size() > 1) {

        }

        List<Map<String,Object>> feeList =  (List)map.get("feeinfo");
        if (ListUtils.isEmpty(feeList)) {
            return null;
        }
        repMap.put("feeList",feeList);
        repMap.put("bizList",bizList);
        return repMap;
    }

}
