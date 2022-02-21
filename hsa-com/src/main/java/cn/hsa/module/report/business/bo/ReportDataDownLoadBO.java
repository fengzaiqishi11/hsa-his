package cn.hsa.module.report.business.bo;

import cn.hsa.module.report.business.dto.ReportReturnDataDTO;

import java.util.Map;

/**
 * @ClassName ReportDataDownLoadBO
 * @Deacription 接口
 * @Author liuzhoting
 * @Date 2022-02-18
 * @Version 1.0
 **/
public interface ReportDataDownLoadBO {

    /**
     * 报表生成
     *
     * @param map
     * @return cn.hsa.module.report.business.dto.ReportReturnDataDTO
     * @menthod saveBuild()
     * @author liuzhuoting
     * @date 2022/02/18 09:30
     **/
    ReportReturnDataDTO saveBuild(Map map);

    /**
     * 报表删除
     *
     * @param map
     * @return java.lang.Boolean
     * @menthod deleteReport()
     * @author liuzhuoting
     * @date 2022/02/18 09:30
     **/
    Boolean deleteReport(Map map);

}
