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
public class UploadFeeExtData implements Serializable {
    //应用渠道编号
    private String payOrdId;
    //机构编码
    private String payToken;
    //医保扩展数据
    private UploadFeeExtDataResult extData;
}
