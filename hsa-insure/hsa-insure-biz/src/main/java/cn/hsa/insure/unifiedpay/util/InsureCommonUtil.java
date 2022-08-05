package cn.hsa.insure.unifiedpay.util;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName InsureCommonUtil
 * @Deacription 湖南新医保公共参数
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Component
public class InsureCommonUtil {

    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;

    public InsureInterfaceParamDTO getInsurCommonParam(Map map) {
        InsureInterfaceParamDTO interfaceParamDTO = new InsureInterfaceParamDTO();

        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(map.get("hospCode").toString());
        if (map.get("orgCode") != null) {
            insureConfigurationDTO.setOrgCode(map.get("orgCode").toString());
        }
        if (map.get("configCode") != null) {
            insureConfigurationDTO.setCode(map.get("configCode").toString());
        }
        if (map.get("configRegCode") != null) {
            insureConfigurationDTO.setRegCode(map.get("configRegCode").toString());
        }
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        if(ObjectUtil.isEmpty(insureConfigurationDTO)){
            throw new AppException("未查询到医保登记信息，请先进行医保登记！");
        }

        String msgId = StringUtils.createMsgId(insureConfigurationDTO.getOrgCode());

        // 交易编号
        interfaceParamDTO.setInfno(MapUtils.get(map,"infno"));
        // 参保地医保区划
        interfaceParamDTO.setInsuplc_admdvs(MapUtils.get(map,"insuplcAdmdvs"));
        // 定点医药机构编号
        interfaceParamDTO.setMedins_code(insureConfigurationDTO.getOrgCode());
        // 医保中心编码
        interfaceParamDTO.setInsur_code(insureConfigurationDTO.getRegCode());
        // 就医地医保区划
        interfaceParamDTO.setMdtrtarea_admvs(insureConfigurationDTO.getMdtrtareaAdmvs());

        interfaceParamDTO.setMsgid(msgId);
        interfaceParamDTO.setOpter(MapUtils.get(map,"opter"));
        interfaceParamDTO.setOpter_name(MapUtils.get(map,"opter_name"));

        interfaceParamDTO.setUrl(insureConfigurationDTO.getUrl());
        String input = MapUtils.isEmpty(map.get("input")) ? JSON.toJSONString(getInputMap(map)) : JSON.toJSONString(map.get("input"));
        interfaceParamDTO.setInput(input);
        return interfaceParamDTO;
    }

    private Map<String, Object> getInputMap(Map map) {
        Map<String, Object> inputMap = new HashMap<>(1);
        inputMap.put("data", map.get("dataMap"));
        return inputMap;
    }

    /**
     * @Method getEmptyErr
     * @Desrciption
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/17 14:34
     * @Return
     **/
    public void getEmptyErr(Object obj,String errMsg) {
        if(obj ==null){
            throw new RuntimeException(errMsg);
        }
        if(obj instanceof  String){
            if(StringUtils.isEmpty((String) obj)){
                throw new RuntimeException(errMsg);
            }
        }
    }
}
