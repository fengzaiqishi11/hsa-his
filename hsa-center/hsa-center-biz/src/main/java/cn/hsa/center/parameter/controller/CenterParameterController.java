package cn.hsa.center.parameter.controller;

import cn.hsa.base.CenterBaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.parameter.dto.CenterParameterDTO;
import cn.hsa.module.center.parameter.service.CenterParameterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.centerDate
 * @Class_name: centerParameterController
 * @Describe:  参数信息维护控制层
 * @Author: zhangxuan
 * @Email: zhangxuan@powersi.com
 * @Date: 2020/7/28 9:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/center/parameter")
@Slf4j
public class CenterParameterController extends CenterBaseController {
    /**
     * 参数信息维护dubbo消费者接口
     */
    @Resource
    private CenterParameterService centerParameterService;

    /**
     * @Menthod queryPage()
     * @Desrciption   根据条件分页查询参数信息
     * @Param
     * 1. [centerParameterDTO] 参数数据传输DTO对象
     * @Author zhangxuan
     * @Date   2020/7/28 16:30
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.center.PageDTO>
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(CenterParameterDTO centerParameterDTO){
        return centerParameterService.queryPage(centerParameterDTO);
    }
    /**
     * @Menthod queryAll()
     * @Desrciption  查询所有参数信息
     * @Param
     * [1. centerParameterDTO]
     * @Author zhangxuan
     * @Date   2020/7/28 14:45
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.parameter.dto.centerParameterDTO>
     **/
    @GetMapping("/queryAll")
    public WrapperResponse<List<CenterParameterDTO>> queryAll(CenterParameterDTO centerParameterDTO){
        return centerParameterService.queryAll(centerParameterDTO);
    }

    /**
     * @Menthod insert()
     * @Desrciption 新增参数
     * @Param
     * 1.[centerParameterDTO] 参数数据传输DTO对象
     * @Author zhangxuan
     * @Date   2020/7/28 16:28
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/insert")
    public WrapperResponse<Boolean> insert(@RequestBody CenterParameterDTO centerParameterDTO){
        centerParameterDTO.setCrteId(userId);
        centerParameterDTO.setCrteName(userName);
        centerParameterDTO.setUpdId(userId);
        centerParameterDTO.setUpdName(userName);
        return centerParameterService.insert(centerParameterDTO);
    }


    /**
     * @Menthod delete()
     * @Desrciption 删除参数
     * @Param
     * 1. [ids] 主键id
     * @Author zhangxuan
     * @Date   2020/7/28 16:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/delete")
    public WrapperResponse<Boolean> delete(@RequestBody CenterParameterDTO centerParameterDTO){
        return centerParameterService.delete(centerParameterDTO);
    }

    /**
     * @Menthod update()
     * @Desrciption  修改参数信息
     * @Param
     *  1. centerParameterDTO  参数数据对象
     * @Author zhangxuan
     * @Date   2020/7/28 16:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.parameter.dto.centerParameterDTO>
     **/
    @PostMapping("/update")
    public WrapperResponse<Boolean> update(@RequestBody CenterParameterDTO centerParameterDTO){
        return centerParameterService.update(centerParameterDTO);
    }

}
