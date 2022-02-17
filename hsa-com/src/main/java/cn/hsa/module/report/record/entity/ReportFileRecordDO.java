package cn.hsa.module.report.record.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName ReportFileRecordDO
 * @Deacription
 * @Author liuzhoting
 * @Date 2022-02-16
 * @Version 1.0
 **/
@Data
public class ReportFileRecordDO extends PageDO implements Serializable {

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
     * 文件地址
     */
    private String fileAddress;
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
