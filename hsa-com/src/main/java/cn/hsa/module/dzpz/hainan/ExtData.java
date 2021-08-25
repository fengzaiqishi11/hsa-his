package cn.hsa.module.dzpz.hainan;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_ame: cn.hsa.module.dzpz.hainan
 * @Class_name: hsa-his
 * @Description: 海南电子凭证
 * @Author: zengfeng
 * @Email: 954123283@qq.com
 * @Date: 2021/1/31  16:09
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExtData implements Serializable {
    //经办人
    private String opter;
    //个人编号
    private String psnNum;
    //单据号
    private String docno;
    //所属系统标识
    private String systemNo;
    //费用明细
    private List<ExtDataList> list;
}
