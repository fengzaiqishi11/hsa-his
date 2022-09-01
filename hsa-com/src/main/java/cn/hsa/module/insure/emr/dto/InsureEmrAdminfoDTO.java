package cn.hsa.module.insure.emr.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

import cn.hsa.module.insure.emr.entity.InsureEmrAdminfoDO;
import lombok.Data;

/**
* @ClassName InsureEmrAdminfoDTO
* @Deacription 医保电子病历上传-入院信息dto层
* @Author liuhuiming
* @Date 2022-03-25
* @Version 1.0
**/
@Data
public class InsureEmrAdminfoDTO extends InsureEmrAdminfoDO implements Serializable {

    /**
     * 就医流水号,院内唯一号
     */
    private String visitId;

    /**
     * 就医流水号,院内唯一号
     */
    private String mdtrtSn;

    /**
     * 电子病历控件id
     */
    private String emrTemplateId;


}
