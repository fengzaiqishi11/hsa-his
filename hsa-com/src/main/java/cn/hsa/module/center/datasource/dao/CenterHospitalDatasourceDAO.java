package cn.hsa.module.center.datasource.dao;

import cn.hsa.module.center.datasource.dto.CenterHospitalDatasourceDTO;
import cn.hsa.module.center.datasource.entity.CenterHospitalDatasourceDO;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.center.datasource.dao
 * @Class_name: CenterHospitalDatasourceDAO
 * @Describe: 医院数据源关系Mapper
 * @Author: Ou·Mr
 * @Email: oubo@powersi.com.cn
 * @Date: 2020/7/30 21:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface CenterHospitalDatasourceDAO {


    /**
     * @Menthod selectByPrimaryKey
     * @Desrciption  根据主键查询医医院数据源关系
     * @param id 主键id
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return CenterDatasourceDO 查询信息
     */
    CenterHospitalDatasourceDO selectByPrimaryKey(String id);

    /**
     * @Menthod insert
     * @Desrciption  新增医院数据源关系
     * @param record 新增参数
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return int 受影响行数
     */
    int insert(CenterHospitalDatasourceDO record);

    /**
     * @Menthod insertSelective
     * @Desrciption  新增医院数据源关系（null不增加）
     * @param record 新增参数
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return int 受影响行数
     */
    int insertSelective(CenterHospitalDatasourceDO record);

    /**
     * @Menthod updateByPrimaryKey
     * @Desrciption  修改医院数据源关系
     * @param record 修改参数
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return int 受影响的行数
     */
    int updateByPrimaryKey(CenterHospitalDatasourceDO record);

    /**
     * @Menthod updateByPrimaryKeySelective
     * @Desrciption  修改医院数据源关系(参数字段为null或者''不进行修改)
     * @param record 修改参数
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return int 受影响的行数
     */
    int updateByPrimaryKeySelective(CenterHospitalDatasourceDO record);

    /**
     * @Menthod deleteById
     * @Desrciption  根据主键删除医院数据源关系
     * @param id 主键
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return int 受影响的行数
     */
    int deleteById(String id);

    /**
     * @Menthod deleteByIds
     * @Desrciption  根据主键批量删除医院数据源关系
     * @param ids 主键
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return int 受影响的行数
     */
    int deleteByIds(@Param("ids") String[] ids);

    /**
     * @Menthod findByCondition
     * @Desrciption  查询医院数据源关系
     * @param centerHospitalDatasourceDTO 查询条件
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return List<CenterHospitalDatasourceDTO> 结果集
     */
    List<CenterHospitalDatasourceDTO> findByCondition(CenterHospitalDatasourceDTO centerHospitalDatasourceDTO);


    /**
     * @Menthod queryCenterHospitalPage
     * @Desrciption  查询医院信息（可查询全部、可分页）
     * @param centerHospitalDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/5 19:14 
     * @Return java.util.List<cn.hsa.module.center.hospital.dto.CenterHospitalDTO> 结果集
     */
    List<CenterHospitalDTO> queryCenterHospitalAll(CenterHospitalDTO centerHospitalDTO);

    /**
     * @Menthod batchInsert
     * @Desrciption 批量增加医院和数据源的关系信息
     * @param centerHospitalDatasourceDOS 数据源关系信息
     * @Author Ou·Mr
     * @Date 2020/8/5 20:07
     * @Return int 受影响行数
     */
    int batchInsert(List<CenterHospitalDatasourceDO> centerHospitalDatasourceDOS);

    /**
     * @Menthod delByDsCode
     * @Desrciption 根据数据源编码删除数据源和医院关系信息
     * @param dsCode 数据源编码
     * @Author Ou·Mr
     * @Date 2020/8/5 20:09 
     * @Return int 受影响行数
     */
    int delByDsCode(@Param("dsCode") String dsCode);

    /**
     * @Method queryHaveHospCode
     * @Desrciption 查询是否有重复绑定的医院
       @params [list]
     * @Author chenjun
     * @Date   2020-11-25 09:41
     * @Return java.util.List<cn.hsa.module.center.datasource.dto.CenterHospitalDatasourceDTO>
    **/
    List<CenterHospitalDatasourceDTO> queryHaveHospCode(Map map);

    /**
     * @Menthod deleteCenterHospital
     * @Desrciption   删除数据源绑定的信息
     * @param ids ID集合
     * @Author pengbo
     * @Date 2021 01 06 19:27
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 结果集
     */
    int deleteCenterHospital(@Param(value="ids") String[] ids);

    /**
     *  根据医院编码查询医院信息
     * @param centerHospitalDTO
     * @return
     */
    CenterHospitalDTO getHospServiceStatsByCode(CenterHospitalDTO centerHospitalDTO);
}