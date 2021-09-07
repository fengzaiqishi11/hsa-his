package cn.hsa.insure.unifiedpay.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.insure.inpt.bo.InsureUnifiedPrescripBO;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureDiseaseMatchDTO;
import cn.hsa.module.outpt.prescribe.dao.OutptDoctorPrescribeDAO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.sys.parameter.dao.SysParameterDAO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSONObject;
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
 * @Package_name: cn.hsa.insure.unifiedpay.bo.impl
 * @class_name: InsureUnifiedPrescripImpl
 * @Description: 电子处方服务
 * @Author: LiJiGuang
 * @Email: jiguang.liao@powersi.com
 * @Date: 2021/9/6 10:08
 * @Company: 创智和宇
 **/
@Slf4j
@Component
public class InsureUnifiedPrescripImpl extends HsafBO implements InsureUnifiedPrescripBO {

    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;

    @Resource
    private OutptDoctorPrescribeDAO outptDoctorPrescribeDAO;

    @Resource
    private SysParameterDAO sysParameterDAO;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String API_ERROR = "电子处方服务接口调用提示";

    /**
     * @param map
     * @Method updatePrescripUpload_7101
     * @Desrciption 电子处方上传
     * @Param map
     * @Author LiaoJiGuang
     * @Date 2021/9/3 17:02
     * @Return
     */
    @Override
    public Map<String, Object> updatePrescripUpload_7101(Map<String, Object> map) {
        String visitId = MapUtils.get(map,"visit_id");
        String hospCode = MapUtils.get(map,"hospCode");
        String insureRegCode = MapUtils.get(map,"insureRegCode");
        if (StringUtils.isEmpty(insureRegCode)) {
            throw new AppException(API_ERROR + "【7101_医保机构编码不能为空！】");
        }

        // 根据就诊ID查询病人处方信息
        Map<String,Object> selectPreMap = new HashMap<>();
        selectPreMap.put("hospCode",hospCode);
        selectPreMap.put("visitId",visitId);
        List<Map<String, Object>> outptDoctorPrescribeList = outptDoctorPrescribeDAO.getPrescribe(selectPreMap);
        if (ListUtils.isEmpty(outptDoctorPrescribeList)) {
            throw new AppException(API_ERROR + "【7101_处方信息为空！】");
        }

        for (Map<String,Object> outptDocPreMap : outptDoctorPrescribeList) {
            Map<String,Object> inputParams = this.getPrescripParams(outptDocPreMap,selectPreMap);
            Map<String,Object> resultMap = this.toCommonUpay(hospCode,insureRegCode,"7101",inputParams);
        }
        return null;
    }

    /**
     * @param map
     * @Method updatePrescripAudit_7102
     * @Desrciption 处方审核结果反馈
     * @Param map
     * @Author LiaoJiGuang
     * @Date 2021/9/6 17:02
     * @Return
     */
    @Override
    public Map<String, Object> updatePrescripAudit_7102(Map<String, Object> map) {
        String hospCode = MapUtils.get(map,"hospCode");
        String insureRegCode = MapUtils.get(map,"insureRegCode");
        Map<String,Object> inputMap = new HashMap<>();
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("hi_rxno","");	            // 医保处方编号	字符型	30		Y
        dataMap.put("fixmedins_name","");	    // 医药机构名称（审方）	字符型	200		Y
        dataMap.put("fixmedins_code","");	    // 医药机构编号（审方）	字符型	20		Y
        dataMap.put("phar_cert_type","");	    // 药师证件类型	字符型	6	Y		参照人员证件类型
        dataMap.put("phar_certno","");	        // 药师证件号码	字符型	50
        dataMap.put("phar_name","");	        // 药师姓名	字符型	50		Y
        dataMap.put("phar_prac_cert_no","");	// 药师执业资格证号	字符型	50
        dataMap.put("rchk_phar_cert_type","");	// 复核药师证件类型	字符型	6			参照人员证件类型
        dataMap.put("rchk_phar_certno","");	// 复核药师证件编号	字符型	50		Y
        dataMap.put("rchk_phar_name","");	    // 复核药师姓名	字符型	40		Y
        dataMap.put("rx_chk_stas_codg","");	// 处方审核状态代码	字符型	10	Y	Y
        dataMap.put("rx_chk_opnn","");	        // 处方审核意见	字符型	2000
        dataMap.put("rx_chk_time","");	        // 处方审核时间	日期时间型			Y
        return this.toCommonUpay(hospCode,insureRegCode,"7102",inputMap);
    }

