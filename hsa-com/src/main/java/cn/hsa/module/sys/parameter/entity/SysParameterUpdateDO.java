package cn.hsa.module.sys.parameter.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @Package_name: cn.hsa.module.sys.parameter.entity
 * @Class_name: SysParameterUpdateDO
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/12/20 14:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysParameterUpdateDO {
  private static final long serialVersionUID = -19280771140556356L;
  /**
   * 主键id
   */
  private String id;
  /**
   * 医院编码
   */
  private String hospCode;
  /**
   * 系统参数主键id
   */
  private String sysParamterId;
  /**
   * 参数名称
   */
  private String name;
  /**
   * 修改前参数代码
   */
  private String beforeCode;
  /**
   * 修改后参数代码
   */
  private String afterCode;
  /**
   * 修改前参数值
   */
  private String beforeValue;
  /**
   * 修改后参数值
   */
  private String afterValue;
  /**
   * 参数描述
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
   * 是否有效
   */
  private String isValid;
  /**
   * 是否可见
   */
  private String isShow;
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
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date crteTime;
  /**
   * 是否需要密码(是否SF: 0否，1是)
   */
  private String isNeedPwd;
}
