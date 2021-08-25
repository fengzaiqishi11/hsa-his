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
 * @Class_name: OperAnesthesiaMonitorDO
 * @Describe:  麻醉检测单
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/12/21 21:18
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OperAnesthesiaMonitorDO extends PageDO implements Serializable {
  private static final long serialVersionUID = 698410713703059608L;
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
   * 监测时间
   */
  private String monitorTime;
  /**
   * 收缩压
   */
  private String sbp;
  /**
   * 舒张压
   */
  private String dbp;
  /**
   * 脉搏
   */
  private String pulse;
  /**
   * 呼吸
   */
  private String breath;
  /**
   * MAP
   */
  private String map;
  /**
   * 鼻咽温
   */
  private String noseTemperature;
  /**
   * 直肠温
   */
  private String rectumTemperature;
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
}
