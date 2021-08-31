package cn.hsa.module.insure.module.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
/**
* @Package_name: 
* @Class_name: DO
* @Describe: 表含义： insure：医保 diagnose：疾病 matching：匹配                                          -&
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureDiseaseMatchDO extends PageDO implements Serializable {
        //序列号
        private static final long serialVersionUID = 2589264667872554315L;
        //主键id
        private String id;
        //医院编码
        private String hospCode;
        //医保注册编码
        private String insureRegCode;
        //医院疾病ID
        private String hospIllnessId;
        //医院ICD编码
        private String hospIllnessCode;
        //医院ICD名称
        private String hospIllnessName;
        //医保中心疾病ID
        private String insureIllnessId;
        //医保中心ICD编码
        private String insureIllnessCode;
        //医保中心ICD名称
        private String insureIllnessName;
        //是否匹配（SF）
        private String isMatch;
        //是否传输（SF）
        private String isTrans;
        //审批状态代码（SHZT）
        private String auditCode;
        //备注
        private String remark;
        //创建人ID
        private String crteId;
        //创建人姓名
        private String crteName;
        // 手术治疗分型
        private String treatmentCode;

        // 手术分值
        private String operNum;

        // 非手术分值
        private String unoperNum;

        //创建时间
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date crteTime;
        private String typeCode; // 疾病分类

}