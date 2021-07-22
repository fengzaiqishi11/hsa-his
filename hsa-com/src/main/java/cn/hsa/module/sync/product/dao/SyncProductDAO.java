package cn.hsa.module.sync.product.dao;


import cn.hsa.module.sync.product.dto.SyncProductDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.base.bfc.dao
 * @Class_name: BaseMaterialManagementDAO
 * @Describe: 生产企业数据访问层接口
 * @Author: powersi
 * @Date: 2020/7/7 15:58
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */

public interface SyncProductDAO {

    /**
     * @Menthod getById
     * @Desrciption 根据主键Id，医院编码等参数查询生产企业
     * @param syncProductDTO  主键ID和医院编码等参数
     * @Author xingyu.xie
     * @Date   2020/7/7 15:31
     * @Return BaseMaterialDTO
     **/
    SyncProductDTO getById(SyncProductDTO syncProductDTO);

    /**
     * @Menthod queryAll
     * @Desrciption 查询全部生产企业
     * @param syncProductDTO  医院编码
     * @Author xingyu.xie
     * @Date   2020/7/7 16:05
     * @Return java.util.List<cn.hsa.module.base.bfc.dto.BaseMaterialDTO>
     **/
    List<SyncProductDTO> queryAll(SyncProductDTO syncProductDTO);

    /**
    * @Menthod queryPage
    * @Desrciption 分页查询生产企业
     * @param syncProductDTO  材料分类数据对象
    * @Author xingyu.xie
    * @Date   2020/7/7 16:05
    * @Return java.util.List<cn.hsa.module.base.bfc.dto.BaseMaterialDTO>
    **/
    List<SyncProductDTO> queryPage(SyncProductDTO syncProductDTO);
    /**
    * @Menthod insert
    * @Desrciption 新增单条生产企业
     * @param syncProductDTO  材料分类数据对象
    * @Author xingyu.xie
    * @Date   2020/7/7 16:05
    * @Return int
    **/
    int insert(SyncProductDTO syncProductDTO);

    /**
    * @Menthod update
    * @Desrciption 修改单条生产企业
     * @param syncProductDTO  材料分类数据对象
    * @Author xingyu.xie
    * @Date   2020/7/7 16:05
    * @Return int
    **/
    int update(SyncProductDTO syncProductDTO);

    /**
    * @Menthod delete
    * @Desrciption   根据主键ID和医院编码等参数删除生产企业
     * @param syncProductDTO  生产企业表主键id列表，医院编码等参数
    * @Author xingyu.xie
    * @Date   2020/7/7 16:05
    * @Return int
    **/
    int updateStatus(SyncProductDTO syncProductDTO);

    /**
     * @Method isCodeExist
     * @Desrciption 判断生产企业编码是否已经存在
     * @Param
     * [centerProductDTO]
     * @Author xingyu.xie
     * @Date   2020/7/24 16:45
     * @Return java.lang.Integer
     **/
    Integer isCodeExist(SyncProductDTO syncProductDTO);
}
