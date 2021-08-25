package cn.hsa.module.base.baseoutptexec.dao;


import cn.hsa.module.base.baseoutptexec.dto.BaseOutptExecDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.base.baseoutptexec.dao
 * @Class_name: BaseOutptExecDAO
 * @Describe: 门诊执行科室配置数据接口层
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/10/14 14:05
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface BaseOutptExecDAO {

  /**
  * @Menthod getById
  * @Desrciption 根据id查询门诊执行科室配置信息
  *
  * @Param
  * [baseOutptExecDTODO]
  *
  * @Author jiahong.yang
  * @Date   2020/10/14 14:08
  * @Return cn.hsa.module.base.baseoutptexec.entity.BaseOutptExecDTO
  **/
  BaseOutptExecDTO getById(BaseOutptExecDTO baseOutptExecDTO);

  /**
  * @Menthod queryPageorAll
  * @Desrciption 根据条件查询门诊执行科室配置信息
  *
  * @Param
  * [baseOutptExecDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/10/14 14:08
  * @Return java.util.List<cn.hsa.module.base.baseoutptexec.entity.BaseOutptExecDTO>
  **/
  List<BaseOutptExecDTO> queryPageorAll(BaseOutptExecDTO baseOutptExecDTO);

  /**
  * @Menthod insert
  * @Desrciption 新增门诊执行科室配置信息
  *
  * @Param
  * [baseOutptExecDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/10/14 14:09
  * @Return int
  **/
  int insert(BaseOutptExecDTO baseOutptExecDTO);

  /**
  * @Menthod update
  * @Desrciption 修改门诊执行科室配置信息
  *
  * @Param
  * [baseOutptExecDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/10/14 14:09
  * @Return int
  **/
  int update(BaseOutptExecDTO baseOutptExecDTO);

  /**
  * @Menthod deleteById
  * @Desrciption 作废/启用门诊执行科室配置信息
  *
  * @Param
  * [baseOutptExecDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/10/14 14:09
  * @Return int
  **/
  int updateStatus(BaseOutptExecDTO baseOutptExecDTO);

  /**
   * @Menthod getExecDept
   * @Desrciption 根据用法、开方科室获取执行科室
   *
   * @Param
   * [baseOutptExecDTODO]
   *
   * @Author zengfeng
   * @Date   2020/10/15 14:08
   * @Return cn.hsa.module.base.baseoutptexec.entity.BaseOutptExecDTO
   **/
  BaseOutptExecDTO getExecDept(BaseOutptExecDTO baseOutptExecDTO);

  /**
   * @Method getExecDeptS
   * @Desrciption 是否存在同一个科室同一个用法重复的数据
   * @Param
   * [baseOutptExecDTO]
   * @Author liaojunjie
   * @Date   2021/1/19 15:18
   * @Return java.util.List<cn.hsa.module.base.baseoutptexec.dto.BaseOutptExecDTO>
   **/
  List<BaseOutptExecDTO> getExecDeptS(BaseOutptExecDTO baseOutptExecDTO);
}
