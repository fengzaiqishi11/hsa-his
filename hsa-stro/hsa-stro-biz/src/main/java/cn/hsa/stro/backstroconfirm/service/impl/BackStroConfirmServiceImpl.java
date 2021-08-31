package cn.hsa.stro.backstroconfirm.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.stro.backstroconfirm.bo.BackStroConfirmBO;
import cn.hsa.module.stro.backstroconfirm.service.BackStroConfirmService;
import cn.hsa.module.stro.stroout.dto.StroOutDTO;
import cn.hsa.module.stro.stroout.dto.StroOutDetailDTO;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.store.returnstro.service.impl
 * @Class_name: ReturnStroApiImpl
 * @Describe:
 * @Author: xingyu.xie
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/22 9:04
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
@HsafRestPath("/service/stro/backStroConfirm")
@Service("backStroConfirmService_provider")
public class BackStroConfirmServiceImpl extends HsafService implements BackStroConfirmService {

  /**
   * 注入退库确认的bo层接口
   */
  @Resource
  private BackStroConfirmBO backStroConfirmBO;


  /**
   * @Menthod queryBackOutinPage
   * @Desrciption  查询药房退库的单据信息
   * @param map 出入库表数据传输对象
   * @Author xingyu.xie
   * @Date   2020/7/27 14:26
   * @Return java.util.List<cn.hsa.module.stro.outinstro.dto.StroOutinDTO>
   **/
  @Override
  public WrapperResponse<PageDTO> queryBackOutPage(Map map) {
    return WrapperResponse.success(backStroConfirmBO.queryBackOutPage(MapUtils.get(map,"stroOutDTO")));
  }

  @Override
  public WrapperResponse<PageDTO> queryBackOutinPageyf(Map map) {
    return WrapperResponse.success(backStroConfirmBO.queryBackOutinPageyf(MapUtils.get(map,"stroOutDTO")));
  }
  /**
   * @Menthod queryOutDetailByOutId
   * @Desrciption  根据医院编码和出入库表orderNo去查询出入库明细的数据
   * @param map 出入库表数据传输对象
   * @Author xingyu.xie
   * @Date   2020/7/27 14:26
   * @Return java.util.List<cn.hsa.module.stro.outinstro.dto.StroOutinDTO>
   **/
  @Override
  public WrapperResponse<List<StroOutDetailDTO>> queryOutDetailByOutId(Map map) {
    return WrapperResponse.success(backStroConfirmBO.queryOutDetailByOutId(MapUtils.get(map,"stroOutDTO")));
  }

  /**
   * @Menthod updateAuditCode
   * @Desrciption  批量进行退库确认的审核
   * @param map 出入库表数据传输对象
   * @Author xingyu.xie
   * @Date   2020/7/27 14:29
   * @Return boolean
   **/
  @Override
  public WrapperResponse<Boolean> updateOkAuditCode(Map map) {
      return WrapperResponse.success(backStroConfirmBO.updateOkAuditCode(MapUtils.get(map, "stroOutDTO")));
  }
  /**
   * @Menthod update
   * @Desrciption  更新
   * @param map
   * @Author xingyu.xie
   * @Date   2020/7/27 14:29
   * @Return boolean
   **/
  @Override
  public WrapperResponse<Boolean> update(Map map) {
    return WrapperResponse.success(backStroConfirmBO.update(MapUtils.get(map,"stroOutDTO"))>0);
  }
  /**
   * @Menthod insert
   * @Desrciption  更新
   * @param map
   * @Author xingyu.xie
   * @Date   2020/7/27 14:29
   * @Return boolean
   **/
  @Override
  public WrapperResponse<Boolean> insert(Map map) {
    return WrapperResponse.success(backStroConfirmBO.insert(MapUtils.get(map,"stroOutDTO"))>0);
  }
  /**
   * @Menthod getById
   * @Desrciption  查询
   * @param map
   * @Author xingyu.xie
   * @Date   2020/7/27 14:29
   * @Return boolean
   **/
  @Override
  public WrapperResponse<StroOutDTO> getById(Map map) {
    return WrapperResponse.success(backStroConfirmBO.getById(MapUtils.get(map,"id")));
  }
  /**
   * @Menthod invalid
   * @Desrciption  作废
   * @param map
   * @Author xingyu.xie
   * @Date   2020/7/27 14:29
   * @Return boolean
   **/

  /**
   * @Menthod examine
   * @Desrciption
   * @param map
   * @Author xingyu.xie
   * @Date   2020/7/27 14:29
   * @Return boolean
   **/
  @Override
  public WrapperResponse<Boolean> updateAuditCode(Map map) {
      return WrapperResponse.success(backStroConfirmBO.updateAuditCode(MapUtils.get(map, "stroOutDTO")) > 0);
  }
}
