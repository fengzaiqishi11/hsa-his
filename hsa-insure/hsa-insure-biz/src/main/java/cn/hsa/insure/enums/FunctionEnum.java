package cn.hsa.insure.enums;

import cn.hsa.insure.util.Constant;

/**
 * @ClassName FunctionEnum
 * @Deacription 接口功能号
 * @Author caizeming
 * @Date 2021/6/23 8:59
 * @Version 3.0
 */
public enum FunctionEnum {

    INSUR_BASE_INFO(Constant.UnifiedPay.REGISTER.UP_1101, "人员信息获取"),
    INSUR_FIXMEDINS_INFO(Constant.UnifiedPay.REGISTER.UP_1201, "医药机构信息获取"),
    DIAG_DIR(Constant.UnifiedPay.DOWNLOAD.UP_1307, "疾病与诊断目录"),
    OPRN_DIR(Constant.UnifiedPay.DOWNLOAD.UP_1308, "手术操作目录"),
    DISE_SETL_DIR(Constant.UnifiedPay.DOWNLOAD.UP_1310, "按病种付费病种目录"),
    DAYSRG_TRT_DIR(Constant.UnifiedPay.DOWNLOAD.UP_1311, "日间手术治疗病种目录"),
    HI_LIST(Constant.UnifiedPay.REGISTER.UP_1312, "医保目录信息查询"),
    DIR_MATCH_INFO(Constant.UnifiedPay.REGISTER.UP_1316, "医疗目录与医保目录匹配信息查询"),
    MEDICAL_DIR_MATCH_INFO(Constant.UnifiedPay.REGISTER.UP_1317, "医药机构目录匹配信息查询"),
    LMTPRIC_INFO(Constant.UnifiedPay.REGISTER.UP_1318, "医保目录限价信息查询"),
    SELFPAY_PROP_INFO(Constant.UnifiedPay.REGISTER.UP_1319, "医保目录先自付比例信息查询"),

    IDCARD_PASSWORD_CHECK(Constant.UnifiedPay.CARD.UP_1602, "医保身份证密码校验"),
    IDCARD_PASSWORD_UPDATE(Constant.UnifiedPay.CARD.UP_1603, "医保修改身份证密码"),

