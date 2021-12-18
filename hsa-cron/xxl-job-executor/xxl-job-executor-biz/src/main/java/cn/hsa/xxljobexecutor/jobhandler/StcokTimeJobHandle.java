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
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.xxljobexecutor.jobhandler
 * @Class_name: StcokTimeJobHandle
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/12/13 16:08
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@com.xxl.job.core.handler.annotation.JobHandler(value="stcokTimeJobHandle")
@Component
public class StcokTimeJobHandle extends IJobHandler{

  @Resource
  private CenterHospitalService centerHospitalService_consumer;

  /*
    库存占用服务层
     */
  @Resource
  private StroStockService stroStockService_consumer;

  @Override
  public ReturnT<String> execute(String s) throws Exception {
    XxlJobLogger.log("月底库存存储定时任务");
    List<CenterHospitalDTO> centerHospitalDTOList = queryValidCenterHospital();
    if(ListUtils.isEmpty(centerHospitalDTOList)){
      return FAIL;
    }
    for(CenterHospitalDTO centerHospitalDTO : centerHospitalDTOList){
      Map map =new HashMap<>();
      map.put("hospCode",centerHospitalDTO.getCode());
      map.put("crteTime",new Date());
      stroStockService_consumer.insertStockByTime(map);
    }
    return SUCCESS;
  }


  private List<CenterHospitalDTO> queryValidCenterHospital() {
    CenterHospitalDTO dto = new CenterHospitalDTO();
    dto.setIsValid(Constants.SF.S);
    WrapperResponse<List<CenterHospitalDTO>> wr = centerHospitalService_consumer.queryAll(dto);
    if (wr.getCode() != 0) {
      XxlJobLogger.log("月底库存存储定时任务执行失败：" + wr.getMessage());
    }
    return wr.getData().stream().filter(centerHospitalDTO -> DateUtils.betweenDate(centerHospitalDTO.getStartDate(), centerHospitalDTO.getEndDate(), DateUtils.getNow())).collect(Collectors.toList());
  }
}
