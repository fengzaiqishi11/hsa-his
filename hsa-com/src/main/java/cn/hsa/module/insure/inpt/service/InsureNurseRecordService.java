package cn.hsa.module.insure.inpt.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.inpt.service
 * @Class_name: InsureNurseRecordService
 * @Describe: 统一支付平台-护理生命记录service
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-08-21 17:23
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-insure")
public interface InsureNurseRecordService {

    /**
     * @Menthod: addInsureNurseRecord
     * @Desrciption: 统一支付平台-护理生命记录上传【4602】
     * @Param: visitId
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-08-22 08:55
     * @Return:
     **/
    @PostMapping("/service/insure/insureNurseRecord/addInsureNurseRecord")
    WrapperResponse<Map<String, Object>> addInsureNurseRecord(Map<String, Object> map);
}
