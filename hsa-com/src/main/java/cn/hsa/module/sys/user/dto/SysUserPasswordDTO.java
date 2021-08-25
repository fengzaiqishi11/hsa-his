package cn.hsa.module.sys.user.dto;

import cn.hsa.module.sys.user.entity.SysUserDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Package_name: cn.hsa.module.sys.user.entity.dto
 * @Class_name: SysUserPasswordDTO
 * @Describe:
 * @Author: marong
 * @Email: 564541256@qq.com
 * @Date: 2020/9/10 08:40
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class SysUserPasswordDTO extends SysUserDO {
    //新密码
    private String newPassword;
    //旧密码
    private String oldPassWord;
}
