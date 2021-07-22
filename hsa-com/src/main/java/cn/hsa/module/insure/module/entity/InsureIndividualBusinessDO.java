package cn.hsa.module.insure.module.entity;

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
* @Describe: 表含义： insure：医保 Individual：个人 business：业务                                                
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureIndividualBusinessDO implements Serializable {
        //序列号
        private static final long serialVersionUID = 2989904311281465017L;
        //主键
        private String id;
        //医院编码
        private String hospCode;
        //就诊id
        private String visitId;
        //个人基本信息id
        private String mibId;
        //业务申请序列号
        private String aaz267;
        //生育序列号
        private String bearNo;
        //业务类型
        private String aka130;
        //业务名称
        private String aka130Name;
        //业务认定编号
        private String voipCode;
        //待遇类型
        private String bka006;
        //待遇名称
        private String bka006Name;
        //申请内容编码
        private String aka083;
        //申请内容名称
        private String aka083Name;
        //申请生效日期
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date aae030;
        //申请失效日期
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date aae031;
        //申请病种编码/疾病编码
        private String aka120;
        //申请病种名称/疾病名称
        private String aka121;
        //受伤部位
        private String vulnerability;
        //先行支付标志（SF）
        private String payMark;
        //创建人ID
        private String crteId;
        //创建人姓名
        private String crteName;
        //创建时间
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date crteTime;

}