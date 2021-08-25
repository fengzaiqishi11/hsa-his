package cn.hsa.module.inpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.nurse.service.InptAdviceFeeCheckService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.DateUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 *@Package_name: cn.hsa.module.inpt
 *@Class_name: InptAdviceFeeCheckController
 *@Describe: 医嘱费用核对Controller控制层
 *@Author: LiaoJiGuang
 *@Eamil: jiguang.liao@powersi.com.cn
 *@Date: 2020/9/15 10:25
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/inpt/nurse")
@Slf4j
public class InptAdviceFeeCheckController extends BaseController {

    @Resource
    InptAdviceFeeCheckService inptAdviceFeeCheckService_consumer;


    /**
    * @Method queryInptAdviceFeeCheckPage
    * @Desrciption 医嘱核对分页查询
    * @param inptAdviceDTO
    * @Author LiaoJiGuang
    * @Date   2020/9/15 10:31
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping (value = "/queryInptAdviceFeeCheckPage")
    WrapperResponse<PageDTO> queryInptAdviceFeeCheckPage(InptAdviceDTO inptAdviceDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        inptAdviceDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptAdviceDTO", inptAdviceDTO);

        WrapperResponse<PageDTO> pageDTOWrapperResponse = inptAdviceFeeCheckService_consumer.queryInptAdviceCheckPage(map);
        return pageDTOWrapperResponse;
    }

    /**
     * @Method queryInptPatientPage
     * @Desrciption 医嘱费用核对 - 获取本科室住院在院人员的患者信息
     * @param inptAdviceDTO
     * @Author LiaoJiGuang
     * @Date   2020/9/15 11:53
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping (value = "/queryInptPatientPage")
    WrapperResponse<PageDTO> queryInptPatientPage(InptAdviceDTO inptAdviceDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        inptAdviceDTO.setHospCode(sysUserDTO.getHospCode());
        inptAdviceDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());

        // 当前状态：2:在院 （参考BRZT 字典值）
        inptAdviceDTO.setStatusCode("2");
        map.put("inptAdviceDTO",inptAdviceDTO);

        WrapperResponse<PageDTO> pageDTOWrapperResponse = inptAdviceFeeCheckService_consumer.queryInptPatientPage(map);
        return pageDTOWrapperResponse;
    }

    /**
     * @Method updateAdviceFeeCheck
     * @Desrciption 医嘱费用核对
     * @param adviceIds 医嘱ID字符串集合
     * @Author LiaoJiGuang
     * @Date   2020/9/17 11:23
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @PutMapping(value = "/updateAdviceFeeCheck")
    WrapperResponse<Boolean> updateAdviceFeeCheck(@RequestBody String adviceIds, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
       if (StringUtils.isEmpty(adviceIds)) {
           throw new AppException("参数错误，请刷新重试");
       }
        Map map = new HashMap();
        String str[] = adviceIds.split(",");
        List<String> adviceIdList = Arrays.asList(str);
        List<InptCostDTO> list = new ArrayList<>();
        for (String s: adviceIdList) {
            InptCostDTO inptCostDTO = new InptCostDTO();
            inptCostDTO.setHospCode(sysUserDTO.getHospCode());
            inptCostDTO.setIatId(s);
            inptCostDTO.setIsCheck(Constants.SF.S); // 是否核对
            inptCostDTO.setCheckId(sysUserDTO.getId());
            inptCostDTO.setCheckName(sysUserDTO.getName());
            inptCostDTO.setCheckTime(DateUtils.getNow());
            list.add(inptCostDTO);
        }
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("list",list);
        map.put("adviceIdList",adviceIdList);
        WrapperResponse<Boolean> pageDTOWrapperResponse = inptAdviceFeeCheckService_consumer.updateAdviceFeeCheck(map);
        return pageDTOWrapperResponse;
    }

    /**
     * @Method queryInptAdviceFeeInfoPage
     * @Desrciption 医嘱费用分页查询
     * @param inptAdviceDTO
     * @Author LiaoJiGuang
     * @Date   2020/9/15 10:31
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping (value = "/queryInptAdviceFeeInfoPage")
    WrapperResponse<PageDTO> queryInptAdviceFeeInfoPage(InptAdviceDTO inptAdviceDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        if (StringUtils.isEmpty(inptAdviceDTO.getIatIds())) {
            inptAdviceDTO.setIatIds("@");
        }

        Map<String,Object> map = new HashMap<>();
        inptAdviceDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptAdviceDTO", inptAdviceDTO);
        WrapperResponse<PageDTO> pageDTOWrapperResponse = inptAdviceFeeCheckService_consumer.queryInptAdviceFeeInfoPage(map);
        return pageDTOWrapperResponse;
    }
}
