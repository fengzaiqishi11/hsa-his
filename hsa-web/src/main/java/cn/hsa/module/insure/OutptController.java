package cn.hsa.module.insure;

import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.InsureIndividualBasicDTO;
import cn.hsa.module.insure.outpt.service.DzpzOutptService;
import cn.hsa.module.insure.outpt.service.OutptService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.MapUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure
 * @Class_name: OutptController
 * @Describe(描述):
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/11/17 19:46
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/insure/outpt")
public class OutptController extends BaseController {

    @Resource
    private OutptService outptService;

    @Resource
    private DzpzOutptService dzpzOutptService;

    /**
     * @Menthod getOutptVisitInfo
     * @Desrciption 获取个人信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/17 20:23
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    @GetMapping("/getOutptVisitInfo")
    public WrapperResponse getOutptVisitInfo(@RequestParam Map<String,Object> param, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        param.put("hospCode",sysUserDTO.getHospCode());
        param.put("crteId",sysUserDTO.getCrteId());
        param.put("crteName",sysUserDTO.getCrteName());
        param.put("code",sysUserDTO.getCode()); // 操作员编码
        String aac001 = String.valueOf(param.get("aac001"));
        String aac002 = String.valueOf(param.get("aac002"));
        String aac003 = String.valueOf(param.get("aac003"));
        String aac004 = String.valueOf(param.get("aac004"));
        String aka130 = String.valueOf(param.get("aka130"));
        String regCode = String.valueOf(param.get("regCode"));
        String bka006 = String.valueOf(param.get("bka006"));
        String bka895 = String.valueOf(param.get("bka895"));
        String bka896 = String.valueOf(param.get("bka896"));
        String cardIden = MapUtils.get(param,"cardIden");
        String aab001 = String.valueOf(param.get("aab001"));
        String psnCertType = String.valueOf(param.get("psnCertType"));
        String nationECResult = (String) param.get("nationECResult");//电子凭证返回参数
        InsureIndividualBasicDTO insureIndividualBasicDTO = new InsureIndividualBasicDTO();
        insureIndividualBasicDTO.setHospCode(sysUserDTO.getHospCode());
        insureIndividualBasicDTO.setCardIden(cardIden);
        insureIndividualBasicDTO.setPsnCertType(psnCertType);
        insureIndividualBasicDTO.setNationECResult(nationECResult);
        insureIndividualBasicDTO.setAka130(aka130);
        insureIndividualBasicDTO.setBka006(bka006);
        insureIndividualBasicDTO.setBka895(bka895);
        insureIndividualBasicDTO.setBka896(bka896);
        insureIndividualBasicDTO.setAaa027(regCode);
        insureIndividualBasicDTO.setInsureRegCode(regCode);
        insureIndividualBasicDTO.setIsRemote(null);
        insureIndividualBasicDTO.setUserCode(sysUserDTO.getCode());
        insureIndividualBasicDTO.setAab001(aab001);
        insureIndividualBasicDTO.setAac001(aac001);
        insureIndividualBasicDTO.setAac002(aac002);
        insureIndividualBasicDTO.setAac003(aac003);
        insureIndividualBasicDTO.setAac004(aac004);
        insureIndividualBasicDTO.setInsuplc_admdvs(String.valueOf(param.get("insuplc_admdvs")));
        param.put("insureIndividualBasicDTO",insureIndividualBasicDTO);
        return outptService.getOutptVisitInfo(param);
    }


    /**
     * @Menthod ecQuery
     * @Desrciption 获取个人信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/17 20:23
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    @PostMapping("/ecQuery")
    public WrapperResponse ecQuery(@RequestBody Map<String, Object> param, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        param.put("hospCode",sysUserDTO.getHospCode());
        param.put("userId",sysUserDTO.getId());
        param.put("userName", sysUserDTO.getName());
        param.put("loginDeptId",sysUserDTO.getLoginBaseDeptDTO().getId());
        param.put("loginDeptName",sysUserDTO.getLoginBaseDeptDTO().getName());
        return dzpzOutptService.ecQuery(param);
    }

    /**
     * @Menthod hosQueryInsu
     * @Desrciption 获取个人信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/17 20:23
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    @PostMapping("/hosQueryInsu")
    public WrapperResponse hosQueryInsu(@RequestBody Map<String, Object> param, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        param.put("hospCode",sysUserDTO.getHospCode());
        param.put("loginDeptId",sysUserDTO.getLoginBaseDeptDTO().getId());
        param.put("loginDeptName",sysUserDTO.getLoginBaseDeptDTO().getName());
        return dzpzOutptService.hosQueryInsu(param);
    }

    /**
     * @Menthod backFee
     * @Desrciption 获取个人信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/17 20:23
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    @PostMapping("/backFee")
    public WrapperResponse backFee(@RequestBody Map<String, Object> param, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        param.put("hospCode",sysUserDTO.getHospCode());
        return dzpzOutptService.backFee(param);
    }

    /**
     * @Menthod backFee
     * @Desrciption 获取个人信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/17 20:23
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    @PostMapping("/saveUpLoadFeeResult")
    public WrapperResponse saveUpLoadFeeResult(@RequestParam Map<String, Object> param, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        param.put("hospCode",sysUserDTO.getHospCode());
        return dzpzOutptService.saveUpLoadFeeResult(param);
    }

}
