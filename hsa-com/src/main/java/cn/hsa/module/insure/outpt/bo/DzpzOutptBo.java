package cn.hsa.module.insure.outpt.bo;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.outpt.bo
 * @Class_name: OutptBo
 * @Describe(描述): 门诊医保统一开放调用 Bo 接口
 * @Author: xingyu.xie
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/11/09 9:26
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface DzpzOutptBo {


    /**
     * @Menthod uploadFee
     * @Desrciption 电子凭证解码
     * @param param 请求参数
     * @Author xingyu.xie
     * @Date 2020/11/26 10:07
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    String ecQuery(Map<String,Object> param);

    /**
     * @Menthod hosQueryInsu
     * @Desrciption 电子凭证解码
     * @param param 请求参数
     * @Author xingyu.xie
     * @Date 2020/11/26 10:07
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    String hosQueryInsu(Map<String,Object> param);

    /**
     * @Menthod uploadFee
     * @Desrciption 门诊费用上传并结算
     * @param param 请求参数
     * @Author xingyu.xie
     * @Date 2020/11/26 10:07
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    String backFee(Map<String,Object> param);

    /**
     * @Menthod uploadFee
     * @Desrciption 门诊费用上传并结算
     * @param param 请求参数
     * @Author xingyu.xie
     * @Date 2020/11/26 10:07
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    Boolean saveUpLoadFeeResult(Map<String,Object> param);
}
