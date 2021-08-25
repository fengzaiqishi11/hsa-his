package cn.hsa.module.sync.orderreceive.bo;



import cn.hsa.base.PageDTO;
import cn.hsa.module.sync.orderreceive.dto.SyncOrderReceiveDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.sync.bfc.bo
 * @Class_name: SyncMaterialManagementBO
 * @Describe: 领药单据类型业务逻辑实现层接口
 * @Author: xingyu.xie
 * @Date: 2020/09/17 16:00
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface SyncOrderReceiveBO {

    /**
    * @Menthod getById
     * @Desrciption 根据主键Id，医院编码等参数查询领药单据类型
     * @param syncOrderReceiveDTO  主键ID 和医院编码等参数
    * @Author xingyu.xie
    * @Date   2020/09/17 16:05 
    * @Return cn.hsa.module.sync.bfc.dto.SyncMaterialDTO
    **/
    SyncOrderReceiveDTO getById(SyncOrderReceiveDTO syncOrderReceiveDTO);

    /**
     * @Menthod queryAll
     * @Desrciption   查询所有领药单据类型
     * @param syncOrderReceiveDTO  医院编码
     * @Author xingyu.xie
     * @Date   2020/7/14 16:04
     * @Return java.util.List<cn.hsa.module.sync.bmm.dto.SyncMaterialDTO>
     **/
    List<SyncOrderReceiveDTO> queryAll(SyncOrderReceiveDTO syncOrderReceiveDTO);

    /**
    * @Menthod queryAll
    * @Desrciption   分页查询领药单据类型
     * @param syncOrderReceiveDTO  生产企业分类数据对象
    * @Author xingyu.xie
    * @Date   2020/09/17 16:04 
    * @Return java.util.List<cn.hsa.module.sync.bfc.dto.SyncMaterialDTO>
    **/
    PageDTO queryPage(SyncOrderReceiveDTO syncOrderReceiveDTO);

    /**
     * @Menthod save
     * @Desrciption  修改或新增领药单据类型
     * @param syncOrderReceiveDTO 生产企业数据参数对象
     * @Author xingyu.xie
     * @Date   2020/7/25 11:32
     * @Return boolean
     **/
    boolean save(SyncOrderReceiveDTO syncOrderReceiveDTO);

    /**
     * @Menthod updateSyncMrisClassifyS
     * @Desrciption  修改领药单据类型
     * @param syncOrderReceiveDTO 生产企业数据参数对象
     * @Author xingyu.xie
     * @Date   2020/09/17 11:32
     * @Return boolean
     **/
    boolean updateSyncOrderReceiveS(SyncOrderReceiveDTO syncOrderReceiveDTO);

    /**
    * @Menthod updateStatus
     * @Desrciption   根据主键ID和医院编码等参数删除领药单据类型
     * @param syncOrderReceiveDTO  领药单据类型表主键id列表，医院编码等参数
    * @Author xingyu.xie
    * @Date   2020/09/17 16:05 
    * @Return int
    **/
    boolean updateStatus(SyncOrderReceiveDTO syncOrderReceiveDTO);
}
