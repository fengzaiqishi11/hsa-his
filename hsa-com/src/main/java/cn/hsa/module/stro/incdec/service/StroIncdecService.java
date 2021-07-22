package cn.hsa.module.stro.incdec.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.stro.incdec.dto.StroIncdecDTO;
import cn.hsa.module.stro.incdec.dto.StroIncdecDetailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

/**
 *@Package_name: cn.hsa.module.stro.incdec.service
 *@Class_name: StroIncdecService
 *@Describe: 药库报损service
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/8/11 10:22
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-stro")
public interface StroIncdecService {


    /**
     * @Method queryStroIncdecDaoPage
     * @Desrciption  分页查询
     * @param map
     * @Author liuqi1
     * @Date   2020/8/11 10:22
     * @Return java.util.List<cn.hsa.module.stro.incdec.dto.StroIncdecDTO>
     **/
    @GetMapping("/service/stro/incdec/queryStroIncdecDtoPage")
    WrapperResponse<PageDTO> queryStroIncdecDtoPage(Map<String,Object> map);

    /**
     * @Method queryStroIncdecDtoById
     * @Desrciption 单个查询
     * @param map
     * @Author liuqi1
     * @Date   2020/8/11 10:22
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.stro.incdec.dto.StroIncdecDTO>
     **/
    @GetMapping("/service/stro/incdec/queryStroIncdecDtoById")
    WrapperResponse<StroIncdecDTO> queryStroIncdecDtoById(Map<String,Object> map);

    /**
     * @Method queryStroIncdecDetailDtoPage
     * @Desrciption 获得报损明细
     * @param map
     * @Author liuqi1
     * @Date   2020/8/11 10:22
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/service/stro/incdec/queryStroIncdecDetailDtoPage")
    WrapperResponse<PageDTO> queryStroIncdecDetailDtoPage(Map<String,Object> map);

    /**
    * @Method queryStroIncdecDetailDtos
    * @Desrciption 根据条件查询出报损明细
    * @param map
    * @Author liuqi1
    * @Date   2020/9/8 13:42
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.stro.incdec.dto.StroIncdecDetailDTO>>
    **/
    @GetMapping("/service/stro/incdec/queryStroIncdecDetailDtos")
    WrapperResponse<List<StroIncdecDetailDTO>> queryStroIncdecDetailDtos(Map<String,Object> map);

    /**
     * @Method insertStroIncdecDao
     * @Desrciption 新增或编辑，按单个报损单来更新
     * @param map
     * @Author liuqi1
     * @Date   2020/8/11 10:22
     * @Return int
     **/
    @GetMapping("/service/stro/incdec/insertOrUpdateStroIncdecDto")
    WrapperResponse<Boolean> insertOrUpdateStroIncdecDto(Map<String,Object> map);

    /**
     * @Method updateStroIncdecDao
     * @Desrciption 批量审核或作废
     * @param map
     * @Author liuqi1
     * @Date   2020/8/11 10:22
     * @Return int
     **/
    @GetMapping("/service/stro/incdec/updateOrCancelStroIncdecDto")
    WrapperResponse<Boolean> updateOrCancelStroIncdecDto(Map<String,Object> map);

}
