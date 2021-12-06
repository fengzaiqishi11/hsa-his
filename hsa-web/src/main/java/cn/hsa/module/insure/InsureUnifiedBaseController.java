package cn.hsa.module.insure;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.inpt.service.InsureUnifiedBaseService;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure
 * @class_name: InsureUnifiedBaseController
 * @Description: TODO 医保统一支付平台：基础信息查询业务控制层
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/4/24 21:17
 * @Company: 创智和宇
 **/
@RestController
@RequestMapping("/web/insure/insureUnifiedBase")
@Slf4j
public class InsureUnifiedBaseController extends BaseController {

    @Resource
    private InsureUnifiedBaseService insureUnifiedBaseService_consumer;

    /**
     * @Method queryUnifiedDept
     * @Desrciption 科室信息查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/23 12:47
     * @Return
     **/
    @PostMapping("/queryUnifiedDept")
    public WrapperResponse<Map<String,Object>> queryUnifiedDept(@RequestBody  Map<String,Object>map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("crteId",sysUserDTO.getCrteId());
        map.put("crteName",sysUserDTO.getCrteName());
        map.put("crteTime", DateUtils.getNow());
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedBaseService_consumer.queryUnifiedDept(map);
    }

    /**
     * @Method queryDoctorInfo
     * @Desrciption 医执人员信息查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/23 12:47
     * @Return
     **/
    @PostMapping("/queryDoctorInfo")
    public WrapperResponse<Map<String,Object>> queryDoctorInfo(@RequestBody Map<String,Object>map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("crteId",sysUserDTO.getCrteId());
        map.put("crteName",sysUserDTO.getCrteName());
        map.put("crteTime", DateUtils.getNow());
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedBaseService_consumer.queryDoctorInfo(map);
    }


    /**
     * @Method queryDoctorInfo
     * @Desrciption 就诊信息查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/23 12:47
     * @Return
     **/
    @PostMapping("/queryVisitInfo")
    public WrapperResponse<Map<String,Object>> queryVisitInfo(@RequestBody Map<String,Object>map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("crteId",sysUserDTO.getCrteId());
        map.put("crteName",sysUserDTO.getCrteName());
        map.put("crteTime", DateUtils.getNow());
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedBaseService_consumer.queryVisitInfo(map);
    }

    /**
     * @Method queryDiagnoseInfo
     * @Desrciption 诊断信息查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/23 12:47
     * @Return
     **/
    @PostMapping("/queryDiagnoseInfo")
    public WrapperResponse<Map<String,Object>> queryDiagnoseInfo(@RequestBody Map<String,Object>map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("crteId",sysUserDTO.getCrteId());
        map.put("crteName",sysUserDTO.getCrteName());
        map.put("crteTime", DateUtils.getNow());
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedBaseService_consumer.queryDiagnoseInfo(map);
    }

    /**
     * @Method updateFormalData
     * @Desrciption 门诊选点改点
     * @Param
     *
     * @Author caoliang
     * @Date   2021/6/30 15:47
     * @Return
     **/
    @PostMapping("/updateFormalData")
    public WrapperResponse<Map<String,Object>> updateFormalData(@RequestBody Map<String,Object>map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("crteId",sysUserDTO.getCrteId());
        map.put("crteName",sysUserDTO.getCrteName());
        map.put("crteTime", DateUtils.getNow());
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedBaseService_consumer.updateFormalData(map);
    }

    /**
     * @Method querySettleInfo
     * @Desrciption 结算信息查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/23 12:47
     * @Return
     **/
    @PostMapping("/updateSettleInfo")
    public WrapperResponse<Map<String,Object>> updateSettleInfo(@RequestBody Map<String,Object>map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("crteId",sysUserDTO.getCrteId());
        map.put("crteName",sysUserDTO.getCrteName());
        map.put("crteTime", DateUtils.getNow());
        map.put("crteName",sysUserDTO.getName());
        return insureUnifiedBaseService_consumer.updateSettleInfo(map);
    }