    INSUR_DICT_INFO(Constant.UnifiedPay.REGISTER.UP_1901, "医保字典信息查询"),
    OUTPATIENT_TREATMENT(Constant.UnifiedPay.REGISTER.UP_2001, "人员待遇享受检查"),
    OUTPATIENT_REG(Constant.UnifiedPay.OUTPT.UP_2201, "门诊挂号"),
    OUTPATIENT_REG_CANCEL(Constant.UnifiedPay.OUTPT.UP_2202, "门诊挂号撤销"),
    OUTPATIENT_VISIT(Constant.UnifiedPay.OUTPT.UP_2203, "门诊就诊信息上传"),
    OUTPATIENT_FEE_UPLOAD(Constant.UnifiedPay.OUTPT.UP_2204, "门诊费用明细上传"),
    OUTPATIENT_FEE_UPLOAD_BACK(Constant.UnifiedPay.OUTPT.UP_2205, "门诊费用明细撤销"),
    OUTPATIENT_PRE_SETTLE(Constant.UnifiedPay.OUTPT.UP_2206, "门诊预结算"),
    OUTPATIENT_SETTLE(Constant.UnifiedPay.OUTPT.UP_2207, "门诊结算"),
    OUTPATIENT_SETTLE_BACK(Constant.UnifiedPay.OUTPT.UP_2208, "门诊结算撤销"),
    INPATIENT_FEE_UPLOAD(Constant.UnifiedPay.INPT.UP_2301, "住院费用明细上传"),
    INPATIENT_FEE_UPLOAD_BACK(Constant.UnifiedPay.INPT.UP_2302, "住院费用明细撤销"),
    INPATIENT_PRE_SETTLE(Constant.UnifiedPay.INPT.UP_2303, "住院预结算"),
    INPATIENT_SETTLE(Constant.UnifiedPay.INPT.UP_2304, "住院结算"),
    INPATIENT_SETTLE_BACK(Constant.UnifiedPay.INPT.UP_2305, "住院结算撤销"),
    INPATIENT_IN(Constant.UnifiedPay.REGISTER.UP_2401, "入院登记"),
    INPATIENT_OUT(Constant.UnifiedPay.REGISTER.UP_2402, "出院登记"),
    INPATIENT_UPDATE(Constant.UnifiedPay.REGISTER.UP_2403, "入院变更"),
    INPATIENT_IN_BACK(Constant.UnifiedPay.REGISTER.UP_2404, "入院撤销"),
    INPATIENT_OUT_BACK(Constant.UnifiedPay.REGISTER.UP_2405, "出院办理撤销"),
    SPECIAL_ATTRIBUTE_UPLOAD(Constant.UnifiedPay.REGISTER.UP_2406, "就医特殊属性上传"),
    SPECIAL_ATTRIBUTE_QUERY(Constant.UnifiedPay.REGISTER.UP_2407, "就医特殊属性查询"),
    TRANS_HOSP_RECORD_UPLOAD(Constant.UnifiedPay.REGISTER.UP_2501, "转院备案"),
    TRANS_HOSP_RECORD_REVOCATION(Constant.UnifiedPay.REGISTER.UP_2502, "转院备案撤销"),
    OPSP_RECORD_UPLOAD(Constant.UnifiedPay.REGISTER.UP_2503, "人员慢特病备案"),
    OPSP_RECORD_BACK(Constant.UnifiedPay.REGISTER.UP_2504, "人员慢特病备案撤销"),
    OFFSET_TRANSACTION(Constant.UnifiedPay.REGISTER.UP_2601, "冲正交易"),
    BIRTH_RECORD(Constant.UnifiedPay.REGISTER.UP_2699, "生育备案申请"),
    GENERAL_LEDGER(Constant.UnifiedPay.OUTPT.UP_3201, "医药机构费用结算对总账"),
    GENERAL_LEDGER_DETAIL(Constant.UnifiedPay.OUTPT.UP_3202, "医药机构费用结算对明细账"),
    DIR_MATCH_UPLOAD(Constant.UnifiedPay.REGISTER.UP_3301, "目录对照上传"),
    DIR_MATCH_CANCEL(Constant.UnifiedPay.REGISTER.UP_3302, "目录对照撤销"),
    INSUR_DETP_UPLOAD(Constant.UnifiedPay.REGISTER.UP_3401, "医保科室信息上传"),
    INSUR_DETP_BATCH_UPLOAD(Constant.UnifiedPay.REGISTER.UP_3401A, "医保批量科室信息上传"),
    INSUR_DETP_UPDATE(Constant.UnifiedPay.REGISTER.UP_3402, "医保科室信息变更"),
    INSUR_DETP_BACK(Constant.UnifiedPay.REGISTER.UP_3403, "医保科室信息撤销"),
    DECLARE_CLE_BACK(Constant.UnifiedPay.OUTPT.UP_3203, "清算申请"),
    DECLARE_CLE_APPLY(Constant.UnifiedPay.OUTPT.UP_3204, "清算申请撤销"),
//    CLR_MONTH_SUM("3693", "月结算申请汇总信息查询"),
//    CLR_SUMMARIZES("3695", "获取清算汇总明细"),
//    INSUR_MED_RECONCILE("3698", "医保对账汇总查询"),
//    INSUR_CENTER_DETAIL("3701", "中心对账明细查询"),
//    CLR_FUND_DETAIL("3702", "获取基金明细信息"),
//    CLR_SETTLE_DETAIL("3703", "获取结算明细信息"),
//    CLR_APPR_DETAIL("3704", "获取拨付单信息"),

//    MR_PATIENT_SETTL_UPLOAD("4101", "上传结算清单信息"),
    FMI_OWNPAY_PATN_DELETE(Constant.UnifiedPay.REGISTER.UP_4204, "自费病人住院费用明细删除"),
    FMI_OWNPAY_PATN_UPLOD(Constant.UnifiedPay.REGISTER.UP_4261, "自费病人信息上传"),
    FMI_OWNPAY_PATN_LEDGER(Constant.UnifiedPay.REGISTER.UP_4262, "自费病人信息对账"),
    FMI_OWNPAY_PATN_LEDGER_DETAIL(Constant.UnifiedPay.REGISTER.UP_4263, "自费病人零报金额不符查询"),
    FMI_OWNPAY_PATN_INPUT_UPLOD(Constant.UnifiedPay.REGISTER.UP_4201A, "自费病人住院费用明细信息上传"),
    FMI_OWNPAY_PATN_DISE_UPLOD(Constant.UnifiedPay.REGISTER.UP_4202, "自费病人住院就诊和诊断信息上传"),
    FMI_OWNPAY_PATN_FINISH(Constant.UnifiedPay.REGISTER.UP_4203, "自费病人就诊以及费用明细上传完成"),
    FMI_OWNPAY_PATN_DISE_FEE_UPLOD(Constant.UnifiedPay.REGISTER.UP_4205, "自费病人门诊就医信息上传"),


    OUTPATIENT_DIAGNOSIS_TREATMENT_INFO (Constant.UnifiedPay.OUTPT.UP_4301, "门急诊诊疗记录"),
    OUTPATIENT_EMERGENCY_RESCUE_INFO (Constant.UnifiedPay.OUTPT.UP_4302, "急诊留观手术及抢救信息"),


