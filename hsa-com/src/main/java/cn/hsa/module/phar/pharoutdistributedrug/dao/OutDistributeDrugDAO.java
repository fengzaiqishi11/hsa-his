package cn.hsa.module.phar.pharoutdistributedrug.dao;

import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsDTO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInDistributeAllDetailDTO;
import cn.hsa.module.phar.pharoutdistribute.dto.PharOutDistributeAllDetailDTO;
import cn.hsa.module.phar.pharoutdistribute.entity.PharOutDistributeDO;
import cn.hsa.module.phar.pharoutdistribute.entity.PharOutDistributeDetailDO;
import cn.hsa.module.phar.pharoutdistributedrug.dto.PharOutReceiveDTO;
import cn.hsa.module.phar.pharoutdistributedrug.dto.PharOutReceiveDetailDTO;
import cn.hsa.module.phar.pharoutdistributedrug.entity.PharOutReceiveDO;
import cn.hsa.module.phar.pharoutdistributedrug.entity.PharOutReceiveDetailDO;
import cn.hsa.module.stro.stock.dto.StroStockDetailDTO;

import java.util.List;
import java.util.Map;

/**
* @Package_name: cn.hsa.module.phar.pharoutdistributedrug.dao
* @class_name: OutDistributeDrugDAO
* @Description: '0004'DAO
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/9/2 15:46
* @Company: 创智和宇
**/
public interface OutDistributeDrugDAO {

    /**
    * @Method: getOutRecivePage
    * @Description: 获取待领列表
    * @Param: pharOutReceiveDTO
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/9/2 16:26
    * @Return: List<PharOutReceiveDTO>
    **/
    List<PharOutReceiveDTO> getOutRecivePage(PharOutReceiveDTO pharOutReceiveDTO);

    /**
    * @Menthod queryOutDistributedByIds
    * @Desrciption  根据ids查询所有的配药单数据
     * @param pharOutReceiveDetailDTO
    * @Author xingyu.xie
    * @Date   2020/10/28 19:58
    * @Return java.util.List<cn.hsa.module.phar.pharoutdistributedrug.dto.PharOutReceiveDetailDTO>
    **/
    List<PharOutReceiveDetailDTO> queryOutDistributedByIds(PharOutReceiveDetailDTO pharOutReceiveDetailDTO);

    /**
     * @Method: getOutDistributePage
     * @Description: 获取发药列表
     * @Param: [pharOutReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/12 16:29
     * @Return: cn.hsa.base.PageDTO
     **/
    List<PharOutReceiveDTO> getOutDistributePage(PharOutReceiveDTO pharOutReceiveDTO);

    /**
     * @Method: getOutReciveDetailPage
     * @Description: 获取待领明细列表
     * @Param: pharOutReceiveDTO
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/2 16:26
     * @Return: List<PharOutReceiveDTO>
     **/
    List<PharOutReceiveDetailDTO> getOutReciveDetailPage(PharOutReceiveDetailDTO pharOutReceiveDetailDTO);

    /**
     * @Method: getOutDistributeDetailPage
     * @Description: 获取发药明细列表
     * @Param: [pharOutReceiveDetailDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/12 16:42
     * @Return: cn.hsa.base.PageDTO
     **/
    List<PharOutReceiveDetailDTO> getOutDistributeDetailPage(PharOutReceiveDetailDTO pharOutReceiveDetailDTO);

    /**
     * @Method: dispense
     * @Description: 门诊配药
     * @Param: [pharOutReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/3 10:59
     * @Return: java.lang.Boolean
     **/
    int updateReceive(PharOutReceiveDTO pharOutReceiveDTO);

    /**
     * @Method: updateOutEnabelDispense
     * @Description: 取消配药--更新状态和配药人信息
     * @Param: [pharOutReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/12 14:54
     * @Return: java.lang.Boolean
     **/
    int updateOutEnabelReceive(PharOutReceiveDTO pharOutReceiveDTO);

    /**
     * @Method: dispense
     * @Description: 根据id获取门诊领药申请对象
     * @Param: [pharOutReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/3 10:59
     * @Return: java.lang.Boolean
     **/
    PharOutReceiveDO getOutReceiveById(PharOutReceiveDTO pharOutReceiveDTO);

    /**
    * @Method:
    * @Description:
    * @Param: 门诊发药主要入库
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/9/3 15:05
    * @Return:
    **/
    int insertOutDistribute(PharOutDistributeDO pharOutDistributeDO);

