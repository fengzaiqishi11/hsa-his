package cn.hsa.module.inpt.doctor.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *@Package_name: cn.hsa.module.inpt.entity
 *@Class_name: InptAdviceDO
 *@Describe: 住院医嘱模板
 *@Author: zengfeng
 *@Eamil: zengfeng@powersi.com.cn
 *@Date: 2020-10-20 19:46:53
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InptAdviceTempDO extends PageDO implements Serializable {
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 模板名称
     */
    private String name;
    /**
     * 模板类型代码（MBLX），0全院，1科室，2个人
     */
    private String typeCode;
    /**
     * 模板使用科室ID（科室、个人）
     */
    private String deptId;
    /**
     * 模板使用科室名称（科室、个人）
     */
    private String deptName;
    /**
     * 模板使用医生ID（个人）
     */
    private String doctorId;
    /**
     * 模板使用医生名称（个人）
     */
    private String doctorName;
    /**
     * 处方类型：普通、急诊、毒麻、贵重、小儿,
     */
    private String prescribeTypeCode;
    /**
     * 中草药付（剂）数
     */
    private BigDecimal herbNum;
    /**
     * 中草药用法（ZYYF）
     */
    private String herbUseCode;
    /**
     * 备注
     */
    private String remark;
    /**
     * 是否有效（SF）
     */
    private String isValid;
    /**
     * 拼音码
     */
    private String pym;
    /**
     * 五笔码
     */
    private String wbm;
    /**
     * 审核状态代码（SHZT）
     */
    private String auditCode;
    /**
     * 审核人ID
     */
    private String auditId;
    /**
     * 审核人姓名
     */
    private String auditName;
    /**
     * 审核时间
     */
    private Date auditTime;
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
    private Date crteTime;

}