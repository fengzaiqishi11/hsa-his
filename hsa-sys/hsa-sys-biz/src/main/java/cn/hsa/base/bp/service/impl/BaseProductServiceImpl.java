package cn.hsa.base.bp.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bp.bo.BaseProductBO;
import cn.hsa.module.base.bp.dto.BaseProductDTO;
import cn.hsa.module.base.bp.service.BaseProductService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.base.bmm.service.impl
 * @Class_name: BaseProductManagementServiceImpl
 * @Describe: 生产企业信息Service接口实现层（提供给dubbo调用）
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/7 16:33
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/base/baseProduct")
@Slf4j
@Service("baseProductService_provider")
public class BaseProductServiceImpl extends HsafService implements BaseProductService {
    @Resource
    BaseProductBO baseProductBO;

    /**
    * @Menthod getById
    * @Desrciption   根据主键id和医院编码查询生产企业信息
     * @param map 生产企业信息表主键
    * @Author xingyu.xie
    * @Date   2020/7/17 15:37
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseProductDTO>
    **/
    @Override
    public WrapperResponse<BaseProductDTO> getById(Map map) {
        BaseProductDTO dto = baseProductBO.getById(MapUtils.get(map,"baseProductDTO"));
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
    public WrapperResponse<List<BaseProductDTO>> queryAll(Map map) {
        List<BaseProductDTO> dto = baseProductBO.queryAll(MapUtils.get(map,"baseProductDTO"));
        return WrapperResponse.success(dto);
    }

    /**
    * @Menthod queryPage
    * @Desrciption   根据数据对象来筛选查询
     * @param map 生产企业分类数据对象
    * @Author xingyu.xie
    * @Date   2020/7/17 15:38
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        PageDTO dto = baseProductBO.queryPage(MapUtils.get(map,"baseProductDTO"));
        return WrapperResponse.success(dto);
    }

    /**
     * @Menthod save
     * @Desrciption  新增或修改生产企业
     * @param map 生产企业数据参数对象
     * @Author xingyu.xie
     * @Date   2020/7/25 11:37
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> save(Map map) {
        return WrapperResponse.success(baseProductBO.save(MapUtils.get(map,"baseProductDTO")));
    }

    /**
    * @Menthod updateStatus
    * @Desrciption   删除一个或多个生产企业信息
     * @param map 生产企业数据传输对象
    * @Author xingyu.xie
    * @Date   2020/7/17 15:40
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> updateStatus(Map map) {
        return WrapperResponse.success(baseProductBO.updateStatus(MapUtils.get(map,"baseProductDTO")));
    }
}
