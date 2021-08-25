package cn.hsa.module.outpt.outptmedicaltemplate.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.outpt.outptmedicaltemplate.dto.OutptMedicalTemplateDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.outptmedicaltemplate.bo
 * @Class_name: OutptMedicalTemplateBO
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/3/9 14:28
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface OutptMedicalTemplateBO {
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
   * @Menthod queryMedicalTemplatePage
   * @Desrciption 分页查询电子病历模板
   *
   * @Param
   * []
   *
   * @Author jiahong.yang
   * @Date   2021/3/9 14:19
   * @Return java.util.List<cn.hsa.module.outpt.outptmedicaltemplate.dto.OutptMedicalTemplateDTO>
   **/
  PageDTO queryMedicalTemplatePage(OutptMedicalTemplateDTO outptMedicalTemplateDTO);


  /**
  * @Menthod queryAll
  * @Desrciption 查询所有的电子病历模板
  *
  * @Param
  * [outptMedicalTemplateDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/3/9 15:30
  * @Return java.util.List<cn.hsa.module.outpt.outptmedicaltemplate.dto.OutptMedicalTemplateDTO>
  **/
  List<OutptMedicalTemplateDTO> queryAll(OutptMedicalTemplateDTO outptMedicalTemplateDTO);

  /**
  * @Menthod saveOutptMedicalTemplate
  * @Desrciption 新增门诊电子病历模板
  *
  * @Param
  * [outptMedicalTemplateDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/3/9 15:31
  * @Return java.lang.Boolean
  **/
  Boolean insertMedicalTemplate(OutptMedicalTemplateDTO outptMedicalTemplateDTO);

  /**
  * @Menthod updateMedicalTemplate
  * @Desrciption 编辑门诊电子病历模板
  *
  * @Param
  * [outptMedicalTemplateDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/3/9 19:29
  * @Return java.lang.Boolean
  **/
  Boolean updateMedicalTemplate(Map map);

  /**
  * @Menthod updateStatus
  * @Desrciption 修改门诊电子病历模板状态
  *
  * @Param
  * [outptMedicalTemplateDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/3/9 15:31
  * @Return java.lang.Boolean
  **/
  Boolean updateStatus(OutptMedicalTemplateDTO outptMedicalTemplateDTO);

  Boolean deleteById(String id);
}
