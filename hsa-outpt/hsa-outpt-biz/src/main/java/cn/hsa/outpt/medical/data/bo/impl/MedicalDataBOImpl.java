package cn.hsa.outpt.medical.data.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.medic.data.bo.MedicalDataBO;
import cn.hsa.module.medic.data.dto.MedicTypeEnum;
import cn.hsa.module.medic.data.dto.MedicalDataDTO;
import cn.hsa.module.medic.data.dto.MedicalDataDetailDTO;
import cn.hsa.module.outpt.lis.dao.LisDao;
import cn.hsa.util.Constants;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @Package_name: cn.hsa.outpt.medical.data.bo.impl
* @class_name: MedicalDataBOImpl
* @Description:
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2021/1/28 15:40
* @Company: 创智和宇
**/
@Component
@Slf4j
public class MedicalDataBOImpl extends HsafBO implements MedicalDataBO {

    @Resource
    LisDao lisDao;

    /**
     * @Method: getMedicalDatas
     * @Description: 获取配置集合
     * @Param: [medicalDataDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 15:49
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public PageDTO getMedicalDatas(MedicalDataDTO medicalDataDTO) {
        PageHelper.startPage(medicalDataDTO.getPageNo(),medicalDataDTO.getPageSize());
        List<MedicalDataDTO> list = lisDao.getMedicalDatas(medicalDataDTO);
        list.stream().forEach(medicalDataDTO1 -> {
            for (MedicTypeEnum typeEnum:MedicTypeEnum.values()) {
                if (typeEnum.getCode().toString().equals(medicalDataDTO1.getType())) {
                    medicalDataDTO1.setTypeStr(typeEnum.getName());
                }
            }
            if ("1".equals(medicalDataDTO1.getSourceType())) {
                medicalDataDTO1.setSourceTypeStr("表/视图");
            } else if ("2".equals(medicalDataDTO1.getSourceType())) {
                medicalDataDTO1.setSourceTypeStr("接口");
            }
        });
        return PageDTO.of(list);
    }

    /**
     * @Method: getMedicalDataDetails
     * @Description: 获取配置明细集合
     * @Param: [medicalDataDetailDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 15:58
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public PageDTO getMedicalDataDetails(MedicalDataDetailDTO medicalDataDetailDTO) {
        PageHelper.startPage(medicalDataDetailDTO.getPageNo(),medicalDataDetailDTO.getPageSize());
        return PageDTO.of(lisDao.getMedicalDataDetails(medicalDataDetailDTO));
    }

    /**
     * @Method: getMedicalData
     * @Description: 获取配置对象
     * @Param: [medicalDataDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 16:11
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.medic.data.dto.MedicalDataDTO>
     **/
    @Override
    public MedicalDataDTO getMedicalData(MedicalDataDTO medicalDataDTO) {
        return lisDao.getMedicalData(medicalDataDTO);
    }

    /**
     * @Method: getMedicalData
     * @Description: 获取配置明细对象
     * @Param: [medicalDataDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 16:11
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.medic.data.dto.MedicalDataDTO>
     **/
    @Override
    public MedicalDataDetailDTO getMedicalDataDetail(MedicalDataDetailDTO medicalDataDetailDTO) {
        return lisDao.getMedicalDataDetail(medicalDataDetailDTO);
    }

    /**
     * @Method: insertMedicalData
     * @Description: 新增配置
     * @Param: [medicalDataDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 16:44
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.medic.data.dto.MedicalDataDetailDTO>
     **/
    @Override
    public Boolean insertMedicalData(MedicalDataDTO medicalDataDTO) {
        return lisDao.insertMedicalData(medicalDataDTO)>0;
    }

    /**
     * @Method: insertMedicalDataDetails
     * @Description: 批量新增配置明细
     * @Param: [medicalDataDetailDTOList]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 16:47
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public Boolean insertMedicalDataDetails(MedicalDataDTO medicalDataDTO) {
        //先删除
        lisDao.deleteMedicalDataDetails(medicalDataDTO);
        //再新增
        return lisDao.insertMedicalDataDetails(medicalDataDTO.getMedicalDataDetailDTOList())>0;
    }

    /**
     * @Method: updateMedicalData
     * @Description: 更新配置
     * @Param: [medicalDataDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 16:49
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public Boolean updateMedicalData(MedicalDataDTO medicalDataDTO) {
        return lisDao.updateMedicalData(medicalDataDTO)>0;
    }

    /**
     * @Method: deleteMedicalData
     * @Description: 删除配置
     * @Param: [medicalDataDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 16:54
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public Boolean deleteMedicalData(List<MedicalDataDTO> medicalDataDTOList) {
        medicalDataDTOList.stream().forEach(medicalDataDTO -> {
            if (Constants.SF.F.equals(medicalDataDTO.getStatusCode())) {
                medicalDataDTO.setOldStatusCode(Constants.SF.S);
            } else if (Constants.SF.S.equals(medicalDataDTO.getStatusCode())) {
                medicalDataDTO.setOldStatusCode(Constants.SF.F);
            } else {
                throw new AppException("状态错误");
            }
        });
        lisDao.deleteMedicalData(medicalDataDTOList);
        return true;
    }

    /**
     * @Method: getTyeps
     * @Description: 获取类型
     * @Param: []
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/2/2 16:46
     * @Return: java.util.List<java.util.Map>
     **/
    @Override
    public List<Map> getTyeps() {
        List<Map> list = new ArrayList<>();
        for (MedicTypeEnum e : MedicTypeEnum.values()) {
            Map map = new HashMap();
            map.put("value", e.getCode().toString());
            map.put("label", e.getName());
            list.add(map);
        }
        return list;
    }
}