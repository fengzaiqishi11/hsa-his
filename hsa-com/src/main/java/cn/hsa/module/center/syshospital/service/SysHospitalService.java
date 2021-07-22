package cn.hsa.module.center.syshospital.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.sys.hospital.service
 * @Class_name:: sysHospitalService
 * @Description: 医院信息维护service接口层（提供给dubbo调用）
 * @Author: zhangxuan
 * @Date: 2020-07-30 11:05
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-sys")
public interface SysHospitalService {

    /**
     * @Method
     * @Desrciption
     * @Param code（医院编码）
     * @Author zhangxuan
     * @Date 2020-07-30 11:07
     * @Return map
     **/
    @PostMapping("/service/sys/hospital/getByHospCode")
    WrapperResponse<CenterHospitalDTO> getByHospCode(Map map);

    /**
     * @Menthod update()
     * @Desrciption  修改医院信息
     * @Author zhangxuan
     * @Date   2020/7/30 11:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.hospital.dto.sysHospitalDTO>
     * @param map
     * */
    @PostMapping("/service/sys/hospital/update")
    WrapperResponse<Boolean> update(Map map);

}