    /**
     * @Method queryFeeDetailInfo
     * @Desrciption 费用明细查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/23 12:47
     * @Return
     **/
    @PostMapping("/queryFeeDetailInfo")
    public WrapperResponse<Map<String,Object>> queryFeeDetailInfo(@RequestBody Map<String,Object>map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("crteId",sysUserDTO.getCrteId());
        map.put("crteName",sysUserDTO.getCrteName());
        map.put("crteTime", DateUtils.getNow());
        map.put("crteName",sysUserDTO.getName());
        return insureUnifiedBaseService_consumer.queryFeeDetailInfo(map);
    }

    /**
     * @Method querySpecialUserDrug
     * @Desrciption 人员慢特病用药记录查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/23 12:47
     * @Return
     **/
    @PostMapping("/querySpecialUserDrug")
    public WrapperResponse<Map<String,Object>> querySpecialUserDrug(@RequestBody Map<String,Object>map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("crteId",sysUserDTO.getCrteId());
        map.put("crteName",sysUserDTO.getCrteName());
        map.put("crteTime", DateUtils.getNow());
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedBaseService_consumer.querySpecialUserDrug(map);
    }

    /**
     * @Method queryPatientSumInfo
     * @Desrciption 人员累计信息查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/23 12:47
     * @Return
     **/
    @PostMapping("/queryPatientSumInfo")
    public WrapperResponse<Map<String,Object>> queryPatientSumInfo(@RequestBody Map<String,Object>map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("crteId",sysUserDTO.getCrteId());
        map.put("crteName",sysUserDTO.getCrteName());
        map.put("crteTime", DateUtils.getNow());
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedBaseService_consumer.queryPatientSumInfo(map);
    }

    /**
     * @Method queryItemConfirm
     * @Desrciption 项目互认信息查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/23 12:47
     * @Return
     **/
    @PostMapping("/queryItemConfirm")
    public WrapperResponse<Map<String,Object>> queryItemConfirm(@RequestBody Map<String,Object>map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("crteId",sysUserDTO.getCrteId());
        map.put("crteName",sysUserDTO.getCrteName());
        map.put("crteTime", DateUtils.getNow());
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedBaseService_consumer.queryItemConfirm(map);
    }


    /**
     * @Method queryPatientInfo
     * @Desrciption 在院信息查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/23 12:47
     * @Return
     **/
    @PostMapping("/queryPatientInfo")
    public WrapperResponse<Map<String,Object>> queryPatientInfo(@RequestBody Map<String,Object>map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("crteId",sysUserDTO.getCrteId());
        map.put("crteName",sysUserDTO.getCrteName());
        map.put("crteTime", DateUtils.getNow());
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedBaseService_consumer.queryPatientInfo(map);
    }




    /**
     * @Method queryTransfInfo
     * @Desrciption 转院信息查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/23 12:47
     * @Return
     **/
    @PostMapping("/queryTransfInfo")
    public WrapperResponse<Map<String,Object>> queryTransfInfo(@RequestBody Map<String,Object>map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("crteId",sysUserDTO.getCrteId());
        map.put("crteName",sysUserDTO.getCrteName());
        map.put("crteTime", DateUtils.getNow());
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedBaseService_consumer.queryTransfInfo(map);
    }

    /**
     * @Method queryFixRecordInfo
     * @Desrciption 人员定点备案信息查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/23 12:47
     * @Return
     **/
    @PostMapping("/queryFixRecordInfo")
    public WrapperResponse<Map<String,Object>> queryFixRecordInfo(@RequestBody Map<String,Object>map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("crteId",sysUserDTO.getCrteId());
        map.put("crteName",sysUserDTO.getCrteName());
        map.put("crteTime", DateUtils.getNow());
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedBaseService_consumer.queryFixRecordInfo(map);
    }
    
