package cn.hsa.module.center.user.dto;

import cn.hsa.module.center.user.entity.CenterUserDO;
import lombok.*;

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
public class CenterUserDTO extends CenterUserDO implements Serializable {
    private static final long serialVersionUID = 574460782082612766L;
    private String keyword;  // 搜索关键字
    private List<String> ids; // ids 用来保存修改标识符的id
    private OutputStream outputStream;

    private String oldPassWord;

    private String newPassword;

    private String orgName;
}
