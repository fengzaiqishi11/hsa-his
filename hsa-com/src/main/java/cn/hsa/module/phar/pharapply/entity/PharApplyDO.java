package cn.hsa.module.phar.pharapply.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * @PackageName: cn.hsa.module.phar.pharapply.entity
 * @Class_name: PharApplyDO
 * @Description: 药房领药申请数据库映射对象
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/7 21:47
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PharApplyDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 971529838073517862L;
    /**
    * 主键
    */
    private String id;
    /**
    * 医院编码
    */
    private String hospCode;
    /**
    * 请领单据号（表base_order_rule）
    */
    private String orderNo;
    /**
    * 出库库位ID（base_dept）
    */
    private String outStroId;
    /**
    * 入库库位ID（base_dept）
    */
    private String inStroId;
    /**
    * 购进总金额
    */
    private BigDecimal buyPriceAll =BigDecimal.valueOf(0);
    /**
    * 零售总金额
    */
    private BigDecimal sellPriceAll =BigDecimal.valueOf(0);
    /**
    * 备注
    */
    private String remark;
    /**
    * 审核代码（SHDM）
    */
    private String auditStatus;
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
    // 时间戳转换为标准时间格式
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
    // 时间戳转换为标准时间格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date crteTime;
    /**
     * 是否出库
     */
    private String isOut;


}
