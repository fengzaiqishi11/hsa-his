package cn.hsa.module.inpt.doctor.entity;

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
 * @Package_name: cn.hsa.module.inpt.doctor.entity
 * @Class_name: InptAdviceExecDO
 * @Describe: 医嘱执行DO
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-03-30 20:31
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InptAdviceExecDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 8599638620888663507L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 医嘱ID
     */
    private String adviceId;
    /**
     * 医嘱明细ID
     */
    private String adviceDetailId;
    /**
     * 就诊ID
     */
    private String visitId;
    /**
     * 婴儿ID
     */
    private String babyId;
    /**
     * 开嘱科室ID
     */
    private String deptId;
    /**
     * 执行科室ID
     */
    private String execDeptId;
    /**
     * 执行签名状态（QMZT）；1、 未签名 2、 已签名  3、取消执行
     */
    private String signCode;
    /**
     * 计划执行时间
     */
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date planExecTime;
    /**
     * 实际执行时间
     */
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date execTime;
    /**
     * 第一执行人ID
     */
    private String execId;
    /**
     * 第一执行人姓名
     */
    private String execName;
    /**
     * 第二执行人ID
     */
    private String secondExecId;
    /**
     * 第二执行人姓名
     */
    private String secondExecName;
    /**
     * 是否皮试：0否、1是（SF）
     */
    private String isSkin;
    /**
     * 是否阳性（SF）
     */
    private String isPositive;
    /**
     * 备注
     */
    private String remark;
    /**
     * 是否打印（SF）
     */
    private String isPrint;
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
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date crteTime;
    /**
     * 皮试结果(PSJG)
     */
    private String skinResultCode;
}
