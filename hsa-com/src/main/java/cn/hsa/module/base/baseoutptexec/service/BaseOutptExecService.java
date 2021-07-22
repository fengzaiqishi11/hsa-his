package cn.hsa.module.base.baseoutptexec.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.baseoutptexec.dto.BaseOutptExecDTO;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.baseoutptexec.service
 * @Class_name: BaseOutptExecBO
 * @Describe: 门诊执行科室配置信息维护业务传输接口
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/10/14 14:14
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface BaseOutptExecService {

  /**
  * @Menthod getById
  * @Desrciption 根据id查询门诊执行科室配置信息
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/10/14 14:18
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.baseoutptexec.entity.BaseOutptExecDTO>
  **/
  @PostMapping("/service/base/baseoutptexec/getById")
  WrapperResponse<BaseOutptExecDTO> getById(Map<String, Object> map);

  /**
  * @Menthod queryPage
  * @Desrciption 根据条件分页查询门诊执行科室配置信息
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/10/14 14:19
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @PostMapping("/service/base/baseoutptexec/queryPage")
  WrapperResponse<PageDTO> queryPage(Map map);

  /**
  * @Menthod queryAll
  * @Desrciption 根据条件查询门诊执行科室配置信息
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/10/14 14:19
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.baseoutptexec.entity.BaseOutptExecDTO>>
  **/
  @PostMapping("/service/base/baseoutptexec/queryAll")
  WrapperResponse<List<BaseOutptExecDTO>> queryAll(Map map);

  /**
  * @Menthod save
  * @Desrciption 新增/编辑门诊执行科室配置信息
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/10/14 14:19
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/service/base/baseoutptexec/insert")
  WrapperResponse<Boolean> save(Map map);

  /**
  * @Menthod updateStatus
  * @Desrciption 启用门诊执行科室配置信息
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/10/14 14:19
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/service/base/baseoutptexec/delete")
  WrapperResponse<Boolean> updateStatus(Map map);

  /**
   * @Menthod getExecDept
   * @Desrciption 根据用法、开方科室获取执行科室
   *
   * @Param
   * [baseOutptExecDTODO]
   *
   * @Author zengfeng
   * @Date   2020/10/15 14:08
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.baseoutptexec.entity.BaseOutptExecDTO>
   **/
  @PostMapping("/service/base/baseoutptexec/getExecDept")
  WrapperResponse<BaseOutptExecDTO> getExecDept(Map<String, Object> map);
}
