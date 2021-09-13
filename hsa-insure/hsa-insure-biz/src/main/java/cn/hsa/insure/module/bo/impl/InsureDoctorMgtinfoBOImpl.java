package cn.hsa.insure.module.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.insure.module.bo.InsureDoctorMgtinfoBO;
import cn.hsa.module.insure.module.dao.InsureDoctorMgtinfoDAO;
import cn.hsa.module.insure.module.entity.InsureDoctorMgtinfoDO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.insure.module.bo.impl
 * @Class_name: InsureDoctorMgtinfoImpl
 * @Describe(描述): 医保医师职业服务 Bo实现层
 * @Author: liaojiguang
 * @Eamil: jiguang.liao@powersi.com.cn
 * @Date: 2021/09/13 19:17
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
public class InsureDoctorMgtinfoBOImpl extends HsafBO implements InsureDoctorMgtinfoBO {

    @Resource
    private InsureDoctorMgtinfoDAO insureDoctorMgtinfoDAO;

    /**
     * @param insureDoctorMgtinfoDO
     * @return InsureDoctorMgtinfoDO
     * @Method queryInsureDoctorMgtinfo
     * @Param [map]
     * @description 获取医师职业信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     */
    @Override
    public List<InsureDoctorMgtinfoDO> queryInsureDoctorMgtinfo(InsureDoctorMgtinfoDO insureDoctorMgtinfoDO) {
        return insureDoctorMgtinfoDAO.queryInsureDoctorMgtinfoPage(insureDoctorMgtinfoDO);
    }

    /**
     * @param insureDoctorMgtinfoDO
     * @return Boolean
     * @Method insertInsureDoctorMgtinfo
     * @Param [map]
     * @description 新增医师职业信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     */
    @Override
    public Boolean insertInsureDoctorMgtinfo(InsureDoctorMgtinfoDO insureDoctorMgtinfoDO) {
        return insureDoctorMgtinfoDAO.insertInsureDoctorMgtinfo(insureDoctorMgtinfoDO) > 0;
    }

    /**
     * @param insureDoctorMgtinfoDO
     * @return Boolean
     * @Method updateInsureDoctorMgtinfoById
     * @Param [map]
     * @description 更新医师职业信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     */
    @Override
    public Boolean updateInsureDoctorMgtinfoById(InsureDoctorMgtinfoDO insureDoctorMgtinfoDO) {
        return insureDoctorMgtinfoDAO.updateInsureDoctorMgtinfoById(insureDoctorMgtinfoDO) > 0;
    }

    /**
     * @param insureDoctorMgtinfoDO
     * @return Boolean
     * @Method updateInsureDoctorMgtinfoById
     * @Param [map]
     * @description 删除医师职业信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     */
    @Override
    public Boolean deleteInsureDoctorMgtinfoById(InsureDoctorMgtinfoDO insureDoctorMgtinfoDO) {
        return insureDoctorMgtinfoDAO.deleteInsureDoctorMgtinfoById(insureDoctorMgtinfoDO) > 0;
    }

    /**
     * @param insureDoctorMgtinfoDO
     * @return InsureDoctorMgtinfoDO
     * @Method getInsureDoctorMgtinfoById
     * @Param [map]
     * @description 根据ID获取医师职业信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     */
    @Override
    public InsureDoctorMgtinfoDO getInsureDoctorMgtinfoById(InsureDoctorMgtinfoDO insureDoctorMgtinfoDO) {
        return insureDoctorMgtinfoDAO.getInsureDoctorMgtinfoById(insureDoctorMgtinfoDO);
    }
}
