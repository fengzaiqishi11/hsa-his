package cn.hsa.sync.syncsystem.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.syncsystem.bo.SyncSystemBO;
import cn.hsa.module.sync.syncsystem.dto.SyncSystemDTO;
import cn.hsa.module.sync.syncsystem.service.SyncSystemService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@HsafRestPath("/service/center/system")
@Slf4j
@Service("syncSystemService_provider")
public class SyncSystemServiceImpl extends HsafBO implements SyncSystemService {

    @Resource
    private SyncSystemBO syncSystemBO;

    @HsafRestPath(value = "/queryAll", method = RequestMethod.GET)
    @Override
    public WrapperResponse<List<SyncSystemDTO>> queryAll(Map map) {
        try {
            SyncSystemDTO syncSystemDTO = MapUtils.get(map, "syncSystemDTO");
            return WrapperResponse.success(syncSystemBO.queryAll(syncSystemDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }

    @HsafRestPath(value = "/queryPage", method = RequestMethod.GET)
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        try {
            SyncSystemDTO syncSystemDTO = MapUtils.get(map, "syncSystemDTO");
            return WrapperResponse.success(syncSystemBO.queryPage(syncSystemDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }

    @Override
    @PostMapping("/service/center/system/save")
    public WrapperResponse<Boolean> save(Map map) {
        return WrapperResponse.success(syncSystemBO.save(MapUtils.get(map,"syncSystemDTO")));
    }

    @Override
    @GetMapping("/service/center/system/getById")
    public WrapperResponse<SyncSystemDTO> getById(Map map) {
        return WrapperResponse.success(syncSystemBO.getById(MapUtils.get(map,"syncSystemDTO")));
    }

    @Override
    @GetMapping("/service/center/system/querySystemSeqNo")
    public WrapperResponse<Integer> querySystemSeqNo(Map map) {
        return WrapperResponse.success(syncSystemBO.querySystemSeqNo(MapUtils.get(map,"syncSystemDTO")));
    }
}
