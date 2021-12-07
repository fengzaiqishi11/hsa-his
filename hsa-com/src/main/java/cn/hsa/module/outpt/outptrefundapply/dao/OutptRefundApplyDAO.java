package cn.hsa.module.outpt.outptrefundapply.dao;

import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.fees.dto.OutptSettleDTO;
import cn.hsa.module.outpt.outptrefundapply.dto.OutptRefundApplyDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.outptrefundapply.dao
 * @Class_name: OutptRefundApplyDAO
 * @Describe: 门诊医生退费申请
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/3/9 15:30
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface OutptRefundApplyDAO {

	/**
	 * @Description: 保存门诊医生退费申请
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/9 15:43
	 * @Return
	 */
	int saveOutptRefundAppy(@Param("list") List<OutptRefundApplyDTO> list);

	/**
	 * @Description:
	 * @Param: 
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/10 10:32
	 * @Return 
	 */
	void deleteOutptRefundApplyByCostId(@Param("costIdList") List<String> costIdList);

	List<Map<String, Object>> queryOutChargePage(OutptSettleDTO outptSettleDTO);

	List<OutptCostDTO> queryOutptPrescribes(OutptSettleDTO outptSettleDTO);

	/**
	 * @Description: 门诊医生退款申请确认
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/10 16:34
	 * @Return
	 */
	int updateOutptRefundAppyStatus(OutptSettleDTO outptSettleDTO);

	/**
	 * @Description: 门诊医生取消退费确认
	 * @Param:
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2021/12/01 14:30
	 * @Return
	 */
	int updateUnconfirmedOutptRefundAppy(OutptSettleDTO outptSettleDTO);

	/**
	 * @Description: 门诊医生取消退费申请
	 * @Param:
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2021/12/01 15:46
	 * @Return
	 */
	int deleteOutptRefundAppy(OutptSettleDTO outptSettleDTO);

	List<OutptCostDTO> queryUncostList(OutptSettleDTO outptSettleDTO);
}
