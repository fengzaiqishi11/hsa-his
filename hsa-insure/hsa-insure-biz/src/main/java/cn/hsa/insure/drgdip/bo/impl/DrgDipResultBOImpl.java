package cn.hsa.insure.drgdip.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.authorization.entity.CenterFunctionAuthorizationDO;
import cn.hsa.module.center.authorization.service.CenterFunctionAuthorizationService;
import cn.hsa.module.insure.drgdip.bo.DrgDipResultBO;
import cn.hsa.module.insure.drgdip.dao.DrgDipResultDAO;
import cn.hsa.module.insure.drgdip.dao.DrgDipResultDetailDAO;
import cn.hsa.module.insure.drgdip.dto.*;
import cn.hsa.module.insure.drgdip.entity.DrgDipResultDO;
import cn.hsa.module.insure.drgdip.entity.DrgDipResultDetailDO;
import cn.hsa.module.insure.module.service.InsureDictService;
import cn.hsa.insure.util.Constant;
import cn.hsa.util.*;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hsa.module.insure.drgdip.bo.DrgDipResultBO;
import cn.hsa.module.insure.drgdip.dao.DrgDipQulityInfoLogDAO;
import cn.hsa.module.insure.drgdip.dao.DrgDipResultDAO;
import cn.hsa.module.insure.drgdip.dto.DrgDipResultDTO;
import cn.hsa.module.insure.drgdip.entity.DrgDipQulityInfoLogDO;
import cn.hsa.util.MapUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.StringValue;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.ResourceTransactionManager;

import javax.annotation.Resource;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Map;


import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Component
@Slf4j
public class DrgDipResultBOImpl extends HsafBO implements DrgDipResultBO {

    @Resource
    private DrgDipQulityInfoLogDAO drgDipQulityInfoLogDAO;

    @Resource
    private DrgDipResultDAO drgDipResultDAO;

    @Resource
    private DrgDipResultDetailDAO drgDipResultDetailDAO;

    /**
     * 开启事务
     */
    @Resource
    private ResourceTransactionManager transactionManager;

    @Resource
    private CenterFunctionAuthorizationService centerFunctionAuthorizationService;

    @Resource
    private InsureDictService insureDictService_consumer;

    /**
     * @return
     * @Method getInsureCost
     * @Desrciption drg\dip调用插入日志表
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-11 22:54
     * @Return
     */
    @Override
    public Boolean insertDrgDipQulityInfoLog(Map<String,Object> map) {
        TransactionStatus status = null;
        boolean log = true;
        try {
            String msgId = MapUtils.get(map,"msgId");
            String hospCode = MapUtils.get(map,"hospCode");
            String orgCode = MapUtils.get(map,"orgCode");
            String visitId = MapUtils.get(map,"visitId");
            String infNo = MapUtils.get(map,"infNo");
            String infName = MapUtils.get(map,"infName");
            Date reqTime = MapUtils.get(map,"reqTime");
            Date respTime = MapUtils.get(map,"respTime");
            String crtId = MapUtils.get(map,"crtId");
            String crtName = MapUtils.get(map,"crtName");
            String reqContent = MapUtils.get(map,"reqContent");
            String respContent = MapUtils.get(map,"respContent");
            String resultCode =  String.valueOf(MapUtils.get(map,"resultCode",""));
            String type = MapUtils.get(map,"type");
            String businessType = MapUtils.get(map,"businessType");
            // 开启独立新事务
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            status = transactionManager.getTransaction(def);
            DrgDipQulityInfoLogDO drgDipQulityInfoLogDO = new DrgDipQulityInfoLogDO();
            drgDipQulityInfoLogDO.setId(SnowflakeUtils.getId());
            drgDipQulityInfoLogDO.setHospCode(hospCode);
            drgDipQulityInfoLogDO.setVisitId(visitId);
            drgDipQulityInfoLogDO.setOrgCode(orgCode);
            drgDipQulityInfoLogDO.setMsgId(msgId);
            drgDipQulityInfoLogDO.setInfNo(infNo);
            drgDipQulityInfoLogDO.setInfName(infName);
            drgDipQulityInfoLogDO.setReqTime(reqTime);
            drgDipQulityInfoLogDO.setRespTime(respTime);
            drgDipQulityInfoLogDO.setReqContent(reqContent);
            drgDipQulityInfoLogDO.setRespContent(respContent);
            drgDipQulityInfoLogDO.setStatus(resultCode);
            drgDipQulityInfoLogDO.setCrtId(crtId);
            drgDipQulityInfoLogDO.setCrtTime(DateUtils.getNow());
            drgDipQulityInfoLogDO.setCrtName(crtName);
            drgDipQulityInfoLogDO.setType(type);
            drgDipQulityInfoLogDO.setBusinessType(businessType);
            drgDipQulityInfoLogDAO.insertDrgDipQulityInfoLog(drgDipQulityInfoLogDO);

            // 提交独立事务
            transactionManager.commit(status);
        } catch (Exception e) {
            if (status != null) {
                transactionManager.rollback(status);
            }
            System.out.println(e.getMessage());
            log = false;
        }
        return log;
    }

