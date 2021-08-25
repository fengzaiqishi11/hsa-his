package cn.hsa.module.base.bp.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bp.dto.BaseProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.bmm.service
 * @Class_name: baseProductManagementService
 * @Describe: 生产企业信息Service接口层（提供给dubbo调用）
 * @Author: xingyu.xie
 * @Date: 2020/7/17 16:14
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-base")
public interface BaseProductService {

    /**
    * @Menthod getById
    * @Desrciption   根据主键ID查询生产企业分类信息
     * @param map id 生产企业信息表主键ID
    * @Author xingyu.xie
    * @Date   2020/7/17 16:17
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.baseProductDTO>
    **/
    @PostMapping("/service/base/baseProduct/getById")
    WrapperResponse<BaseProductDTO> getById(Map map);

    /**
     * @Menthod queryAll
     * @Desrciption   查询所有生产企业信息
     * @param map 医院编码
     * @Author xingyu.xie
     * @Date   2020/7/17 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     *
     * @return*/
    @RequestMapping("/service/base/baseProduct/queryAll")
    WrapperResponse<List<BaseProductDTO>> queryAll(Map map);

    /**
     * @Menthod queryPage
     * @Desrciption   分页查询生产企业信息
     * @param map 生产企业信息数据参数对象
     * @Author xingyu.xie
     * @Date   2020/7/17 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @PostMapping("/service/base/baseProduct/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);

    /**
     * @Menthod save
     * @Desrciption  新增或修改生产企业
     * @param map 生产企业数据参数对象
     * @Author xingyu.xie
     * @Date   2020/7/25 11:35
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/base/baseProduct/save")
    WrapperResponse<Boolean> save(Map map);

    /**
    * @Menthod updateStatus
    * @Desrciption   根据主键ID删除生产企业信息和
     * @param map  生产企业数据参数对象
    * @Author xingyu.xie
    * @Date   2020/7/17 16:18
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/service/base/baseProduct/updateStatus")
    WrapperResponse<Boolean> updateStatus(Map map);
}
