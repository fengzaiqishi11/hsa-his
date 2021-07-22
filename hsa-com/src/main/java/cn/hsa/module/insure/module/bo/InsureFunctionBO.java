package cn.hsa.module.insure.module.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.insure.module.dto.InsureFunctionDTO;


/**
 * @Package_name: cn.hsa.module.insure.insurefunction.bo
 * @Class_name:: InsureFunctionBO
 * @Description: 医保配置
 * @Author: zhangxuan
 * @Date: 2020-11-09 14:32
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InsureFunctionBO {
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
     * @Return insureFunctionDTO
     **/
    PageDTO queryPage(InsureFunctionDTO insureFunctionDTO);
    /**
     * @Method save
     * @Desrciption
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-11-09 16:32
     * @Return insureFunctionDTO
     **/
    boolean save(InsureFunctionDTO insureFunctionDTO);
    /**
     * @Method delete
     * @Desrciption
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-11-09 16:32
     * @Return insureFunctionDTO
     **/
    boolean delete(InsureFunctionDTO insureFunctionDTO);
}
