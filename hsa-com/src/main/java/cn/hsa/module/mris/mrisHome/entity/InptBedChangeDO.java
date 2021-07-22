package cn.hsa.module.mris.mrisHome.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @Package_name: cn.hsa.module.mris.entity
 * @Class_name: InptBedChangeDO
 * @Describe: 床位异动
 * @Author: liaojiguang
 * @Email: liaojiguang@powersi.com.cn
 * @Date: 2020/8/10 17:50
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InptBedChangeDO extends PageDO implements java.io.Serializable {
    private static final long serialVersionUID = -23406029554846328L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 就诊ID
     */
    private String visitId;
    /**
     * 异动类型代码（YDLX）
     */
    private String changeCode;
    /**
     * 换前床号ID
     */
    private String beforeBedId;
    /**
     * 换前床号名称
     */
    private String beforeBedName;
    /**
     * 换后床号ID
     */
    private String afterBedId;
    /**
     * 换后床号名称
     */
    private String afterBedName;
    /**
     * 换前科室ID
     */
    private String beforeDeptId;
    /**
     * 换前科室名称
     */
    private String beforeDeptName;
    /**
     * 换后科室ID
     */
    private String afterDeptId;
    /**
     * 换后科室名称
     */
    private String afterDeptName;
    /**
     * 换前病区ID
     */
    private String beforeWardId;
    /**
     * 换前病区名称
     */
    private String beforeWardName;
    /**
     * 换后病区ID
     */
    private String afterWardId;
    /**
     * 换后病区名称
     */
    private String afterWardName;
    /**
     * 换前主治医生ID
     */
    private String beforeZzDoctorId;
    /**
     * 换前主治医生姓名
     */
    private String beforeZzDoctorName;
    /**
     * 换后主治医生ID
     */
    private String afterZzDoctorId;
    /**
     * 换后主治医生姓名
     */
    private String afterZzDoctorName;
    /**
     * 换前经治医生ID
     */
    private String beforeJzDoctorId;
    /**
     * 换前经治医生姓名
     */
    private String beforeJzDoctorName;
    /**
     * 换后经治医生ID
     */
    private String afterJzDoctorId;
    /**
     * 换后经治医生姓名
     */
    private String afterJzDoctorName;
    /**
     * 换前主管医生ID
     */
    private String beforeZgDoctorId;
    /**
     * 换前主管医生姓名
     */
    private String beforeZgDoctorName;
    /**
     * 换后主管医生ID
     */
    private String afterZgDoctorId;
    /**
     * 换后主管医生姓名
     */
    private String afterZgDoctorName;
    /**
     * 换前责任护士ID
     */
    private String beforeRespNurseId;
    /**
     * 换前责任护士姓名
     */
    private String beforeRespNurseName;
    /**
     * 换后责任护士ID
     */
    private String afterRespNurseId;
    /**
     * 换后责任护士姓名
     */
    private String afterRespNurseName;

    /** 终止时间 */
    // 时间戳转换为标准时间格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date endTime;

    /** 创建人ID */
    private String crteId;

    /** 创建人姓名 */
    private String crteName;

    /** 创建时间 */
    // 时间戳转换为标准时间格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date crteTime;
}