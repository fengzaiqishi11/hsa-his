package cn.hsa.module.inpt.nurse.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Map;

/**
 *@Package_name: cn.hsa.module.inpt.nurse.service
 *@Class_name: LeaveHospitalRecallService
 *@Describe: 出院召回
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/9/15 16:02
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-inpt")
public interface LeaveHospitalRecallService {

    /**
     * @Method queryLeaveHospitalRecallPage
     * @Desrciption 出院召回分页查询
     * @param map
     * @Author liuqi1
     * @Date   2020/9/15 16:13
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    WrapperResponse<PageDTO> queryLeaveHospitalRecallPage(Map<String,Object> map);
}
