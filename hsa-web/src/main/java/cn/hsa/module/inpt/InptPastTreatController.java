package cn.hsa.module.inpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import cn.hsa.module.inpt.pasttreat.dto.*;
import cn.hsa.module.inpt.pasttreat.service.InptPastTreatService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt
 * @Class_name:: InptPastTreatController
 * @Description: 既往诊疗史控制层
 * @Author: fuhui
 * @Date: 2020/9/17 15:25
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */

@RestController
@RequestMapping("/web/inpt/inptPastTreat")
@Slf4j
public class InptPastTreatController extends BaseController {
    @Resource
    private InptPastTreatService inptPastTreatService_consumer;

    /**
     * @Method queryPastTreatPage()
     * @Desrciption 既往诊疗记录史分页查询
     * @Param 既往诊疗史数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 15:58
     * @Return pageDTO
     **/
    @GetMapping("/queryPastTreatPage")
    public WrapperResponse<PageDTO> queryPastTreatPage(InptPastTreatDTO inptPastTreatDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        inptPastTreatDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptPastTreatDTO", inptPastTreatDTO);
        return inptPastTreatService_consumer.queryPastTreatPage(map);
    }
    /**
     * @Method getPastTreatById()
     * @Desrciption  根据id查询既往诊疗史的数据
     * @Param id：数据ID
     *
     * @Author fuhui
     * @Date   2020/11/3 14:29
     * @Return InptPastTreatDTO数据传输对象
    **/
    @GetMapping("/getPastTreatById")
    public WrapperResponse<InptPastTreatDTO> getPastTreatById(InptPastTreatDTO inptPastTreatDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        inptPastTreatDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptPastTreatDTO", inptPastTreatDTO);
        return inptPastTreatService_consumer.getPastTreatById(map);
    }
    /**
     * @Method insertPastTreat
     * @Desrciption 新增既往诊疗史记录
     * @Param inptPastTreatDTO数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 21:28
     * @Return boolean
     **/
    @PostMapping("/insertPastTreat")
    public WrapperResponse<Boolean> insertPastTreat(@RequestBody InptPastTreatDTO inptPastTreatDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        if (StringUtils.isEmpty(inptPastTreatDTO.getProfileId())) {
            throw new AppException("个人档案Id参数为空");
        }
        if (StringUtils.isEmpty(inptPastTreatDTO.getVisitId())) {
            throw new AppException("就诊Id参数为空");
        }
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        inptPastTreatDTO.setHospCode(sysUserDTO.getHospCode());
        inptPastTreatDTO.setCrteId(sysUserDTO.getId());
        inptPastTreatDTO.setCrteName(sysUserDTO.getName());
        inptPastTreatDTO.setCrteTime(DateUtils.getNow());
        map.put("inptPastTreatDTO", inptPastTreatDTO);
        return inptPastTreatService_consumer.insertPastTreat(map);

    }

    /**
     * @Method deletePastTreat()
     * @Desrciption 删除既往诊疗史的记录数据
     * @Param id:既往诊疗史的id
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    @PostMapping("/deletePastTreat")
    public WrapperResponse<Boolean> deletePastTreat(@RequestBody InptPastTreatDTO inptPastTreatDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        if (StringUtils.isEmpty(inptPastTreatDTO.getId())) {
            throw new AppException("删除参数为空");
        }
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        inptPastTreatDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("inptPastTreatDTO", inptPastTreatDTO);
        return inptPastTreatService_consumer.deletePastTreat(map);
    }

    /**
     * @Method updatePastTreat()
     * @Desrciption 修改既往诊疗史的记录数据
     * @Param inptPastTreatDTO数据传输对象
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    @PostMapping("/updatePastTreat")
    public WrapperResponse<Boolean> updatePastTreat(@RequestBody InptPastTreatDTO inptPastTreatDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        if (inptPastTreatDTO == null) {
            throw new AppException("修改既往诊疗史的参数对象为空");
        }
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        inptPastTreatDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("inptPastTreatDTO", inptPastTreatDTO);
        return inptPastTreatService_consumer.updatePastTreat(map);
    }

    /**
     * @Method queryInptPastAdrsPage()
     * @Desrciption 不良反应史分页查询
     * @Param 不良反应史数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 15:58
     * @Return pageDTO
     **/
    @GetMapping("/queryInptPastAdrsPage")
    public WrapperResponse<PageDTO> queryInptPastAdrsPage(InptPastAdrsDTO inptPastAdrsDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        inptPastAdrsDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptPastAdrsDTO", inptPastAdrsDTO);
        return inptPastTreatService_consumer.queryInptPastAdrsPage(map);
    }

