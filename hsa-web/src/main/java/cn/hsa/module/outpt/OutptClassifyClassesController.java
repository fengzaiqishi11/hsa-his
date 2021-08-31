package cn.hsa.module.outpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassesDoctorDTO;
import cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassifyClassesDTO;
import cn.hsa.module.outpt.outptclassifyclasses.service.OutptClassifyClassesService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.DateUtils;
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
 * @Class_name: OutptClassifyClassesController
 * @Describe:
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/8/11 14:47
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/outpt/outptClassifyClasses")
@Slf4j
public class OutptClassifyClassesController extends BaseController {

    /**
     * 注入dubbo消费者
     */
    @Resource
    OutptClassifyClassesService outptClassifyClassesService_customer;



    /**
     * @Menthod getById
     * @Desrciption  通过ID查询单条数据
     * @param outptClassifyClassesDTO 主键id
     * @Author xingyu.xie
     * @Date   2020/8/11 9:40
     * @Return cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassifyClassesDTO
     **/
    @GetMapping("/getClassifyClassesById")
    public WrapperResponse<OutptClassifyClassesDTO> getClassifyClassesById(OutptClassifyClassesDTO outptClassifyClassesDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptClassifyClassesDTO.setHospCode(userDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",userDTO.getHospCode());
        map.put("outptClassifyClassesDTO",outptClassifyClassesDTO);
        return outptClassifyClassesService_customer.getClassifyClassesById(map);
    }

    /**
     * @Menthod queryDoctorByOuptClassify
     * @Desrciption  通过挂号类别id拿到拥有此挂号类别科室操作权限的医生职工
     * @param sysUserDTO
     * @Author xingyu.xie
     * @Date   2020/10/26 16:44
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
     **/
    @PostMapping("/queryDoctorByOuptClassify")
    public WrapperResponse<Map> queryDoctorByOuptClassify(@RequestBody SysUserDTO sysUserDTO,HttpServletRequest req, HttpServletResponse res) {
        // 使用SysUserDTO作为临时数据传输对象
        HashMap mapUser = new HashMap();
        SysUserDTO userDTO = getSession(req, res) ;
        mapUser.put("hospCode",userDTO.getHospCode());
        // 职工类型
        mapUser.put("workTypeCode",sysUserDTO.getIds());
        //挂号类别id
        mapUser.put("cyId", sysUserDTO.getKeyword());
        return outptClassifyClassesService_customer.queryDoctorByOuptClassify(mapUser);
    }


    /**
     * @Menthod mergeSelectDorctorAndAllDoctor
     * @Desrciption
     * @param1 id  挂号类别排班的id
     * @param2 workTypeCode 员工类型（医生护士等）
     * @param3 deptId 科室id
     * @Author xingyu.xie
     * @Date   2020/9/1 10:55
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassesDoctorDTO>>
     **/
    @PostMapping("/mergeSelectDorctorAndAllDoctor")
    public WrapperResponse<Map> mergeSelectDorctorAndAllDoctor(@RequestBody SysUserDTO sysUserDTO,HttpServletRequest req, HttpServletResponse res) {
        // 使用SysUserDTO作为临时数据传输对象
        HashMap mapUser = new HashMap();
        SysUserDTO userDTO = getSession(req, res) ;
        mapUser.put("hospCode",userDTO.getHospCode());
        // 班次id
        mapUser.put("id",sysUserDTO.getHospName());
        // 挂号类别排班表的id
        mapUser.put("id",sysUserDTO.getId());
        // 职工类型
        mapUser.put("workTypeCode",sysUserDTO.getIds());
        //挂号类别id
        mapUser.put("cyId", sysUserDTO.getKeyword());
        return outptClassifyClassesService_customer.mergeSelectDorctorAndAllDoctor(mapUser);
    }

    /**
     * @Menthod queryAll
     * @Desrciption  查询所有的挂号类别排班记录
     * @Author xingyu.xie
     * @Date   2020/8/11 9:40
     * @Return java.util.List<cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassifyClassesDTO>
     **/
    @GetMapping("/queryClassifyClassesAll")
    public WrapperResponse<List<OutptClassifyClassesDTO>> queryClassifyClassesAll(OutptClassifyClassesDTO outptClassifyClassesDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptClassifyClassesDTO.setHospCode(userDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",userDTO.getHospCode());
        map.put("outptClassifyClassesDTO",outptClassifyClassesDTO);
        return outptClassifyClassesService_customer.queryClassifyClassesAll(map);
    }


    /**
     * @Menthod queryPage
     * @Desrciption  通过实体作为筛选分页条件查询挂号类别排班
     * @param outptClassifyClassesDTO 挂号类别排班数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/11 9:36
     * @Return java.util.List<cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassifyClassesDTO>
     **/
    @GetMapping("/queryClassifyClassesPage")
    public WrapperResponse<PageDTO> queryClassifyClassesPage(OutptClassifyClassesDTO outptClassifyClassesDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptClassifyClassesDTO.setHospCode(userDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",userDTO.getHospCode());
        map.put("outptClassifyClassesDTO",outptClassifyClassesDTO);
        return outptClassifyClassesService_customer.queryClassifyClassesPage(map);
    }


    /**
     * @Menthod queryClassesDoctorByClassifyClassesId
     * @Desrciption 通过挂号类别排班表的id去查询医生排班记录
     * @param outptClassifyClassesDTO 挂号类别排班数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/11 9:35
     * @Return java.util.List<cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassesDoctorDTO>
     **/
    @GetMapping("/queryClassesDoctorByClassifyClassesId")
    public WrapperResponse<List<OutptClassesDoctorDTO>> queryClassesDoctorByClassifyClassesId(OutptClassifyClassesDTO outptClassifyClassesDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptClassifyClassesDTO.setHospCode(userDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",userDTO.getHospCode());
        map.put("outptClassifyClassesDTO",outptClassifyClassesDTO);
        return outptClassifyClassesService_customer.queryClassesDoctorByClassifyClassesId(map);
    }

    /**
     * @Menthod queryClassesDoctorAllGroupByCyId
     * @Desrciption 1.通过科室id进行分组
     *              2.通过挂号类别id分组
     *              3.通过星期几进行分组
     * @param outptClassifyClassesDTO 挂号类别排班数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/11 9:35
     * @Return java.util.List<cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassesDoctorDTO>
     *
     * @return*/
    @GetMapping("/queryClassesDoctorAllGroupByCyId")
    public WrapperResponse<Map<String, Map<String, Map<String, List<OutptClassesDoctorDTO>>>>> queryClassesDoctorAllGroupByCyId(OutptClassifyClassesDTO outptClassifyClassesDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
         outptClassifyClassesDTO.setHospCode(userDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",userDTO.getHospCode());
        map.put("outptClassifyClassesDTO",outptClassifyClassesDTO);
        return outptClassifyClassesService_customer.queryClassesDoctorAllGroupByCyId(map);
    }

    /**
     * @Menthod insertClassifyClasses
     * @Desrciption  新增多条挂号类别排班记录和多条医生排班记录
     * @param outptClassifyClassesDTO 挂号类别排班数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/11 9:41
     * @Return boolean
     **/
    @PostMapping("/insertClassifyClassesAndClassesDoctor")
    public WrapperResponse<Boolean> insertClassifyClassesAndClassesDoctor(@RequestBody OutptClassifyClassesDTO outptClassifyClassesDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptClassifyClassesDTO.setHospCode(userDTO.getHospCode());
        outptClassifyClassesDTO.setCrteId(userDTO.getId());
        outptClassifyClassesDTO.setCrteName(userDTO.getName());
        outptClassifyClassesDTO.setCrteTime(DateUtils.getNow());
        Map map = new HashMap();
        map.put("hospCode",userDTO.getHospCode());
        map.put("outptClassifyClassesDTO",outptClassifyClassesDTO);
        return outptClassifyClassesService_customer.insertClassifyClassesAndClassesDoctor(map);
    }


    /**
     * @Menthod updateClassifyClasses
     * @Desrciption  修改单条挂号类别排班记录和修改多条医生排班记录
     * @param outptClassifyClassesDTO 挂号类别排班数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/11 9:41
     * @Return boolean
     **/
    @PostMapping("/updateClassifyClassesAndClassesDoctor")
    public WrapperResponse<Boolean> updateClassifyClassesAndClassesDoctor(@RequestBody OutptClassifyClassesDTO outptClassifyClassesDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptClassifyClassesDTO.setHospCode(userDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",userDTO.getHospCode());
        map.put("outptClassifyClassesDTO",outptClassifyClassesDTO);
        return outptClassifyClassesService_customer.updateClassifyClassesAndClassesDoctor(map);
    }

    /**
     * @Menthod updateStatus
     * @Desrciption  作废和启用多条挂号排班数据
     * @param outptClassifyClassesDTO 挂号类别排班数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/11 9:42
     * @Return boolean
     **/
    @PostMapping("/updateStatus")
    public WrapperResponse<Boolean> updateStatus(@RequestBody OutptClassifyClassesDTO outptClassifyClassesDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptClassifyClassesDTO.setHospCode(userDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",userDTO.getHospCode());
        map.put("outptClassifyClassesDTO",outptClassifyClassesDTO);
        return outptClassifyClassesService_customer.updateStatus(map);
    }

    /**
     * @Describe: 查询分诊方式代码
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/21 11:00
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     **/
    @PostMapping("/getTriageCode")
    public WrapperResponse<Map<String, List<Map<String, Object>>>> getFZFSCodeDetailByCode(@RequestBody Map<String,Object> reqMap,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        reqMap.put("hospCode",userDTO.getHospCode());
        return outptClassifyClassesService_customer.getFZFSCodeDetailByCode(reqMap);
    }
}
