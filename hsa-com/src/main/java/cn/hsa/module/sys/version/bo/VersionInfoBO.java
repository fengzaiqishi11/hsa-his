package cn.hsa.module.sys.version.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.sys.version.dto.VersionInfoDTO;

/**
* @ClassName VersionInfoBO
* @Deacription 接口
* @Author liuhuiming
* @Date 2022-02-10
* @Version 1.0
**/
public interface VersionInfoBO {

    PageDTO queryVersionInfoListByPage(VersionInfoDTO versionInfoDTO);

    boolean saveVersionInfo(VersionInfoDTO versionInfoDTO);

}
