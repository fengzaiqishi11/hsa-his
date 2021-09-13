package cn.hsa.clinical.clinicalpathlist.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.clinical.clinicalpathlist.bo.ClinicPathListBO;
import cn.hsa.module.clinical.clinicalpathlist.dto.ClinicPathListDTO;
import cn.hsa.module.clinical.clinicalpathlist.service.ClinicPathListService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Resource
    private ClinicPathListBO clinicPathListBO;


    /**
    * @Menthod getClinicPathById
    * @Desrciption  临床路径目录根据id查询
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/9/10 9:14
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.clinical.clinicalpathlist.dto.ClinicPathListDTO>
    **/
    @Override
    public WrapperResponse<ClinicPathListDTO> getClinicPathById(Map map) {
      ClinicPathListDTO clinicPathListDTO = MapUtils.get(map,"clinicPathListDTO");
      return  WrapperResponse.success(clinicPathListBO.getClinicPathById(clinicPathListDTO));
    }

    /**
    * @Menthod queryClinicPathAll
    * @Desrciption  查询全部临床路径目录
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/9/10 9:14
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.clinical.clinicalpathlist.dto.ClinicPathListDTO>>
    **/
    @Override
    public WrapperResponse<List<ClinicPathListDTO>> queryClinicPathAll(Map map) {
      ClinicPathListDTO clinicPathListDTO = MapUtils.get(map,"clinicPathListDTO");
      return  WrapperResponse.success(clinicPathListBO.queryClinicPathAll(clinicPathListDTO));
    }

    /**
    * @Menthod queryClinicPathPage
    * @Desrciption  分页查询临床路径目录
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/9/10 9:14
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @Override
    public WrapperResponse<PageDTO> queryClinicPathPage(Map map) {
      ClinicPathListDTO clinicPathListDTO = MapUtils.get(map,"clinicPathListDTO");
      return  WrapperResponse.success(clinicPathListBO.queryClinicPathPage(clinicPathListDTO));
    }

    /**
    * @Menthod saveClinicPath
    * @Desrciption  保存(新增或编辑)临床路径目录
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/9/10 9:14
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> saveClinicPath(Map map) {
      ClinicPathListDTO clinicPathListDTO = MapUtils.get(map,"clinicPathListDTO");
      return  WrapperResponse.success(clinicPathListBO.saveClinicPath(clinicPathListDTO));
    }

    /**
    * @Menthod updateClinicPath
    * @Desrciption 临床路径目录
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/9/10 9:14
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> updateClinicPathAuditCode(Map map) {
      ClinicPathListDTO clinicPathListDTO = MapUtils.get(map,"clinicPathListDTO");
      return  WrapperResponse.success(clinicPathListBO.updateClinicPathAuditCode(clinicPathListDTO));
    }

    /**
    * @Menthod deleteClinicPathById
    * @Desrciption  删除临床路径目录
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/9/10 9:14
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> deleteClinicPathById(Map map) {
      ClinicPathListDTO clinicPathListDTO = MapUtils.get(map,"clinicPathListDTO");
      return  WrapperResponse.success(clinicPathListBO.deleteClinicPathById(clinicPathListDTO));
    }
}
