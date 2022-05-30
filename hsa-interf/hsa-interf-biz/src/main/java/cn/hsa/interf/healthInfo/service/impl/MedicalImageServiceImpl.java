package cn.hsa.interf.healthInfo.service.impl;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.healthInfo.entity.TbJcbg;
import cn.hsa.module.interf.healthInfo.entity.TbJctybg;
import cn.hsa.module.interf.healthInfo.service.MedicalImageBO;
import cn.hsa.module.interf.healthInfo.service.MedicalImageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 医学影像检查报告service实现类
 * @author liudawen
 * @date 2022/5/20
 */
@Service("medicalImageService_provider")
public class MedicalImageServiceImpl implements MedicalImageService {

    @Resource
    private MedicalImageBO medicalImageBO;

    /**
     *  查询医学影像检查报告表-通用检查报告格式
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.interf.healthInfo.entity.TbJctybg>>
     * @Throws
     * @Date 2022/5/20 8:49
     **/
    @Override
    public WrapperResponse<List<TbJctybg>> getUniversalReportFormat(Map map) {
        return WrapperResponse.success(medicalImageBO.listUniversalReportFormat(map));
    }

    /**
     * 查询医学影像检查报告表-常见检查报告格式
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.interf.healthInfo.entity.TbJcbg>>
     * @Throws
     * @Date 2022/5/20 8:44
     **/
    @Override
    public WrapperResponse<List<TbJcbg>> getNormalReportFormat(Map map) {
        return WrapperResponse.success(medicalImageBO.listNormalReportFormat(map));
    }
}
