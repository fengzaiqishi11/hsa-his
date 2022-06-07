package cn.hsa.module.insure.drgdip.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: TODO
 * @Author: 医保开发二部-湛康
 * @Date: 2022-06-07 10:08
 */
@Data
public class DrgDipComboDTO  implements Serializable {
  private static final long serialVersionUID = 1L;

  private DrgDipResultDTO result;

  private List<DrgDipResultDetailDTO> details;

}
