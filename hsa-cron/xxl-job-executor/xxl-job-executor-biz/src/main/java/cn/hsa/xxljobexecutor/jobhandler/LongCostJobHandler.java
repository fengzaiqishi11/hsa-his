package cn.hsa.xxljobexecutor.jobhandler;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import cn.hsa.module.center.hospital.service.CenterHospitalService;
import cn.hsa.module.inpt.medical.dto.MedicalAdviceDTO;
import cn.hsa.module.inpt.medical.service.MedicalAdviceService;
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
* @class_name: LongCostJobHandler
* @Description: 长期费用
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2021/1/22 14:14
* @Company: 创智和宇
**/
@com.xxl.job.core.handler.annotation.JobHandler(value="longCostJob")
@Component
public class LongCostJobHandler extends IJobHandler {

    @Resource
    private MedicalAdviceService medicalAdviceService_consumer;
    @Resource
    private CenterHospitalService centerHospitalService_consumer;

    @Override
    public ReturnT<String> execute(String params) {
        XxlJobLogger.log("长期费用定时任务");
        // 费用滚动开始时间
        List<CenterHospitalDTO> dtoList = queryValidCenterHospital();
        if (ListUtils.isEmpty(dtoList)) {
            return SUCCESS;
        }
        for (CenterHospitalDTO dto : dtoList) {
            XxlJobLogger.log("====================["+dto.getCode()+"]长期费用开始:"+DateUtils.format("yyyy-MM-dd HH:mm:ss"));
            try {
                Map map = new HashMap();
                MedicalAdviceDTO medicalAdviceDTO = new MedicalAdviceDTO();
                medicalAdviceDTO.setHospCode(dto.getCode());
                medicalAdviceDTO.setCheckTime(DateUtils.getNow());
                medicalAdviceDTO.setCheckName("医嘱长期费用自动执行");
                medicalAdviceDTO.setCheckId("-1");
                map.put("hospCode", dto.getCode());
                map.put("medicalAdviceDTO", medicalAdviceDTO);
                medicalAdviceService_consumer.longCost(map);
            } catch (Exception e) {
                e.printStackTrace();
                XxlJobLogger.log("["+dto.getCode()+"]"+e.getMessage());
            } finally {
                XxlJobLogger.log("====================["+dto.getCode()+"]长期费用结束:"+DateUtils.format("yyyy-MM-dd HH:mm:ss"));
            }
        }
        return SUCCESS;
    }

    private List<CenterHospitalDTO> queryValidCenterHospital() {
        CenterHospitalDTO dto = new CenterHospitalDTO();
        dto.setIsValid(Constants.SF.S);
        // 迁移状态（0:未迁移；1:待迁移；2:已迁移）
        // 迁移过的医院长期费用不用生成了
        dto.setMigrationStatus("0");
        dto.setIsLong("1");
        WrapperResponse<List<CenterHospitalDTO>> wr = centerHospitalService_consumer.queryAll(dto);
        if (wr.getCode() != 0) {
            XxlJobLogger.log("长期费用滚动失败：" + wr.getMessage());
        }
        return wr.getData().stream().filter(centerHospitalDTO -> DateUtils.betweenDate(centerHospitalDTO.getStartDate(), centerHospitalDTO.getEndDate(), DateUtils.getNow())).collect(Collectors.toList());
    }
}
