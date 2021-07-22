package cn.hsa.module.base.bd.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.base.bd.dto.BaseDiseaseDTO;
import cn.hsa.module.base.bd.dto.BaseDiseaseRuleDTO;
import cn.hsa.module.insure.module.dto.InsureDiseaseDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_ame: cn.hsa.base.bd.bo
 * @Class_name: BaseDiseaseBO
 * @Description: 疾病管理逻辑实现层
 * @Author liaojunjie
 * @Date 2020/7/8 8:53
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface BaseDiseaseBO  {

    /**
     * @Method getById
     * @Desrciption 通过id获取
     * @Param
     * [baseDiseaseDTO]
     * @Author liaojunjie
     * @Date   2020/7/15 14:24
     * @Return cn.hsa.module.base.bi.dto.BaseDiseaseDTO
     **/
    BaseDiseaseDTO getById(BaseDiseaseDTO baseDiseaseDTO);

    /**
     * @Method queryPage()
     * @Description 分页查询全部疾病信息(默认状态为有效)
     *
     * @Param
     * [baseDiseaseDTO]
     *
     * @Author liaojunjie
     * @Date 2020/7/8 8:53
     * @Return PageDTO
     **/
    PageDTO queryPage(BaseDiseaseDTO baseDiseaseDTO);

    /**
     * @Method queryAll
     * @Desrciption  查询所有疾病信息
     * @Param
     * [baseDiseaseDTO]
     * @Author liaojunjie
     * @Date   2020/7/18 14:40
     * @Return java.util.List<cn.hsa.module.base.bd.dto.BaseDiseaseDTO>
     **/
    List<BaseDiseaseDTO> queryAll(BaseDiseaseDTO baseDiseaseDTO);

    /**
     * @Method save()
     * @Description 插入/修改单条疾病信息
     *
     * @Param
     * [baseDiseaseDTO]
     *
     * @Author liaojunjie
     * @Date 2020/7/8 8:57
     * @Return Boolean
     *
     * @return*/
    Boolean save(BaseDiseaseDTO baseDiseaseDTO);

    /**
     * @Method updateStatus()
     * @Description 删除单/多条疾病信息(修改状态为无效)
     *
     * @Param
     * [baseDiseaseDTO]
     *
     * @Author liaojunjie
     * @Date 2020/7/8 9:03
     * @Return Boolean
     **/
    Boolean updateStatus(BaseDiseaseDTO baseDiseaseDTO);

    /**
    * @Method getDeptByIds
    * @Param [baseDiseaseDTO]
    * @description   多个id查询
    * @author marong
    * @date 2020/10/15 15:14
    * @return java.util.List<cn.hsa.module.base.bd.dto.BaseDiseaseDTO>
    * @throws
    */
    List<BaseDiseaseDTO> getDiseaseByIds(BaseDiseaseDTO baseDiseaseDTO);

    /**
     * @Method queryDiseaseRule()
     * @Description 根据疾病id分页获取对应的疾病规则列表
     * @Param [baseDiseaseDTO]
     * @Author luoyong
     * @Date 2020-11-26 14:32
     * @Return PageDTO
     **/
    PageDTO queryDiseaseRule(BaseDiseaseDTO baseDiseaseDTO);

    /**
     * @Method saveDiseaseRule()
     * @Description 新增/修改疾病规则
     * @Param [baseDiseaseRuleDTOS]
     * @Author luoyong
     * @Date 2020-11-26 14:32
     * @Return Boolean
     **/
    Boolean saveDiseaseRule(Map map);

    /**
     * @Method delDiseaseRule()
     * @Description 删除疾病规则
     * @Param [baseDiseaseRuleDTO]
     * @Author luoyong
     * @Date 2020-11-26 14:32
     * @Return WrapperResponse<Boolean>
     **/
    Boolean updateDiseaseRule(BaseDiseaseRuleDTO baseDiseaseRuleDTO);

    /**
     * @Method queryDiseaseRuleByDid()
     * @Description 检查质控疾病规则
     * @Param [baseDiseaseRuleDTO]
     * @Author luoyong
     * @Date 2020-11-26 14:32
     * @Return List<BaseDiseaseRuleDTO>
     **/
    List<BaseDiseaseRuleDTO> queryDiseaseRuleByDid(BaseDiseaseDTO baseDiseaseDTO);

    /**
     * @param map
     * @Method insertInsureDiseaseMatch
     * @Desrciption 医保统一支付平台： 同步疾病数据到医保疾病匹配表
     * @Param
     * @Author fuhui
     * @Date 2021/3/20 11:05
     * @Return
     */
    Boolean insertInsureDiseaseMatch(Map<String, Object> map);

    PageDTO queryUnifiedPage(BaseDiseaseDTO baseDiseaseDTO);


    /**
     * @Method queryAllInfectious
     * @Desrciption  查询所有传染病信息
     * @Param
     * [baseDiseaseDTO]
     * @Author liuliyun
     * @Date   2021/04/23 09:59
     * @Return java.util.List<cn.hsa.module.base.bd.dto.BaseDiseaseDTO>
     **/
    List<BaseDiseaseDTO> queryAllInfectious(BaseDiseaseDTO baseDiseaseDTO);
}