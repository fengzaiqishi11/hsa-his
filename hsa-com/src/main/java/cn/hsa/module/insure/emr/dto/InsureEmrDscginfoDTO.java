package cn.hsa.module.insure.emr.dto;

import java.util.Date;
import java.io.Serializable;

import cn.hsa.module.insure.emr.entity.InsureEmrDscginfoDO;
import lombok.Data;

/**
* @ClassName InsureEmrDscginfoDTO
* @Deacription 医保电子病历上传-出院记录dto层
* @Author liuhuiming
* @Date 2022-03-25
* @Version 1.0
**/
@Data
public class InsureEmrDscginfoDTO extends InsureEmrDscginfoDO implements Serializable {


    /**
     * 电子病历主键id
     */
    private String emrTemplateId;
}
