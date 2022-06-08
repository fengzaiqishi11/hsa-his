package cn.hsa.module.interf.healthInfo.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.healthInfo.entity.TbJcbg;
import cn.hsa.module.interf.healthInfo.entity.TbJctybg;

import java.util.List;
import java.util.Map;

/**
 * 医学影像检查报告service
 * 包含以下接口：
 * 医学影像检查报告表-常用检查报告格式（TB_JCBG）
 * 医学影像检查报告表-通用检查报告格式（TB_JCBG）
 * @author liudawen
 * @date 2022/5/20
 */
public interface MedicalImageService {


    /**
     * 查询医学影像检查报告表-通用检查报告格式
     * @param map
     * @return
     */
    WrapperResponse<List<TbJctybg>> getUniversalReportFormat(Map map);

    /**
     * 查询医学影像检查报告表-常见检查报告格式
     * @param map
     * @return
     */
    WrapperResponse<List<TbJcbg>> getNormalReportFormat(Map map);
}
