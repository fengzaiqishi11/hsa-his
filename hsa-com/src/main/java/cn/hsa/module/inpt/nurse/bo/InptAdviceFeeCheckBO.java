package cn.hsa.module.inpt.nurse.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;

import java.util.Map;

/**
 *@Package_name: cn.hsa.module.inpt.bo
 *@Class_name: inptAdviceFeeCheckBO
 *@Describe: 医嘱费用核对BO接口层
 *@Author: LiaoJiGuang
 *@Eamil: jiguang.liao@powersi.com
 *@Date: 2020/9/15 09:58
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InptAdviceFeeCheckBO {

    /**
    * @Method queryInptAdviceFeeCheckPage
    * @Desrciption 医嘱费用核对分页查询
    * @param inptAdviceDTO
    * @Author LiaoJiGuang
    * @Date   2020/9/15 09:59
    * @Return cn.hsa.base.PageDTO
    **/
    PageDTO queryInptAdviceCheckPage(InptAdviceDTO inptAdviceDTO);

    /**
     * @Method queryInptPatientPage
     * @Desrciption 医嘱核对 - 获取本科室住院在院人员的患者信息
     * @param inptAdviceDTO
     * @Author LiaoJiGuang
     * @Date   2020/9/15 14:14
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    PageDTO queryInptPatientPage(InptAdviceDTO inptAdviceDTO);

    /**
     * @Method updateAdviceFeeCheck
     * @Desrciption 医嘱费用核对
     * @param map 住院费用实体DTO
     * @Author LiaoJiGuang
     * @Date   2020/9/17 11:23
     * @Return Boolean
     **/
    Boolean updateAdviceFeeCheck(Map map);

    /**
     * @Method queryInptAdviceFeeInfoPage
     * @Desrciption 医嘱费用分页查询
     * @param inptAdviceDTO
     * @Author LiaoJiGuang
     * @Date   2020/9/15 10:31
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    PageDTO queryInptAdviceFeeInfoPage(InptAdviceDTO inptAdviceDTO);
}
