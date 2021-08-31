package cn.hsa.module.sync.syncdisease.dao;

import cn.hsa.module.sync.syncdisease.dto.SyncDiseaseDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.center.disease.dao
 * @Class_name:: CenterDiseaseDAO
 * @Description: 疾病管理访问层接口
 * @Author: liaojunjie
 * @Date: 2020/8/5 17:35
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface SyncDiseaseDAO {

    /**
     * @Method getById
     * @Desrciption
     * @Param
     * [centerDiseaseDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 17:35
     * @Return cn.hsa.module.center.disease.dto.CenterDiseaseDTO
     **/
    SyncDiseaseDTO getById(SyncDiseaseDTO syncDiseaseDTO);

    /**
     * @Method queryPage
     * @Desrciption
     * @Param
     * [centerDiseaseDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 17:35
     * @Return java.util.List<cn.hsa.module.center.disease.dto.CenterDiseaseDTO>
     **/
    List<SyncDiseaseDTO> queryPage(SyncDiseaseDTO syncDiseaseDTO);

    /**
     * @Method queryAll
     * @Desrciption
     * @Param
     * [centerDiseaseDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 17:35
     * @Return java.util.List<cn.hsa.module.center.disease.dto.CenterDiseaseDTO>
     **/
    List<SyncDiseaseDTO> queryAll(SyncDiseaseDTO syncDiseaseDTO);

    /**
     * @Method insert
     * @Desrciption
     * @Param
     * [centerDiseaseDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 17:36
     * @Return java.lang.Integer
     **/
    Integer insert(SyncDiseaseDTO syncDiseaseDTO);

    /**
     * @Method update
     * @Desrciption
     * @Param
     * [centerDiseaseDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 17:36
     * @Return java.lang.Integer
     **/
    Integer update(SyncDiseaseDTO syncDiseaseDTO);


    /**
     * @Method updateStatus()
     * @Description 删除单/多条疾病信息(修改状态为无效)
     *
     * @Param
     * [centerDiseaseDTO]
     *
     * @Author liaojunjie
     * @Date 2020/7/8 9:01
     * @Return Interger
     **/
    Integer updateStatus(SyncDiseaseDTO syncDiseaseDTO);

    /**
     * @Method isCodeExist
     * @Desrciption
     * @Param
     * [centerDiseaseDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 17:36
     * @Return java.lang.Integer
     **/
    Integer isCodeExist(SyncDiseaseDTO syncDiseaseDTO);
}