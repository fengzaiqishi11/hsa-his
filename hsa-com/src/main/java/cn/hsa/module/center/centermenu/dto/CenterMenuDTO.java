package cn.hsa.module.center.centermenu.dto;

import cn.hsa.module.center.centermenu.entity.CenterMenuDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.center.centermenu.dto
 * @Class_name: CenterMenuDTO
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/9/1 19:53
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class CenterMenuDTO extends CenterMenuDO implements Serializable {
  private String usCode;
  private String upName;  //上级菜单名称
}
