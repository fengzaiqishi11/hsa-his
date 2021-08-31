package cn.hsa.module.outpt.lis.dao;

import cn.hsa.module.base.ba.dto.BaseAdviceDTO;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.medic.result.dto.MedicalResultDTO;
import cn.hsa.module.outpt.lis.dto.LisInspectApplyActiveDTO;
import cn.hsa.module.outpt.lis.dto.LisPdfDTO;
import cn.hsa.module.medic.data.dto.MedicalDataDTO;
import cn.hsa.module.medic.data.dto.MedicalDataDetailDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.lis.dao
 * @Class_name: LisDao
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2021-01-04 10:05
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface LisDao {

    List<BaseDeptDTO> getDeptList(Map map);

    List<SysUserDTO> getDocList(Map map);

    List<BaseAdviceDTO> getItemList(Map map);

    List<Map> getPrescribeLis(Map map);

    List<Map> getAdviceLis(Map map);

    void insertLisInspect(List<LisInspectApplyActiveDTO> list);

    void insertLisInspectResult(List<Map> list);

    void insertLisCallbackStatus(List<Map> list);

    void insertLisPDF(LisPdfDTO lisPdfDTO);

    void insertLisCallbackCriticalValue(List<Map> list);

    /**
     * @Method: getMedicalDatas
     * @Description: 获取配置列表
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 15:19
     * @Return:
     **/
    List<MedicalDataDTO> getMedicalDatas(MedicalDataDTO medicalDataDTO);

    /**
     * @Method: getMedicalDatas
     * @Description: 获取配置明细列表
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 15:19
     * @Return:
     **/
    List<MedicalDataDetailDTO> getMedicalDataDetails(MedicalDataDetailDTO medicalDataDetailDTO);

    /**
     * @Method: getMedicalData
     * @Description: 根据参数获取配置对象
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 15:20
     * @Return:
     **/
    MedicalDataDTO getMedicalData(MedicalDataDTO medicalDataDTO);

    /**
     * @Method: getMedicalDataByType
     * @Description: 根据参数获取配置对象
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 15:20
     * @Return:
     **/
    List<MedicalDataDTO> getMedicalDataByType(MedicalDataDTO medicalDataDTO);

    /**
     * @Method: getMedicalData
     * @Description: 根据参数获取配置对象
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 15:20
     * @Return:
     **/
    MedicalDataDetailDTO getMedicalDataDetail(MedicalDataDetailDTO medicalDataDetailDTO);

    /**
     * @Method: insertMedicalData
     * @Description: 新增配置
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 15:14
     * @Return:
     **/
    int insertMedicalData(MedicalDataDTO medicalDataDTO);

    /**
     * @Method: insertMedicalDataDetails
     * @Description: 批量新增配置明细
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 15:15
     * @Return:
     **/
    int insertMedicalDataDetails(List<MedicalDataDetailDTO> list);

    /**
     * @Method: updateMedicalData
     * @Description: 修改配置
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 15:16
     * @Return:
     **/
    int updateMedicalData(MedicalDataDTO medicalDataDTO);

    /**
     * @Method: deleteMedicalData
     * @Description: 删除配置
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 15:15
     * @Return:
     **/
    void deleteMedicalData(List<MedicalDataDTO> medicalDataDTOList);

    /**
     * @Method: deleteMedicalData
     * @Description: 删除配置
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 15:15
     * @Return:
     **/
    void deleteMedicalDataDetails(MedicalDataDTO medicalDataDTO);

    List<Map> getLisDataList(String sql);

    int insertResults(List<MedicalResultDTO> medicalResultDTOList);

    int deleteResultByResultId(String hospCode, String resultId);
}