    /**
     * @Method getInptPastAdrsById()
     * @Desrciption  根据id查找不良不反应史记录
     * @Param id:不良不反应史数据id
     *
     * @Author fuhui
     * @Date   2020/11/3 14:32
     * @Return 不良不反应史数据传输对象
    **/
    @GetMapping("/getInptPastAdrsById")
    public WrapperResponse<InptPastAdrsDTO> getInptPastAdrsById(InptPastAdrsDTO inptPastAdrsDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        inptPastAdrsDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptPastAdrsDTO", inptPastAdrsDTO);
        return inptPastTreatService_consumer.getInptPastAdrsById(map);
    }


    /**
     * @Method insertInptPastAdrs
     * @Desrciption 新增不良反应史
     * @Param InptPastAllergyDTO数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 21:28
     * @Return boolean
     **/
    @PostMapping("/insertInptPastAdrs")
    public WrapperResponse<Boolean> insertInptPastAdrs(@RequestBody InptPastAdrsDTO inptPastAdrsDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        if (StringUtils.isEmpty(inptPastAdrsDTO.getProfileId())) {
            throw new AppException("个人档案Id参数为空");
        }
        if (StringUtils.isEmpty(inptPastAdrsDTO.getVisitId())) {
            throw new AppException("就诊Id参数为空");
        }
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        inptPastAdrsDTO.setHospCode(sysUserDTO.getHospCode());
        inptPastAdrsDTO.setCrteId(sysUserDTO.getId());
        inptPastAdrsDTO.setCrteName(sysUserDTO.getName());
        inptPastAdrsDTO.setCrteTime(DateUtils.getNow());
        map.put("inptPastAdrsDTO", inptPastAdrsDTO);
        return inptPastTreatService_consumer.insertInptPastAdrs(map);

    }

    /**
     * @Method deleteInptPastAdrs()
     * @Desrciption 删除不良反应史的记录数据
     * @Param id:不良反应史的id
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    @PostMapping("/deleteInptPastAdrs")
    public WrapperResponse<Boolean> deleteInptPastAdrs(@RequestBody InptPastAdrsDTO inptPastAdrsDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        if (StringUtils.isEmpty(inptPastAdrsDTO.getId())) {
            throw new AppException("删除参数为空");
        }
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        inptPastAdrsDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("inptPastAdrsDTO", inptPastAdrsDTO);
        return inptPastTreatService_consumer.deleteInptPastAdrs(map);
    }

    /**
     * @Method updatePastTreat()
     * @Desrciption 修改不良反应史的记录数据
     * @Param InptPastAdrsDTO数据传输对象
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    @PostMapping("/updateInptPastAdrs")
    public WrapperResponse<Boolean> updateInptPastAdrs(@RequestBody InptPastAdrsDTO inptPastAdrsDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        if (inptPastAdrsDTO == null) {
            throw new AppException("修改不良反应史的参数对象为空");
        }
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        inptPastAdrsDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("inptPastAdrsDTO", inptPastAdrsDTO);
        return inptPastTreatService_consumer.updateInptPastAdrs(map);
    }

    /**
     * @Method queryInptPastAllergyPage()
     * @Desrciption 不良反应史分页查询
     * @Param 不良反应史数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 15:58
     * @Return pageDTO
     **/
    @GetMapping("/queryInptPastAllergyPage")
    public WrapperResponse<PageDTO> queryInptPastAllergyPage(InptPastAllergyDTO inptPastAllergyDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        inptPastAllergyDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptPastAllergyDTO", inptPastAllergyDTO);
        return inptPastTreatService_consumer.queryInptPastAllergyPage(map);
    }
    /**
     * @Method getInptPastAllergyById()
     * @Desrciption  根据id查找既往过敏史数据
     * @Param id:既往过敏史数据Id
     *
     * @Author fuhui
     * @Date   2020/11/3 14:35
     * @Return 既往过敏史数据传输对象
    **/
    @GetMapping("/getInptPastAllergyById")
    public WrapperResponse<InptPastAllergyDTO> getInptPastAllergyById(InptPastAllergyDTO inptPastAllergyDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        inptPastAllergyDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptPastAllergyDTO", inptPastAllergyDTO);
        return inptPastTreatService_consumer.getInptPastAllergyById(map);
    }

