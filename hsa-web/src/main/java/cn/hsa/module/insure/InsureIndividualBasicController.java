package cn.hsa.module.insure;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.insure.emd.service.InptElectronicBillService;
import cn.hsa.module.insure.module.dto.InsureIndividualBasicDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.service.InsureIndividualBasicService;
import cn.hsa.module.insure.outpt.service.OutptService;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure
 * @Class_name:: InsureIndividualBasicController
 * @Description:  医保个人信息控制层
 * @Author: fuhui
 * @Date: 2020/11/4 20:09
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/insure/insureIndividualBasic")
@Slf4j
public class InsureIndividualBasicController extends BaseController {
    /**
     * 医保个人信息服务层接口
     */
    @Resource
    private InsureIndividualBasicService insureIndividualBasicService_consumer;

    @Resource
    private OutptService outptService;

    @Resource
    private SysParameterService sysParameterService_consumer;

    @Resource
    private InptElectronicBillService inptElectronicBillService_consumer;

    /**
     * @Method queryPage
     * @Desrciption  分页查询医保个人信息
     * @Param basicDTO 医保个人信息数据传输对象
     * @Author fuhui
     * @Date   2020/11/4 20:36
     * @Return 分页的医保个人信息数据传输对象
    **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(InsureIndividualBasicDTO basicDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        basicDTO.setHospCode(sysUserDTO.getHospCode());
        Map map =new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("basicDTO",basicDTO);
        return insureIndividualBasicService_consumer.queryPage(map);
    }

    /**
     * @Method getById()
     * @Desrciption  根据id查询医保个人信息
     * @Param basicDTO 医保个人信息数据传输对象
     *
     * @Author fuhui
     * @Date   2020/11/4 20:36
     * @Return 医保个人信息数据传输对象
     **/
    @GetMapping("/getById")
    public WrapperResponse<InsureIndividualBasicDTO> getById(InsureIndividualBasicDTO basicDTO, HttpServletRequest req, HttpServletResponse res){
        if(StringUtils.isEmpty(basicDTO.getId())){
            throw new AppException("查询医保个人Id参数为空");
        }
        SysUserDTO sysUserDTO = getSession(req, res);
        basicDTO.setHospCode(sysUserDTO.getHospCode());
        Map map =new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("basicDTO",basicDTO);
        return insureIndividualBasicService_consumer.getById(map);
    }

