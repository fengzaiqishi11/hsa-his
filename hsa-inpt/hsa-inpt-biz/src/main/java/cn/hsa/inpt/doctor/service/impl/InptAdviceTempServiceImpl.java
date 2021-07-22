package cn.hsa.inpt.doctor.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.bo.InptAdviceTempBO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDetailTempDTO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceTempDTO;
import cn.hsa.module.inpt.doctor.service.InptAdviceTempService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *@Package_name: cn.hsa.doctor.advice.bo.impl
 *@Class_name: InptAdviceTempServiceImpl
 *@Describe: 医嘱模板service
 *@Author: zengfeng
 *@Eamil: zengfeng@powersi.com.cn
 *@Date: 2020-10-20 19:46:53
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/inpt/inptAdviceTemp")
@Service("inptAdviceTempService_provider")
public class InptAdviceTempServiceImpl extends HsafService implements InptAdviceTempService {

    @Resource
    InptAdviceTempBO inptAdviceTempBO;

    /**
     * @Method queryInptAdviceTemp
     * @Desrciption 医嘱模板分页查询
     * @param map: inptAdviceTempDTO
     * @Author zengfeng
     * @Date   2020/10/20 19:46:53
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public WrapperResponse<PageDTO> queryInptAdviceTempPage(Map<String, Object> map) {
        InptAdviceTempDTO inptAdviceTempDTO = MapUtils.get(map, "inptAdviceTempDTO");
        PageDTO pageDTO = inptAdviceTempBO.queryInptAdviceTempPage(inptAdviceTempDTO);
        return WrapperResponse.success(pageDTO);
    }

    /**
     * @Menthod queryInptAdviceTemp
     * @Desrciption  查询模板信息
     * @param map ： outptPrescribeTempDTO 医嘱模板
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<OutptPrescribeTempDTO>
     **/
    @Override
    public WrapperResponse<List<InptAdviceTempDTO>> queryInptAdviceTemp(Map map) {
        InptAdviceTempDTO inptAdviceTempDTO = MapUtils.get(map, "inptAdviceTempDTO");
        return WrapperResponse.success(inptAdviceTempBO.queryInptAdviceTemp(inptAdviceTempDTO));
    }

    /**
     * @Menthod queryInptAdviceDetailTemp
     * @Desrciption  查询模板明细信息
     * @param map ： outptPrescribeTempDTO 医嘱模板
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<OutptPrescribeTempDTO>
     **/
    @Override
    public WrapperResponse<List<InptAdviceDetailTempDTO>> queryInptAdviceDetailTemp(Map map) {
        InptAdviceTempDTO inptAdviceTempDTO = MapUtils.get(map, "inptAdviceTempDTO");
        return WrapperResponse.success(inptAdviceTempBO.queryInptAdviceDetailTemp(inptAdviceTempDTO));
    }

    /**
     * @Method saveAdciceTemp
     * @Desrciption 保存医嘱模板
     * @param map：inptAdviceTempDTO
     * @Author zengfeng
     * @Date   2020/10/20 19:46:53
     * @Return java.lang.Boolean
     **/
    @Override
    public WrapperResponse<Boolean> saveAdciceTemp(Map map) {
        InptAdviceTempDTO inptAdviceTempDTO = MapUtils.get(map, "inptAdviceTempDTO");
        return WrapperResponse.success(inptAdviceTempBO.saveAdciceTemp(inptAdviceTempDTO));
    }

    /**
     * @Method updateAdviceTemp
     * @Desrciption 审核、删除医嘱模板
     * @param map：inptAdviceTempDTO
     * @Author zengfeng
     * @Date   2020/10/20 19:46:53
     * @Return java.lang.Boolean
     **/
    @Override
    public WrapperResponse<Boolean> updateAdviceTemp(Map map) {
        InptAdviceTempDTO inptAdviceTempDTO = MapUtils.get(map, "inptAdviceTempDTO");
        return WrapperResponse.success(inptAdviceTempBO.updateAdviceTemp(inptAdviceTempDTO));
    }
}
