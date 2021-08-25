package cn.hsa.module.phar.pharoutreceive.entity;/*

import cn.hsa.base.PageDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
* @Package_name: 
* @Class_name: DO
* @Describe: 
* @Author: liaojiguang
* @Email: liaojiguang@powersi.com.cn
* @Date: 2020/9/07 13:53
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PharOutReceiveDO extends PageDO implements java.io.Serializable {

    /** 主键 */
    private String id;

    /** 医院编码 */
    private String hospCode;

    /** 就诊ID */
    private String visitId;

    /** 结算ID */
    private String settleId;

    /** 总金额 */
    private BigDecimal totalPrice;

    /** 发药状态代码（LYZT） */
    private String statusCode;

    /** 发药药房ID */
    private String pharId;

    /** 发药窗口ID */
    private String windowId;

    /** 配药人ID */
    private String assignUserId;

    /** 配药人姓名 */
    private String assignUserName;

    /** 配药时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date assignTime;

    /** 发药人ID */
    private String distUserId;

    /** 发药人姓名 */
    private String distUserName;

    /** 发药时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date distTime;

    /** 申请科室ID */
    private String deptId;

    /** 创建人ID */
    private String crteId;

    /** 创建人姓名 */
    private String crteName;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;
}