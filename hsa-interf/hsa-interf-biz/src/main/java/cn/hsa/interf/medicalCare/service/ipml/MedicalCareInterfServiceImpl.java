package cn.hsa.interf.medicalCare.service.ipml;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.medicalCare.bo.MedicalCareInterfBO;
import cn.hsa.module.interf.medicalCare.service.MedicalCareInterfService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.interf.medicalCare.service.ipml
 * @Class_name: MedicalCareInterfServiceImpl
 * @Describe: 医养转换his接口service实现类
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2022-02-28 11:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/

@Slf4j
@Service("/medicalCareInterfService_provider")
@HsafRestPath("/service/interf/medicalCare")
public class MedicalCareInterfServiceImpl extends HsafService implements MedicalCareInterfService {

    @Resource
    private MedicalCareInterfBO medicalCareInterfBO;

    /**
     * @Menthod: getVisitInfoRecord
     * @Desrciption: 获取医转养就诊信息
     * @Param: hospCode：医院编码，medical_info_id：就诊id
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2022-02-28 11:57
     * @Return:
     **/
    @Override
    public WrapperResponse<Map<String, Object>> getVisitInfoRecord(Map<String, Object> map) {
        return WrapperResponse.success(medicalCareInterfBO.getVisitInfoRecord(map));
    }
}
