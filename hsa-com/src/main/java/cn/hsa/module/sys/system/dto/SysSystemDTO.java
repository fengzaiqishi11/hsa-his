package cn.hsa.module.sys.system.dto;

import cn.hsa.module.sys.system.entity.SysSystemDo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.base.data.dto
 * @Class_name: SysCodeDTO
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
public class SysSystemDTO extends SysSystemDo implements Serializable {
    private String value;
    private String label;
    private String keyword;
    private List<String> ids;
    private String name ; // 员工姓名
}
