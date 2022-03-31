package cn.hsa.module.insure.emr.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.insure.emr.dto.InsureEmrRescinfoDTO;

import java.util.Map;

/**
* @ClassName InsureEmrRescinfoBO
* @Deacription 医保电子病历上传-病情抢救记录接口
* @Author liuhuiming
* @Date 2022-03-25
* @Version 1.0
**/
public interface InsureEmrRescinfoBO {

    PageDTO queryInsureEmrRescinfoListByPage(InsureEmrRescinfoDTO insureEmrRescinfoDTO);

    boolean saveInsureEmrRescinfo(InsureEmrRescinfoDTO insureEmrRescinfoDTO);

    boolean updateInsureEmrRescinfo(Map map);

    boolean deleteInsureEmrRescinfoByIds(InsureEmrRescinfoDTO insureEmrRescinfoDTO);

    InsureEmrRescinfoDTO queryInsureEmrRescinfoById(InsureEmrRescinfoDTO insureEmrRescinfoDTO);
}
