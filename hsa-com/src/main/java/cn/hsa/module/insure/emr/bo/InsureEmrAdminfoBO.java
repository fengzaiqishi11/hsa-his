package cn.hsa.module.insure.emr.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.insure.emr.dto.InsureEmrAdminfoDTO;

import java.util.Map;

/**
* @ClassName InsureEmrAdminfoBO
* @Deacription 医保电子病历上传-入院信息接口
* @Author liuhuiming
* @Date 2022-03-25
* @Version 1.0
**/
public interface InsureEmrAdminfoBO {

    PageDTO queryInsureEmrAdminfoListByPage(InsureEmrAdminfoDTO insureEmrAdminfoDTO);

    boolean saveInsureEmrAdminfo(InsureEmrAdminfoDTO insureEmrAdminfoDTO);

    boolean updateInsureEmrAdminfo(Map map);

    boolean deleteInsureEmrAdminfoByIds(InsureEmrAdminfoDTO insureEmrAdminfoDTO);

    InsureEmrAdminfoDTO queryInsureEmrAdminfoById(InsureEmrAdminfoDTO insureEmrAdminfoDTO);

}
