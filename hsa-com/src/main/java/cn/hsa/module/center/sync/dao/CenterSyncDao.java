package cn.hsa.module.center.sync.dao;

import cn.hsa.module.center.sync.dto.CenterSyncDTO;
import cn.hsa.module.center.sync.entity.CenterSyncDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.center.sync.dao
 * @Class_name: CenterHospitalSyncDao
 * @Describe: 医院数据同步表Mapper
 * @Author: Ou·Mr
 * @Email: oubo@powersi.com.cn
 * @Date: 2020/7/30 21:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface CenterSyncDao {

    /**
     * @Menthod insert
     * @Desrciption  新增医院数据同步
     * @param record 新增参数
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return int 受影响的行数
     */
    int insert(CenterSyncDO record);

    /**
     * @Menthod insertSelective
     * @Desrciption  新增医院数据同步
     * @param record 新增参数
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return int 受影响的行数
     */
    int insertSelective(CenterSyncDO record);

    /**
     * @Menthod findByCondition
     * @Desrciption   查询医院数据同步表列表数据
     * @param centerSyncDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/4 22:56 
     * @Return java.util.List<cn.hsa.module.center.sync.dto.CenterSyncDTO>
     */
    List<CenterSyncDTO> findByCondition(CenterSyncDTO centerSyncDTO);

    /**
     * @Menthod deleteById
     * @Desrciption 根据主键删除同步表信息
     * @param id 主键id
     * @Author Ou·Mr
     * @Date 2020/8/4 22:57
     * @Return int 受影响的行数
     */
    int deleteById(@Param("id") String id);

    /**
     * @Menthod deleteByIds
     * @Desrciption 根据主键批量删除同步表信息
     * @param ids ids
     * @Author Ou·Mr
     * @Date 2020/8/4 22:57 
     * @Return int 受影响行数
     */
    int deleteByIds(@Param("ids") String[] ids);

    /**
     * @Menthod updateByPrimaryKeySelective
     * @Desrciption 修改表同步表信息（字段为null不进行修改）
     * @param centerSyncDO 同步表信息
     * @Author Ou·Mr
     * @Date 2020/8/4 22:58 
     * @Return int 受影响行数
     */
    int updateByPrimaryKeySelective(CenterSyncDO centerSyncDO);

    /**
     * @Menthod updateByPrimaryKey
     * @Desrciption 修改同步表信息
     * @param centerSyncDTO 同步表信息
     * @Author Ou·Mr
     * @Date 2020/8/4 23:00 
     * @Return int 受影响行数
     */
    int updateByPrimaryKey(CenterSyncDTO centerSyncDTO);

    /**
     * @Menthod selectByPrimaryKey
     * @Desrciption 根据主键查询同步表信息
     * @param id 主键id
     * @Author Ou·Mr
     * @Date 2020/8/4 23:00 
     * @Return cn.hsa.module.center.sync.entity.CenterSyncDO
     */
    CenterSyncDO selectByPrimaryKey(@Param("id") String id);

    List<CenterSyncDTO> getSyncTableByHosp(CenterSyncDTO centerSyncDTO);

    /**
     * @Menthod queryCenterSyncTableList
     * @Desrciption   查询关联同步表信息集合
     * @param centerSyncDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/9/1 19:48
     * @Return java.util.List<cn.hsa.module.center.sync.dto.CenterSyncDTO>
     */
    List<CenterSyncDTO> queryCenterSyncTableList(CenterSyncDTO centerSyncDTO);

    /**
     * @Menthod editCenterSyncIsValidByids
     * @Desrciption 修改同步表状态
     * @param ids 修改的同步表信息ids
     * @param isValid 是否有效信息
     * @Author Ou·Mr
     * @Date 2020/9/1 16:20 
     * @Return int 受影响的行数
     */
    int editCenterSyncIsValidByids(@Param("ids") String[] ids,@Param("isValid") String isValid);

    /**
     * @Menthod queryBoundHospSync
     * @Desrciption 根据id数组，查询那些id是绑定医院的
     * @param ids 同步表id
     * @param isValid 是否有效
     * @Author Ou·Mr
     * @Date 2020/9/2 15:56
     * @Return java.util.List<java.lang.String> 绑定医院的id集合
     */
    List<CenterSyncDTO> queryBoundHospSync(@Param("ids") String[] ids,@Param("isValid") String isValid);

    /**
     * @备注  根据表名查询数据库表数据
     * @创建者  pengbo
     * @创建时间  2021-01-11
     * @修改者  pengbo
     * @param centerSyncDTO
     * @return  map
     */
    List<Map<String, Object>> getSyncDataByTableName(CenterSyncDTO centerSyncDTO);
    /**
     * @备注  查询所有需要同步的表名
     * @创建者  pengbo
     * @创建时间  2021-01-11
     * @修改者  pengbo
     * @param
     * @return
     */
    List<Map<String,Object>> getAllNeedSyncTableName();


    /**
     * @备注  查询同步表对应的目标表名（例如：sync_item  ->   base_item）
     * @创建者  pengbo
     * @创建时间  2021-01-11
     * @修改者  pengbo
     * @param
     * @return
     */
    List<Map<String, Object>> getCenterTableContrast();



    /**
     * @备注
     * @创建者  pengbo
     * @创建时间  2021-01-12
     * @修改者  pengbo
     * @param
     * @return
     */
    List<CenterSyncDTO> getCenterSyncByTableNameOrCode(CenterSyncDTO centerSyncDTO) ;
}