    /**
     * @param map
     * @Method updatePrescripAudit_7103
     * @Desrciption 处方购药结果反馈
     * @Param map
     * @Author LiaoJiGuang
     * @Date 2021/9/6 17:02
     * @Return
     */
    @Override
    public Map<String, Object> updatePrescripBuyMedicine_7103(Map<String, Object> map) {
        String hospCode = MapUtils.get(map,"hospCode");
        String insureRegCode = MapUtils.get(map,"insureRegCode");
        Map<String,Object> inputMap = new HashMap<>();

        // data 节点
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("hi_rxno","");	        // 医保处方编号	字符型	30		Y
        dataMap.put("fund_pay_sumamt","");	// 基金支付总额	数值型	16,2		Y
        dataMap.put("psn_part_amt","");	// 个人负担总金额	数值型	16,2		Y
        dataMap.put("acct_pay","");	    // 个人账户支出	数值型	16,2		Y
        dataMap.put("psn_cash_pay","");	// 个人现金支出	数值型	16,2		Y
        dataMap.put("setl_time","");	    // 结算时间	日期时间型			Y	yyyy-MM-dd HH:mm:ss
        inputMap.put("data",dataMap);

        // seltdelts节点 - 购药明细信息
        List<Map<String,Object>> seltdeltsList = new ArrayList<>();
        List<Map<String,Object>> hisSeltdeltsList = new ArrayList<>();
        for (Map<String,Object> seltdeltMap : hisSeltdeltsList) {
            Map<String,Object> seltdelts = new HashMap<>();
            seltdelts.put("medins_drug_no","");	// 医药机构药品编号	字符型	36
            seltdelts.put("drug_genname","");	// 通用名	字符型	100
            seltdelts.put("drug_prodname","");	// 药品商品名	字符型	100
            seltdelts.put("drug_dosform","");	// 剂型	字符型	50		Y
            seltdelts.put("drug_spec","");	    // 规格	字符型	50		Y
            seltdelts.put("drug_totlcnt","");	// 数量	数值型	16,4		Y
            seltdelts.put("aprvno","");	        // 批准文号	字符型	30
            seltdelts.put("bchno","");	        // 批次号	字符型	30
            seltdelts.put("fixmedins_code","");	// 定点医药机构编号(结算)	字符型	50		Y
            seltdelts.put("fixmedins_name","");	// 定点医药机构名称(结算)	字符型	50		Y
            seltdelts.put("manu_lot_num","");	// 生产批号	字符型	30
            seltdelts.put("prdr_name","");	    // 生厂厂家	字符型	100		Y
            seltdelts.put("pric","");	        // 单价	数值型	16,4		Y
            seltdelts.put("total_unit","");	    // 总计单位	字符型	20		Y
            seltdelts.put("sumamt","");	        // 合计金额	数值型	16,2		Y
            seltdeltsList.add(seltdelts);
        }
        inputMap.put("seltdelts",seltdeltsList);
        return this.toCommonUpay(hospCode,insureRegCode,"7103",inputMap);
    }

    /**
     * @param map
     * @Method updatePrescripRevoke_7104
     * @Desrciption 电子处方撤销
     * @Param map
     * @Author LiaoJiGuang
     * @Date 2021/9/6 17:02
     * @Return
     */
    @Override
    public Map<String, Object> updatePrescripRevoke_7104(Map<String, Object> map) {
        String hospCode = MapUtils.get(map,"hospCode");
        String insureRegCode = MapUtils.get(map,"insureRegCode");

        Map<String,Object> inputMap = new HashMap<>();
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("hi_rxno","");	          // 医保处方编号	字符型	30		Y
        dataMap.put("prsc_dr_name","");	      // 撤销医师姓名	字符型	50		Y
        dataMap.put("undo_dr_cert_type","");  // 撤销医师证件类型	字符型	6	Y		参照人员证件类型
        dataMap.put("undo_dr_certno","");	  // 撤销医师证件号码	字符型	50		Y
        dataMap.put("undo_rea","");	          // 撤销原因描述	字符型	200		Y
        dataMap.put("undo_time","");	      // 撤销时间	日期时间型			Y
        inputMap.put("data",dataMap);
        return this.toCommonUpay(hospCode,insureRegCode,"7104",inputMap);
    }

