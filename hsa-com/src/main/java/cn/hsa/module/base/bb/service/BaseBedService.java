package cn.hsa.module.base.bb.service;

/**
 * @Package_name: cn.hsa.base.bb.service.impl
 * @Class_name:: BaseBedServiceImpl
 * @Description:
 * @Author: ljh
 * @Date: 2020/8/6 10:31
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bb.dto.BaseBedDTO;
import cn.hsa.module.base.bb.dto.BaseBedItemDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_ame: cn.hsa.base.bfc.service
 * @Class_name: BaseBedService
 * @Description: 床位分类service接口层（提供给dubbo调用）
 * @Author: ljh
 * @Email: 406224709@qq.com
 * @Date: 2020/7/1 20:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/

@FeignClient(value = "hsa-base")
public interface BaseBedService {


    /**
     * @Method queryAll()
     * @Description 通过实体作为筛选条件查询
     * @Param 1、BaseBedDTO 实例对象
     * @Author jlh
     * @Date 2020/7/1 20:53
     * @Return BaseFinanceClassifyDTO
     **/
    @PostMapping("/service/base/bb/queryAll")
    WrapperResponse<List<BaseBedDTO>> queryAll(Map map);


    /**
     * @Method insert()
     * @Description 新增数据
     * @Param 1、BaseBedDTO 实例对象
     * @Author jlh
     * @Date 2020/7/1 20:53
     * @Return BaseFinanceClassifyDTO
     **/
    @PostMapping("/service/base/bb/insert")
    WrapperResponse<Boolean> insert(Map map) throws Exception;

    /**
    * @Method insertBaseBedDTO
    * @Desrciption 新增床位
    * @param map
    * @Author liuqi1
    * @Date   2020/10/22 22:31
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/service/base/bb/insertBaseBedDTO")
    WrapperResponse<Boolean> insertBaseBedDTO(Map map);

    /**
     * @Method update()
     * @Description 修改数据
     * @Param 1、BaseBedDTO 实例对象
     * @Author jlh
     * @Date 2020/7/1 20:53
     * @Return BaseFinanceClassifyDTO
     **/
    @PostMapping("/service/base/bb/update")
    WrapperResponse<Boolean> update(Map map);

    /**
     * @Method deleteById()
     * @Description 删除数据
     * @Param 1、id
     * @Author jlh
     * @Date 2020/7/1 20:53
     * @Return BaseFinanceClassifyDTO
     **/
    @GetMapping("/service/base/bb/updateStatus")
    WrapperResponse<Boolean> updateStatus(Map map);

    /**
     * @Method queryPage()
     * @Description 分页查询财务分类信息
     * @Param 1、baseFinanceClassifyDTO：财务分类数据参数对象
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return PageDTO
     **/
    @PostMapping("/service/base/bb/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);

    /**
     * @Method queryPage()
     * @Description 分页查询财务分类信息
     * @Param 1、baseFinanceClassifyDTO：财务分类数据参数对象
     * @Author ljh
     * @Date 2020/7/1 20:55
     * @Return WrapperResponse<PageDTO>
     **/
    @PostMapping("/service/base/bb/ItemqueryPage")
    WrapperResponse<PageDTO> itemqueryPage(Map map);

    /**
     * @Method updateVisit
     * @Desrciption 批量更新visitId和status_code
     @params [map]
      * @Author chenjun
     * @Date   2020/9/9 15:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/base/bb/updateVisit")
    WrapperResponse<Boolean> updateVisit(Map map);

    /**
     * @Method deleteById
     * @Desrciption 根据id删除床位信息，虚拟床位预出院用
       @params [map]
     * @Author chenjun
     * @Date   2020/9/9 15:59
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/service/base/bb/deleteById")
    WrapperResponse<Boolean> deleteById(Map map);

    @PostMapping("/service/base/bb/queryBedItemAll")
    WrapperResponse<List<BaseBedItemDTO>> queryBedItemAll(Map map);

    @GetMapping("/service/base/bb/getMaxSeq")
    WrapperResponse<Integer> getMaxSeq(Map map);
}
