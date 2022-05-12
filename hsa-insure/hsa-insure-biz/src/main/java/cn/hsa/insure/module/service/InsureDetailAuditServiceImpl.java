package cn.hsa.insure.module.service;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dto.AnaResJudgeDTO;
import cn.hsa.module.insure.module.dto.AnalysisDTO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.service.InsureDetailAuditService;
import cn.hsa.util.HttpConnectUtil;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
     * @param analysisDTO
     * @return cn.hsa.module.insure.module.dto.AnaResJudgeDTO
     * @Description 明细审核事前分析
     * @Author 产品三部-郭来
     * @Date 2022-05-09 14:30
     */
    @Override
    public AnaResJudgeDTO upldBeforeAnalysisDTO(AnalysisDTO analysisDTO,String hospCode,String orgCode) {
        //查询医保配置
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);//医院编码
        insureConfigurationDTO.setOrgCode(orgCode);  // 医疗机构编码（H码）
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        String json = JSONObject.toJSONString(analysisDTO);
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
        List<Map<String,Object>> resultDataMap =  MapUtils.get(outptMap,"result");
        return null;
    }

    /**
     * @param analysisDTO
     * @return cn.hsa.module.insure.module.dto.AnaResJudgeDTO
     * @Description 明细审核事中分析
     * @Author 产品三部-郭来
     * @Date 2022-05-09 14:30
     */
    @Override
    public AnaResJudgeDTO upldMidAnalysisDTO(AnalysisDTO analysisDTO,String hospCode,String orgCode) {
        return null;
    }


}
