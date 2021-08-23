package cn.hsa.insure.unifiedpay.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.inpt.bo.InsureNurseRecordBO;
import cn.hsa.module.insure.inpt.service.InsureNurseRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.unifiedpay.service.impl
 * @Class_name: InsureNurseRecordServiceImpl
 * @Describe: 统一支付平台-护理生命记录service实现类
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-08-21 17:33
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
@HsafRestPath("/service/insure/insureNurseRecord")
@Service("insureNurseRecordService_provider")
public class InsureNurseRecordServiceImpl extends HsafService implements InsureNurseRecordService {

    @Resource
    private InsureNurseRecordBO insureNurseRecordBO;

    /**
     * @Menthod: addInsureNurseRecord
     * @Desrciption: 统一支付平台-护理生命记录上传【4602】
     * @Param: visitId
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-08-22 08:55
     * @Return:
     **/
    @Override
    public WrapperResponse<Map<String, Object>> addInsureNurseRecord(Map<String, Object> map) {
        return WrapperResponse.success(insureNurseRecordBO.addInsureNurseRecord(map));
    }
}
