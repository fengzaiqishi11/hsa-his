package cn.hsa.module.base.baseorderreceive.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.baseorderreceive.dto.BaseOrderReceiveDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.bmm.service
 * @Class_name: baseOrderReceiveManagementService
 * @Describe: 领药单据类型Service接口层（提供给dubbo调用）
 * @Author: xingyu.xie
 * @Date: 2020/09/17 16:14
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-base")
public interface BaseOrderReceiveService {

    /**
    * @Menthod getById
    * @Desrciption   根据主键ID查询领药单据类型分类信息
     * @param map id 领药单据类型表主键ID
    * @Author xingyu.xie
    * @Date   2020/09/17 16:17
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseOrderReceiveDTO>
    **/
    @PostMapping("/service/base/baseOrderReceive/getById")
    WrapperResponse<BaseOrderReceiveDTO> getById(Map map);

    /**
     * @Menthod queryAll
     * @Desrciption   查询所有领药单据类型
     * @param map 医院编码
     * @Author xingyu.xie
     * @Date   2020/09/17 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     *
     * @return*/
    @RequestMapping("/service/base/baseOrderReceive/queryAll")
    WrapperResponse<List<BaseOrderReceiveDTO>> queryAll(Map map);

    /**
     * @Menthod queryPage
     * @Desrciption   分页查询领药单据类型
     * @param map 领药单据类型数据参数对象
     * @Author xingyu.xie
     * @Date   2020/09/17 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @PostMapping("/service/base/baseOrderReceive/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);

    /**
     * @Menthod save
     * @Desrciption  新增或修改领药单据类型(无判空)
     * @param map 领药单据类型参数对象
     * @Author xingyu.xie
     * @Date   2020/7/25 11:35
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/base/baseOrderReceive/save")
    WrapperResponse<Boolean> save(Map map);

    /**
     * @Menthod save
     * @Desrciption  修改领药单据类型(有判空)
     * @param map 领药单据类型参数对象
     * @Author xingyu.xie
     * @Date   2020/7/25 11:35
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/base/baseOrderReceive/updateBaseOrderReceiveS")
    WrapperResponse<Boolean> updateBaseOrderReceiveS(Map map);

    /**
    * @Menthod updateStatus
    * @Desrciption   根据主键ID删除领药单据类型和
     * @param map  领药单据类型参数对象
    * @Author xingyu.xie
    * @Date   2020/09/17 16:18
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/service/base/baseOrderReceive/updateStatus")
    WrapperResponse<Boolean> updateStatus(Map map);
}
