package cn.hsa.module.outpt.medictocare.dao;

import cn.hsa.module.outpt.medictocare.entity.OutptMedicalCareConfigurationDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;
/**
 * @author powersi
 * @create 2022-03-07 9:38
 * @desc
 **/
public interface OutptMedicalCareConfigurationDAO{
    /**
     * 通过ID查询单条数据
     *
     * @param
     * @return 实例对象
     */
    OutptMedicalCareConfigurationDO queryById(OutptMedicalCareConfigurationDO outptMedicalCareConfiguration);

    /**
     * 查询指定行数据
     *
     * @param outptMedicalCareConfiguration 查询条件
     * @param
     * @return 对象列表
     */
    List<OutptMedicalCareConfigurationDO> queryAllByLimit(OutptMedicalCareConfigurationDO outptMedicalCareConfiguration);

    /**
     * 统计总行数
     *
     * @param outptMedicalCareConfiguration 查询条件
     * @return 总行数
     */
    long count(OutptMedicalCareConfigurationDO outptMedicalCareConfiguration);

    /**
     * 新增数据
     *
     * @param outptMedicalCareConfiguration 实例对象
     * @return 影响行数
     */
    int insertConfiguration(OutptMedicalCareConfigurationDO outptMedicalCareConfiguration);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<OutptMedicalCareConfiguration> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<OutptMedicalCareConfigurationDO> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<OutptMedicalCareConfiguration> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<OutptMedicalCareConfigurationDO> entities);

    /**
     * 修改数据
     *
     * @param outptMedicalCareConfiguration 实例对象
     * @return 影响行数
     */
    int updateConfiguration(OutptMedicalCareConfigurationDO outptMedicalCareConfiguration);

    /**
     * 通过主键删除数据
     *
     * @param
     * @return 影响行数
     */
    int deleteById(OutptMedicalCareConfigurationDO outptMedicalCareConfiguration);

}
