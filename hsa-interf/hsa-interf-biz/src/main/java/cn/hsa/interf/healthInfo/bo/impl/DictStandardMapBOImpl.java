package cn.hsa.interf.healthInfo.bo.impl;

import cn.hsa.module.interf.healthInfo.bo.DictStandardMapBO;
import cn.hsa.module.interf.healthInfo.dao.DictStandardMapDAO;
import cn.hsa.module.interf.healthInfo.entity.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 本地字典与标准映射数据BO实现类
 * @author liudawen
 * @date 2022/5/20
 */
@Component
public class DictStandardMapBOImpl implements DictStandardMapBO {

    @Resource
    private DictStandardMapDAO dictStandardMapDAO;

    /**
     * 查询科室字典映射
     * @Author liudawen
     * @Param [map]
     * @Return java.util.List<cn.hsa.module.interf.healthInfo.entity.TbKsys>
     * @Throws
     * @Date 2022/5/20 9:14
     **/
    @Override
    public List<TbKsys> listDeptDictMap(Map map) {
        return null;
    }

    /**
     * 查询药品字典映射
     * @Author liudawen
     * @Param [map]
     * @Return java.util.List<cn.hsa.module.interf.healthInfo.entity.TbYpys>
     * @Throws
     * @Date 2022/5/20 9:14
     **/
    @Override
    public List<TbYpys> listDrugDictMap(Map map) {
        return null;
    }

    /**
     * 查询疾病字典映射
     * @Author liudawen
     * @Param [map]
     * @Return java.util.List<cn.hsa.module.interf.healthInfo.entity.TbJbys>
     * @Throws
     * @Date 2022/5/20 9:14
     **/
    @Override
    public List<TbJbys> listDiseaseDictMap(Map map) {
        return null;
    }

    /**
     * 查询诊间项目映射
     * @Author liudawen
     * @Param [map]
     * @Return java.util.List<cn.hsa.module.interf.healthInfo.entity.TbZlxmys>
     * @Throws
     * @Date 2022/5/20 9:14
     **/
    @Override
    public List<TbZlxmys> listClinicProjectMap(Map map) {
        return null;
    }

    /**
     * 查询收费明细类型映射
     * @Author liudawen
     * @Param [map]
     * @Return java.util.List<cn.hsa.module.interf.healthInfo.entity.TbFylbys>
     * @Throws
     * @Date 2022/5/20 9:14
     **/
    @Override
    public List<TbFylbys> listChargeDetailTypeMap(Map map) {
        return null;
    }
}
