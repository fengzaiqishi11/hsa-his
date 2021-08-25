package cn.hsa.base.modify.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.base.modify.bo.BaseModifyTraceBO;
import cn.hsa.module.base.modify.dao.BaseModifyTraceDAO;
import cn.hsa.module.base.modify.dto.BaseModifyTraceDTO;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.base.modify.bo
 * @class_name: BaseModifyTraceApiImpl
 * @Description: 修改痕迹Api实现业务逻辑
 * @Author: zengfeng
 * @Email: 954123283@qq.com
 * @Date: 2020/7/25  10:28
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class BaseModifyTraceBOImpl extends HsafBO implements BaseModifyTraceBO {

    /**
     * 修改痕迹数据库访问接口
     */
    @Resource
    private BaseModifyTraceDAO baseModifyTraceDAO;

    /**
     * @Method: queryPage()
     * @Description: 分页带参数查询修改痕迹
     * @Param: baseModifyTraceDTO
     * @Author: zengfeng
     * @Email: 954123283@qq.com
     * @Date: 2020/7/25  10:28
     * @Return: 修改痕迹信息数据传输对象list集合
     */
    @Override
    public PageDTO queryPage(BaseModifyTraceDTO baseModifyTraceDTO) {
        // 设置分页信息
        PageHelper.startPage(baseModifyTraceDTO.getPageNo(), baseModifyTraceDTO.getPageSize());
        List<BaseModifyTraceDTO> modifyTraceDTOList = baseModifyTraceDAO.queryPage(baseModifyTraceDTO);
        return PageDTO.of(modifyTraceDTOList);
    }

    /**
     * @Method: getById()
     * @Description: 通过主键查询修改痕迹
     * @Param: baseModifyTraceDTO
     * @Author: zengfeng
     * @Email: 954123283@qq.com
     * @Date: 2020/7/25  10:28
     * @Return: 修改痕迹详细信息
     */
    @Override
    public BaseModifyTraceDTO getById(BaseModifyTraceDTO baseModifyTraceDTO) {
        return baseModifyTraceDAO.getById(baseModifyTraceDTO);
    }

    /**
     * @Method: insert()
     * @Description: 新增修改痕迹
     * @Param: baseModifyTraceDTO
     * @Author: zengfeng
     * @Email: 954123283@qq.com
     * @Date: 2020/7/25  10:28
     * @Return: 是否成功
     */
    @Override
    public boolean insert(BaseModifyTraceDTO baseModifyTraceDTO) {
        return baseModifyTraceDAO.insert(baseModifyTraceDTO) > 0;
    }

    /**
     * @Method: getTableConent()
     * @Description: 查询数据详情
     * @Param: baseModifyTraceDTO
     * @Author: zengfeng
     * @Email: 954123283@qq.com
     * @Date: 2020/7/25  10:28
     * @Return: 返回list
     */
    @Override
    public List<Map<String, Object>> getTableConent(BaseModifyTraceDTO baseModifyTraceDTO) {
        return baseModifyTraceDAO.getTableConent(baseModifyTraceDTO);
    }
}
