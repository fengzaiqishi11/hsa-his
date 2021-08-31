package cn.hsa.module.inpt.inptprint.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.inptprint.dto.InptAdvicePrintDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt.inptprint.service
 * @Class_name: inptPrint
 * @Describe: 住院打印
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/10/27 19:32
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-inpt")
public interface InptPrintService {

  /**
  * @Menthod queryInptCostListPrint
  * @Desrciption 费用清单打印
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/10/27 19:37
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.doctor.dto.InptCostDTO>>
  **/
  @GetMapping("/service/inpt/inptprint/queryInptCostList")
  WrapperResponse<Map> queryInptCostListPrint(Map<String,Object> map);

  //费用清单批量打印 author：luoyong date：2021-03-05
  @GetMapping("/service/inpt/inptprint/queryRegisteredPageBatch")
  WrapperResponse<Map> queryRegisteredPageBatch(Map<String,Object> map);

  /**
  * @Menthod getApplyDetailsListPrint
  * @Desrciption 科室领药申请打印
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/10/30 15:50
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
  **/
  @GetMapping("/service/inpt/inptprint/getApplyDetailsListPrint")
  WrapperResponse<Map> getApplyDetailsListPrint(Map map);

  /**
  * @Menthod updateAdvicePrint
  * @Desrciption 更新医嘱打印表
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/1/15 10:15
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  WrapperResponse<List<InptAdvicePrintDTO>> queryAdvicePrint(Map map);

  /**
  * @Menthod updateAdvicePrintStatus
  * @Desrciption 医嘱打印回写状态
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/1/18 17:06
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  WrapperResponse<Boolean> updateAdvicePrintStatus(Map map);

  /**
  * @Menthod updateAdvicePrintResetStatus
  * @Desrciption 重置续打打印条件
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/1/19 16:12
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  WrapperResponse<Boolean> updateAdvicePrintResetStatus(Map map);

  /**
  * @Menthod updateResetPrint
  * @Desrciption 重置医嘱打印格式
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/4/15 16:12
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  WrapperResponse<Boolean> updateResetPrint(Map map);

  /**
  * @Menthod saveAdvicePrint
  * @Desrciption 保存医嘱打印格式
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/4/15 16:12
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  WrapperResponse<Boolean> saveAdvicePrint(Map map);
}
