package cn.hsa.module.emr.emrquality.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.emr.emrquality.dto.EmrQualityAgingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.emr.emrquality.service
 * @Class_name: EmrQualityAgingService
 * @Describe: 电子病历时效质控业务传输层接口
 * @Author: liuliyun
 * @Email: liyun.liu@powersi.com
 * @Date: 2021/9/23 14:04
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-emr")
public interface EmrQualityAgingService {

    /**
     * @Menthod insertEmrQualityAging
     * @Desrciption 新增时效质控记录
     * @param map
     * @Author liuliyun
     * @Date   2021/9/24 10:14
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @PostMapping("/service/emr/emrqualityaging/insertEmrQualityAging")
    WrapperResponse<Boolean> insertEmrQualityAging(Map map);

    /**
     * @Menthod updateEmrQualityAging
     * @Desrciption 更新时效质控记录
     * @param map
     * @Author liuliyun
     * @Date   2021/9/24 10:14
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @PostMapping("/service/emr/emrqualityaging/updateEmrQualityAging")
    WrapperResponse<Boolean> updateEmrQualityAging(Map map);

    /**
     * @Menthod deleteEmrQualityAging
     * @Desrciption 删除时效质控记录
     * @param map
     * @Author liuliyun
     * @Date   2021/9/24 14:15
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @PostMapping("/service/emr/emrqualityaging/deleteEmrQualityAging")
    WrapperResponse<Boolean> deleteEmrQualityAging(Map map);

    /**
     * @Menthod queryEmrTemplateList
     * @Desrciption 查询病历模板
     * @param map
     * @Author liuliyun
     * @Date   2021/9/24 10:14
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List<Map>>
     **/
    @GetMapping("/service/emr/emrqualityaging/queryEmrTemplateList")
    WrapperResponse<List<Map>> queryEmrTemplateList(Map map);

    /**
     * @Menthod queryEmrQualityList
     * @Desrciption 查询时效质控list
     * @param map
     * @Author liuliyun
     * @Date   2021/9/24 11:57
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List<EmrQualityAgingDTO>>
     **/
    @GetMapping("/service/emr/emrqualityaging/queryEmrQualityList")
    WrapperResponse<List<EmrQualityAgingDTO>> queryEmrQualityList(Map map);

    /**
     * @Menthod queryEmrQualityListById
     * @Desrciption 通过id查询时效质控list
     * @param map
     * @Author liuliyun
     * @Date   2021/9/25 15:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List<EmrQualityAgingDTO>>
     **/
    @GetMapping("/service/emr/emrqualityaging/queryEmrQualityListById")
    WrapperResponse<List<EmrQualityAgingDTO>> queryEmrQualityListById(Map map);

    /**
     * @Description: 病历书写提醒
     * @param map
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com.cn
     * @Date 2021/11/29 9:19
     * @Return WrapperResponse<Boolean>
     */
    @PostMapping("/service/emr/emrArchiveLogging/queryUnwriteEmrList")
    WrapperResponse<Boolean> queryUnwriteEmrList(Map map);
}
