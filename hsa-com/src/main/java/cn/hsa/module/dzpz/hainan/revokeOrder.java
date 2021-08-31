package cn.hsa.module.dzpz.hainan;

import cn.hsa.util.MapUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Package_name: cn.hsa.module.dzpz.hainan
 * @Class_name: revokeOrder
 * @Describe:
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2021/2/1 13:46
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class revokeOrder {

    // 应用渠道编号
    String appId;
    // 支付订单号
    String payOrdId;
    // 机构编号
    String orgCodg;
    // 支付 token
    String payToken;
    // 证件号码
    String idNo;
    // 用户姓名
    String userName;
    // 证件类别
    String idType;
    // 拓展内容
    ExtData extData;
}
