package cn.hsa.module.report.config.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName ReportCustomConfigDTO
 * @Deacription dto层
 * @Author liuzhoting
 * @Date 2022-02-17
 * @Version 1.0
 **/
@Data
public class ReportCustomConfigDTO implements Serializable {

    /**
     * 文件格式 1-html 2-pdf 3-txt 4-jpg
     */
    private String fileFormat;
}
