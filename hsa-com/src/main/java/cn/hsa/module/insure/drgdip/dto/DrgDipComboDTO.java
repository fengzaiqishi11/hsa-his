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

    /**
     * 质控状态编码 0:未质控 1:质控中 2:质控完成
     */
    private String quaStaus;

    /**
     * 质控状态编码
     */
    private String quaStausName;

    /**
     * 可疑条数
     */
    private Integer suspiciousNum;

    /**
     * 违规条数
     */
    private Integer violationNum;

}
