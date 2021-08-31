package cn.hsa.module.emr.emrprintsetting.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.emr.emrprintsetting.dto.EmrPrintSettingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.emr.emrprintsetting.service
 * @Class_name: EmrPrintSettingService
 * @Describe: 电子病历打印设置业务传输层接口
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/9/17 15:48
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-emr")
public interface EmrPrintSettingService {

  /**
  * @Menthod getById
  * @Desrciption 根据id查询单个电子病历打印设置
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 15:50
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.emr.emrprintsetting.dto.EmrPrintSettingDTO>
  **/
  @PostMapping("/service/emr/emrprintsetting/getById")
  WrapperResponse<EmrPrintSettingDTO> getById(Map<String, Object> map);

  /**
  * @Menthod queryPage
  * @Desrciption 分页查询电子病历打印设置
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 15:51
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @PostMapping("/service/emr/emrprintsetting/queryPage")
  WrapperResponse<PageDTO> queryPage(Map map);

  /**
  * @Menthod queryAll
  * @Desrciption 查询所有电子病历打印设置
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 15:51
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.emr.emrprintsetting.dto.EmrPrintSettingDTO>>
  **/
  @PostMapping("/service/emr/emrprintsetting/queryAll")
  WrapperResponse<List<EmrPrintSettingDTO>> queryAll(Map map);

  /**
  * @Menthod insert
  * @Desrciption 保存电子病历打印设置
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 15:51
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/service/emr/emrprintsetting/insert")
  WrapperResponse<Boolean> save(Map map);

  /**
  * @Menthod updateStatus
  * @Desrciption 保存电子病历打印设置状态
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 15:52
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/service/emr/emrprintsetting/delete")
  WrapperResponse<Boolean> updateStatus(Map map);
}
