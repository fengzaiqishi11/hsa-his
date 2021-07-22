package cn.hsa.module.sys.parameter.dto;

import cn.hsa.module.sys.parameter.entity.SysParameterDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.sys.data.dto
 * @Class_name: SysParameterDTO
 * @Describe: 参数数据传输DTO对象
 * @Author: zhangxuan
 * @Email: zhangxaun@powersi.com
 * @Date: 2020/7/28 9:27
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class SysParameterDTO extends SysParameterDO implements Serializable{
    private List<String> ids;
    private List<String> codes;
    private String keyword;

}
