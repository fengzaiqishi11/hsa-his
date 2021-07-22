package cn.hsa.module.inpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.nurse.dto.InptNurseRecordDTO;
import cn.hsa.module.inpt.nurse.service.NursingRecordService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt
 * @Class_name: NursingRecordController
 * @Describe: 护理记录控制器
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2020/9/16 9:04
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@RestController
@RequestMapping("/web/inpt/nursingRecord")
public class NursingRecordController extends BaseController {

    @Resource
    private NursingRecordService nursingRecordService_consumer;

    /**
     * @Method queryInptVisitAll
     * @Desrciption 查询所有住院病人
     * @Param inptVisitDTO-住院病人DTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/16 9:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/queryInptVisitAll")
    public WrapperResponse<PageDTO> queryInptVisitAll(InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode()); //当前登陆机构
        inptVisitDTO.setInDeptId(sysUserDTO.getLoginBaseDeptDTO().getId()); //当前登陆科室
        if (StringUtils.isEmpty(inptVisitDTO.getStatusCode())) {
            inptVisitDTO.setStatusCode(Constants.BRZT.ZY); //在院病人
        }
        Map map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptVisitDTO", inptVisitDTO);
        return nursingRecordService_consumer.queryInptVisitAll(map);
    }
    /**
     * @Method queryInptVisitAll
     * @Desrciption 查询所有住院病人，预交金缴纳情况
     * @Param inptVisitDTO-住院病人DTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/16 9:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/queryAcceptGold")
    public WrapperResponse<List<Map<String, Object>>> queryAcceptGold (InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode()); //当前登陆机构
//        if (StringUtils.isEmpty(inptVisitDTO.getStatusCode())) {
//            inptVisitDTO.setStatusCode(Constants.BRZT.ZY); //在院病人
//        }
        Map map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptVisitDTO", inptVisitDTO);
        return nursingRecordService_consumer.queryAcceptGold (map);
    }

    /**
     * @Method queryNursingRecord
     * @Desrciption 分页查询护理单记录
     * @Param inptNurseRecordDTO-护理记录DTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/16 9:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/queryNursingRecord")
    public WrapperResponse<PageDTO> queryNursingRecord(InptNurseRecordDTO inptNurseRecordDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        inptNurseRecordDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptNurseRecordDTO", inptNurseRecordDTO);
        return nursingRecordService_consumer.queryNursingRecord(map);
    }

    /**
     * @Method queryNurseRecordByPrint
     * @Desrciption 护理记录打印接口
     * @Param inptNurseRecordDTO-护理记录DTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/16 9:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List<InptNurseRecordDTO>>
     **/
    @GetMapping("/queryNurseRecordByPrint")
    public WrapperResponse<List<InptNurseRecordDTO>> queryNurseRecordByPrint(InptNurseRecordDTO inptNurseRecordDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        if (StringUtils.isEmpty(inptNurseRecordDTO.getBnoId())) {
            throw new RuntimeException("未选择护理单据");
        }
        if (StringUtils.isEmpty(inptNurseRecordDTO.getVisitId())){
            throw new RuntimeException("未选择就诊人");
        }
        inptNurseRecordDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptNurseRecordDTO", inptNurseRecordDTO);
        return nursingRecordService_consumer.queryNurseRecordByPrint(map);
    }

    /**
     * @Method save
     * @Desrciption 保存护理记录(新增 、 编辑)
     * @Param List<InptNurseRecordDTO> 护理记录list
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/16 9:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/save")
    public WrapperResponse<Boolean> save(@RequestBody List<InptNurseRecordDTO> saveList, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        if (saveList == null || saveList.size() <= 0) {
            throw new RuntimeException("未选择要保存的护理记录");
        }
        saveList.forEach(inptNurseRecordDTO -> inptNurseRecordDTO.setHospCode(sysUserDTO.getHospCode()));
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("saveList", saveList);
        map.put("userId", sysUserDTO.getId());
        map.put("userName", sysUserDTO.getName());
        return nursingRecordService_consumer.save(map);
    }

    /**
     * @Method delete
     * @Desrciption 删除护理记录
     * @Param List<InptNurseRecordDTO> 护理记录list
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/16 9:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/delete")
    public WrapperResponse<Boolean> delete(@RequestBody List<InptNurseRecordDTO> delList, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        if (delList == null || delList.size() <= 0) {
            throw new RuntimeException("未选择要删除的护理记录");
        }
        delList.forEach(inptNurseRecordDTO -> inptNurseRecordDTO.setHospCode(sysUserDTO.getHospCode()));
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("delList", delList);
        map.put("userId", sysUserDTO.getId());
        map.put("userName", sysUserDTO.getName());
        return nursingRecordService_consumer.delete(map);
    }

    /**
     * @Method addDaySummary
     * @Desrciption 添加日间小结
     * @Param inptNurseRecordDTO-护理记录
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 14:46
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/addDaySummary")
    public WrapperResponse<Boolean> addDaySummary(@RequestBody InptNurseRecordDTO inptNurseRecordDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        //校验参数
        if (StringUtils.isEmpty(inptNurseRecordDTO.getStartTime())) {
            throw new RuntimeException("日间小结开始时间不能为空");
        }
        if (StringUtils.isEmpty(inptNurseRecordDTO.getEndTime())) {
            throw new RuntimeException("日间小结结束时间不能为空");
        }
        if (StringUtils.isEmpty(inptNurseRecordDTO.getBnoId())) {
            throw new RuntimeException("未选择护理记录单类型");
        }
        if (StringUtils.isEmpty(inptNurseRecordDTO.getVisitId())) {
            throw new RuntimeException("未选择患者");
        }

        //封装参数
        inptNurseRecordDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptNurseRecordDTO", inptNurseRecordDTO);
        map.put("userId", sysUserDTO.getId());
        map.put("userName", sysUserDTO.getName());
        return nursingRecordService_consumer.addDaySummary(map);
    }

    /**
     * @Method getValue
     * @Desrciption 获取分割参数
     * @Param inptNurseRecordDTO-护理记录
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 14:46
     * @Return Map<String,Object>
     **/
    @GetMapping("/getValue")
    public WrapperResponse<Map<String,Object>> getValue(InptNurseRecordDTO inptNurseRecordDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        inptNurseRecordDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptNurseRecordDTO", inptNurseRecordDTO);
        return nursingRecordService_consumer.getValue(map);
//        return null;
    }

    /**
     * @Method getMaxGroupNo
     * @Desrciption 获取当前人员最大组号
     * @Param inptNurseRecordDTO-护理记录
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 14:46
     * @Return Integer
     **/
    @GetMapping("/getMaxGroupNo")
    public WrapperResponse<Integer> getMaxGroupNo(InptNurseRecordDTO inptNurseRecordDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        inptNurseRecordDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptNurseRecordDTO", inptNurseRecordDTO);
        return nursingRecordService_consumer.getMaxGroupNo(map);
    }
}
