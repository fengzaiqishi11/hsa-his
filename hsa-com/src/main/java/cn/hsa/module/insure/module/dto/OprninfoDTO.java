package cn.hsa.module.insure.module.dto;

import cn.hsa.module.oper.operInforecord.entity.OperInfoRecordDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OprninfoDTO extends OperInfoRecordDO {
    private String opmOprtType; // 手术操作类别
    private String opmOprtName; // 手术操作名称
    private String opmOprtCode; // 手术操作代码
    private Date opmOprtDate; // 手术操作日期
    private String operDrName; // 术者医师姓名
    private String operDrCode; // 术者医师代码

//    private String opmOprtTypr; // 手术操作类别
//    private String opmOprtName; // 手术操作名称
//    private String opmOprtCode; // 手术操作代码
//    private String opmOprtDate; // 手术操作日期
//    private String operDrName; // 术者医师姓名
//    private String operDrCode; // 术者医师代码
}
