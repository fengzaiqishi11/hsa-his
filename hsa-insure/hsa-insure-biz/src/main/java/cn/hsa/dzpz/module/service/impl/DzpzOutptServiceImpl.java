package cn.hsa.dzpz.module.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.outpt.bo.DzpzOutptBo;
import cn.hsa.module.insure.outpt.service.DzpzOutptService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.module.service.impl
 * @Class_name: DzpzOutptServiceImpl
 * @Describe(描述): 门诊医保开放统一接口 service实现层
 * @Author: xingyu.xie
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/11/09 9:11
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/insure/dzpzOutpt")
@Service("DzpzOutptService_provider")
public class DzpzOutptServiceImpl extends HsafService implements DzpzOutptService {

    @Resource
    private DzpzOutptBo outptBo;

    /**
     * @Menthod uploadFee
     * @Desrciption 电子凭证解码
     * @param param 请求参数
     * @Author xingyu.xie
     * @Date 2020/11/26 10:07
     * @Return java.util.Map<String,Object><java.lang.String,java.lang.Object>
     */
    @Override
    @GetMapping("/ecQuery")
    public WrapperResponse ecQuery(Map<String,Object> param) {
        return WrapperResponse.success(outptBo.ecQuery(param));
    }

    /**
     * @Menthod hosQueryInsu
     * @Desrciption 电子凭证解码
     * @param param 请求参数
     * @Author xingyu.xie
     * @Date 2020/11/26 10:07
     * @Return java.util.Map<String,Object><java.lang.String,java.lang.Object>
     */
    @Override
    public WrapperResponse hosQueryInsu(Map<String,Object> param) {
        return WrapperResponse.success(outptBo.hosQueryInsu(param));
    }


    /**
     * @Menthod delFee
     * @Desrciption 门诊费用上传并结算
     * @param param 请求参数
     * @Author xingyu.xie
     * @Date 2020/11/26 10:07
     * @Return java.util.Map<String,Object><java.lang.String,java.lang.Object>
     */
    @Override
    public WrapperResponse backFee(Map<String,Object> param) {
        return WrapperResponse.success(outptBo.backFee(param));
    }

    /**
     * @Menthod delFee
     * @Desrciption 门诊费用上传并结算
     * @param param 请求参数
     * @Author xingyu.xie
     * @Date 2020/11/26 10:07
     * @Return java.util.Map<String,Object><java.lang.String,java.lang.Object>
     */
    @Override
    public WrapperResponse saveUpLoadFeeResult( Map<String,Object> param) {
        return WrapperResponse.success(outptBo.saveUpLoadFeeResult(param));
    }



}
