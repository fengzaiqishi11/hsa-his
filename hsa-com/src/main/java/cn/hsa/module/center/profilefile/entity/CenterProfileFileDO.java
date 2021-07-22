package cn.hsa.module.center.profilefile.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.center.profilefile.entity.entity
 * @Class_name:: CenterProfileFileDO
 * @Description: 
 * @Author: liaojunjie
 * @Date: 2020/8/18 13:59 
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CenterProfileFileDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -27629458307817484L;
    /**
     * 主键
     */
    private String id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别代码（XB）
     */
    private String genderCode;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 出生日期
     */
    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    /**
     * 民族代码（MZ）
     */
    private String nationCode;
    /**
     * 国籍代码（GJ）
     */
    private String nationalityCation;
    /**
     * 籍贯
     */
    private String nativePlace;
    /**
     * 学历代码（XL）
     */
    private String educationCode;
    /**
     * 职业代码（ZY）
     */
    private String occupationCode;
    /**
     * 婚姻状况代码（HYZK）
     */
    private String marryCode;
    /**
     * 证件类型代码（ZJLX）
     */
    private String certCode;
    /**
     * 证件号码
     */
    private String certNo;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 常住类型（CZLX）（1 户籍，2非户籍）
     */
    private String oftenCode;
    /**
     * 居住地（省）
     */
    private String nowProv;
    /**
     * 居住地（市）
     */
    private String nowCity;
    /**
     * 居住地（区、县）
     */
    private String nowArea;
    /**
     * 居住地邮编
     */
    private String nowPostCode;
    /**
     * 户口地（省）
     */
    private String nativeProv;
    /**
     * 户口地（市）
     */
    private String nativeCity;
    /**
     * 户口地（区、县）
     */
    private String nativeArea;
    /**
     * 户口地邮编
     */
    private String nativePostCode;
    /**
     * 联系人姓名
     */
    private String contactName;
    /**
     * 联系人关系（RYGX）
     */
    private String contactRelaCode;
    /**
     * 联系人电话
     */
    private String contactPhone;
    /**
     * 联系人邮编
     */
    private String contactPostCode;
    /**
     * 联系人地址
     */
    private String contactAddress;
    /**
     * 监护人姓名
     */
    private String takeName;
    /**
     * 监护人关系（RYGX）
     */
    private String takeRelaCode;
    /**
     * 监护人证件号码
     */
    private String takeCertNo;
    /**
     * 监护人联系电话
     */
    private String takePhone;
    /**
     * 工作单位
     */
    private String work;
    /**
     * 单位电话
     */
    private String workPhone;
    /**
     * 单位邮编
     */
    private String workPostCode;
    /**
     * 单位地址
     */
    private String workAddress;
    /**
     * 血型代码（XX）
     */
    private String bloodCode;
    /**
     * 家族史
     */
    private String familyDisease;
    /**
     * 暴露史
     */
    private String exposeDisease;
    /**
     * 遗传病史
     */
    private String heredityDisease;
    /**
     * 过敏史
     */
    private String allergyDisease;
    /**
     * 既往史疾病
     */
    private String pastDisease;
    /**
     * 既往史手术
     */
    private String pastOper;
    /**
     * 既往史外伤
     */
    private String pastTrauma;
    /**
     * 既往史输血
     */
    private String pastBlood;
    /**
     * 既往史描述
     */
    private String pastRemark;
    /**
     * 过敏史描述
     */
    private String allergyRemark;
    /**
     * 拼音码
     */
    private String pym;
    /**
     * 五笔码
     */
    private String wbm;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建人ID
     */
    private String crteId;
    /**
     * 创建人姓名
     */
    private String crteName;
    /**
     * 创建时间
     */
    private Date crteTime;
}