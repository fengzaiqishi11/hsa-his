package cn.hsa.module.interf.healthInfo.dao;

import cn.hsa.module.interf.healthInfo.entity.*;

import java.util.List;
import java.util.Map;

/**
 * 本地基础字典数据DAO
 * @author liudawen
 * @date 2022/5/13
 */
public interface LocalBasicDictDAO {

    /**
     * 条件查询医院的科室字典表信息
     * @param map
     * @return
     */
    List<TbKsxx> listDeptDict(Map map);

    /**
     * 条件查询医护人员基本信息
     * @param map
     * @return
     */
    List<TbYhryjbxx> listMedicalStaffBasicInfo(Map map);

    /**
     * 条件查询药品字典信息
     * @param map
     * @return
     */
    List<TbYpzd> listDrugDict(Map map);

    /**
     * 条件查询诊疗项目字典信息
     * @param map
     * @return
     */
    List<TbZlxmzd> listClinicProjectDict(Map map);

    /**
     * 条件查询疾病字典信息
     * @param map
     * @return
     */
    List<TbJbzd> listDiseaseDict(Map map);

    /**
     * 条件查询物料字典信息
     * @param map
     * @return
     */
    List<TbWzzd> listMaterialsDict(Map map);

}
