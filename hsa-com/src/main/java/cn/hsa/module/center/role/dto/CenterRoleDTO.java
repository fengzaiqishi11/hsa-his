package cn.hsa.module.center.role.dto;

import cn.hsa.module.center.role.entity.CenterRoleDO;
import cn.hsa.module.center.role.entity.CenterRoleMenuDO;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
* @Package_name: cn.hsa.module.center.role.dto
* @class_name: CenterRoleDTO
* @Description: 中心平台角色DTO
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/8/4 15:42
* @Company: 创智和宇
**/
@Data
@AllArgsConstructor
@NoArgsConstructor
@NonNull
@ToString(callSuper = true)
public class CenterRoleDTO extends CenterRoleDO implements Serializable {
    private List<String> ids;
    private String keyword;
    private List<CenterRoleMenuDO> menuDOList;
}
