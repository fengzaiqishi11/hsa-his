package cn.hsa.module.outpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.executioncardprint.service.OutptExecutionCardPrintService;
import cn.hsa.module.outpt.infusionRegister.dto.OutptInfusionRegisterDTO;
import cn.hsa.module.phar.pharoutdistributedrug.dto.PharOutReceiveDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt
 * @Class_name:: OutptExecutionCardPrintController
 * @Description: 获取执行卡打印数据
 * @Author: zhangxuan
 * @Date: 2020-08-17 17:10
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/outpt/executionCardPrint")
@Slf4j
public class OutptExecutionCardPrintController extends BaseController {
    /*
    * 执行卡打印dubbo消费者接口
    * */
    @Resource
    private OutptExecutionCardPrintService outptExecutionCardPrintService_consumer;
    /**
     * @Method
     * @Desrciption  执行卡打印信息查询
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-08-17 17:14
     * @Return map
    **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(OutptInfusionRegisterDTO outptInfusionRegisterDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptInfusionRegisterDTO.setHospCode(userDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",userDTO.getHospCode());
        map.put("outptInfusionRegisterDTO",outptInfusionRegisterDTO);
        return outptExecutionCardPrintService_consumer.queryPage(map);
    }
    /**
     * @Method update
     * @Desrciption
     * @Param map
     * [outptInfusionRegisterDTO]
     * @Author zhangxuan
     * @Date   2020-08-26 11:58
     * @Return map
     **/
    @PostMapping("/update")
    public WrapperResponse<Boolean> update(@RequestBody OutptInfusionRegisterDTO outptInfusionRegisterDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptInfusionRegisterDTO.setHospCode(userDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",userDTO.getHospCode());
        map.put("outptInfusionRegisterDTO",outptInfusionRegisterDTO);
        return outptExecutionCardPrintService_consumer.update(map);
    }


    /**
     * @Method queryInfusionRegisterList
     * @Desrciption  门诊配药输液执行卡查询
     * @param pharOutReceiveDTO
     * @Author liuliyun
     * @Date   2021-11-04 13:51
     * @Return WrapperResponse<List<OutptInfusionRegisterDTO>>
     **/
    @GetMapping("/queryInfusionRegisterList")
    public WrapperResponse<List<OutptInfusionRegisterDTO>> queryInfusionRegisterList(PharOutReceiveDTO pharOutReceiveDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        pharOutReceiveDTO.setHospCode(userDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",userDTO.getHospCode());
        map.put("pharOutReceiveDTO",pharOutReceiveDTO);
        return outptExecutionCardPrintService_consumer.queryInfusionRegisterList(map);
    }
}
