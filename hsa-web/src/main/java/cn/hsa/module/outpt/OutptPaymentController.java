package cn.hsa.module.outpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.NoRepeatSubmit;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.fees.dto.OutptPayDTO;
import cn.hsa.module.outpt.fees.dto.OutptSettleDTO;
import cn.hsa.module.outpt.fees.entity.OutptPayDO;
import cn.hsa.module.outpt.fees.service.OutptOutTmakePriceFormService;
import cn.hsa.module.outpt.fees.service.OutptTmakePriceFormService;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.payment.service.OutptPaymentService;
import cn.hsa.module.payment.service.PaymentSettleService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
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
 * @Class_name: OutptPaymentController
 * @Describe(描述): 诊间支付Controller
 * @Author: liuliyun
 * @Eamil: liyun.liu@powersi.com
 * @Date: 2022/08/30 19:13
 * @Company: CopyRight@2014 POWERSI Inc.All Rights ReserverdupdateOutptOPharInfo
 */
@RestController
@RequestMapping("/web/fee/OutptPayment")
public class OutptPaymentController extends BaseController {

    @Resource
    private OutptPaymentService outptPaymentService_consumer;

    @Resource
    private OutptTmakePriceFormService outptTmakePriceFormService_consumer;

    @Resource
    private OutptOutTmakePriceFormService outptOutTmakePriceFormService_consumer;

    @Resource
    private PaymentSettleService paymentSettleService_consumer;

    /**@Method checkOutptPhonePayAuthority
     * @Author liuliyun
     * @Description 检查诊间支付被扫权限
     * @Date 2022/08/30 19:34
     * @Param [map]
     * @return WrapperResponse<Boolean>
     **/
    @PostMapping("/checkOutptPhonePayAuthority")
    public WrapperResponse<Boolean> checkOutptPhonePayAuthority(@RequestBody Map<String,Object> params,HttpServletRequest req, HttpServletResponse res) {
        OutptVisitDTO outptVisitDTO = JSON.parseObject (JSON.toJSONString(params.get("outptVisit")),OutptVisitDTO.class) ;//获取个人信息
        OutptSettleDTO outptSettleDTO = JSON.parseObject (JSON.toJSONString(params.get("outptSettle")),OutptSettleDTO.class);//获取结算信息
        List<OutptPayDO> outptPayDOList = JSONArray.parseArray(JSON.toJSONString(params.get("outptPay")), OutptPayDO.class);//支付方式信息
        //判断参数是否正确（费用信息、就诊id、结算id）
        if (outptSettleDTO == null || outptVisitDTO == null
                || outptVisitDTO.getOutptCostDTOList() == null || outptVisitDTO.getOutptCostDTOList().isEmpty()
                || StringUtils.isEmpty(outptVisitDTO.getId()) || StringUtils.isEmpty(outptSettleDTO.getId())
            /*|| outptPayDOList == null || outptPayDOList.isEmpty()*/){
            return WrapperResponse.fail("参数错误。",null);
        }
        SysUserDTO userDTO = getSession(req, res) ;
        outptVisitDTO.setHospCode(userDTO.getHospCode());//医院编码
        outptVisitDTO.setHospName(userDTO.getHospName());//医院名称
        outptVisitDTO.setCrteId(userDTO.getId());//当前用户id
        outptVisitDTO.setCrteName(userDTO.getName());
        outptVisitDTO.setCode(userDTO.getCode()); // 操作员编码
        outptVisitDTO.setDeptId(userDTO.getLoginBaseDeptDTO().getId() );//当前登录用户操作科室ID
        outptSettleDTO.setHospCode(userDTO.getHospCode());//医院编码
        Map param = new HashMap();
        param.put("hospCode",userDTO.getHospCode());
        param.put("outptVisitDTO",outptVisitDTO);//(个人信息、费用信息)
        param.put("outptSettleDTO",outptSettleDTO);//结算信息
        param.put("outptPayDOList",outptPayDOList);//支付信息
        return outptPaymentService_consumer.checkOutptPhonePayAuthority(param);
    }

    /**@Method checkOutptPrescriptionPayAuthority
     * @Author liuliyun
     * @Description 检查诊间支付主扫权限
     * @Date 2022/08/30 19:34
     * @Param [map]
     * @return WrapperResponse<Boolean>
     **/
    @GetMapping("/checkOutptPrescriptionPayAuthority")
    public WrapperResponse<Boolean> checkOutptPrescriptionPayAuthority(HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        String systemCode = sysUserDTO.getSystemCode();
        Map param =new HashMap();
        param.put("hospCode",sysUserDTO.getHospCode());
        return WrapperResponse.success(outptPaymentService_consumer.checkOutptPrescriptionPayAuthority(param));
    }


