package cn.hsa.module.sys.parameter.dto;

import cn.hsa.module.sys.parameter.entity.SysParameterUpdateDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.sys.parameter.dto
 * @Class_name: SysParameterUpdateDTO
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/12/20 14:53
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class SysParameterUpdateDTO extends SysParameterUpdateDO implements Serializable {
  private static final long serialVersionUID = 3963329709370513789L;
  private String keyword;
}
