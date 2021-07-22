package cn.hsa.base.baseoutptexec.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.baseoutptexec.bo.BaseOutptExecBO;
import cn.hsa.module.base.baseoutptexec.dto.BaseOutptExecDTO;
import cn.hsa.module.base.baseoutptexec.service.BaseOutptExecService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.base.baseoutptexec.service.impl
 * @Class_name: BaseOutptExecServiceImpl
 * @Describe: 门诊执行科室配置信息维护业务传输实现层
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/10/14 14:37
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/base/baseOutptExecService")
@Slf4j
@Service("baseOutptExecService_provider")
public class BaseOutptExecServiceImpl extends HsafService implements BaseOutptExecService {
  /**
   * 门诊执行科室配置信息逻辑接口
   */
  @Resource
  private BaseOutptExecBO baseOutptExecBO;

  /**
  * @Menthod getById
  * @Desrciption
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/10/14 14:49
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.baseoutptexec.dto.BaseOutptExecDTO>
  **/
  @Override
  public WrapperResponse<BaseOutptExecDTO> getById(Map<String, Object> map) {
    BaseOutptExecDTO baseOutptExecDTO = MapUtils.get(map,"baseOutptExecDTO");
    return WrapperResponse.success(baseOutptExecBO.getById(baseOutptExecDTO));
  }

  /**
  * @Menthod queryPage
  * @Desrciption
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/10/14 14:49
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @Override
  public WrapperResponse<PageDTO> queryPage(Map map) {
    BaseOutptExecDTO baseOutptExecDTO = MapUtils.get(map,"baseOutptExecDTO");
    PageDTO pageDTO = baseOutptExecBO.queryPage(baseOutptExecDTO);
    return WrapperResponse.success(pageDTO);
  }

  /**
  * @Menthod queryAll
  * @Desrciption
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/10/14 14:49
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.baseoutptexec.dto.BaseOutptExecDTO>>
  **/
  @Override
  public WrapperResponse<List<BaseOutptExecDTO>> queryAll(Map map) {
    BaseOutptExecDTO baseOutptExecDTO = MapUtils.get(map,"baseOutptExecDTO");
    List<BaseOutptExecDTO> baseOutptExecDTOS = baseOutptExecBO.queryAll(baseOutptExecDTO);
    return WrapperResponse.success(baseOutptExecDTOS);
  }

  /**
  * @Menthod save
  * @Desrciption
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/10/14 14:49
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> save(Map map) {
    BaseOutptExecDTO baseOutptExecDTO = MapUtils.get(map,"baseOutptExecDTO");
    return WrapperResponse.success(baseOutptExecBO.save(baseOutptExecDTO));
  }

  /**
  * @Menthod updateStatus
  * @Desrciption
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/10/14 14:50
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> updateStatus(Map map) {
    BaseOutptExecDTO baseOutptExecDTO = MapUtils.get(map,"baseOutptExecDTO");
    return WrapperResponse.success(baseOutptExecBO.updateStatus(baseOutptExecDTO));
  }

  /**
   * @Menthod getExecDept
   * @Desrciption 根据用法、开方科室获取执行科室
   *
   * @Param
   * [baseOutptExecDTODO]
   *
   * @Author zengfeng
   * @Date   2020/10/15 14:08
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.baseoutptexec.dto.BaseOutptExecDTO>
   **/
  @Override
  public WrapperResponse<BaseOutptExecDTO> getExecDept(Map<String, Object> map) {
    BaseOutptExecDTO baseOutptExecDTO = MapUtils.get(map,"baseOutptExecDTO");
    return WrapperResponse.success(baseOutptExecBO.getExecDept(baseOutptExecDTO));
  }

}
