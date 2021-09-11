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
        if (StringUtils.isEmpty(insureReckonDTO.getId())) {
            throw  new AppException("撤销申报失败：请选中需要撤销申报的数据！");
        }
        Map<String,Object> selectMap = new HashMap();
        insureReckonDTO.setHospCode(sysUserDTO.getHospCode());
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


}
