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
* @Describe: 表含义： insure：医保 Individual：个人 basic：基本                                             -&#
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureIndividualBasicDO extends PageDO implements Serializable {
        //序列号
        private static final long serialVersionUID = -8868824449786059867L;
        //主键id
        private String id;
        //医院编码
        private String hospCode;
        //是否住院（SF）
        private String isHospital;
        //个人电脑号
        private String aac001;
        //个人业务号(工伤、生育)
        private String injuryBorthSn;
        //分级统筹中心编码
        private String aaa027;
        //分级统筹中心名称
        private String aaa027Name;
        //姓名
        private String aac003;
        //性别
        private String aac004;
        //公民身份号码
        private String aac002;
        //社会保障号码
        private String aaz500;
        //联系电话
        private String aae005;
        //出生日期
        private String aac006;
        //地区编码
        private String orgcode;
        //单位管理码
        private String aab999;
        //单位类型
        private String aab019;
        //单位编码
        private String aab001;
        //单位名称
        private String bka008;
        //人员类别编码
        private String bka035;
        //人员类别名称
        private String bka035Name;
        //人员状态编码
        private String aac008;
        //人员状态名称
        private String aac008Name;
        //公务员级别编码
        private String bac001;
        //公务员级别名称
        private String bac001Name;
        //业务类型
        private String aka130;
        //业务类型名称
        private String aka130Name;
        //待遇类型
        private String bka006;
        //待遇类型名称
        private String bka006Name;
        //补助类型
        private String aac148;
        //补助类型名称
        private String aac148Name;
        //用工形式编码
        private String aac013;
        //用工形式名称
        private String aac013Name;
        //险种类型（码表AAE140）
        private String aae140;
        //基金冻结状态
        private String bka888;
        //个人帐户余额
        private BigDecimal akc252;
        //参保身份
        private String aac066;
        //所属行政区代码.常住地
        private String aab301;
        //人员缴费状态{1参保缴费，2暂停缴费，3终止缴费}
        private String aac031;
        //上次住院入院日期
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date aae030Last;
        //上次住院出院日期
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date aae031Last;
        //特殊业务申请有效开始时间
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date aae030Special;
        //特殊业务申请有效结束时间
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date aae031Special;
        //医疗待遇申请事件ID
        private String aaz267;
        //参保地中心编码
        private String baa027;
        //参保地中心名称
        private String baa027Name;
        //疾病ICD编码
        private String akc193;
        //疾病ICD名称
        private String akc193Name;
        //低保对象标识
        private String aac158;
        //参加公务员医疗补助标识
        private String akc026;
        //参保地行政区划代码(指参保人所在地的行政区划代码)
        private String baa301;
        //参保地社会保险经办机构名称(指参保人所在地的社会保险经办机构名称)
        private String aab300;
        //参保人员类别
        private String akc009;
        //本年住院次数
        private Integer bka010;
        //套餐标识
        private String bkh015;
        //创建人ID
        private String crteId;
        //创建人姓名
        private String crteName;
        //创建时间
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date crteTime;
        // 卡识别码
        private String cardIden;
        //统计时间段开始时间
        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date startDate;
        //统计时间段结束时间
        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date endDate;

}