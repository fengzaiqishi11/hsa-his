package cn.hsa.interf.healthInfo.service.impl;


import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.healthInfo.bo.HealthSettleBO;
import cn.hsa.module.interf.healthInfo.entity.TbCwjsxx;
import cn.hsa.module.interf.healthInfo.entity.TbCwsffs;
import cn.hsa.module.interf.healthInfo.entity.TbCwsfmx;
import cn.hsa.module.interf.healthInfo.service.HealthSettleInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *@Package_name: cn.hsa.interf.healthInfo.service.impl
 *@Class_name: HealthCwSettleServiceImpl
 *@Describe:  财务报告信息查询
 *@Author: liuliyun
 *@Eamil: liyun.liu@powersi.com
 *@Date: 2022/05/20 16:47
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/health/settle")
@Slf4j
@Service("healthSettleInfoService_provider")
public class HealthCwSettleServiceImpl extends HsafService implements HealthSettleInfoService {

    @Resource
    HealthSettleBO healthSettleBO;

    /**
     * @Description: 查询财务结算信息（日结缴款）
     * @Param: [map]
     * @return: List<TbCwjsxx>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-20 16:48
     */
    @Override
    public WrapperResponse<List<TbCwjsxx>> queryCwSettleInfo(Map map) {
        List<TbCwjsxx> tbCwjsxxes = healthSettleBO.queryCwSettleInfo(map);
        return WrapperResponse.success(tbCwjsxxes);
    }

    /**
     * @Description: 查询财务结算详细信息（日结缴款）
     * @Param: [map]
     * @return: List<TbCwsfmx>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-20 16:48
     */
    @Override
    public WrapperResponse<List<TbCwsfmx>> queryCwSettleDetailInfo(Map map) {
        List<TbCwsfmx> tbCwsfmxes = healthSettleBO.queryCwSettleDetailInfo(map);
        return WrapperResponse.success(tbCwsfmxes);
    }

    /**
     * @Description: 查询财务结算-收费方式（日结缴款）
     * @Param: [map]
     * @return: List<TbCwsffs>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-23 09:39
     */
    @Override
    public WrapperResponse<List<TbCwsffs>> queryCwPayInfo(Map map) {
        List<TbCwsffs> tbCwsffs = healthSettleBO.queryCwPayInfo(map);
        return WrapperResponse.success(tbCwsffs);
    }
}
