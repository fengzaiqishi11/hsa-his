package cn.hsa.insure.unifiedpay.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.inptnursethird.dto.InptNurseThirdDTO;
import cn.hsa.module.inpt.inptnursethird.service.InptNurseThirdService;
import cn.hsa.module.inpt.inregister.service.InptVisitService;
import cn.hsa.module.insure.inpt.bo.InsureNurseRecordBO;
import cn.hsa.module.insure.inpt.service.InsureUnifiedPayInptService;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.unifiedpay.bo.impl
 * @Class_name: InsureNurseRecordBOImpl
 * @Describe: 统一支付平台-护理生命记录bo
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-08-22 08:47
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
@Component
public class InsureNurseRecordBOImpl extends HsafBO implements InsureNurseRecordBO {

    @Resource
    private InsureUnifiedPayInptService insureUnifiedPayInptService_consumer;

    @Resource
    private InptVisitService inptVisitService_consumer;

    @Resource
    private InptNurseThirdService inptNurseThirdService_consumer;

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
    public Map<String, Object> addInsureNurseRecord(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String visitId = MapUtils.get(map, "visitId");
        String name = MapUtils.get(map, "name");
        if (StringUtils.isEmpty(visitId)) {
            throw new RuntimeException("未选择需要上传的人员信息");
        }

        InptVisitDTO visitDTO = new InptVisitDTO();
        visitDTO.setHospCode(hospCode);
        visitDTO.setId(visitId);
        map.put("inptVisitDTO", visitDTO);
        // 获取就诊信息
        InptVisitDTO inptVisitDTO = inptVisitService_consumer.getInptVisitById(map).getData();
        if (inptVisitDTO == null) {
            throw new RuntimeException("未查询到【" + name + "】相关就诊记录");
        }

        // 根据就诊id查询护理记录(inpt_nurse_third)
        List<InptNurseThirdDTO> smtzList =  inptNurseThirdService_consumer.queryAllByVisitId(map).getData();
        map.put("smtzList", smtzList);

        return insureUnifiedPayInptService_consumer.UP_4602(map).getData();
    }
}
