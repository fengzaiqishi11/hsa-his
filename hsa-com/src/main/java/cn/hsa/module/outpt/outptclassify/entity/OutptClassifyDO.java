package cn.hsa.module.outpt.outptclassify.entity;

import cn.hsa.base.PageDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.outpt.outptclassify.entity
 * @Class_name: OutptClassifyDO
 * @Describe:  挂号类别数据映射对象
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/8/10 15:35
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OutptClassifyDO extends PageDO implements Serializable {

  private static final long serialVersionUID = 794694798785358114L;
  /**
   * 主键
   */
  private String id;
  /**
   * 医院编码
   */
  private String hospCode;
  /**
   * 类别名称
   */
  private String name;
  /**
   * 挂号科室ID
   */
  private String deptId;
  /**
   * 就诊类别代码（JZLB）
   */
  private String visitCode;
  /**
   * 挂号类别代码（GHLB）
   */
  private String registerCode;
  /**
   * 备注
   */
  private String remark;
  /**
   * 拼音码
   */
  private String pym;
  /**
   * 五笔码
   */
  private String wbm;
  /**
   * 是否专家（SF）
   */
  private String isExpert;
  /**
   * 是否有效（SF）
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
  private Date crteTime;
}