    /**
     * @return
     * @Method getInsureCost
     * @Desrciption drg\dip保存质控结果
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-11 22:54
     * @Return
     */
    @Override
    public Boolean insertDrgDipResult(DrgDipResultDTO drgDipResultDTO,List<DrgDipResultDetailDTO> drgDipResultDetailDTOList) {

        //保存质控结果表
        if(drgDipResultDTO != null){
            drgDipResultDTO.setId(SnowflakeUtils.getId());
            drgDipResultDTO.setCrtTime(DateUtils.getNow());
            //保存可疑，违规条数
            int suspiciousNum = 0;
            int violationNum = 0;
            if(drgDipResultDetailDTOList != null && drgDipResultDetailDTOList.size()>0){
                for(DrgDipResultDetailDTO drgDipResultDetailDTO:drgDipResultDetailDTOList){
                    if("1".equals(drgDipResultDetailDTO.getRuleLevel())){
                        suspiciousNum++;
                    }
                    if("2".equals(drgDipResultDetailDTO.getRuleLevel())){
                        violationNum++;
                    }
                }

            }
            drgDipResultDTO.setSuspiciousNum(suspiciousNum);
            drgDipResultDTO.setViolationNum(violationNum);
            drgDipResultDTO.setValidFlag("1");
            //保存完成状态
            if(violationNum == 0){
                drgDipResultDTO.setStates("2");
            }else {
                drgDipResultDTO.setStates("1");
            }
            //判断分组结果
            if(StringUtils.isEmpty(drgDipResultDTO.getDrgDipCode())){
                drgDipResultDTO.setGroupResult("0");
            }else if("0000".equals(drgDipResultDTO.getDrgDipCode())){
                drgDipResultDTO.setGroupResult("1");
            }else if(drgDipResultDTO.getDrgDipCode().length() == 3){
                drgDipResultDTO.setGroupResult("2");
            }else {
                drgDipResultDTO.setGroupResult("3");
            }
            //插入之前先更新之前的数据
            drgDipResultDAO.updateByVisitId(drgDipResultDTO);
            drgDipResultDAO.insertDrgDipResult(drgDipResultDTO);
        }
        //保存质控结果明细表
        if(drgDipResultDetailDTOList != null && drgDipResultDetailDTOList.size()>0){
            for(DrgDipResultDetailDTO drgDipResultDetailDTO:drgDipResultDetailDTOList){
                drgDipResultDetailDTO.setId(SnowflakeUtils.getId());
                drgDipResultDetailDTO.setResultId(drgDipResultDTO.getId());
                drgDipResultDetailDTO.setCrtId(drgDipResultDTO.getCrtId());
                drgDipResultDetailDTO.setCrtName(drgDipResultDTO.getCrtName());
                drgDipResultDetailDTO.setCrtTime(DateUtils.getNow());
            }
            drgDipResultDetailDAO.insertDrgDipResultDetailList(drgDipResultDetailDTOList);
        }
        return true;

    }

