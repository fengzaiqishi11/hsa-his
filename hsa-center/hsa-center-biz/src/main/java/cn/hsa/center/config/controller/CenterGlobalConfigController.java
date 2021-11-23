package cn.hsa.center.config.controller;

import cn.hsa.base.CenterBaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.config.dto.CenterGlobalConfigDTO;
import cn.hsa.module.center.config.service.CenterGlobalConfigService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  中心端全局配置信息网关控制层
 * @Author: luonianxin
 * @Date: 2021-11-17
 */
@RestController
@RequestMapping("/center/config")
public class CenterGlobalConfigController extends CenterBaseController {

    @Resource
    private CenterGlobalConfigService centerGlobalConfigService;

    @GetMapping("/queryGlobalConfig")
    public WrapperResponse<PageDTO> queryCenterGlobalConfigPage(CenterGlobalConfigDTO centerGlobalConfigDTO){

        return centerGlobalConfigService.queryCenterGlobalConfig(centerGlobalConfigDTO);
    }

    @PostMapping("/save")
    public WrapperResponse<Boolean> save(@RequestBody  CenterGlobalConfigDTO configDTO){
        Map<String, Object> param = new HashMap<>();
        configDTO.setCrteId(userId);
        configDTO.setCrteName(userName);
        param.put("centerGlobalConfigDTO",configDTO);
        return centerGlobalConfigService.save(param);
    }


    /**
     *  作废中心端全局配置信息
     * @param centerMenuDTO 传输参数实体
     * @return 是否成功
     */
    @PostMapping("/inValidCenterGlobalConfig")
    public WrapperResponse<Boolean> inValidCenterGlobalConfig(@RequestBody  CenterGlobalConfigDTO centerMenuDTO){
        return centerGlobalConfigService.modifyCenterGlobalConfig(centerMenuDTO);
    }

    @GetMapping("/getGlobalConfigById/{id}")
    public WrapperResponse<CenterGlobalConfigDTO> getGlobalConfigById(@PathVariable String id){
        return centerGlobalConfigService.getGlobalConfigById(id);
    }

}
