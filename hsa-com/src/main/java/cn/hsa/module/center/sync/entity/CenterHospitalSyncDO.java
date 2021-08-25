package cn.hsa.module.center.sync.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
/**
 * @Package_name: cn.hsa.module.center.sync.entity
 * @Class_name: CenterHospitalSyncDO
 * @Describe: 医院数据同步关系
 * @Author: Ou·Mr
 * @Email: oubo@powersi.com.cn
 * @Date: 2020/7/30 21:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CenterHospitalSyncDO implements Serializable {
    //序列号
    private static final long serialVersionUID = 4742898851656064203L;
    //主键
    private String id;
    //医院编码
    private String hospCode;
    //同步编码
    private String syncCode;
    //同步方式代码：1、全量，2、选择数据同步
    private String typeCode;
    //创建人ID
    private String crteId;
    //创建人姓名
    private String crteName;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date crteTime;

}