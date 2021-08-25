package cn.hsa.module.base.bdc.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bdc.dto.BaseDailyfirstCalcDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@FeignClient(value = "hsa-base")
public interface BaseDailyfirstCalcService {

    /**
     * @Method queryAll()
     * @Description 通过实体作为筛选条件查询
     * @Param
     * 1、Map map
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<List<SyncAssistDetailDTO>>
     **/
    @PostMapping("/service/base/bdc/queryAll")
    WrapperResponse<List<BaseDailyfirstCalcDTO>> queryAll(Map map);
    /**
     * @Method insert()
     * @Description 更新
     * @Param
     * 1、Map map
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/service/base/bdc/insert")
    WrapperResponse<Boolean>  insert(Map map);
    /**
     * @Method deleteById()
     * @Description 删除
     * @Param
     * Map map
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<Boolean>
     **/

    @PostMapping("/service/base/bdc/deleteById")
    WrapperResponse<Boolean>  deleteById(Map map);
    /**
     * @Method queryPage()
     * @Description 分页查询
     * @Param
     * Map map
     * @Author ljh
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<PageDTO>
     **/

    @PostMapping("/service/base/bacd/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);

    /**
     * @Method: queryDaily
     * @Description: 获取每日首次计费信息
     * @Param: [dailyMap]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/24 11:36
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bdc.dto.BaseDailyfirstCalcDTO>
     **/
    @GetMapping("/service/base/bacd/queryDaily")
    WrapperResponse<List<BaseDailyfirstCalcDTO>> queryDaily(Map dailyMap);
}
