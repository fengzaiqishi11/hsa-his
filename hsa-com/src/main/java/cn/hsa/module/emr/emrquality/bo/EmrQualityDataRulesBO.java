package cn.hsa.module.emr.emrquality.bo;

import cn.hsa.module.emr.emrquality.dto.EmrQualityDataRulesDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.emr.emrquality.bo
 * @Class_name: EmrQualityAgingBO
 * @Describe: 电子病历数据控制规则bo层
 * @Author: liuliyun
 * @Email: liyun.liu@powersi.com
 * @Date: 2021/9/26 20：03
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface EmrQualityDataRulesBO {

    /**
     * @Menthod insertEmrQualityDataRules
     * @Desrciption 新增病历数据控制规则
     * @param emrQualityDataRulesDTO
     * @Author liuliyun
     * @Date   2021/9/26 20:05
     * @Return boolean
     **/
    boolean insertEmrQualityDataRules(EmrQualityDataRulesDTO emrQualityDataRulesDTO);

    /**
     * @Menthod updateEmrQualityDataRules
     * @Desrciption 更新病历数据控制规则
     * @param emrQualityDataRulesDTO
     * @Author liuliyun
     * @Date   2021/9/26 20:06
     * @Return boolean
     **/
    boolean updateEmrQualityDataRules(EmrQualityDataRulesDTO emrQualityDataRulesDTO);

    /**
     * @Menthod queryEmrQualityDataRulesList
     * @param  emrQualityDataRulesDTO
     * @Describe: 查询数据控制规则列表
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021/9/26 20:09
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     **/
    List<EmrQualityDataRulesDTO> queryEmrQualityDataRulesList(EmrQualityDataRulesDTO emrQualityDataRulesDTO);
    /**
     * @Menthod queryEmrQualityDataRulesById
     * @param  emrQualityDataRulesDTO
     * @Describe: 查询单个数据控制规则
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021/9/26 20:08
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     **/
    EmrQualityDataRulesDTO queryEmrQualityDataRulesById(EmrQualityDataRulesDTO emrQualityDataRulesDTO);

    /**
     * @Menthod updateEmrQualityDataInvalid
     * @param  map
     * @Describe: 数据控制规则作废
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021/9/26 20:07
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     **/
    boolean updateEmrQualityDataInvalid(Map map);
}
