package cn.hsa.module.inpt.inptprint.dao;

import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.fees.dto.InptSettleDTO;
import cn.hsa.module.inpt.inptprint.dto.InptAdvicePrintDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.inpt.inptprint.dao
 * @Class_name: inptPrintDAO
 * @Describe: 住院打印
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/10/27 19:35
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface InptPrintDAO {

  /**
  * @Menthod queryInptCostListPrint
  * @Desrciption 费用清单打印
  *
  * @Param
  * [inptCostDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/10/27 19:37
  * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptCostDTO>
  **/
  List<InptCostDTO> queryInptCostListPrint(InptCostDTO inptCostDTO);
  //费用清单批量打印 author：luoyong date：2021-03-05
  List<InptCostDTO> queryInptCostListPrintBatch(InptCostDTO inptCostDTO);

  /**
  * @Menthod queryInptItemCostListPrint
  * @Desrciption 项目汇总打印
  *
  * @Param
  * [inptCostDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/12/21 19:11
  * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptCostDTO>
  **/
  List<InptCostDTO> queryInptItemCostListPrint(InptCostDTO inptCostDTO);
  //费用清单批量打印 author：luoyong date：2021-03-05
  List<InptCostDTO> queryInptItemCostListPrintBatch(InptCostDTO inptCostDTO);

  /**
  * @Menthod queryAdviceByVistiId
  * @Desrciption 根据就诊id查询医嘱信息
  *
  * @Param
  * [inptAdvicePrintDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/1/15 11:16
  * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptAdviceDTO>
  **/
  List<InptAdvicePrintDTO> queryAdviceByVistiId(InptAdvicePrintDTO inptAdvicePrintDTO);

  /**
  * @Menthod queryAdvicePrintByVisitId
  * @Desrciption 根据就诊id查询医嘱打印信息，按序号排序
  *
  * @Param
  * [inptAdvicePrintDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/1/15 11:16
  * @Return java.util.List<cn.hsa.module.inpt.inptprint.dto.InptAdvicePrintDTO>
  **/
  List<InptAdvicePrintDTO> queryAdvicePrintByVisitId(InptAdvicePrintDTO inptAdvicePrintDTO);

  /**
  * @Menthod queryAdvicePrintByVisitId
  * @Desrciption 新增医嘱打印信息
  *
  * @Param
  * [inptAdvicePrintDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/1/15 11:18
  * @Return java.util.List<cn.hsa.module.inpt.inptprint.dto.InptAdvicePrintDTO>
  **/
  int insertAdvicePrint(@Param("list") List<InptAdvicePrintDTO> inptAdvicePrintDTOS);

  /**
  * @Menthod updateAdvicePrint
  * @Desrciption 批量更新医嘱打印信息
  *
  * @Param
  * [list]
  *
  * @Author jiahong.yang
  * @Date   2021/1/15 11:16
  * @Return int
  **/
  int updateAdvicePrint(@Param("list") List<InptAdvicePrintDTO> inptAdvicePrintDTOS);

  /**
  * @Menthod updateAdvicePrintStatus
  * @Desrciption 批量修改状态
  *
  * @Param
  * [inptAdvicePrintDTOS]
  *
  * @Author jiahong.yang
  * @Date   2021/1/18 17:26
  * @Return java.lang.Boolean
  **/
  int updateAdvicePrintStatus(@Param("list") List<InptAdvicePrintDTO> inptAdvicePrintDTOS);

  /**
  * @Menthod updateAdvicePrintResetStatus
  * @Desrciption 重置打印格式
  *
  * @Param
  * [inptAdvicePrintDTOS]
  *
  * @Author jiahong.yang
  * @Date   2021/1/19 16:15
  * @Return java.lang.Boolean
  **/
  int updateAdvicePrintResetStatus(InptAdvicePrintDTO inptAdvicePrintDTOS);

  /**
  * @Menthod deleteAdvicePrintByVisit
  * @Desrciption 通过就诊id删除医嘱打印信息
  *
  * @Param
  * [inptAdvicePrintDTOS]
  *
  * @Author jiahong.yang
  * @Date   2021/4/15 16:21
  * @Return int
  **/
  int deleteAdvicePrintByVisit(InptAdvicePrintDTO inptAdvicePrintDTOS);

  /**
  * @Menthod deleteAdvicePrintByVisit
  * @Desrciption 根据医嘱id删除
  *
  * @Param
  * [inptAdvicePrintDTOS]
  *
  * @Author jiahong.yang
  * @Date   2021/4/22 10:56
  * @Return int
  **/
  int deleteAdvicePrintByIaid(InptAdvicePrintDTO inptAdvicePrintDTOS);

  /**
  * @Menthod queryAdvicePrintByIds
  * @Desrciption 根据id查询,按传入的id顺序来查，不按序号排序
  *
  * @Param
  * [inptAdvicePrintDTOS]
  *
  * @Author jiahong.yang
  * @Date   2021/4/15 16:39
  * @Return java.util.List<cn.hsa.module.inpt.inptprint.dto.InptAdvicePrintDTO>
  **/
  List<InptAdvicePrintDTO> queryAdvicePrintByIds(InptAdvicePrintDTO inptAdvicePrintDTOS);

  /**
  * @Menthod updateAdvicePrintIsVlidStatus
  * @Desrciption 批量修改医嘱打印表状态
  *
  * @Param
  * [inptAdvicePrintDTOS]
  *
  * @Author jiahong.yang
  * @Date   2021/4/15 16:49
  * @Return int
  **/
  int updateAdvicePrintIsVlidStatus(InptAdvicePrintDTO inptAdvicePrintDTOS);

  /**
  * @Menthod queryMaxSeqNoByVisit
  * @Desrciption 根据就诊id查询最大
  *
  * @Param
  * [inptAdvicePrintDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/4/25 11:03
  * @Return int
  **/
  int queryMaxSeqNoByVisit(InptAdvicePrintDTO inptAdvicePrintDTO);

  List<InptCostDTO> queryInptCostListXmhzmx(InptCostDTO inptCostDTO);

  List<InptSettleDTO> queryInptSettleListPrint(InptCostDTO inptCostDTO);
}
