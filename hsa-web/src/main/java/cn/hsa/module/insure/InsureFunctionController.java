package cn.hsa.module.insure;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.InsureFunctionDTO;
import cn.hsa.module.insure.module.service.InsureFunctionService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure
 * @Class_name:: InsureFunctionController
 * @Description: 医保配置
 * @Author: zhangxuan
 * @Date: 2020-11-09 14:36
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/insure/insurefunction")
@Slf4j
public class InsureFunctionController extends BaseController {
    /*
     * 医院医保信息配置维护
     * */
    @Resource
    private InsureFunctionService insureFunctionService_consumer;

    /**
     * @Method getById
     * @Desrciption  根据主键查询医院医保信息
     * @Param
     * map[id,hosp_code]
     * @Author zhangxuan
     * @Date   2020-11-05 15:52
     * @Return map
     **/
    @GetMapping("getById")
    public WrapperResponse<InsureFunctionDTO> getById(InsureFunctionDTO insureFunctionDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureFunctionDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureFunctionDTO",insureFunctionDTO);
        return insureFunctionService_consumer.getById(map);
    }

    /**
     * @Method queryPage
     * @Desrciption
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-11-05 16:32
     * @Return map
     **/
    @GetMapping("queryPage")
    public WrapperResponse<PageDTO> queryPage(InsureFunctionDTO insureFunctionDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureFunctionDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureFunctionDTO",insureFunctionDTO);
        return insureFunctionService_consumer.queryPage(map);
    }
    /**
     * @Method save
     * @Desrciption  新增或者修改医院医保信息配置
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-11-05 17:19
     * @Return
     **/
    @PostMapping("save")
    public WrapperResponse<Boolean> save(@RequestBody InsureFunctionDTO insureFunctionDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureFunctionDTO.setHospCode(sysUserDTO.getHospCode());
        insureFunctionDTO.setCrteId(sysUserDTO.getId());
        insureFunctionDTO.setCrteName(sysUserDTO.getName());
        insureFunctionDTO.setCrteTime(DateUtils.getNow());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureFunctionDTO",insureFunctionDTO);
        return insureFunctionService_consumer.save(map);
    }

    /**
     * @Method delete
     * @Desrciption  删除医院医保信息配置
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-11-05 17:19
     * @Return
     **/
    @PostMapping("deleteByIds")
    public WrapperResponse<Boolean> delete(@RequestBody InsureFunctionDTO insureFunctionDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureFunctionDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureFunctionDTO",insureFunctionDTO);
        return insureFunctionService_consumer.delete(map);
    }
}
