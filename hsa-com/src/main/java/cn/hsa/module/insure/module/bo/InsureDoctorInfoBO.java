package cn.hsa.module.insure.module.bo;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.InsureDoctorInfoDTO;
import cn.hsa.module.insure.module.entity.InsureDoctorInfoDO;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

public interface InsureDoctorInfoBO {

    /**
     * @Method queryInsureDoctorInfo
     * @Param [map]
     * @description  获取医师信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     * @return insureDoctorInfoDTO
     */
    List<InsureDoctorInfoDTO> queryInsureDoctorInfo(InsureDoctorInfoDTO insureDoctorInfoDTO);

    /**
     * @Method insertInsureDoctorInfo
     * @Param [map]
     * @description  新增医师信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     * @return Boolean
     */
    Boolean insertInsureDoctorInfo(InsureDoctorInfoDTO insureDoctorInfoDTO);

    /**
     * @Method updateInsureDoctorInfoById
     * @Param [map]
     * @description  更新医师信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     * @return Boolean
     */
    Boolean updateInsureDoctorInfoById(InsureDoctorInfoDTO insureDoctorInfoDTO);

    /**
     * @Method updateInsureDoctorInfoById
     * @Param [map]
     * @description  删除医师信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     * @return Boolean
     */
    Boolean deleteInsureDoctorInfoById(InsureDoctorInfoDTO insureDoctorInfoDTO);

    /**
     * @Method getInsureDoctorInfoById
     * @Param [map]
     * @description  根据IDh获取医师信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     * @return InsureDoctorInfoDTO
     */
    InsureDoctorInfoDTO getInsureDoctorInfoById(InsureDoctorInfoDTO insureDoctorInfoDTO);
    /**
     * @param insureDoctorInfoDTO
     * @Method UpdateToInsureUpload
     * @Desrciption 医师信息上传
     * @Param insureDoctorInfoDTO
     * @Author 廖继广
     * @Date 2021-09-13 16:50
     * @Return Boolean
     */
    Boolean updateToInsureUpload(InsureDoctorInfoDTO insureDoctorInfoDTO);

    /**
     * @param insureDoctorInfoDTO
     * @Method UpdateToInsureUpload
     * @Desrciption 医师信息变更
     * @Param insureDoctorInfoDTO
     * @Author 廖继广
     * @Date 2021-09-13 16:50
     * @Return Boolean
     */
    Boolean updateToInsureEdit(InsureDoctorInfoDTO insureDoctorInfoDTO);

    /**
     * @param insureDoctorInfoDTO
     * @Method updateToInsureDelete
     * @Desrciption 医师信息撤销
     * @Param insureDoctorInfoDTO
     * @Author 廖继广
     * @Date 2021-09-13 16:50
     * @Return insureDoctorInfoDTO
     */
    Boolean updateToInsureDelete(InsureDoctorInfoDTO insureDoctorInfoDTO);
}
