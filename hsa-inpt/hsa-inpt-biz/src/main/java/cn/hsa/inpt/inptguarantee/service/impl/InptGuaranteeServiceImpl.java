package cn.hsa.inpt.inptguarantee.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.inpt.inptguarantee.bo.InptGuaranteeBO;
import cn.hsa.module.inpt.inptguarantee.dto.InptGuaranteeDTO;
import cn.hsa.module.inpt.inptguarantee.service.InptGuaranteeService;
import cn.hsa.util.BigDecimalUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.inpt.inptguarantee.service.impl
 * @Class_name: InptGuaranteeServiceImpl
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/8/10 15:34
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/inpt/inptGuaranteeService")
@Slf4j
@Service("inptGuaranteeService_provider")
public class InptGuaranteeServiceImpl implements InptGuaranteeService {

    @Autowired
    private InptGuaranteeBO inptGuaranteeBO;

    /**
    * @Menthod queryById
    * @Desrciption 查询单个保证金信息
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/8/10 15:51
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.inpt.inptguarantee.dto.InptGuaranteeDTO>
    **/
    @Override
    public WrapperResponse<InptGuaranteeDTO> queryById(Map map) {
      InptGuaranteeDTO inptGuaranteeDTO = MapUtils.get(map, "inptGuaranteeDTO");
      return WrapperResponse.success(inptGuaranteeBO.queryById(inptGuaranteeDTO));
    }

    /**
    * @Menthod queryAllInptGuarantee
    * @Desrciption 查询所有保证金
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/8/10 15:51
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.inptguarantee.dto.InptGuaranteeDTO>>
    **/
    @Override
    public WrapperResponse<List<InptGuaranteeDTO>> queryAllInptGuarantee(Map map) {
      InptGuaranteeDTO inptGuaranteeDTO = MapUtils.get(map, "inptGuaranteeDTO");
      return WrapperResponse.success(inptGuaranteeBO.queryAllInptGuarantee(inptGuaranteeDTO));
    }

    /**
    * @Menthod queryPageInptGuarantee
    * @Desrciption 分页查询所有保证金
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/8/10 15:52
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @Override
    public WrapperResponse<PageDTO> queryPageInptGuarantee(Map map) {
      InptGuaranteeDTO inptGuaranteeDTO = MapUtils.get(map, "inptGuaranteeDTO");
      return WrapperResponse.success(inptGuaranteeBO.queryPageInptGuarantee(inptGuaranteeDTO));
    }

    /**
    * @Menthod save
    * @Desrciption  保存
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/8/10 15:52
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> save(Map map) {
      InptGuaranteeDTO inptGuaranteeDTO = MapUtils.get(map, "inptGuaranteeDTO");
      if(StringUtils.isEmpty(inptGuaranteeDTO.getGuaranteeId())) {
        throw new AppException("保证人不能为空");
      }
      return WrapperResponse.success(inptGuaranteeBO.save(inptGuaranteeDTO));
    }

    /**
    * @Menthod updateAuditCode
    * @Desrciption  修改状态
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/8/10 15:52
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> updateAuditCode(Map map) {
      InptGuaranteeDTO inptGuaranteeDTO = MapUtils.get(map, "inptGuaranteeDTO");
      return WrapperResponse.success(inptGuaranteeBO.updateAuditCode(inptGuaranteeDTO));
    }
}
