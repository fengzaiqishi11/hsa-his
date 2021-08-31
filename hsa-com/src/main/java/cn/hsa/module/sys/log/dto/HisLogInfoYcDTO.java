package cn.hsa.module.sys.log.dto;

import cn.hsa.module.sys.log.entity.HisLogInfoYcDo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@ToString(callSuper = true)
@NoArgsConstructor
public class HisLogInfoYcDTO extends HisLogInfoYcDo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String fhcs;//返回参数
    private String keyword;// 查询字段
    private String queryType;// 查询类型 cz:操作日志查询  yc:异常日志查询
    private String querySystem; // 查询系统

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date queryDate;//查询日期

    private String logDate; // 日志的生成时间
    private String logLevel; // 日志级别
    private String logText; // 其它日志信息 text文本
}

