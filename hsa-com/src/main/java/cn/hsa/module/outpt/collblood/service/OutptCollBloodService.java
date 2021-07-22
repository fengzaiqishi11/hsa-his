package cn.hsa.module.outpt.collblood.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.medic.apply.dto.MedicalApplyDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.collblood.service
 * @Class_name: OutptCollBloodService
 * @Describe: 门诊采血service
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-04-28 11:42
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "has-outpt")
public interface OutptCollBloodService {

    /**
     * @Menthod: queryCollBlood
     * @Desrciption: 查询门诊采血列表数据
     * @Param: medicalApplyDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-04-28 15:18
     * @Return: PageDTO
     **/
    @GetMapping("/service/outpt/outptCollBlood/queryCollBlood")
    WrapperResponse<PageDTO> queryCollBlood(Map map);

    /**
     * @Menthod: saveCollBlood
     * @Desrciption: 保存门诊采血数据
     * @Param: medicalApplyDTOList
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-04-28 15:18
     * @Return: Boolean
     **/
    @PostMapping("/service/outpt/outptCollBlood/saveCollBlood")
    WrapperResponse<Boolean> saveCollBlood(Map map);
}
