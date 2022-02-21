package cn.hsa.report.config.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.report.config.bo.ReportConfigurationBO;
import cn.hsa.module.report.config.dao.ReportConfigurationDAO;
import cn.hsa.module.report.config.dto.ReportConfigurationDTO;
import cn.hsa.util.SnowflakeUtils;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName ReportConfigurationBOImpl
 * @Deacription 服务层
 * @Author liuzhoting
 * @Date 2022-02-16
 * @Version 1.0
 **/
@Component
public class ReportConfigurationBOImpl extends HsafBO implements ReportConfigurationBO {

    @Autowired
    private ReportConfigurationDAO reportConfigurationDAO;

    @Override
    public PageDTO queryPage(ReportConfigurationDTO reportConfigurationDTO) {
        // 设置分页信息
        PageHelper.startPage(reportConfigurationDTO.getPageNo(), reportConfigurationDTO.getPageSize());
        // 根据条件查询所
        List<ReportConfigurationDTO> sysParameterDTOS = reportConfigurationDAO.queryPage(reportConfigurationDTO);
        return PageDTO.of(sysParameterDTOS);
    }

    @Override
    public boolean insert(ReportConfigurationDTO reportConfigurationDTO) {
        //判断编码(名称)是否重复
        if (reportConfigurationDAO.queryCodeIsExist(reportConfigurationDTO) > 0) {
            throw new AppException("该报表编码已存在");
        }
        reportConfigurationDTO.setId(SnowflakeUtils.getId());
        return reportConfigurationDAO.insert(reportConfigurationDTO) > 0;
    }

    @Override
    public boolean delete(ReportConfigurationDTO reportConfigurationDTO) {
        return reportConfigurationDAO.delete(reportConfigurationDTO) > 0;
    }

    @Override
    public boolean update(ReportConfigurationDTO reportConfigurationDTO) {
        return reportConfigurationDAO.update(reportConfigurationDTO) > 0;
    }

}
