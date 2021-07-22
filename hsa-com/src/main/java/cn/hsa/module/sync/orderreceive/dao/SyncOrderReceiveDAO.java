package cn.hsa.module.sync.orderreceive.dao;


import cn.hsa.module.sync.orderreceive.dto.SyncOrderReceiveDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.sync.bfc.dao
 * @Class_name: SyncMaterialManagementDAO
 * @Describe: 领药单据类型数据访问层接口
 * @Author: powersi
 * @Date: 2020/09/17 15:58
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */

public interface SyncOrderReceiveDAO {

    /**
     * @Menthod getById
     * @Desrciption 根据主键Id，医院编码等参数查询领药单据类型
     * @param syncOrderReceiveDTO  主键ID和医院编码等参数
     * @Author xingyu.xie
     * @Date   2020/09/17 15:31
     * @Return SyncMaterialDTO
     **/
    SyncOrderReceiveDTO getById(SyncOrderReceiveDTO syncOrderReceiveDTO);

    /**
     * @Menthod queryAll
     * @Desrciption 查询全部领药单据类型
     * @param syncOrderReceiveDTO  医院编码
     * @Author xingyu.xie
     * @Date   2020/09/17 16:05
     * @Return java.util.List<cn.hsa.module.sync.bfc.dto.SyncMaterialDTO>
     **/
    List<SyncOrderReceiveDTO> queryAll(SyncOrderReceiveDTO syncOrderReceiveDTO);

    /**
    * @Menthod insert
    * @Desrciption 新增单条领药单据类型
     * @param syncOrderReceiveDTO  材料分类数据对象
    * @Author xingyu.xie
    * @Date   2020/09/17 16:05
    * @Return int
    **/
    int insert(SyncOrderReceiveDTO syncOrderReceiveDTO);

    /**
    * @Menthod update
    * @Desrciption 修改单条领药单据类型(有非空判断)
     * @param syncOrderReceiveDTO  材料分类数据对象
    * @Author xingyu.xie
    * @Date   2020/09/17 16:05
    * @Return int
    **/
    int updateSyncOrderReceiveS(SyncOrderReceiveDTO syncOrderReceiveDTO);

    /**
     * @Menthod update
     * @Desrciption 修改单条领药单据类型(无非空判断)
     * @param syncOrderReceiveDTO  材料分类数据对象
     * @Author xingyu.xie
     * @Date   2020/09/17 16:05
     * @Return int
     **/
    int updateSyncOrderReceive(SyncOrderReceiveDTO syncOrderReceiveDTO);

    /**
    * @Menthod updateStatus
    * @Desrciption   根据主键ID和医院编码等参数删除领药单据类型
     * @param syncOrderReceiveDTO  领药单据类型表主键id列表，医院编码等参数
    * @Author xingyu.xie
    * @Date   2020/09/17 16:05
    * @Return int
    **/
    int updateStatus(SyncOrderReceiveDTO syncOrderReceiveDTO);

    /**
     * @Method isCodeExist
     * @Desrciption 判断领药单据类型编码是否已经存在
     * @Param
     * [syncOrderReceiveDTO]
     * @Author xingyu.xie
     * @Date   2020/7/24 16:45
     * @Return java.lang.Integer
     **/
    Integer isCodeExist(SyncOrderReceiveDTO syncOrderReceiveDTO);

    /**
     * @Method codeExist
     * @Desrciption 判断领药单据类型编码是否已经存在
     * @Param
     * [syncOrderReceiveDTO]
     * @Author xingyu.xie
     * @Date   2020/7/24 16:45
     * @Return java.lang.Integer
     **/
    Integer codeExist(SyncOrderReceiveDTO syncOrderReceiveDTO);
}
