package cn.hsa.module.base.bspl.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.base.bspl.dto.BaseSupplierDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.data.bo
 * @Class_name: BaseSupplierBO
 * @Describe:  供应商管理业务逻辑实现层
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/7 16:32
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface BaseSupplierBO {
  /**
   * @Menthod getById()
   * @Desrciption  通过主键id查询供应商信息
   *
   * @Param
   * 1. map  主键id和医院编码
   *
   * @Author jiahong.yang
   * @Date   2020/7/7 15:45
   * @Return cn.hsa.module.base.data.dao.BaseSupplierDao
   **/
  BaseSupplierDTO getById(Map<String, Object> map);


  /**
   * @Menthod queryPage
   * @Desrciption 根据条件查询供应商信息
   *
   * @Param
   * 1. baseSupplierDTO  供应商数据对象
   *
   * @Author jiahong.yang
   * @Date   2020/7/7 17:02
   * @Return cn.hsa.base.PageDTO
   **/
  PageDTO queryPage(BaseSupplierDTO baseSupplierDTO);

  /**
   * @Menthod queryAll()
   * @Desrciption  查询所有供应商信息
   *
   * @Param
   * [1. baseSupplierDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/7/18 14:47
   * @Return java.util.List<cn.hsa.module.base.bspl.dto.BaseSupplierDTO>
   **/
  List<BaseSupplierDTO> queryAll(BaseSupplierDTO baseSupplierDTO);

  /**
   * @Menthod insert()
   * @Desrciption 新增供应商
   *
   * @Param
   * 1. baseSupplierDTO  供应商数据对象
   *
   * @Author jiahong.yang
   * @Date   2020/7/7 15:53
   * @Return int
   **/
  boolean insert(BaseSupplierDTO baseSupplierDTO);

  /**
   * @Menthod deleteSuppelier()
   * @Desrciption 启用禁用供应商
   *
   * @Param
   *  1. map
   *
   * @Author jiahong.yang
   * @Date   2020/7/7 15:57
   * @Return int
   **/
  boolean updateStatus(BaseSupplierDTO baseSupplierDTO);

  /**
   * @Menthod update()
   * @Desrciption 修改供应商信息
   *
   * @Param
   * 1. baseSupplierDTO  供应商数据对象
   *
   * @Author jiahong.yang
   * @Date   2020/7/7 15:58
   * @Return int
   **/
  boolean update(BaseSupplierDTO baseSupplierDTO);


  /**
   * @Menthod insertUpLoad
   * @Desrciption   供应商导入功能
   * @param map  医院编码等参数
   * @Author xingyu.xie
   * @Date   2020/7/7 16:05
   * @Return int
   **/
  boolean insertUpLoad(Map map);
}
