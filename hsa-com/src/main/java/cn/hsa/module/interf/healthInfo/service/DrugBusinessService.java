package cn.hsa.module.interf.healthInfo.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.healthInfo.entity.*;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface DrugBusinessService {

    /**
     * 4.5.1.1 诊后随访表（TB_ZHSF）
     * @param map
     * @return
     */
    WrapperResponse<List<TbZhsf>> getTbZhsfData(Map map);

    /**
     * 4.2.1.1 药品出入库信息（TB_YPRKXX）
     * @param map
     * @return
     */
    WrapperResponse<List<TbYprkxx>> getTbYprkxxData(Map map);

    /**
     * 4.2.1.2 药品库存信息（TB_YPKCXX）
     * @param map
     * @return
     */
    WrapperResponse<List<TbYpkcxx>> getTbYpkcxxData(Map map);

    /**
     *4.2.1.3 药品销售信息（TB_YPXSXX）
     * @param map
     * @return
     */
    WrapperResponse<List<TbYpxsxx>> getTbYpxxssData(Map map);

    /**
     *4.2.1.4 抗菌药物基本情况统计（TB_KJYWJBJKTJ）
     * @param map
     * @return
     */
    WrapperResponse<List<TbKjywjbjktj>> getTbKjywjbjktjData(Map map);

    /**
     *4.2.1.5 抗菌药物各品种统计（TB_KJYWGPZTJ）
     * @param map
     * @return
     */
    WrapperResponse<List<TbKjywgpztj>> getTbKjywgpztjData(Map map);

    /**
     *4.4.1.1 双向转诊表（TB_SXZZ）
     * @param map
     * @return
     */
    WrapperResponse<List<TbSxzz>> getTbSxzzData(Map map);

    /**
     *4.3.1.1 物资领取记录表（TB_WZLQJL）
     * @param map
     * @return
     */
    WrapperResponse<List<TbWzlqjl>> getTbWzlqjlData(Map map);

    /**
     *2.9.1.1 业务量、收入统计表（TB_YWL_REPORT）
     * @param map
     * @return
     */
    WrapperResponse<List<TbYwlReport>> getTbYwlReportData(Map map);

}
