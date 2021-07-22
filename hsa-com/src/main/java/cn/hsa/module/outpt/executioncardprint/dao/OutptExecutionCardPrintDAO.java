package cn.hsa.module.outpt.executioncardprint.dao;

import cn.hsa.module.outpt.infusionRegister.dto.OutptInfusionRegisterDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.outpt.executioncardprint.dao
 * @Class_name:: OutptExecutionCardPrintDAO
 * @Description: 执行卡打印DAO层
 * @Author: zhangxuan
 * @Date: 2020-08-18 10:47
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface OutptExecutionCardPrintDAO {
    /**
     * @Method queryPage
     * @Desrciption 根据条件查询分页条件
     * @Param
     * outptExecutionCardPrintDTO
     * @Author zhangxuan
     * @Date   2020-08-18 10:51
     * @Return
    **/
    List<OutptInfusionRegisterDTO> queryByIds(OutptInfusionRegisterDTO outptInfusionRegisterDTO);

    /**
     * @Method queryPage
     * @Desrciption 根据条件查询分页条件
     * @Param
     * outptExecutionCardPrintDTO
     * @Author zhangxuan
     * @Date   2020-08-18 10:51
     * @Return
     **/
    List<OutptInfusionRegisterDTO> queryPage(OutptInfusionRegisterDTO outptInfusionRegisterDTO);

    /**
     * @Method update
     * @Desrciption
     * @Param
     * outptInfusionRegisterDTO
     * @Author zhangxuan
     * @Date   2020-08-18 13:54
     * @Return int
    **/
    Boolean update(OutptInfusionRegisterDTO outptInfusionRegisterDTO);

    Boolean updatePrintFlagByType(@Param("outptInfusionRegisterDTO") List<OutptInfusionRegisterDTO> outptInfusionRegisterDTO);
}
