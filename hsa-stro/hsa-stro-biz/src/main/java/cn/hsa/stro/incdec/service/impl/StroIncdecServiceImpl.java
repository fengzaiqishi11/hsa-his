package cn.hsa.stro.incdec.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.stro.incdec.bo.StroIncdecBO;
import cn.hsa.module.stro.incdec.dto.StroIncdecDTO;
import cn.hsa.module.stro.incdec.dto.StroIncdecDetailDTO;
import cn.hsa.module.stro.incdec.service.StroIncdecService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *@Package_name: cn.hsa.stro.incdec.service
 *@Class_name: StroIncdecServiceImpl
 *@Describe:
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/8/11 10:34
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/stro/incdec")
@Service("stroIncdecService_provider")
public class StroIncdecServiceImpl extends HsafService implements StroIncdecService {

    @Resource
    StroIncdecBO stroIncdecBO;

    /**
     * @Method queryStroIncdecDaoPage
     * @Desrciption  分页查询
     * @param map
     * @Author liuqi1
     * @Date   2020/8/11 10:34
     * @Return java.util.List<cn.hsa.module.stro.incdec.dto.StroIncdecDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryStroIncdecDtoPage(Map<String, Object> map) {
        StroIncdecDTO stroIncdecDTO = MapUtils.get(map,"stroIncdecDTO");
        return WrapperResponse.success(stroIncdecBO.queryStroIncdecDTOPage(stroIncdecDTO));
    }

    /**
     * @Method queryStroIncdecDtoById
     * @Desrciption 单个查询
     * @param map
     * @Author liuqi1
     * @Date   2020/8/11 10:34
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.stro.incdec.dto.StroIncdecDTO>
     **/
    @Override
    public WrapperResponse<StroIncdecDTO> queryStroIncdecDtoById(Map<String, Object> map) {
        StroIncdecDTO stroIncdecDTO = MapUtils.get(map,"stroIncdecDTO");
        return WrapperResponse.success(stroIncdecBO.queryStroIncdecDTOById(stroIncdecDTO));
    }

    /**
     * @Method queryStroIncdecDetailDtoPage
     * @Desrciption 获得报损明细
     * @param map
     * @Author liuqi1
     * @Date   2020/8/11 10:34
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryStroIncdecDetailDtoPage(Map<String, Object> map) {
        StroIncdecDTO stroIncdecDTO = MapUtils.get(map,"stroIncdecDTO");
        return WrapperResponse.success(stroIncdecBO.queryStroIncdecDetailDtoPage(stroIncdecDTO));
    }

    /**
    * @Method queryStroIncdecDetailDtos
    * @Desrciption 根据条件查询出报损明细
    * @param map
    * @Author liuqi1
    * @Date   2020/9/8 13:44
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.stro.incdec.dto.StroIncdecDetailDTO>>
    **/
    @Override
    public WrapperResponse<List<StroIncdecDetailDTO>> queryStroIncdecDetailDtos(Map<String, Object> map) {
        StroIncdecDTO stroIncdecDTO = MapUtils.get(map,"stroIncdecDTO");
        List<StroIncdecDetailDTO> stroIncdecDetailDTOS = stroIncdecBO.queryStroIncdecDetailDtos(stroIncdecDTO);
        return WrapperResponse.success(stroIncdecDetailDTOS);
    }

    /**
     * @Method insertStroIncdecDao
     * @Desrciption 新增或编辑，按单个报损单来更新
     * @param map
     * @Author liuqi1
     * @Date   2020/8/11 10:34
     * @Return int
     **/
    @Override
    public WrapperResponse<Boolean> insertOrUpdateStroIncdecDto(Map<String, Object> map) {
        StroIncdecDTO stroIncdecDTO = MapUtils.get(map,"stroIncdecDTO");
        return WrapperResponse.success(stroIncdecBO.insertOrUpdateStroIncdecDTO(stroIncdecDTO));
    }

    /**
     * @Method updateStroIncdecDao
     * @Desrciption 批量审核或作废
     * @param map
     * @Author liuqi1
     * @Date   2020/8/11 10:34
     * @Return int
     **/
    @Override
    public WrapperResponse<Boolean> updateOrCancelStroIncdecDto(Map<String, Object> map) {
        StroIncdecDTO stroIncdecDTO = MapUtils.get(map,"stroIncdecDTO");
        boolean b = stroIncdecBO.updateOrCancelStroIncdecDTO(stroIncdecDTO);
        return WrapperResponse.success(b);
    }

}
