package cn.hsa.sync.syncsystem.controller;

import cn.hsa.base.CenterBaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.syncsystem.dto.SyncSystemDTO;
import cn.hsa.module.sync.syncsystem.service.SyncSystemService;
import cn.hsa.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/center/sync/system")
@Slf4j
public class SyncSystemController extends CenterBaseController {

    @Resource
    private SyncSystemService syncSystemService_consumer;

    @GetMapping("/queryAll")
    public WrapperResponse<List<SyncSystemDTO>> queryAll(SyncSystemDTO syncSystemDTO) {
        Map map = new HashMap();
        map.put("syncSystemDTO", syncSystemDTO);
        return syncSystemService_consumer.queryAll(map);
    }

    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(SyncSystemDTO syncSystemDTO) {
        Map map = new HashMap();
        map.put("syncSystemDTO", syncSystemDTO);
        return syncSystemService_consumer.queryPage(map);
    }

    @PostMapping("/save")
    public WrapperResponse<Boolean> save(@RequestBody SyncSystemDTO syncSystemDTO) {
        syncSystemDTO.setCrteId(userId);
        syncSystemDTO.setCrteName(userName);
        syncSystemDTO.setCrteTime(DateUtils.getNow());

        Map map = new HashMap();
        map.put("syncSystemDTO", syncSystemDTO);
        return syncSystemService_consumer.save(map);
    }

    @GetMapping("/getById")
    public  WrapperResponse<SyncSystemDTO> getById(SyncSystemDTO syncSystemDTO){
        Map map = new HashMap();
        map.put("syncSystemDTO",syncSystemDTO);
        return syncSystemService_consumer.getById(map);
    }

    @GetMapping("/querySystemSeqNo")
    public WrapperResponse<Integer> querySystemSeqNo(SyncSystemDTO syncSystemDTO){
        Map map = new HashMap();
        map.put("syncSystemDTO", syncSystemDTO);
        return syncSystemService_consumer.querySystemSeqNo(map);
    }
}
