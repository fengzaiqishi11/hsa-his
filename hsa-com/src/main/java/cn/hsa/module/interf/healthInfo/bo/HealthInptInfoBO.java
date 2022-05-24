package cn.hsa.module.interf.healthInfo.bo;

import cn.hsa.module.interf.healthInfo.entity.*;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.interf.healthInfo.bo
 * @Class_name: HealthInptInfoBO
 * @Describe: 住院健康信息上报
 * @Author: liuliyun
 * @Eamil: liyun.liu@powersi.com
 * @Date: 2022/5/10 11:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface HealthInptInfoBO {

    /**
     * @Description: 查询住院就诊记录
     * @Param: [map]
     * @return: List<TbZyjzjl>
     * @Author: liuliyun
     * @Date: 2022-05-12 13:46
     */
    List<TbZyjzjl> queryInptVisitInfo(Map map);

    /**
     * @Description: 查询住院转科记录
     * @Param: [map]
     * @return: List<TbZkjl>
     * @Author: liuliyun
     * @Date: 2022-05-12 14:57
     */
    List<TbZkjl> queryInptTurnDeptInfo(Map map);

    /**
     * @Description: 查询住院医嘱信息
     * @Param: [map]
     * @return: List<TbZyyzmx>
     * @Author: liuliyun
     * @Date: 2022-05-12 20:07
     */
    List<TbZyyzmx> queryInptAdviceInfo(Map map);
    /**
     * @Description: 查询住院医嘱执行信息
     * @Param: [map]
     * @return: List<TbZyyzzxjl>
     * @Author: liuliyun
     * @Date: 2022-05-12 20:07
     */
    List<TbZyyzzxjl> queryInptAdviceExecuteInfo(Map map);

    /**
     * @Description: 查询住院住院收费明细信息
     * @Param: [map]
     * @return: List<TbZysfmx>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-13 11:00
     */
    List<TbZysfmx> queryInptCostInfo(Map map);

    /**
     * @Description: 查询住院住院收费结算信息
     * @Param: [map]
     * @return: List<TbZysfjs>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-16 09:26
     */
    List<TbZysfjs> queryInptSettleInfo(Map map);


    /**
     * @Description: 查询住院护理三测单记录信息
     * @Param: [map]
     * @return: List<TbHlscdjl>
     * @Author: liuliyun
     * @Date: 2022-05-16 11:23
     */
    List<TbHlscdjl> queryInptThirdMeasurementInfo(Map map);

    /**
     * @Description: 查询电子病历索引信息
     * @Param: [map]
     * @return: List<TbDzblsyxx>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-16 14:23
     */
    List<TbDzblsyxx> queryEmrIndexInfo(Map map);


    /**
     * @Description: 查询一般护理记录信息
     * @Param: [map]
     * @return: List<TbEmrYbhljl>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-16 16:04
     */
    List<TbEmrYbhljl> queryInptNurseRecordInfo(Map map);

    /**
     * @Description: 查询出院小结信息
     * @Param: [map]
     * @return: List<TbEmrCyxj>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-17 09:35
     */
    List<TbEmrCyxj> queryInptEmrOutInfo(Map map);


    /**
     * @Description: 查询住院床位信息
     * @Param: [map]
     * @return: List<TbZycwjl>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-17 11:34
     */
    List<TbZycwjl> queryInptBedInfo(Map map);


}
