package cn.hsa.module.phar.pharinbackdrug.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *@Package_name: cn.hsa.module.phar.pharinbackdrug.entity
 *@Class_name: PharInWaitReceiveDO
 *@Describe: 住院领药申请
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/8/21 16:21
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PharInReceiveDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -66904556162514892L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 发药药房ID
     */
    private String pharId;
    /**
     * 发药窗口ID
     */
    private String windowId;
    /**
     * 单据号
     */
    private String orderNo;
    /**
     * 领药单据类型ID（base_order_receive.id）
     */
    private String orderReceiveId;
    /**
     * 总金额
     */
    private BigDecimal totalPrice;
    /**
     * 配药人ID
     */
    private String assignUserId;
    /**
     * 配药人姓名
     */
    private String assignUserName;
    /**
     * 配药时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date assignTime;
    /**
     * 发药人ID
     */
    private String distUserId;
    /**
     * 发药人姓名
     */
    private String distUserName;
    /**
     * 发药时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date distTime;
    /**
     * 发药状态代码（LYZT）
     */
    private String statusCode;
    /**
     * 申请科室ID
     */
    private String deptId;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;

}