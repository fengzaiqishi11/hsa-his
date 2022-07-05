package cn.hsa.module.center.authorization.dto;

import cn.hsa.module.center.authorization.entity.CenterFunctionAuthorizationDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class CenterFunctionAuthorizationDto  extends CenterFunctionAuthorizationDO {

    //医院名称
    private String hospName;
    //服务名称
    private String name;
    //sql
    private String sql1;

    private String sql2 ;

    //是否开通
    //是否有审核数据  0未开通 ，1已开通
    private String openFlag;

    //是否过期
    private String sfgq ;

    //是否有审核数据  0没有 ，1有
    private String sfysh;
}
