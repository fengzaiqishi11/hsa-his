package cn.hsa.module.insure.outpt.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.insure.outpt.entity
 * @Class_name: InsureOutptCostUploadRevoke
 * @Describe: 门诊费用明细信息撤销
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/2/9 14:23
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureOutptCostUploadRevokeDO implements Serializable {
	private static final long serialVersionUID = 2374285454630328520L;

	private String mdtrtId;

	private String chrgBchno;

	private String psnNo;
}
