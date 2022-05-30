package cn.hsa.interf.healthInfo.bo.impl;

import cn.hsa.module.interf.healthInfo.bo.DrugBusinessServiceBO;
import cn.hsa.module.interf.healthInfo.dao.DrugBusinessDAO;
import cn.hsa.module.interf.healthInfo.entity.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
@Component
public class DrugBusinessServiceBOImpl implements DrugBusinessServiceBO {
    @Resource
    private DrugBusinessDAO drugBusinessDAO;

    /**
     * 4.5.1.1 诊后随访表（TB_ZHSF）
     *
     * @param map
     * @return
     */
    @Override
    public List<TbZhsf> getTbZhsfData(Map map) {
        return drugBusinessDAO.getTbZhsfData(map);
    }

    /**
     * 4.2.1.1 药品出入库信息（TB_YPRKXX）
     *
     * @param map
     * @return
     */
    @Override
    public List<TbYprkxx> getTbYprkxxData(Map map) {
        return drugBusinessDAO.getTbYprkxxData(map);
    }

    /**
     * 4.2.1.2 药品库存信息（TB_YPKCXX）
     *
     * @param map
     * @return
     */
    @Override
    public List<TbYpkcxx> getTbYpkcxxData(Map map) {
        return drugBusinessDAO.getTbYpkcxxData(map);
    }

    /**
     * 4.2.1.3 药品销售信息（TB_YPXSXX）
     *
     * @param map
     * @return
     */
    @Override
    public List<TbYpxsxx> getTbYpxxssData(Map map) {
        return drugBusinessDAO.getTbYpxxssData(map);
    }

    /**
     * 4.2.1.4 抗菌药物基本情况统计（TB_KJYWJBJKTJ）
     *
     * @param map
     * @return
     */
    @Override
    public List<TbKjywjbjktj> getTbKjywjbjktjData(Map map) {
        return drugBusinessDAO.getTbKjywjbjktjData(map);
    }

    /**
     * 4.2.1.5 抗菌药物各品种统计（TB_KJYWGPZTJ）
     *
     * @param map
     * @return
     */
    @Override
    public List<TbKjywgpztj> getTbKjywgpztjData(Map map) {
        return drugBusinessDAO.getTbKjywgpztjData(map);
    }

    /**
     * 4.4.1.1 双向转诊表（TB_SXZZ）
     *
     * @param map
     * @return
     */
    @Override
    public List<TbSxzz> getTbSxzzData(Map map) {
        return drugBusinessDAO.getTbSxzzData(map);
    }

    /**
     * 4.3.1.1 物资领取记录表（TB_WZLQJL）
     *
     * @param map
     * @return
     */
    @Override
    public List<TbWzlqjl> getTbWzlqjlData(Map map) {
        return drugBusinessDAO.getTbWzlqjlData(map);
    }

    /**
     * 2.9.1.1 业务量、收入统计表（TB_YWL_Report）
     *
     * @param map
     * @return
     */
    @Override
    public List<TbYwlReport> getTbYwlReportData(Map map) {
        return drugBusinessDAO.getTbYwlReportData(map);
    }
}
