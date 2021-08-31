package cn.hsa.module.dzpz.hainan;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_ame: cn.hsa.module.dzpz.hainan
 * @Class_name: hsa-his
 * @Description:
 * @Author: zengfeng
 * @Email: 954123283@qq.com
 * @Date: 2021/1/31  16:10
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RxList implements Serializable {
    //结算ID
    private String id;
    //处方号
    private String rxno;
    //处方明细流水号
    private String rxDetlNo;
    //项目编号
    private String itemNo;
    //项目名称
    private String itemName;
    //发票项目编号
    private String invoItemNo;
    //是否医保项目
    private String hiItem;
    //项目单价
    private String itemPric;
    //项目单位
    private String itemEmp;
    //项目规格
    private String itemSpec;
    //项目数量
    private String itemCnt;
    //项目金额
    private String itemAmt;
    //商品条形编码
    private String prodBarc;
    //药品频率
    private String drugFrqu;
    //药品用量
    private String drugDos;
    //药品天数
    private String drutDays;
    //剂型
    private String dosform;
    //单位用药单位
    private String empMedcEmp;
    //取药总量
    private String drugTotlnt;
    //取药总量单位
    private String drugTotlntEmp;
    //给药途径
    private String drugDelvWay;
    //药量天数
    private String drugCntDays;
    //医生姓名
    private String drName;
    //医生编号
    private String drCertNo;
    //处方日期
    private String rxDate;
    //处方科室编码
    private String rsDeptCodg;
    //处方科室
    private String rsDept;
}
