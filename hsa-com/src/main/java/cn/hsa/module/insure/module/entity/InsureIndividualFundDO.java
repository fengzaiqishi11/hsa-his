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
* @Describe: 表含义： insure：医保 Individual：个人 fund：基金                                            -&#&
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureIndividualFundDO implements Serializable {
        //序列号
        private static final long serialVersionUID = -3525390907423052520L;
        //主键id
        private String id;
        //医院编码
        private String hospCode;
        //就诊id
        private String visitId;
        //个人基本信息id
        private String mibId;
        //基金编号
        private String fundId;
        //基金名称
        private String fundName;
        //基金状态标志（"0"——"正常"             "1"——"冻结"            "2"——"暂停参保" "3"——"中止参保" "9"—— "未参保"）
        private String indiFreezeStatus;
        //创建人ID
        private String crteId;
        //创建人姓名
        private String crteName;
        //创建时间
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date crteTime;
        private String fundPayType ; // 基金支付类型
        private BigDecimal inscpScpAmt; // 符合政策范围金额
        private BigDecimal crtPaybLmtAmt; // 本次可支付限额金额
        private BigDecimal fundPayamt ; // 基金支付金额
        private String fundPayTypeName ; // 基金支付类型名称
        private String setlProcInfo ; // 结算过程信息
        private String insureSettleId; // 医保结算id

}