package cn.hsa.insure.unifiedpay.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualVisitDAO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.outpt.bo.InsureReckonBO;
import cn.hsa.module.insure.outpt.dao.InsureReckonDAO;
import cn.hsa.module.insure.outpt.dao.InsureReversalTradeDAO;
import cn.hsa.module.insure.outpt.dto.InsureReckonDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.unifiedpay.bo.impl
 * @Class_name: insureUnifiedPayReversalTradeBOImpl
 * @Describe: 医保通一支付平台, 冲正交易
 * @Author: liuqi1
 * @Eamil: liuqi1@powersi.com.cn
 * @Date: 2021/4/12 11:16
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@Component
public class InsureReckonBOImpl extends HsafBO implements InsureReckonBO {
    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;

    @Resource
    InsureReversalTradeDAO insureReversalTradeDAO;

    @Resource
    InsureIndividualVisitDAO insureIndividualVisitDAO;

    @Resource
    private SysParameterService sysParameterService_consumer;

    @Resource
    private InsureReckonDAO insureReckonDAO;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 清算查询
     *
     * @param insureReckonDTO
     * @Method queryInsureReckonInfo
     * @Desrciption
     * @Author liaojiguang
     * @Date 2021/9/9 19:45
     * @Return
     **/
    @Override
    public PageDTO queryInsureReckonInfo(InsureReckonDTO insureReckonDTO) {
        // 设置分页信息
        PageHelper.startPage(insureReckonDTO.getPageNo(), insureReckonDTO.getPageSize());
        return PageDTO.of(insureReckonDAO.queryInsureReckonInfoPage(insureReckonDTO));
    }

    /**
     * 清算新增
     *
     * @param insureReckonDTO
     * @Method addInsureReckonInfo
     * @Desrciption
     * @Author liaojiguang
     * @Date 2021/9/9 19:45
     * @Return
     **/
    @Override
    public Boolean addInsureReckonInfo(InsureReckonDTO insureReckonDTO) {
        insureReckonDTO.setId(SnowflakeUtils.getId());
        insureReckonDTO.setCrteTime(new Date());

        // 是否退费，REFD_SETL_FLAG = 1 则只查询正常的
        String state = null;
        Map<String,Object> selectParamterCodeMap = new HashMap<>();
        selectParamterCodeMap.put("hospCode",insureReckonDTO.getHospCode());
        selectParamterCodeMap.put("code","REFD_SETL_FLAG");
        WrapperResponse<SysParameterDTO> wr = sysParameterService_consumer.getParameterByCode(selectParamterCodeMap);
        if (wr != null && wr.getData() != null && "1".equals(wr.getData().getValue()) ) {
            state = "0";
        }

        Map<String,Object> selectMap = new HashMap<>();
        selectMap.put("startDate",DateUtils.format(insureReckonDTO.getBegndate(),DateUtils.Y_M_D));
        selectMap.put("endDate",DateUtils.format(insureReckonDTO.getEnddate(),DateUtils.Y_M_D));
        selectMap.put("insureRegCode",insureReckonDTO.getInsureRegCode());
        selectMap.put("state",state);
        selectMap.put("clrType",insureReckonDTO.getClrType());
        selectMap.put("clrWay",insureReckonDTO.getClrWay());
        Map<String,Object> resultMap = insureReversalTradeDAO.getHisReckonInfo(selectMap);
        insureReckonDTO.setAcctPay(MapUtils.get(resultMap,"acct_pay"));
        insureReckonDTO.setPsntime(Integer.valueOf(MapUtils.get(resultMap,"fixmedins_setl_cnt").toString()));
        insureReckonDTO.setMedSumfee(MapUtils.get(resultMap,"medfee_sumamt"));
        insureReckonDTO.setMedfeeSumamt(MapUtils.get(resultMap,"medfee_sumamt"));
        insureReckonDTO.setCashPayamt(MapUtils.get(resultMap,"person_price"));
        insureReckonDTO.setMedicineOrgCode(MapUtils.get(resultMap,"fixmedins_code"));
        insureReckonDTO.setSetlym(DateUtils.format(insureReckonDTO.getBegndate(),DateUtils.YM));
        insureReckonDTO.setFundAppySum(MapUtils.get(resultMap,"fund_pay_sumamt"));
        insureReckonDAO.addInsureReckonInfo(insureReckonDTO);
        return true;
    }

    /**
     * 清算编辑
     *
     * @param insureReckonDTO
     * @Method updateInsureReckonInfo
     * @Desrciption
     * @Author liaojiguang
     * @Date 2021/9/9 19:45
     * @Return
     **/
    @Override
    public Boolean updateInsureReckonInfo(InsureReckonDTO insureReckonDTO) {
        return null;
    }

