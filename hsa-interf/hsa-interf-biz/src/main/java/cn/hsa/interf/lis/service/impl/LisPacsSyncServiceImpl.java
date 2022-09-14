package cn.hsa.interf.lis.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.lis.bo.LisPacsSyncBO;
import cn.hsa.module.interf.lis.service.LisPacsSyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Lis/Pacs系统同步服务实现类
 * @author liudawen
 * @date 2022/9/1
 */
@Slf4j
@Service("lisPacsSyncService_provider")
@HsafRestPath("/service/interf/lispacs")
public class LisPacsSyncServiceImpl extends HsafService implements LisPacsSyncService {
    @Resource
    private LisPacsSyncBO lisPacsSyncBO;

    /**
     * 获取门诊pacs检查申请单，并修改其申请单状态为已发送
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map>>
     * @Throws
     * @Date 2022/9/1 10:20
     **/
    @Override
    public WrapperResponse<List<Map>> updateOutptPacsApply(Map map) {
        return WrapperResponse.success(lisPacsSyncBO.updateOutptPacsApply(map));
    }

    /**
     * 获取住院pacs检查申请单，并修改其申请单状态为已发送
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map>>
     * @Throws
     * @Date 2022/9/1 10:20
     **/
    @Override
    public WrapperResponse<List<Map>> updateInptPacsApply(Map map) {
        return WrapperResponse.success(lisPacsSyncBO.updateInptPacsApply(map));
    }

    /**
     * 保存pacs影像检查结果，并修改其申请单状态为已接收结果
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @Throws
     * @Date 2022/9/1 11:14
     **/
    @Override
    public WrapperResponse<Boolean> savePacsResult(Map map) {
        return WrapperResponse.success(lisPacsSyncBO.savePacsResult(map));
    }

    /**
     * 获取科室字典信息（中南职工医院B/S架构LIS系统接口方式对接）
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map>>
     * @Throws 
     * @Date 2022/9/6 15:30
     **/
    @Override
    public WrapperResponse<List<Map>> getLisDept(Map map) {
        return WrapperResponse.success(lisPacsSyncBO.getLisDept(map));
    }

    /**
     * 获取医生字典信息（中南职工医院B/S架构LIS系统接口方式对接）
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map>>
     * @Throws
     * @Date 2022/9/6 15:34
     **/
    @Override
    public WrapperResponse<List<Map>> getLisDoctor(Map map) {
        return WrapperResponse.success(lisPacsSyncBO.getLisDoctor(map));
    }

    /**
     * 获取收费项目字典信息（中南职工医院B/S架构LIS系统接口方式对接）
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map>>
     * @Throws
     * @Date 2022/9/6 15:34
     **/
    @Override
    public WrapperResponse<List<Map>> getLisAdvice(Map map) {
        return WrapperResponse.success(lisPacsSyncBO.getLisAdvice(map));
    }

    /**
     * 获取lis检验申请单信息，并修改其申请单状态为已发送（中南职工医院B/S架构LIS系统接口方式对接）
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map>>
     * @Throws
     * @Date 2022/9/6 15:34
     **/
    @Override
    public WrapperResponse<List<Map>> updateLisApply(Map map) {
        return WrapperResponse.success(lisPacsSyncBO.updateLisApply(map));
    }

    /**
     * 保存lis检验结果，并修改其申请单状态为已接收结果（中南职工医院B/S架构LIS系统接口方式对接）
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @Throws
     * @Date 2022/9/6 15:35
     **/
    @Override
    public WrapperResponse<Boolean> saveLisResult(Map map) {
        return WrapperResponse.success(lisPacsSyncBO.saveLisResult(map));
    }
}
