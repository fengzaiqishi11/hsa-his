package cn.hsa.outpt.prescribe.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.prescribe.bo.OutptPrescribeTempBO;
import cn.hsa.module.outpt.prescribe.dto.OutptPrescribeTempDTO;
import cn.hsa.module.outpt.prescribe.service.OutptPrescribeTempService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *@Package_name: cn.hsa.outpt.prescribe.service.impl
 *@Class_name: OutptPrescribeTempServiceImpl
 *@Describe:
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/8/19 15:20
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/outpt/prescribe")
@Service("outptPrescribeTempService_provider")
public class OutptPrescribeTempServiceImpl extends HsafService implements OutptPrescribeTempService {

    @Resource
    OutptPrescribeTempBO outptPrescribeTempBO;

    /**
    * @Method queryOutptPrescribeTempDTOPage
    * @Desrciption 分页查询
    * @param map
    * @Author liuqi1
    * @Date   2020/8/19 15:21
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @Override
    public WrapperResponse<PageDTO> queryOutptPrescribeTempDTOPage(Map<String, Object> map) {
        OutptPrescribeTempDTO outptPrescribeTempDTO = MapUtils.get(map, "outptPrescribeTempDTO");
        PageDTO pageDTO = outptPrescribeTempBO.queryOutptPrescribeTempDTOPage(outptPrescribeTempDTO);
        return WrapperResponse.success(pageDTO);
    }

    /**
     * @Menthod queryOutptPrescribeTempDTO
     * @Desrciption  查询模板信息
     * @param map ： outptPrescribeTempDTO 处方模板
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<OutptPrescribeTempDTO>
     **/
    @Override
    public WrapperResponse<List<OutptPrescribeTempDTO>> queryOutptPrescribeTempDTO(Map map) {
        OutptPrescribeTempDTO outptPrescribeTempDTO = MapUtils.get(map, "outptPrescribeTempDTO");
        return WrapperResponse.success(outptPrescribeTempBO.queryOutptPrescribeTempDTO(outptPrescribeTempDTO));
    }

    /**
     * @Menthod queryOutptPrescribeTempDTO
     * @Desrciption  查询模板明细信息
     * @param map
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<OutptPrescribeTempDetailDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryOutptPrescribeTempDetails(Map map) {
        OutptPrescribeTempDTO outptPrescribeTempDTO = MapUtils.get(map, "outptPrescribeTempDTO");
        return WrapperResponse.success(outptPrescribeTempBO.queryOutptPrescribeTempDetails(outptPrescribeTempDTO));
    }


    /*
     * @Menthod savePrescribeTemp
     * @Desrciption  保存处方模板信息
     * @param outptPrescribeDTO 处方明细
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return boolean
     */
    @Override
    public WrapperResponse<Boolean> savePrescribeTemp(Map map) {
        OutptPrescribeTempDTO outptPrescribeTempDTO = MapUtils.get(map, "outptPrescribeTempDTO");
        return WrapperResponse.success(outptPrescribeTempBO.savePrescribeTemp(outptPrescribeTempDTO));
    }

    /*
     * @Menthod updateOutptPrescribeTempDTO
     * @Desrciption  修改模板信息
     * @param
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return boolean
     */
    @Override
    public WrapperResponse<Boolean> updateOutptPrescribeTempDTO(Map map) {
        OutptPrescribeTempDTO outptPrescribeTempDTO = MapUtils.get(map, "outptPrescribeTempDTO");
        return WrapperResponse.success(outptPrescribeTempBO.updateOutptPrescribeTempDTO(outptPrescribeTempDTO));
    }

    /**
     * @Method updateTempAudit
     * @Desrciption 模板取消审核
     * @param map
     * @Author liuliyun
     * @Date   2021/11/10 17:16
     * @Return boolean
     **/
    @Override
    public WrapperResponse<Boolean> cancelAuditPrescribeTemp(Map map) {
        OutptPrescribeTempDTO outptPrescribeTempDTO = MapUtils.get(map, "outptPrescribeTempDTO");
        return WrapperResponse.success(outptPrescribeTempBO.updateTempAudit(outptPrescribeTempDTO));
    }
}
