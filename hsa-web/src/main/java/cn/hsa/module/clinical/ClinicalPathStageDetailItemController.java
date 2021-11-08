package cn.hsa.module.clinical;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import cn.hsa.module.clinical.clinicalpathstagedetailitem.dto.ClinicalPathStageDetailItemDTO;
import cn.hsa.module.clinical.clinicalpathstagedetailitem.service.ClinicalPathStageDetailItemService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
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
 * @Package_name: cn.hsa.module.clinical
 * @Class_name: clinicalPathStageDetailItem
 * @Describe: 路径阶段明细项目关联医嘱
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/9/17 14:09
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/inpt/clinicalPathStageDetailItemController")
@Slf4j
public class ClinicalPathStageDetailItemController extends BaseController {
  @Resource
  private ClinicalPathStageDetailItemService clinicalPathStageDetailItemService;

  /**
  * @Menthod getClinicalPathStageDetailItemDTOById
  * @Desrciption 查询路径明细绑定医嘱
  *
  * @Param
  * [clinicalPathStageDetailItemDTO, req, res]
  *
  * @Author jiahong.yang
  * @Date   2021/9/17 15:35
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.clinical.clinicalpathstagedetailitem.dto.ClinicalPathStageDetailItemDTO>
  **/
  @GetMapping("/getClinicalPathStageDetailItemDTOById")
  public WrapperResponse<ClinicalPathStageDetailItemDTO> getClinicalPathStageDetailItemDTOById(ClinicalPathStageDetailItemDTO clinicalPathStageDetailItemDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req,res);
    String hospCode = sysUserDTO.getHospCode();
    clinicalPathStageDetailItemDTO.setHospCode(hospCode);
    Map map = new HashMap<>();
    map.put("hospCode",hospCode);
    map.put("clinicalPathStageDetailItemDTO",clinicalPathStageDetailItemDTO);
    return clinicalPathStageDetailItemService.getClinicalPathStageDetailItemDTOById(map);
  }

  /**
  * @Menthod queryClinicalPathStageDetailItemDTOPage
  * @Desrciption 分页查询路径明细绑定医嘱
  *
  * @Param
  * [clinicalPathStageDetailItemDTO, req, res]
  *
  * @Author jiahong.yang
  * @Date   2021/9/17 15:35
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.clinical.clinicalpathstagedetailitem.dto.ClinicalPathStageDetailItemDTO>>
  **/
  @RequestMapping("/queryClinicalPathStageDetailItemDTOPage")
  public WrapperResponse<PageDTO> queryClinicalPathStageDetailItemDTOPage(ClinicalPathStageDetailItemDTO clinicalPathStageDetailItemDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req,res);
    String hospCode = sysUserDTO.getHospCode();
    clinicalPathStageDetailItemDTO.setHospCode(hospCode);
    clinicalPathStageDetailItemDTO.setLoginDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
    Map map = new HashMap<>();
    map.put("hospCode",hospCode);
    map.put("clinicalPathStageDetailItemDTO",clinicalPathStageDetailItemDTO);
    return clinicalPathStageDetailItemService.queryClinicalPathStageDetailItemDTOPage(map);
  }

  /**
  * @Menthod queryClinicalPathStageDetailItemDTOAll
  * @Desrciption  查询所有路径明细绑定医嘱
  *
  * @Param
  * [clinicalPathStageDetailItemDTO, req, res]
  *
  * @Author jiahong.yang
  * @Date   2021/9/17 15:36
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @RequestMapping("/queryClinicalPathStageDetailItemDTOAll")
  public WrapperResponse<List<ClinicalPathStageDetailItemDTO>> queryClinicalPathStageDetailItemDTOAll(ClinicalPathStageDetailItemDTO clinicalPathStageDetailItemDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req,res);
    String hospCode = sysUserDTO.getHospCode();
    clinicalPathStageDetailItemDTO.setHospCode(hospCode);
    clinicalPathStageDetailItemDTO.setLoginDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
    Map map = new HashMap<>();
    map.put("hospCode",hospCode);
    map.put("clinicalPathStageDetailItemDTO",clinicalPathStageDetailItemDTO);
    return clinicalPathStageDetailItemService.queryClinicalPathStageDetailItemDTOAll(map);
  }

  /**
  * @Menthod saveClinicalPathStageDetailItemDTO
  * @Desrciption 保存路径明细绑定医嘱
  *
  * @Param
  * [clinicalPathStageDetailItemDTO, req, res]
  *
  * @Author jiahong.yang
  * @Date   2021/9/17 15:36
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @RequestMapping("/saveClinicalPathStageDetailItem")
  public WrapperResponse<Boolean> saveClinicalPathStageDetailItem(@RequestBody ClinicalPathStageDetailItemDTO clinicalPathStageDetailItemDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req,res);
    String hospCode = sysUserDTO.getHospCode();
    clinicalPathStageDetailItemDTO.setHospCode(hospCode);
    clinicalPathStageDetailItemDTO.setCrteId(sysUserDTO.getId());
    clinicalPathStageDetailItemDTO.setCrteName(sysUserDTO.getName());
    clinicalPathStageDetailItemDTO.setCrteTime(DateUtils.getNow());
    Map map = new HashMap<>();
    map.put("hospCode",hospCode);
    map.put("clinicalPathStageDetailItemDTO",clinicalPathStageDetailItemDTO);
    return clinicalPathStageDetailItemService.saveClinicalPathStageDetailItem(map);
  }

  /**
  * @Menthod updateClinicalPathStageDetailItemDTO
  * @Desrciption 修改路径明细绑定医嘱
  *
  * @Param
  * [clinicalPathStageDetailItemDTO, req, res]
  *
  * @Author jiahong.yang
  * @Date   2021/9/17 15:37
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @RequestMapping("/updateClinicalPathStageDetailItemDTO")
  public WrapperResponse<Boolean> updateClinicalPathStageDetailItemDTO(@RequestBody ClinicalPathStageDetailItemDTO clinicalPathStageDetailItemDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req,res);
    String hospCode = sysUserDTO.getHospCode();
    clinicalPathStageDetailItemDTO.setHospCode(hospCode);
    Map map = new HashMap<>();
    map.put("hospCode",hospCode);
    map.put("clinicalPathStageDetailItemDTO",clinicalPathStageDetailItemDTO);
    return clinicalPathStageDetailItemService.updateClinicalPathStageDetailItemDTO(map);
  }

  /**
  * @Menthod deleteClinicalPathStageDetailItemDTOById
  * @Desrciption 删除路径明细绑定医嘱
  *
  * @Param
  * [clinicalPathStageDetailItemDTO, req, res]
  *
  * @Author jiahong.yang
  * @Date   2021/9/17 15:38
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @RequestMapping("/deleteClinicalPathStageDetailItemDTOById")
  public WrapperResponse<Boolean> deleteClinicalPathStageDetailItemDTOById(@RequestBody ClinicalPathStageDetailItemDTO clinicalPathStageDetailItemDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req,res);
    String hospCode = sysUserDTO.getHospCode();
    clinicalPathStageDetailItemDTO.setHospCode(hospCode);
    Map map = new HashMap<>();
    map.put("hospCode",hospCode);
    map.put("clinicalPathStageDetailItemDTO",clinicalPathStageDetailItemDTO);
    return clinicalPathStageDetailItemService.deleteClinicalPathStageDetailItemDTOById(map);
  }

  /**
  * @Menthod quertAllItem
  * @Desrciption 查询所有项目（药品，材料，医嘱项目）
  *
  * @Param
  * [clinicalPathStageDetailItemDTO, req, res]
  *
  * @Author jiahong.yang
  * @Date   2021/9/22 10:19
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @RequestMapping("/quertAllItem")
  public WrapperResponse<PageDTO> quertAllItem(BaseDrugDTO baseDrugDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req,res);
    String hospCode = sysUserDTO.getHospCode();
    baseDrugDTO.setHospCode(hospCode);
    Map map = new HashMap<>();
    map.put("hospCode",hospCode);
    map.put("baseDrugDTO",baseDrugDTO);
    return clinicalPathStageDetailItemService.quertAllItem(map);
  }


}
