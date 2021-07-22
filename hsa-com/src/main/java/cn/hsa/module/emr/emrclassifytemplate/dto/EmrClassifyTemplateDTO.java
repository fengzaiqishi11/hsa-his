package cn.hsa.module.emr.emrclassifytemplate.dto;

import cn.hsa.module.emr.emrclassify.dto.EmrClassifyDTO;
import cn.hsa.module.emr.emrclassifyelement.dto.EmrClassifyElementDTO;
import cn.hsa.module.emr.emrclassifytemplate.entity.EmrClassifyTemplateDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.emr.emrclassifytemplate.dto
 * @Class_name:: EmrClassifyTemplateDTO
 * @Description: 
 * @Author: liaojunjie
 * @Date: 2020/9/28 14:49 
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class EmrClassifyTemplateDTO extends EmrClassifyTemplateDO implements Serializable {
    private static final long serialVersionUID = -5277696631645290515L;
    private List<String> codes;   //编码列表
    private List<EmrClassifyDTO> emrClassifyDTOS;  //分类列表
    private List<EmrClassifyElementDTO> emrClassifyElementDTOS;  //分类元素列表
    private String noDealHtml;   //未处理的html
}
