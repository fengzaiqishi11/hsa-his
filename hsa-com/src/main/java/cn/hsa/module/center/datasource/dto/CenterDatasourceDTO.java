package cn.hsa.module.center.datasource.dto;

import cn.hsa.module.center.datasource.entity.CenterDatasourceDO;
import cn.hsa.module.center.datasource.entity.CenterHospitalDatasourceDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.center.datasource.dto
 * @Class_name: CenterDatasourceDTO
 * @Describe: 医院数据源DTO
 * @Author: Ou·Mr
 * @Email: oubo@powersi.com.cn
 * @Date: 2020/7/30 21:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CenterDatasourceDTO extends CenterDatasourceDO implements Serializable {
    //顺序号
    private static final long serialVersionUID = 234447821465644075L;
    //医院编码
    private String hospCode;
    //医院数据源关系
    private CenterHospitalDatasourceDO centerHospitalDatasourceDO;
    //接收批量的数据源绑定医院信息
    private List<CenterHospitalDatasourceDO> centerHospitalDatasourceDOS;
    //1新增  2删除
    private String addDelFlag;

}