    /**
     * 清算申请
     *
     * @param insureReckonDTO
     * @Method updateToInsureReckon
     * @Desrciption
     * @Author liaojiguang
     * @Date 2021/9/9 19:45
     * @Return
     **/
    @Override
    public Boolean updateToInsureReckon(InsureReckonDTO insureReckonDTO) {
        String hospCode = insureReckonDTO.getHospCode();
        String insureRegCode = insureReckonDTO.getInsureRegCode();
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("clr_type",insureReckonDTO.getClrType());	       // 清算类别	字符型	30	Y	Y
        dataMap.put("clr_way",insureReckonDTO.getClrWay());	       // 清算方式	字符型	30	Y
        dataMap.put("setlym",insureReckonDTO.getSetlym());	       // 清算年月	字符型	6		Y
        dataMap.put("psntime",insureReckonDTO.getPsntime());	       // 清算人次	数值型	6		Y
        dataMap.put("medfee_sumamt",insureReckonDTO.getMedfeeSumamt());   // 医疗费总额	数值型	16,2		Y
        dataMap.put("med_sumfee",insureReckonDTO.getMedSumfee());	   // 医保认可费用总额	数值型	16,2		Y
        dataMap.put("fund_appy_sum",insureReckonDTO.getFundAppySum());   // 基金申报总额	数值型	16,2		Y
        dataMap.put("cash_payamt",insureReckonDTO.getCashPayamt());	   // 现金支付金额	数值型	16,2		Y
        dataMap.put("acct_pay",insureReckonDTO.getAcctPay());	       // 个人账户支出	数值型	16,2		Y
        dataMap.put("begndate",DateUtils.format(insureReckonDTO.getBegndate(),DateUtils.Y_M_D));	       // 开始日期	日期型			Y	yyyy-MM-dd
        dataMap.put("enddate",DateUtils.format(insureReckonDTO.getEnddate(),DateUtils.Y_M_D));	       // 结束日期	日期型			Y	yyyy-MM-dd
        Map<String,Object> map = invokingUpay(hospCode, insureRegCode, Constant.UnifiedPay.OUTPT.UP_3203, dataMap);
        logger.info(map.toString());
        Map result = (Map)map.get("result");
        String clrAppyEvtId = result.get("clr_appy_evt_id").toString();
        insureReckonDTO.setIsDeclare(Constants.SF.S);
        insureReckonDTO.setClrAppyEvtId(clrAppyEvtId);
        return insureReckonDAO.updateInsureReckonInfo(insureReckonDTO) > 0;
    }

    /**
     * 清算撤销申请
     *
     * @param insureReckonDTO
     * @Method updateToRevokeInsureReckon
     * @Desrciption
     * @Author liaojiguang
     * @Date 2021/9/9 19:45
     * @Return
     **/
    @Override
    public Boolean updateToRevokeInsureReckon(InsureReckonDTO insureReckonDTO) {
        String hospCode = insureReckonDTO.getHospCode();
        String insureRegCode = insureReckonDTO.getInsureRegCode();
        String clrAppyEvtId = insureReckonDTO.getClrAppyEvtId();
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("clr_appy_evt_id",clrAppyEvtId);
        this.invokingUpay(hospCode, insureRegCode, Constant.UnifiedPay.OUTPT.UP_3204, dataMap);
        insureReckonDTO.setIsDeclare(Constants.SF.F);
        return insureReckonDAO.updateInsureReckonInfo(insureReckonDTO) > 0;
    }

    /**
     * 医药机构清算申请 - 删除
     *
     * @param insureReckonDTO
     * @Method deleteInsureReckon
     * @Desrciption
     * @Author liaojiguang
     * @Date 2021/9/9 17:31
     * @Return
     **/
    @Override
    public Boolean deleteInsureReckon(InsureReckonDTO insureReckonDTO) {
        return insureReckonDAO.deleteInsureReckon(insureReckonDTO) > 0;
    }

    /**
     * 调用医保统一支付，返回调用结果
     *
     * @param hospCode      医院编码
     * @param insureRegCode 医保注册编码
     * @param infno         功能号
     * @param dataMap       接口数据
     * @Method invokingUpay
     * @Desrciption
     * @Author liuqi1
     * @Date 2021/4/13 10:55
     * @Return java.lang.String
     **/
    public Map<String, Object> invokingUpay(String hospCode, String insureRegCode, String infno, Map<String, Object> dataMap) {
        //获得医保配置信息
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(insureRegCode);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);

        Map<String, Object> setDataMap = new HashMap<>();
        setDataMap.put("data", dataMap);

        //封装统一支付接口入参
        Map httpParam = new HashMap();
        httpParam.put("infno", infno);  //交易编号
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号

        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        if ("1".equals(MapUtils.get(dataMap, "flag"))) {
            dataMap.remove("flag");
            httpParam.put("input", dataMap);
        } else {
            httpParam.put("input", setDataMap);
        }

        String jsonStr = JSONObject.toJSONString(httpParam);
        String url = insureConfigurationDTO.getUrl();

        logger.info("时间：" + DateUtils.format() + "  url:" + url + "   统一支付平台入参：" + jsonStr);
        String resultJson = HttpConnectUtil.sendPostToCall(url, jsonStr);
        if (StringUtils.isEmpty(resultJson)) {
            throw new AppException("系统异常，调用医保接口返参为空");
        }
        int lengthStr = resultJson.length() > 800 ? 800 : resultJson.length();
        logger.info("时间：" + DateUtils.getNow() + "  统一支付平台回参:" + resultJson.substring(0, lengthStr) + "......");
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson);

        if ("-1".equals(MapUtils.get(resultMap, "infcode"))) {
            throw new AppException("调用医保接口失败:" + MapUtils.get(resultMap, "err_msg"));
        }
        return resultMap;
    }
}
