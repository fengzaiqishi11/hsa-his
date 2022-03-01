package cn.hsa.module.interf.medicalCare.bo;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.interf.medicalCare.bo
 * @Class_name: MedicalCareInterfBO
 * @Describe: 医养转换his接口bo
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2022-02-28 11:25
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface MedicalCareInterfBO {

    /**
     * @Menthod: getVisitInfoRecord
     * @Desrciption: 获取医转养就诊信息
     * @Param: hospCode：医院编码，medical_info_id：就诊id
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2022-02-28 11:57
     * @Return:
     **/
    Map<String, Object> getVisitInfoRecord(Map<String, Object> map);
}
