package cn.hsa.sys.version.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sys.version.service.VersionInfoService;
import cn.hsa.module.sys.version.bo.VersionInfoBO;
import cn.hsa.util.MapUtils;
import org.apache.http.util.VersionInfo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import cn.hsa.module.sys.version.dto.VersionInfoDTO;

import java.util.List;
import java.util.Map;

/**
* @ClassName  VersionInfoServiceImpl
* @Deacription 服务层
* @Author liuhuiming
* @Date 2022-02-10
* @Version 1.0
**/
@HsafRestPath("/service/sys/version")
@Service("sysVersionInfoService_provider")
public class VersionInfoServiceImpl extends HsafService implements VersionInfoService {

    @Autowired
    private VersionInfoBO versionInfoBO;

    @Override
    public WrapperResponse<PageDTO> queryVersionInfoListByPage(Map map) {
        PageDTO pageDTO = versionInfoBO.queryVersionInfoListByPage(MapUtils.get(map,"versionInfoDTO"));
        return WrapperResponse.success(pageDTO);
    }

    @Override
    public WrapperResponse<Boolean> saveVersionInfo(Map map) {
        return WrapperResponse.success(versionInfoBO.saveVersionInfo(MapUtils.get(map,"versionInfoDTO")));
    }

    @Override
    public WrapperResponse<VersionInfoDTO> queryVersionInfo(Map map) {
        VersionInfoDTO versionInfoDTO = versionInfoBO.queryVersionInfo();
        return WrapperResponse.success(versionInfoDTO);
    }

    /**
     * 查询历史升级公告消息 默认分页5条
     *
     * @param map 分页参数信息  VersionInfoDTO versionInfo 参数是必传参数
     * @return 历史消息列表
     */
    @Override
    public WrapperResponse<PageDTO> queryHistoryVersionInfo(Map<String, Object> map) {
        VersionInfoDTO versionInfoDTO  = MapUtils.get(map,"versionInfoDTO");

        return  WrapperResponse.success(versionInfoBO.queryHistoryVersionInfo(versionInfoDTO));
    }

    /**
     * 支持批量更新系统公告状态至已读
     *
     * @param map 参数值
     * @return
     */
    @Override
    public WrapperResponse<Boolean> updateStatus2Read(Map<String, Object> map) {
        return WrapperResponse.success(versionInfoBO.updateStatus2Read(map));
    }

}
