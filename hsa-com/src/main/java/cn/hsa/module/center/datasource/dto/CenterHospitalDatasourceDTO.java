package cn.hsa.module.center.datasource.dto;

import cn.hsa.module.center.datasource.entity.CenterHospitalDatasourceDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.center.datasource.dto
 * @Class_name: CenterHospitalDatasourceDTO
 * @Describe: 1.2 医院数据源关系表
 * @Author: Ou·Mr
 * @Email: oubo@powersi.com.cn
 * @Date: 2020/7/30 21:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CenterHospitalDatasourceDTO extends CenterHospitalDatasourceDO implements Serializable {
    //序列号
    private static final long serialVersionUID = 589380909913029013L;
    private String name;//医院名称
    private String code;//医院编码
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;//服务有效开始日期
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;//服务有效结束日期
}
