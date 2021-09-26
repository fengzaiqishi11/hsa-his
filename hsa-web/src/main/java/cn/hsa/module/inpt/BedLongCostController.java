package cn.hsa.module.inpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.NoRepeatSubmit;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.hospital.service.CenterHospitalService;
import cn.hsa.module.inpt.longcost.service.BedLongCostService;
import cn.hsa.module.inpt.medical.dto.MedicalAdviceDTO;
import cn.hsa.module.inpt.medical.service.MedicalAdviceService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.DateUtils;
import cn.hsa.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt
 * @Class_name: BedLongCostController
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2020/10/28 9:57
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@RestController
@RequestMapping("/web/inpt/bedLongCost")
@Slf4j
public class BedLongCostController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(BedLongCostController.class);

    @Resource
    private MedicalAdviceService medicalAdviceService_consumer;

    @Resource
    private BedLongCostService bedLongCostService ;

    @Resource
    private RedisUtils redisUtils;

    /**
     * @Method: longCost
     * @Description: 长期费用滚动入口
     * @Param: []
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/31 14:26
     * @Return: void
     **/
//    @Scheduled(cron = "0 40 23 ? * *") //每天晚上23:40分执行
    @GetMapping("/longCost")
    @NoRepeatSubmit
    public WrapperResponse<Boolean> longCost(HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        WrapperResponse<Boolean> response = WrapperResponse.success(true);
        logger.info("====================["+sysUserDTO.getHospCode()+"]长期费用开始:"+DateUtils.format("yyyy-MM-dd HH:mm:ss"));
        Map map = new HashMap();
        String key = sysUserDTO.getHospCode()+"_LONGCOST";
        if(redisUtils.hasKey(key)){
            throw  new RuntimeException("当前医院有正在执行的长期费用,请耐心等待!");
        }
        redisUtils.set(key,key);
        try {
            MedicalAdviceDTO medicalAdviceDTO = new MedicalAdviceDTO();
            medicalAdviceDTO.setHospCode(sysUserDTO.getHospCode());
            medicalAdviceDTO.setCheckTime(DateUtils.getNow());
            medicalAdviceDTO.setCheckName("护士站长期医嘱费用手动执行");
            medicalAdviceDTO.setCheckId("-1");
            map.put("hospCode", sysUserDTO.getHospCode());
            map.put("medicalAdviceDTO", medicalAdviceDTO);
            medicalAdviceService_consumer.longCost(map);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("["+sysUserDTO.getHospCode()+"]"+e.getMessage());
        } finally {
            logger.info("====================["+sysUserDTO.getHospCode()+"]长期医嘱费用结束:"+DateUtils.format("yyyy-MM-dd HH:mm:ss"));
        }

        try {
            map.clear();
            map.put("isAuto", Constants.SF.S);
            map.put("userId", "-1");
            map.put("userName", "护士站长期床位费用手动执行");
            map.put("hospCode", sysUserDTO.getHospCode());
            //床位费当天费用不生成,所以-1天
            map.put("endTime",DateUtils.dateAdd(new Date(),-1));
            // 滚动医院长期床位费用
            bedLongCostService.saveBedLongCost(map);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("["+sysUserDTO.getHospCode()+"]"+e.getMessage());
        } finally {
            logger.info("====================["+sysUserDTO.getHospCode()+"]长期床位费用结束:"+DateUtils.format("yyyy-MM-dd HH:mm:ss"));
        }

        redisUtils.del(key);

        return response;
    }
}
