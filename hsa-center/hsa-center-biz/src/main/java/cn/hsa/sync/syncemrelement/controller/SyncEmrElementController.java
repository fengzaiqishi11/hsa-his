package cn.hsa.sync.syncemrelement.controller;

import cn.hsa.base.BaseController;
import cn.hsa.base.CenterBaseController;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.emr.emrelement.dto.EmrElementDTO;
import cn.hsa.module.sync.syncemrelement.dto.SyncEmrElementDTO;
import cn.hsa.module.sync.syncemrelement.service.SyncEmrElementServcie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.emr
 * @Class_name: EmrElementController
 * @Describe:  电子病历元素管理控制层
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/9/18 16:20
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/center/sync/syncEmrElement")
@Slf4j
public class SyncEmrElementController extends CenterBaseController {

  /**
   * 电子病历元素管理
   */
  @Resource
  private SyncEmrElementServcie emrElementServcie_consumer;

  /**
  * @Menthod getById
  * @Desrciption 根据主键id查询电子病历元素
  *
  * @Param
  * [emrElementDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/18 16:29
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.emr.emrelement.dto.EmrElementDTO>
  **/
  @GetMapping("/getByIdorCode")
  public WrapperResponse<SyncEmrElementDTO> getByIdorCode(SyncEmrElementDTO emrElementDTO) {
    return emrElementServcie_consumer.getByIdorCode(emrElementDTO);
  }

  /**
  * @Menthod queryAll
  * @Desrciption 查询所有电子病历元素
  *
  * @Param
  * [emrElementDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/18 16:29
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.emr.emrelement.dto.SyncEmrElementDTO>>
  **/
  @GetMapping("/queryAll")
  public WrapperResponse<List<SyncEmrElementDTO>> queryAll(SyncEmrElementDTO emrElementDTO) {
    return this.emrElementServcie_consumer.queryAll(emrElementDTO);
  }

  /**
  * @Menthod insert
  * @Desrciption 新增电子病历元素
  *
  * @Param
  * [emrElementDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/18 16:28
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/save")
  public WrapperResponse<Boolean> save(@RequestBody SyncEmrElementDTO emrElementDTO) {
    emrElementDTO.setCrteId(userId);
    emrElementDTO.setCrteName(userName);
    return emrElementServcie_consumer.save(emrElementDTO);
  }

  /**
  * @Menthod getEmrElementTree
  * @Desrciption 获取电子病历元素树
  *
  * @Param
  * [emrElementDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/18 16:28
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
  **/
  @GetMapping("getEmrElementTree")
  public WrapperResponse<List<TreeMenuNode>> getEmrElementTree(SyncEmrElementDTO emrElementDTO) {
    return emrElementServcie_consumer.getEmrElementTree(emrElementDTO);
  }
}
