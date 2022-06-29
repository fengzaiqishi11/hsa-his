package cn.hsa.module.insure.other;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.drgdip.dto.DrgDipAuthDTO;
import cn.hsa.module.insure.drgdip.dto.DrgDipResultDTO;
import cn.hsa.module.insure.drgdip.service.DrgDipResultService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 质控结果查询
 * @Author: lhm
 * @Date: 2022-05-06 17:31
 */
@RestController
@RequestMapping("/web/insure/drgDipResult")
@Slf4j
public class drgDipResultController extends BaseController {

  @Resource
  private DrgDipResultService drgDipResultService_consumer;

  /**
   * 质控权限查询
   * @param
   * @param req
   * @param res
   * @Author lhm
   * @Date 2022-05-07 9:10
   * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
   */
  @GetMapping("/checkDrgDipBizAuthorization")
  public WrapperResponse<Map<String,Object>> checkDrgDipBizAuthorization(DrgDipResultDTO drgDipResultDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map<String,Object> map = new HashMap<>();
    drgDipResultDTO.setHospCode(sysUserDTO.getHospCode());
    map.put("hospCode", sysUserDTO.getHospCode());
    map.put("drgDipResultDTO",drgDipResultDTO);
    DrgDipAuthDTO drgDipAuthDTO = drgDipResultService_consumer.checkDrgDipBizAuthorization(map).getData();
    Boolean drg ;
    Boolean dip ;
    if("true".equals(drgDipAuthDTO.getDrg())){
      drg = true;
    }else {
      drg = false;
    }
    if("true".equals(drgDipAuthDTO.getDip())){
      dip = true;
    }else {
      dip = false;
    }
    Map<String,Object> drgDipMap = new HashMap<>();
    drgDipMap.put("drg",drg);
    drgDipMap.put("dip",dip);
    return WrapperResponse.success(drgDipMap);
  }

  /**
   * 质控结果查询-结算清单
   * @param
   * @param req
   * @param res
   * @Author lhm
   * @Date 2022-05-07 9:10
   * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
   */
  @PostMapping("/queryDrgDipResultSetlinfo")
  public WrapperResponse<PageDTO> queryDrgDipResultSetlinfo(@RequestBody DrgDipResultDTO drgDipResultDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map<String,Object> map = new HashMap<>();
    drgDipResultDTO.setHospCode(sysUserDTO.getHospCode());
    map.put("hospCode", sysUserDTO.getHospCode());
    map.put("drgDipResultDTO",drgDipResultDTO);
    return drgDipResultService_consumer.queryDrgDipResultSetlinfo(map);
  }

  /**
   * 质控结果查询汇总-结算清单
   * @param
   * @param req
   * @param res
   * @Author lhm
   * @Date 2022-05-07 9:10
   * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
   */
  @PostMapping("/queryDrgDipResultSetlinfoSum")
  public WrapperResponse<Map<String, Object>> queryDrgDipResultSetlinfoSum(@RequestBody DrgDipResultDTO drgDipResultDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map<String,Object> map = new HashMap<>();
    drgDipResultDTO.setHospCode(sysUserDTO.getHospCode());
    map.put("hospCode", sysUserDTO.getHospCode());
    map.put("drgDipResultDTO",drgDipResultDTO);
    return drgDipResultService_consumer.queryDrgDipResultSetlinfoSum(map);
  }

  /**
   * 质控结果查询-详细
   * @param
   * @param req
   * @param res
   * @Author lhm
   * @Date 2022-05-07 9:10
   * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
   */
  @GetMapping("/queryDrgDipResultDetail")
  public WrapperResponse<Map<String, Object>> queryDrgDipResultDetail(DrgDipResultDTO drgDipResultDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map<String,Object> map = new HashMap<>();
    drgDipResultDTO.setHospCode(sysUserDTO.getHospCode());
    map.put("hospCode", sysUserDTO.getHospCode());
    map.put("drgDipResultDTO",drgDipResultDTO);
    return drgDipResultService_consumer.queryDrgDipResultDetail(map);
  }

  /**
   * 质控结果查询-病案首页
   * @param
   * @param req
   * @param res
   * @Author lhm
   * @Date 2022-05-07 9:10
   * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
   */
  @PostMapping("/queryDrgDipResultMris")
  public WrapperResponse<PageDTO> queryDrgDipResultMris(@RequestBody DrgDipResultDTO drgDipResultDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map<String,Object> map = new HashMap<>();
    map.put("hospCode", sysUserDTO.getHospCode());
    drgDipResultDTO.setHospCode(sysUserDTO.getHospCode());
    map.put("drgDipResultDTO",drgDipResultDTO);
    return drgDipResultService_consumer.queryDrgDipResultMris(map);
  }

  /**
   * 质控结果查询汇总-病案首页
   * @param
   * @param req
   * @param res
   * @Author lhm
   * @Date 2022-05-07 9:10
   * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
   */
  @PostMapping("/queryDrgDipResultMrisSum")
  public WrapperResponse<Map<String, Object>> queryDrgDipResultMrisSum(@RequestBody DrgDipResultDTO drgDipResultDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map<String,Object> map = new HashMap<>();
    drgDipResultDTO.setHospCode(sysUserDTO.getHospCode());
    map.put("hospCode", sysUserDTO.getHospCode());
    map.put("drgDipResultDTO",drgDipResultDTO);
    return drgDipResultService_consumer.queryDrgDipResultMrisSum(map);
  }

}
