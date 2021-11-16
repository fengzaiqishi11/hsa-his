package cn.hsa.module.inpt.nurse.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;

/**
 *@Package_name: cn.hsa.module.inpt.nurse.bo
 *@Class_name: BackCostSureWithInptBO
 *@Describe: 住院退费确认
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/9/10 15:22
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface BackCostSureByInptBO {

    /**
    * @Method queryBackCostSurePage
    * @Desrciption 退费确认分页查询
    * @param inptCostDTO
    * @Author liuqi1
    * @Date   2020/9/10 15:26
    * @Return java.lang.Boolean
    **/
    PageDTO queryBackCostSurePage(InptCostDTO inptCostDTO);

    /**
    * @Method updateBackCostSure
    * @Desrciption 退费确认
    * @param inptCostDTO
    * @Author liuqi1
    * @Date   2020/9/10 15:31
    * @Return java.lang.Boolean
    **/
    Boolean updateBackCostSure(InptCostDTO inptCostDTO);

    /**
       * 查询手术补记账全部类型的病人补记账记录
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/31 20:00
    **/
    PageDTO queryOutpatientSurgeryCostPage(InptCostDTO inptCostDTO);

    /**
     * @Method updateCancelBackCost
     * @Desrciption 取消退费
     * @param inptCostDTO
     * @Author liuliyun
     * @Date   2021/11/10 20:15
     * @Return java.lang.Boolean
     **/
    Boolean updateCancelBackCost(InptCostDTO inptCostDTO);

}
