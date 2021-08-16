package cn.hsa.module.insure;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.module.service.InsureIndividualCostService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.ListUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Package_name: cn.hsa.module.insure
 * @Class_name:: InsureIndividualCostController
 * @Description: 医保费用控制层
 * @Author: fuhui
 * @Date: 2020/11/5 10:42 
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/insure/insureIndividualCost")
@Slf4j
public class InsureIndividualCostController extends BaseController {

    @Resource
    private InsureIndividualCostService insureIndividualCostService_consumer;

    /**
     * @Method queryPage()
     * @Desrciption  分页查询医保住院费用传输数据
     * @Param insureIndividualCostDTO数据传输对象
     *
     * @Author fuhui
     * @Date   2020/11/5 10:58
     * @Return insureIndividualCostDTO分页数据传输对象
    **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("hospCode",sysUserDTO.getHospCode());
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
        inptVisitDTO.setCrteId(sysUserDTO.getId());//创建人id
        inptVisitDTO.setCrteName(sysUserDTO.getName());//创建人姓名
        param.put("inptVisitDTO",inptVisitDTO);
        return insureIndividualCostService_consumer.queryPage(param);
    }

    /**
     * @Method queryUnMatchPage
     * @Desrciption  查询没有匹配的费用数据集合
     * @Param
     *
     * @Author fuhui
     * @Date   2021/6/20 9:55
     * @Return
    **/
    @GetMapping("/queryUnMatchPage")
    public WrapperResponse<PageDTO> queryUnMatchPage(InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> param = new HashMap<String,Object>();
        String ids = inptVisitDTO.getVisitIds();
        if (!StringUtils.isEmpty(ids)) {
            List<String> idsList = Arrays.asList(ids.split(","));
            inptVisitDTO.setIds(idsList);
        }
        param.put("hospCode",sysUserDTO.getHospCode());
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
        inptVisitDTO.setCrteId(sysUserDTO.getId());//创建人id
        inptVisitDTO.setCrteName(sysUserDTO.getName());//创建人姓名
        param.put("inptVisitDTO",inptVisitDTO);
        return insureIndividualCostService_consumer.queryUnMatchPage(param);
    }

    /**
     * @Method feeTransmit()
     * @Desrciption 住院病人费用传输
     * @Param
     *
     * @Author fuhui
     * @Date   2020/11/10 15:18
     * @Return
     **/
    @PostMapping("/feeTransmit")
    public WrapperResponse<Map<String,Object>> feeTransmit(@RequestBody InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res){
        if(StringUtils.isEmpty(inptVisitDTO.getId())){
            throw new AppException("费用传输参数为空");
        }
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map =new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
        inptVisitDTO.setCrteId(sysUserDTO.getId());
        inptVisitDTO.setCrteName(sysUserDTO.getName());
        inptVisitDTO.setCode(sysUserDTO.getCode());
        inptVisitDTO.setUserCode(sysUserDTO.getCode());
        map.put("inptVisitDTO",inptVisitDTO);
        return insureIndividualCostService_consumer.saveFeeTransmit(map);
    }


    /**
     * @Method feesTransmit()
     * @Desrciption 住院病人批量费用传输
     * @Param inptVisitDTO
     *
     * @Author 廖继广
     * @Date   2021/04/13 19:51
     * @Return
     **/
    @PostMapping("/feesTransmit")
    public WrapperResponse<Boolean> feesTransmit(@RequestBody InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        List<String> idLists = inptVisitDTO.getIds();
        if (ListUtils.isEmpty(idLists)) {
            throw new AppException("费用传输参数为空");
        }
        Map<String,Object> map =new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
        inptVisitDTO.setCrteId(sysUserDTO.getId());
        inptVisitDTO.setCrteName(sysUserDTO.getName());
        inptVisitDTO.setCode(sysUserDTO.getCode());
        inptVisitDTO.setUserCode(sysUserDTO.getCode());
        map.put("inptVisitDTO",inptVisitDTO);
        return insureIndividualCostService_consumer.saveFeesTransmit(map);
    }

    /**
     * @Method queryInptPatientPage（）
     * @Desrciption  查询医保住院病人的信息
     * @Param
     *
     * @Author fuhui
     * @Date   2020/11/18 11:00
     * @Return
    **/
    @GetMapping("/queryInptPatient")
    public WrapperResponse<PageDTO> queryInptPatientPage(InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("inptVisitDTO",inptVisitDTO);
        return insureIndividualCostService_consumer.queryInptPatientPage(map);
    }

    /**
     * @Method updateLimitUserFlag
     * @Desrciption  住院医生站开完医嘱保存，填写报销标识以后。修改这些报销标识
     * @Param
     *
     * @Author fuhui
     * @Date   2021/7/20 9:20
     * @Return
     **/
    @PostMapping("/updateLimitUserFlag")
    public WrapperResponse <Boolean> updateLimitUserFlag(@RequestBody Map<String,Object> map ,HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        return insureIndividualCostService_consumer.updateLimitUserFlag(map);
    }

    /**
     * @Method queryInptCostPage
     * @Desrciption  根据就诊id 查询住院费用明细数据
     * @Param
     *
     * @Author fuhui
     * @Date   2021/7/20 13:49
     * @Return
     **/
    @GetMapping("/queryInptCostPage")
    public WrapperResponse<PageDTO> queryInptCostPage(InptVisitDTO inptVisitDTO,HttpServletRequest req, HttpServletResponse res){
        Map<String,Object> map  = new HashMap<>();
        SysUserDTO sysUserDTO = getSession(req, res);
        String hospCode = sysUserDTO.getHospCode();
        map.put("hospCode",hospCode);
        inptVisitDTO.setHospCode(hospCode);
        map.put("inptVisitDTO",inptVisitDTO);
        return insureIndividualCostService_consumer.queryInptCostPage(map);

    }

    /**
     * @Method deleteInptHisCost
     * @Desrciption  删除his本地费用
     * @Param
     *
     * @Author fuhui
     * @Date   2021/8/9 10:59
     * @Return
    **/
    @PostMapping("/deleteInptHisCost")
    public WrapperResponse <Boolean> deleteInptHisCost(@RequestBody InptVisitDTO inptVisitDTO,HttpServletRequest req, HttpServletResponse res){
        Map<String,Object> map  = new HashMap<>();
        SysUserDTO sysUserDTO = getSession(req, res);
        String hospCode = sysUserDTO.getHospCode();
        map.put("hospCode",hospCode);
        inptVisitDTO.setHospCode(hospCode);
        map.put("inptVisitDTO",inptVisitDTO);
        return insureIndividualCostService_consumer.deleteInptHisCost(map);
    }
}
