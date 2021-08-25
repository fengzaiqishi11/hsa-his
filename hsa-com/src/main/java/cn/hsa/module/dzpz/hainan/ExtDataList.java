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
 * @Description:
 * @Author: zengfeng
 * @Email: 954123283@qq.com
 * @Date: 2021/1/31  17:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExtDataList implements Serializable {
    //经办人
    private String rxDetlNo;
    //经办人
    private String chrgitmType;
    //经办人
    private String chrgType;
    //经办人
    private String chigitmCentCodg;
    //经办人
    private String used;
    //经办人
    private String minunt;
}
