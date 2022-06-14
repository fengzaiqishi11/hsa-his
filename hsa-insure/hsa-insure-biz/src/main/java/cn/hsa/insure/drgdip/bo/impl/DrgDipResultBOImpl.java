package cn.hsa.insure.drgdip.bo.impl;

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
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.StringValue;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.ResourceTransactionManager;

import javax.annotation.Resource;
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
}
