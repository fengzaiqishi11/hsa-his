package cn.hsa.module.inpt.doctor.bo;

import cn.hsa.module.inpt.doctor.dto.InptCostDTO;

import java.util.List;

/**
 *@Package_name: cn.hsa.module.inpt.doctor.bo
 *@Class_name: InptCostBO
 *@Describe: 住院费用
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/9/15 9:51
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InptCostBO {

    /**
    * @Method updateInptCostBatchWithBackDrug
    * @Desrciption 住院退药批量更新
    * @param inptCostDTO
    * @Author liuqi1
    * @Date   2020/9/15 9:51
    * @Return boolean
    **/
    boolean updateInptCostBatchWithBackDrug(InptCostDTO inptCostDTO);

    /**
    * @Method insertInptCost
    * @Param [inptCostDTOList]
    * @description    新增住院费用
    * @author marong
    * @date 2020/9/19 14:00
    * @return boolean
    * @throws
    */
    boolean insertInptCost(List<InptCostDTO> inptCostDTOList);

    /**
    * @Method updateInptCostList
    * @Param [inptCostDTOList]
    * @description    住院费用修改
    * @author marong
    * @date 2020/9/21 14:24
    * @return boolean
    * @throws
    */
    boolean updateInptCostList(List<InptCostDTO> inptCostDTOList);
}
