package cn.hsa.module.interf.healthInfo.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.healthInfo.entity.*;

import java.util.List;
import java.util.Map;

/**
 * 本地字典与标准映射数据service
 * 包含以下接口：
 * 科室字典映射（TB_KSYS）
 * 药品字典映射（TB_YPYS）
 * 诊疗项目字典映射（TB_ZLLXMYS）
 * 疾病字典映射（TB_JBZD）
 * 收费明细费用类别映射（TB_FYLBYS）
 * @author liudawen
 * @date 2022/5/20
 */
public interface DictStandardMapService {

    /**
     * 查询科室字典映射
     * @param map
     * @return
     */
    WrapperResponse<List<TbKsys>> getDeptDictMap(Map map);

    /**
     * 查询药品字典映射
     * @param map
     * @return
     */
    WrapperResponse<List<TbYpys>> getDrugDictMap(Map map);

    /**
     * 查询疾病字典映射
     * @param map
     * @return
     */
    WrapperResponse<List<TbJbys>> getDiseaseDictMap(Map map);

    /**
     * 查询诊间项目映射
     * @param map
     * @return
     */
    WrapperResponse<List<TbZlxmys>> getClinicProjectMap(Map map);

    /**
     * 查询收费明细类型映射
     * @param map
     * @return
     */
    WrapperResponse<List<TbFylbys>> getChargeDetailTypeMap(Map map);
}
