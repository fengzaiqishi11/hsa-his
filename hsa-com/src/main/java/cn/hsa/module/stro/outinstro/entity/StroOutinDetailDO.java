package cn.hsa.module.stro.outinstro.entity;

import cn.hsa.base.PageDO;
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
 * @Package_name: cn.hsa.module.stro.outinstro.entity
 * @Class_name: StroOutinDetailDTO
 * @Describe:  出入库明细映射对象
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/25 14:44
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StroOutinDetailDO extends PageDO implements Serializable {
  private static final long serialVersionUID = 890629419097358533L;
  /**
   * 主键
   */
  private String id;
  /**
   * 医院编码
   */
  private String hospCode;
  /**
   * 出入库ID
   */
  private String outinId;
  /**
   * 项目类型代码（药品/材料）
   */
  private String itemCode;
  /**
   * 项目ID（药品/材料）
   */
  private String itemId;
  /**
   * 项目名称（药品/材料）
   */
  private String itemName;
  /**
   * 规格
   */
  private String spec;
  /**
   * 数量
   */
  private BigDecimal num;
  /**
   * 单位代码
   */
  private String unitCode;
  /**
   * 零售单价
   */
  private BigDecimal buyPrice;
  /**
   * 购进单价
   */
  private BigDecimal sellPrice;
  /**
   * 零售总金额
   */
  private BigDecimal buyPriceAll;
  /**
   * 购进总金额
   */
  private BigDecimal sellPriceAll;
  /**
   * 拆分比
   */
  private BigDecimal splitRatio;
  /**
   * 拆零数量
   */
  private BigDecimal splitNum;
  /**
   * 拆零单价
   */
  private BigDecimal splitPrice;
  /**
   * 拆零单位代码
   */
  private String splitUnitCode;
  /**
   * 批号
   */
  private String batchNo;
  /**
   * 有效期
   */
  // 时间戳转换为标准时间格式
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date expiryDate;
  /**
   * 顺序号
   */
  private Integer seqNo;

}
