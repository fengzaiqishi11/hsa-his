package cn.hsa.module.inpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.fees.entity.InptPayDO;
import cn.hsa.module.inpt.fees.service.InptSettlementService;
import cn.hsa.module.outpt.fees.service.OutptTmakePriceFormService;
import cn.hsa.module.outpt.outinInvoice.dto.OutinInvoiceDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Package_name: cn.hsa.module.inpt
 * @Class_name: InptSettlementController
 * @Describe(描述): 住院结算Controller
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/09/25 9:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/inpt/inptSettlement")
public class InptSettlementController extends BaseController {

    @Resource
    private InptSettlementService inptSettlementService_consumer;

    @Resource
    private OutptTmakePriceFormService outptTmakePriceFormService_consumer;

    /**
     * @Menthod queryInptvisitByPage
     * @Desrciption  查询可出院结算的用户信息
     * @param inptVisitDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/9/25 10:47
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping("/queryInptvisitByPage")
    public WrapperResponse queryInptvisitByPage(InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());//医院编码
        String isMidWaySettle = inptVisitDTO.getIsMidWaySettle();
        if (isMidWaySettle != null && "1".equals(isMidWaySettle)) {
            inptVisitDTO.setStatusCode(Constants.BRZT.ZY);//当前状态 = 在院
            inptVisitDTO.setMidWayStartDate(inptVisitDTO.getStartDate());
            inptVisitDTO.setMidWayEndDate(inptVisitDTO.getEndDate());
            inptVisitDTO.setStartDate(null);
            inptVisitDTO.setEndDate(null);
        }else {
            inptVisitDTO.setStatusCode(Constants.BRZT.YCY);//当前状态 = 预出院
        }
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("hospCode", sysUserDTO.getHospCode());//医院编码
        param.put("inptVisitDTO",inptVisitDTO);//查询条件
        return inptSettlementService_consumer.queryInptvisitByPage(param);
    }

    /**
     * @Menthod queryInptCostByList
     * @Desrciption 查询预交金、费用信息
     * @param id 就诊id
     * @Author Ou·Mr
     * @Date 2020/9/25 11:09
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping("/queryInptCostByList")
    public WrapperResponse queryInptCostByList(@Param("id") String id, @Param("isMidWaySettle") String isMidWaySettle, @Param("patientCode") String patientCode,
                                               @Param("attributionCode") String attributionCode,HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        if (StringUtils.isEmpty(id)){
            return WrapperResponse.fail("参数错误。",null);
        }
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("hospCode", sysUserDTO.getHospCode());//医院编码
        param.put("id",id);//就诊id
        if (isMidWaySettle != null && "1".equals(isMidWaySettle)) {
            param.put("patientCode", Integer.valueOf(patientCode)); // 病人类型
        }else {
            param.put("patientCode", null); // 病人类型
        }

        param.put("statusCode",Constants.ZTBZ.ZC);//状态标志
        param.put("settleCodes",new String[]{Constants.JSZT.WJS,Constants.JSZT.YUJS});//结算状态 = 未结算、预结算
        param.put("backCode",Constants.TYZT.YFY);//退费状态 = 已发药
        param.put("queryBaby","N");
        if(!StringUtils.isEmpty(attributionCode)) {
          param.put("attributionCode",attributionCode);
        }
        return inptSettlementService_consumer.queryInptCostByList(param);
    }

    /**
     * @Menthod saveCostTrial
     * @Desrciption  住院结算试算
     * @param inptVisitDTO 请求参数
     * @Author Ou·Mr
     * @Date 2020/9/25 11:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @PostMapping("/saveCostTrial")
    public WrapperResponse saveCostTrial(@RequestBody InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        if (StringUtils.isEmpty(inptVisitDTO.getId())){
            return WrapperResponse.fail("参数错误。",null);
        }
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());//医院编码
        inptVisitDTO.setCrteId(sysUserDTO.getId());//当前登录用户id
        inptVisitDTO.setCrteName(sysUserDTO.getName());//当前登录用户名
        inptVisitDTO.setInDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());//当前登录用户所属科室id
        inptVisitDTO.setInDeptName(sysUserDTO.getLoginBaseDeptDTO().getName());//当前登录用户所属科室名称
        inptVisitDTO.setUserCode(sysUserDTO.getCode());
        inptVisitDTO.setCode(sysUserDTO.getCode());
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("hospCode", sysUserDTO.getHospCode());//医院编码
        param.put("inptVisitDTO",inptVisitDTO);//请求参数
        return inptSettlementService_consumer.saveCostTrial(param);
    }

    /**
     * @Method insureUnifiedPayInpt
     * @Desrciption 住院预结算
     * @Param
     *
     * @Author 曹亮
     * @Date   2021/7/14 11:17
     * @Return
     **/
    @PostMapping("/insureUnifiedPayInpt")
    public WrapperResponse<Map<String,Object>> insureUnifiedPayInpt(@RequestBody InptVisitDTO inptVisitDTO,HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("hospCode", sysUserDTO.getHospCode());//医院编码
        param.put("visitId",inptVisitDTO.getVisitId());
        param.put("inptVisitDTO",inptVisitDTO);//请求参数
        return inptSettlementService_consumer.insureUnifiedPayInpt(param);
    }

