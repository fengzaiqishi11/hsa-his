package cn.hsa.module.inpt.pasttreat.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.inpt.pasttreat.entity
 * @Class_name:: InptPastAdrs
 * @Description: 不良反应史数据库映射对象
 * @Author: fuhui
 * @Date: 2020/9/17 14:35 
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InptPastAdrsDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -62695742280777073L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 个人档案ID
     */
    private String profileId;
    /**
     * 就诊ID
     */
    private String visitId;
    /**
     * 药品ID
     */
    private String drugId;
    /**
     * 药品名称
     */
    private String drugName;
    /**
     * 不良反应症状
     */
    private String adrs;
    /**
     * 发生原因
     */
    private String reason;
    /**
     * 严重程度
     */
    private String serious;
    /**
     * 发生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    /**
     * 创建人ID
     */
    private String crteId;
    /**
     * 创建人姓名
     */
    private String crteName;
    /**
     * 创建时间
     */
    private Date crteTime;

}