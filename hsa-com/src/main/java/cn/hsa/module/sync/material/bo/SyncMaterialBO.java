package cn.hsa.module.sync.material.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.sync.material.dto.SyncMaterialDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.base.bfc.bo
 * @Class_name: BaseMaterialManagementBO
 * @Describe: 材料信息业务逻辑实现层接口
 * @Author: powersi
 * @Date: 2020/7/7 16:00
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface SyncMaterialBO {

    /**
    * @Menthod getById
     * @Desrciption 根据主键Id，医院编码等参数查询材料信息
     * @param syncMaterialDTO  主键ID 和医院编码等参数
    * @Author xingyu.xie
    * @Date   2020/7/7 16:05 
    * @Return cn.hsa.module.base.bfc.dto.SyncMaterialDTO
    **/
    SyncMaterialDTO getById(SyncMaterialDTO syncMaterialDTO);

    /**
     * @Menthod queryAll
     * @Desrciption   查询所有材料信息
     * @Author xingyu.xie
     * @Date   2020/7/14 16:04
     * @Return java.util.List<cn.hsa.module.base.bmm.dto.SyncMaterialDTO>
     **/
    List<SyncMaterialDTO> queryAll();

    /**
    * @Menthod queryAll
    * @Desrciption   分页查询材料信息
     * @param syncMaterialDTO  材料分类数据对象
    * @Author xingyu.xie
    * @Date   2020/7/7 16:04 
    * @Return java.util.List<cn.hsa.module.base.bfc.dto.SyncMaterialDTO>
    **/
    PageDTO queryPage(SyncMaterialDTO syncMaterialDTO);

    /**
     * @Menthod save
     * @Desrciption  修改或新增材料分类信息
     * @param syncMaterialDTO 材料分类数据参数对象
     * @Author xingyu.xie
     * @Date   2020/7/25 11:32
     * @Return boolean
     **/
    boolean save(SyncMaterialDTO syncMaterialDTO);

    /**
     * @Menthod updateList
     * @Desrciption 修改多条材料
     * @param syncMaterialDTOList 材料分类数据参数对象
     * @Author xingyu.xie
     * @Date   2020/8/24 15:32
     * @Return boolean
     **/
    boolean updateList(List<SyncMaterialDTO> syncMaterialDTOList);

    /**
    * @Menthod updateStatus
     * @Desrciption   根据主键ID和医院编码等参数删除材料信息
     * @param syncMaterialDTO  材料信息表主键id列表，医院编码等参数
    * @Author xingyu.xie
    * @Date   2020/7/7 16:05 
    * @Return int
    **/
    boolean updateStatus(SyncMaterialDTO syncMaterialDTO);
}
