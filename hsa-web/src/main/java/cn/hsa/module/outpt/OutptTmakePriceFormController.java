package cn.hsa.module.outpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.NoRepeatSubmit;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.deptDrug.dto.BaseDeptDrugStoreDTO;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.fees.dto.OutptSettleDTO;
import cn.hsa.module.outpt.fees.entity.OutptPayDO;
import cn.hsa.module.outpt.fees.service.OutptTmakePriceFormService;
import cn.hsa.module.outpt.outinInvoice.dto.OutinInvoiceDTO;
import cn.hsa.module.outpt.outinInvoice.entity.OutinInvoiceDO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Package_name: cn.hsa.module.outpt
 * @Class_name: OutptTmakePriceFormController
 * @Describe(描述): 划价收费Controller
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/08/24 11:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/fee/OutptTmakePriceForm")
public class OutptTmakePriceFormController extends BaseController {

    @Resource
    private OutptTmakePriceFormService outptTmakePriceFormService_consumer;

    /**
     * @Menthod queryOutptVisitPage
     * @Desrciption 分页查询门诊就诊表
     * @param outptVisitDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/24 13:53
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping(value = "/queryOutptVisitPage")
    public WrapperResponse queryOutptVisitPage(OutptVisitDTO outptVisitDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptVisitDTO.setHospCode(userDTO.getHospCode());//医院编码
        Map param = new HashMap();
        param.put("hospCode",userDTO.getHospCode());
        param.put("outptVisitDTO",outptVisitDTO);
        return outptTmakePriceFormService_consumer.queryOutptVisitPage(param);
    }

    /**
     * @Menthod queryOutptSettleVisitPage
     * @Desrciption 分页查询门诊已结算信息
     * @param outptVisitDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/12/7 23:17
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping(value = "/queryOutptSettleVisitPage")
    public WrapperResponse queryOutptSettleVisitPage(OutptVisitDTO outptVisitDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptVisitDTO.setHospCode(userDTO.getHospCode());//医院编码
        Map param = new HashMap();
        param.put("hospCode",userDTO.getHospCode());
        param.put("outptVisitDTO",outptVisitDTO);
        return outptTmakePriceFormService_consumer.queryOutptSettleVisitPage(param);
    }


    /**
     * @Menthod queryOutinInvoice
     * @Desrciption   获取当前发票号
     * @param outinInvoiceDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/25 14:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 请求结果
     */
    @GetMapping(value = "/queryOutinInvoice")
    public WrapperResponse queryOutinInvoice(OutinInvoiceDTO outinInvoiceDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outinInvoiceDTO.setHospCode(userDTO.getHospCode());//医院编码
        outinInvoiceDTO.setUseId(userDTO.getId());//发票使用人id
        //首先查询当前用户是否有可使用的发票段（医院编码、使用人、发票使用状态、票据类型）
        //票据类型（0、全院通用，1、门诊发票，2、挂号发票，3、门诊通用，4、住院）
        List<String> typeCode = new ArrayList<String>();
        //0、全院通用；1、门诊发票；3、门诊通用
        Collections.addAll(typeCode,Constants.PJLX.TY,Constants.PJLX.MZ,Constants.PJLX.MZTY);
        outinInvoiceDTO.setTypeCodeList(typeCode);
        Map param = new HashMap();
        param.put("hospCode",userDTO.getHospCode());
        param.put("outinInvoiceDTO",outinInvoiceDTO);
        return outptTmakePriceFormService_consumer.queryOutinInvoice(param);
    }

