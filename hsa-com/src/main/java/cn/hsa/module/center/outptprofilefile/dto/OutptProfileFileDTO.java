package cn.hsa.module.center.outptprofilefile.dto;

import cn.hsa.module.center.outptprofilefile.entity.OutptProfileFileDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.outpt.profilefile.dto
 * @Class_name:: OutptProfileFileDTO
 * @Description: 
 * @Author: liaojunjie
 * @Date: 2020/8/19 19:35
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OutptProfileFileDTO extends OutptProfileFileDO implements Serializable {
    private static final long serialVersionUID = 412020272708327045L;
    private String keyword;                   //搜索关键字
    private String sex;                       //存储性别名
    private String hospCode;                  //暂存医院编码给从表使用
    private List<String>ids;                  //存储档案id集合
    private String type;                      //（建档来源） 0.建档 1.住院 2.门诊
//    private String totalIn;                   //记录住院次数
    /**
     * 优惠类别名称
     */
    private String preferentialTypeName;
}
