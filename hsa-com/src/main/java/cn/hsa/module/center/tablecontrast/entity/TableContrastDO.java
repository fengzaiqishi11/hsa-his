package cn.hsa.module.center.tablecontrast.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 1. TableContrast信息表(TableContrast)实体类
 * @author makejava
 * @since 2020-07-30 16:13:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TableContrastDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -42943141547367717L;
    /**
     * 主键
     */
    private String id;
    /**
     * 表名（中心端的 - sync_xxx）
     */
    private String tableName;
    /**
     * 同步表名（业务系统 - base_xxx）
     */
    private String syncTableName;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
