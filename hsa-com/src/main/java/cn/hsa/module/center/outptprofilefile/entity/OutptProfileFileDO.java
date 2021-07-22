package cn.hsa.module.center.outptprofilefile.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.outpt.profilefile.entity
 * @Class_name:: OutptProfileFileDO
 * @Description:
 * @Author: liaojunjie
 * @Date: 2020/8/19 17:26
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutptProfileFileDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -27629458307817484L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 住院档案号（入院登记回写）
     */
    private String inProfile;
    /**
     * 门诊档案号（挂号时生成）
     */
    private String outProfile;
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
      * 年龄单位
    **/
    private String ageUnitCode;
    /**
     * 出生日期
     */
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
     * 居住地详细地址
     */
    private String nowAddress;
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
     * 户口地详细地址
     */
    private String nativeAddress;
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
     * 病人来源途径代码（LYTJ）
     */
    private String sourceTjCode;
    /**
     * 病人来源途径备注
     */
    private String sourceTjRemark;
    /**
     * 拼音码
     */
    private String pym;
    /**
     * 五笔码
     */
    private String wbm;
    /**
     * 累计门诊次数
     */
    private Integer totalOut;
    /**
     * 累计住院次数
     */
    private Integer totalIn;
    /**
     * 门诊最后就诊时间
     */
    private Date outptLastVisitTime;
    /**
     * 住院最后就诊时间
     */
    private Date inptLastVisitTime;
    /**
     * 病人类型代码（BRLX）
     */
    private String patientCode;
    /**
     * 优惠类别ID
     */
    private String preferentialTypeId;
    /**
     * 备注
     */
    private String remark;
    /**
     * 是否有效（SF）
     */
    private String isValid;
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