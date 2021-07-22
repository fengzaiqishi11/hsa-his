package cn.hsa.module.insure;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureDictDTO;
import cn.hsa.module.insure.module.entity.InsureDictDO;
import cn.hsa.module.insure.module.entity.InsureDiseaseDO;
import cn.hsa.module.insure.module.service.InsureDictService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.ListUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Package_name: cn.hsa.module.insure
 * @Class_name:: InsureFunctionController
 * @Description: 医保配置
 * @Author: zhangxuan
 * @Date: 2020-11-09 14:36
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/insure/insureDict")
@Slf4j
public class InsureDictController extends BaseController {
    /*
     * 医院字典服务
     */
    @Resource
    private InsureDictService insureDictService_consumer;

    /**
     * @Method queryInsureDict
     * @Desrciption  获取字典信息
     * @Param [map]
     * @Author 廖继广
     * @Date   2020-11-11 15:52
     * @Return map
     **/
    @GetMapping("/queryInsureDict")
    public WrapperResponse<List<InsureDictDO>> queryInsureDict(InsureDictDO insureDictDO, HttpServletRequest req, HttpServletResponse res){
        if (StringUtils.isEmpty(insureDictDO.getCode())) {
            throw new AppException("获取医保字典参数错误");
        }
        SysUserDTO sysUserDTO = getSession(req, res);
        insureDictDO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureDictDO",insureDictDO);
        return insureDictService_consumer.queryInsureDict(map);
    }


    /**
     * @Method queryAllInsureDict
     * @Desrciption  查询所有的码表信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/26 20:13
     * @Return
    **/
    @GetMapping("/queryAllInsureDict")
    public WrapperResponse<Map<String,Object>> queryAllInsureDict(InsureDictDO insureDictDO, HttpServletRequest req, HttpServletResponse res){
        if(!StringUtils.isEmpty(insureDictDO.getStrCodes())){
            String str = insureDictDO.getStrCodes();
            String[] split = str.split(",");
            List<String> resultList = new ArrayList<>(Arrays.asList(split));
            insureDictDO.setCodeList(resultList);
        }
        SysUserDTO sysUserDTO = getSession(req, res);
        insureDictDO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureDictDO",insureDictDO);
        return insureDictService_consumer.queryAllInsureDict(map);
    }

    /**
     * @Method queryInsureDisease
     * @Desrciption  获取诊断信息
     * @Param InsureDiseaseDO
     * @Author 廖继广
     * @Date   2020-11-11 15:52
     * @Return map
     **/
    @GetMapping("/queryInsureDisease")
    public WrapperResponse<PageDTO> queryInsureDisease(InsureDiseaseDO insureDiseaseDO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        insureDiseaseDO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureDiseaseDO",insureDiseaseDO);
        return insureDictService_consumer.queryInsureDiseasePage(map);
    }

    /**
     * @Method queryInsureOrgInfo
     * @Desrciption  获取医保配置信息
     * @Param insureConfigurationDTO
     * @Author 廖继广
     * @Date   2020-11-11 15:52
     * @Return map
     **/
    @GetMapping("/queryInsureOrgInfo")
    public WrapperResponse<List<InsureConfigurationDTO>> queryInsureOrgInfo(InsureConfigurationDTO insureConfigurationDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        insureConfigurationDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureConfigurationDTO",insureConfigurationDTO);
        return insureDictService_consumer.queryInsureOrgInfo(map);
    }

