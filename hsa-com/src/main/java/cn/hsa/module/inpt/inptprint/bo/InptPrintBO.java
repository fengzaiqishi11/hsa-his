package cn.hsa.module.inpt.inptprint.bo;

import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.inptprint.dto.InptAdvicePrintDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt.inptprint.bo
 * @Class_name: inptPrintBO
 * @Describe: 住院打印
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/10/27 19:36
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface InptPrintBO {

  /**
  * @Menthod queryInptCostList
  * @Desrciption 费用清单打印
  *
  * @Param
  * [inptCostDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/10/27 19:37
  * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptCostDTO>
  **/
  Map queryInptCostListPrint(InptCostDTO inptCostDTO);

  //费用清单批量打印 author：luoyong date：2021-03-05
  Map queryRegisteredPageBatch(InptCostDTO inptCostDTO);

  /**
  * @Menthod getApplyDetailsListPrint
  * @Desrciption 科室领药申请打印
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/10/30 15:53
  * @Return java.util.Map
  **/
  Map getApplyDetailsListPrint(Map map);

  /**
  * @Menthod updateAdvicePrint
  * @Desrciption 查询医嘱打印表数据
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/1/15 10:49
  * @Return java.util.List<cn.hsa.module.inpt.inptprint.dto.InptAdvicePrintDTO>
  **/
  List<InptAdvicePrintDTO> updateAdvicePrint(InptAdvicePrintDTO inptAdvicePrintDTO);

  /**
  * @Menthod updateAdvicePrintStatus
  * @Desrciption 医嘱续打回写
  *
  * @Param
  * [inptAdvicePrintDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/1/18 17:08
  * @Return java.lang.Boolean
  **/
  Boolean updateAdvicePrintStatus(InptAdvicePrintDTO inptAdvicePrintDTO);

  /**
  * @Menthod updateAdvicePrintResetStatus
  * @Desrciption 重置续打打印格式
  *
  * @Param
  * [inptAdvicePrintDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/1/19 16:13
  * @Return java.lang.Boolean
  **/
  Boolean updateAdvicePrintResetStatus(InptAdvicePrintDTO inptAdvicePrintDTO);

  /**
  * @Menthod updateResetPrint
  * @Desrciption 重置打印格式
  *
  * @Param
  * [inptAdvicePrintDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/4/15 16:16
  * @Return java.lang.Boolean
  **/
  Boolean updateResetPrint(InptAdvicePrintDTO inptAdvicePrintDTO);

  /**
  * @Menthod saveAdvicePrint
  * @Desrciption 保存医嘱打印格式
  *
  * @Param
  * [inptAdvicePrintDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/4/15 16:16
  * @Return java.lang.Boolean
  **/
  Boolean saveAdvicePrint(InptAdvicePrintDTO inptAdvicePrintDTO);

  /**
  * @Menthod queryAdvicePrintDTOByVisit
  * @Desrciption 查询医嘱打印表信息
  *
  * @Param
  * [inptAdvicePrintDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/4/20 19:22
  * @Return java.util.List<cn.hsa.module.inpt.inptprint.dto.InptAdvicePrintDTO>
  **/
  List<InptAdvicePrintDTO> queryAdvicePrintDTOByVisit(InptAdvicePrintDTO inptAdvicePrintDTO);

  /**
   * @Menthod queryBabyInptCostListPrint
   * @Desrciption 合并结算费用清单合并打印
   * @Param  [inptCostDTO]
   * @Author liuliyun
   * @Date   2021/09/23 09:06
   * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptCostDTO>
   **/
  Map queryBabyInptCostListPrint(InptCostDTO inptCostDTO);
}
