package cn.hsa.stro.stroout.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.stro.stroout.bo.StroOutBO;
import cn.hsa.module.stro.stroout.dto.StroOutDTO;
import cn.hsa.module.stro.stroout.service.StroOutService;
import cn.hsa.util.MapUtils;
import groovy.util.logging.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.stro.stroout.service.impl
 * @Class_name: StrooutServiceImpl
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/7/30 17:34
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
@HsafRestPath("/service/stro/stroOut")
@Service("stroOutService_provider")
public class StroOutServiceImpl extends HsafService implements StroOutService {

    /**
     * 库房出库bo接口
     */
    @Resource
    private StroOutBO stroOutBO;

    /**
     * @Method getById
     * @Desrciption 通过id获取到某个出库单信息
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/7/30 19:16
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.stro.outinstro.dto.StroOutinDTO>
     **/
    @Override
    public WrapperResponse<StroOutDTO> getById(Map map) {
        return WrapperResponse.success(stroOutBO.getById(MapUtils.get(map,"stroOutDTO")));
    }

    /**
     * @Method queryPage
     * @Desrciption 分页查询出库单信息（包含出库明细）
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/7/30 19:16
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        return WrapperResponse.success(stroOutBO.queryPage(MapUtils.get(map,"stroOutDTO")));
    }

    /**
     * @Method queryAll
     * @Desrciption 查询所有出库单信息（包含出库明细）
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/7/30 19:16
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.stro.outinstro.dto.StroOutDTO>>
     **/
    @Override
    public WrapperResponse<List<StroOutDTO>> queryAll(Map map) {
        return WrapperResponse.success(stroOutBO.queryAll(MapUtils.get(map,"stroOutDTO")));
    }

    /**
     * @Method save
     * @Desrciption 新增和保存出库单信息(包括出库单明细)
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/7/30 19:16
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> save(Map map) {
        return WrapperResponse.success(stroOutBO.save(MapUtils.get(map,"stroOutDTO")));
    }

    /**
     * @Method updateAuditCode
     * @Desrciption 审核和作废
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/7/30 19:17
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> updateAuditCode(Map map) {
        return WrapperResponse.success(stroOutBO.updateAuditCode(MapUtils.get(map,"stroOutDTO")));
    }

    /**
     * @Method queryStock
     * @Desrciption 查询处理后的库存明细
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/8/27 12:46
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryStock(Map map) {
        return WrapperResponse.success(stroOutBO.queryStock(MapUtils.get(map,"stroOutDTO")));
    }

    /**
     * @Method insertWholeOut
     * @Desrciption 整单出库
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/12/16 10:43
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.stro.stroout.dto.StroOutDTO>
     **/
    @Override
    public WrapperResponse<StroOutDTO> insertWholeOut(Map map) {
        return WrapperResponse.success(stroOutBO.insertWholeOut(MapUtils.get(map,"stroOutDTO")));
    }

    /**
     * @Method queryWholeOut
     * @Desrciption 整单出库前进行库存数量查询
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/12/16 10:43
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.stro.stroout.dto.StroOutDTO>
     **/
    @Override
    public WrapperResponse<StroOutDTO> queryWholeOut(Map map) {
        return WrapperResponse.success(stroOutBO.queryWholeOut(MapUtils.get(map,"stroOutDTO")));
    }


}
