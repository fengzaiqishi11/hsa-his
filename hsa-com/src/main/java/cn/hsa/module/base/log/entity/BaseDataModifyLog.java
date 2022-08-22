package cn.hsa.module.base.log.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.base.log.entity
 * @Class_name:: BaseDataModifyLog
 * @Description: 基础数据日志记录实体类
 * @Author: luonianxin
 * @Date: 2022/8/19 11:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseDataModifyLog extends PageDO implements Serializable  {

    private static final long serialVersionUID = -43484976415870913L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     *  项目id
     */
    private String itemId;
    /**
     *  项目编码
     */
    private String itemCode;
    /**
     *  项目名称
     */
    private String itemName;
    /**
     *  业务id
     */
    private String bizId;
    /**
     *   业务类型
     */
    private String bizType;

    /**
     *  修改前数据
     */
    private String beforeModifying;
    /**
     *  修改后数据
     */
    private  String afterModifying;

    /**
     *  修改前后差异对比值
     */
    private String difference;
    /**
     *  调价前价格
     */
    private BigDecimal priceBeforeAdjust;
    /**
     *  调价后价格
     */
    private BigDecimal priceAfterAdjust;
    /**
     *  创建人id
     */
    private String createId;
    /**
     *  创建人名称
     */
    private String createName;
    /**
     *   创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     *  调价时间,（修改时间）
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date adjustmentTime;

}
