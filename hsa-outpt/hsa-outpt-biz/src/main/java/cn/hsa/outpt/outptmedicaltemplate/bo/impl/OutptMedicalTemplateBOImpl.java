package cn.hsa.outpt.outptmedicaltemplate.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bd.dto.BaseDiseaseDTO;
import cn.hsa.module.base.bd.service.BaseDiseaseService;
import cn.hsa.module.outpt.outptmedicaltemplate.bo.OutptMedicalTemplateBO;
import cn.hsa.module.outpt.outptmedicaltemplate.dao.OutptMedicalTemplateDAO;
import cn.hsa.module.outpt.outptmedicaltemplate.dto.OutptMedicalTemplateDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Package_name: cn.hsa.outpt.outptmedicaltemplate.bo.impl
 * @Class_name: OutptMedicalTemplateBOImpl
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/3/9 15:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class OutptMedicalTemplateBOImpl extends HsafBO implements OutptMedicalTemplateBO {

  @Resource
  private OutptMedicalTemplateDAO outptMedicalTemplateDAO;

  @Resource
  private BaseDiseaseService baseDiseaseService_consumer;

  /**
  * @Menthod getById
  * @Desrciption 根据主键查询电子病历模板
  *
  * @Param
  * [outptMedicalTemplateDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/3/9 15:30
  * @Return cn.hsa.module.outpt.outptmedicaltemplate.dto.OutptMedicalTemplateDTO
  **/
  @Override
  public OutptMedicalTemplateDTO getById(OutptMedicalTemplateDTO outptMedicalTemplateDTO) {
    return outptMedicalTemplateDAO.getById(outptMedicalTemplateDTO);
  }

  /**
  * @Menthod queryMedicalTemplatePage
  * @Desrciption 分页查询电子病历模板
  *
  * @Param
  * [outptMedicalTemplateDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/3/9 15:30
  * @Return cn.hsa.base.PageDTO
  **/
  @Override
  public PageDTO queryMedicalTemplatePage(OutptMedicalTemplateDTO outptMedicalTemplateDTO) {
    PageHelper.startPage(outptMedicalTemplateDTO.getPageNo(), outptMedicalTemplateDTO.getPageSize());
    List<OutptMedicalTemplateDTO> outptMedicalTemplateDTOS = outptMedicalTemplateDAO.queryMedicalTemplatePage(outptMedicalTemplateDTO);
    outptMedicalTemplateDTOS = changeDisease(outptMedicalTemplateDTOS,outptMedicalTemplateDTO.getHospCode());
    return PageDTO.of(outptMedicalTemplateDTOS);
  }

  /**
  * @Menthod queryAll
  * @Desrciption 查询所有的电子病历模板
  *
  * @Param
  * [outptMedicalTemplateDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/3/9 15:29
  * @Return java.util.List<cn.hsa.module.outpt.outptmedicaltemplate.dto.OutptMedicalTemplateDTO>
  **/
  @Override
  public List<OutptMedicalTemplateDTO> queryAll(OutptMedicalTemplateDTO outptMedicalTemplateDTO) {
    List<OutptMedicalTemplateDTO> outptMedicalTemplateDTOS = outptMedicalTemplateDAO.queryMedicalTemplatePage(outptMedicalTemplateDTO);
    return outptMedicalTemplateDTOS;
  }

  /**
  * @Menthod saveOutptMedicalTemplate
  * @Desrciption 新增门诊电子病历模板
  *
  * @Param
  * [outptMedicalTemplateDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/3/9 15:29
  * @Return java.lang.Boolean
  **/
  @Override
  public Boolean insertMedicalTemplate(OutptMedicalTemplateDTO outptMedicalTemplateDTO) {
    outptMedicalTemplateDTO.setId(SnowflakeUtils.getId());
    outptMedicalTemplateDTO.setIsValid("1");
    if(StringUtils.isEmpty(outptMedicalTemplateDTO.getTypeCode())) {
      throw new AppException("模板类型为空");
    }
    if(StringUtils.isEmpty(outptMedicalTemplateDTO.getName())) {
      throw new AppException("模板名称为空");
    }
    outptMedicalTemplateDTO.setPym(PinYinUtils.toFirstPY(outptMedicalTemplateDTO.getName()));
    outptMedicalTemplateDTO.setWbm(WuBiUtils.getWBCode(outptMedicalTemplateDTO.getName()));
    if("0".equals(outptMedicalTemplateDTO.getTypeCode())){
      outptMedicalTemplateDTO.setDeptId(null);
      outptMedicalTemplateDTO.setDeptName(null);
      outptMedicalTemplateDTO.setDoctorId(null);
      outptMedicalTemplateDTO.setDoctorName(null);
    } else if("1".equals(outptMedicalTemplateDTO.getTypeCode())) {
      outptMedicalTemplateDTO.setDoctorId(null);
      outptMedicalTemplateDTO.setDoctorName(null);
    } else {
      outptMedicalTemplateDTO.setDeptId(null);
      outptMedicalTemplateDTO.setDeptName(null);
    }
    return outptMedicalTemplateDAO.insert(outptMedicalTemplateDTO) > 0;
  }

  /**
  * @Menthod updateMedicalTemplate
  * @Desrciption 编辑门诊电子病历模板
  *
  * @Param
  * [outptMedicalTemplateDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/3/9 19:31
  * @Return java.lang.Boolean
  **/
  @Override
  public Boolean updateMedicalTemplate(Map map) {
    OutptMedicalTemplateDTO outptMedicalTemplateDTO = MapUtils.get(map,"outptMedicalTemplateDTO");
    if(StringUtils.isEmpty(outptMedicalTemplateDTO.getTypeCode())) {
      throw new AppException("模板类型为空");
    }
    if(StringUtils.isEmpty(outptMedicalTemplateDTO.getName())) {
      throw new AppException("模板名称为空");
    }
    outptMedicalTemplateDTO.setPym(PinYinUtils.toFirstPY(outptMedicalTemplateDTO.getName()));
    outptMedicalTemplateDTO.setWbm(WuBiUtils.getWBCode(outptMedicalTemplateDTO.getName()));
    OutptMedicalTemplateDTO byId = outptMedicalTemplateDAO.getById(outptMedicalTemplateDTO);
    if(!byId.getTypeCode().equals(outptMedicalTemplateDTO.getTypeCode())){
      if("0".equals(outptMedicalTemplateDTO.getTypeCode())) {
        outptMedicalTemplateDTO.setDeptId(null);
        outptMedicalTemplateDTO.setDeptName(null);
        outptMedicalTemplateDTO.setDoctorId(null);
        outptMedicalTemplateDTO.setDoctorName(null);
      } else if("1".equals(outptMedicalTemplateDTO.getTypeCode())){
        outptMedicalTemplateDTO.setDeptId(MapUtils.get(map,"loginDeptId"));
        outptMedicalTemplateDTO.setDeptName(MapUtils.get(map,"loginDeptName"));
        outptMedicalTemplateDTO.setDoctorId(null);
        outptMedicalTemplateDTO.setDoctorName(null);
      } else {
        outptMedicalTemplateDTO.setDoctorId(MapUtils.get(map,"userId"));
        outptMedicalTemplateDTO.setDoctorName(MapUtils.get(map,"userName"));
        outptMedicalTemplateDTO.setDeptId(null);
        outptMedicalTemplateDTO.setDeptName(null);
      }
    }
    return outptMedicalTemplateDAO.update(outptMedicalTemplateDTO) > 0;
  }

  /**
  * @Menthod updateStatus
  * @Desrciption 修改门诊电子病历模板状态
  *
  * @Param
  * [outptMedicalTemplateDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/3/9 15:28
  * @Return java.lang.Boolean
  **/
  @Override
  public Boolean updateStatus(OutptMedicalTemplateDTO outptMedicalTemplateDTO) {
    return outptMedicalTemplateDAO.updateStatus(outptMedicalTemplateDTO) > 0;
  }

  @Override
  public Boolean deleteById(String id) {
    return true;
  }


  /**
   * 将疾病ID转化成{id,name}
   * @param list
   * @param hospCode
   * @return
   */
  public List<OutptMedicalTemplateDTO> changeDisease (List<OutptMedicalTemplateDTO> list ,String hospCode){
    for(OutptMedicalTemplateDTO outptMedicalTemplateDTO : list){
      String diseaseIds = outptMedicalTemplateDTO.getDiseaseId();
      if(StringUtils.isNotEmpty(diseaseIds)){
        BaseDiseaseDTO baseDiseaseDTO = new BaseDiseaseDTO();
        baseDiseaseDTO.setHospCode(hospCode);
        baseDiseaseDTO.setIds(Arrays.asList(diseaseIds.split(",")));
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode",hospCode);
        map.put("baseDiseaseDTO",baseDiseaseDTO);
        List<BaseDiseaseDTO> baseDiseaseDTOS =   baseDiseaseService_consumer.getDiseaseByIds(map);
        List<Map<String, Object>> diseaseList = new ArrayList<>();
        Map<String, Object>  diseaseMap = null ;
        for(BaseDiseaseDTO a :baseDiseaseDTOS){
          diseaseMap = new HashMap() ;
          diseaseMap.put("id",a.getId());
          diseaseMap.put("name",a.getName());
          diseaseList.add(diseaseMap);
        }
        outptMedicalTemplateDTO.setDiseaseIds(diseaseList);
      }
    }
    return list;
  }
}
