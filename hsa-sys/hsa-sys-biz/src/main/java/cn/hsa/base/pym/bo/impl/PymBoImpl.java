package cn.hsa.base.pym.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.base.ba.dto.BaseAdviceDTO;
import cn.hsa.module.base.bd.dto.BaseDiseaseDTO;
import cn.hsa.module.base.bi.dto.BaseItemDTO;
import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import cn.hsa.module.base.pym.bo.PymBo;
import cn.hsa.module.base.pym.dao.PymDao;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.PinYinUtils;
import cn.hsa.util.WuBiUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_ame: cn.hsa.base.pym.bo.impl
 * @Class_name: hsa-his
 * @Description:
 * @Author: zengfeng
 * @Email: 954123283@qq.com
 * @Date: 2020/12/6  16:48
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
@Component
public class PymBoImpl extends HsafBO implements PymBo {
    @Resource
    private PymDao pymDao;

    @Override
    public Boolean updatePym(Map map) {
        //用户
        List<SysUserDTO> sysUserDTOList =  pymDao.querySysUserAll(map);
        for(SysUserDTO sysUserDTO : sysUserDTOList){
            sysUserDTO.setPym(PinYinUtils.toFirstPY(sysUserDTO.getName()));
            sysUserDTO.setWbm(WuBiUtils.getWBCode(sysUserDTO.getName()));
        }
        pymDao.updateSysUserAll(sysUserDTOList);

        /*//材料
        List<BaseMaterialDTO> BaseMaterialDTOList =  pymDao.queryBaseMaterialAll(map);
        for(BaseMaterialDTO baseMaterialDTO : BaseMaterialDTOList){
            //设置通用名拼音码
            //设置通用名五笔码
            baseMaterialDTO.setPym(PinYinUtils.toFirstPY(baseMaterialDTO.getName()));
            baseMaterialDTO.setWbm(WuBiUtils.getWBCode(baseMaterialDTO.getName()));
        }
       pymDao.updateBaseMaterial(BaseMaterialDTOList);

        //医嘱目录
        List<BaseAdviceDTO> BaseAdviceDTOList =  pymDao.queryBaseAdviceAll(map);
        for(BaseAdviceDTO baseAdviceDTO : BaseAdviceDTOList){
            //设置通用名拼音码
            //设置通用名五笔码
            baseAdviceDTO.setPym(PinYinUtils.toFirstPY(baseAdviceDTO.getName()));
            baseAdviceDTO.setWbm(WuBiUtils.getWBCode(baseAdviceDTO.getName()));
        }
        pymDao.updateBaseAdvice(BaseAdviceDTOList);

        //项目
        List<BaseItemDTO> BaseItemDTOList =  pymDao.queryBaseItem(map);
        for(BaseItemDTO baseItemDTO : BaseItemDTOList){
            baseItemDTO.setNamePym(PinYinUtils.toFirstPY(baseItemDTO.getName()));
            baseItemDTO.setNameWbm(WuBiUtils.getWBCode(baseItemDTO.getName()));
            //设置商品名拼音码
            //设置商品名五笔码
            baseItemDTO.setAbbrPym(PinYinUtils.toFirstPY(baseItemDTO.getAbbr()));
            baseItemDTO.setAbbrWbm(WuBiUtils.getWBCode(baseItemDTO.getAbbr()));
        }
        pymDao.updateBaseItem(BaseItemDTOList);

        //疾病
        List<BaseDiseaseDTO> baseDiseaseDTOList =  pymDao.queryDisease(map);
        for(BaseDiseaseDTO baseDiseaseDTO : baseDiseaseDTOList){
            baseDiseaseDTO.setPym(PinYinUtils.toFirstPY(baseDiseaseDTO.getName()));
            baseDiseaseDTO.setWbm(WuBiUtils.getWBCode(baseDiseaseDTO.getName()));
        }
        pymDao.updateDisease(baseDiseaseDTOList);*/
        return true;
    }

}
