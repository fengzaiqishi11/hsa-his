package cn.hsa.module.medic.apply.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name
 * @class_nameMedicalApplyDO
 * @Description 申请单
 * @Author youxianlin
 * @Email: 254580179@qq.com
 * @Date2020/12/11 10:21
 * @Company 创智和宇
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MedicalApplyDO extends PageDO implements Serializable {

    private static final long serialVersionUID = 217448257180401094L;

    private String id;

    private String hospCode;

    private String applyNo;

    private String typeCode;

    private String visitId;
    /**
     * 婴儿id
     */
    private String babyId;

    private String inNo;

    private String orderNo;

    private String isInpt;

    private String deptId;
    private String deptName;
    private String doctorId;
    private String doctorName;
    private String applyTime;
    private String impDeptId;
    private String barCode;
    private String printTime;
    private String printTimes;
    private String rporter;
    private String isAllergy;
    private String resultId;
    private String isPositive;
    private String documentSta;
    private String opdId;
    private String content;
    private String medicType;
    private String isMerge;
    private String mergeId;
    private String remark;
    private String reportTime;
    private String crteId;
    private String crteName;
    // 时间戳转换为标准时间格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;
    /**
     * 采血人id
     */
    private String collBloodId;
    /**
     * 采血人姓名
     */
    private String collBloodName;
    /**
     * 采血时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date collBloodTime;

    // 费用id
    private String costId;

}
