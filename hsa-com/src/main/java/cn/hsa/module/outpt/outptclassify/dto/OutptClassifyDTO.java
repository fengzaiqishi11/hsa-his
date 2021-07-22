package cn.hsa.module.outpt.outptclassify.dto;

import cn.hsa.module.outpt.outptclassify.entity.OutptClassifyDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.outpt.outptclassify.dto
 * @Class_name: OutptClassifyDTO
 * @Describe:  挂号类别设置数据对象
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/8/10 15:40
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OutptClassifyDTO extends OutptClassifyDO implements Serializable {
  private static final long serialVersionUID = 568394639280356022L;
  private List<OutptClassifyCostDTO> outptClassifyCostDTOS;    //挂号类别费用对象
  private List<String> ids;    //用于挂号费用删除的id
  private String deptName; // 挂号科室名称

  private List<String> deptIds;
  private String deptCode;  //科室编码
  private List<String> deptCodes;  //科室编码列表
}
