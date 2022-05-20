package cn.hsa.module.interf.healthInfo.dao;

import cn.hsa.module.interf.healthInfo.entity.TbJcbg;
import cn.hsa.module.interf.healthInfo.entity.TbJctybg;

import java.util.List;
import java.util.Map;

/**
 * 医学影像检查报告DAO
 * @author liudawen
 * @date 2022/5/20
 */
public interface MedicalImageDAO {

    /**
     * 查询医学影像检查报告表-通用检查报告格式
     * @param map
     * @return
     */
    List<TbJctybg> listUniversalReportFormat(Map map);

    /**
     * 查询医学影像检查报告表-常见检查报告格式
     * @param map
     * @return
     */
    List<TbJcbg> listNormalReportFormat(Map map);
}
