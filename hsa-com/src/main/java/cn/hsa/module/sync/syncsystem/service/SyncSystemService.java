package cn.hsa.module.sync.syncsystem.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.syncsystem.dto.SyncSystemDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@FeignClient(value = "hsa-center")
public interface SyncSystemService {

    @GetMapping("/service/center/system/queryAll")
    WrapperResponse<List<SyncSystemDTO>> queryAll(Map map);

    @GetMapping("/service/center/system/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);

    WrapperResponse<Boolean> save(Map map);

    WrapperResponse<SyncSystemDTO> getById(Map map);

    WrapperResponse<Integer> querySystemSeqNo(Map map);
}
