package cn.hsa.xxljobexecutor.jobhandler;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import cn.hsa.module.center.hospital.service.CenterHospitalService;
import cn.hsa.module.inpt.longcost.service.BedLongCostService;
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
* @class_name: LongBedCostJobHandler
* @Description: 长期床位费
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2021/1/22 14:14
* @Company: 创智和宇
**/
@com.xxl.job.core.handler.annotation.JobHandler(value="longBedCostJob")
@Component
public class LongBedCostJobHandler extends IJobHandler {

    @Resource
    private BedLongCostService bedLongCostService;
    @Resource
    private CenterHospitalService centerHospitalService_consumer;

    @Override
    public ReturnT<String> execute(String params) {
        XxlJobLogger.log("长期床位费用自动执行");
        // 费用滚动开始时间
        List<CenterHospitalDTO> dtoList = queryValidCenterHospital();
        if (ListUtils.isEmpty(dtoList)) {
            return SUCCESS;
        }

        Map map = new HashMap();
        map.put("isAuto", Constants.SF.S);
        map.put("userId", "-1");
        map.put("userName", "自动执行");
        for(CenterHospitalDTO dto : dtoList) {
            map.put("hospCode", dto.getCode());
            try {
                // 滚动医院长期费用
                WrapperResponse<Boolean> wr = bedLongCostService.saveBedLongCost(map);
                if (wr.getCode() != 0) {
                    XxlJobLogger.log("医院编号【" + dto.getCode() + "】， 医院名称【" + dto.getName() + "】生成床位长期费用失败：" + wr.getMessage());
                    continue;
                }
                if (wr.getData()) {
                    XxlJobLogger.log("医院编号【" + dto.getCode() + "】， 医院名称【" + dto.getName() + "】生成床位长期费用成功！");
                } else {
                    XxlJobLogger.log("医院编号【" + dto.getCode() + "】， 医院名称【" + dto.getName() + "】生成床位长期费用异常：请联系管理员");
                }
            } catch (Exception e) {
                XxlJobLogger.log(e.getMessage(), e);
            }
        }
        return SUCCESS;
    }

    private List<CenterHospitalDTO> queryValidCenterHospital() {
        CenterHospitalDTO dto = new CenterHospitalDTO();
        dto.setIsValid(Constants.SF.S);
        WrapperResponse<List<CenterHospitalDTO>> wr = centerHospitalService_consumer.queryAll(dto);
        if (wr.getCode() != 0) {
            XxlJobLogger.log("长期费用滚动失败：" + wr.getMessage());
        }
        return wr.getData().stream().filter(centerHospitalDTO -> DateUtils.betweenDate(centerHospitalDTO.getStartDate(), centerHospitalDTO.getEndDate(), DateUtils.getNow())).collect(Collectors.toList());
    }
}
