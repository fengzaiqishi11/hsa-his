package cn.hsa.module.sys;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.sysDate
 * @Class_name: SysParameterController
 * @Describe:  参数信息维护控制层
 * @Author: zhangxuan
 * @Email: zhangxuan@powersi.com
 * @Date: 2020/7/28 9:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/sys/parameter")
@Slf4j
public class SysParameterController extends BaseController {
     /**
     * 参数信息维护dubbo消费者接口
     */
    @Resource
    private SysParameterService sysParameterService_consumer;

    /**
     * @Menthod queryPage()
     * @Desrciption   根据条件分页查询参数信息
     * @Param
     * 1. [sysParameterDTO] 参数数据传输DTO对象
     * @Author zhangxuan
     * @Date   2020/7/28 16:30
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sys.PageDTO>
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(SysParameterDTO sysParameterDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        sysParameterDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("sysParameterDTO",sysParameterDTO);
        return sysParameterService_consumer.queryPage(map);
    }
    /**
     * @Menthod queryAll()
     * @Desrciption  查询所有参数信息
     * @Param
     * [1. sysParameterDTO]
     * @Author zhangxuan
     * @Date   2020/7/28 14:45
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sys.PageDTO>
     **/
    @GetMapping("/queryAll")
    public WrapperResponse<List<SysParameterDTO>> queryAll(SysParameterDTO sysParameterDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        sysParameterDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("sysParameterDTO",sysParameterDTO);
        return this.sysParameterService_consumer.queryAll(map);
    }

    /**
     * @Menthod insert()
     * @Desrciption 新增参数
     * @Param
     * 1.[sysParameterDTO] 参数数据传输DTO对象
     * @Author zhangxuan
     * @Date   2020/7/28 16:28
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.parameter.dto.sysParameterDTO>
     **/
    @PostMapping("/insert")
    public WrapperResponse<Boolean> insert(@RequestBody SysParameterDTO sysParameterDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        sysParameterDTO.setHospCode(sysUserDTO.getHospCode());
        sysParameterDTO.setCrteId(sysUserDTO.getId());
        sysParameterDTO.setCrteName(sysUserDTO.getName());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("sysParameterDTO",sysParameterDTO);
        return sysParameterService_consumer.insert(map);
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
    public WrapperResponse<Boolean> delete(@RequestBody SysParameterDTO sysParameterDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        sysParameterDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("sysParameterDTO",sysParameterDTO);
        return sysParameterService_consumer.delete(map);
    }

    /**
     * @Menthod update()
     * @Desrciption  修改参数信息
     * @Param
     *  1. sysParameterDTO  参数数据对象
     * @Author zhangxuan
     * @Date   2020/7/28 16:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.bspl.dto.sysParameterDTO>
     **/
    @PostMapping("/update")
    public WrapperResponse<Boolean> update(@RequestBody SysParameterDTO sysParameterDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        sysParameterDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("sysParameterDTO",sysParameterDTO);
        return sysParameterService_consumer.update(map);
    }

    /**
     * @Menthod getParameterByCode()
     * @Desrciption  根据code查询参数信息
     * @Param
     *  1. sysParameterDTO  参数数据对象
     * @Author xingyu.xie
     * @Date   2020/7/28 16:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.bspl.dto.sysParameterDTO>
     **/
    @GetMapping("/getParameterByCode")
    public WrapperResponse<SysParameterDTO> getParameterByCode(SysParameterDTO sysParameterDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        sysParameterDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("code",sysParameterDTO.getCode());
        return sysParameterService_consumer.getParameterByCode(map);
    }


}
