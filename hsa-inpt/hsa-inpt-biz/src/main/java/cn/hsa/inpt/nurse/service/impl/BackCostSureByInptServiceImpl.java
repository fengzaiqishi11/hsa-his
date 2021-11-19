package cn.hsa.inpt.nurse.service.impl;


import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.nurse.bo.BackCostSureByInptBO;
import cn.hsa.module.inpt.nurse.service.BackCostSureByInptService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 *@Package_name: cn.hsa.inpt.nurse.service.impl
 *@Class_name: BackCostSureWithInptServiceImpl
 *@Describe: 住院退费
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/9/10 16:09
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/inpt/nurse")
@Service("backCostSureWithInptService_provider")
public class BackCostSureByInptServiceImpl extends HsafService implements BackCostSureByInptService {

    @Resource
    BackCostSureByInptBO backCostSureWithInptBO;

    /**
    * @Method queryBackCostSurePage
    * @Desrciption 退费确认分页查询
    * @param map
    * @Author liuqi1
    * @Date   2020/9/10 16:12
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<PageDTO> queryBackCostSurePage(Map<String, Object> map) {
        InptCostDTO inptCostDTO = MapUtils.get(map, "inptCostDTO");
        PageDTO pageDTO = backCostSureWithInptBO.queryBackCostSurePage(inptCostDTO);
        return WrapperResponse.success(pageDTO);
    }

    /**
    * @Method updateBackCostSure
    * @Desrciption 退费确认
    * @param map
    * @Author liuqi1
    * @Date   2020/9/10 16:13
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> updateBackCostSure(Map<String, Object> map) {
        InptCostDTO inptCostDTO = MapUtils.get(map, "inptCostDTO");
        Boolean isSuccess = backCostSureWithInptBO.updateBackCostSure(inptCostDTO);
        return WrapperResponse.success(isSuccess);
    }

    /**
     * 查询手术补记账全部类型的病人补记账记录
     *
     * @param map  查询参数
     *           必传参数： hospCode 医院代码
     *           就诊号码： visitId
     *           费用来源方式代码（FYLYFS） sourceCode
     *           '费用来源ID' sourceId
     *           来源科室   sourceDeptId
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/5/31 20:00
     */
    @Override
    public WrapperResponse<PageDTO> queryOutpatientSurgeryCostPage(Map<String,Object> map) {
        InptCostDTO inptCostDTO = MapUtils.get(map,"inptCostDTO");

        return WrapperResponse.success(backCostSureWithInptBO.queryOutpatientSurgeryCostPage(inptCostDTO));
    }


    /**
     * @Method updateCancelBackCost
     * @Desrciption 取消退费
     * @param map
     * @Author liuliyun
     * @Date   2021/11/10 20:14
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> updateCancelBackCost(Map<String, Object> map) {
        InptCostDTO inptCostDTO = MapUtils.get(map, "inptCostDTO");
        Boolean isSuccess = backCostSureWithInptBO.updateCancelBackCost(inptCostDTO);
        return WrapperResponse.success(isSuccess);
    }

}
