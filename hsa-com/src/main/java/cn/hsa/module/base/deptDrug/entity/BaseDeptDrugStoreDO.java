package cn.hsa.module.base.deptDrug.entity;

import cn.hsa.base.PageDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @PackageName: cn.hsa.module.base.deptDrug.dto
 * @Class_name: BaseDeptDTO
 * @Description: 基础数据科室领药数据实体对象
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/16 11:30
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BaseDeptDrugStoreDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -816989272277325373L;
    //主键
    private String id;
    //医院编码
    private String hospCode;
    //科室编码（表base_dept）
    private String deptCode;
    //药房类别代码（YFLB）
    private String typeCode;
    //药房编码
    private String drugStoreCode;
    //是否有效：0否、1是（SF）
    private String isValid;
    //创建人ID
    private String crteId;
    //创建人姓名
    private String crteName;
    //创建时间
    private Date crteTime;
}