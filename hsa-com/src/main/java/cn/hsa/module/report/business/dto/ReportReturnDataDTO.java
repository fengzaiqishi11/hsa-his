package cn.hsa.module.report.business.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName ReportReturnDataDTO
 * @Deacription dto层
 * @Author liuzhoting
 * @Date 2022-02-21
 * @Version 1.0
 **/
@Data
public class ReportReturnDataDTO implements Serializable {

    /**
     * 报表key
     */
    private String key;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件格式
     */
    private String fileFormat;

    /**
     * 返回数据
     */
    private String returnData;

    private String returnDataType;

    public ReportReturnDataDTO() {
    }

    public ReportReturnDataDTO(String key, String fileName, String fileFormat, String returnData, String returnDataType) {
        this.key = key;
        this.fileName = fileName;
        this.fileFormat = fileFormat;
        this.returnData = returnData;
        this.returnDataType = returnDataType;
    }
}
