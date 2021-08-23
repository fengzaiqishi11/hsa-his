package cn.hsa.module.outpt.prescribeDetails.dto;

import cn.hsa.module.outpt.prescribeDetails.entity.OutptPrescribeDetailsExtDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Package_name: cn.hsa.module.outpt.prescribeDetails.entity
 * @Class_name: OutptPrescribeDetailsDO
 * @Describe: 门诊处方明细执行
 * @Author: zengfeng
 * @Email: zengfeng@powersi.com.cn
 * @Date: 2020/9/8 9:13
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OutptPrescribeDetailsExtDTO extends OutptPrescribeDetailsExtDO {
    /**
     * 搜索条件(姓名/就诊号)
     */
    private String keyword;
    //财务分类
    private String bfcCode;
    //财务分类ID
    private String bfcId;
    //费用来源
    private String sourceCode;
    //医嘱类别
    private String yzlb;
    //是否计费
    private String isCost;
    // 处方号
    private String orderNo;
    // 开方时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;
    // 处方类别
    private String typeCode;
    // 项目分类名称
    private String itemCodeName;
    // 中草药脚注编码
    private String herbNoteCode;
    // 中草药脚注名称
    private String herbNoteName;
    // 剂量单位名称
    private String dosageUnitName;
    // 剂型名称
    private String prepName;
    // 频率名称
    private String rateName;
    // 是否皮试
    private String isSkin;
    // 最小执行时间，即用药开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date minPlanExecDate;
    // 最大执行时间，即用药结束时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date maxPlanExecDate;
    // 用药天数
    private Integer useDays;
    // 用法名称
    private String usageName;
    // 药品分类代码
    private String drugTypeCode;
    // 药品分类名称
    private String drugTypeName;
    // 药品大类代码
    private String drugBigTypeCode;
    // 药品大类名称
    private String drugBigTypeName;
}

