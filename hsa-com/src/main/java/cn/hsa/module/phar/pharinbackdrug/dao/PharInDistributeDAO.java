package cn.hsa.module.phar.pharinbackdrug.dao;

import cn.hsa.module.phar.pharinbackdrug.dto.PharInDistributeDTO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInDistributeDetailDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 *@Package_name: cn.hsa.module.phar.pharinbackdrug.dao
 *@Class_name: PharInWaitReceiveDao
 *@Describe: 住院发药
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/8/21 16:40
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface PharInDistributeDAO {

    /**
    * @Method getPharInDistributeById
    * @Desrciption 单个查询
    * @param pharInDistributeDTO
    * @Author liuqi1
    * @Date   2020/8/21 16:53
    * @Return cn.hsa.module.phar.pharinbackdrug.dto.PharInDistributeDTO
    **/
    PharInDistributeDTO getPharInDistributeById(PharInDistributeDTO pharInDistributeDTO);

    /**
    * @Method queryPharInDistributes
    * @Desrciption 批量查询：根据发药明细查询出发药单据
    * @param pharInDistributeDetailDTOs
    * @Author liuqi1
    * @Date   2020/8/25 17:22
    * @Return cn.hsa.module.phar.pharinbackdrug.dto.PharInDistributeDTO
    **/
    List<PharInDistributeDTO> queryPharInDistributes(@Param("list") List<PharInDistributeDetailDTO> pharInDistributeDetailDTOs);

    /**
    * @Method queryPharInDistributePage
    * @Desrciption 分页查询
    * @param pharInDistributeDTO
    * @Author liuqi1
    * @Date   2020/8/21 16:53
    * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInDistributeDTO>
    **/
    List<PharInDistributeDTO> queryPharInDistributePage(PharInDistributeDTO pharInDistributeDTO);

    /**
    * @Method insertPharInDistributeDTO
    * @Desrciption 单个新增
    * @param pharInDistributeDTO
    * @Author liuqi1
    * @Date   2020/8/21 16:53
    * @Return int
    **/
    int insertPharInDistribute(PharInDistributeDTO pharInDistributeDTO);

    /**
    * @Method insertPharInDistributes
    * @Desrciption 批量新增
    * @param pharInDistributeDTOs
    * @Author liuqi1
    * @Date   2020/8/25 17:31
    * @Return int
    **/
    int insertPharInDistributes(@Param("list") List<PharInDistributeDTO> pharInDistributeDTOs);

    /**
    * @Method updatePharInDistributeDTO
    * @Desrciption 单个修改
    * @param pharInDistributeDTO
    * @Author liuqi1
    * @Date   2020/8/21 16:53
    * @Return int
    **/
    int updatePharInDistribute(PharInDistributeDTO pharInDistributeDTO);


}