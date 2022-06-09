package cn.hsa.interf.healthInfo.bo.impl;

import cn.hsa.module.interf.healthInfo.bo.PhysBusinessBO;
import cn.hsa.module.interf.healthInfo.dao.PhysBusinessDAO;
import cn.hsa.module.interf.healthInfo.entity.TbYlTjbgsy;
import cn.hsa.module.interf.healthInfo.entity.TbYlTjfzbg;
import cn.hsa.module.interf.healthInfo.entity.TbYlTjmx;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 体检业务BO实现类
 * @author liudawen
 * @date 2022/5/20
 */
@Component
public class PhysBusinessBOImpl implements PhysBusinessBO {

    @Resource
    private PhysBusinessDAO physBusinessDAO;

    /**
     *  查询体检报告首页
     * @Author liudawen
     * @Param [map]
     * @Return java.util.List<cn.hsa.module.interf.healthInfo.entity.TbYlTjbgsy>
     * @Throws
     * @Date 2022/5/20 9:30
     **/
    @Override
    public List<TbYlTjbgsy> listPhysReportFront(Map map) {
        return null;
    }

    /**
     *  查询体检分组报告
     * @Author liudawen
     * @Param [map]
     * @Return java.util.List<cn.hsa.module.interf.healthInfo.entity.TbYlTjfzbg>
     * @Throws
     * @Date 2022/5/20 9:30
     **/
    @Override
    public List<TbYlTjfzbg> listPhysGroupingReport(Map map) {
        return null;
    }

    /**
     *  查询体检项目明细
     * @Author liudawen
     * @Param [map]
     * @Return java.util.List<cn.hsa.module.interf.healthInfo.entity.TbYlTjmx>
     * @Throws
     * @Date 2022/5/20 9:30
     **/
    @Override
    public List<TbYlTjmx> listPhysItemDetail(Map map) {
        return null;
    }
}
