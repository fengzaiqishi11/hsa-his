package cn.hsa.module.emr.emrprintsetting.dto;

import cn.hsa.module.emr.emrprintsetting.entity.EmrPrintSettingDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.emr.emrprintsetting.dto
 * @Class_name: EmrPrintSettingDTO
 * @Describe: 电子病历打印设置数据传输对象
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/9/17 15:46
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class EmrPrintSettingDTO extends EmrPrintSettingDO implements Serializable {
  private static final long serialVersionUID = 217448257180403222L;
  private String keyword;
  private List<String> ids;
}
