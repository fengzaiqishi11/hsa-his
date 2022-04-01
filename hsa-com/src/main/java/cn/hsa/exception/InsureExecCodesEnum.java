package cn.hsa.exception;


/**
 * @author powersi
 */

public enum InsureExecCodesEnum implements BizExcCodes {

    INSURE_RETURN_DATA_EMPTY(-1, SYS_MSG_OUT+"{1}返回数据为空"),

    INSURE_REQUEST_DATA_EMPTY(-1, SYS_MSG_OUT+"{1}参数为空"),

    INSUR_INTF_FAILURE(-1, OTHER_SYS_ERR_MSG+"流水号-{1},医保业务功能号 {2}-{3}, 错误异常信息:{4}"),

    ATDDR_NO_EMPTY(-1, SYS_MSG_INNER+"住院就诊号{1}的主治医生编码为空,请先安床"),

    IN_HOSP_DIAG_EMPTY(-1, SYS_MSG_INNER+"住院就诊号{1}的入院诊断为空"),

    OUT_HOSP_DIAG_EMPTY(-1, SYS_MSG_INNER+"住院就诊号{1}的出院诊断为空"),

    IN_HOSP_FEE_DATA_EMPTY(-1, SYS_MSG_INNER + "住院就诊号{1}的患者没有可以上传的医保费用数据！");


