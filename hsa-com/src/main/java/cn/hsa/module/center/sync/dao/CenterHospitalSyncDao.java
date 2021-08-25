package cn.hsa.module.center.sync.dao;

import cn.hsa.module.center.sync.entity.CenterHospitalSyncDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.center.sync.dao
 * @Class_name: CenterHospitalSyncDao
 * @Describe: 医院数据同步关系Mapper
 * @Author: Ou·Mr
 * @Email: oubo@powersi.com.cn
 * @Date: 2020/7/30 21:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface CenterHospitalSyncDao {

    /**
     * @Menthod insert
     * @Desrciption  新增医院数据同步关系
     * @param record 新增参数
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return int 受影响的行数
     */
    int insert(CenterHospitalSyncDO record);

    /**
     * @Menthod insertSelective
     * @Desrciption  新增医院数据同步关系（null不增加）
     * @param record 新增参数
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return int 受影响的行数
     */
    int insertSelective(CenterHospitalSyncDO record);

    /**
     * @Menthod queryHospitalSyncList
     * @Desrciption  获取医院同步表信息
     * @param centerHospitalSyncDO 查询条件
     * @Author Ou·Mr
     * @Date 2020/9/1 20:29 
     * @Return java.util.List<cn.hsa.module.center.sync.entity.CenterHospitalSyncDO>
     */
    List<CenterHospitalSyncDO> queryHospitalSyncList(CenterHospitalSyncDO centerHospitalSyncDO);

    /**
     * @Menthod delHospitalSyncByHospCode
     * @Desrciption 根据医院编码批量删除同步表信息
     * @param ids 医院id
     * @Author Ou·Mr
     * @Date 2020/9/1 20:30 
     * @Return int 受影响的行数
     */
    int delHospitalSyncByHospCode(@Param("ids") String[] ids);

    /**
     * @Menthod batchInsert
     * @Desrciption  批量保存医院同步表信息
     * @param centerHospitalSyncDOList 同步表信息集合
     * @Author Ou·Mr
     * @Date 2020/9/1 20:33 
     * @Return int 受影响的行数
     */
    int batchInsert(List<CenterHospitalSyncDO> centerHospitalSyncDOList);

}