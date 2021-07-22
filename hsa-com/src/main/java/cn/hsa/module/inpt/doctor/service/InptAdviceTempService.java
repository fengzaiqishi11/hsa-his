package cn.hsa.module.inpt.doctor.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDetailTempDTO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceTempDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 *@Package_name: cn.hsa.module.inpt.doctor.service
 *@Class_name: InptAdviceTempService
 *@Describe: 医嘱模板service
 *@Author: zengfeng
 *@Eamil: zengfeng@powersi.com.cn
 *@Date: 2020-10-20 19:46:53
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-inpt")
public interface InptAdviceTempService {

    /**
     * @Method queryInptAdviceTemp
     * @Desrciption 医嘱模板分页查询
     * @param map: inptAdviceTempDTO
     * @Author zengfeng
     * @Date   2020/10/20 19:46:53
     * @Return cn.hsa.base.PageDTO
     **/
    @GetMapping("/service/inpt/adviceTemp/queryInptAdviceTempPage")
    public WrapperResponse<PageDTO> queryInptAdviceTempPage(Map<String, Object> map);


    /**
     * @Menthod queryInptAdviceTemp
     * @Desrciption  查询模板信息
     * @param map ： inptAdviceTempDTO 医嘱模板
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<OutptPrescribeTempDTO>
     **/
    @GetMapping("/service/inpt/adviceTemp/queryInptAdviceTemp")
    public WrapperResponse<List<InptAdviceTempDTO>> queryInptAdviceTemp(Map<String, Object> map);

    /**
     * @Menthod queryInptAdviceDetailTemp
     * @Desrciption  查询模板明细信息
     * @param map ： inptAdviceTempDTO 医嘱模板
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<OutptPrescribeTempDTO>
     **/
    @GetMapping("/service/inpt/adviceTemp/queryInptAdviceDetailTemp")
    public WrapperResponse<List<InptAdviceDetailTempDTO>> queryInptAdviceDetailTemp(Map<String, Object> map);

    /**
     * @Method saveInptAdvice
     * @Desrciption 保存医院医嘱信息
     * @param map：inptAdviceDTOList 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return boolean
     **/
    @PostMapping("/service/inpt/adviceTemp/saveAdciceTemp")
    WrapperResponse<Boolean> saveAdciceTemp(Map<String, Object> map);

    /**
     * @Method updateAdviceTemp
     * @Desrciption 审核、删除医嘱模板
     * @param map：inptAdviceTempDTO
     * @Author zengfeng
     * @Date   2020/10/20 19:46:53
     * @Return java.lang.Boolean
     **/
    @PostMapping("/service/inpt/adviceTemp/updateAdviceTemp")
    WrapperResponse<Boolean> updateAdviceTemp(Map<String, Object> map);
}
