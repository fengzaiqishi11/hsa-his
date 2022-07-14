package cn.hsa.module.center.user.dto;

import cn.hsa.module.center.user.entity.CenterUserDO;
import cn.hsa.module.center.user.entity.CenterUserRoleDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.center.user.dto
 * @class_name: CenterUserDTO
 * @Description: 中心品台用户数据传输对象
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/8/3 16:27
 * @Company: 创智和宇
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class CenterUserRoleDTO extends CenterUserRoleDO implements Serializable {
    private static final long serialVersionUID = 574460782082612766L;
    private String keyword;  // 搜索关键字
    private List<String> ids; // ids 用来保存修改标识符的id

    private String userName;

    private String roleName;
}
