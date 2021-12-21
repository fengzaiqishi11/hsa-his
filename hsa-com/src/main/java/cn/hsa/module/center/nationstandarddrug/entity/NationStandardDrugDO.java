package cn.hsa.module.center.nationstandarddrug.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;


import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.center.nationstandarddrug.entity
 * @Class_name: NationStandardDrugDO
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/1/26 9:26
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(indexName = "nation_standard_drug")
public class NationStandardDrugDO extends PageDO implements Serializable {
  private static final long serialVersionUID = -22652313057460226L;
  /**
   * 主键
   */
  @Id
  private String id;
  /**
   * 数据来源
   */
  private String dataSources;
  /**
   * 药品编码
   */
  private String code;
  /**
   * 注册名称
   */
  private String registerName;
  /**
   * 商品名称
   */
  private String goodName;
  /**
   * 注册剂型
   */
  private String registerPrep;
  /**
   * 实际剂型
   */
  private String actualPrep;
  /**
   * 注册规格
   */
  private String registerSpec;
  /**
   * 实际规格
   */
  private String actualSpec;
  /**
   * 包装材质
   */
  private String packaging;
  /**
   * 拆分比
   */
  private String splitRatio;
  /**
   * 包装单位
   */
  private String unit;
  /**
   * 拆零单位
   */
  private String splitUnit;
  /**
   * 生产厂家
   */
  private String prod;
  /**
   * 批准文号
   */
  private String dan;
  /**
   * 分包装企业
   */
  private String packagProd;
  /**
   * 市场状态
   */
  private String marketStatus;
  /**
   * 药品本位码
   */
  private String insure;
  /**
   * 甲乙类
   */
  private String ratioAtOne;
  /**
   * 编号
   */
  private String codeNo;
  /**
   * 医保药品名称
   */
  private String insuranceName;
  /**
   * 注册剂型
   */
  private String insurancePrep;
  /**
   * 备注
   */
  private String remark;
  /**
   * 是否有效：0否、1是
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
  /** 五笔码 **/
  private String wbm;
  /** 拼音码 **/
  private String pym;
  /** 省份代码 **/
  private String provinceCode;
}
