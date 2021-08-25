package cn.hsa.outpt.classes.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.classes.bo.OutptClassesBO;
import cn.hsa.module.outpt.classes.dto.OutptClassesDTO;
import cn.hsa.module.outpt.classes.service.OutptClassesService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.outpt.classes.service.impl
 * @Class_name: OutptClassesServiceImpl
 * @Describe:  班次service接口实现层（提供给dubbo调用）
 * @Author: zhangxuan
 * @Email: zhangxuan@powersi.com
 * @Date: 2020/8/10 11:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/outpt/classes")
@Slf4j
@Service("outptClassesService_provider")
public class OutptClassesServiceImpl extends HsafService implements OutptClassesService {

    /**
     * 班次业务逻辑接口
     */
    @Resource
    private OutptClassesBO outptClassesBO;

    /**
     * @Menthod queryPage()
     * @Desrciption 根据条件查询班次信息
     * @Param
     * 1. OutptClassesDTO  班次数据传输对象
     * @Author zhangxuan
     * @Date   2020/7/28 9:26
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sys.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        PageDTO pageDTO = outptClassesBO.queryPage(MapUtils.get(map,"outptClassesDTO"));
        return WrapperResponse.success(pageDTO);
    }
    /**
     * @Menthod queryById()
     * @Desrciption 根据条件查询班次信息
     * @Param
     * 1. OutptClassesDTO  班次数据传输对象
     * @Author zhangxuan
     * @Date   2020/7/28 9:26
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.outpt.classes.dto.OutptClassesDTO>
     **/
    @Override
    public WrapperResponse<OutptClassesDTO> getById(Map map) {
        OutptClassesDTO outptClassesDTO = outptClassesBO.getById(MapUtils.get(map,"outptClassesDTO"));
        return WrapperResponse.success(outptClassesDTO);
    }

    /**
     * @Menthod queryAll()
     * @Desrciption  查询所有班次接口
     * @Param
     * [1. OutptClassesDTO]
     * @Author zhangxuan
     * @Date   2020/28 14:54
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sys.parameter.dto.outptClassesDTO>>
     **/
    @Override
    public WrapperResponse<List<OutptClassesDTO>> queryAll(Map map) {
        List<OutptClassesDTO> outptClassesDTOS = outptClassesBO.queryAll(MapUtils.get(map,"outptClassesDTO"));
        return WrapperResponse.success(outptClassesDTOS);
    }

    /**
     * @Menthod insert()
     * @Desrciption 新增班次
     * @Param
     * 1. OutptClassesDTO  班次数据传输对象
     * @Author zhangxuan
     * @Date   2020/7/28 9:26
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override

    public WrapperResponse<Boolean> insert(Map map) {
        return WrapperResponse.success(outptClassesBO.insert(MapUtils.get(map,"outptClassesDTO")));
    }

    /**
     * @Menthod delete()
     * @Desrciption 删除班次根据主键id
     * @Param
     * 1.map
     * @Author zhangxuan
     * @Date   2020/7/28 9:26
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> delete(Map map) {
        return WrapperResponse.success(outptClassesBO.delete(MapUtils.get(map,"outptClassesDTO")));
    }

    /**
     * @Menthod update()
     * @Desrciption 修改班次信息
     * @Param
     * 1. OutptClassesDTO  班次数据传输对象
     * @Author zhangxuan
     * @Date   2020/7/28 9:27
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> update(Map map) {
        return WrapperResponse.success(outptClassesBO.update(MapUtils.get(map,"outptClassesDTO")));
    }
}
