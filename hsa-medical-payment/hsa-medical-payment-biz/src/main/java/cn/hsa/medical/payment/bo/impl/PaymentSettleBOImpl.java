package cn.hsa.medical.payment.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.medical.payment.enums.PaymentExceptionEnums;
import cn.hsa.module.outpt.fees.dto.OutptSettleDTO;
import cn.hsa.module.outpt.fees.entity.OutptPayDO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.payment.bo.PaymentSettleBO;
import cn.hsa.module.payment.dao.PaymentOrderDAO;
import cn.hsa.module.payment.dao.PaymentSettleDAO;
import cn.hsa.module.payment.dto.PaymentInterfParamDTO;
import cn.hsa.module.payment.dto.PaymentSettleDTO;
import cn.hsa.module.payment.entity.PaymentOrderDO;
import cn.hsa.module.payment.entity.PaymentSettleDO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.medical.payment.bo.impl
 * @class_name: PaymentOrderBOImpl
 * @Description: 诊间诊间支付结算传输层接口
 * @Author: liyun.liu
 * @Email:liyun.liu@powersi.com
 * @Date: 2022/09/01 14:21
 * @Company: 创智和宇
 **/
@Component
public class PaymentSettleBOImpl implements PaymentSettleBO {

    @Resource
    PaymentSettleDAO paymentSettleDAO;

    @Resource
    PaymentOrderDAO paymentOrderDAO;

    @Resource
    PaymentTransferBoImpl paymentTransferBo;

    /**@Menthod queryById
     * @Describe:通过ID查询单条数据
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param id
     * @Date: 2022-09-01 19:10
     * @return PaymentOrderDO
     */
    @Override
    public PaymentSettleDO queryById(String id) {
        return paymentSettleDAO.queryById(id);
    }

    /**@Menthod queryAllPaymentSettle
     * @Describe: 查询诊间支付结算信息
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param paymentSettleDTO
     * @Date: 2022-09-01 19:10
     * @return List<PaymentOrderDO>
     */
    @Override
    public PageDTO queryAllPaymentSettle(PaymentSettleDTO paymentSettleDTO) {
        // 设置分页信息
        PageHelper.startPage(paymentSettleDTO.getPageNo(), paymentSettleDTO.getPageSize());
        List<Map<String, Object>> paymentSettleDOList =paymentSettleDAO.queryPaymentBillList(paymentSettleDTO);
        paymentSettleDTO.setPayCode(Constants.ZJ_PAY_TYPE.WX);
        List<Map<String, Object>> wxPaymentSettleInfo = paymentSettleDAO.queryPaymentBillDetailList(paymentSettleDTO);
        paymentSettleDTO.setPayCode(Constants.ZJ_PAY_TYPE.ZFB);
        List<Map<String, Object>> zfbPaymentSettleInfo = paymentSettleDAO.queryPaymentBillDetailList(paymentSettleDTO);
        if (paymentSettleDOList!=null&&paymentSettleDOList.size()>0){
            for (Map<String, Object> paymentSettle:paymentSettleDOList){
                if (Constants.ZJ_PAY_TYPE.WX.equals(MapUtils.get(paymentSettle,"payCode"))){  // 支付类型为微信
                    paymentSettle.put("paymentBillDetail",wxPaymentSettleInfo);
                }else if (Constants.ZJ_PAY_TYPE.ZFB.equals(MapUtils.get(paymentSettle,"payCode"))){ // 支付类型为支付宝
                    paymentSettle.put("paymentBillDetail",zfbPaymentSettleInfo);
                }
                paymentSettle.put("startTime",paymentSettleDTO.getStartTime());
                paymentSettle.put("endTime",paymentSettleDTO.getEndTime());
            }
        }
        return PageDTO.of(paymentSettleDOList);
    }

    /**@Menthod insert
     * @Describe:新增诊间支付结算信息
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param paymentOrderDO 主键
     * @Date: 2022-09-01 19:10
     * @return Boolean
     */
    @Override
    public Boolean insert(PaymentSettleDO paymentOrderDO) {
        return paymentSettleDAO.insert(paymentOrderDO)>0;
    }

    /**@Menthod insertBatch
     * @Describe:批量新增诊间支付结算信息
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param entities
     * @Date: 2022-09-01 19:08
     * @return Boolean
     */
    @Override
    public Boolean insertBatch(List<PaymentSettleDO> entities) {
        return paymentSettleDAO.insertBatch(entities)>0;
    }

    /**@Menthod update
     * @Describe:更新诊间支付结算信息
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param paymentSettleDO
     * @Date: 2022-09-01 19:07
     * @return Boolean
     */
    @Override
    public Boolean update(PaymentSettleDO paymentSettleDO) {
        return paymentSettleDAO.update(paymentSettleDO)>0;
    }

    /**@Menthod deleteById
     * @Describe:删除诊间支付结算信息
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param id
     * @Date: 2022-09-01 19:06
     * @return Boolean
     */
    @Override
    public Boolean deleteById(String id) {
        return paymentSettleDAO.deleteById(id)>0;
    }


    /**@Menthod quyeryPaymentInfoByCondition
     * @description 根据医院编码、就诊id、结算id 查询诊间支付结算信息
     * @param paymentSettleDTO
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/09/05 10:37
     * @return PaymentSettleDTO
     */
    @Override
    public PaymentSettleDTO quyeryPaymentInfoByCondition(PaymentSettleDTO paymentSettleDTO) {
        return paymentSettleDAO.quyeryPaymentInfoByCondition(paymentSettleDTO);
    }


