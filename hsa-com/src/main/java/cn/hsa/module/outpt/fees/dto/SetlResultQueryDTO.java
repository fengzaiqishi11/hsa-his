package cn.hsa.module.outpt.fees.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 医保订单结算结果查询dto
 * @Author: 医保开发二部-湛康
 * @Date: 2022-05-09 14:51
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SetlResultQueryDTO  implements Serializable {

  /**
   * 序列号
   */
  private static final long serialVersionUID = -8142117462251296112L;

  /**
   * 医保注册编码
   */
  private String insureRegCode;

  /**
   * 就诊号
   */
  private String visitId;

  /**
   * 定点机构编码
   */
  private String orgCodg;

  /**
   * 支付订单号
   */
  private String payOrdId;

  /**
   * 支付token
   */
  private String payToken;

  /**
   * 证件号码
   */
  private String idNo;

  /**
   * 用户姓名
   */
  private String userName;

  /**
   * 证件类别
   */
  private String idType;

  /**
   * 扩展数据
   */
  private String extData;

}
