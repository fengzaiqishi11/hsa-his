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
 * @Package_name: cn.hsa.module.insure.module.entity
 * @class_name: InsureInptRecordDO
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/4/7 9:23
 * @Company: 创智和宇
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureInptRecordDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -2339664304930982846L;
    private String id;
    private String visitId;
    private String hospCode;
    private String psnNo;
    private String regCode;
    private String insureRegCode;
    private String memo;
    private String tel;
    private String insutype;
    private String addr;
    private String insuOptins;
    private String diagCode;
    private String diagName;
    private String diseCondDscr;
    private String reflinMedinsNo;
    private String reflinMedinsName;
    private String mdtrtareaAdmdvs;
    private String hospAgreReflFlag;
    private String reflType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reflDate;
    private String reflRea;
    private String reflOpnn;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date begndate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date enddate;
    private String trtDclaDetlSn;
    private String crteId;
    private String crteName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;
    private String reflTypeName;

}
