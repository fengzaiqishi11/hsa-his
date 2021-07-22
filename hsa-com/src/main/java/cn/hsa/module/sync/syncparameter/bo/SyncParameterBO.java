package cn.hsa.module.sync.syncparameter.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.sync.syncparameter.dto.SyncParameterDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.sync.parameter.bo
 * @Class_name: syncParameterBO
 * @Describe:  参数管理业务逻辑实现层
 * @Author: zhangxuan
 * @Email: zhangxuan@powersi.com
 * @Date: 2020/9/2 16:32
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface SyncParameterBO {

  /**
   * @Menthod queryPage
   * @Desrciption 根据条件查询参数信息
   * @Param
   * 1. syncParameterDTO  参数数据对象
   * @Author zhangxuan
   * @Date   2020/9/2 17:02
   * @Return cn.hsa.base.PageDTO
   **/
  PageDTO queryPage(SyncParameterDTO syncParameterDTO);

  /**
   * @Menthod queryAll()
   * @Desrciption  查询所有参数信息
   * @Param
   * [1. syncParameterDTO]
   * @Author zhangxuan
   * @Date   2020/9/2 14:47
   * @Return java.util.List<cn.hsa.module.sync.parameter.dto.SyncParameterDTO>
   **/
  List<SyncParameterDTO> queryAll(SyncParameterDTO syncParameterDTO);

  /**
   * @Menthod insert()
   * @Desrciption 新增参数
   * @Param
   * 1. syncParameterDTO  参数数据对象
   * @Author zhangxuan
   * @Date   2020/9/2 15:53
   * @Return int
   **/
  boolean insert(SyncParameterDTO syncParameterDTO);

  /**
   * @Menthod deleteSuppelier()
   * @Desrciption 删除参数
   * @Param
   *  1. map
   * @Author zhangxuan
   * @Date   2020/9/2 15:57
   * @Return int
   **/
  boolean delete(SyncParameterDTO syncParameterDTO);

  /**
   * @Menthod update()
   * @Desrciption 修改参数信息
   * @Param
   * 1. syncParameterDTO  参数数据对象
   * @Author zhangxuan
   * @Date   2020/9/2 15:58
   * @Return int
   **/
  boolean update(SyncParameterDTO syncParameterDTO);


}
