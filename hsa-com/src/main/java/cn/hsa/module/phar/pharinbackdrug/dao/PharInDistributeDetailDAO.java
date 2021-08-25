package cn.hsa.module.phar.pharinbackdrug.dao;

import cn.hsa.module.phar.pharinbackdrug.dto.*;
import cn.hsa.module.phar.pharinbackdrug.entity.PharInWaitReceiveDO;
import cn.hsa.module.stro.stock.dto.StroStockDetailDTO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;


/**
 *@Package_name: cn.hsa.module.phar.pharinbackdrug.dao
 *@Class_name: PharInWaitReceiveDao
 *@Describe: 住院发药明细
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/8/21 16:40
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface PharInDistributeDetailDAO {

    /**
    * @Method getPharInDistributeDetailById
    * @Desrciption 单个查询
    * @param pharInDistributeDetailDTO
    * @Author liuqi1
    * @Date   2020/8/21 16:47
    * @Return cn.hsa.module.phar.pharinbackdrug.dto.PharInDistributeDetailDTO
    **/
    PharInDistributeDetailDTO getPharInDistributeDetailById(PharInDistributeDetailDTO pharInDistributeDetailDTO);

    /**
    * @Method queryPharInDistributeDetails
    * @Desrciption 批量查询
    * @Author liuqi1
    * @Date   2020/8/25 17:15
    * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInDistributeDetailDTO>
    **/
    List<PharInDistributeDetailDTO> queryPharInDistributeDetails(PharInWaitReceiveDTO pharInWaitReceiveDTO);

    /**
    * @Method queryPharInDistributeDetailPage
    * @Desrciption 分页查询
    * @param pharInDistributeDetailDTO
    * @Author liuqi1
    * @Date   2020/8/21 16:47
    * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInDistributeDetailDTO>
    **/
    List<PharInDistributeDetailDTO> queryPharInDistributeDetailPage(PharInDistributeDetailDTO pharInDistributeDetailDTO);

    /**
    * @Method insertPharInDistributeDetail
    * @Desrciption 单个新增
    * @param pharInDistributeDetailDTO
    * @Author liuqi1
    * @Date   2020/8/21 16:48
    * @Return int
    **/
    int insertPharInDistributeDetail(PharInDistributeDetailDTO pharInDistributeDetailDTO);

    /**
    * @Method insertPharInDistributeDetail
    * @Desrciption 批量新增
    * @param pharInDistributeDetailDTOs
    * @Author liuqi1
    * @Date   2020/8/25 17:27
    * @Return int
    **/
    int insertPharInDistributeDetails(@Param("list") List<PharInDistributeDetailDTO> pharInDistributeDetailDTOs);

    /**
    * @Menthod insertPharInDistribute
    * @Desrciption 新增发药表退药主表
    *
    * @Param
    * [pharInDistributeDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/4/14 15:30
    * @Return int
    **/
    int insertPharInDistribute(PharInDistributeDTO pharInDistributeDTO);

    /**
    * @Method updatePharInDistributeDetail
    * @Desrciption 单个修改
    * @param pharInDistributeDetailDTO
    * @Author liuqi1
    * @Date   2020/8/21 16:48
    * @Return int
    **/
    int updatePharInDistributeDetail(PharInDistributeDetailDTO pharInDistributeDetailDTO);

    /**
    * @Method queryStroStockDetailDTOs
    * @Desrciption 获得库存明细数据
    * @param pharInDistributeDetailDTOs
    * @Author liuqi1
    * @Date   2020/8/26 17:00
    * @Return java.util.List<cn.hsa.module.stro.stock.dto.StroStockDetailDTO>
    **/
    List<StroStockDetailDTO> queryStroStockDetailDTOs(@Param("list") List<PharInDistributeDetailDTO> pharInDistributeDetailDTOs);

    /**
    * @Menthod queryPharInDistributeDetailById
    * @Desrciption
    *
    * @Param
    * [pharInDistributeDetailDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/5/26 14:36
    * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInDistributeDetailDTO>
    **/
    BigDecimal queryPharInDistributeDetailById(PharInDistributeDetailDTO pharInDistributeDetailDTO);


    /**
    * @Menthod queryPharInDistributeAllDetailDTO
    * @Desrciption 查询发药批次汇总记录
    *
    * @Param
    * [pharInWaitReceiveDTOS]
    *
    * @Author jiahong.yang
    * @Date   2021/5/26 16:14
    * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInDistributeAllDetailDTO>
    **/
    List<PharInDistributeAllDetailDTO> queryPharInDistributeAllDetailDTO(PharInWaitReceiveDTO pharInWaitReceiveDTO);

}
