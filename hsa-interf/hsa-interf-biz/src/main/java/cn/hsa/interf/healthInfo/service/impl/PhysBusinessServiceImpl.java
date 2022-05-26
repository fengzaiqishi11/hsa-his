package cn.hsa.interf.healthInfo.service.impl;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.healthInfo.bo.PhysBusinessBO;
import cn.hsa.module.interf.healthInfo.entity.TbYlTjbgsy;
import cn.hsa.module.interf.healthInfo.entity.TbYlTjfzbg;
import cn.hsa.module.interf.healthInfo.entity.TbYlTjmx;
import cn.hsa.module.interf.healthInfo.service.PhysBusinessService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 体检业务service实现类
 * @author liudawen
 * @date 2022/5/20
 */
@Service("physBusinessService_provider")
public class PhysBusinessServiceImpl implements PhysBusinessService {

    @Resource
    private PhysBusinessBO physBusinessBO;

    /**
     *  查询体检报告首页
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.interf.healthInfo.entity.TbYlTjbgsy>>
     * @Throws
     * @Date 2022/5/20 9:26
     **/
    @Override
    public WrapperResponse<List<TbYlTjbgsy>> getPhysReportFront(Map map) {
        return WrapperResponse.success(physBusinessBO.listPhysReportFront(map));
    }

    /**
     *  查询体检分组报告
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.interf.healthInfo.entity.TbYlTjfzbg>>
     * @Throws
     * @Date 2022/5/20 9:26
     **/
    @Override
    public WrapperResponse<List<TbYlTjfzbg>> getPhysGroupingReport(Map map) {
        return WrapperResponse.success(physBusinessBO.listPhysGroupingReport(map));
    }

    /**
     *  查询体检项目明细
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.interf.healthInfo.entity.TbYlTjmx>>
     * @Throws
     * @Date 2022/5/20 9:26
     **/
    @Override
    public WrapperResponse<List<TbYlTjmx>> getPhysItemDetail(Map map) {
        return WrapperResponse.success(physBusinessBO.listPhysItemDetail(map));
    }
}
