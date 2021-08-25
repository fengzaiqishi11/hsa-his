package cn.hsa.module.inpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.inpt.advancepay.dto.InptAdvancePayDTO;
import cn.hsa.module.inpt.advancepay.service.InptAdvancePayService;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.ListUtils;
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
 * @Class_name: InptAdvancePayController
 * @Describe: 预交金管理controller层
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/9/14 19:35
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/inpt/inptAdvancePay")
@Slf4j
public class InptAdvancePayController extends BaseController {

    @Resource
    private InptAdvancePayService inptAdvancePayService_consumer;

    /**
     * @param inptAdvancePayDTO id 预交金信息表主键ID
     * @Menthod getById
     * @Desrciption 根据主键ID查询预交金信息
     * @Author xingyu.xie
     * @Date 2020/7/14 16:17
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.inpt.bmm.dto.InptAdvancePayDTO>
     **/
    @GetMapping("/getById")
    WrapperResponse<InptAdvancePayDTO> getById(InptAdvancePayDTO inptAdvancePayDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        inptAdvancePayDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptAdvancePayDTO",inptAdvancePayDTO);
        return inptAdvancePayService_consumer.getById(map);
    }

    /**
     * @Method queryPatientInfoPage
     * @Desrciption 住院患者信息分页查询
     * @param inptVisitDTO
     * @Author xingyu.xie
     * @Date   2020/9/4 10:49
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @RequestMapping(value = "/queryPatientInfoPage")
    public WrapperResponse<PageDTO> queryPatientInfoPage(InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptVisitDTO", inptVisitDTO);

        WrapperResponse<PageDTO> pageDTOWrapperResponse = inptAdvancePayService_consumer.queryPatientInfoPage(map);

        return pageDTOWrapperResponse;
    }

    /**
     * @Menthod queryPage
     * @Desrciption   查询全部预交金信息
     * @param inptAdvancePayDTO 医院编码
     * @Author xingyu.xie
     * @Date   2020/7/14 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.inpt.PageDTO>
     **/
    @GetMapping("/queryAll")
    WrapperResponse<List<InptAdvancePayDTO>> queryAll(InptAdvancePayDTO inptAdvancePayDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        inptAdvancePayDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptAdvancePayDTO",inptAdvancePayDTO);
        return inptAdvancePayService_consumer.queryAll(map);
    }

    /**
     * @Menthod queryPage
     * @Desrciption   分页查询预交金信息
     * @param inptAdvancePayDTO 预交金信息数据参数对象
     * @Author xingyu.xie
     * @Date   2020/7/14 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.inpt.PageDTO>
     **/
    @GetMapping("/queryPage")
    WrapperResponse<PageDTO> queryPage(InptAdvancePayDTO inptAdvancePayDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        inptAdvancePayDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptAdvancePayDTO",inptAdvancePayDTO);
        return inptAdvancePayService_consumer.queryPage(map);
    }

    /**
     * @Menthod insert
     * @Desrciption 新增单条预交金信息
     * @param inptAdvancePayDTO  预交金分类数据对象
     * @Author xingyu.xie
     * @Date   2020/7/14 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.inpt.bmm.dto.InptAdvancePayDTO>
     **/
    @PostMapping("/insert")
    WrapperResponse<Boolean> insert(@RequestBody InptAdvancePayDTO inptAdvancePayDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        inptAdvancePayDTO.setHospCode(sysUserDTO.getHospCode());
        inptAdvancePayDTO.setCrteId(sysUserDTO.getId());
        inptAdvancePayDTO.setCrteName(sysUserDTO.getName());
        if (StringUtils.isEmpty(inptAdvancePayDTO.getVisitId())){
            throw new AppException("就诊ID不能为空！");
        }
        if (inptAdvancePayDTO.getPrice()==null){
            throw new AppException("预交金不能为空！");
        }
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptAdvancePayDTO",inptAdvancePayDTO);
        return inptAdvancePayService_consumer.insert(map);
    }


