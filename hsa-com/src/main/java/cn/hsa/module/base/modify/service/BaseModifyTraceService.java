package cn.hsa.module.base.modify.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.modify.dto.BaseModifyTraceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

/**
 * @PackageName: cn.hsa.module.base.modify.entity
 * @Class_name: 修改痕迹
 * @Description: 修改痕迹数据库实体对象
 * @Author: zengfeng
 * @Email: 954123283@qq.com
 * @Date: 2020/7/25  10:29
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-base")
public interface BaseModifyTraceService {

    /**
     * @Method: queryPage()
     * @Description: 分页带参数查询修改痕迹
     * @Param: baseDeptDTO
     * @Author: zengfeng
     * @Email: 954123283@qq.com
     * @Date: 2020/7/25  10:28
     * @Return: PageDTO分页数据传输对象
     */
    @GetMapping("/api/base/baseModifyTrace/queryPage")
    WrapperResponse<PageDTO> queryPage(BaseModifyTraceDTO baseModifyTraceDTO);

    /**
     * @Method: queryPage()
     * @Description: 通过主键获取修改痕迹详细
     * @Param: baseDeptDTO
     * @Author: zengfeng
     * @Email: 954123283@qq.com
     * @Date: 2020/7/25  10:28
     * @Return: 修改痕迹详细
     */
    @GetMapping("/api/base/baseModifyTrace/getById")
    WrapperResponse<BaseModifyTraceDTO> getById(BaseModifyTraceDTO baseModifyTraceDTO);

    /**
     * @Method: getTableConent()
     * @Description: 查询数据详情
     * @Param: baseModifyTraceDTO
     * @Author: zengfeng
     * @Email: 954123283@qq.com
     * @Date: 2020/7/25  10:28
     * @Return: 返回list
     */
    @GetMapping("/api/base/baseModifyTrace/getTableConent")
    WrapperResponse<List<Map<String, Object>>> getTableConent(BaseModifyTraceDTO baseModifyTraceDTO);

    /**
     * @Method: insert()
     * @Description: 新增修改痕迹
     * @Param: baseModifyTraceDTO
     * @Author: zengfeng
     * @Email: 954123283@qq.com
     * @Date: 2020/7/25  10:28
     * @Return: 是否成功
     */
    @GetMapping("/api/base/baseModifyTrace/insert")
    WrapperResponse<Boolean> insert(BaseModifyTraceDTO baseModifyTraceDTO);
}
