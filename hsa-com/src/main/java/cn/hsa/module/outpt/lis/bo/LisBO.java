package cn.hsa.module.outpt.lis.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.medic.data.dto.MedicalDataDTO;
import cn.hsa.module.medic.data.dto.MedicalDataDetailDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.lis.bo
 * @Class_name: LisBO
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2021-01-04 10:04
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface LisBO {

    Boolean modifyDeptList(Map map);

    Boolean getDocList(Map map);

    Boolean getItemList(Map map);

    /**
     * @Method saveInspectApply
     * @Desrciption 提交检验申请单接口
       @params [map]
     * @Author chenjun
     * @Date   2021-01-06 10:08
     * @Return java.lang.Boolean
    **/
    Boolean saveInspectApply(Map map);

    /**
     * @Method saveInspectResult
     * @Desrciption 回传检验结果接口(第三方主动获取)
       @params [map]
     * @Author chenjun
     * @Date   2021-01-06 10:10
     * @Return java.lang.Boolean
    **/
    Boolean saveInspectResultActive(Map map);

    /**
     * @Method saveInspectResult
     * @Desrciption 回传检验结果接口(Lis主动推送)
       @params [map]
     * @Author chenjun
     * @Date   2021-01-06 11:11
     * @Return java.lang.Boolean
    **/
    Boolean saveInspectResult(Map map);

    Boolean getPDFReport(Map map);

    Boolean callbackStatus(Map map);

    Boolean callbackCriticalValue(Map map);

    Boolean criticalValue(Map map);

    /**
     * @Method: getMedicalDatas
     * @Description: 获取配置列表
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 15:19
     * @Return:
     **/
    PageDTO getMedicalDatas(MedicalDataDTO medicalDataDTO);

    /**
     * @Method: getMedicalDatas
     * @Description: 获取配置明细列表
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 15:19
     * @Return:
     **/
    PageDTO getMedicalDataDetails(MedicalDataDetailDTO medicalDataDetailDTO);

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
    Boolean insertMedicalData(MedicalDataDTO medicalDataDTO);

    /**
     * @Method: insertMedicalDataDetails
     * @Description: 批量新增配置明细
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 15:15
     * @Return:
     **/
    Boolean insertMedicalDataDetails(MedicalDataDTO medicalDataDTO);

    /**
     * @Method: updateMedicalData
     * @Description: 修改配置
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 15:16
     * @Return:
     **/
    Boolean updateMedicalData(MedicalDataDTO medicalDataDTO);

    /**
     * @Method: deleteMedicalData
     * @Description: 删除配置
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 15:15
     * @Return:
     **/
    Boolean deleteMedicalData(List<MedicalDataDTO> medicalDataDTOList);

    List<Map> getTyeps();
}