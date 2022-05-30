package cn.hsa.interf.healthInfo.service.impl;


import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.healthInfo.bo.HealthInptInfoBO;
import cn.hsa.module.interf.healthInfo.entity.*;
import cn.hsa.module.interf.healthInfo.service.HealthInptInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *@Package_name: cn.hsa.interf.healthInfo.service.impl
 *@Class_name: HealthInptServiceImpl
 *@Describe:  患者住院健康信息查询
 *@Author: liuliyun
 *@Eamil: liyun.liu@powersi.com
 *@Date: 2022/05/10 9:48
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/health/inpt")
@Slf4j
@Service("healthInptInfoService_provider")
public class HealthInptServiceImpl extends HsafService implements HealthInptInfoService {

    @Resource
    HealthInptInfoBO healthInptInfoBO;

    /**
     * @Description: 查询住院就诊记录
     * @Param: [map]
     * @return: WrapperResponse<List<TbZyjzjl>>
     * @Author: liuliyun
     * @Date: 2022-05-12 13:45
     */
    @Override
    public WrapperResponse<List<TbZyjzjl>> queryInptVisitInfo(Map map) {
        List<TbZyjzjl> tbZyjzjls = healthInptInfoBO.queryInptVisitInfo(map);
        return WrapperResponse.success(tbZyjzjls);
    }

    /**
     * @Description: 查询住院转科信息
     * @Param: [map]
     * @return: WrapperResponse<List<TbZkjl>>
     * @Author: liuliyun
     * @Date: 2022-05-12 14:56
     */
    @Override
    public WrapperResponse<List<TbZkjl>> queryInptTurnDeptInfo(Map map) {
        List<TbZkjl> tbZkjls = healthInptInfoBO.queryInptTurnDeptInfo(map);
        return WrapperResponse.success(tbZkjls);
    }


    /**
     * @Description: 查询住院医嘱信息
     * @Param: [map]
     * @return: WrapperResponse<List<TbZyyzmx>>
     * @Author: liuliyun
     * @Date: 2022-05-12 20:08
     */
    @Override
    public WrapperResponse<List<TbZyyzmx>> queryInptAdviceInfo(Map map) {
        List<TbZyyzmx> tbZyyzmxes = healthInptInfoBO.queryInptAdviceInfo(map);
        return WrapperResponse.success(tbZyyzmxes);
    }



    /**
     * @Description: 查询住院医嘱执行信息
     * @Param: [map]
     * @return: WrapperResponse<List<TbZyyzzxjl>>
     * @Author: liuliyun
     * @Date: 2022-05-12 20:10
     */
    @Override
    public WrapperResponse<List<TbZyyzzxjl>> queryInptAdviceExecuteInfo(Map map) {
        List<TbZyyzzxjl> tbZyyzzxjls = healthInptInfoBO.queryInptAdviceExecuteInfo(map);
        return WrapperResponse.success(tbZyyzzxjls);
    }


    /**
     * @Description: 查询住院收费明细信息
     * @Param: [map]
     * @return: WrapperResponse<List<TbZysfmx>>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-13 11:00
     */
    @Override
    public WrapperResponse<List<TbZysfmx>> queryInptCostInfo(Map map) {
        List<TbZysfmx> tbZysfmxes = healthInptInfoBO.queryInptCostInfo(map);
        return WrapperResponse.success(tbZysfmxes);
    }


    /**
     * @Description: 查询住院收费结算信息
     * @Param: [map]
     * @return: WrapperResponse<List<TbZysfjs>>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-13 11:00
     */
    @Override
    public WrapperResponse<List<TbZysfjs>> queryInptSettleInfo(Map map) {
        List<TbZysfjs> tbZysfjs = healthInptInfoBO.queryInptSettleInfo(map);
        return WrapperResponse.success(tbZysfjs);
    }


    /**
     * @Description: 查询住院三测单记录信息
     * @Param: [map]
     * @return: WrapperResponse<List<TbHlscdjl>>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-16 11:21
     */
    @Override
    public WrapperResponse<List<TbHlscdjl>> queryInptThirdMeasurementInfo(Map map) {
        List<TbHlscdjl> tbHlscdjls = healthInptInfoBO.queryInptThirdMeasurementInfo(map);
        return WrapperResponse.success(tbHlscdjls);
    }


    /**
     * @Description: 查询电子病历索引信息
     * @Param: [map]
     * @return: WrapperResponse<List<TbDzblsyxx>>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-16 14:23
     */

    @Override
    public WrapperResponse<List<TbDzblsyxx>> queryEmrIndexInfo(Map map) {
        List<TbDzblsyxx> tbDzblsyxxes = healthInptInfoBO.queryEmrIndexInfo(map);
        return WrapperResponse.success(tbDzblsyxxes);
    }

    /**
     * @Description: 查询一般护理记录信息
     * @Param: [map]
     * @return: WrapperResponse<List<TbEmrYbhljl>>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-16 16:03
     */

    @Override
    public WrapperResponse<List<TbEmrYbhljl>> queryInptNurseRecordInfo(Map map) {
        List<TbEmrYbhljl> tbEmrYbhljls = healthInptInfoBO.queryInptNurseRecordInfo(map);
        return WrapperResponse.success(tbEmrYbhljls);
    }


    /**
     * @Description: 查询出院小结信息
     * @Param: [map]
     * @return: WrapperResponse<List<TbEmrCyxj>>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-17 09:38
     */

    @Override
    public WrapperResponse<List<TbEmrCyxj>> queryInptEmrOutInfo(Map map) {
        List<TbEmrCyxj> tbEmrCyxjs = healthInptInfoBO.queryInptEmrOutInfo(map);
        return WrapperResponse.success(tbEmrCyxjs);
    }


    /**
     * @Description: 查询住院床位信息
     * @Param: [map]
     * @return: WrapperResponse<List<TbZycwjl>>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-17 11:31
     */

    @Override
    public WrapperResponse<List<TbZycwjl>> queryInptBedInfo(Map map) {
        List<TbZycwjl> tbZycwjls = healthInptInfoBO.queryInptBedInfo(map);
        return WrapperResponse.success(tbZycwjls);
    }


}
