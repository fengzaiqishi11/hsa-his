package cn.hsa.module.center.sync.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.center.sync.entity
 * @Class_name: CenterSyncDO
 * @Describe: 医院数据同步
 * @Author: Ou·Mr
 * @Email: oubo@powersi.com.cn
 * @Date: 2020/7/30 21:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CenterSyncDO extends PageDO implements Serializable {
    //序列号
    private static final long serialVersionUID = -7767229645693761358L;
    //主键
    private String id;
    //同步编码
    private String syncCode;
    //同步名称
    private String syncName;
    //同步方式代码：1、全量，2、选择数据同步
    private String typeCode;
    //表名
    private String tableName;
    //关联同步表名
    private String refTableName;
    //是否必须（sf）
    private String isMust;
    //是否有效
    private String isValid;
    //创建人ID
    private String crteId;
    //创建人姓名
    private String crteName;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date crteTime;

}