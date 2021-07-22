package cn.hsa.module.inpt.careTemplate.entity;

import cn.hsa.base.PageDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @PackageName: cn.hsa.module.inpt.careTemplate.entity
 * @Class_name: InptNurseTemplate
 * @Description: 护理模板数据库映射对象
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/9/16 20:02
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InptNurseTemplateDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -7857250847754335634L;
    private String id;  // 主键id
    private String hospCode; //医院编码
    private String name; // 模板名称
    private String deptId; //科室id
    private String content; //模板内容
    private String isValid; //是否有效
    private String crteId;  //创建人id
    private String crteName; //创建人姓名
    private Date crteTime;  //创建时间
}
