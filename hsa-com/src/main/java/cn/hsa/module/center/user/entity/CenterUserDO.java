package cn.hsa.module.center.user.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @PackageName: cn.hsa.module.center.user.entity
 * @Class_name: CenterUserDO
 * @Description: 中心品台 用户表
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/8/3 16:26
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CenterUserDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 574460782082612766L;
    /**
     * 主键
     */
    private String id;
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
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
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
     * 密码错误次数
     */
    private Integer pswdErrCnt;
    /**
     * 最后登录时间
     */
    // 时间戳转换为标准时间格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
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
    // 时间戳转换为标准时间格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date crteTime;

}