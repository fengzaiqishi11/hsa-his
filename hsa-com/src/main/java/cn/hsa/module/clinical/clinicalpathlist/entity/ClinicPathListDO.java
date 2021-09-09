package cn.hsa.module.clinical.clinicalpathlist.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.clinical.clinicalpathlist.entity
 * @Class_name: ClinicPathListDO
 * @Describe: 临床路径目录维护数据映射对象
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/9/8 19:27
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClinicPathListDO extends PageDO implements Serializable {

  private static final long serialVersionUID = -4198533375777100742L;
  /**
   * 主键
   */
  private String id;
  /**
   * 医院编码
   */
  private String hospCode;
  /**
   * 路径编号
   */
  private String code;
  /**
   * 路径名称
   */
  private String name;
  /**
   * 使用范围（MBLX），0全院，1科室
   */
  private String typeCode;
  /**
   * 使用科室ID（科室）
   */
  private String deptId;
  /**
   * 使用科室名称（科室）
   */
  private String deptName;
  /**
   * 排序编号
   */
  private String sortNo;
  /**
   * 诊断ID集合（多个用逗号分开）
   */
  private String diagnoseIds;
  /**
   * 最小住院天数
   */
  private String minInptDay;
  /**
   * 最大住院天数
   */
  private String maxInptDay;
  /**
   * 最小费用参考标准
   */
  private String minPrice;
  /**
   * 最大费用参考标准
   */
  private String maxPrice;
  /**
   * 是否可以录入非路径内医嘱：0否、1是（SF）
   */
  private String isFreeEntry;
  /**
   * 打印几列格式(DYLS):1一列,2二列,3三列,4四列,5五列
   */
  private String printColunm;
  /**
   * 拼音码
   */
  private String pym;
  /**
   * 五笔码
   */
  private String wbm;
  /**
   * 备注
   */
  private String remarke;
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
  private Date auditTime;
}
