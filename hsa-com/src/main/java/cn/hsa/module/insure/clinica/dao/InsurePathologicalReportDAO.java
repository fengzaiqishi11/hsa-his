package cn.hsa.module.insure.clinica.dao;


import cn.hsa.module.insure.clinica.dto.InsurePathologicalReportDTO;

import java.util.List;
import java.util.Map;

/**
* @ClassName InsurePathologicalReportDAO
* @Deacription 病理检查报告记录信息表dao层
* @Author liuhuiming
* @Date 2022-05-10
* @Version 1.0
**/
public interface InsurePathologicalReportDAO {

    /**
     * @Menthod queryPage
     * @Desrciption 分页查询
     * @Param
     * 1. InsurePathologicalReportDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return
     **/
    List<InsurePathologicalReportDTO> queryPagePathologicalReport(InsurePathologicalReportDTO insurePathologicalReportDTO);

    /**
     * @Menthod insert()
     * @Desrciption 新增
     * @Param
     *1. insureEmrAdminfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    int insertInsurePathologicalReport(InsurePathologicalReportDTO insurePathologicalReportDTO);

    /**
     * @Menthod updateSelective()
     * @Desrciption 修改
     * @Param
     *1. insureEmrAdminfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    int updateSelective(Map map);

    /**
     * @Menthod queryByUuid()
     * @Desrciption 查询单条
     * @Param
     *1. insureEmrAdminfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    InsurePathologicalReportDTO queryByUuid(Long uuid);

}
