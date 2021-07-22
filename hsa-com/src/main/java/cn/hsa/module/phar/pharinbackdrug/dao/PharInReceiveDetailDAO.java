package cn.hsa.module.phar.pharinbackdrug.dao;

import cn.hsa.module.phar.pharinbackdrug.dto.PharInReceiveDetailDTO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 *@Package_name: cn.hsa.module.phar.pharinbackdrug.dao
 *@Class_name: PharInWaitReceiveDao
 *@Describe: 住院领药申请明细
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/8/21 16:40
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface PharInReceiveDetailDAO {

    /**
    * @Method queryPharInReceiveDetailDaoById
    * @Desrciption 单个查询
    * @param pharInReceiveDetailDTO
    * @Author liuqi1
    * @Date   2020/8/21 16:58
    * @Return cn.hsa.module.phar.pharinbackdrug.dto.PharInReceiveDetailDTO
    **/
    PharInReceiveDetailDTO getPharInReceiveDetailById(PharInReceiveDetailDTO pharInReceiveDetailDTO);

    /**
    * @Method getPharInReceiveDetails
    * @Desrciption 根据待领id批量查询
    * @param hharInWaitReceiveDTOs
    * @Author liuqi1
    * @Date   2020/8/25 15:58
    * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInReceiveDetailDTO>
    **/
    List<PharInReceiveDetailDTO> queryPharInReceiveDetails(List<PharInWaitReceiveDTO> hharInWaitReceiveDTOs);

    /**
    * @Method queryPharInReceiveDetailPage
    * @Desrciption 分页查询
    * @param pharInReceiveDetailDTO
    * @Author liuqi1
    * @Date   2020/8/21 16:59
    * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInReceiveDetailDTO>
    **/
    List<PharInReceiveDetailDTO> queryPharInReceiveDetailPage(PharInReceiveDetailDTO pharInReceiveDetailDTO);

    /**
    * @Method insertPharInReceiveDetail
    * @Desrciption 单个新增
    * @param pharInReceiveDetailDTO
    * @Author liuqi1
    * @Date   2020/8/21 16:59
    * @Return int
    **/
    int insertPharInReceiveDetail(PharInReceiveDetailDTO pharInReceiveDetailDTO);

    /**
    * @Method insertPharInReceiveDetails
    * @Desrciption 批量新增
    * @param pharInReceiveDetailDTOs
    * @Author liuqi1
    * @Date   2020/8/25 16:20
    * @Return int
    **/
    int insertPharInReceiveDetails(@Param("list") List<PharInReceiveDetailDTO> pharInReceiveDetailDTOs);

    /**
    * @Method updatePharInReceiveDetail
    * @Desrciption 单个修改
    * @param pharInReceiveDetailDTO
    * @Author liuqi1
    * @Date   2020/8/21 16:59
    * @Return int
    **/
    int updatePharInReceiveDetail(PharInReceiveDetailDTO pharInReceiveDetailDTO);

}