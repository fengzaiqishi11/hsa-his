package cn.hsa.module.base.baseoutptexec.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.base.baseoutptexec.entity
 * @Class_name: BaseOutptExecDO
 * @Describe: 门诊执行科室配置数据库映射对象
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/10/14 13:58
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseOutptExecDO extends PageDO implements Serializable {
  private static final long serialVersionUID = 579603873650270209L;
  /**
   * 主键
   */
  private String id;
  /**
   * 医院编码
   */
  private String hospCode;
  /**
   * 用法代码列表（YF）
   */
  private String usageCodes;
  /**
   * 科室ID列表
   */
  private String deptIds;
  /**
   * 执行科室ID
   */
  private String execDeptId;
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
