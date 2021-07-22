package cn.hsa.module.sync.syncsystem.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.sync.syncsystem.dto.SyncSystemDTO;

import java.util.List;

public interface SyncSystemBO {
    List<SyncSystemDTO> queryAll(SyncSystemDTO syncSystemDTO);

    PageDTO queryPage(SyncSystemDTO syncSystemDTO);

    Boolean save(SyncSystemDTO syncSystemDTO);

    SyncSystemDTO getById(SyncSystemDTO syncSystemDTO);

    Integer querySystemSeqNo(SyncSystemDTO syncSystemDTO);
}
