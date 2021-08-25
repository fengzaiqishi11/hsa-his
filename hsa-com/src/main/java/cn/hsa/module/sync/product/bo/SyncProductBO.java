package cn.hsa.module.sync.product.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.sync.product.dto.SyncProductDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.base.bfc.bo
 * @Class_name: BaseMaterialManagementBO
 * @Describe: 生产企业信息业务逻辑实现层接口
 * @Author: xingyu.xie
 * @Date: 2020/7/7 16:00
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface SyncProductBO {

    /**
    * @Menthod getById
     * @Desrciption 根据主键Id，医院编码等参数查询生产企业信息
     * @param syncProductDTO  主键ID 和医院编码等参数
    * @Author xingyu.xie
    * @Date   2020/7/7 16:05 
    * @Return cn.hsa.module.base.bfc.dto.BaseMaterialDTO
    **/
    SyncProductDTO getById(SyncProductDTO syncProductDTO);

    /**
     * @Menthod queryAll
     * @Desrciption   查询所有生产企业信息
     * @param syncProductDTO  医院编码
     * @Author xingyu.xie
     * @Date   2020/7/14 16:04
     * @Return java.util.List<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
     **/
    List<SyncProductDTO> queryAll(SyncProductDTO syncProductDTO);

    /**
    * @Menthod queryAll
    * @Desrciption   分页查询生产企业信息
     * @param syncProductDTO  生产企业分类数据对象
    * @Author xingyu.xie
    * @Date   2020/7/7 16:04 
    * @Return java.util.List<cn.hsa.module.base.bfc.dto.BaseMaterialDTO>
    **/
    PageDTO queryPage(SyncProductDTO syncProductDTO);

    /**
     * @Menthod save
     * @Desrciption  修改或新增生产企业信息
     * @param syncProductDTO 生产企业数据参数对象
     * @Author xingyu.xie
     * @Date   2020/7/25 11:32
     * @Return boolean
     **/
    boolean save(SyncProductDTO syncProductDTO);

    /**
    * @Menthod updateStatus
     * @Desrciption   根据主键ID和医院编码等参数删除生产企业信息
     * @param syncProductDTO  生产企业信息表主键id列表，医院编码等参数
    * @Author xingyu.xie
    * @Date   2020/7/7 16:05 
    * @Return int
    **/
    boolean updateStatus(SyncProductDTO syncProductDTO);
}
