package cn.hsa.center.syshospital.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import cn.hsa.module.center.syshospital.bo.SysHospitalBO;
import cn.hsa.module.center.syshospital.service.SysHospitalService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.sys.hospital.service.impl
 * @Class_name: sysHospitalServiceImpl
 * @Describe:  医院service接口实现层（提供给dubbo调用）
 * @Author: zhangxuan
 * @Email: zhangxuan@powersi.com
 * @Date: 2020/7/30 11:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/sys/hospital")
@Slf4j
@Service("sysHospitalService_provider")
public class SysHospitalServiceImpl extends HsafService implements SysHospitalService {
    /**
     * 医院业务逻辑接口
     */
    @Resource
    private SysHospitalBO sysHospitalBO;

    /**
     * @Menthod getByHospCode
     * @Desrciption  通过code查询医院信息
     * @Param
     * 1. map
     * @Author zhangxuan
     * @Date   2020/7/30 11:26
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.hospital.dto.sysHospitalDTO>
     **/
    @Override
    public WrapperResponse<CenterHospitalDTO> getByHospCode(Map map) {
        return WrapperResponse.success(sysHospitalBO.getByHospCode(MapUtils.get(map,"centerHospitalDTO")));
    }

    /**
     * @Menthod update()
     * @Desrciption 修改医院信息
     * @Param
     * 1. sysHospitalDTO  医院数据传输对象
     * @Author zhangxuan
     * @Date   2020/7/28 9:27
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> update(Map map) {
        return WrapperResponse.success(sysHospitalBO.update(MapUtils.get(map,"centerHospitalDTO")));
    }
}
