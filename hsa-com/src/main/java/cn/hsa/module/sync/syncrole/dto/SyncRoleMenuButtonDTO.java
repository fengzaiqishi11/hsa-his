package cn.hsa.module.sync.syncrole.dto;

import cn.hsa.module.sync.syncrole.entity.SyncRoleMenuButtonDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name
 * @class_nameSysMenuButtonDo
 * @Description 角色菜单按钮关系表
 * @Author youxianlin
 * @Email: 254580179@qq.com
 * @Date2020/7/28 10:17
 * @Company 创智和宇
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class SyncRoleMenuButtonDTO implements Serializable {
    private static final long serialVersionUID = 217448257180401094L;

    private String hospCode;
    private String roleCode;
    private List<SyncRoleMenuButtonDO> buttons;
}
