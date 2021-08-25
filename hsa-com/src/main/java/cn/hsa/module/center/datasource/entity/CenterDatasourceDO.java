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
 * @Class_name: CenterDatasourceDO
 * @Describe: 医院数据源
 * @Author: Ou·Mr
 * @Email: oubo@powersi.com.cn
 * @Date: 2020/7/30 21:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CenterDatasourceDO extends PageDO implements Serializable {
    //序列号
    private static final long serialVersionUID = 1067110238686444312L;
    //主键
    private String id;
    //数据源编码
    private String code;
    //数据源名称
    private String name;
    //数据源类型
    private String typeCode;
    //数据源驱动
    private String driverName;
    //数据源URL
    private String url;
    //数据源账号
    private String username;
    //数据源密码
    private String password;
    //创建人ID
    private String crteId;
    //创建人姓名
    private String crteName;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date crteTime;
}