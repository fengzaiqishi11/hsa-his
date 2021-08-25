package cn.hsa.module.sys.code.dto;

import cn.hsa.module.sys.code.entity.SysCodeDetailDo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
* @Package_name: cn.hsa.module.base.code.dto
* @class_name: SysCodeDetailDTO
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
public class SysCodeDetailDTO extends SysCodeDetailDo implements Serializable {
    private String upValueName;
    private String keyword;
    private List<String> ids;

    private List<String> valueList;  //值域值列表

    //处方引入类型
    private String type;

    // 病案费用编码
    private String mrisCode;

    // 财务分类编码
    private String bfcCodes;

    private String nationCode; //国家编码
}
