package cn.hsa.clinical.clinicalpathstagedetail.bo.impl;

import cn.hsa.base.PageDO;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.clinical.clinicalpathstagedetail.bo.ClinicPathStageDetailBO;
import cn.hsa.module.clinical.clinicalpathstagedetail.dao.ClinicPathStageDetailDAO;
import cn.hsa.module.clinical.clinicalpathstagedetail.dto.ClinicPathStageDetailDTO;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.clinical.clinicalpathstagedetail.bo.impl
 * @Class_name: ClinicPathStageDetailBoImpl
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/9/10 10:00
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class ClinicPathStageDetailBoImpl extends HsafBO implements ClinicPathStageDetailBO {

  @Resource
  private ClinicPathStageDetailDAO clinicPathStageDetailDAOl;

  @Override
  public ClinicPathStageDetailDTO getClinicPathStageDetailById(ClinicPathStageDetailDTO clinicPathStageDetailDTO) {
    return clinicPathStageDetailDAOl.getClinicPathStageDetailById(clinicPathStageDetailDTO);
  }

  @Override
  public List<ClinicPathStageDetailDTO> queryAllClinicPathStageDetail(ClinicPathStageDetailDTO clinicPathStageDetailDTO) {
    return clinicPathStageDetailDAOl.queryAllClinicPathStageDetail(clinicPathStageDetailDTO);
  }

  @Override
  public PageDTO queryClinicPathStageDetailPage(ClinicPathStageDetailDTO clinicPathStageDetailDTO) {
    PageHelper.startPage(clinicPathStageDetailDTO.getPageNo(),clinicPathStageDetailDTO.getPageSize());
    List<ClinicPathStageDetailDTO> clinicPathStageDetailDTOS = clinicPathStageDetailDAOl.queryAllClinicPathStageDetail(clinicPathStageDetailDTO);
    return PageDTO.of(clinicPathStageDetailDTOS);
  }

  @Override
  public Boolean insertClinicPathStageDetail(ClinicPathStageDetailDTO clinicPathStageDetailDTO) {
    return clinicPathStageDetailDAOl.insertClinicPathStageDetail(clinicPathStageDetailDTO) > 0;

  }

  @Override
  public Boolean updateClinicPathStageDetail(ClinicPathStageDetailDTO clinicPathStageDetailDTO) {
    return clinicPathStageDetailDAOl.updateClinicPathStageDetail(clinicPathStageDetailDTO) > 0;

  }

  @Override
  public Boolean deleteClinicPathStageDetailById(ClinicPathStageDetailDTO clinicPathStageDetailDTO) {
    return clinicPathStageDetailDAOl.deleteClinicPathStageDetailById(clinicPathStageDetailDTO) > 0;

  }
}
