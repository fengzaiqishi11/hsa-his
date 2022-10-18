package cn.hsa.module.interf.wxsettle.dao;

import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.fees.dto.OutptSettleDTO;
import cn.hsa.module.outpt.fees.entity.OutptPayDO;
import cn.hsa.module.outpt.fees.entity.OutptSettleDO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.payment.entity.PaymentSettleDO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.interf.wxsettle.dao
 * @Class_name: WxSettleDAO
 * @Describe: 微信试算、结算接口dao
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/6/28 19:02
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface WxSettleDAO {

	/**
	 * @Menthod delOutptSettleByParam
	 * @Desrciption 根据条件删除门诊费用数据
	 * @param param 请求参数
	 * @Author Ou·Mr
	 * @Date 2020/10/15 11:13
	 * @Return int 受影响行数
	 */
	int delOutptSettleByParam(Map<String,String> param);

	/**
	 * @Menthod insertSelective
	 * @Desrciption  保存门诊结算信息（字段为 null OR '' 不会保存字段）
	 * @param outptSettleDO 门诊结算参数
	 * @Author Ou·Mr
	 * @Date 2020/8/25 9:22
	 * @Return int 受影响的行数
	 */
	int insertSelective(OutptSettleDO outptSettleDO);

	/**
	 * @Menthod queryOutptCostSourceidNotNUll
	 * @Desrciption 查询患者处方费用信息
	 * @param param 请求参数
	 * @Author Ou·Mr
	 * @Date 2020/11/11 14:35
	 * @Return java.util.List<cn.hsa.module.outpt.fees.dto.OutptCostDTO>
	 */
	List<OutptCostDTO> queryOutptCostSourceidNotNUll(Map param);

	/**
	 * @Menthod queryOutptCostRest
	 * @Desrciption 查询非处方、非划价收费费用
	 * @param param 请求参数
	 * @Author Ou·Mr
	 * @Date 2020/11/16 10:06
	 * @Return java.util.List<cn.hsa.module.outpt.fees.dto.OutptCostDTO>
	 */
	List<OutptCostDTO> queryOutptCostRest(Map param);

	/**
	 * @Menthod queryByVisitID
	 * @Desrciption 根据就诊id查询门诊患者信息
	 * @param param 请求参数
	 * @Author Ou·Mr
	 * @Date 2020/12/17 21:55
	 * @Return cn.hsa.module.outpt.visit.dto.OutptVisitDTO
	 */
	OutptVisitDTO queryByVisitID(Map<String,String> param);

	/**
	 * @Desrciption 检查库存
	 * @param outptPrescribeDetailsDTO
	 * @Author zengfeng
	 * @Date   2020/10/26 14:44
	 * @Return List<Map<String, Object>>
	 */
	List<Map<String, Object>> checkStock(OutptPrescribeDetailsDTO outptPrescribeDetailsDTO);

	/**
	 * @Desrciption 获取系统参数信息
	 * @param hospCode
	 * @param code
	 * @Author zengfeng
	 * @Date   2020/10/19 14:44
	 * @Return List<OutptDiagnoseDTO>
	 */
	List<SysParameterDTO> getParameterValue(@Param("hospCode") String hospCode, @Param("code") String[] code);

	/**
	 * @Menthod selectBySettleId
	 * @Desrciption 根据结算id查询本次结算的费用信息
	 * @param param  hospCode（医院编码）、statusCode（状态标志）、settleCode（结算状态）、settleId（结算id）
	 * @Author Ou·Mr
	 * @Date 2020/8/31 10:50
	 * @Return java.util.List<cn.hsa.module.outpt.fees.dto.OutptCostDTO>
	 */
	List<OutptCostDTO> queryBySettleId(Map param);

	/**
	 * @Menthod queryDrugMaterialListByIds
	 * @Desrciption 根据费用项目id查询费用信息
	 * @param param 请求参数
	 * @Author Ou·Mr
	 * @Date 2020/9/21 19:59
	 * @Return java.util.List<cn.hsa.module.outpt.fees.dto.OutptCostDTO>
	 */
	List<OutptCostDTO> queryDrugMaterialListByIds(Map<String,Object> param);

	/**
	 * @Menthod selectByPrimaryKey
	 * @Desrciption 根据主键查询门诊结算
	 * @param key 主键
	 * @Author Ou·Mr
	 * @Date 2020/8/25 9:12
	 * @Return cn.hsa.module.outpt.fees.dto.OutptSettleDTO 门诊结算信息
	 */
	OutptSettleDTO selectByPrimaryKey(@Param("id") String key);

	/**
	 * @Menthod batchInsertOutptPay
	 * @Desrciption  批量保存发票信息
	 * @param outptPayDOList 参数
	 * @Author Ou·Mr
	 * @Date 2020/9/14 15:26
	 * @Return int 受影响的行数
	 */
	int batchInsertOutptPay(List<OutptPayDO> outptPayDOList);

	/**
	 * @Menthod editCostSettleCodeByIDS
	 * @Desrciption 根据费用id批量修改费用状态信息
	 * @param param 请求参数
	 * @Author Ou·Mr
	 * @Date 2020/9/14 11:06
	 * @Return int 受影响的行数
	 */
	int editCostSettleCodeByIDS(Map param);

	/**
	 * @Menthod updateByPrimaryKeySelective
	 * @Desrciption 修改门诊结算信息
	 * @param outptSettleDO 门诊结算参数
	 * @Author Ou·Mr
	 * @Date 2020/8/25 9:28
	 * @Return int 受影响的行数
	 */
	int updateByPrimaryKeySelective(OutptSettleDO outptSettleDO);

	OutptSettleDTO getById(Map<String, Object> selectMap);

	/**
	 * @Menthod updateOutptPrescribeByIds
	 * @Desrciption 修改处方结算信息
	 * @param param 请求参数
	 * @Author Ou·Mr
	 * @Date 2020/10/15 9:52
	 * @Return int 受影响行数
	 */
	int updateOutptPrescribeByIds(Map<String,Object> param);

	/**
	 * @Menthod queryShortcutWindows
	 * @Desrciption 根据药房id查询最快捷发药窗口
	 * @param param 查询条件
	 * @Author Ou·Mr
	 * @Date 2020/11/17 9:28
	 * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
	 */
	List<Map<String,Object>> queryShortcutWindows(Map param);

	/**
	 * @Menthod queryBaseDeptById
	 * @Desrciption 根据科室id查询科室名称
	 * @param param 请求参数
	 * @Author Ou·Mr
	 * @Date 2020/11/17 9:45
	 * @Return java.lang.String
	 */
	String queryBaseDeptById(Map param);

	/**
	 * @Menthod batchEditCostPrice
	 * @Desrciption 批量修改费用表的费用信息
	 * @param outptCostDTOList 费用数据
	 * @Author Ou·Mr
	 * @Date 2020/9/11 13:47
	 * @Return int 受影响的行数
	 */
	int batchEditCostPrice(List<OutptCostDTO> outptCostDTOList);

	/**
	 * @Menthod delOutptPaymentSettleByParam
	 * @Desrciption 删除试算诊间支付订单脏数据
	 * @param param  1、hospCode-医院编码  2、visitId--就诊id 3、isSettle--是否结算 4、statusCode--正常
	 * @Author liuliyun
	 * @Date 2022-10-17 09:33
	 * @Return int 受影响的行数
	 */
	int delOutptPaymentSettleByParam(Map<String,String> param);

	/**@Menthod insertPaymentSettle
	 * @Describe:新增诊间支付结算信息
	 * @Author: liuliyun
	 * @Eamil: liyun.liu@powersi.com
	 * @param PaymentSettleDO
	 * @Date: 2022-10-17 09:59
	 * @return int
	 */
	int insertPaymentSettle(PaymentSettleDO PaymentSettleDO);

	/**@Menthod updatePaymentSettle
	 * @Describe:修改诊间支付结算状态
	 * @Author: liuliyun
	 * @Eamil: liyun.liu@powersi.com
	 * @param PaymentSettleDO
	 * @Date: 2022-10-17 11:54
	 * @return int
	 */
	int updatePaymentSettle(PaymentSettleDO PaymentSettleDO);
}
