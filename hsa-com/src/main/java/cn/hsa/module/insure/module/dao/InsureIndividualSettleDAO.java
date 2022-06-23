package cn.hsa.module.insure.module.dao;


import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.fees.dto.InptSettleDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualFundDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualSettleDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.entity.InsureIndividualSettleDO;
import cn.hsa.module.outpt.fees.dto.OutptSettleDTO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.insureIndividualSettle.dao
 * @Class_name:: InsureIndividualSettleDAO
 * @Description: 医保个人结算查询数据库访问层
 * @Author: fuhui
 * @Date: 2020/11/5 9:18
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InsureIndividualSettleDAO {


    /**
     * @Method getById()
     * @Desrciption  根据id查询医保结算个人信息
     * @Param insureIndividualSettleDTO个人结算数据库传输对象
     *
     * @Author fuhui
     * @Date   2020/11/5 9:21
     * @Return insureIndividualSettleDTO个人结算数据库传输对象
     **/
    InsureIndividualSettleDTO getById(InsureIndividualSettleDTO insureIndividualSettleDTO);


    /**
     * @Method queryPage()
     * @Desrciption  分页查询医保结算个人信息
     * @Param insureIndividualSettleDTO个人结算数据库传输对象
     *
     * @Author fuhui
     * @Date   2020/11/5 9:21
     * @Return insureIndividualSettleDTO个人结算数据库传输对象集合
     **/
    List<InsureIndividualSettleDTO> queryPage(InsureIndividualSettleDTO insureIndividualSettleDTO);

    /**
     * @Menthod insert
     * @Desrciption 保存医保结算信息
     * @param insureIndividualSettleDO 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/29 20:27 
     * @Return int 受影响的行数
     */
    int insert(InsureIndividualSettleDO insureIndividualSettleDO);

    /**
     * @Menthod insertSelective
     * @Desrciption 保存医保结算信息
     * @param insureIndividualSettleDO 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/29 20:29 
     * @Return int 受影响行数
     */
    int insertSelective(InsureIndividualSettleDO insureIndividualSettleDO);

    /**
     * @Menthod delInsureIndividualSettleByVisitId
     * @Desrciption 根据就诊id删除医保结算信息
     * @param param 请求参数(hospCode:医院编码、visitId:就诊id、settleState:结算状态)
     * @Author Ou·Mr
     * @Date 2020/11/29 20:58
     * @Return int 受影响行数
     */
    int delInsureIndividualSettleByVisitId(Map<String,String> param);

    /**
     * @Menthod findByCondition
     * @Desrciption 根据医院编码、就诊id、结算id 查询医保结算信息
     * @param insureIndividualSettleDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/11/30 11:32 
     * @Return cn.hsa.module.insure.module.dto.InsureIndividualSettleDTO
     */
    InsureIndividualSettleDTO findByCondition(InsureIndividualSettleDTO insureIndividualSettleDTO);
    
    /**
     * @Menthod updateByPrimaryKey
     * @Desrciption  修改医保结算信息
     * @param insureIndividualSettleDO 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/30 12:21 
     * @Return int 受影响行数
     */
    int updateByPrimaryKey(InsureIndividualSettleDO insureIndividualSettleDO);

    /**
     * @Menthod updateByPrimaryKeySelective
     * @Desrciption 修改医保结算信息
     * @param insureIndividualSettleDO 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/30 12:22 
     * @Return int 受影响函数
     */
    int updateByPrimaryKeySelective(InsureIndividualSettleDO insureIndividualSettleDO);

    /**
     * @Menthod getByParams
     * @Desrciption 修改医保结算信息
     * @param settleMap 请求参数
     * @Author liaojiguang
     * @Date 2020/12/02 11:10
     * @Return InsureIndividualSettleDTO
     */
    InsureIndividualSettleDTO getByParams(Map<String, Object> settleMap);


    List<InsureIndividualSettleDTO> queryAll(InsureIndividualSettleDTO individualSettleDTO);

    void updateSettleMsgId(@Param("settleDTOList") List<InsureIndividualSettleDTO> settleDTOList);

    /**
     * @Method querySettle
     * @Desrciption  根据就诊id查询结算信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/28 17:11
     * @Return
    **/
    InsureIndividualSettleDTO querySettle(InsureIndividualSettleDTO individualSettleDTO);

    /**
     * @param fundDTOList
     * @Method insertBatchFund
     * @Desrciption 结算以后 把基金信息保存到基金表当中
     * @Param
     * @Author fuhui
     * @Date 2021/4/29 19:27
     * @Return
     */
    Boolean insertBatchFund(@Param("fundDTOList") List<InsureIndividualFundDTO> fundDTOList);

    /**
     * @Method selectInsureSettInfo
     * @Desrciption  医保统一支付平台：查询医保结算信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/5/10 9:25
     * @Return
    **/
    InsureIndividualSettleDTO selectInsureSettInfo(InsureIndividualSettleDTO individualSettleDTO);

    /**
     * @Method querySettleForMap
     * @Desrciption  查询医保病人的结算信息。返回map数据
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/6/28 17:23
     * @Return
    **/
    Map<String, Object> querySettleForMap(Map<String, Object> map);

    /**
     * @Method queryInsureFundListMap
     * @Desrciption  查询医保病人结算以后返回回来的基金信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/6/28 17:28
     * @Return
    **/
    List<Map<String, Object>> queryInsureFundListMap(Map<String, Object> map);

    /**
     * @Method queryInptSettle
     * @Desrciption  根据就诊id,his结算id,医院编码查询患者住院结算信息
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/8/6 17:38
     * @Return
    **/
    InptSettleDTO queryInptSettle(Map<String, Object> map);

    /**
     * @Method queryInptSettle
     * @Desrciption  根据就诊id,his结算id,医院编码查询患者门诊结算信息
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/8/6 17:38
     * @Return
     **/
    OutptSettleDTO queryOutptSettle(Map<String, Object> map);

    /**
     * @Method updateInsureSettleValue
     * @Desrciption  把没有取消结算的his的医保结算表作废
     * @Param
     *
     * @Author fuhui
     * @Date   2021/10/12 15:40
     * @Return
    **/
    void updateInsureSettleValue(Map<String, Object> map);

    int updateByInsureSettleId(InsureIndividualSettleDTO insureIndividualSettleDTO);

    /**
     * @param map
     * @Method selectInsureIndividualSettleById
     * @Desrciption 通过his结算id、就诊id查询医保结算表记录
     * @Param
     * @Author fuhui
     * @Date 2021/12/20 10:30
     * @Return
     */
    InsureIndividualSettleDTO selectInsureIndividualSettleById(Map<String, Object> map);

    /**
     * @Method
     * @Desrciption
     * @Param
     *
     * @Author fuhui
     * @Date   2022/2/23 9:41
     * @Return
    **/
    int queryInsureSettle(InsureIndividualVisitDTO insureIndividualVisitDTO);

    /**
     * @Method updateEntityAcctPay
     * @Desrciption  更新扣减后医保结算表的个人账户金额
     * @Param
     *
     * @Author fuhui
     * @Date   2022/3/23 16:30
     * @Return
    **/
    void updateEntityAcctPay(InsureIndividualSettleDTO individualSettleDTO);

    /**
     * @Method updateOutptSettleAcctPay
     * @Desrciption  更新住院信息表
     * @Param
     *
     * @Author fuhui
     * @Date   2022/3/23 16:39
     * @Return
    **/
    void updateInptSettleAcctPay(InsureIndividualSettleDTO individualSettleDTO);

    /**
     * @Method updateOutptSettleAcctPay
     * @Desrciption  更新门诊结算信息表
     * @Param
     *
     * @Author fuhui
     * @Date   2022/3/23 16:39
     * @Return
    **/
    void updateOutptSettleAcctPay(InsureIndividualSettleDTO individualSettleDTO);

    /**
     * @Method selectTotalAdvance
     * @Desrciption  查询预交金金额
     * @Param
     *
     * @Author fuhui
     * @Date   2022/3/25 15:33
     * @Return
    **/
    BigDecimal selectTotalAdvance(Map<String, Object> map);

    /**
     * 根据就诊ID获取最新一条结算信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-06-17 8:50
     * @return cn.hsa.module.insure.module.dto.InsureIndividualSettleDTO
     */
    InsureIndividualSettleDTO getInsureSettleByVisitId(Map<String, Object> map);
}