    /**
     * @param map
     * @Method updatePrescripPay_7105
     * @Desrciption 处方支付状态同步
     * @Param map
     * @Author LiaoJiGuang
     * @Date 2021/9/6 17:02
     * @Return
     */
    @Override
    public Map<String, Object> updatePrescripPay_7105(Map<String, Object> map) {
        String hospCode = MapUtils.get(map,"hospCode");
        String insureRegCode = MapUtils.get(map,"insureRegCode");

        Map<String,Object> inputMap = new HashMap<>();
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("hi_rxno","");	            // 医保处方编号	字符型	30		Y
        dataMap.put("hosp_rxno","");	        // 定点医疗机构处方编号	字符型	40		Y
        dataMap.put("rx_pay_status_code","");	// 处方支付状态	字符型	6	Y	Y	支付成功 字符限制，逻辑处理，便于扩展
        dataMap.put("pay_time","");	            // 支付时间	日期时间型			Y
        inputMap.put("data",dataMap);
        return this.toCommonUpay(hospCode,insureRegCode,"7105",inputMap);
    }

    /**
     * 封装入参
     * @param prescribeMap
     * @return
     */
    private Map<String, Object> getPrescripParams(Map<String,Object> prescribeMap,Map<String,Object> selectPreMap) {
        Map<String,Object> inputMap = new HashMap<>();
        String hospCode = MapUtils.get(selectPreMap,"hospCode");

        // data节点 - *************处方信息 - 单行数据*************
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("hosp_rxno",MapUtils.get(prescribeMap,"order_no"));	       // 定点医疗机构处方编号	字符型	40		Y
        dataMap.put("init_rxno","");	       // 续方的原处方编号	字符型	40
        dataMap.put("rx_type_code",this.getRxTypeCode(prescribeMap));	       // 处方类别代码	字符型	3	Y	Y
        dataMap.put("prsc_time",MapUtils.get(prescribeMap,"crte_time"));	       // 开方时间	日期时间型			Y
        dataMap.put("rx_drug_nums","");	       // 药品数量（剂数）	数值型	16,4		Y
        dataMap.put("rx_way_codg","");	       // 处方整剂用法编号	字符型	20	Y		参考药物使用-途径代码(drug_medc_way_code)
        dataMap.put("rx_way_name","");	       // 处方整剂用法名称	字符型	40
        dataMap.put("rx_freq_codg","");	       // 处方整剂频次编号	字符型	20	Y		参考使用频次（used_frqu）
        dataMap.put("rx_freq_name","");	       // 处方整剂频次名称	字符型	40
        dataMap.put("rx_dosunt","");	       // 处方整剂剂量单位	字符型	20
        dataMap.put("rx_doscnt","");	       // 处方整剂单次剂量数	数值型	16,2
        dataMap.put("rx_drord_dscr","");	   // 处方整剂医嘱说明	字符型	400
        Integer validDays = this.getValidDays(prescribeMap,hospCode);
        dataMap.put("valid_days",validDays);   // 处方有效天数	数值型	10		Y
        String validEndTime = DateUtils.format(DateUtils.dateAdd(MapUtils.get(prescribeMap,"crte_time"),validDays),DateUtils.Y_M_DH_M_S);
        dataMap.put("valid_end_time",validEndTime);	   // 有效截止时间	日期时间型			Y
        dataMap.put("rept_flag","");	       // 复用（多次）使用标志	字符型	3	Y		0-否、1-是
        dataMap.put("max_rept_cnt","");	       // 最大使用次数	数值型	3,0
        dataMap.put("reptd_cnt","");	       // 已使用次数	数值型	3,0
        dataMap.put("min_inrv_days","");	   // 使用最小间隔（天数）	数值型	3,0
        dataMap.put("dr_sign_info","");	       // 开方医生电子签名信息	字符型	2000
        dataMap.put("phar_sign_info","");	   // 审方药师电子签名信息	字符型	2000
        dataMap.put("fixmedins_sign_info",""); // 定点医疗机构签章信息	字符型	2000
        dataMap.put("rx_cotn_flag","0");	       // 续方标志	字符型	1	Y	Y
        dataMap.put("rx_file","-");	           // 处方原件	大文本			Y	原件base64后的字符，
        dataMap.put("rx_circ_flag","1");	       // 外购处方标志	字符型	3	Y	Y	0-不外购 1-外购，默认为1
        inputMap.put("data",dataMap);

        // rxdrugdetail结点 *************处方明细信息 - 多行数据*************
        List<Map<String,Object>> rxdrugdetailList = new ArrayList();
        List<OutptPrescribeDetailsDTO> outptPrescribeDTOList = new ArrayList();
        if (ListUtils.isEmpty(outptPrescribeDTOList)) {
            throw new AppException(API_ERROR + "【7101_处方明细信息不能为空！】");
        }

        for (OutptPrescribeDetailsDTO outptPrescribeDetailsDTO : outptPrescribeDTOList) {
            Map<String,Object> rxdrugMap = new HashMap<>();
            rxdrugMap.put("med_list_codg","");	    // 医疗目录编码	字符型	50		Y
            rxdrugMap.put("fixmedins_hilist_id","");	// 定点医药机构目录编号	字符型	30
            rxdrugMap.put("hosp_prep_flag","");	    // 医疗机构制剂标志	字符型	3	Y
            rxdrugMap.put("rx_item_type_code","");	// 处方项目分类代码	字符型	30	Y
            rxdrugMap.put("rx_item_type_name","");	// 处方项目分类名称	字符型	100
            rxdrugMap.put("tcmdrug_type_name","");	// 中药类别名称	字符型	20
            rxdrugMap.put("tcmdrug_type_code","");	// 中药类别代码	字符型	30	Y
            rxdrugMap.put("tcmherb_foote","");	    // 草药脚注	字符型	200
            rxdrugMap.put("medn_type_code","");	    // 药物类型代码	字符型	100	Y
            rxdrugMap.put("medn_type_name","");	    // 药物类型	字符型	100
            rxdrugMap.put("main_medc_flag","");	    // 主要用药标志	字符型	30
            rxdrugMap.put("urgt_flag","");	        // 加急标志	字符型	30
            rxdrugMap.put("bas_medn_flag","");	    // 基本药物标志	字符型	3	Y
            rxdrugMap.put("imp_drug_flag","");	    // 是否进口药品	字符型	3	Y		0-否 1-是
            rxdrugMap.put("prod_barc","");	        // 药品条形编码	字符型	40
            rxdrugMap.put("drug_prodname","");	    // 药品商品名	字符型	255
            rxdrugMap.put("genname_codg","");        // 通用名编码	字符型	50
            rxdrugMap.put("drug_genname","");	    // 药品通用名	字符型	500		Y
            rxdrugMap.put("chemname","");	        // 化学名称	字符型	100
            rxdrugMap.put("drugstdcode","");	    // 药品本位码	字符型	50
            rxdrugMap.put("drug_dosform","");	    // 药品剂型	字符型	30		Y
            rxdrugMap.put("drug_spec","");	        // 药品规格	字符型	40		Y
            rxdrugMap.put("prdr_name","");	        // 生厂厂家	字符型	100
            rxdrugMap.put("drug_pric","");	        // 药品单价	数值型	16,6
            rxdrugMap.put("drug_cnt","");           // 药品数量	数值型	16,4		Y
            rxdrugMap.put("drug_cnt_unit","");	    // 药品数量单位	字符型	20		Y
            rxdrugMap.put("drug_sumamt","");	    // 药品总金额	数值型	16,2
            rxdrugMap.put("medc_way_codg","");	    // 用药途径代码	字符型	10	Y	Y	参考药物使用-途径代码(drug_medc_way_code)
            rxdrugMap.put("medc_way_dscr","");	    // 用药途径描述	字符型	100		Y
            rxdrugMap.put("medc_starttime","");	    // 用药开始时间	日期时间型			Y
            rxdrugMap.put("medc_endtime","");	    // 用药结束时间	日期时间型			Y
            rxdrugMap.put("medc_days","");	        // 用药天数	数值型	8,2		Y
            rxdrugMap.put("drug_dosunt","");	    // 药品剂量单位	字符型	20
            rxdrugMap.put("sin_doscnt","");	        // 单次用量	数值型	16,2
            rxdrugMap.put("sin_dosunt","");	        // 单次剂量单位	字符型	20
            rxdrugMap.put("used_frqu_codg","");	    // 使用频次编码	字符型	10	Y		参考使用频次（used_frqu）
            rxdrugMap.put("used_frqu_name","");	    // 使用频次名称	字符型	30
            rxdrugMap.put("drug_totlnt","");	    // 用药总量	字符型	40
            rxdrugMap.put("drug_totlnt_emp","");	// 用药总量单位	字符型	40
            rxdrugMap.put("dise_codg","");	        // 病种编码	字符型	30	Y		按照标准代码填写：按病种结算病种目录代码(bydise_setl_list_code)、门诊慢特病病种目录代码(opsp_dise_cod)、日间手术病种目录代码(daysrg_dise_list_code)
            rxdrugdetailList.add(rxdrugMap);
        }
        inputMap.put("rxdrugdetail",rxdrugdetailList);

        // mdtrtinfo节点 - 门诊信息 - 多行数据
        List<Map<String,Object>> mdtrtinfoList = new ArrayList();
        List<OutptVisitDTO> outptVisitDTOList = new ArrayList<>();
        if (ListUtils.isEmpty(outptVisitDTOList)) {
            throw new AppException(API_ERROR + "【7101_门诊信息不能为空！】");
        }

        for (OutptVisitDTO outptVisitDTO : outptVisitDTOList) {
            Map<String,Object> mdtrtMap = new HashMap<>();
            mdtrtMap.put("mdtrt_id","");	        // 就诊ID	字符型	30		Y
            mdtrtMap.put("med_type","");	        // 医疗类别	字符型	6	Y	Y
            mdtrtMap.put("ipt_op_no","");	        // 住院/门诊号	字符型	30		Y
            mdtrtMap.put("psn_no","");	            // 人员编号	字符型	30		Y
            mdtrtMap.put("patn_name","");	        // 患者姓名	字符型	40		Y
            mdtrtMap.put("age","");	                // drug_res年龄	数值型	4,1		Y
            mdtrtMap.put("patn_ht","");	            // 患者身高	数值型	6,2			单位：cm
            mdtrtMap.put("patn_wt","");	            // 患者体重	数值型	6,2			单位：kg
            mdtrtMap.put("gend","");                // 性别	字符型	6	Y	Y
            mdtrtMap.put("geso_val","");	        // 妊娠(孕周)	数值型	2
            mdtrtMap.put("nwb_flag","");	        // 新生儿标志	字符型	3	Y		0-否、1-是
            mdtrtMap.put("nwb_age","");	            // 新生儿日、月龄	字符型	20
            mdtrtMap.put("suck_prd_flag","");	    // 哺乳期标志	数值型	3	Y		0-否、1-是
            mdtrtMap.put("algs_his","");	        // 过敏史	字符型	1000
            mdtrtMap.put("insuplc_admdvs","");	    // 参保地医保区划	字符型	6		Y
            mdtrtMap.put("psn_cert_type","");	    // 人员证件类型	字符型	6	Y	Y
            mdtrtMap.put("certno","");	            // 证件号码	字符型	50		Y
            mdtrtMap.put("insutype","");	        // 险种类型	字符型	6	Y	Y
            mdtrtMap.put("prsc_dept_name","");	    // 开方科室名称	字符型	50		Y
            mdtrtMap.put("prsc_dept_code","");	    // 开方科室编号	字符型	30		Y
            mdtrtMap.put("prsc_dr_name","");	    // 开方医师姓名	字符型	50		Y
            mdtrtMap.put("prsc_dr_cert_type","");	// 开方医师证件类型	字符型	6	Y		参照人员证件类型
            mdtrtMap.put("prsc_dr_certno","");	    // 开方医师证件号码	字符型	50
            mdtrtMap.put("dr_profttl_codg","");	    // 医生职称编码	字符型	20			参考开单医生编码
            mdtrtMap.put("dr_profttl_name","");	    // 医生职称名称	字符型	20
            mdtrtMap.put("phar_cert_type","");	    // 药师证件类型	字符型	6	Y		参照人员证件类型
            mdtrtMap.put("phar_certno","");	        // 药师证件号码	字符型	50
            mdtrtMap.put("phar_name","");	        // 药师姓名	字符型	50		Y
            mdtrtMap.put("phar_prac_cert_no","");	// 药师执业资格证号	字符型	50
            mdtrtMap.put("phar_chk_time","");       // 医疗机构药师审方时间	日期时间型			Y
            mdtrtMap.put("mdtrt_time","");	        // 就诊时间	日期时间型			Y
            mdtrtMap.put("dise_codg","");	        // 病种编码	字符型	30			按照标准编码填写：按病种结算病种目录代码(bydise_setl_list_code)、门诊慢特病病种目录代码(opsp_dise_cod)、日间手术病种目录代码(daysrg_dise_list_code)
            mdtrtMap.put("dise_name","");	        // 病种名称	字符型	500			　
            mdtrtMap.put("sp_dise_flag","");	    // 特殊病种标志	字符型	3	Y	Y
            mdtrtMap.put("diag_code","");	        // 主诊断代码	字符型	20		Y
            mdtrtMap.put("diag_name","");	        // 主诊断名称	字符型	40		Y
            mdtrtMap.put("dise_cond_dscr","");	    // 疾病病情描述	字符型	2000
            mdtrtMap.put("hi_feesetl_type","");	    // 医保费用结算类型	字符型	6	Y
            mdtrtMap.put("hi_feesetl_name","");	    // 医保费用类别名称	字符型	20
            mdtrtMap.put("rgst_fee","");	        // 挂号费	数值型	16,2		Y
            mdtrtMap.put("medfee_sumamt","");	    // 医疗费总额	数值型	16,2			本处方总金额
            mdtrtMap.put("fstdiag","");	            // 是否初诊	 字符型	3			0-否、1-是
            mdtrtinfoList.add(mdtrtMap);
        }
        inputMap.put("mdtrtinfo",mdtrtinfoList);


        // diseinfo节点 - 诊断信息 - 多行数据
        List<Map<String,Object>> diseinfoList = new ArrayList();
        List<InsureDiseaseMatchDTO> insureDiseaseMatchDTOList = new ArrayList();
        if (ListUtils.isEmpty(insureDiseaseMatchDTOList)) {
            throw new AppException(API_ERROR + "【7101_诊断信息不能为空！】");
        }

        for (InsureDiseaseMatchDTO insureDiseaseMatchDTO : insureDiseaseMatchDTOList) {
            Map<String,Object> diseinfoMap = new HashMap<>();
            diseinfoMap.put("diag_type","");	    // 诊断类别	字符型	3	Y	Y
            diseinfoMap.put("maindiag_flag","");	// 主诊断标志	字符型	3	Y	Y
            diseinfoMap.put("diag_srt_no","");	    // 诊断排序号	数值型	2		Y
            diseinfoMap.put("diag_code","");	    // 诊断代码	字符型	20	Y	Y
            diseinfoMap.put("diag_name","");	    // 诊断名称	字符型	100		Y
            diseinfoMap.put("diag_dept","");	    // 诊断科室	字符型	50		Y
            diseinfoMap.put("dise_dor_no","");	    // 诊断医生编码	字符型	30		Y
            diseinfoMap.put("dise_dor_name","");	// 诊断医生姓名	字符型	50		Y
            diseinfoMap.put("diag_time","");	    // 诊断时间	日期时间型			Y	yyyy-MM-ddHH:mm:ss
            diseinfoList.add(diseinfoMap);
        }
        inputMap.put("diseinfo",diseinfoList);

        return inputMap;
    }

