package cn.hsa.module.emr.emrclassify.dto;

import cn.hsa.module.emr.emrclassify.entity.EmrClassifyDO;
import cn.hsa.module.emr.emrelement.dto.EmrElementDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.emr.emrclassify.dto
 * @Class_name: EmrClassifyDTO
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/9/25 17:23
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class EmrClassifyDTO extends EmrClassifyDO implements Serializable {

    private static final long serialVersionUID = 2388067263923498804L;
    //编码列表
    private List<String> codes;
    //模板html
    private String tempHtml;
    //是否为父节点
    private String son;

    /**
     * 病历时间记录项    官红强
     */
    private List<EmrElementDTO> recordList;

    private String ksDeptId; // base表的科室id，用于判断科室是否为门诊or住院
}
