package cn.hsa.interf.healthInfo.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.interf.healthInfo.bo.HealthOperInfoBO;
import cn.hsa.module.interf.healthInfo.dao.HealthOperDAO;
import cn.hsa.module.interf.healthInfo.entity.TbSsmx;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.interf.healthInfo.bo.impl
 * @Class_name: HealthDiagnoseBoImpl
 * @Describe: 手术信息BO实现层
 * @Author: liuliyun
 * @Email: liuliyun@powersi.com
 * @Date: 2022/5/10 11:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class HealthOperBoImpl extends HsafBO implements HealthOperInfoBO {

    @Resource
    private HealthOperDAO healthOperDAO;

    /**
     * @Description: 查询住院手术明细信息
     * @Param: [map]
     * @return: List<TbZdmx>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-19 17:05
     */
    @Override
    public List<TbSsmx> queryInptOperInfo(Map map) {
        return healthOperDAO.queryInptOperInfo(map);
    }



}
