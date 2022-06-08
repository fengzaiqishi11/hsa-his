package cn.hsa.interf.healthInfo.bo.impl;

import cn.hsa.module.interf.healthInfo.bo.LocalBasicDictBO;
import cn.hsa.module.interf.healthInfo.dao.LocalBasicDictDAO;
import cn.hsa.module.interf.healthInfo.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 本地基础字典数据BO实现类
 * @author liudawen
 * @date 2022/5/13
 */
@Component
@Slf4j
public class LocalBasicDictBOImpl implements LocalBasicDictBO {

    @Resource
    private LocalBasicDictDAO localBasicDictDAO;

    /**
     * 查询医院的科室字典表信息
     * @Author liudawen
     * @Param [map]
     * @Return java.util.List<cn.hsa.module.interf.healthInfo.entity.TbKsxx>
     * @Throws
     * @Date 2022/5/13 14:18
     **/
    @Override
    public List<TbKsxx> listDeptDict(Map map) {
        return localBasicDictDAO.listDeptDict(map);
    }

    /**
     *  查询医护人员基本信息
     * @Author liudawen
     * @Param [map]
     * @Return java.util.List<cn.hsa.module.interf.healthInfo.entity.TbYhryjbxx>
     * @Throws
     * @Date 2022/5/13 14:57
     **/
    @Override
    public List<TbYhryjbxx> listMedicalStaffBasicInfo(Map map) {
        return localBasicDictDAO.listMedicalStaffBasicInfo(map);
    }

    /**
     *  查询药品字典信息
     * @Author liudawen
     * @Param [map]
     * @Return java.util.List<cn.hsa.module.interf.healthInfo.entity.TbYpzd>
     * @Throws
     * @Date 2022/5/13 14:57
     **/
    @Override
    public List<TbYpzd> listDrugDict(Map map) {
        return localBasicDictDAO.listDrugDict(map);
    }

    /**
     *  查询诊疗项目字典信息
     * @Author liudawen
     * @Param [map]
     * @Return java.util.List<cn.hsa.module.interf.healthInfo.entity.TbZlxmzd>
     * @Throws
     * @Date 2022/5/13 14:57
     **/
    @Override
    public List<TbZlxmzd> listClinicProjectDict(Map map) {
        return localBasicDictDAO.listClinicProjectDict(map);
    }

    /**
     *  查询疾病字典信息
     * @Author liudawen
     * @Param [map]
     * @Return java.util.List<cn.hsa.module.interf.healthInfo.entity.TbJbzd>
     * @Throws
     * @Date 2022/5/13 14:57
     **/
    @Override
    public List<TbJbzd> listDiseaseDict(Map map) {
        return localBasicDictDAO.listDiseaseDict(map);
    }

    /**
     *  查询物料字典信息
     * @Author liudawen
     * @Param [map]
     * @Return java.util.List<cn.hsa.module.interf.healthInfo.entity.TbWzzd>
     * @Throws
     * @Date 2022/5/13 14:57
     **/
    @Override
    public List<TbWzzd> listMaterialsDict(Map map) {
        return localBasicDictDAO.listMaterialsDict(map);
    }
}
