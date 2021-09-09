package cn.hsa.clinical.clinicalpathlist.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.clinical.clinicalpathlist.dto.ClinicPathListDTO;
import cn.hsa.module.clinical.clinicalpathlist.service.ClinicPathListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.clinical.clinicalpathlist.service.impl
 * @Class_name: ClinicPathListServiceImpl
 * @Describe: 临床路径service接口
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/9/8 20:05
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/inpt/clinicPathListService")
@Slf4j
@Service("clinicPathListService_provider")
public class ClinicPathListServiceImpl implements ClinicPathListService {
    @Override
    public WrapperResponse<ClinicPathListDTO> getClinicPathById(Map map) {
      return null;
    }

    @Override
    public WrapperResponse<List<ClinicPathListDTO>> queryClinicPathAll(Map map) {
      return null;
    }

    @Override
    public WrapperResponse<PageDTO> queryClinicPathPage(Map map) {
      return null;
    }

    @Override
    public WrapperResponse<Boolean> insertClinicPath(Map map) {
      return null;
    }

    @Override
    public WrapperResponse<Boolean> updateClinicPath(Map map) {
      return null;
    }

    @Override
    public WrapperResponse<Boolean> deleteClinicPathById(Map map) {
      return null;
    }
}
