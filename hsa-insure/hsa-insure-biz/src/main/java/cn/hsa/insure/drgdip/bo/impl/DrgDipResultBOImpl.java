package cn.hsa.insure.drgdip.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;

import cn.hsa.module.insure.drgdip.bo.DrgDipResultBO;
import cn.hsa.module.insure.drgdip.dao.DrgDipResultDAO;
import cn.hsa.module.insure.drgdip.dao.DrgDipResultDetailDAO;
import cn.hsa.module.insure.drgdip.dto.DrgDipComboDTO;
import cn.hsa.module.insure.drgdip.dto.DrgDipResultDTO;
import cn.hsa.module.insure.drgdip.dto.DrgDipResultDetailDTO;
import cn.hsa.module.insure.drgdip.entity.DrgDipResultDO;
import cn.hsa.module.insure.drgdip.entity.DrgDipResultDetailDO;
import cn.hsa.util.MapUtils;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Component
@Slf4j
public class DrgDipResultBOImpl implements DrgDipResultBO {


    @Resource
    private DrgDipResultDAO drgDipResultDAO;

    @Resource
    private DrgDipResultDetailDAO drgDipResultDetailDAO;

  /**
   * 获取质控信息
   * @param map
   * @Author 医保开发二部-湛康
   * @Date 2022-06-07 15:57
   * @return cn.hsa.module.insure.drgdip.dto.DrgDipComboDTO
   */
    @Override
    public DrgDipComboDTO getDrgDipInfoByParam(HashMap map) {
      DrgDipResultDTO dto =MapUtils.get(map, "drgDipResultDTO");
      //新增质控信息
      DrgDipResultDO drgDipResultDO = drgDipResultDAO.queryListByVisitIdDesc(dto);
      //未质控过
      if (ObjectUtil.isEmpty(drgDipResultDO)){
        return null;
      }else{
        List<DrgDipResultDetailDO> list = drgDipResultDetailDAO.selectListByVisitId(drgDipResultDO.getId());
        //出参转换
        BeanUtil.copyProperties(drgDipResultDO,dto);
        List<DrgDipResultDetailDTO> dtoList = Convert.toList(DrgDipResultDetailDTO.class, list);
        DrgDipComboDTO combo = new DrgDipComboDTO();
        combo.setResult(dto);
        combo.setDetails(dtoList);
        return combo;
      }
    }
}
