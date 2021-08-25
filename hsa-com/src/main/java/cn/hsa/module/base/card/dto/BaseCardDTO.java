package cn.hsa.module.base.card.dto;

import cn.hsa.module.base.card.entity.BaseCardDO;
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
 * @Describe: 一卡通dto
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-05-11 15:32
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class BaseCardDTO extends BaseCardDO implements Serializable {
    private static final long serialVersionUID = -7776372151804535600L;
    /**
     * keyword
     */
    private String keyword;
    /**
     * 姓名
     */
    private String name;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 性别
     */
    private String genderCode;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 证件类型（ZJLX）
     */
    private String certCode;
    /**
     * 证件号码
     */
    private String certNo;
    /**
     * 门诊档案号
     */
    private String outProfile;
    /**
     * 住院档案号
     */
    private String inProfile;
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
