package cn.hsa.insure.module.service.impl;
import cn.hsa.insure.util.Constant;
import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.serializer.SerializerFeature;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dto.AnaResJudgeDTO;
import cn.hsa.module.insure.module.dto.AnaResJudgeDetlDTO;
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

import javax.annotation.Resource;
import java.util.HashMap;
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
        //海南的接口编号和入参节点名不一样
        if (insureConfigurationDTO.getMdtrtareaAdmvs().startsWith(Constant.UnifiedPay.YBBMQZ.HN)) {
            param.put("infno", Constant.UnifiedPay.MXSHBM.SQSH_HAINAN);
            data.put("trigScen",analysisDTO);
        }else {
            data.put("data",HumpUnderlineUtils.humpToUnderline(analysisDTO));
        }
        param.put("input",data);
        //增加参数消除对同一对象循环引用的问题
        String json = JSONObject.toJSONString(param,SerializerFeature.DisableCircularReferenceDetect);
        log.info("统一支付平台明细审核事前分析入参:" + json);
        String resultStr = HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), json);
        log.info("统一支付平台明细审核事前分析回参:" + resultStr);
        if(StringUtils.isEmpty(resultStr)){
            throw new AppException("无法访问统一支付平台");
        }
        Map<String, Object> resultMap = JSONObject.parseObject(resultStr,Map.class);
        if ("999".equals(MapUtil.getStr(resultMap,"code"))) {
            throw new AppException("明细审核事前分析返回："+MapUtil.getStr(resultMap,"msg"));
        }
        if (!"0".equals(MapUtil.getStr(resultMap,"infcode"))) {
            throw new AppException("明细审核事前分析返回："+MapUtil.getStr(resultMap,"err_msg"));
        }
        //湖南医保会返回infcode=0的异常信息，如：{"infcode":"0",	"err_msg":"FSI-文件查询号不能为空[fileBO_156]"
        if (ObjectUtil.isNotEmpty(MapUtil.getStr(resultMap,"err_msg"))) {
            throw new AppException("明细审核事前分析返回："+MapUtil.getStr(resultMap,"err_msg"));
        }
        Map<String,Object> outptMap = MapUtil.get(resultMap,"output",Map.class);
        AnaResJudgeDTO result = JSON.parseObject(MapUtil.getStr(outptMap, "result"), AnaResJudgeDTO.class);
        checkResult(result);
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
        //海南的接口编号和入参节点名不一样
        if (insureConfigurationDTO.getMdtrtareaAdmvs().startsWith(Constant.UnifiedPay.YBBMQZ.HN)) {
            param.put("infno", Constant.UnifiedPay.MXSHBM.SZSH_HAINAN);
            data.put("trigScen",analysisDTO);
        }else {
            data.put("data",HumpUnderlineUtils.humpToUnderline(analysisDTO));
        }
        param.put("input",data);
        //增加参数消除对同一对象循环引用的问题
        String json = JSONObject.toJSONString(param,SerializerFeature.DisableCircularReferenceDetect);
        log.info("统一支付平台明细审核事中分析入参:" + json);
        String resultStr = HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), json);
        log.info("统一支付平台明细审核事中分析回参:" + resultStr);
        if(StringUtils.isEmpty(resultStr)){
            throw new AppException("无法访问统一支付平台");
        }
        Map<String, Object> resultMap = JSONObject.parseObject(resultStr,Map.class);
        if ("999".equals(MapUtil.getStr(resultMap,"code"))) {
            throw new AppException("明细审核事中分析返回："+MapUtil.getStr(resultMap,"msg"));
        }
        if (!"0".equals(MapUtil.getStr(resultMap,"infcode"))) {
            throw new AppException("明细审核事中分析返回："+MapUtil.getStr(resultMap,"err_msg"));
        }
        //湖南医保会返回infcode=0的异常信息，如：{"infcode":"0",	"err_msg":"FSI-文件查询号不能为空[fileBO_156]"
        if (ObjectUtil.isNotEmpty(MapUtil.getStr(resultMap,"err_msg"))) {
            throw new AppException("明细审核事中分析返回："+MapUtil.getStr(resultMap,"err_msg"));
        }
        Map<String,Object> outptMap = MapUtil.get(resultMap,"output",Map.class);
        AnaResJudgeDTO result = JSON.parseObject(MapUtil.getStr(outptMap, "result"), AnaResJudgeDTO.class);
        checkResult(result);
        return result;
    }

    private void checkResult(AnaResJudgeDTO result) {
        if (ObjectUtil.isNotEmpty(result) && ObjectUtil.isNotEmpty(result.getJudgeResultDetailDtos())) {
            StringBuilder builder = new StringBuilder();
            if (ObjectUtil.isNotEmpty(result.getJrId())) {
                builder.append("违规标识:").append(result.getJrId()).append(",");
                builder.append("规则ID:").append(result.getRuleId()).append(",");
                builder.append("规则名称:").append(result.getRuleName()).append(",");
                builder.append("违规内容:").append(result.getVolaCont()).append(",");
                builder.append("违规金额:").append(result.getVolaAmt()).append(",");
                builder.append("违规明细：{");
                for (AnaResJudgeDetlDTO judgeDetlDTO : result.getJudgeResultDetailDtos()) {
                    if (ObjectUtil.isNotEmpty(judgeDetlDTO.getJrdId()) && ObjectUtil.isNotEmpty(judgeDetlDTO.getVolaItemType())) {
                        builder.append("违规明细标识：").append(judgeDetlDTO.getJrdId()).append(",");
                        builder.append("参保人标识：").append(judgeDetlDTO.getPatnId()).append(",");
                        builder.append("就诊标识：").append(judgeDetlDTO.getMdtrtId()).append(",");
                        builder.append("处方(医嘱)标识：").append(judgeDetlDTO.getRxId()).append(",");
                        builder.append("违规明细类型：").append(judgeDetlDTO.getVolaItemType()).append(",");
                        builder.append("违规金额：").append(judgeDetlDTO.getVolaAmt()).append(";");
                    }
                }
                builder.append("}");
            }
            if (ObjectUtil.isNotEmpty(builder) && builder.length() > 0) {
                throw new RuntimeException("明细审核存在违规情况："+builder.toString());
            }
        }
    }


}
