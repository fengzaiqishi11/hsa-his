package cn.hsa.module.interf.report.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.interf.phys.dto
 * @Class_name: PhysSettleDTO
 * @Describe: 体检结算dto 需要转成费用dto
 * @Author: zhangguorui
 * @Eamil: guorui.zhang@powersi.com
 * @Date: 2021/7/14 9:36
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PhysSettleDTO implements Serializable {
    private String id;
    // 优惠套餐ID
    private String discountId;

    //套餐名称
    private String groupName;

    //套餐名称
    private String comboName;

    //组合id
    private String groupIds;
    //优惠方式 1- 按比例 2-按金额 3-自定义金额
    private String preferentialCode;
    //优惠比例
    private BigDecimal preferentialProp;
    //优惠模板id
    private String templateId;
    //当前登陆人id
    private String userId;

    // 科室地址
    private String place;

    //是否打印条形码
    private String isPrintBar;

    // 结算状态
    private String settleStatusCode;

    // 医技类型
    private String skillCode;

    // 容器类型
    private String holderCode;

    // 标本类型
    private String sampleCode;

    private String hospCode;

    private String settleNo;

    private String registerId;

    private String groupId;

    private String costId;

    private BigDecimal totalPrice;

    private BigDecimal preferentialPrice;
    /**
     * 实收金额
     */
    private BigDecimal actualPrice;

    private BigDecimal realityPrice;

    private BigDecimal selfPrice;

    private BigDecimal miPrice;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date settleTime;

    private String redId;

    private String statusCode;

    private String payCode;

    private String isSettle;
    /**
     * 是否有效：0否、1是（SF）
     */
    private String isValid;
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
    /**
     * 科室id
     */
    private String deptId;
    /**
     * 执行状态 0:未执行 1：已执行 2：执行完成
     */
    private String executeCode;
    /**
     * 科室名称
     */
    private String deptName;
    //套餐ID
    private String comboId;

    // 是否异常
    private String isPositive;

    // 项目类别
    private String categoryCode;

    // 条码号
    private String barCode;

    // 申请id
    private String lisId;
}
