package cn.hsa.base.bmc.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bmc.bo.BaseMrisClassifyBO;
import cn.hsa.module.base.bmc.dto.BaseMrisClassifyDTO;
import cn.hsa.module.base.bmc.service.BaseMrisClassifyService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.base.bmm.service.impl
 * @Class_name: BaseMrisClassifyManagementServiceImpl
 * @Describe: 病案费用归类信息Service接口实现层（提供给dubbo调用）
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/7 16:33
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/base/baseMrisClassify")
@Slf4j
@Service("baseMrisClassifyService_provider")
public class BaseMrisClassifyServiceImpl extends HsafService implements BaseMrisClassifyService {
    @Resource
    BaseMrisClassifyBO baseMrisClassifyBO;

    /**
    * @Menthod getById
    * @Desrciption   根据主键id和医院编码查询病案费用归类信息
     * @param map 病案费用归类信息表主键
    * @Author xingyu.xie
    * @Date   2020/7/17 15:37
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMrisClassifyDTO>
    **/
    @Override
    public WrapperResponse<BaseMrisClassifyDTO> getById(Map map) {
        BaseMrisClassifyDTO dto = baseMrisClassifyBO.getById(MapUtils.get(map,"baseMrisClassifyDTO"));
        return WrapperResponse.success(dto);
    }

    /**
     * @Menthod queryAll
     * @Desrciption   查询全部
     * @param map 医院编码
     * @Author xingyu.xie
     * @Date   2020/7/17 15:38
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     *
     * @return*/
    @Override
    public WrapperResponse<List<BaseMrisClassifyDTO>> queryAll(Map map) {
        List<BaseMrisClassifyDTO> dto = baseMrisClassifyBO.queryAll(MapUtils.get(map,"baseMrisClassifyDTO"));
        return WrapperResponse.success(dto);
    }

    /**
    * @Menthod queryPage
    * @Desrciption   根据数据对象来筛选查询
     * @param map 病案费用归类分类数据对象
    * @Author xingyu.xie
    * @Date   2020/7/17 15:38
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        PageDTO dto = baseMrisClassifyBO.queryPage(MapUtils.get(map,"baseMrisClassifyDTO"));
        return WrapperResponse.success(dto);
    }

    /**
     * @Menthod save
     * @Desrciption  新增或修改病案费用归类
     * @param map 病案费用归类数据参数对象
     * @Author xingyu.xie
     * @Date   2020/7/25 11:37
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> save(Map map) {
        return WrapperResponse.success(baseMrisClassifyBO.save(MapUtils.get(map,"baseMrisClassifyDTO")));
    }

    /**
    * @Menthod updateBaseMrisClassifyS
    * @Desrciption  修改单条病案费用归类（有判空）
     * @param map
    * @Author xingyu.xie
    * @Date   2020/9/17 15:19
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> updateBaseMrisClassifyS(Map map) {
        return WrapperResponse.success(baseMrisClassifyBO.updateBaseMrisClassifyS(MapUtils.get(map,"baseMrisClassifyDTO")));
    }

    /**
    * @Menthod updateStatus
    * @Desrciption   删除一个或多个病案费用归类信息
     * @param map 病案费用归类数据传输对象
    * @Author xingyu.xie
    * @Date   2020/7/17 15:40
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> updateStatus(Map map) {
        return WrapperResponse.success(baseMrisClassifyBO.updateStatus(MapUtils.get(map,"baseMrisClassifyDTO")));
    }
}
