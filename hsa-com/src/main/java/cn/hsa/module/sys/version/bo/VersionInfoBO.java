package cn.hsa.module.sys.version.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.sys.version.dto.VersionInfoDTO;

import java.util.List;
import java.util.Map;

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

    VersionInfoDTO queryVersionInfo();

    /**
     *  查询历史升级公告消息 默认分页5条
     * @param versionInfoDTO 分页参数信息
     * @return
     */
    PageDTO queryHistoryVersionInfo(VersionInfoDTO versionInfoDTO);

    /**
     *  批量更新 系统公告信息状态为已读
     * @param map 参数
     * @return
     */
    Boolean updateStatus2Read(Map<String, Object> map);

}
