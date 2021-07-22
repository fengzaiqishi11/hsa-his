package cn.hsa.module.mris.mrisHome.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.mris.entity
 * @Class_name: MrisControlDO
 * @Describe: 病案控制表
 * @author LiaoJiGuang
 * @since 2020-09-22 15:14:38
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MrisControlDO implements Serializable {
    private static final long serialVersionUID = 901594002239380230L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 病案ID（mris_base_info.id）
     */
    private String mbiId;
    /**
     * 就诊ID
     */
    private String visitId;
    /**
     * 是否提交（SF）
     */
    private String isCommit;
    /**
     * 提交人ID
     */
    private String commitId;
    /**
     * 提交人姓名
     */
    private String commitName;
    /**
     * 提交时间
     */
    private Date commitTime;
    /**
     * 是否打印（SF）
     */
    private String isPrint;
    /**
     * 打印人ID
     */
    private String printId;
    /**
     * 打印人姓名
     */
    private String printName;
    /**
     * 打印时间
     */
    private Date printTime;
    /**
     * 是否送档（SF）
     */
    private String isSend;
    /**
     * 送档人ID
     */
    private String sendId;
    /**
     * 送档人姓名
     */
    private String sendName;
    /**
     * 送档时间
     */
    private Date sendTime;
    /**
     * 是否接收（SF）
     */
    private String isReceive;
    /**
     * 接收人ID
     */
    private String receiveId;
    /**
     * 接收人姓名
     */
    private String receiveName;
    /**
     * 接收时间
     */
    private Date receiveTime;
    /**
     * 借阅状态代码（JYZT）
     */
    private String borrowCode;
    /**
     * 借阅人ID
     */
    private String borrowId;
    /**
     * 借阅人姓名
     */
    private String borrowName;
    /**
     * 借阅时间
     */
    private Date borrowTime;
    /**
     * 归还人ID
     */
    private String backId;
    /**
     * 归还人姓名
     */
    private String backName;
    /**
     * 归还时间
     */
    private Date backTime;
    /**
     * 是否复印（SF）
     */
    private String isCopy;
    /**
     * 复印人ID
     */
    private String copyId;
    /**
     * 复印人姓名
     */
    private String copyName;
    /**
     * 复印时间
     */
    private Date copyTime;
}