package cn.hsa.module.center.authorization.dto;

import cn.hsa.module.center.authorization.entity.CenterFunctionAuthorizationDO;
import cn.hsa.module.center.authorization.entity.CenterFunctionDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class CenterFunctionDto extends CenterFunctionDO {

    //医院名称
    private String hospName;
}
