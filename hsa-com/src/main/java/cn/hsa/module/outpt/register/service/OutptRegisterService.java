package cn.hsa.module.outpt.register.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.outpt.outptclassify.dto.OutptClassifyCostDTO;
import cn.hsa.module.outpt.queue.dto.OutptDoctorQueueDto;
import cn.hsa.module.outpt.register.dto.OutptRegisterDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.register.service
 * @Class_name: OutptRegisterService
 * @Describe: 门诊挂号Service接口层（提供给dubbo调用）
 * @Author: liaojiguang
 * @Eamil: liaojiguang@powersi.com.cn
 * @Date: 2020/8/10 18:01
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-outpt")
public interface OutptRegisterService {

    /**
     * @Menthod queryRegisterInfoByParams
     * @Desrciption 根据参数获取挂号信息
     * @param map  入参
     * @Author liaojiguang
     * @Date   2020/8/10 17:40
     * @Return cn.hsa.module.outpt.register.dto.OutpoRegisterDTO
     **/
    @PutMapping("/service/outpt/register/queryRegisterInfoByParamsPage")
    WrapperResponse<PageDTO> queryRegisterInfoByParamsPage(Map<String,Object> map);

    /**
     * @Menthod updateOutRegister
     * @Desrciption 门诊退号
     * @param map  入参[id,hospCode]
     * @Author liaojiguang
     * @Date   2020/8/10 17:40
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean> 成功/失败
     **/
    @PutMapping("/service/outpt/register/updateOutRegister")
    WrapperResponse<Boolean> updateOutRegister(Map map);

    @PutMapping("/service/outpt/register/outRegisterSettle")
    WrapperResponse<Map<String, String>> saveOutRegisterSettle(Map map);

    @PutMapping("/service/outpt/register/queryOutptDoctorList")
    WrapperResponse<List<OutptDoctorQueueDto>> queryOutptDoctorList(Map map);

    @PutMapping("/service/outpt/register/queryRegisterCostList")
    WrapperResponse<List<OutptClassifyCostDTO>> queryRegisterCostList(Map map);

    @PutMapping("/service/outpt/register/queryOutptDeptClassify")
    WrapperResponse<List<BaseDeptDTO>> queryOutptDeptClassify(Map map);

    /**
     * @Method updateCostPreferential
     * @Desrciption 处理优惠金额
       @params [map]
     * @Author chenjun
     * @Date   2020/9/4 11:35
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PutMapping("/service/outpt/register/updateCostPreferential")
    WrapperResponse<List<OutptClassifyCostDTO>> updateCostPreferential(Map map);


    /**
     * @Author 医保二部-张金平
     * @Date 2022-07-01 17:00
     * @Description 根据身份证查询门诊挂号信息
     * @param map
     * @return cn.hsa.module.outpt.register.dto.OutptRegisterDTO
     */
    List<OutptRegisterDTO> queryRegisterInfoByCertno(Map<String, Object> map);

    OutptRegisterDTO getOutptRegisterByVisitId(Map<String, Object> map);
}
