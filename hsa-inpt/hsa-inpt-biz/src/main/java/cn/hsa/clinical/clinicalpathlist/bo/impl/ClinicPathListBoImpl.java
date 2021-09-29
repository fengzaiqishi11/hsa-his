package cn.hsa.clinical.clinicalpathlist.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.baseoutptexec.dto.BaseOutptExecDTO;
import cn.hsa.module.base.bd.dto.BaseDiseaseDTO;
import cn.hsa.module.base.bd.service.BaseDiseaseService;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.clinical.clinicalpathlist.bo.ClinicPathListBO;
import cn.hsa.module.clinical.clinicalpathlist.dao.ClinicPathListDAO;
import cn.hsa.module.clinical.clinicalpathlist.dto.ClinicPathListDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Resource
    private ClinicPathListDAO clinicPathListDAO;

    @Resource
    private BaseOrderRuleService baseOrderRuleService;

    @Resource
    private BaseDiseaseService baseDiseaseService;

    /**
    * @Menthod getClinicPathById
    * @Desrciption 临床路径目录根据id查询
    *
    * @Param
    * [clinicPathListDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/9/9 11:03
    * @Return cn.hsa.module.clinical.clinicalpathlist.dto.ClinicPathListDTO
    **/
    @Override
    public ClinicPathListDTO getClinicPathById(ClinicPathListDTO clinicPathListDTO) {
      if("1".equals(clinicPathListDTO.getFlag())) {
        // 查询最大序号
        return clinicPathListDAO.getMaxSortNo(clinicPathListDTO);
      }
      return clinicPathListDAO.getClinicPathById(clinicPathListDTO);
    }

    /**
    * @Menthod queryClinicPathAll
    * @Desrciption  查询全部临床路径目录
    *
    * @Param
    * [clinicPathListDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/9/9 11:04
    * @Return java.util.List<cn.hsa.module.clinical.clinicalpathlist.dto.ClinicPathListDTO>
    **/
    @Override
    public List<ClinicPathListDTO> queryClinicPathAll(ClinicPathListDTO clinicPathListDTO) {
      return clinicPathListDAO.queryClinicPathAll(clinicPathListDTO);
    }

    /**
    * @Menthod queryClinicPathPage
    * @Desrciption  分页查询临床路径目录
    *
    * @Param
    * [clinicPathListDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/9/9 11:04
    * @Return cn.hsa.base.PageDTO
    **/
    @Override
    public PageDTO queryClinicPathPage(ClinicPathListDTO clinicPathListDTO) {
      PageHelper.startPage(clinicPathListDTO.getPageNo(),clinicPathListDTO.getPageSize());
      List<ClinicPathListDTO> clinicPathListDTOS = clinicPathListDAO.queryClinicPathAll(clinicPathListDTO);
      for (ClinicPathListDTO item : clinicPathListDTOS) {
        item.setDiagnoseNames(changeDiagnoseNameById(item));
      }
      return PageDTO.of(clinicPathListDTOS);
    }


    /**
    * @Menthod changeDiagnoseNameById
    * @Desrciption 根据疾病id串转化为疾病名称串
    *
    * @Param
    * [clinicPathListDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/9/15 15:09
    * @Return java.lang.String
    **/
    private String changeDiagnoseNameById(ClinicPathListDTO clinicPathListDTO){
      String diagnoseIds = clinicPathListDTO.getDiagnoseIds();
      if(StringUtils.isEmpty(diagnoseIds)) {
        return "";
      }
      String[] split = diagnoseIds.split(",");
      List<String> diagnoseIdList = Arrays.asList(split);
      Map map = new HashMap();
      map.put("hospCode",clinicPathListDTO.getHospCode());
      BaseDiseaseDTO baseDiseaseDTO = new BaseDiseaseDTO();
      baseDiseaseDTO.setHospCode(clinicPathListDTO.getHospCode());
      baseDiseaseDTO.setIds(diagnoseIdList);
      map.put("baseDiseaseDTO",baseDiseaseDTO);
      List<BaseDiseaseDTO> baseDiseaseDTOS = baseDiseaseService.queryAll(map).getData();
      if(ListUtils.isEmpty(baseDiseaseDTOS)) {
        throw new AppException("不存在改疾病id");
      }
      clinicPathListDTO.setBaseDiseaseDTOS(baseDiseaseDTOS);
      List<String> collect = baseDiseaseDTOS.stream().map(BaseDiseaseDTO::getName).collect(Collectors.toList());
      clinicPathListDTO.setNewDiagnoseIds(collect);
      return Joiner.on(",").join(collect);
    }
    /**
    * @Menthod saveClinicPath
    * @Desrciption 保存(新增或编辑)临床路径目录
    *
    * @Param
    * [clinicPathListDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/9/9 11:04
    * @Return java.lang.Boolean
    **/
    @Override
    public Boolean saveClinicPath(ClinicPathListDTO clinicPathListDTO) {
      if(StringUtils.isEmpty(clinicPathListDTO.getName())) {
        throw new AppException("路径名称为空");
      }
      if(StringUtils.isEmpty(clinicPathListDTO.getTypeCode())) {
        throw new AppException("路径使用范围类型为空");
      }
      String namePym = PinYinUtils.toFirstPY(clinicPathListDTO.getName());
      clinicPathListDTO.setPym(namePym);
      String nameWbm = WuBiUtils.getWBCode(clinicPathListDTO.getName());
      clinicPathListDTO.setWbm(nameWbm);
      if(!"1".equals(clinicPathListDTO.getTypeCode())) {
        clinicPathListDTO.setDeptName(null);
        clinicPathListDTO.setDeptId(null);
      }
      if(StringUtils.isEmpty(clinicPathListDTO.getId())) {
        HashMap map = new HashMap();
        map.put("hospCode",clinicPathListDTO.getHospCode());
        map.put("typeCode", Constants.ORDERRULE.LCLJML);
        WrapperResponse<String> orderNo = baseOrderRuleService.getOrderNo(map);
        if(StringUtils.isEmpty(orderNo.getData())) {
          throw new AppException("系统没有临床路径目录单号规则");
        }
        // 新增赋值编码
        clinicPathListDTO.setCode(orderNo.getData());
        // 赋值主键id
        clinicPathListDTO.setId(SnowflakeUtils.getId());
        // 未审核
        clinicPathListDTO.setAuditCode("0");
        if(StringUtils.isEmpty(clinicPathListDTO.getSortNo())) {
          ClinicPathListDTO maxSortNo = clinicPathListDAO.getMaxSortNo(clinicPathListDTO);
        }
        return clinicPathListDAO.insertClinicPath(clinicPathListDTO) > 0;
      }
      return clinicPathListDAO.updateClinicPath(clinicPathListDTO) > 0;
    }

    /**
    * @Menthod updateClinicPath
    * @Desrciption 临床路径目录
    * @Param
    * [clinicPathListDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/9/9 11:05
    * @Return java.lang.Boolean
    **/
    @Override
    public Boolean updateClinicPathAuditCode(ClinicPathListDTO clinicPathListDTO) {
      if(!"1".equals(clinicPathListDTO.getAuditCode())){
        clinicPathListDTO.setAuditTime(null);
        clinicPathListDTO.setAuditId(null);
        clinicPathListDTO.setAuditName(null);
      }
      return clinicPathListDAO.updateClinicPathAuditCode(clinicPathListDTO) > 0;
    }

    /**
    * @Menthod deleteClinicPathById
    * @Desrciption 删除临床路径目录
    *
    * @Param
    * [clinicPathList]
    *
    * @Author jiahong.yang
    * @Date   2021/9/9 11:05
    * @Return java.lang.Boolean
    **/
    @Override
    public Boolean deleteClinicPathById(ClinicPathListDTO clinicPathList) {
      return clinicPathListDAO.updateClinicPathAuditCode(clinicPathList) > 0;
    }
}
