package cn.hsa.inpt.inptguarantee.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.inptguarantee.bo.InptGuaranteeBO;
import cn.hsa.module.inpt.inptguarantee.dao.InptGuaranteeDAO;
import cn.hsa.module.inpt.inptguarantee.dto.InptGuaranteeDTO;
import cn.hsa.util.BigDecimalUtils;
import cn.hsa.util.ListUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.inpt.inptguarantee.bo.impl
 * @Class_name: InptGuaranteeBOImp
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/8/10 15:39
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class InptGuaranteeBOImpl implements InptGuaranteeBO {

  @Resource
  private InptGuaranteeDAO inptGuaranteeDAO;

  /**
   * 单据生成规则dubbo消费者接口
   */
  @Resource
  private BaseOrderRuleService baseOrderRuleService;

  /**
  * @Menthod queryById
  * @Desrciption
  *
  * @Param
  * [inptGuaranteeDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/8/10 16:07
  * @Return cn.hsa.module.inpt.inptguarantee.dto.InptGuaranteeDTO
  **/
  @Override
  public InptGuaranteeDTO queryById(InptGuaranteeDTO inptGuaranteeDTO) {
    return inptGuaranteeDAO.queryById(inptGuaranteeDTO);
  }

  /**
  * @Menthod queryAllInptGuarantee
  * @Desrciption
  *
  * @Param
  * [inptGuaranteeDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/8/10 16:07
  * @Return java.util.List<cn.hsa.module.inpt.inptguarantee.dto.InptGuaranteeDTO>
  **/
  @Override
  public List<InptGuaranteeDTO> queryAllInptGuarantee(InptGuaranteeDTO inptGuaranteeDTO) {
    return inptGuaranteeDAO.queryAllInptGuarantee(inptGuaranteeDTO);
  }


  /**
  * @Menthod queryPageInptGuarantee
  * @Desrciption
  *
  * @Param
  * [inptGuaranteeDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/8/10 16:07
  * @Return cn.hsa.base.PageDTO
  **/
  @Override
  public PageDTO queryPageInptGuarantee(InptGuaranteeDTO inptGuaranteeDTO) {
    PageHelper.startPage(inptGuaranteeDTO.getPageNo(), inptGuaranteeDTO.getPageSize());
    List<InptGuaranteeDTO> inptGuaranteeDTOS = inptGuaranteeDAO.queryAllInptGuarantee(inptGuaranteeDTO);
    return PageDTO.of(inptGuaranteeDTOS);
  }

  /**
  * @Menthod save
  * @Desrciption 保存
  *
  * @Param
  * [inptGuaranteeDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/8/10 16:07
  * @Return java.lang.Boolean
  **/
  @Override
  public Boolean save(InptGuaranteeDTO inptGuaranteeDTO) {
    if(StringUtils.isEmpty(inptGuaranteeDTO.getId())) {
      Map map = new HashMap();
      map.put("typeCode", "118");
      map.put("hospCode", inptGuaranteeDTO.getHospCode());
      String data = baseOrderRuleService.getOrderNo(map).getData();
      inptGuaranteeDTO.setGuaranteeNo(data);
      inptGuaranteeDTO.setId(SnowflakeUtils.getId());
      inptGuaranteeDTO.setAuditCode("0");
      return inptGuaranteeDAO.insert(inptGuaranteeDTO) > 0;
    }
    return inptGuaranteeDAO.update(inptGuaranteeDTO) > 0;
  }

  /**
  * @Menthod updateAuditCode
  * @Desrciption
  *
  * @Param
  * [inptGuaranteeDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/8/10 16:07
  * @Return java.lang.Boolean
  **/
  @Override
  public Boolean updateAuditCode(InptGuaranteeDTO inptGuaranteeDTO) {
    List<InptGuaranteeDTO> inptGuaranteeDTOS = inptGuaranteeDAO.queryAllInptGuaranteeByIds(inptGuaranteeDTO);
    if(ListUtils.isEmpty(inptGuaranteeDTOS)) {
      throw new AppException("未获取到担保信息");
    }
    BigDecimal sumNum = BigDecimal.valueOf(0);
    for (InptGuaranteeDTO item : inptGuaranteeDTOS) {
      if(!"0".equals(item.getAuditCode())){
        throw new AppException("只能审核和作废未审核的数据");
      }
      sumNum = BigDecimalUtils.add(sumNum,item.getPrice());
    }
    // 就诊信息
    InptVisitDTO inptVisitDTO = new InptVisitDTO();
    inptVisitDTO.setHospCode(inptGuaranteeDTO.getHospCode());
    inptVisitDTO.setId(inptGuaranteeDTOS.get(0).getVisitId());
    InptVisitDTO visitById = inptGuaranteeDAO.getVisitById(inptVisitDTO);
    // 如果审核，就更新就诊表里保证金
    if("1".equals(inptGuaranteeDTO.getAuditCode())) {
      visitById.setGuaranteeBalance(BigDecimalUtils.add(
        BigDecimalUtils.nullToZero(visitById.getGuaranteeBalance()),sumNum));
      inptGuaranteeDAO.updatePeopleGuaranteeBalance(visitById);
    }
    return inptGuaranteeDAO.updateAuditCode(inptGuaranteeDTO) > 0;
  }
}