    /**
     * @Method insertInptPastAllergy()
     * @Desrciption 新增不良反应史记录
     * @Param InptPastAllergyDTO数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 21:28
     * @Return boolean
     **/
    @PostMapping("/insertInptPastAllergy")
    public WrapperResponse<Boolean> insertInptPastAllergy(@RequestBody InptPastAllergyDTO inptPastAllergyDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        if (StringUtils.isEmpty(inptPastAllergyDTO.getProfileId())) {
            throw new AppException("个人档案Id参数为空");
        }
        if (StringUtils.isEmpty(inptPastAllergyDTO.getVisitId())) {
            throw new AppException("就诊Id参数为空");
        }
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        inptPastAllergyDTO.setHospCode(sysUserDTO.getHospCode());
        inptPastAllergyDTO.setCrteId(sysUserDTO.getId());
        inptPastAllergyDTO.setCrteName(sysUserDTO.getName());
        inptPastAllergyDTO.setCrteTime(DateUtils.getNow());
        map.put("inptPastAllergyDTO", inptPastAllergyDTO);
        return inptPastTreatService_consumer.insertInptPastAllergy(map);

    }

    /**
     * @Method deleteInptPastAdrs()
     * @Desrciption 删除不良反应史的记录数据
     * @Param id:不良反应史的id
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    @PostMapping("/deleteInptPastAllergy")
    public WrapperResponse<Boolean> deleteInptPastAllergy(@RequestBody InptPastAllergyDTO inptPastAllergyDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        if (StringUtils.isEmpty(inptPastAllergyDTO.getId())) {
            throw new AppException("删除参数为空");
        }
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        inptPastAllergyDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("inptPastAllergyDTO", inptPastAllergyDTO);
        return inptPastTreatService_consumer.deleteInptPastAllergy(map);
    }

    /**
     * @Method updateInptPastAllergy()
     * @Desrciption 修改不良反应史的记录数据
     * @Param InptPastAdrsDTO数据传输对象
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    @PostMapping("/updateInptPastAllergy")
    public WrapperResponse<Boolean> updateInptPastAllergy(@RequestBody InptPastAllergyDTO inptPastAllergyDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        if (inptPastAllergyDTO == null) {
            throw new AppException("修改不良反应史的参数对象为空");
        }
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        inptPastAllergyDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("inptPastAllergyDTO", inptPastAllergyDTO);
        return inptPastTreatService_consumer.updateInptPastAllergy(map);
    }

    /**
     * @Method queryInptPastDrugDTOPage()
     * @Desrciption 既往用药史分页查询
     * @Param 不良反应史数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 15:58
     * @Return pageDTO
     **/
    @GetMapping("/queryInptPastDrugPage")
    public WrapperResponse<PageDTO> queryInptPastDrugPage(InptPastDrugDTO inptPastDrugDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        inptPastDrugDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptPastDrugDTO", inptPastDrugDTO);
        return inptPastTreatService_consumer.queryInptPastDrugPage(map);
    }

    /**
     * @Method getInptPastDrugById()
     * @Desrciption  根据id查找既往用药史数据
     * @Param
     *
     * @Author fuhui
     * @Date   2020/11/3 14:38
     * @Return 既往用药史数据传输对象
    **/
    @GetMapping("/getInptPastDrugById")
    public WrapperResponse<InptPastDrugDTO> getInptPastDrugById(InptPastDrugDTO inptPastDrugDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        inptPastDrugDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptPastDrugDTO", inptPastDrugDTO);
        return inptPastTreatService_consumer.getInptPastDrugById(map);
    }
    /**
     * @Method insertInptPastDrug（）
     * @Desrciption 新增既往用药史史记录
     * @ParamInptPastDrugDTO数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 21:28
     * @Return boolean
     **/
    @PostMapping("/insertInptPastDrug")
    public WrapperResponse<Boolean> insertInptPastDrug(@RequestBody InptPastDrugDTO inptPastDrugDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        if (StringUtils.isEmpty(inptPastDrugDTO.getProfileId())) {
            throw new AppException("个人档案Id参数为空");
        }
        if (StringUtils.isEmpty(inptPastDrugDTO.getVisitId())) {
            throw new AppException("就诊Id参数为空");
        }
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        inptPastDrugDTO.setHospCode(sysUserDTO.getHospCode());
        inptPastDrugDTO.setCrteId(sysUserDTO.getId());
        inptPastDrugDTO.setCrteName(sysUserDTO.getName());
        inptPastDrugDTO.setCrteTime(DateUtils.getNow());
        map.put("inptPastDrugDTO", inptPastDrugDTO);
        return inptPastTreatService_consumer.insertInptPastDrug(map);

    }

