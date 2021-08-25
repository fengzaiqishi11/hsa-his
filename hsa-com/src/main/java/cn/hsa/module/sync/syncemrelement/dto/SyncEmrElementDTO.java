package cn.hsa.module.sync.syncemrelement.dto;

import cn.hsa.module.sync.syncemrelement.entity.SyncEmrElementDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.emr.emrclassifyelement.dto
 * @Class_name: EmrElementDTO
 * @Describe: 电子病历元素管理数据传输对象
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/9/17 16:34
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class SyncEmrElementDTO extends SyncEmrElementDO implements Serializable {
  private List<String> codes;   //编码列表
  private String sysCodeRefName;
  private String patientCodeRefName;
  private String deptId;
  private String noValue;
}
