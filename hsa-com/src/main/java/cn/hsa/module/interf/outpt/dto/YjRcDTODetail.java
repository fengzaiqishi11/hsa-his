package cn.hsa.module.interf.outpt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @Package_ame: cn.hsa.module.interf.outpt.dto
 * @Class_name: hsa-his
 * @Description:
 * @Author: zengfeng
 * @Email: 954123283@qq.com
 * @Date: 2021/5/19  13:55
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class YjRcDTODetail {
    //处方组号
    private Integer groupNo;
    //处方组内序号
    private Integer groupSeqNo;
    //药品项目类型
    private String ypxmlx;
    //药品项目ID
    private String ypxmid;
    //用量
    private BigDecimal ycyl = BigDecimal.valueOf(0);
    //用法ID
    private String yfdm;
    //用药间隔ID
    private String yyjgid;
    //执行天数
    private Integer zxts;
    //备注
    private String remark;
    //数量
    private BigDecimal sl;
    //皮试标志
    private String sfps;
    // 速率
    private String speed;
    //
    private Integer zh;
    //
    private Integer cfxh;
}
