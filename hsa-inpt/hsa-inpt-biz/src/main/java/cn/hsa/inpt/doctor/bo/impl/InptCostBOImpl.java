package cn.hsa.inpt.doctor.bo.impl;


import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.inpt.doctor.bo.InptCostBO;
import cn.hsa.module.inpt.doctor.dao.InptCostDAO;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 *@Package_name: cn.hsa.inpt.doctor.bo.impl
 *@Class_name: InptCostBOImpl
 *@Describe: 住院费用
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/9/15 9:59
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class InptCostBOImpl extends HsafBO implements InptCostBO {

    @Resource
    InptCostDAO inptCostDAO;

    /**
    * @Method updateInptCostBatchWithBackDrug
    * @Desrciption 住院退药批量更新
    * @param inptCostDTO
    * @Author liuqi1
    * @Date   2020/9/15 10:00
    * @Return boolean
    **/
    @Override
    public boolean updateInptCostBatchWithBackDrug(InptCostDTO inptCostDTO) {
        int count = inptCostDAO.updateInptCostBatchWithBackDrug(inptCostDTO);
        return count > 0;
    }

    /**
    * @Method insertInptCost
    * @Param [inptCostDTOList]
    * @description   新增住院费用
    * @author marong
    * @date 2020/9/21 14:24
    * @return boolean
    * @throws
    */
    @Override
    public boolean insertInptCost(List<InptCostDTO> inptCostDTOList) {
        int count = inptCostDAO.insertInptCostBatch(inptCostDTOList);
        return count > 0;
    }

    /**
    * @Method updateInptCostList
    * @Param [inptCostDTOList]
    * @description    住院费用修改
    * @author marong
    * @date 2020/9/21 14:24
    * @return boolean
    * @throws
    */
    @Override
    public boolean updateInptCostList(List<InptCostDTO> inptCostDTOList) {
        int count = inptCostDAO.updateInptCostList(inptCostDTOList);
        return count > 0;
    }
}
