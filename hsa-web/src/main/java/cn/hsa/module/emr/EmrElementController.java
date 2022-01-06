package cn.hsa.module.emr;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.emr.emrelement.dto.EmrElementDTO;
import cn.hsa.module.emr.emrelement.entity.EmrElementMatchDO;
import cn.hsa.module.emr.emrelement.service.EmrElementServcie;
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
 * @Package_name: cn.hsa.module.emr
 * @Class_name: EmrElementController
 * @Describe:  电子病历元素管理控制层
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/9/18 16:20
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/emr/emrElement")
@Slf4j
public class EmrElementController extends BaseController {

  /**
   * 电子病历元素管理
   */
  @Resource
  private EmrElementServcie emrElementServcie_consumer;

  /**
  * @Menthod getById
  * @Desrciption 根据主键id查询电子病历元素
  *
  * @Param
  * [emrElementDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/18 16:29
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.emr.emrelement.dto.EmrElementDTO>
  **/
  @GetMapping("/getByIdorCode")
  public WrapperResponse<EmrElementDTO> getByIdorCode(EmrElementDTO emrElementDTO, HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO sysUserDTO = getSession(req, res);
    emrElementDTO.setHospCode(sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("emrElementDTO", emrElementDTO);
    map.put("hospCode", sysUserDTO.getHospCode());
    return emrElementServcie_consumer.getByIdorCode(map);
  }

  /**
  * @Menthod queryAll
  * @Desrciption 查询所有电子病历元素
  *
  * @Param
  * [emrElementDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/18 16:29
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.emr.emrelement.dto.EmrElementDTO>>
  **/
  @GetMapping("/queryAll")
  public WrapperResponse<List<EmrElementDTO>> queryAll(EmrElementDTO emrElementDTO, HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO sysUserDTO = getSession(req, res);
    emrElementDTO.setHospCode(sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode", sysUserDTO.getHospCode());
    map.put("emrElementDTO", emrElementDTO);
    return this.emrElementServcie_consumer.queryAll(map);
  }

  /**
  * @Menthod insert
  * @Desrciption 新增电子病历元素
  *
  * @Param
  * [emrElementDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/18 16:28
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/save")
  public WrapperResponse<Boolean> save(@RequestBody EmrElementDTO emrElementDTO, HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO sysUserDTO = getSession(req, res);
    emrElementDTO.setHospCode(sysUserDTO.getHospCode());
    emrElementDTO.setCrteId(sysUserDTO.getId());
    emrElementDTO.setCrteName(sysUserDTO.getName());
    Map map = new HashMap();
    map.put("hospCode", sysUserDTO.getHospCode());
    map.put("emrElementDTO", emrElementDTO);
    return emrElementServcie_consumer.save(map);
  }

  /**
  * @Menthod getEmrElementTree
  * @Desrciption 获取电子病历元素树
  *
  * @Param
  * [emrElementDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/18 16:28
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
  **/
  @GetMapping("getEmrElementTree")
  public WrapperResponse<List<TreeMenuNode>> getEmrElementTree(EmrElementDTO emrElementDTO, HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO sysUserDTO = getSession(req, res);
    emrElementDTO.setHospCode(sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode", sysUserDTO.getHospCode());
    map.put("emrElementDTO", emrElementDTO);
    return emrElementServcie_consumer.getEmrElementTree(map);
  }

  /**
   * @Menthod getEmrElementTree
   * @Desrciption 获取电子病历元素树(医保使用)
   *
   * @Param
   * [emrElementDTO]
   *
   * @Author jiguang.liao
   * @Date   2022/1/04 10:18
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
   **/
  @GetMapping("getInsureEmrElementTree")
  public WrapperResponse<List<TreeMenuNode>> getInsureEmrElementTree(EmrElementDTO emrElementDTO, HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO sysUserDTO = getSession(req, res);
    emrElementDTO.setHospCode(sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode", sysUserDTO.getHospCode());
    map.put("emrElementDTO", emrElementDTO);
    return emrElementServcie_consumer.getInsureEmrElementTree(map);
  }

  /**
   * @Menthod getInsureDictEmrElementTree
   * @Desrciption 获取系统码表中电子病历元素树(医保使用)
   *
   * @Param
   * [emrElementDTO]
   *
   * @Author jiguang.liao
   * @Date   2022/1/04 10:18
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
   **/
  @GetMapping("getInsureDictEmrElementTree")
  public WrapperResponse<List<TreeMenuNode>> getInsureDictEmrElementTree(EmrElementDTO emrElementDTO, HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO sysUserDTO = getSession(req, res);
    emrElementDTO.setHospCode(sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode", sysUserDTO.getHospCode());
    map.put("emrElementDTO", emrElementDTO);
    return emrElementServcie_consumer.getInsureDictEmrElementTree(map);
  }

  /**
   * @Menthod queryInsureEmrElementMatchInfo
   * @Desrciption 获取元素匹配关系(医保使用)
   *
   * @Param
   * [emrElementMatchDO]
   *
   * @Author jiguang.liao
   * @Date   2022/1/04 10:18
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.EmrElementMatchDO>>
   **/
  @GetMapping("queryInsureEmrElementMatchInfo")
  public WrapperResponse<PageDTO> queryInsureEmrElementMatchInfo(EmrElementMatchDO emrElementMatchDO, HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO sysUserDTO = getSession(req, res);
    emrElementMatchDO.setHospCode(sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode", sysUserDTO.getHospCode());
    map.put("emrElementMatchDO", emrElementMatchDO);
    return WrapperResponse.success(emrElementServcie_consumer.queryInsureEmrElementMatchInfo(map));
  }


}
