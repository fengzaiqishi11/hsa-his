package cn.hsa.module.sync.syncmenu.dto;

import cn.hsa.module.sync.syncmenu.entity.SyncMenuDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.center.menu.dto
 * @Class_name: SyncMenuDTO
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
public class SyncMenuDTO extends SyncMenuDO implements Serializable {
  private String sysName;            //子系统名称
  private String upName;            //上级菜单名称
  private String keyword;         //搜索关键字
  private String usID;           //用户子系统关系id
  private String usCode;        //用户子系统关系编码
  private String isMenuOrBtn;  //判断查询的序号是菜单还是按钮  1.菜单 2.按钮

}
