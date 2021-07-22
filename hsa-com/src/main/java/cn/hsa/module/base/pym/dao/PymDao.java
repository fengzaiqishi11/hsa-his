package cn.hsa.module.base.pym.dao;

import cn.hsa.module.base.ba.dto.BaseAdviceDTO;
import cn.hsa.module.base.bd.dto.BaseDiseaseDTO;
import cn.hsa.module.base.bi.dto.BaseItemDTO;
import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import cn.hsa.module.base.nurse.dto.BaseNurseOrderDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_ame: cn.hsa.module.base.pym.dao
 * @Class_name: hsa-his
 * @Description:
 * @Author: zengfeng
 * @Email: 954123283@qq.com
 * @Date: 2020/12/6  16:52
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface PymDao {

    List<SysUserDTO> querySysUserAll(Map map);

    int updateSysUserAll(List<SysUserDTO> sysUserDTOList);

    List<BaseDrugDTO> queryBaseDrugAll(Map map);

    int updateBaseDrupg(List<BaseDrugDTO> baseDrugDTOList);

    List<BaseMaterialDTO> queryBaseMaterialAll(Map map);

    int updateBaseMaterial(List<BaseMaterialDTO> baseMaterialDTOList);

    List<BaseAdviceDTO> queryBaseAdviceAll(Map map);

    int updateBaseAdvice(List<BaseAdviceDTO> baseAdviceDTOList);

    List<BaseItemDTO> queryBaseItem(Map map);

    int updateBaseItem(List<BaseItemDTO> baseItemDTOList);

    List<BaseDiseaseDTO> queryDisease(Map map);

    int updateDisease(List<BaseDiseaseDTO> baseDiseaseDTODTOList);

}
