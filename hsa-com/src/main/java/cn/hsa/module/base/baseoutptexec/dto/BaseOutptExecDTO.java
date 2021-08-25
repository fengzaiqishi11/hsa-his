package cn.hsa.module.base.baseoutptexec.dto;

import cn.hsa.module.base.baseoutptexec.entity.BaseOutptExecDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.base.baseoutptexec.dto
 * @Class_name: BaseOutptExecDTO
 * @Describe: 门诊执行科室配置数据传输对象
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/10/14 14:00
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class BaseOutptExecDTO extends BaseOutptExecDO implements Serializable {
  private static final long serialVersionUID = 579603873650270239L;
  private String execDeptName;  //执行科室名称
  private String deptNames; // 科室列表名称
  private String usageCodeNames; // 用法列表名称
  private List<String> ids; // 主键列表
  private String keyword;  //关键字搜索
}
