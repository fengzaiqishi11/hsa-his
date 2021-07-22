package cn.hsa.module.sys.log.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name:cn.hsa.module.sys.log.dto
 * @Class_name:LogFilePojoDto
 * @Project_name:hsa-his
 * @Describe:
 * @Author: zibo.yuan
 * @Date: 2020/12/2 9:08
 * @Email: zibo.yuan@powersi.com.cn
 */
@Data
@AllArgsConstructor
@ToString(callSuper = true)
@NoArgsConstructor
public class LogFileDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    //日志内容
    private String logContent;
    //日志时间
    private String logDate;
    //日志级别
    private String logRank;
    // traceID
    private String traceId;

}
