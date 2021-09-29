package cn.hsa.module.insure.module.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.InsureDoctorInfoDTO;
import cn.hsa.module.insure.module.entity.InsureDoctorInfoDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@FeignClient(value = "hsa-insure")
public interface InsureDoctorInfoService {

    /**
     * @Method queryInsureDoctorInfo
     * @Param [map]
     * @description  获取医师信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     * @return java.util.List<insureDoctorInfoDTO>
     */
    @GetMapping("/service/insure/InsureDoctorInfo/queryInsureDoctorInfo")
    WrapperResponse<List<InsureDoctorInfoDTO>> queryInsureDoctorInfo(Map map);

    /**
     * @Method insertInsureDoctorInfo
     * @Param [map]
     * @description  新增医师信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     * @return java.util.List<Boolean>
     */
    @PostMapping("/service/insure/InsureDoctorInfo/insertInsureDoctorInfo")
    WrapperResponse<Boolean> insertInsureDoctorInfo(Map map);

    /**
     * @Method updateInsureDoctorInfoById
     * @Param [map]
     * @description  更新医师信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     * @return java.util.List<Boolean>
     */
    @PostMapping("/service/insure/InsureDoctorInfo/updateInsureDoctorInfoById")
    WrapperResponse<Boolean> updateInsureDoctorInfoById(Map map);

    /**
     * @Method updateInsureDoctorInfoById
     * @Param [map]
     * @description  删除医师信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     * @return java.util.List<Boolean>
     */
    @PostMapping("/service/insure/InsureDoctorInfo/deleteInsureDoctorInfoById")
    WrapperResponse<Boolean> deleteInsureDoctorInfoById(Map map);

    /**
     * @Method getInsureDoctorInfoById
     * @Param [map]
     * @description  根据IDh获取医师信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     * @return insureDoctorInfoDTO
     */
    @PostMapping("/service/insure/InsureDoctorInfo/getInsureDoctorInfoById")
    WrapperResponse<InsureDoctorInfoDTO> getInsureDoctorInfoById(Map map);

    /**
     * @Method UpdateToInsureUpload
     * @Desrciption  医师信息上传
     * @Param insureDoctorInfoDTO
     * @Author 廖继广
     * @Date   2021-09-13 16:50
     * @Return insureDoctorInfoDTO
     **/
    WrapperResponse<Boolean> updateToInsureUpload(Map map);

    /**
     * @Method updateToInsureEdit
     * @Desrciption  医师信息变更
     * @Param insureDoctorInfoDTO
     * @Author 廖继广
     * @Date   2021-09-13 16:50
     * @Return insureDoctorInfoDTO
     **/
    WrapperResponse<Boolean> updateToInsureEdit(Map map);

    /**
     * @Method updateToInsureDelete
     * @Desrciption  医师信息撤销
     * @Param insureDoctorInfoDTO
     * @Author 廖继广
     * @Date   2021-09-13 16:50
     * @Return insureDoctorInfoDTO
     **/
    WrapperResponse<Boolean> updateToInsureDelete(Map map);
}
