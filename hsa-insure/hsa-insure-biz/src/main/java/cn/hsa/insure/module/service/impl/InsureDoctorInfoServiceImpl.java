package cn.hsa.insure.module.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.bo.InsureDoctorInfoBO;
import cn.hsa.module.insure.module.dto.InsureDoctorInfoDTO;
import cn.hsa.module.insure.module.service.InsureDoctorInfoService;
import cn.hsa.util.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.module.service.impl
 * @Class_name: InsureDoctorInfompl
 * @Describe(描述): 医保医师服务 service实现层
 * @Author: liaojiguang
 * @Eamil: jiguang.liao@powersi.com.cn
 * @Date: 2021/09/13 19:06
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/insure/insureDoctorInfo")
@Service("insureDoctorInfoService_provider")
public class InsureDoctorInfoServiceImpl extends HsafService implements InsureDoctorInfoService {

    @Resource
    private InsureDoctorInfoBO insureDoctorInfoBO;

    /**
     * @param map
     * @return java.util.List<InsureDoctorInfoDO>
     * @Method queryInsureDoctorInfo
     * @Param [map]
     * @description 获取医师信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     */
    @Override
    public WrapperResponse<List<InsureDoctorInfoDTO>> queryInsureDoctorInfo(Map map) {
        return WrapperResponse.success(insureDoctorInfoBO.queryInsureDoctorInfo(MapUtils.get(map,"insureDoctorInfoDTO")));
    }

    /**
     * @param map
     * @return java.util.List<Boolean>
     * @Method insertInsureDoctorInfo
     * @Param [map]
     * @description 新增医师信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     */
    @Override
    public WrapperResponse<Boolean> insertInsureDoctorInfo(Map map) {
        return WrapperResponse.success(insureDoctorInfoBO.insertInsureDoctorInfo(MapUtils.get(map,"insureDoctorInfoDTO")));
    }

    /**
     * @param map
     * @return java.util.List<Boolean>
     * @Method updateInsureDoctorInfoById
     * @Param [map]
     * @description 更新医师信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     */
    @Override
    public WrapperResponse<Boolean> updateInsureDoctorInfoById(Map map) {
        return WrapperResponse.success(insureDoctorInfoBO.updateInsureDoctorInfoById(MapUtils.get(map,"insureDoctorInfoDTO")));
    }

    /**
     * @param map
     * @return java.util.List<Boolean>
     * @Method updateInsureDoctorInfoById
     * @Param [map]
     * @description 删除医师信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     */
    @Override
    public WrapperResponse<Boolean> deleteInsureDoctorInfoById(Map map) {
        return WrapperResponse.success(insureDoctorInfoBO.deleteInsureDoctorInfoById(MapUtils.get(map,"insureDoctorInfoDTO")));
    }

    /**
     * @param map
     * @return InsureDoctorInfoDO
     * @Method getInsureDoctorInfoById
     * @Param [map]
     * @description 根据ID获取医师信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     */
    @Override
    public WrapperResponse<InsureDoctorInfoDTO> getInsureDoctorInfoById(Map map) {
        return WrapperResponse.success(insureDoctorInfoBO.getInsureDoctorInfoById(MapUtils.get(map,"insureDoctorInfoDTO")));
    }

    /**
     * @param map
     * @Method UpdateToInsureUpload
     * @Desrciption 医师信息上传
     * @Param insureDoctorInfoDTO
     * @Author 廖继广
     * @Date 2021-09-13 16:50
     * @Return insureDoctorInfoDTO
     */
    @Override
    public WrapperResponse<Boolean> updateToInsureUpload(Map map) {
        return WrapperResponse.success(insureDoctorInfoBO.updateToInsureUpload(MapUtils.get(map,"insureDoctorInfoDTO")));

    }

    /**
     * @param map
     * @Method updateToInsureEdit
     * @Desrciption 医师信息变更
     * @Param insureDoctorInfoDTO
     * @Author 廖继广
     * @Date 2021-09-13 16:50
     * @Return insureDoctorInfoDTO
     */
    @Override
    public WrapperResponse<Boolean> updateToInsureEdit(Map map) {
        return WrapperResponse.success(insureDoctorInfoBO.updateToInsureEdit(MapUtils.get(map,"insureDoctorInfoDTO")));
    }

    /**
     * @param map
     * @Method updateToInsureDelete
     * @Desrciption 医师信息撤销
     * @Param insureDoctorInfoDTO
     * @Author 廖继广
     * @Date 2021-09-13 16:50
     * @Return insureDoctorInfoDTO
     */
    @Override
    public WrapperResponse<Boolean> updateToInsureDelete(Map map) {
        return WrapperResponse.success(insureDoctorInfoBO.updateToInsureDelete(MapUtils.get(map,"insureDoctorInfoDTO")));
    }
}
