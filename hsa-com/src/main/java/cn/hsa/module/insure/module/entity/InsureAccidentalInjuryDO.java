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

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureAccidentalInjuryDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -2412250406809934052L;
   private String id;
   private String trtDclaDetlSn;
   private String hospCode;
   private String visitId;
   private String dclaSouc;// 申报来源
   private String insutype;//险种类型
   private String psnNo;//人员编号
   private String psnInsuRltsId;//人员参保关系 ID
   private String psnCertType;//人员证件类型
   private String certno;//证件号码
   private String psnName;//人员姓名
   private String gend;//性别
   private String naty;//民族
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date brdy;//出生日期
   private String tel;//联系电话
   private String addr;//联系地址
   private String insuAdmdvs;//参保所属医保区划
   private String empNo;//单位编号
   private String empName;//单位名称
   private String mdtrtareaAdmdvs;//就医地医保区划
   private String fixmedinsCode;//定点医药机构编号
   private String fixmedinsName;//定点医药机构名称
   private String hospLv;//医院等级
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date admTime;//入院时间
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date trumTime;//受伤时间
   private String trumSite;//受伤地点
   private String trumRea;//致伤原因
   private String chkPayFlag;//审核支付标志
   private String agnterName;//代办人姓名
   private String agnterCertType;//代办人证件类型
   private String agnterCertno;//代办人证件号码
   private String agnterTel;//代办人联系方式
   private String agnterAddr;//代办人联系地址
   private String agnterRlts;//代办人关系
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date begndate;//开始日期
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date enddate;//结束日期
   private String memo;//备注
   private String crteId;
   private String crteName;
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date crteTime;

}
