package cn.hsa.module.oper;


import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.oper.operInforecord.dto.OperAccountTempDTO;
import cn.hsa.module.oper.operInforecord.dto.OperAccountTempDetailDTO;
import cn.hsa.module.oper.operInforecord.entity.OperAccountTempDetailDO;
import cn.hsa.module.oper.operInforecord.service.OperAccountTempService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@Package_name: cn.hsa.module.oper
 *@Class_name: operAccountTempController
 *@Describe: 手术模板 controller
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/12/5 11:24
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/oper/operAccountTemp/")
@Slf4j
public class OperAccountTempController extends BaseController {

    @Resource
    OperAccountTempService operAccountTempService_consumer;

    /**
     * @Method queryOperAccountTempDTOPage
     * @Desrciption 手术模板分页查询
     * @param operAccountTempDTO
     * @Author liuqi1
     * @Date   2020/12/5 11:00
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @RequestMapping("queryOperAccountTempDTOPage")
    public WrapperResponse<PageDTO> queryOperAccountTempDTOPage(OperAccountTempDTO operAccountTempDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        operAccountTempDTO.setHospCode(sysUserDTO.getHospCode());
        operAccountTempDTO.setDeptId(sysUserDTO.getBaseDeptDTO().getId());

        Map<String,Object> map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("operAccountTempDTO",operAccountTempDTO);

        //手术模板分页查询
        WrapperResponse<PageDTO> response = operAccountTempService_consumer.queryOperAccountTempDTOPage(map);

        return response;
    }

    /**
     * @Method queryOperAccountTempDetailDTOPage
     * @Desrciption 手术模板明细分页查询
     * @param operAccountTempDetailDTO
     * @Author liuqi1
     * @Date   2020/12/5 11:02
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @RequestMapping("queryOperAccountTempDetailDTOPage")
    public WrapperResponse<PageDTO> queryOperAccountTempDetailDTOPage(OperAccountTempDetailDTO operAccountTempDetailDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        operAccountTempDetailDTO.setHospCode(sysUserDTO.getHospCode());

        Map<String,Object> map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("operAccountTempDetailDTO",operAccountTempDetailDTO);

        //手术模板明细分页查询
        WrapperResponse<PageDTO> response = operAccountTempService_consumer.queryOperAccountTempDetailDTOPage(map);

        return response;
    }

    /**
     * @Method queryOperAccountTempDetailDTO
     * @Desrciption 手术模板明细分页查询
     * @param operAccountTempDetailDTO
     * @Author liuqi1
     * @Date   2020/12/5 11:02
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @RequestMapping("queryOperAccountTempDetailDTO")
    public WrapperResponse<List<OperAccountTempDetailDTO>> queryOperAccountTempDetailDTO(OperAccountTempDetailDTO operAccountTempDetailDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        operAccountTempDetailDTO.setHospCode(sysUserDTO.getHospCode());

        Map<String,Object> map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("operAccountTempDetailDTO",operAccountTempDetailDTO);

        //手术模板明细分页查询
        WrapperResponse<List<OperAccountTempDetailDTO>> response = operAccountTempService_consumer.queryOperAccountTempDetailDTO(map);

        return response;
    }

    /**
     * @Method insertOperAccountTempDTO
     * @Desrciption 手术模板、明细新增
     * @param operAccountTempDTO
     * @Author liuqi1
     * @Date   2020/12/5 11:03
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @RequestMapping("insertOperAccountTempDTO")
    public WrapperResponse<Boolean> insertOperAccountTempDTO(@RequestBody OperAccountTempDTO operAccountTempDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        operAccountTempDTO.setHospCode(sysUserDTO.getHospCode());
        operAccountTempDTO.setCrteId(sysUserDTO.getId());
        operAccountTempDTO.setCrteName(sysUserDTO.getName());
        operAccountTempDTO.setDeptId(sysUserDTO.getBaseDeptDTO().getId());
        operAccountTempDTO.setDeptName( sysUserDTO.getBaseDeptDTO().getName());

        Map<String,Object> map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("operAccountTempDTO",operAccountTempDTO);

        //手术模板、明细新增
        WrapperResponse<Boolean> booleanWrapperResponse = operAccountTempService_consumer.insertOperAccountTempDTO(map);

        return booleanWrapperResponse;
    }

    
    /**
    * @Menthod deleteOperAccountTempDTOById
    * @Desrciption  
     * @param operAccountTempDTO
    * @Author xingyu.xie
    * @Date   2021/1/22 11:23 
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @RequestMapping("deleteOperAccountTempDTOById")
    public WrapperResponse<Boolean> deleteOperAccountTempDTOById(@RequestBody OperAccountTempDTO operAccountTempDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        operAccountTempDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("operAccountTempDTO",operAccountTempDTO);

        WrapperResponse<Boolean> booleanWrapperResponse = operAccountTempService_consumer.deleteOperAccountTempDTOById(map);

        return booleanWrapperResponse;
    }


    /**
     * @Menthod deleteOperAccountTempDTOById
     * @Desrciption 保存模版内容和模版明细内容
     * @param operAccountTempDTO
     * @Author xingyu.xie
     * @Date   2021/1/22 11:23
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @RequestMapping("saveOperAccountTemp")
    public WrapperResponse<Boolean> saveOperAccountTemp(@RequestBody OperAccountTempDTO operAccountTempDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();

        if (StringUtils.isEmpty(operAccountTempDTO.getCrteId())){
            operAccountTempDTO.setCrteId(sysUserDTO.getId());
            operAccountTempDTO.setCrteName(sysUserDTO.getName());
        }
        if (StringUtils.isEmpty(operAccountTempDTO.getDeptId())){
            operAccountTempDTO.setDeptId(sysUserDTO.getBaseDeptDTO().getId());
            operAccountTempDTO.setDeptName(sysUserDTO.getBaseDeptDTO().getName());
        }
        operAccountTempDTO.setHospCode(sysUserDTO.getHospCode());

        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("operAccountTempDTO",operAccountTempDTO);

        //手术模板、明细新增
        WrapperResponse<Boolean> booleanWrapperResponse = operAccountTempService_consumer.saveOperAccountTemp(map);

        return booleanWrapperResponse;
    }

}
