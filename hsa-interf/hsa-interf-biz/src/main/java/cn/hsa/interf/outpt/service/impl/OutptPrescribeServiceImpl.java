package cn.hsa.interf.outpt.service.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bd.service.BaseDiseaseService;
import cn.hsa.module.base.profileFile.service.BaseProfileFileService;
import cn.hsa.module.center.outptprofilefile.service.OutptProfileFileService;
import cn.hsa.module.interf.outpt.bo.OutptPrescribeBO;
import cn.hsa.module.interf.outpt.service.OutptPrescribeService;
import cn.hsa.module.oper.operInforecord.service.OperInfoRecordService;
import cn.hsa.module.outpt.classes.dto.OutptClassesDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.interf.outpt.service.impl
 * @Class_name:: OutptPrescribeServiceImpl
 * @Description: 门诊处方接口BO层实现类
 * @Author: zengfeng
 * @Date: 2021-05-10 10:31
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/interf/outptPrescribe")
@Slf4j
@Service("outptPrescribeService_provider")
public class OutptPrescribeServiceImpl extends HsafBO implements OutptPrescribeService {

    @Resource
    OutptPrescribeBO outptPrescribeBO;

    /**
     * 云净接口
     * @param map
     * @return
     */
    @Override
    public WrapperResponse<List<Map<String, Object>>> hisInferface(Map map) throws Exception {
        return WrapperResponse.success(outptPrescribeBO.updateHisInferface(map));
    }

}
