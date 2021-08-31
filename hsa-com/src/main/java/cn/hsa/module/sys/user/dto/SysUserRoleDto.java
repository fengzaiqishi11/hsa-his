package cn.hsa.module.sys.user.dto;

import cn.hsa.module.sys.user.SysUserRoleDo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class SysUserRoleDto extends SysUserRoleDo implements Serializable {

    private List<String> roleCodes;
    private List<String> usCodes;
}