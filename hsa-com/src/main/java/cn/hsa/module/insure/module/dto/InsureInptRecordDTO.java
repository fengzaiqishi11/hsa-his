package cn.hsa.module.insure.module.dto;

import cn.hsa.module.insure.module.entity.InsureInptRecordDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Package_name: cn.hsa.module.insure.module.dto
 * @class_name: InsureInptRecordDTO
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/4/7 9:36
 * @Company: 创智和宇
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureInptRecordDTO extends InsureInptRecordDO {
    private String keyword;
    private String hospName;
    private String isHospital;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date begntime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endtime;
    private String medicineOrgCode;
    private String bka006;
    private String visitIcdCode;
    private String visitId;
    private String visitIcdName;
    private String name;
    private String visitDrptName;
    private String visitBerth;
    private String aka130Name;
    private String tel;
    private String address;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date regsiterTime;
    private String aac001;
    private String aae140; // 险种类型
    private Date inTime;//入院时间
    private Date outTime;//出院时间
    private String outDiseaseName;//出院诊断名称
    private String outDiseaseIcd10;//出院诊断编码
}
