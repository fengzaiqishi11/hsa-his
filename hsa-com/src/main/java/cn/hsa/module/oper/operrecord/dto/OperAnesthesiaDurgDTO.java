package cn.hsa.module.oper.operrecord.dto;

import cn.hsa.module.oper.operrecord.entity.OperAnesthesiaDurgDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.oper.operrecord.dto
 * @Class_name: OperAnesthesiaDurgDTO
 * @Describe: 麻醉用药记录数据传输对象
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/12/21 21:20
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OperAnesthesiaDurgDTO extends OperAnesthesiaDurgDO implements Serializable {
  private String Flag;
}