    /**@Menthod updatePaymentSettleStatus
     * @description 调用支付平台支付
     * @param param
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/09/05 11:46
     * @return Map
     */
    @Override
    public Map updatePaymentSettleStatus(Map param) {
        /*todo 调用支付平台支付*/
        return paymentTransferBo.transferPayment(PaymentExceptionEnums.PAYMENT_CHARGE,new PaymentInterfParamDTO());
    }

    @Override
    public Boolean saveSettleInfo(OutptVisitDTO outptVisitDTO, OutptSettleDTO outptSettleDTO, List<OutptPayDO> outptPayDTOList) {
        //删除结算脏数据
        Map<String, String> settleParam = new HashMap<String, String>();
        settleParam.put("hospCode", outptVisitDTO.getHospCode()); //医院编码
        settleParam.put("visitId", outptVisitDTO.getId());//就诊id
        settleParam.put("isSettle", Constants.SF.F);//是否结算 = 否
        settleParam.put("statusCode", Constants.ZTBZ.ZC);//状态标志 = 正常
        paymentSettleDAO.delOutptPaymentSettleByParam(settleParam);
        settleParam.put("settleCode", Constants.SETTLECODE.WJS);//结算状态 = 未结算
        paymentOrderDAO.delOutptPaymentOrderByParam(settleParam);
        BigDecimal wxPrice = new BigDecimal(0.00);
        if (outptPayDTOList!=null&&outptPayDTOList.size()>0){
            for (OutptPayDO dto : outptPayDTOList) {
                if (dto!=null&& Constants.ZFFS.WX.equals(dto.getPayCode())){
                    wxPrice=dto.getPrice();
                    break;
                }
            }
        }
        if (BigDecimalUtils.isZero(wxPrice)){
            throw new AppException("微信实付金额不能为0");
        }
        if (StringUtils.isEmpty(outptVisitDTO.getPatientCode())){
            outptVisitDTO.setPatientCode("0");
        }
        Integer patientValueCode = Integer.parseInt(outptVisitDTO.getPatientCode());
        if (patientValueCode!=null&&patientValueCode>0){
            throw new AppException("微信扫码支付暂不支持医保病人，请走线下支付");
        }
       // 生成诊间支付预结算信息
        PaymentSettleDO paymentSettleDO =new PaymentSettleDO();
        paymentSettleDO.setId(SnowflakeUtils.getId());
        paymentSettleDO.setVisitId(outptVisitDTO.getId());
        paymentSettleDO.setHospCode(outptSettleDTO.getHospCode()); // 医院编码
        paymentSettleDO.setPayCode(Constants.ZFFS.WX);   // 支付方式： 微信
        paymentSettleDO.setPaymentPrice(wxPrice); // 微信支付实际金额
        paymentSettleDO.setTotalPrice(outptSettleDTO.getTotalPrice()); // 订单总费用
        paymentSettleDO.setStatusCode(Constants.ZTBZ.ZC); // 状态标志： 正常
        paymentSettleDO.setIsSettle(Constants.SF.F);
        paymentSettleDO.setSettleCode(Constants.JSZT.WJS); // 结算状态： 未结算
        paymentSettleDO.setSettleId(outptSettleDTO.getId()); // his结算id
        paymentSettleDO.setCrteId(outptVisitDTO.getCrteId());
        paymentSettleDO.setCrteName(outptVisitDTO.getCrteName());
        paymentSettleDO.setCrteTime(DateUtils.getNow());
        paymentSettleDAO.insert(paymentSettleDO);
        // 生成诊间支付订单预结算信息
        PaymentOrderDO paymentOrderDO =new PaymentOrderDO();
        paymentOrderDO.setId(SnowflakeUtils.getId()); // id
        paymentOrderDO.setHospCode(outptSettleDTO.getHospCode()); // 医院编码
        paymentOrderDO.setVisitId(outptVisitDTO.getId());
        paymentOrderDO.setPayCode(Constants.ZFFS.WX); // 支付方式： 微信
        paymentOrderDO.setPaymentPrice(wxPrice);  // 微信支付实际金额
        paymentOrderDO.setTotalPrice(outptSettleDTO.getTotalPrice()); // 订单总费用
        paymentOrderDO.setStatusCode(Constants.ZTBZ.ZC);    // 状态标志： 正常
        paymentOrderDO.setSettleCode(Constants.JSZT.WJS); // 结算状态： 未结算
        paymentOrderDO.setSettleId(outptSettleDTO.getId()); // his结算id
        paymentOrderDO.setCrteId(outptVisitDTO.getCrteId());
        paymentOrderDO.setCrteName(outptVisitDTO.getCrteName());
        paymentOrderDO.setCrteTime(DateUtils.getNow());
        paymentOrderDO.setUpdateName(outptVisitDTO.getCrteName());
        paymentOrderDO.setUpdateTime(DateUtils.getNow());
        paymentOrderDO.setUpdateId(outptVisitDTO.getCrteId());
        paymentOrderDAO.insert(paymentOrderDO);
        return true;
    }


    /**
     * @Menthod queryPaymentSettle
     * @Desrciption 查询诊间支付结算数据
     * @param paymentSettleDTO
     * @Author liuliyun
     * @Date 2022/9/21 16:47
     * @Return PaymentSettleDTO
     */
    @Override
    public PaymentSettleDTO queryPaymentSettle(PaymentSettleDTO paymentSettleDTO) {
        return paymentSettleDAO.queryPaymentSettle(paymentSettleDTO);
    }
}
