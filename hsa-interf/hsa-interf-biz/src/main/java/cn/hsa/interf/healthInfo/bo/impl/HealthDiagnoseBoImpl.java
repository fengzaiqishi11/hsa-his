package cn.hsa.interf.healthInfo.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.interf.healthInfo.bo.HealthDiagnoseBO;
import cn.hsa.module.interf.healthInfo.dao.HealthDiagnoseDAO;
import cn.hsa.module.interf.healthInfo.entity.TbZdmx;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.interf.healthInfo.bo.impl
 * @Class_name: HealthDiagnoseBoImpl
 * @Describe: 诊断信息BO实现层
 * @Author: liuliyun
 * @Email: liuliyun@powersi.com
 * @Date: 2022/5/10 11:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class HealthDiagnoseBoImpl extends HsafBO implements HealthDiagnoseBO {

    @Resource
    private HealthDiagnoseDAO healthDiagnoseDAO;

    /**
     * @Description: 查询住院诊断明细信息
     * @Param: [map]
     * @return: List<TbBasyssmx>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-19 13:50
     */
    @Override
    public List<TbZdmx> queryInptDiagnoseInfo(Map map) {
        return healthDiagnoseDAO.queryInptDiagnoseInfo(map);
    }

}
