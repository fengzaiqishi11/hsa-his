package cn.hsa.center.syshospital.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import cn.hsa.module.center.syshospital.bo.SysHospitalBO;
import cn.hsa.module.center.syshospital.dao.SysHospitalDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Package_name: cn.hsa.sys.hospital.bo.impl
 * @Class_name:: sysHospitalBOImpl
 * @Description: 医院信息管理业务逻辑实现层
 * @Author: zhangxuan
 * @Date: 2020-07-30 13:48
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class SysHospitalBOImpl extends HsafBO implements SysHospitalBO {
    /**
     * 医院数据库访问接口
     */
    @Resource
    private SysHospitalDAO sysHospitalDao;

    /**
     * @Menthod getByHospCode()
     * @Desrciption  通过code查询医院信息
     * @Param
     * 1. map
     * @Author zhangxuan
     * @Date   2020/7/30 15:45
     * @Return cn.hsa.module.sys.hospital.dao.sysHospitalDTO
     **/
    @Override
    public CenterHospitalDTO getByHospCode(CenterHospitalDTO centerHospitalDTO) {
        return this.sysHospitalDao.getByHospCode(centerHospitalDTO);
    }
    /**
     * @Menthod update()
     * @Desrciption 修改医院信息
     * @Param
     * 1. sysHospitalDTO  医院数据传输对象
     * @Author zhangxuan
     * @Date   2020/7/30 15:58
     * @Return int
     **/
    @Override
    public boolean update(CenterHospitalDTO centerHospitalDTO) {
        return this.sysHospitalDao.update(centerHospitalDTO) >0;
    }
}