    /**
     * @Method deleteInptPastDrug()
     * @Desrciption 删除既往用药史史记录
     * @Param id:既往用药史的id
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    @PostMapping("/deleteInptPastDrug")
    public WrapperResponse<Boolean> deleteInptPastDrug(@RequestBody InptPastDrugDTO inptPastDrugDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        if (StringUtils.isEmpty(inptPastDrugDTO.getId())) {
            throw new AppException("删除参数为空");
        }
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        inptPastDrugDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("inptPastDrugDTO", inptPastDrugDTO);
        return inptPastTreatService_consumer.deleteInptPastDrug(map);
    }

    /**
     * @Method updateInptPastDrug()
     * @Desrciption 修改既往用药史史记录
     * @Param InptPastAdrsDTO数据传输对象
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    @PostMapping("/updateInptPastDrug")
    public WrapperResponse<Boolean> updateInptPastDrug(@RequestBody InptPastDrugDTO inptPastDrugDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        if (inptPastDrugDTO == null) {
            throw new AppException("修改既往用药史的参数对象为空");
        }
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        inptPastDrugDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("inptPastDrugDTO", inptPastDrugDTO);
        return inptPastTreatService_consumer.updateInptPastDrug(map);
    }

    /**
     * @Method queryInptPastOperationPage()
     * @Desrciption 既往手术史分页查询
     * @Param 不良反应史数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 15:58
     * @Return pageDTO
     **/
    @GetMapping("/queryInptPastOperationPage")
    public WrapperResponse<PageDTO> queryInptPastOperationPage(InptPastOperationDTO inptPastOperationDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        inptPastOperationDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptPastOperationDTO", inptPastOperationDTO);
        return inptPastTreatService_consumer.queryInptPastOperationPage(map);
    }

    /**
     * @Method getInptPastOperationById()
     * @Desrciption  根据id查找既往手术史记录
     * @Param
     *
     * @Author fuhui
     * @Date   2020/11/3 14:40
     * @Return 既往手术史数据传输对象
    **/
    @GetMapping("/getInptPastOperationById")
    public WrapperResponse<InptPastOperationDTO> getInptPastOperationById(InptPastOperationDTO inptPastOperationDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        inptPastOperationDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptPastOperationDTO", inptPastOperationDTO);
        return inptPastTreatService_consumer.getInptPastOperationById(map);
    }
    /**
     * @Method insertPastTreat
     * @Desrciption 新增既往手术史记录
     * @Param iinptPastOperationDTO数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 21:28
     * @Return boolean
     **/
    @PostMapping("/insertInptPastOperation")
    public WrapperResponse<Boolean> insertInptPastOperation(@RequestBody InptPastOperationDTO inptPastOperationDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        if (StringUtils.isEmpty(inptPastOperationDTO.getProfileId())) {
            throw new AppException("个人档案Id参数为空");
        }
        if (StringUtils.isEmpty(inptPastOperationDTO.getVisitId())) {
            throw new AppException("就诊Id参数为空");
        }
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        inptPastOperationDTO.setHospCode(sysUserDTO.getHospCode());
        inptPastOperationDTO.setCrteId(sysUserDTO.getId());
        inptPastOperationDTO.setCrteName(sysUserDTO.getName());
        inptPastOperationDTO.setCrteTime(DateUtils.getNow());
        map.put("inptPastOperationDTO", inptPastOperationDTO);
        return inptPastTreatService_consumer.insertInptPastOperation(map);

    }

    /**
     * @Method deleteInptPastOperation()
     * @Desrciption 删除既往手术史记录
     * @Param id:既往用药史的id
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    @PostMapping("/deleteInptPastOperation")
    public WrapperResponse<Boolean> deleteInptPastOperation(@RequestBody InptPastOperationDTO inptPastOperationDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        if (StringUtils.isEmpty(inptPastOperationDTO.getId())) {
            throw new AppException("删除参数为空");
        }
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        inptPastOperationDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("inptPastOperationDTO", inptPastOperationDTO);
        return inptPastTreatService_consumer.deleteInptPastOperation(map);
    }

    /**
     * @Method updateInptPastOperation()
     * @Desrciption 修改既往用药史史记录
     * @Param InptPastOperationDTO数据传输对象
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    @PostMapping("/updateInptPastOperation")
    public WrapperResponse<Boolean> updateInptPastOperation(@RequestBody InptPastOperationDTO inptPastOperationDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        if (inptPastOperationDTO == null) {
            throw new AppException("修改既往用药史的参数对象为空");
        }
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        inptPastOperationDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("inptPastOperationDTO", inptPastOperationDTO);
        return inptPastTreatService_consumer.updateInptPastOperation(map);
    }

    /**
     * @Method queryDrugPage()
     * @Desrciption 分页查询药品的名称 和对应的频率
     * @Param
     * @Author fuhui
     * @Date 2020/9/22 13:58
     * @Return BaseDrugDTO 药品数据传输对象
     **/
    @GetMapping("/queryDrugPage")
    public WrapperResponse<PageDTO> queryDrugPage(BaseDrugDTO baseDrugDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        baseDrugDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("baseDrugDTO", baseDrugDTO);
        return inptPastTreatService_consumer.queryDrugPage(map);
    }

}
