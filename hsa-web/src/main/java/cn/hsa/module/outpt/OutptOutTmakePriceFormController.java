package cn.hsa.module.outpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.NoRepeatSubmit;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.fees.dto.OutptPayDTO;
import cn.hsa.module.outpt.fees.dto.OutptSettleDTO;
import cn.hsa.module.outpt.fees.service.OutptOutTmakePriceFormService;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.ListUtils;
import cn.hsa.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt
 * @Class_name: OutptOutTmakePriceFormController
 * @Describe(描述): 退费管理Controller
 * @Author: liaojiguang
 * @Eamil: jiguang.liao@powersi.com.cn
 * @Date: 2020/09/08 14:30
 * @Company: CopyRight@2014 POWERSI Inc.All Rights ReserverdupdateOutptOPharInfo
 */
@RestController
@RequestMapping("/web/fee/OutptOutTmakePriceForm")
public class OutptOutTmakePriceFormController extends BaseController {

    @Resource
    private OutptOutTmakePriceFormService outptOutTmakePriceFormService_consumer;

    /**
     * @Menthod queryOutChargePage
     * @Desrciption 分页查询门诊已结算患者信息
     * @param outptSettleDTO 查询条件
     * @Author liaojiguang
     * @Date 2020/9/09 13:34
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping(value = "/queryOutChargePage")
    public WrapperResponse queryOutChargePage(OutptSettleDTO outptSettleDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptSettleDTO.setHospCode(userDTO.getHospCode());//医院编码
        Map param = new HashMap();
        param.put("hospCode",userDTO.getHospCode());
        param.put("outptSettleDTO",outptSettleDTO);
        return outptOutTmakePriceFormService_consumer.queryOutChargePage(param);
    }

    /**
     * @Menthod queryOutptPrescribes
     * @Desrciption 查询门诊处方详细信息
     * @param outptSettleDTO 查询条件
     * @Author liaojiguang
     * @Date 2020/9/09 13:34
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping(value = "/queryOutptPrescribes")
    public WrapperResponse queryOutptPrescribes(OutptSettleDTO outptSettleDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptSettleDTO.setHospCode(userDTO.getHospCode());//医院编码
        Map param = new HashMap();
        if (!StringUtils.isEmpty(outptSettleDTO.getRedId())) {
            outptSettleDTO.setId(outptSettleDTO.getRedId());
        }
        param.put("hospCode",userDTO.getHospCode());
        param.put("outptSettleDTO",outptSettleDTO);
        return outptOutTmakePriceFormService_consumer.queryOutptPrescribes(param);
    }

    /**
     * @Menthod updateOutptOutFee
     * @Desrciption 门诊退费
     * @param param
     * @Author liaojiguang
     * @Date 2020/9/09 13:34
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @NoRepeatSubmit
    @PutMapping(value = "/updateOutptOutFee")
    public WrapperResponse updateOutptOutFee(@RequestBody Map<String,Object> param,HttpServletRequest req, HttpServletResponse res) {
        List<OutptCostDTO> outptCostDTOList = JSON.parseArray(JSON.toJSONString(param.get("outptCostDTOList")),OutptCostDTO.class);
        OutptVisitDTO outptVisitDTO = new OutptVisitDTO();
        outptVisitDTO.setOutptCostDTOList(outptCostDTOList);

        List<OutptPayDTO> outptPayDOList = JSONArray.parseArray(JSON.toJSONString(param.get("outptPayDTO")),OutptPayDTO.class);
        OutptSettleDTO outptSettleDTO = JSON.parseObject(JSON.toJSONString(param.get("outptSettleDTO")),OutptSettleDTO.class);

        SysUserDTO userDTO = getSession(req, res) ;
        //医院编码
        outptSettleDTO.setHospCode(userDTO.getHospCode());
        outptVisitDTO.setHospCode(userDTO.getHospCode());
        outptVisitDTO.setHospName(userDTO.getHospName());
        outptVisitDTO.setCrteId(userDTO.getId());//当前用户id
        outptVisitDTO.setCrteName(userDTO.getName()); // 当前用户名
        outptVisitDTO.setDeptId(userDTO.getLoginBaseDeptDTO() == null?null :
                userDTO.getLoginBaseDeptDTO().getId());//当前登录用户操作科室ID
        outptVisitDTO.setCode(userDTO.getCode());
        outptVisitDTO.setId(outptSettleDTO.getVisitId());

        Map params = new HashMap();
        params.put("hospCode",userDTO.getHospCode());
        params.put("outptSettleDTO",outptSettleDTO);
        params.put("outptVisitDTO",outptVisitDTO);
        params.put("outptPayDOList",outptPayDOList);
        return outptOutTmakePriceFormService_consumer.updateOutptOutFee(params);
    }

    /**
     * @Menthod queryOutptPrescribe
     * @Desrciption 查询门诊处方类别
     * @param outptSettleDTO 查询条件
     * @Author liaojiguang
     * @Date 2020/9/09 13:34
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping(value = "/queryOutptPrescribe")
    public WrapperResponse queryOutptPrescribe(OutptSettleDTO outptSettleDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptSettleDTO.setHospCode(userDTO.getHospCode());//医院编码
        if (!StringUtils.isEmpty(outptSettleDTO.getRedId())) {
            outptSettleDTO.setId(outptSettleDTO.getRedId());
        }
        Map param = new HashMap();
        param.put("hospCode",userDTO.getHospCode());
        param.put("outptSettleDTO",outptSettleDTO);
        return outptOutTmakePriceFormService_consumer.queryOutptPrescribe(param);
    }

    /**
     * @Menthod getDiagnoseInfo
     * @Desrciption 查询门诊诊断信息
     * @param outptSettleDTO 查询条件
     * @Author liaojiguang
     * @Date 2020/10/21 13:34
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping(value = "/getDiagnoseInfo")
    public WrapperResponse getDiagnoseInfo(OutptSettleDTO outptSettleDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptSettleDTO.setHospCode(userDTO.getHospCode());//医院编码
        Map param = new HashMap();
        param.put("hospCode",userDTO.getHospCode());
        param.put("outptSettleDTO",outptSettleDTO);
        return outptOutTmakePriceFormService_consumer.getDiagnoseInfo(param);
    }

    /**
     * @Menthod getInvoiceInfo
     * @Desrciption 获取发票信息
     * @param outptSettleDTO 查询条件
     * @Author liaojiguang
     * @Date 2020/10/21 13:34
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping(value = "/getInvoiceInfo")
    public WrapperResponse getInvoiceInfo(OutptSettleDTO outptSettleDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptSettleDTO.setHospCode(userDTO.getHospCode());//医院编码
        Map param = new HashMap();
        param.put("hospCode",userDTO.getHospCode());
        param.put("outptSettleDTO",outptSettleDTO);
        return outptOutTmakePriceFormService_consumer.getInvoiceInfo(param);
    }

    /**
     * @Menthod updateOutptOPharInfo
     * @Desrciption 门诊退费 - 判断是否已经完成发药或退药
     * @param param
     * @Author liaojiguang
     * @Date 2020/9/09 13:34
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @NoRepeatSubmit
    @PutMapping(value = "/updateOutptOPharInfo")
    public WrapperResponse updateOutptOPharInfo(@RequestBody Map<String,Object> param,HttpServletRequest req, HttpServletResponse res) {
        List<OutptCostDTO> outptCostDTOList = JSON.parseArray(JSON.toJSONString(param.get("outptCostDTOList")),OutptCostDTO.class);
        if (ListUtils.isEmpty(outptCostDTOList)) {
            throw new AppException("未获取到费用项目信息");
        }
        OutptVisitDTO outptVisitDTO = new OutptVisitDTO();
        outptVisitDTO.setOutptCostDTOList(outptCostDTOList);
        OutptSettleDTO outptSettleDTO = JSON.parseObject(JSON.toJSONString(param.get("outptSettleDTO")),OutptSettleDTO.class);
        SysUserDTO userDTO = getSession(req, res) ;
        //医院编码
        outptSettleDTO.setHospCode(userDTO.getHospCode());
        outptVisitDTO.setHospCode(userDTO.getHospCode());
        outptVisitDTO.setCrteId(userDTO.getId());//当前用户id
        outptVisitDTO.setCrteName(userDTO.getName()); // 当前用户名
        outptVisitDTO.setDeptId(userDTO.getLoginBaseDeptDTO() ==null ? null:
                userDTO.getLoginBaseDeptDTO().getId() );//当前登录用户操作科室ID
        outptVisitDTO.setId(outptSettleDTO.getVisitId());

        Map params = new HashMap();
        params.put("hospCode",userDTO.getHospCode());
        params.put("outptSettleDTO",outptSettleDTO);
        params.put("outptVisitDTO",outptVisitDTO);
        return outptOutTmakePriceFormService_consumer.updateOutptOPharInfo(params);
    }

    /**
     * @Method updateOutptRegister
     * @Desrciption  医保统一支付平台：门诊挂号取消
     * @Param
     *
     * @Author fuhui
     * @Date   2021/5/8 8:37
     * @Return
    **/
    @PostMapping("/updateOutptRegister")
    public WrapperResponse<Boolean> updateOutptRegister(@RequestBody  Map<String,Object> map,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        map.put("crteName",userDTO.getCrteName());
        map.put("crteId",userDTO.getCrteId());
        map.put("hospCode",userDTO.getHospCode());
        return outptOutTmakePriceFormService_consumer.updateOutptRegister(map);
    }

    /**
     * @Method updateOutptRegister
     * @Desrciption  医保统一支付平台：门急诊诊疗记录【4301】
     * @Param id-就诊id
     *
     * @Author luoyong
     * @Date   2021/8/20 8:37
     * @Return
     **/
    @PostMapping("/addOutptVisitRecordUpload")
    public WrapperResponse<Boolean> addOutptVisitRecordUpload(@RequestBody  Map<String,Object> map,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        map.put("hospCode",userDTO.getHospCode());
        return outptOutTmakePriceFormService_consumer.addOutptVisitRecordUpload(map);
    }


    /**
     * @Menthod: addOperAndRescue
     * @Desrciption: 统一支付平台-急诊留观手术及抢救信息【4302】
     * @Param: visitId-就诊id
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-08-23 13:50
     * @Return:
     **/
    @PostMapping("/addOperAndRescue")
    public WrapperResponse<Boolean> addOperAndRescue(@RequestBody Map<String, Object> map, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode", sysUserDTO.getHospCode());
        return outptOutTmakePriceFormService_consumer.addOperAndRescue(map);
    }
}
