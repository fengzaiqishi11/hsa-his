package cn.hsa.module.sys.menu.dto;

import cn.hsa.module.sys.menu.entity.SysMenuButtonDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.sys.menu.dto
 * @Class_name: SysMenuButtonDTO
 * @Describe:  系统菜单按钮数据传输对象
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/30 10:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class SysMenuButtonDTO extends SysMenuButtonDO implements Serializable {
  private String usCode;
  private String upName;  //上级菜单名称
  private String sysCode;  //系统编码
}
