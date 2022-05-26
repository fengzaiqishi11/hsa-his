package cn.hsa.module.interf.healthInfo.bo;

import cn.hsa.module.interf.healthInfo.entity.TbSsmx;
import cn.hsa.module.interf.healthInfo.entity.TbZdmx;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.interf.healthInfo.bo
 * @Class_name: HealthDiagnoseBO
 * @Describe: 手术健康信息上报
 * @Author: liuliyun
 * @Eamil: liyun.liu@powersi.com
 * @Date: 2022/5/10 11:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface HealthOperInfoBO {

    /**
     * @Description: 查询住院手术明细信息
     * @Param: [map]
     * @return: List<TbSsmx>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-19 15:42
     */
    List<TbSsmx> queryInptOperInfo(Map map);


}
