package cn.hsa.module.report.record.dao;

import cn.hsa.module.report.record.dto.ReportFileRecordDTO;

/**
 * @ClassName ReportFileRecordDAO
 * @Deacription dao层
 * @Author liuzhoting
 * @Date 2022-02-16
 * @Version 1.0
 **/
public interface ReportFileRecordDAO {

    /**
     * 新增
     *
     * @param reportFileRecordDTO 参数数据对象
     * @return int
     * @menthod insert()
     * @author liuzhuoting
     * @date 2022/02/17 09:30
     **/
    int insert(ReportFileRecordDTO reportFileRecordDTO);

    /**
     * 删除
     *
     * @param fileAddress 参数数据
     * @return int
     * @menthod delete()
     * @author liuzhuoting
     * @date 2022/02/17 09:30
     **/
    int deleteByFileAddress(String fileAddress);

}
