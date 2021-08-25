package cn.hsa.inpt.inptprint.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.inptprint.bo.InptPrintBO;
import cn.hsa.module.inpt.inptprint.dto.InptAdvicePrintDTO;
import cn.hsa.module.inpt.inptprint.service.InptPrintService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.inpt.inptprint.service.impl
 * @Class_name: InptPrintServiceImpl
 * @Describe: 住院打印
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/10/27 19:43
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
@HsafRestPath("/service/inpt/inptprint")
@Service("inptPrintService_provider")
public class InptPrintServiceImpl extends HsafService implements InptPrintService {

  @Resource
  private InptPrintBO inptPrintBO;


  /**
  * @Menthod queryInptCostList
  * @Desrciption 费用清单打印
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/10/27 19:43
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.doctor.dto.InptCostDTO>>
  **/
  @Override
  public WrapperResponse<Map> queryInptCostListPrint(Map<String, Object> map) {
    InptCostDTO inptCostDTO = MapUtils.get(map, "inptCostDTO");
    return WrapperResponse.success(inptPrintBO.queryInptCostListPrint(inptCostDTO));
  }

  //费用清单批量打印 author：luoyong date：2021-03-05
  @Override
  public WrapperResponse<Map> queryRegisteredPageBatch(Map<String, Object> map) {
    InptCostDTO inptCostDTO = MapUtils.get(map, "inptCostDTO");
    return WrapperResponse.success(inptPrintBO.queryRegisteredPageBatch(inptCostDTO));
  }

  /**
  * @Menthod getApplyDetailsListPrint
  * @Desrciption 领药申请打印
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/10/30 16:20
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
  **/
  @Override
  public WrapperResponse<Map> getApplyDetailsListPrint(Map map) {
    return WrapperResponse.success(inptPrintBO.getApplyDetailsListPrint(map));
  }

  /**
  * @Menthod updateAdvicePrint
  * @Desrciption 查询医嘱打印表数据
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/1/15 10:48
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.inptprint.dto.InptAdvicePrintDTO>>
  **/
  @Override
  public WrapperResponse<List<InptAdvicePrintDTO>> queryAdvicePrint(Map map) {
    InptAdvicePrintDTO inptAdvicePrintDTO = MapUtils.get(map, "inptAdvicePrintDTO");
    if("1".equals(inptAdvicePrintDTO.getFlag())) {
      // 直接查数据
      return WrapperResponse.success(inptPrintBO.queryAdvicePrintDTOByVisit(inptAdvicePrintDTO));
    }
    // 更新医嘱打印表在查询
    return WrapperResponse.success(inptPrintBO.updateAdvicePrint(inptAdvicePrintDTO));
  }

  /**
  * @Menthod updateAdvicePrintStatus
  * @Desrciption 医嘱续打回写
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/1/18 17:08
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> updateAdvicePrintStatus(Map map) {
    InptAdvicePrintDTO inptAdvicePrintDTO = MapUtils.get(map, "inptAdvicePrintDTO");
    return WrapperResponse.success(inptPrintBO.updateAdvicePrintStatus(inptAdvicePrintDTO));
  }

  @Override
  public WrapperResponse<Boolean> updateAdvicePrintResetStatus(Map map) {
    InptAdvicePrintDTO inptAdvicePrintDTO = MapUtils.get(map, "inptAdvicePrintDTO");
    return WrapperResponse.success(inptPrintBO.updateAdvicePrintResetStatus(inptAdvicePrintDTO));
  }

  /**
  * @Menthod updateResetPrint
  * @Desrciption 重置医嘱打印格式
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/4/15 16:13
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> updateResetPrint(Map map) {
    InptAdvicePrintDTO inptAdvicePrintDTO = MapUtils.get(map, "inptAdvicePrintDTO");
    return WrapperResponse.success(inptPrintBO.updateResetPrint(inptAdvicePrintDTO));
  }

  /**
  * @Menthod saveAdvicePrint
  * @Desrciption 保存医嘱打印格式
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/4/15 16:13
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> saveAdvicePrint(Map map) {
    InptAdvicePrintDTO inptAdvicePrintDTO = MapUtils.get(map, "inptAdvicePrintDTO");
    return WrapperResponse.success(inptPrintBO.saveAdvicePrint(inptAdvicePrintDTO));
  }
}
