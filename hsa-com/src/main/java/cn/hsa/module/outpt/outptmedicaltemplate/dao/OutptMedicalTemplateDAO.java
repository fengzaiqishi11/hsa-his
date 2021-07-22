package cn.hsa.module.outpt.outptmedicaltemplate.dao;

import cn.hsa.module.outpt.outptmedicaltemplate.dto.OutptMedicalTemplateDTO;
import java.util.List;

/**
 * 门诊病历模板表(OutptMedicalTemplate)表数据库访问层
 *
 * @author makejava
 * @since 2021-03-09 14:13:05
 */
public interface OutptMedicalTemplateDAO {

    /**
    * @Menthod getById
    * @Desrciption 根据id查询
    *
    * @Param
    * [id]
    *
    * @Author jiahong.yang
    * @Date   2021/3/9 14:19
    * @Return cn.hsa.module.outpt.outptmedicaltemplate.dto.OutptMedicalTemplateDTO
    **/
    OutptMedicalTemplateDTO getById(OutptMedicalTemplateDTO outptMedicalTemplateDTO);

    /**
    * @Menthod queryAllByLimit
    * @Desrciption
    *
    * @Param
    * []
    *
    * @Author jiahong.yang
    * @Date   2021/3/9 14:19
    * @Return java.util.List<cn.hsa.module.outpt.outptmedicaltemplate.dto.OutptMedicalTemplateDTO>
    **/
    List<OutptMedicalTemplateDTO> queryMedicalTemplatePage(OutptMedicalTemplateDTO outptMedicalTemplateDTO );


    /**
    * @Menthod queryAll
    * @Desrciption
    *
    * @Param
    * [outptMedicalTemplateDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/3/9 15:41
    * @Return java.util.List<cn.hsa.module.outpt.outptmedicaltemplate.dto.OutptMedicalTemplateDTO>
    **/
    List<OutptMedicalTemplateDTO> queryAll(OutptMedicalTemplateDTO outptMedicalTemplateDTO);

    /**
    * @Menthod insert
    * @Desrciption 新增门诊电子病历模板
    *
    * @Param
    * [outptMedicalTemplateDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/3/9 15:26
    * @Return int
    **/
    int insert(OutptMedicalTemplateDTO outptMedicalTemplateDTO);

    /**
    * @Menthod update
    * @Desrciption 修改门诊电子病历模板
    *
    * @Param
    * [outptMedicalTemplateDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/3/9 15:27
    * @Return int
    **/
    int update(OutptMedicalTemplateDTO outptMedicalTemplateDTO);

    /**
    * @Menthod updateStatus
    * @Desrciption 修改门诊电子病历模板状态
    *
    * @Param
    * [outptMedicalTemplateDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/3/9 15:27
    * @Return int
    **/
    int updateStatus(OutptMedicalTemplateDTO outptMedicalTemplateDTO);

    int deleteById(String id);

}
