package cn.hsa.inpt.nurse.service.impl;


import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.nurse.bo.BackCostByInputBO;
import cn.hsa.module.inpt.nurse.service.BackCostByInputService;
import cn.hsa.util.MapUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 *@Package_name: cn.hsa.inpt.nurse.service.impl
 *@Class_name: BackCostByInptServiceImpl
 *@Describe:
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/9/10 13:45
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/inpt/nurse")
@Service("backCostByInputService_provider")
public class BackCostByInptServiceImpl extends HsafService implements BackCostByInputService {

    @Resource
    BackCostByInputBO backCostByInputBO;

    /**
    * @Method queryBackCostInfoPage
    * @Desrciption 住院退费费用分页查询
    * @param map
    * @Author liuqi1
    * @Date   2020/9/10 13:46
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @Override
    public WrapperResponse<PageDTO> queryBackCostInfoPage(Map<String, Object> map) {
        InptCostDTO inptCostDTO = MapUtils.get(map, "inptCostDTO");
        PageHelper.startPage(inptCostDTO.getPageNo(),inptCostDTO.getPageSize());
        PageDTO pageDTO = backCostByInputBO.queryBackCostInfoPage(inptCostDTO);
        return WrapperResponse.success(pageDTO);
    }

    /**
     * @param map 参数
     * @Method queryBackCostInfoPage
     * @Desrciption 住院退费费用分页查询
     * @Author luonianxin
     * @Date 2021/06/02
     **/
    @Override
    public WrapperResponse<PageDTO> querySurgeryBackCostInfoPage(Map<String, Object> map) {
        PageDTO outptCostDTO = backCostByInputBO.querySurgeryBackCostInfoPage(map);
        return WrapperResponse.success(outptCostDTO);
    }

    /**
    * @Method saveBackCostWithInpt
    * @Desrciption 住院退费保存
    * @param map
    * @Author liuqi1
    * @Date   2020/9/10 14:48
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> saveBackCostWithInpt(Map<String, Object> map) {
        Boolean isSuccess = backCostByInputBO.saveBackCostWithInpt(map);
        return WrapperResponse.success(isSuccess);
    }
}
