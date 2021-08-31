package cn.hsa.module.inpt.pasttreat.entity;

import cn.hsa.base.PageDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.inpt.pasttreat.entity
 * @Class_name:: InptPastTreatDO
 * @Description:  既往诊疗史数据库实体对象
 * @Author: fuhui
 * @Date: 2020/9/17 14:16
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InptPastTreatDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -16455362147413508L;
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
     * 疾病ID
     */
    private String diseaseId;
    /**
     * 就诊科室ID
     */
    private String deptId;
    /**
     * 就诊科室名称
     */
    private String deptName;
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