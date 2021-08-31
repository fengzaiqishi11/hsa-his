package cn.hsa.module.insure.module.entity;

import cn.hsa.base.PageDO;
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
* @Describe: 表含义： insure：医保 Individual：就诊 cost：费用                                            -&#&
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureIndividualCostDO extends PageDO implements Serializable {
        //序列号
        private static final long serialVersionUID = 566896434835537538L;
        //主键
        private String id;
        //医院编码
        private String hospCode;
        //就诊id
        private String visitId;
        //费用id
        private String costId;
        //结算id
        private String settleId;
        //是否住院（SF）
        private String isHospital;
        //对应医保项目类别
        private String itemType;
        //对应医保项目编码
        private String itemCode;
        //对应医保项目名称
        private String itemName;
        //自付比例
        private String guestRatio;
        //原费用
        private BigDecimal primaryPrice;
        //报销后费用
        private BigDecimal applyLastPrice;
        //顺序号（以患者为单位，生成费用顺序号）
        private String orderNo;
        //传输标志（0：未传输、1：已传输）
        private String transmitCode;
        //创建人ID
        private String crteId;
        //创建人姓名
        private String crteName;
        //创建时间
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date crteTime;

        //（医保返回）处方明细 ID
        private String rxSn;
        //超限价自付金额
        private String overlmtSelfpayAmt;
        //收费项目等级
        private String chrgItemLv;
        //全额自费标志
        private String fulamtOwnpayFlag;
        //自费原因
        private String ownpayRea;
        //医保限价
        private String hiLmtpric;
        private String insureIsTransmit ; // 是否上传到医保
        private String feeBrgNo; // 上传到医保的费用批次号
        private String feedetlSn; //医保费用明细流水号
}