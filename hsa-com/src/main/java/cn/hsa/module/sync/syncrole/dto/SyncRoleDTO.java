package cn.hsa.module.sync.syncrole.dto;

import cn.hsa.module.sync.syncrole.entity.SyncRoleDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name
 * @class_nameSysMenuButtonDo
 * @Description 角色
 * @Author youxianlin
 * @Email: 254580179@qq.com
 * @Date2020/7/28 10:17
 * @Company 创智和宇
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class SyncRoleDTO extends SyncRoleDO implements Serializable {
    private String keyword;
    private String sysCode;
    private List<String> ids;
}
