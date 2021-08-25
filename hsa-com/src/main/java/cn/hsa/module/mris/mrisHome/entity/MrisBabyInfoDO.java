package cn.hsa.module.mris.mrisHome.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @Package_name: cn.hsa.module.mris.entity
 * @Class_name: InptBedChangeDO
 * @Describe: 婴儿信息
 * @Author: liuliyun
 * @Email: liuyun.liu@powersi.com.cn
 * @Date: 2021/5/14 16:58
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MrisBabyInfoDO extends PageDO implements java.io.Serializable {
    private static final long serialVersionUID = 2806579896749026265L;
    // 主键
    private String id;
    // 医院编码
    private String hospCode;
    // 病案编码
    private String mbiId;
    // 就诊id
    private String visitId;
    // 婴儿id
    private String babyId;
    // 婴儿名字
    private String name;
    // 性别
    private String genderCode;
    // 体重
    private BigDecimal weight;
    // 分娩结果编码
    private String childBirthCode;
    // 转归编码
    private String outComeCode;
    // 抢救次数
    private String rescueNum;
    // 抢救成功次数
    private String rescueSuccessNum;
    // 呼吸编码
    private String breathingCode;
    // 婴儿编号
    private String babyCode;

}
