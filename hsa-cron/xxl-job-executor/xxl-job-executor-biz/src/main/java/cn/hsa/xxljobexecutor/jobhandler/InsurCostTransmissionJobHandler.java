package cn.hsa.xxljobexecutor.jobhandler;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import cn.hsa.module.center.hospital.service.CenterHospitalService;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.module.service.InsureIndividualCostService;
import cn.hsa.util.Constants;
import cn.hsa.util.DateUtils;
import cn.hsa.util.ListUtils;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @Package_name: cn.hsa.xxljobexecutor.jobhandler
* @class_name: InsurCostTransmissionJobHandler
* @Description: 医保费用传输
* @Author: liaojiguang
* @Email: 847025096@qq.com
* @Date: 2021/7/14 14:36
* @Company: 创智和宇
**/
@com.xxl.job.core.handler.annotation.JobHandler(value="insurCostTransmissionJob")
@Component
public class InsurCostTransmissionJobHandler extends IJobHandler {

    @Resource
    private InsureIndividualCostService insureIndividualCostService_consumer;
    @Resource
    private CenterHospitalService centerHospitalService_consumer;

    @Override
    public ReturnT<String> execute(String params) {
        XxlJobLogger.log("医保费用定时传输");
        List<CenterHospitalDTO> dtoList = queryValidCenterHospital();
        if (ListUtils.isEmpty(dtoList)) {
            return FAIL;
        }
        for (CenterHospitalDTO dto : dtoList) {
            XxlJobLogger.log("====================["+dto.getCode()+"]医保费用定时传输开始:"+DateUtils.format("yyyy-MM-dd HH:mm:ss"));
            try {
                Map map = new HashMap();
                InptVisitDTO inptVisitDTO = new InptVisitDTO();
                inptVisitDTO.setHospCode(dto.getCode());
                inptVisitDTO.setCrteTime(DateUtils.getNow());
                inptVisitDTO.setCrteId("-1");
                inptVisitDTO.setCrteName("自动执行");
                map.put("hospCode", dto.getCode());
                map.put("inptVisitDTO", inptVisitDTO);
                Map<String,String> resultMap = insureIndividualCostService_consumer.insurCostTransmissionJob(map);
                String code = resultMap.get("code");
                String info = resultMap.get("info");
                String msg = resultMap.get("msg");
                if ("-1".equals(code)) {
                    XxlJobLogger.log("["+dto.getCode()+"]" + info);
                } else if (msg != null) {
                    XxlJobLogger.log("["+dto.getCode()+"]" + msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
                XxlJobLogger.log("["+dto.getCode()+"]"+e.getMessage());
            } finally {
                XxlJobLogger.log("====================["+dto.getCode()+"]医保费用定时传输结束:"+DateUtils.format("yyyy-MM-dd HH:mm:ss"));
            }
        }
        return SUCCESS;
    }

    private List<CenterHospitalDTO> queryValidCenterHospital() {
        CenterHospitalDTO dto = new CenterHospitalDTO();
        dto.setIsValid(Constants.SF.S);
        WrapperResponse<List<CenterHospitalDTO>> wr = centerHospitalService_consumer.queryAll(dto);
        if (wr.getCode() != 0) {
            XxlJobLogger.log("医保定时费用传输失败：" + wr.getMessage());
        }
        return wr.getData().stream().filter(centerHospitalDTO -> DateUtils.betweenDate(centerHospitalDTO.getStartDate(), centerHospitalDTO.getEndDate(), DateUtils.getNow())).collect(Collectors.toList());
    }
}
