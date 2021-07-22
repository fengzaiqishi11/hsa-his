package cn.hsa.module.phar.pharoutbackdrug.dao;

import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.phar.pharoutdistribute.dto.PharOutDistributeAllDetailDTO;
import cn.hsa.module.phar.pharoutdistribute.dto.PharOutDistributeDTO;
import cn.hsa.module.phar.pharoutdistribute.dto.PharOutDistributeDetailDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.store.instore.bo
 * @Class_name: StroOutinBo
 * @Describe:
 * @Author: xingyu.xie
 * @Email: xingyu.xie@powersi.com
 * @Date: 2020/7/22 8:46
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface OutBackDrugDAO {



  /**
  * @Menthod getOutBackDrugPeopleById
  * @Desrciption  通过id来查询发药主表记录
   * @param map 医院编码，id
  * @Author xingyu.xie
  * @Date   2020/10/15 10:54
  * @Return cn.hsa.module.phar.pharoutdistribute.dto.PharOutDistributeDTO
  **/
  PharOutDistributeDTO getOutBackDrugPeopleById(Map map);


  /**
  * @Menthod updatePharOutDistributeS
  * @Desrciption  修改发药主表记录
   * @param map
  * @Author xingyu.xie
  * @Date   2020/10/23 11:33
  * @Return boolean
  **/
  boolean updatePharOutDistributeS(Map map);

  /**
  * @Menthod queryOutBackDrugPeoplePage
  * @Desrciption  通过姓名和身份证以及手机号查询门诊发药单的人
   * @param pharOutDistributeDTO  门诊发药表数据传输对象
  * @Author xingyu.xie
  * @Date   2020/7/29 17:19
  * @Return java.util.List<cn.hsa.module.phar.pharwaitreceive.dto.PharWaitReceiveDTO>
  **/
  List<PharOutDistributeDTO> queryOutBackDrugPeoplePage(PharOutDistributeDTO pharOutDistributeDTO);

  /**
   * @Menthod queryOutBackDrugDetailPage
   * @Desrciption  通过发药id来查询所有的发药单详情
   * @param pharOutDistributeDTO  门诊发药表数据传输对象
   * @Author xingyu.xie
   * @Date   2020/7/29 17:19
   * @Return java.util.List<cn.hsa.module.phar.pharwaitreceive.dto.PharWaitReceiveDTO>
   **/
  List<PharOutDistributeDetailDTO> queryOutBackDrugDetailPage(PharOutDistributeDTO pharOutDistributeDTO);

  /**
   * @Description: 查询患者费用明细，其中可退数量为门诊医生申请退费数量
   * @Param:
   * @Author: guanhongqiang
   * @Email: hongqiang.guan@powersi.com.cn
   * @Date 2021/3/10 19:14
   * @Return
   */
  List<PharOutDistributeDetailDTO> queryOutBackDrugDetailRefundApplyPage(PharOutDistributeDTO pharOutDistributeDTO);
  /**
   * @Menthod getOutBackDrugDetailById
   * @Desrciption  通过发药明细id来查询发药明细记录
   * @param pharOutDistributeDetailDTO  门诊发药明细表数据传输对象
   * @Author xingyu.xie
   * @Date   2020/7/29 17:19
   * @Return java.util.List<cn.hsa.module.phar.pharwaitreceive.dto.PharWaitReceiveDTO>
   **/
  PharOutDistributeDetailDTO getOutBackDrugDetailById(PharOutDistributeDetailDTO pharOutDistributeDetailDTO);

  /**
  * @Menthod updateBackNumAndTotalBackNum
  * @Desrciption  门诊退药更新退药数量和累计退药数量
   * @param pharOutDistributeDetailDTOList 发药详情list列表
  * @Author xingyu.xie
  * @Date   2020/7/30 10:48
  * @Return int
  **/
  int updateBackNumAndTotalBackNum(@Param("list") List<PharOutDistributeDetailDTO> pharOutDistributeDetailDTOList);

  /**
  * @Menthod insertDistribute
  * @Desrciption  新增单条负的退药记录数据
   * @param pharOutDistributeDTO 门诊发药表数据传输对象
  * @Author xingyu.xie
  * @Date   2020/8/26 19:54
  * @Return int
  **/
  int insertDistribute(PharOutDistributeDTO pharOutDistributeDTO);

  /**
  * @Menthod insertDistributeDetailList
  * @Desrciption 新增多条负的退药详细记录
   * @param pharOutDistributeDetailDTOList 门诊发药表数据传输对象List
  * @Author xingyu.xie
  * @Date   2020/8/26 19:58
  * @Return int
  **/
  int insertDistributeDetailList(@Param("list") List<PharOutDistributeDetailDTO> pharOutDistributeDetailDTOList);

  /**
   * @Description: 根据主键查询费用的回退数量
   * @Param:
   * @Author: guanhongqiang
   * @Email: hongqiang.guan@powersi.com.cn
   * @Date 2021/4/27 15:05
   * @Return
   */
  OutptCostDTO getOutptCostBackNumById(OutptCostDTO outptCostDTO);

  /**
  * @Menthod queryOutBackDrugDetail
  * @Desrciption 根据批次发药汇总查询发药明细数据
  *
  * @Param
  * [pharOutDistributeBatchDetailDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/5/21 14:36
  * @Return java.util.List<cn.hsa.module.phar.pharoutdistribute.dto.PharOutDistributeDetailDTO>
  **/
  List<PharOutDistributeDetailDTO> queryOutBackDrugDetail(PharOutDistributeAllDetailDTO pharOutDistributeBatchDetailDTO);

  /**
  * @Menthod updateBackNumAndTotalBackNum
  * @Desrciption 门诊退药更新批次汇总表退药数量和累计退药数量
  *
  * @Param
  * [pharOutDistributeDetailDTOList]
  *
  * @Author jiahong.yang
  * @Date   2021/5/21 16:26
  * @Return int
  **/
  int updateBatchBackNumAndTotalBackNum(@Param("list") List<PharOutDistributeAllDetailDTO> pharOutDistributeBatchDetailDTOS);

  int updateCostBcakNumByDsitirId(@Param("list") List<OutptCostDTO> outptCostDTOList);

  /**
   * @Menthod updateDistributeAllDetailId
   * @Desrciption 回写发药明细负数据合计id
   *
   * @Param
   * [pharOutDistributeBatchDetailDTOs]
   *
   * @Author jiahong.yang
   * @Date   2021/5/24 20:27
   * @Return int
   **/
  int updateDistributeAllDetailId(@Param("list")  List<PharOutDistributeDetailDTO> pharOutDistributeDetailDTOS);


}
