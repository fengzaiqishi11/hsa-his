package cn.hsa.clinical.clinicalpathlist.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.module.clinical.clinicalpathlist.bo.ClinicPathListBO;
import cn.hsa.module.clinical.clinicalpathlist.dto.ClinicPathListDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Package_name: cn.hsa.clinical.clinicalpathlist.bo.impl
 * @Class_name: ClinicPathListBoImpl
 * @Describe: 临床路径业务层
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/9/8 20:05
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class ClinicPathListBoImpl implements ClinicPathListBO {

    @Override
    public ClinicPathListDTO getClinicPathById(String id) {
      return null;
    }

    @Override
    public List<ClinicPathListDTO> queryClinicPathAll(ClinicPathListDTO clinicPathList) {
      return null;
    }

    @Override
    public PageDTO queryClinicPathPage(ClinicPathListDTO clinicPathList) {
      return null;
    }

    @Override
    public int insertClinicPath(ClinicPathListDTO clinicPathList) {
      return 0;
    }

    @Override
    public int updateClinicPath(ClinicPathListDTO clinicPathList) {
      return 0;
    }

    @Override
    public int deleteClinicPathById(ClinicPathListDTO clinicPathList) {
      return 0;
    }
}
