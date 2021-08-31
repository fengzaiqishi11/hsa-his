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
 * @class_name: InsureFixPersonnalRecordDO
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/3/30 15:00
 * @Company: 创智和宇
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureFixPersonnalRecordDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -4030366790489484405L;
    private String id;
    private String hospCode;
    private String visitId; // 就诊ID
    private String psnNo; // 人员编号
    private String tel; // 联系电话
    private String addr; //联系地址
    private String bizAppyType; // 业务申请类型
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String begndate; // 开始日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String enddate; // 结束日期
    private String agnterName; // 代办人姓名
    private String agnterCertType; // 代办人证件类型
    private String agnterCertTypeName; // 代办人证件类型名称
    private String agnterCertno; // 代办人证件号码
    private String agnterTel; // 代办人联系方式
    private String agnterRlts; //代办人关系
    private String agnterRltsName; //代办人关系名称
    private String fixSrtNo; // 定点排序号
    private String fixmedinsCode; // 定点医药机构名称
    private String fixmedinsName; // 定点医药机构名称
    private String insureRegCode;
    private String agnterAddr;
    private String memo; // 备注
    private String trtDclaDetlSn;
    //创建人ID
    private String crteId;
    //创建人姓名
    private String crteName;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;
    private String bizAppyTypeName; //业务申请类型名称
}
