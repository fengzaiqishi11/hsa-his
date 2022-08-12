package cn.hsa.module.center.centerfunction.dto;

import cn.hsa.module.center.centerfunction.entity.CenterFunctionDetailDo;
import cn.hsa.module.center.code.entity.CenterCodeDetailDo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
* @Package_name: cn.hsa.module.base.code.dto
* @class_name: CenterCodeDetailDTO
* @Description: 值域代码DTO
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/7/15 14:32
* @Company: 创智和宇
**/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class CenterFunctionDetailDTO extends CenterFunctionDetailDo implements Serializable {
    private String keyword;
    private List<String> ids;
    private String upValueName;

}
