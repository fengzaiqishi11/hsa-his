package cn.hsa.module.sys.menu.dto;

import cn.hsa.module.sys.menu.entity.SysMenuDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.sys.menu.dto
 * @Class_name: SysMenuDTO
 * @Describe:  系统菜单数据传输对象
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/30 10:11
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class SysMenuDTO extends SysMenuDO implements Serializable {
  private String sysName;      //子系统名称
  private String upName;      //上级菜单名称
  private String keyword;     //搜索关键字
  private String usID;        //用户子系统关系id
  private String usCode;      //用户子系统关系编码
  private String isMenuOrBtn;  //查询序号时判断时菜单还是按钮 1.菜单  2.按钮
}
