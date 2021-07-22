package cn.hsa.module.sync.syncmenu.entity;

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
 * @Package_name: cn.hsa.module.center.menu.entity
 * @Class_name: SyncMenuDO
 * @Describe:  菜单映射对象
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/28 10:54
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SyncMenuDO extends PageDO implements Serializable {

  private static final long serialVersionUID = 193278407786517385L;
  /**
   * 主键
   */
  private String id;
  /**
   * 系统编码：sys_system表系统编码
   */
  private String sysCode;
  /**
   * 菜单编码
   */
  private String code;
  /**
   * 菜单名称
   */
  private String name;
  /**
   * 菜单类型代码：0、目录，1、菜单
   */
  private String typeCode;
  /**
   * 上级菜单编码
   */
  private String upCode;
  /**
   * 菜单URL
   */
  private String url;
  /**
   * 菜单图标
   */
  private String icon;
  /**
   * 顺序号
   */
  private Integer seqNo;
  /**
   * 菜单描述
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
   */
  // 时间戳转换为标准时间格式
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date crteTime;
}
