package cn.hsa.module.base.card.dto;

import cn.hsa.module.base.card.entity.BaseCardRechargeChangeDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.base.card.dto
 * @Class_name: BaseCardDTO
 * @Describe: 一卡通充值dto
 * @Author: liuliyun
 * @Eamil: liyun.liu@powersi.com
 * @Date: 2021-08-10 15:32
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class BaseCardRechargeChangeDTO extends BaseCardRechargeChangeDO implements Serializable {

    private static final long serialVersionUID = -7297934485928805832L;

    private String keyword;  // 查询关键字
    private String inProfile;// 病案号
    private String outProfile; // 门诊号
    private String certNo; // 身份证号
    private String cardTypeCode; // 一卡通类型
    private String cardNo; // 卡号
    private String cardStatusCode; // 一卡通使用状态
    private String name; // 姓名
    private String age;  // 年龄
    private String genderCode; // 性别

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cardTime; // 发卡时间
    /**
     * 开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    /**
     * 结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

}
