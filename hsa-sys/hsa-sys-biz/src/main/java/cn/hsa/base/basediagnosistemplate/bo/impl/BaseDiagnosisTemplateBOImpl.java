package cn.hsa.base.basediagnosistemplate.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.ba.dao.BaseAdviceDetailDAO;
import cn.hsa.module.base.bd.dao.BaseDiseaseDAO;
import cn.hsa.module.base.bd.dto.BaseDiseaseDTO;
import cn.hsa.module.base.diagnosistemplate.bo.BaseDiagnosisTemplateBO;
import cn.hsa.module.base.diagnosistemplate.dao.BaseDiagnosisTemplateDAO;
import cn.hsa.module.base.diagnosistemplate.dto.BaseDiagnosisTemplateDTO;
import cn.hsa.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.base.ba.bo.impl
 * @Class_name: BaseDiagnosisTemplateBOImpl
 * @Describe: 医嘱信息业务逻辑实现层
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/13 15:06
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class BaseDiagnosisTemplateBOImpl extends HsafBO implements BaseDiagnosisTemplateBO {
    @Resource
    BaseDiagnosisTemplateDAO baseDiagnosisTemplateDAO;

    @Resource
    BaseDiseaseDAO baseDiseaseDAO;


    /**
     * @param map 诊断模板信息
     * @Menthod getById
     * @Desrciption 通过主键ID查询诊断模板信息
     * @Author pengbo
     * @Date 2021/3/27 9:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
     **/
    @Override
    public BaseDiagnosisTemplateDTO getById(Map<String, Object> map) {
        BaseDiagnosisTemplateDTO baseDiagnosisTemplate = (BaseDiagnosisTemplateDTO) map.get("baseDiagnosisTemplateDTO");
        return baseDiagnosisTemplateDAO.getById(baseDiagnosisTemplate);
    }

    /**
     * @param baseDiagnosisTemplate 诊断模板信息
     * @Menthod queryAll
     * @Desrciption 查询诊断模板
     * @Author pengbo
     * @Date 2021/3/27 9:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
     **/
    @Override
    public List<BaseDiagnosisTemplateDTO> queryAll(BaseDiagnosisTemplateDTO baseDiagnosisTemplate) {
        return baseDiagnosisTemplateDAO.queryAll(baseDiagnosisTemplate);
    }

    /**
     * @param map 诊断模板信息
     * @Menthod save
     * @Desrciption 保存诊断模板信息
     * @Author pengbo
     * @Date 2021/3/27 9:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
     **/
    @Override
    public Boolean save(Map<String, Object> map) {
        BaseDiagnosisTemplateDTO baseDiagnosisTemplate = (BaseDiagnosisTemplateDTO) map.get("baseDiagnosisTemplateDTO");
        if(baseDiagnosisTemplate == null){
            throw  new RuntimeException("保存模板失败,模板信息为空！");
        }
        baseDiagnosisTemplate.setId(SnowflakeUtils.getId());
        baseDiagnosisTemplate.setPym(PinYinUtils.toFirstPY(baseDiagnosisTemplate.getDiseaseName()));
        baseDiagnosisTemplate.setWbm(WuBiUtils.getWBCode(baseDiagnosisTemplate.getDiseaseName()));
        baseDiagnosisTemplate.setIsCheck(Constants.SF.F);
        baseDiagnosisTemplate.setIsValid(Constants.SF.S);
        baseDiagnosisTemplate.setCrteId(MapUtils.getVS(map,"crteId",""));
        baseDiagnosisTemplate.setCrteName(MapUtils.getVS(map,"crteName",""));
        baseDiagnosisTemplate.setCrteTime(new Date());

        if (StringUtils.isEmpty(baseDiagnosisTemplate.getDiseaseId())){
            BaseDiseaseDTO baseDiseaseDTO = new BaseDiseaseDTO();
            baseDiseaseDTO.setId(SnowflakeUtils.getId());
            baseDiagnosisTemplate.setDiseaseId(baseDiseaseDTO.getId());
            baseDiseaseDTO.setHospCode(baseDiagnosisTemplate.getHospCode());
            baseDiseaseDTO.setTypeCode(baseDiagnosisTemplate.getTypeCode());
            baseDiseaseDTO.setIsAdd("0");
            baseDiseaseDTO.setNationCode("");
            baseDiseaseDTO.setCode(baseDiagnosisTemplate.getDiseaseIcd10());
            baseDiseaseDTO.setAttachCode(baseDiagnosisTemplate.getDiseaseIcd10());
            baseDiseaseDTO.setName(baseDiagnosisTemplate.getDiseaseName());
            baseDiseaseDTO.setWbm(baseDiagnosisTemplate.getWbm());
            baseDiseaseDTO.setPym(baseDiagnosisTemplate.getPym());
            baseDiseaseDTO.setIsValid("1");
            baseDiseaseDTO.setCrteId(baseDiagnosisTemplate.getCrteId());
            baseDiseaseDTO.setCrteName(baseDiagnosisTemplate.getCrteName());
            baseDiseaseDTO.setCrteTime(baseDiagnosisTemplate.getCrteTime());
            try{
                baseDiseaseDAO.insert(baseDiseaseDTO);
            }catch (Exception e){
                throw new RuntimeException("疾病编码重复,请重新输入!");
            }
        }
        return baseDiagnosisTemplateDAO.save(baseDiagnosisTemplate);
    }

    /**
     * @param map 诊断模板信息
     * @Menthod deleteById
     * @Desrciption 根据ID删除诊断模板信息
     * @Author pengbo
     * @Date 2021/3/27 9:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
     **/
    @Override
    public Boolean deleteById(Map<String, Object> map) {
        BaseDiagnosisTemplateDTO baseDiagnosisTemplate = (BaseDiagnosisTemplateDTO) map.get("baseDiagnosisTemplateDTO");
        return baseDiagnosisTemplateDAO.deleteById(baseDiagnosisTemplate);
    }

    /**
     * @param map 诊断模板信息
     * @Menthod updateById
     * @Desrciption 根据ID 修改诊断模板信息
     * @Author pengbo
     * @Date 2021/3/27 9:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
     **/
    @Override
    public Boolean updateById(Map<String, Object> map) {
        BaseDiagnosisTemplateDTO baseDiagnosisTemplate = (BaseDiagnosisTemplateDTO) map.get("baseDiagnosisTemplateDTO");
        if(baseDiagnosisTemplate == null){
            throw  new RuntimeException("修改模板失败,模板信息为空！");
        }
        baseDiagnosisTemplate.setPym(PinYinUtils.toFirstPY(baseDiagnosisTemplate.getDiseaseName()));
        baseDiagnosisTemplate.setWbm(WuBiUtils.getWBCode(baseDiagnosisTemplate.getDiseaseName()));
        return baseDiagnosisTemplateDAO.updateById(baseDiagnosisTemplate);
    }

    /**
     * @Menthod: updateStatusCode
     * @Desrciption: 审核/作废诊断管理
     * @Param: baseDiagnosisTemplateDTO
     *  审核：checkFlag = 1，作废：checkFlag = 2
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-10-12 13:49
     * @Return: Boolean
     **/
    @Override
    public Boolean updateStatusCode(BaseDiagnosisTemplateDTO baseDiagnosisTemplateDTO) {
        return baseDiagnosisTemplateDAO.updateStatusCode(baseDiagnosisTemplateDTO) > 0;
    }
}
