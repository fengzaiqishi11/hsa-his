package cn.hsa.module.outpt.fees.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
/**
* @Package_name: cn.hsa.module.outpt.fees.entity
* @Class_name: OutptPrescribeDO
* @Describe: 处方信息；处方类别：1、西药，2、材料，3、中草药，4、检验LIS，5、检查PACS，6、处置 处方类型：1、普通，2
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutptPrescribeDO extends PageDO implements Serializable {
        //序列号
        private static final long serialVersionUID = 1459094207342131603L;
        //主键
        private String id;
        //医院编码
        private String hospCode;
        //就诊ID
        private String visitId;
        //诊断ID
        private String diagnoseId;
        //处方单号
        private String orderNo;
        //开方医生ID
        private String doctorId;
        //开方医生名称
        private String doctorName;
        //开方科室ID
        private String deptId;
        //开方科室名称
        private String deptName;
        //处方类别代码（CFLB）
        private String typeCode;
        //处方类型代码（CFLX）
        private String prescribeTypeCode;
        //结算ID
        private String settleId;
        //备注
        private String remark;
        //是否结算（SF）
        private String isSettle;
        //是否作废（SF）
        private String isCancel;
        //是否打印（SF）
        private String isPrint;
        //中草药是否本院煎药（SF）
        private String isHerbHospital;
        //中草药付（剂）数
        private BigDecimal herbNum;
        //中草药用法（ZYYF）
        private String herbUseCode;
        //体重（儿科）
        private BigDecimal weight;
        //代办人姓名（精麻）
        private String agentName;
        //代办人身份编号（精麻）
        private String agentCertNo;
        //作废人ID
        private String cancelId;
        //作废人
        private String cancelName;
        //作废时间
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date cancelDate;
        //作废原因
        private String cancelReason;
        //创建人ID
        private String crteId;
        //创建人姓名
        private String crteName;
        //创建时间（开方日期）
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Timestamp crteTime;

}