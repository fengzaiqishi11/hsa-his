package cn.hsa.sys.version.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.module.sys.version.dao.VersionInfoDAO;
import cn.hsa.module.sys.version.bo.VersionInfoBO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import cn.hsa.module.sys.version.dto.VersionInfoDTO;
import java.util.List;

/**
* @ClassName VersionInfoBOImpl
* @Deacription 服务层
* @Author liuhuiming
* @Date 2022-02-10
* @Version 1.0
**/
@Component
public class VersionInfoBOImpl extends HsafBO implements VersionInfoBO {

     @Autowired
     private VersionInfoDAO versionInfoDAO;

     @Override
     public PageDTO queryVersionInfoListByPage(VersionInfoDTO versionInfoDTO) {
          // 设置分页信息
          PageHelper.startPage(versionInfoDTO.getPageNo(), versionInfoDTO.getPageSize());
          List<VersionInfoDTO> versionInfoList = versionInfoDAO.queryPage(versionInfoDTO);
          return PageDTO.of(versionInfoList);
     }

     @Override
     public boolean saveVersionInfo(VersionInfoDTO versionInfoDTO) {
          if(StringUtils.isEmpty(versionInfoDTO.getId())){
               versionInfoDTO.setId(SnowflakeUtils.getId());
          }
          int insert = versionInfoDAO.insert(versionInfoDTO);
          return insert > 0;
     }

     @Override
     public VersionInfoDTO queryVersionInfo() {
          VersionInfoDTO versionInfo = versionInfoDAO.queryVersionInfo();
          return versionInfo;
     }
}
