package cn.hsa.emr.emrqualityaging.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.emr.emrquality.bo.EmrQualityDataRulesBO;
import cn.hsa.module.emr.emrquality.dao.EmrQualityDataRulesDAO;
import cn.hsa.module.emr.emrquality.dto.EmrQualityDataRulesDTO;
import cn.hsa.util.SnowflakeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.emr.emrqualityaging.bo.impl
 * @Class_name: EmrQualityDataRulesBOImpl
 * @Describe: 电子病历数据控制股则业务实现层
 * @Author: liuliyun
 * @Email: liyun.liu@powersi.com
 * @Date: 2021/09/26 20:17
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class EmrQualityDataRulesBOImpl extends HsafBO implements EmrQualityDataRulesBO {

    @Resource
    private EmrQualityDataRulesDAO emrQualityDataRulesDAO;

    @Override
    public boolean insertEmrQualityDataRules(EmrQualityDataRulesDTO emrQualityDataRulesDTO) {
        if (emrQualityDataRulesDTO!=null){
            emrQualityDataRulesDTO.setId(SnowflakeUtils.getId());
        }
        if (emrQualityDataRulesDAO.insertEmrQualityDataRules(emrQualityDataRulesDTO)>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean updateEmrQualityDataRules(EmrQualityDataRulesDTO emrQualityDataRulesDTO) {
        if (emrQualityDataRulesDAO.updateEmrQualityDataRules(emrQualityDataRulesDTO)>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<EmrQualityDataRulesDTO> queryEmrQualityDataRulesList(EmrQualityDataRulesDTO emrQualityDataRulesDTO) {
        return emrQualityDataRulesDAO.queryEmrQualityDataRulesList(emrQualityDataRulesDTO);
    }

    @Override
    public EmrQualityDataRulesDTO queryEmrQualityDataRulesById(EmrQualityDataRulesDTO emrQualityDataRulesDTO) {
        return emrQualityDataRulesDAO.queryEmrQualityDataRulesById(emrQualityDataRulesDTO);
    }

    @Override
    public boolean updateEmrQualityDataInvalid(Map map) {
        if (emrQualityDataRulesDAO.updateEmrQualityDataInvalid(map)>0){
            return true;
        }else {
            return false;
        }
    }
}
