package cn.hsa.module.base.bspl.entity;

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
 * @Package_name: cn.hsa.module.base.data.entity
 * @Class_name: BaseSupplierDo
 * @Describe:  供应商数据库映射对象
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/7 15:38
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseSupplierDO extends PageDO implements Serializable {
  private static final long serialVersionUID = 217448257180401094L;

  private String id;

  private String hospCode;

  private String code;

  private String abbr;

  private String name;

  private String contact;

  private String phone;

  private String fax;

  private String postCode;

  private String email;

  private String bank;

  private String account;

  private String taxNum;

  private String address;

  private String remark;

  private String abbrPym;

  private String abbrWbm;

  private String namePym;

  private String nameWbm;

  private String isValid;

  private String crteId;

  private String crteName;

  // 时间戳转换为标准时间格式
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date crteTime;

  private String companyType;
}
