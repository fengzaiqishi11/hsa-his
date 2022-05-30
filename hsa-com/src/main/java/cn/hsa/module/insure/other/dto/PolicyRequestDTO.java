package cn.hsa.module.insure.other.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description: 政策项入参
 * @Author: 医保开发二部-湛康
 * @Date: 2022-05-07 09:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PolicyRequestDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 结算ID
   */
  private String setlId;
  /**
   * 医保就诊ID
   */
  private String mdtrtId;
  /**
   * 人员编号
   */
  private String psnNo;
  /**
   * 就诊ID
   */
  private String visitId;
  /**
   * 证件号码
   */
  private String certno;
}
