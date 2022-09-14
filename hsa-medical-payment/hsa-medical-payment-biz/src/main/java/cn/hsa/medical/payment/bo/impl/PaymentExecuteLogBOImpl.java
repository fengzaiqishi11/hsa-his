package cn.hsa.medical.payment.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.module.payment.bo.PaymentExecuteLogBO;
import cn.hsa.module.payment.dao.PaymentExecuteLogDAO;
import cn.hsa.module.payment.entity.PaymentExecuteLogDO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.SnowflakeUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.medical.payment.bo.impl
 * @class_name: PaymentOrderBOImpl
 * @Description: 诊间支付订单传输层接口
 * @Author: liyun.liu
 * @Email:liyun.liu@powersi.com
 * @Date: 2022/09/01 14:21
 * @Company: 创智和宇
 **/
@Component
public class PaymentExecuteLogBOImpl implements PaymentExecuteLogBO {

    @Resource
    PaymentExecuteLogDAO paymentExecuteLogDAO;

    /**@Menthod queryById
     * @Describe:通过ID查询诊间支付日志
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param id
     * @Date: 2022-09-01 19:14
     * @return PaymentExecuteLogDO
     */
    @Override
    public PaymentExecuteLogDO queryById(String id) {
        return paymentExecuteLogDAO.queryById(id);
    }

    /**@Menthod queryAllByLimit
     * @Describe:查询诊间支付日志信息
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param paymentExecuteLogDO
     * @Date: 2022-09-01 17:24
     * @return List<PaymentOrderDO>
     */
    @Override
    public PageDTO queryAllByLimit(PaymentExecuteLogDO paymentExecuteLogDO) {
        List<PaymentExecuteLogDO> paymentExecuteLogDOList =paymentExecuteLogDAO.queryAllByLimit(paymentExecuteLogDO);
        return PageDTO.of(paymentExecuteLogDOList);
    }

    /**@Menthod count
     * @Describe:查询诊间支付日志条数
     * @param paymentExecuteLogDO
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @Date: 2022-09-01 19:20
     * @return long
     */
    @Override
    public long count(PaymentExecuteLogDO paymentExecuteLogDO) {
        return paymentExecuteLogDAO.count(paymentExecuteLogDO);
    }

    /**@Menthod insert
     * @Describe:新增诊间支付日志信息
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param param
     * @Date: 2022-09-01 17:24
     * @return Boolean
     */
    @Override
    public Boolean insert(Map<String,Object> param) {
        String hospCode = MapUtils.get(param,"hospCode");
        String crteId = MapUtils.get(param,"crteId");
        String crteName = MapUtils.get(param,"crteName");
        String visitId = MapUtils.get(param,"visitId");
        String msgId = MapUtils.get(param,"msgId");
        String msgInfo = MapUtils.get(param,"msgInfo");
        String msgName = MapUtils.get(param,"msgName");
        String paramMapJson = MapUtils.get(param,"paramMapJson");
        String resultStr = MapUtils.get(param,"resultStr");
        /*日志记录*/
        PaymentExecuteLogDO paymentExecuteLogDO =new PaymentExecuteLogDO();
        //先判断返回串是否包含infcode
        String resultCode = "0";
        if (resultStr.contains("infcode")) {
            Map<String,Object> m = JSONObject.parseObject(resultStr,Map.class);
            resultCode = MapUtils.get(m,"infcode","0");
            if(!"0".equals(resultCode)){
                paymentExecuteLogDO.setErrorMsg("调用失败"); // 错误信息
            }else{
                paymentExecuteLogDO.setErrorMsg("调用成功"); // 错误信息
            }
        }else{
            paymentExecuteLogDO.setErrorMsg("调用成功"); // 错误信息
        }
        paymentExecuteLogDO.setId(SnowflakeUtils.getId());
        paymentExecuteLogDO.setHospCode(hospCode); // 医院编码
        paymentExecuteLogDO.setVisitId(visitId);
        paymentExecuteLogDO.setInParams(paramMapJson); // 入参
        paymentExecuteLogDO.setOutParams(resultStr); // 出参
        paymentExecuteLogDO.setMsgId(msgId); // 业务流水号
        paymentExecuteLogDO.setMsgInfo(msgInfo); // 功能号：支付、查询、退款
        paymentExecuteLogDO.setCode(resultCode);  // 状态码：0 成功
        paymentExecuteLogDO.setCrteId(crteId); // 创建人id
        paymentExecuteLogDO.setCrteName(crteName); // 创建人
        paymentExecuteLogDO.setCrteTime(DateUtils.getNow());
        paymentExecuteLogDO.setMsgName(msgName); // 功能名称
        return paymentExecuteLogDAO.insert(paymentExecuteLogDO)>0;
    }

    /**@Menthod insertBatch
     * @Describe:批量新增诊间支付日志信息
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param entities
     * @Date: 2022-09-01 19:25
     * @return Boolean
     */
    @Override
    public Boolean insertBatch(List<PaymentExecuteLogDO> entities) {
        return paymentExecuteLogDAO.insertBatch(entities)>0;
    }

    /**@Menthod update
     * @Describe:更新支付订单信息
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param paymentExecuteLogDO
     * @Date: 2022-09-01 19:17
     * @return Boolean
     */
    @Override
    public Boolean update(PaymentExecuteLogDO paymentExecuteLogDO) {
        return paymentExecuteLogDAO.update(paymentExecuteLogDO)>0;
    }

    /**@Menthod deleteById
     * @Describe:删除订单信息
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param id
     * @Date: 2022-09-01 19:18
     * @return Boolean
     */
    @Override
    public Boolean deleteById(String id) {
        return paymentExecuteLogDAO.deleteById(id)>0;
    }
}
