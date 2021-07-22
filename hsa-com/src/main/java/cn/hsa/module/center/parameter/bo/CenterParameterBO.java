package cn.hsa.module.center.parameter.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.center.parameter.dto.CenterParameterDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.center.parameter.bo
 * @Class_name: centerParameterBO
 * @Describe:  参数管理业务逻辑实现层
 * @Author: zhangxuan
 * @Email: zhangxuan@powersi.com
 * @Date: 2020/7/28 16:32
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface CenterParameterBO {

  /**
   * @Menthod queryPage
   * @Desrciption 根据条件查询参数信息
   * @Param
   * 1. centerParameterDTO  参数数据对象
   * @Author zhangxuan
   * @Date   2020/7/28 17:02
   * @Return cn.hsa.base.PageDTO
   **/
  PageDTO queryPage(CenterParameterDTO centerParameterDTO);

  /**
   * @Menthod queryAll()
   * @Desrciption  查询所有参数信息
   * @Param
   * [1. centerParameterDTO]
   * @Author zhangxuan
   * @Date   2020/7/28 14:47
   * @Return java.util.List<cn.hsa.module.center.parameter.dto.SyncParameterDTO>
   **/
  List<CenterParameterDTO> queryAll(CenterParameterDTO centerParameterDTO);

  /**
   * @Menthod insert()
   * @Desrciption 新增参数
   * @Param
   * 1. centerParameterDTO  参数数据对象
   * @Author zhangxuan
   * @Date   2020/7/28 15:53
   * @Return int
   **/
  boolean insert(CenterParameterDTO centerParameterDTO);

  /**
   * @Menthod deleteSuppelier()
   * @Desrciption 删除参数
   * @Param
   *  1. map
   * @Author zhangxuan
   * @Date   2020/7/28 15:57
   * @Return int
   **/
  boolean delete(CenterParameterDTO centerParameterDTO);

  /**
   * @Menthod update()
   * @Desrciption 修改参数信息
   * @Param
   * 1. centerParameterDTO  参数数据对象
   * @Author zhangxuan
   * @Date   2020/7/28 15:58
   * @Return int
   **/
  boolean update(CenterParameterDTO centerParameterDTO);

  /**
   * @Method: getParameterByCode
   * @Description: 根据编码获取参数
   * @Param: [code]
   * @Author: youxianlin
   * @Email: 254580179@qq.com
   * @Date: 2020/11/25 14:24
   * @Return: cn.hsa.module.center.parameter.dto.CenterParameterDTO
   **/
  CenterParameterDTO getParameterByCode(String code);

  Map<String, CenterParameterDTO> getParameterByCodeList(String... codeList);
}
