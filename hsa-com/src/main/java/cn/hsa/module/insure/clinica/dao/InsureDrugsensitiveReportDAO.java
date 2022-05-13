package cn.hsa.module.insure.clinica.dao;


import cn.hsa.module.insure.clinica.dto.InsureDrugsensitiveReportDTO;

import java.util.List;
import java.util.Map;

/**
* @ClassName InsureDrugsensitiveReportDAO
* @Deacription 医保药敏记录上报表dao层
* @Author liuhuiming
* @Date 2022-05-10
* @Version 1.0
**/
public interface InsureDrugsensitiveReportDAO {

    /**
     * @Menthod queryPage
     * @Desrciption 分页查询
     * @Param
     * 1. InsureDrugsensitiveReportDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return
     **/
    List<InsureDrugsensitiveReportDTO> queryPageInsureDrugsensitiveReport(InsureDrugsensitiveReportDTO insureDrugsensitiveReportDTO);

    /**
     * @Menthod insert()
     * @Desrciption 新增
     * @Param
     *1. insureEmrAdminfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    int insertInsureDrugsensitiveReport(InsureDrugsensitiveReportDTO insureDrugsensitiveReportDTO);

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
    InsureDrugsensitiveReportDTO queryByUuid(Long uuid);

}
