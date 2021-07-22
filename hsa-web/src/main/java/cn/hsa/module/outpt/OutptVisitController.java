package cn.hsa.module.outpt;


import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.outpt.visit.service.OutptVisitService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import groovy.util.logging.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt
 * @Class_name:: OutptSkinController
 * @Description: 皮试处理控制层
 * @Author: zhangxuan
 * @Date: 2020-08-14 10:14
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/outpt/visit")
@Slf4j
public class OutptVisitController extends BaseController {
    /**
     * 皮试处理dubbo‘消费者接口
     */
    @Resource
    private OutptVisitService outptVisitService_consumer;


    /**
     * @Method queryVisitRecords
     * @Desrciption 通过档案ID查询病人就诊记录
     * @Param
     * [outptVisitDTO]
     * @Author liaojunjie
     * @Date   2020/9/25 9:03
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/queryVisitRecords")
    public WrapperResponse<PageDTO> queryVisitRecords(OutptVisitDTO outptVisitDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptVisitDTO.setHospCode(userDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",userDTO.getHospCode());
        map.put("outptVisitDTO",outptVisitDTO);

        return outptVisitService_consumer.queryVisitRecords(map);
    }
}
