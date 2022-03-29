package cn.hsa.report.business.bo.impl.factory.settle;

/**
 * @ClassName SettleSheetTypeEnum
 * @Deacription 结算单类型
 * @Author liuzhuoting
 * @Date 2021/11/27 14:51
 * @Version 1.0
 **/
public enum SettleSheetTypeEnum {

    YZS("his_insur_settle_0004", "一站式结算单", "settleSheetByYzsProcess"),
    ZY("his_insur_settle_0001", "普通住院结算单", "settleSheetByZyProcess"),
    ZY_SNYD("his_insur_settle_0005", "省内异地结算单", "settleSheetByZySnydProcess"),
    ZY_SWYD("his_insur_settle_0005", "省外异地结算单", "settleSheetByZySwydProcess"),
    MZ("his_insur_settle_0002", "门诊结算单", "settleSheetByMzProcess"),
    ZY_LX("his_insur_settle_0003", "离休结算单", "settleSheetByZyLxProcess");

    private final String code;
    private final String desc;
    private final String process;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public String getProcess() {
        return process;
    }

    SettleSheetTypeEnum(String code, String desc, String process) {
        this.code = code;
        this.desc = desc;
        this.process = process;
    }

    public static String getValue(String code) {
        for (SettleSheetTypeEnum ele : values()) {
            if (ele.getCode().equals(code)) {
                return ele.getDesc();
            }
        }
        return null;
    }

    public static String getProcess(String code) {
        for (SettleSheetTypeEnum ele : values()) {
            if (ele.getCode().equals(code)) {
                return ele.getProcess();
            }
        }
        return null;
    }

}
