package cn.hsa.xxljobexecutor.jobhandler;

import cn.hsa.module.base.bi.service.BaseItemService;
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
@com.xxl.job.core.handler.annotation.JobHandler(value="baseItemJob")
@Component
public class BaseItemJobHandlerBO extends IJobHandler {

    /*
    库存占用服务层
     */
    @Resource
    private BaseItemService baseItemService;


    @Override
    public ReturnT<String> execute(String params) throws Exception {
        XxlJobLogger.log("项目查询");
        Map map =new HashMap<>();
        map.put("hospCode",params);
        baseItemService.queryAll(map);
        return SUCCESS;
    }







}
