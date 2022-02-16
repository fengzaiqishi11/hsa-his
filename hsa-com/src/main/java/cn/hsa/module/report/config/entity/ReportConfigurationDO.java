package cn.hsa.module.report.config.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName ReportConfigurationDO
 * @Deacription
 * @Author liuzhoting
 * @Date 2022-02-16
 * @Version 1.0
 **/
@Data
public class ReportConfigurationDO extends PageDO implements Serializable {

    /**
     * 主键
     */
    private String id;
    /**
     * 模板编码
     */
    private String tempCode;
    /**
     * 报表模板文件名称
     */
    private String tempName;
    /**
     * 报表模板文件内容
     */
    private byte[] tempContent;
    /**
     * 返回数据类型 1-html 2-文件地址 3-文件流
     */
    private String returnDataType;
    /**
     * 文件格式 1-html 2-pdf 3-txt 4-jpg
     */
    private String fileFormat;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updtTime;
    /**
     * 创建人
     */
    private String crterId;
    /**
     * 创建人姓名
     */
    private String crterName;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date crteTime;

}
