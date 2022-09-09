package cn.hsa.module.insure.emr.dto;

import java.util.Date;
import java.io.Serializable;

import cn.hsa.module.insure.emr.entity.InsureEmrCoursrinfoDO;
import lombok.Data;

/**
* @ClassName InsureEmrCoursrinfoDTO
* @Deacription 医保电子病历上传-病程记录dto层
* @Author liuhuiming
* @Date 2022-03-25
* @Version 1.0
**/
@Data
public class InsureEmrCoursrinfoDTO extends InsureEmrCoursrinfoDO implements Serializable {

  /**
   * 电子病历控件id
   */
  private String emrTemplateId;

}
