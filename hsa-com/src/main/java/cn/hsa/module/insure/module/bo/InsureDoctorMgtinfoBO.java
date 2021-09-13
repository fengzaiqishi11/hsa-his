package cn.hsa.module.insure.module.bo;

import cn.hsa.module.insure.module.entity.InsureDoctorMgtinfoDO;

import java.util.List;

public interface InsureDoctorMgtinfoBO {

    /**
     * @Method queryInsureDoctorMgtinfo
     * @Param [map]
     * @description  获取医师执业信息(不分页，不会超过3个执业信息)
     * @author 廖继广
     * @date 2021/9/13 14:22
     * @return java.util.List<InsureDoctorMgtinfoDO>
     */
    List<InsureDoctorMgtinfoDO> queryInsureDoctorMgtinfo(InsureDoctorMgtinfoDO insureDoctorMgtinfoDO);

    /**
     * @Method insertInsureDoctorMgtinfo
     * @Param [map]
     * @description  新增医师执业信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     * @return Boolean
     */
    Boolean insertInsureDoctorMgtinfo(InsureDoctorMgtinfoDO insureDoctorMgtinfoDO);

    /**
     * @Method updateInsureDoctorMgtinfoById
     * @Param [map]
     * @description  更新医师执业信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     * @return Boolean
     */
    Boolean updateInsureDoctorMgtinfoById(InsureDoctorMgtinfoDO insureDoctorMgtinfoDO);

    /**
     * @Method updateInsureDoctorMgtinfoById
     * @Param [map]
     * @description  删除医师执业信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     * @return Boolean
     */
    Boolean deleteInsureDoctorMgtinfoById(InsureDoctorMgtinfoDO insureDoctorMgtinfoDO);

    /**
     * @Method getInsureDoctorMgtinfoById
     * @Param [map]
     * @description  根据ID获取医师执业信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     * @return InsureDoctorMgtinfoDO
     */
    InsureDoctorMgtinfoDO getInsureDoctorMgtinfoById(InsureDoctorMgtinfoDO insureDoctorMgtinfoDO);
}
