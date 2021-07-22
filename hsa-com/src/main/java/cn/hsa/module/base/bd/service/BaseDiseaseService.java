package cn.hsa.module.base.bd.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bd.dto.BaseDiseaseDTO;
import cn.hsa.module.base.bd.dto.BaseDiseaseRuleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.bd.service
 * @Class_name: BaseDiseaseService
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/7/30 11:31
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-base")
public interface BaseDiseaseService {
    /**
     * @Method getById
     * @Desrciption 通过id获取项目信息
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/7/14 14:35
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bd.dto.BaseDiseaseDTO>
     **/
    @GetMapping("/service/base/baseDisease/getById")
    WrapperResponse<BaseDiseaseDTO> getById(Map map);

    /**
     * @Method queryPage()
     * @Description 分页查询所有疾病信息(默认状态为有效)
     *
     * @Param
     * [map]
     *
     * @Author liaojunjie
     * @Date 2020/7/8 8:53
     * @Return WrapperResponse<PageDTO>
     **/
    @GetMapping("/service/base/baseDisease/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);

    /**
     * @Method queryAll
     * @Desrciption 查询某医院下的所有疾病信息
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/7/16 11:10
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<BaseDiseaseDTO>>
     **/
    @GetMapping("/service/base/baseDisease/queryAll")
    WrapperResponse<List<BaseDiseaseDTO>> queryAll(Map map);

    /**
     * @Method updateStatus()
     * @Description 删除单/多条疾病信息(修改状态为无效)
     *
     * @Param
     * [map]
     *
     * @Author liaojunjie
     * @Date 2020/7/8 9:03
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/base/baseDisease/updateStatus")
    WrapperResponse<Boolean> updateStatus(Map map);

    /**
     * @Method save()
     * @Description 新增/修改单条疾病信息
     *
     * @Param
     * [map]
     *
     * @Author liaojunjie
     * @Date 2020/7/24 8:57
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/service/base/baseDisease/save")
    WrapperResponse<Boolean> save(Map map);

    /**
    * @Method getDeptByIds
    * @Param [baseDiseaseDTO]
    * @description   多个id查询
    * @author marong
    * @date 2020/10/15 15:13
    * @return java.util.List<cn.hsa.module.base.bd.dto.BaseDiseaseDTO>
    * @throws
    */
    @GetMapping("/service/base/baseDisease/getDiseaseByIds")
    List<BaseDiseaseDTO> getDiseaseByIds(Map map);

    /**
     * @Method queryDiseaseRule()
     * @Description 根据疾病id分页获取对应的疾病规则列表
     * @Param [baseDiseaseDTO]
     * @Author luoyong
     * @Date 2020-11-26 14:32
     * @Return WrapperResponse<PageDTO>
     **/
    @GetMapping("/service/base/baseDisease/queryDiseaseRule")
    WrapperResponse<PageDTO> queryDiseaseRule(Map map);

    /**
     * @Method saveDiseaseRule()
     * @Description 新增/修改疾病规则
     * @Param [baseDiseaseRuleDTOS]
     * @Author luoyong
     * @Date 2020-11-26 14:32
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/service/base/baseDisease/saveDiseaseRule")
    WrapperResponse<Boolean> saveDiseaseRule(Map map);

    /**
     * @Method delDiseaseRule()
     * @Description 删除疾病规则
     * @Param [baseDiseaseRuleDTO]
     * @Author luoyong
     * @Date 2020-11-26 14:32
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/service/base/baseDisease/updateDiseaseRule")
    WrapperResponse<Boolean> updateDiseaseRule(Map map);

    /**
     * @Method queryDiseaseRuleByDid()
     * @Description 检查质控疾病规则
     * @Param [baseDiseaseRuleDTO]
     * @Author luoyong
     * @Date 2020-11-26 14:32
     * @Return List<BaseDiseaseRuleDTO>
     **/
    @PostMapping("/service/base/baseDisease/queryDiseaseRuleByDid")
    WrapperResponse<List<BaseDiseaseRuleDTO>> queryDiseaseRuleByDid(Map map);

    /**
     * @Method insertInsureDiseaseMatch
     * @Desrciption 医保统一支付平台： 同步疾病数据到医保疾病匹配表
     * @Param
     *
     * @Author fuhui
     * @Date   2021/3/20 11:05
     * @Return
     **/
    @PostMapping("/service/base/baseDisease/insertInsureDiseaseMatch")
    WrapperResponse<Boolean> insertInsureDiseaseMatch(Map<String, Object> map);

    WrapperResponse<PageDTO> queryUnifiedPage(Map<String, Object> map);


    /**
     * @Method queryAll
     * @Desrciption 查询传染病信息
     * @Param
     * [map]
     * @Author liuliyun
     * @Date   2021/04/23 09:57
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<BaseDiseaseDTO>>
     **/
    @GetMapping("/service/base/baseDisease/queryAllInfectious")
    WrapperResponse<List<BaseDiseaseDTO>> queryAllInfectious(Map map);
}
