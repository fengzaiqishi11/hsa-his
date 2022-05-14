package cn.hsa.module.insure.module.dto;

import cn.hsa.module.insure.module.entity.InsureAccidentalInjuryDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureAccidentalInjuryDTO extends InsureAccidentalInjuryDO{
    private static final long serialVersionUID = 2818662276666937510L;
    private String regCode;
    private String hospName;
    private String name;
    private String keyword;
    private String visitId;
    private String registerNo;
    private String deptName;
    /*@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;        //挂号开始日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;         //挂号结束日期
*/
    private String hospOpnn; // 医院意见

    private String opterName; // 经办人姓名

    private String appyRea; // 申请原因

    private String sympDscr; // 症状描述

    private String appyer; // 申请人

    private String appyerTel; // 申请人电话

    private String appyerAddr; // 申请人地址

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hospAppyTime;
}
