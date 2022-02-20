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
     * 文件格式 html、pdf、txt、jpg
     */
    private String fileFormat;

}
