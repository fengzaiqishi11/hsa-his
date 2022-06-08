package cn.hsa.module.interf.healthInfo.bo;
import cn.hsa.module.interf.healthInfo.entity.*;

import java.util.List;
import java.util.Map;

public interface DrugBusinessServiceBO {
    /**
     * 4.5.1.1 诊后随访表（TB_ZHSF）
     * @param map
     * @return
     */
    List<TbZhsf> getTbZhsfData(Map map);

    /**
     * 4.2.1.1 药品出入库信息（TB_YPRKXX）
     * @param map
     * @return
     */
    List<TbYprkxx> getTbYprkxxData(Map map);

    /**
     * 4.2.1.2 药品库存信息（TB_YPKCXX）
     * @param map
     * @return
     */
    List<TbYpkcxx> getTbYpkcxxData(Map map);

    /**
     *4.2.1.3 药品销售信息（TB_YPXSXX）
     * @param map
     * @return
     */
    List<TbYpxsxx> getTbYpxxssData(Map map);

    /**
     *4.2.1.4 抗菌药物基本情况统计（TB_KJYWJBJKTJ）
     * @param map
     * @return
     */
    List<TbKjywjbjktj> getTbKjywjbjktjData(Map map);

    /**
     *4.2.1.5 抗菌药物各品种统计（TB_KJYWGPZTJ）
     * @param map
     * @return
     */
    List<TbKjywgpztj> getTbKjywgpztjData(Map map);
    /**
     * 4.4.1.1 双向转诊表（TB_SXZZ）
     *
     * @param map
     * @return
     */
    List<TbSxzz> getTbSxzzData(Map map);
    /**
     * 4.3.1.1 物资领取记录表（TB_WZLQJL）
     *
     * @param map
     * @return
     */
    List<TbWzlqjl> getTbWzlqjlData(Map map);
    /**
     * 2.9.1.1 业务量、收入统计表（TB_YWL_Report）
     *
     * @param map
     * @return
     */
    List<TbYwlReport> getTbYwlReportData(Map map);
}
