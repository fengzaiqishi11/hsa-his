package cn.hsa.module.outpt.outptmedicaltemplate.dto;

import cn.hsa.module.outpt.outptmedicaltemplate.entity.OutptMedicalTemplateDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.outptmedicaltemplate.dto
 * @Class_name: OutptMedicalTemplateDTO
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/3/9 14:14
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutptMedicalTemplateDTO extends OutptMedicalTemplateDO implements Serializable {
  private static final long serialVersionUID = 420678770212769978L;
  private List<String>  ids;
  private String keyword;
  private String diseaseName; //疾病名称
  private List<Map<String,Object>> diseaseIds;
}
