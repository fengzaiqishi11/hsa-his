package cn.hsa.inpt.doctor.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.bo.InptCostBO;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.service.InptCostService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *@Package_name: cn.hsa.inpt.doctor.service.impl
 *@Class_name: InptCostServiceImpl
 *@Describe:
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/9/15 9:59
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/inpt/doctor")
@Service("inptCostService_provider")
public class InptCostServiceImpl extends HsafService implements InptCostService {

    @Resource
    InptCostBO inptCostBO;

    /**
    * @Method updateInptCostBatchWithBackDrug
    * @Desrciption 住院退药批量更新
    * @param map
    * @Author liuqi1
    * @Date   2020/9/15 10:04
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> updateInptCostBatchWithBackDrug(Map<String,Object> map) {
        InptCostDTO inptCostDTO = MapUtils.get(map, "inptCostDTO");
        boolean isSuccess = inptCostBO.updateInptCostBatchWithBackDrug(inptCostDTO);
        return WrapperResponse.success(isSuccess);
    }

    /**
    * @Method insertInptCost
    * @Param [map]
    * @description
    * @author marong
    * @date 2020/9/19 13:59
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    * @throws
    */
    @Override
    public WrapperResponse<Boolean> insertInptCost(Map map) {
        List<InptCostDTO> inptCostDTOList = MapUtils.get(map, "inptCostDTOList");
        boolean isSuccess = inptCostBO.insertInptCost(inptCostDTOList);
        return WrapperResponse.success(isSuccess);
    }

    @Override
    public WrapperResponse<Boolean> updateInptCostList(Map map) {
        List<InptCostDTO> inptCostDTOList = MapUtils.get(map, "inptCostDTOList");
        boolean isSuccess = inptCostBO.updateInptCostList(inptCostDTOList);
        return WrapperResponse.success(isSuccess);
    }
}
