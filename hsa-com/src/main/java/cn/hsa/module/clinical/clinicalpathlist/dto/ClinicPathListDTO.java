package cn.hsa.module.clinical.clinicalpathlist.dto;

import cn.hsa.module.clinical.clinicalpathlist.entity.ClinicPathListDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.clinical.clinicalpathlist.dto
 * @Class_name: ClinicPathListDODTO
 * @Describe: 临床路径目录维护数据传输对象
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/9/8 19:29
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class ClinicPathListDTO extends ClinicPathListDO implements Serializable {
  private static final long serialVersionUID = -8286967177244410037L;
  private List<String> ids;
}
