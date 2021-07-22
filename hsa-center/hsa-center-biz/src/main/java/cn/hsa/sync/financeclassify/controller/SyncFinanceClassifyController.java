package cn.hsa.sync.financeclassify.controller;

import cn.hsa.base.CenterBaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.financeclassify.dto.SyncFinanceClassifyDTO;
import cn.hsa.module.sync.financeclassify.service.SyncFinanceClassifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_ame: cn.hsa.web.base
 * @Class_name: SyncFinanceClassifyController
 * @Description: 财务分类控制层
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/7/1 20:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/center/sync/syncFinanceClassify")
@Slf4j
public class SyncFinanceClassifyController extends CenterBaseController {
    /**
     * 财务分类dubbo消费者接口
     */
    @Resource
    private SyncFinanceClassifyService syncFinanceClassifyService;

    /**
     * @Method getById()
     * @Description 根据主键ID查询财务分类信息
     *
     * @Param
     * 1、id：财务分类信息表主键ID
     *
     * @Author zhongming
     * @Date 2020/7/1 20:53
     * @Return WrapperResponse<SyncFinanceClassifyDTO>
     **/
    @GetMapping("/getById")
    public WrapperResponse<SyncFinanceClassifyDTO> getById(SyncFinanceClassifyDTO syncFinanceClassifyDTO) {
        return syncFinanceClassifyService.getById(syncFinanceClassifyDTO);
    }

    /**
     * @Menthod queryTree
     * @Desrciption 财务分类树状查询
     * @param
     * @Author xingyu.xie
     * @Date   2020/7/18 10:42
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.bfc.dto.SyncFinanceClassifyDTO>>
     **/
    @GetMapping("/queryTree")
    public WrapperResponse<List<TreeMenuNode>> queryTree(SyncFinanceClassifyDTO syncFinanceClassifyDTO) {
        return syncFinanceClassifyService.queryTree(syncFinanceClassifyDTO);
    }

    /**
    * @Menthod queryDropDownEnd
    * @Desrciption  
     * @param 
    * @Author xingyu.xie
    * @Date   2020/7/18 10:42 
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.bfc.dto.SyncFinanceClassifyDTO>>
    **/
    @GetMapping("/queryDropDownEnd")
    public WrapperResponse<List<SyncFinanceClassifyDTO>> queryDropDownEnd() {
        return syncFinanceClassifyService.queryDropDownEnd();
    }

    /**
     * @Method queryPage()
     * @Description 分页查询财务分类信息
     *
     * @Param
     * 1、syncFinanceClassifyDTO：财务分类数据参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return WrapperResponse<PageDTO>
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(SyncFinanceClassifyDTO syncFinanceClassifyDTO) {

        return syncFinanceClassifyService.queryPage(syncFinanceClassifyDTO);
    }

    /**
     * @Method queryAll()
     * @Description 分页查询财务分类信息
     *
     * @Param
     * 1、syncFinanceClassifyDTO：财务分类数据参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return WrapperResponse<PageDTO>
     **/
    @GetMapping("/queryAll")
    public WrapperResponse<List<SyncFinanceClassifyDTO>> queryAll(SyncFinanceClassifyDTO syncFinanceClassifyDTO) {

        return syncFinanceClassifyService.queryAll(syncFinanceClassifyDTO);
    }

    /**
     * @Method insert()
     * @Description 新增或修改单条财务分类信息
     *
     * @Param
     * 1、syncFinanceClassifyDTO：财务分类数据参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/save")
    public WrapperResponse<Boolean> save(@RequestBody SyncFinanceClassifyDTO syncFinanceClassifyDTO) {
        syncFinanceClassifyDTO.setCrteName(userName);
        syncFinanceClassifyDTO.setCrteId(userId);
        return syncFinanceClassifyService.save(syncFinanceClassifyDTO);
    }


    /**
     * @Method updateIsValid()
     * @Description 单个或者批量更改有效标识
     *
     * @Param
     * 1、ids：财务分类信息表主键ID集合
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/updateStatus")
    public WrapperResponse<Boolean> updateStatus(@RequestBody SyncFinanceClassifyDTO syncFinanceClassifyDTO) {

        return syncFinanceClassifyService.updateStatus(syncFinanceClassifyDTO);
    }
}
