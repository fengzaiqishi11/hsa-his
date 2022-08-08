package cn.hsa.module.phar.pharindistributedrug.dao;

import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDetailDTO;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInDistributeAllDetailDTO;
import cn.hsa.module.phar.pharinbackdrug.entity.PharInDistributeDO;
import cn.hsa.module.phar.pharinbackdrug.entity.PharInDistributeDetailDO;
import cn.hsa.module.phar.pharinbackdrug.entity.PharInReceiveDO;
import cn.hsa.module.phar.pharindistributedrug.dto.PharInReceiveDTO;
import cn.hsa.module.phar.pharindistributedrug.dto.PharInReceiveDetailDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @Package_name: cn.hsa.module.phar.pharoutdistributedrug.dao
* @class_name: OutDistributeDrugDAO
* @Description: 住院发药DAO
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/9/2 15:46
* @Company: 创智和宇
**/
public interface InDistributeDrugDAO {

    /**
     * @Method: getInRecivePage
     * @Description: 住院发药--申请记录
     * @Param: [pharInReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/10 14:28
     * @Return: cn.hsa.base.PageDTO
     **/
    List<PharInReceiveDTO> getInRecivePage(PharInReceiveDTO pharInReceiveDTO);

    /**
    * @Method: getSendInRecivePage
    * @Description: 住院发药记录
    * @Param:
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/10/26 14:46
    * @Return:
    **/
    List<PharInReceiveDTO> getSendInRecivePage(PharInReceiveDTO pharInReceiveDTO);

    /**
     * @Method: getInReviceDetailPage
     * @Description: 住院发药--取药明细单
     * @Param: [pharInReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/10 14:54
     * @Return: cn.hsa.base.PageDTO
     **/
    List<PharInReceiveDetailDTO> getInReviceDetailPage(PharInReceiveDetailDTO pharInReceiveDetailDTO);

    /**
    * @Menthod getInReviceDetail
    * @Desrciption  住院配药的明细单打印
     * @param pharInReceiveDetailDTO
    * @Author xingyu.xie
    * @Date   2020/12/25 9:41
    * @Return java.util.List<cn.hsa.module.phar.pharindistributedrug.dto.PharInReceiveDetailDTO>
    **/
    List<PharInReceiveDetailDTO> getInReviceDetail(PharInReceiveDetailDTO pharInReceiveDetailDTO);
    /**
     * @Method: getInSumReviceDetailPage
     * @Description: 住院发药--取药合计单
     * @Param: [pharInReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/10 14:54
     * @Return: cn.hsa.base.PageDTO
     **/
    List<PharInReceiveDetailDTO> getInSumReviceDetailPage(PharInReceiveDetailDTO pharInReceiveDetailDTO);

    /**
    * @Method: getInReceiveDetailsById
    * @Description: 根据领药申请ID获取明细数据
    * @Param:
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/9/10 16:15
    * @Return: List<PharInReceiveDetailDTO>
    **/
    List<PharInReceiveDetailDTO> getInReceiveDetailsById(PharInReceiveDTO pharInReceiveDTO);

    /**
    * @Method: updateInReceive
    * @Description: 更新住院领药申请表 配药信息
    * @Param:
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/9/10 16:23
    * @Return: int
    **/
    int updateInReceive(PharInReceiveDTO pharInReceiveDTO);

    /**
    * @Method: updateInReceiveAssign
    * @Description: 更新配药人信息
    * @Param:
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/10/26 15:08
    * @Return:
    **/
    int updateInReceiveAssign(PharInReceiveDTO pharInReceiveDTO);

    /**
    * @Method: updateInWaitReceive
    * @Description: 更新住院待领表 配药信息
    * @Param:
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/9/10 16:23
    * @Return: int
    **/
    int updateInWaitReceive(List<PharInReceiveDetailDTO> list);

    /**
    * @Method:
    * @Description: 更新住院待领表、配药信息
    * @Param:
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/10/26 15:10
    * @Return:
    **/
    int updateInWaitReceiveAssign(List<PharInReceiveDetailDTO> list);

    int updateInWaitReceiveDistribute(List<PharInReceiveDetailDTO> list);

    /**
    * @Method: getInReceiveById
    * @Description: 根据主键获取住院领药申请数据
    * @Param:
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/9/10 17:08
    * @Return: PharInReceiveDO
    **/
    PharInReceiveDO getInReceiveById(PharInReceiveDTO pharInReceiveDTO);

    /**
    * @Method: insertInDistribute
    * @Description: 插入住院发药表
    * @Param:
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/9/11 8:53
    * @Return: int
    **/
    int insertInDistribute(PharInDistributeDO inDistributeDO);

    /**
     * @Method: insertInDistribute
     * @Description: 插入住院发药明细表
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/11 8:53
     * @Return: int
     **/
    int insertInDistributeDetail(List<PharInDistributeDetailDO> list);

    /**
    * @Menthod insertInDistributeAllDetail
    * @Desrciption 住院发药批次汇总表入库
    *
    * @Param
    * [list]
    *
    * @Author jiahong.yang
    * @Date   2021/5/26 10:02
    * @Return int
    **/
    int insertInDistributeAllDetail(List<PharInDistributeAllDetailDTO> list);

