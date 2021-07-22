package cn.hsa.module.base.deptDrug.dto;

import cn.hsa.module.base.deptDrug.entity.BaseDeptDrugStoreDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @PackageName: cn.hsa.module.base.deptDrug.dto
 * @Class_name: BaseDeptDTO
 * @Description: 基础数据 科室领药数据传输对象
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/16 11:30
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)

public class BaseDeptDrugStoreDTO extends BaseDeptDrugStoreDO implements Serializable {
    private static final long serialVersionUID = -816983272277325373L;
    private String drugTypeCode;  //药房编码
    private String name;  //药房编码对应的药房名称
}
