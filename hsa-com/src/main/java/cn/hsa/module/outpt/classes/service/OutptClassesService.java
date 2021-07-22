package cn.hsa.module.outpt.classes.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.classes.dto.OutptClassesDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.service
 * @Class_name:: OutptClassesService
 * @Description: 班次信息维护service接口层（提供给dubbo调用）
 * @Author: zhangxuan
 * @Date: 2020-08-10 16:23
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-outpt")
public interface OutptClassesService {

    /**
     * @Menthod queryPage()
     * @Desrciption   根据条件分页查询班次信息
     *   map
     * @Author zhangxuan
     * @Date   2020-08-10 16:23
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @PostMapping("/service/outpt/classes/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);
    /**
     * @Menthod queryById()
     * @Desrciption   根据id查询班次信息
     *   map
     * @Author zhangxuan
     * @Date   2020-08-10 16:23
     * @Return map
     **/
    @PostMapping("/service/outpt/classes/getById")
    WrapperResponse<OutptClassesDTO> getById(Map map);

    /**
     * @Menthod queryAll()
     * @Desrciption  查询所有班次信息
     * @Param
     *  map
     * @Author zhangxuan
     * @Date   2020-08-10 16:23
     * @Return map
     * @return*/
    @PostMapping("/service/outpt/classes/queryAll")
    WrapperResponse<List<OutptClassesDTO>> queryAll(Map map);

    /**
     * @Menthod insert()
     * @Desrciption 新增班次
     * @Param
     *  map
     * @Author zhangxuan
     * @Date   2020-08-10 16:23
     * @Return map
     **/
    @PostMapping("/service/outpt/classes/insert")
    WrapperResponse<Boolean> insert(Map map);

    /**
     * @Menthod delete()
     * @Desrciption 删除班次
     * @Param
     *  map
     * @Author zhangxuan
     * @Date   2020-08-10 16:23
     * @Return map
     **/
    @PostMapping("/service/outpt/classes/delete")
    WrapperResponse<Boolean> delete(Map map);

    /**
     * @Menthod update()
     * @Desrciption  修改班次信息
     * @Param
     *  map
     * @Author zhangxuan
     * @Date   2020-08-10 16:23
     * @Return map
     **/
    @PostMapping("/service/outpt/classes/update")
    WrapperResponse<Boolean> update(Map map);

}
