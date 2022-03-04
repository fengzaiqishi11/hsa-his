package cn.hsa.enums;

/**
 * @ClassName HsaSrvEnum
 * @Deacription 微服务接口功能号
 * @Author qiang.fan
 * @Date 2022/3/3 15:59
 * @Version 3.0
 */
public enum HsaSrvEnum {

    HSA_CENTER("hsa-center", "中心平台子系统"),
    HSA_INPT("hsa-inpt", "住院医护子系统"),
    HSA_INSURE("hsa-insure", "医保管理子系统"),
    HSA_INTERF("hsa-interf", "接口服务"),
    HSA_OUTPT("hsa-outpt", "门诊医护子系统"),
    HSA_REPORT("hsa-report", "报表子系统"),
    HSA_STRO("hsa-stro", "药房药库子系统"),
    HSA_PLATFORM("hsa-platform", "消息推送平台"),
    HSA_SYS("hsa-sys", "系统管理子系统"),
    HYGEIA_HGS("hygeia-hgs", "医保助手平台");

    private final String code;
    private final String desc;

    public static String getValue(String code) {
        for (HsaSrvEnum ele : values()) {
            if(ele.getCode().equals(code)) {
                return ele.getDesc();
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    HsaSrvEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
