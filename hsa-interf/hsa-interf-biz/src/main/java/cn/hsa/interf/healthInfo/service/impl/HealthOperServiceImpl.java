package cn.hsa.interf.healthInfo.service.impl;


import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.healthInfo.bo.HealthOperInfoBO;
import cn.hsa.module.interf.healthInfo.entity.TbSsmx;
import cn.hsa.module.interf.healthInfo.service.HealthOperInfoService;
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
@HsafRestPath("/service/health/oper")
@Slf4j
@Service("healthOperInfoService_provider")
public class HealthOperServiceImpl extends HsafService implements HealthOperInfoService {

    @Resource
    HealthOperInfoBO healthOperInfoBO;


    /**
     * @Description: 查询住院手术明细信息
     * @Param: [map]
     * @return: WrapperResponse<List<TbZdmx>>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-19 15:40
     */

    @Override
    public WrapperResponse<List<TbSsmx>> queryInptOperInfo(Map map) {
        List<TbSsmx> tbSsmxes = healthOperInfoBO.queryInptOperInfo(map);
        return WrapperResponse.success(tbSsmxes);
    }


}
