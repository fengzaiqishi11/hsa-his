package cn.hsa.module.interf.inpt.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.interf.inpt.service
 * @Class_name: InptMrisImportService
 * @Describe: 病案首页接口
 * @Author: liuliyun
 * @Eamil: liyun.liu@powersi.com
 * @Date: 2021/7/19 14:40
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-interf")
public interface InptMrisImportService {

    /**
     *
     * @param map
     * @return String
     */
    @GetMapping("/service/interf/inptMrisImport/importMrisInfo")
    WrapperResponse<String> importMrisInfo(Map map) throws Exception;

}
