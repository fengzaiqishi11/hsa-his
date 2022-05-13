package cn.hsa.insure.module.service.impl;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dto.AnaResJudgeDTO;
import cn.hsa.module.insure.module.dto.AnalysisDTO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.service.InsureDetailAuditService;
import cn.hsa.util.HttpConnectUtil;
import cn.hsa.util.HumpUnderlineUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 明细审核分析服务
 * @ClassName InsureDetailAuditServiceImpl
 * @Author 产品三部-郭来
 * @Date 2022/5/9 14:55
 * @Version 1.0
 **/
@Slf4j
@Service("insureDetailAuditService")
public class InsureDetailAuditServiceImpl implements InsureDetailAuditService {

    @Resource
    InsureConfigurationDAO insureConfigurationDAO;
    /**
     * @param inputMap
     * @return cn.hsa.module.insure.module.dto.AnaResJudgeDTO
     * @Description 明细审核事前分析
     * @Author 产品三部-郭来
     * @Date 2022-05-09 14:30
     */
    @Override
    public AnaResJudgeDTO upldBeforeAnalysisDTO(Map inputMap) {
        AnalysisDTO analysisDTO = (AnalysisDTO)inputMap.get("analysisDTO");
        InsureIndividualVisitDTO insureVisitDTO = (InsureIndividualVisitDTO)inputMap.get("insureIndividualVisitDTO");
        //查询医保配置
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(insureVisitDTO.getHospCode());//医院编码
        insureConfigurationDTO.setRegCode(insureVisitDTO.getInsureRegCode());  // 医疗机构编码（H码）
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        if (ObjectUtil.isEmpty(insureConfigurationDTO)) {
            throw new AppException("未获取到医保配置信息!");
        }

        Map param = new HashMap();
        param.put("infno", "3101");  //交易编号
        param.put("msgid",StringUtils.createMsgId(insureVisitDTO.getMedicineOrgCode()));
        param.put("insuplc_admdvs", insureVisitDTO.getInsuplcAdmdvs()); //参保地医保区划分
        param.put("medins_code", insureVisitDTO.getMedicineOrgCode()); //定点医药机构编号
        param.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        param.put("mdtrtarea_admvs",insureConfigurationDTO.getMdtrtareaAdmvs());// 就医地医保区划
        Map<String, Object> data = new HashMap<>();
        data.put("data",HumpUnderlineUtils.humpToUnderline(analysisDTO));
        param.put("input",data);
        String json = JSONObject.toJSONString(param);
        log.info("统一支付平台明细审核事前分析入参:" + json);
        String resultStr = HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), json);
        if(StringUtils.isEmpty(resultStr)){
            throw new AppException("无法访问统一支付平台");
        }
        Map<String, Object> resultMap = JSONObject.parseObject(resultStr,Map.class);
        if ("999".equals(MapUtils.get(resultMap,"code"))) {
            throw new AppException((String) resultMap.get("msg"));
        }
        if (!MapUtils.get(resultMap,"infcode").equals("0")) {
            throw new AppException((String) resultMap.get("err_msg"));
        }
        log.info("统一支付平台明细审核事前分析回参:" + resultStr);
        Map<String,Object> outptMap = MapUtils.get(resultMap,"output");
        AnaResJudgeDTO result = JSON.parseObject(JSON.toJSONString(MapUtils.get(outptMap, "result")), AnaResJudgeDTO.class);
        return result;
    }

    /**
     * @param inputMap
     * @return cn.hsa.module.insure.module.dto.AnaResJudgeDTO
     * @Description 明细审核事中分析
     * @Author 产品三部-郭来
     * @Date 2022-05-09 14:30
     */
    @Override
    public AnaResJudgeDTO upldMidAnalysisDTO(Map inputMap) {
        AnalysisDTO analysisDTO = (AnalysisDTO)inputMap.get("analysisDTO");
        InsureIndividualVisitDTO insureVisitDTO = (InsureIndividualVisitDTO)inputMap.get("insureIndividualVisitDTO");
        //查询医保配置
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(insureVisitDTO.getHospCode());//医院编码
        insureConfigurationDTO.setRegCode(insureVisitDTO.getInsureRegCode());  // 医疗机构编码（H码）
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        if (ObjectUtil.isEmpty(insureConfigurationDTO)) {
            throw new AppException("未获取到医保配置信息!");
        }

        Map param = new HashMap();
        param.put("infno", "3102");  //交易编号
        param.put("msgid",StringUtils.createMsgId(insureVisitDTO.getMedicineOrgCode()));
        param.put("insuplc_admdvs", insureVisitDTO.getInsuplcAdmdvs()); //参保地医保区划分
        param.put("medins_code", insureVisitDTO.getMedicineOrgCode()); //定点医药机构编号
        param.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        param.put("mdtrtarea_admvs",insureConfigurationDTO.getMdtrtareaAdmvs());// 就医地医保区划
        Map<String, Object> data = new HashMap<>();
        data.put("data",HumpUnderlineUtils.humpToUnderline(analysisDTO));
        param.put("input",data);
        String json = JSONObject.toJSONString(param);
        log.info("统一支付平台明细审核事中分析入参:" + json);
        String resultStr = HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), json);
        if(StringUtils.isEmpty(resultStr)){
            throw new AppException("无法访问统一支付平台");
        }
        Map<String, Object> resultMap = JSONObject.parseObject(resultStr,Map.class);
        if ("999".equals(MapUtils.get(resultMap,"code"))) {
            throw new AppException((String) resultMap.get("msg"));
        }
        if (!MapUtils.get(resultMap,"infcode").equals("0")) {
            throw new AppException((String) resultMap.get("err_msg"));
        }
        log.info("统一支付平台明细审核事中分析回参:" + resultStr);
        Map<String,Object> outptMap = MapUtils.get(resultMap,"output");
        AnaResJudgeDTO result = JSON.parseObject(JSON.toJSONString(MapUtils.get(outptMap, "result")), AnaResJudgeDTO.class);
        return result;
    }


}
