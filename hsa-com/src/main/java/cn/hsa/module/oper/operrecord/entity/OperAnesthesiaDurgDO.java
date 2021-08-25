package cn.hsa.module.oper.operrecord.entity;

import cn.hsa.base.PageDO;
import cn.hsa.base.PageDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.oper.operrecord.entity
 * @Class_name: OperAnesthesiaDurgDO
 * @Describe: 麻醉用药记录数据映射层
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/12/21 21:16
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OperAnesthesiaDurgDO extends PageDO implements Serializable {
  private static final long serialVersionUID = 152721015785494446L;
  /**
   * 主键
   */
  private String id;
  /**
   * 医院编码
   */
  private String hospCode;
  /**
   * 麻醉记录ID
   */
  private String anesthesiaId;
  /**
   * 记录类型
   */
  private String typeCode;
  /**
   * 值1
   */
  private String value1;
  /**
   * 值2
   */
  private String value2;
  /**
   * 值3
   */
  private String value3;
  /**
   * 值4
   */
  private String value4;
  /**
   * 值5
   */
  private String value5;
  /**
   * 值6
   */
  private String value6;
  /**
   * 值7
   */
  private String value7;
  /**
   * 值8
   */
  private String value8;
  /**
   * 值9
   */
  private String value9;
  /**
   * 值10
   */
  private String value10;
  /**
   * 修改人ID
   */
  private String updtId;
  /**
   * 修改人名称
   */
  private String updtName;
  /**
   * 修改时间
   */
  // 时间戳转换为标准时间格式
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date updtTime;
  /**
   * 创建人ID
   */
  private String crteId;
  /**
   * 创建人名称
   */
  private String crteName;
  /**
   * 创建时间
   */
  // 时间戳转换为标准时间格式
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date crteTime;
  /**
   * 说明
   */
  private String remark;
}
