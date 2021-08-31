package cn.hsa.module.stro.adjust.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.stro.adjust.dto.StroAdjustDTO;
import cn.hsa.module.stro.adjust.dto.StroAdjustDetailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

/**
 *@Package_name: cn.hsa.module.stro.adjust.service
 *@Class_name: StroAdjustService
 *@Describe: 药品调价service
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/8/1 19:23
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-stro")
public interface StroAdjustService {

    /**
     * @Method queryStroAdjustDaoPage
     * @Desrciption  分页查询
     * @param map
     * @Author liuqi1
     * @Date   2020/8/1 18:56
     * @Return java.util.List<cn.hsa.module.stro.adjust.dto.StroAdjustDTO>
     **/
    @GetMapping("/service/stro/adjust/queryStroAdjustDtoPage")
    WrapperResponse<PageDTO> queryStroAdjustDtoPage(Map<String,Object> map);


    /**
    * @Menthod queryStroAdjustDtoAll
    * @Desrciption 查询所有调价单 根据id
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/5/18 15:46
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping("/service/stro/adjust/queryStroAdjustDtoAll")
    WrapperResponse<List<StroAdjustDTO>> queryStroAdjustDtoAll(Map<String,Object> map);

    /**
    * @Method queryStroAdjustDtoById
    * @Desrciption 单个查询
    * @param map
    * @Author liuqi1
    * @Date   2020/8/5 11:04
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.stro.adjust.dto.StroAdjustDTO>
    **/
    @GetMapping("/service/stro/adjust/queryStroAdjustDtoById")
    WrapperResponse<StroAdjustDTO> queryStroAdjustDtoById(Map<String,Object> map);

    /**
    * @Method queryStroAdjustDetailDtoPage
    * @Desrciption 获得调价明细
    * @param map
    * @Author liuqi1
    * @Date   2020/8/4 10:09
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping("/service/stro/adjust/queryStroAdjustDetailDtoPage")
    WrapperResponse<PageDTO> queryStroAdjustDetailDtoPage(Map<String,Object> map);

    /**
    * @Method queryStroAdjustDetailDtos
    * @Desrciption 根据条件查询出调价明细
    * @param map
    * @Author liuqi1
    * @Date   2020/9/8 13:56
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.stro.adjust.dto.StroAdjustDetailDTO>>
    **/
    @GetMapping("/service/stro/adjust/queryStroAdjustDetailDtos")
    WrapperResponse<List<StroAdjustDetailDTO>> queryStroAdjustDetailDtos(Map<String,Object> map);

    /**
     * @Method insertStroAdjustDao
     * @Desrciption 新增或编辑，按单个调价单来更新
     * @param map
     * @Author liuqi1
     * @Date   2020/8/1 18:56
     * @Return int
     **/
    @GetMapping("/service/stro/adjust/insertOrUpdateStroAdjustDto")
    WrapperResponse<Boolean> insertOrUpdateStroAdjustDto(Map<String,Object> map);

    /**
     * @Method updateStroAdjustDao
     * @Desrciption 批量审核或作废
     * @param map
     * @Author liuqi1
     * @Date   2020/8/1 18:57
     * @Return int
     **/
    @GetMapping("/service/stro/adjust/updateOrCancelStroAdjustDto")
    WrapperResponse<Boolean> updateOrCancelStroAdjustDto(Map<String,Object> map);

}
