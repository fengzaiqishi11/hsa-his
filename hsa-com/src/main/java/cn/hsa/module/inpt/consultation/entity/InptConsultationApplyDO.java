package cn.hsa.module.inpt.consultation.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.inpt.consultation.entity
 * @Class_name: InptConsultationApplyDO
 * @Describe: 会诊申请do
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-11-04 20:09
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InptConsultationApplyDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -2303589996075047500L;
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
     * 婴儿ID
     */
    private String babyId;
    /**
     * 医嘱ID(inpt_advice.id)
     */
    private String adviceId;
    /**
     * 会诊申请单号
     */
    private String consulNo;
    /**
     * 会诊申请状态(HZZT:0保存,1审核,2完成,3作废)
     */
    private String consulState;
    /**
     * 会诊类型(HZLX:0常规会诊,1急会诊)
     */
    private String consulType;
    /**
     * 会诊时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date consulTime;
    /**
     * 会诊地址
     */
    private String consulAddr;
    /**
     * 申请科室ID
     */
    private String applyDeptid;
    /**
     * 申请人ID
     */
    private String applyUserid;
    /**
     * 申请人姓名
     */
    private String applyUsername;
    /**
     * 病情描述
     */
    private String illness;
    /**
     * 诊疗情况
     */
    private String diagnoTreat;
    /**
     * 会诊原因
     */
    private String consulReason;
    /**
     * 会诊目的
     */
    private String consulObjective;
    /**
     * 会诊科室ID合集
     */
    private String consulDeptidList;
    /**
     * 会诊医生ID合集
     */
    private String consulUseridList;
    /**
     * 备注
     */
    private String remark;
    /**
     * 完成人ID
     */
    private String finishUserid;
    /**
     * 完成人姓名
     */
    private String finishUsername;
    /**
     * 完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;
    /**
     * 创建人ID
     */
    private String crteId;
    /**
     * 创建人姓名
     */
    private String crteName;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;
}