    MR_PATIENT_SETTL_UPLOAD(Constant.UnifiedPay.REGISTER.UP_4101, "上传结算清单信息"),
    MR_PATIENT_SETTL_UPLOAD_A(Constant.UnifiedPay.REGISTER.UP_4101A, "上传结算清单信息"),
    MR_PATIENT_SETTL_UPLOAD_UPDATE(Constant.UnifiedPay.REGISTER.UP_4102, "上传结算清单信息状态修改"),
    MR_PATIENT_SETTL_UPLOAD_QUERY(Constant.UnifiedPay.REGISTER.UP_4103, "医疗保障基金结算清单信息查询"),
    INSUR_MRI_UPLOAD(Constant.UnifiedPay.INPT.UP_4401, "住院病案首页上传"),
    INSUR_EMR_UPLOAD(Constant.UnifiedPay.REGISTER.UP_4701, "上传电子病历信息"),

    CLINICAL_EXAMINATION_UPLOAD(Constant.UnifiedPay.REGISTER.UP_4501,"临床检查报告记录"),
    CLINICAL_CHECKOUT_UPLOAD(Constant.UnifiedPay.REGISTER.UP_4502,"临床检验报告记录"),
    BACTERIAL_REPORT_UPLOAD(Constant.UnifiedPay.REGISTER.UP_4503,"细菌培养报告记录"),
    DRUGSENSITIVE_REPORT_UPLOAD(Constant.UnifiedPay.REGISTER.UP_4504,"药敏记录报告记录"),
    PATHOLOGICAL_REPORT_UPLOAD(Constant.UnifiedPay.REGISTER.UP_4505,"病理检查报告记录"),

    INSUR_DETP_INFO(Constant.UnifiedPay.REGISTER.UP_5101, "医保科室信息查询"),
    INSUR_PSN_INFO(Constant.UnifiedPay.REGISTER.UP_5102, "医保人员信息查询"),
    MDTRT_INFO(Constant.UnifiedPay.REGISTER.UP_5201, "就诊信息查询"),
    DISE_INFO(Constant.UnifiedPay.REGISTER.UP_5202, "诊断信息查询"),
    SETTLE_INFO(Constant.UnifiedPay.REGISTER.UP_5203, "结算信息查询"),
    COST_INFO(Constant.UnifiedPay.REGISTER.UP_5204, "费用明细查询"),
    OPSP_DRUG_INFO(Constant.UnifiedPay.REGISTER.UP_5205, "人员慢特病用药记录查询"),
    PSN_CUMULATION_INFO(Constant.UnifiedPay.REGISTER.UP_5206, "人员累计信息查询"),
    INSUR_SETTLE_DOWNLOAD(Constant.UnifiedPay.OUTPT.UP_5265, "结算单下载"),
    INSUR_SETTLE_DOWNLOAD_MZ(Constant.UnifiedPay.OUTPT.UP_5269, "异地门诊结算单下载"),
    INSUR_SETTLE_DOWNLOAD_ZY(Constant.UnifiedPay.OUTPT.UP_5270, "异地住院结算单下载"),
    OPSP_RECORD_INFO(Constant.UnifiedPay.REGISTER.UP_5301, "人员慢特病备案查询"),
    PSN_FIX_MEDIN_INFO(Constant.UnifiedPay.REGISTER.UP_5302, "人员定点信息查询"),
    IN_HOSP_INFO(Constant.UnifiedPay.REGISTER.UP_5303, "在院信息查询"),
    TRANS_HOSP_INFO(Constant.UnifiedPay.REGISTER.UP_5304, "转院信息查询"),
    PSN_POLICY(Constant.UnifiedPay.REGISTER.UP_100001, "政策项查询"),
    ONLINE_FEE_PAY(Constant.UnifiedPay.REGISTER.UP_6201, "线上费用明细上传"),
    ORD_QUERY_PAY(Constant.UnifiedPay.REGISTER.UP_6301, "医保订单结算结果查询"),
    ONLINE_FEE_REVOKE(Constant.UnifiedPay.REGISTER.UP_6401, "医保订单结算结果查询"),
    INSURE_REFUND(Constant.UnifiedPay.REGISTER.UP_6203, "医保退费"),
    //接口功能号 请按顺序填写

    LOCAL_SETTLE_DOWNLOAD("999999", "本地结算单下载"),

    POLICY_ITEM_INFO("100001", "政策项查询");
    private final String code;
    private final String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static String getValue(String code) {
        for (FunctionEnum ele : values()) {
            if (ele.getCode().equals(code)) {
                return ele.getDesc();
            }
        }
        return null;
    }

    FunctionEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static FunctionEnum getEnum(String code) {
        for (FunctionEnum sign : FunctionEnum.values()) {
            if (sign.getCode().equals(code)) {
                return sign;
            }
        }
        return null;
    }

}
