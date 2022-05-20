package cn.hsa.module.interf.healthInfo.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.healthInfo.entity.*;

import java.util.List;
import java.util.Map;

/**
 * 本地基础字典数据Service
 * @author liudawen
 * @date 2022/5/13
 */
public interface LocalBasicDictService {

    /**
     * 查询医院的科室字典表信息
     * @param map
     * @return
     */
    WrapperResponse<List<TbKsxx>> getDeptDict(Map map);

    /**
     * 查询医护人员基本信息
     * @param map
     * @return
     */
    WrapperResponse<List<TbYhryjbxx>> getMedicalStaffBasicInfo(Map map);

    /**
     * 查询药品字典信息
     * @param map
     * @return
     */
    WrapperResponse<List<TbYpzd>> getDrugDict(Map map);

    /**
     * 查询诊疗项目字典信息
     * @param map
     * @return
     */
    WrapperResponse<List<TbZlxmzd>> getClinicProjectDict(Map map);

    /**
     * 查询疾病字典信息
     * @param map
     * @return
     */
    WrapperResponse<List<TbJbzd>> getDiseaseDict(Map map);

    /**
     * 查询物料字典信息
     * @param map
     * @return
     */
    WrapperResponse<List<TbWzzd>> getMaterialsDict(Map map);
}
