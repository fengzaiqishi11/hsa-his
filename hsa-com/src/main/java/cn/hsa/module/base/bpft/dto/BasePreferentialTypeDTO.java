package cn.hsa.module.base.bpft.dto;

import cn.hsa.module.base.bpft.entity.BasePreferentialTypeDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.base.bpft.dto
 * @Class_name: BasePreferentialTypeDTO
 * @Describe: 优惠配置类型数据传输DTO对象
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/8/5 17:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class BasePreferentialTypeDTO extends BasePreferentialTypeDO {
  private static final long serialVersionUID = 217448257180401432L;
  private List<BasePreferentialDTO> basePreferentialTypeDOList;  //优惠配置
  private List<String> ids;  //批量删除id
  private String keyword;     //搜索关键字
}
