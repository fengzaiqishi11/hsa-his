package cn.hsa.module.inpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.inptguarantee.dto.InptGuaranteeDTO;
import cn.hsa.module.inpt.inptguarantee.service.InptGuaranteeService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.SnowflakeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt
 * @Class_name: GuaranteeAmountController
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/8/10 14:25
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@Slf4j
@RequestMapping("/web/inpt/inptGuarantee")
public class InptGuaranteeController extends BaseController {

  @Resource
  private InptGuaranteeService inptGuaranteeService_consumer;


    /**
    * @Menthod getById
    * @Desrciption 查询单个保证金信息
    *
    * @Param
    * [inptGuaranteeDTO, req, res]
    *
    * @Author jiahong.yang
    * @Date   2021/8/10 15:47
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.inpt.inptguarantee.dto.InptGuaranteeDTO>
    **/
    @GetMapping("/queryById")
    public WrapperResponse<InptGuaranteeDTO> queryById(InptGuaranteeDTO inptGuaranteeDTO, HttpServletRequest req, HttpServletResponse res){
      SysUserDTO sysUserDTO = getSession(req, res);
      Map map = new HashMap();
      map.put("inptGuaranteeDTO",inptGuaranteeDTO);
      map.put("hospCode",sysUserDTO.getHospCode());
      return inptGuaranteeService_consumer.queryById(map);
    }


    /**
    * @Menthod queryPageInptGuarantee
    * @Desrciption 分页查询所有保证金
    *
    * @Param
    * [inptGuaranteeDTO, req, res]
    *
    * @Author jiahong.yang
    * @Date   2021/8/10 15:47
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping("/queryPageInptGuarantee")
    public WrapperResponse<PageDTO> queryPageInptGuarantee(InptGuaranteeDTO inptGuaranteeDTO, HttpServletRequest req, HttpServletResponse res){
      SysUserDTO sysUserDTO = getSession(req, res);
      inptGuaranteeDTO.setHospCode( sysUserDTO.getHospCode());
      Map map = new HashMap();
      map.put("hospCode",sysUserDTO.getHospCode());
      map.put("inptGuaranteeDTO",inptGuaranteeDTO);
      return inptGuaranteeService_consumer.queryPageInptGuarantee(map);
    }


    /**
    * @Menthod queryAllInptGuarantee
    * @Desrciption 查询所有保证金
    *
    * @Param
    * [inptGuaranteeDTO, req, res]
    *
    * @Author jiahong.yang
    * @Date   2021/8/10 15:47
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.inptguarantee.dto.InptGuaranteeDTO>>
    **/
    @GetMapping("/queryAllInptGuarantee")
    public WrapperResponse<List<InptGuaranteeDTO>> queryAllInptGuarantee(InptGuaranteeDTO inptGuaranteeDTO, HttpServletRequest req, HttpServletResponse res){
      SysUserDTO sysUserDTO = getSession(req, res);
      inptGuaranteeDTO.setHospCode( sysUserDTO.getHospCode());
      Map map = new HashMap();
      map.put("hospCode",sysUserDTO.getHospCode());
      map.put("inptGuaranteeDTO",inptGuaranteeDTO);
      return this.inptGuaranteeService_consumer.queryAllInptGuarantee(map);
    }


    /**
    * @Menthod save
    * @Desrciption  保存
    *
    * @Param
    * [inptGuaranteeDTO, req, res]
    *
    * @Author jiahong.yang
    * @Date   2021/8/10 15:52
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/save")
    public WrapperResponse<Boolean> save(@RequestBody InptGuaranteeDTO inptGuaranteeDTO, HttpServletRequest req, HttpServletResponse res){
      SysUserDTO sysUserDTO = getSession(req, res);
      inptGuaranteeDTO.setHospCode( sysUserDTO.getHospCode());
      inptGuaranteeDTO.setCrteId(sysUserDTO.getId());
      inptGuaranteeDTO.setCrteName(sysUserDTO.getName());
      inptGuaranteeDTO.setCrteTime(DateUtils.getNow());
      Map map = new HashMap();
      map.put("hospCode",sysUserDTO.getHospCode());
      map.put("inptGuaranteeDTO",inptGuaranteeDTO);
      return inptGuaranteeService_consumer.save(map);
    }


    /**
    * @Menthod updateAuditCode
    * @Desrciption 修改状态
    *
    * @Param
    * [inptGuaranteeDTO, req, res]
    *
    * @Author jiahong.yang
    * @Date   2021/8/10 15:48
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/updateAuditCode")
    public WrapperResponse<Boolean> updateAuditCode(@RequestBody InptGuaranteeDTO inptGuaranteeDTO, HttpServletRequest req, HttpServletResponse res){
      SysUserDTO sysUserDTO = getSession(req, res);
      inptGuaranteeDTO.setHospCode(sysUserDTO.getHospCode());
      inptGuaranteeDTO.setAuditId(sysUserDTO.getId());
      inptGuaranteeDTO.setAuditName(sysUserDTO.getName());
      inptGuaranteeDTO.setAuditTime(DateUtils.getNow());
      Map map = new HashMap();
      map.put("hospCode",sysUserDTO.getHospCode());
      map.put("inptGuaranteeDTO",inptGuaranteeDTO);
      return inptGuaranteeService_consumer.updateAuditCode(map);
    }

}
