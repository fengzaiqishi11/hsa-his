package cn.hsa.module.base.bmc.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bmc.dto.BaseMrisClassifyDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.bmm.service
 * @Class_name: baseMrisClassifyManagementService
 * @Describe: 病案费用归类Service接口层（提供给dubbo调用）
 * @Author: xingyu.xie
 * @Date: 2020/09/17 16:14
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-base")
public interface BaseMrisClassifyService {

    /**
    * @Menthod getById
    * @Desrciption   根据主键ID查询病案费用归类分类信息
     * @param map id 病案费用归类表主键ID
    * @Author xingyu.xie
    * @Date   2020/09/17 16:17
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMrisClassifyDTO>
    **/
    @PostMapping("/service/base/baseMrisClassify/getById")
    WrapperResponse<BaseMrisClassifyDTO> getById(Map map);

    /**
     * @Menthod queryAll
     * @Desrciption   查询所有病案费用归类
     * @param map 医院编码
     * @Author xingyu.xie
     * @Date   2020/09/17 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     *
     * @return*/
    @RequestMapping("/service/base/baseMrisClassify/queryAll")
    WrapperResponse<List<BaseMrisClassifyDTO>> queryAll(Map map);

    /**
     * @Menthod queryPage
     * @Desrciption   分页查询病案费用归类
     * @param map 病案费用归类数据参数对象
     * @Author xingyu.xie
     * @Date   2020/09/17 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @PostMapping("/service/base/baseMrisClassify/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);

    /**
     * @Menthod save
     * @Desrciption  新增或修改病案费用归类(无判空)
     * @param map 病案费用归类参数对象
     * @Author xingyu.xie
     * @Date   2020/7/25 11:35
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/base/baseMrisClassify/save")
    WrapperResponse<Boolean> save(Map map);

    /**
     * @Menthod save
     * @Desrciption  修改病案费用归类(有判空)
     * @param map 病案费用归类参数对象
     * @Author xingyu.xie
     * @Date   2020/7/25 11:35
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/base/baseMrisClassify/updateBaseMrisClassifyS")
    WrapperResponse<Boolean> updateBaseMrisClassifyS(Map map);

    /**
    * @Menthod updateStatus
    * @Desrciption   根据主键ID删除病案费用归类和
     * @param map  病案费用归类参数对象
    * @Author xingyu.xie
    * @Date   2020/09/17 16:18
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/service/base/baseMrisClassify/updateStatus")
    WrapperResponse<Boolean> updateStatus(Map map);
}
