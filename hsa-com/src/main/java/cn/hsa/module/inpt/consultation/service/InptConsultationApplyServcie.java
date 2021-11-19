package cn.hsa.module.inpt.consultation.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.consultation.dto.InptConsultationApplyDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt.consultation.service
 * @Class_name: InptConsultationApplyServcie
 * @Describe: 会诊申请service
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-11-04 20:08
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-inpt")
public interface InptConsultationApplyServcie {

    /**
     * @Menthod: saveConsultationApply
     * @Desrciption: 保存会诊申请记录
     * @Param: inptConsultationApplyDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-11-04 20:36
     * @Return:
     **/
    @PostMapping("/service/inpt/consultationApply/saveConsultationApply")
    WrapperResponse<String> saveConsultationApply(Map map);

    /**
     * @Menthod: queryConsultationApply
     * @Desrciption: 分页查询会诊记录
     * @Param: inptConsultationApplyDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-11-04 20:36
     * @Return:
     **/
    @GetMapping("/service/inpt/consultationApply/queryConsultationApply")
    WrapperResponse<PageDTO> queryConsultationApply(Map map);

    /**
     * @Menthod: getById
     * @Desrciption: 分页查询会诊记录
     * @Param: inptConsultationApplyDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-11-04 20:36
     * @Return:
     **/
    @GetMapping("/service/inpt/consultationApply/getById")
    WrapperResponse<InptConsultationApplyDTO> getById(Map map);

    /**
     * @Menthod: updateStatus
     * @Desrciption: 更改会诊状态
     * @Param: inptConsultationApplyDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-11-04 20:42
     * @Return:
     **/
    @GetMapping("/service/inpt/consultationApply/updateStatus")
    WrapperResponse<Boolean> updateStatus(Map map);
}
