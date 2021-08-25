package cn.hsa.module.outpt.infusionRegister.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.outpt.infusionRegister.dto.OutptInfusionRegisterDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;

import java.util.Map;


/**
 * @Package_name: cn.hsa.module.outpt.infusionRegister.bo
 * @Class_name:: OutptInfusionRegisterBO
 * @Description: 门诊输液登记BO接口层
 * @Author: luoyong
 * @Email: luoyong@powersi.com.cn
 * @Date: 2020/8/12 9:13
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface OutptInfusionRegisterBO {

    /**
     * @Menthod: queryPage()
     * @Desrciption: 根据条件分页《未登记》查询患者列表
     * @Param: outptInfusionRegisterDTO 门诊输液登记传输对象
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/12 10:48
     * @Return: cn.hsa.base.PageDTO
     **/
    PageDTO queryPage(OutptInfusionRegisterDTO outptInfusionRegisterDTO);

    /**
     * @Menthod: queryPageByInfu()
     * @Desrciption: 根据条件分页查询《已登记》患者列表
     * @Param: outptInfusionRegisterDTO 门诊输液登记传输对象
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/12 10:48
     * @Return: cn.hsa.base.PageDTO
     **/
    PageDTO queryPageByInfu(OutptInfusionRegisterDTO outptInfusionRegisterDTO);

    /**
     * @Menthod: save()
     * @Desrciption: 输液登记
     * @Param: map (outptVisitDTO-门诊就诊DTO, crteId, crteName)
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/12 10:48
     * @Return: Boolean
     **/
    Boolean save(Map map);

    /**
     * @Menthod: getByVisitId()
     * @Desrciption: 根据患者visitId分页查询出对应的处方明细列表
     * @Param: outptVisitDTO-门诊就诊DTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/12 16:15
     * @Return: cn.hsa.base.PageDTO
     **/
    PageDTO getByVisitId(OutptVisitDTO outptVisitDTO);

    /**
     * @Menthod: queryCost()
     * @Desrciption: 根据患者visitId分页查询出对应的费用列表
     * @Param: outptVisitDTO-门诊就诊DTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/12 16:15
     * @Return: cn.hsa.sys.PageDTO
     **/
    PageDTO queryCost(OutptVisitDTO outptVisitDTO);
}
