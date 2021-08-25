package cn.hsa.module.base.bpft.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.base.bpft.entity
 * @Class_name: BasePreferential
 * @Describe: 优惠配置数据库映射对象
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/13 9:53
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BasePreferentialDO extends PageDO implements Serializable {
  private static final long serialVersionUID = 231456409879498215L;

  private String id;

  private String hospCode;

  private String typeCode;

  private String bizCode;

  private String pfTypeCode;

  private String outCode;

  private BigDecimal outScale;

  private String inCode;

  private BigDecimal inScale;

  private String isValid;

  private String crteId;

  private String crteName;

  // 时间戳转换为标准时间格式
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date crteTime;

}
