package cn.hsa.module.sync.financeclassify.service;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.financeclassify.dto.SyncFinanceClassifyDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @Package_ame: cn.hsa.sync.bfc.service
 * @Class_name: BaseFinanceClassifyService
 * @Description: 财务分类service接口层（提供给dubbo调用）
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/7/1 20:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-sync")
public interface SyncFinanceClassifyService {
    /**
     * @Method getById()
     * @Description 根据主键ID查询财务分类信息
     *
     * @Param
     * 1、id：财务分类信息表主键ID
     *
     * @Author zhongming
     * @Date 2020/7/1 20:53
     * @Return WrapperResponse<CenterFinanceClassifyDTO>
     **/
    @GetMapping("/service/sync/syncFinanceClassify/getById")
    WrapperResponse<SyncFinanceClassifyDTO> getById(SyncFinanceClassifyDTO syncFinanceClassifyDTO);


    /**
    * @Menthod queryTree
    * @Desrciption  树的搜索
    * @Author xingyu.xie
    * @Date   2020/7/20 17:23
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sync.bfc.dto.CenterFinanceClassifyDTO>>
    **/
    @GetMapping("/service/sync/syncFinanceClassify/queryTree")
    WrapperResponse<List<TreeMenuNode>> queryTree(SyncFinanceClassifyDTO syncFinanceClassifyDTO);

    /**
    * @Menthod queryDropDownEnd
    * @Desrciption  查询最下级下拉框数据
    * @Author xingyu.xie
    * @Date   2020/7/20 17:23
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sync.bfc.dto.CenterFinanceClassifyDTO>>
    **/
    @GetMapping("/service/sync/syncFinanceClassify/queryDropDownEnd")
    WrapperResponse<List<SyncFinanceClassifyDTO>> queryDropDownEnd();

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
    @GetMapping("/service/sync/syncFinanceClassify/queryPage")
    WrapperResponse<PageDTO> queryPage(SyncFinanceClassifyDTO syncFinanceClassifyDTO);

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
    @GetMapping("/service/sync/syncFinanceClassify/queryAll")
    WrapperResponse<List<SyncFinanceClassifyDTO>> queryAll(SyncFinanceClassifyDTO syncFinanceClassifyDTO);

    /**
    * @Menthod save
    * @Desrciption  新增或修改财务分类
     * @param syncFinanceClassifyDTO 财务分类数据参数对象
    * @Author xingyu.xie
    * @Date   2020/7/25 11:35
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/service/sync/syncFinanceClassify/save")
    WrapperResponse<Boolean> save(SyncFinanceClassifyDTO syncFinanceClassifyDTO);

    /**
     * @Method updateIsValid()
     * @Description 单个或者批量更改有效标识
     *
     * @Param
     * 1、id：财务分类信息表主键ID
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return WrapperResponse<Boolean>
     **/
    @DeleteMapping("/service/sync/syncFinanceClassify/updateStatus")
    WrapperResponse<Boolean> updateStatus(SyncFinanceClassifyDTO syncFinanceClassifyDTO);

}