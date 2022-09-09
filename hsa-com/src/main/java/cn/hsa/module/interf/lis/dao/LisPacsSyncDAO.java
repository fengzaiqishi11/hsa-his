package cn.hsa.module.interf.lis.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Lis/Pacs系统同步DAO数据对象操作类
 * @author liudawen
 * @date 2022/9/1
 */
public interface LisPacsSyncDAO {

    /**
     * 获取门诊pacs检查申请单
     * @param map
     * @return
     */
    List<Map> listOutptPacsApply(Map map);
    /**
     * 获取住院pacs检查申请单
     * @param map
     * @return
     */
    List<Map> listInptPacsApply(Map map);
    /**
     * 保存pacs影像检查结果
     * @param mapList
     * @return
     */
    int insertPacsResult(List<Map> pacsResult);
    /**
     * 修改申请单状态
     * @param mapList
     * @return
     */
    int updateApplyDocumentSta(@Param("mapList")List<Map> mapList,@Param("hospCode")String hospCode,@Param("documentSta")String documentSta);
    /**
     * 获取科室字典信息集合
     * @param map
     * @return
     */
    List<Map> listLisDept(Map map);
    /**
     * 获取医生字典信息集合
     * @param map
     * @return
     */
    List<Map> listLisDoctor(Map map);
    /**
     * 获取收费项目字典信息集合
     * @param map
     * @return
     */
    List<Map> listLisAdvice(Map map);
    /**
     * 获取lis检验申请单集合
     * @param map
     * @return
     */
    List<Map> listLisApply(Map map);
    /**
     * 保存lis检验检验结果
     * @param lisResult
     * @return
     */
    int insertLisResult(List<Map> lisResult);
}
