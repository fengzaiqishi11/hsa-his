package cn.hsa.module.phar.pharoutdistributedrug.dto;

import cn.hsa.module.phar.pharoutdistributedrug.entity.PharOutReceiveDetailDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
* @Package_name: cn.hsa.module.phar.pharoutdistributedrug.entity
* @class_name: PharOutReceiveDO
* @Description: 门诊领药申请表DTO
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/9/2 15:58
* @Company: 创智和宇
**/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PharOutReceiveDetailDTO extends PharOutReceiveDetailDO implements Serializable {

    private static final long serialVersionUID = 402755114841777814L;

    //发药窗口
    private String windowId;
    //处方单号
    private String orderNo;
    //组号
    private String groupNo;
    //批号
    private String batchNo;
    //库存数量
    private int stockNum;
    //库位
    private String bizName;
    // 就诊号
    private String visitNo;
    //患者名
    private String name;
    // 患者年龄单位
    private String ageUnitCode;
    //患者年龄
    private String age;
    // 性别
    private String genderCode;
    // 就诊科室名
    private String deptName;
    // 频率名称
    private String rateName;
    // 频率备注
    private String rateRemark;
    //  每日次数
    private BigDecimal dailyTimes;
    // 用药天数
    private String useDays;
    // 用法代码
    private String usageCode;
    // 用法列表(输液列表)
    private List<String> usageCodeList;
    // 门诊治疗卡打印是否通过用药方式为输液过滤的打印启用标志（1：表示启用，其他或者空表示不启用）
    private String printFlag;
    // 总数量
    private String totalNum;
    // 处方的每次数量
    private String preNum;
    // 数量单位
    private String numUnitCode;
    //生产厂家
    private String prodName;
    //配药时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date assignTime;
    //配药人名称
    private String assignUserName;
    //处方类别
    private String typeCode;

    private String bigTypeCode;
    /**
     * 速度(SD)
     */
    private String speedCode;
    // 是否皮试
    private String isSkin;
    // 开方医生
    private String doctorName;
    // 医保编码
    private String nationCode;
    // 国家编码
    private String nationName;
    private String diseaseName;
    private String aka130;
    private String remark;
}
