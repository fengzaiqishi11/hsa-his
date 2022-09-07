package cn.hsa.module.outpt.prescribeDetails.dto;

import cn.hsa.module.outpt.prescribeDetails.entity.OutptPrescribeDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.prescribeDetails.dto
 * @Class_name: OutptPrescribeDetailsDTO
 * @Describe: 门诊处方DTO传输对象
 * @Author: zengfeng
 * @Email: zengfeng@powersi.com.cn
 * @Date: 2020/9/9 9:13
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OutptPrescribeDTO extends OutptPrescribeDO {
    //就诊类型
    private String keyword;
    //就诊类型
    private String visitCode;
    //毒麻药品级别代码（DMYPJB）
    private String phCode;
    //模板名称
    private String name;
    //诊断名称
    private String diseaseNames;
    /**
     * 处方明细集合
     */
    private List<OutptPrescribeDetailsDTO> outptPrescribeDetailsDTOList;
    //诊断名称
    private String ba;
    //出生日期
    private String birthday;
    //处方主表ID串
    private String ids;
    //是否修改
    private String isUpdate;
    //处方金额
    private BigDecimal cfje;
    //处方类金额
    private BigDecimal cflJf;
    //是否主诊断
    private String isMain;
    //单位
    private String unitCode;
    //结算单号
    private String settleNo;
    private String[] typeCodeList;
    //就诊开始日期
    private String startDate;
    //就诊结束日期
    private String endDate;
    private String lmtUserFlag ; //限制使用标志

    private String limUserExplain; // 限制使用说明
    private Boolean reimburseFlag ; // 报销标志
    private String itemId;//项目/药品/材料

    private List<Map> outptPrescribeDetailsDtoPrintList;

    private String orId;  // 领药申请id

    /**
     * @Description: 用于微信小程序接口
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date 2021-06-22 16:35
     */
    // 诊断名合并，String ','分隔
    private String diagnoseNames;
    // 处方金额合计
    private BigDecimal orderPrice;
    // 待缴费支付的处方金额总计
    private BigDecimal zfje;
    // 开方时间
    private String prescribeTime;
    /**
     * 配药打印处方单患者信息
     */
    private Map<String, Object> singlePatInfo;
    private List<String> diagnoseList;
    private String tcmDiseaseId; // 中医诊断Id
    private String tcmDiseaseName; // 中医诊断名称
    private String tcmSyndromesId; // 中医症候id
    private String tcmSyndromesName; // 中医症候名称
    private String diagnoseType; // 诊断类型: 0 西医; 1 中医
    private String nationCode; //
    private String nationName; //
    private String isValid;// 是否有效
    private String visitNo;//就诊号
    private String decoctionMethod;//煎药方式
    private Integer age; // 年龄
    private String certNo; // 年龄


    private String itemName  ;  //申请项目
    private String execDeptId  ;  //执行科室ID
    private String execDeptName;  //执行科室ID
}

