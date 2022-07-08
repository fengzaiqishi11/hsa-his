package cn.hsa.module.inpt;

import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.inptprint.dto.InptAdvicePrintDTO;
import cn.hsa.module.inpt.inptprint.service.InptPrintService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.ListUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt
 * @Class_name: inptPrintController
 * @Describe:  住院打印控制层
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/10/27 19:29
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/inpt/inptPrint")
@Slf4j
public class inptPrintController extends BaseController {

  @Resource
  private InptPrintService inptPrintService_consumer;

  /**
  * @Menthod queryRegisteredPage
  * @Desrciption 费用清单打印查询
  *
  * @Param
  * [inptVisitDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/10/27 20:05
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.doctor.dto.InptCostDTO>>
  **/
  @GetMapping(value = "/queryRegisteredPage")
  public WrapperResponse<Map<String, List<InptCostDTO>>> queryRegisteredPage(InptCostDTO inptCostDTO, HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO sysUserDTO = getSession(req, res);
    inptCostDTO.setHospCode(sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode", sysUserDTO.getHospCode());
    map.put("inptCostDTO",inptCostDTO);
    return inptPrintService_consumer.queryInptCostListPrint(map);
  }

  //费用清单批量打印 author：luoyong date：2021-03-05
  @GetMapping(value = "/queryRegisteredPageBatch")
  public WrapperResponse<Map<String, List<InptCostDTO>>> queryRegisteredPageBatch(InptCostDTO inptCostDTO, HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO sysUserDTO = getSession(req, res);
    if (ListUtils.isEmpty(inptCostDTO.getVisitIds())) {
      throw new AppException("未选择需要打印的患者信息");
    }
    inptCostDTO.setHospCode(sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode", sysUserDTO.getHospCode());
    map.put("inptCostDTO",inptCostDTO);
    return inptPrintService_consumer.queryRegisteredPageBatch(map);
  }

  /**
  * @Menthod getApplyDetailsListPrint
  * @Desrciption 领药申请打印
  *
  * @Param
  * [code, flag]
  *
  * @Author jiahong.yang
  * @Date   2020/10/30 16:56
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
  **/
  @GetMapping(value = "/getApplyDetailsListPrint")
  public WrapperResponse<Map> getApplyDetailsListPrint(String code,String flag,String id,String ids, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    List<String> codeList = new ArrayList<>();
    codeList.add(code);
    Map map = new HashMap<>();
    map.put("flag",flag);
    map.put("hospCode", sysUserDTO.getHospCode());
    map.put("codeList", codeList);
    map.put("id", id);
    map.put("ids", ids);
    map.put("deptId", sysUserDTO.getLoginBaseDeptDTO().getId());
    WrapperResponse<Map> wrapperResponse = inptPrintService_consumer.getApplyDetailsListPrint(map);
    return wrapperResponse;
  }

  /**
  * @Menthod updateAdvicePrint
  * @Desrciption 更新打印表
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/1/15 9:54
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
  **/
  @GetMapping(value = "/queryAdvicePrint")
  public WrapperResponse<List<InptAdvicePrintDTO>> queryAdvicePrint(InptAdvicePrintDTO inptAdvicePrintDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map map = new HashMap();
    map.put("hospCode", sysUserDTO.getHospCode());
    map.put("inptAdvicePrintDTO",inptAdvicePrintDTO);
    inptAdvicePrintDTO.setHospCode(sysUserDTO.getHospCode());
    WrapperResponse<List<InptAdvicePrintDTO>> wrapperResponse = inptPrintService_consumer.queryAdvicePrint(map);
    return wrapperResponse;
  }

  /**
  * @Menthod updateAdvicePrintStatus
  * @Desrciption 回写医嘱打印状态表
  *
  * @Param
  * [inptAdvicePrintDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/1/18 10:42
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.inptprint.dto.InptAdvicePrintDTO>>
  **/
  @PostMapping(value = "/updateAdvicePrintStatus")
  public WrapperResponse<Boolean> updateAdvicePrintStatus(@RequestBody InptAdvicePrintDTO inptAdvicePrintDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map map = new HashMap();
    map.put("hospCode", sysUserDTO.getHospCode());
    map.put("inptAdvicePrintDTO",inptAdvicePrintDTO);
    inptAdvicePrintDTO.setHospCode(sysUserDTO.getHospCode());
    WrapperResponse<Boolean> wrapperResponse = inptPrintService_consumer.updateAdvicePrintStatus(map);
    return wrapperResponse;
  }

  @PostMapping(value = "/updateAdvicePrintResetStatus")
  public WrapperResponse<Boolean> updateAdvicePrintResetStatus(@RequestBody InptAdvicePrintDTO inptAdvicePrintDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map map = new HashMap();
    map.put("hospCode", sysUserDTO.getHospCode());
    map.put("inptAdvicePrintDTO",inptAdvicePrintDTO);
    inptAdvicePrintDTO.setHospCode(sysUserDTO.getHospCode());
    WrapperResponse<Boolean> wrapperResponse = inptPrintService_consumer.updateAdvicePrintResetStatus(map);
    return wrapperResponse;
  }

  /**
  * @Menthod updateResetPrint
  * @Desrciption 重置患者医嘱打印表
  *
  * @Param
  * [inptAdvicePrintDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/4/15 16:02
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping(value = "/updateResetPrint")
  public WrapperResponse<Boolean> updateResetPrint(@RequestBody InptAdvicePrintDTO inptAdvicePrintDTO, HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO sysUserDTO = getSession(req, res);
    Map map = new HashMap();
    map.put("hospCode", sysUserDTO.getHospCode());
    map.put("inptAdvicePrintDTO",inptAdvicePrintDTO);
    inptAdvicePrintDTO.setHospCode(sysUserDTO.getHospCode());
    WrapperResponse<Boolean> wrapperResponse = inptPrintService_consumer.updateResetPrint(map);
    return wrapperResponse;
  }

  /**
  * @Menthod saveAdvicePrint
  * @Desrciption 保存医嘱单打印格式
  *
  * @Param
  * [inptAdvicePrintDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/4/15 16:11
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping(value = "/saveAdvicePrint")
  public WrapperResponse<Boolean> saveAdvicePrint(@RequestBody InptAdvicePrintDTO inptAdvicePrintDTO, HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO sysUserDTO = getSession(req, res);
    Map map = new HashMap();
    map.put("hospCode", sysUserDTO.getHospCode());
    map.put("inptAdvicePrintDTO",inptAdvicePrintDTO);
    inptAdvicePrintDTO.setHospCode(sysUserDTO.getHospCode());
    WrapperResponse<Boolean> wrapperResponse = inptPrintService_consumer.saveAdvicePrint(map);
    return wrapperResponse;
  }
}
