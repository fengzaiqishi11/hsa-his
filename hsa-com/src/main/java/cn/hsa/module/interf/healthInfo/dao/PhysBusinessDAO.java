package cn.hsa.module.interf.healthInfo.dao;

import cn.hsa.module.interf.healthInfo.entity.TbYlTjbgsy;
import cn.hsa.module.interf.healthInfo.entity.TbYlTjfzbg;
import cn.hsa.module.interf.healthInfo.entity.TbYlTjmx;

import java.util.List;
import java.util.Map;

/**
 * 体检业务DAO
 * @author liudawen
 * @date 2022/5/20
 */
public interface PhysBusinessDAO {

    /**
     * 查询体检报告首页
     * @param map
     * @return
     */
    List<TbYlTjbgsy> listPhysReportFront(Map map);

    /**
     * 查询体检分组报告
     * @param map
     * @return
     */
    List<TbYlTjfzbg> listPhysGroupingReport(Map map);

    /**
     * 查询体检项目明细
     * @param map
     * @return
     */
    List<TbYlTjmx> listPhysItemDetail(Map map);
}
