package cn.hsa.module.insure.module.dao;

import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureDictDTO;
import cn.hsa.module.insure.module.dto.InsureDoctorInfoDTO;
import cn.hsa.module.insure.module.entity.InsureDictDO;
import cn.hsa.module.insure.module.entity.InsureDiseaseDO;
import cn.hsa.module.insure.module.entity.InsureDoctorInfoDO;
import cn.hsa.module.insure.module.entity.InsureDoctorMgtinfoDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.module.dao
 * @Class_name: InsureDoctorInfoDAO
 * @Describe: 医保医师信息DAO层
 * @Author:廖继广
 * @Email: jiguang.liao@powersi.com
 * @Date: 2021/9/13 14:48
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface InsureDoctorInfoDAO {

    /**
     * @Method queryInsureDoctorInfo
     * @Param [map]
     * @description  获取医师信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     * @return insureDoctorInfoDTO
     */
    List<InsureDoctorInfoDTO> queryInsureDoctorInfoPage(InsureDoctorInfoDTO insureDoctorInfoDTO);

    /**
     * @Method insertInsureDoctorInfo
     * @Param [map]
     * @description  新增医师信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     * @return Boolean
     */
    int insertInsureDoctorInfo(InsureDoctorInfoDTO insureDoctorInfoDTO);

    /**
     * @Method updateInsureDoctorInfoById
     * @Param [map]
     * @description  更新医师信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     * @return Boolean
     */
    int updateInsureDoctorInfoById(InsureDoctorInfoDTO insureDoctorInfoDTO);

    /**
     * @Method updateInsureDoctorInfoById
     * @Param [map]
     * @description  删除医师信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     * @return Boolean
     */
    int deleteInsureDoctorInfoById(InsureDoctorInfoDTO insureDoctorInfoDTO);

    /**
     * @Method getInsureDoctorInfoById
     * @Param [map]
     * @description  根据IDh获取医师信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     * @return InsureDoctorInfoDTO
     */
    InsureDoctorInfoDTO getInsureDoctorInfoById(InsureDoctorInfoDTO insureDoctorInfoDO);

}
