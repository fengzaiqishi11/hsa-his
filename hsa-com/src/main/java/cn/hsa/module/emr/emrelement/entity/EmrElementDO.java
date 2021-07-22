package cn.hsa.module.emr.emrelement.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.emr.emrclassifyelement.entity
 * @Class_name: EmrElementDO
 * @Describe: 电子病历元素管理数据映射对象
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/9/17 16:33
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmrElementDO extends PageDO implements Serializable {
  private static final long serialVersionUID = 964991850486064476L;
  /**
   * 主键
   */
  private String id;
  /**
   * 医院编码
   */
  private String hospCode;
  /**
   * 父元素编码
   */
  private String upCode;
  /**
   * 元素编码
   */
  private String code;
  /**
   * 元素名称
   */
  private String name;
  /**
   * 元素类型代码（YSLX）
   */
  private String typeCode;
  /**
   * 拼音码
   */
  private String pym;
  /**
   * 五笔码
   */
  private String wbm;
  /**
   * 是否末级元素
   */
  private String isEnd;
  /**
   * 是否必填
   */
  private String isRequire;
  /**
   * 是否有效
   */
  private String isValid;
  /**
   * 默认值
   */
  private String defaultVlaue;
  /**
   * 数值下限
   */
  private Double minValue;
  /**
   * 数值上限
   */
  private Double maxValue;
  /**
   * 性别限制代码
   */
  private String genderCode;
  /**
   * 最小年龄
   */
  private Integer minAge;
  /**
   * 最大年龄
   */
  private Integer maxAge;
  /**
   * 病人信息关联项代码（BRGLX）
   */
  private String patientCodeRef;
  /**
   * 系统参数关联项(关联系统码表)
   */
  private String sysCodeRef;
  /**
   * 系统参数默认值
   */
  private String sysCodeDefault;
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
   * 是否单项模板
   */
  private String isSingleTemplate;


}
