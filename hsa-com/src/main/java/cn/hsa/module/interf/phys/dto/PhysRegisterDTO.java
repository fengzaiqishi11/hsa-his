package cn.hsa.module.interf.phys.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.interf.phys.dto
 * @Class_name: PhysRegisterDTO
 * @Describe: 体检登记DTO 需要转成就诊DTO
 * @Author: zhangguorui
 * @Eamil: guorui.zhang@powersi.com
 * @Date: 2021/7/14 9:33
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PhysRegisterDTO implements Serializable {
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 体检号
     */
    private String physNo;
    /**
     * 体检类别（TJLB）
     */
    private String physCategory;
    /**
     * 体检类型（TJLX）
     */
    private String physType;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别（XB）
     */
    private String genderCode;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 年龄单位（NLDW）
     */
    private String ageUnitCode;
    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    /**
     * 套餐ID
     */
    private String comboId;
    /**
     * 紧急联系人
     */
    private String sosName;
    /**
     * 联系人关系(LXRGX)
     */
    private String sosType;
    /**
     * 紧急联系人电话
     */
    private String sosPhone;
    /**
     * 优惠模板ID
     */
    private String discountId;
    /**
     * 是否自费(SF)
     */
    private String isPerson;
    /**
     * 婚姻状况代码（HYZK）
     */
    private String marryCode;
    /**
     * 民族代码（MZ）
     */
    private String nationCode;
    /**
     * 证件类型代码（ZJLX）
     */
    private String certCode;
    /**
     * 证件号码
     */
    private String certNo;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 联系地址
     */
    private String address;
    /**
     * 状态标志（TJZT）
     */
    private String statusCode;
    /**
     * 预约时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderTime;
    /**
     * 单位ID
     */
    private String companyId;
    /**
     * 分组名称
     */
    private String groupName;
    /**
     * 单位部门
     */
    private String dept;
    /**
     * 总金额
     */
    private Double totalMoney;
    /**
     * 体检次数
     */
    private Integer num;
    /**
     * 头像路径
     */
    private String photoPath;
    /**
     * 登记人ID
     */
    private String crteId;
    /**
     * 登记人
     */
    private String crteName;
    /**
     * 登记时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;
    /**
     * 会员卡号
     */
    private String vipNo;
    /**
     * 用户名
     */
    private String user;
    /**
     * 密码
     */
    private String password;
    /**
     * 是否打印
     */
    private String isPrint;
    /**
     * 打印人ID
     */
    private String crtePrintId;
    /**
     * 打印人姓名
     */
    private String crtePrintName;
    /**
     * 打印时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crtePrintTime;

    // 分组id
    private String groupId;

    // 医生id
    private String doctorId;

    // 医生姓名
    private String doctorName;

    // 科室id
    private String deptId;

    // 科室名称
    private String deptName;
}
