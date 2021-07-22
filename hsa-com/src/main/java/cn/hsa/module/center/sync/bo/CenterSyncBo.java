package cn.hsa.module.center.sync.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.sync.dto.CenterHospitalSyncDTO;
import cn.hsa.module.center.sync.dto.CenterSyncDTO;
import cn.hsa.module.center.sync.entity.CenterHospitalSyncDO;
import cn.hsa.module.center.sync.entity.CenterSyncDO;
import org.apache.ibatis.annotations.Param;

/**
 * @Package_name: cn.hsa.module.center.sync.bo
 * @Class_name: CenterSyncBo
 * @Describe(描述): 医院数据同步表BO接口
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/08/05 8:38
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface CenterSyncBo {

    /**
     * @Menthod insert
     * @Desrciption  新增医院数据同步
     * @param record 新增参数
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return WrapperResponse 成功/失败
     */
    WrapperResponse insert(CenterSyncDTO record);

    /**
     * @Menthod insertSelective
     * @Desrciption  新增医院数据同步
     * @param record 新增参数
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return WrapperResponse 成功/失败
     */
    WrapperResponse insertSelective(CenterSyncDO record);

    /**
     * @Menthod findByCondition
     * @Desrciption   查询医院数据同步表列表数据
     * @param centerSyncDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/4 22:56
     * @Return WrapperResponse 结果集
     */
    WrapperResponse<PageDTO> queryCenterSyncTablePage(CenterSyncDTO centerSyncDTO);

    /**
     * @Menthod deleteById
     * @Desrciption 根据主键删除同步表信息
     * @param id 主键id
     * @Author Ou·Mr
     * @Date 2020/8/4 22:57
     * @Return WrapperResponse 成功/失败
     */
    WrapperResponse deleteById(String id);

    /**
     * @Menthod deleteByIds
     * @Desrciption 根据主键批量删除同步表信息
     * @param ids ids
     * @Author Ou·Mr
     * @Date 2020/8/4 22:57
     * @Return WrapperResponse 成功/失败
     */
    WrapperResponse deleteByIds(String ids);

    /**
     * @Menthod updateByPrimaryKeySelective
     * @Desrciption 修改表同步表信息（字段为null不进行修改）
     * @param centerSyncDTO 同步表信息
     * @Author Ou·Mr
     * @Date 2020/8/4 22:58
     * @Return WrapperResponse 成功/失败
     */
    WrapperResponse updateByPrimaryKeySelective(CenterSyncDTO centerSyncDTO);

    /**
     * @Menthod updateByPrimaryKey
     * @Desrciption 修改同步表信息
     * @param centerSyncDTO 同步表信息
     * @Author Ou·Mr
     * @Date 2020/8/4 23:00
     * @Return WrapperResponse 成功/失败
     */
    WrapperResponse updateByPrimaryKey(CenterSyncDTO centerSyncDTO);

    /**
     * @Menthod selectByPrimaryKey
     * @Desrciption 根据主键查询同步表信息
     * @param id 主键id
     * @Author Ou·Mr
     * @Date 2020/8/4 23:00
     * @Return WrapperResponse 结果集
     */
    WrapperResponse selectByPrimaryKey(@Param("id") String id);

    WrapperResponse saveSyncHospData(CenterSyncDTO centerSyncDTO);

    /**
     * @Menthod queryCenterSyncList
     * @Desrciption 获取关联同步表信息
     * @param centerSyncDTO 请求参数
     * @Author Ou·Mr
     * @Date 2020/9/1 11:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    WrapperResponse queryCenterSyncTableList(CenterSyncDTO centerSyncDTO);

    /**
     * @Menthod queryCenterHospitalSyncList
     * @Desrciption  查询医院所同步的表
     * @param centerHospitalSyncDO 请求参数
     * @Author Ou·Mr
     * @Date 2020/9/1 20:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    WrapperResponse queryCenterHospitalSyncList(CenterHospitalSyncDO centerHospitalSyncDO);

    /**
     * @Menthod saveHospitalSync
     * @Desrciption  保存医院同步表信息
     * @param centerHospitalSyncDTO 请求参数
     * @Author Ou·Mr
     * @Date 2020/9/2 8:46
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    WrapperResponse saveHospitalSync(CenterHospitalSyncDTO centerHospitalSyncDTO);
    /**
     * @备注  查询所有需要同步的表名
     * @创建者  pengbo
     * @创建时间  2021-01-11
     * @修改者  pengbo
     * @param
     * @return
     */
    WrapperResponse getAllNeedSyncTableName();
}
