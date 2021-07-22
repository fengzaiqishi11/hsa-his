package cn.hsa.module.outpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.clinic.dto.BaseClinicDTO;
import cn.hsa.module.base.clinic.service.BaseClinicService;
import cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassifyClassesDTO;
import cn.hsa.module.outpt.queue.dto.OutptClassesQueueDto;
import cn.hsa.module.outpt.queue.service.OutptClassesQueueService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.SnowflakeUtils;
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
 * @Package_name: cn.hsa.module.outpt
 * @Class_name: OutptClassesQueueController
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2020/8/12 11:35
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/outpt/classesQueue")
@Slf4j
public class OutptClassesQueueController extends BaseController {

    @Resource
    OutptClassesQueueService outptClassesQueueService_consumer;

    /**
     * 分诊室Dubbo消费者接口
     */

    @Resource
    private BaseClinicService baseClinicService_consumer;

    /**
     * @Method queryPage
     * @Desrciption 分页查询
     @params [outptClassesQueueDto]
      * @Author chenjun
     * @Date   2020/10/14 9:24
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(OutptClassesQueueDto outptClassesQueueDto,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptClassesQueueDto.setHospCode(userDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",userDTO.getHospCode());
        map.put("outptClassesQueueDto",outptClassesQueueDto);
        return outptClassesQueueService_consumer.queryPage(map);
    }

    @GetMapping("/queryClassesDoctor")
    public WrapperResponse<List<Map>> queryClassesDoctor(OutptClassifyClassesDTO outptClassifyClassesDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptClassifyClassesDTO.setHospCode(userDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",userDTO.getHospCode());
        map.put("outptClassifyClassesDTO",outptClassifyClassesDTO);
        return outptClassesQueueService_consumer.queryClassesDoctor(map);
    }

    @PostMapping("/saveClassesQueue")
    public WrapperResponse<Boolean> saveClassesQueue(@RequestBody OutptClassesQueueDto outptClassesQueueDto,HttpServletRequest req, HttpServletResponse res) {
        if(null == outptClassesQueueDto){
            throw new AppException("坐诊队列数据为空");
        }
        outptClassesQueueDto.setQueueDate(DateUtils.parse(outptClassesQueueDto.getQueueDateTem(), DateUtils.Y_M_D));
        SysUserDTO userDTO = getSession(req, res) ;
        outptClassesQueueDto.setHospCode(userDTO.getHospCode());
        outptClassesQueueDto.setIsValid("1");
        outptClassesQueueDto.setCrteId(userDTO.getId());
        outptClassesQueueDto.setCrteName(userDTO.getName());
        outptClassesQueueDto.setCrteTime(new Date());
        Map map = new HashMap();
        map.put("hospCode",userDTO.getHospCode());
        map.put("outptClassesQueueDto",outptClassesQueueDto);
        return outptClassesQueueService_consumer.saveClassesQueue(map);
    }
    @PostMapping("/produceQueue")
    public WrapperResponse<Boolean> saveProduceQueue(@RequestBody OutptClassesQueueDto outptClassesQueueDto,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptClassesQueueDto.setId(SnowflakeUtils.getId());
        outptClassesQueueDto.setHospCode(userDTO.getHospCode());
        outptClassesQueueDto.setCrteId(userDTO.getId());
        outptClassesQueueDto.setCrteName(userDTO.getName());
        outptClassesQueueDto.setCrteTime(new Date());
        Map map = new HashMap();
        map.put("hospCode",userDTO.getHospCode());
        map.put("outptClassesQueueDto",outptClassesQueueDto);
        return outptClassesQueueService_consumer.saveProduceQueue(map);
    }

    @PostMapping("/deleteQueue")
    public WrapperResponse<Boolean> deleteQueue(@RequestBody OutptClassesQueueDto outptClassesQueueDto,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptClassesQueueDto.setHospCode(userDTO.getHospCode());
        outptClassesQueueDto.setCrteId(userDTO.getId());
        outptClassesQueueDto.setCrteName(userDTO.getName());
        outptClassesQueueDto.setCrteTime(new Date());
        Map map = new HashMap();
        map.put("hospCode",userDTO.getHospCode());
        map.put("outptClassesQueueDto",outptClassesQueueDto);
        return outptClassesQueueService_consumer.deleteQueue(map);
    }

    /**
     * 获取所有诊室信息
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/21 17:13
     **/
    @GetMapping("/getAllClinicInfo")
    public WrapperResponse<PageDTO> getAllClinicInfo(@RequestParam(value = "deptId",required = false) String deptId1,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        Map<String,Object> mapParam = new HashMap<>();
        BaseClinicDTO baseClinicDTO = new BaseClinicDTO();
        baseClinicDTO.setHospCode(userDTO.getHospCode());
        baseClinicDTO.setPageSize(300);
        baseClinicDTO.setPageNo(0);
        baseClinicDTO.setDeptId(deptId1);
        mapParam.put("hospCode",userDTO.getHospCode());
        mapParam.put("baseClinicDTO",baseClinicDTO);
        return baseClinicService_consumer.queryAll(mapParam);
    }

}