package cn.hsa.module.center.syncassistdetail.dao;


import cn.hsa.module.center.syncassistdetail.dto.SyncAssistDetailDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ljh
 * @date 2020/07/14.
 */
public interface SyncAssistDetailDAO {

    SyncAssistDetailDTO getById(Long id);

    List<SyncAssistDetailDTO> queryAll(SyncAssistDetailDTO baseDailyfirstCalcDTO);


    int insert(SyncAssistDetailDTO baseDailyfirstCalcDTO);


    int update(SyncAssistDetailDTO baseDailyfirstCalcDTO);


    int deleteById(Long id);
    int  deleteBylist(List<SyncAssistDetailDTO> list);

    int updateBatch(@Param("list") List<SyncAssistDetailDTO> list);


    int insertList(List<SyncAssistDetailDTO> list);
}
