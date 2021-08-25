package cn.hsa.module.center.syncassist.dao;

import cn.hsa.module.center.syncassist.dto.SyncassistDTO;

import java.util.List;

/**
 * @Package_ame: cn.hsa.base.bfc.dao
 * @Class_name: BaseBedDao
 * @Description: 辅助计费数据访问层接口
 * @Author: ljh
 * @Email: 307753910@qq.com
 * @Date: 2020/7/1 20:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/

public interface SyncassistDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SyncassistDTO queryById(Long id);



    /**
     * 通过实体作为筛选条件查询
     *
     * @param baseAssistCalcDTO 实例对象
     * @return 对象列表
     */
    List<SyncassistDTO> queryAll(SyncassistDTO baseAssistCalcDTO);

    /**
     * 新增数据
     *
     * @param baseAssistCalcDTO 实例对象
     * @return 影响行数
     */
    int insert(SyncassistDTO baseAssistCalcDTO);
    /**
     * 新增数据
     *
     * @param baseAssistCalcDTO 实例对象
     * @return 影响行数
     */
    int queryNameExist(SyncassistDTO baseAssistCalcDTO);

    /**
     * 修改数据
     *
     * @param baseAssistCalcDTO 实例对象
     * @return 影响行数
     */
    int update(SyncassistDTO baseAssistCalcDTO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    int updateStatus(List<SyncassistDTO> list);

}
