package cn.hsa.module.phar.pharoutdistribute.dto;

import cn.hsa.module.phar.pharoutdistribute.entity.PharOutDistributeDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.phar.phardistribute.dto
 * @Class_name: PharDistributeDTO
 * @Describe:
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/31 15:37
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class PharOutDistributeDTO extends PharOutDistributeDO implements Serializable {

    private static final long serialVersionUID = -17497614503067092L;

    // 用于搜索身份证，姓名，手机号的关键字
    private String keyword;
    // 姓名
    private String name;
    // 性别代码
    private String genderCode;
    // 年龄
    private String age;
    // 年龄单位
    private String ageUnitCode;
    // 身法证号
    private String certNo;
    // 手机号
    private String phone;
    // 发药窗口名
    private String windowName;
    // 发药明细数据
    private List<PharOutDistributeDetailDTO> pharOutDistributeDetailDTOList;
    // 发药明细批次汇总
    private List<PharOutDistributeAllDetailDTO> pharOutDistributeBatchDetailDTOs;
    // 发药还是退药的标识
    private String type;
    // 就诊号
    private String visitNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;        //开始日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;         //结束日期

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;        //开始日期
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;         //结束日期

    private String patientInfo; //病人信息

    private String returnFlag;   // 查询报表门诊/住院标志

    private String retrunType;  //退药类型

    private String pharName; //退药药房

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date refundTime; // 领药申请时间
    private String statusCode;// 住院状态
    private String deptToldId;  // 开方/开嘱科室id
    private String deptName; // 开方/开嘱科室名称

    private String orderStatus;// 退药状态，0：未退药 1：已退药

}
