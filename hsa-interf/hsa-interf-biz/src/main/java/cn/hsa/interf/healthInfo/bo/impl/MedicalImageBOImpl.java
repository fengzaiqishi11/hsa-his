package cn.hsa.interf.healthInfo.bo.impl;

import cn.hsa.module.interf.healthInfo.dao.MedicalImageDAO;
import cn.hsa.module.interf.healthInfo.entity.TbJcbg;
import cn.hsa.module.interf.healthInfo.entity.TbJctybg;
import cn.hsa.module.interf.healthInfo.service.MedicalImageBO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 医学影像检查报告BO实现类
 * @author liudawen
 * @date 2022/5/20
 */
@Component
public class MedicalImageBOImpl implements MedicalImageBO {

    @Resource
    private MedicalImageDAO medicalImageDAO;

    /**
     *  查询医学影像检查报告表-通用检查报告格式
     * @Author liudawen
     * @Param [map]
     * @Return java.util.List<cn.hsa.module.interf.healthInfo.entity.TbJctybg>
     * @Throws
     * @Date 2022/5/20 8:52
     **/
    @Override
    public List<TbJctybg> listUniversalReportFormat(Map map) {
        return null;
    }

    /**
     *  查询医学影像检查报告表-常见检查报告格式
     * @Author liudawen
     * @Param [map]
     * @Return java.util.List<cn.hsa.module.interf.healthInfo.entity.TbJcbg>
     * @Throws
     * @Date 2022/5/20 8:52
     **/
    @Override
    public List<TbJcbg> listNormalReportFormat(Map map) {
        return null;
    }
}
