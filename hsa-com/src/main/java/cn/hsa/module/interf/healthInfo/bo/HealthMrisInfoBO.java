package cn.hsa.module.interf.healthInfo.bo;

import cn.hsa.module.interf.healthInfo.entity.*;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.interf.healthInfo.bo
 * @Class_name: HealthMrisInfoBO
 * @Describe:  病案健康信息上报
 * @Author: liuliyun
 * @Eamil: liyun.liu@powersi.com
 * @Date: 2022/5/10 11:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface HealthMrisInfoBO {

    /**
     * @Description: 查询住院病案首页主体信息
     * @Param: [map]
     * @return: List<TbBasyztb>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-17 19:44
     */
    List<TbBasyztb> queryInptMrisInfo(Map map);

    /**
     * @Description: 查询住院病案首页--诊断信息
     * @Param: [map]
     * @return: List<TbBasyzdmx>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-18 10:43
     */
    List<TbBasyzdmx> queryInptMrisDiagnoseInfo(Map map);


    /**
     * @Description: 查询住院病案首页--诊断信息
     * @Param: [map]
     * @return: List<TbBasyssmx>
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-05-18 10:43
     */
    List<TbBasyssmx> queryInptMrisOperInfo(Map map);




}
