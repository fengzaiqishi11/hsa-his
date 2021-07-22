package cn.hsa.module.inpt;

import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.fees.dto.InptSettleDTO;
import cn.hsa.module.inpt.fees.service.InptCancelSettlementService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt
 * @Class_name: InptCancelSettlementController
 * @Describe(描述): 住院取消结算Controller
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/09/25 9:52
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/inpt/inptCancelSettlement")
public class InptCancelSettlementController extends BaseController {

    @Resource
    private InptCancelSettlementService inptCancelSettlementService_consumer;

    /**
     * @Menthod querySettleVisitPage
     * @Desrciption  获取结算患者信息
     * @param inptSettleDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/10/12 9:41 
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping("/querySettleVisitPage")
    public WrapperResponse querySettleVisitPage(InptSettleDTO inptSettleDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("hospCode", sysUserDTO.getHospCode());//医院编码
        inptSettleDTO.setHospCode(sysUserDTO.getHospCode());//医院编码
        inptSettleDTO.setStatusCode(Constants.ZTBZ.ZC);//状态标志 = 正常
       /* inptSettleDTO.setIsSettle(Constants.SF.S);//是否结算 =  是*/
        param.put("inptSettleDTO",inptSettleDTO);
        return inptCancelSettlementService_consumer.querySettleVisitPage(param);
    }
    
    /**
     * @Menthod queryCostOrPayList
     * @Desrciption 获取选中患者的费用信息、预交金信息
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/10/12 9:46 
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping("/queryCostOrPayList")
    public WrapperResponse queryCostOrPayList(@RequestParam Map<String,String> param, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        //判断必填参数
        if (StringUtils.isEmpty(param.get("id")) || StringUtils.isEmpty(param.get("settleId"))){
            return WrapperResponse.fail("参数错误。",null);
        }
        param.put("hospCode", sysUserDTO.getHospCode());//医院编码
        param.put("settleCode",Constants.JSZT.YIJS);//结算状态 = 已结算
        param.put("statusCode",Constants.ZTBZ.ZC);//状态标志 = 正常
        return inptCancelSettlementService_consumer.queryCostOrPayList(param);
    }

    /**
     * @Menthod editCancelSettlement
     * @Desrciption 取消结算
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/10/12 9:56 
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @PutMapping("/editCancelSettlement")
    public WrapperResponse editCancelSettlement(@RequestBody Map<String,String> param, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        if (StringUtils.isEmpty(param.get("visitId")) || StringUtils.isEmpty(param.get("settleId"))){
            return WrapperResponse.fail("参数错误。",null);
        }
        param.put("hospCode", sysUserDTO.getHospCode());//医院编码
        param.put("userId", sysUserDTO.getId());//用户id
        param.put("userName", sysUserDTO.getName());//用户姓名
        param.put("userCode", sysUserDTO.getCode());
        return inptCancelSettlementService_consumer.editCancelSettlement(param);
    }

}
