package cn.hsa.module.insure.module.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Package_name: cn.hsa.module.insure.module.entity
 * @class_name: InsureFunctionLogDO
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/10/11 10:48
 * @Company: 创智和宇
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureFunctionLogDO extends PageDO {
    //主键
    private String id;
    //医院编码
    private String hospCode;
    private String medisCode;
    //实例名
    private String visitId;
    private String medicalRegNo;
    //医保注册编码
    private String msgId;
    //功能编码
    private String msgInfo;
    //功能包地址（当前医保对应的医保功能号）
    private String msgName;
    //功能号描述
    private String inParams;
    private String outParams;
    //是否有效（SF）
    private String code;
    //创建人ID
    private String crteId;
    //创建人姓名
    private String crteName;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;
    private String errorMsg;
    private String isHospital;
    private String status;
    private String keyword;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

}
