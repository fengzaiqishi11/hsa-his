package cn.hsa.inpt.consultation.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.consultation.bo.InptConsultationApplyBO;
import cn.hsa.module.inpt.consultation.dto.InptConsultationApplyDTO;
import cn.hsa.module.inpt.consultation.service.InptConsultationApplyServcie;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.inpt.consultation.service.impl
 * @Class_name: InptConsultationApplyServcieImpl
 * @Describe: 会诊申请service实现类
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-11-04 20:23
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
@Service("inptConsultationService_provider")
@HsafRestPath("/service/inpt/consultationApply")
public class InptConsultationApplyServcieImpl extends HsafService implements InptConsultationApplyServcie {

    @Resource
    private InptConsultationApplyBO inptConsultationApplyBO;

    /**
     * @Menthod: saveConsultationApply
     * @Desrciption: 保存会诊申请记录
     * @Param: inptConsultationApplyDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-11-04 20:36
     * @Return:
     **/
    @Override
    public WrapperResponse<String> saveConsultationApply(Map map) {
        return WrapperResponse.success(inptConsultationApplyBO.saveConsultationApply(MapUtils.get(map, "inptConsultationApplyDTO")));
    }

    /**
     * @Menthod: queryConsultationApply
     * @Desrciption: 分页查询会诊记录
     * @Param: inptConsultationApplyDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-11-04 20:36
     * @Return:
     **/
    @Override
    public WrapperResponse<PageDTO> queryConsultationApply(Map map) {
        return WrapperResponse.success(inptConsultationApplyBO.queryConsultationApply(MapUtils.get(map, "inptConsultationApplyDTO")));
    }

    /**
     * @Menthod: getById
     * @Desrciption: 分页查询会诊记录
     * @Param: inptConsultationApplyDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-11-04 20:36
     * @Return:
     **/
    @Override
    public WrapperResponse<InptConsultationApplyDTO> getById(Map map) {
        return WrapperResponse.success(inptConsultationApplyBO.getById(MapUtils.get(map, "inptConsultationApplyDTO")));
    }

    /**
     * @Menthod: updateStatus
     * @Desrciption: 更改会诊状态
     * @Param: inptConsultationApplyDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-11-04 20:42
     * @Return:
     **/
    @Override
    public WrapperResponse<Boolean> updateStatus(Map map) {
        return WrapperResponse.success(inptConsultationApplyBO.updateStatus(MapUtils.get(map, "inptConsultationApplyDTO")));
    }
}
