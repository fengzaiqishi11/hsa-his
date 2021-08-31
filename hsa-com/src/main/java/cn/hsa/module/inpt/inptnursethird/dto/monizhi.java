package cn.hsa.module.inpt.inptnursethird.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.inpt.inptnursethird.dto
 * @Class_name:: monizhi
 * @Description: 三次单控件返回类
 * @Author: ljh
 * @Date: 2020/9/17 13:42
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class monizhi implements Serializable {
    private static final long serialVersionUID = 457919390719606833L;
    private Date riqi;// 时间
    private String ruyuantianshu;// 入院天数
    private String shijian; // 时间（几点）
    private String tiwen; // 体温
    private String fctiwen; // 复测体温
    private String manbo; // 脉搏
    private String xinlv; // 心率
    private String huxi; // 呼吸
    private String peeNO;// 小便次数
    private String shitNO; // 大便次数
    private String weight; // 体重
    private String height; // 身高
    private String bloodPressure; // 血压
    private String inputQuantity; // 入液量
    private String outputQuantity; // 出液量
    private String drugAllergy; // 药物过敏
    private String operation; // 手术
    private String tiwenbeizhu40; // 体温超过40度备注
    private String tiwenbeizhu35; // 体温超过35度备注


}
