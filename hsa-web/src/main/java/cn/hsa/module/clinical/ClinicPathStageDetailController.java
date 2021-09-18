package cn.hsa.module.clinical;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.clinical.clinicalpathstagedetail.dto.ClinicPathStageDetailDTO;
import cn.hsa.module.clinical.clinicalpathstagedetail.service.ClinicPathStageDetailService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.clinical
 * @Class_name: ClinicPathStageDetailController
 * @Describe:  临床路径-路径明细维护控制层
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/9/10 10:31
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/inpt/clinicPathStageDetailController")
@Slf4j
public class ClinicPathStageDetailController extends BaseController {
  @Resource
  private ClinicPathStageDetailService clinicPathStageDetailService_consumer;

  /**
  * @Menthod getClinicPathStageDetailById
  * @Desrciption 查询单个路径阶段明细
  *
  * @Param
  * [clinicPathStageDetailDTO, req, res]
  *
  * @Author jiahong.yang
  * @Date   2021/9/10 10:58
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.clinical.clinicalpathstagedetail.dto.ClinicPathStageDetailDTO>
  **/
  @GetMapping("/getClinicPathStageDetailById")
  public WrapperResponse<ClinicPathStageDetailDTO> getClinicPathStageDetailById(ClinicPathStageDetailDTO clinicPathStageDetailDTO, HttpServletRequest req, HttpServletResponse res) {
    HashMap map = new HashMap();
    SysUserDTO sysUserDTO = getSession(req, res);
    clinicPathStageDetailDTO.setHospCode(sysUserDTO.getHospCode());
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("clinicPathStageDetailDTO",clinicPathStageDetailDTO);
    return clinicPathStageDetailService_consumer.getClinicPathStageDetailById(map);
  }

  /**
  * @Menthod queryAllClinicPathStageDetail
  * @Desrciption 查询所有路径阶段明细
  *
  * @Param
  * [clinicPathStageDetailDTO, req, res]
  *
  * @Author jiahong.yang
  * @Date   2021/9/10 11:00
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.clinical.clinicalpathstagedetail.dto.ClinicPathStageDetailDTO>>
  **/
  @GetMapping("/queryAllClinicPathStageDetail")
  public WrapperResponse<List<ClinicPathStageDetailDTO>> queryAllClinicPathStageDetail(ClinicPathStageDetailDTO clinicPathStageDetailDTO, HttpServletRequest req, HttpServletResponse res) {
    HashMap map = new HashMap();
    SysUserDTO sysUserDTO = getSession(req, res);
    clinicPathStageDetailDTO.setHospCode(sysUserDTO.getHospCode());
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("clinicPathStageDetailDTO",clinicPathStageDetailDTO);
    return clinicPathStageDetailService_consumer.queryAllClinicPathStageDetail(map);
  }

  /**
  * @Menthod queryClinicPathStageDetailPage
  * @Desrciption 分页查询路径阶段明细
  *
  * @Param
  * [clinicPathStageDetailDTO, req, res]
  *
  * @Author jiahong.yang
  * @Date   2021/9/10 11:01
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @GetMapping("/queryClinicPathStageDetailPage")
  public WrapperResponse<PageDTO> queryClinicPathStageDetailPage(ClinicPathStageDetailDTO clinicPathStageDetailDTO, HttpServletRequest req, HttpServletResponse res) {
    HashMap map = new HashMap();
    SysUserDTO sysUserDTO = getSession(req, res);
    clinicPathStageDetailDTO.setHospCode(sysUserDTO.getHospCode());
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("clinicPathStageDetailDTO",clinicPathStageDetailDTO);
    return clinicPathStageDetailService_consumer.queryClinicPathStageDetailPage(map);
  }

  /**
  * @Menthod insertClinicPathStageDetail
  * @Desrciption 新增路径阶段明细
  *
  * @Param
  * [clinicPathStageDetailDTO, req, res]
  *
  * @Author jiahong.yang
  * @Date   2021/9/10 11:01
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/insertClinicPathStageDetail")
  public WrapperResponse<Boolean> insertClinicPathStageDetail(@RequestBody ClinicPathStageDetailDTO clinicPathStageDetailDTO, HttpServletRequest req, HttpServletResponse res) {
    HashMap map = new HashMap();
    SysUserDTO sysUserDTO = getSession(req, res);
    clinicPathStageDetailDTO.setHospCode(sysUserDTO.getHospCode());
    clinicPathStageDetailDTO.setCrteId(sysUserDTO.getId());
    clinicPathStageDetailDTO.setCrteName(sysUserDTO.getName());
    clinicPathStageDetailDTO.setCrteTime(DateUtils.getNow());
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("clinicPathStageDetailDTO",clinicPathStageDetailDTO);
    return clinicPathStageDetailService_consumer.insertClinicPathStageDetail(map);

  }

  /**
  * @Menthod updateClinicPathStageDetail
  * @Desrciption 修改路径阶段明细
  *
  * @Param
  * [clinicPathStageDetailDTO, req, res]
  *
  * @Author jiahong.yang
  * @Date   2021/9/10 11:01
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/updateClinicPathStageDetail")
  public WrapperResponse<Boolean> updateClinicPathStageDetail(@RequestBody ClinicPathStageDetailDTO clinicPathStageDetailDTO, HttpServletRequest req, HttpServletResponse res) {
    HashMap map = new HashMap();
    SysUserDTO sysUserDTO = getSession(req, res);
    clinicPathStageDetailDTO.setHospCode(sysUserDTO.getHospCode());
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("clinicPathStageDetailDTO",clinicPathStageDetailDTO);
    return clinicPathStageDetailService_consumer.updateClinicPathStageDetail(map);
  }

  /**
  * @Menthod deleteClinicPathStageDetailById
  * @Desrciption 删除路径阶段明细
  *
  * @Param
  * [clinicPathStageDetailDTO, req, res]
  *
  * @Author jiahong.yang
  * @Date   2021/9/10 11:01
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/deleteClinicPathStageDetailById")
  public WrapperResponse<Boolean> deleteClinicPathStageDetailById(@RequestBody ClinicPathStageDetailDTO clinicPathStageDetailDTO, HttpServletRequest req, HttpServletResponse res) {
    HashMap map = new HashMap();
    SysUserDTO sysUserDTO = getSession(req, res);
    clinicPathStageDetailDTO.setHospCode(sysUserDTO.getHospCode());
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("clinicPathStageDetailDTO",clinicPathStageDetailDTO);
    return clinicPathStageDetailService_consumer.deleteClinicPathStageDetailById(map);
  }

  /**
  * @Menthod queryClinicalPathTree
  * @Desrciption 查询路径阶段树
  *
  * @Param
  * [clinicPathStageDetailDTO, req, res]
  *
  * @Author jiahong.yang
  * @Date   2021/9/13 15:43
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
  **/
  @RequestMapping("/queryClinicalPathTree")
  public WrapperResponse<List<TreeMenuNode>> queryClinicalPathTree(ClinicPathStageDetailDTO clinicPathStageDetailDTO, HttpServletRequest req, HttpServletResponse res){
    HashMap map = new HashMap();
    SysUserDTO sysUserDTO = getSession(req, res);
    clinicPathStageDetailDTO.setHospCode(sysUserDTO.getHospCode());
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("clinicPathStageDetailDTO",clinicPathStageDetailDTO);
    return clinicPathStageDetailService_consumer.queryClinicalPathTree(map);
  }


}
