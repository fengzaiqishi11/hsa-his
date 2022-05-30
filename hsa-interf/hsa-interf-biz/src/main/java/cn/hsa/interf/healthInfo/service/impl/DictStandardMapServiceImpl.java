package cn.hsa.interf.healthInfo.service.impl;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.healthInfo.bo.DictStandardMapBO;
import cn.hsa.module.interf.healthInfo.entity.*;
import cn.hsa.module.interf.healthInfo.service.DictStandardMapService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 本地字典与标准映射数据service实现类
 * @author liudawen
 * @date 2022/5/20
 */
@Service("dictStandardMapService_provider")
public class DictStandardMapServiceImpl implements DictStandardMapService {

    @Resource
    private DictStandardMapBO dictStandardMapBO;

    /**
     *  查询科室字典映射
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.interf.healthInfo.entity.TbKsys>>
     * @Throws
     * @Date 2022/5/20 9:13
     **/
    @Override
    public WrapperResponse<List<TbKsys>> getDeptDictMap(Map map) {
        return WrapperResponse.success(dictStandardMapBO.listDeptDictMap(map));
    }

    /**
     *  查询药品字典映射
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.interf.healthInfo.entity.TbYpys>>
     * @Throws
     * @Date 2022/5/20 9:13
     **/
    @Override
    public WrapperResponse<List<TbYpys>> getDrugDictMap(Map map) {
        return WrapperResponse.success(dictStandardMapBO.listDrugDictMap(map));
    }

    /**
     *  查询疾病字典映射
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.interf.healthInfo.entity.TbJbys>>
     * @Throws
     * @Date 2022/5/20 9:13
     **/
    @Override
    public WrapperResponse<List<TbJbys>> getDiseaseDictMap(Map map) {
        return WrapperResponse.success(dictStandardMapBO.listDiseaseDictMap(map));
    }

    /**
     *  查询诊间项目映射
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.interf.healthInfo.entity.TbZlxmys>>
     * @Throws
     * @Date 2022/5/20 9:13
     **/
    @Override
    public WrapperResponse<List<TbZlxmys>> getClinicProjectMap(Map map) {
        return WrapperResponse.success(dictStandardMapBO.listClinicProjectMap(map));
    }

    /**
     *  查询收费明细类型映射
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.interf.healthInfo.entity.TbFylbys>>
     * @Throws
     * @Date 2022/5/20 9:13
     **/
    @Override
    public WrapperResponse<List<TbFylbys>> getChargeDetailTypeMap(Map map) {
        return WrapperResponse.success(dictStandardMapBO.listChargeDetailTypeMap(map));
    }
}