    /**
     * @Menthod saveOutptPaymentSettle
     * @Desrciption  门诊诊间支付结算
     * @param params  请求参数
     * @Author liuliyun
     * @Date 2022/09/02 11:52
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @NoRepeatSubmit
    @PostMapping(value = "/saveOutptPaymentSettle")
    public WrapperResponse saveOutptPaymentSettle(@RequestBody Map<String,Object> params, HttpServletRequest req, HttpServletResponse res) {
        OutptVisitDTO outptVisitDTO = JSON.parseObject (JSON.toJSONString(params.get("outptVisit")),OutptVisitDTO.class) ;//获取个人信息
        OutptSettleDTO outptSettleDTO = JSON.parseObject (JSON.toJSONString(params.get("outptSettle")),OutptSettleDTO.class);//获取结算信息
        outptSettleDTO.setIsInvoice((Boolean) params.get("isInvoice"));//是否打印发票
        List<OutptPayDO> outptPayDOList = JSONArray.parseArray(JSON.toJSONString(params.get("outptPay")), OutptPayDO.class);//支付方式信息
        //判断参数是否正确（费用信息、就诊id、结算id）
        if (outptSettleDTO == null || outptVisitDTO == null
                || outptVisitDTO.getOutptCostDTOList() == null || outptVisitDTO.getOutptCostDTOList().isEmpty()
                || StringUtils.isEmpty(outptVisitDTO.getId()) || StringUtils.isEmpty(outptSettleDTO.getId())
            /*|| outptPayDOList == null || outptPayDOList.isEmpty()*/){
            return WrapperResponse.fail("参数错误。",null);
        }
        SysUserDTO userDTO = getSession(req, res) ;
        outptVisitDTO.setHospCode(userDTO.getHospCode());//医院编码
        outptVisitDTO.setHospName(userDTO.getHospName());//医院名称
        outptVisitDTO.setCrteId(userDTO.getId());//当前用户id
        outptVisitDTO.setCrteName(userDTO.getName());
        outptVisitDTO.setCode(userDTO.getCode()); // 操作员编码
        outptVisitDTO.setDeptId(userDTO.getLoginBaseDeptDTO().getId() );//当前登录用户操作科室ID
        outptSettleDTO.setHospCode(userDTO.getHospCode());//医院编码
        Map param = new HashMap();
        param.put("hospCode",userDTO.getHospCode());
        param.put("outptVisitDTO",outptVisitDTO);//(个人信息、费用信息)
        param.put("outptSettleDTO",outptSettleDTO);//结算信息
        param.put("outptPayDOList",outptPayDOList);//支付信息
        return outptTmakePriceFormService_consumer.saveOutptPaymentSettleInvoice(param);
    }

    /**
     * @Menthod updateOutptOnlinePayOutFee
     * @Desrciption 门诊诊间支付退费
     * @param param
     * @Author liuliyun
     * @Date 2022/09/19 10:15
     * @email liyun.liu@powersi.com
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @NoRepeatSubmit
    @PutMapping(value = "/updateOutptOnlinePayOutFee")
    public WrapperResponse updateOutptOnlinePayOutFee(@RequestBody Map<String,Object> param,HttpServletRequest req, HttpServletResponse res) {
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
        return outptOutTmakePriceFormService_consumer.updateOutptOnlinePayOutFee(params);
    }

    /**@Method queryPaymentBill
     * @Desrciption 诊间支付结算对总账数据查询
     * @param paraMap
     * @Author liuliyun
     * @Date   2022-09-15 10:41
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/queryPaymentBill")
    public WrapperResponse<PageDTO> queryPaymentBill(@RequestParam Map<String,Object> paraMap, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        paraMap.put("hospCode",sysUserDTO.getHospCode());
        return paymentSettleService_consumer.queryByPage(paraMap);
    }

    /**@Method updatePaymentBill
     * @Desrciption 诊间支付对总账
     * @param paraMap
     * @Author liuliyun
     * @Date   2022-09-16 10:03
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/updatePaymentBill")
    public WrapperResponse<Map<String,Object>> updatePaymentBill(@RequestParam Map<String,Object> paraMap, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        paraMap.put("hospCode",sysUserDTO.getHospCode());
        return outptPaymentService_consumer.updatePaymentBill(paraMap);
    }

}
