package cn.hsa.module.sync.syncemrprintsetting.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.emr.emrprintsetting.entity
 * @Class_name: EmrPrintSettingDO
 * @Describe:  电子并病历打印设置数据映射对象
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/9/17 15:43
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SyncEmrPrintSettingDO extends PageDO implements Serializable {
  private static final long serialVersionUID = 233292329876990689L;
  /**
   * 主键
   */
  private String id;
  /**
   * 格式编码
   */
  private String code;
  /**
   * 格式名称
   */
  private String name;
  /**
   * 页宽
   */
  private Double width;
  /**
   * 页高
   */
  private Double height;
  /**
   * 左边距
   */
  private Double marginLeft;
  /**
   * 右边距
   */
  private Double marginRight;
  /**
   * 上边距
   */
  private Double marginUp;
  /**
   * 下边距
   */
  private Double marginUnder;
  /**
   * 是否横向（SF）
   */
  private String isInfeed;
  /**
   * 是否有效（SF）
   */
  private String isValid;
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
}
