package cn.hsa.module.sys.log.entity;

import java.util.Date;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class HisLogInfoYcDo extends PageDO {
    /**
     * 主键ID
     */
    private String id;


    /**
     * 请求参数
     */
    private String qqcs;

    /**
     * 异常名称
     */
    private String ycmc;

    /**
     * 异常信息
     */
    private String ycxx;

    /**
     * 请求路径
     */
    private String qqlj;

    /**
     * 操作方法
     */
    private String czff;

    /**
     * 操作人ID
     */
    private String czrid;

    /**
     * 操作人
     */
    private String czr;

    /**
     * 操作IP
     */
    private String czip;

    /**
     * 操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date xzsj;

    /**
     * 操作类型
     */
    private String czlx;

    /**
     * 操作描述
     */
    private String czms;
    /**
     * 机构编码
     */
    private String hospCode;

    /**
     * 日志唯一编码
     */
    private String traceId;
}

