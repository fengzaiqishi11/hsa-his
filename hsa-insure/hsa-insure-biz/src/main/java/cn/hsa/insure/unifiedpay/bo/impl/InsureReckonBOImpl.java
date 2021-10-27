package cn.hsa.insure.unifiedpay.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
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
import java.util.*;

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
        if (resultMap == null) {
            throw new AppException("没有需要申报的数据！");
        }
        insureReckonDTO.setAcctPay(MapUtils.get(resultMap,"acct_pay"));
        insureReckonDTO.setPsntime(Integer.valueOf(MapUtils.get(resultMap,"fixmedins_setl_cnt").toString()));
        insureReckonDTO.setMedSumfee(MapUtils.get(resultMap,"medfee_sumamt"));
        insureReckonDTO.setMedfeeSumamt(MapUtils.get(resultMap,"medfee_sumamt"));
        insureReckonDTO.setCashPayamt(MapUtils.get(resultMap,"person_price"));
        insureReckonDTO.setMedicineOrgCode(MapUtils.get(resultMap,"fixmedins_code"));
        insureReckonDTO.setSetlym(DateUtils.format(insureReckonDTO.getBegndate(),DateUtils.YM));
        insureReckonDTO.setFundAppySum(MapUtils.get(resultMap,"fund_pay_sumamt"));
        insureReckonDTO.setClrOptins(MapUtils.get(resultMap,"clr_optins"));
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
        dataMap.put("clrType",insureReckonDTO.getClrType());	       // 清算类别	字符型	30	Y	Y
        dataMap.put("clrWay",insureReckonDTO.getClrWay());	       // 清算方式	字符型	30	Y
        dataMap.put("clrOptins",insureReckonDTO.getClrOptins()); // 清算中心
        dataMap.put("begndate",DateUtils.format(insureReckonDTO.getBegndate(),DateUtils.Y_M_D));	       // 开始日期	日期型			Y	yyyy-MM-dd
        dataMap.put("enddate",DateUtils.format(insureReckonDTO.getEnddate(),DateUtils.Y_M_D));	       // 结束日期	日期型			Y	yyyy-MM-dd

        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(insureRegCode);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        dataMap.put("fixmedinsCode",insureConfigurationDTO.getOrgCode());
        dataMap.put("fixmedinsName",insureReckonDTO.getHospName());

        Map<String,Object> inptMap = new HashMap<>();
        inptMap.put("MedinsClrAppyDTO",dataMap);
        this.invokingUpay(hospCode, insureRegCode, "3692", inptMap);
        insureReckonDTO.setIsDeclare(Constants.SF.S);
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
        Date begndate = insureReckonDTO.getBegndate();
        Date enddate = insureReckonDTO.getEnddate();
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("fixmedinsCode",insureReckonDTO.getFixmedinsCode());
        dataMap.put("fixmedinsName",insureReckonDTO.getFixmedinsName());
        dataMap.put("clr_appy_evt_id",insureReckonDTO.getClrAppyEvtId());
        dataMap.put("begndate",DateUtils.format(begndate,DateUtils.Y_M_D));
        dataMap.put("enddate",DateUtils.format(enddate,DateUtils.Y_M_D));

        Map<String,Object>  inptMap = new HashMap<>();
        inptMap.put("MedinsClrSumDDTO",dataMap);

        this.invokingUpay(hospCode, insureRegCode, "3691", inptMap);
        insureReckonDTO.setIsDeclare(Constants.SF.F);
        insureReckonDTO.setDeclareTime(new Date());
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
     * 医药机构清算申请 - 获取清算机构
     *
     * @param insureReckonDTO
     * @Method getInsureClrOptinsByRegCode
     * @Desrciption
     * @Author liaojiguang
     * @Date 2021/9/9 17:31
     * @Return
     **/
    @Override
    public List<String> getInsureClrOptinsByRegCode(InsureReckonDTO insureReckonDTO) {
        return insureReckonDAO.getInsureClrOptinsByRegCode(insureReckonDTO);
    }

    /**
     * 医疗机构月结算申请汇总信息分页查询-3693
     *
     * @param insureReckonDTO
     * @Method queryInsureMonSettleApplyInfo
     * @Desrciption
     * @Author liaojiguang
     * @Date 2021/9/9 17:31
     * @Return
     **/
    @Override
    public PageDTO queryInsureMonSettleApplyInfo(InsureReckonDTO insureReckonDTO) {
        List<Map<String,Object>> resultList = new ArrayList<>();
       /* InsureReckonDO insureReckon = insureReckonDAO.getInsureUnifiedReckonById(insureReckonDTO);
        if (insureReckon == null) {
            throw new AppException("未获取到清算申请信息！");
        }*/

        String hospCode = insureReckonDTO.getHospCode();
        String insureRegCode = insureReckonDTO.getInsureRegCode();
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("clrType",insureReckonDTO.getClrType());	 // 清算类别
        dataMap.put("clrYm",insureReckonDTO.getSetlym());	     // 清算年月
        dataMap.put("clrOptins",insureReckonDTO.getClrOptins()); // 清算中心
        dataMap.put("pageNum",insureReckonDTO.getPageNo());
        dataMap.put("pageSize",insureReckonDTO.getPageSize());

        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(insureRegCode);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        dataMap.put("fixmedinsCode",insureConfigurationDTO.getOrgCode());
        dataMap.put("fixmedinsName",insureReckonDTO.getHospName());

        Map<String,Object> inptMap = new HashMap<>();
        inptMap.put("QMedinsClrSumDDTO",dataMap);
        Map<String,Object> resultMap = this.invokingUpay(hospCode, insureRegCode, "3693", dataMap);
        Map<String,Object> outptMap =  (Map)resultMap.get("output");
        if (MapUtils.get(outptMap,"lastPage")) {
            resultList = MapUtils.get(outptMap,"data");
        }
        return PageDTO.of(resultList);
    }

    /**
     * 获取清算机构 -3694
     *
     * @param insureReckonDTO
     * @Method queryInsureClrOptinsInfo
     * @Desrciption
     * @Author liaojiguang
     * @Date 2021/9/22 09:15
     * @Return
     **/
    @Override
    public PageDTO queryInsureClrOptinsInfo(InsureReckonDTO insureReckonDTO) {
        String hospCode = insureReckonDTO.getHospCode();
        String fixmedinsCode = insureReckonDTO.getFixmedinsCode();
        String insureRegCode = insureReckonDTO.getInsureRegCode();

        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("fixmedinsCode",fixmedinsCode);	 // 医疗机构编码

        Map<String,Object> inptMap = new HashMap<>();
        inptMap.put("QClrOptinsDTO",dataMap);
        Map<String,Object> resultMap = this.invokingUpay(hospCode, insureRegCode, "3694", dataMap);

        Map<String,Object> outptMap =  (Map)resultMap.get("output");
        List<Map<String,Object>> resultList = MapUtils.get(outptMap,"data");
        return PageDTO.of(resultList);
    }

    /**
     * 获取清算汇总明细 -3695
     * @param insureReckonDTO
     * @Method queryInsureSettleApplyInfo
     * @Desrciption 获取清算汇总明细
     * @Author liaojiguang
     * @Date 2021/9/22 09:15
     * @Return
     **/
    @Override
    public PageDTO queryInsureSettleApplyInfo(InsureReckonDTO insureReckonDTO) {
        String hospCode = insureReckonDTO.getHospCode();
        String fixmedinsCode = insureReckonDTO.getFixmedinsCode();
        String insureRegCode = insureReckonDTO.getInsureRegCode();

        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("fixmedinsCode",fixmedinsCode);	 // 医疗机构编码
        dataMap.put("pageNum",insureReckonDTO.getPageNo()); // 页码
        dataMap.put("pageSize",insureReckonDTO.getPageSize()); // 每页条数
        dataMap.put("clrAppyEvtId",insureReckonDTO.getClrAppyEvtId()); // 申报批次号
        Map<String,Object> inptMap = new HashMap<>();
        inptMap.put("QMedinsClrSumDDTO",dataMap);
        Map<String,Object> resultMap = this.invokingUpay(hospCode, insureRegCode, "3695", dataMap);
        Map<String,Object> outptMap =  (Map)resultMap.get("output");
        List<Map<String,Object>> resultList = MapUtils.get(outptMap,"data");
        return PageDTO.of(resultList);
    }

    /**
     * 获取暂扣明细信息 -3696
     * @param insureReckonDTO
     * @Method queryInsureDetDetlList
     * @Desrciption 获取暂扣明细信息
     * @Author liaojiguang
     * @Date 2021/9/22 09:15
     * @Return
     **/
    @Override
    public PageDTO queryInsureDetDetlList(InsureReckonDTO insureReckonDTO) {
        String hospCode = insureReckonDTO.getHospCode();
        String fixmedinsCode = insureReckonDTO.getFixmedinsCode();
        String insureRegCode = insureReckonDTO.getInsureRegCode();

        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("fixmedinsCode",fixmedinsCode);	 // 医疗机构编码
        dataMap.put("pageNum",insureReckonDTO.getPageNo()); // 页码
        dataMap.put("pageSize",insureReckonDTO.getPageSize()); // 每页条数
        dataMap.put("feeClrId",insureReckonDTO.getFeeClrId()); // 清算id
        Map<String,Object> inptMap = new HashMap<>();
        inptMap.put("QMedinsClrSumDDTO",dataMap);
        Map<String,Object> resultMap = this.invokingUpay(hospCode, insureRegCode, "3696", dataMap);
        Map<String,Object> outptMap =  (Map)resultMap.get("output");
        List<Map<String,Object>> resultList = MapUtils.get(outptMap,"data");
        return PageDTO.of(resultList);
    }

    /**
     * 医疗机构月结算报表pdf文档 -3697
     *
     * @param insureReckonDTO
     * @Method getImportClredReportPdf
     * @Desrciption 医疗机构月结算报表pdf文档
     * @Author liaojiguang
     * @Date 2021/9/22 09:15
     * @Return
     **/
    @Override
    public Map<String, Object> getImportClredReportPdf(InsureReckonDTO insureReckonDTO) {
        String hospCode = insureReckonDTO.getHospCode();
        String fixmedinsCode = insureReckonDTO.getFixmedinsCode();
        String insureRegCode = insureReckonDTO.getInsureRegCode();

        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("fixmedinsCode",fixmedinsCode);	 // 医疗机构编码
        dataMap.put("feeClrId",insureReckonDTO.getFeeClrId()); // 清算id
        Map<String,Object> inptMap = new HashMap<>();
        inptMap.put("MonthSetlPayParaDTO",dataMap);
        Map<String,Object> resultMap = this.invokingUpay(hospCode, insureRegCode, "3697", dataMap);
        Map<String,Object> outptMap =  (Map)resultMap.get("output");
        return MapUtils.get(outptMap,"data");
    }

    /**
     * 获取拨付单信息 - 3407
     *
     * @param insureReckonDTO
     * @Method queryInsureAppropriationList
     * @Desrciption 获取拨付单信息
     * @Author liaojiguang
     * @Date 2021/9/22 09:15
     * @Return
     **/
    @Override
    public PageDTO queryInsureAppropriationList(InsureReckonDTO insureReckonDTO) {
        String hospCode = insureReckonDTO.getHospCode();
        String insureRegCode = insureReckonDTO.getInsureRegCode();

        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(insureRegCode);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);

        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("fixmedinsCode",insureConfigurationDTO.getOrgCode());	 // 医疗机构编码
        dataMap.put("pageNum",insureReckonDTO.getPageNo()); // 页码
        dataMap.put("pageSize",insureReckonDTO.getPageSize()); // 每页条数
        dataMap.put("clrOptins",insureReckonDTO.getClrOptins()); // 清算中心
        Map<String,Object> inptMap = new HashMap<>();
        inptMap.put("data",dataMap);
        Map<String,Object> resultMap = this.invokingUpay(hospCode, insureRegCode, "3704", dataMap);
        Map<String,Object> outptMap =  (Map)resultMap.get("output");
        List<Map<String,Object>> resultList = MapUtils.get(outptMap,"data");
        return PageDTO.of(resultList);
    }

    /**
     * 获取基金明细信息 - 3702
     *
     * @param insureReckonDTO
     * @Method queryInsureDetailFundList
     * @Desrciption 获取基金明细信息
     * @Author liaojiguang
     * @Date 2021/9/22 09:15
     * @Return
     **/
    @Override
    public PageDTO queryInsureDetailFundList(InsureReckonDTO insureReckonDTO) {
        String hospCode = insureReckonDTO.getHospCode();
        String fixmedinsCode = insureReckonDTO.getFixmedinsCode();
        String insureRegCode = insureReckonDTO.getInsureRegCode();

        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("fixmedinsCode",fixmedinsCode);	 // 医疗机构编码
        dataMap.put("pageNum",insureReckonDTO.getPageNo()); // 页码
        dataMap.put("pageSize",insureReckonDTO.getPageSize()); // 每页条数
        dataMap.put("feeClrId",insureReckonDTO.getFeeClrId()); // 清算ID
        Map<String,Object> inptMap = new HashMap<>();
        inptMap.put("data",dataMap);
        Map<String,Object> resultMap = this.invokingUpay(hospCode, insureRegCode, "3702", dataMap);
        Map<String,Object> outptMap =  (Map)resultMap.get("output");
        List<Map<String,Object>> resultList = MapUtils.get(outptMap,"data");
        return PageDTO.of(resultList);
    }

    /**
     * 获取结算明细信息 - 3703
     *
     * @param insureReckonDTO
     * @Method queryInsureSetlDetlList
     * @Desrciption 获取结算明细信息
     * @Author liaojiguang
     * @Date 2021/9/22 09:15
     * @Return
     **/
    @Override
    public PageDTO queryInsureSetlDetlList(InsureReckonDTO insureReckonDTO) {
        String hospCode = insureReckonDTO.getHospCode();
        String fixmedinsCode = insureReckonDTO.getFixmedinsCode();
        String insureRegCode = insureReckonDTO.getInsureRegCode();

        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("fixmedinsCode",fixmedinsCode);	 // 医疗机构编码
        dataMap.put("pageNum",insureReckonDTO.getPageNo()); // 页码
        dataMap.put("pageSize",insureReckonDTO.getPageSize()); // 每页条数
        dataMap.put("feeClrId",insureReckonDTO.getFeeClrId()); // 清算ID
        dataMap.put("clrStas",insureReckonDTO.getClrStas()); // 清算状态
        Map<String,Object> inptMap = new HashMap<>();
        inptMap.put("data",dataMap);
        Map<String,Object> resultMap = this.invokingUpay(hospCode, insureRegCode, "3703", dataMap);
        Map<String,Object> outptMap =  (Map)resultMap.get("output");
        List<Map<String,Object>> resultList = MapUtils.get(outptMap,"data");
        return PageDTO.of(resultList);
    }

    /**
     * 对账汇总明细查询 - 3699
     *
     * @param insureReckonDTO
     * @Method queryInsureTotlStmtInfo
     * @Desrciption 对账汇总明细查询 - 3699
     * @Author liaojiguang
     * @Date 2021/9/22 09:15
     * @Return
     **/
    @Override
    public PageDTO queryInsureTotlStmtInfo(InsureReckonDTO insureReckonDTO) {
        String hospCode = insureReckonDTO.getHospCode();
        String insureRegCode = insureReckonDTO.getInsureRegCode();

        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(insureRegCode);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);

        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("fixmedinsCode",insureConfigurationDTO.getOrgCode());	 // 医疗机构编码
        dataMap.put("stmtBegndate",insureReckonDTO.getBegndate()); // 对账开始时间
        dataMap.put("stmtEnddate",insureReckonDTO.getEnddate()); // 对账结束时间
        Map<String,Object> inptMap = new HashMap<>();
        inptMap.put("data",dataMap);
        Map<String,Object> resultMap = this.invokingUpay(hospCode, insureRegCode, "3699", dataMap);
        Map<String,Object> outptMap =  (Map)resultMap.get("output");
        List<Map<String,Object>> resultList = MapUtils.get(outptMap,"data");
        return PageDTO.of(resultList);
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

        //封装统一支付接口入参
        Map httpParam = new HashMap();
        httpParam.put("infno", infno);  //交易编号
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号

        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        httpParam.put("input", dataMap);

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