    /**
     * @Menthod saveSettle
     * @Desrciption  住院结算操作
     * @param params 请求参数
     * @Author Ou·Mr
     * @Date 2020/9/25 11:31
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @PostMapping("/saveSettle")
    public WrapperResponse saveSettle(@RequestBody Map<String,Object> params, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        //检验必要参数（id、结算id、支付方式）
        if (!params.containsKey("id") || StringUtils.isEmpty((String) params.get("id")) || !params.containsKey("settleId")
                || StringUtils.isEmpty((String) params.get("settleId")) || params.get("inptPay") == null || !params.containsKey("isInvoice")){
            return WrapperResponse.fail("参数错误。",null);
        }
        List<InptPayDO> inptPayDOList = JSONArray.parseArray(JSON.toJSONString(params.get("inptPay")), InptPayDO.class);//支付方式信息
        params.put("hospCode", sysUserDTO.getHospCode());//医院编码
        params.put("userId", sysUserDTO.getId());//当前登录用户id
        params.put("userName", sysUserDTO.getName());//当前登录用户名称
        params.put("deptId", sysUserDTO.getLoginBaseDeptDTO().getId());//当前用户科室id
        params.put("deptName", sysUserDTO.getLoginBaseDeptDTO().getName());//当前用户科室名称
        params.put("inptPayDOList",inptPayDOList);//支付方式
        params.put("userCode", sysUserDTO.getCode());
        params.put("userName", sysUserDTO.getName());
        params.put("treatmentCode",params.get("treatmentCode"));
        if(StringUtils.isEmpty(MapUtils.get(params,"attributionCode"))){
          params.remove("attributionCode");
        }
        return inptSettlementService_consumer.saveSettle(params);
    }

    /**
     * @Method: querySettle
     * @Description: 获取住院发票数据
     * @Param: [params]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/12/8 11:37
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse
     **/
    @GetMapping("/querySettle")
    public WrapperResponse<Map<String,Object>> querySettle(String settleId, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        //检验必要参数
        if (StringUtils.isEmpty(settleId)){
            return WrapperResponse.fail("参数错误。",null);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("settleId", settleId);
        map.put("userName", sysUserDTO.getName());
        map.put("hospCode", sysUserDTO.getHospCode());
        return inptSettlementService_consumer.querySettle(map);
    }

    /**
     * @Menthod queryOutinInvoice
     * @Desrciption 查询住院发票信息
     * @param outinInvoiceDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/9/30 9:11
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping("/queryOutinInvoice")
    public WrapperResponse queryOutinInvoice(OutinInvoiceDTO outinInvoiceDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        outinInvoiceDTO.setHospCode(sysUserDTO.getHospCode());
        outinInvoiceDTO.setUseId(sysUserDTO.getId());//发票使用人id
        //首先查询当前用户是否有可使用的发票段（医院编码、使用人、发票使用状态、票据类型）
        //票据类型（0、全院通用，4、住院）
        List<String> typeCode = new ArrayList<String>();
        Collections.addAll(typeCode,Constants.PJLX.TY,Constants.PJLX.ZY);
        outinInvoiceDTO.setTypeCodeList(typeCode);
        Map param = new HashMap();
        param.put("hospCode", sysUserDTO.getHospCode());
        param.put("outinInvoiceDTO",outinInvoiceDTO);
        return outptTmakePriceFormService_consumer.queryOutinInvoice(param);
    }

    /**
     * @Menthod: queryDiagnose
     * @Desrciption: 根据就诊id查询对应的诊断列表
     * @Param: inptVisitDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-03-04 19:02
     * @Return: PageDTO
     **/
    @GetMapping("/queryDiagnose")
    public WrapperResponse<PageDTO> queryDiagnose(InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        if (StringUtils.isEmpty(inptVisitDTO.getId())){
            throw new AppException("未选择患者基本信息");
        }
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptVisitDTO", inptVisitDTO);
        return inptSettlementService_consumer.queryDiagnose(map);
    }

    /**
     * @Method editDischargeInpt
     * @Desrciption  医保出院办理
     * @Param params
     *
     * @Author fuhui
     * @Date   2021/5/28 11:40
     * @Return
     **/
    @PostMapping("/editDischargeInpt")
    public WrapperResponse<Boolean> editDischargeInpt(@RequestBody Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode", sysUserDTO.getHospCode());
        return inptSettlementService_consumer.editDischargeInpt(map);
    }

    /**
     * @Method editCancelDischargeInpt
     * @Desrciption  医保出院办理撤销
     * @Param params
     *
     * @Author fuhui
     * @Date   2021/5/28 11:40
     * @Return
     **/
    @PostMapping("/editCancelDischargeInpt")
    public WrapperResponse<Boolean> editCancelDischargeInpt(@RequestBody Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode", sysUserDTO.getHospCode());
        return inptSettlementService_consumer.editCancelDischargeInpt(map);
    }

    /**
     * @Menthod saveBabyCostTrial
     * @Desrciption  住院结算试算(婴儿)
     * @param inptVisitDTO 请求参数
     * @Author liuliyun
     * @Date 2021/5/17 15:08
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @PostMapping("/saveBabyCostTrial")
    public WrapperResponse saveBabyCostTrial(@RequestBody InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        if (StringUtils.isEmpty(inptVisitDTO.getId())){
            return WrapperResponse.fail("参数错误。",null);
        }
        if (StringUtils.isEmpty(inptVisitDTO.getBabyId())){
            return WrapperResponse.fail("参数错误,请刷新浏览器或联系管理员",null);
        }
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());//医院编码
        inptVisitDTO.setCrteId(sysUserDTO.getId());//当前登录用户id
        inptVisitDTO.setCrteName(sysUserDTO.getName());//当前登录用户名
        inptVisitDTO.setInDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());//当前登录用户所属科室id
        inptVisitDTO.setInDeptName(sysUserDTO.getLoginBaseDeptDTO().getName());//当前登录用户所属科室名称
        inptVisitDTO.setUserCode(sysUserDTO.getCode());
        inptVisitDTO.setCode(sysUserDTO.getCode());
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("hospCode", sysUserDTO.getHospCode());//医院编码
        param.put("inptVisitDTO",inptVisitDTO);//请求参数
        return inptSettlementService_consumer.saveBabyCostTrial(param);
    }

    /**
     * @Menthod saveBabySettle
     * @Desrciption  住院结算操作(婴儿)
     * @param params 请求参数
     * @Author liuliyun
     * @Date 2021/5/17 15:35
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @PostMapping("/saveBabySettle")
    public WrapperResponse saveBabySettle(@RequestBody Map<String,Object> params, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        //检验必要参数（id、结算id、支付方式）
        if (!params.containsKey("id") || StringUtils.isEmpty((String) params.get("id")) || !params.containsKey("settleId")
                || StringUtils.isEmpty((String) params.get("settleId")) || params.get("inptPay") == null || !params.containsKey("isInvoice")){
            return WrapperResponse.fail("参数错误。",null);
        }
        if (!params.containsKey("babyId") || StringUtils.isEmpty((String) params.get("babyId"))){
            return WrapperResponse.fail("参数错误,请刷新浏览器或联系管理员",null);
        }
        List<InptPayDO> inptPayDOList = JSONArray.parseArray(JSON.toJSONString(params.get("inptPay")), InptPayDO.class);//支付方式信息
        params.put("hospCode", sysUserDTO.getHospCode());//医院编码
        params.put("userId", sysUserDTO.getId());//当前登录用户id
        params.put("userName", sysUserDTO.getName());//当前登录用户名称
        params.put("deptId", sysUserDTO.getLoginBaseDeptDTO().getId());//当前用户科室id
        params.put("deptName", sysUserDTO.getLoginBaseDeptDTO().getName());//当前用户科室名称
        params.put("inptPayDOList",inptPayDOList);//支付方式
        params.put("userCode", sysUserDTO.getCode());
        params.put("userName", sysUserDTO.getName());
        params.put("treatmentCode",params.get("treatmentCode"));
        return inptSettlementService_consumer.saveBabySettle(params);
    }

    /**
     * @Menthod queryInptBabyCostByList
     * @Desrciption 查询预交金、费用信息
     * @param id 就诊id
     * @param babyId 婴儿id
     * @Author liuliyun
     * @Date 2021/05/18 13:41
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping("/queryInptBabyCostByList")
    public WrapperResponse queryInptBabyCostByList(@Param("id") String id,@Param("babyId")String babyId, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        if (StringUtils.isEmpty(id)){
            return WrapperResponse.fail("参数错误。",null);
        }
        if (StringUtils.isEmpty(babyId)){
            return WrapperResponse.fail("参数错误,请刷新浏览器或联系管理员",null);
        }
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("hospCode", sysUserDTO.getHospCode());//医院编码
        param.put("id",id);//就诊id
        param.put("babyId",babyId);//婴儿id
        param.put("statusCode",Constants.ZTBZ.ZC);//状态标志
        param.put("settleCodes",new String[]{Constants.JSZT.WJS,Constants.JSZT.YUJS});//结算状态 = 未结算、预结算
        param.put("backCode",Constants.TYZT.YFY);//退费状态 = 已发药
        param.put("queryBaby","Y");
        return inptSettlementService_consumer.queryInptCostByList(param);
    }



    /**
     * @Menthod saveAttributionCostTrial
     * @Desrciption 归属结算试算
     *
     * @Param
     * [inptVisitDTO, req, res]
     *
     * @Author jiahong.yang
     * @Date   2021/9/1 9:15
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     **/
    @PostMapping("/saveAttributionCostTrial")
    public WrapperResponse saveAttributionCostTrial(@RequestBody InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res){
      SysUserDTO sysUserDTO = getSession(req, res);
      if (StringUtils.isEmpty(inptVisitDTO.getId())){
        return WrapperResponse.fail("参数错误。",null);
      }
      inptVisitDTO.setHospCode(sysUserDTO.getHospCode());//医院编码
      inptVisitDTO.setCrteId(sysUserDTO.getId());//当前登录用户id
      inptVisitDTO.setCrteName(sysUserDTO.getName());//当前登录用户名
      inptVisitDTO.setInDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());//当前登录用户所属科室id
      inptVisitDTO.setInDeptName(sysUserDTO.getLoginBaseDeptDTO().getName());//当前登录用户所属科室名称
      inptVisitDTO.setUserCode(sysUserDTO.getCode());
      inptVisitDTO.setCode(sysUserDTO.getCode());
      Map<String,Object> param = new HashMap<String,Object>();
      param.put("hospCode", sysUserDTO.getHospCode());//医院编码
      param.put("inptVisitDTO",inptVisitDTO);//请求参数
      return inptSettlementService_consumer.saveAttributionCostTrial(param);
    }

