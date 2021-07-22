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
* @Describe: 表含义： insure：医保 item：项目 matching：匹配  
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureItemMatchDO extends PageDO implements Serializable {
        //序列号
        private static final long serialVersionUID = 5920552713507023821L;
        //主键id
        private String id;
        //医院编码
        private String hospCode;
        //医保注册编码
        private String insureRegCode;
        //项目类别标志（11.西药、12.中成药、13.中草药、2.项目）
        private String itemCode;
        //人社部药品id
        private String molssItemId;
        //卫计委药品id
        private String pqccItemId;
        //医院项目id
        private String hospItemId;
        //医院项目名称
        private String hospItemName;
        //医院项目编码
        private String hospItemCode;
        //医院项目类别
        private String hospItemType;
        //医院项目规格
        private String hospItemSpec;
        //医院项目单位
        private String hospItemUnitCode;
        //医院项目剂型
        private String hospItemPrepCode;
        //医院项目价格
        private BigDecimal hospItemPrice;
        //医保中心项目名称
        private String insureItemName;
        //医保中心项目编码
        private String insureItemCode;
        //医保中心项目类别
        private String insureItemType;
        //医保中心项目规格
        private String insureItemSpec;
        //医保中心项目单位
        private String insureItemUnitCode;
        //医保中心项目剂型
        private String insureItemPrepCode;
        //医保中心项目价格
        private BigDecimal insureItemPrice;
        //自费比例
        private String deductible;
        //本位码
        private String standardCode;
        //限价
        private BigDecimal checkPrice;
        //生产厂家
        private String manufacturer;
        //审核状态代码（SHZT）
        private String auditCode;
        //是否匹配（SF）
        private String isMatch;
        //是否传输（SF）
        private String isTrans;
        //是否有效（SF）
        private String isValid;
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
        //创建人ID
        private String crteId;
        //创建人姓名
        private String crteName;
        //创建时间
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date crteTime;

        // 中药使用方式（1复方 2单方），涉及是否报销
        private String tcmdrugUsedWay;

}