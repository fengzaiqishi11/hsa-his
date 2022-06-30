package cn.hsa.xxljobexecutor.jobhandler;

import cn.hsa.util.DBUtils;
import cn.hsa.util.DateUtils;
import cn.hutool.core.date.DateUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;

/**
 *  统一支付平台助手日志定期清理执行器
 * @author luonianxin
 * @version 1.0
 * @date 2022/5/31 9:20
 */
@com.xxl.job.core.handler.annotation.JobHandler(value="hgeiaHgsLogCleanJobHandler")
@Component
public class HygeiaHgsLogCleanJobHandler extends IJobHandler {

    /**
     *  hygeia-hgs系统数据库用户名
     */
    @Value("${db.druid.username:dhis}")
    private String userName ;
    /**
     *  hygeia-hgs系统数据库用户密码
     */
    @Value("${db.druid.password:Powersi@2021}")
    private String password = "";
    /**
     *  hygeia-hgs系统数据库连接地址
     */
    @Value("${db.druid.url:jdbc:mysql://rm-bp13m528f4h31v33q.mysql.rds.aliyuncs.com:3306/hygeia_hgs?connectTimeout=5000&socketTimeout=10000&autoReconnect=true&failOverReadOnly=false&useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8&allowMultiQueries=true}")
    private String jdbcUrl = "";
    /**
     *  hygeia-hgs系统数据库驱动程序名
     */
    @Value("${db.druid.driverClassName:com.mysql.cj.jdbc.Driver}")
    private String driverClassName;
    /**
     *  统一支付平台调用日志清理语句
     */
    private final String LOG_CLEAN_SQL = "delete from hygeia_hgs.bas_business_log where create_time < date_sub(now(),interval 20 day);";

    private final String SPACE_RELEASE_SQL = "alter table hygeia_hgs.bas_business_log engine = innodb;";
    @Override
    public ReturnT<String> execute(String param) throws Exception {
        XxlJobLogger.log("开始清理统一支付平台调用日志时间：{}" , DateUtils.format(DateUtils.Y_M_DH_M_S));
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
             conn = DBUtils.getConnection(driverClassName, jdbcUrl, userName, password);
             conn.setAutoCommit(false);
             preparedStatement = conn.prepareStatement(LOG_CLEAN_SQL);
            int count = preparedStatement.executeUpdate();
            XxlJobLogger.log("统一支付平台日志清理完成，本次共清理：{}条接口日志" , count);
            preparedStatement = conn.prepareStatement(SPACE_RELEASE_SQL);
            boolean result = preparedStatement.execute();
            XxlJobLogger.log("统一支付平台日志表空间整理结果：{}" , result);

        }catch (Exception e){
            XxlJobLogger.log("清理日志任务执行出错异常信息如下：{}" , e.getMessage());
            return ReturnT.FAIL;
        }finally {
            conn.commit();
            conn.setAutoCommit(true);
            DBUtils.closeConnection(conn,preparedStatement,null);
        }
        return ReturnT.SUCCESS;
    }
}
