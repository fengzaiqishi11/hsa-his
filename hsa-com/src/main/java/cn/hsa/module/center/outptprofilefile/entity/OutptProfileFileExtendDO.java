package cn.hsa.module.center.outptprofilefile.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.outpt.profilefile.entity
 * @Class_name:: OutptProfileFileExtendDO
 * @Description:
 * @Author: liaojunjie
 * @Date: 2020/8/19 17:26
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutptProfileFileExtendDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 542055524696754465L;
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
     * 住院档案号（入院登记回写）
     */
    private String inProfile;
    /**
     * 门诊档案号（挂号时生成）
     */
    private String outProfile;
    /**
     * 累计门诊次数
     */
    private Integer totalOut;
    /**
     * 累计住院次数
     */
    private Integer totalIn;
    /**
     * 门诊最后就诊时间
     */
    private Date outptLastVisitTime;
    /**
     * 住院最后就诊时间
     */
    private Date inptLastVisitTime;
    /**
     * 病人来源途径代码（LYTJ）
     */
    private String sourceTjCode;
    /**
     * 病人来源途径备注
     */
    private String sourceTjRemark;
    /**
     * 创建人ID（医院人员ID）
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