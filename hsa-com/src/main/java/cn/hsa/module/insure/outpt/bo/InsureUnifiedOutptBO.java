package cn.hsa.module.insure.outpt.bo;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.outpt.bo
 * @Class_name: InsureUnifiedPayOutptBo
 * @Describe: 统一支付平台，门诊实现层接口
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/2/10 8:07
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface InsureUnifiedOutptBO {

    /**
     * @Menthod: UP_4301
     * @Desrciption: 医保统一支付平台：门急诊诊疗记录，上传单次病人就诊信息
     * @Param:
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-08-17 19:45
     * @Return:
     **/
    Map<String, Object> UP_4301(Map<String, Object> map);

    /**
     * @Menthod: UP_4302
     * @Desrciption: 医保统一支付平台：急诊留观手术及抢救信息
     * @Param:
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-08-17 19:45
     * @Return:
     **/
    Map<String, Object> UP_4302(Map<String, Object> map);

    /**
     * 医保统一支付平台: 线上费用明细上传
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-04-26 9:38
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    Map<String, Object> UP_6201(Map<String, Object> map);

    /**
     * 医保订单结算结果查询
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-05-09 16:42
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    Map<String, Object> UP_6301(Map<String, Object> map);

    /**
     * 费用明细上传撤销
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-05-10 14:09
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    Map<String, Object> UP_6401(Map<String, Object> map);

    Map<String, Object> UP_6203(Map<String, Object> map);

    /**
     * 线上医保移动支付完成的结算订单，可通过此接口进行退款
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-06-15 9:36
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    Map<String, Object> AmpRefund(Map<String, Object> map);
}
