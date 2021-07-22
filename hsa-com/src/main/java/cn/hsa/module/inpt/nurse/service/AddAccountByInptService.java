package cn.hsa.module.inpt.nurse.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 *@Package_name: cn.hsa.module.inpt.nurse.service
 *@Class_name: AddAccountByInptService
 *@Describe:
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/9/3 11:17
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-inpt")
public interface AddAccountByInptService {


    /**
    * @Method queryPatientInfoPage
    * @Desrciption 分页查询患者信息
    * @param map
    * @Author liuqi1
    * @Date   2020/9/4 10:37
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>>
    **/
    @GetMapping("/service/inpt/nurse/queryPatientInfoPage")
    WrapperResponse<PageDTO> queryPatientInfoPage(Map<String,Object> map);


    /**
     * @Method queryPatientInfoPage
     * @Desrciption 分页查询患者信息
     * @param map
     * @Author xingyu.xie
     * @Date   2020/9/4 10:37
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>>
     **/
    @GetMapping("/service/inpt/nurse/queryPatientInfoPageByMoney")
    WrapperResponse<PageDTO> queryPatientInfoPageByMoney(Map<String,Object> map);

    /**
    * @Method saveAddAccountByInpt
    * @Desrciption 住院补记账保存
    * @param map
    * @Author liuqi1
    * @Date   2020/9/3 11:23
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @GetMapping("/service/inpt/nurse/saveAddAccountByInpt")
    WrapperResponse<Boolean> saveAddAccountByInpt(Map<String,Object> map);

    WrapperResponse<Boolean> saveAddSurgicalAccounting(Map<String, Object> map);

    /**
    * @Method queryBackCostWithAddAccountPage
    * @Desrciption 补记账管理退费查询
    * @param map
    * @Author liuqi1
    * @Date   2020/9/4 14:45
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    WrapperResponse<PageDTO> queryBackCostWithAddAccountPage(Map<String,Object> map);
    /**
     * @Method queryBackCostWithAddAccountPage
     * @Desrciption 根据visitId and soureTypeCode 查询这个病人录入的长期费用补记账
     * @param map
     * @Author pengbo
     * @Date   2021/5/24 14:45
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    WrapperResponse<PageDTO> queryLongCostByVistIdAndSoureTypeCode(Map<String, Object> map);
    /**
     * @Method queryBackCostWithAddAccountPage
     * @Desrciption 补记账长期费用管理保存
     * @param map
     * @Author pengbo
     * @Date   2021/5/24 14:45
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    WrapperResponse<Boolean> saveAccountRepairLong(Map<String, Object> map);

    /**
     * @Method queryBabyPatientInfoPage
     * @Desrciption 分页查询患者(含婴儿)信息
     * @param map
     * @Author liuliyun
     * @Date   2021/05/25 19:17
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>>
     **/
    @GetMapping("/service/inpt/nurse/queryBabyPatientInfoPage")
    WrapperResponse<PageDTO> queryBabyPatientInfoPage(Map<String,Object> map);

    WrapperResponse<Boolean> updateAccountRepairLong(Map<String, Object> map);
}
