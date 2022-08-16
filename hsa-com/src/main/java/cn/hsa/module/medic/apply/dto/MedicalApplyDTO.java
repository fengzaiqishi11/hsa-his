package cn.hsa.module.medic.apply.dto;

import cn.hsa.module.medic.apply.entity.MedicalApplyDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Package_name
 * @class_nameMedicalApplyDTO
 * @Description
 * @Author youxianlin
 * @Email: 254580179@qq.com
 * @Date2020/12/11 10:28
 * @Company 创智和宇
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class MedicalApplyDTO extends MedicalApplyDO implements Serializable {
    private static final long serialVersionUID = -1530461391880802014L;
    private String keyword;
    //处方ID
    private String opId;
    //材料ID
    private String materialId;
    //执行科室ID
    private String execDeptId;
    //开方科室ID
    private String deptId;
    //开方科室
    private String deptName;
    //开方医生ID
    private String doctorId;
    //开方医生
    private String doctorName;
    //容器类型代码（RQ）
    private String containerCode;
    //标本类型代码（BBLX）
    private String specimenCode;
    //医技分类代码
    private String technologyCode;
    //医嘱code
    private String code;

    /**
     * 开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    /**
     * 结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

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
    private Integer age;
    /**
     * 年龄单位
     */
    private Integer ageUnitCode;
    /**
     * 是否已采血
     */
    private String isCollBlood;

    /**
     * 医技id
     */
    private String applyId;
    // 就诊号
    private String visitNo;
    // 单价
    private String price;
    // 数量
    private String num;
    // 项目名称
    private String itemName;
    // 是否结算
    private String isSettle;
    // 医技申请主键集合
    private List<String> ids;

    /**
     * 报告人
     */
    private String reporter;

    /**
     * 报告时间
     */
    private String reportTime;
}
