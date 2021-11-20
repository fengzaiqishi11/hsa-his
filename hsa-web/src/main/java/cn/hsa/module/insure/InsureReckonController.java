package cn.hsa.module.insure;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.insure.outpt.dto.InsureReckonDTO;
import cn.hsa.module.insure.outpt.service.InsureReckonService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@Package_name: cn.hsa.module.insure
 *@Class_name: insureReversalTradeController
 *@Describe: 医保清算申请服务
 *@Author: liaojiguang
 *@Eamil: jiguang.liao@powersi.com.cn
 *@Date: 2021/9/9 14:08
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/insure/insureReckon")
@Slf4j
public class InsureReckonController extends BaseController {

    @Resource
    private InsureReckonService insureReckonService_consumer;

    /**医药机构清算申请查询
     * @Method queryInsureReckonInfo
     * @Desrciption
     * @param insureReckonDTO
     * @Author liaojiguang
     * @Date   2021/9/9 17:31
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @GetMapping("/queryInsureReckonInfo")
    public WrapperResponse<PageDTO> queryInsureReckonInfo (HttpServletRequest req, HttpServletResponse res,InsureReckonDTO insureReckonDTO) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> selectMap = new HashMap();
        insureReckonDTO.setHospCode(sysUserDTO.getHospCode());
        selectMap.put("hospCode",sysUserDTO.getHospCode());
        selectMap.put("insureReckonDTO",insureReckonDTO);
        return insureReckonService_consumer.queryInsureReckonInfo(selectMap);
    }

    /**新增医药机构清算申请
     * @Method queryInsureReckonInfo
     * @Desrciption
     * @param insureReckonDTO
     * @Author liaojiguang
     * @Date   2021/9/9 17:31
     * @Return
     **/
    @PostMapping("/addInsureReckonInfo")
    public WrapperResponse<Boolean> addInsureReckonInfo( HttpServletRequest req, HttpServletResponse res, @RequestBody InsureReckonDTO insureReckonDTO) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> selectMap = new HashMap();
        insureReckonDTO.setHospCode(sysUserDTO.getHospCode());
        insureReckonDTO.setCrteId(sysUserDTO.getId());
        insureReckonDTO.setCrteName(sysUserDTO.getName());
        selectMap.put("hospCode",sysUserDTO.getHospCode());
        selectMap.put("insureReckonDTO",insureReckonDTO);
        return insureReckonService_consumer.addInsureReckonInfo(selectMap);
    }

    /**编辑医药机构清算申请
     * @Method queryInsureReckonInfo
     * @Desrciption
     * @param insureReckonDTO
     * @Author liaojiguang
     * @Date   2021/9/9 17:31
     * @Return
     **/
    @PostMapping("/updateInsureReckonInfo")
    public WrapperResponse<Boolean> updateInsureReckonInfo(HttpServletRequest req, HttpServletResponse res,InsureReckonDTO insureReckonDTO ) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> selectMap = new HashMap();
        insureReckonDTO.setHospCode(sysUserDTO.getHospCode());
        selectMap.put("hospCode",sysUserDTO.getHospCode());
        selectMap.put("insureReckonDTO",insureReckonDTO);
        return insureReckonService_consumer.updateInsureReckonInfo(selectMap);
    }

    /**医药机构清算申请 - 申报
     * @Method queryInsureReckonInfo
     * @Desrciption
     * @param insureReckonDTO
     * @Author liaojiguang
     * @Date   2021/9/9 17:31
     * @Return
     **/
    @PostMapping("/updateToInsureReckon")
    public WrapperResponse<Boolean> updateToInsureReckon( HttpServletRequest req, HttpServletResponse res,@RequestBody InsureReckonDTO insureReckonDTO) {
        SysUserDTO sysUserDTO = getSession(req, res);
        if (StringUtils.isEmpty(insureReckonDTO.getId())) {
            throw  new AppException("申报失败：请选中需要申报的数据！");
        }
        insureReckonDTO.setDeclareName(sysUserDTO.getName());
        insureReckonDTO.setHospName(sysUserDTO.getHospName());
        insureReckonDTO.setDeclareTime(new Date());
        Map<String,Object> selectMap = new HashMap();
        insureReckonDTO.setHospCode(sysUserDTO.getHospCode());
        selectMap.put("hospCode",sysUserDTO.getHospCode());
        selectMap.put("insureReckonDTO",insureReckonDTO);
        return insureReckonService_consumer.updateToInsureReckon(selectMap);
    }

    /**医药机构清算申请 - 撤销
     * @Method queryInsureReckonInfo
     * @Desrciption
     * @param insureReckonDTO
     * @Author liaojiguang
     * @Date   2021/9/9 17:31
     * @Return
     **/
    @PostMapping("/updateToRevokeInsureReckon")
    public WrapperResponse<Boolean> updateToRevokeInsureReckon(@RequestBody InsureReckonDTO insureReckonDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        if (StringUtils.isEmpty(insureReckonDTO.getClrAppyEvtId())) {
            throw  new AppException("撤销申报失败：请选中需要撤销申报的数据！");
        }
        Map<String,Object> selectMap = new HashMap();
        insureReckonDTO.setHospCode(sysUserDTO.getHospCode());
        insureReckonDTO.setDeclareName(sysUserDTO.getName());
        selectMap.put("hospCode",sysUserDTO.getHospCode());
        selectMap.put("insureReckonDTO",insureReckonDTO);
        return insureReckonService_consumer.updateToRevokeInsureReckon(selectMap);
    }

    /**医药机构清算申请 - 删除
     * @Method queryInsureReckonInfo
     * @Desrciption
     * @param insureReckonDTO
     * @Author liaojiguang
     * @Date   2021/9/9 17:31
     * @Return
     **/
    @PostMapping("/deleteInsureReckon")
    public WrapperResponse<Boolean> deleteInsureReckon(@RequestBody InsureReckonDTO insureReckonDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        if (StringUtils.isEmpty(insureReckonDTO.getId())) {
            throw  new AppException("删除失败：请选中需要删除的数据！");
        }
        Map<String,Object> selectMap = new HashMap();
        insureReckonDTO.setHospCode(sysUserDTO.getHospCode());
        selectMap.put("hospCode",sysUserDTO.getHospCode());
        selectMap.put("insureReckonDTO",insureReckonDTO);
        return insureReckonService_consumer.deleteInsureReckon(selectMap);
    }

    /**医药机构清算申请 - 获取清算机构
     * @Method getInsureClrOptinsByRegCode
     * @Desrciption
     * @param insureReckonDTO
     * @Author liaojiguang
     * @Date   2021/9/9 17:31
     * @Return
     **/
    @GetMapping("/getInsureClrOptinsByRegCode")
    public WrapperResponse<List<String>> getInsureClrOptinsByRegCode(InsureReckonDTO insureReckonDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        if (StringUtils.isEmpty(insureReckonDTO.getInsureRegCode())) {
            throw new AppException("医疗机构编码不能为空!");
        }

        Map<String,Object> selectMap = new HashMap();
        insureReckonDTO.setHospCode(sysUserDTO.getHospCode());
        selectMap.put("hospCode",sysUserDTO.getHospCode());
        selectMap.put("insureReckonDTO",insureReckonDTO);
        return insureReckonService_consumer.getInsureClrOptinsByRegCode(selectMap);
    }

    /**医疗机构月结算申请汇总信息分页查询-3693
     * @Method queryInsureMonSettleApplyInfo
     * @Desrciption
     * @param insureReckonDTO
     * @Author liaojiguang
     * @Date   2021/9/9 17:31
     * @Return
     **/
    @GetMapping("/queryInsureMonSettleApplyInfo")
    public WrapperResponse<PageDTO> queryInsureMonSettleApplyInfo(InsureReckonDTO insureReckonDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);

        Map<String,Object> selectMap = new HashMap();
        insureReckonDTO.setHospCode(sysUserDTO.getHospCode());
        selectMap.put("hospCode",sysUserDTO.getHospCode());
        selectMap.put("insureReckonDTO",insureReckonDTO);
        return insureReckonService_consumer.queryInsureMonSettleApplyInfo(selectMap);
    }

    /**获取清算机构 -3694
     * @Method queryInsureClrOptinsInfo
     * @Desrciption
     * @param insureReckonDTO
     * @Author liaojiguang
     * @Date   2021/9/22 09:15
     * @Return
     **/
    @GetMapping("/queryInsureClrOptinsInfo")
    public WrapperResponse<PageDTO> queryInsureClrOptinsInfo(InsureReckonDTO insureReckonDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);

        Map<String,Object> selectMap = new HashMap();
        insureReckonDTO.setHospCode(sysUserDTO.getHospCode());
        selectMap.put("hospCode",sysUserDTO.getHospCode());
        selectMap.put("insureReckonDTO",insureReckonDTO);
        return insureReckonService_consumer.queryInsureClrOptinsInfo(selectMap);
    }

     /** 获取清算汇总明细 -3695
      * @param insureReckonDTO
      * @Method queryInsureSettleApplyInfo
      * @Desrciption 获取清算汇总明细
      * @Author liaojiguang
      * @Date 2021/9/22 09:15
      * @Return
     **/
     @GetMapping("/queryInsureSettleApplyInfo")
     public WrapperResponse<PageDTO> queryInsureSettleApplyInfo(InsureReckonDTO insureReckonDTO, HttpServletRequest req, HttpServletResponse res) {
         SysUserDTO sysUserDTO = getSession(req, res);

         Map<String,Object> selectMap = new HashMap();
         insureReckonDTO.setHospCode(sysUserDTO.getHospCode());
         selectMap.put("hospCode",sysUserDTO.getHospCode());
         selectMap.put("insureReckonDTO",insureReckonDTO);
         return insureReckonService_consumer.queryInsureSettleApplyInfo(selectMap);
     }

    /** 获取暂扣明细信息 -3696
     * @param insureReckonDTO
     * @Method queryInsureDetDetlList
     * @Desrciption 获取暂扣明细信息
     * @Author liaojiguang
     * @Date 2021/9/22 09:15
     * @Return
     **/
    @GetMapping("/queryInsureDetDetlList")
    public WrapperResponse<PageDTO> queryInsureDetDetlList(InsureReckonDTO insureReckonDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);

        Map<String,Object> selectMap = new HashMap();
        insureReckonDTO.setHospCode(sysUserDTO.getHospCode());
        selectMap.put("hospCode",sysUserDTO.getHospCode());
        selectMap.put("insureReckonDTO",insureReckonDTO);
        return insureReckonService_consumer.queryInsureDetDetlList(selectMap);
    }

    /** 医疗机构月结算报表pdf文档 -3697
     * @param insureReckonDTO
     * @Method getImportClredReportPdf
     * @Desrciption 医疗机构月结算报表pdf文档
     * @Author liaojiguang
     * @Date 2021/9/22 09:15
     * @Return
     **/
    @GetMapping("/getImportClredReportPdf")
    public WrapperResponse<Map<String,Object>> getImportClredReportPdf(InsureReckonDTO insureReckonDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);

        Map<String,Object> selectMap = new HashMap();
        insureReckonDTO.setHospCode(sysUserDTO.getHospCode());
        selectMap.put("hospCode",sysUserDTO.getHospCode());
        selectMap.put("insureReckonDTO",insureReckonDTO);
        return insureReckonService_consumer.getImportClredReportPdf(selectMap);
    }

    /** 获取拨付单信息 - 3704
     * @param insureReckonDTO
     * @Method queryInsureAppropriationList
     * @Desrciption 获取拨付单信息
     * @Author liaojiguang
     * @Date 2021/9/22 09:15
     * @Return
     **/
    @GetMapping("/queryInsureAppropriationList")
    public WrapperResponse<PageDTO> queryInsureAppropriationList(InsureReckonDTO insureReckonDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);

        Map<String,Object> selectMap = new HashMap();
        insureReckonDTO.setHospCode(sysUserDTO.getHospCode());
        selectMap.put("hospCode",sysUserDTO.getHospCode());
        selectMap.put("insureReckonDTO",insureReckonDTO);
        return insureReckonService_consumer.queryInsureAppropriationList(selectMap);
    }

    /** 获取拨付单信息 - 3701
     * @param insureReckonDTO
     * @Method queryInsureAccountInfo
     * @Desrciption 中心对账信息查询
     * @Author liaojiguang
     * @Date 2021/9/22 09:15
     * @Return
     **/
    @GetMapping("/queryInsureAccountInfo")
    public WrapperResponse<List<Map<String,Object>>> queryInsureAccountInfo(InsureReckonDTO insureReckonDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);

        Map<String,Object> selectMap = new HashMap();
        insureReckonDTO.setHospCode(sysUserDTO.getHospCode());
        selectMap.put("hospCode",sysUserDTO.getHospCode());
        selectMap.put("insureReckonDTO",insureReckonDTO);
        return insureReckonService_consumer.queryInsureAccountInfo(selectMap);
    }

    /** 获取基金明细信息 - 3702
     * @param insureReckonDTO
     * @Method queryInsureDetailFundList
     * @Desrciption 获取基金明细信息
     * @Author liaojiguang
     * @Date 2021/9/22 09:15
     * @Return
     **/
    @GetMapping("/queryInsureDetailFundList")
    public WrapperResponse<PageDTO> queryInsureDetailFundList(InsureReckonDTO insureReckonDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);

        Map<String,Object> selectMap = new HashMap();
        insureReckonDTO.setHospCode(sysUserDTO.getHospCode());
        selectMap.put("hospCode",sysUserDTO.getHospCode());
        selectMap.put("insureReckonDTO",insureReckonDTO);
        return insureReckonService_consumer.queryInsureDetailFundList(selectMap);
    }

    /** 获取结算明细信息 - 3703
     * @param insureReckonDTO
     * @Method queryInsureSetlDetlList
     * @Desrciption 获取结算明细信息
     * @Author liaojiguang
     * @Date 2021/9/22 09:15
     * @Return
     **/
    @GetMapping("/queryInsureSetlDetlList")
    public WrapperResponse<PageDTO> queryInsureSetlDetlList(InsureReckonDTO insureReckonDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);

        Map<String,Object> selectMap = new HashMap();
        insureReckonDTO.setHospCode(sysUserDTO.getHospCode());
        selectMap.put("hospCode",sysUserDTO.getHospCode());
        selectMap.put("insureReckonDTO",insureReckonDTO);
        return insureReckonService_consumer.queryInsureSetlDetlList(selectMap);
    }

    /** 获取医保对账汇总查询 - 3699
     * @param insureReckonDTO
     * @Method queryInsureSetlDetlList
     * @Desrciption 获取医保对账汇总查询
     * @Author liaojiguang
     * @Date 2021/9/22 09:15
     * @Return
     **/
    @GetMapping("/queryInsureTotlStmtInfo")
    public WrapperResponse<PageDTO> queryInsureTotlStmtInfo(InsureReckonDTO insureReckonDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);

        Map<String,Object> selectMap = new HashMap();
        insureReckonDTO.setHospCode(sysUserDTO.getHospCode());
        selectMap.put("hospCode",sysUserDTO.getHospCode());
        selectMap.put("insureReckonDTO",insureReckonDTO);
        return insureReckonService_consumer.queryInsureTotlStmtInfo(selectMap);
    }

}
