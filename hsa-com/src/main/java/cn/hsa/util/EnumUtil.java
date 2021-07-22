package cn.hsa.util;

/**
 * @公司 创智和宇信息技术股份有限公司 Copyright (c) 2006-2020,All Rights Reserved.
 * @描叙
 * @创建者 zengfeng
 * @修改者 zengfeng
 * @版本 V1.0
 * @日期 2020/2/25  16:33
 */
public enum EnumUtil {
    //缴款
    JK01("JK01", "门诊缴款"),
    JK02("JK02", "住院缴款"),
    JK03("JK03", "挂号缴款"),
    JK13("JK13", "住院预交金"),
    JK21("JK21", "诊疗卡缴款"),
    JK04("JK04", "诊疗卡充值"),

    //缴款
    CRFS1("1", "采购入库"),
    CRFS2("2", "直接入库"),
    CRFS3("3", "退供应商"),
    CRFS4("4", "出库到科室"),
    CRFS5("5", "出库到药房"),
    CRFS6("6", "退库"),
    CRFS7("7", "盘盈盘亏"),
    CRFS8("8", "报损报溢"),
    CRFS9("9", "平级出库"),
    CRFS10("10", "同级调拨（药房）"),
    CRFS20("20", "入库确认"),
    CRFS21("21", "退库确认"),
    CRFS22("22", "同级调拨确认"),
    CRFS23("23", "门诊药房发药"),
    CRFS24("24", "调价"),
    CRFS25("25", "门诊药房退药"),
    CRFS26("26", "整单出库"),
    CRFS27("27", "住院药房发药"),
    CRFS28("28", "住院药房退药"),

    //系统
    XT_JCXXZXT("JCXXZXT","基础信息子系统"),
    XT_XTGLZXT("XTGLZXT","系统管理子系统"),
    XT_YKGLZXT("YKGLZXT","药库管理子系统"),
    XT_MZGHZXT("MZGHZXT","门诊挂号子系统"),
    XT_MZHSZZXT("MZHSZZXT","门诊护士子系统"),
    XT_YF0001("YF0001","药房管理子系统"),
    XT_MZYSZXT("MZYSZXT","门诊医生子系统"),
    XT_MZSFZXT("MZSFZXT","门诊收费子系统"),
    XT_RCYZTT("RCYZTT","住院入出子系统"),
    XT_ZYHSZZXT("ZYHSZZXT","住院护士子系统"),
    XT_ZYYSZZXT("ZYYSZZXT","住院医生子系统"),
    XT_DZBLZXT("DZBLZXT","电子病历子系统"),
    XT_SSMZZXT("SSMZZXT","手术麻醉子系统"),
    XT_YBGLZXT("YBGLZXT","医保管理子系统"),
    XT_BBCXZXT("BBCXZXT","报表查询子系统"),
    XT_BAGLZXT("BAGLZXT","病案管理子系统"),


    //项目类别
    XMLB1("1", "药品"),
    XMLB2("2", "材料"),
    XMLB3("3", "项目"),
    XMLB4("4", "医嘱目录");


    private String key;
    private String name;

    EnumUtil(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

}
