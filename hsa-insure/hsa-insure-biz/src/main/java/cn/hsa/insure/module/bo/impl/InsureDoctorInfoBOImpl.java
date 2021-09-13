package cn.hsa.insure.module.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.insure.module.bo.InsureDoctorInfoBO;
import cn.hsa.module.insure.module.dao.InsureDoctorInfoDAO;
import cn.hsa.module.insure.module.dao.InsureDoctorMgtinfoDAO;
import cn.hsa.module.insure.module.dto.InsureDoctorInfoDTO;
import cn.hsa.module.insure.module.entity.InsureDoctorMgtinfoDO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.insure.module.bo.impl
 * @Class_name: InsureDoctorInfoImpl
 * @Describe(描述): 医保医师服务 Bo实现层
 * @Author: liaojiguang
 * @Eamil: jiguang.liao@powersi.com.cn
 * @Date: 2021/09/13 19:17
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
public class InsureDoctorInfoBOImpl extends HsafBO implements InsureDoctorInfoBO {

    @Resource
    private InsureDoctorInfoDAO insureDoctorInfoDAO;

    @Resource
    private InsureDoctorMgtinfoDAO insureDoctorMgtinfoDAO;

    /**
     * @param insureDoctorInfoDTO
     * @return InsureDoctorInfoDO
     * @Method queryInsureDoctorInfo
     * @Param [map]
     * @description 获取医师信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     */
    @Override
    public List<InsureDoctorInfoDTO> queryInsureDoctorInfo(InsureDoctorInfoDTO insureDoctorInfoDTO) {
        return insureDoctorInfoDAO.queryInsureDoctorInfoPage(insureDoctorInfoDTO);
    }

    /**
     * @param insureDoctorInfoDTO
     * @return Boolean
     * @Method insertInsureDoctorInfo
     * @Param [map]
     * @description 新增医师信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     */
    @Override
    public Boolean insertInsureDoctorInfo(InsureDoctorInfoDTO insureDoctorInfoDTO) {
        insureDoctorInfoDAO.insertInsureDoctorInfo(insureDoctorInfoDTO);

        /*if () {

        }*/

        // insureDoctorMgtinfoDAO.insertInsureDoctorMgtinfos()
        return true;
    }

    /**
     * @param insureDoctorInfoDTO
     * @return Boolean
     * @Method updateInsureDoctorInfoById
     * @Param [map]
     * @description 更新医师信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     */
    @Override
    public Boolean updateInsureDoctorInfoById(InsureDoctorInfoDTO insureDoctorInfoDTO) {
        return insureDoctorInfoDAO.updateInsureDoctorInfoById(insureDoctorInfoDTO) > 0;
    }

    /**
     * @param insureDoctorInfoDTO
     * @return Boolean
     * @Method updateInsureDoctorInfoById
     * @Param [map]
     * @description 删除医师信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     */
    @Override
    public Boolean deleteInsureDoctorInfoById(InsureDoctorInfoDTO insureDoctorInfoDTO) {
        return insureDoctorInfoDAO.deleteInsureDoctorInfoById(insureDoctorInfoDTO) > 0;
    }

    /**
     * @param insureDoctorInfoDTO
     * @return InsureDoctorInfoDO
     * @Method getInsureDoctorInfoById
     * @Param [map]
     * @description 根据IDh获取医师信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     */
    @Override
    public InsureDoctorInfoDTO getInsureDoctorInfoById(InsureDoctorInfoDTO insureDoctorInfoDTO) {
        return insureDoctorInfoDAO.getInsureDoctorInfoById(insureDoctorInfoDTO);
    }
}
