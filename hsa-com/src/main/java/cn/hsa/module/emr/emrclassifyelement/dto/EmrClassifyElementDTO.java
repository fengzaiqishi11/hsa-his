package cn.hsa.module.emr.emrclassifyelement.dto;

import cn.hsa.module.emr.emrclassifyelement.entity.EmrClassifyElementDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.emr.emrclassifyelement.dto
 * @Class_name: EmrElementDTO
 * @Describe: 电子病历元素管理数据传输对象
 * @Author: xiexingyu
 * @Email: xingyu.xie@powersi.com
 * @Date: 2020/9/17 16:34
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class EmrClassifyElementDTO extends EmrClassifyElementDO implements Serializable {

  private static final long serialVersionUID = -13087039349282877L;

  private List<String> codes;   //编码列表

  private List<String> ids; // 要删除的ids列表



}
