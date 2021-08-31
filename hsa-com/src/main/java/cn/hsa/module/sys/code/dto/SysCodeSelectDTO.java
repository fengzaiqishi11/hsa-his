package cn.hsa.module.sys.code.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.base.data.dto
 * @Class_name: SysCodeDTO
 * @Describe: 值域代码下拉框响应值
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/7 16:06
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class SysCodeSelectDTO implements Serializable {
    private String id;
    private String value;
    private String label;
    private String cName;
    private String cCode;
    private String showDefault;
}
