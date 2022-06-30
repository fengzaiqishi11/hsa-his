package cn.hsa.insure.drgdip.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.util.DateUtil;
import cn.hsa.module.drgdip.bo.DrgDipBusinessOptInfoBO;
import cn.hsa.module.insure.drgdip.dao.DrgDipBusinessOptInfoLogDAO;
import cn.hsa.module.insure.drgdip.entity.DrgDipBusinessOptInfoLogDO;
import cn.hsa.module.insure.module.entity.InsureFunctionLogDO;
import cn.hsa.util.*;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.ResourceTransactionManager;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class DrgDipBusinessOptInfoBOImpl extends HsafBO implements DrgDipBusinessOptInfoBO {
    /**
     * 开启事务
     */
    @Resource
    private ResourceTransactionManager transactionManager;
    @Resource
    DrgDipBusinessOptInfoLogDAO drgDipBusinessOptInfoLogDAO;
    /**
     * @Author 医保二部-张金平
     * @Date 2022-06-07 13:45
     * @Description 保存drg、dip质控业务操作信息日志
     * @param map
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     */
    @Override
    public Boolean insertDrgDipBusinessOptInfoLog(Map<String, Object> map) {

       TransactionStatus status = null;
        boolean functionLog = false;
        try {
            String qulityId = MapUtils.get(map, "qulityId");
            String businessId = MapUtils.get(map, "businessId");
            String optType = MapUtils.get(map, "optType");
            String optTypeName = MapUtils.get(map, "optTypeName");
            String type = MapUtils.get(map, "type");
            String businessType = MapUtils.get(map, "businessType");
            String isForce = MapUtils.get(map, "isForce");

            String forceUploadInfo = MapUtils.get(map, "forceUploadInfo");
            String insureRegCode = MapUtils.get(map, "insureRegCode");
            String hospName = MapUtils.get(map, "hospName");
            String orgCode = MapUtils.get(map, "orgCode");
            String insureSettleId = MapUtils.get(map, "insureSettleId");
            String hospCode = MapUtils.get(map,"hospCode");

            String medicalRegNo = MapUtils.get(map,"medicalRegNo");
            String settleId = MapUtils.get(map,"settleId");
            String visitId = MapUtils.get(map,"visitId");
            String psnNo = MapUtils.get(map,"psnNo");
            String psName = MapUtils.get(map,"psnName");
            String crteId = MapUtils.get(map,"crtId");
            String crteName = MapUtils.get(map,"crtName");
            String certno = MapUtils.get(map,"certno");
            String deptId = MapUtils.get(map,"deptId");
            String sex = MapUtils.get(map,"sex");
            String age = MapUtils.get(map,"age");
            String insueType = MapUtils.get(map,"insueType");
            Date inptTime=null;
            Date outptTime=null;

            inptTime = MapUtils.get(map, "inptTime");
            outptTime = MapUtils.get(map,"outptTime");

            String medType = MapUtils.get(map,"medType");

            String medTypeName = MapUtils.get(map,"medTypeName");
            String deptName = MapUtils.get(map,"deptName");
            String doctorId = MapUtils.get(map,"doctorId");
            String doctorName = MapUtils.get(map,"doctorName");
            String inptDiagnose = MapUtils.get(map,"outptDiagnose");
            String outptDiagnose = MapUtils.get(map,"outptDiagnose");
            BigDecimal totalFee = BigDecimalUtils.convert(MapUtils.get(map,"totalFee")!=null?MapUtils.get(map,"totalFee").toString():null);
            BigDecimal payFee = BigDecimalUtils.convert(MapUtils.get(map,"payFee")!=null?MapUtils.get(map,"payFee").toString():null);
            BigDecimal selfFee = BigDecimalUtils.convert(MapUtils.get(map,"selfFee")!=null?MapUtils.get(map,"selfFee").toString():null);
            BigDecimal cashPayFee = BigDecimalUtils.convert(MapUtils.get(map,"cashPayFee")!=null?MapUtils.get(map,"cashPayFee").toString():null);
            String inputJson = MapUtils.get(map,"inputJson");

            // 开启独立新事务
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            status = transactionManager.getTransaction(def);
            DrgDipBusinessOptInfoLogDO drgDipBusinessOptInfoLogDO = new DrgDipBusinessOptInfoLogDO();
            drgDipBusinessOptInfoLogDO.setAge(age);
            drgDipBusinessOptInfoLogDO.setBusinessId(businessId);
            drgDipBusinessOptInfoLogDO.setBusinessType(businessType);
             drgDipBusinessOptInfoLogDO.setCashPayFee(cashPayFee);
            drgDipBusinessOptInfoLogDO.setCertno(certno);
            drgDipBusinessOptInfoLogDO.setCrtId(crteId);
            drgDipBusinessOptInfoLogDO.setCrtName(crteName);
            drgDipBusinessOptInfoLogDO.setCrtTime(new Date());
            drgDipBusinessOptInfoLogDO.setDeptId(deptId);
            drgDipBusinessOptInfoLogDO.setDeptName(deptName);
            drgDipBusinessOptInfoLogDO.setDoctorId(doctorId);
            drgDipBusinessOptInfoLogDO.setDoctorName(doctorName);
            drgDipBusinessOptInfoLogDO.setForceUploadInfo(forceUploadInfo);
            drgDipBusinessOptInfoLogDO.setHospCode(hospCode);
            drgDipBusinessOptInfoLogDO.setHospName(hospName);
            drgDipBusinessOptInfoLogDO.setId(SnowflakeUtils.getId());
            drgDipBusinessOptInfoLogDO.setInptDiagnose(inptDiagnose);
            drgDipBusinessOptInfoLogDO.setInptTime(inptTime);
            drgDipBusinessOptInfoLogDO.setInputJson(inputJson);
            drgDipBusinessOptInfoLogDO.setInsueType(insueType);
            drgDipBusinessOptInfoLogDO.setInsureRegCode(insureRegCode);
            drgDipBusinessOptInfoLogDO.setInsureSettleId(insureSettleId);
            drgDipBusinessOptInfoLogDO.setIsForce(isForce);
            drgDipBusinessOptInfoLogDO.setMedicalRegNo(medicalRegNo);
            drgDipBusinessOptInfoLogDO.setMedType(medType);
            drgDipBusinessOptInfoLogDO.setMedTypeName(medTypeName);
            drgDipBusinessOptInfoLogDO.setOptType(optType);
            drgDipBusinessOptInfoLogDO.setOptTypeName(optTypeName);
            drgDipBusinessOptInfoLogDO.setOrgCode(orgCode);
            drgDipBusinessOptInfoLogDO.setOutptDiagnose(outptDiagnose);
            drgDipBusinessOptInfoLogDO.setOutptTime(outptTime);
            drgDipBusinessOptInfoLogDO.setPayFee(payFee);
            drgDipBusinessOptInfoLogDO.setPsName(psName);
            drgDipBusinessOptInfoLogDO.setPsnNo(psnNo);
            drgDipBusinessOptInfoLogDO.setQulityId(qulityId);
            drgDipBusinessOptInfoLogDO.setSelfFee(selfFee);
            drgDipBusinessOptInfoLogDO.setSettleId(settleId);
            drgDipBusinessOptInfoLogDO.setSex(sex);
            drgDipBusinessOptInfoLogDO.setTotalFee(totalFee);
            drgDipBusinessOptInfoLogDO.setType(type);
            drgDipBusinessOptInfoLogDO.setValidFlag("1");
            drgDipBusinessOptInfoLogDO.setVisitId(visitId);

            functionLog = drgDipBusinessOptInfoLogDAO.insertDrgDipBusinessOptInfoLog(drgDipBusinessOptInfoLogDO);
            // 提交独立事务
            transactionManager.commit(status);
        } catch (Exception e) {
            if (status != null) {
                transactionManager.rollback(status);
            }
            e.printStackTrace();
        }
        return functionLog;
    }

    /**
     * @Author 医保二部-张金平
     * @Date 2022-06-08 14:31
     * @Description 查询dip、drg质控过程日志记录
     * @param drgDipBusinessOptInfoLogDO
     * @return cn.hsa.base.PageDTO
     */
    @Override
    public PageDTO queryDrgDipBusinessOptInfoLogList(DrgDipBusinessOptInfoLogDO drgDipBusinessOptInfoLogDO) {
        List<DrgDipBusinessOptInfoLogDO> list = drgDipBusinessOptInfoLogDAO.queryDrgDipBusinessOptInfoLogList(drgDipBusinessOptInfoLogDO);
        return PageDTO.of(list);
    }

    /**
     * 质控信息查询统计
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-06-09 10:20
     * @return cn.hsa.base.PageDTO
     */
    @Override
    public PageDTO getDrgDipInfoByParam(Map<String, Object> map) {
      return null;
    }
}
