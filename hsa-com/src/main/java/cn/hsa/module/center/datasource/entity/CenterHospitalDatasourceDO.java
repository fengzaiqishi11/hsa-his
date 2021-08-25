package cn.hsa.module.center.datasource.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
/**
 * @Package_name: cn.hsa.module.center.datasource.entity
 * @Class_name: CenterHospitalDatasourceDO
 * @Describe: 医院数据源关系
 * @Author: Ou·Mr
 * @Email: oubo@powersi.com.cn
 * @Date: 2020/7/30 21:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CenterHospitalDatasourceDO extends PageDO implements Serializable {
    //序列号
    private static final long serialVersionUID = -8713056603428418786L;
    //主键
    private String id;
    //数据源编码
    private String dsCode;
    //医院编码
    private String hospCode;
    //创建人ID
    private String crteId;
    //创建人姓名
    private String crteName;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date crteTime;

}