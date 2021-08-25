package cn.hsa.inpt.compositequery.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.compositequery.bo.CompositeQueryBO;
import cn.hsa.module.inpt.compositequery.service.CompositeQueryService;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@HsafRestPath("/service/inpt/compositeQuery")
@Service("compositeQueryService_provider")
public class CompositeQueryServiceImpl extends HsafService implements CompositeQueryService {

    @Resource
    private CompositeQueryBO compositeQueryBO;

    /**
     * @Method queryAll
     * @Desrciption 查询所有住院病人
     * @Param inptVisitDTO-住院病人DTO
     * @Author luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date 2020/9/11 16:45
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryAll(Map map) {
        return WrapperResponse.success(compositeQueryBO.queryAll(MapUtils.get(map, "inptVisitDTO")));
    }

    /**
     * @Method queryInptVisit
     * @Desrciption 查询患者基本信息
     * @Param inptVisitDTO-住院病人DTO
     * @Author luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date 2020/9/11 16:45
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<InptVisitDTO>
     **/
    @Override
    public WrapperResponse<InptVisitDTO> queryInptVisit(Map map) {
        return WrapperResponse.success(compositeQueryBO.queryInptVisit(MapUtils.get(map, "inptVisitDTO")));
    }

    /**
     * @Method queryAdvance
     * @Desrciption 查询患者预交金信息
     * @Param inptVisitDTO-住院病人DTO
     * @Author luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date 2020/9/11 16:45
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List<Map<String, Object>>>
     **/
    @Override
    public WrapperResponse<List<Map<String, Object>>> queryAdvance(Map map) {
        return WrapperResponse.success(compositeQueryBO.queryAdvance(MapUtils.get(map, "inptVisitDTO")));
    }

    /**
     * @Method queryDisease
     * @Desrciption 查询患者诊断信息
     * @Param inptVisitDTO-住院病人DTO
     * @Author luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date 2020/9/14 16:54
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List<Map<String, Object>>>
     **/
    @Override
    public WrapperResponse<List<Map<String, Object>>> queryDisease(Map map) {
        return WrapperResponse.success(compositeQueryBO.queryDisease(MapUtils.get(map, "inptVisitDTO")));
    }

    /**
    * @Method queryVisitsByCondition
    * @Param [map]
    * @description    条件查询住院病人
    * @author marong
    * @date 2020/9/22 18:58
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>>
    * @throws
    */
    @Override
    public WrapperResponse<List<InptVisitDTO>> queryVisitsByCondition(Map map) {
        return WrapperResponse.success(compositeQueryBO.queryVisitsByCondition(MapUtils.get(map, "inptVisitDTO")));
    }


}
