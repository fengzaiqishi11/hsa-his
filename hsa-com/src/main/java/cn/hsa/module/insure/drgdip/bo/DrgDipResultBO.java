package cn.hsa.module.insure.drgdip.bo;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.drgdip.dto.DrgDipAuthDTO;
import cn.hsa.module.insure.drgdip.dto.DrgDipComboDTO;
import cn.hsa.module.insure.drgdip.dto.DrgDipResultDTO;
import cn.hsa.module.insure.drgdip.dto.DrgDipResultDetailDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.advice.bo
 * @Class_name: AdviceBoImpl
 * @Describe(描述): 医嘱医保统一开放调用 Bo 接口
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/11/09 9:24
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface DrgDipResultBO {

    /**
     * 获取质控信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-06-07 15:54
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    DrgDipComboDTO getDrgDipInfoByParam(HashMap map);

  /**
   * 质控插入日志
   * @param map
   * @Author
   * @Date 2022-06-07 15:54
   * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
   */
  Boolean insertDrgDipQulityInfoLog(Map<String, Object> map);


    /**
     * 前端调用DRG DIP接口授权校验
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-06-08 9:42
     * @return java.lang.Boolean
     */
    DrgDipAuthDTO checkDrgDipBizAuthorization(Map<String, Object> map);
    DrgDipAuthDTO checkDrgDipBizAuthorizationSettle(Map<String, Object> map);

  /**
   * 质控结果保存
   * @param
   * @Author
   * @Date 2022-06-07 15:54
   * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
   */
  Boolean insertDrgDipResult(DrgDipResultDTO drgDipResultDTO,List<DrgDipResultDetailDTO> drgDipResultDetailDTOList);
}
