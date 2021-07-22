package cn.hsa.module.sync.material.dao;

import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import cn.hsa.module.sync.material.dto.SyncMaterialDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.base.bfc.dao
 * @Class_name: BaseMaterialManagementDAO
 * @Describe: 材料信息数据访问层接口
 * @Author: powersi
 * @Date: 2020/7/7 15:58
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */

public interface SyncMaterialDAO {

    /**
     * @Menthod getById
     * @Desrciption 根据主键Id，医院编码等参数查询材料信息
     * @param syncMaterialDTO  主键ID和医院编码等参数
     * @Author xingyu.xie
     * @Date   2020/7/7 15:31
     * @Return SyncMaterialDTO
     **/
    SyncMaterialDTO getById(SyncMaterialDTO syncMaterialDTO);

    /**
     * @Menthod queryByIds
     * @Desrciption  根据主键ids查询多个材料的库存信息
     * @param syncMaterialDTO
     * @Author xingyu.xie
     * @Date   2020/12/9 11:34
     * @Return java.util.List<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
     **/
    List<SyncMaterialDTO> queryByIds(SyncMaterialDTO syncMaterialDTO);

    /**
     * @Menthod queryAll
     * @Desrciption 查询全部材料信息
     * @Author xingyu.xie
     * @Date   2020/7/7 16:05
     * @Return java.util.List<cn.hsa.module.base.bfc.dto.SyncMaterialDTO>
     **/
    List<SyncMaterialDTO> queryAll();

    /**
    * @Menthod queryPage
    * @Desrciption 分页查询材料信息
     * @param syncMaterialDTO  材料分类数据对象
    * @Author xingyu.xie
    * @Date   2020/7/7 16:05
    * @Return java.util.List<cn.hsa.module.base.bfc.dto.SyncMaterialDTO>
    **/
    List<SyncMaterialDTO> queryPage(SyncMaterialDTO syncMaterialDTO);
    /**
    * @Menthod insert
    * @Desrciption 新增单条材料信息
     * @param syncMaterialDTO  材料分类数据对象
    * @Author xingyu.xie
    * @Date   2020/7/7 16:05
    * @Return int
    **/
    int insert(SyncMaterialDTO syncMaterialDTO);

    /**
    * @Menthod update
    * @Desrciption 修改单条材料信息
     * @param syncMaterialDTO  材料分类数据对象
    * @Author xingyu.xie
    * @Date   2020/7/7 16:05
    * @Return int
    **/
    int update(SyncMaterialDTO syncMaterialDTO);

    /**
     * @Menthod updateList
     * @Desrciption 修改多条条材料信息
     * @param syncMaterialDTO  材料分类数据对象
     * @Author xingyu.xie
     * @Date   2020/7/7 16:05
     * @Return int
     **/
    int updateList(@Param("list") List<SyncMaterialDTO> syncMaterialDTO);

    /**
    * @Menthod delete
    * @Desrciption   根据主键ID和医院编码等参数删除材料信息
     * @param syncMaterialDTO  材料信息表主键id列表，医院编码等参数
    * @Author xingyu.xie
    * @Date   2020/7/7 16:05
    * @Return int
    **/
    int updateStatus(SyncMaterialDTO syncMaterialDTO);

    /**
     * @Method isCodeExist
     * @Desrciption 判断材料编码是否已经存在
     * @Param
     * [centerMaterialDTO]
     * @Author xingyu.xie
     * @Date   2020/7/24 16:45
     * @Return java.lang.Integer
     **/
    Integer isCodeExist(SyncMaterialDTO syncMaterialDTO);
}
