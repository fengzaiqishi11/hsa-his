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
import cn.hsa.util.MapUtils;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hsa.module.insure.drgdip.bo.DrgDipResultBO;
import cn.hsa.module.insure.drgdip.dao.DrgDipQulityInfoLogDAO;
import cn.hsa.module.insure.drgdip.dao.DrgDipResultDAO;
import cn.hsa.module.insure.drgdip.dto.DrgDipResultDTO;
import cn.hsa.module.insure.drgdip.entity.DrgDipQulityInfoLogDO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.SnowflakeUtils;
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
            drgDipResultDAO.insertDrgDipResult(drgDipResultDTO);
        }
        //保存质控结果明细表
        if(drgDipResultDetailDTOList != null && drgDipResultDetailDTOList.size()>0){
            for(DrgDipResultDetailDTO drgDipResultDetailDTO:drgDipResultDetailDTOList){
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
    public Boolean checkDrgDipBizAuthorization(Map<String, Object> map) {
      HashMap param = new HashMap();
      //入参判断
      if (ObjectUtil.isEmpty(MapUtils.get(map, "orderTypeCode"))){
        throw new AppException("授权类型类型【orderTypeCode】不能为空！");
      }
      param.put("hospCode",MapUtils.get(map, "hospCode"));
      String orderTypeCode = MapUtils.get(map, "orderTypeCode");
      //不开启
      if ("0".equals(orderTypeCode)){
        throw new AppException("未查询到本机构的DIP、DRG质控服务的授权信息，请通过400电话400-987-5000或通过企业微信联系我们");
      }
      //1：DIP 2:DRG
      if ("1".equals(orderTypeCode) || "2".equals(orderTypeCode)){
        param.put("orderTypeCode",orderTypeCode);
        CenterFunctionAuthorizationDO centerFunctionAuthorizationDO =
            (CenterFunctionAuthorizationDO)centerFunctionAuthorizationService.queryBizAuthorizationByOrderTypeCode(param).getData();
        if (ObjectUtil.isEmpty(centerFunctionAuthorizationDO)){
          throw new AppException("未查询到本机构的DIP、DRG质控服务的授权信息，请通过400电话400-987-5000或通过企业微信联系我们");
        }
      }
      //3:DIP和DRG 循环调用
      if ("3".equals(orderTypeCode)){
        // 1 DIP
        param.put("orderTypeCode",1);
        CenterFunctionAuthorizationDO centerFunctionAuthorizationDO =
            (CenterFunctionAuthorizationDO)centerFunctionAuthorizationService.queryBizAuthorizationByOrderTypeCode(param).getData();
        if (ObjectUtil.isEmpty(centerFunctionAuthorizationDO)){
          throw new AppException("未查询到本机构的DIP质控服务的授权信息，请通过400电话400-987-5000或通过企业微信联系我们");
        }
        // 2 DRG
        param.put("orderTypeCode",2);
        CenterFunctionAuthorizationDO centerFunctionAuthorizationDO1 =
            (CenterFunctionAuthorizationDO)centerFunctionAuthorizationService.queryBizAuthorizationByOrderTypeCode(param).getData();
        if (ObjectUtil.isEmpty(centerFunctionAuthorizationDO1)){
          throw new AppException("未查询到本机构的DRG质控服务的授权信息，请通过400电话400-987-5000或通过企业微信联系我们");
        }
      }
      return true;
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
      DrgDipResultDTO dto =MapUtils.get(map, "drgDipResultDTO");
      //新增质控信息
      DrgDipResultDO drgDipResultDO = drgDipResultDAO.queryListByVisitIdDesc(dto);
      //未质控过
      if (ObjectUtil.isEmpty(drgDipResultDO)){
        return null;
      }else{
        List<DrgDipResultDetailDO> list = drgDipResultDetailDAO.selectListByVisitId(drgDipResultDO.getId());
        //出参转换
        BeanUtil.copyProperties(drgDipResultDO,dto);
        List<DrgDipResultDetailDTO> dtoList = Convert.toList(DrgDipResultDetailDTO.class, list);
        DrgDipComboDTO combo = new DrgDipComboDTO();
        combo.setResult(dto);
        combo.setDetails(dtoList);
        return combo;
      }
    }
}
