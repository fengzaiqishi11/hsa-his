package cn.hsa.center.sync.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.sync.bo.CenterSyncBo;
import cn.hsa.module.center.sync.dto.CenterHospitalSyncDTO;
import cn.hsa.module.center.sync.dto.CenterSyncDTO;
import cn.hsa.module.center.sync.entity.CenterHospitalSyncDO;
import cn.hsa.module.center.sync.entity.CenterSyncDO;
import cn.hsa.module.center.sync.sevice.CenterSyncService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Package_name: cn.hsa.center.sync.service.impl
 * @Class_name: CenterSyncServiceImpl
 * @Describe(描述): 医院数据同步表ServiceImpl
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/08/05 9:41
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/center/sync")
@Service("centerSyncService_provider")
public class CenterSyncServiceImpl extends HsafService implements CenterSyncService {

    @Resource
    private CenterSyncBo centerSyncBo;

    /**
     * @Menthod insert
     * @Desrciption  新增医院数据同步
     * @param record 新增参数
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return WrapperResponse 成功/失败
     */
    @Override
    public WrapperResponse insert(CenterSyncDTO record) {
        return centerSyncBo.insert(record);
    }

    /**
     * @Menthod findByCondition
     * @Desrciption   查询医院数据同步表列表数据
     * @param centerSyncDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/4 22:56
     * @Return WrapperResponse 结果集
     */
    @Override
    public WrapperResponse<PageDTO> queryCenterSyncTablePage(CenterSyncDTO centerSyncDTO) {
        return centerSyncBo.queryCenterSyncTablePage(centerSyncDTO);
    }

    /**
     * @Menthod deleteById
     * @Desrciption 根据主键删除同步表信息
     * @param id 主键id
     * @Author Ou·Mr
     * @Date 2020/8/4 22:57
     * @Return WrapperResponse 成功/失败
     */
    @Override
    public WrapperResponse deleteById(String id) {
        return centerSyncBo.deleteById(id);
    }

    /**
     * @Menthod deleteByIds
     * @Desrciption 根据主键批量删除同步表信息
     * @param ids ids
     * @Author Ou·Mr
     * @Date 2020/8/4 22:57
     * @Return WrapperResponse 成功/失败
     */
    @Override
    public WrapperResponse deleteByIds(String ids) {
        return centerSyncBo.deleteByIds(ids);
    }

    /**
     * @Menthod updateByPrimaryKey
     * @Desrciption 修改同步表信息
     * @param centerSyncDTO 同步表信息
     * @Author Ou·Mr
     * @Date 2020/8/4 23:00
     * @Return WrapperResponse 成功/失败
     */
    @Override
    public WrapperResponse updateByPrimaryKey(CenterSyncDTO centerSyncDTO) {
        return centerSyncBo.updateByPrimaryKeySelective(centerSyncDTO);
    }

    @Override
    public WrapperResponse saveSyncHospData(CenterSyncDTO centerSyncDTO) {
        return centerSyncBo.saveSyncHospData(centerSyncDTO);
    }

    /**
     * @Menthod queryCenterSyncList
     * @Desrciption 获取关联同步表信息
     * @param centerSyncDTO 请求参数
     * @Author Ou·Mr
     * @Date 2020/9/1 11:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse queryCenterSyncTableList(CenterSyncDTO centerSyncDTO) {
        return centerSyncBo.queryCenterSyncTableList(centerSyncDTO);
    }

    /**
     * @Menthod queryCenterHospitalSyncList
     * @Desrciption  查询医院所同步的表
     * @param centerHospitalSyncDO 请求参数
     * @Author Ou·Mr
     * @Date 2020/9/1 20:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse queryCenterHospitalSyncList(CenterHospitalSyncDO centerHospitalSyncDO) {
        return centerSyncBo.queryCenterHospitalSyncList(centerHospitalSyncDO);
    }

    /**
     * @Menthod saveHospitalSync
     * @Desrciption  保存医院同步表信息
     * @param centerHospitalSyncDTO 请求参数
     * @Author Ou·Mr
     * @Date 2020/9/2 8:46
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse saveHospitalSync(CenterHospitalSyncDTO centerHospitalSyncDTO) {
        return centerSyncBo.saveHospitalSync(centerHospitalSyncDTO);
    }

    /**
     * @return
     * @备注 查询所有需要同步的表名
     * @创建者 pengbo
     * @创建时间 2021-01-11
     * @修改者 pengbo
     */
    @Override
    public WrapperResponse getAllNeedSyncTableName() {
        return centerSyncBo.getAllNeedSyncTableName();
    }
}
