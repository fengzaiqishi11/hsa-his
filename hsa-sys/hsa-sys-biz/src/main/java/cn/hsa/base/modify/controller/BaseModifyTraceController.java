package cn.hsa.base.modify.controller;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.modify.dto.BaseModifyTraceDTO;
import cn.hsa.module.base.modify.service.BaseModifyTraceService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Package_name: cn.hsa.module.base
 * @Class_name: BaseModifyTraceController
 * @Describe: 修改痕迹
 * @Author: zengfeng
 * @Eamil: zengfeng@powersi.com.cn
 * @Date: 2020/7/27 20:15
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/base/baseModifyTrace")
@Slf4j
public class BaseModifyTraceController extends BaseController {
    @Resource
    BaseModifyTraceService baseModifyTraceService;

    /**
     * @Method: getById()
     * @Description: 通过id获取修改痕迹
     * @Param: baseModifyTraceDTO
     * @Author: zengfeng
     * @Email: 954123283@qq.com
     * @Date: 2020/7/25  10:28
     * @Return: 具体修改痕迹对象
     */
    @GetMapping("/getById")
    public WrapperResponse<BaseModifyTraceDTO> getById(BaseModifyTraceDTO baseModifyTraceDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req,res);
        baseModifyTraceDTO.setHospCode(sysUserDTO.getHospCode());
        return baseModifyTraceService.getById(baseModifyTraceDTO);
    }

    /**
     * @Method: queryPage()
     * @Description: 分页带参数查询修改痕迹
     * @Param: baseModifyTraceDTO
     * @Author: zengfeng
     * @Email: 954123283@qq.com
     * @Date: 2020/7/25  10:28
     * @Return: PageDTO分页数据传输对象
     */
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(BaseModifyTraceDTO baseModifyTraceDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req,res);
        baseModifyTraceDTO.setHospCode(sysUserDTO.getHospCode());
        return baseModifyTraceService.queryPage(baseModifyTraceDTO);
    }
}
