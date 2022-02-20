package cn.hsa.module.report.config.dao;

import cn.hsa.module.report.config.dto.ReportConfigurationDTO;

import java.util.List;

/**
 * @ClassName ReportConfigurationDAO
 * @Deacription dao层
 * @Author liuzhoting
 * @Date 2022-02-16
 * @Version 1.0
 **/
public interface ReportConfigurationDAO {

    /**
     * 根据条件查询参数信息
     *
     * @param reportConfigurationDTO 参数数据对象
     * @return cn.hsa.base.PageDTO
     * @menthod queryPage
     * @author liuzhuoting
     * @date 2022/02/17 09:30
     **/
    List<ReportConfigurationDTO> queryPage(ReportConfigurationDTO reportConfigurationDTO);

    /**
     * 新增
     *
     * @param reportConfigurationDTO 参数数据对象
     * @return int
     * @menthod insert()
     * @author liuzhuoting
     * @date 2022/02/17 09:30
     **/
    int insert(ReportConfigurationDTO reportConfigurationDTO);

    /**
     * 删除
     *
     * @param reportConfigurationDTO 参数数据对象
     * @return int
     * @menthod deleteSuppelier()
     * @author liuzhuoting
     * @date 2022/02/17 09:30
     **/
    int delete(ReportConfigurationDTO reportConfigurationDTO);

    /**
     * 修改
     *
     * @param reportConfigurationDTO 参数数据对象
     * @return int
     * @menthod update()
     * @author liuzhuoting
     * @date 2022/02/17 09:30
     **/
    int update(ReportConfigurationDTO reportConfigurationDTO);

    /**
     * 判断编码是否存在
     *
     * @param reportConfigurationDTO 参数数据对象
     * @return int
     * @menthod queryCodeIsExist()
     * @author liuzhuoting
     * @date 2022/02/17 09:30
     **/
    int queryCodeIsExist(ReportConfigurationDTO reportConfigurationDTO);

    /**
     * 根据报表名称查询
     *
     * @param hospCode 医院编码
     * @param tempCode 报表模板编码
     * @return ReportConfigurationDTO
     * @menthod queryByTempName()
     * @author liuzhuoting
     * @date 2022/02/17 09:30
     **/
    ReportConfigurationDTO queryByTempCode(String hospCode, String tempCode);

    /**
     * 根据报表名称删除
     *
     * @param tempName 报表模板文件名称
     * @return int
     * @menthod deleteByTempName()
     * @author liuzhuoting
     * @date 2022/02/17 09:30
     **/
    int deleteByTempName(String tempName);

}
