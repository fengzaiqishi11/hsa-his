package cn.hsa.inpt.nurse.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.nurse.bo.InptAdviceFeeCheckBO;
import cn.hsa.module.inpt.nurse.service.InptAdviceFeeCheckService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 *@Package_name: hsa.inpt.nurse.service.impl
 *@Class_name: inptAdviceFeeCheckServiceImpl
 *@Describe: 医嘱费用核对Service接口层
 *@Author: LiaoJiGuang
 *@Eamil: jiguang.liao@powersi.com.cn
 *@Date: 2020/9/15 10:14
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/inpt/nurse")
@Service("inptAdviceFeeCheckService_provider")
public class InptAdviceFeeCheckServiceImpl extends HsafService implements InptAdviceFeeCheckService{

    @Resource
    InptAdviceFeeCheckBO inptAdviceFeeCheckBO;

    /**
    * @Method queryInptAdviceCheckPage
    * @Desrciption 医嘱核对分页查询
    * @param map
    * @Author LiaoJiGuang
    * @Date   2020/9/15 10:16
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @Override
    public WrapperResponse<PageDTO> queryInptAdviceCheckPage(Map<String, Object> map) {
        InptAdviceDTO inptAdviceDTO = MapUtils.get(map,"inptAdviceDTO");
        PageDTO pageDTO = inptAdviceFeeCheckBO.queryInptAdviceCheckPage(inptAdviceDTO);
        return WrapperResponse.success(pageDTO);
    }

    /**
     * @Method queryInptPatientPage
     * @Desrciption 医嘱核对 - 获取本科室住院在院人员的患者信息
     * @param map
     * @Author LiaoJiGuang
     * @Date   2020/9/15 14:14
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryInptPatientPage(Map<String, Object> map) {
        InptAdviceDTO inptAdviceDTO = MapUtils.get(map,"inptAdviceDTO");
        PageDTO pageDTO = inptAdviceFeeCheckBO.queryInptPatientPage(inptAdviceDTO);
        return WrapperResponse.success(pageDTO);
    }

    /**
     * @Method updateAdviceFeeCheck
     * @Desrciption 医嘱费用核对
     * @param map 住院费用实体DTO
     * @Author LiaoJiGuang
     * @Date   2020/9/17 11:23
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> updateAdviceFeeCheck(Map map) {
        return WrapperResponse.success(inptAdviceFeeCheckBO.updateAdviceFeeCheck(map));
    }

    /**
     * @Method queryInptAdviceFeeInfoPage
     * @Desrciption 医嘱费用分页查询
     * @param map
     * @Author LiaoJiGuang
     * @Date   2020/9/15 10:31
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryInptAdviceFeeInfoPage(Map map) {
        InptAdviceDTO inptAdviceDTO = MapUtils.get(map,"inptAdviceDTO");
        PageDTO pageDTO = inptAdviceFeeCheckBO.queryInptAdviceFeeInfoPage(inptAdviceDTO);
        return WrapperResponse.success(pageDTO);
    }
}
