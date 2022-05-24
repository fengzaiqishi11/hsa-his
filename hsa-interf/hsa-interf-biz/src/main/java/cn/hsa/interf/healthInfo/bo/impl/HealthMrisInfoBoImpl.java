package cn.hsa.interf.healthInfo.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.interf.healthInfo.bo.HealthMrisInfoBO;
import cn.hsa.module.interf.healthInfo.dao.HealthMrisDAO;
import cn.hsa.module.interf.healthInfo.entity.TbBasyssmx;
import cn.hsa.module.interf.healthInfo.entity.TbBasyzdmx;
import cn.hsa.module.interf.healthInfo.entity.TbBasyztb;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.interf.healthInfo.bo.impl
 * @Class_name: HealthMrisInfoBoImpl
 * @Describe: 病案首頁基础信息业务BO实现层
 * @Author: liuliyun
 * @Email: liuliyun@powersi.com
 * @Date: 2022/5/10 11:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class HealthMrisInfoBoImpl extends HsafBO implements HealthMrisInfoBO {

    @Resource
    private HealthMrisDAO healthMrisDAO;

   

    /**
     * @Description: 查询住院病案首页主体信息
     * @Param: [map]
     * @return: List<TbBasyztb>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-17 19:41
     */
    @Override
    public List<TbBasyztb> queryInptMrisInfo(Map map) {
        return healthMrisDAO.queryInptMrisInfo(map);
    }


    /**
     * @Description: 查询住院病案首页--诊断信息
     * @Param: [map]
     * @return: List<TbBasyzdmx>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-18 10:42
     */
    @Override
    public List<TbBasyzdmx> queryInptMrisDiagnoseInfo(Map map) {
        return healthMrisDAO.queryInptMrisDiagnoseInfo(map);
    }


    /**
     * @Description: 查询住院病案首页--手术信息
     * @Param: [map]
     * @return: List<TbBasyssmx>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-18 16:31
     */
    @Override
    public List<TbBasyssmx> queryInptMrisOperInfo(Map map) {
        return healthMrisDAO.queryInptMrisOperInfo(map);
    }
}
