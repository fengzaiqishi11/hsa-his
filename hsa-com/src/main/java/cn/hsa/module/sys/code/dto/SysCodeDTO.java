package cn.hsa.module.sys.code.dto;

import cn.hsa.module.sys.code.entity.SysCodeDo;
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
public class SysCodeDTO extends SysCodeDo implements Serializable {
    private static final long serialVersionUID = -1849084003252313476L;
    private String oldCode;
    private String keyword;
    private List<String> ids;
    private String cCode;
    private List<SysCodeSelectDTO> sysCodeSelectDTOList;
}
