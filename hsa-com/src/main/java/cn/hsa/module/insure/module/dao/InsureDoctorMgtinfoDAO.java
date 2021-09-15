package cn.hsa.module.insure.module.dao;

import cn.hsa.module.insure.module.entity.InsureDoctorMgtinfoDO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.insure.module.dao
 * @Class_name: InsureDoctorMgtinfoDAO
 * @Describe: 医保医师执业信息DAO层
 * @Author:廖继广
 * @Email: jiguang.liao@powersi.com
 * @Date: 2021/9/13 14:48
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface InsureDoctorMgtinfoDAO {

    /**
     * @Method queryInsureDoctorMgtinfoPage
     * @Param [map]
     * @description  获取医师执业信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     * @return InsureDoctorInfoDO
     */
    List<InsureDoctorMgtinfoDO> queryInsureDoctorMgtinfoPage(InsureDoctorMgtinfoDO insureDoctorMgtinfoDO);

    /**
     * @Method insertInsureDoctorMgtinfo
     * @Param [map]
     * @description  新增医师执业信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     * @return Boolean
     */
    int insertInsureDoctorMgtinfo(InsureDoctorMgtinfoDO insureDoctorMgtinfoDO);

    /**
     * @Method updateInsureDoctorMgtinfoById
     * @Param [map]
     * @description  更新医师执业信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     * @return Boolean
     */
    int updateInsureDoctorMgtinfoById(InsureDoctorMgtinfoDO insureDoctorMgtinfoDO);

    /**
     * @Method updateInsureDoctorMgtinfoById
     * @Param [map]
     * @description  删除医师执业信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     * @return Boolean
     */
    int deleteInsureDoctorMgtinfoById(InsureDoctorMgtinfoDO insureDoctorMgtinfoDO);

    /**
     * @Method getInsureDoctorMgtinfoById
     * @Param [map]
     * @description  根据ID获取医师执业信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     * @return InsureDoctorMgtinfoDO
     */
    InsureDoctorMgtinfoDO getInsureDoctorMgtinfoById(InsureDoctorMgtinfoDO insureDoctorMgtinfoDO);

    /**
     * @Method insertInsureDoctorMgtinfos
     * @Param [map]
     * @description  批量插入数据
     * @author 廖继广
     * @date 2021/9/13 14:22
     * @return InsureDoctorMgtinfoDO
     */
    int insertInsureDoctorMgtinfos(List<InsureDoctorMgtinfoDO> list);


    
}
