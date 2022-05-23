package cn.hsa.module.interf.healthInfo.bo;

import cn.hsa.module.interf.healthInfo.entity.TbCwjsxx;
import cn.hsa.module.interf.healthInfo.entity.TbCwsffs;
import cn.hsa.module.interf.healthInfo.entity.TbCwsfmx;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.interf.healthInfo.bo
 * @Class_name: HealthSettleBO
 * @Describe: 财务报告信息上报
 * @Author: liuliyun
 * @Eamil: liyun.liu@powersi.com
 * @Date: 2022/5/10 11:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface HealthSettleBO {

    /**
     * @Description: 查询财务结算信息（日结缴款）
     * @Param: [map]
     * @return: List<TbCwjsxx>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-20 15:33
     */
    List<TbCwjsxx> queryCwSettleInfo(Map map);
    /**
     * @Description: 查询财务结算详细信息（日结缴款）
     * @Param: [map]
     * @return: List<TbCwsfmx>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-20 16:27
     */
    List<TbCwsfmx> queryCwSettleDetailInfo(Map map);

    /**
     * @Description: 查询财务结算-收费方式（日结缴款）
     * @Param: [map]
     * @return: List<TbCwsffs>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-23 09:29
     */
    List<TbCwsffs> queryCwPayInfo(Map map);


}
