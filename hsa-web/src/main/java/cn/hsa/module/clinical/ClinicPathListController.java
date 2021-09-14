package cn.hsa.module.clinical;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.clinical.clinicalpathlist.dto.ClinicPathListDTO;
import cn.hsa.module.clinical.clinicalpathlist.service.ClinicPathListService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.DateUtils;
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

/**
 * @Package_name: cn.hsa.module.clinical
 * @Class_name: ClinicPathListController
 * @Describe: 临床路径目录维护控制层
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/9/8 19:59
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/inpt/clinicPathListController")
@Slf4j
public class ClinicPathListController extends BaseController {
   @Resource
   private ClinicPathListService clinicPathListService_consumer;

     /**
     * @Menthod getClinicPathById
     * @Desrciption 临床路径目录根据id查询
     *
     * @Param
     * [map]
     *
     * @Author jiahong.yang
     * @Date   2021/9/9 10:40
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.clinical.clinicalpathlist.dto.ClinicPathListDTO>
     **/
    @RequestMapping("getClinicPathById")
    public WrapperResponse<ClinicPathListDTO> getClinicPathById(ClinicPathListDTO clinicPathListDTO, HttpServletRequest req, HttpServletResponse res) {
      SysUserDTO sysUserDTO = getSession(req, res);
      HashMap map = new HashMap();
      clinicPathListDTO.setHospCode(sysUserDTO.getHospCode());
      map.put("hospCode",sysUserDTO.getHospCode());
      map.put("clinicPathListDTO",clinicPathListDTO);
      return clinicPathListService_consumer.getClinicPathById(map);
    }

    /**
    * @Menthod queryClinicPathAll
    * @Desrciption 查询全部临床路径目录
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/9/9 10:40
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.clinical.clinicalpathlist.dto.ClinicPathListDTO>>
    **/
    @RequestMapping("queryClinicPathAll")
    public WrapperResponse<List<ClinicPathListDTO>> queryClinicPathAll(ClinicPathListDTO clinicPathListDTO, HttpServletRequest req, HttpServletResponse res) {
      SysUserDTO sysUserDTO = getSession(req, res);
      HashMap map = new HashMap();
      clinicPathListDTO.setHospCode(sysUserDTO.getHospCode());
      map.put("hospCode",sysUserDTO.getHospCode());
      map.put("clinicPathListDTO",clinicPathListDTO);
      return clinicPathListService_consumer.queryClinicPathAll(map);
    }

    /**
    * @Menthod queryClinicPathPage
    * @Desrciption 分页查询临床路径目录
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/9/9 10:40
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @RequestMapping("queryClinicPathPage")
    public WrapperResponse<PageDTO> queryClinicPathPage(ClinicPathListDTO clinicPathListDTO, HttpServletRequest req, HttpServletResponse res) {
      SysUserDTO sysUserDTO = getSession(req, res);
      HashMap map = new HashMap();
      clinicPathListDTO.setHospCode(sysUserDTO.getHospCode());
      map.put("hospCode",sysUserDTO.getHospCode());
      map.put("clinicPathListDTO",clinicPathListDTO);
      return clinicPathListService_consumer.queryClinicPathPage(map);
    }

    /**
    * @Menthod saveClinicPath
    * @Desrciption 保存(新增或编辑)临床路径目录
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/9/9 10:40
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @RequestMapping("saveClinicPath")
    public WrapperResponse<Boolean> saveClinicPath(@RequestBody ClinicPathListDTO clinicPathListDTO, HttpServletRequest req, HttpServletResponse res) {
      SysUserDTO sysUserDTO = getSession(req, res);
      HashMap map = new HashMap();
      clinicPathListDTO.setHospCode(sysUserDTO.getHospCode());
      clinicPathListDTO.setCrteId(sysUserDTO.getId());
      clinicPathListDTO.setCrteName(sysUserDTO.getName());
      clinicPathListDTO.setCrteTime(DateUtils.getNow());
      clinicPathListDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
      clinicPathListDTO.setDeptName(sysUserDTO.getLoginBaseDeptDTO().getName());
      map.put("hospCode",sysUserDTO.getHospCode());
      map.put("clinicPathListDTO",clinicPathListDTO);
      return clinicPathListService_consumer.saveClinicPath(map);
    }

    /**
    * @Menthod updateClinicPath
    * @Desrciption 审核/作废
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/9/9 10:40
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("updateClinicPathAuditCode")
    public WrapperResponse<Boolean> updateClinicPathAuditCode(@RequestBody ClinicPathListDTO clinicPathListDTO, HttpServletRequest req, HttpServletResponse res) {
      SysUserDTO sysUserDTO = getSession(req, res);
      HashMap map = new HashMap();
      clinicPathListDTO.setHospCode(sysUserDTO.getHospCode());
      map.put("hospCode",sysUserDTO.getHospCode());
      map.put("clinicPathListDTO",clinicPathListDTO);
      return clinicPathListService_consumer.updateClinicPathAuditCode(map);
    }

    /**
    * @Menthod deleteClinicPathById
    * @Desrciption
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/9/9 10:40
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @RequestMapping("deleteClinicPathById")
    public WrapperResponse<Boolean> deleteClinicPathById(ClinicPathListDTO clinicPathListDTO, HttpServletRequest req, HttpServletResponse res) {
      SysUserDTO sysUserDTO = getSession(req, res);
      HashMap map = new HashMap();
      clinicPathListDTO.setHospCode(sysUserDTO.getHospCode());
      map.put("hospCode",sysUserDTO.getHospCode());
      map.put("clinicPathListDTO",clinicPathListDTO);
      return clinicPathListService_consumer.deleteClinicPathById(map);
    }
}
