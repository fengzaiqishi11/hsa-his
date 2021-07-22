package cn.hsa.module.base.modify.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.base.modify.dto.BaseModifyTraceDTO;

import java.util.List;
import java.util.Map;

/**
 * @PackageName: cn.hsa.module.base.modify.dao
 * @Class_name: BaseModifyTraceBO
 * @Description: 修改痕迹业务逻辑实现层接口
 * @Author: zengfeng
 * @Email: 954123283@qq.com
 * @Date: 2020/7/25  10:28
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface BaseModifyTraceBO {
    /**
     * @Method: queryAll()
     * @Description: 分页带参数查询修改痕迹
     * @Param: baseModifyTraceDTO
     * @Author: zengfeng
     * @Email: 954123283@qq.com
     * @Date: 2020/7/25  10:28
     * @Return: 修改痕迹信息数据传输对象list集合
     */
    PageDTO queryPage(BaseModifyTraceDTO baseModifyTraceDTO);

    /**
     * @Method: getById()
     * @Description: 通过主键查询修改痕迹
     * @Param: baseModifyTraceDTO
     * @Author: zengfeng
     * @Email: 954123283@qq.com
     * @Date: 2020/7/25  10:28
     * @Return: 修改痕迹详细
     */
    BaseModifyTraceDTO getById(BaseModifyTraceDTO baseModifyTraceDTO);

    /**
     * @Method: insert()
     * @Description: 新增修改痕迹
     * @Param: baseModifyTraceDTO
     * @Author: zengfeng
     * @Email: 954123283@qq.com
     * @Date: 2020/7/25  10:28
     * @Return: 是否成功
     */
    boolean insert(BaseModifyTraceDTO baseModifyTraceDTO);

    /**
     * @Method: getTableConent()
     * @Description: 查询数据详情
     * @Param: baseModifyTraceDTO
     * @Author: zengfeng
     * @Email: 954123283@qq.com
     * @Date: 2020/7/25  10:28
     * @Return: 返回list
     */
    List<Map<String, Object>> getTableConent(BaseModifyTraceDTO baseModifyTraceDTO);
}
