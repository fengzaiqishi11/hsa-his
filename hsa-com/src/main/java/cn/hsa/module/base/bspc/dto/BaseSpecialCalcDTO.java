package cn.hsa.module.base.bspc.dto;

import cn.hsa.module.base.bspc.entity.BaseSpecialCalcDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.base.bspc.dto
 * @Class_name: BaseSpecialCalcDTO
 * @Describe: 特殊药品计费数据传输DTO对象
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/15 15:25
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class BaseSpecialCalcDTO extends BaseSpecialCalcDO implements Serializable{
  private static final long serialVersionUID = 217448257180401012L;
  private List<String> ids;
  private String deptName;     //科室名称
  private String drugName;    //药品通用名
  private String keyword;    //查询条件
  private String parentId;   //树根节点
  private List<String> deptTypeCodes;  //科室性质编码
  private String deptTypeCode;  //科室性质编码
  private String spec;    //药品规格
  private String deptId;//科室ID
  private String drugId;//药品ID
}
