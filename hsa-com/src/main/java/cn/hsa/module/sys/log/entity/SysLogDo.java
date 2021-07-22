package cn.hsa.module.sys.log.entity;

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
* @Package_name: cn.hsa.module.sys.log.entity
* @class_name: SysLogDo
* @Description: 日志
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/11/30 9:53
* @Company: 创智和宇
**/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysLogDo extends PageDO implements Serializable {
  private static final long serialVersionUID = 217448257180401095L;

  private String id;

  //医院编码
  private String hospCode;

  //医院名称
  private String hospName;

  //用户id
  private String userId;

  //用户名
  private String userName;

  //工号
  private String userCode;

  //用户操作科室id
  private String deptId;

  //用户操作科室名称
  private String deptName;

  //用户操作科室性质
  private String deptType;

  //用户操作科室标识
  private String deptTypeIdentity;

  //请求IP地址
  private String ip;

  //全路径
  private String requestPath;

  //请求参数
  private String requestParam;

  //请求状态(200:成功,500:失败)
  private String code;

  private String crteId;

  private String crteName;

  // 时间戳转换为标准时间格式
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date crteTime;
}
