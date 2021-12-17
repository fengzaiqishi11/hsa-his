package cn.hsa.module.sys.user.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.sys.user.entity
 * @Class_name:: SysUserDO
 * @Description:
 * @Author: liaojunjie
 * @Date: 2020/8/10 17:00
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysUserDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 133052673365086735L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 所属科室编码
     */
    private String deptCode;
    /**
     * 员工工号（登录账户）
     */
    private String code;
    /**
     * 登录密码（初始化密码：888888）
     */
    private String password;
    /**
     * 是否在职
     */
    private String isInJob;
    /**
     * 姓名
     */
    private String name;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 出生日期
     */
    private Date birthday;
    /**
     * 性别代码
     */
    private String genderCode;
    /**
     * 民族代码
     */
    private String nationCode;
    /**
     * 籍贯
     */
    private String nativePlace;
    /**
     * 婚姻状况代码
     */
    private String marryCode;
    /**
     * 是否党员
     */
    private String isPm;
    /**
     * 入党时间
     */
    private Date inPmDate;
    /**
     * 证件类型代码
     */
    private String certCode;
    /**
     * 证件号码
     */
    private String certNo;
    /**
     * 头像照片路径
     */
    private String photoImage;
    /**
     * 电子签名照片路径
     */
    private String signImage;
    /**
     * 医生介绍
     */
    private String introduce;
    /**
     * 医生擅长
     */
    private String speciality;
    /**
     * 家庭地址
     */
    private String address;
    /**
     * 电子邮件
     */
    private String email;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 学历分类代码
     */
    private String educationCode;
    /**
     * 所学专业代码
     */
    private String majorCode;
    /**
     * 毕业学校
     */
    private String school;
    /**
     * 毕业时间
     */
    private Date schoolDate;
    /**
     * 毕业证书图片路径
     */
    private String schoolImage;
    /**
     * 参加工作时间
     */
    private Date workDate;
    /**
     * 职工类型代码
     */
    private String workTypeCode;
    /**
     * 职称代码
     */
    private String dutiesCode;
    /**
     * 执业证书编号
     */
    private String pracCertiNo;
    /**
     * 执业证书名称
     */
    private String pracCertiName;
    /**
     * 处方级别代码
     */
    private String prescribeCode;
    /**
     * 药品级别代码
     */
    private String durgCode;
    /**
     * 抗菌药级别代码
     */
    private String antibacterialCode;
    /**
     * 毒麻药品级别代码
     */
    private String phCode;
    /**
     * 手术级别代码
     */
    private String opeartionCode;
    /**
     * 密码错误次数
     */
    private Integer pswdErrCnt;
    /**
     * 最后登录时间
     */
    private Date lastLoginTime;
    /**
     * 最后登录IP
     */
    private String lastLoginIp;
    /**
     * 用户状态代码
     */
    private String statusCode;
    /**
     * 拼音码
     */
    private String pym;
    /**
     * 五笔码
     */
    private String wbm;
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
    /**
     * 签字路径
     */
    private String signaturePath;
    /**
     * 医生简介
     */
    private String doctorIntro;
    /**
     * 是否阅读指引
     */
    private String isGuide;
    /**
     * 是否具有麻醉权
     */
    private String isAnaesthesia;
    /**
     * 近期是否修改过密码 0表示未修改,1表示已修改 -2 表示密码为弱密码,需要强制修改密码
     */
    private String isPasswordChange;
    /**
     * 是否具有麻醉权
     */
    private String onlyOpenItem;

}