    /**
     * 前端调用DRG DIP接口授权校验
     * @param map
     * 【参数配置1】：支付方式：0：不开启DRG/DIP支付方式（默认），1：DIP 2：DRG 3:DIP和DRG
     * @Author 医保开发二部-湛康
     * @Date 2022-06-08 9:44
     * @return java.lang.Boolean
     */
    @Override
    public DrgDipAuthDTO checkDrgDipBizAuthorization(Map<String, Object> map) {
      DrgDipAuthDTO dto = new DrgDipAuthDTO();
      HashMap param = new HashMap();
      param.put("hospCode",MapUtils.get(map, "hospCode"));
      //循环查询DRG和DIP质控权限1：DIP 2:DRG
      Boolean dip = false;
      Boolean drg = false;
      param.put("orderTypeCode","1");
      CenterFunctionAuthorizationDO dipAuth =
          (CenterFunctionAuthorizationDO)centerFunctionAuthorizationService.queryBizAuthorizationByOrderTypeCode(param).getData();
      param.put("orderTypeCode","2");
      CenterFunctionAuthorizationDO drgAuth =
          (CenterFunctionAuthorizationDO)centerFunctionAuthorizationService.queryBizAuthorizationByOrderTypeCode(param).getData();
      //都没有权限 报错
      if (ObjectUtil.isEmpty(dipAuth)&&ObjectUtil.isEmpty(drgAuth)){
        throw new AppException("未查询到本机构的DIP、DRG质控服务的授权信息，请通过400电话400-987-5000或通过企业微信联系我们");
      }
      //dip
      if (ObjectUtil.isNotEmpty(dipAuth)){
        dto.setDip("true");
      }else{
        dto.setDip("false");
      }
      //drg
      if (ObjectUtil.isNotEmpty(drgAuth)){
        dto.setDrg("true");
      }else{
        dto.setDrg("false");
      }
      return dto;
    }

    @Override
    public DrgDipAuthDTO checkDrgDipBizAuthorizationSettle(Map<String, Object> map) {
        DrgDipAuthDTO dto = new DrgDipAuthDTO();
        HashMap param = new HashMap();
        param.put("hospCode",MapUtils.get(map, "hospCode"));
        //循环查询DRG和DIP质控权限1：DIP 2:DRG
        Boolean dip = false;
        Boolean drg = false;
        param.put("orderTypeCode","1");
        CenterFunctionAuthorizationDO dipAuth =
                (CenterFunctionAuthorizationDO)centerFunctionAuthorizationService.queryBizAuthorizationByOrderTypeCode(param).getData();
        param.put("orderTypeCode","2");
        CenterFunctionAuthorizationDO drgAuth =
                (CenterFunctionAuthorizationDO)centerFunctionAuthorizationService.queryBizAuthorizationByOrderTypeCode(param).getData();
        //都没有权限 报错
        if (ObjectUtil.isEmpty(dipAuth)&&ObjectUtil.isEmpty(drgAuth)){

        }
        //dip
        if (ObjectUtil.isNotEmpty(dipAuth)){
            dto.setDip("true");
        }else{
            dto.setDip("false");
        }
        //drg
        if (ObjectUtil.isNotEmpty(drgAuth)){
            dto.setDrg("true");
        }else{
            dto.setDrg("false");
        }
        return dto;
    }


