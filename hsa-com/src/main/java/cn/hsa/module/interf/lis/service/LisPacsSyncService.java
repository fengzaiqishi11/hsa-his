package cn.hsa.module.interf.lis.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * Lis/Pacs系统同步服务类
 * @author liudawen
 * @date 2022/8/31
 */
@FeignClient(value = "hsa-interf")
public interface LisPacsSyncService {

    /**
     * 获取门诊pacs检查申请单，并修改其申请单状态为已发送
     * @param map
     * @return
     */
    @PostMapping("/service/interf/lispacs/pacs/getOutptPacsApply")
    WrapperResponse<List<Map>> updateOutptPacsApply(Map map);

    /**
     * 获取住院pacs检查申请单，并修改其申请单状态为已发送
     * @param map
     * @return
     */
    @PostMapping("/service/interf/lispacs/pacs/getInptPacsApply")
    WrapperResponse<List<Map>> updateInptPacsApply(Map map);

    /**
     * 保存pacs影像检查结果，并修改其申请单状态为已接收结果
     * @param map
     * @return
     */
    @PostMapping("/service/interf/lispacs/pacs/savePacsResult")
    WrapperResponse<Boolean> savePacsResult(Map map);

    /**
     * 获取科室字典信息（中南职工医院B/S架构LIS系统接口方式对接）
     * @param map
     * @return
     */
    @PostMapping("/service/interf/lispacs/lis/getLisDept")
    WrapperResponse<List<Map>> getLisDept(Map map);

    /**
     * 获取医生字典信息（中南职工医院B/S架构LIS系统接口方式对接）
     * @param map
     * @return
     */
    @PostMapping("/service/interf/lispacs/lis/getLisDoctor")
    WrapperResponse<List<Map>> getLisDoctor(Map map);

    /**
     * 获取收费项目字典信息（中南职工医院B/S架构LIS系统接口方式对接）
     * @param map
     * @return
     */
    @PostMapping("/service/interf/lispacs/lis/getLisAdvice")
    WrapperResponse<List<Map>> getLisAdvice(Map map);

    /**
     * 获取lis检验申请单信息，并修改其申请单状态为已发送（中南职工医院B/S架构LIS系统接口方式对接）
     * @param map
     * @return
     */
    @PostMapping("/service/interf/lispacs/lis/getLisApply")
    WrapperResponse<List<Map>> updateLisApply(Map map);

    /**
     * 保存lis检验结果，并修改其申请单状态为已接收结果（中南职工医院B/S架构LIS系统接口方式对接）
     * @param map
     * @return
     */
    @PostMapping("/service/interf/lispacs/lis/saveLisResult")
    WrapperResponse<Boolean> saveLisResult(Map map);
}
