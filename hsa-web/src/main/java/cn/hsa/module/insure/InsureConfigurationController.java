package cn.hsa.module.insure;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.service.InsureConfigurationService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.DocumentException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure
 * @Class_name:: ConfigurationController
 * @Description: 医院医保信息配置
 * @Author: zhangxuan
 * @Date: 2020-11-05 15:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/insure/insureConfiguration")
@Slf4j
public class InsureConfigurationController extends BaseController {
    /*
    * 医院医保信息配置维护
    * */
    @Resource
    private InsureConfigurationService insureConfigurationService_consumer;

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
    public WrapperResponse<InsureConfigurationDTO> getById(InsureConfigurationDTO insureConfigurationDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureConfigurationDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureConfigurationDTO",insureConfigurationDTO);
        return insureConfigurationService_consumer.getById(map);
    }


    @GetMapping("/queryAll")
    public WrapperResponse<List<InsureConfigurationDTO>> queryAll(InsureConfigurationDTO insureConfigurationDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureConfigurationDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureConfigurationDTO",insureConfigurationDTO);
        return insureConfigurationService_consumer.queryAll(map);
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
    public WrapperResponse<PageDTO> queryPage(InsureConfigurationDTO insureConfigurationDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureConfigurationDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureConfigurationDTO",insureConfigurationDTO);
        return insureConfigurationService_consumer.queryPage(map);
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
    public WrapperResponse<Boolean> save(@RequestBody InsureConfigurationDTO insureConfigurationDTO, HttpServletRequest req, HttpServletResponse res) throws DocumentException {
        SysUserDTO sysUserDTO = getSession(req, res);
        insureConfigurationDTO.setHospCode(sysUserDTO.getHospCode());
        insureConfigurationDTO.setCrteId(sysUserDTO.getId());
        insureConfigurationDTO.setCrteName(sysUserDTO.getName());
        insureConfigurationDTO.setCrteTime(DateUtils.getNow());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureConfigurationDTO",insureConfigurationDTO);
        return insureConfigurationService_consumer.save(map);
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
    public WrapperResponse<Boolean> delete(@RequestBody InsureConfigurationDTO insureConfigurationDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureConfigurationDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureConfigurationDTO",insureConfigurationDTO);
        return insureConfigurationService_consumer.delete(map);
    }


}
