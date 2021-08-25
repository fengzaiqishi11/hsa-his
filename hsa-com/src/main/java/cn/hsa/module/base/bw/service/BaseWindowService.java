package cn.hsa.module.base.bw.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bw.dto.BaseWindowDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.bmm.service
 * @Class_name: baseWindowManagementService
 * @Describe: 发药窗口Service接口层（提供给dubbo调用）
 * @Author: xingyu.xie
 * @Date: 2020/7/23 16:14
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-base")
public interface BaseWindowService {

    /**
    * @Menthod getById
    * @Desrciption   根据主键ID查询生产企业分类信息
     * @param map id 发药窗口表主键ID
    * @Author xingyu.xie
    * @Date   2020/7/23 16:17
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.baseProductDTO>
    **/
    @PostMapping("/service/base/baseWindow/getById")
    WrapperResponse<BaseWindowDTO> getById(Map map);

    /**
     * @Menthod queryAll
     * @Desrciption   查询所有发药窗口
     * @param map 医院编码
     * @Author xingyu.xie
     * @Date   2020/7/23 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     *
     * @return*/
    @RequestMapping("/service/base/baseWindow/queryAll")
    WrapperResponse<List<BaseWindowDTO>> queryAll(Map map);

    /**
     * @Menthod queryPage
     * @Desrciption   分页查询发药窗口
     * @param map 发药窗口数据参数对象
     * @Author xingyu.xie
     * @Date   2020/7/23 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @PostMapping("/service/base/baseWindow/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);

    /**
    * @Menthod insert
    * @Desrciption 新增单条发药窗口
     * @param map  生产企业分类数据对象
    * @Author xingyu.xie
    * @Date   2020/7/23 16:18
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.baseProductDTO>
    **/
    @PostMapping("/service/base/baseWindow/insert")
    WrapperResponse<Boolean> insert(Map map);

    /**
    * @Menthod update
    * @Desrciption 修改单条发药窗口
     * @param map  生产企业分类数据对象
    * @Author xingyu.xie
    * @Date   2020/7/23 16:18
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.baseProductDTO>
    **/
    @PostMapping("/service/base/baseWindow/update")
    WrapperResponse<Boolean> update(Map map);

    /**
    * @Menthod delete
    * @Desrciption   根据主键ID删除发药窗口和
     * @param map  发药窗口表主键
    * @Author xingyu.xie
    * @Date   2020/7/23 16:18
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/service/base/baseWindow/delete")
    WrapperResponse<Boolean> delete(Map map);

    /**
     * @Menthod queryByDeptId
     * @Desrciption  根据当前登录的科室查询所有窗口
     * @param map 科室编码等参数
     * @Author xingyu.xie
     * @Date   2020/7/31 9:48
     * @Return java.util.List<cn.hsa.module.base.bw.dto.BaseWindowDTO>
     **/
    WrapperResponse<List<BaseWindowDTO>> queryByDeptId(Map map);
}
