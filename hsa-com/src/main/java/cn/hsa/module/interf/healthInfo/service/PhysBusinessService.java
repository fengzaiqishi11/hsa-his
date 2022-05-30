package cn.hsa.module.interf.healthInfo.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.healthInfo.entity.TbYlTjbgsy;
import cn.hsa.module.interf.healthInfo.entity.TbYlTjfzbg;
import cn.hsa.module.interf.healthInfo.entity.TbYlTjmx;

import java.util.List;
import java.util.Map;

/**
 * 体检业务service
 * 包含以下接口：
 * 体检报告首页（TB_YL_TJBGSY）
 * 体检分组报告（TB_YL_TJFZBG）
 * 体检项目明细表（TB_YL_TJMX）
 * @author liudawen
 * @date 2022/5/20
 */
public interface PhysBusinessService {

    /**
     * 查询体检报告首页
     * @param map
     * @return
     */
    WrapperResponse<List<TbYlTjbgsy>> getPhysReportFront(Map map);

    /**
     * 查询体检分组报告
     * @param map
     * @return
     */
    WrapperResponse<List<TbYlTjfzbg>> getPhysGroupingReport(Map map);

    /**
     * 查询体检项目明细
     * @param map
     * @return
     */
    WrapperResponse<List<TbYlTjmx>> getPhysItemDetail(Map map);
}
