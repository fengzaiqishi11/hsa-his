package cn.hsa.module.outpt.skin.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.skin.dto.OutptSkinDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.skin.service
 * @Class_name:: OutptSkinService
 * @Description: 皮试处理service接口层提供给（dubbo使用）
 * @Author: zhangxuan
 * @Date: 2020-08-14 10:09
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-outpt")
public interface OutptSkinService {
    /**
     * @Method queryPage
     * @Desrciption 根据条件分页查询皮试处理结果
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-08-14 13:55
     * @Return map
    **/
    @PostMapping("/service/outpt/skin/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);
    /**
     * @Method queryAll
     * @Desrciption  根据条件查询全部皮试处理结果
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-08-14 15:10
     * @Return
    **/
    @PostMapping("/service/outpt/skin/queryAll")
    WrapperResponse<List<OutptSkinDTO>> queryAll(Map map);
    /**
     * @Method getById
     * @Desrciption  根据主键查询皮试处理结果
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-08-14 16:22
     * @Return
    **/
    @PostMapping("/service/outpt/skin/getById")
    WrapperResponse<OutptSkinDTO> getById(Map map);
    /**
     * @Method insert
     * @Desrciption 新增皮试处理结果
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-08-14 16:24
     * @Return
    **/
    @PostMapping("/service/outpt/skin/insert")
    WrapperResponse<Boolean> insert(Map map);
    /**
     * @Method update
     * @Desrciption  编辑皮试处理结果
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-08-14 16:30
     * @Return
    **/
    @PostMapping("/service/outpt/skin/update")
    WrapperResponse<Boolean> update(Map map);

    /**
     * @Method delete
     * @Desrciption  删除皮试处理结果
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-08-14 16:30
     * @Return
    **/
    @PostMapping("/service/outpt/skin/delete")
    WrapperResponse<Boolean> delete(Map map);
}
