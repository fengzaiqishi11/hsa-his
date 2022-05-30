package cn.hsa.module.interf.healthInfo.dao;

import cn.hsa.module.interf.healthInfo.entity.*;

import java.util.List;
import java.util.Map;

/**
 * 本地字典与标准映射数据DAO
 * @author liudawen
 * @date 2022/5/20
 */
public interface DictStandardMapDAO {

    /**
     * 查询科室字典映射
     * @param map
     * @return
     */
    List<TbKsys> listDeptDictMap(Map map);

    /**
     * 查询药品字典映射
     * @param map
     * @return
     */
    List<TbYpys> listDrugDictMap(Map map);

    /**
     * 查询疾病字典映射
     * @param map
     * @return
     */
    List<TbJbys> listDiseaseDictMap(Map map);

    /**
     * 查询诊间项目映射
     * @param map
     * @return
     */
    List<TbZlxmys> listClinicProjectMap(Map map);

    /**
     * 查询收费明细类型映射
     * @param map
     * @return
     */
    List<TbFylbys> listChargeDetailTypeMap(Map map);
}
