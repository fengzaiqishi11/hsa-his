package cn.hsa.module.sync.mrisclassify.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.sync.mrisclassify.dto.SyncMrisClassifyDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.sync.bfc.bo
 * @Class_name: SyncMaterialManagementBO
 * @Describe: 病案费用归类业务逻辑实现层接口
 * @Author: xingyu.xie
 * @Date: 2020/09/17 16:00
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface SyncMrisClassifyBO {

    /**
    * @Menthod getById
     * @Desrciption 根据主键Id，医院编码等参数查询病案费用归类
     * @param syncMrisClassifyDTO  主键ID 和医院编码等参数
    * @Author xingyu.xie
    * @Date   2020/09/17 16:05 
    * @Return cn.hsa.module.sync.bfc.dto.SyncMaterialDTO
    **/
    SyncMrisClassifyDTO getById(SyncMrisClassifyDTO syncMrisClassifyDTO);

    /**
     * @Menthod queryAll
     * @Desrciption   查询所有病案费用归类
     * @param syncMrisClassifyDTO  医院编码
     * @Author xingyu.xie
     * @Date   2020/7/14 16:04
     * @Return java.util.List<cn.hsa.module.sync.bmm.dto.SyncMaterialDTO>
     **/
    List<SyncMrisClassifyDTO> queryAll(SyncMrisClassifyDTO syncMrisClassifyDTO);

    /**
    * @Menthod queryAll
    * @Desrciption   分页查询病案费用归类
     * @param syncMrisClassifyDTO  生产企业分类数据对象
    * @Author xingyu.xie
    * @Date   2020/09/17 16:04 
    * @Return java.util.List<cn.hsa.module.sync.bfc.dto.SyncMaterialDTO>
    **/
    PageDTO queryPage(SyncMrisClassifyDTO syncMrisClassifyDTO);

    /**
     * @Menthod save
     * @Desrciption  修改或新增病案费用归类
     * @param syncMrisClassifyDTO 生产企业数据参数对象
     * @Author xingyu.xie
     * @Date   2020/7/25 11:32
     * @Return boolean
     **/
    boolean save(SyncMrisClassifyDTO syncMrisClassifyDTO);

    /**
     * @Menthod updateSyncMrisClassifyS
     * @Desrciption  修改病案费用归类
     * @param syncMrisClassifyDTO 生产企业数据参数对象
     * @Author xingyu.xie
     * @Date   2020/09/17 11:32
     * @Return boolean
     **/
    boolean updateSyncMrisClassifyS(SyncMrisClassifyDTO syncMrisClassifyDTO);

    /**
    * @Menthod updateStatus
     * @Desrciption   根据主键ID和医院编码等参数删除病案费用归类
     * @param syncMrisClassifyDTO  病案费用归类表主键id列表，医院编码等参数
    * @Author xingyu.xie
    * @Date   2020/09/17 16:05 
    * @Return int
    **/
    boolean updateStatus(SyncMrisClassifyDTO syncMrisClassifyDTO);
}
