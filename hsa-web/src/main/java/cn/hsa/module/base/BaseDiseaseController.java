package cn.hsa.module.base;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bd.dto.BaseDiseaseDTO;
import cn.hsa.module.base.bd.dto.BaseDiseaseRuleDTO;
import cn.hsa.module.base.bd.service.BaseDiseaseService;
import cn.hsa.module.insure.module.dto.InsureDiseaseDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.ListUtils;
import cn.hsa.util.MapUtils;
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

/**
 * @Package_ame: cn.hsa.web.base
 * @Class_name: BaseDiseaseController
 * @Description: 疾病管理控制层
 * @Author: liaojunjie
 * @Email: 871127471@qq.com
 * @Date: 2020/7/8 8:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/base/baseDisease")
@Slf4j
public class BaseDiseaseController extends BaseController {
    /**
     * 疾病管理dubbo消费者接口
     */
    @Resource
    private BaseDiseaseService baseDiseaseService_consumer;

    /**
     * @Method getById
     * @Desrciption 通过id获取项目信息
     * @Param
     * [BaseDiseaseDTO]
     * @Author liaojunjie
     * @Date   2020/7/14 15:05
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bi.dto.BaseDiseaseDTO>
     **/
    @GetMapping("/getById")
    public WrapperResponse<BaseDiseaseDTO> getById(BaseDiseaseDTO baseDiseaseDTO, HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseDiseaseDTO.setHospCode(hospCode);
        baseDiseaseDTO.setHospCode(sysUserDTO.getHospCode());
        //封装参数
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseDiseaseDTO",baseDiseaseDTO);
        log.debug("id:{}", baseDiseaseDTO.getId());
        return this.baseDiseaseService_consumer.getById(map);
    }

    /**
     * @Method query()
     * @Description 多条件查询信息(包括初始加载)
     *
     * @Param
     * [baseDiseaseDTO]
     *
     * @Author liaojunjie
     * @Date 2020/7/9 8:57
     * @Return WrapperResponse<PageDTO>
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(BaseDiseaseDTO baseDiseaseDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseDiseaseDTO.setHospCode(hospCode);
        baseDiseaseDTO.setHospCode(sysUserDTO.getHospCode());
        //封装参数
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseDiseaseDTO",baseDiseaseDTO);
        return this.baseDiseaseService_consumer.queryPage(map);
    }

    /**
     * @Method queryAll
     * @Desrciption 查询所有疾病信息
     * @Param
     * [baseDiseaseDTO]
     * @Author liaojunjie
     * @Date   2020/7/18 14:49
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.bd.dto.BaseDiseaseDTO>>
     **/
    @GetMapping("/queryAll")
    public WrapperResponse<List<BaseDiseaseDTO>> queryAll(BaseDiseaseDTO baseDiseaseDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseDiseaseDTO.setHospCode(hospCode);
        baseDiseaseDTO.setHospCode(sysUserDTO.getHospCode());
        //封装参数
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseDiseaseDTO",baseDiseaseDTO);
        return this.baseDiseaseService_consumer.queryAll(map);
    }

    /**
     * @Method save()
     * @Description 新增/修改单条疾病信息
     *
     * @Param
     *[baseDiseaseDTO]
     *
     * @Author liaojunjie
     * @Date 2020/7/24 18:57
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/save")
    public WrapperResponse<Boolean> save(@RequestBody BaseDiseaseDTO baseDiseaseDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseDiseaseDTO.setHospCode(hospCode);
        baseDiseaseDTO.setHospCode(sysUserDTO.getHospCode());
        //baseDiseaseDTO.setCrteId(userId);
        baseDiseaseDTO.setCrteId(sysUserDTO.getId());
        //baseDiseaseDTO.setCrteName(userName);
        baseDiseaseDTO.setCrteName(sysUserDTO.getName());
        //封装参数
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseDiseaseDTO",baseDiseaseDTO);
        log.debug("BaseDiseaseDTO:{}", baseDiseaseDTO);
        return this.baseDiseaseService_consumer.save(map);
    }

    /**
     * @Method updateStatus()
     * @Description 启用/停用疾病信息
     *
     * @Param
     * [baseDiseaseDTO]
     *
     * @Author liaojunjie
     * @Date 2020/7/8 9:03
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/updateStatus")
    public WrapperResponse<Boolean> updateStatus(@RequestBody BaseDiseaseDTO baseDiseaseDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseDiseaseDTO.setHospCode(hospCode);
        baseDiseaseDTO.setHospCode(sysUserDTO.getHospCode());
        //封装参数
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseDiseaseDTO",baseDiseaseDTO);
        return this.baseDiseaseService_consumer.updateStatus(map);
    }

    /**
     * @Method queryDiseaseRule()
     * @Description 根据疾病id分页获取对应的疾病规则列表
     * @Param [baseDiseaseDTO]
     * @Author luoyong
     * @Date 2020-11-26 14:32
     * @Return WrapperResponse<PageDTO>
     **/
    @GetMapping("/queryDiseaseRule")
    public WrapperResponse<PageDTO> queryDiseaseRule(BaseDiseaseDTO baseDiseaseDTO,HttpServletRequest req, HttpServletResponse res){
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        if (StringUtils.isEmpty(baseDiseaseDTO.getId())){
            throw new RuntimeException("未选择疾病信息");
        }
        //baseDiseaseDTO.setHospCode(hospCode);
        baseDiseaseDTO.setHospCode(sysUserDTO.getHospCode());
        //baseDiseaseDTO.setHospCode(hospCode);
        baseDiseaseDTO.setHospCode(sysUserDTO.getHospCode());
        baseDiseaseDTO.setIsValid(Constants.SF.S);
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseDiseaseDTO",baseDiseaseDTO);
        return baseDiseaseService_consumer.queryDiseaseRule(map);
    }

    /**
     * @Method saveDiseaseRule()
     * @Description 新增/修改疾病规则
     * @Param [baseDiseaseRuleDTOS]
     * @Author luoyong
     * @Date 2020-11-26 14:32
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/saveDiseaseRule")
    public WrapperResponse<Boolean> saveDiseaseRule(@RequestBody List<BaseDiseaseRuleDTO> baseDiseaseRuleDTOS,HttpServletRequest req, HttpServletResponse res){
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        if (ListUtils.isEmpty(baseDiseaseRuleDTOS)){
            throw new RuntimeException("未选择要保存信息");
        }
        baseDiseaseRuleDTOS.forEach(item ->{
            if (StringUtils.isEmpty(item.getTypeCode())){
                throw new RuntimeException("提示类型不能为空");
            }
            if (StringUtils.isEmpty(item.getPromptMsg())){
                throw new RuntimeException("提示信息不能为空");
            }
        });
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        //map.put("userId",userId);
        map.put("userId",sysUserDTO.getId());
        //map.put("userName",userName);
        map.put("userName",sysUserDTO.getName());
        map.put("baseDiseaseRuleDTOS",baseDiseaseRuleDTOS);
        return baseDiseaseService_consumer.saveDiseaseRule(map);
    }

    /**
     * @Method delDiseaseRule()
     * @Description 删除疾病规则
     * @Param [baseDiseaseRuleDTO]
     * @Author luoyong
     * @Date 2020-11-26 14:32
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/updateDiseaseRule")
    public WrapperResponse<Boolean> updateDiseaseRule(@RequestBody BaseDiseaseRuleDTO baseDiseaseRuleDTO,HttpServletRequest req, HttpServletResponse res){
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        if (StringUtils.isEmpty(baseDiseaseRuleDTO.getId())){
            throw new RuntimeException("未选择要删除的规则信息");
        }
        //baseDiseaseRuleDTO.setHospCode(hospCode);
        baseDiseaseRuleDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseDiseaseRuleDTO",baseDiseaseRuleDTO);
        return baseDiseaseService_consumer.updateDiseaseRule(map);
    }

    /**
     * @Method queryDiseaseRuleByDid()
     * @Description 检查质控疾病规则
     * @Param [baseDiseaseRuleDTO]
     * @Author luoyong
     * @Date 2020-11-26 14:32
     * @Return List<BaseDiseaseRuleDTO>
     **/
    @PostMapping("/queryDiseaseRuleByDid")
    public WrapperResponse<List<BaseDiseaseRuleDTO>> queryDiseaseRuleByDid(@RequestBody BaseDiseaseDTO baseDiseaseDTO,HttpServletRequest req, HttpServletResponse res){
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        if(StringUtils.isEmpty(baseDiseaseDTO.getDiseaseIds())){
            throw new RuntimeException("未选择诊断信息");
        }
        baseDiseaseDTO.setIds(Arrays.asList(baseDiseaseDTO.getDiseaseIds().split(",")));
        //baseDiseaseDTO.setHospCode(hospCode);
        baseDiseaseDTO.setHospCode(sysUserDTO.getHospCode());
        baseDiseaseDTO.setIsValid(Constants.SF.S);
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseDiseaseDTO",baseDiseaseDTO);
        return baseDiseaseService_consumer.queryDiseaseRuleByDid(map);
    }

    /**
     * @Method insertInsureDiseaseMatch
     * @Desrciption 医保统一支付平台： 同步疾病数据到医保疾病匹配表
     * @Param
     *
     * @Author fuhui
     * @Date   2021/3/20 11:05
     * @Return
     **/
    @PostMapping("/insertInsureDiseaseMatch")
    public WrapperResponse<Boolean> insertInsureDiseaseMatch(@RequestBody Map<String,Object> map,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        if (StringUtils.isEmpty(MapUtils.get(map, "regCode"))) {
            throw new AppException("请先选择医保机构");
        }
        //map.put("hospCode", hospCode);
        map.put("hospCode", sysUserDTO.getHospCode());
        //map.put("crteName", userName);
        map.put("crteName", sysUserDTO.getName());
        //map.put("crteId", userId);
        map.put("crteId", sysUserDTO.getId());
        return baseDiseaseService_consumer.insertInsureDiseaseMatch(map);
    }
}
