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
     * 医院编码
     */
    private String hospCode;
    /**
     * 模板编码
     */
    private String tempCode;
    /**
     * 报表模板文件名称
     */
    private String tempName;
    /**
     * 返回数据类型 1-html 2-文件地址 3-base64编码
     */
    private String returnDataType;
    /**
     * 是否上传 0-否 1-是
     */
    private String isUpload;
    /**
     * 自定义配置
     */
    private String customConfig;
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
