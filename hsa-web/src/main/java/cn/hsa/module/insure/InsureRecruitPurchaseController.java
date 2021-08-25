package cn.hsa.module.insure;


import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.service.InsureRecruitPurchaseService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure
 * @Class_name:: CommonInterfaceController
 * @Description: 海南招采
 * @Author: yuelong.chen
 * @Date: 2021/8/23 16:17
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/insure/insureRecruitPurchase")
@Slf4j
public class InsureRecruitPurchaseController extends BaseController {

    @Resource
    private InsureRecruitPurchaseService insureRecruitPurchaseService_consumer;


    /**
     * @Method queryAll
     * @Param [map]
     * @description   获取当前医院库存列表
     * @author yuelong.chen
     * @date 2021/8/24 10:50
     * @return
     * @throws
     */
    @GetMapping("/queryHospitalInventory")
    public WrapperResponse<Map<String,Object>> queryHospitalInventory(Map<String,Object> map,HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        return insureRecruitPurchaseService_consumer.queryAll(map);
    }


    /**
     * @Description: 获取当前医院库存列表
     * @Param: [map]
     * @return:
     * @Author: yuelong.chen
     * @Date: 2021/8/24 10:50
     */
    @PostMapping("queryCommoditySalesRecord")
    public WrapperResponse<Map<String, Object>> queryCommoditySalesRecord(@RequestBody Map<String, Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        return insureRecruitPurchaseService_consumer.queryCommoditySalesRecord(map);
    }


}
