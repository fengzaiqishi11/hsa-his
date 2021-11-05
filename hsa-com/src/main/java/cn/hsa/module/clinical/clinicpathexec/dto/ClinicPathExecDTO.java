package cn.hsa.module.clinical.clinicpathexec.dto;

import cn.hsa.module.clinical.clinicpathexec.entity.ClinicPathExecDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.clinical.clinicpathexec.dto
 * @Class_name: ClinicPathExecDTO
 * @Describe: 临床路径执行情况数据传输实体
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/10/14 13:59
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClinicPathExecDTO extends ClinicPathExecDO implements Serializable {
  private static final long serialVersionUID = 4877084765784812418L;
  private String keyword;
  List<ClinicPathExecDTO> clinicPathExecDTOList;
  private String typeFlag;
  private String deptId;
}
