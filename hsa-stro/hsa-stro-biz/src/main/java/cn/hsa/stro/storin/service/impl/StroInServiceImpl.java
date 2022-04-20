package cn.hsa.stro.storin.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import cn.hsa.module.stro.stroin.bo.StroInBO;
import cn.hsa.module.stro.stroin.dto.StroInDTO;
import cn.hsa.module.stro.stroin.dto.StroInDetailDTO;
import cn.hsa.module.stro.stroin.service.StroInService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.store.outinStore.service.impl
 * @Class_name: StroInServiceImpl
 * @Describe: 药库入库API接口实现层（提供给dubbo调用）
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/22 9:04
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
@HsafRestPath("/service/stro/storin")
@Service("stroInService_provider")
public class StroInServiceImpl extends HsafService implements StroInService {

  /**
   * 库房出入库逻辑接口
   */
  @Resource
  private StroInBO stroInBO;

  /**
  * @Menthod getById()
  * @Desrciption  根据主键id查询入库信息
  *
  * @Param
  * [1. btroStroInDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/25 13:44
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.stro.outinstro.dto.StroInDTO>
  **/
  @Override
  public WrapperResponse<StroInDTO> getById(Map map) {
    StroInDTO stroInDTO = MapUtils.get(map,"stroInDTO");
    return WrapperResponse.success(stroInBO.getById(stroInDTO));
  }

  /**
  * @Menthod queryStroInPage()
  * @Desrciption 根据条件分页查询入库信息
  *
  * @Param
  * [1。btroStroInDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/25 13:46
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @Override
  public WrapperResponse<PageDTO> queryStroInPage(Map map) {
    StroInDTO stroInDTO = MapUtils.get(map,"stroInDTO");
    return WrapperResponse.success(stroInBO.queryStroInPage(stroInDTO));
  }

  /**
  * @Menthod save()
  * @Desrciption  保存新增和编辑的入库单据信息
  *
  * @Param
  * [1. btroStroInDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/25 13:57
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> save(Map map) {
    StroInDTO stroInDTO = MapUtils.get(map,"stroInDTO");
    List<StroInDetailDTO> stroInDetailDTOS1 = stroInDTO.getStroInDetailDTOS();
    for (StroInDetailDTO s: stroInDetailDTOS1){
      if(s.getBuyPrice() == null){
        throw new AppException("购进价不能为空");
      }
      if(s.getSellPrice() == null){
        throw new AppException("零售价不能为空");
      }
      if(s.getNum() == null){
        throw new AppException("数量不能为空");
      }
      if(s.getSplitRatio() == null){
        s.setSplitRatio(BigDecimal.valueOf(1));
      }
    }
    return WrapperResponse.success(stroInBO.save(stroInDTO));
  }

  /**
  * @Menthod updateAuditCode()
  * @Desrciption 修改审核状态(审核和作废)
  *
  * @Param
  * [1. stroInDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/27 14:52
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> updateAuditCode(Map map) {
    StroInDTO stroInDTO = MapUtils.get(map,"stroInDTO");
    return WrapperResponse.success(stroInBO.updateAuditCode(stroInDTO));
  }

  /**
  * @Menthod queryDrugPage()
  * @Desrciption 获取全部药品或者材料填充下拉表单
  *
  * @Param
  * [1. baseDrugDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/29 17:46
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @Override
  public WrapperResponse<PageDTO> queryDrugorMaterialPage(Map map) {
    return WrapperResponse.success(stroInBO.queryDrugorMaterialPage(map));
  }

  /**
  * @Menthod queryMaterialPage()
  * @Desrciption  获取全部材料填充下拉表单
  *
  * @Param
  * [1. baseMaterialDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/29 17:46
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @Override
  public WrapperResponse<PageDTO> queryMaterialPage(Map map) {
    BaseMaterialDTO baseMaterialDTO = MapUtils.get(map,"baseMaterialDTO");
    PageDTO dto = stroInBO.queryMaterialPage(baseMaterialDTO);
    return WrapperResponse.success(dto);
  }

  /**
  * @Menthod queryWholeSuppOut
  * @Desrciption 整单出库供应商查询
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/3/8 11:14
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.stro.stroin.dto.StroInDTO>
  **/
  @Override
  public WrapperResponse<StroInDTO> queryWholeSuppOut(Map map) {
    return WrapperResponse.success(stroInBO.queryWholeSuppOut(MapUtils.get(map,"stroInDTO")));
  }

  /**
  * @Menthod insertWholeSuppOut
  * @Desrciption 整单出库新增供应商
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/3/8 14:19
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.stro.stroin.dto.StroInDTO>
  **/
  @Override
  public WrapperResponse<StroInDTO> insertWholeSuppOut(Map map) {
    return WrapperResponse.success(stroInBO.insertWholeSuppOut(MapUtils.get(map,"stroInDTO")));
  }

  /**
  * @Menthod queryStroinDetail
  * @Desrciption 查询明细数据
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/3/29 10:01
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.stro.stroin.dto.StroInDetailDTO>>
  **/
  @Override
  public WrapperResponse<List<StroInDetailDTO>> queryStroinDetail(Map map) {
    return WrapperResponse.success(stroInBO.queryStroinDetail(MapUtils.get(map,"stroInDetailDTO")));
  }

  /**
   * @Meth: queryStroinDetailForExprot
   * @Description: 批量查询明细数据
   * @Param: [map]
   * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.stro.stroin.dto.StroInDetailDTO>>
   * @Author: zhangguorui
   * @Date: 2021/9/17
   */
  @Override
  public WrapperResponse<List<StroInDetailDTO>> queryStroinDetailForExprot(Map map) {
    return WrapperResponse.success(stroInBO.queryStroinDetailForExprot(MapUtils.get(map,"stroInDetailDTO")));
  }

  /**
   * @Menthod updateStroInFk()
   * @Desrciption  修改财务付款状态
   *
   * @Param
   * [baseDrugDTO]
   *
   * @Author pengbo
   * @Date   2022/04/19 17:36
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
   **/
  @Override
  public WrapperResponse<Boolean> updateStroInFk(Map map) {
    return WrapperResponse.success(stroInBO.updateStroInFk(MapUtils.get(map,"stroInDTO")));
  }

}
