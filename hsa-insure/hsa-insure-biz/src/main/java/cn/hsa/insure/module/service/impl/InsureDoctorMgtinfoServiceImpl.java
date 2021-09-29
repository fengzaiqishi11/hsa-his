package cn.hsa.insure.module.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.bo.InsureDoctorMgtinfoBO;
import cn.hsa.module.insure.module.entity.InsureDoctorMgtinfoDO;
import cn.hsa.module.insure.module.service.InsureDoctorMgtinfoService;
import cn.hsa.util.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.module.service.impl
 * @Class_name: InsureDoctorMgtinfompl
 * @Describe(描述): 医保医师服务 service实现层
 * @Author: liaojiguang
 * @Eamil: jiguang.liao@powersi.com.cn
 * @Date: 2021/09/13 19:06
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/insure/insureDoctorMgtinfo")
@Service("insureDoctorMgtinfoService_provider")
public class InsureDoctorMgtinfoServiceImpl extends HsafService implements InsureDoctorMgtinfoService {

    @Resource
    private InsureDoctorMgtinfoBO insureDoctorMgtinfoBO;

    /**
     * @param map
     * @return java.util.List<InsureDoctorMgtinfoDO>
     * @Method queryInsureDoctorMgtinfo
     * @Param [map]
     * @description 获取医师信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     */
    @Override
    public WrapperResponse<List<InsureDoctorMgtinfoDO>> queryInsureDoctorMgtinfo(Map map) {
        return WrapperResponse.success(insureDoctorMgtinfoBO.queryInsureDoctorMgtinfo(MapUtils.get(map,"insureDoctorMgtinfoDO")));
    }

    /**
     * @param map
     * @return java.util.List<Boolean>
     * @Method insertInsureDoctorMgtinfo
     * @Param [map]
     * @description 新增医师信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     */
    @Override
    public WrapperResponse<Boolean> insertInsureDoctorMgtinfo(Map map) {
        return WrapperResponse.success(insureDoctorMgtinfoBO.insertInsureDoctorMgtinfo(MapUtils.get(map,"insureDoctorMgtinfoDO")));
    }

    /**
     * @param map
     * @return java.util.List<Boolean>
     * @Method updateInsureDoctorMgtinfoById
     * @Param [map]
     * @description 更新医师信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     */
    @Override
    public WrapperResponse<Boolean> updateInsureDoctorMgtinfoById(Map map) {
        return WrapperResponse.success(insureDoctorMgtinfoBO.updateInsureDoctorMgtinfoById(MapUtils.get(map,"insureDoctorMgtinfoDO")));
    }

    /**
     * @param map
     * @return java.util.List<Boolean>
     * @Method updateInsureDoctorMgtinfoById
     * @Param [map]
     * @description 删除医师信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     */
    @Override
    public WrapperResponse<Boolean> deleteInsureDoctorMgtinfoById(Map map) {
        return WrapperResponse.success(insureDoctorMgtinfoBO.deleteInsureDoctorMgtinfoById(MapUtils.get(map,"insureDoctorMgtinfoDO")));
    }

    /**
     * @param map
     * @return InsureDoctorMgtinfoDO
     * @Method getInsureDoctorMgtinfoById
     * @Param [map]
     * @description 根据ID获取医师信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     */
    @Override
    public WrapperResponse<InsureDoctorMgtinfoDO> getInsureDoctorMgtinfoById(Map map) {
        return WrapperResponse.success(insureDoctorMgtinfoBO.getInsureDoctorMgtinfoById(MapUtils.get(map,"insureDoctorMgtinfoDO")));
    }
}
