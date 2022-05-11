package cn.hsa.xxljobexecutor.jobhandler;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import cn.hsa.module.center.hospital.service.CenterHospitalService;
import cn.hsa.module.stro.stroinvoicing.service.StroInvoicingMonthlyService;
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
 * @Class_name: StroInvoicingMonthlyJobHandler
 * @Describe:
 * @Author: zhangguorui
 * @Date: 2022/3/18 9:58
 **/
@com.xxl.job.core.handler.annotation.JobHandler(value="stroInvoicingMonthlyJobHandler")
@Component
public class StroInvoicingMonthlyJobHandler extends IJobHandler{
    @Resource
    private StroInvoicingMonthlyService stroInvoicingMonthlyService_consumer;
    @Resource
    private CenterHospitalService centerHospitalService_consumer;
    /**
     * @Meth: execute
     * @Description: 每日同步进销存
     * @Param: [param]
     * @return: com.xxl.job.core.biz.model.ReturnT<java.lang.String>
     * @Author: zhangguorui
     * @Date: 2022/3/18
     */
    @Override
    public ReturnT<String> execute(String param) throws Exception {
        XxlJobLogger.log("月度进销存同步定时任务");
        List<CenterHospitalDTO> centerHospitalDTOList = queryValidCenterHospital();
        if(ListUtils.isEmpty(centerHospitalDTOList)){
            return FAIL;
        }
        for(CenterHospitalDTO centerHospitalDTO : centerHospitalDTOList){
            Map map =new HashMap<>();
            map.put("hospCode",centerHospitalDTO.getCode());
            stroInvoicingMonthlyService_consumer.insertCopyStroInvoicing(map);
        }
        return SUCCESS;
    }
    private List<CenterHospitalDTO> queryValidCenterHospital() {
        CenterHospitalDTO dto = new CenterHospitalDTO();
        dto.setIsValid(Constants.SF.S);
        WrapperResponse<List<CenterHospitalDTO>> wr = centerHospitalService_consumer.queryAll(dto);
        if (wr.getCode() != 0) {
            XxlJobLogger.log("库存过期定时任务执行失败：" + wr.getMessage());
        }
        return wr.getData().stream().filter(centerHospitalDTO -> DateUtils.betweenDate(centerHospitalDTO.getStartDate(), centerHospitalDTO.getEndDate(), DateUtils.getNow())).collect(Collectors.toList());
    }
}