    /**
     * @Method:
     * @Description:
     * @Param: 根据领药申请ID获取领药申请明细
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/3 15:05
     * @Return:
     **/
    List<PharOutReceiveDetailDO> getOutReceiveDetailsById(PharOutReceiveDTO pharOutReceiveDTO);

    /**
     * @Method:
     * @Description:
     * @Param: 门诊发药明细表入库
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/3 15:05
     * @Return:
     **/
    int insertOutDistributeDetail(List<PharOutDistributeDetailDO> list);

    /**
     * @Method:
     * @Description:
     * @Param: 删除领药明细表记录
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/3 15:05
     * @Return:
     **/
    int deleteOutReceiveDetail(PharOutReceiveDTO pharOutReceiveDTO);

    /**
     * @Method:
     * @Description:
     * @Param: 删除领药表记录
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/3 15:05
     * @Return:
     **/
    int deleteOutReceive(PharOutReceiveDTO pharOutReceiveDTO);

    /**
     * @Method: getOrderList
     * @Description: 获取所有处方列表
     * @Param: [pharOutReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/7 14:18
     * @Return: java.util.List<java.util.Map>
     **/
    List<Map> getOrderList(PharOutReceiveDTO pharOutReceiveDTO);

    /**
    * @Method: updateOutptCostIsDist
    * @Description: 更新费用表是否已发药标识
    * @Param:
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/12/19 18:17
    * @Return:
    **/
    int updateOutptCostIsDist(PharOutReceiveDO receiveDTO);

    int updateOutptCostIsDistAndDistId(List<PharOutDistributeAllDetailDTO> pharOutDistributeBatchDetailDTOs);

    //根据配置的输液参数值和医院编码查询出输液的用法
    String findInfusionParam(String code, String hospCode);

    /**
    * @Menthod insertPharOutDistributeBatchDetail
    * @Desrciption 插入同批次发药汇总信息
    *
    * @Param
    * [pharOutDistributeBatchDetailDTOs]
    *
    * @Author jiahong.yang
    * @Date   2021/5/20 16:18
    * @Return int
    **/
    int insertPharOutDistributeBatchDetail(List<PharOutDistributeAllDetailDTO> pharOutDistributeBatchDetailDTOs);

    /**
    * @Menthod queryPharOutDistributeAllDetailDTO
    * @Desrciption 查询门诊发药信息
    *
    * @Param
    * [pharOutDistributeAllDetailDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/5/27 11:48
    * @Return java.util.List<cn.hsa.module.phar.pharoutdistribute.dto.PharOutDistributeAllDetailDTO>
    **/
    List<PharOutDistributeAllDetailDTO> queryPharOutDistributeAllDetailDTO(PharOutDistributeAllDetailDTO pharOutDistributeAllDetailDTO);


    /**
     * @Menthod queryPharOutDistributeAllDetailDTO
     * @Desrciption 查询门诊发药信息
     *
     * @Param
     * [pharOutDistributeAllDetailDTO]
     *
     * @Author jiahong.yang
     * @Date   2021/5/27 11:48
     * @Return java.util.List<cn.hsa.module.phar.pharoutdistribute.dto.PharOutDistributeAllDetailDTO>
     **/
    List<PharInDistributeAllDetailDTO> queryPharInDistributeAllDetailDTO(PharInDistributeAllDetailDTO pharInDistributeAllDetailDTO);


    /**
    * @Menthod getPrescribePrint
    * @Desrciption 获取处方单打印
    *
    * @Param
    * [pharOutReceiveDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/6/15 15:58
    * @Return java.util.List<cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDTO>
    **/
    List<OutptPrescribeDTO> getPrescribePrint(PharOutReceiveDTO pharOutReceiveDTO);

    /**
    * @Menthod getPrescribeDetailPrint
    * @Desrciption 处方明细
    *
    * @Param
    * [pharOutReceiveDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/6/15 16:18
    * @Return java.util.List<cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsDTO>
    **/
    List<Map> getPrescribeDetailPrint(OutptPrescribeDTO outptPrescribeDTO);

    Map<String, Object> queryVistitInfo(OutptPrescribeDTO visitParam);
    /**
     * @Author gory
     * @Description 查询过期的药品明细
     * @Date 2022/9/9 11:48
     * @Param [map]
     **/
    List<StroStockDetailDTO> queryNumShortage(Map map);
}
