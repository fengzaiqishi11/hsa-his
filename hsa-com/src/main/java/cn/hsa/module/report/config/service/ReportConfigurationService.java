package cn.hsa.module.report.config.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @ClassName ReportConfigurationService
 * @Deacription 服务接口层
 * @Author liuzhoting
 * @Date 2022-02-16
 * @Version 1.0
 **/
@FeignClient(value = "hsa-report")
public interface ReportConfigurationService {

    /**
     * 分页查询
     *
     * @param map
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @menthod queryPage()
     * @author liuzhuoting
     * @date 2022/02/17 09:30
     **/
    @PostMapping("/service/report/config/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);

    /**
     * 新增
     *
     * @param map
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
     * @menthod insert()
     * @author liuzhuoting
     * @date 2022/02/17 09:30
     **/
    @PostMapping("/service/report/config/insert")
    WrapperResponse<Boolean> insert(Map map);

    /**
     * 删除
     *
     * @param map
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @menthod delete()
     * @author liuzhuoting
     * @date 2022/02/17 09:30
     **/
    @PostMapping("/service/report/config/delete")
    WrapperResponse<Boolean> delete(Map map);

    /**
     * 修改
     *
     * @param map
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
     * @menthod update()
     * @author liuzhuoting
     * @date 2022/02/17 09:30
     **/
    @PostMapping("/service/report/config/update")
    WrapperResponse<Boolean> update(Map map);

}
