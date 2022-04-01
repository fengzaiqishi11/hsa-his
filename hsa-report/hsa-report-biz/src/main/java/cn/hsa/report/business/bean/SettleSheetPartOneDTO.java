package cn.hsa.report.business.bean;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName SettleSheetPartOneDTO
 * @Deacription 结算单基本信息
 * @Author liuzhuoting
 * @Date 2021/11/27 14:51
 * @Version 3.0
 */
@Data
public class SettleSheetPartOneDTO implements Serializable {

    @ApiModelProperty(name = "mdtrtId", value = "就诊号/单据号码")
    private String mdtrtId;

    @ApiModelProperty(name = "psnName", value = "姓名")
    private String psnName;

    @ApiModelProperty(name = "gend", value = "性别")
    private String gend;

    @ApiModelProperty(name = "gendName", value = "性别")
    private String gendName;

    @ApiModelProperty(name = "age", value = "年龄")
    private Integer age;

    @ApiModelProperty(name = "psnNo", value = "个人编号")
    private String psnNo;

    @ApiModelProperty(name = "psnIdetType", value = "补助类别")
    private String psnIdetType;

    @ApiModelProperty(name = "psnIdetTypeName", value = "补助类别")
    private String psnIdetTypeName;

    @ApiModelProperty(name = "empName", value = "单位名称")
    private String empName;

    @ApiModelProperty(name = "psnType", value = "人员类别")
    private String psnType;

    @ApiModelProperty(name = "psnTypeName", value = "人员类别")
    private String psnTypeName;

    @ApiModelProperty(name = "tel", value = "联系电话")
    private String tel;

    @ApiModelProperty(name = "cvlservFlag", value = "公务员标志")
    private String cvlservFlag;

    @ApiModelProperty(name = "cvlservFlagName", value = "公务员标志")
    private String cvlservFlagName;

    @ApiModelProperty(name = "iptNo", value = "住院号")
    private String iptNo;

    @ApiModelProperty(name = "admDeptName", value = "科室")
    private String admDeptName;

    @ApiModelProperty(name = "admBed", value = "床位号")
    private String admBed;

    @ApiModelProperty(name = "begntime", value = "入院日期")
    @JSONField(format = "yyyy-MM-dd")
    private Date begntime;

    @ApiModelProperty(name = "endtime", value = "出院日期")
    @JSONField(format = "yyyy-MM-dd")
    private Date endtime;

    @ApiModelProperty(name = "endtimeBegntime", value = "住院天数")
    private Long endtimeBegntime;

    @ApiModelProperty(name = "certno", value = "证件号码")
    private String certno;

    @ApiModelProperty(name = "mdtrtCertType", value = "证件类型")
    private String mdtrtCertType;

    @ApiModelProperty(name = "mdtrtCertTypeName", value = "证件类型")
    private String mdtrtCertTypeName;

    @ApiModelProperty(name = "dscgMaindiagName", value = "入院诊断")
    private String dscgMaindiagName;

    @ApiModelProperty(name = "outMaindiagName", value = "出院诊断")
    private String outMaindiagName;

    @ApiModelProperty(name = "medType", value = "医疗类别")
    private String medType;

    @ApiModelProperty(name = "medTypeName", value = "医疗类别名称")
    private String medTypeName;

    @ApiModelProperty(name = "setlTime", value = "结算时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date setlTime;

    @ApiModelProperty(name = "chfpdrName", value = "主管医师")
    private String chfpdrName;

    @ApiModelProperty(name = "balc", value = "个人账户余额")
    private BigDecimal balc;

    @ApiModelProperty(name = "mdtrtareaAdmvsName", value = "就医地")
    private String mdtrtareaAdmvsName;

    @ApiModelProperty(name = "insuplcAdmdvsName", value = "参保地")
    private String insuplcAdmdvsName;

    @ApiModelProperty(name = "brdy", value = "出生年月")
    @JSONField(format = "yyyy-MM-dd")
    private Date brdy;

    @ApiModelProperty(name = "diseName", value = "病种名称")
    private String diseName;

}
