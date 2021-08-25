package cn.hsa.module.outpt;

import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.outpt.outptclassify.dto.OutptClassifyCostDTO;
import cn.hsa.module.outpt.outptclassify.dto.OutptClassifyDTO;
import cn.hsa.module.outpt.queue.dto.OutptDoctorQueueDto;
import cn.hsa.module.outpt.register.dto.OutptRegisterDTO;
import cn.hsa.module.outpt.register.dto.OutptRegisterSettleDto;
import cn.hsa.module.outpt.register.service.OutptRegisterService;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Package_name: cn.hsa.module.outpt
 * @Class_name: OutptRegisterController
 * @Describe: 门诊挂号控制层
 * @Author: liaojiguang
 * @Eamil: liaojiguang@powersi.com.cn
 * @Date: 2020/8/11 11:40
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/outpt/outptRegister")
@Slf4j
public class OutptRegisterController extends BaseController {

  /**
   *  门诊挂号注入service对象
   */
  @Resource
  private OutptRegisterService outptRegisterService_consumer;

  /**
   * @Menthod queryRegisterInfoByParamsPage
   * @Desrciption 根据参数获取患者挂号信息分页展示
   * @Param
   * [outptRegisterDTO]
   *
   * @Author lioajiguang
   * @Date   2020/8/11 11:41
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.outpt.outptclassify.dto.OutptClassifyDTO>
   **/
  @GetMapping("/queryRegisterInfoByParamsPage")
  public WrapperResponse<OutptRegisterDTO> queryRegisterInfoByParamsPage(OutptRegisterDTO outptRegisterDTO,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    outptRegisterDTO.setHospCode(userDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",userDTO.getHospCode());
    map.put("outptRegisterDTO",outptRegisterDTO);
    return this.outptRegisterService_consumer.queryRegisterInfoByParamsPage(map);
  }

  /**
   * @Menthod updateOutRegister
   * @Desrciption 门诊退号
   * @Param [id] 挂号id
   * @Author lioajiguang
   * @Date   2020/8/11 11:41:23
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean> 成功/失败
   **/
  @PutMapping("/updateOutRegister")
  public WrapperResponse<Boolean> updateOutRegister(@RequestBody String id,HttpServletRequest req, HttpServletResponse res) {
    if (StringUtils.isEmpty(id)) {
      return WrapperResponse.error(WrapperResponse.FAIL,"参数错误!",null);
    }
    SysUserDTO userDTO = getSession(req, res) ;
    Map map = new HashMap();
    map.put("id",id);
    map.put("hospCode",userDTO.getHospCode());
    map.put("crteId",userDTO.getId());
    map.put("crteName",userDTO.getName());
    return this.outptRegisterService_consumer.updateOutRegister(map);
  }


  /**
   * @Method OutRegisterSettle
   * @Desrciption 挂号结算
   @params [outptVisitDTO, outptRegisterDTO, regDetailList, outptRegisterSettleDto]
    * @Author chenjun
   * @Date   2020/8/18 15:40
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
   **/
  @PostMapping("/outRegisterSettle")
  public WrapperResponse<Map<String, String>> saveOutRegisterSettle(@RequestBody String jsonstr,HttpServletRequest req, HttpServletResponse res) {
//    if (null == outptVisitDTO && null == outptRegisterDTO  && null == outptRegisterSettleDto && ListUtils.isEmpty(outptRegisterDTO.getRegDetailList())) {
//      return WrapperResponse.error(WrapperResponse.FAIL,"参数错误!",null);
//    }
    JSONObject jsonObject = JSON.parseObject(jsonstr);
    JSONObject   jsonstrObj = jsonObject.getJSONObject("jsonstr");
    JSONObject   outptVisitDTOJson= jsonstrObj.getJSONObject("outptVisitDTO");
    JSONObject   outptRegisterDTOJson= jsonstrObj.getJSONObject("outptRegisterDTO");
    JSONObject   outptRegisterSettleDTOJson= jsonstrObj.getJSONObject("outptRegisterSettleDTO");
    Boolean   isInvoice = jsonstrObj.getBoolean("isInvoice"); // 是否使用发票
    Boolean   SFJS = jsonstrObj.getBoolean("SFJS")  == null || jsonstrObj.getBoolean("SFJS");
    OutptVisitDTO outptVisitDTO = JSON.toJavaObject(outptVisitDTOJson, OutptVisitDTO.class);
    OutptRegisterDTO outptRegisterDTO = JSON.toJavaObject(outptRegisterDTOJson, OutptRegisterDTO.class);
    OutptRegisterSettleDto outptRegisterSettleDTO = JSON.toJavaObject(outptRegisterSettleDTOJson, OutptRegisterSettleDto.class);

    SysUserDTO userDTO = getSession(req, res) ;
    outptRegisterDTO.setAgeUnitCode(outptVisitDTO.getAgeUnitCode());
    outptVisitDTO.setCrteId(userDTO.getId());
    outptVisitDTO.setCrteName(userDTO.getName());
    Map map = new HashMap();
    map.put("outptVisitDTO",outptVisitDTO);
    map.put("outptRegisterDTO",outptRegisterDTO);
    map.put("outptRegisterSettleDto",outptRegisterSettleDTO);
    map.put("SFJS",SFJS);
    map.put("isInvoice",isInvoice);
    map.put("hospCode",userDTO.getHospCode());
    return this.outptRegisterService_consumer.saveOutRegisterSettle(map);
  }

  @GetMapping("/queryOutptDeptClassify")
  public WrapperResponse<List<BaseDeptDTO>> queryOutptDeptClassify(OutptClassifyDTO outptClassifyDTO,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    outptClassifyDTO.setHospCode(userDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode", userDTO.getHospCode());
    map.put("outptClassifyDTO", outptClassifyDTO);
    return this.outptRegisterService_consumer.queryOutptDeptClassify(map);
  }

  @GetMapping("/queryOutptDoctorList")
  public WrapperResponse<List<OutptDoctorQueueDto>> queryOutptDoctorList(OutptClassifyDTO outptClassifyDTO,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    outptClassifyDTO.setHospCode(userDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode", userDTO.getHospCode());
    map.put("outptClassifyDTO", outptClassifyDTO);
    return this.outptRegisterService_consumer.queryOutptDoctorList(map);
  }

  @GetMapping("/queryRegisterCostList")
  public WrapperResponse<List<OutptClassifyCostDTO>> queryRegisterCostList(OutptClassifyDTO outptClassifyDTO,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    outptClassifyDTO.setHospCode(userDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode", userDTO.getHospCode());
    map.put("outptClassifyDTO", outptClassifyDTO);
    return this.outptRegisterService_consumer.queryRegisterCostList(map);
  }

  @PostMapping("/updateCostPreferential")
  public WrapperResponse<List<OutptClassifyCostDTO>> updateCostPreferential(@RequestBody Map mapReq,HttpServletRequest req, HttpServletResponse res) {
//    if (null == outptVisitDTO && null == outptRegisterDTO  && null == outptRegisterSettleDto && ListUtils.isEmpty(outptRegisterDTO.getRegDetailList())) {
//      return WrapperResponse.error(WrapperResponse.FAIL,"参数错误!",null);
//    }
    Map map11 = MapUtils.get(mapReq, "outptVisitDTO");
    String jsonstr = MapUtils.get(mapReq, "jsonstr");
    String code = MapUtils.get(mapReq, "code");
    if(StringUtils.isEmpty(jsonstr) || StringUtils.isEmpty(code)){
      return WrapperResponse.success(null);
    }
    SysUserDTO userDTO = getSession(req, res) ;
    List<OutptClassifyCostDTO> costList = JSONArray.parseArray(jsonstr,OutptClassifyCostDTO.class);
    Map map = new HashMap();
    map.put("costList",costList);
    map.put("code",code);
    map.put("hospCode",userDTO.getHospCode());
    return this.outptRegisterService_consumer.updateCostPreferential(map);
  }

}
