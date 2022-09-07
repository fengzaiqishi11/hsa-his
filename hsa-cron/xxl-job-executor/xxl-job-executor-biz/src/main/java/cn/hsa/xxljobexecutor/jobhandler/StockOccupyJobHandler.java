package cn.hsa.xxljobexecutor.jobhandler;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import cn.hsa.module.center.hospital.service.CenterHospitalService;
import cn.hsa.module.stro.stock.service.StroStockService;
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
 * @Package_name: cn.hsa.xxljobexecutor.jobhandler
 * @class_name: JobHandler
 * @project_name: hsa-his
 * @Description: 库存过期占用库存定时任务处理层
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date:2020/11/29 11:07
 * @Company: 创智和宇
 **/
@JobHandler(value = "stockOccupyJob")
@Component
public class StockOccupyJobHandler extends IJobHandler {

    /*
    库存占用服务层
     */
    @Resource
    private StroStockService stroStockService_consumer;

    @Resource
    private CenterHospitalService centerHospitalService_consumer;

    /**
     * @Method: execute()
     * @Descrition: 库存过期占用库存定时任务执行方法
     * @Pramas: hospcode:医院编码
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/11/29
     * @Retrun:
     */
    @Override
    public ReturnT<String> execute(String params) throws Exception {
        XxlJobLogger.log("库存过期占用库存定时任务");
        List<CenterHospitalDTO> centerHospitalDTOList = queryValidCenterHospital();
        if (ListUtils.isEmpty(centerHospitalDTOList)) {
            return FAIL;
        }
        for (CenterHospitalDTO centerHospitalDTO : centerHospitalDTOList) {
            try {
                Map map = new HashMap<>();
                map.put("hospCode", centerHospitalDTO.getCode());
                stroStockService_consumer.updateOccupyByExpire(map);
            } catch (Exception e) {
                e.printStackTrace();
                XxlJobLogger.log("[" + centerHospitalDTO.getCode() + "]" + e.getMessage());
            } finally {
                XxlJobLogger.log("====================[" + centerHospitalDTO.getCode() + "]进销存同步结束:" + DateUtils.format("yyyy-MM-dd HH:mm:ss"));
            }
        }
        return SUCCESS;
    }

    private List<CenterHospitalDTO> queryValidCenterHospital() {
        CenterHospitalDTO dto = new CenterHospitalDTO();
        dto.setIsValid(Constants.SF.S);
        WrapperResponse<List<CenterHospitalDTO>> wr = centerHospitalService_consumer.queryAll(dto);
        if (wr.getCode() != 0) {
            XxlJobLogger.log("库存过期占用库存定时任务：" + wr.getMessage());
        }
        return wr.getData().stream().filter(centerHospitalDTO -> DateUtils.betweenDate(centerHospitalDTO.getStartDate(), centerHospitalDTO.getEndDate(), DateUtils.getNow())).collect(Collectors.toList());
    }
}
