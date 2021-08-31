package cn.hsa.module.dzpz.hainan;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Package_name: cn.hsa.module.dzpz.hainan
 * @Class_name: refundSetl
 * @Describe:
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2021/2/1 13:49
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class refundSetl {

    // 应用渠道编号
    String appId;
    // 支付订单号
    String payOrdId;
    // 应用退款流水号
    String appRefdSn;
    // 应用退费时间
    String appRefdTime;
    // 总退费金额
    String totlRefdAmt;
    // 医保个人账户支付
    String psnAcctRefdAmt;
    // 基金支付
    String fundRefdAmt;
    // 现金退费金额
    String cashRefdAmt;
    // 电子凭证授权 Token
    String ecToken;
    // 退费类型
    String refdType;
    // 拓展内容
    ExtData extData;


}
