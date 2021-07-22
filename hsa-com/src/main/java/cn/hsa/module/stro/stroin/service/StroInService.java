package cn.hsa.module.stro.stroin.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.stro.stroin.dto.StroInDTO;
import cn.hsa.module.stro.stroin.dto.StroInDetailDTO;
import cn.hsa.module.stro.stroout.dto.StroOutDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.store.instore.service
 * @Class_name: StroStroInApi
 * @Describe:  药库入库接口层（提供给dubbo调用）
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/22 8:47
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/

@FeignClient(value = "hsa-stro")
public interface StroInService {

  /**
  * @Menthod getById()
  * @Desrciption 根据主键id获取出入库信息
  *
  * @Param
   *  [1. id  主键id
   *  2。hosCode   医院编码]
   *
  * @Author jiahong.yang
  * @Date   2020/7/23 15:20
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.stro.comm.dto.StroInDTO>
  **/
  @GetMapping("/service/stro/storin/getById")
  WrapperResponse<StroInDTO> getById(Map map);

  /**
  * @Menthod queryPage()
  * @Desrciption 按条件查询药品入库信息
  *
  * @Param
  * [1. btroStroInDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/23 15:21
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.stro.comm.dto.StroInDTO>>
  **/
  @GetMapping("/service/stro/storin/queryStroInPage")
  WrapperResponse<PageDTO> queryStroInPage(Map map);

  /**
  * @Menthod save()
  * @Desrciption 保存新增和编辑的入库信息
  *
  * @Param
  * [1. btroStroInDTOs]
  *
  * @Author jiahong.yang
  * @Date   2020/7/25 13:50
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/service/stro/storin/save")
  WrapperResponse<Boolean> save(Map map);

  /**
  * @Menthod updateAuditCode()
  * @Desrciption 修改审核状态(审核和作废)
  *
  * @Param
  * [1. stroStroInDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/27 14:32
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/service/stro/storin/updateAuditCode")
  WrapperResponse<Boolean> updateAuditCode(Map map);

  /**
  * @Menthod queryDrugPage()
  * @Desrciption  获取全部药品或者材料填充下拉表单
  *
  * @Param
  * [1. baseDrugDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/29 17:40
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @GetMapping("/service/stro/storin/queryDrugPage")
  WrapperResponse<PageDTO> queryDrugorMaterialPage(Map map);

  /**
  * @Menthod queryMaterialPage
  * @Desrciption 获取全部材料填充下拉表单
  *
  * @Param
  * [1. materialDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/29 17:40
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @GetMapping("/service/stro/storin/queryMaterialPage")
  WrapperResponse<PageDTO> queryMaterialPage(Map map);

  /**
  * @Menthod queryWholeSuppOut
  * @Desrciption 整单出库供应商查询
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/3/8 11:12
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.stro.stroin.dto.StroInDTO>
  **/
  @PostMapping("/service/stro/storin/queryWholeSuppOut")
  WrapperResponse<StroInDTO> queryWholeSuppOut(Map map);

  /**
  * @Menthod insertWholeSuppOut
  * @Desrciption 整单出库供应商新增
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/3/8 14:18
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.stro.stroin.dto.StroInDTO>
  **/
  @PostMapping("/service/stro/storin/insertWholeSuppOut")
  WrapperResponse<StroInDTO> insertWholeSuppOut(Map map);

  /**
  * @Menthod insertWholeSuppOut
  * @Desrciption 查询明细数据
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/3/29 10:00
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.stro.stroin.dto.StroInDTO>
  **/
  @PostMapping("/service/stro/storin/insertWholeSuppOut")
  WrapperResponse<List<StroInDetailDTO>> queryStroinDetail(Map map);


}
