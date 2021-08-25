package cn.hsa.module.sync.syncemrelement.service;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.syncemrelement.dto.SyncEmrElementDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.emr.emrelement.service
 * @Class_name: EmrElementServcie
 * @Describe: 电子病历元素管理业务传输接口
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/9/17 16:38
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-center")
public interface SyncEmrElementServcie {

  /**
  * @Menthod getById
  * @Desrciption 根据主键id或者编码code查询单个电子病历元素
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 16:42
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.emr.emrelement.dto.EmrElementDTO>
  **/
  @PostMapping("/service/sync/syncemrelement/getByIdorCode")
  WrapperResponse<SyncEmrElementDTO> getByIdorCode(SyncEmrElementDTO syncEmrElementDTO);

  /**
  * @Menthod queryAll
  * @Desrciption 查询所有电子病历元素
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 16:43
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.emr.emrelement.dto.EmrElementDTO>>
  **/
  @PostMapping("/service/sync/syncemrelement/queryAll")
  WrapperResponse<List<SyncEmrElementDTO>> queryAll(SyncEmrElementDTO syncEmrElementDTO);

  /**
  * @Menthod queryAll
  * @Desrciption 根据条件查询元素编码列表
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/10/9 10:58
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.lang.String>>
  **/
  @PostMapping("/service/sync/syncemrelement/queryElementCodes")
  WrapperResponse<List<String>> queryElementCodes(Map map);

  /**
  * @Menthod save
  * @Desrciption 保存电子病历元素
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 16:43
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/service/sync/syncemrelement/save")
  WrapperResponse<Boolean> save(SyncEmrElementDTO syncEmrElementDTO);

  /**
  * @Menthod getEmrElementTree
  * @Desrciption 获取电子病历元素树
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/9/18 16:35
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
  **/
  @GetMapping("/service/sync/syncemrelement/getEmrElementTree")
  WrapperResponse<List<TreeMenuNode>> getEmrElementTree(SyncEmrElementDTO syncEmrElementDTO);

}