    /**
     * 处方有效天数
     * @param prescribeMap
     * @param hospCode
     * @return
     */
    private Integer getValidDays(Map<String, Object> prescribeMap,String hospCode) {
        String typeCode = MapUtils.get(prescribeMap,"type_code"); // 处方类别

        String code = "MZCF_YXTS_PT";
        SysParameterDTO sysParameterDTO_PT = sysParameterDAO.getParameterByCode(hospCode,code);
        if (sysParameterDTO_PT == null) {
            throw new AppException(API_ERROR + "【7101_未维护普通门诊处方有效天数系统参数（MZCF_YXTS_PT）！】");
        }

        code = "MZCF_YXTS_JZ";
        SysParameterDTO sysParameterDTO_JZ = sysParameterDAO.getParameterByCode(hospCode,code);
        if (sysParameterDTO_JZ == null) {
            throw new AppException(API_ERROR + "【7101_未维护急诊处方有效天数系统参数（MZCF_YXTS_JZ）！】");
        }

        Integer validDays = Integer.valueOf(sysParameterDTO_PT.getValue());
        if ("2".equals(typeCode)) {
            validDays = Integer.valueOf(sysParameterDTO_JZ.getValue());
        }
        return validDays;
    }

    /**
     *  处方类别代码
     * 普通1 急诊2 儿科3 麻、精一4 精二5 贵重6 - typeCode
     * 西药1 材料2 中草药3 LIS4 PACS5 处置6 - prescribeTypeCode
     *
     * 1门诊西药 2门诊中药饮片 3急诊西药 4急诊中药饮片 5儿科西药
     6儿科中药饮片  7麻、精一 8精二 9中药饮片 10中成药 99其它 - rxTypeCode
     * @param prescribeMap
     * @return
     */
    private Object getRxTypeCode(Map<String, Object> prescribeMap) {
        String rxTypeCode;
        String typeCode = MapUtils.get(prescribeMap,"type_code"); // 处方类别
        String prescribeTypeCode = MapUtils.get(prescribeMap,"prescribe_type_code"); // 处方类型
        String key = typeCode + "|" +prescribeTypeCode;
        switch (key) {
            case "1|1":
                rxTypeCode = "1";
                break;
            case "1|3":
                rxTypeCode = "2";
                break;
            case "2|1":
                rxTypeCode = "3";
                break;
            case "2|3":
                rxTypeCode = "4";
                break;
            case "3|1":
                rxTypeCode = "5";
                break;
            case "3|2":
                rxTypeCode = "6";
                break;
            default:
                rxTypeCode = "99";
                break;
        }

        if ("4".equals(typeCode)) {
            rxTypeCode = "7";
        }

        if ("5".equals(typeCode)) {
            rxTypeCode = "8";
        }
        return rxTypeCode;
    }

