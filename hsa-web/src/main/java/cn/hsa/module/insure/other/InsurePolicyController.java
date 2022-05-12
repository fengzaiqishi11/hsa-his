package cn.hsa.module.insure.other;

import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.dzpz.hainan.SetlInfoDTO;
import cn.hsa.module.insure.emr.dto.InsureEmrDscginfoDTO;
import cn.hsa.module.insure.other.dto.PolicyRequestDTO;
import cn.hsa.module.insure.other.service.InsurePolicyService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 医保其他借口-政策项查询
 * @Author: 医保开发二部-湛康
 * @Date: 2022-05-06 17:31
 */
@RestController
@RequestMapping("/web/insure/policy")
@Slf4j
public class InsurePolicyController  extends BaseController {

  @Resource
  private InsurePolicyService insurePolicyService;

  /**
   * 政策项查询 100001
   * @param setlInfoDTO
   * @param req
   * @param res
   * @Author 医保开发二部-湛康
   * @Date 2022-05-07 9:10
   * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
   */
  @PostMapping("/queryInsurePolicy")
  public WrapperResponse queryInsurePolicy(@RequestBody PolicyRequestDTO setlInfoDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map map = new HashMap();
    map.put("hospCode", sysUserDTO.getHospCode());
    map.put("policyRequestDTO",setlInfoDTO);
    return WrapperResponse.success(insurePolicyService.queryInsurePolicy(map));
  }
}
