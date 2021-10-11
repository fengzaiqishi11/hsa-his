package cn.hsa.emr.emrqualityaging.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.emr.emrquality.bo.EmrQualityAgingBO;
import cn.hsa.module.emr.emrquality.dao.EmrQualityAgingDAO;
import cn.hsa.module.emr.emrquality.dto.EmrQualityAgingDTO;
import cn.hsa.util.BigDecimalUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.emr.emrqualityaging.bo.impl
 * @Class_name: EmrQualityAgingBOImpl
 * @Describe: 电子病历时效质控业务实现层
 * @Author: liuliyun
 * @Email: liyun.liu@powersi.com
 * @Date: 2021/09/24 10:18
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class EmrQualityAgingBOImpl extends HsafBO implements EmrQualityAgingBO {
    @Resource
    private EmrQualityAgingDAO emrQualityAgingDAO;


    @Override
    public boolean insertEmrQualityAging(EmrQualityAgingDTO emrQualityAgingDTO) {
        if (emrQualityAgingDTO!=null){
            emrQualityAgingDTO.setId(SnowflakeUtils.getId());
            if (emrQualityAgingDTO.getTipsType()!=null &&emrQualityAgingDTO.getTipsType().equals("5")){
                if (StringUtils.isEmpty(emrQualityAgingDTO.getAdviceList())){
                    throw new AppException("请选择医嘱");
                }
            }
            if (emrQualityAgingDTO.getTimeOut()!=null){
                if(BigDecimalUtils.isZero(emrQualityAgingDTO.getTimeOut())){
                    throw new AppException("超时时间不能为0");
                }
                if(BigDecimalUtils.lessZero(emrQualityAgingDTO.getTimeOut())){
                    throw new AppException("超时时间不能为负数");
                }
            }
            EmrQualityAgingDTO qualityAgingDTO =emrQualityAgingDAO.queryEmrQualityListByEmrCode(emrQualityAgingDTO);
            if (qualityAgingDTO!=null&&qualityAgingDTO.getEmrCode()!=null){
                throw new AppException("该病历模板已添加时效提醒，请勿重复添加！");
            }
        }
        if (emrQualityAgingDAO.insertQualityRecord(emrQualityAgingDTO)>0){
            return  true;
        } else {
            return false;
        }
    }

    @Override
    public boolean updateEmrQualityAging(EmrQualityAgingDTO emrQualityAgingDTO) {
        if (emrQualityAgingDAO.updateQualityRecord(emrQualityAgingDTO)>0){
            return  true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteEmrQualityAging(Map map) {
        if (emrQualityAgingDAO.deleteEmrQualityAging(map)>0){
            return  true;
        } else {
            return false;
        }
    }

    @Override
    public List<Map> queryEmrTemplateList(Map map) {
        return emrQualityAgingDAO.queryEmrTemplateList(map);
    }

    @Override
    public List<EmrQualityAgingDTO> queryEmrQualityList(Map map) {
        EmrQualityAgingDTO emrQualityAgingDTO = MapUtils.get(map,"emrQualityAgingDTO");
        return emrQualityAgingDAO.queryEmrQualityList(emrQualityAgingDTO);
    }

    @Override
    public List<EmrQualityAgingDTO> queryEmrQualityListById(Map map) {
        EmrQualityAgingDTO emrQualityAgingDTO = MapUtils.get(map,"emrQualityAgingDTO");
        return emrQualityAgingDAO.queryEmrQualityListById(emrQualityAgingDTO);
    }
}
