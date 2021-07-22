package cn.hsa.sync.syncassist.controller;

import cn.hsa.base.CenterBaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.syncassist.dto.SyncassistDTO;
import cn.hsa.module.center.syncassist.dto.SyncassistDetailDTO;
import cn.hsa.module.center.syncassist.service.SyncassistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_ame: cn.hsa.web.base
 * @Class_name: BaseAssistCalcDetailController
 * @Description:  辅助计费分类Controller层
 * @Author: ljh
 * @Email: 307753910@qq.com
 * @Date: 2020/7/1 20:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/center/syncassist/bac")
@Slf4j
public class SyncassistController extends CenterBaseController {
    /**
     * 辅助计费dubbo消费者接口
     */
    @Resource
    private SyncassistService syncassistService;
    /**
     * @Menthod queryAll
     * @Desrciption 查询
     * @param syncassistDTO
     * @Author ljh
     * @Date   2020/8/6 10:40
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.syncassist.dto.SyncassistDTO>>
     **/
    @GetMapping("/queryAll")
    WrapperResponse<List<SyncassistDTO>> queryAll( SyncassistDTO syncassistDTO) {
        return syncassistService.queryAll(syncassistDTO);
    }

    /**
     * @Menthod insert
     * @Desrciption 新增
     * @param syncassistDTO
     * @Author ljh
     * @Date   2020/8/6 10:41
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/insert")
    public WrapperResponse<Boolean> insert(@RequestBody SyncassistDTO syncassistDTO) {
        syncassistDTO.setCrteId(userId);
        syncassistDTO.setCrteName(userName);
        return syncassistService.insert(syncassistDTO);
    }

    /**
     * @Menthod update
     * @Desrciption 更新
     * @param syncassistDTO
     * @Author ljh
     * @Date   2020/8/6 10:41
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/update")
    public WrapperResponse<Boolean> update(@RequestBody SyncassistDTO syncassistDTO) {
        return syncassistService.update(syncassistDTO);
    }



    /**
    * @Menthod deleteById
    * @Desrciption   删除
     * @param syncassistDTO
    * @Author ljh
    * @Date   2020/8/6 10:41
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/updateStatus")
    public WrapperResponse<Boolean> updateStatus(@RequestBody SyncassistDTO syncassistDTO) {
        return syncassistService.updateStatus(syncassistDTO);
    }
    /**
     * @Menthod queryPage
     * @Desrciption   分页
     * @param syncassistDTO
     * @Author ljh
     * @Date   2020/8/6 10:42
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(SyncassistDTO syncassistDTO) {

        return syncassistService.queryPage(syncassistDTO);
    }
    /**
     * @Menthod detailqueryPage
     * @Desrciption 删除
     * @param syncassistDetailDTO
     * @Author ljh
     * @Date   2020/8/6 10:42
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/detailqueryPage")
    public WrapperResponse<PageDTO> detailqueryPage( SyncassistDetailDTO syncassistDetailDTO) {
        return syncassistService.detailqueryPage(syncassistDetailDTO);

    }
}
