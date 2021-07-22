package cn.hsa.module.sys.log.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name:cn.hsa.module.sys.log.dto
 * @Class_name:LogQueryDTO
 * @Project_name:hsa-his
 * @Describe:  查询日志dto
 * @Author: zibo.yuan
 * @Date: 2020/12/2 9:11
 * @Email: zibo.yuan@powersi.com.cn
 */
@Data
@AllArgsConstructor
@ToString(callSuper = true)
public class LogQueryDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    //日志文件类型
    private String logFileType;
    //日志文件生成日期
    private String logFileCreateDate;
    //文件路径
    private String filePath;
    //日志级别
    private String logLevel;
    //查询参数
    private String queryStr;
    //结束时间
    private String endTime;
    //开始时间
    private String startTime;
    //开始记录数
    private int startNumber;
}
