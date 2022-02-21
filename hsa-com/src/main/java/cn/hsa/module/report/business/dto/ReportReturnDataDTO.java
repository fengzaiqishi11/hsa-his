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
     * 返回数据
     */
    private String returnData;

}
