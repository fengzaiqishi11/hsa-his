package cn.hsa.module.base.modify.dao;

import cn.hsa.module.base.modify.dto.BaseModifyTraceDTO;

import java.util.List;
import java.util.Map;

/**
 * @PackageName: cn.hsa.module.base.modify.dao
 * @Class_name: BaseModifyTraceDAO
 * @Description: 修改痕迹数据访问层
 * @Author: zengfeng
 * @Email: 954123283@qq.com
 * @Date: 2020/7/25  10:28
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface BaseModifyTraceDAO {
    /**
     * @Method: queryAll()
     * @Description: 分页带参数查询修改痕迹
     * @Param: baseModifyTraceDTO
     * @Author: zengfeng
     * @Email: 954123283@qq.com
     * @Date: 2020/7/25  10:28
     * @Return: 修改痕迹信息数据传输对象list集合
     */
    List<BaseModifyTraceDTO> queryPage(BaseModifyTraceDTO baseModifyTraceDTO);

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
    int insert(BaseModifyTraceDTO baseModifyTraceDTO);

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
