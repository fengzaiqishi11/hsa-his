package cn.hsa.module.stro.backstroconfirm.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.stro.stroout.dto.StroOutDTO;
import cn.hsa.module.stro.stroout.dto.StroOutDetailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;


/**
 * @Package_name: cn.hsa.module.store.backstroconfirm.service
 * @Class_name: BackStroConfirmApi
 * @Describe:
 * @Author: xingyu.xie
 * @Email: xingyu.xie@powersi.com
 * @Date: 2020/7/22 8:47
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/

@FeignClient(value = "hsa-stro")
public interface BackStroConfirmService {


   /**
   * @Menthod queryReturnOutinPage
   * @Desrciption  查询药房退库的单据信息
   * @param map 出入库表数据传输对象
   * @Author xingyu.xie
   * @Date   2020/7/27 14:26
   * @Return java.util.List<cn.hsa.module.stro.outinstro.dto.StroOutinDTO>
   **/
  @GetMapping("/service/stro/backStroConfirm/queryBackOutPage")
  WrapperResponse<PageDTO> queryBackOutPage(Map map);


  /**
   * @Menthod queryReturnOutinPage
   * @Desrciption  查询药房退库的单据信息
   * @param map 出入库表数据传输对象
   * @Author xingyu.xie
   * @Date   2020/7/27 14:26
   * @Return java.util.List<cn.hsa.module.stro.outinstro.dto.StroOutinDTO>
   **/
  @GetMapping("/service/stro/backStroConfirm/queryBackOutinPageyf")
  WrapperResponse<PageDTO> queryBackOutinPageyf(Map map);

  /**
   * @Menthod queryOutinDetailByOutinId
   * @Desrciption  根据医院编码和出入库表orderNo去查询出入库明细的数据
   * @param map 出入库表数据传输对象
   * @Author xingyu.xie
   * @Date   2020/7/27 14:26
   * @Return java.util.List<cn.hsa.module.stro.outinstro.dto.StroOutinDTO>
   **/
  @GetMapping("/service/stro/backStroConfirm/queryOutDetailByOutId")
  WrapperResponse<List<StroOutDetailDTO>> queryOutDetailByOutId(Map map);


  /**
   * @Menthod updateAuditCode
   * @Desrciption  批量进行退库确认的审核
   * @param map 出入库表数据传输对象
   * @Author xingyu.xie
   * @Date   2020/7/27 14:29
   * @Return boolean
   **/
  @PostMapping("/service/store/backStroConfirm/updateOkAuditCode")
  WrapperResponse<Boolean> updateOkAuditCode(Map map);
  /**
   * @Menthod updateAuditCode
   * @Desrciption  更新
   * @param map 出入库表数据传输对象
   * @Author xingyu.xie
   * @Date   2020/7/27 14:29
   * @Return boolean
   **/
  @PostMapping("/service/store/backStroConfirm/update")
  WrapperResponse<Boolean> update(Map map);
  /**
   * @Menthod insert
   * @Desrciption  新增
   * @param map
   * @Author xingyu.xie
   * @Date   2020/7/27 14:29
   * @Return boolean
   **/
  @PostMapping("/service/store/backStroConfirm/insert")
  WrapperResponse<Boolean> insert(Map map);

  /**
   * @Menthod getById
   * @Desrciption  查询一个
   * @param map
   * @Author xingyu.xie
   * @Date   2020/7/27 14:29
   * @Return StroOutinDTO
   **/
  @PostMapping("/service/store/backStroConfirm/getById")
  WrapperResponse<StroOutDTO>  getById(Map map);

  /**
   * @Menthod examine
   * @Desrciption  审核
   * @param map
   * @Author xingyu.xie
   * @Date   2020/7/27 14:29
   * @Return boolean
   **/
  @PostMapping("/service/store/backStroConfirm/insert")
  WrapperResponse<Boolean> updateAuditCode(Map map);
}
