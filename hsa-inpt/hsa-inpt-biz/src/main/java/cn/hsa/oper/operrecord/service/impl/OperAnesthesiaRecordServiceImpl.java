package cn.hsa.oper.operrecord.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bd.dto.BaseDiseaseDTO;
import cn.hsa.module.base.bd.service.BaseDiseaseService;
import cn.hsa.module.oper.operrecord.bo.OperAnesthesiaRecordBO;
import cn.hsa.module.oper.operrecord.dto.OperAnesthesiaDurgDTO;
import cn.hsa.module.oper.operrecord.dto.OperAnesthesiaMonitorDTO;
import cn.hsa.module.oper.operrecord.dto.OperAnesthesiaRecordDTO;
import cn.hsa.module.oper.operrecord.service.OperAnesthesiaRecordService;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.oper.operrecord.service.impl
 * @Class_name: OperAnesthesiaRecordServiceImpl
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/12/22 20:34
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
@HsafRestPath("/service/oper/operrecord")
@Service("operAnesthesiaRecordService_provider")
public class OperAnesthesiaRecordServiceImpl implements OperAnesthesiaRecordService {

    @Resource
    private OperAnesthesiaRecordBO operAnesthesiaRecordBO;

    @Resource
    private BaseDiseaseService baseDiseaseService;
    /**
    * @Menthod getOperAnesthesiaRecordById
    * @Desrciption 根据id查询麻醉记录
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2020/12/22 20:36
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.oper.operrecord.dto.OperAnesthesiaRecordDTO>
    **/
    @Override
    public WrapperResponse<OperAnesthesiaRecordDTO> getOperAnesthesiaRecordById(Map map) {
      OperAnesthesiaRecordDTO operAnesthesiaRecordDTO = MapUtils.get(map, "operAnesthesiaRecordDTO");
      OperAnesthesiaRecordDTO anesthesiaRecordDTO = operAnesthesiaRecordBO.getOperAnesthesiaRecordById(operAnesthesiaRecordDTO);
      if (anesthesiaRecordDTO!=null) {
          String diagnoseIds = anesthesiaRecordDTO.getOperDiseaseAfter();
          if (StringUtils.isNotEmpty(diagnoseIds)) {
              String[] split = diagnoseIds.split(",");
              List<String> diagnoseIdList = Arrays.asList(split);
              Map paramMap = new HashMap();
              paramMap.put("hospCode", anesthesiaRecordDTO.getHospCode());
              BaseDiseaseDTO baseDiseaseDTO = new BaseDiseaseDTO();
              baseDiseaseDTO.setHospCode(anesthesiaRecordDTO.getHospCode());
              baseDiseaseDTO.setIds(diagnoseIdList);
              paramMap.put("baseDiseaseDTO", baseDiseaseDTO);
              List<BaseDiseaseDTO> baseDiseaseDTOS = baseDiseaseService.queryAll(paramMap).getData();
              anesthesiaRecordDTO.setBaseDiseaseDTOS(baseDiseaseDTOS);
          }
      }
      return WrapperResponse.success(anesthesiaRecordDTO);
    }

    /**
    * @Menthod insertOperAnesthesiaRecord
    * @Desrciption 新增麻醉记录单
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2020/12/22 21:01
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> insertOperAnesthesiaRecord(Map map) {
      OperAnesthesiaRecordDTO operAnesthesiaRecordDTO = MapUtils.get(map, "operAnesthesiaRecordDTO");
      return WrapperResponse.success(operAnesthesiaRecordBO.insertOperAnesthesiaRecord(operAnesthesiaRecordDTO));
    }

    /**
    * @Menthod updateOperAnesthesiaRecord
    * @Desrciption 修改麻醉记录单
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2020/12/22 21:05
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> updateOperAnesthesiaRecord(Map map) {
      OperAnesthesiaRecordDTO operAnesthesiaRecordDTO = MapUtils.get(map, "operAnesthesiaRecordDTO");
      return WrapperResponse.success(operAnesthesiaRecordBO.updateOperAnesthesiaRecord(operAnesthesiaRecordDTO));
    }

    @Override
    public WrapperResponse<PageDTO> queryOperPatientPage(Map map) {
      return null;
    }

  /**
    * @Menthod queryOperAnesthesiaRecordPage
    * @Desrciption 分页查询病人麻醉单
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2020/12/23 14:24
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @Override
    public WrapperResponse<PageDTO> queryOperAnesthesiaRecordPage(Map map) {
      OperAnesthesiaRecordDTO operAnesthesiaRecordDTO = MapUtils.get(map, "operAnesthesiaRecordDTO");
      return WrapperResponse.success(operAnesthesiaRecordBO.queryOperAnesthesiaRecordPage(operAnesthesiaRecordDTO));
    }

    @Override
    public WrapperResponse<Boolean> deleteOperAnesthesiaRecordById(Map map) {
      return null;
    }

    @Override
    public WrapperResponse<OperAnesthesiaDurgDTO> getOperAnesthesiaDurgById(Map map) {
      return null;
    }

    @Override
    public WrapperResponse<Boolean> insertOperAnesthesiaDurg(Map map) {
      return null;
    }

    @Override
    public WrapperResponse<Boolean> updateOperAnesthesiaDurg(Map map) {
      return null;
    }

    @Override
    public WrapperResponse<Boolean> deleteOperAnesthesiaDurgById(Map map) {
      return null;
    }

    @Override
    public WrapperResponse<OperAnesthesiaMonitorDTO> getOperAnesthesiaMonitorById(Map map) {
      return null;
    }

    @Override
    public WrapperResponse<Boolean> insertOperAnesthesiaMonitor(Map map) {
      return null;
    }

    @Override
    public WrapperResponse<Boolean> updateOperAnesthesiaMonitor(Map map) {
      return null;
    }

    @Override
    public WrapperResponse<Boolean> deleteOperAnesthesiaMonitorById(Map map) {
      return null;
    }
}
