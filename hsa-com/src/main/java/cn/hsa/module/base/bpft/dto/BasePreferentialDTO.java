package cn.hsa.module.base.bpft.dto;

import cn.hsa.module.base.bpft.entity.BasePreferentialDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.base.bpft.dto
 * @Class_name: BasePreferentialDTO
 * @Describe: 优惠配置数据传输DTO对象
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/13 9:55
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class BasePreferentialDTO extends BasePreferentialDO implements Serializable {
  private static final long serialVersionUID = 217448257180401422L;
  private String keyword;    //搜索关键字
  private List<String> ids;
  private String projectName;    //查詢出的項目/药品/财务分类名称
  private String itemId; //项目id
  private String itemCode; //项目id
  private String itemName; //项目id
  private String pfTypeCodeId;

}
