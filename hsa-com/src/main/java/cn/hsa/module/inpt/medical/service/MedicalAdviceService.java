package cn.hsa.module.inpt.medical.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
* @Package_name: cn.hsa.module.inpt.medical.service
* @class_name: MedicalAdviceService
* @Description: 医嘱核收Service
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/9/9 10:45
* @Company: 创智和宇
**/
@FeignClient(value = "hsa-inpt")
public interface MedicalAdviceService {

    /**
     * @Method: getMedicalAdvices
     * @Description: 获取未核收医嘱列表
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/9 11:18
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/service/inpt/medical/getMedicalAdvices")
    WrapperResponse<PageDTO> getMedicalAdvices(Map map);

    /**
     * @Method: acceptMedicalAdvices
     * @Description: 医嘱核收
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/9 14:30
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/inpt/medical/acceptMedicalAdvices")
    WrapperResponse<Boolean> acceptMedicalAdvices(Map map);

    /**
     * @Method: refuseMedicalAdvices
     * @Description: 医嘱拒收
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/9 14:13
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @PostMapping("/service/inpt/medical/refuseMedicalAdvices")
    WrapperResponse<Boolean> refuseMedicalAdvices(Map map);

    /**
     * @Method: longCost
     * @Description: 长期费用滚动
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/31 14:33
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/inpt/medical/longCost")
    WrapperResponse<Boolean> longCost(Map map);
    /**
     * @Method: updateAdviceInChecked
     * @Description: 修改医嘱信息，核收人，核对签名人，核收状态
     * isChecked: 0：未核收，1：已核对，2：已核收未核对，3：核对退回，4：
     * @Param: [medicalAdviceDTO]
     * @Author: pengbo
     * @Date: 2022/08/24 16:25
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/inpt/medical/updateAdviceInChecked")
    WrapperResponse<Boolean> updateAdviceInChecked(Map<String, Object> map);

    WrapperResponse<PageDTO> getMedicalAdvicesNew(Map<String, Object> map);
}
