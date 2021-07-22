package cn.hsa.module.center.syncassist.dao;

import cn.hsa.module.center.syncassist.dto.SyncassistDetailDTO;

import java.util.List;
import java.util.Map;

/**
 * @author ljh
 * @date 2020/07/09.
 */
public interface SyncassistDetailDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SyncassistDetailDTO queryById(Long id);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param BaseAssistCalcDetailDTO 实例对象
     * @return 对象列表
     */
    List<SyncassistDetailDTO> queryAll(SyncassistDetailDTO BaseAssistCalcDetailDTO);

    /**
     * 新增数据
     *
     * @param BaseAssistCalcDetailDTO 实例对象
     * @return 影响行数
     */
    int insert(SyncassistDetailDTO BaseAssistCalcDetailDTO);

    /**
     * 修改数据
     *
     * @param BaseAssistCalcDetailDTO 实例对象
     * @return 影响行数
     */
    int update(SyncassistDetailDTO BaseAssistCalcDetailDTO);

    /**
     * 通过主键删除数据
     *
     * @param code 主键
     * @return 影响行数
     */
    int deleteBycode(String code);


    List<Map<String,Object>> queryallcode(SyncassistDetailDTO BaseAssistCalcDetailDTO);

    int insertList(List<SyncassistDetailDTO> list);
}
