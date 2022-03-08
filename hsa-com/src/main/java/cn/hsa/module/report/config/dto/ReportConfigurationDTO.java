package cn.hsa.module.report.config.dto;

import cn.hsa.module.report.config.entity.ReportConfigurationDO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName ReportConfigurationDTO
 * @Deacription dto层
 * @Author liuzhoting
 * @Date 2022-02-16
 * @Version 1.0
 **/
@Data
public class ReportConfigurationDTO extends ReportConfigurationDO implements Serializable {

    private String keyword;

    private List<String> ids;

}