  /**
   * 获取质控信息
   * @param map
   * @Author 医保开发二部-湛康
   * @Date 2022-06-07 15:57
   * @return cn.hsa.module.insure.drgdip.dto.DrgDipComboDTO
   */
    @Override
    public DrgDipComboDTO getDrgDipInfoByParam(HashMap map) {
      DrgDipComboDTO combo = new DrgDipComboDTO();
      DrgDipResultDTO dto =MapUtils.get(map, "drgDipResultDTO");
      DrgDipAuthDTO drgDipAuthDTO =MapUtils.get(map, "drgDipAuthDTO");
      if (!("true".equals(drgDipAuthDTO.getDip())&&"true".equals(drgDipAuthDTO.getDrg()))){
        if ("true".equals(drgDipAuthDTO.getDip())){
          dto.setType("2");
        }
        if ("true".equals(drgDipAuthDTO.getDrg())){
          dto.setType("1");
        }
      }
      //新增质控信息
      List<DrgDipResultDO> list = drgDipResultDAO.queryListByVisitIdDesc(dto);
      //未质控过
      if (CollectionUtil.isEmpty(list)){
        combo.setQuaStaus("0");
        combo.setQuaStausName("未质控");
      }else{
        //只有一条
        if (list.size() > 1){
          //完成标志
          Integer flag = 0;
          //可疑条数
          Integer suspiciousNum = 0;
          //违规条数
          Integer violationNum = 0;
          for (DrgDipResultDO result : list){
            //2已完成
            if ("2".equals(result.getStates())){
              flag++;
            }
            suspiciousNum = suspiciousNum + result.getSuspiciousNum();
            violationNum = violationNum + result.getViolationNum();
          }
          if (flag >= 2){
            combo.setQuaStaus("2");
            combo.setQuaStausName("质控完成");
          }else{
            combo.setQuaStaus("1");
            combo.setQuaStausName("质控中");
            combo.setSuspiciousNum(suspiciousNum);
            combo.setViolationNum(violationNum);
          }
        }else{
          DrgDipResultDO result = list.get(0);
          //2已完成
          if ("2".equals(result.getStates())){
            combo.setQuaStaus("2");
            combo.setQuaStausName("质控完成");
          }else{
            combo.setQuaStaus("1");
            combo.setQuaStausName("质控中");
            combo.setSuspiciousNum(result.getSuspiciousNum());
            combo.setViolationNum(result.getViolationNum());
          }
        }

        /*List<DrgDipResultDetailDO> list = drgDipResultDetailDAO.selectListByVisitId(drgDipResultDO.getId());
        //出参转换
        BeanUtil.copyProperties(drgDipResultDO,dto);
        List<DrgDipResultDetailDTO> dtoList = Convert.toList(DrgDipResultDetailDTO.class, list);
        combo.setResult(dto);
        combo.setDetails(dtoList);*/
      }
      return combo;
    }

