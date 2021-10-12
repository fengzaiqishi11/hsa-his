package cn.hsa.module.interf.phys.dao;

import cn.hsa.module.medic.apply.dto.MedicalApplyDTO;
import cn.hsa.module.medic.result.dto.MedicalResultDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;

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
    List<Map> queryNoResultLis();

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
    List<String> queryDXBackResult();
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
     * @Description:
     * @Param: [stringList]
     * @return: java.lang.Boolean
     * @Author: zhangxuan
     * @Date: 2021-07-09
     */
    Boolean deleteResult(@Param("list") List<String> stringList);

}
