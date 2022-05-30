package cn.hsa.interf.healthInfo.service.impl;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.healthInfo.bo.DrugBusinessServiceBO;
import cn.hsa.module.interf.healthInfo.entity.*;
import cn.hsa.module.interf.healthInfo.service.DrugBusinessService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("drugBusinessService_provider")
public class DrugBusinessServiceImpl implements DrugBusinessService {

    @Resource
    private DrugBusinessServiceBO drugBusinessServiceBO;

    /**
     * 4.5.1.1 诊后随访表（TB_ZHSF）
     *
     * @param map
     * @return
     */
    @Override
    public WrapperResponse<List<TbZhsf>> getTbZhsfData(Map map) {
        return WrapperResponse.success(drugBusinessServiceBO.getTbZhsfData(map));
    }

    /**
     * 4.2.1.1 药品出入库信息（TB_YPRKXX）
     *
     * @param map
     * @return
     */
    @Override
    public WrapperResponse<List<TbYprkxx>> getTbYprkxxData(Map map) {
        return WrapperResponse.success(drugBusinessServiceBO.getTbYprkxxData(map));
    }

    /**
     * 4.2.1.2 药品库存信息（TB_YPKCXX）
     *
     * @param map
     * @return
     */
    @Override
    public WrapperResponse<List<TbYpkcxx>> getTbYpkcxxData(Map map) {
        return WrapperResponse.success(drugBusinessServiceBO.getTbYpkcxxData(map));
    }

    /**
     * 4.2.1.3 药品销售信息（TB_YPXSXX）
     *
     * @param map
     * @return
     */
    @Override
    public WrapperResponse<List<TbYpxsxx>> getTbYpxxssData(Map map) {
        return WrapperResponse.success(drugBusinessServiceBO.getTbYpxxssData(map));
    }

    /**
     * 4.2.1.4 抗菌药物基本情况统计（TB_KJYWJBJKTJ）
     *
     * @param map
     * @return
     */
    @Override
    public WrapperResponse<List<TbKjywjbjktj>> getTbKjywjbjktjData(Map map) {
        return WrapperResponse.success(drugBusinessServiceBO.getTbKjywjbjktjData(map));
    }

    /**
     * 4.2.1.5 抗菌药物各品种统计（TB_KJYWGPZTJ）
     *
     * @param map
     * @return
     */
    @Override
    public WrapperResponse<List<TbKjywgpztj>> getTbKjywgpztjData(Map map) {
        return WrapperResponse.success(drugBusinessServiceBO.getTbKjywgpztjData(map));
    }

    /**
     * 4.4.1.1 双向转诊表（TB_SXZZ）
     *
     * @param map
     * @return
     */
    @Override
    public WrapperResponse<List<TbSxzz>> getTbSxzzData(Map map) {
        return WrapperResponse.success(drugBusinessServiceBO.getTbSxzzData(map));
    }

    /**
     * 4.3.1.1 物资领取记录表（TB_WZLQJL）
     *
     * @param map
     * @return
     */
    @Override
    public WrapperResponse<List<TbWzlqjl>> getTbWzlqjlData(Map map) {
        return WrapperResponse.success(drugBusinessServiceBO.getTbWzlqjlData(map));
    }

    /**
     * 2.9.1.1 业务量、收入统计表（TB_YWL_Report）
     *
     * @param map
     * @return
     */
    @Override
    public WrapperResponse<List<TbYwlReport>> getTbYwlReportData(Map map) {
        return WrapperResponse.success(drugBusinessServiceBO.getTbYwlReportData(map));
    }
}
