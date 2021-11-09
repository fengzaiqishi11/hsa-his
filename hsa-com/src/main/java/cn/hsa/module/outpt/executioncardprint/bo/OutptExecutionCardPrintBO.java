package cn.hsa.module.outpt.executioncardprint.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.outpt.infusionRegister.dto.OutptInfusionRegisterDTO;
import cn.hsa.module.phar.pharoutdistributedrug.dto.PharOutReceiveDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.outpt.executioncardprint.bo
 * @Class_name:: OutptExecutionCardPrintBO
 * @Description: 执行卡打印业务逻辑接口
 * @Author: zhangxuan
 * @Date: 2020-08-18 10:33 
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface OutptExecutionCardPrintBO {
    /**
     * @Method queryPage
     * @Desrciption  根据条件分页查询执行卡打印数据
     * @Param
     * outptExecutionCardPrintDTO
     * @Author zhangxuan
     * @Date   2020-08-18 10:35
     * @Return outptExecutionCardPrintDTO
    **/
    PageDTO queryPage(OutptInfusionRegisterDTO outptInfusionRegisterDTO);
    /**
     * @Method update
     * @Desrciption  打印后更改打印状态
     * @Param
     * outptExecutionCardPrintDTO
     * @Author zhangxuan
     * @Date   2020-08-26 14:00
     * @Return
    **/
    Boolean update(OutptInfusionRegisterDTO outptInfusionRegisterDTO);

    List<OutptInfusionRegisterDTO> queryInfusionRegisterList(PharOutReceiveDTO pharOutReceiveDTO);
}
