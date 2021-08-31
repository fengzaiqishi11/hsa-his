package cn.hsa.module.insure.module.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.insure.module.dto
 * @class_name: InsureDiseaseRecord
 * @Description: 医保统一支付平台：人员慢特病备案数据库实体类
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/3/30 13:44
 * @Company: 创智和宇
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureDiseaseRecordDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -2412250406809934052L;
    private String id;
    private String hospCode;
    private String visitId;
    //险种类型
    private String insutype;
    // 人员编号
    private String psnNo;
    //门慢门特病种目录代码
    private String opspDiseCode;
    //门慢门特病种名称
    private String opspDiseName;
    //联系电话
    private String tel;
    //联系地址
    private String addr;
    //参保机构医保区划
    private String insuOptins;
    //鉴定定点医药机构编号
    private String ideFixmedinsNo;
    //鉴定定点医药机构名称
    private String ideFixmedinsName;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    //医院鉴定日期
    private Date hospIdeDate;
    //诊断医师编码
    private String diagDrCodg;
    //诊断医师姓名
    private String diagDrName;
    //开始日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date begndate;
    // 结束日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date enddate;
    // 待遇申报明细流水号
    private String trtDclaDetlSn;
    private String isRecord; //是否备案
    private String memo; //备注
    private String crteName;
    private String crteId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;

}
