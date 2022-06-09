package cn.hsa.module.drgdip.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @Author 医保二部-张金平
 * @Date 2022-06-07 10:09
 * @Description DRG/DIP质控业务操作日志记录service接口
 */
@FeignClient(value = "hsa-insure")
public interface DrgDipBusinessOptInfoLogService {

    /**
     * @Author 医保二部-张金平
     * @Date 2022-06-07 13:45
     * @Description 保存drg、dip质控业务操作信息日志
     * @param map
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     */
    @PostMapping("/service/insure/insureDrgDipBusinessOptInfo/insertDrgDipBusinessOptInfoLog")
    WrapperResponse<Boolean> insertDrgDipBusinessOptInfoLog(Map<String,Object> map);

    /**
     * @Author 医保二部-张金平
     * @Date 2022-06-08 13:57
     * @Description  查询dip、drg质控过程日志记录
     * @param map
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     */
    WrapperResponse<PageDTO> queryDrgDipBusinessOptInfoLogList(Map<String, Object> map);
    /**
     * @Author 医保二部-张金平
     * @Date 2022-06-08 13:57
     * @Description  查询dip、drg质控过程日志记录详细信息
     * @param map
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     */
    WrapperResponse<PageDTO> getDrgDipBusinessOptInfoLogDetail(Map<String, Object> map);

    /**
     * 质控信息查询统计
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-06-09 10:12
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     */
    WrapperResponse<PageDTO> getDrgDipInfoByParam(Map<String, Object> map);
}