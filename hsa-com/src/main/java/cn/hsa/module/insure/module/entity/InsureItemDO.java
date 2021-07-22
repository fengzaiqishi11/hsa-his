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
* @Describe: 表含义： insure：医保 item：项目  表说明： 用
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureItemDO extends PageDO implements Serializable {
        //序列号
        private static final long serialVersionUID = 7617282717635592447L;
        //主键
        private String id;
        //医院编码
        private String hospCode;
        //医保注册编码
        private String insureRegCode;
        //项目类别标志（11.西药、12.中成药、13.中草药、2.项目）
        private String itemMark;
        //医保中心项目编码
        private String itemCode;
        //医保中心项目名称
        private String itemName;
        //医保中心项目类别
        private String itemType;
        //医保中心项目剂型
        private String itemDosage;
        //医保中心项目规格
        private String itemSpec;
        //医保中心项目价格
        private BigDecimal itemPrice;
        //医保中心项目单位
        private String itemUnitCode;
        //生产厂家
        private String prod;
        //自费比例
        private String deductible;
        //限价
        private BigDecimal checkPrice;
        //医保目录标志（0.甲、1.乙、2.全自费）
        private String directory;
        //生效日期
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date takeDate;
        //失效日期
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date loseDate;
        //拼音码
        private String pym;
        //五笔码
        private String wbm;
        //是否有效（SF）
        private String isValid;
        //创建人ID
        private String crteId;
        //创建人姓名
        private String crteName;
        //创建时间
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date crteTime;
        private String spLmtpricDrugFlag; // 特殊限价药品标志
        private String spDrugFlag; // 特殊药品标志
        private String lmtUsedFlag ; // 限制使用标志
        private String limUseExplain; // 限制使用说明
        private String natMedInsureCode;  // 国家医保药品目录甲乙类标识
        private String verName;
        private String hospItemName;
        private String hospItemCode;
        private int size;
        private int num;
        private int recordCounts;

}