package cn.hsa.xxljobexecutor.jobhandler;

import cn.hsa.module.base.bi.service.BaseItemService;
import cn.hsa.module.insure.module.service.InsureGetInfoService;
import cn.hsa.util.DateUtils;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.xxljobexecutor.jobhandler
 * @Class_name: baseAdviceJobHandler
 * @Describe:
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2021/1/21 11:00
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@com.xxl.job.core.handler.annotation.JobHandler(value="setlJobHandler")
@Component
public class SetlJobHandler extends IJobHandler {

    @Resource
    private InsureGetInfoService insureGetInfoService_consumer;


    @Override
    public ReturnT<String> execute(String params) throws Exception {
        XxlJobLogger.log("结算清单地址历史数据处理");
        Map<String,Object> resultMap = new HashMap<>();
        try {
            Map map =new HashMap<>();
            String[] paramArry = params.split(",");
            if(paramArry.length>0){
                map.put("hospCode",paramArry[0]);
            }
            if(paramArry.length>1){
                map.put("insureRegCode",paramArry[1]);
            }
            map.put("params",params);
            if(paramArry.length>2){
                map.put("startTime",paramArry[2]);
            }
            map.put("params",params);
            if(paramArry.length>3){
                map.put("endtime",paramArry[3]);
            }
            map.put("params",params);
            resultMap = (Map<String,Object>)insureGetInfoService_consumer.updateHistoricalData(map).getData();
        }catch (Exception e) {
            e.printStackTrace();
            XxlJobLogger.log("["+params+"]"+e.getMessage());
        } finally {
            XxlJobLogger.log("====================结算清单地址历史数据处理结果为:"+resultMap);
            XxlJobLogger.log("====================["+params+"]结算清单地址历史数据处理结束:"+DateUtils.format("yyyy-MM-dd HH:mm:ss"));
        }
        return ReturnT.SUCCESS;
    }







}
