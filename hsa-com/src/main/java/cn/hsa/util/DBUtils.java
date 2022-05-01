package cn.hsa.util;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @Package_name: cn.hsa.util
 * @Class_name: DBScriptUtil
 * @Description:
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/09/01 10:32
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
public class DBUtils {
    /**
     * @Method 测试数据库连接
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/1 17:54
     * @Return
     **/
    public static boolean testConnection(String driverClassName, String url, String username, String password) {
        boolean isConn = true;
        try {
            getConnection(driverClassName, url, username, password);
        } catch (Exception e) {
            isConn = false;
        }
        return isConn;
    }

        /**
     * @Method 获取数据库连接对象
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/1 17:54
     * @Return
     **/
    public static Connection getConnection(String driverClassName, String url, String username, String password) throws Exception {
        Class.forName(driverClassName);
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * @Method 数据库是否已经存在表，存在表表示已经初始化过数据库
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/1 17:54
     * @Return
     **/
    public static boolean existTable(String driverClassName, String url, String username, String password) {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = getConnection(driverClassName, url, username, password);
            st = conn.createStatement();
            rs = st.executeQuery("show tables");
            return rs.next();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            closeConnection(conn, st, rs);
        }
        return false;
    }

    /**
     * @Method 关闭数据库连接
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/1 17:53
     * @Return
     **/
    public static void closeConnection(Connection conn, Statement st, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            rs = null;
        }
        if (st != null) {
            try {
                st.close();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            st = null;
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            conn = null;
        }
    }

    /**
     * @Method 初始化数据库脚本
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/1 17:53
     * @Return
     **/
    public static void initScript(String driverClassName, String url, String username, String password, String sqlScriptPath) {
        Connection conn = null;
        try {
//            File file = new File(sqlScriptPath);
//            if (!file.exists() || !file.isFile()) {
//                throw new AppException("file is not exist");
//            }
             conn = getConnection(driverClassName, url, username, password);
            // 是否存在表（存在表表示已经初始化过数据）
            if (!existTable(driverClassName, url, username, password)) {
                ScriptRunner runner = new ScriptRunner(conn);
                runner.setAutoCommit(true);
                runner.setFullLineDelimiter(false);
                // 语句结束符号设置
                runner.setDelimiter(";");
                // 日志数据输出，这样就不会输出过程
                runner.setLogWriter(null);
                runner.setSendFullScript(false);
                runner.setStopOnError(true);

                log.info("初始化表结构SQL文件地址:"+sqlScriptPath);
                ClassPathResource classPathResource = new ClassPathResource(sqlScriptPath);
                InputStream inputStream =classPathResource.getInputStream();
                runner.runScript(new InputStreamReader(inputStream, "utf8"));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new AppException(e.getMessage());
        } finally {
            closeConnection(conn, null, null);
        }
    }

    public InputStream getPath (){
        return this.getClass().getResourceAsStream("/script/his_v2.sql");
    }
}
