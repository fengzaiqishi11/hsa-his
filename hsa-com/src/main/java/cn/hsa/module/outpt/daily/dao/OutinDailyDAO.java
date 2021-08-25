package cn.hsa.module.outpt.daily.dao;

import cn.hsa.module.inpt.advancepay.entity.InptAdvancePayDO;
import cn.hsa.module.outpt.card.dto.BaseCardRechargeChangeDTO;
import cn.hsa.module.outpt.daily.dto.*;
import cn.hsa.module.outpt.daily.entity.OutinDailyAdvancePayDO;
import cn.hsa.module.outpt.daily.entity.OutinDailyCardPayDO;
import cn.hsa.module.outpt.daily.entity.OutinDailyDO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.daily.dao
 * @Class_name: OutinDaliyDAO
 * @Description: 日结缴款DAO层
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/09/24 10:10
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface OutinDailyDAO {
    /**
     * 通过日结单号查询日结数据
     *
     * @param  outinDailyDTO
     * @return 实例对象
     */
    List<OutinDailyDTO> queryOutinDailyByDailyNo(OutinDailyDTO outinDailyDTO);

    /**查询出个人日结缴款最大的开始与结束时间
    * @Method queryOutinDailyByCrteId
    * @Desrciption
    * @param outinDailyDTO
    * @Author liuqi1
    * @Date   2021/4/29 13:33
    * @Return cn.hsa.module.outpt.daily.dto.OutinDailyDTO
    **/
    OutinDailyDTO queryOutinDailyByCreateId(OutinDailyDTO outinDailyDTO);

    /**
     * @Method 分页查询日结缴款主表
     * @Description
     *
     * @Param outinDailyDTO
     *
     * @Author zhongming
     * @Date 2020/9/24 11:58
     * @Return
     **/
    List<OutinDailyDTO> queryAll(OutinDailyDTO outinDailyDTO);

    /**
     * @Method 获取最后一次缴款信息
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/24 14:54
     * @Return
     **/
    OutinDailyDTO getLastDaily(OutinDailyDTO outinDailyDTO);

    /**
     * @Method 确认缴款
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/24 14:54
     * @Return
     **/
    int update(OutinDailyDTO outinDailyDTO);

    /**
     * @Method 取消缴款
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/24 14:54
     * @Return
     **/
    int delete(OutinDailyDTO outinDailyDTO);

    /**
     * @Method 挂号 - 结算主表
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/27 9:08
     * @Return
     **/
    @MapKey("status_code")
    Map<String, Map<String, Object>> queryOutptRegisterSettle(OutinDailyDTO outinDailyDTO);

    Map<String, Map<String, Object>> queryOutptRegisterSettleAll(OutinDailyDTO outinDailyDTO);

    /**
     * @Method 门诊结算发票信息
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/10/30 13:57
     * @Return
     **/
    List<Map<String, Object>> queryOutptRegisterSettleByBillId(OutinDailyDTO dto);

    /**
     * @Method 挂号 - 费用表
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/27 9:08
     * @Return
     **/
    List<Map<String, Object>> queryOutptRegisterDetail(OutinDailyDTO outinDailyDTO);

    /**
     * @Method 挂号 - 支付方式表
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/27 9:08
     * @Return
     **/
    List<Map<String, Object>> queryOutptRegisterPay(OutinDailyDTO outinDailyDTO);

    /**
     * @Method 门诊 - 结算表
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/27 9:10
     * @Return
     **/
    @MapKey("status_code")
    Map<String, Map<String, Object>>  queryOutptSettle(OutinDailyDTO dto);

    /**
     * @Method 门诊 - 费用表
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/27 9:10
     * @Return
     **/
    Map<String, Map<String, Object>> queryOutptSettleAll(OutinDailyDTO dto);

    /**
     * @Method 门诊 - 费用表
     * @Description 
     * 
     * @Param 
     * 
     * @Author zhongming
     * @Date 2020/9/27 9:10
     * @Return 
     **/
    List<Map<String, Object>> queryOutptCost(OutinDailyDTO dto);

    /**
     * @Method 门诊 - 发票表
     * @Description 
     * 
     * @Param 
     * 
     * @Author zhongming
     * @Date 2020/9/27 9:10
     * @Return 
     **/
    List<Map<String, Object>> queryOutptSettleInvoice(OutinDailyDTO dto);

    /**
     * @Method 门诊 - 支付方式表
     * @Description 
     * 
     * @Param 
     * 
     * @Author zhongming
     * @Date 2020/9/27 9:11
     * @Return 
     **/
    List<Map<String, Object>> queryOutptPay(OutinDailyDTO dto);

    /**
     * @Method 门诊 - 合同单位支付表
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/27 9:11
     * @Return
     **/
    List<Map<String, Object>> queryOutptInsurePay(OutinDailyDTO dto);

    /**
     * @Method 住院 - 结算表
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/27 9:10
     * @Return
     **/
    @MapKey("status_code")
    Map<String, Map<String, Object>> queryInptSettle(OutinDailyDTO dto);


    Map<String, Map<String, Object>> queryInptSettleAll(OutinDailyDTO dto);
    /**
     * @Method 住院 - 费用表
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/27 9:10
     * @Return
     **/
    List<Map<String, Object>> queryIntptCost(OutinDailyDTO dto);

    /**
     * @Method 住院 - 发票表
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/27 9:10
     * @Return
     **/
    List<Map<String, Object>> queryInptSettleInvoice(OutinDailyDTO dto);

    /**
     * @Method 住院 - 支付方式表
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/27 9:11
     * @Return
     **/
    List<Map<String, Object>> queryInptPay(OutinDailyDTO dto);

    /**
     * @Method 住院 - 合同单位支付表
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/27 9:11
     * @Return
     **/
    List<Map<String, Object>> queryInptInsurePay(OutinDailyDTO dto);

    /**
     * @Method 日结 - 日结缴款主表
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/27 9:08
     * @Return
     **/
    int insertOutinDailyList(@Param("list") List<OutinDailyDTO> outinDailyDTOList);

    /**
     * @Method 日结 - 日结缴款发票表
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/27 9:08
     * @Return
     **/
    int insertOutinDailyInvoiceList(@Param("list") List<OutinDailyInvoiceDTO> outinDailyInvoiceDTOList);

    /**
     * @Method 日结 - 日结缴款支付方式表
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/27 9:09
     * @Return
     **/
    int insertOutinDailyPayList(@Param("list") List<OutinDailyPayDTO> outinDailyPayDTOList);

    /**
     * @Method 日结 - 日结缴款财务分类表
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/27 9:09
     * @Return
     **/
    int insertOutinDailyFinclassifyList(@Param("list") List<OutinDailyFinclassifyDTO> outinDailyFinclassifyDTOList);

    /**
     * @Method 日结 - 日结缴款合同单位支付表
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/27 9:09
     * @Return
     **/
    int insertOutinDailyInsureList(@Param("list") List<OutinDailyInsureDTO> outinDailyInsureList);

    /**
     * @Method 日结预交金支付方式
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/10/30 20:47
     * @Return
     **/
    int insertOutinDailyAdvancePayList(@Param("list") List<OutinDailyAdvancePayDO> odapList);

    /**
     * @Method 修改挂号结算表日结缴款ID
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/27 9:58
     * @Return
     **/
    int updateDailyIdByOutptRegisterSettle(OutinDailyDTO outinDailyDTO);

    /**
     * @Method 修改门诊结算表日结缴款ID
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/27 9:58
     * @Return
     **/
    int updateDailyIdByOutptSettle(OutinDailyDTO outinDailyDTO);

    /**
     * @Method 修改住院结算表日结缴款ID
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/27 9:58
     * @Return
     **/
    int updateDailyIdByInptSettle(OutinDailyDTO outinDailyDTO);

    /**
     * @Method 修改住院预交金表日结缴款ID
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/27 9:58
     * @Return
     **/
    int updateDailyIdByInptAdvancePay(OutinDailyDTO outinDailyDTO);


    List<Map<String, Object>> queryDailyByDailyNo(OutinDailyDTO outinDailyDTO);

    /**
     * @Method 根据日结单号、日结类型查询日结主表
     * @Description 
     * 
     * @Param 
     * 
     * @Author zhongming
     * @Date 2020/9/30 16:01
     * @Return 
     **/
    OutinDailyDO getOutinDaily(OutinDailyDTO outinDailyDTO);

    /**
     * @Method 根据日结单号、日结类型查询日结支付方式表
     * @Description 
     * 
     * @Param 
     * 
     * @Author zhongming
     * @Date 2020/9/30 16:01
     * @Return 
     **/
    List<Map<String, Object>> queryOutinDailyPay(OutinDailyDTO outinDailyDTO);

    /**
     * @Method 根据日结单号、日结类型查询日结发票表
     * @Description 
     * 
     * @Param 
     * 
     * @Author zhongming
     * @Date 2020/9/30 19:35
     * @Return 
     **/
    List<Map<String, Object>> queryOutinDailyInvoiceByDailyNo(OutinDailyDTO outinDailyDTO);

    /**
     * @Method 住院预交金
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/28 10:00
     * @Return
     **/
    List<InptAdvancePayDO> queryInptAdvancePay(OutinDailyDTO dto);

    /**
     * @Method 住院预交金 - 支付方式
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/28 10:00
     * @Return
     **/
    String queryOutinDailyAdvancePay(OutinDailyDTO dto);

    /**
     * @Description: 一卡通充值、退款 - 支付方式
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/8/23 10:57
     * @Return
     */
    String queryOutinDailyCardPay(OutinDailyDTO dto);

    /**
     * @Method 日结缴款 - 结算列表
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/11/2 14:57
     * @Return
     **/
    List<Map<String, Object>> querySettle(OutinDailyDTO outinDailyDTO);

    /**
     * @Method 日结缴款 - 预交金列表
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/11/2 15:00
     * @Return
     **/
    List<Map<String, Object>> queryAdvancePay(OutinDailyDTO outinDailyDTO);

    /**
     * @Method 日结缴款 - 预交金冲抵列表
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/11/2 15:07
     * @Return
     **/
    List<Map<String, Object>> queryAdvancePayCd(OutinDailyDTO outinDailyDTO);

    /**
     * @Method 日结缴款 - 清空已存在的日结ID
     * @Description 
     * 
     * @Param 
     * 
     * @Author zhongming
     * @Date 2020/12/8 14:08
     * @Return 
     **/
    int updateDailyIdIsNull(OutinDailyDTO outinDailyDTO);

    /**
     * @Description: 一卡通充值
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/8/19 15:40
     * @Return
     */
    List<BaseCardRechargeChangeDTO> queryBaseCardCZ(OutinDailyDTO dto);

    /**
     * @Description: 更新一卡通异动表缴款ID
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/8/19 17:48
     * @Return
     */
    void updateDailyIdToBaseCardRechangeChargeDO(OutinDailyDTO outinDailyDTO);

    /**
     * @Description: 日结缴款  一卡通充值、退款支付表
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/8/19 17:47
     * @Return
     */
    void insertOutinDailyCardPayList(List<OutinDailyCardPayDO> odcpList);

    /**
     * @Description: 根据缴款id查询门诊划价收费结算表一卡通支付总金额
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/8/23 14:01
     * @Return
     */
    Map<String, Object> getOutptSettleCardTotalPrice(OutinDailyDTO dto);

    /**
     * @Description: 根据缴款id查询门诊挂号结算表一卡通支付总金额
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/8/23 14:28
     * @Return
     */
    Map<String, Object> getOutptRegisterSettleCardTotalPrice(OutinDailyDTO dto);

    /**
     * @Description: 查询缴款表一卡通支付记录 queryOutinDailyCardPayPay
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/8/23 16:07
     * @Return
     */
    List<Map<String, Object>> queryOutinDailyCardPays(OutinDailyDTO dto);

    /**
     * @Description: 一卡通充值，退款
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/8/23 16:47
     * @Return
     */
    List<Map<String, Object>> queryBaseCardCZAndTK(OutinDailyDTO outinDailyDTO);

    /**
     * @Description: 一卡通消费、退费明细
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/8/23 17:24
     * @Return
     */
    List<Map<String, Object>> queryCardConsumePay(OutinDailyDTO outinDailyDTO);
}
