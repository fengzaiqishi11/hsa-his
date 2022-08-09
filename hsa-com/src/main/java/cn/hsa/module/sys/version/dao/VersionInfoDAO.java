package cn.hsa.module.sys.version.dao;

import cn.hsa.module.sys.version.dto.VersionInfoDTO;
import java.util.List;

/**
* @ClassName VersionInfoDAO
* @Deacription dao层
* @Author liuhuiming
* @Date 2022-02-10
* @Version 1.0
**/
public interface VersionInfoDAO  {

    /**
     * @Menthod queryPage
     * @Desrciption 根据条件查询版本信息
     * @Param
     * 1. VersionInfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/2/10 17:02
     * @Return cn.hsa.base.PageDTO
     **/
    List<VersionInfoDTO> queryPage(VersionInfoDTO versionInfoDTO);

    /**
     * @Menthod insert()
     * @Desrciption 新增版本信息
     * @Param
     *1. VersionInfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/2/10 17:02
     * @Return int
     **/
    int insert(VersionInfoDTO versionInfoDTO);

    /**
     * @Menthod queryversionInfo
     * @Desrciption 根据条件查询版本信息
     * @Param
     * 1. VersionInfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/2/10 17:02
     * @Return cn.hsa.base.PageDTO
     **/
    VersionInfoDTO queryVersionInfo();

    /**
     *  查询历史升级公告消息
     * @param versionInfoDTO 参数只需要传递分页号
     * @return
     */
    List<VersionInfoDTO> queryHistoryVersionInfo(VersionInfoDTO versionInfoDTO);


}
