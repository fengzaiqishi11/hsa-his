package cn.hsa.module.medic.data.dto;

import cn.hsa.module.medic.data.entity.MedicalDataDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
* @Package_name: cn.hsa.module.medic.data.entity
* @class_name: MedicalDataDO
* @Description: lis/pacs配置主表
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2021/1/28 15:02
* @Company: 创智和宇
**/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class MedicalDataDTO extends MedicalDataDO implements Serializable {
    private static final long serialVersionUID = 217448257180421094L;

    private String keyword;

    private String oldStatusCode;

    private String typeStr;

    private String sourceTypeStr;

    private List<MedicalDataDetailDTO> medicalDataDetailDTOList;
}
