package cn.hsa.module.insure.emr.bo;


import cn.hsa.base.PageDTO;
import cn.hsa.module.insure.emr.dto.InsureEmrCoursrinfoDTO;

import java.util.Map;

/**
* @ClassName InsureEmrCoursrinfoBO
* @Deacription 医保电子病历上传-病程记录接口
* @Author liuhuiming
* @Date 2022-03-25
* @Version 1.0
**/
public interface InsureEmrCoursrinfoBO {

    PageDTO queryInsureEmrCoursrinfoListByPage(InsureEmrCoursrinfoDTO insureEmrCoursrinfoDTO);

    boolean saveInsureEmrCoursrinfo(InsureEmrCoursrinfoDTO insureEmrCoursrinfoDTO);

    boolean updateInsureEmrCoursrinfo(Map map);

    boolean deleteInsureEmrCoursrinfoByIds(InsureEmrCoursrinfoDTO insureEmrCoursrinfoDTO);

    InsureEmrCoursrinfoDTO queryInsureEmrCoursrinfoById(InsureEmrCoursrinfoDTO insureEmrCoursrinfoDTO);

}
