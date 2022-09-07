package cn.hsa.module.payment.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 诊间支付日志表(PaymentExecuteLog)实体类
 *
 * @author makejava
 * @since 2022-08-31 14:10:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentExecuteLogDO implements Serializable {
    private static final long serialVersionUID = -24861726070645095L;
    /**
     * 主键id
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 就诊id
     */
    private String visitId;
    /**
     * 业务流水号
     */
    private String msgId;
    /**
     * 业务功能号
     */
    private String msgInfo;
    /**
     * 业务名称
     */
    private String msgName;
    /**
     * 业务入参
     */
    private String inParams;
    /**
     * 业务回参
     */
    private String outParams;
    /**
     * 状态码（1：成功 0:失败 999:系统错误）
     */
    private String code;
    /**
     * 创建人id
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
    /**
     * 错误信息
     */
    private String errorMsg;
    /**
     * 冲正状态（1:已冲正  0：未冲正）
     */
    private String status;


}
