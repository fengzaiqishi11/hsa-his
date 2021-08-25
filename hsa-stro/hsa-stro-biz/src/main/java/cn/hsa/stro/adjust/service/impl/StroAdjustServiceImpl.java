package cn.hsa.stro.adjust.service.impl;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.stro.adjust.bo.StroAdjustBO;
import cn.hsa.module.stro.adjust.dto.StroAdjustDTO;
import cn.hsa.module.stro.adjust.dto.StroAdjustDetailDTO;
import cn.hsa.module.stro.adjust.service.StroAdjustService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *@Package_name: cn.hsa.module.stro.adjust.service
 *@Class_name: StroAdjustServiceImpl
 *@Describe: 药品调价service实现类
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/8/1 19:37
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/stro/adjust")
@Service("stroAdjustService_provider")
public class StroAdjustServiceImpl extends HsafService implements StroAdjustService {

    @Resource
    StroAdjustBO stroAdjustBO;


    /**
     * @Method queryStroAdjustDoPage
     * @Desrciption 分页查询
     * @param map
     * @Author liuqi1
     * @Date   2020/8/1 21:21
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public WrapperResponse<PageDTO> queryStroAdjustDtoPage(Map<String,Object> map) {
        StroAdjustDTO stroAdjustDTO = MapUtils.get(map,"stroAdjustDTO");
        return WrapperResponse.success(stroAdjustBO.queryStroAdjustDtoPage(stroAdjustDTO));
    }

    /**
    * @Menthod queryStroAdjustDtoAll
    * @Desrciption 查询所有调价单 根据id
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/5/18 15:47
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @Override
    public WrapperResponse<List<StroAdjustDTO>> queryStroAdjustDtoAll(Map<String, Object> map) {
      StroAdjustDTO stroAdjustDTO = MapUtils.get(map,"stroAdjustDTO");
      return WrapperResponse.success(stroAdjustBO.queryStroAdjustDtoAll(stroAdjustDTO));
    }

  /**
     * @Method queryStroAdjustDtoById
     * @Desrciption 单个查询
     * @param map
     * @Author liuqi1
     * @Date   2020/8/5 11:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.stro.adjust.dto.StroAdjustDTO>
     **/
    @Override
    public WrapperResponse<StroAdjustDTO> queryStroAdjustDtoById(Map<String, Object> map) {
        StroAdjustDTO stroAdjustDTO = MapUtils.get(map,"stroAdjustDTO");
        return WrapperResponse.success(stroAdjustBO.queryStroAdjustDtoById(stroAdjustDTO));
    }

    /**
    * @Method queryStroAdjustDetailDtoPage
    * @Desrciption 获得调价明细
    * @param map
    * @Author liuqi1
    * @Date   2020/8/4 10:12
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @Override
    public WrapperResponse<PageDTO> queryStroAdjustDetailDtoPage(Map<String,Object> map) {
        StroAdjustDTO stroAdjustDTO = MapUtils.get(map,"stroAdjustDTO");
        return WrapperResponse.success(stroAdjustBO.queryStroAdjustDetailDtoPage(stroAdjustDTO));
    }

    /**
    * @Method queryStroAdjustDetailDtos
    * @Desrciption 根据条件查询出调价明细
    * @param map
    * @Author liuqi1
    * @Date   2020/9/8 13:58
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.stro.adjust.dto.StroAdjustDetailDTO>>
    **/
    @Override
    public WrapperResponse<List<StroAdjustDetailDTO>> queryStroAdjustDetailDtos(Map<String, Object> map) {
        StroAdjustDTO stroAdjustDTO = MapUtils.get(map,"stroAdjustDTO");
        List<StroAdjustDetailDTO> stroAdjustDetailDTOS = stroAdjustBO.queryStroAdjustDetailDtos(stroAdjustDTO);
        return WrapperResponse.success(stroAdjustDetailDTOS);
    }

    /**
     * @Method insertStroAdjustDo
     * @Desrciption 新增或更新调价信息
     * @param map
     * @Author liuqi1
     * @Date   2020/8/1 21:22
     * @Return boolean
     **/
    @Override
    public WrapperResponse<Boolean> insertOrUpdateStroAdjustDto(Map<String,Object> map) {
        StroAdjustDTO stroAdjustDTO = MapUtils.get(map,"stroAdjustDTO");
        return WrapperResponse.success(stroAdjustBO.insertOrUpdateStroAdjustDto(stroAdjustDTO));
    }

    /**
     * @Method updateStroAdjustDo
     * @Desrciption 批量审核或作废调价
     * @param map
     * @Author liuqi1
     * @Date   2020/8/1 21:22
     * @Return boolean
     **/
    @Override
    public WrapperResponse<Boolean> updateOrCancelStroAdjustDto(Map<String,Object> map) {
        StroAdjustDTO stroAdjustDTO = MapUtils.get(map,"stroAdjustDTO");
        return WrapperResponse.success(stroAdjustBO.updateOrCancelStroAdjustDto(stroAdjustDTO));
    }
}
