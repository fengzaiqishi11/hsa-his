package cn.hsa.module.outpt.prescribe.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.prescribe.dto.OutptPrescribeTempDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 *@Package_name: cn.hsa.module.outpt.prescribe.service
 *@Class_name: OutptPrescribeTempService
 *@Describe: 处方模板
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/8/19 15:02
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-stro")
public interface OutptPrescribeTempService {

    /**
    * @Method queryOutptPrescribeTempDTOPage
    * @Desrciption
    * @param map
    * @Author liuqi1
    * @Date   2020/8/19 15:04
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping("/service/outpt/prescribe/queryOutptPrescribeTempDTOPage")
    WrapperResponse<PageDTO> queryOutptPrescribeTempDTOPage(Map<String,Object> map);

    /**
     * @Menthod queryOutptPrescribeTempDTO
     * @Desrciption  查询模板信息
     * @param map ： outptPrescribeTempDTO 处方模板
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<OutptPrescribeTempDTO>
     **/
    @GetMapping("/service/outpt/prescribe/queryOutptPrescribeTempDTO")
    WrapperResponse<List<OutptPrescribeTempDTO>> queryOutptPrescribeTempDTO(Map map);

    /**
     * @Menthod queryOutptPrescribeTempDTO
     * @Desrciption  查询模板信息
     * @param map ： outptPrescribeTempDTO 处方模板
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<OutptPrescribeTempDetailDTO>
     **/
    @GetMapping("/service/outpt/prescribe/queryOutptPrescribeTempDetails")
    WrapperResponse<PageDTO> queryOutptPrescribeTempDetails(Map map);

    /*
     * @Menthod savePrescribeTemp
     * @Desrciption  保存处方模板信息
     * @param outptPrescribeDTO 处方明细
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return boolean
     */
    @PostMapping("/service/outpt/prescribe/savePrescribeTemp")
    WrapperResponse<Boolean> savePrescribeTemp(Map map);

    /*
     * @Menthod updateOutptPrescribeTempDTO
     * @Desrciption  修改模板信息
     * @param
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return boolean
     */
    @PostMapping("/service/outpt/prescribe/updateOutptPrescribeTempDTO")
    WrapperResponse<Boolean> updateOutptPrescribeTempDTO(Map map);
}
