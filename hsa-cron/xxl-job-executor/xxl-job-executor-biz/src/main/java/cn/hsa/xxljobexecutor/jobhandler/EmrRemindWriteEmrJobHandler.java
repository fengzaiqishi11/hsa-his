package cn.hsa.xxljobexecutor.jobhandler;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import cn.hsa.module.center.hospital.service.CenterHospitalService;
import cn.hsa.module.emr.emrarchivelogging.service.EmrArchiveLoggingService;
import cn.hsa.module.emr.emrquality.service.EmrQualityAgingService;
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
 * @class_name: JobHandler
 * @project_name: hsa-his
 * @Description: 电子病历书写提醒定时器
 * @Author: liuliyun
 * @Email: liyun.liu@powersi.com
 * @Date:2021/12/06 15:43
 * @Company: 创智和宇
 **/
@com.xxl.job.core.handler.annotation.JobHandler(value="emrRemindWriteEmrJob")
@Component
public class EmrRemindWriteEmrJobHandler extends IJobHandler {

    /**
     * 电子病历书写提醒定时器
     */
    @Resource
    private EmrQualityAgingService emrQualityAgingService_consumer;

    @Resource
    private CenterHospitalService centerHospitalService_consumer;

    /**
     * @Method: execute()
     * @Descrition: 电子病历书写提醒定时任务执行方法
     * @Pramas: hospcode:医院编码
     * @Author: liuliyun
     * @mail: liyun.liu
     * @Date: 2021/12/06
     * @Retrun:
     */
    @Override
    public ReturnT<String> execute(String params) throws Exception {
        XxlJobLogger.log("电子病历未书写定时任务");
        List<CenterHospitalDTO> centerHospitalDTOList = queryValidCenterHospital();
        if(ListUtils.isEmpty(centerHospitalDTOList)){
            return FAIL;
        }
        for(CenterHospitalDTO centerHospitalDTO : centerHospitalDTOList){
            Map map =new HashMap<>();
            map.put("hospCode",centerHospitalDTO.getCode());
            map.put("crteName","自动执行");
            map.put("crteId","-1");
            WrapperResponse<Boolean> wr=emrQualityAgingService_consumer.queryUnwriteEmrList(map);
            XxlJobLogger.log("医院编号【" + centerHospitalDTO.getCode() + "】， 医院名称【" + centerHospitalDTO.getName() + "】电子病历未书写信息：" + wr.getMessage());
        }
        return SUCCESS;
    }

    private List<CenterHospitalDTO> queryValidCenterHospital() {
        CenterHospitalDTO dto = new CenterHospitalDTO();
        dto.setIsValid(Constants.SF.S);
        WrapperResponse<List<CenterHospitalDTO>> wr = centerHospitalService_consumer.queryAll(dto);
        if (wr.getCode() != 0) {
            XxlJobLogger.log("电子病历未书写信息定时任务执行失败：" + wr.getMessage());
        }
        return wr.getData().stream().filter(centerHospitalDTO -> DateUtils.betweenDate(centerHospitalDTO.getStartDate(), centerHospitalDTO.getEndDate(), DateUtils.getNow())).collect(Collectors.toList());
    }
}
