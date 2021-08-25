package cn.hsa.base.baseorderreceive.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.baseorderreceive.bo.BaseOrderReceiveBO;
import cn.hsa.module.base.baseorderreceive.dto.BaseOrderReceiveDTO;
import cn.hsa.module.base.baseorderreceive.service.BaseOrderReceiveService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.base.bmm.service.impl
 * @Class_name: BaseOrderReceiveManagementServiceImpl
 * @Describe: 领药单据类型信息Service接口实现层（提供给dubbo调用）
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/09/17 16:33
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/base/baseOrderReceive")
@Slf4j
@Service("baseOrderReceiveService_provider")
public class BaseOrderReceiveServiceImpl extends HsafService implements BaseOrderReceiveService {
    @Resource
    BaseOrderReceiveBO baseOrderReceiveBO;

    /**
    * @Menthod getById
    * @Desrciption   根据主键id和医院编码查询领药单据类型信息
     * @param map 领药单据类型信息表主键
    * @Author xingyu.xie
    * @Date   2020/09/17 15:37
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseOrderReceiveDTO>
    **/
    @Override
    public WrapperResponse<BaseOrderReceiveDTO> getById(Map map) {
        BaseOrderReceiveDTO dto = baseOrderReceiveBO.getById(MapUtils.get(map,"baseOrderReceiveDTO"));
        return WrapperResponse.success(dto);
    }

    /**
     * @Menthod queryAll
     * @Desrciption   查询全部
     * @param map 医院编码
     * @Author xingyu.xie
     * @Date   2020/09/17 15:38
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     *
     * @return*/
    @Override
    public WrapperResponse<List<BaseOrderReceiveDTO>> queryAll(Map map) {
        List<BaseOrderReceiveDTO> dto = baseOrderReceiveBO.queryAll(MapUtils.get(map,"baseOrderReceiveDTO"));
        return WrapperResponse.success(dto);
    }

    /**
    * @Menthod queryPage
    * @Desrciption   根据数据对象来筛选查询
     * @param map 领药单据类型分类数据对象
    * @Author xingyu.xie
    * @Date   2020/09/17 15:38
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        PageDTO dto = baseOrderReceiveBO.queryPage(MapUtils.get(map,"baseOrderReceiveDTO"));
        return WrapperResponse.success(dto);
    }

    /**
     * @Menthod save
     * @Desrciption  新增或修改领药单据类型
     * @param map 领药单据类型数据参数对象
     * @Author xingyu.xie
     * @Date   2020/09/17 11:37
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> save(Map map) {
        return WrapperResponse.success(baseOrderReceiveBO.save(MapUtils.get(map,"baseOrderReceiveDTO")));
    }

    /**
    * @Menthod updateBaseOrderReceiveS
    * @Desrciption  修改单条领药单据类型（有判空）
     * @param map
    * @Author xingyu.xie
    * @Date   2020/9/17 15:19
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> updateBaseOrderReceiveS(Map map) {
        return WrapperResponse.success(baseOrderReceiveBO.updateBaseOrderReceiveS(MapUtils.get(map,"baseOrderReceiveDTO")));
    }

    /**
    * @Menthod updateStatus
    * @Desrciption   删除一个或多个领药单据类型信息
     * @param map 领药单据类型数据传输对象
    * @Author xingyu.xie
    * @Date   2020/09/17 15:40
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> updateStatus(Map map) {
        return WrapperResponse.success(baseOrderReceiveBO.updateStatus(MapUtils.get(map,"baseOrderReceiveDTO")));
    }
}
