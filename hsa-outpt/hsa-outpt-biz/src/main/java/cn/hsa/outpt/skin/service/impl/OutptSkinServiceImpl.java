package cn.hsa.outpt.skin.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.skin.bo.OutptSkinBO;
import cn.hsa.module.outpt.skin.dto.OutptSkinDTO;
import cn.hsa.module.outpt.skin.service.OutptSkinService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.outpt.skin.service.impl
 * @Class_name:: OutptSkinserviceImpl
 * @Description: 皮试处理service接口实现类（提供给dubbo）
 * @Author: zhangxuan
 * @Date: 2020-08-14 10:19
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/outpt/skin")
@Slf4j
@Service("outptSkinService_provider")
public class OutptSkinServiceImpl extends HsafService implements OutptSkinService {
    /**
     * 皮试处理逻辑接口
     */
    @Resource
    private OutptSkinBO outptSkinBO;
    /**
     * @Method
     * @Desrciption  根据条件分页查询皮试处理结果
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-08-14 13:57
     * @Return map
    **/
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        return WrapperResponse.success(outptSkinBO.queryPage(MapUtils.get(map,"outptSkinDTO")));
    }
    /**
     * @Method
     * @Desrciption  根据条件查询皮试处理结果
     * @Param 
     * map
     * @Author zhangxuan
     * @Date   2020-08-14 15:11 
     * @Return outptSkinDTOS
    **/
    @Override
    public WrapperResponse<List<OutptSkinDTO>> queryAll(Map map) {
        List<OutptSkinDTO> outptSkinDTOS = outptSkinBO.queryAll(MapUtils.get(map,"outptSkinDTO"));
        return WrapperResponse.success(outptSkinDTOS);
    }
    /**
     * @Method getById
     * @Desrciption  根据主键查找皮试处理结果
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-08-14 16:40
     * @Return
    **/
    @Override
    public WrapperResponse<OutptSkinDTO> getById(Map map) {
        return WrapperResponse.success(outptSkinBO.getById(MapUtils.get(map,"outptSkinDTO")));
    }
    /**
     * @Method insert
     * @Desrciption  新增皮试处理结果
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-08-14 16:41
     * @Return
    **/
    @Override
    public WrapperResponse<Boolean> insert(Map map) {
        return WrapperResponse.success(outptSkinBO.insert(MapUtils.get(map,"outptSkinDTO")));
    }
    /**
     * @Method update
     * @Desrciption  编辑皮试处理结果
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-08-14 16:41
     * @Return
    **/
    @Override
    public WrapperResponse<Boolean> update(Map map) {
        return WrapperResponse.success(outptSkinBO.update(MapUtils.get(map,"outptSkinDTO")));
    }
    /**
     * @Method delete
     * @Desrciption  删除皮试处理结果
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-08-14 16:42
     * @Return
    **/
    @Override
    public WrapperResponse<Boolean> delete(Map map) {
        return WrapperResponse.success(outptSkinBO.delete(MapUtils.get(map,"outptSkinDTO")));
    }
}
