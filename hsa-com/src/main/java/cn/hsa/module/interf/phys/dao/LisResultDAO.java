package cn.hsa.module.interf.phys.dao;

import cn.hsa.module.medic.apply.dto.MedicalApplyDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface LisResultDAO {
    /**
     * @Description: 保存lis结果
     * @Param: [medicalApplyDTOList]
     * @return: java.lang.Boolean
     * @Author: zhangxuan
     * @Date: 2021-07-05
     */
    Boolean saveLisResult(@Param("list") List<MedicalApplyDTO> medicalApplyDTOList);

    List<MedicalApplyDTO> queryNoResult(String days);

    /**
     * @Description: lis结果数据存库
     * @Param: [map]
     * @return: java.lang.Boolean
     * @Author: zhangxuan
     * @Date: 2021-07-09
     */
    int insertResult(@Param("list") List<Map> medicalResultDTOList);

    /**
     * @Description: lis结果数据存库
     * @Param: [map]
     * @return: java.lang.Boolean
     * @Author: zhangxuan
     * @Date: 2021-09-09
     */
    int insertDXResult(@Param("list") List<Map> medicalResultDTOList);

    /**
    * @Description: 更新申请单状态
    * @Param:
    * @return:
    * @Author: zhangxuan
    * @Date: 2021-09-09
    */
    int updateApplyStatus(@Param("list") List<String> stringList);

    /**
     * @Description: 更新申请单状态
     * @Param:
     * @return:
     * @Author: zhangxuan
     * @Date: 2021-09-09
     */
    int updateStatus(@Param("list") List<String> stringList);

    /**
     * @Description: 更新申请单状态
     * @Param:
     * @return:
     * @Author: zhangxuan
     * @Date: 2021-09-09
     */
    int updateStatusMap(@Param("list") List<Map> stringList);

    /** 
    * @Description: 获取未上传的申请单的医嘱id
    * @Param: 
    * @return: 
    * @Author: zhangxuan
    * @Date: 2021-09-04
    */ 
    List<Map> queryNoResultLis(Map map);

    /**
     * @Description:
     * @Param: [map]
     * @return: java.util.List<java.util.Map>
     * @Author: zhangxuan
     * @Date: 2022-01-13
     */
    List<Map> queryNoResultLisS(Map map);

    /** 
    * @Description: 查询没有结果的lis申请单的医嘱id
    * @Param: 
    * @return: 
    * @Author: zhangxuan
    * @Date: 2021-09-11
    */ 
    List<String> queryDXNoResult(Map map);

    /**
     * @Description: 查询没有结果的lis申请单的医嘱id
     * @Param:
     * @return:
     * @Author: zhangxuan
     * @Date: 2021-09-11
     */
    List<String> queryDXBackResult(Map map);
    /**
    * @Description: 更新申请单是否上传状态
    * @Param:
    * @return:
    * @Author: zhangxuan
    * @Date: 2021-10-08
    */
    int updateIsIssue(@Param("list") List<Map> mapList);

    List<Map> queryAdvice(Map map);

    List<Map> queryDept(Map map);

    List<Map> queryUser(Map map);

    /**
     * @Description: 根据barcode 删除旧的医技结果（线上版通用）
     * @Param: [stringList]
     * @return: java.lang.Boolean
     * @Author: zhangxuan
     * @Date: 2021-07-09
     */
    int deleteResult(@Param("list") List<Map> list);

    /**
     * 根据adviceId 删除旧的医技结果（适用落地版德星）
     * @param stringList
     * @return
     */
    int deleteResultDX(@Param("list") List<String> stringList);

    /**
     * 根据medic_apply 的 apply_no 更新申请单状态为 已完成状态
     * @param list
     * @return
     */
    int updateApplyStatusForLine(@Param("list") List<String> list);

}
