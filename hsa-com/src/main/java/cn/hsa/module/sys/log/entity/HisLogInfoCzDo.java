package cn.hsa.module.sys.log.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.hsa.base.PageDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HisLogInfoCzDo  extends PageDO implements Serializable {
    private static final long serialVersionUID = 217448257180401094L;
    /**
     * 主键ID
     */
    private String id;

    /**
     * 操作类型
     */
    private String czlx;

    /**
     * 操作描述
     */
    private String czms;

    /**
     * 操作方法
     */
    private String czff;

    /**
     * 请求参数
     */
    private String qqcs;

    /**
     * 返回参数
     */
    private String fhcs;

    /**
     * 请求路径
     */
    private String qqlj;

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
    private Date xzsj;


    /**
     * 机构编码
     */
    private String   hospCode;
    /**
     * 日志唯一编码
     */
    private String traceId;
}

