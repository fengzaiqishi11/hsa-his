package cn.hsa.module.inpt.nurse.dto;

import cn.hsa.module.inpt.nurse.entity.InptAdviceExecDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


/**
 *@Package_name: cn.hsa.module.inpt.dto
 *@Class_name: InptAdviceExecDTO
 *@Describe: 医嘱执行记录
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020-09-01 19:46:53
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class InptAdviceExecDTO extends InptAdviceExecDO implements Serializable {

  private static final long serialVersionUID = 3593572490595005105L;

  //就诊名称
  private String visitName;
  //医嘱内容
  private String content;
  //开嘱医生
  private String adviceDoctor;
  //停嘱时间
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date stopTime;
  //开嘱时间
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date startTime;
  //查询出来就不变的执行签名状态（QMZT）；1、 未签名 2、 已签名  3、取消执行
  private String signCodeJudge;
  //医嘱组号
  private String groupNo;
  //规格
  private String spec;
  //是否临嘱
  private String isLong;
  //剂量
  private String dosage;
  //剂量单位
  private String dosageUnitCode;
  //用法
  private String usageCode;
  //频率
  private String rateRemark;
  //皮试来源id
  private String sourceIaId;
  //皮试记过
  private String psjg;
  //皮试结果
  private String skinResultCode;


  //停嘱时间
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date execStartTime;
  //开嘱时间
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date execEndTime;
  // 是否婴儿
  private String queryBaby;
  // 婴儿名称
  private String babyName;

  private String stageDetailItemId;

  private String clinicalPathStageId;

  // 取消医嘱执行查询标志
  private String canceFlag;

  // 排序字段名称
  private String columnName;

  // 排序名称 desc:降序  asc:升序
  private String sortName;
}
