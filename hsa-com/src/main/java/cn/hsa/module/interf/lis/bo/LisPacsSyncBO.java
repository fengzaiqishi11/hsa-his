package cn.hsa.module.interf.lis.bo;

import java.util.List;
import java.util.Map;

/**
 * Lis/Pacs系统同步BO类
 * @author liudawen
 * @date 2022/9/1
 */
public interface LisPacsSyncBO {

    /**
     * 获取门诊pacs检查申请单，并修改其申请单状态为已发送
     * @param map
     * @return
     */
    List<Map> updateOutptPacsApply(Map map);

    /**
     * 获取住院pacs检查申请单，并修改其申请单状态为已发送
     * @param map
     * @return
     */
    List<Map> updateInptPacsApply(Map map);

    /**
     * 保存pacs影像检查结果，并修改其申请单状态为已接收结果
     * @param map
     * @return
     */
    Boolean savePacsResult(Map map);

    /**
     * 获取科室字典信息
     * @param map
     * @return
     */
    List<Map> getLisDept(Map map);

    /**
     * 获取医生字典信息
     * @param map
     * @return
     */
    List<Map> getLisDoctor(Map map);

    /**
     * 获取收费项目字典信息
     * @param map
     * @return
     */
    List<Map> getLisAdvice(Map map);

    /**
     * 获取lis检验申请单信息，并修改其申请单状态为已发送
     * @param map
     * @return
     */
    List<Map> updateLisApply(Map map);

    /**
     * 保存lis检验结果，并修改其申请单状态为已接收结果
     * @param map
     * @return
     */
    Boolean saveLisResult(Map map);
}