    /**
     * @Menthod editOutinInvoice
     * @Desrciption  修改发票状态
     * @param id 发票id
     * @Author Ou·Mr
     * @Date 2020/8/31 17:23
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @PutMapping(value = "/editOutinInvoiceIsStatusCode/{id}")
    public WrapperResponse editOutinInvoiceIsStatusCode(@PathVariable String id,HttpServletRequest req, HttpServletResponse res) {
        if (StringUtils.isEmpty(id)){
            return WrapperResponse.fail("参数错误。",null);
        }
        OutinInvoiceDO outinInvoiceDO = new OutinInvoiceDO();
        SysUserDTO userDTO = getSession(req, res) ;
        outinInvoiceDO.setHospCode(userDTO.getHospCode());//医院编码
        outinInvoiceDO.setStatusCode(Constants.PJSYZT.ZY);//使用状态 = 在用状态
        outinInvoiceDO.setId(id);//发票id
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("hospCode",userDTO.getHospCode());//医院编码
        param.put("outinInvoiceDO",outinInvoiceDO);//请求参数
        return outptTmakePriceFormService_consumer.editOutinInvoiceIsStatusCode(param);
    }

    /**
     * @Menthod editOutinInvoice
     * @Desrciption 编辑发票信息
     * @param outinInvoiceDTO 请求参数
     * @Author Ou·Mr
     * @Date 2020/8/31 19:11
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @PutMapping(value = "/editOutinInvoice")
    public WrapperResponse editOutinInvoice(@RequestBody OutinInvoiceDTO outinInvoiceDTO,HttpServletRequest req, HttpServletResponse res) {
        if(StringUtils.isEmpty(outinInvoiceDTO.getId())){
            return WrapperResponse.fail("参数错误。",null);
        }
        SysUserDTO userDTO = getSession(req, res) ;
        outinInvoiceDTO.setHospCode(userDTO.getHospCode());//医院编码
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("hospCode",userDTO.getHospCode());//医院编码
        param.put("outinInvoiceDTO",outinInvoiceDTO);
        return outptTmakePriceFormService_consumer.editOutinInvoice(param);
    }

    /**
     * @Menthod queryOutptCostList
     * @Desrciption 根据处方id查询当前患者的处方产生的费用信息
     * @param param 请求参数（就诊id，优惠类型）
     * @Author Ou·Mr
     * @Date 2020/8/25 14:15
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping(value = "/queryOutptCostList")
    public WrapperResponse queryOutptCostList(@RequestParam Map param,HttpServletRequest req, HttpServletResponse res) {
        /* 校验必要参数；判断就诊id参数 */
        if (!param.containsKey("visitId") && StringUtils.isEmpty((String) param.get("visitId"))
                && !param.containsKey("preferentialTypeId") && StringUtils.isEmpty((String) param.get("preferentialTypeId"))){
            return WrapperResponse.error(WrapperResponse.FAIL,"参数错误。",null);
        }
        SysUserDTO userDTO = getSession(req, res) ;
        param.put("hospCode",userDTO.getHospCode());//医院编码
        return outptTmakePriceFormService_consumer.queryOutptCostList(param);
    }


    /**
     * @Menthod queryBaseByPage
     * @Desrciption   查询当前医院项目、药品、材料信息
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/24 14:24
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping(value = "/queryBaseByPage")
    public WrapperResponse queryBaseByPage(@RequestParam Map param,HttpServletRequest req, HttpServletResponse res) {
        if(!param.containsKey("pfTypeCode") || StringUtils.isEmpty((String) param.get("pfTypeCode"))){
            return WrapperResponse.fail("参数错误",null);
        }
        SysUserDTO userDTO = getSession(req, res) ;
        param.put("deptId",userDTO.getLoginBaseDeptDTO()==null?null:
                userDTO.getLoginBaseDeptDTO().getId());
        param.put("hospCode",userDTO.getHospCode());
        return outptTmakePriceFormService_consumer.queryBaseByPage(param);
    }


    /**
     * @Menthod saveOutptCostAndVisit
     * @Desrciption   暂存个人信息及费用信息
     * @param outptVisitDTO 个人信息、费用信息
     * @Author Ou·Mr
     * @Date 2020/8/25 14:21
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @PostMapping(value = "/saveOutptCostAndVisit")
    public WrapperResponse saveOutptCostAndVisit(@RequestBody OutptVisitDTO outptVisitDTO,HttpServletRequest req, HttpServletResponse res) {
        /* 校验是否存在费用信息，不存在费用返回错误提示。 */
        if (outptVisitDTO.getOutptCostDTOList() == null || outptVisitDTO.getOutptCostDTOList().isEmpty()){
            return WrapperResponse.fail("该患者未产生费用。",null);
        }
        SysUserDTO userDTO = getSession(req, res) ;
        /* 校验就诊人信息，由于前端可手动录入医生，校验医生信息参数是否存在，不存在赋值当前登录医生 */
        outptVisitDTO.setDoctorId(StringUtils.isEmpty(outptVisitDTO.getDoctorId()) ? userDTO.getId() : outptVisitDTO.getDoctorId());//就诊医生id
        outptVisitDTO.setDoctorName(StringUtils.isEmpty(outptVisitDTO.getDoctorName()) ? userDTO.getName() : outptVisitDTO.getDoctorName());//就诊医生名称
        outptVisitDTO.setDeptId(StringUtils.isEmpty(outptVisitDTO.getDeptId()) ? userDTO.getLoginBaseDeptDTO().getId() : outptVisitDTO.getDeptId());//就诊科室id
        outptVisitDTO.setDeptName(StringUtils.isEmpty(outptVisitDTO.getDeptName()) ? userDTO.getLoginBaseDeptDTO().getName() : outptVisitDTO.getDeptName());//就诊科室名称
        outptVisitDTO.setVisitTime(new Date());//就诊时间
        outptVisitDTO.setCrteId(userDTO.getId());//创建人id
        outptVisitDTO.setCrteName(userDTO.getName());//创建人姓名
        outptVisitDTO.setCrteTime(new Date());//创建时间
        outptVisitDTO.setIsVisit(Constants.SF.S);//是否就诊，设置 "是"
        outptVisitDTO.setHospCode(userDTO.getHospCode());//医院编码
        Map param = new HashMap();
        param.put("hospCode",userDTO.getHospCode());
        param.put("outptVisitDTO",outptVisitDTO);
        return outptTmakePriceFormService_consumer.saveOutptCostAndVisit(param);
    }


    /**
     * @Menthod saveOutptSettleMoney
     * @Desrciption  计算此次费用需支付金额
     * @param outptVisitDTO 患者信息、费用信息
     * @Author Ou·Mr
     * @Date 2020/8/25 14:23
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @NoRepeatSubmit
    @PostMapping(value = "/saveOutptSettleMoney")
    public WrapperResponse saveOutptSettleMoney(@RequestBody OutptVisitDTO outptVisitDTO,HttpServletRequest req, HttpServletResponse res) {
        //校验信息是否正确（身份证件号、姓名、身份证件类型、费用）
        if (StringUtils.isEmpty(outptVisitDTO.getName())
                || outptVisitDTO.getOutptCostDTOList() == null || outptVisitDTO.getOutptCostDTOList().isEmpty() || StringUtils.isEmpty(outptVisitDTO.getPreferentialTypeId())){
            return WrapperResponse.fail("参数错误",null);
        }
        if (!Constants.BRLX.PTBR.equals(outptVisitDTO.getPatientCode()) && StringUtils.isEmpty(outptVisitDTO.getInsure())){
            return WrapperResponse.fail("医保病人，未获取医保病人信息。",null);
        }
        //判断前端恶意传参
        outptVisitDTO.getOutptCostDTOList().stream().forEach(outptCostDTO -> {
            if(StringUtils.isEmpty(outptCostDTO.getItemId()) || StringUtils.isEmpty(outptCostDTO.getItemCode())
                    || outptCostDTO.getTotalNum() == null ||  outptCostDTO.getTotalNum().compareTo(new BigDecimal(0)) <= 0){
                throw new RuntimeException("参数错误");
            }
        });
        SysUserDTO userDTO = getSession(req, res) ;
        outptVisitDTO.setHospCode(userDTO.getHospCode());//医院编码
        outptVisitDTO.setHospName(userDTO.getHospName());//医院名称
        outptVisitDTO.setCrteId(userDTO.getId());//创建人id
        outptVisitDTO.setCode(userDTO.getCode());
        outptVisitDTO.setCrteName(userDTO.getName());//创建人姓名
        Map param = new HashMap();
        param.put("hospCode",userDTO.getHospCode());
        param.put("outptVisitDTO",outptVisitDTO);
        return outptTmakePriceFormService_consumer.saveOutptSettleMoney(param);
    }

    /**
     * @Menthod saveOutptSettle
     * @Desrciption  门诊划价收费结算
     * @param params  请求参数
     * @Author Ou·Mr
     * @Date 2020/8/25 14:25
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @NoRepeatSubmit
    @PostMapping(value = "/saveOutptSettle")
    public WrapperResponse saveOutptSettle(@RequestBody Map<String,Object> params,HttpServletRequest req, HttpServletResponse res) {
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
        outptVisitDTO.setCode(userDTO.getCode()); // 操作员编码
        outptVisitDTO.setDeptId(userDTO.getLoginBaseDeptDTO().getId() );//当前登录用户操作科室ID
        outptSettleDTO.setHospCode(userDTO.getHospCode());//医院编码
        Map param = new HashMap();
        param.put("hospCode",userDTO.getHospCode());
        param.put("outptVisitDTO",outptVisitDTO);//(个人信息、费用信息)
        param.put("outptSettleDTO",outptSettleDTO);//结算信息
        param.put("outptPayDOList",outptPayDOList);//支付信息
        return outptTmakePriceFormService_consumer.saveOutptSettle(param);
    }

    /**
     * @Menthod getBasePreferentialType
     * @Desrciption 查询优惠类型
     * @param
     * @Author Ou·Mr
     * @Date 2020/8/27 9:44
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping(value = "/getBasePreferentialType")
    public WrapperResponse getBasePreferentialType(HttpServletRequest req, HttpServletResponse res) {
        Map<String,String> param = new HashMap<String,String>();
        SysUserDTO userDTO = getSession(req, res) ;
        param.put("hospCode",userDTO.getHospCode());//医院编码
        param.put("isValid",Constants.SF.S);//是否有效
        return outptTmakePriceFormService_consumer.getBasePreferentialType(param);
    }

    /**
     * @Menthod verifyCoupon
     * @Desrciption 计算优惠金额
     * @param outptVisitDTO 校验费用参数
     * @Author Ou·Mr
     * @Date 2020/8/27 11:44
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @PutMapping(value = "/updateVerifyCoupon")
    public WrapperResponse updateVerifyCoupon(@RequestBody OutptVisitDTO outptVisitDTO,HttpServletRequest req, HttpServletResponse res) {
        if (StringUtils.isEmpty(outptVisitDTO.getPreferentialTypeId()) || outptVisitDTO.getOutptCostDTOList() == null || outptVisitDTO.getOutptCostDTOList().isEmpty()){
            return WrapperResponse.fail("该患者未产生费用。",null);
        }
        SysUserDTO userDTO = getSession(req, res) ;
        outptVisitDTO.setHospCode(userDTO.getHospCode());//医院编码
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("hospCode",userDTO.getHospCode());//医院编码
        param.put("outptVisitDTO",outptVisitDTO);
        return outptTmakePriceFormService_consumer.updateVerifyCoupon(param);
    }

    /**
     * @Menthod: queryPageByInfu()
     * @Desrciption: 收费查询
     * @Param: OutptSettleDTO--门诊结算DTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/9/2 14:19
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sys.PageDTO>
     **/
    @GetMapping("/queryCharge")
    public WrapperResponse<PageDTO> queryGetCharge(OutptSettleDTO outptSettleDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptSettleDTO.setHospCode(userDTO.getHospCode());
        Map paramMap = new HashMap();
        paramMap.put("hospCode",userDTO.getHospCode());
        paramMap.put("outptSettleDTO",outptSettleDTO);
        return outptTmakePriceFormService_consumer.queryCharge(paramMap);
    }

    /**收费查询(导出，多级表头为post请求)
     * @Method queryPostCharge
     * @Desrciption
     * @param outptSettleDTO
     * @Author liuqi1
     * @Date   2021/5/6 17:17
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @PostMapping("/queryCharge")
    public WrapperResponse<PageDTO> queryPostCharge(@RequestBody OutptSettleDTO outptSettleDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptSettleDTO.setHospCode(userDTO.getHospCode());
        Map paramMap = new HashMap();
        paramMap.put("hospCode",userDTO.getHospCode());
        paramMap.put("outptSettleDTO",outptSettleDTO);
        return outptTmakePriceFormService_consumer.queryCharge(paramMap);
    }

    /**
     * @Menthod queryBaseDept
     * @Desrciption  获取选择科室指定的药房科室id
     * @param baseDeptDrugStoreDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/9/18 15:26
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping("/queryBaseDept")
    public WrapperResponse queryBaseDept(BaseDeptDrugStoreDTO baseDeptDrugStoreDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        baseDeptDrugStoreDTO.setHospCode(userDTO.getHospCode());//医院编码
        baseDeptDrugStoreDTO.setIsValid(Constants.SF.S);//是否有效 = 是
        Map param = new HashMap<String,Object>();
        param.put("hospCode",userDTO.getHospCode());
        param.put("baseDeptDrugStoreDTO",baseDeptDrugStoreDTO);
        return outptTmakePriceFormService_consumer.queryBaseDept(param);
    }


    /**
     * @Menthod queryIsSettleCost
     * @Desrciption 获取门诊已结算费用信息
     * @param settleNo 结算单号
     * @Author Ou·Mr
     * @Date 2020/10/11 14:51
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping("/queryIsSettleCost")
    public WrapperResponse queryIsSettleCost(String settleNo,HttpServletRequest req, HttpServletResponse res) {
        if (StringUtils.isEmpty(settleNo)){
            return  WrapperResponse.fail("参数错误。",null);
        }
        SysUserDTO userDTO = getSession(req, res) ;
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("hospCode",userDTO.getHospCode());//医院编码
        OutptCostDTO outptCostDTO = new OutptCostDTO();
        outptCostDTO.setHospCode(userDTO.getHospCode());//医院编码
        outptCostDTO.setSettleId(settleNo);//结算单号
        outptCostDTO.setSettleCode(Constants.JSZT.YIJS);//结算状态 = 已结算
        outptCostDTO.setStatusCode(Constants.ZTBZ.ZC);//标志状态 = 正常
        param.put("outptCostDTO",outptCostDTO);
        return outptTmakePriceFormService_consumer.queryIsSettleCost(param);
    }

    /**
     * @Description: 根据页面设置的自定义优惠类别，勾选的需要计算的项目计算自定义价格
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/1/26 14:51
     * @Return
     */
    @NoRepeatSubmit
    @PostMapping("/customMoney")
    public WrapperResponse<OutptVisitDTO> customMoney(@RequestBody OutptVisitDTO outptVisitDTO,HttpServletRequest req, HttpServletResponse res) {
        if (outptVisitDTO == null) {
            return  WrapperResponse.fail("参数错误，请联系管理员。",null);
        }
        if (outptVisitDTO.getCustomYhlx().equals("0")){
            return  WrapperResponse.fail("请选择优惠类型。",null);
        }
        if (outptVisitDTO.getCustom() == null) {
            return  WrapperResponse.fail("请输入折扣或收取金额。",null);
        }
        if (outptVisitDTO.getOutptCostDTOList().size() == 0) {
            return  WrapperResponse.fail("请勾选需要自定义优惠的收费项目。",null);
        }
        Map<String, Object> map = new HashMap<>();
        SysUserDTO userDTO = getSession(req, res) ;
        map.put("hospCode",userDTO.getHospCode());//医院编码
        map.put("outptVisitDTO", outptVisitDTO);
        return outptTmakePriceFormService_consumer.customMoney(map);
    }

    /**
     * @Description: 保存自定义优惠数据
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/1/26 14:54
     * @Return
     */
    @NoRepeatSubmit
    @PostMapping("/saveCustomMoney")
    public WrapperResponse<Boolean> saveCustomMoney(@RequestBody OutptVisitDTO outptVisitDTO,HttpServletRequest req, HttpServletResponse res) {
        if (outptVisitDTO == null) {
            return  WrapperResponse.fail("参数错误，请联系管理员。",null);
        }
        if (outptVisitDTO.getCustomYhlx().equals("0")){
            return  WrapperResponse.fail("请选择优惠类型。",null);
        }
        if (outptVisitDTO.getCustom() == null) {
            return  WrapperResponse.fail("请输入折扣或收取金额。",null);
        }
        if (outptVisitDTO.getOutptCostDTOList().size() == 0) {
            return  WrapperResponse.fail("请勾选需要自定义优惠的收费项目。",null);
        }
        Map<String, Object> map = new HashMap<>();
        SysUserDTO userDTO = getSession(req, res) ;
        map.put("hospCode",userDTO.getHospCode());//医院编码
        map.put("outptVisitDTO", outptVisitDTO);
        return outptTmakePriceFormService_consumer.saveCustomMoney(map);
    }

    @NoRepeatSubmit
    @PostMapping(value = "/saveOutptInsureVisit")
    public WrapperResponse saveOutptInsureVisit(@RequestBody OutptVisitDTO outptVisitDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptVisitDTO.setHospCode(userDTO.getHospCode());//医院编码
        outptVisitDTO.setHospName(userDTO.getHospName());//医院名称
        outptVisitDTO.setCrteId(userDTO.getId());//创建人id
        outptVisitDTO.setCrteName(userDTO.getName());//创建人姓名
        Map param = new HashMap();
        param.put("hospCode",userDTO.getHospCode());
        param.put("outptVisitDTO",outptVisitDTO);
        return outptTmakePriceFormService_consumer.saveOutptInsureVisit(param);
    }

    @NoRepeatSubmit
    @PostMapping(value = "/saveOutptSettleMoneyDz")
    public WrapperResponse saveOutptSettleMoneyDz(@RequestBody OutptVisitDTO outptVisitDTO,HttpServletRequest req, HttpServletResponse res) {
        //校验信息是否正确（身份证件号、姓名、身份证件类型、费用）
        if (StringUtils.isEmpty(outptVisitDTO.getName())
                || outptVisitDTO.getOutptCostDTOList() == null || outptVisitDTO.getOutptCostDTOList().isEmpty() || StringUtils.isEmpty(outptVisitDTO.getPreferentialTypeId())){
            return WrapperResponse.fail("参数错误",null);
        }
        if (!Constants.BRLX.PTBR.equals(outptVisitDTO.getPatientCode()) && StringUtils.isEmpty(outptVisitDTO.getInsure())){
            return WrapperResponse.fail("医保病人，未获取医保病人信息。",null);
        }
        //判断前端恶意传参
        outptVisitDTO.getOutptCostDTOList().stream().forEach(outptCostDTO -> {
            if(StringUtils.isEmpty(outptCostDTO.getItemId()) || StringUtils.isEmpty(outptCostDTO.getItemCode())
                    || outptCostDTO.getTotalNum() == null ||  outptCostDTO.getTotalNum().compareTo(new BigDecimal(0)) <= 0){
                throw new RuntimeException("参数错误");
            }
        });
        SysUserDTO userDTO = getSession(req, res) ;
        outptVisitDTO.setHospCode(userDTO.getHospCode());//医院编码
        outptVisitDTO.setHospName(userDTO.getHospName());//医院名称
        outptVisitDTO.setCrteId(userDTO.getId());//创建人id
        outptVisitDTO.setCrteName(userDTO.getName());//创建人姓名
        Map param = new HashMap();
        param.put("hospCode",userDTO.getHospCode());
        param.put("outptVisitDTO",outptVisitDTO);
        return outptTmakePriceFormService_consumer.saveOutptSettleMoneyDz(param);
    }

    @NoRepeatSubmit
    @PostMapping(value = "/revokeOrder")
    public WrapperResponse revokeOrder(@RequestBody OutptVisitDTO outptVisitDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptVisitDTO.setHospCode(userDTO.getHospCode());//医院编码
        outptVisitDTO.setHospName(userDTO.getHospName());//医院名称
        outptVisitDTO.setCrteId(userDTO.getId());//创建人id
        outptVisitDTO.setCrteName(userDTO.getName());//创建人姓名
        Map param = new HashMap();
        param.put("hospCode",userDTO.getHospCode());
        param.put("outptVisitDTO",outptVisitDTO);
        return outptTmakePriceFormService_consumer.revokeOrder(param);
    }

    /**
     * @Description: 根据费用id、优惠类别、默认数量为1来计算费用优惠金额(直接划价收费时，添加一条费用项目，立马计算当前项目优惠金额)
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/2/4 11:24
     * @Return
     */
    @PostMapping("/getOutptCostDTOForPreferential")
    public WrapperResponse<OutptCostDTO> getOutptCostDTOForPreferential(@RequestBody OutptCostDTO outptCostDTO,HttpServletRequest req, HttpServletResponse res) {

        Map<String, Object> map = new HashMap<>();
        SysUserDTO userDTO = getSession(req, res) ;
        map.put("hospCode", userDTO.getHospCode());
        map.put("outptCost", outptCostDTO);
        return outptTmakePriceFormService_consumer.getOutptCostDTOForPreferential(map);
    }

    /**
     * @Method selectCheckInfo
     * @Desrciption  读取审批信息
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/2/1 19:31
     * @Return map
     **/
    @GetMapping("/selectCheckInfo")
    public WrapperResponse<Map<String,Object>> selectCheckInfo(@RequestParam Map<String,Object> map,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        map.put("hospCode",userDTO.getHospCode());
        map.put("code",userDTO.getCode());
        return outptTmakePriceFormService_consumer.selectCheckInfo(map);
    }


    /**
     * @Desrciption  uploadCheckInfo 医院审批信息上报
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/2/1 19:28
     * @Return boolean
     **/
    @GetMapping("/uploadCheckInfo")
    public WrapperResponse<Boolean> uploadCheckInfo(@RequestParam Map<String,Object> map,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        map.put("hospCode",userDTO.getHospCode());
        map.put("code",userDTO.getCode());
        map.put("crteName",userDTO.getName());
        return outptTmakePriceFormService_consumer.uploadCheckInfo(map);
    }
    /**
     * @Desrciption  cancelRegister 门特病人取消结算以后 取消登记
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/2/1 19:28
     * @Return boolean
     **/
    @GetMapping("/cancelRegister")
    public WrapperResponse<Boolean> cancelRegister(@RequestParam Map<String,Object> map,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        map.put("hospCode",userDTO.getHospCode());
        map.put("code",userDTO.getCode());
        map.put("crteName",userDTO.getName());
        return outptTmakePriceFormService_consumer.cancelRegister(map);
    }
    /**
     * @Method updateFeeSubmit()
     * @Desrciption  门诊医保病人费用传输
     * @Param
     *
     * @Author fuhui
     * @Date   2021/3/3 16:38
     * @Return
     **/
    @PostMapping("/feeSubmit")
    @NoRepeatSubmit
    public WrapperResponse<Map<String,Object>> updateFeeSubmit(@RequestParam Map<String,Object> map,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        map.put("hospCode" ,userDTO.getHospCode());
        return outptTmakePriceFormService_consumer.updateFeeSubmit(map);
    }

    /**
     * @Method updateCancelFeeSubmit
     * @Desrciption  门诊医保病人取消费用上传
     * @Param
     *
     * @Author fuhui
     * @Date   2021/3/3 16:44
     * @Return
     **/
    @PostMapping("/updateCancelFeeSubmit")
    @NoRepeatSubmit
    public WrapperResponse<Boolean> updateCancelFeeSubmit(@RequestBody Map<String,Object> map,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        map.put("visitId", MapUtils.get(map,"id"));
        map.put("hospCode" ,userDTO.getHospCode());
        return outptTmakePriceFormService_consumer.updateCancelFeeSubmit(map);
    }

    /**
     * @Method updateVisitTime
     * @Desrciption  更新患者的就诊时间
     * @Param
     *
     * @Author fuhui
     * @Date   2021/6/24 0:47
     * @Return
     **/
    @PostMapping("/updateVisitTime")
    @NoRepeatSubmit
    public WrapperResponse<Boolean> updateVisitTime(@RequestBody Map<String,Object> map,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        map.put("visitId", MapUtils.get(map,"id"));
        map.put("hospCode" ,userDTO.getHospCode());
        return outptTmakePriceFormService_consumer.updateVisitTime(map);
    }

    /**
     * @Method checkSettleMoney
     * @Desrciption  门诊预结算之前，需要判断his产生的费用，是否和上传到医保的费用相等。
     * @Param params
     *
     * @Author fuhui
     * @Date   2021/3/12 11:25
     * @Return Map<String,Object>
     **/
    @PostMapping(value = "/checkSettleMoney")
    public WrapperResponse<Map<String,Object>> checkSettleMoney(@RequestBody OutptVisitDTO outptVisitDTO,HttpServletRequest req, HttpServletResponse res) {
        Map<String,Object> map = new HashMap<>();
        SysUserDTO userDTO = getSession(req, res) ;
        outptVisitDTO.setHospCode(userDTO.getHospCode());
        map.put("outptVisitDTO",outptVisitDTO);
        map.put("hospCode",userDTO.getHospCode());
        return outptTmakePriceFormService_consumer.checkSettleMoney(map);

    }

    /**
     * @Description: lis数据下发
     * @Param: [outptVisitDTO]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @Author: zhangxuan
     * @Date: 2021-06-29
     */
    @PostMapping("/lisData")
    public void lisData(@RequestBody OutptVisitDTO outptVisitDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptVisitDTO.setHospCode(userDTO.getHospCode());

        Map map = new HashMap();
        map.put("outptVisitDTO",outptVisitDTO);
        map.put("hospCode",userDTO.getHospCode());

        outptTmakePriceFormService_consumer.lisData(map);

    }
}
