package cn.hsa.module.base.bspl.dao;

import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import cn.hsa.module.base.bspl.dto.BaseSupplierDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.data.dao
 * @Class_name: BaseSupplierDao
 * @Describe: 供应商数据访问层接口
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/7 15:42
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface BaseSupplierDAO {
  /**
   * @Menthod getById()
   * @Desrciption  通过主键id查询供应商信息
   *
   * @Param
   * 1.map  主键id和医院编码
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
   List<BaseSupplierDTO> queryPage(BaseSupplierDTO baseSupplierDTO);

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
   *1. baseSupplierDTO  供应商数据对象
   *
   * @Author jiahong.yang
   * @Date   2020/7/7 15:53
   * @Return int
   **/
  int insert(BaseSupplierDTO baseSupplierDTO);

  /**
   * @Menthod updateStatus()
   * @Desrciption 启用禁用供应商
   *
   * @Param
   *  1. map
   *
   * @Author jiahong.yang
   * @Date   2020/7/7 15:57
   * @Return int
   **/
  int updateStatus(BaseSupplierDTO baseSupplierDTO);

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
  int update(BaseSupplierDTO baseSupplierDTO);

  /**
  * @Menthod queryCodeIsExist（）
  * @Desrciption 判断编码是否存在
  *
  * @Param
  * [baseSupplierDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/6 19:13
  * @Return int
  **/
  int queryCodeIsExist(BaseSupplierDTO baseSupplierDTO);

    /**
    * @Menthod insertList
    * @Desrciption
    * @param baseSupplierDTO
    * @Author xingyu.xie
    * @Date   2021/1/8 16:42
    * @Return int
    **/
    int insertList(@Param("list") List<BaseSupplierDTO> baseSupplierDTO);

}
