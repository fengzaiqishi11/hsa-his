package cn.hsa.module.interf.extract.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.io.Serializable;
import cn.hsa.base.PageDO;
/**
 * 消耗量主表，记录同步的数据(ExtractData)实体类
 *
 * @author gory
 * @since 2022-07-06 10:13:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class ExtractDataDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 804886897376823838L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 抽取数量
     */
    private Integer extractNum;
    /**
     * 抽取时间
     */
    private Date extractTime;
    /**
     * 抽取类型（1:药房消耗报表）
     */
    private String extractType;
    /**
     * 耗时，存毫秒数
     */
    private Integer consumeTime;
    /**
     * 同步状态
     */
    private String extractStatus;


}

