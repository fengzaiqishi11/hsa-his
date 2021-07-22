package cn.hsa.module.sync.syncsystem.dao;

import cn.hsa.module.sync.syncsystem.dto.SyncSystemDTO;

import java.util.List;

public interface SyncSystemDAO {
    List<SyncSystemDTO> queryAll(SyncSystemDTO syncSystemDTO);

    List<SyncSystemDTO> queryPage(SyncSystemDTO syncSystemDTO);

    int getCount(SyncSystemDTO syncSystemDTO);

    int insert(SyncSystemDTO syncSystemDTO);

    int updateSyncSystemByIsvalid(SyncSystemDTO syncSystemDTO);

    int update(SyncSystemDTO syncSystemDTO);

    SyncSystemDTO getById(SyncSystemDTO syncSystemDTO);

    Integer querySystemSeqNo(SyncSystemDTO syncSystemDTO);

    int getCountByName(SyncSystemDTO syncSystemDTO);
}
