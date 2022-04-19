package cn.hsa.module.insure.fmiownpaypatn.dto;

import lombok.Data;

/**
 * @ClassName FmiOwnpayPatnMdtrtDDTO
 * @Description TODO
 * @Author qiang.fan
 * @Date 2022/4/6 11:16
 * @Version 1.0
 **/
@Data
public class FmiOwnpayPatnMdtrtDDTO {

    /**
     * 医药机构就诊事件ID 机构生成内唯一就诊流水
     */
    private String mdtrtId;
    /**
     * 定点医药机构编号
     */
    private String fixmedinsCode;
    /**
     * 定点医药机构名称
     */
    private String fixmedinsName;
    /**
     * 人员证件类型
     */
    private String psnCertType;
    /**
     * 证件号码
     */
    private String certno;
    /**
     * 人员姓名	字符型	30		Y
     */
    private String psnName;
    /**
     * 性别	字符型	6
     */
    private String gend;
    /**
     * 民族	字符型	3
     */
    private String naty;
    /**
     * 出生日期	日期时间型				yyyy-MM-dd
     */
    private String brdy;
    /**
     * 年龄	数值型	4,1
     */
    private String age;
    /**
     * 联系人姓名	日期时间型
     */
    private String conerName;
    /**
     * 		联系电话	字符型	50	　
     */
    private String tel;
    /**
     * 		联系地址	字符型	500	　
     */
    private String addr;
    /**
     * 		开始时间	日期时间型		　	Y	　yyyy-MM-dd HH:mm:ss
     */
    private String begntime;
    /**
     * 结束时间	日期时间型		　		　yyyy-MM-dd HH:mm:ss
     */
    private String endtime;
    /**
     * 医疗类别	字符型	6	　	Y
     */
    private String medType;
    /**
     * 		住院/门诊号	字符型	30	　
     */
    private String iptOpNo;
    /**
     * 	病历号	字符型	30	　
     */
    private String medrcdno;
    /**
     * 		主治医生编码	字符型	30
     */
    private String chfpdrCode;
    /**
     * 		主治医师姓名	字符型	50
     */
    private String chfpdrName;
    /**
     * 		入院诊断描述	字符型	200	　
     */
    private String admDiseDscr;
    /**
     * 		入院科室编码	字符型	30
     */
    private String admDeptCode;
    /**
     * 		入院科室名称	字符型	100	　
     */
    private String admDeptName;
    /**
     * 	入院床位	字符型	30	　	　
     */
    private String admBed;
    /**
     * 病区床位	字符型	50
     */
    private String wardareaBed;
    /**
     * 	转科室标志	字符型	6	　
     */
    private String ifturnDept;
    /**
     * 	住院主诊断代码	字符型	20
     */
    private String dscgDiseCode;
    /**
     * 		住院主诊断名称	字符型	100
     */
    private String dscgDiseName;
    /**
     * 	出院科室编码	字符型	30	　
     */
    private String dscgDeptCode;
    /**
     * 	出院科室名称	字符型	100	　	　	　
     */
    private String dscgDeptName;
    /**
     * 出院床位	字符型	30
     */
    private String dscgBed;
    /**
     * 离院方式	字符型	3
     */
    private String dscgWay;
    /**
     * 主要病情描述	字符型	1000
     */
    private String mainCondDesc;
    /**
     * 病种编号	字符型	30
     */
    private String diseNo;
    /**
     * 病种名称	字符型	500
     */
    private String diseName;
    /**
     * 手术操作代码	字符型	200
     */
    private String oprnOprtCode;
    /**
     * 手术操作名称	字符型	500
     */
    private String oprnOprtName;
    /**
     * 门诊诊断信息	字符型	200
     */
    private String opDiseInfo;
    /**
     * 在院状态	字符型	3		Y
     */
    private String inhospStas;
    /**
     * 死亡日期	日期时间型				yyyy-MM-dd
     */
    private String dieDate;
    /**
     * 住院天数	数值型	16,0
     */
    private String iptDays;
    /**
     * 计划生育服务证号	字符型	50
     */
    private String fpscNo;
    /**
     * 生育类别	字符型	6
     */
    private String matnType;
    /**
     * 计划生育手术类别	字符型	6
     */
    private String birctrlType;
    /**
     * 晚育标志	字符型	3
     */
    private String latechbFlag;
    /**
     * 孕周数	数值型	2,0
     */
    private String gesoVal;
    /**
     * 胎次	数值型	3,0
     */
    private String fetts;
    /**
     * 胎儿数	数值型	3,0
     */
    private String fetusCnt;
    /**
     * 早产标志	字符型	3
     */
    private String prematureFlag;
    /**
     * 妊娠时间	日期时间型				yyyy-MM-dd HH:mm:ss
     */
    private String gestationTime;
    /**
     * 计划生育手术或生育日期	日期时间型				yyyy-MM-dd HH:mm:ss
     */
    private String birctrlOrMatnTime;
    /**
     * 伴有并发症标志	字符型
     */
    private String cmplctFlag;
    /**
     * 有效标志	不传默认1有效
     */
    private String valiFlag;
    /**
     * 备注	字符型	500
     */
    private String memo;

}
