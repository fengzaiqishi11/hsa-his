package cn.hsa.module.oper.operrecord.dto;

import cn.hsa.module.base.bd.dto.BaseDiseaseDTO;
import cn.hsa.module.oper.operrecord.entity.OperAnesthesiaRecordDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.oper.operrecord.dto
 * @Class_name: OperAnesthesiaRecordDTO
 * @Describe: 麻醉记录数据传输对象
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/12/21 21:23
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OperAnesthesiaRecordDTO extends OperAnesthesiaRecordDO implements Serializable {
  private static final long serialVersionUID = -18735093083213425L;
  private List<OperAnesthesiaDurgDTO> operAnesthesiaDurgDTOS;
  private List<OperAnesthesiaMonitorDTO> operAnesthesiaMonitorDTOS;
  private String Flag;
  private List<BaseDiseaseDTO> baseDiseaseDTOS;
  private String diseaseName;
}
