package cn.hsa.module.insure.module.dto;

import cn.hsa.module.insure.module.entity.InsureDiseaseRecordDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Package_name: cn.hsa.module.insure.module.dto
 * @class_name: InsureDiseaseRecordDTO
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/3/30 13:56
 * @Company: 创智和宇
 **/
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureDiseaseRecordDTO extends InsureDiseaseRecordDO {
    private static final long serialVersionUID = 2818662276666937510L;
    private String regCode;
    private String hospName;
    private String name;
    private String keyword;
    private String visitId;
    private String registerNo;
    private String certNo;
    private String deptName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;        //挂号开始日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;         //挂号结束日期

    private String hospOpnn; // 医院意见

    private String opterName; // 经办人姓名

    private String appyRea; // 申请原因

    private String sympDscr; // 症状描述

    private String appyer; // 申请人

    private String appyerTel; // 申请人电话

    private String appyerAddr; // 申请人地址

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hospAppyTime;

    private String bka896;


}
