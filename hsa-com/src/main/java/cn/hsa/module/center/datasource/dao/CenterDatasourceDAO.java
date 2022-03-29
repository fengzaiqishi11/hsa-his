package cn.hsa.module.center.datasource.dao;

import cn.hsa.module.center.datasource.dto.CenterDatasourceDTO;
import cn.hsa.module.center.datasource.entity.CenterDatasourceDO;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import cn.hsa.module.center.sync.dto.CenterSyncDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.center.datasource.dao
 * @Class_name: CenterDatasourceDAO
 * @Describe: 医院数据源Mapper
 * @Author: Ou·Mr
 * @Email: oubo@powersi.com.cn
 * @Date: 2020/7/30 21:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface CenterDatasourceDAO {

    /**
     * @Menthod selectByPrimaryKey
     * @Desrciption  根据主键查询医院数据源信息
     * @param id 主键id
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return CenterDatasourceDO 查询信息
     */
    CenterDatasourceDO selectByPrimaryKey(String id);

    /**
     * @Menthod insert
     * @Desrciption  新增医院数据源
     * @param record 新增参数
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return int 受影响行数
     */
    int insert(CenterDatasourceDO record);

    /**
     * @Menthod insertSelective
     * @Desrciption  新增医院数据源（null不增加）
     * @param record 新增参数
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return int 受影响行数
     */
    int insertSelective(CenterDatasourceDO record);

    /**
     * @Menthod insertSelective
     * @Desrciption  查询医院数据源集合
     * @param
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return List<CenterDatasourceDTO> 结果集合
     */
    List<CenterDatasourceDTO> queryCenterHospitalDatasourceAll();

    /**
     * @Menthod updateByPrimaryKey
     * @Desrciption  修改医院数据源集合
     * @param record 修改参数
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return int 受影响的行数
     */
    int updateByPrimaryKey(CenterDatasourceDO record);

    /**
     * @Menthod updateByPrimaryKeySelective
     * @Desrciption  修改医院数据源集合(参数字段为null或者''不进行修改)
     * @param record 修改参数
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return int 受影响的行数
     */
    int updateByPrimaryKeySelective(CenterDatasourceDO record);

    /**
     * @Menthod deleteById
     * @Desrciption  根据主键删除医院数据源信息
     * @param id 主键
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return int 受影响的行数
     */
    int deleteById(String id);

    /**
     * @Menthod deleteByIds
     * @Desrciption  根据主键批量删除医院数据源信息
     * @param ids 主键
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return int 受影响的行数
     */
    int deleteByIds(@Param("ids") String[] ids);

    /**
     * @Menthod findByCondition
     * @Desrciption  查询医院数据源列表信息
     * @param centerDatasourceDTO 查询条件
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return List<CenterDatasourceDTO> 结果集
     */
    List<CenterDatasourceDTO> findByCondition(CenterDatasourceDTO centerDatasourceDTO);

    List<CenterDatasourceDTO> getDataSourceByHosp(CenterSyncDTO centerSyncDTO);
    /**
     * @param dataSourceIds
     * @Menthod findCenterDataSourceByIds
     * @Desrciption 根据所选数据源列表 对数据进行字段同步
     * @Author pengbo
     * @Date 2021 01 06 19:27
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 结果集
     */
    List<CenterDatasourceDTO> findCenterDataSourceByIds(@Param("ids") List<String> dataSourceIds);


    /**
     * @param dataSourceIds
     * @Menthod findCenterDataSourceByIds
     * @Desrciption 根据所选数据源,医院信息列表 对数据进行字段同步
     * @Author pengbo
     * @Date 2021 01 06 19:27
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 结果集
     */
    List<CenterDatasourceDTO> findHospitalCenterDataSourceByIds(@Param("ids") List<String> dataSourceIds);

    /**
     * @param hospCode
     * @Menthod findCenterDataSourceByIds
     * @Desrciption 根据所选数据源,医院信息列表 对数据进行字段同步
     * @Author pengbo
     * @Date 2021 01 06 19:27
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 结果集
     */
    List<CenterDatasourceDTO> getCenterHospitalDatasource(String hospCode);

    /**
     * @Description: 查询所有的数据库信息
     * @Param: []
     * @return: java.util.List<cn.hsa.module.center.datasource.dto.CenterDatasourceDTO>
     * @Author: zhangxuan
     * @Date: 2022-02-17
     */
    List<CenterDatasourceDTO> findHospitalCenterDataSource();

    void addDataUser(CenterHospitalDTO centerHospitalDTO);
}