    /**
    * @Menthod saveAttributionSettle
    * @Desrciption 归属结算
    *
    * @Param
    * [params, req, res]
    *
    * @Author jiahong.yang
    * @Date   2021/9/1 9:14
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
    **/
    @PostMapping("/saveAttributionSettle")
    public WrapperResponse saveAttributionSettle(@RequestBody Map<String,Object> params, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    //检验必要参数（id、结算id、支付方式）
    if (!params.containsKey("id") || StringUtils.isEmpty((String) params.get("id")) || !params.containsKey("settleId")
      || StringUtils.isEmpty((String) params.get("settleId")) || params.get("inptPay") == null || !params.containsKey("isInvoice")){
      return WrapperResponse.fail("参数错误。",null);
    }
    List<InptPayDO> inptPayDOList = JSONArray.parseArray(JSON.toJSONString(params.get("inptPay")), InptPayDO.class);//支付方式信息
    params.put("hospCode", sysUserDTO.getHospCode());//医院编码
    params.put("userId", sysUserDTO.getId());//当前登录用户id
    params.put("userName", sysUserDTO.getName());//当前登录用户名称
    params.put("deptId", sysUserDTO.getLoginBaseDeptDTO().getId());//当前用户科室id
    params.put("deptName", sysUserDTO.getLoginBaseDeptDTO().getName());//当前用户科室名称
    params.put("inptPayDOList",inptPayDOList);//支付方式
    params.put("userCode", sysUserDTO.getCode());
    params.put("userName", sysUserDTO.getName());
    params.put("treatmentCode",params.get("treatmentCode"));
    if(StringUtils.isEmpty(MapUtils.get(params,"attributionCode"))){
      params.remove("attributionCode");
    }
    return inptSettlementService_consumer.saveAttributionSettle(params);
  }

}
