package cn.hsa.module.report.config.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.report.config.dto.ReportConfigurationDTO;

/**
 * @ClassName ReportConfigurationBO
 * @Deacription 接口
 * @Author liuzhoting
 * @Date 2022-02-16
 * @Version 1.0
 **/
public interface ReportConfigurationBO {

    /**
     * 根据条件查询参数信息
     *
     * @param reportConfigurationDTO 参数数据对象
     * @return cn.hsa.base.PageDTO
     * @menthod queryPage
     * @author liuzhuoting
     * @date 2022/02/17 09:30
     **/
    PageDTO queryPage(ReportConfigurationDTO reportConfigurationDTO);

    /**
     * 新增
     *
     * @param reportConfigurationDTO 参数数据对象
     * @return boolean
     * @menthod insert()
     * @author liuzhuoting
     * @date 2022/02/17 09:30
     **/
    boolean insert(ReportConfigurationDTO reportConfigurationDTO);

    /**
     * 删除
     *
     * @param reportConfigurationDTO 参数数据对象
     * @return boolean
     * @menthod delete()
     * @author liuzhuoting
     * @date 2022/02/17 09:30
     **/
    boolean delete(ReportConfigurationDTO reportConfigurationDTO);

    /**
     * 修改
     *
     * @param reportConfigurationDTO 参数数据对象
     * @return boolean
     * @menthod update()
     * @author liuzhuoting
     * @date 2022/02/17 09:30
     **/
    boolean update(ReportConfigurationDTO reportConfigurationDTO);

}
