package cn.hsa.module.outpt.prescribe.dto;

import cn.hsa.module.outpt.prescribe.entity.OutptPrescribeTempDetailDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 *@Package_name: cn.hsa.module.outpt.prescribe.dto
 *@Class_name: OutptPrescribeTempDetailDTO
 *@Describe:
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/8/19 16:07
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OutptPrescribeTempDetailDTO extends OutptPrescribeTempDetailDO implements Serializable {
    private static final long serialVersionUID = -233206583234655353L;

    //执行科室名称
    private String execDeptName;

    /**
     * 处方类别代码
     */
    private String type;
    /**
     * 处方类型代码(统一用毒麻特效表示)
     */
    private String phCode;
    //频率ID
    private String rateName;
    //药房ID
    private String pharId;
    //拆零单位
    private String splitUnitCode;
    //路径
    private String drugImgPath;
    //描述
    private String drugRemark;
    //医嘱类型
    private String typeCode;
    //文字医嘱
    private String isWz;
    //大单位
    private String bigUnitCode;
    // 是否住院可用
    private String isIn;
    // 是否门诊可用
    private String isOut;
    // 颜色
    private String color;

}

