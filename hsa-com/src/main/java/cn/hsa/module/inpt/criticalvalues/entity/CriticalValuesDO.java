package cn.hsa.module.inpt.criticalvalues.entity;

import cn.hsa.base.PageDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @PackageName: cn.hsa.module.base.dept.entity
 * @Class_name: BaseDeptDO
 * @Description: 危急值数据库实体对象
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/01/07 15:30
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CriticalValuesDO extends PageDO implements Serializable {

    private static final long serialVersionUID = 1438653119080991069L;
    private String id; // 主键
    private String hospCode; // 医院编码
    private String visitId ; // 就诊id
    private String itemId;  // 项目ID
    private String itemName;  // 项目名称
    private String criticalItemId;  // 危险值项目ID
    private String criticalItemName;  // 危险值项目名称
    private String jyckz;  // 检验参考值
    private String jyjgz;  // 检验结果值
    private String jyjgdw;  // 检验结果单位
    private String jyzt;  // 检验状态代码
    private String wjzzt;  // 危急值状态代码
    private String jyff;  // 检验方法代码
    private String wjsm;  // 危急说明
    private Date wjsj; // 危急时间
    private String isWjcl;  // 是否危急处理
    private String wjclrid;  // 危急处理人ID
    private String wjclrxm; //危急处理人姓名
    private Date wjclrsj; // 危急处理人时间
    private String wjclbz; //危急处理备注
    private String crteId; //创建人ID
    private String crteName; //创建人姓名
    private String crteTime; //创建时间

}
