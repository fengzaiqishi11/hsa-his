package cn.hsa.module.phar.pharinbackdrug.dto;

import cn.hsa.module.phar.pharinbackdrug.entity.PharInDistributeAllDetailDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.phar.pharinbackdrug.dto
 * @Class_name: PharInDistributeBatchDetailDTO
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/5/20 15:14
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class PharInDistributeAllDetailDTO extends PharInDistributeAllDetailDO implements Serializable {
  private static final long serialVersionUID = 5601679274268764780L;
  private List<String> ids;
  // 患者信息
  private String patientInfo;
  private String keyword;

  private String deptId;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  private Date startTime;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  private Date endTime;
  // 开方科室名称
  private String deptName;
  /**
   * 发药药房id
   */
  private String pharId;
  /**
   * 病人状态
   */
  private String statusCode;
}
