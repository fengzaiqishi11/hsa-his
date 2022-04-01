package cn.hsa.module.insure.emr.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.insure.emr.dto.InsureEmrDieinfoDTO;

import java.util.Map;

/**
* @ClassName InsureEmrDieinfoBO
* @Deacription 医保电子病历上传-死亡记录接口
* @Author liuhuiming
* @Date 2022-03-25
* @Version 1.0
**/
public interface InsureEmrDieinfoBO {

    PageDTO queryInsureEmrDieinfoListByPage(InsureEmrDieinfoDTO insureEmrDieinfoDTO);

    boolean saveInsureEmrDieinfo(InsureEmrDieinfoDTO insureEmrDieinfoDTO);

    boolean updateInsureEmrDieinfo(Map map);

    boolean deleteInsureEmrDieinfoByIds(InsureEmrDieinfoDTO insureEmrDieinfoDTO);

    InsureEmrDieinfoDTO queryInsureEmrDieinfoById(InsureEmrDieinfoDTO insureEmrDieinfoDTO);


}
