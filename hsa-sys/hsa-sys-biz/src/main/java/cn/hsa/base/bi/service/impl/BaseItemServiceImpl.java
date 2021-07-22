package cn.hsa.base.bi.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bi.bo.BaseItemBO;
import cn.hsa.module.base.bi.dto.BaseItemDTO;
import cn.hsa.module.base.bi.service.BaseItemService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.base.bi.service.impl
 * @Class_name: BaseItemImpl
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/7/30 14:31
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/base/baseItem")
@Slf4j
@Service("baseItemService_provider")
public class BaseItemServiceImpl extends HsafService implements BaseItemService {

    /**
     * 项目管理业务逻辑接口
     */
    @Resource
    private BaseItemBO baseItemBO;

    /**
     * @Method getById
     * @Desrciption 通过id获取项目信息
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/7/16 9:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bi.dto.BaseItemDTO>
     **/
    @Override
    public WrapperResponse<BaseItemDTO> getById(Map map) {
        return WrapperResponse.success(baseItemBO.getById(MapUtils.get(map,"baseItemDTO")));
    }

    /**
     * @Method queryPage()
     * @Description 分页查询所有疾病信息(默认状态为有效)
     *
     * @Param
     * [map]
     *
     * @Author liaojunjie
     * @Date 2020/7/8 8:53
     * @Return WrapperResponse<PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        return WrapperResponse.success(baseItemBO.queryPage(MapUtils.get(map,"baseItemDTO")));
    }

    /**
     * @Method queryAll
     * @Desrciption 查询所有项目消息
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/7/18 14:45
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.bi.dto.BaseItemDTO>>
     **/
    @Override
    public WrapperResponse<List<BaseItemDTO>> queryAll(Map map) {
        return WrapperResponse.success(baseItemBO.queryAll(MapUtils.get(map,"baseItemDTO")));
    }

    /**
     * @Method updateStatus()
     * @Description 修改有效标识状态
     *
     * @Param
     * [map]
     *
     * @Author liaojunjie
     * @Date 2020/7/8 20:55
     * @Return WrapperResponse<Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> updateStatus(Map map) {
        return WrapperResponse.success(baseItemBO.updateStatus(MapUtils.get(map,"baseItemDTO")));
    }

    /**
     * @Method save
     * @Desrciption 新增/修改单条疾病信息
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/7/24 16:11
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> save(Map map) {
        return WrapperResponse.success(baseItemBO.save(MapUtils.get(map,"baseItemDTO")));
    }

    /**
     * @Method getById
     * @Desrciption 通过id获取项目信息
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/7/16 9:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bi.dto.BaseItemDTO>
     **/
    @Override
    public WrapperResponse<BaseItemDTO> getByCode(Map map) {
        return WrapperResponse.success(baseItemBO.getByCode(MapUtils.get(map,"baseItemDTO")));
    }

    @Override
    public WrapperResponse<List<BaseItemDTO>> queryAllBfcId(Map map) {
        return WrapperResponse.success(baseItemBO.queryAllBfcId(MapUtils.get(map,"baseItemDTO")));
    }

    @Override
    public WrapperResponse<Boolean> upLoad(Map map) {
        return WrapperResponse.success(baseItemBO.insertUpload(map));
    }

    /**
     * @param map
     * @Method insertInsureItemMatch
     * @Desrciption 医保统一支付平台： 同步项目数据到医保匹配表
     * @Param
     * @Author fuhui
     * @Date 2021/3/20 11:05
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> insertInsureItemMatch(Map<String, Object> map) {
        return WrapperResponse.success(baseItemBO.insertInsureItemMatch(map));
    }

    @Override
    public WrapperResponse<PageDTO> queryUnifiedPage(Map<String, Object> map) {
        return WrapperResponse.success(baseItemBO.queryUnifiedPage(MapUtils.get(map,"baseItemDTO")));
    }

    /**
     * @param map 材料信息数据传输对象List
     * @Menthod updateNationCodeById
     * @Desrciption 根据ID修改国家编码
     * @Author pengbo
     * @Date 2021/3/25 16:48
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> updateNationCodeById(Map map) {
        return WrapperResponse.success(baseItemBO.updateNationCodeById(map));
    }
}
