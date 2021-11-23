package cn.hsa.module.center.config.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 *  中心端全局配置类实体
 * @Author: luonianixn
 * @Date: 2021-11-16
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CenterGlobalConfigDO extends PageDO implements Serializable {

  private static final long serialVersionUID = 2248213231610812460L;
  /**
   * 主键
   */
  private String id;

  /**
   *  全局配置名称
   */
  private String configName;
  /**
   *  全局配置具体的值
   */
  private String configValue;

  /**
   *  配置描述
   */
  private String remark;
  /**
   * 是否有效
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
   *   时间戳转换为标准时间格式
   */

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date crteTime;
  /**
   *  配置更新时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date updateTime;
}
