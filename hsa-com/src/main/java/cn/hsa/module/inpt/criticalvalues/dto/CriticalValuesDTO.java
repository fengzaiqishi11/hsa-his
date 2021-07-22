package cn.hsa.module.inpt.criticalvalues.dto;


import cn.hsa.module.inpt.criticalvalues.entity.CriticalValuesDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @PackageName: cn.hsa.module.base.dept.entity
 * @Class_name: BaseDeptDO
 * @Description: 危急值数据传输对象
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/01/07 15:30
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CriticalValuesDTO extends CriticalValuesDO implements Serializable {
    private static final long serialVersionUID = 3740706725290856641L;
    private String criticalValueCode; // 危急值代码
    private String name ; // 姓名
    private String statusCode; // 危机处理状态
}
