package cn.hsa.module.center.log.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
* @Package_name: cn.hsa.module.sys.log.entity
* @class_name: CenterPasswordModifyLogDo
* @Description: 用户密码修改日志
* @Author: luonianxin
* @Email: 1423364324@qq.com
* @Date: 2022/4/25 9:53
* @Company: 创智和宇
**/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class CenterPasswordModifyLogDo extends PageDO implements Serializable {
  private static final long serialVersionUID = 217448257180401101L;

  private String id;

  /**
   *   医院编码
   */
  private String hospCode;

  /**
   *   医院名称
   */
  private String hospName;

  /**
   *  用户id
   */
  private String userId;

  /**
   *  用户名
   */
  private String userName;

  /**
   *  工号
   */
  private String userCode;

  /**
   *  用户操作科室id
   */
  private String deptId;

  /**
   *  用户操作科室名称
   */
  private String deptName;


  /**
   *  请求IP地址
   */
  private String ip;

  /**
   *  请求参数
   */
  private String requestParam;

  /**
   *  创建人id
   */
  private String crteId;
  /**
   *  创建人名称
   */
  private String crteName;

  /**
   *  时间戳转换为标准时间格式
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date crteTime;

}
