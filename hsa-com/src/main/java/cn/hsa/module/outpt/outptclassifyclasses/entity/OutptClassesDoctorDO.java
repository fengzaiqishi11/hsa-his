package cn.hsa.module.outpt.outptclassifyclasses.entity;

import cn.hsa.base.PageDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * (OutptClassesDoctor)实体类
 *
 * @author xingyu.xie
 * @since 2020-08-11 08:53:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OutptClassesDoctorDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 593642656076764944L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 类别排班ID
     */
    private String ccId;
    /**
     * 医生ID
     */
    private String doctorId;
    /**
     * 限号数
     */
    private Integer registerNum;
    /**
     * 是否有效（SF）
     */
    private String isValid;
    /**
     *  分诊室ID
     */
    private String clinicId;


}