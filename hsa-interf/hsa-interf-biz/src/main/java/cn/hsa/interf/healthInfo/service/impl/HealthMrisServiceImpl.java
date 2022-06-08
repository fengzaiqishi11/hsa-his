package cn.hsa.interf.healthInfo.service.impl;


import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.healthInfo.bo.HealthMrisInfoBO;
import cn.hsa.module.interf.healthInfo.entity.TbBasyssmx;
import cn.hsa.module.interf.healthInfo.entity.TbBasyzdmx;
import cn.hsa.module.interf.healthInfo.entity.TbBasyztb;
import cn.hsa.module.interf.healthInfo.service.HealthMrisInfoService;
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
@HsafRestPath("/service/health/mris")
@Slf4j
@Service("healthMrisInfoService_provider")
public class HealthMrisServiceImpl extends HsafService implements HealthMrisInfoService {

    @Resource
    HealthMrisInfoBO healthMrisInfoBO;

    /**
     * @Description: 查询住院病案首页主体信息
     * @Param: [map]
     * @return: WrapperResponse<List<TbBasyztb>>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-17 19:42
     */

    @Override
    public WrapperResponse<List<TbBasyztb>> queryInptMrisInfo(Map map) {
        List<TbBasyztb> tbBasyztbs = healthMrisInfoBO.queryInptMrisInfo(map);
        return WrapperResponse.success(tbBasyztbs);
    }


    /**
     * @Description: 查询住院病案首页--诊断信息
     * @Param: [map]
     * @return: WrapperResponse<List<TbBasyzdmx>>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-18 10:41
     */

    @Override
    public WrapperResponse<List<TbBasyzdmx>> queryInptMrisDiagnoseInfo(Map map) {
        List<TbBasyzdmx> tbBasyzdmxes = healthMrisInfoBO.queryInptMrisDiagnoseInfo(map);
        return WrapperResponse.success(tbBasyzdmxes);
    }


    /**
     * @Description: 查询住院病案首页--手术信息
     * @Param: [map]
     * @return: WrapperResponse<List<TbBasyssmx>>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-19 08:54
     */

    @Override
    public WrapperResponse<List<TbBasyssmx>> queryInptMrisOperInfo(Map map) {
        List<TbBasyssmx> tbBasyssmxes = healthMrisInfoBO.queryInptMrisOperInfo(map);
        return WrapperResponse.success(tbBasyssmxes);
    }


}