    /**
     * @Desrciption 统一支付平台 - 统一调用方法
     * @param hospCode
     * @param insureRegCode
     * @param inputMap
     * @return
     */
    private Map<String, Object> toCommonUpay(String hospCode, String insureRegCode,String functionCode, Map<String, Object> inputMap) {
        //获得医保配置信息
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(insureRegCode);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);

        //封装统一支付接口入参
        Map httpParam = new HashMap();
        httpParam.put("infno", functionCode);  //交易编号
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号

        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        httpParam.put("input", inputMap);

        String jsonStr = JSONObject.toJSONString(httpParam);
        String url = insureConfigurationDTO.getUrl();

        logger.info("【"+functionCode + "】时间：" + DateUtils.format() + "  url:" + url + "   统一支付平台入参：" + jsonStr);
        String resultJson = HttpConnectUtil.sendPostToCall(url, jsonStr);
        if (StringUtils.isEmpty(resultJson)) {
            throw new AppException("系统异常，调用医保接口返参为空");
        }
        int lengthStr = resultJson.length() > 800 ? 800 : resultJson.length();
        logger.info("【"+functionCode + "】时间：" + DateUtils.getNow() + "  统一支付平台回参:" + resultJson.substring(0, lengthStr) + "......");
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson);

        if ("-1".equals(MapUtils.get(resultMap, "infcode"))) {
            throw new AppException(API_ERROR + "【" + MapUtils.get(resultMap, "err_msg"));
        }
        return resultMap;
    }
}
