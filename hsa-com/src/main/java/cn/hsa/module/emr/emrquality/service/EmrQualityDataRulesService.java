package cn.hsa.module.emr.emrquality.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.emr.emrquality.dto.EmrQualityDataRulesDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.emr.emrquality.service
 * @Class_name: EmrQualityDataRulesService
 * @Describe: 电子病历数据控制规则传输层接口
 * @Author: liuliyun
 * @Email: liyun.liu@powersi.com
 * @Date: 2021/9/23 20：08
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-emr")
public interface EmrQualityDataRulesService {
    /**
     * @Menthod insertEmrQualityDataRules
     * @Desrciption 新增病历数据控制规则
     * @param map
     * @Author liuliyun
     * @Date   2021/9/26 20：10
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @PostMapping("/service/emr/emrqualitydatarules/insertEmrQualityDataRules")
    WrapperResponse<Boolean> insertEmrQualityDataRules(Map map);

    /**
     * @Menthod updateEmrQualityDataRules
     * @Desrciption 更新病历数据控制规则
     * @param map
     * @Author liuliyun
     * @Date   2021/9/26 20：11
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @PostMapping("/service/emr/emrqualitydatarules/updateEmrQualityDataRules")
    WrapperResponse<Boolean> updateEmrQualityDataRules(Map map);

    /**
     * @Menthod updateEmrQualityDataInvalid
     * @Desrciption 病历控制规则作废
     * @param map
     * @Author liuliyun
     * @Date   2021/9/26 20:28
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @PostMapping("/service/emr/emrqualitydatarules/updateEmrQualityDataInvalid")
    WrapperResponse<Boolean> updateEmrQualityDataInvalid(Map map);

    /**
     * @Menthod queryEmrQualityDataRulesList
     * @Desrciption 查询病历数据控制规则列表
     * @param map
     * @Author liuliyun
     * @Date   2021/9/26 20：14
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<PageDTO>
     **/
    @PostMapping("/service/emr/emrqualitydatarules/queryEmrQualityDataRulesList")
    WrapperResponse<List<EmrQualityDataRulesDTO>> queryEmrQualityDataRulesList(Map map);

    /**
     * @Menthod queryEmrQualityDataRulesById
     * @Desrciption 根据id查询病历数据控制规则
     * @param map
     * @Author liuliyun
     * @Date   2021/9/26 20：13
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<EmrQualityDataRulesDTO>
     **/
    @PostMapping("/service/emr/emrqualitydatarules/queryEmrQualityDataRulesById")
    WrapperResponse<EmrQualityDataRulesDTO> queryEmrQualityDataRulesById(Map map);
}