    /**
     * @Method getInsurePatientInfo()
     * @Desrciption  查询医保住院信息
     * @Param basicDTO 医保个人信息数据传输对象
     *
     * @Author fuhui
     * @Date   2020/11/4 20:36
     * @Return 医保个人信息数据传输对象
     **/
    @GetMapping("/getInsurePatientInfo")
    public WrapperResponse<Map<String,Object>> getInsurePatientInfo(@RequestParam Map<String,Object> param, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
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
        String patientCode = String.valueOf(param.get("patientCode"));
        String psnCertType = String.valueOf(param.get("psnCertType"));
        String nationECResult = (String) param.get("nationECResult");//电子凭证返回参数
        if (StringUtils.isEmpty(patientCode)) {
            throw new AppException("请选择病人类型");
        }

        if (Constants.BRLX.PTBR.equals(patientCode)) {
            throw new AppException("普通病人无需登记");
        }

        String isRemote = Constants.SF.F;
        if (Constants.BRLX.SNYDBR.equals(patientCode) || Constants.BRLX.SWYDBR.equals(patientCode)) {
            isRemote = Constants.SF.S;
        }

        if (StringUtils.isEmpty(regCode) || StringUtils.isEmpty(bka895) || StringUtils.isEmpty(bka896)) {
            throw new AppException("参数错误");
        }

        Map map =new HashMap();
        InsureIndividualBasicDTO insureIndividualBasicDTO = new InsureIndividualBasicDTO();
        map.put("hospCode",sysUserDTO.getHospCode());
        insureIndividualBasicDTO.setHospCode(sysUserDTO.getHospCode());
        insureIndividualBasicDTO.setAac001(aac001);
        insureIndividualBasicDTO.setAac002(aac002);
        insureIndividualBasicDTO.setAac003(aac003);
        insureIndividualBasicDTO.setAac004(aac004);
        insureIndividualBasicDTO.setAka130(aka130);
        insureIndividualBasicDTO.setBka006(bka006);
        insureIndividualBasicDTO.setAab001(aab001);
        insureIndividualBasicDTO.setBka895(bka895);
        insureIndividualBasicDTO.setBka896(bka896);
        insureIndividualBasicDTO.setAaa027(regCode);
        insureIndividualBasicDTO.setInsureRegCode(regCode);
        insureIndividualBasicDTO.setIsRemote(isRemote);
        insureIndividualBasicDTO.setUserCode(sysUserDTO.getCode());
        insureIndividualBasicDTO.setCardIden(cardIden);
        insureIndividualBasicDTO.setInsuplc_admdvs(String.valueOf(param.get("insuplc_admdvs")));
        insureIndividualBasicDTO.setPsnCertType(psnCertType);
        insureIndividualBasicDTO.setNationECResult(nationECResult);
        map.put("insureRegCode",regCode);
        map.put("insureIndividualBasicDTO",insureIndividualBasicDTO);

        // 查询医院医保配置（直接走医保还是走统一支付平台）
        Map<String, Object> tempMap = new HashMap<>();
        tempMap.put("hospCode", sysUserDTO.getHospCode());
        tempMap.put("code", "UNIFIED_PAY");
        SysParameterDTO sys = sysParameterService_consumer.getParameterByCode(tempMap).getData();
        if ("qrcode".equals(bka895)){
            if (StringUtils.isEmpty(nationECResult)){
                throw new AppException("使用电子凭证需要扫码进行身份确认。");
            }
            //电子凭证
            return inptElectronicBillService_consumer.getPatientInfo(map);
        } else if (sys != null && sys.getValue().equals("1")) {
            //调用统一支付平台平台
            return outptService.getOutptVisitInfo(map);
        }else{
            return insureIndividualBasicService_consumer.getPatientsInfoByInpt(map);
        }

    }

    @GetMapping("/getByVisitId")
    public WrapperResponse<InsureIndividualBasicDTO> getByVisitId(InsureIndividualBasicDTO insureIndividualBasicDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureIndividualBasicDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureIndividualBasicDTO",insureIndividualBasicDTO);
        return insureIndividualBasicService_consumer.getByVisitId(map);
    }

    /**
     * @Method queryInsurePatientPage
     * @Desrciption  分页查询医保病人的登记注册信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/5/17 14:14
     * @Return
    **/
    @GetMapping("/queryInsurePatientPage")
    public WrapperResponse<PageDTO> queryInsurePatientPage(InsureIndividualVisitDTO insureIndividualVisitDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode" ,sysUserDTO.getHospCode());
        insureIndividualVisitDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
        return insureIndividualBasicService_consumer.queryInsurePatientPage(map);
    }

    /**
     * @Method queryInptAndOutptPatientPage
     * @Desrciption  查询门诊或者住院病人的登记信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/5/25 14:41
     * @Return
    **/
    @GetMapping("/queryInptAndOutptPatientPage")
    public WrapperResponse<PageDTO> queryInptAndOutptPatientPage(@RequestParam  Map<String,Object>map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        return insureIndividualBasicService_consumer.queryInptAndOutptPatientPage(map);
    }

    /**
     * @Method queryInsureInfo
     * @Desrciption  通过入院或者门诊登记的个人信息 用身份证或者调用医保接口获取个人编号 通过个人编好调用医保查询接口
     * @Param
     *
     * @Author fuhui
     * @Date   2021/5/25 15:05
     * @Return
    **/
    public WrapperResponse<Map<String,Object>> v(Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        return insureIndividualBasicService_consumer.queryInsureInfo(map);
    }
}
