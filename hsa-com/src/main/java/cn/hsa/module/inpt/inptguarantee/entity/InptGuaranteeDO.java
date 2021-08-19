package cn.hsa.module.inpt.inptguarantee.entity;

import cn.hsa.base.PageDO;
import cn.hsa.base.PageDTO;
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
 * @Package_name: cn.hsa.module.inpt.inptguarantee.entity
 * @Class_name: InptGuaranteeDO
 * @Describe: 保证金管理数据映射对象
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/8/10 15:06
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InptGuaranteeDO extends PageDO implements Serializable {
      private static final long serialVersionUID = 8294496056719349485L;

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
       * 担保单号
       */
      private String guaranteeNo;
      /**
       * 担保金额
       */
      private BigDecimal price = BigDecimal.valueOf(0);
      /**
       * 备注
       */
      private String remark;
      /**
       * 担保人ID
       */
      private String guaranteeId;
      /**
       * 担保人姓名
       */
      private String guaranteeName;
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
      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
      private Date auditTime;
}
