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
     * @param map
     * @return java.lang.Object
     * @method AMP_HOS_001
     * @author wang'qiao
     * @date 2022/6/14 16:22
     * @description 通过区域医保服务平台推送消息（待结算、结算成功、检查报告、挂号通知）等信息给用户,待结算消息推送（必选）
     **/
    boolean AMP_HOS_001(Map<String, Object> map);

    /**
     * 线上医保移动支付完成的结算订单，可通过此接口进行退款
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-06-15 9:36
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    Map<String, Object> AmpRefund(Map<String, Object> map);

    /**
     * @param map
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map < java.lang.String, java.lang.Object>>
     * @method refundInquiry
     * @author wang'qiao
     * @date 2022/6/20 14:55
     * @description 查询退款结果（AMP_HOS_003） 调用AMP_HOS_002平台退款申请接口后，根据此状态来查询对应的退款具体结果
     **/
    Map<String, Object> refundInquiry(Map<String, Object> map);

    /**
     * @param map
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map < java.lang.String, java.lang.Object>>
     * @method reconciliationDocument
     * @author wang'qiao
     * @date 2022/6/20 19:48
     * @description 对账文件获取  下载后定点医疗机构可自行解析此对账文件并与定点机构的对账文件和医保核心的对账文件进行三方账目的对账
     **/
    Map<String, Object> reconciliationDocument(Map<String, Object> map);
}
