package cn.hsa.module.phar.pharoutreceive.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.phar.pharoutreceive.service
 * @Class_name: PharOutReceiveDetailService
 * @Describe(描述):门诊领药申请明细信息Service层
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/09/16 15:24
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-stro")
public interface PharOutReceiveDetailService {

    /**
     * @Menthod batchInsert
     * @Desrciption  批量新增门诊领药申请明细信息
     * @param param 参数
     * @Author Ou·Mr
     * @Date 2020/9/16 15:13
     * @Return int 受影响的行数
     */
    @PostMapping("/service/stro/pharOutReceiveDetail/batchInsert")
    WrapperResponse batchInsert(Map param);

}
