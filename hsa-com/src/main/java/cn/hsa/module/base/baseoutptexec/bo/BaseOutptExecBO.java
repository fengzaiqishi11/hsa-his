package cn.hsa.module.base.baseoutptexec.bo;


import cn.hsa.base.PageDTO;
import cn.hsa.module.base.baseoutptexec.dto.BaseOutptExecDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.base.baseoutptexec.bo
 * @Class_name: BaseOutptExecBO
 * @Describe: 门诊执行科室配置信息维护业务逻辑接口
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/10/14 14:13
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface BaseOutptExecBO {
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
   * @Menthod queryPage
   * @Desrciption 根据条件分页查询门诊执行科室配置信息
   *
   * @Param
   * [baseOutptExecDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/10/14 14:08
   * @Return java.util.List<cn.hsa.module.base.baseoutptexec.entity.BaseOutptExecDTO>
   **/
  PageDTO queryPage(BaseOutptExecDTO baseOutptExecDTO);

  /**
  * @Menthod queryAll
  * @Desrciption 根据条件查询门诊执行科室配置信息
  *
  * @Param
  * [baseOutptExecDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/10/14 14:17
  * @Return java.util.List<cn.hsa.module.base.baseoutptexec.entity.BaseOutptExecDTO>
  **/
  List<BaseOutptExecDTO> queryAll(BaseOutptExecDTO baseOutptExecDTO);

  /**
   * @Menthod save
   * @Desrciption 新增/编辑门诊执行科室配置信息
   *
   * @Param
   * [baseOutptExecDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/10/14 14:09
   * @Return int
   **/
  boolean save(BaseOutptExecDTO baseOutptExecDTO);

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
  boolean updateStatus(BaseOutptExecDTO baseOutptExecDTO);

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
}
