package cn.hsa.module.insure.module.dao;

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
}