    /**
     * @Method queryMzSpecialLimitPrice
     * @Desrciption  门诊特病限额使用情况查询
     * @Param 
     * 
     * @Author fuhui
     * @Date   2021/6/8 14:09 
     * @Return 
    **/
    @PostMapping("/queryMzSpecialLimitPrice")
    public WrapperResponse<Map<String,Object>> queryMzSpecialLimitPrice(@RequestBody Map<String,Object>map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("crteId",sysUserDTO.getCrteId());
        map.put("crteName",sysUserDTO.getCrteName());
        map.put("crteTime", DateUtils.getNow());
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedBaseService_consumer.queryMzSpecialLimitPrice(map);
    }


    /**
     * @Method querySpecialRecord
     * @Desrciption 人员慢特病备案信息查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/23 12:47
     * @Return
     **/
    @PostMapping("/querySpecialRecord")
    public WrapperResponse<Map<String,Object>> querySpecialRecord(@RequestBody Map<String,Object>map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("crteId",sysUserDTO.getCrteId());
        map.put("crteName",sysUserDTO.getCrteName());
        map.put("crteTime", DateUtils.getNow());
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedBaseService_consumer.querySpecialRecord(map);
    }


    /**
     * @Method querySpecialRecord
     * @Desrciption 告知单查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/23 12:47
     * @Return
     **/
    @PostMapping("/queryInform")
    public WrapperResponse<Map<String,Object>> queryInform(@RequestBody Map<String,Object>map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("crteId",sysUserDTO.getCrteId());
        map.put("crteName",sysUserDTO.getCrteName());
        map.put("crteTime", DateUtils.getNow());
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedBaseService_consumer.queryInform(map);
    }

    /**
     * @Method queryPage
     * @Desrciption  分页查询医保就诊信息(病人已经结算)
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/26 9:20
     * @Return
    **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(InsureIndividualVisitDTO insureIndividualVisitDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>(2);
        map.put("hospCode", sysUserDTO.getHospCode());
        insureIndividualVisitDTO.setHospCode( sysUserDTO.getHospCode());
        map.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
        return insureUnifiedBaseService_consumer.queryPage(map);
    }

    /**
     * @Description: 人员定点信息查询
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
     * @Author: zhangxuan
     * @Date: 2021-04-28
     */
    @PostMapping("/queryPersonFixInfo")
    public WrapperResponse<Map<String,Object>> queryPersonFixInfo(@RequestBody Map<String,Object>map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("crteId",sysUserDTO.getCrteId());
        map.put("crteName",sysUserDTO.getCrteName());
        map.put("crteTime", DateUtils.getNow());
        map.put("hospCode",  sysUserDTO.getHospCode());
        return insureUnifiedBaseService_consumer.queryPersonFixInfo(map);
    }

    /**
     * @Description: 报告明细信息查询
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
     * @Author: zhangxuan
     * @Date: 2021-04-28
     */
    @PostMapping("/queryReportDetails")
    public WrapperResponse<Map<String, Object>> queryReportDetails(@RequestBody Map<String, Object>map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("crteId",sysUserDTO.getCrteId());
        map.put("crteName",sysUserDTO.getCrteName());
        map.put("crteTime", DateUtils.getNow());
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedBaseService_consumer.queryReportDetails(map);
    }
    
    /**
     * @Method updateUnifiedDept
     * @Desrciption  科室信息上传
     * @Param 
     * 
     * @Author fuhui
     * @Date   2021/6/10 14:45 
     * @Return 
    **/
    @PostMapping("/updateUnifiedDept")
    public WrapperResponse<Map<String, Object>> updateUnifiedDept(@RequestBody Map<String, Object>map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("crteId",sysUserDTO.getCrteId());
        map.put("crteName",sysUserDTO.getCrteName());
        map.put("crteTime", DateUtils.getNow());
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedBaseService_consumer.updateUnifiedDept(map);
    }
    