    /**
     * 100000-1000019 基础异常码

    INSUR_SYS_FAILURE(-1, insur_err_msg+"{0}"),

    HIS_SYS_FAILURE(-1, his_msg+"{1}"),
    HIS_RETURN_DATA_EMPTY(-1, his_msg+"{1} 返回数据为空"),

    SYS_FAILURE(-1, sys_msg+"{0}"),

    PARAM_ASSEMBLY_FAILURE(-1, sys_msg+"参数组装失败"),
    EXCEL_IMPORT_FAILURE(-1, sys_msg+"Excel导入转换失败"),
    USER_ACCOUNT_EMPTY(-1, sys_msg+"用户长时间未操作系统，登录用户信息已失效，请重新登录"),
    USER_EMPTY(-1, sys_msg+"该用户不存在或者未登录"),
    USER_ROLE_EMPTY(-1, sys_msg+"获取用户角色异常"),

    PARAM_CHANGE_ERROR(-1,sys_msg + "{0},系统配置参数异常:{1}"),

    PRIMARY_KEY_EMPTY(-1, sys_msg+"{0}主键数据为空"),
    IPT_NO_EMPTY(-1, sys_msg+"住院号为空"),
    VISIT_ID_EMPTY(-1, sys_msg+"HIS就诊号为空"),
    MDTRT_ID_EMPTY(-1, sys_msg+"医保就诊ID（mdtrtId）为空"),
    PSN_NO_EMPTY(-1, sys_msg+"医保人员编号(psnNo)为空"),
    MAP_RLTS_EMPTY(-1, sys_msg+"{0},{1}的目录编码匹配结果不存在"),
    DSCG_MAIN_DIAG_CODE_EMPTY(-1, sys_msg+"{0}的{1}住院主诊断代码为空"),
    ATDDR_NO_EMPTY(-1, sys_msg+"{0}的{1}主治医生编码为空"),
    DIAG_CODE_EMPTY(-1, sys_msg+"{0}诊断代码为空"),
    DISE_DOR_NO_EMPTY(-1, sys_msg+"{0}诊断医生编码为空"),
//    HOSP_STAFF_INFO_EMPTY(100032, "医执人员信息为空"),
//    HOSP_DEPT_INFO_EMPTY(100033, "科室信息为空"),
//    DIAG_DEPT_EMPTY(100034, "诊断科室编码为空"),
    BILG_DR_CODG_EMPTY(-1, sys_msg+"{0}费用的开单医生编码为空"),
    DIAG_STATUS_EMPTY(-1, sys_msg+"诊断状态为空"),
//    IPT_OTP_NO_EMPTY(100037, "门诊号为空"),
    CERT_NO_EMPTY(-1, sys_msg+"身份证号为空"),
//    MDTRT_SN_EMPTY(100039, "挂号流水号为空"),
    DIAG_NAME_EMPTY(-1,sys_msg+"{0}医保主诊断缺失，通过匹配关系无法找到{1}匹配数据"),
    MDTRT_CERT_TYPE_EMPTY(-1,sys_msg+"就诊凭证类型为空"),
    MDTRT_CERT_NO_EMPTY(-1,sys_msg+"就诊凭证编号为空"),
    CARD_SN_EMPTY(-1,sys_msg+"卡识别码为空"),
    STATUS_EMPTY(-1, sys_msg+"{0}状态为空，未初始化"),
    MSG_HEADER_IS_NOT_NULL(-1, sys_msg+"消息头参数{0}不能为空"),
    BEGIN_DATE_EMPTY(-1, sys_msg+"查询条件开始时间为空"),
    END_DATE_EMPTY(-1, sys_msg+"查询条件结束时间为空"),
    INSUTYPE_EMPTY(-1, sys_msg+"险种类型为空"),
    MED_TYPE_EMPTY(-1, sys_msg+"医疗类别为空"),
    LIMT_USED_INFO_EMPTY(-1, sys_msg + "{0}的限制药用药标志信息不存在"),
    RECONCILE_CHS_ONGOING(-1, sys_msg + "数据对比-同步医保数据正在进行，请稍后处理"),
    RECONCILE_HIS_ONGOING(-1, sys_msg + "数据对比-同步HIS数据正在进行，请稍后处理"),
    GENERAL_LEDGER_ONGOING(-1, sys_msg + "医保对账-对总账正在进行，请稍后处理"),
    GENERAL_LEDGER_DETAIL_ONGOING(-1, sys_msg + "医保对账-对明细账正在进行，请稍后处理"),


//    IN_HOSP_REGISTER_FAILURE(1000500, "入院登记失败"),
    IN_HOSP_REGISTER_ALREADY(-1, sys_msg+"住院号{0}入院登记已上传"),
    IN_HOSP_REGISTER_NOT_UPLOAD(-1, sys_msg+"住院号{0}入院登记未上传"),
    IN_HOSP_REGISTER_FEE_UPLOAD_EXIST(-1, sys_msg+"住院号{0}该次入院登记已上传费用明细"),
    IN_HOSP_REGISTER_RECORD_EMPTY(-1, sys_msg+"{0}，入院登记记录不存在，请先进行入院登记"),
    IN_HOSP_REGISTER_EMPTY(-1, sys_msg+"{0}，入院登记记录不存在"),
    IN_HOSP_REGISTER_DISE_COUNT_ERROR(-1, sys_msg+"入院主诊断数量只能有一条"),
    IN_HOSP_DELETE_REGISTER_DISE_ERROR(-1, sys_msg+"只能删除副诊断"),
    IN_HOSP_DELETE_DISE_ERROR(-1, sys_msg+"只能删除平台录入诊断"),
    IN_HOSP_DIAG_EMPTY(-1, sys_msg+"{0}诊断为空"),
    IS_NOT_CONSISTENT(-1, sys_msg+"人证不一致"),
    ATDDR_HOSP_STAFF_INFO_EMPTY(-1, sys_msg+"入院登记【主治医生】医执人员信息执业人员代码为空"),
    IN_HOSP_DIAG_DOR_STAFF_INFO_EMPTY(-1, sys_msg+"入院诊断【诊断医生】医执人员信息执业人员代码为空"),
    IS_READLY_SETTLE(-1,sys_msg+"{0}，人员已结算"),
    IN_HOSP_VIR_DOC_ERROR(-1,"虚拟信息配置有误：{0}"),
    IN_HOSP_NOT_UPDATE_PERMISSION(-1,"无权限修改入院时间，请联系医保科进行修改"),
    IN_HOSP_AREADY_REG(-1,"患者已登记，不能修改入院时间！"),
    IN_HOSP_CANCEL_REG(-1,"跨年患者不允许取消登记"),
    IN_HOSP_UPDATE_BEGNTIME_EMPTY(-1,"修改后入院时间为空"),
    ATDDR_HOSP_STAFF_NAME_EMPTY(-1, sys_msg+"【主治医生姓名】医执人员信息执业人员姓名为空"),
    VIR_NOT_UPDATE(-1,"虚拟医生不允许医保变更，请先分配管床医生和床位后再进行变更!"),
    BEGNTIME_IS_EMPTY(-1,"入院时间不能为空"),



    IN_HOSP_FEE_DATA_EMPTY(-1, sys_msg+"住院费用明细为空"),
    IN_HOSP_FEE_CONFIG_EMPTY(-1, sys_msg+"医保费用定时器配置为空"),
    IN_HOSP_FEE_DATA_ERROR(-1, sys_msg+"费用明细流水号只能数字，不能为0，不能大于15位"),
    IN_HOSP_FEE_UPLOAD_ONGOING(-1, sys_msg+"费用上传正在进行，请稍后处理"),
    IN_HOSP_FEE_UPLOAD_BILG_DR_STAFF_INFO_EMPTY(-1, sys_msg+"费用上传【开单医生】医执人员信息执业人员代码为空"),
    IN_HOSP_FEE_UPLOAD_ORDERS_DR_STAFF_INFO_EMPTY(-1, sys_msg+"费用上传【受单医生】医执人员信息执业人员代码为空"),
    IN_HOSP_FEE_SYNC_HIS_ONGOING(-1, sys_msg+"费用同步HIS正在进行，请稍后处理"),
    IN_HOSP_FEE_REVOCATION_ONGOING(-1, sys_msg+"费用撤销正在进行，请稍后处理"),
    IN_HOSP_FEE_DATA_UNCHECK(-1, sys_msg+"费用明细未审核"),
    IN_HOSP_FEE_ID_EMPTY(-1, sys_msg+"费用明细ID为空"),
    IN_HOSP_FEE_OUT_CHECK(-1, sys_msg+"超限超范审核正在进行，请稍后处理"),
    IN_HOSP_FEE_CANCLE_CHECK(-1, sys_msg+"取消超限超范正在进行，请稍后处理"),
    IN_HOSP_FEE_OFFSET(-1, sys_msg+"冲正正在进行，请稍后处理"),
    IN_HOSP_PERSON_INFO(-1, sys_msg+"HIS人员信息同步正在进行，请稍后处理"),
    IN_HOSP_CANCLE_SETTLE(-1, sys_msg+"结算撤销正在进行，请稍后处理"),
    IN_HOSP_FEE_TOTAL_EMPTY(-1, sys_msg+"还未上传费用，请先上传费用再进行预结算"),
    IN_HOSP_MDTRTIDS_EMPTY(-1,sys_msg + "医保就诊ID为空"),
    N_HOSP_FEE_EXPROT_ERROR(-1,"费用导出异常，异常信息{0}"),
    IN_HOSP_HAVE_RUSH_FEE(-1, sys_msg+"审核得费用流水号{0},项目名称{1},存在冲账费用或被冲帐费用，审核无效"),
    IN_HOSP_FEE_DATE_EMPTY(-1, "费用流水号{0},住院费用发生时间不能为空"),
    OVERRUN_APPROVAL_ALREADY_OUT_HOSP(-1, sys_msg + "{0}，患者已出院，无法进行超限超范围审批"),


    OUT_HOSP_REGISTER_DISE_COUNT_ERROR(-1, sys_msg+"出院主诊断数量只能有一条"),
    OUT_HOSP_DIAG_EMPTY(-1, sys_msg+"出院诊断为空"),
    NOT_OUT_HOSP(-1, his_msg+"{1}患者尚未出院,请先在HIS进行出院办理"),
    NOT_OUT_DISE(-1, his_msg+"{1}出院诊断信息为空"),
    NOT_OUT_PATIENT(-1, his_msg+"{1}出院就诊信息为空"),
    OUT_PATIENT_NOT_VALI(-1, his_msg+"{1}出院就诊信息无效"),
    OUT_HOSP_DIAG_DOR_STAFF_INFO_EMPTY(-1, sys_msg+"出院诊断【诊断医生】医执人员信息执业人员代码为空"),


//    SETTLE_SUCCESS_DOWNLOAD_FAILURE(-1, sys_msg+"结算成功，结算单下载异常,{0}"),
    SETTLE_DOC_UPLOAD_FAILURE(-1, sys_msg+"结算单上传失败,{0}"),
    SETTLE_OFFSET_FAILURE(-1, sys_msg+"结算冲正失败,{0}"),
    SETTLE_ONGOING(-1, sys_msg+"结算进行中，请勿重复操作"),
    SETTLE_EMPTY(-1, sys_msg+"还未进行结算"),
    SETl_ID_EMPTY(-1, sys_msg+"结算ID为空"),
    SETL_DOWNLOAD_FAILURE(-1,insur_err_msg+ "结算成功，结算单下载异常:{0}"),
    SETl_CANCEL_FAIL(-1, "HIS已结算，请先在HIS取消结算"),
    SETL_FEE_DIFF(-1,"医疗总费用：{0},已上传费用：{1} 不相等，请先上传全部费用再结算!"),
    PRE_SETL_LOCAL_EMPTYE(-1,"预结算入参为空"),
    SETTLE_IN_HOSP(-1, sys_msg+"{0}处于在院状态，不能撤销出院！"),
    SETTLE_VIR_ERROR(-1,"主治医生编码或入院床位还处于虚拟值或同步完未进行变更，请先变更实际主治医生编码和入院床位再进行出院结算"),

    PSN_NAME_EMPTY(-1,sys_msg+"该患者还未同步医保个人信息，无法查询相关信息"),
    IPTTYPE_EMPTY(-1,sys_msg+"门诊住院标志为空"),
    BIZ_APPY_TYPE(-1,sys_msg+"业务申请类型为空"),
    SETTLE_DIFF_MONEY_ERROR(-1,"允许与HIS总金额误差范围参数配置有误，请正确配置！"),
    SETTLE_HIS_DIFF_MONEY_EMPTY(-1,"HIS出院信息总费用字段为空!"),
    SETTLE_HIS_DIFF_MONEY_OVER_LIMIT(-1,"偏差金额为：{0}，医院允许HIS与医保偏差金额为：{1}，HIS出院信息总费用金额为{2}，与平台上传费用金额{3}不一致，请检查费用明细！"),



    OUTPATIENT_FEE_DATA_EMPTY(-1, sys_msg + "门诊费用明细为空"),
    OUT_PATIENT_EMPTY(-1, sys_msg + "患者未进行门诊结算"),
    OUT_PATIENT_NOT_SUPPORT(-1, sys_msg + "门诊结算和门诊结算撤销才支持冲正交易"),
    OUT_PATIENT_NOT_REG(-1, sys_msg + "未登记门诊患者不能进行结算"),
    OUT_PATIENT_AREADY_SETTLE(-1, sys_msg + "门诊患者已结算, 请勿重复结算"),
    OUT_PATIENT_REG_DOR_STAFF_INFO_EMPTY(-1, sys_msg + "门诊挂号【医生编码,{0},{1}】医执人员信息执业人员代码为空"),
    OUT_PATIENT_DIAG_DOR_STAFF_INFO_EMPTY(-1, sys_msg + "门诊诊断【诊断医生,{0},{1}】医执人员信息执业人员代码为空"),
    OUT_PATIENT_REG_EMPTY(-1, "门诊号为{0},挂号流水号为{1},的患者未查到登记信息,请先进行登记！"),
    OUT_PATIENT_FEE_IDS_EMPTY(-1, "费用流水号为空！"),
    OUT_PATIENT_IPTOTPNO_EMPTY(-1, "HIS门诊号为空！"),
    OUT_PATIENT_MDTRTSN_EMPTY(-1, "HIS挂号流水号为空！"),
    OUT_PATIENT_MDTRTID_EMPTY(-1, "医保就诊ID为空！"),
    OUT_PATIENT_SETTLE_DOWNLOAD_FAILURE(-1,insur_err_msg+ "结算成功，结算单下载异常:{0}"),
    OUT_PATIENT_EMPTY_BY_MDTRTID(-1, "未查询到{0}医保流水号对应的患者信息！"),
    OUT_PATIENT_ON_UPLOAD(-1,"选中患者费用已上传，请先撤销后再上传"),
    OUT_PATIENT_IN_REG_EMPTY(-1, "未查到登记信息,请先进行登记！"),
    OUT_PATIENT_SERACH_EMPTY(-1, sys_msg + "未查询到结算信息"),
    OUT_PATIENT_FEE_DIFF(-1, "还存在未上传费用，请先全部上传后再结算！"),

    OUT_PATIENT_REGISTER_ALREADY(-1, sys_msg+"医保门诊已登记"),
//    OUT_PATIENT_REGISTER_RECORD_EMPTY(2000201, sys_msg+"门诊登记记录不存在"),
//    OUT_PATIENT_REGISTER_NOT_UPLOAD(2000202, sys_msg+"门诊登记未登记"),
//    OUT_PATIENT_REGISTER_FEE_UPLOAD_EXIST(2000203, sys_msg+"该次门诊登记已上传费用明细"),


    RECORD_STATUS_FAILURE(-1, sys_msg+"备案状态为{0},无法{1}"),
    RECORD_INFO_EMPTY(-1, sys_msg+"备案信息不存在"),


    INQUIRE_EMPTY(-1,sys_msg+"查询条件{0}为空"),


    YEARMONTH_EMPTY(-1, sys_msg + "申报年月为空"),
    CLRAPPYEVTID_EMPTY(-1, sys_msg + "清算申请ID为空"),
    CLRTYPE_EMPTY(-1, sys_msg + "清算类型为空"),
    UN_DECLARE(-1, sys_msg + "{0}还未进行申报");
    */

    private int code;
    private String message;

    InsureExecCodesEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
