package cn.hsa.module.insure.module.dao;

import cn.hsa.module.insure.module.dto.InsureFunctionDTO;
import cn.hsa.module.insure.module.entity.InsureFunctionDO;

import java.util.List;

public interface InsureFunctionDAO {
    /**
     * @Method getById
     * @Desrciption  根据主键查询医院医保信息
     * @Param
     * map[id,hosp_code]
     * @Author zhangxuan
     * @Date   2020-11-09 15:52
     * @Return map
     **/
    InsureFunctionDTO getById(InsureFunctionDTO insureFunctionDTO);
    /**
     * @Method queryPage
     * @Desrciption
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-11-09 16:32
     * @Return insureFunctionnDTO
     **/
    List<InsureFunctionDTO> queryPage(InsureFunctionDTO insureFunctionDTO);

    /**
     * @Method insert
     * @Desrciption
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-11-09 16:32
     * @Return insureFunctionnDTO
     **/
    int insert(InsureFunctionDTO insureFunctionDTO);

    /**
     * @Method update
     * @Desrciption
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-11-09 16:32
     * @Return insureFunctionnDTO
     **/

    int update(InsureFunctionDTO insureFunctionDTO);

    /**
     * @Method delete
     * @Desrciption
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-11-09 16:32
     * @Return insureFunctionnDTO
     **/

    int deleteByIds(InsureFunctionDTO insureFunctionDTO);

    /**
     * @Method queryCodeIsExist
     * @Desrciption
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-11-09 16:32
     * @Return insureFunctionnDTO
     **/
    int queryNameIsExist(InsureFunctionDTO insureFunctionDTO);

    /**
     * @Menthod queryFunctionAll
     * @Desrciption 根据查询条件查询功能号
     * @param insureFunctionDO 查询条件
     * @Author Ou·Mr
     * @Date 2020/11/10 11:15 
     * @Return java.util.List<cn.hsa.module.insure.insureIndividualBasic.entity.InsureFunctionDO>
     */
    List<InsureFunctionDO> queryFunctionAll(InsureFunctionDO insureFunctionDO);
}
