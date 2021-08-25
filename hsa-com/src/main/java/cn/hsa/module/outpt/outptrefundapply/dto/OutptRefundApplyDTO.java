package cn.hsa.module.outpt.outptrefundapply.dto;

import cn.hsa.module.outpt.outptrefundapply.entity.OutptRefundApplyDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.outpt.outptrefundapply.dto
 * @Class_name: OutptRefundApplyDTO
 * @Describe: TODO
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/3/9 14:06
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OutptRefundApplyDTO extends OutptRefundApplyDO implements Serializable {
	private static final long serialVersionUID = -5379330249045067423L;

	private String visitId;  // 就诊id
}
