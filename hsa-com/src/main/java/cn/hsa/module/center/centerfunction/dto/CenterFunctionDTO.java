package cn.hsa.module.center.centerfunction.dto;

import cn.hsa.module.center.centerfunction.entity.CenterFunctionDo;
import cn.hsa.module.center.code.entity.CenterCodeDo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.base.data.dto
 * @Class_name: CenterCodeDTO
 * @Describe: 供应商数据传输DTO对象
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/7 16:06
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class CenterFunctionDTO extends CenterFunctionDo implements Serializable {
    private String oldCode;
    private String keyword;
    private List<String> ids;

}
