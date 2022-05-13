package cn.hsa.module.outpt.fees.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: 诊断信息
 * @Author: 医保开发二部-湛康
 * @Date: 2022-04-25 14:05
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DiseInfoDTO implements Serializable {

  /**
   * 序列号
   */
  private static final long serialVersionUID = -8142117462251296112L;

  /**
   * 诊断类别
   */
  private String diagType;

  /**
   * 诊断排序号
   */
  private Integer diagSrtNo;

  /**
   * 诊断代码
   */
  private String diagCode;

  /**
   * 诊断名称
   */
  private String diagName;

  /**
   * 诊断科室
   */
  private String diagDept;

  /**
   * 诊断医生编码
   */
  private String diseDorNo;

  /**
   * 诊断医生姓名
   */
  private String diseDorName;

  /**
   * 诊断时间
   */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date diagTime;

  /**
   * 有效标志
   */
  private String valiFlag;

}
