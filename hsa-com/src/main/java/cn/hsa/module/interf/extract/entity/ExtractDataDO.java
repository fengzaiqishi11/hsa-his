package cn.hsa.module.interf.extract.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.io.Serializable;
import cn.hsa.base.PageDO;
import org.springframework.format.annotation.DateTimeFormat;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date extractTime;
    /**
     * 进销存时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date extractDate;
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

