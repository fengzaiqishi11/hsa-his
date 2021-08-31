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
 * @Class_name: OperAnesthesiaRecordDO
 * @Describe: 麻醉记录单
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/12/21 21:19
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OperAnesthesiaRecordDO extends PageDO implements Serializable {
  private static final long serialVersionUID = -18735093083213615L;
  /**
   * 主键
   */
  private String id;
  /**
   * 医院编码
   */
  private String hospCode;
  /**
   * 手术id
   */
  private String operId;
  /**
   * 就诊ID
   */
  private String visitId;
  /**
   * 体重 单位：公斤
   */
  private String weight;
  /**
   * 身高 单位：厘米
   */
  private String height;
  /**
   * 手术前诊断
   */
  private String operDiseaseBefore;
  /**
   * 手术后诊断
   */
  private String operDiseaseAfter;
  /**
   * ASA分级代码
   */
  private String asaCode;
  /**
   * 拟施手术
   */
  private String proposedOper;
  /**
   * 麻醉分类代码
   */
  private String anesthesiaCode;
  /**
   * 麻醉方法
   */
  private String anesthesiaMethod;
  /**
   * 手术体位
   */
  private String positionCode;

  /**
   * 侧卧体位
   */
  private String lateraldecubitusCode;
  /**
   * 入室时间
   */
  private String inRoomTime;
  /**
   * 麻醉时间
   */
  private String anesthesiaTime;
  /**
   * 插管时间
   */
  private String intubationTime;
  /**
   * 拨管时间
   */
  private String extubationTime;
  /**
   * 手术开始时间
   */
  private String operStartTime;
  /**
   * 手术结束时间
   */
  private String operEndTime;
  /**
   * 回病房/PACU/ICU时间
   */
  private String backTime;
  /**
   * 实施手术
   */
  private String performSurgery;
  /**
   * 手术者
   */
  private String operationDoctor;
  /**
   * 麻醉者
   */
  private String anaesthesiaDoctor;
  /**
   * 洗手护士
   */
  private String handwashingNurse;
  /**
   * 备注
   */
  private String remark;
  /**
   * 监测间隔 单位：分钟
   */
  private String intervalMinute;
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
   * 麻醉日期
   */
  // 时间戳转换为标准时间格式
  @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date anesthesiaDate;
}
