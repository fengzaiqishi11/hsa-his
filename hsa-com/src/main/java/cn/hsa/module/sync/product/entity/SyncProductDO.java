package cn.hsa.module.sync.product.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 表名含义：
base：基础模块
product：生产企业信息表
                    (BaseProduct)实体类
 *
 * @author xingyu.xie
 * @since 2020-07-17 14:16:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SyncProductDO extends PageDO implements  Serializable {

    private static final long serialVersionUID = 922090749614395669L;

    private String id;
    /**
    * 生产企业编码
    */
    private String code;
    /**
    * 生产企业名称
    */
    private String name;
    /**
    * 联系人
    */
    private String contact;
    /**
    * 电话
    */
    private String phone;
    /**
    * 传真
    */
    private String fax;
    /**
    * 邮编
    */
    private String postCode;
    /**
    * 电子邮箱
    */
    private String email;
    /**
    * 地址
    */
    private String address;
    /**
    * 备注
    */
    private String remark;
    /**
    * 拼音码
    */
    private String pym;
    /**
    * 五笔码
    */
    private String wbm;
    /**
    * 是否有效：0否，1是
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;


}