    /**
     * @Menthod queryInsureDictList
     * @Desrciption  根据查询条件查询医保字典列表
     * @param insureDictDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/11/17 16:41
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping("/queryInsureDictList")
    public WrapperResponse queryInsureDictList(InsureDictDTO insureDictDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureDictDTO.setHospCode(sysUserDTO.getHospCode());
        Map param = new HashMap();
        param.put("hospCode",sysUserDTO.getHospCode());//医院编码
        param.put("insureDictDTO",insureDictDTO);//查询条件
        return insureDictService_consumer.queryInsureDictList(param);
    }

    /**
     * @Method queryDictValuePage()
     * @Desrciption  分页查询医保的码表值
     * @Param
     *
     * @Author fuhui
     * @Date   2020/11/18 16:13
     * @Return
    **/
    @GetMapping("/queryDictValuePage")
    public WrapperResponse<PageDTO> queryDictValuePage(InsureDictDTO insureDictDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureDictDTO.setHospCode(sysUserDTO.getHospCode());
        Map map =new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureDictDTO",insureDictDTO);
        return insureDictService_consumer.queryDictValuePage(map);
    }
    /**
     * @Method updateDictValue()
     * @Desrciption  更新医保码表数据
     * @Param regCode:医保机构
     *
     * @Author fuhui
     * @Date   2020/11/18 21:03
     * @Return
    **/
    @PostMapping("/updateDictValue")
    public WrapperResponse<Boolean> updateDictValue(@RequestBody InsureDictDTO insureDictDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureDictDTO.setHospCode(sysUserDTO.getHospCode());
        insureDictDTO.setCrteName(sysUserDTO.getName());
        insureDictDTO.setCrteId(sysUserDTO.getId());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureDictDTO",insureDictDTO);
        return insureDictService_consumer.updateDictValue(map);
    }

    /**
     * @Method updateDictCode()
     * @Desrciption  更新医保传过来的码表值数据
     * @Param regCode:医保机构
     *
     * @Author fuhui
     * @Date   2020/11/18 21:03
     * @Return
     **/
    @PostMapping("/updateDictCode")
    public WrapperResponse<Boolean> updateDictCode(@RequestBody InsureDictDTO insureDictDTO, HttpServletRequest req, HttpServletResponse res){
        if(insureDictDTO ==null){
            throw new AppException("修改参数为空");
        }
        SysUserDTO sysUserDTO = getSession(req, res);
        insureDictDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureDictDTO",insureDictDTO);
        return insureDictService_consumer.updateDictCode(map);
    }
    /**
     * @Method deleteDictCode()
     * @Desrciption  删除医保传过来的码表值数据
     * @Param regCode:医保机构
     *
     * @Author caoliang
     * @Date   2021/7/5 17:03
     * @Return
     **/
    @PostMapping("/deleteDictCode")
    public WrapperResponse<Boolean> deleteDictCode(@RequestBody InsureDictDTO insureDictDTO, HttpServletRequest req, HttpServletResponse res){
        if(insureDictDTO == null){
            throw new AppException("修改参数为空");
        }
        SysUserDTO sysUserDTO = getSession(req, res);
        insureDictDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureDictDTO",insureDictDTO);
        return insureDictService_consumer.deleteDictCode(map);
    }
    /**
     * @Method getDictById()
     * @Desrciption  根据Id查询医保字典编码值
     * @Param regCode:医保机构
     *
     * @Author fuhui
     * @Date   2020/11/18 21:03
     * @Return
     **/
    @GetMapping("/getDictById")
    public WrapperResponse<InsureDictDTO> getDictById(InsureDictDTO insureDictDTO, HttpServletRequest req, HttpServletResponse res){
        if(StringUtils.isEmpty(insureDictDTO.getId())){
            throw new AppException("编辑查询医保字典Id参数为空");
        }
        SysUserDTO sysUserDTO = getSession(req, res);
        insureDictDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureDictDTO",insureDictDTO);
        return insureDictService_consumer.getDictById(map);
    }

    /**
     * @Method insertDict
     * @Desrciption  新增医保字典对象
     * @Param insureDictDTO
     *
     * @Author fuhui
     * @Date   2021/2/1 9:58
     * @Return boolean`
    **/
    @PostMapping("/insertDict")
    public WrapperResponse<Boolean> insertDict(@RequestBody InsureDictDTO insureDictDTO, HttpServletRequest req, HttpServletResponse res){
        if(insureDictDTO ==null){
            return WrapperResponse.fail("新增医保字典值对象参数为空",null);
        }else{
            SysUserDTO sysUserDTO = getSession(req, res);
            Map map = new HashMap();
            map.put("hospCode",sysUserDTO.getHospCode());
            insureDictDTO.setId(SnowflakeUtils.getId());
            insureDictDTO.setHospCode(sysUserDTO.getHospCode());
            insureDictDTO.setCrteId(sysUserDTO.getId());
            insureDictDTO.setCrteName(sysUserDTO.getName());
            insureDictDTO.setCrteTime(DateUtils.getNow());
            map.put("insureDictDTO",insureDictDTO);
            return insureDictService_consumer.insertDict(map);
        }
    }
}
