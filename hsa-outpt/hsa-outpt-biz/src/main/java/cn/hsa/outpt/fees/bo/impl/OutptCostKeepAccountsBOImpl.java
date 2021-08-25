package cn.hsa.outpt.fees.bo.impl;

import cn.hsa.module.inpt.doctor.dto.OutptCostDTO;
import cn.hsa.module.outpt.fees.bo.OuptCostKeepAccountsBO;
import cn.hsa.module.outpt.fees.dao.OutptCostDAO;
import cn.hsa.util.MapUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
   * 门诊手术补记账业务实现层
   * @Author: luonianxin
   * @Email: nianxin.luo@powersi.com
   * @Date: 2021/5/27 8:52
   * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
**/
@Component
public class OutptCostKeepAccountsBOImpl implements OuptCostKeepAccountsBO {

    @Resource
    private OutptCostDAO outptCostDAO;

    /**
     * 门诊手术补记账
     * @param outptCostDTOs 传递的记账参数列表
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/5/26 20:19
     */

    @Override
    public Boolean insertOutptCostBatch(List<OutptCostDTO> outptCostDTOs) {

        return outptCostDAO.batchOutptCostInsert(outptCostDTOs) > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * 更新门诊病人的手术费用
     *
     * @param updateParams
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/5/31 13:42
     */
    @Override
    public Boolean updateOperInfoRecord(Map<String, Object> updateParams) {
        return outptCostDAO.updateOperInfoRecord(updateParams) >0? Boolean.TRUE : Boolean.FALSE;
    }
}
