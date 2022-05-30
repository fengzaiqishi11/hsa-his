package cn.hsa.module.interf.healthInfo.bo;

import cn.hsa.module.interf.healthInfo.entity.*;

import java.util.List;
import java.util.Map;

/**
 * 本地基础字典数据BO
 * @author liudawen
 * @date 2022/5/13
 */
public interface LocalBasicDictBO {

    /**
     * 查询医院的科室字典表信息
     * @param map
     * @return
     */
    List<TbKsxx> listDeptDict(Map map);

    /**
     * 查询医护人员基本信息
     * @param map
     * @return
     */
    List<TbYhryjbxx> listMedicalStaffBasicInfo(Map map);

    /**
     * 查询药品字典信息
     * @param map
     * @return
     */
    List<TbYpzd> listDrugDict(Map map);

    /**
     * 查询诊疗项目字典信息
     * @param map
     * @return
     */
    List<TbZlxmzd> listClinicProjectDict(Map map);

    /**
     * 查询疾病字典信息
     * @param map
     * @return
     */
    List<TbJbzd> listDiseaseDict(Map map);

    /**
     * 查询物料字典信息
     * @param map
     * @return
     */
    List<TbWzzd> listMaterialsDict(Map map);
}
