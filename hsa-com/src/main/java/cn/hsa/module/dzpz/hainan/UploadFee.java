package cn.hsa.module.dzpz.hainan;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Package_ame: cn.hsa.module.dzpz.hainan
 * @Class_name: hsa-his
 * @Description: 海南电子凭证费用实体
 * @Author: zengfeng
 * @Email: 954123283@qq.com
 * @Date: 2021/1/31  15:47
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UploadFee extends ExtData implements Serializable {
    //计算ID
    private String id;
    //就诊ID
    private String visitId;
    //医院编码
    private String hospCode;
    //应用渠道编号
    private String appId;
    //机构编码
    private String orgCodg;
    //电子凭证机构号
    private String orgId;
    //电子凭证授权 ecToken
    private String ecToken;
    //参保人所在统筹区编码
    private String insuCode;
    //证件号码
    private String idNo;
    //用户姓名
    private String userName;
    //证件类别
    private String idType;
    //医药机构订单号
    private String medOrgOrd;
    //要续方的原处方流水
    private String initRxOrd;
    //急诊标志
    private String erFlag;
    //外伤标志
    private String trumFlag;
    //费用类型
    private String feeType;
    //门诊类别
    private String otpType;
    //诊疗类别
    private String medType;
    //费用总金额
    private BigDecimal feeSumamt;
    //收费日期
    private String chrgDate;
    //收费时间
    private String chrgTime;
    //医保挂号流水号
    private String rgstSn;
    //挂号费用
    private BigDecimal rgstFee;
    //挂号科室名称
    private String rgstDeptName;
    //医生姓名
    private String drName;
    //是否医改网点
    private String medreformDot;
    //医生科室编号
    private String drDeptCodg;
    //医生证件号码
    private String drCertNo;
    //明细上传数量
    private Integer rxItemVal;
    //住院类别
    private String iptType;
    //住院/门诊号
    private String iptOpNo;
    //住院科室编码
    private String iptDeptCodg;
    //住院科室名称
    private String iptDeptName;
    //住院起始日期
    private String iptBegnDate;
    //住院截止日期
    private String iptEndDate;
    //离院方式
    private String dscgWay;
    //住院天数
    private Integer iptDays;
    //计划生育手术类别
    private String birctrlType;
    //生育类别(方式)
    private String birmtd;
    //胎儿数
    private String babyNum;
    //计划生育手术或生育日期
    private String birctrlDate;
    //怀孕天数
    private Integer prgDays;
    //医保扩展数据
    private ExtData extData;
    //诊断或症状明细
    private List<DiseList> diseList;
    //病情编码列表
    private List<CondList> condList;
    //费用明细
    private List<RxList> rxList;
    //支付 token
    private String payToken;
    //支付 订单
    private String payOrdId;

}
