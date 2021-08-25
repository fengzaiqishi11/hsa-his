package cn.hsa.module.base.card.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.base.card.entity
 * @Class_name: BaseCardDO
 * @Describe: 一卡通do
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-05-11 15:31
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseCardDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -5959284776025519096L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 档案ID
     */
    private String profileId;
    /**
     * 卡号
     */
    private String cardNo;
    /**
     * 卡密码
     */
    private String cardPassword;
    /**
     * 一卡通类型（YKTLX）
     * 1诊疗卡，2医保卡，3电子健康卡，
     */
    private String cardTypeCode;
    /**
     * 一卡通状态（YKTZT）
     * 0正常，1挂失，2冻结，3注销，4确认挂失，5作废
     */
    private String statusCode;
    /**
     * 账户余额
     */
    private BigDecimal accountBalance;
    /**
     * 交易ID
     */
    private String settleId;
    /**
     * 电子健康卡ID
     */
    private String eleHealthId;
    /**
     * 市民健康系统ID
     */
    private String cityHealthId;
    /**
     * 默认关联卡类型
     */
    private String defCardType;
    /**
     * 默认关联卡号
     */
    private String defCardNo;
    /**
     * 健康卡二维码文本
     */
    private String healthCodeText;
    /**
     * 创建人ID
     */
    private String crteId;
    /**
     * 创建人姓名
     */
    private String crteName;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;
}
