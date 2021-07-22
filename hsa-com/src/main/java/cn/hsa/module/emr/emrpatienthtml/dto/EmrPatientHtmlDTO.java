package cn.hsa.module.emr.emrpatienthtml.dto;

import cn.hsa.module.emr.emrpatienthtml.entity.EmrPatientHtmlDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.emr.emrpatienthtml.dto
 * @Class_name: EmrPatientHtmlDTO
 * @Describe: 扩展表
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2020/9/23 20:13
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@ToString(callSuper = true)
public class EmrPatientHtmlDTO extends EmrPatientHtmlDO implements Serializable {
	private static final long serialVersionUID = 3371463068074937312L;


}
