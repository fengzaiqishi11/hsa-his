package cn.hsa.interf.healthInfo.service.impl;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.healthInfo.bo.LocalBasicDictBO;
import cn.hsa.module.interf.healthInfo.entity.*;
import cn.hsa.module.interf.healthInfo.service.LocalBasicDictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 本地基础字典数据Service实现类
 * @author liudawen
 * @date 2022/5/13
 */
@Service("localBasicDictService_provider")
public class LocalBasicDictServiceImpl implements LocalBasicDictService {

    @Resource
    private LocalBasicDictBO localBasicDictBO;

    /**
     *  查询医院的科室字典表信息
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.interf.healthInfo.entity.TbKsxx>>
     * @Throws
     * @Date 2022/5/18 15:26
     **/
    @Override
    public WrapperResponse<List<TbKsxx>> getDeptDict(Map map) {
        return WrapperResponse.success(localBasicDictBO.listDeptDict(map));
    }

    /**
     *  查询医护人员基本信息
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.interf.healthInfo.entity.TbYhryjbxx>>
     * @Throws
     * @Date 2022/5/18 15:26
     **/
    @Override
    public WrapperResponse<List<TbYhryjbxx>> getMedicalStaffBasicInfo(Map map) {
        return WrapperResponse.success(localBasicDictBO.listMedicalStaffBasicInfo(map));
    }

    /**
     *  查询药品字典信息
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.interf.healthInfo.entity.TbYpzd>>
     * @Throws
     * @Date 2022/5/18 15:26
     **/
    @Override
    public WrapperResponse<List<TbYpzd>> getDrugDict(Map map) {
        return WrapperResponse.success(localBasicDictBO.listDrugDict(map));
    }

    /**
     *  查询诊疗项目字典信息
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.interf.healthInfo.entity.TbZlxmzd>>
     * @Throws
     * @Date 2022/5/18 15:26
     **/
    @Override
    public WrapperResponse<List<TbZlxmzd>> getClinicProjectDict(Map map) {
        return WrapperResponse.success(localBasicDictBO.listClinicProjectDict(map));
    }

    /**
     *  查询疾病字典信息
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.interf.healthInfo.entity.TbJbzd>>
     * @Throws
     * @Date 2022/5/18 15:26
     **/
    @Override
    public WrapperResponse<List<TbJbzd>> getDiseaseDict(Map map) {
        return WrapperResponse.success(localBasicDictBO.listDiseaseDict(map));
    }

    /**
     *  查询物料字典信息
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.interf.healthInfo.entity.TbWzzd>>
     * @Throws
     * @Date 2022/5/18 15:26
     **/
    @Override
    public WrapperResponse<List<TbWzzd>> getMaterialsDict(Map map) {
        return WrapperResponse.success(localBasicDictBO.listMaterialsDict(map));
    }
}
