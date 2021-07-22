package cn.hsa.module.sync.syncdisease.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.sync.syncdisease.dto.SyncDiseaseDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.center.disease.bo
 * @Class_name:: CenterDiseaseBO
 * @Description: 疾病管理逻辑实现层
 * @Author: liaojunjie
 * @Date: 2020/8/5 17:34
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface SyncDiseaseBO {

    /**
     * @Method getById
     * @Desrciption
     * @Param
     * [syncDiseaseDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 17:34
     * @Return cn.hsa.module.center.disease.dto.CenterDiseaseDTO
     **/
    SyncDiseaseDTO getById(SyncDiseaseDTO syncDiseaseDTO);

    /**
     * @Method queryPage
     * @Desrciption
     * @Param
     * [syncDiseaseDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 17:34
     * @Return cn.hsa.base.PageDTO
     **/
    PageDTO queryPage(SyncDiseaseDTO syncDiseaseDTO);

    /**
     * @Method queryAll
     * @Desrciption
     * @Param
     * [syncDiseaseDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 17:34
     * @Return java.util.List<cn.hsa.module.center.disease.dto.CenterDiseaseDTO>
     **/
    List<SyncDiseaseDTO> queryAll(SyncDiseaseDTO syncDiseaseDTO);

    /**
     * @Method save
     * @Desrciption
     * @Param
     * [syncDiseaseDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 17:34
     * @Return java.lang.Boolean
     **/
    Boolean save(SyncDiseaseDTO syncDiseaseDTO);

    /**
     * @Method updateStatus
     * @Desrciption
     * @Param
     * [syncDiseaseDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 17:35
     * @Return java.lang.Boolean
     **/
    Boolean updateStatus(SyncDiseaseDTO syncDiseaseDTO);
}