    /**
    * @Method: updateInptCostIsDist
    * @Description: 费用表回写是否已发药
    * @Param:
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/12/19 18:27
    * @Return:
    **/
    int updateInptCostIsDist(@Param("costDTOList") List<InptCostDTO> costDTOList, @Param("hospCode") String hospCode);

    /**
    * @Method: getInptCostsByReceiveDetail
    * @Description: 根据id修改费用表是否发药标识
    * @Param:
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/12/19 19:52
    * @Return:
    **/
    List<InptCostDTO> getInptCostsByReceiveDetail(@Param("pharInReceiveDetailDOList") List<PharInReceiveDetailDTO> pharInReceiveDetailDOList
            , @Param("hospCode") String hospCode);

    /**
    * @Menthod queryVisitByReceiveOrder
    * @Desrciption 查询就诊id
    *
    * @Param
    * [pharInReceiveDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/4/22 19:40
    * @Return java.util.List<java.lang.String>
    **/
    List<String> queryVisitByReceiveOrder(PharInReceiveDTO pharInReceiveDTO);

    /**
    * @Menthod queryVisitByDistributeOrder
    * @Desrciption 根据发药id查询就诊id
    *
    * @Param
    * [pharInReceiveDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/4/23 15:56
    * @Return java.util.List<java.lang.String>
    **/
    List<String> queryVisitByDistributeOrder(PharInReceiveDTO pharInReceiveDTO);

    /**
    * @Menthod queryPatientInfoByVitsitId
    * @Desrciption 根据就诊id查询病人信息
    *
    * @Param
    * [inptVisitDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/4/22 19:41
    * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
    **/
    List<InptVisitDTO> queryPatientInfoByVitsitId(InptVisitDTO inptVisitDTO);

    /**
    * @Menthod queryDrugByOrderAndVisitId
    * @Desrciption 根据就诊id和配药id查询医嘱信息
    *
    * @Param
    * [pharInReceiveDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/4/23 16:05
    * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptAdviceDTO>
    **/
    List<InptAdviceDTO> queryDrugByOrderAndVisitId(PharInReceiveDTO pharInReceiveDTO);

    /**
    * @Menthod queryDrugByDistributeIdAndVisitId
    * @Desrciption 根据发药id和就诊id查询医嘱信息
    *
    * @Param
    * [pharInReceiveDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/4/23 16:06
    * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptAdviceDTO>
    **/
    List<InptAdviceDTO> queryDrugByDistributeIdAndVisitId(PharInReceiveDTO pharInReceiveDTO);

    /**
    * @Menthod uodateCostDistId
    * @Desrciption 回写发药id
    *
    * @Param
    * [pharInReceiveDetailDOList]
    *
    * @Author jiahong.yang
    * @Date   2021/5/28 8:59
    * @Return int
    **/
    int updateCostDistId(List<PharInReceiveDetailDTO> pharInReceiveDetailDOList);

    /**
    * @Menthod deletePharInReceiveDetailDTO
    * @Desrciption
    *
    * @Param
    * []
    *
    * @Author jiahong.yang
    * @Date   2021/7/13 11:24
    * @Return int
    **/
    int deletePharInReceiveDetailDTO(PharInReceiveDetailDTO pharInReceiveDetailDTO);

    /**
    * @Menthod deletePharInReceiveDetailDTO
    * @Desrciption 查询需要需要预配药的所有的配药明细
    *
    * @Param
    * [pharInReceiveDetailDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/7/13 14:52
    * @Return java.util.List<cn.hsa.module.phar.pharindistributedrug.dto.PharInReceiveDetailDTO>
    **/
    List<PharInReceiveDetailDTO> queryCanclePharInReceiveDetailDTO(PharInReceiveDetailDTO pharInReceiveDetailDTO);

    /**
    * @Menthod updateCostBackCodeStatus
    * @Desrciption 修改费用退药状态代码
    *
    * @Param
    * [inptCostDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/7/13 15:00
    * @Return int
    **/
    int updateCostBackCodeStatus(InptCostDTO inptCostDTO);

    /**
    * @Menthod querySurplusPharInReceiveDetailDTO
    * @Desrciption 查询剩余的配药明细
    *
    * @Param
    * [pharInReceiveDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/7/13 15:29
    * @Return java.util.List<cn.hsa.module.phar.pharindistributedrug.dto.PharInReceiveDetailDTO>
    **/
    List<PharInReceiveDetailDTO> querySurplusPharInReceiveDetailDTO(PharInReceiveDTO pharInReceiveDTO);

    /**
    * @Menthod deleteCanclePharInReceivelDTO
    * @Desrciption 删除配药主表根据id
    *
    * @Param
    * [pharInReceiveDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/7/13 15:26
    * @Return int
    **/
    int deleteCanclePharInReceivelDTO(PharInReceiveDTO pharInReceiveDTO);
    /**
     * @Meth: getOldCostIds
     * @Description: 根据费用id集合查询 出老费用id集合 如果back_code = 1 退药未退费 则筛选出来
     * @Param: [inptCostDTO]
     * @return: java.util.List<java.lang.String>
     * @Author: zhangguorui
     * @Date: 2021/10/18
     */
    List<String> getOldCostIds(InptCostDTO inptCostDTO);

    List<InptAdviceDTO> queryDMDrugByOrderAndVisitId(PharInReceiveDTO pharInReceiveDTO);
}
