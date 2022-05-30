package cn.hsa.interf.healthInfo.service.impl;


import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.healthInfo.bo.HealthDiagnoseBO;
import cn.hsa.module.interf.healthInfo.entity.TbZdmx;
import cn.hsa.module.interf.healthInfo.service.HealthDiagnoseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *@Package_name: cn.hsa.interf.healthInfo.service.impl
 *@Class_name: HealthInfoServiceImpl
 *@Describe:  患者健康信息查询
 *@Author: liuliyun
 *@Eamil: liyun.liu@powersi.com
 *@Date: 2022/05/10 9:48
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/health/diagnose")
@Slf4j
@Service("healthDiagnoseInfoService_provider")
public class HealthDiagnoseServiceImpl extends HsafService implements HealthDiagnoseInfoService {

    @Resource
    HealthDiagnoseBO healthDiagnoseBO;

    /**
     * @Description: 查询住院诊断明细信息
     * @Param: [map]
     * @return: WrapperResponse<List<TbZdmx>>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-19 08:54
     */

    @Override
    public WrapperResponse<List<TbZdmx>> queryInptDiagnoseInfo(Map map) {
        List<TbZdmx> zdmxes = healthDiagnoseBO.queryInptDiagnoseInfo(map);
        return WrapperResponse.success(zdmxes);
    }


}
