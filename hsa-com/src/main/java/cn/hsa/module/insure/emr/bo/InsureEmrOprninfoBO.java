package cn.hsa.module.insure.emr.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.insure.emr.dto.InsureEmrOprninfoDTO;

import java.util.Map;

/**
* @ClassName InsureEmrOprninfoBO
* @Deacription 医保电子病历上传-手术记录接口
* @Author liuhuiming
* @Date 2022-03-25
* @Version 1.0
**/
public interface InsureEmrOprninfoBO {

    PageDTO queryInsureEmrOprninfoListByPage(InsureEmrOprninfoDTO insureEmrOprninfoDTO);

    boolean saveInsureEmrOprninfo(InsureEmrOprninfoDTO insureEmrOprninfoDTO);

    boolean updateInsureEmrOprninfo(Map map);

    boolean deleteInsureEmrOprninfoByIds(InsureEmrOprninfoDTO insureEmrOprninfoDTO);

    InsureEmrOprninfoDTO queryInsureEmrOprninfoById(InsureEmrOprninfoDTO insureEmrOprninfoDTO);

}
