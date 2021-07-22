package cn.hsa.module.insure.module.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class IcuInfoDTO {
    private String scsCutdWardType; // 手术操作类别
    private Date scsCutdInpoolTime; // 手术操作名称
    private Date scsCutdExitTime; // 手术操作代码
    private String scsCutdSumDura; // 手术操作日期

//    private String scsCutdWardType; // 手术操作类别
//    private Date scsCutdInpoolTime; // 手术操作名称
//    private Date scsCutdExitTime; // 手术操作代码
//    private String scsCutdSumDura; // 手术操作日期

}
