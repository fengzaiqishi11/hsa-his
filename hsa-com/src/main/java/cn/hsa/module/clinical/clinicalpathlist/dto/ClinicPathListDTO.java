package cn.hsa.module.clinical.clinicalpathlist.dto;

import cn.hsa.module.base.bd.dto.BaseDiseaseDTO;
import cn.hsa.module.clinical.clinicalpathlist.entity.ClinicPathListDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
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
  private String flag;
  private List<String> ids;
  private List<String> newDiagnoseIds;
  private List<BaseDiseaseDTO> baseDiseaseDTOS;
  private String diagnoseNames;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date startDate;        //开始日期
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date endDate;         //结束日期
  private String keyword;       //搜索关键字
}
