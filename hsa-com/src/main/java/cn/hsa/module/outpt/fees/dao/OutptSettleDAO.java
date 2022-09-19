package cn.hsa.module.outpt.fees.dao;

import cn.hsa.module.dzpz.hainan.DiseList;
import cn.hsa.module.dzpz.hainan.ExtDataList;
import cn.hsa.module.dzpz.hainan.RxList;
import cn.hsa.module.dzpz.hainan.UploadFee;
import cn.hsa.module.insure.module.dto.InsureIndividualCostDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualSettleDTO;
import cn.hsa.module.insure.module.entity.InsureIndividualSettleDO;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.fees.dto.OutptSettleDTO;
import cn.hsa.module.outpt.fees.entity.OutptPrescribeDO;
import cn.hsa.module.outpt.fees.entity.OutptSettleDO;
import cn.hsa.module.outpt.prescribe.dto.OutptDiagnoseDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.payment.dto.PaymentSettleDTO;
import cn.hsa.module.payment.entity.PaymentSettleDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.fees.dao
 * @Class_name: OutptSettleDAO
 * @Describe(描述):门诊结算DAO
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/08/25 10:17
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface OutptSettleDAO {

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
     * @Menthod insert
     * @Desrciption 保存门诊结算信息
     * @param outptSettleDO 门诊结算参数
     * @Author Ou·Mr
     * @Date 2020/8/25 9:16
     * @Return int 受影响的行数
     */
    int insert(OutptSettleDO outptSettleDO);

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
     * @Menthod updateByPrimaryKey
     * @Desrciption 修改门诊结算信息
     * @param outptSettleDO 门诊结算参数
     * @Author Ou·Mr
     * @Date 2020/8/25 9:27
     * @Return int 受影响的行数
     */
    int updateByPrimaryKey(OutptSettleDO outptSettleDO);

    /**
     * @Menthod updateByPrimaryKeySelective
     * @Desrciption 修改门诊结算信息
     * @param outptSettleDO 门诊结算参数
     * @Author Ou·Mr
     * @Date 2020/8/25 9:28
     * @Return int 受影响的行数
     */
    int updateByPrimaryKeySelective(OutptSettleDO outptSettleDO);

    /**
     * @Menthod deleteById
     * @Desrciption 根据主键删除门诊结算信息
     * @param id 主键
     * @Author Ou·Mr
     * @Date 2020/8/25 9:30
     * @Return int 受影响的行数
     */
    int deleteById(@Param("id") String id);

    /**
     * @Menthod deleteByIds
     * @Desrciption  根据主键批量删除门诊结算
     * @param ids 主键
     * @Author Ou·Mr
     * @Date 2020/8/25 9:33
     * @Return int 受影响的行数
     */
    int deleteByIds(@Param("ids") String[] ids);

    /**
     * @Menthod findByCondition
     * @Desrciption 查询门诊结算信息
     * @param outptSettleDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/25 9:38
     * @Return java.util.List<cn.hsa.module.outpt.fees.dto.OutptSettleDTO> 结果集
     */
    List<OutptSettleDTO> findByCondition(OutptSettleDTO outptSettleDTO);

    /**
     * @Menthod: queryCharge()
     * @Desrciption: 收费查询
     * @Param: OutptSettleDTO--门诊结算DTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/9/2 14:19
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sys.PageDTO>
     **/
    List<OutptSettleDTO> queryCharge(OutptSettleDTO outptSettleDTO);

    /**
     * @Menthod: queryCharge()
     * @Desrciption: 门诊退费 - 查询门诊已结算患者信息
     * @Param: OutptSettleDTO--门诊结算DTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/9/2 14:19
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sys.PageDTO>
     **/
    List<Map<String, Object>> queryOutChargePage(OutptSettleDTO outptSettleDTO);

    /**
     * @Menthod: queryOutptPrescribes
     * @Desrciption: 门诊退费 - 查询门诊处方信息
     * @Param: outptSettleDTO
     * @Author: liaojiguang
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/9/08 14:19
     * @Return: List
     **/
    List<OutptCostDTO> queryOutptPrescribes(OutptSettleDTO outptSettleDTO);

	/**
	 * @Description: 当医院门诊退费需要医生申请时，在未发药的情况下，直接取医生申请退费数量做为收费室退费数量
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/11 15:13
	 * @Return
	 */
	List<OutptCostDTO> queryOutptPrescribesandRefundApply(OutptSettleDTO outptSettleDTO);

    /**
     * @Menthod queryOutCharge
     * @Desrciption 门诊退费 - 查询门诊处方类型
     * @param outptSettleDTO
     * @Author liaojiguang
     * @Date 2020/9/08 13:53
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    List<OutptPrescribeDO> queryOutptPrescribe(OutptSettleDTO outptSettleDTO);

    /**
     * @Menthod queryOutCharge
     * @Desrciption 门诊退费 - 查询门诊就诊信息
     * @param selectMap
     * @Author liaojiguang
     * @Date 2020/9/08 13:53
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    OutptVisitDTO getVisitByVisitId(Map<String, Object> selectMap);

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
     * @Menthod getDiagnoseInfo
     * @Desrciption 查询门诊诊断信息
     * @param outptSettleDTO 查询条件
     * @Author liaojiguang
     * @Date 2020/10/21 13:34
     * @Return java.util.List
     */
    List<OutptDiagnoseDTO> getDiagnoseInfo(OutptSettleDTO outptSettleDTO);

    /**
     * @Menthod getInvoiceInfo
     * @Desrciption 获取发票信息
     * @param outptSettleDTO 查询条件
     * @Author liaojiguang
     * @Date 2020/10/21 13:34
     * @Return java.util.Map
     */
    Map<String,Object> getInvoiceInfo(OutptSettleDTO outptSettleDTO);

    /**
     * @Menthod updateOutptPrescribe
     * @Desrciption 更新门诊处方结算标志
     * @param oldOutptSettleDTO 查询条件
     * @Author liaojiguang
     * @Date 2020/10/25 13:34
     * @Return java.util.Map
     */
    int updateOutptPrescribe(OutptSettleDTO oldOutptSettleDTO);


    UploadFee getInsureFee(UploadFee uploadFee);

    List<RxList> getInsureFeeRxList(UploadFee uploadFee);

    List<ExtDataList> getInsureFeeExtDataList(UploadFee uploadFee);

    List<DiseList> getInsureFeeDiseList(UploadFee uploadFee);

    UploadFee getDzpzOrgId(UploadFee uploadFee);

    int updateIndividualVisitToken(Map<String, Object> selectMap);

    List<InsureIndividualCostDTO> getInsureIndividualCost(UploadFee uploadFee);

    int insertInsureCost(@Param("costDTOList") List<InsureIndividualCostDTO> insureIndividualCostDTOList);

    InsureIndividualSettleDO getInsureInsureIndividualSettle(Map<String, Object> selectMap);

    int insertInsureIndividualSettle(InsureIndividualSettleDO insureIndividualSettleDO);

    int updateOutptSettle(Map<String, Object> selectMap);

    int updateOutptSettleStatus(Map<String, Object> selectMap);

    OutptSettleDTO getById(Map<String, Object> selectMap);

    List<InsureIndividualSettleDTO> queryOutptSettle(Map selectMap);

    /**
     * @Description: 删除医保费用表记录，（未结算的）
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/11/15 19:51
     * @Return
     */
    int deleteInsureCost(Map<String, String> map);

    /**
     * @Menthod: queryCreditCharge()
     * @Desrciption: 挂账查询
     * @Param: OutptSettleDTO--门诊结算DTO
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/2/28 11:12
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sys.PageDTO>
     **/
    List<OutptSettleDTO> queryCreditCharge(OutptSettleDTO outptSettleDTO);

    /**
     * @Menthod: updateCreditStatus()
     * @Desrciption: 更新补缴状态
     * @Param: OutptSettleDTO--门诊结算DTO
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/3/1 11:15
     * @Return: int
     **/
    int updateCreditStatus(OutptSettleDTO outptSettleDTO);

    /**
     * @Menthod: updateRegisterCreditStatus()
     * @Desrciption: 更新挂号补缴状态
     * @Param: OutptSettleDTO--门诊结算DTO
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/3/3 15：46
     * @Return: int
     **/
    int updateRegisterCreditStatus(OutptSettleDTO outptSettleDTO);

    /**
     * @Menthod: queryApplyRefundCharge()
     * @Desrciption: 门诊退费 - 查询门诊已申请退费的病人
     * @Param: OutptSettleDTO--门诊结算DTO
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/04/22 09:51
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sys.PageDTO>
     **/
    List<Map<String, Object>> queryApplyRefundCharge(OutptSettleDTO outptSettleDTO);

    /**
     * @Menthod: queryPaymentSettle()
     * @Desrciption: 根据结算id查询诊间支付结算信息
     * @Param: OutptSettleDTO--门诊结算DTO
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/09/06 10:05
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sys.PageDTO>
     **/
    PaymentSettleDTO queryPaymentSettle(OutptSettleDTO outptSettleDTO);

    /**
     * @Menthod: updatePaymentSettleStatus()
     * @Desrciption: 更新诊间支付结算信息状态
     * @Param: paymentSettleDO
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/09/06 10:13
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sys.PageDTO>
     **/
    int updatePaymentSettleStatus(PaymentSettleDO paymentSettleDO);

    /**
     * @Menthod: insertPaymentSettleInfo()
     * @Desrciption: 新增诊间支付结算信息
     * @Param: paymentSettleDO
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/09/06 10:21
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sys.PageDTO>
     **/
    int insertPaymentSettleInfo(PaymentSettleDO paymentSettleDO);

}
