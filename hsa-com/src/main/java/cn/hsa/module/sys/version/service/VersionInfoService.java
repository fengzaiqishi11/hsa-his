package cn.hsa.module.sys.version.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sys.version.dto.VersionInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
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

    @PostMapping("/service/sys/version/queryVersionInfo")
    WrapperResponse<VersionInfoDTO> queryVersionInfo(Map map);

    /**
     *  查询历史升级公告消息 默认分页5条
     * @param map 分页参数信息  VersionInfoDTO versionInfo 参数是必传参数
     * @return 历史消息列表
     */
    WrapperResponse<PageDTO> queryHistoryVersionInfo(Map<String,Object> map);

    /**
     *  支持批量更新系统公告状态至已读
     * @param map
     * @return
     */
    WrapperResponse<Boolean> updateStatus2Read(Map<String,Object> map);
}
