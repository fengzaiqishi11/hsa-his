package cn.hsa.module.interf.medicalCare.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.interf.medicalCare.service
 * @Class_name: MedicalCareInterfService
 * @Describe: 医养转换his接口service
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2022-02-28 11:17
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/

@FeignClient(value = "hsa-interf")
public interface MedicalCareInterfService {

    /**
     * @Menthod: getVisitInfoRecord
     * @Desrciption: 获取医转养就诊信息
     * @Param: hospCode：医院编码，medical_info_id：就诊id
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2022-02-28 11:57
     * @Return:
     **/
    @PostMapping("/service/interf/medicalCare/getVisitInfoRecord")
    WrapperResponse<Map<String, Object>> getVisitInfoRecord(@RequestBody Map<String, Object> map);

    /**
     * @Menthod: insertCare2Medic
     * @Desrciption: 插入养转医申请数据，调用本地插入医转养的申请接口
     * @Param:
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2022-03-02 10:46
     * @Return:
     **/
    @PostMapping("/service/interf/medicalCare/insertCare2Medic")
    WrapperResponse<Boolean> insertCare2Medic(@RequestBody Map<String, Object> map);

    /**
     * @Menthod: updateApplyStatus
     * @Desrciption: 更新医转养申请状态
     * @Param:
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2022-03-02 10:48
     * @Return:
     **/
    @PostMapping("/service/interf/medicalCare/updateApplyStatus")
    WrapperResponse<Boolean> updateApplyStatus(@RequestBody Map<String, Object> map);
}
