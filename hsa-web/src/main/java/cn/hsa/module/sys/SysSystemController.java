package cn.hsa.module.sys;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sys.system.dto.SysSystemDTO;
import cn.hsa.module.sys.system.entity.SysSystemDo;
import cn.hsa.module.sys.system.service.SysSystemService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.sys
 * @class_name: SysSystemController
 * @Description: 子系统控制层
 * @Author: youxianlin
 * @Email: 254580179@qq.com
 * @Date: 2020/7/30 9:31
 * @Company: 创智和宇
 **/
@RestController
@RequestMapping("/web/sys/system")
@Slf4j
public class SysSystemController extends BaseController {

    @Resource
    private SysSystemService sysSystemService_consumer;

    /**
     * @Method: queryAll
     * @Description: 获取所有的子系统列表
     * @Param: [sysSystemDo]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 9:34
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < cn.hsa.module.sys.system.entity.SysSystemDo>>
     **/
    @GetMapping("/queryAll")
    public WrapperResponse<List<SysSystemDTO>> queryAll(SysSystemDo sysSystemDo, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        sysSystemDo.setHospCode(sysUserDTO.getHospCode());
        map.put("sysSystemDo", sysSystemDo);
        return sysSystemService_consumer.queryAll(map);
    }

    /**
     * @Method: queryPage
     * @Description: 分页获取子系统列表
     * @Param: [sysSystemDo]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 9:43
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(SysSystemDTO sysSystemDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        sysSystemDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("sysSystemDTO", sysSystemDTO);
        return sysSystemService_consumer.queryPage(map);
    }

    /**
     * @Method: save
     * @Description: 保存、修改子系统
     * @Param: [SysSystemDTO]
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/6 9:05
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/save")
    public WrapperResponse<Boolean> save(@RequestBody SysSystemDTO sysSystemDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        sysSystemDTO.setHospCode(sysUserDTO.getHospCode());
        sysSystemDTO.setCrteId(sysUserDTO.getId());
        sysSystemDTO.setCrteName(sysUserDTO.getName());
        sysSystemDTO.setCrteTime(DateUtils.getNow());

        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("sysSystemDTO", sysSystemDTO);
        return sysSystemService_consumer.save(map);
    }

    /**
     * @Method: delete
     * @Description: 删除子系统
     * @Param: List<SysSystemDTO>
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/6 9:05
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/delete")
    public WrapperResponse<Boolean> delete(@RequestBody List<SysSystemDTO> sysSystemDTOS, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        sysSystemDTOS.forEach(sysSystemDTO -> {
            sysSystemDTO.setHospCode(sysUserDTO.getHospCode());
        });
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("sysSystemDTOS", sysSystemDTOS);
        return sysSystemService_consumer.delete(map);
    }

    /**
     * @Method getById
     * @Desrciption 通过id获取值
     * @Param: [SysSystemDTO]
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/6 9:05
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.system.dto.SysSystemDTO>
     **/
    @GetMapping("/getById")
    public WrapperResponse<SysSystemDTO> getById(SysSystemDTO sysSystemDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        sysSystemDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("sysSystemDTO", sysSystemDTO);
        return sysSystemService_consumer.getById(map);
    }

    /**
     * @Method querySystemSeqNo
     * @Desrciption 查询子系统序号自动填充前端
     * @Param: [SysSystemDTO]
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/6 9:05
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<int>
     **/
    @GetMapping("/querySystemSeqNo")
    public WrapperResponse<Integer> querySystemSeqNo(SysSystemDTO sysSystemDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        sysSystemDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("sysSystemDTO", sysSystemDTO);
        return sysSystemService_consumer.querySystemSeqNo(map);
    }
}
