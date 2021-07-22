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
* @Describe: 表含义： insure：医保 diagnose：疾病  表说明：                                    -&
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureDiseaseDO extends PageDO implements Serializable {
        //序列号
        private static final long serialVersionUID = -6909163329563864797L;
        //主键
        private String id;
        //医院编码
        private String hospCode;
        //医保机构注册编码
        private String insureRegCode;
        //中心疾病ID
        private String insureIllnessId;
        //中心疾病编码
        private String insureIllnessCode;
        //中心疾病名称
        private String insureIllnessName;
        //ICD编码
        private String icd10;
        //拼音码
        private String pym;
        //五笔码
        private String wbm;
        //生效日期
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date takeDate;
        //失效日期
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date loseDate;
        //备注
        private String remark;
        //创建人ID
        private String crteId;
        //创建人姓名
        private String crteName;
        //创建时间
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date crteTime;
        private String downLoadType;
        private String verName;
        private String ver;
        private int size;
        private int num;
        private int recordCounts;

}