    /**
     * @Method updateUnifiedDeptInfo
     * @Desrciption  变更科室信息。
     * @Param 
     * 
     * @Author fuhui
     * @Date   2021/6/10 15:00 
     * @Return 
    **/
    @PostMapping("/updateUnifiedDeptInfo")
    public WrapperResponse<Map<String, Object>> updateUnifiedDeptInfo(@RequestBody Map<String, Object>map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("crteId",sysUserDTO.getCrteId());
        map.put("crteName",sysUserDTO.getCrteName());
        map.put("crteTime", DateUtils.getNow());
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedBaseService_consumer.updateUnifiedDeptInfo(map);
    }
    
    /**
     * @Method deleteUnifiedDeptInfo
     * @Desrciption  撤销科室信息。
     * @Param 
     *
     * @Author fuhui
     * @Date   2021/6/10 15:02 
     * @Return 
    **/
    @PostMapping("/deleteUnifiedDeptInfo")
    public WrapperResponse<Map<String, Object>> deleteUnifiedDeptInfo(@RequestBody Map<String, Object>map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("crteId",sysUserDTO.getCrteId());
        map.put("crteName",sysUserDTO.getCrteName());
        map.put("crteTime", DateUtils.getNow());
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedBaseService_consumer.deleteUnifiedDeptInfo(map);
    }


    /**
     * @Method updateInsureInptRegisterStatus
     * @Desrciption  更新医保登记状态（医保已经登记 而his没有登记）
     * @Param
     *
     * @Author fuhui
     * @Date   2021/10/8 8:33
     * @Return
    **/
    @PostMapping("/updateInsureInptRegisterStatus")
    public WrapperResponse<Boolean> updateInsureInptRegisterStatus(@RequestBody Map<String, Object>map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("crteId",sysUserDTO.getCrteId());
        map.put("crteName",sysUserDTO.getCrteName());
        map.put("crteTime", DateUtils.getNow());
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedBaseService_consumer.updateInsureInptRegisterStatus(map);
    }

    /**
     * @Method updateInsureInptSettleStatus
     * @Desrciption  更新医保结算状态（医保已经结算 而his没有结算）
     * @Param
     *
     * @Author fuhui
     * @Date   2021/10/8 10:07
     * @Return
    **/
    @PostMapping("/updateInsureInptSettleStatus")
    public WrapperResponse<Boolean> updateInsureInptSettleStatus(@RequestBody Map<String, Object>map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("crteId",sysUserDTO.getCrteId());
        map.put("crteName",sysUserDTO.getCrteName());
        map.put("crteTime", DateUtils.getNow());
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedBaseService_consumer.updateInsureInptSettleStatus(map);
    }

    /**
     * @Method updateInsureInptCancelSettleStatus
     * @Desrciption  同步取消结算状态  his和医保   结算单边
     * @Param
     *
     * @Author fuhui
     * @Date   2021/10/8 10:21
     * @Return
    **/
    @PostMapping("/updateInsureInptCancelSettleStatus")
    public WrapperResponse<Boolean> updateInsureInptCancelSettleStatus(@RequestBody Map<String, Object>map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("crteId",sysUserDTO.getCrteId());
        map.put("crteName",sysUserDTO.getCrteName());
        map.put("crteTime", DateUtils.getNow());
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedBaseService_consumer.updateInsureInptCancelSettleStatus(map);
    }

    /**
     * @Method updateInptPatientCode
     * @Desrciption  修改病人类型
     * @Param
     *
     * @Author fuhui
     * @Date   2021/10/8 10:21
     * @Return
     **/
    @PostMapping("/updateInptPatientCode")
    public WrapperResponse<Boolean> updateInptPatientCode(@RequestBody Map<String, Object>map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedBaseService_consumer.updateInptPatientCode(map);
    }

    @GetMapping("/queryInptInsurePatient")
    public WrapperResponse<PageDTO> queryInptInsurePatient(InsureIndividualVisitDTO insureIndividualVisitDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        String hospCode = sysUserDTO.getHospCode();
        Map<String,Object> map = new HashMap<>();
        insureIndividualVisitDTO.setHospCode(hospCode);
        map.put("hospCode",hospCode);
        map.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
        return insureUnifiedBaseService_consumer.queryInptInsurePatient(map);
    }
}
