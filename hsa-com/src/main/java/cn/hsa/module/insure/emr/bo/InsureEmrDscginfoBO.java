package cn.hsa.module.insure.emr.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.insure.emr.dto.InsureEmrDscginfoDTO;

import java.util.Map;

/**
* @ClassName InsureEmrDscginfoBO
* @Deacription 医保电子病历上传-出院记录接口
* @Author liuhuiming
* @Date 2022-03-25
* @Version 1.0
**/
public interface InsureEmrDscginfoBO {

    PageDTO queryInsureEmrDscginfoListByPage(InsureEmrDscginfoDTO insureEmrDscginfoDTO);

    boolean saveInsureEmrDscginfo(InsureEmrDscginfoDTO insureEmrDscginfoDTO);

    boolean updateInsureEmrDscginfo(Map map);

    boolean deleteInsureEmrDscginfoByIds(InsureEmrDscginfoDTO insureEmrDscginfoDTO);

    InsureEmrDscginfoDTO queryInsureEmrDscginfoById(InsureEmrDscginfoDTO insureEmrDscginfoDTO);

}
