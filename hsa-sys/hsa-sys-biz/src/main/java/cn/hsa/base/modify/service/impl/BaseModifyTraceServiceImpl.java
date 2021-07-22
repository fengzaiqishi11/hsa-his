package cn.hsa.base.modify.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.modify.bo.BaseModifyTraceBO;
import cn.hsa.module.base.modify.dto.BaseModifyTraceDTO;
import cn.hsa.module.base.modify.service.BaseModifyTraceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

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
@HsafRestPath("/service/base/modifyTrace")
@Slf4j
@Service("baseModifyTraceService_provider")
public class BaseModifyTraceServiceImpl extends HsafService implements BaseModifyTraceService {

    /**
     * 修改痕迹数据库访问接口
     */
    @Autowired
    private BaseModifyTraceBO baseModifyTraceBO;

    /**
     * @Method: queryPage()
     * @Description: 分页带参数查询修改痕迹
     * @Param: baseModifyTraceDTO
     * @Author: zengfeng
     * @Email: 954123283@qq.com
     * @Date: 2020/7/25  10:28
     * @Return: PageDTO分页数据传输对象
     */
    @Override
    @HsafRestPath(value = "/queryPage", method = RequestMethod.GET)
    public WrapperResponse<PageDTO> queryPage(BaseModifyTraceDTO baseModifyTraceDTO) {
        PageDTO pageDTO = baseModifyTraceBO.queryPage(baseModifyTraceDTO);
        return WrapperResponse.success(pageDTO);
    }

    /**
     * @Method: getById()
     * @Description: 通过id获取修改痕迹
     * @Param: baseModifyTraceDTO
     * @Author: zengfeng
     * @Email: 954123283@qq.com
     * @Date: 2020/7/25  10:28
     * @Return: 具体修改痕迹对象
     */
    @Override
    @HsafRestPath(value = "/getById", method = RequestMethod.GET)
    public WrapperResponse<BaseModifyTraceDTO> getById(BaseModifyTraceDTO baseModifyTraceDTO){
        return WrapperResponse.success(baseModifyTraceBO.getById(baseModifyTraceDTO));
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
    @HsafRestPath(value = "/getTableConent",method = RequestMethod.GET)
    @Override
    public WrapperResponse<List<Map<String, Object>>> getTableConent(BaseModifyTraceDTO baseModifyTraceDTO) {
        return WrapperResponse.success(baseModifyTraceBO.getTableConent(baseModifyTraceDTO));
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
    public WrapperResponse<Boolean> insert(BaseModifyTraceDTO baseModifyTraceDTO) {
        return WrapperResponse.success(baseModifyTraceBO.insert(baseModifyTraceDTO));
    }
}