    /**
     * @Menthod flushingRed
     * @Desrciption 预交金冲红
     * @param inptAdvancePayDTOList  预交金分类数据对象
     * @Author xingyu.xie
     * @Date   2020/7/14 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.inpt.bmm.dto.InptAdvancePayDTO>
     **/
    @PostMapping("/flushingRed")
    WrapperResponse<Boolean> flushingRed(@RequestBody List<InptAdvancePayDTO> inptAdvancePayDTOList, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        if (ListUtils.isEmpty(inptAdvancePayDTOList)){
          throw new AppException("冲红的记录不能为空");
        }
        for (InptAdvancePayDTO inptAdvancePayDTO:inptAdvancePayDTOList){
            if(StringUtils.isEmpty(inptAdvancePayDTO.getVisitId())){
                throw new AppException("就诊ID不能为空！");
            }
            if(inptAdvancePayDTO.getRedPrice()==null){
                throw new AppException("冲红金额不能为空！");
            }
            inptAdvancePayDTO.setHospCode(sysUserDTO.getHospCode());
            inptAdvancePayDTO.setRedUserId(sysUserDTO.getId());
            inptAdvancePayDTO.setRedName(sysUserDTO.getName());
        }
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptAdvancePayDTOList",inptAdvancePayDTOList);
        return inptAdvancePayService_consumer.flushingRed(map);
    }

    /**
     * @Menthod update
     * @Desrciption 修改单条预交金信息(有非空判断)
     * @param inptAdvancePayDTO  预交金分类数据对象
     * @Author xingyu.xie
     * @Date   2020/7/14 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.inpt.bmm.dto.InptAdvancePayDTO>
     **/
    @PostMapping("/updateInptAdvancePay")
    WrapperResponse<Boolean> updateInptAdvancePay(@RequestBody InptAdvancePayDTO inptAdvancePayDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        if(StringUtils.isEmpty(inptAdvancePayDTO.getVisitId())){
            throw new AppException("就诊ID不能为空！");
        }
        inptAdvancePayDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptAdvancePayDTO",inptAdvancePayDTO);
        return inptAdvancePayService_consumer.insert(map);
    }

    /**
     * @Menthod update
     * @Desrciption 修改单条预交金信息(无非空判断)
     * @param inptAdvancePayDTO  预交金分类数据对象
     * @Author xingyu.xie
     * @Date   2020/7/14 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.inpt.bmm.dto.InptAdvancePayDTO>
     **/
    @PostMapping("/updateInptAdvancePayS")
    WrapperResponse<Boolean> updateInptAdvancePayS(@RequestBody InptAdvancePayDTO inptAdvancePayDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        if(StringUtils.isEmpty(inptAdvancePayDTO.getVisitId())){
            throw new AppException("就诊ID不能为空！");
        }
        inptAdvancePayDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptAdvancePayDTO",inptAdvancePayDTO);
        return inptAdvancePayService_consumer.insert(map);
    }

    /**
     * @Menthod updateStatus
     * @Desrciption   根据主键ID删除预交金信息
     * @param inptAdvancePayDTO  预交金信息表主键
     * @Author xingyu.xie
     * @Date   2020/7/14 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/deleteById")
    WrapperResponse<Boolean> deleteById(@RequestBody InptAdvancePayDTO inptAdvancePayDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        if(StringUtils.isEmpty(inptAdvancePayDTO.getVisitId())){
            throw new AppException("就诊ID不能为空！");
        }
        inptAdvancePayDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptAdvancePayDTO",inptAdvancePayDTO);
        return inptAdvancePayService_consumer.insert(map);
    }

    /**
     * @Menthod queryAdvancePay
     * @Desrciption  预交金查询接口
     * @param inptVisitDTO
     * @Author luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date   2020/9/9 11:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/queryAdvancePay")
    WrapperResponse<PageDTO> queryAdvancePay(InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptVisitDTO",inptVisitDTO);
        return inptAdvancePayService_consumer.queryAdvancePay(map);
    }






}
