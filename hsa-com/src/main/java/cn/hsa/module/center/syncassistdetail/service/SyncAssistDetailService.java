package cn.hsa.module.center.syncassistdetail.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.syncassistdetail.dto.SyncAssistDetailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "hsa-base")
public interface SyncAssistDetailService {

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
    WrapperResponse<List<SyncAssistDetailDTO>> queryAll(SyncAssistDetailDTO syncAssistDetailDTO);
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
    WrapperResponse<Boolean>  insert(List<SyncAssistDetailDTO> syncAssistDetailDTO);
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
    WrapperResponse<Boolean>  deleteById(SyncAssistDetailDTO syncAssistDetailDTO);
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
    WrapperResponse<PageDTO> queryPage(SyncAssistDetailDTO syncAssistDetailDTO);
}
