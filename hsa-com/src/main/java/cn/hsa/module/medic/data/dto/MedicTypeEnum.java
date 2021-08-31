package cn.hsa.module.medic.data.dto;

/**
 * @Package_name
 * @class_nameMedicType
 * @Description
 * @Author youxianlin
 * @Email: 254580179@qq.com
 * @Date2021/2/2 16:48
 * @Company 创智和宇
 **/
public enum MedicTypeEnum {
    LIS_KS(1,"lis科室同步"),
    LIS_YS(2,"lis医生同步"),
    LIS_SFXM(3,"lis收费项目同步"),
    LIS_SQD_MZ(4,"lis门诊申请单"),
    LIS_SQD_TFMZ(5,"lis门诊退费申请单"),
    LIS_SQD_ZY(6,"lis住院申请单"),
    LIS_SQD_ZYTF(7,"lis住院退费申请单"),
    LIS_JG_ZD(8,"lis结果主动获取"),
    LIS_JG_BD(9,"lis结果被动获取"),
    LIS_JG_PDF(10,"lis获取PDF报告"),
    LIS_HCZT(11,"lis回传状态"),
    LIS_WJ_ZD(12,"lis主动获取危急值"),
    LIS_WJ_BD(13,"lis被动获取危急值"),

    PACS_KS(41,"pacs科室同步"),
    PACS_YS(42,"pacs医生同步"),
    PACS_SFXM(43,"pacs收费项目同步"),
    PACS_SQD(44,"pacs申请单"),
    PACS_JG(44,"pacs获取结果");

    int code;
    String name;

    //成员方法
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //构造方法
    MedicTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
