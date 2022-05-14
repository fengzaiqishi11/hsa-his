package cn.hsa.module.insure.clinica.dao;


import cn.hsa.module.insure.clinica.dto.ClinicalExaminationInfoDTO;

import java.util.List;
import java.util.Map;

/**
* @ClassName ClinicalExaminationInfoDAO
* @Deacription 临床检查报告信息表dao层
* @Author liuhuiming
* @Date 2022-05-05
* @Version 1.0
**/
public interface ClinicalExaminationInfoDAO {

    /**
     * @Menthod queryPage
     * @Desrciption 分页查询
     * @Param
     * 1. clinicalExaminationInfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return
     **/
    List<ClinicalExaminationInfoDTO> queryPageClinicalExamination(ClinicalExaminationInfoDTO clinicalExaminationInfoDTO);

    /**
     * @Menthod insert()
     * @Desrciption 新增
     * @Param
     *1. insureEmrAdminfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    int insertClinicalExamination(ClinicalExaminationInfoDTO clinicalExaminationInfoDTO);

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
    ClinicalExaminationInfoDTO queryByUuid(Long uuid);

}
