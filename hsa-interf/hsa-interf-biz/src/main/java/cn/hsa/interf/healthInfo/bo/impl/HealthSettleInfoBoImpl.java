package cn.hsa.interf.healthInfo.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.interf.healthInfo.bo.HealthSettleBO;
import cn.hsa.module.interf.healthInfo.dao.HealthSettleInfoDAO;
import cn.hsa.module.interf.healthInfo.entity.TbCwjsxx;
import cn.hsa.module.interf.healthInfo.entity.TbCwsffs;
import cn.hsa.module.interf.healthInfo.entity.TbCwsfmx;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.interf.healthInfo.bo.impl
 * @Class_name: HealthSettleInfoBoImpl
 * @Describe: 财务报告信息BO实现层
 * @Author: liuliyun
 * @Email: liuliyun@powersi.com
 * @Date: 2022/5/20 16:36
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class HealthSettleInfoBoImpl extends HsafBO implements HealthSettleBO {

    @Resource
    private HealthSettleInfoDAO healthSettleInfoDAO;

    /**
     * @Description: 查询财务结算信息
     * @Param: [map]
     * @return: List<TbCwjsxx>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-20 16:37
     */
    @Override
    public List<TbCwjsxx> queryCwSettleInfo(Map map) {
        return healthSettleInfoDAO.queryCwSettleInfo(map);
    }

    /**
     * @Description: 查询财务结算详细信息
     * @Param: [map]
     * @return: List<TbCwsfmx>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-20 13:50
     */
    @Override
    public List<TbCwsfmx> queryCwSettleDetailInfo(Map map) {
        return healthSettleInfoDAO.queryCwSettleDetailInfo(map);
    }


    /**
     * @Description: 查询财务结算-收费方式（日结缴款）
     * @Param: [map]
     * @return: List<TbCwsffs>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-23 09:30
     */
    @Override
    public List<TbCwsffs> queryCwPayInfo(Map map) {
        return healthSettleInfoDAO.queryCwPayInfo(map);
    }


}
