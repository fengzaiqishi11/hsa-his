package cn.hsa.module.outpt.prescribeExec.entity;

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
 * @Package_name: cn.hsa.module.outpt.prescribeExec.entity
 * @Class_name: OutptPrescribeExecDO
 * @Describe: 门诊处方执行entity
 * @Author: luoyong
 * @Email: luoyong@powersi.com.cn
 * @Date: 2020/8/12 9:13
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutptPrescribeExecDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 218640176598860800L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 处方明细ID
     */
    private String opdId;
    /**
     * 就诊ID
     */
    private String visitId;
    /**
     * 计划执行时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date planExecDate;
    /**
     * 签名状态（QMZT）；1、 未签名 2、 已签名  3、取消执行
     */
    private String signCode;
    /**
     * 执行人ID
     */
    private String execId;
    /**
     * 执行人姓名
     */
    private String execName;
    /**
     * 执行科室ID
     */
    private String execDeptId;
    /**
     * 执行时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date execTime;
    /**
     * 第二执行人ID
     */
    private String secondExecId;
    /**
     * 第二执行人姓名
     */
    private String secondExecName;
    /**
     * 是否打印（SF）
     */
    private String isPrint;
    /**
     * 备注
     */
    private String remark;

}