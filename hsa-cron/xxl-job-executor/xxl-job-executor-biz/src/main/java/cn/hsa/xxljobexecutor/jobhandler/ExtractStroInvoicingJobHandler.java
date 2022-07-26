package cn.hsa.xxljobexecutor.jobhandler;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import cn.hsa.module.center.hospital.service.CenterHospitalService;
import cn.hsa.module.inpt.medical.dto.MedicalAdviceDTO;
import cn.hsa.module.interf.extract.service.ExtractStroInvoicingService;
import cn.hsa.util.Constants;
import cn.hsa.util.DateUtils;
import cn.hsa.util.ListUtils;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author gory
 * @date 2022/7/6 8:18
 * 抽取进销存同步数据
 */
@JobHandler(value = "extractStroInvoicingJobHandler")
@Component
public class ExtractStroInvoicingJobHandler extends IJobHandler {
    @Resource
    private CenterHospitalService centerHospitalService_consumer;
    @Resource
    ExtractStroInvoicingService extractStroInvoicingService_consumer;
    @Override
    public ReturnT<String> execute(String param) throws Exception {
        XxlJobLogger.log("定时抽取进销存任务开始");
        List<CenterHospitalDTO> centerHospitalDTOList = queryValidCenterHospital();
        if(ListUtils.isEmpty(centerHospitalDTOList)){
            return FAIL;
        }
        for(CenterHospitalDTO centerHospitalDTO : centerHospitalDTOList){
            try {
                Map map =new HashMap<>();
                map.put("hospCode",centerHospitalDTO.getCode());
                extractStroInvoicingService_consumer.insertDataToExtractReport(map);
            } catch (Exception e) {
                e.printStackTrace();
                XxlJobLogger.log("["+centerHospitalDTO.getCode()+"]"+e.getMessage());
            } finally {
                XxlJobLogger.log("====================["+centerHospitalDTO.getCode()+"]长期费用结束:"+DateUtils.format("yyyy-MM-dd HH:mm:ss"));
            }
        }
        return SUCCESS;
    }
    private List<CenterHospitalDTO> queryValidCenterHospital() {
        CenterHospitalDTO dto = new CenterHospitalDTO();
        dto.setIsValid(Constants.SF.S);
        WrapperResponse<List<CenterHospitalDTO>> wr = centerHospitalService_consumer.queryAll(dto);
        if (wr.getCode() != 0) {
            XxlJobLogger.log("定时抽取进销存任务执行失败：" + wr.getMessage());
        }
        return wr.getData().stream().filter(centerHospitalDTO -> DateUtils.betweenDate(centerHospitalDTO.getStartDate(), centerHospitalDTO.getEndDate(), DateUtils.getNow())).collect(Collectors.toList());
    }
}
