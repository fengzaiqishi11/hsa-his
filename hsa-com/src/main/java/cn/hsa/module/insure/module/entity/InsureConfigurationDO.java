package cn.hsa.module.insure.module.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 表含义：
insure：医保
hosp：医院
configuration：配置
-(InsureConfiguration)实体类
 *
 * @author makejava
 * @since 2020-10-13 10:28:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureConfigurationDO extends PageDO {
    /**
    * 主键
    */
    private String id;
    /**
    * 医院编码
    */
    private String hospCode;
    /**
    * 医保机构编码
    */
    private String code;
    /**
    * 医保机构名称
    */
    private String name;
    /**
    * 医保注册编码
    */
    private String regCode;
    /**
    * 医疗机构编码
    */
    private String orgCode;
    /**
    * 医保归属机构编码
    */
    private String attrCode;
    /**
    * 医保类型代码（YBLX）
    */
    private String typeCode;
    /**
    * 医保服务请求地址路径
    */
    private String url;
    /**
    * 医保请求方式代码（YBQQFS）
    */
    private String requestCode;
    /**
    * 请求超时时间
    */
    private String timeOut;
    /**
    * 登录账号
    */
    private String username;
    /**
    * 登录密码
    */
    private String password;
    /**
    * 联系人
    */
    private String contact;
    /**
    * 邮箱
    */
    private String email;
    /**
    * 联系电话
    */
    private String phone;
    /**
    * 是否异地（SF）
    */
    private String isRemote;
    /**
    * 是否有效（SF）
    */
    private String isValid;
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

    /**
     * 就医地医保区划
     */
    private String mdtrtareaAdmvs;
    /**
     * 是否走统一支付平台(1是，0/null否)
     */
    private String isUnifiedPay;
}