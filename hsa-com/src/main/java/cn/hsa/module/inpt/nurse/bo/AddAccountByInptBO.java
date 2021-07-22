package cn.hsa.module.inpt.nurse.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;

import java.util.List;
import java.util.Map;

/**
 *@Package_name: cn.hsa.module.inpt.nurse.bo
 *@Class_name: AddAccountByInptBO
 *@Describe:
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/9/3 11:17
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface AddAccountByInptBO {

    /**
    * @Method queryPatientInfoPage
    * @Desrciption 分页查询患者信息
    * @param inptVisitDTO
    * @Author liuqi1
    * @Date   2020/9/4 10:33
    * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
    **/
    PageDTO queryPatientInfoPage(InptVisitDTO inptVisitDTO);

    /**
    * @Menthod queryPatientInfoPageByMoney
    * @Desrciption  分页根据金额报警线查询患者信息
     * @param inptVisitDTO
    * @Author xingyu.xie
    * @Date   2020/11/7 11:37
    * @Return cn.hsa.base.PageDTO
    **/
    PageDTO queryPatientInfoPageByMoney(InptVisitDTO inptVisitDTO);

    /**
    * @Method saveAddAccountByInpt
    * @Desrciption 住院补记账保存
    * @param map
    * @Author liuqi1
    * @Date   2020/9/3 11:22
    * @Return java.lang.Boolean
    **/
    Boolean saveAddAccountByInpt(Map<String,Object> map);

    Boolean saveAddAccountByOutpt(Map<String,Object> map);
    /**
    * @Method inptPreferentialRecalculate
    * @Desrciption 住院优惠重算
    * @param inptCostDTOs
    * @Author liuqi1
    * @Date   2020/9/30 16:26
    * @Return void
    **/
    List<InptCostDTO> inptPreferentialRecalculate(List<InptCostDTO> inptCostDTOs);

    /**
    * @Method queryBackCostWithAddAccountPage
    * @Desrciption 补记账退费分页查询
    * @param inptCostDTO
    * @Author liuqi1
    * @Date   2020/9/4 14:40
    * @Return cn.hsa.base.PageDTO
    **/
    PageDTO queryBackCostWithAddAccountPage(InptCostDTO inptCostDTO);

    /**
     * @Method queryBackCostWithAddAccountPage
     * @Desrciption 根据visitId and soureTypeCode 查询这个病人录入的长期费用补记账
     * @param inptCostDTO
     * @Author pengbo
     * @Date   2021/5/24 14:45
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    PageDTO queryLongCostByVistIdAndSoureTypeCode(InptCostDTO inptCostDTO);

    /**
     * @param map
     * @Method queryBackCostWithAddAccountPage
     * @Desrciption 补记账长期费用管理保存
     * @Author pengbo
     * @Date 2021/5/24 14:45
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    WrapperResponse<Boolean> saveAccountRepairLong(Map<String, Object> map);

    /**
     * @Method queryBabyPatientInfoPage
     * @Desrciption 分页查询患者信息
     * @param inptVisitDTO
     * @Author liuliyun
     * @Date   2021/05/25 19:19
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
     **/
    PageDTO queryBabyPatientInfoPage(InptVisitDTO inptVisitDTO);

    WrapperResponse<Boolean> updateAccountRepairLong(Map<String, Object> map);
}
