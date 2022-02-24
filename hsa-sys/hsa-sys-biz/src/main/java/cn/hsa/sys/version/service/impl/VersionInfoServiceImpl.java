package cn.hsa.sys.version.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sys.version.service.VersionInfoService;
import cn.hsa.module.sys.version.bo.VersionInfoBO;
import cn.hsa.util.MapUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import cn.hsa.module.sys.version.dto.VersionInfoDTO;

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

}
