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
 * @Class_name: NationStandardDrugZYDO
 * @Describe: 国家标准药品（中药）数据实体  @Document 注解表示该实体类可以用来存入es的文档类型
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
@Document(indexName = "nation_standard_tcm_drug")
public class NationStandardDrugZYDO extends PageDO implements Serializable {

  private static final long serialVersionUID = -6756941019684364943L;
  /**
   * 主键
   */
  @Id
  private String id;

  /**
   * 药品编码
   */
  private String code;
  /**
   *  中药饮片名称
   */
  private String decoctionName;
  /**
   *  中药饮片名称列别名
   */
  private String registerName;

  /**
   * 药材名称
   */
  private String goodName;
  /**
   * 注册剂型
   */
  private String functionType;
  /**
   * 药材科来源
   */
  private String drugsSourceSubject;
  /**
   * 药材种来源
   */
  private String drugsSourceRace;
  /**
   * 药用部位
   */
  private String pharmacyPosition;
  /**
   * 炮制方法
   */
  private String processingMethod;
  /**
   * 性味与归经
   */
  private String sexualTaste;
  /**
   * 功能与主治
   */
  private String functionIndications;
  /**
   * 用法与用量
   */
  private String usageDosage;
  /**
   * 医保支付政策
   */
  private String insurancePolicy;


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
  /**
   * 创建时间
   */
  private String crteTime;

  /** 五笔码 **/
  private String wbm;
  /** 拼音码 **/
  private String pym;
  /** 省份代码 **/
  private String provinceCode ;
}
