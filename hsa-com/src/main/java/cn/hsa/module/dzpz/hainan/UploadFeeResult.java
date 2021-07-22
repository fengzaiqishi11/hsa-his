package cn.hsa.module.dzpz.hainan;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_ame: cn.hsa.module.dzpz.hainan
 * @Class_name: hsa-his
 * @Description: 海南电子凭证费用实体
 * @Author: zengfeng
 * @Email: 954123283@qq.com
 * @Date: 2021/1/31  15:47
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UploadFeeResult implements Serializable {
    //（医保返回）处方明细 ID
    private String rxSn;
    //医院上传处方明细 ID
    private String hosRxDetlNo;
    //金额
    private String amt;
    //自理金额
    private String pfoAmt;
    //自费金额
    private String ownpayAmt;
    //超限价自付金额
    private String overlmtSelfpayAmt;
    //收费项目等级
    private String chrgItemLv;
    //全额自费标志
    private String fulamtOwnpayFlag;
    //自费原因
    private String ownpayRea;
    //医保限价
    private String hiLmtpric;
    //自付比例
    private String selfpayProp;
}
