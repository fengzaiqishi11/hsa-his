package cn.hsa.module.center.sync.sevice;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.sync.dto.CenterHospitalSyncDTO;
import cn.hsa.module.center.sync.dto.CenterSyncDTO;
import cn.hsa.module.center.sync.entity.CenterHospitalSyncDO;
import cn.hsa.module.center.sync.entity.CenterSyncDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @Package_name: cn.hsa.module.center.sync.sevice
 * @Class_name: CenterSyncService
 * @Describe(描述): 医院数据同步表 service 接口
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/08/05 9:16
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-center")
public interface CenterSyncService {

    /**
     * @Menthod insert
     * @Desrciption  新增医院数据同步
     * @param record 新增参数
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return WrapperResponse 成功/失败
     */
    @PostMapping(value = "/service/center/sync/insert")
    WrapperResponse insert(CenterSyncDTO record);


    /**
     * @Menthod findByCondition
     * @Desrciption   查询医院数据同步表列表数据
     * @param centerSyncDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/4 22:56
     * @Return WrapperResponse 结果集
     */
    @GetMapping(value = "/service/center/sync/queryCenterSyncTablePage")
    WrapperResponse<PageDTO> queryCenterSyncTablePage(CenterSyncDTO centerSyncDTO);

    /**
     * @Menthod deleteById
     * @Desrciption 根据主键删除同步表信息
     * @param id 主键id
     * @Author Ou·Mr
     * @Date 2020/8/4 22:57
     * @Return WrapperResponse 成功/失败
     */
    @DeleteMapping(value = "/service/center/sync/deleteById")
    WrapperResponse deleteById(String id);

    /**
     * @Menthod deleteByIds
     * @Desrciption 根据主键批量删除同步表信息
     * @param ids ids
     * @Author Ou·Mr
     * @Date 2020/8/4 22:57
     * @Return WrapperResponse 成功/失败
     */
    @DeleteMapping(value = "/service/center/sync/deleteByIds")
    WrapperResponse deleteByIds(String ids);

    /**
     * @Menthod updateByPrimaryKey
     * @Desrciption 修改同步表信息
     * @param centerSyncDTO 同步表信息
     * @Author Ou·Mr
     * @Date 2020/8/4 23:00
     * @Return WrapperResponse 成功/失败
     */
    @PutMapping(value = "/service/center/sync/updateByPrimaryKey")
    WrapperResponse updateByPrimaryKey(CenterSyncDTO centerSyncDTO);

    //水水
    @PostMapping(value = "/service/center/sync/syncHospData")
    WrapperResponse saveSyncHospData(CenterSyncDTO centerSyncDTO);

    /**
     * @Menthod queryCenterSyncList
     * @Desrciption 获取关联同步表信息
     * @param centerSyncDTO 请求参数
     * @Author Ou·Mr
     * @Date 2020/9/1 11:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping(value = "/service/center/sync/queryCenterSyncList")
    WrapperResponse queryCenterSyncTableList(CenterSyncDTO centerSyncDTO);

    /**
     * @Menthod queryCenterHospitalSyncList
     * @Desrciption  查询医院所同步的表
     * @param centerHospitalSyncDO 请求参数
     * @Author Ou·Mr
     * @Date 2020/9/1 20:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping(value = "/service/center/sync/queryCenterHospitalSyncList")
    WrapperResponse queryCenterHospitalSyncList(CenterHospitalSyncDO centerHospitalSyncDO);

    /**
     * @Menthod saveHospitalSync
     * @Desrciption  保存医院同步表信息
     * @param centerHospitalSyncDTO 请求参数
     * @Author Ou·Mr
     * @Date 2020/9/2 8:46 
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @PostMapping(value = "/service/center/sync/saveHospitalSync")
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
