package cn.hsa.insure.drgdip.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.drgdip.dto.DrgDipComboDTO;
import cn.hsa.module.insure.drgdip.dto.DrgDipResultDTO;
import cn.hsa.module.insure.drgdip.service.DrgDipResultService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@HsafRestPath("/service/insure/drgDipResultService")
@Service("drgDipResultService")
public class DrgDipResultServiceImpl extends HsafService implements DrgDipResultService {

  /**
   * 查询质控信息
   * @param dtoo
   * @Author 医保开发二部-湛康
   * @Date 2022-06-07 15:48
   * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.insure.drgdip.dto.DrgDipComboDTO>
   */
  @Override
  public WrapperResponse<DrgDipComboDTO> getDrgDipInfoByParam(DrgDipResultDTO dtoo) {
    return null;
  }
}
