//package cn.hsa.example.job;
//
//import com.xxl.job.core.biz.model.ReturnT;
//import com.xxl.job.core.handler.IJobHandler;
//import com.xxl.job.core.handler.annotation.JobHandler;
//import com.xxl.job.core.log.XxlJobLogger;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.TimeUnit;
//
///**
// * 本地不启动
// */
//@ConditionalOnProperty(name = "xxx.job.enabled", havingValue = "true", matchIfMissing = true)
//@JobHandler(value = "myJobHandler")
//@Component
//public class XxlJobHandlerDemo extends IJobHandler {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(XxlJobHandlerDemo.class);
//
//    @Override
//    public ReturnT<String> execute(String s) throws Exception {
//        LOGGER.info("===> 执行定时任务！");
//        XxlJobLogger.log("===> 执行定时任务！");
//        try {
//            LOGGER.info("Sleeping ......");
//            XxlJobLogger.log("Sleeping ......");
//            TimeUnit.SECONDS.sleep(5);
//        } catch (InterruptedException e) {
//            LOGGER.info("######### error: ", e);
//            XxlJobLogger.log("######### error: ", e);
//            return FAIL;
//        }
//
//        LOGGER.info("<=== job is done!");
//        XxlJobLogger.log("Sleeping ......");
//        return SUCCESS;
//    }
//
//
//}