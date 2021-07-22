package cn.hsa.module.dzpz.hainan;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Package_name: cn.hsa.module.dzpz.hainan
 * @Class_name: queryInsu
 * @Describe:
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2021/2/1 13:55
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class queryInsu {
    // 应用渠道编号
    String appId;
    // 支付订单号
    String payOrdId;
    // 诊断类别
    String medType;
    // 电子凭证授权 token
    String ecToken;
    // 证件号码
    String idNo;
    // 用户姓名
    String userName;
    // 证件类别
    String idType;
    // 统筹区编码
    String insuCode;
    // 拓展数据
    ExtData extData;
}