    /**
     * 质控结果查询-结算清单
     * @param
     * @Author
     * @Date 2022-06-07 15:54
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public PageDTO queryDrgDipResultSetlinfo(DrgDipResultDTO drgDipResultDTO){
        if(StringUtils.isEmpty(drgDipResultDTO.getType())){
            throw new AppException("请选择质控类型");
        }
        //分页参数
        PageHelper.startPage(drgDipResultDTO.getPageNo(), drgDipResultDTO.getPageSize());
        List<DrgDipResultDTO>  drgDipResultDTOList = drgDipResultDAO.queryDrgDipResultSetlinfo(drgDipResultDTO);
        for(DrgDipResultDTO drgDipResultDTO1:drgDipResultDTOList){
            //性别转义
            String gendName = getSysCodeName(drgDipResultDTO.getHospCode(),"XB",drgDipResultDTO1.getGend());
            //拼接信息
            drgDipResultDTO1.setNameGendAge(drgDipResultDTO1.getPsnName()+"/"+gendName+"/"+String.valueOf(drgDipResultDTO1.getAge()));
        }
        return PageDTO.of(drgDipResultDTOList);
    }

    /**
     * 质控结果查询汇总-结算清单
     * @param
     * @Author
     * @Date 2022-06-07 15:54
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public Map<String, Object> queryDrgDipResultSetlinfoSum(DrgDipResultDTO drgDipResultDTO){
        if(StringUtils.isEmpty(drgDipResultDTO.getType())){
            throw new AppException("请选择质控类型");
        }
        List<DrgDipResultDTO>  drgDipResultDTOList = drgDipResultDAO.queryDrgDipResultSetlinfo(drgDipResultDTO);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("total",drgDipResultDTOList.size());//总数
        int drgDipFinshSum = 0;//质控通过人数
        int drgDipNoFinshSum = 0;//质控未通过完成人数
        for(DrgDipResultDTO drgDipResultDTO1:drgDipResultDTOList){
            if(Constant.UnifiedPay.ZKZT.ZKWWC.equals(drgDipResultDTO1.getStates())){
                drgDipNoFinshSum++;
            }
            if(Constant.UnifiedPay.ZKZT.ZKWC.equals(drgDipResultDTO1.getStates())){
                drgDipFinshSum++;
            }
        }
        int drgDipSum = drgDipFinshSum + drgDipNoFinshSum;//质控人数
        int drgDipNotSum = drgDipResultDTOList.size() - drgDipSum ;//未质控人数
        //格式化
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);
        String drgDipFinsh = numberFormat.format((float)drgDipFinshSum/(float)drgDipSum*100) + "%";//通过率
        String drgDipNoFinsh = numberFormat.format((float)drgDipNoFinshSum/(float)drgDipSum*100) + "%";//未通过率
        resultMap.put("drgDipFinshSum",drgDipFinshSum);
        resultMap.put("drgDipNoFinshSum",drgDipNoFinshSum);
        resultMap.put("drgDipSum",drgDipSum);
        resultMap.put("drgDipNotSum",drgDipNotSum);
        resultMap.put("drgDipFinsh",drgDipFinsh);
        resultMap.put("drgDipNoFinsh",drgDipNoFinsh);
        return resultMap;
    }

    /**
     * 质控结果查询-详细
     * @param
     * @Author
     * @Date 2022-06-07 15:54
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public Map<String, Object>  queryDrgDipResultDetail(DrgDipResultDTO drgDipResultDTO){
        Map<String, Object> responseDataMap = new HashMap<>();
        if(StringUtils.isEmpty(drgDipResultDTO.getType())){
            throw new AppException("请选择质控类型");
        }
        if(StringUtils.isEmpty(drgDipResultDTO.getBusinessType())){
            throw new AppException("请选择业务类型");
        }
        DrgDipResultDTO drgDipResultDTO1 = drgDipResultDAO.queryDrgDipResultByVisitId(drgDipResultDTO);
        if(drgDipResultDTO1 == null){
            throw new AppException("该患者还未进行质控，未查到质控结果");
        }
        List<DrgDipResultDetailDTO>  qualityInfoList = drgDipResultDAO.queryDrgDipResultById(drgDipResultDTO1);
        responseDataMap.put("name",drgDipResultDTO1.getPsnName());// 姓名
        responseDataMap.put("sex",drgDipResultDTO1.getGend());// 性别
        responseDataMap.put("age",drgDipResultDTO1.getAge());// 年龄
        if(StringUtils.isNotEmpty(drgDipResultDTO1.getInsutype())){
            if(Constant.UnifiedPay.XZLX.CXJM.equals(drgDipResultDTO1.getInsutype())){
                responseDataMap.put("hiType","城乡居民基本医疗保险");// 医保类型
            }else if(Constant.UnifiedPay.XZLX.CZZG.equals(drgDipResultDTO1.getInsutype())){
                responseDataMap.put("hiType","职工基本医疗保险");// 医保类型
            }else if(Constant.UnifiedPay.XZLX.LX.equals(drgDipResultDTO1.getInsutype())){
                responseDataMap.put("hiType","离休人员医疗保障");// 医保类型
            }else {
                responseDataMap.put("hiType","自费病人");// 医保类型
            }
        }else {
            responseDataMap.put("hiType","自费病人");// 医保类型
        }
        responseDataMap.put("inNO",drgDipResultDTO1.getVisitId());//住院号
        responseDataMap.put("diagCode",drgDipResultDTO1.getDrgDipCode());// DIP组编码
        responseDataMap.put("diagName",drgDipResultDTO1.getDrgDipName());// DIP组名称
        responseDataMap.put("diagFeeSco",drgDipResultDTO1.getFeePay());// 分值
        responseDataMap.put("profitAndLossAmount",drgDipResultDTO1.getProfit());// 盈亏额
        responseDataMap.put("totalFee",drgDipResultDTO1.getTotalFee());// 总费用
        responseDataMap.put("feeStand",drgDipResultDTO1.getStandFee());// 总费用标杆
        responseDataMap.put("proMedicMater",drgDipResultDTO1.getProMedicMater());// 药占比
        responseDataMap.put("proMedicMaterStand",drgDipResultDTO1.getStandProMedicMater());// 药占比标杆
        responseDataMap.put("proConsum",drgDipResultDTO1.getProConsum());// 耗材比
        responseDataMap.put("proConsumStand",drgDipResultDTO1.getStandProConsum());// 耗材比标杆
        responseDataMap.put("quality",qualityInfoList);// 质控信息
        return responseDataMap;
    }

    /**
     * 质控结果查询-病案首页
     * @param
     * @Author
     * @Date 2022-06-07 15:54
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public PageDTO queryDrgDipResultMris(DrgDipResultDTO drgDipResultDTO){
        if(StringUtils.isEmpty(drgDipResultDTO.getType())){
            throw new AppException("请选择质控类型");
        }
        //分页参数
        PageHelper.startPage(drgDipResultDTO.getPageNo(), drgDipResultDTO.getPageSize());
        List<DrgDipResultDTO>  drgDipResultDTOList = drgDipResultDAO.queryDrgDipResultMris(drgDipResultDTO);
        for(DrgDipResultDTO drgDipResultDTO1:drgDipResultDTOList){
            //性别转义
            String gendName = getSysCodeName(drgDipResultDTO.getHospCode(),"XB",drgDipResultDTO1.getGend());
            //拼接信息
            drgDipResultDTO1.setNameGendAge(drgDipResultDTO1.getPsnName()+"/"+gendName+"/"+String.valueOf(drgDipResultDTO1.getAge()));
        }
        return PageDTO.of(drgDipResultDTOList);
    }

    /**
     * 质控结果查询汇总-病案首页
     * @param
     * @Author
     * @Date 2022-06-07 15:54
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public Map<String, Object> queryDrgDipResultMrisSum(DrgDipResultDTO drgDipResultDTO){
        if(StringUtils.isEmpty(drgDipResultDTO.getType())){
            throw new AppException("请选择质控类型");
        }
        List<DrgDipResultDTO>  drgDipResultDTOList = drgDipResultDAO.queryDrgDipResultMris(drgDipResultDTO);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("total",drgDipResultDTOList.size());//总数
        int drgDipFinshSum = 0;//质控通过人数
        int drgDipNoFinshSum = 0;//质控未通过完成人数
        for(DrgDipResultDTO drgDipResultDTO1:drgDipResultDTOList){
            if(Constant.UnifiedPay.ZKZT.ZKWWC.equals(drgDipResultDTO1.getStates())){
                drgDipNoFinshSum++;
            }
            if(Constant.UnifiedPay.ZKZT.ZKWC.equals(drgDipResultDTO1.getStates())){
                drgDipFinshSum++;
            }
        }
        int drgDipSum = drgDipFinshSum + drgDipNoFinshSum;//质控人数
        int drgDipNotSum = drgDipResultDTOList.size() - drgDipSum ;//未质控人数
        //格式化
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);
        String drgDipFinsh = numberFormat.format((float)drgDipFinshSum/(float)drgDipSum*100) + "%";//通过率
        String drgDipNoFinsh = numberFormat.format((float)drgDipNoFinshSum/(float)drgDipSum*100) + "%";//未通过率
        resultMap.put("drgDipFinshSum",drgDipFinshSum);
        resultMap.put("drgDipNoFinshSum",drgDipNoFinshSum);
        resultMap.put("drgDipSum",drgDipSum);
        resultMap.put("drgDipNotSum",drgDipNotSum);
        resultMap.put("drgDipFinsh",drgDipFinsh);
        resultMap.put("drgDipNoFinsh",drgDipNoFinsh);
        return resultMap;
    }

    //字典转义
    private String getSysCodeName(String hospCode, String code, String value) {
        Map map = new HashMap(2);
        map.put("hospCode", hospCode);
        map.put("code", code);
        Map<String, String> dictMap = insureDictService_consumer.querySysCodeByCode(map).getData();
        return MapUtils.isEmpty(dictMap) ? "" : dictMap.get(value);
    }
}
