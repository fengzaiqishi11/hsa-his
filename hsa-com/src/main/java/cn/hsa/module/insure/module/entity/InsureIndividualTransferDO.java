package cn.hsa.module.insure.module.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
* @Package_name: 
* @Class_name: DO
* @Describe: 表含义： medical：医保 Individual：个人 inpatient：住院
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureIndividualTransferDO implements Serializable {
        //序列号
        private static final long serialVersionUID = -827026302905623812L;
        //主键
        private String id;
        //医院编码
        private String hospCode;
        //就诊id
        private String visitId;
        //个人基本信息id
        private String mibId;
        //转院关联医院编号
        private String relaHospId;
        //转院关联就医登记号
        private String relaAaz217;
        //转院申请序列号
        private String aaz267;
        //入院标志
        private String bka068;
        //本年度住院次数
        private String akc200;
        //本年住院申报累计金额
        private BigDecimal declareYear;
        //创建人ID
        private String crteId;
        //创建人姓名
        private String crteName;
        //创建时间
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date crteTime;

}