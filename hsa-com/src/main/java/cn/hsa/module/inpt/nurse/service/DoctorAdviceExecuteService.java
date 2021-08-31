package cn.hsa.module.inpt.nurse.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 *@Package_name: cn.hsa.module.inpt.service
 *@Class_name: DoctorAdviceExecuteService
 *@Describe: 医嘱执行
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/9/2 11:22
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-inpt")
public interface DoctorAdviceExecuteService {

    /**
     * @Method queryDoctorAdviceExecuteInfo
     * @Desrciption 医嘱执行查询
     * @param map
     * @Author liuqi1
     * @Date   2020/9/2 11:06
     * @Return cn.hsa.base.PageDTO
     **/
    @GetMapping("/service/inpt/nurse/queryDoctorAdviceExecuteInfo")
    WrapperResponse<PageDTO> queryDoctorAdviceExecuteInfo(Map<String,Object> map);

    /**
     * @Method updateDoctorAdviceExecute
     * @Desrciption 医嘱执行修改
     * @param map
     * @Author liuqi1
     * @Date   2020/9/2 11:16
     * @Return boolean
     **/
    @GetMapping("/service/inpt/nurse/updateDoctorAdviceExecute")
    WrapperResponse<Boolean> updateDoctorAdviceExecute(Map<String,Object> map);

    /**
    * @Menthod updateAdviceExecuteCance
    * @Desrciption 医嘱执行取消
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/1/13 9:16
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/service/inpt/nurse/updateAdviceExecuteCance")
    WrapperResponse<Boolean> updateAdviceExecuteCance(Map<String,Object> map);

    /**
     * @Package_name: cn.hsa.module.inpt.nurse.service
     * @Class_name:: DoctorAdviceExecuteService
     * @Description: 未停医嘱缺药查询
     * @Author: ljh
     * @Date: 2020/9/24 14:10
     * @Company1: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    @GetMapping("/service/inpt/medical/queryNoMedical")
    WrapperResponse<PageDTO> queryNoMedical(Map map);

}
