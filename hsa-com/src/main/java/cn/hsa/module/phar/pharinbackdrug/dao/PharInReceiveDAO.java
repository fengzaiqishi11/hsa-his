package cn.hsa.module.phar.pharinbackdrug.dao;

import cn.hsa.module.phar.pharinbackdrug.dto.PharInReceiveDTO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInReceiveDetailDTO;

import java.util.List;


/**
 *@Package_name: cn.hsa.module.phar.pharinbackdrug.dao
 *@Class_name: PharInWaitReceiveDao
 *@Describe: 住院领药申请
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/8/21 16:40
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface PharInReceiveDAO {

    /**
    * @Method getPharInReceiveById
    * @Desrciption 单个查询
    * @param pharInReceiveDTO
    * @Author liuqi1
    * @Date   2020/8/21 16:55
    * @Return cn.hsa.module.phar.pharinbackdrug.dto.PharInReceiveDTO
    **/
    PharInReceiveDTO getPharInReceiveById(PharInReceiveDTO pharInReceiveDTO);

    /**
     * @Method queryPharInReceives
     * @Desrciption 批量查询
     * @param pharInReceiveDTO
     * @Author liuqi1
     * @Date   2020/8/25 15:52
     * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInReceiveDTO>
     **/
    List<PharInReceiveDTO> queryPharInReceives(PharInReceiveDTO pharInReceiveDTO);

   /**
   * @Method queryPharInReceivePage
   * @Desrciption 分页查询
   * @param pharInReceiveDTO
   * @Author liuqi1
   * @Date   2020/8/21 16:56
   * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInReceiveDTO>
   **/
    List<PharInReceiveDTO> queryPharInReceivePage(PharInReceiveDTO pharInReceiveDTO);

    List<PharInReceiveDTO> queryPharInReceiveList(PharInReceiveDTO pharInReceiveDTO);

    /**
    * @Method insertPharInReceive
    * @Desrciption 单个新增
    * @param pharInReceiveDTO
    * @Author liuqi1
    * @Date   2020/8/21 16:56
    * @Return int
    **/
    int insertPharInReceive(PharInReceiveDTO pharInReceiveDTO);

    /**
    * @Method insertPharInReceives
    * @Desrciption 批量新增
    * @param pharInReceiveDTOs
    * @Author liuqi1
    * @Date   2020/8/25 16:14
    * @Return int
    **/
    int insertPharInReceives(List<PharInReceiveDTO> pharInReceiveDTOs);

    /**
    * @Method updatePharInReceive
    * @Desrciption 单个修改
    * @param pharInReceiveDTO
    * @Author liuqi1
    * @Date   2020/8/21 16:56
    * @Return int
    **/
    int updatePharInReceive(PharInReceiveDTO pharInReceiveDTO);

    /**
     * @Method queryAll
     * @Desrciption 查询列表
       @params [pharInReceiveDTO]
     * @Author chenjun
     * @Date   2020/10/10 11:12
     * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInReceiveDTO>
    **/
    List<PharInReceiveDTO> queryAll(PharInReceiveDTO pharInReceiveDTO);

    void deletePharInReceive(PharInReceiveDTO pharInReceiveDTO);

    void deletePharInReceiveDetail(PharInReceiveDetailDTO pharInReceiveDetailDTO);

}