package cn.hsa.module.insure.fmiownpaypatn.dto;

import lombok.Data;

/**
 * @ClassName FmiOwnpayPatnDiseListDDTO
 * @Description TODO
 * @Author qiang.fan
 * @Date 2022/4/6 11:18
 * @Version 1.0
 **/
@Data
public class FmiOwnpayPatnDiseListDDTO {

    /**
     * 	定点医药机构编号	字符型	30		Y
     */
    private String fixmedinsCode;
    /**
     * 	定点医药机构名称	字符型	200
     */
    private String fixmedinsName;
    /**
     * 	诊断排序号	数值型	2,0		Y
     */
    private Integer diagSrtNo;
    /**
     * 	诊断信息ID	字符型	30
     */
    private String diagInfoId;
    /**
     * 	就诊ID	字符型	30		Y
     */
    private String mdtrtId;
    /**
     * 	出入院诊断类别	字符型	6		Y
     */
    private String inoutDiagType;
    /**
     * 	诊断类别	字符型	3		Y
     */
    private String diagType;
    /**
     * 	主诊断标志	字符型	3		Y
     */
    private String maindiagFlag;
    /**
     * 	诊断代码	字符型	20		Y
     */
    private String diagCode;
    /**
     * 	诊断名称	字符型	255		Y
     */
    private String diagName;
    /**
     * 	入院病情	字符型	500
     */
    private String admCond;
    /**
     * 	诊断科室	字符型	50
     */
    private String diagDept;
    /**
     * 	诊断医师代码	字符型	30
     */
    private String diagDrCode;
    /**
     * 	诊断医师姓名	字符型	50
     */
    private String diagDrName;
    /**
     * 诊断时间	时间类型
     */
    private String diagTime;
    /**
     * 	统筹区编号	字符型	6
     */
    private String poolarea;

}
