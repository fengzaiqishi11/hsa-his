package cn.hsa.module.mris.mrisHome.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.mris.entity
 * @Class_name: MrisTumourChemoDO
 * @Describe: 病案放疗信息表
 * @author LiaoJiGuang
 * @since 2020-09-22 15:14:38
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MrisTumourInfoDO implements Serializable {
    private static final long serialVersionUID = 141110650201636566L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 病案ID（mris_base_info.id）
     */
    private String mbiId;
    /**
     * 就诊ID
     */
    private String visitId;
    /**
     * 放疗方式代码（FLFS）
     */
    private String flfsCode;
    /**
     * 放疗方式名称
     */
    private String flfsName;
    /**
     * 放疗程序代码（FLCX）
     */
    private String flcxCode;
    /**
     * 放疗程序名称
     */
    private String flcxName;
    /**
     * 放疗装置代码（FLZZ）
     */
    private String flzzCode;
    /**
     * 放疗装置名称
     */
    private String flzzName;
    /**
     * 原发灶剂量
     */
    private String yfzjl;
    /**
     * 原发灶次数
     */
    private String yfzcs;
    /**
     * 原发灶天数
     */
    private String yfzts;
    /**
     * 原发灶开始日期
     */
    private Date yfzkssj;
    /**
     * 原发灶结束时间
     */
    private Date yfzjssj;
    /**
     * 区域淋巴结剂量
     */
    private String qylbjjl;
    /**
     * 区域淋巴结次数
     */
    private String qylbjcs;
    /**
     * 区域淋巴结天数
     */
    private String qylbjts;
    /**
     * 区域淋巴结开始时间
     */
    private Date qylbjkssj;
    /**
     * 区域淋巴结结束时间
     */
    private Date qylbjjssj;
    /**
     * 转移灶名称
     */
    private String zyzmc;
    /**
     * 转移灶剂量
     */
    private String zyzjl;
    /**
     * 转移灶次数
     */
    private String zyzcs;
    /**
     * 转移灶天数
     */
    private String zyzts;
    /**
     * 转移灶开始时间
     */
    private Date zyzkssj;
    /**
     * 转移灶结束时间
     */
    private Date zyzjssj;
    /**
     * 化疗方式代码（HLFS）
     */
    private String hlfs;
    /**
     * 化疗方式名称
     */
    private String hlfsmc;
    /**
     * 化疗方法代码（HLFF）
     */
    private String hlff;
    /**
     * 化疗方法名称
     */
    private String hlffmc;
    /**
     * 肿瘤分期类型代码（ZLFQ）
     */
    private String zlfqlx;
    /**
     * 分期T
     */
    private String fqt;
    /**
     * 分期N
     */
    private String fqn;
    /**
     * 分期M
     */
    private String fqm;
    /**
     * 分期
     */
    private String fq;
    /**
     * 分期编号
     */
    private String fqmh;

}