package cn.hsa.module.insure.emr.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.insure.emr.dto.InsureEmrDiseinfoDTO;

import java.util.Map;

/**
* @ClassName InsureEmrDiseinfoBO
* @Deacription 医保电子病历上传-诊断信息接口
* @Author liuhuiming
* @Date 2022-03-25
* @Version 1.0
**/
public interface InsureEmrDiseinfoBO {

    PageDTO queryInsureEmrDiseinfoListByPage(InsureEmrDiseinfoDTO insureEmrDiseinfoDTO);

    boolean saveInsureEmrDiseinfo(InsureEmrDiseinfoDTO insureEmrDiseinfoDTO);

    boolean updateInsureEmrDiseinfo(Map map);

    boolean deleteInsureEmrDiseinfoByIds(InsureEmrDiseinfoDTO insureEmrDiseinfoDTO);

    InsureEmrDiseinfoDTO queryInsureEmrDiseinfoById(InsureEmrDiseinfoDTO insureEmrDiseinfoDTO);

}
