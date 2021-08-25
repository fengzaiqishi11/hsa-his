package cn.hsa.module.insure.outpt.bo;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.outpt.bo
 * @Class_name: InsureUnifiedPayMatterBO
 * @Describe: 统一支付平台---事前事中分析
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-02-24 15:07
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface InsureUnifiedPayMatterBO {
    /**
     * @Menthod: UP_3660
     * @Desrciption: 事前提醒
     * @Param:
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-02-24 15:12
     * @Return:
     **/
    Map<String, Object> UP_3660(Map<String, Object> map);

    /**
     * @Menthod: UP_3661
     * @Desrciption: 事中预警
     * @Param:
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-02-24 15:12
     * @Return:
     **/
    Map<String, Object> UP_3661(Map<String, Object> map);
}
