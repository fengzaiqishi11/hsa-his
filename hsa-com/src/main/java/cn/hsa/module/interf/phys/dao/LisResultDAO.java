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
