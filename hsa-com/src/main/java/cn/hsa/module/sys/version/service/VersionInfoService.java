package cn.hsa.module.sys.version.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sys.version.dto.VersionInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
* @ClassName  VersionInfoService
* @Deacription 服务接口层
* @Author liuhuiming
* @Date 2022-02-10
* @Version 1.0
**/
@FeignClient(value = "hsa-sys")
public interface VersionInfoService  {

    @PostMapping("/service/sys/version/queryVersionInfoListByPage")
    WrapperResponse<PageDTO> queryVersionInfoListByPage(Map map);

    @PostMapping("/service/sys/version/saveVersionInfo")
    WrapperResponse<Boolean> saveVersionInfo(Map map);

}
