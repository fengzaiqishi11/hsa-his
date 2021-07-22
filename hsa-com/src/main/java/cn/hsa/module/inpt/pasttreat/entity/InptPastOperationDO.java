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
 * @Class_name:: InptPastOperationDO
 * @Description: 既往手术史数据库映射对象
 * @Author: fuhui
 * @Date: 2020/9/17 14:28
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InptPastOperationDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 504381432483093644L;
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
     * 主诉
     */
    private String chiefComplaint;
    /**
     * 手术ID（ICD-9）
     */
    private String diseaseId;
    /**
     * 手术医生ID
     */
    private String operationId;
    /**
     * 手术医生名称
     */
    private String operationName;
    /**
     * 手术时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String operationTime;
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