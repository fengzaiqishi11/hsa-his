package cn.hsa.module.base.bd.dao;

import cn.hsa.module.base.bd.dto.BaseDiseaseDTO;
import cn.hsa.module.base.bd.dto.BaseDiseaseRuleDTO;
import cn.hsa.module.insure.module.dto.InsureDiseaseMatchDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Package_ame: cn.hsa.base.bd.dao
 * @Class_name: BaseDiseaseDao
 * @Description: 疾病管理访问层接口
 * @Author: liaojunjie
 * @Date: 2020/7/8 20:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface BaseDiseaseDAO {

    /**
     * @Method getById
     * @Desrciption 通过id获取疾病对象
     * @Param
     * [baseDiseaseDTO]
     * @Author liaojunjie
     * @Date   2020/7/14 14:13
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
    List<BaseDiseaseDTO> queryPage(BaseDiseaseDTO baseDiseaseDTO);

    /**
     * @Method queryAll
     * @Desrciption 查询所有疾病消息
     * @Param
     * [baseDiseaseDTO]
     * @Author liaojunjie
     * @Date   2020/7/18 14:44
     * @Return java.util.List<cn.hsa.module.base.bd.dto.BaseDiseaseDTO>
     **/
    List<BaseDiseaseDTO> queryAll(BaseDiseaseDTO baseDiseaseDTO);

    /**
     * @Method insert
     * @Desrciption 新增单条疾病信息
     * @Param
     * [baseDiseaseDTO]
     * @Author liaojunjie
     * @Date   2020/7/18 14:43
     * @Return java.lang.Integer
     **/
    Integer insert(BaseDiseaseDTO baseDiseaseDTO);

    /**
     * @Method update()
     * @Description 修改单条疾病信息
     *
     * @Param
     * [baseDiseaseDTO]
     *
     * @Author liaojunjie
     * @Date 2020/7/8 9:01
     * @Return Interger
     **/
    Integer update(BaseDiseaseDTO baseDiseaseDTO);

    /**
     * @Method updateStatus()
     * @Description 删除单/多条疾病信息(修改状态为无效)
     *
     * @Param
     * [baseDiseaseDTO]
     *
     * @Author liaojunjie
     * @Date 2020/7/8 9:01
     * @Return Interger
     **/
    Integer updateStatus(BaseDiseaseDTO baseDiseaseDTO);

    /**
     * @Method isCodeExist
     * @Desrciption 判断疾病编码、附加编码、国家编码是否已经存在
     * @Param
     * [code]
     * @Author liaojunjie
     * @Date   2020/8/29 11:30
     * @Return java.lang.Integer
     **/
    Integer isCodeExist(BaseDiseaseDTO baseDiseaseDTO);

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
     * @Return WrapperResponse<PageDTO>
     **/
    List<BaseDiseaseRuleDTO> queryDiseaseRule(BaseDiseaseDTO baseDiseaseDTO);

    /**
     * @Method insertDiseaseRule()
     * @Description 新增疾病规则
     * @Param List<BaseDiseaseRuleDTO>
     * @Author luoyong
     * @Date 2020-11-26 14:32
     * @Return
     **/
    int insertDiseaseRule(@Param("addList") List<BaseDiseaseRuleDTO> addList);

    /**
     * @Method updateDiseaseRule()
     * @Description 编辑疾病规则
     * @Param List<BaseDiseaseRuleDTO>
     * @Author luoyong
     * @Date 2020-11-26 14:32
     * @Return
     **/
    int updateDiseaseRule(@Param("editList") List<BaseDiseaseRuleDTO> editList);

    /**
     * @Method updateDiseaseRuleIsValid()
     * @Description 删除疾病规则
     * @Param [baseDiseaseRuleDTO]
     * @Author luoyong
     * @Date 2020-11-26 14:32
     * @Return WrapperResponse<Boolean>
     **/
    int updateDiseaseRuleIsValid(BaseDiseaseRuleDTO baseDiseaseRuleDTO);

    /**
     * @Method queryDiseaseRuleByDid()
     * @Description 检查质控疾病规则
     * @Param [baseDiseaseRuleDTO]
     * @Author luoyong
     * @Date 2020-11-26 14:32
     * @Return List<BaseDiseaseRuleDTO>
     **/
    List<BaseDiseaseRuleDTO> queryDiseaseRuleByDid(BaseDiseaseDTO baseDiseaseDTO);

    List<BaseDiseaseDTO> queryUnifiedPage(BaseDiseaseDTO baseDiseaseDTO);

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

    List<InsureDiseaseMatchDTO> getDiseaseIsMatch(BaseDiseaseDTO baseDiseaseDTO);

    void updateDiseaseMatch(@Param("insureDiseaseMatchDTOList") List<InsureDiseaseMatchDTO> insureDiseaseMatchDTOList, @Param("hospCode") String hospCode);
}