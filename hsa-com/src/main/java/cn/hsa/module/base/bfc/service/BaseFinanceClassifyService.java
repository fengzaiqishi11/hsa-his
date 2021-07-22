package cn.hsa.module.base.bfc.service;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bfc.dto.BaseFinanceClassifyDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_ame: cn.hsa.base.bfc.service
 * @Class_name: BaseFinanceClassifyService
 * @Description: 财务分类service接口层（提供给dubbo调用）
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/7/1 20:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-base")
public interface BaseFinanceClassifyService {
    /**
     * @Method getById()
     * @Description 根据主键ID查询财务分类信息
     *
     * @Param
     * 1、id：财务分类信息表主键ID
     *
     * @Author zhongming
     * @Date 2020/7/1 20:53
     * @Return WrapperResponse<BaseFinanceClassifyDTO>
     **/
    @GetMapping("/service/base/baseFinanceClassify/getById")
    WrapperResponse<BaseFinanceClassifyDTO> getById(Map map);


    /**
    * @Menthod queryTree
    * @Desrciption  树的搜索不显示末级
     * @param map 1.医院编码 2。是否末级 3。是否有效
    * @Author xingyu.xie
    * @Date   2020/7/20 17:23
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.bfc.dto.BaseFinanceClassifyDTO>>
    **/
    @RequestMapping("/service/base/baseFinanceClassify/queryTree")
    WrapperResponse<List<TreeMenuNode>> queryTree(Map map);

    /**
    * @Menthod queryDropDownEnd
    * @Desrciption  查询最下级下拉框数据
     * @param map 医院编码
    * @Author xingyu.xie
    * @Date   2020/7/20 17:23
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.bfc.dto.BaseFinanceClassifyDTO>>
    **/
    @RequestMapping("/service/base/baseFinanceClassify/queryDropDownEnd")
    WrapperResponse<List<BaseFinanceClassifyDTO>> queryDropDownEnd(Map map);

    /**
     * @Method queryPage()
     * @Description 分页查询财务分类信息
     *
     * @Param
     * 1、baseFinanceClassifyDTO：财务分类数据参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return WrapperResponse<PageDTO>
     **/
    @GetMapping("/service/base/baseFinanceClassify/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);

    /**
     * @Method queryAll()
     * @Description 分页查询财务分类信息
     *
     * @Param
     * 1、baseFinanceClassifyDTO：财务分类数据参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return WrapperResponse<PageDTO>
     **/
    @GetMapping("/service/base/baseFinanceClassify/queryAll")
    WrapperResponse<List<BaseFinanceClassifyDTO>> queryAll(Map map);

    /**
    * @Menthod save
    * @Desrciption  新增或修改财务分类
     * @param map 财务分类数据参数对象
    * @Author xingyu.xie
    * @Date   2020/7/25 11:35
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/service/base/baseFinanceClassify/save")
    WrapperResponse<Boolean> save(Map map);

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
    @DeleteMapping("/service/base/baseFinanceClassify/updateStatus")
    WrapperResponse<Boolean> updateStatus(Map map);

    /**
    * @Menthod isNameExist
    * @Desrciption  判断财务分类名称是否重复
     * @param map
    * @Author xingyu.xie
    * @Date   2020/11/25 16:46
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @GetMapping("/service/base/baseFinanceClassify/updateStatus")
    WrapperResponse<Boolean> isNameExist(Map map);

}