package cn.hsa.module.insure.emr.dto;

import java.util.Date;
import java.io.Serializable;

import cn.hsa.module.insure.emr.entity.InsureEmrDieinfoDO;
import lombok.Data;

/**
* @ClassName InsureEmrDieinfoDTO
* @Deacription 医保电子病历上传-死亡记录dto层
* @Author liuhuiming
* @Date 2022-03-25
* @Version 1.0
**/
@Data
public class InsureEmrDieinfoDTO extends InsureEmrDieinfoDO implements Serializable {

    /**
     * 电子病历控件id
     */
    private String emrTemplateId;

}
