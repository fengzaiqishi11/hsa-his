package cn.hsa.module.insure.module.dao;

import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.mris.mrisHome.dto.MrisBaseInfoDTO;
import cn.hsa.module.mris.tcmMrisHome.dto.TcmMrisBaseInfoDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.module.dao
 * @class_name: InsureUnifiedEmrUploadDAO
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/12/10 13:48
 * @Company: 创智和宇
 **/
@Component
public interface InsureUnifiedEmrUploadDAO {

    /**
     * @Method selectDiseinfo
     * @Desrciption  查询病案首页诊断信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/12/10 13:57
     * @Return
    **/
    List<Map<String, Object>> selectDiseinfo(Map<String, Object> map);
    
    /**
     * @Method 
     * @Desrciption  
     * @Param 
     * 
     * @Author fuhui
     * @Date   2021/12/10 15:41
     * @Return 
    **/
    List<Map<String, Object>> selectOperInfo(Map<String, Object> map);

    /**
     * @Method updateEmrBaseInfo
     * @Desrciption  病案首页信息上传以后,更新上传标识
     * @Param
     *
     * @Author fuhui
     * @Date   2022/1/7 9:11
     * @Return
    **/
    void updateEmrBaseInfo(MrisBaseInfoDTO mrisBaseInfoDTO);
    
    /**
     * @Method updateEmrPatientFlag
     * @Desrciption  更新电子病历上传标识
     * @Param 
     * 
     * @Author fuhui
     * @Date   2022/3/4 10:21 
     * @Return 
    **/
    void updateEmrPatientFlag(InptVisitDTO inptVisitDTO);
    /**
     * @Description 查询中医病案首页诊断信息
     * @Author 产品三部-郭来
     * @Date 2022-06-01 16:45
     * @param map
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> selectTcmDiseinfo(Map<String, Object> map);
    /**
     * @Description 查询中医病案首页手术信息
     * @Author 产品三部-郭来
     * @Date 2022-06-01 16:59
     * @param map
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> selectTcmOperInfo(Map<String, Object> map);
    /**
     * @Method updateEmrBaseInfo
     * @Desrciption  中医病案首页信息上传以后,更新上传标识
     * @Param
     *
     * @Author guolai
     * @Date   2022/6/8 9:11
     * @Return
     **/
    void updateTcmEmrBaseInfo(TcmMrisBaseInfoDTO tcmmrisBaseInfoDTO);

    Map<String, Object> selectInsureDiseaseMatch(Map<String, Object> map);
}
