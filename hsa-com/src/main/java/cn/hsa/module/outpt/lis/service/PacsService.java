package cn.hsa.module.outpt.lis.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.lis.service
 * @Class_name: PacsService
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2021-01-11 15:06
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-outpt")
public interface PacsService {

    @PostMapping("/service/outpt/pacs/getPacsDeptList")
    WrapperResponse<Boolean> getPacsDeptList(Map map);

    @PostMapping("/service/outpt/pacs/getPacsDocList")
    WrapperResponse<Boolean> getPacsDocList(Map map);

    @PostMapping("/service/outpt/pacs/getPacsItemList")
    WrapperResponse<Boolean> getPacsItemList(Map map);

    @PostMapping("/service/outpt/pacs/savePacsInspectApply")
    WrapperResponse<Boolean> savePacsInspectApply(Map map);

    @PostMapping("/service/outpt/pacs/SavePacsInspectCallback")
    WrapperResponse<Integer> SavePacsInspectCallback(Map map);
}
