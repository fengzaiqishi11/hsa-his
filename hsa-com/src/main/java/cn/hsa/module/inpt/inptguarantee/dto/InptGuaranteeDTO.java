package cn.hsa.module.inpt.inptguarantee.dto;

import cn.hsa.module.inpt.inptguarantee.entity.InptGuaranteeDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.inpt.inptguarantee.dto
 * @Class_name: InptGuaranteeDTO
 * @Describe: 保证金管理数据传输对象
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/8/10 15:15
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InptGuaranteeDTO extends InptGuaranteeDO implements Serializable {

  private static final long serialVersionUID = -7285663252864944724L;
  private List<String> ids;
}
