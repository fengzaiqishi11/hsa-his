package cn.hsa.interf.healthInfo.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.interf.healthInfo.bo.HealthInptInfoBO;
import cn.hsa.module.interf.healthInfo.dao.HealthInptDAO;
import cn.hsa.module.interf.healthInfo.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.interf.healthInfo.bo.impl
 * @Class_name: HealthInptBoImpl
 * @Describe: 住院基础信息业务BO实现层
 * @Author: liuliyun
 * @Email: liuliyun@powersi.com
 * @Date: 2022/5/10 11:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class HealthInptBoImpl extends HsafBO implements HealthInptInfoBO {

    @Resource
    private HealthInptDAO healthInptDAO;

  

    /**
     * @Description: 查询住院就诊记录
     * @Param: [map]
     * @return: List<TbZyjzjl>
     * @Author: liuliyun
     * @Date: 2022-05-12 13:43
     */
    @Override
    public List<TbZyjzjl> queryInptVisitInfo(Map map) {
        return healthInptDAO.queryInptVisitInfo(map);
    }


    /**
     * @Description: 查询住院就诊记录
     * @Param: [map]
     * @return: List<TbZkjl>
     * @Author: liuliyun
     * @Date: 2022-05-12 13:43
     */
    @Override
    public List<TbZkjl> queryInptTurnDeptInfo(Map map) {
        return healthInptDAO.queryInptTurnDeptInfo(map);
    }

    /**
     * @Description: 查询住院医嘱信息
     * @Param: [map]
     * @return: List<TbZyyzmx>
     * @Author: liuliyun
     * @Date: 2022-05-12 20:05
     */
    @Override
    public List<TbZyyzmx> queryInptAdviceInfo(Map map) {
        return healthInptDAO.queryInptAdviceInfo(map);
    }


    /**
     * @Description: 查询住院医嘱执行记录
     * @Param: [map]
     * @return: List<TbZyyzzxjl>
     * @Author: liuliyun
     * @Date: 2022-05-12 20:06
     */
    @Override
    public List<TbZyyzzxjl> queryInptAdviceExecuteInfo(Map map) {
        return healthInptDAO.queryInptAdviceExecuteInfo(map);
    }

    /**
     * @Description: 按时间段查询住院收费明细
     * @Param: [map]
     * @return: List<TbZysfmx>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-13 11:00
     */
    @Override
    public List<TbZysfmx> queryInptCostInfo(Map map) {
        return healthInptDAO.queryInptCostInfo(map);
    }


    /**
     * @Description: 按时间段查询住院收费结算信息
     * @Param: [map]
     * @return: List<TbZysfjs>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-16 09:22
     */
    @Override
    public List<TbZysfjs> queryInptSettleInfo(Map map) {
        return healthInptDAO.queryInptSettleInfo(map);
    }

    /**
     * @Description: 按时间段查询住院护理三测单记录信息
     * @Param: [map]
     * @return: List<TbHlscdjl>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-16 11:22
     */
    @Override
    public List<TbHlscdjl> queryInptThirdMeasurementInfo(Map map) {
        return healthInptDAO.queryInptThirdMeasurementInfo(map);
    }


    /**
     * @Description: 查询电子病历索引信息
     * @Param: [map]
     * @return: List<TbDzblsyxx>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-16 14:22
     */
    @Override
    public List<TbDzblsyxx> queryEmrIndexInfo(Map map) {
        return healthInptDAO.queryEmrIndexInfo(map);
    }


    /**
     * @Description: 查询一般护理记录信息
     * @Param: [map]
     * @return: List<TbEmrYbhljl>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-16 16:02
     */
    @Override
    public List<TbEmrYbhljl> queryInptNurseRecordInfo(Map map) {
        return healthInptDAO.queryInptNurseRecordInfo(map);
    }


    /**
     * @Description: 查询出院小结信息
     * @Param: [map]
     * @return: List<TbEmrCyxj>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-16 09:34
     */
    @Override
    public List<TbEmrCyxj> queryInptEmrOutInfo(Map map) {
        return healthInptDAO.queryInptEmrOutInfo(map);
    }

    /**
     * @Description: 查询住院床位信息
     * @Param: [map]
     * @return: List<TbZycwjl>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-17 11:28
     */
    @Override
    public List<TbZycwjl> queryInptBedInfo(Map map) {
        return healthInptDAO.queryInptBedInfo(map);
    }

 }
