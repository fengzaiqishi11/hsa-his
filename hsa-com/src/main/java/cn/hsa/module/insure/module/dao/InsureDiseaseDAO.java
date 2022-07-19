package cn.hsa.module.insure.module.dao;

import cn.hsa.module.insure.module.dto.InsureDiseaseDTO;
import cn.hsa.module.insure.module.dto.InsureItemMatchDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.module.dao
 * @class_name: InsureDiseaseDAO
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/1/27 21:31
 * @Company: 创智和宇
 **/
public interface InsureDiseaseDAO {
    /**
     * @Method queryAll
     * @Desrciption  查询从医保下载回来的疾病信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/1/27 21:32
     * @Return
    **/
    List<InsureDiseaseDTO> queryAll(InsureDiseaseDTO insureDiseaseDTO);
    
    /**
     * @Method
     * @Desrciption  
     * @Param 
     * 
     * @Author fuhui
     * @Date   2021/1/28 11:14 
     * @Return 
    **/
    Integer insertDisease(@Param("list")List <InsureDiseaseDTO> insureDiseaseDTOList);

    /**
     * @Method getById
     * @Desrciption  医保统一支付平台：根据id和医院编码查询医保下载信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/10 2:02
     * @Return
     **/
    InsureDiseaseDTO getById(InsureItemMatchDTO itemMatchDTO);
    
    /**
     * @Method selectLatestVer
     * @Desrciption  查询下载数据的最后一次信息，包括版本号  分页数据
     *
     * @Param 
     * 
     * @Author fuhui
     * @Date   2021/6/4 15:21 
     * @Return 
    **/
    InsureDiseaseDTO selectLatestVer(Map<String, Object> map);

    InsureDiseaseDTO getInsureDiseaseById(InsureItemMatchDTO insureItemMatchDTO);

    List<InsureDiseaseDTO> selectLastPageList(Map<String, Object> map);

    void deleteLastPage(Map<String, Object> map);
}
