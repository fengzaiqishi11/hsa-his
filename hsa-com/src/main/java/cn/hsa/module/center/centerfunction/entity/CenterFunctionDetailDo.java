package cn.hsa.module.center.centerfunction.entity;

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
 * @公司 创智和宇信息技术股份有限公司 Copyright (c) 2006-2020,All Rights Reserved.
 * @描叙 值域代码明细数据库映射对象
 * @创建者 zengfeng
 * @修改者 zengfeng
 * @版本 V1.0
 * @日期 2020/7/13  19:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CenterFunctionDetailDo extends PageDO implements Serializable {
  private static final long serialVersionUID = 217448257180401094L;


  private String id;

  private String functionCode;

  private String name;

  private String value;

  private String remark;

  private String seqNo;

  private String pym;

  private String wbm;

  private String upValue;

  private String isEnd;

  private String isValid;

  private String crteId;

  private String crteName;

  // 时间戳转换为标准时间格式
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date crteTime;
}
