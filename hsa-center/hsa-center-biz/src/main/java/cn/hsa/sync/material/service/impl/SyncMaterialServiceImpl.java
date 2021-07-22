package cn.hsa.sync.material.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.material.bo.SyncMaterialBO;
import cn.hsa.module.sync.material.dto.SyncMaterialDTO;
import cn.hsa.module.sync.material.service.SyncMaterialService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.sync.bmm.service.impl
 * @Class_name: BaseMaterialManagementServiceImpl
 * @Describe: 材料信息API接口实现层（提供给dubbo调用）
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/7 16:33
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/sync/syncMaterial")
@Slf4j
@Service("syncMaterialService_provider")
public class SyncMaterialServiceImpl extends HsafService implements SyncMaterialService {
    @Resource
    SyncMaterialBO syncMaterialBO;

    /**
    * @Menthod getById
    * @Desrciption   根据主键id和医院编码查询材料信息
     * @param syncMaterialDTO 材料信息表主键
    * @Author xingyu.xie
    * @Date   2020/7/8 15:37
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sync.bmm.dto.SyncMaterialDTO>
    **/
    @Override
    public WrapperResponse<SyncMaterialDTO> getById(SyncMaterialDTO syncMaterialDTO) {
        SyncMaterialDTO dto = syncMaterialBO.getById(syncMaterialDTO);
        return WrapperResponse.success(dto);
    }

    /**
     * @Menthod queryAll
     * @Desrciption   查询全部
     * @Author xingyu.xie
     * @Date   2020/7/8 15:38
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sync.PageDTO>
     *
     * @return*/
    @Override
    public WrapperResponse<List<SyncMaterialDTO>> queryAll() {
        List<SyncMaterialDTO> dto = syncMaterialBO.queryAll();
        return WrapperResponse.success(dto);
    }

    /**
    * @Menthod queryPage
    * @Desrciption   根据数据对象来筛选查询
     * @param syncMaterialDTO 材料分类数据对象
    * @Author xingyu.xie
    * @Date   2020/7/8 15:38
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sync.PageDTO>
    **/
    @Override
    public WrapperResponse<PageDTO> queryPage(SyncMaterialDTO syncMaterialDTO) {
        PageDTO dto = syncMaterialBO.queryPage(syncMaterialDTO);
        return WrapperResponse.success(dto);
    }

    /**
     * @Menthod save
     * @Desrciption  新增或修改材料分类
     * @param syncMaterialDTO 材料分类数据参数对象
     * @Author xingyu.xie
     * @Date   2020/7/25 11:37
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> save(SyncMaterialDTO syncMaterialDTO) {
        return WrapperResponse.success(syncMaterialBO.save(syncMaterialDTO));
    }

    /**
     * @Menthod updateList
     * @Desrciption  修改多条材料信息
     * @param map 材料分类数据参数对象list和医院编码
     * @Author xingyu.xie
     * @Date   2020/8/24 15:38
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> updateList(Map map) {
        return WrapperResponse.success(syncMaterialBO.updateList(MapUtils.get(map,"syncMaterialDTOList")));
    }

    /**
    * @Menthod updateStatus
    * @Desrciption   删除一个或多个材料信息
     * @param syncMaterialDTO 材料信息表的主键列表
    * @Author xingyu.xie
    * @Date   2020/7/8 15:40
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> updateStatus(SyncMaterialDTO syncMaterialDTO) {

        return WrapperResponse.success(syncMaterialBO.updateStatus(syncMaterialDTO));
    }
}
