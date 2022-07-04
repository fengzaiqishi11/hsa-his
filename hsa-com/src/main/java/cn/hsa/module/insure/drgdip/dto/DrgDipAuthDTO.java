package cn.hsa.module.insure.drgdip.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: TODO
 * @Author: 医保开发二部-湛康
 * @Date: 2022-06-09 15:32
 */
@Data
public class DrgDipAuthDTO implements Serializable {
  private static final long serialVersionUID = 1L;

    /**
     * drg
     */
    private String drg;
    /**
     * dip
     */
    private String dip;
    /**
     * drg
     */
    private String drgMsg;
    /**
     * dip
     */
    private String dipMsg;
}
