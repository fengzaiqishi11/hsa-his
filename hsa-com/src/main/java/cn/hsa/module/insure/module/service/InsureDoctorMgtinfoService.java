package cn.hsa.module.insure.module.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.entity.InsureDoctorMgtinfoDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@FeignClient(value = "hsa-insure")
public interface InsureDoctorMgtinfoService {

    /**
     * @Method queryInsureDoctorMgtinfo
     * @Param [map]
     * @description  获取医师执业信息(不分页，不会超过3个执业信息)
     * @author 廖继广
     * @date 2021/9/13 14:22
     * @return java.util.List<InsureDoctorMgtinfoDO>
     */
    @GetMapping("/service/insure/InsureDoctorMgtinfo/queryInsureDoctorMgtinfo")
    WrapperResponse<List<InsureDoctorMgtinfoDO>> queryInsureDoctorMgtinfo(Map map);

    /**
     * @Method insertInsureDoctorMgtinfo
     * @Param [map]
     * @description  新增医师执业信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     * @return java.util.List<Boolean>
     */
    @PostMapping("/service/insure/InsureDoctorMgtinfo/insertInsureDoctorMgtinfo")
    WrapperResponse<Boolean> insertInsureDoctorMgtinfo(Map map);

    /**
     * @Method updateInsureDoctorMgtinfoById
     * @Param [map]
     * @description  更新医师执业信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     * @return java.util.List<Boolean>
     */
    @PostMapping("/service/insure/InsureDoctorMgtinfo/updateInsureDoctorMgtinfoById")
    WrapperResponse<Boolean> updateInsureDoctorMgtinfoById(Map map);

    /**
     * @Method updateInsureDoctorMgtinfoById
     * @Param [map]
     * @description  删除医师执业信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     * @return java.util.List<Boolean>
     */
    @PostMapping("/service/insure/InsureDoctorMgtinfo/deleteInsureDoctorMgtinfoById")
    WrapperResponse<Boolean> deleteInsureDoctorMgtinfoById(Map map);

    /**
     * @Method getInsureDoctorMgtinfoById
     * @Param [map]
     * @description  根据ID获取医师执业信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     * @return InsureDoctorMgtinfoDO
     */
    @PostMapping("/service/insure/InsureDoctorMgtinfo/getInsureDoctorMgtinfoById")
    WrapperResponse<InsureDoctorMgtinfoDO> getInsureDoctorMgtinfoById(Map map);

}
