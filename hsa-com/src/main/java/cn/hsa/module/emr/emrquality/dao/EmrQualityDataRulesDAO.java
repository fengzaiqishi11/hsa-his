package cn.hsa.module.emr.emrquality.dao;

import cn.hsa.module.emr.emrquality.dto.EmrQualityDataRulesDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.emr.emrquality.dao
 * @Class_name: EmrQualityDataRulesDAO
 * @Describe: 电子病历数据校验规则dao层
 * @Author: liuliyun
 * @Email: liyun.liu@powersi.com
 * @Date: 2021/9/26 19：54
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface EmrQualityDataRulesDAO {
    /**
     * @Menthod insertEmrQualityDataRules
     * @param  emrQualityDataRulesDTO
     * @Describe: 新增数据控制规则
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021/9/26 19：57
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     **/
    int insertEmrQualityDataRules(EmrQualityDataRulesDTO emrQualityDataRulesDTO);
    /**
     * @Menthod updateEmrQualityDataRules
     * @param  emrQualityDataRulesDTO
     * @Describe: 更新数据控制规则
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021/9/26 19：57
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     **/
    int updateEmrQualityDataRules(EmrQualityDataRulesDTO emrQualityDataRulesDTO);
    /**
     * @Menthod queryEmrQualityDataRulesList
     * @param  emrQualityDataRulesDTO
     * @Describe: 查询数据控制规则列表
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021/9/26 19：59
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     **/
    List<EmrQualityDataRulesDTO> queryEmrQualityDataRulesList(EmrQualityDataRulesDTO emrQualityDataRulesDTO);
    /**
     * @Menthod queryEmrQualityDataRulesById
     * @param  emrQualityDataRulesDTO
     * @Describe: 查询单个数据控制规则
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021/9/26 20:00
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     **/
    EmrQualityDataRulesDTO queryEmrQualityDataRulesById(EmrQualityDataRulesDTO emrQualityDataRulesDTO);

    /**
     * @Menthod updateEmrQualityDataInvalid
     * @param  map
     * @Describe: 数据控制规则作废
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021/9/26 20:01
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     **/
    int updateEmrQualityDataInvalid(Map map);

}
