package cn.hsa.module.sys;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.datasource.service.CenterDatasourceService;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    private CenterDatasourceService centerDatasourceService;

    @Autowired
    public void setCenterDatasourceService(CenterDatasourceService centerDatasourceService){
        this.centerDatasourceService = centerDatasourceService;
    }

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
        sysParameterDTO.setCrteId(sysUserDTO.getId());
        sysParameterDTO.setCrteName(sysUserDTO.getName());
        sysParameterDTO.setCrteTime(DateUtils.getNow());
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
        sysParameterDTO.setCrteId(sysUserDTO.getId());
        sysParameterDTO.setCrteName(sysUserDTO.getName());
        sysParameterDTO.setCrteTime(DateUtils.getNow());
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

    /**
    * @Menthod getIsReallyPwd
    * @Desrciption 校验密码是否正确
    *
    * @Param
    * [sysParameterDTO, req, res]
    *
    * @Author jiahong.yang
    * @Date   2021/12/20 14:05
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
    **/
    @GetMapping("/getIsReallyPwd")
    public WrapperResponse<Map> getIsReallyPwd(SysParameterDTO sysParameterDTO, HttpServletRequest req, HttpServletResponse res) {
      SysUserDTO sysUserDTO = getSession(req, res);
      sysParameterDTO.setHospCode(sysUserDTO.getHospCode());
      Map map = new HashMap();
      map.put("hospCode",sysUserDTO.getHospCode());
      map.put("sysParameterDTO",sysParameterDTO);
      map.put("sysUserDTO",sysUserDTO);
      return sysParameterService_consumer.getIsReallyPwd(map);
    }
    /**
     * @Menthod getIsReallyPwd
     * @Desrciption 请求登录人员与机构信息信息
     *
     * @Param
     * [sysParameterDTO, req, res]
     *
     * @Author yuelong.chen
     * @Date   2022/5/10 10:05
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
     **/
    @GetMapping("/getLoginInfo")
    public WrapperResponse<Map> getLoginInfo(HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("sysUserDTO",sysUserDTO);
        return sysParameterService_consumer.getLoginInfo(map);
    }

    @GetMapping("/getHospServiceStats")
    public WrapperResponse<Map<String,Object>> getHospServiceStats(HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        CenterHospitalDTO centerHospitalDTO = new CenterHospitalDTO();
        centerHospitalDTO.setCode(sysUserDTO.getHospCode());
        centerHospitalDTO.setWorkTypeCode(sysUserDTO.getWorkTypeCode());
        Map map = new HashMap();
        map.put("centerHospitalDTO",centerHospitalDTO);
        map.put("hospCode",sysUserDTO.getHospCode());
        return sysParameterService_consumer.getHospServiceStatsByCode(map);
    }

}
