package cn.hsa.module.clinical.inptclinicalpathstate.dto;

import cn.hsa.module.clinical.inptclinicalpathstate.entity.InptClinicalPathStateDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class InptClinicalPathStateDTO extends InptClinicalPathStateDO implements Serializable {
    /**
     * 项目分类(LCXMFL):1诊疗；2医嘱；3；护理； 9其他
     */
    private String itemType;

    /**
     * 路径名称
     */
    private String listName;
    /**
     * 当前阶段名称
     */
    private String stageName;
    /**
     * 姓名/住院号
     */
    private String keyword;
    /**
     * 开医嘱是否查询主管病人（当前医生的病人）0查全部，1查询当前医生的,
     */
    private String zgbrQuery;
    /**
     * 档案号
     */
    private String inProfile;
    /**
     * 住院号
     */
    private String inNo;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private String genderCode;

    /**
     * 年龄
     */
    private String age;
    /**
     * 年龄单位代码（NLDW）
     */
    private String ageUnitCode;
    //就诊ID
    private String outptId;
    //就诊医生
    private String doctorName;
    //就诊号
    private String visitNo;
    //就诊时间
    private Date visitTime;
    /**
     * 累计余额
     */
    private BigDecimal totalBalance;
    /**
     * 优惠类别
     */
    private String preferentialTypeId;
    /**
     * 病人类型
     */
    private String patientCode;
    /**
     * 床位id
     */
    private String bedId;
    /**
     * 床位号
     */
    private String bedName;
    /**
     * 当前科室id
     */
    private String inDeptId;
    /**
     * 主管医生
     */
    private String zgDoctorId;
    /**
     * 入院时间
     */
    private Date inTimestring;
    /**
     * 住院天数
     */
    private String inNum;
    /**
     * 证件号码
     */
    private String certNo;
}
