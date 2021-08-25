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
 * @Describe: 病案化疗信息表
 * @author LiaoJiGuang
 * @since 2020-09-22 15:14:38
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MrisTumourChemoDO implements Serializable {
    private static final long serialVersionUID = 327405647551387438L;
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
     * 化疗编号
     */
    private String chemoCode;
    /**
     * 化疗时间
     */
    private Date chemoTime;
    /**
     * 化疗药物及计量
     */
    private String chemoDurg;
    /**
     * 化疗疗程
     */
    private String chemoCourse;
    /**
     * 疗效代码（LX）
     */
    private String effectCode;
}